package br.com.tavernadovale.tavernadovale.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id; // Não precisa mais do GeneratedValue/GenerationType
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    // NOVO: codigo_barras é a nova chave primária e é String
    @Id
    @Column(name = "codigo_barras", length = 50, nullable = false)
    protected String codigo_barras;

    @Column(name = "nome_produto", length = 45, nullable = true)
    protected String nome_produto;

    @Column(name = "valor_produto", nullable = true)
    protected Float valor_produto;

    // O tipo no banco é ENUM, mas no Java String é o mais comum para mapear
    @Column(name = "tipo_produto", length = 45, nullable = true)
    protected String tipo_produto;


    // Getters e Setters atualizados para codigo_barras
    
    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
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