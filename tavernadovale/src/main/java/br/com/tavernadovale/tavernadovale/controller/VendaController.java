package br.com.tavernadovale.tavernadovale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.tavernadovale.tavernadovale.model.Venda;
import br.com.tavernadovale.tavernadovale.service.VendaService;

@RestController
@CrossOrigin("*")
@RequestMapping("/vendas")
public class VendaController {  

    @Autowired
    private VendaService service;

    @GetMapping()
    public List<Venda> listarVenda() {
        return service.listarVenda();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }
    
    // Método POST atualizado para tratar a transação e capturar erros do Service
    @PostMapping
    public ResponseEntity<?> criarVenda(@RequestBody Venda venda) {
        try {
            // O VendaService gerencia salvar a venda e dar baixa no estoque
            Venda novaVenda = service.criarVenda(venda);
            // Retorna 201 Created se for sucesso
            return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
        } catch (RuntimeException e) {
            // Captura a exceção de falta de estoque ou outro erro de negócio
            // Retorna 400 Bad Request com a mensagem de erro para o frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Erro genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a venda: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> editarVenda(@PathVariable("id") Integer idVenda, @RequestBody Venda vendaAtualizada) {
        return service.editarVenda(idVenda, vendaAtualizada);
    }

    @DeleteMapping("/{id}")
    public Optional<Venda> exlcuirVenda(@PathVariable("id") Integer idVenda) {
        return service.exlcuirVenda(idVenda);
    }
}