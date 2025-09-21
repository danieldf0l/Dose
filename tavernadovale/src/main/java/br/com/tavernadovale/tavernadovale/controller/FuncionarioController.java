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

import br.com.tavernadovale.tavernadovale.dao.IFuncionario;
import br.com.tavernadovale.tavernadovale.model.Funcionario;

@RestController
@CrossOrigin("*")
@RequestMapping("/funcionario")
public class FuncionarioController{

    @Autowired
    private IFuncionario dao;

    @GetMapping()
    public List<Funcionario> listarFuncionario(){
        return (List<Funcionario>) dao.findAll();
    }

    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario){
        Funcionario funcionarioNovo = dao.save(funcionario);
        return funcionarioNovo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable("id") Integer idfuncionario, @RequestBody Funcionario funcionarioAtualizado) {
        Optional<Funcionario> funcionarioExistente = dao.findById(idfuncionario);

        if (funcionarioExistente.isPresent()) {
            Funcionario funcionario = funcionarioExistente.get();
            
            funcionario.setNome_funcionario(funcionarioAtualizado.getNome_funcionario());
            funcionario.setCargo_funcionario(funcionarioAtualizado.getCargo_funcionario());
            funcionario.setHorario_entrada(funcionarioAtualizado.getHorario_entrada());
            funcionario.setHorario_saida(funcionarioAtualizado.getHorario_saida());
            
            Funcionario FuncionarioSalvo = dao.save(funcionario);
            return ResponseEntity.ok(FuncionarioSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public Optional<Funcionario> exlcuirFuncionario(@PathVariable("id") Integer idFuncionario){
        Optional<Funcionario> funcionario = dao.findById(idFuncionario);
        dao.deleteById(idFuncionario);
        return funcionario;
    }
}