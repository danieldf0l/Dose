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

import br.com.tavernadovale.tavernadovale.model.Venda;
import br.com.tavernadovale.tavernadovale.service.VendaService;

@RestController
@CrossOrigin("*")
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService service;

    @GetMapping()
    public List<Venda> listarVenda() {
        return service.listarVenda();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }
    
    @PostMapping
    public Venda criarVenda(@RequestBody Venda venda) {
        return service.criarVenda(venda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> editarVenda(@PathVariable("id") Integer idVenda, @RequestBody Venda vendaAtualizada) {
        return service.editarVenda(idVenda, vendaAtualizada);
    }

    @DeleteMapping("/{id}")
    public Optional<Venda> exlcuirVenda(@PathVariable("id") Integer idVenda) {
        return service.exlcuirVenda(idVenda);
    }
}
