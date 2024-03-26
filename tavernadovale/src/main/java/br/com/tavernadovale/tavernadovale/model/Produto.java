package br.com.tavernadovale.tavernadovale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
    
    
    int id_produtoInt;
    
    
    String nome_produtoString;
    
    
    Float valor_produtoFloat;
    
    
    String tipoString;


    public int getId_produtoInt() {
        return id_produtoInt;
    }


    public void setId_produtoInt(int id_produtoInt) {
        this.id_produtoInt = id_produtoInt;
    }


    public String getNome_produtoString() {
        return nome_produtoString;
    }


    public void setNome_produtoString(String nome_produtoString) {
        this.nome_produtoString = nome_produtoString;
    }


    public Float getValor_produtoFloat() {
        return valor_produtoFloat;
    }


    public void setValor_produtoFloat(Float valor_produtoFloat) {
        this.valor_produtoFloat = valor_produtoFloat;
    }


    public String getTipoString() {
        return tipoString;
    }


    public void setTipoString(String tipoString) {
        this.tipoString = tipoString;
    }

    
}
