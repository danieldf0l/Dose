package br.com.tavernadovale.tavernadovale.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.dao.IProduto;
import br.com.tavernadovale.tavernadovale.model.Produto;



@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private IProduto repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void buscarPorId_RetornaProdutoQuandoExiste() {
        String codigoBarras = "123456";
        Produto produto = new Produto();
        when(repository.findById(codigoBarras)).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> response = service.buscarPorId(codigoBarras);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(produto, response.getBody());
        verify(repository).findById(codigoBarras);
    }

    @Test
    void buscarPorId_RetornaNotFoundQuandoNaoExiste() {
        String codigoBarras = "nao-existe";
        when(repository.findById(codigoBarras)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = service.buscarPorId(codigoBarras);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).findById(codigoBarras);
    }
}