package br.com.tavernadovale.tavernadovale.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_estoque")
    public int id_produto_estoque;
 
    @Column(name = "fk_id_produto", nullable = true)
    public int fk_id_produto;

    @Column(name = "quantidade_lote", nullable = true)
    public int quantidade_lote;
    
    @Column(name = "data_validade", nullable = true)
    public Date data_validade;
    
    @Column(name = "numero_lote", length = 25, nullable = true)
    public String numero_lote;

    public int getId_produto_estoque() {
        return id_produto_estoque;
    }

    public void setId_produto_estoque(int id_produto_estoque) {
        this.id_produto_estoque = id_produto_estoque;
    }

    public int getFk_id_produto() {
        return fk_id_produto;
    }

    public void setFk_id_produto(int fk_id_produto) {
        this.fk_id_produto = fk_id_produto;
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
