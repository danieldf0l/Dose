package br.com.tavernadovale.tavernadovale.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProdutosVendaTest {

    private ProdutosVenda produtosVenda;

    @Mock
    private Venda mockVenda;

    @Mock
    private Produto mockProduto;

    @BeforeEach
    void setUp() {
        produtosVenda = new ProdutosVenda();
    }

    @Test
    void testConstrutor() {
        assertNotNull(produtosVenda);
    }

    @Test
    void testGetAndSetIdProdutoVenda() {
        int testId = 123;

        produtosVenda.setId_produto_venda(testId);

        assertEquals(testId, produtosVenda.getId_produto_venda());
    }

    @Test
    void testGetAndSetVenda() {
        produtosVenda.setVenda(mockVenda);

        assertSame(mockVenda, produtosVenda.getVenda());
    }

    @Test
    void testGetAndSetProduto() {

        produtosVenda.setProduto(mockProduto);

        assertSame(mockProduto, produtosVenda.getProduto());
    }

    @Test
    void testGetAndSetQuantidadeVenda() {
        int testQuantidade = 10;

        produtosVenda.setQuantidade_venda(testQuantidade);

        assertEquals(testQuantidade, produtosVenda.getQuantidade_venda());
    }
}
