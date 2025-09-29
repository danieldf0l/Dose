// scripts/estoque.js

const API_URL = "http://localhost:8080/estoque"; // Endpoint do backend para Estoque

// Seleciona o botão salvar
document.querySelector(".btn_slv_estq").addEventListener("click", async () => {
    
    // 1. Captura os valores dos inputs
    const codigoBarras = document.getElementById("codigo-estoque").value.trim();
    const quantidade = document.getElementById("quatidade-estoque").value.trim();
    const dataValidade = document.getElementById("data-estoque").value.trim(); // Formato YYYY-MM-DD
    const numeroLote = document.getElementById("lote-estoque").value.trim();

    // 2. Validação simples
    if (!codigoBarras || !quantidade || !dataValidade || !numeroLote) { 
        alert("Preencha todos os campos do estoque!");
        return;
    }
    
    // Converte a quantidade para número inteiro
    const quantidadeLote = parseInt(quantidade, 10);
    if (isNaN(quantidadeLote) || quantidadeLote <= 0) {
        alert("Quantidade inválida.");
        return;
    }

    // 3. Monta o objeto Produto minimalista para a FK
    const produtoRelacionado = {
        // O campo deve se chamar 'codigo_barras' (como na Entidade Produto.java)
        codigo_barras: codigoBarras 
    };

    // 4. Monta o objeto Estoque no formato esperado pelo backend (Estoque.java)
    const estoque = {
        // O campo do relacionamento deve se chamar 'produto' (como na Entidade Estoque.java)
        produto: produtoRelacionado, 
        
        quantidade_lote: quantidadeLote,
        data_validade: dataValidade, // O Spring/Jackson aceita YYYY-MM-DD para java.sql.Date
        numero_lote: numeroLote
    };

    try {
        // 5. Faz a requisição POST para o backend
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(estoque)
        });

        // 6. Trata a resposta
        if (response.ok) {
            alert("Registro de Estoque cadastrado com sucesso!");
            document.querySelector("form.campos_estq").reset(); // Limpa o formulário
        } else {
            const errorText = await response.text();
            console.error("Erro do servidor:", errorText);
            
            // Se falhar (ex: código de barras inexistente), o backend retorna um erro.
            if (response.status === 500 && errorText.includes("ConstraintViolationException")) {
                alert("Erro: O Código de Barras do Produto não foi encontrado. Cadastre o produto primeiro!");
            } else {
                 alert("Erro ao cadastrar estoque! Verifique o console para detalhes.");
            }
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
        alert("Não foi possível conectar ao servidor. Verifique se o backend está rodando.");
    }
});