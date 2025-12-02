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

import br.com.tavernadovale.tavernadovale.model.Estoque;
import br.com.tavernadovale.tavernadovale.service.EstoqueService;

@RestController
@CrossOrigin("*")
@RequestMapping("/estoque") // Alterado para /api/estoque para seguir convenção REST e JS
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @GetMapping()
    public List<Estoque> listarEstoque() {
        return service.listarEstoque();
    }

    /**
     * NOVO ENDPOINT: Usado pelo frontend de Vendas para buscar lotes disponíveis de um produto.
     * Corresponde ao fetch no JavaScript: /api/estoque/produto/{codigoBarras}
     * @param codigoBarras O código de barras do produto.
     * @return Lista de lotes disponíveis (> 0).
     */
    @GetMapping("/produto/{codigoBarras}")
    public ResponseEntity<List<Estoque>> buscarLotesPorProduto(@PathVariable String codigoBarras) {
        List<Estoque> lotes = service.buscarLotesDisponiveisPorProduto(codigoBarras);
        if (lotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Estoque criarEstoque(@RequestBody Estoque estoque) {
        return service.criarEstoque(estoque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> editarEstoque(@PathVariable("id") Integer idEstoque, @RequestBody Estoque estoqueAtualizado) {
        return service.editarEstoque(idEstoque,estoqueAtualizado);
    }

    @DeleteMapping("/{id}")
    public Optional<Estoque> exlcuirEstoque(@PathVariable("id") Integer idEstoque) {
        return service.exlcuirEstoque(idEstoque);
    }
}