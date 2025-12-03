const API_URLS = {
    PRODUTO: "http://localhost:8080/produto",
    ESTOQUE: "http://localhost:8080/estoque"
};

document.addEventListener('DOMContentLoaded', () => {
    const formVenda = document.getElementById('form-venda');
    const btnAdicionarItem = document.getElementById('btn-adicionar-item');
    const inputEAN = document.getElementById('input-ean');
    const btnBuscarProduto = document.getElementById('btn-buscar-produto');
    const selectLote = document.getElementById('select-lote');
    const inputQuantidade = document.getElementById('input-quantidade');
    const tabelaItensBody = document.querySelector('#tabela-itens tbody');
    
    let itensVenda = [];
    let produtoAtual = null;
    let todosLotes = []; // Vamos armazenar todos os lotes aqui

    // --- FUNÇÕES DE BUSCA DE PRODUTO ---

    async function buscarProdutoPorEAN(codigoBarras) {
        try {
            const codigoLimpo = codigoBarras.trim();
            
            if (!codigoLimpo) {
                alert('Por favor, digite um código de barras válido.');
                return null;
            }

            // Busca o produto
            const response = await fetch(`http://localhost:8080/produto/${codigoLimpo}`);
            
            if (response.status === 404) {
                alert('Produto não encontrado! Verifique o código de barras.');
                return null;
            }
            
            if (!response.ok) {
                throw new Error('Falha ao buscar produto.');
            }
            
            const produto = await response.json();
            return produto;
            
        } catch (error) {
            console.error("Erro ao buscar produto:", error);
            alert("Erro ao buscar produto. Verifique o console.");
            return null;
        }
    }

    // Função para carregar TODOS os lotes uma vez
    async function carregarTodosLotes() {
        try {
            const response = await fetch(API_URLS.ESTOQUE);
            if (!response.ok) throw new Error('Falha ao carregar lotes.');
            todosLotes = await response.json();
            console.log('Todos os lotes carregados:', todosLotes);
        } catch (error) {
            console.error("Erro ao carregar lotes:", error);
            alert("Erro ao carregar dados de estoque.");
        }
    }

    // Função para filtrar lotes por código de barras
    function filtrarLotesPorProduto(codigoBarras) {
        selectLote.innerHTML = '<option value="">Filtrando lotes...</option>';
        selectLote.disabled = true;

        // Filtra os lotes pelo código de barras do produto
        const lotesDoProduto = todosLotes.filter(lote => 
            lote.produto.codigo_barras === codigoBarras
        );

        console.log(`Lotes encontrados para ${codigoBarras}:`, lotesDoProduto);

        selectLote.innerHTML = '<option value="">Selecione o Lote</option>';
        
        if (lotesDoProduto.length === 0) {
            selectLote.innerHTML = '<option value="">Nenhum lote disponível</option>';
            alert('Este produto não possui lotes cadastrados em estoque.');
            return;
        }

        let lotesDisponiveis = false;
        lotesDoProduto.forEach(lote => {
            if (lote.quantidade_lote > 0) {
                lotesDisponiveis = true;
                const option = document.createElement('option');
                option.value = lote.id_registro_estoque;
                option.textContent = `Lote: ${lote.numero_lote} (Qtd: ${lote.quantidade_lote}, Validade: ${lote.data_validade})`;
                option.dataset.lote = JSON.stringify(lote);
                selectLote.appendChild(option);
            }
        });

        if (!lotesDisponiveis) {
            selectLote.innerHTML = '<option value="">Estoque esgotado</option>';
            alert('Todos os lotes deste produto estão com estoque zerado.');
        } else {
            selectLote.disabled = false;
        }
    }

    // --- EVENTOS DE INTERAÇÃO NA TELA ---

    btnBuscarProduto.addEventListener('click', async () => {
        const codigoBarras = inputEAN.value;
        
        if (!codigoBarras) {
            alert('Por favor, digite um código de barras.');
            return;
        }

        // Busca o produto
        produtoAtual = await buscarProdutoPorEAN(codigoBarras);
        
        if (produtoAtual) {
            console.log(`Produto encontrado:`, produtoAtual);
            
            // Habilita o campo de quantidade
            inputQuantidade.disabled = false;
            inputQuantidade.focus();
            
            // Filtra os lotes deste produto
            filtrarLotesPorProduto(codigoBarras);
        } else {
            selectLote.innerHTML = '<option value="">Produto não encontrado</option>';
            selectLote.disabled = true;
            inputQuantidade.disabled = true;
        }
    });

    inputEAN.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            btnBuscarProduto.click();
        }
    });

    // Evento: Adicionar item à tabela (ATUALIZADO para pegar dados corretos)
    btnAdicionarItem.addEventListener('click', () => {
        if (!produtoAtual) {
            alert('Primeiro busque um produto válido.');
            return;
        }

        const idLote = selectLote.value;
        const quantidade = parseInt(inputQuantidade.value);

        if (!idLote || isNaN(quantidade) || quantidade <= 0) {
            alert('Por favor, selecione um lote e informe uma quantidade válida.');
            return;
        }

        // Encontra o lote selecionado nos dados
        const selectedOption = selectLote.options[selectLote.selectedIndex];
        const loteData = JSON.parse(selectedOption.dataset.lote);
        
        console.log('Dados do lote selecionado:', loteData);
        
        // Verifica estoque
        if (quantidade > loteData.quantidade_lote) {
            alert(`Quantidade solicitada (${quantidade}) excede o estoque disponível (${loteData.quantidade_lote}).`);
            return;
        }

        // Cria o objeto ProdutosVenda (ajustado para sua estrutura)
        const itemVenda = {
            quantidade_venda: quantidade,
            produto: { 
                codigo_barras: produtoAtual.codigo_barras 
            },
            estoque: { 
                id_registro_estoque: parseInt(idLote) 
            }
        };
        
        // Adiciona nome do produto para exibição
        itemVenda.nomeProduto = produtoAtual.nome_produto;
        itemVenda.numeroLote = loteData.numero_lote;
        
        itensVenda.push(itemVenda);
        
        // Adiciona na tabela
        adicionarItemNaTabela(
            produtoAtual.nome_produto, 
            loteData.numero_lote, 
            quantidade, 
            itensVenda.length - 1
        );

        // Limpa campos
        resetarCamposItem();
    });

    // Função para resetar campos
    function resetarCamposItem() {
        inputEAN.value = '';
        selectLote.innerHTML = '<option value="">Busque um novo produto</option>';
        selectLote.disabled = true;
        inputQuantidade.value = '';
        inputQuantidade.disabled = true;
        produtoAtual = null;
        inputEAN.focus();
    }

    // Função para adicionar item na tabela
    function adicionarItemNaTabela(nomeProduto, numeroLote, quantidade, index) {
        const newRow = tabelaItensBody.insertRow();
        newRow.innerHTML = `
            <td>${nomeProduto}</td>
            <td>${numeroLote}</td>
            <td>${quantidade}</td>
            <td><button type="button" onclick="removerItem(${index})">Remover</button></td>
        `;
    }

    // Função global para remover item (ATUALIZADA)
    window.removerItem = function(index) {
        // Remove o item
        const itemRemovido = itensVenda.splice(index, 1)[0];
        console.log('Item removido:', itemRemovido);
        
        // Atualiza a tabela
        tabelaItensBody.innerHTML = '';
        
        // Recria toda a tabela
        itensVenda.forEach((item, i) => {
            adicionarItemNaTabela(
                item.nomeProduto || `Produto: ${item.produto.codigo_barras}`,
                item.numeroLote || `Lote ID: ${item.estoque.id_registro_estoque}`,
                item.quantidade_venda,
                i
            );
        });
    };

    // --- EVENTO: SUBMISSÃO DA VENDA ---
    
