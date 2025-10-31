package br.com.tavernadovale.tavernadovale.service;

import java.time.LocalTime;
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
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.tavernadovale.tavernadovale.dao.IFuncionario;
import br.com.tavernadovale.tavernadovale.model.Funcionario;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private IFuncionario repository;

    @InjectMocks
    private FuncionarioService service;

    @Test
    void editarFuncionario_whenNotFound_shouldReturnNotFound() {
        Integer id = 2;
        Funcionario atualizado = new Funcionario();
        atualizado.setNome_funcionario("Doesn't Matter");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Funcionario> response = service.editarFuncionario(id, atualizado);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(repository).findById(id);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void criarFuncionario_whenCargoValido_shouldCreateAndReturnWithId() {
        Funcionario novo = new Funcionario();
        novo.setNome_funcionario("João");
        novo.setCargo_funcionario("Gerente");

        when(repository.save(any())).thenAnswer(invocation -> {
            Funcionario f = invocation.getArgument(0);
            f.setId_funcionario(1);
            return f;
        });

        Funcionario salvo = service.criarFuncionario(novo);

        assertEquals(1, salvo.getId_funcionario());
        assertEquals("João", salvo.getNome_funcionario());
        assertEquals("Gerente", salvo.getCargo_funcionario());

        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void criarFuncionario_whenCargoInvalido_shouldThrowException() {
        Funcionario novo = new Funcionario();
        novo.setNome_funcionario("Maria");
        novo.setCargo_funcionario("CargoInvalido");

        when(repository.save(any())).thenThrow(new IllegalArgumentException("Cargo inválido"));

        try {
            service.criarFuncionario(novo);
            fail("Expected IllegalArgumentException for invalid cargo");
        } catch (IllegalArgumentException e) {
            assertEquals("Cargo inválido", e.getMessage());
        }

        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void listarFuncionario_DeveRetornarListaDeFuncionarios() {
        Funcionario f1 = new Funcionario();
        f1.setId_funcionario(1);
        Funcionario f2 = new Funcionario();
        f2.setId_funcionario(2);
        List<Funcionario> listaMock = Arrays.asList(f1, f2);

        when(repository.findAll()).thenReturn(listaMock);

        List<Funcionario> resultado = service.listarFuncionario();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertSame(listaMock, resultado);
        verify(repository).findAll();
    }

    @Test
    void listarFuncionario_DeveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Funcionario> resultado = service.listarFuncionario();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_QuandoFuncionarioExiste_DeveRetornarOkComFuncionario() {
        Integer id = 1;
        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setId_funcionario(id);
        funcionarioMock.setNome_funcionario("Funcionario Teste");

        when(repository.findById(id)).thenReturn(Optional.of(funcionarioMock));

        ResponseEntity<Funcionario> response = service.buscarPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(funcionarioMock, response.getBody());
        verify(repository).findById(id);
    }

    @Test
    void buscarPorId_QuandoFuncionarioNaoExiste_DeveRetornarNotFound() {
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Funcionario> response = service.buscarPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).findById(id);
    }

    @Test
    void editarFuncionario_QuandoFuncionarioExiste_DeveRetornarOkComFuncionarioAtualizado() {
        Integer id = 1;
        LocalTime entrada = LocalTime.of(8, 0);
        LocalTime saida = LocalTime.of(17, 0);

        Funcionario existente = new Funcionario();
        existente.setId_funcionario(id);
        existente.setNome_funcionario("Nome Antigo");

        Funcionario atualizado = new Funcionario();
        atualizado.setNome_funcionario("Nome Novo");
        atualizado.setCargo_funcionario("Cargo Novo");
        atualizado.setHorario_entrada(entrada);
        atualizado.setHorario_saida(saida);

        when(repository.findById(id)).thenReturn(Optional.of(existente));

        ArgumentCaptor<Funcionario> captor = ArgumentCaptor.forClass(Funcionario.class);
        when(repository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<Funcionario> response = service.editarFuncionario(id, atualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Funcionario salvo = response.getBody();
        assertEquals(id, salvo.getId_funcionario());
        assertEquals("Nome Novo", salvo.getNome_funcionario());
        assertEquals("Cargo Novo", salvo.getCargo_funcionario());
        assertEquals(entrada, salvo.getHorario_entrada());
        assertEquals(saida, salvo.getHorario_saida());

        verify(repository).findById(id);
        verify(repository).save(any(Funcionario.class));
    }

    @Test
    void excluirFuncionario_QuandoFuncionarioExiste_DeveRetornarOptionalComFuncionario() {
        Integer id = 1;
        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setId_funcionario(id);

        when(repository.findById(id)).thenReturn(Optional.of(funcionarioMock));
        doNothing().when(repository).deleteById(id);

        Optional<Funcionario> resultado = service.exlcuirFuncionario(id);

        assertTrue(resultado.isPresent());
        assertSame(funcionarioMock, resultado.get());
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void excluirFuncionario_QuandoFuncionarioNaoExiste_DeveRetornarOptionalVazio() {
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(id);

        Optional<Funcionario> resultado = service.exlcuirFuncionario(id);

        assertFalse(resultado.isPresent());
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

}
