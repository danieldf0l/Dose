package br.com.tavernadovale.tavernadovale.dao;

import br.com.tavernadovale.tavernadovale.model.Produto;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface IProduto extends CrudRepository<Produto, Integer> {
    // Busca todos os produtos que tenham o nome exatamente igual
    List<Produto> findByNome_produto(String nome);

    // Se quiser buscar por nome contendo (ignora maiúsc/minúsc)
    List<Produto> findByNome_produtoContainingIgnoreCase(String nome);
}
