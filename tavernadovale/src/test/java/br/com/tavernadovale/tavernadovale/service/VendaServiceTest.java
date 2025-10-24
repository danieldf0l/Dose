package br.com.tavernadovale.tavernadovale.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.dao.IVenda;
import br.com.tavernadovale.tavernadovale.model.Venda;

class VendaServiceTest {

    @Mock
    private IVenda repository;
    
    private VendaService vendaService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendaService = new VendaService(repository);
    }
    
    @Test
    void listarVenda_DeveRetornarListaDeVendas() {
        Venda venda = new Venda();
        List<Venda> vendas = Arrays.asList(venda);
        when(repository.findAll()).thenReturn(vendas);
        
        List<Venda> resultado = vendaService.listarVenda();
        
        assertEquals(vendas, resultado);
        verify(repository).findAll();
    }
    
    @Test
    void buscarPorId_QuandoExistir_DeveRetornarVenda() {
        Venda venda = new Venda();
        when(repository.findById(1)).thenReturn(Optional.of(venda));
        
        ResponseEntity<Venda> response = vendaService.buscarPorId(1);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(venda, response.getBody());
    }
    
    @Test
    void buscarPorId_QuandoNaoExistir_DeveRetornar404() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        
        ResponseEntity<Venda> response = vendaService.buscarPorId(1);
        
        assertTrue(response.getStatusCode().is4xxClientError());
    }
    
    @Test
    void criarVenda_DeveSalvarESRetornarVenda() {
        Venda venda = new Venda();
        when(repository.save(any(Venda.class))).thenReturn(venda);
        
        Venda resultado = vendaService.criarVenda(venda);
        
        assertEquals(venda, resultado);
        verify(repository).save(venda);
    }
    
    @Test
    void editarVenda_QuandoExistir_DeveAtualizarVenda() {
        Venda vendaExistente = new Venda();
        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setData_venda(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        vendaAtualizada.setForma_pagamento_venda("Cartão");
        vendaAtualizada.setValor_final_venda(100.0);
        
        when(repository.findById(1)).thenReturn(Optional.of(vendaExistente));
        when(repository.save(any(Venda.class))).thenReturn(vendaExistente);
        
        ResponseEntity<Venda> response = vendaService.editarVenda(1, vendaAtualizada);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(repository).save(vendaExistente);
    }
    
    @Test
    void excluirVenda_QuandoExistir_DeveDeletarVenda() {
        Venda venda = new Venda();
        when(repository.findById(1)).thenReturn(Optional.of(venda));
        
        Optional<Venda> resultado = vendaService.exlcuirVenda(1);
        
        assertTrue(resultado.isPresent());
        verify(repository).deleteById(1);
    }
}