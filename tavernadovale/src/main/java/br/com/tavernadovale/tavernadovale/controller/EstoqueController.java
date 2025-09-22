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

@GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable int id) {
        return dao.findById(id)
                .map(estoque -> ResponseEntity.ok(estoque))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estoque criarEstoque(@RequestBody Estoque estoque){
        Estoque estoqueNovo = dao.save(estoque);
        return estoqueNovo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> editarEstoque(@PathVariable("id") Integer idEstoque, @RequestBody Estoque estoqueAtualizado) {
        Optional<Estoque> estoqueExistente = dao.findById(idEstoque);

        if (estoqueExistente.isPresent()) {
            Estoque estoque = estoqueExistente.get();
            
            estoque.setId_produto_estoque(estoqueAtualizado.getId_produto_estoque());
            estoque.setFk_id_produto(estoqueAtualizado.getFk_id_produto());
            estoque.setQuantidade_lote(estoqueAtualizado.getQuantidade_lote());
            estoque.setData_validade(estoqueAtualizado.getData_validade());
            estoque.setNumero_lote(estoqueAtualizado.getNumero_lote());

            Estoque EstoqueSalvo = dao.save(estoque);
            return ResponseEntity.ok(EstoqueSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public Optional<Estoque> exlcuirEstoque(@PathVariable("id") Integer idEstoque){
        Optional<Estoque> estoque = dao.findById(idEstoque);
        dao.deleteById(idEstoque);
        return estoque;
    }
}
