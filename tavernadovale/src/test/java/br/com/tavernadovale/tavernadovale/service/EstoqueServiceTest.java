package br.com.tavernadovale.tavernadovale.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.dao.IEstoque;
import br.com.tavernadovale.tavernadovale.model.Estoque;
import br.com.tavernadovale.tavernadovale.model.Produto;

class EstoqueServiceTest {

    @Mock
    private IEstoque repository;
    
    private EstoqueService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EstoqueService(repository);
    }

    @Test
    void listarEstoque_DeveRetornarListaDeEstoque() {
        Estoque estoque = new Estoque();
        List<Estoque> expected = Arrays.asList(estoque);
        
        when(repository.findAll()).thenReturn(expected);
        
        List<Estoque> result = service.listarEstoque();
        
        assertEquals(expected, result);
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_QuandoEncontrado_DeveRetornarEstoque() {
        Estoque estoque = new Estoque();
        int id = 1;
        
        when(repository.findById(id)).thenReturn(Optional.of(estoque));
        
        ResponseEntity<Estoque> response = service.buscarPorId(id);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(estoque, response.getBody());
    }

    @Test
    void buscarPorId_QuandoNaoEncontrado_DeveRetornarNotFound() {
        int id = 1;
        
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        ResponseEntity<Estoque> response = service.buscarPorId(id);
        
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void criarEstoque_DeveSalvarERetornarNovoEstoque() {
        Estoque estoque = new Estoque();
        Produto produto = new Produto();
        estoque.setProduto(produto);
        
        when(repository.save(estoque)).thenReturn(estoque);
        
        Estoque result = service.criarEstoque(estoque);
        
        assertEquals(estoque, result);
        verify(repository).save(estoque);
    }

    @Test
    void editarEstoque_QuandoEncontrado_DeveAtualizarERetornarEstoque() {
        int id = 1;
        Estoque existente = new Estoque();
        Estoque atualizado = new Estoque();
        atualizado.setProduto(new Produto());
        
        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        
        ResponseEntity<Estoque> response = service.editarEstoque(id, atualizado);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(repository).save(existente);
    }

    @Test
    void editarEstoque_QuandoNaoEncontrado_DeveRetornarNotFound() {
        int id = 1;
        Estoque atualizado = new Estoque();
        
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        ResponseEntity<Estoque> response = service.editarEstoque(id, atualizado);
        
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void excluirEstoque_DeveExcluirERetornarEstoqueExcluido() {
        int id = 1;
        Estoque estoque = new Estoque();
        
        when(repository.findById(id)).thenReturn(Optional.of(estoque));
        
        Optional<Estoque> result = service.exlcuirEstoque(id);
        
        assertTrue(result.isPresent());
        assertEquals(estoque, result.get());
        verify(repository).deleteById(id);
    }
}