formVenda.addEventListener('submit', async (e) => {
    e.preventDefault();

    if (itensVenda.length === 0) {
        alert('A venda precisa ter pelo menos um item.');
        return;
    }

    // Coleta dados da venda
    const valorParcial = parseFloat(document.getElementById('valor-parcial').value.replace('R$', '').replace(',', '.').trim());
    const valorFinal = parseFloat(document.getElementById('valor-final').value.replace('R$', '').replace(',', '.').trim());
    const formaPagamento = document.getElementById('forma-pagamento').value;
    const dataVenda = document.getElementById('data_venda').value;

    if (isNaN(valorParcial) || isNaN(valorFinal) || !formaPagamento || !dataVenda) {
        alert('Por favor, preencha todos os campos da venda corretamente.');
        return;
    }
    
    // Prepara os itens para envio
    const itensParaEnvio = itensVenda.map(item => ({
        quantidade_venda: item.quantidade_venda,
        produto: { codigo_barras: item.produto.codigo_barras },
        estoque: { id_registro_estoque: item.estoque.id_registro_estoque }
        // NÃO inclui a venda aqui - será associada automaticamente pelo backend
    }));

    const vendaParaEnvio = {
        valor_parcial_venda: valorParcial,
        valor_final_venda: valorFinal,
        forma_pagamento_venda: formaPagamento,
        data_hora_venda: dataVenda,
        produtosVenda: itensParaEnvio
    };

    console.log('Enviando venda:', vendaParaEnvio);

    try {
        const response = await fetch('http://localhost:8080/venda', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(vendaParaEnvio),
        });

        if (response.ok) {
            const resultado = await response.json();
            alert('Venda registrada com sucesso! ID: ' + resultado.id_venda);
            
            // Limpa tudo
            formVenda.reset();
            itensVenda = [];
            tabelaItensBody.innerHTML = '';
            resetarCamposItem();
            
            // Recarrega os lotes (para atualizar quantidades)
            await carregarTodosLotes();
            
        } else {
            const errorText = await response.text();
            alert(`Falha ao registrar a venda: ${errorText}`);
        }
    } catch (error) {
        console.error('Erro ao enviar a venda:', error);
        alert('Erro de conexão ao finalizar a venda.');
    }
});
// --- INICIALIZAÇÃO ---
    inputQuantidade.disabled = true;
    
    // Carrega todos os lotes quando a página carrega
    carregarTodosLotes();

});