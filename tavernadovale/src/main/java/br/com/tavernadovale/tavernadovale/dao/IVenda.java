package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.Venda;

public interface IVenda extends CrudRepository<Venda, Integer>{

}