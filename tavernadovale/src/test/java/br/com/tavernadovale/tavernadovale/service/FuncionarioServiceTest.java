package br.com.tavernadovale.tavernadovale.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
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
}