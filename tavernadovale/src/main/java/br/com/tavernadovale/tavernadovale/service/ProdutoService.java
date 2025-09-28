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

    // ALTERADO: ID agora é String (codigo_barras)
    public ResponseEntity<Produto> buscarPorId(String codigoBarras) {
        return repository.findById(codigoBarras)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    public Produto criarProduto(Produto produto) {
        try {
            // O objeto 'produto' recebido deve vir com o 'codigo_barras' preenchido
            Produto produtoNovo = repository.save(produto);
            return produtoNovo;
        } catch (Exception e) {
            // Aqui, o TipoInvalidoException pode ser substituído por uma exceção mais específica
            // se for o caso de violar a chave primária (código de barras repetido)
            throw new TipoInvalidoException(); 
        }
    }

    // ALTERADO: ID agora é String (codigo_barras)
    public ResponseEntity<Produto> editarProduto(String codigoBarras, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = repository.findById(codigoBarras); // Usa String aqui
        
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            
            // O codigo_barras não é alterado, apenas os outros campos
            produto.setNome_produto(produtoAtualizado.getNome_produto());
            produto.setTipo_produto(produtoAtualizado.getTipo_produto());
            produto.setValor_produto(produtoAtualizado.getValor_produto());
            
            Produto produtoSalvo = repository.save(produto);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ALTERADO: ID agora é String (codigo_barras)
    public Optional<Produto> excluirProduto(String codigoBarras) {
        Optional<Produto> produto = repository.findById(codigoBarras);
        // Exclui usando o codigo_barras (String)
        repository.deleteById(codigoBarras); 
        return produto;
    }
}