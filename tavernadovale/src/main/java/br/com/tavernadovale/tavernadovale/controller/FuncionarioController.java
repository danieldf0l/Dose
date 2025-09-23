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

import br.com.tavernadovale.tavernadovale.model.Funcionario;
import br.com.tavernadovale.tavernadovale.service.FuncionarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping()
    public List<Funcionario> listarFuncionario() {
        return service.listarFuncionario();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        return service.criarFuncionario(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable("id") Integer idfuncionario, @RequestBody Funcionario funcionarioAtualizado) {
        return service.editarFuncionario(idfuncionario, funcionarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public Optional<Funcionario> exlcuirFuncionario(@PathVariable("id") Integer idFuncionario) {
        return service.exlcuirFuncionario(idFuncionario);
    }
}
