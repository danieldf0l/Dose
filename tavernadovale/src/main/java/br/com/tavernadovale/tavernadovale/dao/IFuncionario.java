package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.Funcionario;

public interface IFuncionario extends CrudRepository<Funcionario, Integer>{

}
