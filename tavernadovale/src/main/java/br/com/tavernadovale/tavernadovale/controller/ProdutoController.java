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

import br.com.tavernadovale.tavernadovale.dao.IProduto;
import br.com.tavernadovale.tavernadovale.model.Produto;

@RestController
@CrossOrigin("*")
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private IProduto dao;

    @GetMapping()
    public List<Produto> listarProduto(){
        return (List<Produto>) dao.findAll();
    }
    
    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto){
        Produto produtoNovo = dao.save(produto);
        return produtoNovo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable("id") Integer idProduto, @RequestBody Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = dao.findById(idProduto);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            
            produto.setNome_produto(produtoAtualizado.getNome_produto());
            produto.setTipo_produto(produtoAtualizado.getTipo_produto());
            produto.setValor_produto(produtoAtualizado.getValor_produto());
            
            Produto produtoSalvo = dao.save(produto);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public Optional<Produto> exlcuirProduto(@PathVariable("id") Integer idProduto){
        Optional<Produto> produto = dao.findById(idProduto);
        dao.deleteById(idProduto);
        return produto;
    }
}
