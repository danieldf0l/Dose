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
        Estoque estoqueNovo = repository.save(estoque);
        return estoqueNovo;
    }

    public ResponseEntity<Estoque> editarEstoque(Integer idEstoque, @RequestBody Estoque estoqueAtualizado) {
        Optional<Estoque> estoqueExistente = repository.findById(idEstoque);

        if (estoqueExistente.isPresent()) {
            Estoque estoque = estoqueExistente.get();

            estoque.setId_produto_estoque(estoqueAtualizado.getId_produto_estoque());
            estoque.setFk_id_produto(estoqueAtualizado.getFk_id_produto());
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
