// scripts/funcionario.js

const API_URL = "http://localhost:8080/funcionario"; // Endpoint do backend para Funcionário

// Seleciona o botão salvar e adiciona o evento de clique
document.querySelector(".btn_slv_funcn").addEventListener("click", async () => {
    
    // 1. Captura os valores dos inputs
    const nome = document.getElementById("nome-funcionario").value.trim();
    const cargo = document.getElementById("cargo-funcionario").value.trim();
    const entrada = document.getElementById("horario-entrada").value.trim();
    const saida = document.getElementById("horario-saida").value.trim();

    // 2. Validação simples
    if (!nome || !cargo || !entrada || !saida) { 
        alert("Preencha todos os campos do funcionário!");
        return;
    }

    // 3. Monta o objeto JSON no formato esperado pelo backend (Funcionario.java)
    // As chaves devem corresponder aos nomes das variáveis na sua entidade Java (Ex: nome_funcionario)
    const funcionario = {
        nome_funcionario: nome,
        cargo_funcionario: cargo,
        horario_entrada: entrada, // Horário é enviado como String (HH:mm:ss)
        horario_saida: saida      // Horário é enviado como String (HH:mm:ss)
    };

    try {
        // 4. Faz a requisição POST para o backend
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(funcionario)
        });

        // 5. Trata a resposta
        if (response.ok) {
            alert("Funcionário cadastrado com sucesso!");
            document.querySelector("form.campos_funcn").reset(); // Limpa o formulário
        } else {
            const errorText = await response.text();
            console.error("Erro do servidor:", errorText);
            alert("Erro ao cadastrar funcionário! Verifique o console para detalhes.");
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
        alert("Não foi possível conectar ao servidor. Verifique se o backend está rodando.");
    }
});