package br.com.tavernadovale.tavernadovale.service;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    void editarFuncionario_whenExists_shouldUpdateAndReturnOk() {
        Integer id = 1;
        Funcionario existente = new Funcionario();
        existente.setNome_funcionario("Old Name");
        existente.setCargo_funcionario("Old Cargo");
        existente.setHorario_entrada(LocalTime.parse("08:00"));
        existente.setHorario_saida(LocalTime.parse("17:00"));

        Funcionario atualizado = new Funcionario();
        atualizado.setNome_funcionario("New Name");
        atualizado.setCargo_funcionario("New Cargo");
        atualizado.setHorario_entrada(LocalTime.parse("09:00"));
        atualizado.setHorario_saida(LocalTime.parse("18:00"));

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(Funcionario.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Funcionario> response = service.editarFuncionario(id, atualizado);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Funcionario body = response.getBody();
        assertEquals("New Name", body.getNome_funcionario());
        assertEquals("New Cargo", body.getCargo_funcionario());
        assertEquals("09:00", body.getHorario_entrada());
        assertEquals("18:00", body.getHorario_saida());

        ArgumentCaptor<Funcionario> captor = ArgumentCaptor.forClass(Funcionario.class);
        verify(repository).save(captor.capture());
        Funcionario saved = captor.getValue();
        assertEquals("New Name", saved.getNome_funcionario());
        assertEquals("New Cargo", saved.getCargo_funcionario());
        assertEquals("09:00", saved.getHorario_entrada());
        assertEquals("18:00", saved.getHorario_saida());

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void editarFuncionario_whenNotFound_shouldReturnNotFound() {
        Integer id = 2;
        Funcionario atualizado = new Funcionario();
        atualizado.setNome_funcionario("Doesn't Matter");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Funcionario> response = service.editarFuncionario(id, atualizado);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(repository).findById(id);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }
}