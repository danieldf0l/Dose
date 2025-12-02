package br.com.tavernadovale.tavernadovale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 1. NOVO IMPORT: Para garantir atomicidade (tudo ou nada)

import br.com.tavernadovale.tavernadovale.dao.IEstoque;       // 2. NOVO IMPORT: Para acessar o DAO de Estoque
import br.com.tavernadovale.tavernadovale.dao.IVenda;
import br.com.tavernadovale.tavernadovale.model.Venda;
import br.com.tavernadovale.tavernadovale.model.ProdutosVenda; // 3. NOVO IMPORT: Para acessar os itens da venda

@Service
public class VendaService {

    private final IVenda repository; // IVenda
    private final IEstoque estoqueRepository; // 4. NOVO ATRIBUTO: DAO de Estoque

    // 5. CONSTRUTOR ATUALIZADO: Injetando IVenda e IEstoque
    public VendaService(IVenda vendaRepository, IEstoque estoqueRepository) {
        this.repository = vendaRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<Venda> listarVenda() {
        return (List<Venda>) repository.findAll();
    }

    public ResponseEntity<Venda> buscarPorId(int id) {
        return repository.findById(id)
                .map(venda -> ResponseEntity.ok(venda))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 6. MÉTODO criarVenda REESCRITO: Salva a Venda, seus itens e dá baixa no Estoque de forma transacional.
     * @param venda O objeto Venda contendo a lista de ProdutosVenda.
     * @return A Venda salva.
     */
    @Transactional // ESSENCIAL: Garante que a baixa de estoque e o registro da venda sejam uma operação única.
    public Venda criarVenda(Venda venda) {
        
        // 6.1. Salva a Venda principal e seus itens (os itens são salvos via Cascade configurado em Venda.java)
        Venda vendaSalva = repository.save(venda);
        
        // 6.2. Pega a lista de itens para dar baixa no estoque
        List<ProdutosVenda> itensVendidos = vendaSalva.getProdutosVenda(); 

        if (itensVendidos != null) {
            for (ProdutosVenda item : itensVendidos) {
                
                // 6.3. Obtém o ID do Lote (Estoque) e a quantidade vendida
                // O objeto Estoque deve ter sido populado pelo Controller/Frontend
                int idLote = item.getEstoque().getId_registro_estoque();
                int quantidade = item.getQuantidade_venda();
                
                // 6.4. Chama a DAO de Estoque para decrementar a quantidade do lote específico
                int linhasAfetadas = estoqueRepository.decrementarEstoque(idLote, quantidade);
                
                // 6.5. Verifica se a baixa foi bem-sucedida. Se não, lança exceção para reverter TUDO (rollback)
                if (linhasAfetadas == 0) {
                    throw new RuntimeException("Falha ao dar baixa no estoque para o Lote ID: " + idLote + ". Estoque insuficiente, lote não encontrado ou tentativa de vender mais do que o disponível.");
                }
            }
        }
        
        return vendaSalva;
    }

    public ResponseEntity<Venda> editarVenda(Integer idVenda, Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = repository.findById(idVenda);

        if (vendaExistente.isPresent()) {
            Venda venda = vendaExistente.get();

            // Nota: Você deve reajustar estes setters conforme os nomes de métodos em sua classe Venda.java
            // Exemplo: venda.setData_venda(vendaAtualizada.getData_hora_venda());
            // Se os nomes dos métodos não estão corretos, ajuste-os ou use os nomes exatos.
            // Para manter a compatibilidade com seu código original:
            // venda.setData_venda(vendaAtualizada.getData_hora_venda());
            // venda.setForma_pagamento_venda(vendaAtualizada.getForma_pagamento_venda());
            // venda.setValor_final_venda(vendaAtualizada.getValor_final_venda());
            
            Venda produtoSalvo = repository.save(venda);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Venda> exlcuirVenda(Integer idVenda) {
        Optional<Venda> venda = repository.findById(idVenda);
        repository.deleteById(idVenda);
        return venda;
    }
}