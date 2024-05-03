package br.com.tavernadovale.tavernadovale.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda", nullable = true)
    public int id_venda;
    
    @Column(name = "valor_parcial", nullable = true)
    public double valor_parcial_venda;
    
    @Column(name = "valor_final", nullable = true)
    public double valor_final_venda;
    
    @Column(name = "forma_pagamento", length = 45, nullable = true)
    public String forma_pagamento_venda;
    
    @Column(name = "data_venda", nullable = true)
    public Timestamp data_hora_venda;

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

    public void setData_venda(Timestamp data_hora_venda) {
        this.data_hora_venda = data_hora_venda;
    }
}
