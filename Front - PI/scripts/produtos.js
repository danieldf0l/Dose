// scripts/produtos.js

const API_URL = "http://localhost:8080/produto"; // endpoint do backend

// Seleciona o botão salvar
document.querySelector(".btn_slv_prdts").addEventListener("click", async () => {
    
    // Captura os valores dos inputs
    const nome = document.getElementById("nome-produto").value.trim();
    const valor = document.getElementById("valor-produto").value.trim();
    // NOVO: Captura o código de barras
    const codigoBarras = document.getElementById("codigo-produto").value.trim();
    const tipo = document.getElementById("tipo-produto").value.trim();

    // Validação simples
    // NOVO: Inclui o codigoBarras na validação
    if (!nome || !valor || !codigoBarras || !tipo) { 
        alert("Preencha todos os campos!");
        return;
    }
    
    // Converte o valor para float, tratando formatos "R$ 100,00"
    let valorNumerico;
    try {
        // Remove R$ e vírgulas, substitui por ponto para o parseFloat
        valorNumerico = parseFloat(valor.replace("R$", "").replace(",", ".").trim());
        if (isNaN(valorNumerico)) {
            throw new Error("Valor inválido");
        }
    } catch (e) {
        alert("O valor do produto deve ser um número válido.");
        return;
    }

    // Monta o objeto no formato EXATO esperado pelo backend (Produto.java)
    const produto = {
        // CHAVE PRINCIPAL: O nome do campo JSON deve ser igual ao nome da variável Java
        codigo_barras: codigoBarras, 
        nome_produto: nome,
        valor_produto: valorNumerico,
        tipo_produto: tipo
    };

    try {
        // Faz o POST pro backend
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(produto)
        });

        if (response.ok) {
            alert("Produto cadastrado com sucesso!");
            document.querySelector("form.campos_prdts").reset(); // limpa o form
        } else {
            const errorText = await response.text();
            console.error("Erro do servidor:", errorText);
            
            // Tenta dar um feedback mais útil para o usuário
            if (response.status === 400) {
                alert("Erro de validação. Verifique se o Tipo está correto ou se o Código de Barras já existe.");
            } else {
                 alert("Erro ao cadastrar produto! Detalhes no console.");
            }
        }
    } catch (error) {
        console.error("Erro de conexão ou durante a requisição:", error);
        alert("Não foi possível conectar ao servidor (http://localhost:8080). Verifique se o Spring Boot está rodando.");
    }
});