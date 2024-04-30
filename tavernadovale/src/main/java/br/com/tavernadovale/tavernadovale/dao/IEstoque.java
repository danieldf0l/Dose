package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.Estoque;

public interface IEstoque extends CrudRepository<Estoque, Integer>{

}