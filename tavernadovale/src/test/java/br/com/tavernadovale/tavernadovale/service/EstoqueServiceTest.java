package br.com.tavernadovale.tavernadovale.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.dao.IEstoque;
import br.com.tavernadovale.tavernadovale.model.Estoque;
import br.com.tavernadovale.tavernadovale.model.Produto;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private IEstoque repository;

    @Mock
    private Produto mockProduto;

    @InjectMocks
    private EstoqueService service;

    @Test
    void listarEstoque_DeveRetornarListaDeEstoque() {

        Estoque e1 = new Estoque();
        Estoque e2 = new Estoque();
        List<Estoque> listaMock = Arrays.asList(e1, e2);
        when(repository.findAll()).thenReturn(listaMock);

        List<Estoque> resultado = service.listarEstoque();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertSame(listaMock, resultado);
        verify(repository).findAll();
    }

    @Test
    void listarEstoque_DeveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Estoque> resultado = service.listarEstoque();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_QuandoEstoqueExiste_DeveRetornarOkComEstoque() {
        Integer id = 1;
        Estoque estoqueMock = new Estoque();

        estoqueMock.setId_registro_estoque(id);

        when(repository.findById(id)).thenReturn(Optional.of(estoqueMock));

        ResponseEntity<Estoque> response = service.buscarPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(estoqueMock, response.getBody());
        verify(repository).findById(id);
    }

    @Test
    void buscarPorId_QuandoEstoqueNaoExiste_DeveRetornarNotFound() {
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Estoque> response = service.buscarPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).findById(id);
    }

    @Test
    void criarEstoque_DeveSalvarERetornarEstoqueComId() {
        Estoque estoqueParaSalvar = new Estoque();
        estoqueParaSalvar.setProduto(mockProduto);
        estoqueParaSalvar.setNumero_lote("LOTE-001");
        estoqueParaSalvar.setQuantidade_lote(100);

        when(repository.save(any(Estoque.class))).thenAnswer(invocation -> {
            Estoque estoqueSalvo = invocation.getArgument(0);
            estoqueSalvo.setId_registro_estoque(5);
            return estoqueSalvo;
        });

        Estoque resultado = service.criarEstoque(estoqueParaSalvar);

        assertNotNull(resultado);

        assertEquals(5, resultado.getId_registro_estoque());
        assertEquals(100, resultado.getQuantidade_lote());
        assertEquals("LOTE-001", resultado.getNumero_lote());
        assertSame(mockProduto, resultado.getProduto());

        verify(repository).save(estoqueParaSalvar);
    }

    @Test
    void editarEstoque_QuandoExiste_DeveAtualizarEstoque() {
        Integer id = 1;
        Date novaData = Date.valueOf("2025-12-31");

        Estoque estoqueExistente = new Estoque();
        estoqueExistente.setId_registro_estoque(id);
        estoqueExistente.setNumero_lote("LOTE-ANTIGO");

        Estoque estoqueAtualizado = new Estoque();
        estoqueAtualizado.setNumero_lote("LOTE-NOVO");
        estoqueAtualizado.setQuantidade_lote(50);
        estoqueAtualizado.setData_validade(novaData);
        estoqueAtualizado.setProduto(mockProduto);

        when(repository.findById(id)).thenReturn(Optional.of(estoqueExistente));

        ArgumentCaptor<Estoque> captor = ArgumentCaptor.forClass(Estoque.class);
        when(repository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<Estoque> response = service.editarEstoque(id, estoqueAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Estoque estoqueSalvo = response.getBody();

        assertEquals(id, estoqueSalvo.getId_registro_estoque());

        assertEquals("LOTE-NOVO", estoqueSalvo.getNumero_lote());
        assertEquals(50, estoqueSalvo.getQuantidade_lote());
        assertEquals(novaData, estoqueSalvo.getData_validade());
        assertSame(mockProduto, estoqueSalvo.getProduto());

        verify(repository).findById(id);
        verify(repository).save(any(Estoque.class));
    }

    @Test
    void editarEstoque_QuandoNaoExiste_DeveRetornarNotFound() {
        Integer id = 99;
        Estoque estoqueAtualizado = new Estoque();
        estoqueAtualizado.setNumero_lote("LOTE-FANTASMA");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Estoque> response = service.editarEstoque(id, estoqueAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).findById(id);
        verify(repository, never()).save(any(Estoque.class));
    }

    @Test
    void excluirEstoque_QuandoExiste_DeveRetornarOptionalComEstoque() {
        Integer id = 1;
        Estoque estoqueMock = new Estoque();

        estoqueMock.setId_registro_estoque(id);
        when(repository.findById(id)).thenReturn(Optional.of(estoqueMock));
        doNothing().when(repository).deleteById(id);

        Optional<Estoque> resultado = service.exlcuirEstoque(id);

        assertTrue(resultado.isPresent());
        assertSame(estoqueMock, resultado.get());
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void excluirEstoque_QuandoNaoExiste_DeveRetornarOptionalVazio() {
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(id);

        Optional<Estoque> resultado = service.exlcuirEstoque(id);

        assertFalse(resultado.isPresent());
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }
}
