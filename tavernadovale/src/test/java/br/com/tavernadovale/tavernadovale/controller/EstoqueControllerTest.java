package br.com.tavernadovale.tavernadovale.controller;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.model.Estoque;
import br.com.tavernadovale.tavernadovale.model.Produto;
import br.com.tavernadovale.tavernadovale.service.EstoqueService;

@ExtendWith(MockitoExtension.class)
class EstoqueControllerTest {

    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private EstoqueController estoqueController;

    private Estoque estoque1;
    private Estoque estoque2;
    private Produto produtoMock;

    @BeforeEach
    void setUp() {
        produtoMock = new Produto();

        estoque1 = new Estoque();
        estoque1.setId_registro_estoque(1);
        estoque1.setProduto(produtoMock);
        estoque1.setQuantidade_lote(50);
        estoque1.setNumero_lote("L001");
        estoque1.setData_validade(Date.valueOf("2025-12-31"));

        estoque2 = new Estoque();
        estoque2.setId_registro_estoque(2);
        estoque2.setProduto(produtoMock);
        estoque2.setQuantidade_lote(30);
        estoque2.setNumero_lote("L002");
        estoque2.setData_validade(Date.valueOf("2026-06-15"));
    }

    @Test
    void deveListarEstoque() {
        List<Estoque> lista = Arrays.asList(estoque1, estoque2);
        when(estoqueService.listarEstoque()).thenReturn(lista);

        List<Estoque> resultado = estoqueController.listarEstoque();

        assertEquals(2, resultado.size());
        assertEquals("L001", resultado.get(0).getNumero_lote());
        verify(estoqueService, times(1)).listarEstoque();
    }

    @Test
    void deveBuscarPorId() {
        when(estoqueService.buscarPorId(1)).thenReturn(ResponseEntity.of(Optional.of(estoque1)));

        ResponseEntity<Estoque> resposta = estoqueController.buscarPorId(1);

        assertTrue(resposta.getStatusCode().is2xxSuccessful());
        assertEquals(estoque1, resposta.getBody());
        assertEquals("L001", resposta.getBody().getNumero_lote());
        verify(estoqueService, times(1)).buscarPorId(1);
    }

    @Test
    void deveCriarEstoque() {
        when(estoqueService.criarEstoque(estoque1)).thenReturn(estoque1);

        Estoque criado = estoqueController.criarEstoque(estoque1);

        assertNotNull(criado);
        assertEquals("L001", criado.getNumero_lote());
        assertEquals(50, criado.getQuantidade_lote());
        verify(estoqueService, times(1)).criarEstoque(estoque1);
    }

    @Test
    void deveEditarEstoque() {
        Estoque atualizado = new Estoque();
        atualizado.setId_registro_estoque(1);
        atualizado.setProduto(produtoMock);
        atualizado.setQuantidade_lote(60);
        atualizado.setNumero_lote("L001A");
        atualizado.setData_validade(Date.valueOf("2026-01-01"));

        when(estoqueService.editarEstoque(1, atualizado))
                .thenReturn(ResponseEntity.of(Optional.of(atualizado)));

        ResponseEntity<Estoque> resposta = estoqueController.editarEstoque(1, atualizado);

        assertTrue(resposta.getStatusCode().is2xxSuccessful());
        assertEquals("L001A", resposta.getBody().getNumero_lote());
        assertEquals(60, resposta.getBody().getQuantidade_lote());
        verify(estoqueService, times(1)).editarEstoque(1, atualizado);
    }

    @Test
    void deveExcluirEstoque() {
        when(estoqueService.exlcuirEstoque(1)).thenReturn(Optional.of(estoque1));

        Optional<Estoque> excluido = estoqueController.exlcuirEstoque(1);

        assertTrue(excluido.isPresent());
        assertEquals("L001", excluido.get().getNumero_lote());
        verify(estoqueService, times(1)).exlcuirEstoque(1);
    }
}
