package br.com.tavernadovale.tavernadovale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    
    public int id;
    
    
    public String cargo;
    
    
    public String horario_entrada;
    
    
    public String horario_saida;
    
    
    public String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getHorario_entrada() {
        return horario_entrada;
    }

    public void setHorario_entrada(String horario_entrada) {
        this.horario_entrada = horario_entrada;
    }

    public String getHorario_saida() {
        return horario_saida;
    }

    public void setHorario_saida(String horario_saida) {
        this.horario_saida = horario_saida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
