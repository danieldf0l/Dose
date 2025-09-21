package br.com.tavernadovale.tavernadovale.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto", nullable = true)
    protected int id_produto;
    
    @Column(name = "nome_produto", length = 45, nullable = true)
    protected String nome_produto;
    
    @Column(name = "valor_produto", nullable = true)
    protected Float valor_produto;
    
    @Column(name = "tipo_produto", length = 45, nullable = true)
    protected String tipo_produto;

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public Float getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(Float valor_produto) {
        this.valor_produto = valor_produto;
    }

    public String getTipo_produto() {
        return tipo_produto;
    }

    public void setTipo_produto(String tipo_produto) {
        this.tipo_produto = tipo_produto;
    }

}
