package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.Produto;

// MUDANÇA: O tipo do ID deve ser String para mapear o codigo_barras
public interface IProduto extends CrudRepository<Produto, String>{

}