package br.com.tavernadovale.tavernadovale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tavernadovale.tavernadovale.dao.IVenda;
import br.com.tavernadovale.tavernadovale.model.Venda;

@RestController
@CrossOrigin("*")
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private IVenda dao;

    @GetMapping()
    public List<Venda> listarVenda(){
        return (List<Venda>) dao.findAll();
    }

    @PostMapping
    public Venda criarVenda(@RequestBody Venda venda){
        Venda vendaNovo = dao.save(venda);
        return vendaNovo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> editarVenda(@PathVariable("id") Integer idVenda, @RequestBody Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = dao.findById(idVenda);

        if (vendaExistente.isPresent()) {
            Venda venda = vendaExistente.get();
            
            venda.setData_venda(vendaAtualizada.getData_hora_venda());
            venda.setForma_pagamento_venda(vendaAtualizada.getForma_pagamento_venda());
            venda.setValor_final_venda(vendaAtualizada.getValor_final_venda());
            
            Venda produtoSalvo = dao.save(venda);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public Optional<Venda> exlcuirVenda(@PathVariable("id") Integer idVenda){
        Optional<Venda> venda = dao.findById(idVenda);
        dao.deleteById(idVenda);
        return venda;
    }
}