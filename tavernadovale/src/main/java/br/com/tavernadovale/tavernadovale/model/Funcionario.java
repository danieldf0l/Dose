package br.com.tavernadovale.tavernadovale.model;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario", nullable = true)
    protected int id_funcionario;
    
    @Column(name = "cargo_funcionario", length = 25,nullable = true)
    protected String cargo_funcionario;
    
    @Column(name = "horario_entrada", nullable = true)
    protected Time horario_entrada;
    
    @Column(name = "horario_saida", nullable = true)
    protected Time horario_saida;
    
    @Column(name = "nome_funcionario", length = 100, nullable = true)
    protected String nome_funcionario;

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id) {
        this.id_funcionario = id;
    }

    public String getCargo_funcionario() {
        return cargo_funcionario;
    }

    public void setCargo_funcionario(String cargo) {
        this.cargo_funcionario = cargo;
    }

    public Time getHorario_saida() {
        return horario_saida;
    }

    public void setHorario_saida(Time horario_saida) {
        this.horario_saida = horario_saida;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome) {
        this.nome_funcionario = nome;
    }

    public void setHorario_entrada(Time horario_entrada) {
        this.horario_entrada = horario_entrada;
    }

    public Time getHorario_entrada() {
        return horario_entrada;
    }

}
