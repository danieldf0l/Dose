package br.com.tavernadovale.tavernadovale.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void criarProduto_ComDadosValidos_DeveRetornarProduto() {
        Produto produtoParaSalvar = new Produto();
        produtoParaSalvar.setCodigo_barras("78910001");
        produtoParaSalvar.setNome_produto("Produto Valido");
        produtoParaSalvar.setValor_produto(10.50f);
        produtoParaSalvar.setTipo_produto("Bebida");

        when(repository.save(produtoParaSalvar)).thenReturn(produtoParaSalvar);

        Produto produtoSalvo = service.criarProduto(produtoParaSalvar);

        assertNotNull(produtoSalvo);
        assertSame(produtoParaSalvar, produtoSalvo, "O objeto retornado deve ser o mesmo que foi salvo.");
        assertEquals("78910001", produtoSalvo.getCodigo_barras());
        verify(repository).save(produtoParaSalvar); // Verifica se o save foi chamado
    }

    @Test
    void criarProduto_ComPrecoNulo_DeveRetornarProduto() {
        Produto produtoPrecoNulo = new Produto();
        produtoPrecoNulo.setCodigo_barras("78910002");
        produtoPrecoNulo.setNome_produto("Produto Com Preco Nulo");
        produtoPrecoNulo.setValor_produto(null); // Campo de preço nulo
        produtoPrecoNulo.setTipo_produto("Comida");

        when(repository.save(produtoPrecoNulo)).thenReturn(produtoPrecoNulo);

        Produto produtoSalvo = service.criarProduto(produtoPrecoNulo);

        assertNotNull(produtoSalvo);
        assertNull(produtoSalvo.getValor_produto(), "O valor do produto deve ser nulo.");
        assertEquals("78910002", produtoSalvo.getCodigo_barras());
        verify(repository).save(produtoPrecoNulo);
    }

    @Test
    void criarProduto_QuandoRepositorioLancaExcecao_DeveRethrowExcecao() {
        Produto produtoComErro = new Produto();
        produtoComErro.setCodigo_barras("123");

        RuntimeException excecaoSimulada = new RuntimeException("Erro de banco de dados");

        when(repository.save(produtoComErro)).thenThrow(excecaoSimulada);

        RuntimeException exceptionLancada = assertThrows(RuntimeException.class, () -> {
            service.criarProduto(produtoComErro);
        }, "O serviço deveria ter relançado a RuntimeException");

        assertSame(excecaoSimulada, exceptionLancada, "A exceção relançada deve ser a mesma do repositório.");

        verify(repository).save(produtoComErro);
    }

    @Test
    void listarProduto_DeveRetornarListaDeProdutos() {
        Produto p1 = new Produto();
        p1.setCodigo_barras("111");
        Produto p2 = new Produto();
        p2.setCodigo_barras("222");
        List<Produto> listaMock = Arrays.asList(p1, p2);

        when(repository.findAll()).thenReturn(listaMock);

        List<Produto> resultado = service.listarProduto();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertSame(listaMock, resultado);
        verify(repository).findAll();
    }

    @Test
    void listarProduto_DeveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Produto> resultado = service.listarProduto();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void editarProduto_QuandoProdutoExiste_DeveRetornarOk() {
        String codigoBarras = "789_EXISTE";
        Produto produtoExistente = new Produto();
        produtoExistente.setCodigo_barras(codigoBarras);
        produtoExistente.setNome_produto("Nome Antigo");
        produtoExistente.setValor_produto(10.0f);

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome_produto("Nome Novo");
        produtoAtualizado.setValor_produto(15.0f);
        produtoAtualizado.setTipo_produto("Tipo Novo");

        when(repository.findById(codigoBarras)).thenReturn(Optional.of(produtoExistente));

        ArgumentCaptor<Produto> produtoCaptor = ArgumentCaptor.forClass(Produto.class);
        when(repository.save(produtoCaptor.capture())).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<Produto> response = service.editarProduto(codigoBarras, produtoAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Produto produtoSalvo = response.getBody();
        assertEquals(codigoBarras, produtoSalvo.getCodigo_barras());
        assertEquals("Nome Novo", produtoSalvo.getNome_produto());
        assertEquals(15.0f, produtoSalvo.getValor_produto());
        assertEquals("Tipo Novo", produtoSalvo.getTipo_produto());

        verify(repository).findById(codigoBarras);
        verify(repository).save(any(Produto.class));
    }

    @Test
    void editarProduto_QuandoProdutoNaoExiste_DeveRetornarNotFound() {
        String codigoBarras = "789_NAO_EXISTE";
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome_produto("Nome Novo");

        when(repository.findById(codigoBarras)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = service.editarProduto(codigoBarras, produtoAtualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(repository).findById(codigoBarras);
        verify(repository, never()).save(any(Produto.class));
    }

    @Test
    void excluirProduto_QuandoProdutoExiste_DeveRetornarOptionalComProduto() {
        String codigoBarras = "333_DELETE";
        Produto produtoParaExcluir = new Produto();
        produtoParaExcluir.setCodigo_barras(codigoBarras);

        when(repository.findById(codigoBarras)).thenReturn(Optional.of(produtoParaExcluir));
        doNothing().when(repository).deleteById(codigoBarras);

        Optional<Produto> resultado = service.excluirProduto(codigoBarras);

        assertTrue(resultado.isPresent());
        assertSame(produtoParaExcluir, resultado.get());

        verify(repository).findById(codigoBarras);
        verify(repository).deleteById(codigoBarras);
    }

    @Test
    void excluirProduto_QuandoProdutoNaoExiste_DeveRetornarOptionalVazio() {
        String codigoBarras = "444_NAO_EXISTE";

        when(repository.findById(codigoBarras)).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(codigoBarras);

        Optional<Produto> resultado = service.excluirProduto(codigoBarras);

        assertFalse(resultado.isPresent(), "O Optional retornado deve estar vazio.");

        verify(repository).findById(codigoBarras);
        verify(repository).deleteById(codigoBarras);
    }

}
