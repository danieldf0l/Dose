package br.com.tavernadovale.tavernadovale.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos_venda")
public class ProdutosVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_venda")
    protected int id_produto_venda;
    
    // Mapeamento para a Venda (fk_id_venda)
    @ManyToOne
    @JoinColumn(name = "fk_id_venda", nullable = false)
    protected Venda venda;
    
    // Mapeamento para o Produto (fk_codigo_barras)
    @ManyToOne
    @JoinColumn(name = "fk_codigo_barras", nullable = false) // Coluna do banco agora é fk_codigo_barras
    protected Produto produto;
    
    @Column(name = "quantidade_venda", nullable = false)
    protected int quantidade_venda;
    

    // Getters e Setters

    public int getId_produto_venda() {
        return id_produto_venda;
    }

    public void setId_produto_venda(int id_produto_venda) {
        this.id_produto_venda = id_produto_venda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade_venda() {
        return quantidade_venda;
    }

    public void setQuantidade_venda(int quantidade_venda) {
        this.quantidade_venda = quantidade_venda;
    }
}