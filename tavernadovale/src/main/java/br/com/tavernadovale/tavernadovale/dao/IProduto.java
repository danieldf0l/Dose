package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.Produto;

public interface IProduto extends CrudRepository<Produto, Integer>{

}