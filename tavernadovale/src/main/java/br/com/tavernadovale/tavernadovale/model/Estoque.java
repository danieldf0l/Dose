package br.com.tavernadovale.tavernadovale.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_estoque")
    protected int id_registro_estoque; // Renomeado para seguir o banco

    // NOVO: Mapeamento ManyToOne com a entidade Produto
    @ManyToOne
    @JoinColumn(name = "fk_codigo_barras", nullable = false) // Coluna do banco agora é fk_codigo_barras
    protected Produto produto; // Objeto Produto que este estoque se refere

    @Column(name = "quantidade_lote", nullable = true)
    protected int quantidade_lote;

    @Column(name = "data_validade", nullable = true)
    protected Date data_validade;

    @Column(name = "numero_lote", length = 25, nullable = true)
    protected String numero_lote;


    // Getters e Setters
    
    public int getId_registro_estoque() {
        return id_registro_estoque;
    }

    public void setId_registro_estoque(int id_registro_estoque) {
        this.id_registro_estoque = id_registro_estoque;
    }

    // Getter e Setter para o objeto Produto
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public int getQuantidade_lote() {
        return quantidade_lote;
    }

    public void setQuantidade_lote(int quantidade_lote) {
        this.quantidade_lote = quantidade_lote;
    }

    public Date getData_validade() {
        return data_validade;
    }

    public void setData_validade(Date data_validade) {
        this.data_validade = data_validade;
    }

    public String getNumero_lote() {
        return numero_lote;
    }

    public void setNumero_lote(String numero_lote) {
        this.numero_lote = numero_lote;
    }
}