document.addEventListener('DOMContentLoaded', () => {
    const formVenda = document.getElementById('form-venda');
    const btnAdicionarItem = document.getElementById('btn-adicionar-item');
    const selectProduto = document.getElementById('select-produto');
    const selectLote = document.getElementById('select-lote');
    const inputQuantidade = document.getElementById('input-quantidade');
    const tabelaItensBody = document.querySelector('#tabela-itens tbody');
    
    // Array para armazenar os itens da venda antes de serem submetidos
    let itensVenda = [];

    // --- FUNÇÕES DE CARREGAMENTO DE DADOS ---

    // Função para carregar todos os produtos disponíveis
    async function carregarProdutos() {
        try {
            // Ajuste a URL para o seu endpoint de listar produtos
            const response = await fetch('/api/produtos'); 
            if (!response.ok) throw new Error('Falha ao carregar produtos.');
            const produtos = await response.json();
            
            produtos.forEach(produto => {
                const option = document.createElement('option');
                option.value = produto.codigo_barras;
                option.textContent = produto.nome_produto;
                // Armazena o objeto produto completo no dataset para uso posterior
                option.dataset.produto = JSON.stringify(produto); 
                selectProduto.appendChild(option);
            });
        } catch (error) {
            console.error("Erro ao carregar produtos:", error);
            alert("Erro ao carregar produtos. Verifique o console.");
        }
    }

    // Função para carregar os lotes (registros de estoque) de um produto selecionado
    async function carregarLotes(codigoBarras) {
        selectLote.innerHTML = '<option value="">Carregando Lotes...</option>';
        selectLote.disabled = true;

        try {
            // Ajuste a URL para o seu endpoint de listar estoque por produto
            const response = await fetch(`/api/estoque/produto/${codigoBarras}`); 
            if (!response.ok) throw new Error('Falha ao carregar lotes.');
            const lotes = await response.json();

            selectLote.innerHTML = '<option value="">Selecione o Lote</option>';
            
            lotes.forEach(lote => {
                // Apenas lotes com quantidade > 0
                if (lote.quantidade_lote > 0) {
                    const option = document.createElement('option');
                    // O valor é o ID do registro de estoque, crucial para a baixa no backend
                    option.value = lote.id_registro_estoque;
                    option.textContent = `Lote #${lote.numero_lote} (Qtd: ${lote.quantidade_lote}, Validade: ${lote.data_validade})`;
                    // Armazena o objeto lote completo no dataset
                    option.dataset.lote = JSON.stringify(lote); 
                    selectLote.appendChild(option);
                }
            });

            selectLote.disabled = false;
        } catch (error) {
            console.error("Erro ao carregar lotes:", error);
            selectLote.innerHTML = '<option value="">Erro ao carregar Lotes</option>';
        }
    }

    // --- EVENTOS DE INTERAÇÃO NA TELA ---

    // Evento: Quando um produto é selecionado, carrega os lotes dele
    selectProduto.addEventListener('change', (e) => {
        const codigoBarras = e.target.value;
        selectLote.disabled = true;
        selectLote.innerHTML = '<option value="">Selecione o Lote</option>';
        if (codigoBarras) {
            carregarLotes(codigoBarras);
        }
    });

    // Evento: Adicionar um item à tabela e ao array de itensVenda
    btnAdicionarItem.addEventListener('click', () => {
        const codigoBarras = selectProduto.value;
        const idLote = selectLote.value;
        const quantidade = parseInt(inputQuantidade.value);

        if (!codigoBarras || !idLote || isNaN(quantidade) || quantidade <= 0) {
            alert('Por favor, preencha todos os campos do item corretamente.');
            return;
        }

        const selectedOptionLote = selectLote.options[selectLote.selectedIndex];
        const loteData = JSON.parse(selectedOptionLote.dataset.lote);
        const produtoData = JSON.parse(selectProduto.options[selectProduto.selectedIndex].dataset.produto);
        
        // Verifica se a quantidade vendida é menor ou igual à quantidade no lote
        if (quantidade > loteData.quantidade_lote) {
            alert(`Quantidade solicitada (${quantidade}) excede o estoque disponível para este lote (${loteData.quantidade_lote}).`);
            return;
        }

        // Cria o objeto ProdutosVenda no formato que o backend espera:
        const itemVenda = {
            quantidade_venda: quantidade,
            // Apenas o código de barras é necessário para identificar o Produto (FK no banco)
            produto: { codigo_barras: codigoBarras }, 
            // Apenas o ID do registro de estoque é necessário para dar baixa no Service
            estoque: { id_registro_estoque: parseInt(idLote) }
        };
        
        // Adiciona ao array principal
        itensVenda.push(itemVenda);

        // Renderiza o item na tabela (apenas para visualização do usuário)
        adicionarItemNaTabela(produtoData.nome_produto, idLote, quantidade, itensVenda.length - 1);

        // Limpa os campos de item para o próximo
        selectProduto.value = '';
        selectLote.value = '';
        selectLote.disabled = true;
        inputQuantidade.value = '';
    });

    // Função para renderizar o item na tabela
    function adicionarItemNaTabela(nomeProduto, idLote, quantidade, index) {
        const newRow = tabelaItensBody.insertRow();
        newRow.innerHTML = `
            <td>${nomeProduto}</td>
            <td>${idLote}</td>
            <td>${quantidade}</td>
            <td><button type="button" onclick="removerItem(${index})">Remover</button></td>
        `;
    }

    // Função global para remover item (precisa ser global ou acessível)
    window.removerItem = function(index) {
        // Remove do array de submissão
        itensVenda.splice(index, 1);
        // Atualiza a tabela (mais fácil recarregar tudo)
        tabelaItensBody.innerHTML = '';
        itensVenda.forEach((item, i) => {
            // Recarrega os dados do produto para exibir o nome (não está no array itensVenda)
            // Para simplificar, vamos exibir apenas o ID do produto, ou manter o nome na renderização
            // Como não temos o nome no array principal, uma solução rápida é:
            const nomeProdutoDisplay = `Produto ID: ${item.produto.codigo_barras}`;
            adicionarItemNaTabela(nomeProdutoDisplay, item.estoque.id_registro_estoque, item.quantidade_venda, i);
        });
        // Se a lógica acima parecer complexa, a melhor prática seria armazenar o nome do produto no array itensVenda.
    };
    
    // --- EVENTO: SUBMISSÃO FINAL DA VENDA ---

    formVenda.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (itensVenda.length === 0) {
            alert('A venda precisa ter pelo menos um item.');
            return;
        }

        // 1. Coleta os dados principais da Venda
        const valorParcial = parseFloat(document.getElementById('valor-parcial').value.replace('R$', '').replace(',', '.').trim());
        const valorFinal = parseFloat(document.getElementById('valor-final').value.replace('R$', '').replace(',', '.').trim());
        const formaPagamento = document.getElementById('forma-pagamento').value;
        const dataVenda = document.getElementById('data_venda').value; // Formato yyyy-mm-dd

        if (isNaN(valorParcial) || isNaN(valorFinal) || !formaPagamento || !dataVenda) {
            alert('Por favor, preencha todos os campos da venda corretamente.');
            return;
        }
        
        // 2. Monta o objeto Venda no formato JSON esperado pelo Java (Venda Model)
        const vendaParaEnvio = {
            // Seus campos de Venda
            valor_parcial_venda: valorParcial,
            valor_final_venda: valorFinal,
            forma_pagamento_venda: formaPagamento,
            // O backend (Java) provavelmente irá gerar o Timestamp, mas enviamos a data como string
            data_hora_venda: dataVenda, 
            
            // Campo crucial: lista de itens (ProdutosVenda)
            produtosVenda: itensVenda 
        };

        try {
            // 3. Envia o objeto para o Controller/Service
            const response = await fetch('/api/vendas', { // Ajuste a URL para seu VendaController
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(vendaParaEnvio),
            });

            if (response.ok) {
                alert('Venda registrada com sucesso e estoque baixado!');
                // Limpar formulário após sucesso
                formVenda.reset();
                itensVenda = [];
                tabelaItensBody.innerHTML = '';
            } else {
                // Se o Service lançou a RuntimeException (por falta de estoque), a resposta não será 'ok'
                const errorText = await response.text();
                alert(`Falha ao registrar a venda. Erro no Servidor: ${errorText}`);
                console.error('Erro na resposta do servidor:', response.status, errorText);
            }
        } catch (error) {
            console.error('Erro ao enviar a venda:', error);
            alert('Erro de conexão ao tentar finalizar a venda.');
        }
    });

    // 4. Inicia o carregamento de dados
    carregarProdutos();
});