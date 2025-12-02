package br.com.tavernadovale.tavernadovale.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.tavernadovale.tavernadovale.model.Estoque;

public interface IEstoque extends CrudRepository<Estoque, Integer> {

    /**
     * NOVO MÉTODO: Query Method para buscar todos os registros de estoque (lotes)
     * de um produto específico que ainda possuem quantidade disponível (> 0).
     * @param codigoBarras Código de barras do produto.
     * @param quantidade Zero, para buscar quantidades maiores que zero.
     * @return Lista de objetos Estoque (lotes disponíveis).
     */
    @Query("SELECT e FROM Estoque e WHERE e.produto.codigo_barras = :codigoBarras AND e.quantidade_lote > :quantidade")
    List<Estoque> buscarLotesDisponiveisPorProduto(
            @Param("codigoBarras") String codigoBarras,
            @Param("quantidade") int quantidade);

    /**
     * Decrementa a quantidade do lote específico vendido no estoque.
     * Esta operação é executada dentro da transação em VendaService.
     * @param idLote O ID do registro de estoque (id_registro_estoque) a ser baixado.
     * @param quantidadeVendida A quantidade a ser subtraída.
     * @return O número de linhas afetadas (deve ser 1 se o lote existir).
     */
    @Modifying
    @Query("UPDATE Estoque e SET e.quantidade_lote = e.quantidade_lote - :quantidadeVendida WHERE e.id_registro_estoque = :idLote")
    int decrementarEstoque(
            @Param("idLote") int idLote,
            @Param("quantidadeVendida") int quantidadeVendida);

}