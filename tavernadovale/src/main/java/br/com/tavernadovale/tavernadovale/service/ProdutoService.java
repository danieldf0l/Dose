package br.com.tavernadovale.tavernadovale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tavernadovale.tavernadovale.dao.IProduto;
import br.com.tavernadovale.tavernadovale.exceptions.TipoInvalidoException;
import br.com.tavernadovale.tavernadovale.model.Produto;

@Service
public class ProdutoService {

    private final IProduto repository;

    public ProdutoService(IProduto repository) {
        this.repository = repository;
    }

    public List<Produto> listarProduto() {
        return (List<Produto>) repository.findAll();
    }

    public ResponseEntity<Produto> buscarPorId(int id) {
        return repository.findById(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    public Produto criarProduto(Produto produto) {
        try {
            Produto produtoNovo = repository.save(produto);
            return produtoNovo;
        } catch (Exception e) {
            throw new TipoInvalidoException();
        }
    }

    public ResponseEntity<Produto> editarProduto(Integer idProduto, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = repository.findById(idProduto);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome_produto(produtoAtualizado.getNome_produto());
            produto.setTipo_produto(produtoAtualizado.getTipo_produto());
            produto.setValor_produto(produtoAtualizado.getValor_produto());
            Produto produtoSalvo = repository.save(produto);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Produto> excluirProduto(Integer idProduto) {
        Optional<Produto> produto = repository.findById(idProduto);
        repository.deleteById(idProduto);
        return produto;
    }

}
