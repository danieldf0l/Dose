package br.com.tavernadovale.tavernadovale.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class FuncionarioController {

    @GetMapping("/funcionario")
    public String texto(){
        return "Hello World";
    }
}
