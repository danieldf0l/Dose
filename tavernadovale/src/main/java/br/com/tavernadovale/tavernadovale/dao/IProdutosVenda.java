package br.com.tavernadovale.tavernadovale.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.tavernadovale.tavernadovale.model.ProdutosVenda;

// ID da ProdutosVenda é id_produto_venda, que é um Integer
public interface IProdutosVenda extends CrudRepository<ProdutosVenda, Integer>{

}