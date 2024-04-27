package br.com.tavernadovale.tavernadovale.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tavernadovale.tavernadovale.dao.IFuncionario;
import br.com.tavernadovale.tavernadovale.model.Funcionario;

@RestController
@CrossOrigin("*")
public class FuncionarioController{

    @Autowired
    private IFuncionario dao;

    @GetMapping("/funcionario")
    public List<Funcionario> listaFuncionario(){
        return (List<Funcionario>) dao.findAll();
    }
}
