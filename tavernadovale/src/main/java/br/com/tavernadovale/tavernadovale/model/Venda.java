package br.com.tavernadovale.tavernadovale.model;

import java.sql.Timestamp;
import java.util.List; // Import para a lista de itens da venda

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType; // Novo import necessário
import jakarta.persistence.OneToMany;   // Novo import necessário

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda", nullable = true)
    protected int id_venda;
    
    @Column(name = "valor_parcial", nullable = true)
    protected double valor_parcial_venda;
    
    @Column(name = "valor_final", nullable = true)
    protected double valor_final_venda;
    
    @Column(name = "forma_pagamento", length = 45, nullable = true)
    protected String forma_pagamento_venda;
    
    @Column(name = "data_venda", nullable = true)
    protected Timestamp data_hora_venda;

    // NOVO CAMPO: Lista de itens da venda
    // Mapeamento OneToMany: Uma Venda tem Múltiplos ProdutosVenda.
    // mappedBy="venda": Indica que a chave estrangeira (fk_id_venda) está na classe ProdutosVenda.
    // cascade=CascadeType.ALL: Garante que, ao salvar a Venda, seus ProdutosVenda também sejam salvos (ou deletados).
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutosVenda> produtosVenda;

    // --- Getters e Setters Existentes ---

    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public double getValor_parcial_venda() {
        return valor_parcial_venda;
    }

    public void setValor_parcial_venda(double valor) {
        this.valor_parcial_venda = valor;
    }

    public double getValor_final_venda() {
        return valor_final_venda;
    }

    public void setValor_final_venda(double valor_final) {
        this.valor_final_venda = valor_final;
    }

    public String getForma_pagamento_venda() {
        return forma_pagamento_venda;
    }

    public void setForma_pagamento_venda(String forma_pagamento_venda) {
        this.forma_pagamento_venda = forma_pagamento_venda;
    }

    public Timestamp getData_hora_venda() {
        return data_hora_venda;
    }

    // Corrigindo o nome do método para data_hora_venda para consistência, se necessário
    public void setData_venda(Timestamp data_hora_venda) {
        this.data_hora_venda = data_hora_venda;
    }

    // --- Novos Getters e Setters para a Lista de Itens ---
    
    public List<ProdutosVenda> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<ProdutosVenda> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }
}