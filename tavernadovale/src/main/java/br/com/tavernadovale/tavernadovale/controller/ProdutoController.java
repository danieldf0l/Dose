package br.com.tavernadovale.tavernadovale.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/produto")
public class ProdutoController {

    @GetMapping()
    public List<Produto> listarProduto(){
        return (List<Produto>) dao.findAll();
    }
    
    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto){
        Estoque produtoNovo = dao.save(produto);
        return produtoNovo;
    }

    @PutMapping
    public Produto editarProduto(@RequestBody Produto produto){
        Produto novoProduto = dao.save(produto);
        return novoProduto;
    }

    @DeleteMapping
    public Optional<Produto> exlcuirProduto(@PathVariable Integer idProduto){
        Optional<Produto> produto = dao.findById(idProduto);
        dao.deleteById(idProduto);
        return produto;
    }
}
