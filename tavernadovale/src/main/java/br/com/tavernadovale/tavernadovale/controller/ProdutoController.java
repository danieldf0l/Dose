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

    // ALTERADO: Recebe String (codigo_barras) em vez de int/Integer (id)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable String id) {
        // O Service também precisará ser atualizado para receber String
        return service.buscarPorId(id); 
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        // O produto recebido no corpo do JSON agora deve ter o campo "codigo_barras"
        return service.criarProduto(produto);
    }

    // ALTERADO: Recebe String (codigo_barras) em vez de Integer (idProduto)
    @PutMapping({"/{id}"})
    public ResponseEntity<Produto> editarProduto(@PathVariable("id") String codigoBarras, @RequestBody Produto produtoAtualizado) {
        // O Service também precisará ser atualizado
        return service.editarProduto(codigoBarras, produtoAtualizado);
    }

    // ALTERADO: Recebe String (codigo_barras) em vez de Integer (idProduto)
    @DeleteMapping({"/{id}"})
    public Optional<Produto> excluirProduto(@PathVariable("id") String codigoBarras) {
        // O Service também precisará ser atualizado
        return service.excluirProduto(codigoBarras);
    }
}