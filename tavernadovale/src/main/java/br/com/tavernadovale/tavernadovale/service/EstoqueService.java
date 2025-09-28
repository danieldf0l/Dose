package br.com.tavernadovale.tavernadovale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tavernadovale.tavernadovale.dao.IEstoque;
import br.com.tavernadovale.tavernadovale.model.Estoque;

@Service
public class EstoqueService {

    private final IEstoque repository;

    public EstoqueService(IEstoque repository) {
        this.repository = repository;
    }

    public List<Estoque> listarEstoque() {
        return (List<Estoque>) repository.findAll();
    }

    public ResponseEntity<Estoque> buscarPorId(int id) {
        return repository.findById(id)
                .map(estoque -> ResponseEntity.ok(estoque))
                .orElse(ResponseEntity.notFound().build());
    }

    public Estoque criarEstoque(Estoque estoque) {
        // O objeto 'estoque' recebido aqui deve ter o 'produto' (com codigo_barras) preenchido
        Estoque estoqueNovo = repository.save(estoque);
        return estoqueNovo;
    }

    public ResponseEntity<Estoque> editarEstoque(Integer idEstoque, @RequestBody Estoque estoqueAtualizado) {
        Optional<Estoque> estoqueExistente = repository.findById(idEstoque);

        if (estoqueExistente.isPresent()) {
            Estoque estoque = estoqueExistente.get();

            // CORREÇÃO: Removida a linha que tentava setar o ID do Estoque
            // estoque.setId_produto_estoque(estoqueAtualizado.getId_produto_estoque()); 
            
            // CORREÇÃO: Atualizar o relacionamento com Produto
            // setFk_id_produto() não existe mais, agora você usa setProduto()
            estoque.setProduto(estoqueAtualizado.getProduto()); 
            
            estoque.setQuantidade_lote(estoqueAtualizado.getQuantidade_lote());
            estoque.setData_validade(estoqueAtualizado.getData_validade());
            estoque.setNumero_lote(estoqueAtualizado.getNumero_lote());

            Estoque EstoqueSalvo = repository.save(estoque);
            return ResponseEntity.ok(EstoqueSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Estoque> exlcuirEstoque(Integer idEstoque) {
        Optional<Estoque> estoque = repository.findById(idEstoque);
        repository.deleteById(idEstoque);
        return estoque;
    }

}