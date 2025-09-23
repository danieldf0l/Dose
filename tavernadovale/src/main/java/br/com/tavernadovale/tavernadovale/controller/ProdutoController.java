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

import br.com.tavernadovale.tavernadovale.model.Produto;
import br.com.tavernadovale.tavernadovale.service.ProdutoService;

@RestController
@CrossOrigin({"*"})
@RequestMapping({"/produto"})
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    public ProdutoController() {
    }

    @GetMapping
    public List<Produto> listarProduto() {
        return service.listarProduto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return service.criarProduto(produto);

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Produto> editarProduto(@PathVariable("id") Integer idProduto, @RequestBody Produto produtoAtualizado) {
        return service.editarProduto(idProduto, produtoAtualizado);
    }

    @DeleteMapping({"/{id}"})
    public Optional<Produto> excluirProduto(@PathVariable("id") Integer idProduto) {
        return service.excluirProduto(idProduto);
    }
}
