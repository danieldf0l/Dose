package br.com.tavernadovale.tavernadovale.model;

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
    @Column(name = "id_registro")
    public int id_produto;
    
    
    public String nome;
    
    
    public String tipo;
    
    
    public int quantidade;
    
    
    public String data_validade;
    
    
    public String numero_lote;
    
    public int getId_produto(){
        return id_produto;
    } 
    public void setId_produto(int id_produto){
        this.id_produto = id_produto;
    }
    public String getNome(){
        return nome;
    } 
    public void setNome(String  nome){
        this.nome = nome;
    }
    public String getTipo(){
        return tipo;
    } 
    public void setTipo(String  tipo){
        this.tipo = tipo;
    }
    public int getQuantidade(){
        return quantidade;
    } 
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
    public String getData_validade(){
        return data_validade;
    } 
    public void setData_validade(String data_validade){
        this.data_validade = data_validade;
    }
    public String getNumero_lote(){
        return numero_lote;
    } 
    public void setNumero_lote(String numero_lote){
        this.numero_lote = numero_lote;
    }
}
