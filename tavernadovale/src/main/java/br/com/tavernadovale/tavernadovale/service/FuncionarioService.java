package br.com.tavernadovale.tavernadovale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tavernadovale.tavernadovale.dao.IFuncionario;
import br.com.tavernadovale.tavernadovale.model.Funcionario;

@Service
public class FuncionarioService {

    private final IFuncionario repository;

    public FuncionarioService(IFuncionario repository) {
        this.repository = repository;
    }

    public List<Funcionario> listarFuncionario() {
        return (List<Funcionario>) repository.findAll();
    }

    public ResponseEntity<Funcionario> buscarPorId(int id) {
        return repository.findById(id)
                .map(funcionario -> ResponseEntity.ok(funcionario))
                .orElse(ResponseEntity.notFound().build());
    }

    public Funcionario criarFuncionario(Funcionario funcionario) {
        Funcionario funcionarioNovo = repository.save(funcionario);
        return funcionarioNovo;
    }

    public ResponseEntity<Funcionario> editarFuncionario(Integer idfuncionario, Funcionario funcionarioAtualizado) {
        Optional<Funcionario> funcionarioExistente = repository.findById(idfuncionario);

        if (funcionarioExistente.isPresent()) {
            Funcionario funcionario = funcionarioExistente.get();

            funcionario.setNome_funcionario(funcionarioAtualizado.getNome_funcionario());
            funcionario.setCargo_funcionario(funcionarioAtualizado.getCargo_funcionario());
            funcionario.setHorario_entrada(funcionarioAtualizado.getHorario_entrada());
            funcionario.setHorario_saida(funcionarioAtualizado.getHorario_saida());

            Funcionario FuncionarioSalvo = repository.save(funcionario);
            return ResponseEntity.ok(FuncionarioSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Funcionario> exlcuirFuncionario(Integer idFuncionario) {
        Optional<Funcionario> funcionario = repository.findById(idFuncionario);
        repository.deleteById(idFuncionario);
        return funcionario;
    }
}
