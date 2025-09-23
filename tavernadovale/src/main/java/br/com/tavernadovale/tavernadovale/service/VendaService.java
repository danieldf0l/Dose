package br.com.tavernadovale.tavernadovale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tavernadovale.tavernadovale.dao.IVenda;
import br.com.tavernadovale.tavernadovale.model.Venda;

@Service
public class VendaService {

    private final IVenda repository;

    public VendaService(IVenda repository) {
        this.repository = repository;
    }

    public List<Venda> listarVenda() {
        return (List<Venda>) repository.findAll();
    }

    public ResponseEntity<Venda> buscarPorId(int id) {
        return repository.findById(id)
                .map(venda -> ResponseEntity.ok(venda))
                .orElse(ResponseEntity.notFound().build());
    }

    public Venda criarVenda(Venda venda) {
        Venda vendaNovo = repository.save(venda);
        return vendaNovo;
    }

    public ResponseEntity<Venda> editarVenda(Integer idVenda, Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = repository.findById(idVenda);

        if (vendaExistente.isPresent()) {
            Venda venda = vendaExistente.get();

            venda.setData_venda(vendaAtualizada.getData_hora_venda());
            venda.setForma_pagamento_venda(vendaAtualizada.getForma_pagamento_venda());
            venda.setValor_final_venda(vendaAtualizada.getValor_final_venda());

            Venda produtoSalvo = repository.save(venda);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Venda> exlcuirVenda(Integer idVenda) {
        Optional<Venda> venda = repository.findById(idVenda);
        repository.deleteById(idVenda);
        return venda;
    }
}
