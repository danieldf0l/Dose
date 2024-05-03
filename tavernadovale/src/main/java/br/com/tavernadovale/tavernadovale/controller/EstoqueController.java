package br.com.tavernadovale.tavernadovale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tavernadovale.tavernadovale.dao.IEstoque;
import br.com.tavernadovale.tavernadovale.model.Estoque;

@RestController
@CrossOrigin("*")
@RequestMapping("/estoque")
public class EstoqueController {
    
    @Autowired
    private IEstoque dao;

    @GetMapping()
    public List<Estoque> listarEstoque(){
        return (List<Estoque>) dao.findAll();
    }

    @PostMapping
    public Estoque criarEstoque(@RequestBody Estoque estoque){
        Estoque estoqueNovo = dao.save(estoque);
        return estoqueNovo;
    }

    @PutMapping
    public Estoque editarEstoque(@RequestBody Estoque estoque){
        Estoque novoEstoque = dao.save(estoque);
        return novoEstoque;
    }

    @DeleteMapping
    public Optional<Estoque> exlcuirEstoque(@PathVariable Integer idEstoque){
        Optional<Estoque> estoque = dao.findById(idEstoque);
        dao.deleteById(idEstoque);
        return estoque;
    }
}
