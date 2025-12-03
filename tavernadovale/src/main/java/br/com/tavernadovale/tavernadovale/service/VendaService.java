package br.com.tavernadovale.tavernadovale.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tavernadovale.tavernadovale.dao.IEstoque;
import br.com.tavernadovale.tavernadovale.dao.IProduto;
import br.com.tavernadovale.tavernadovale.dao.IVenda;
import br.com.tavernadovale.tavernadovale.model.Estoque;
import br.com.tavernadovale.tavernadovale.model.Produto;
import br.com.tavernadovale.tavernadovale.model.ProdutosVenda;
import br.com.tavernadovale.tavernadovale.model.Venda;

@Service
public class VendaService {

    @Autowired
    private IVenda repository;

    @Autowired
    private IProduto produtoRepository;

    @Autowired
    private IEstoque estoqueRepository;

    // ---------------------------------------------------------
    // MÉTODOS QUE O CONTROLLER ESTÁ EXIGINDO
    // ---------------------------------------------------------

    public List<Venda> listarVenda() {
        return (List<Venda>) repository.findAll();
    }

    public ResponseEntity<Venda> buscarPorId(int id) {
        Optional<Venda> venda = repository.findById(id);
        return venda.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Venda> editarVenda(Integer idVenda, Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = repository.findById(idVenda);

        if (!vendaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Venda venda = vendaExistente.get();
        venda.setNome_cliente(vendaAtualizada.getNome_cliente());
        venda.setCpf_cliente(vendaAtualizada.getCpf_cliente());
        venda.setValor_total_venda(vendaAtualizada.getValor_total_venda());
        venda.setPago(vendaAtualizada.isPago());

        // Você pode decidir se permite editar produtos_venda

        Venda vendaEditada = repository.save(venda);

        return ResponseEntity.ok(vendaEditada);
    }

    public Optional<Venda> exlcuirVenda(Integer idVenda) {
        Optional<Venda> venda = repository.findById(idVenda);
        venda.ifPresent(repository::delete);
        return venda;
    }

    // ---------------------------------------------------------
    // MÉTODO PRINCIPAL: CRIAR VENDA
    // ---------------------------------------------------------

    @Transactional
    public Venda criarVenda(Venda venda) {

        if (venda.getDataHoraVenda() == null) {
            venda.setDataHoraVenda(LocalDateTime.now());
        }

        for (ProdutosVenda item : venda.getProdutosVenda()) {

            String codigo = item.getProduto().getCodigo_barras();
            Produto produtoBanco = produtoRepository.findById(codigo)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + codigo));

            item.setProduto(produtoBanco);

            int idLote = item.getEstoque().getId_registro_estoque();

            Estoque loteBanco = estoqueRepository.findById(idLote)
                    .orElseThrow(() -> new RuntimeException("Lote não encontrado: " + idLote));

            if (loteBanco.getQuantidade_lote() < item.getQuantidade_venda()) {
                throw new RuntimeException(
                    "Estoque insuficiente no lote " + idLote +
                    " (disponível: " + loteBanco.getQuantidade_lote() +
                    ", vendido: " + item.getQuantidade_venda() + ")"
                );
            }

            item.setEstoque(loteBanco);
            item.setVenda(venda);
        }

        Venda vendaSalva = repository.save(venda);

        for (ProdutosVenda item : vendaSalva.getProdutosVenda()) {

            int linhasAfetadas = estoqueRepository.decrementarEstoque(
                    item.getEstoque().getId_registro_estoque(),
                    item.getQuantidade_venda()
            );

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Erro ao atualizar estoque do lote: " +
                        item.getEstoque().getId_registro_estoque());
            }
        }

        return vendaSalva;
    }
}
