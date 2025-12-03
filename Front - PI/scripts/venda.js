document.addEventListener("DOMContentLoaded", function () {

    const form = document.querySelector(".campos_vnds");

    form.addEventListener("submit", function (e) {
        e.preventDefault(); // impede recarregar página

        // Pega os valores dos campos
        const valorParcial = document.getElementById("nome-produto").value;
        const valorFinal = document.getElementById("valor-produto").value;
        const formaPagamento = document.getElementById("forma-pagamento").value;
        const dataVenda = document.getElementById("data_venda").value;

        // Monta o objeto JSON exatamente como o BACK-END espera
        const venda = {
            valor_parcial_venda: parseFloat(valorParcial),
            valor_final_venda: parseFloat(valorFinal),
            forma_pagamento_venda: formaPagamento,
            data_venda: dataVenda
        };

        console.log("Enviando venda:", venda);

        // Faz o POST para a API
        fetch("http://localhost:8080/venda", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(venda)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao cadastrar venda.");
            }
            return response.json();
        })
        .then(data => {
            alert("Venda cadastrada com sucesso! ID: " + data.id_venda);

            // limpa os campos
            form.reset();
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Erro ao cadastrar venda. Verifique a API.");
        });
    });
});
