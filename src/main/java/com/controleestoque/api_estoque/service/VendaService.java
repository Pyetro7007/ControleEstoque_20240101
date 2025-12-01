package com.controleestoque.api_estoque.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controleestoque.api_estoque.dto.ItemVendaDTO;
import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.exception.EstoqueInsuficienteException;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.ItemVenda;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ClienteRepository;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.VendaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    
    @Transactional(rollbackFor = EstoqueInsuficienteException.class)
    public Venda registrarVenda(VendaRequestDTO request) {
        
        Cliente cliente = clienteRepository.findById(request.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda.setItemVenda(new ArrayList<>());
        BigDecimal totalVenda = BigDecimal.ZERO;
        
        for (ItemVendaDTO itemDto : request.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.getProdutoId()));
            
            Estoque estoque = produto.getEstoque();

            if (estoque.getQuantidade() < itemDto.getQuantidade()) {
                throw new EstoqueInsuficienteException (
                    "Estoque insuficiente para o produto: " + produto.getNome()
                );
            }

            estoque.setQuantidade(estoque.getQuantidade() - itemDto.getQuantidade());
            estoqueRepository.save(estoque);

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(novaVenda);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidadeVendida(itemDto.getQuantidade());
            itemVenda.setPrecoUnitario(produto.getPreco());

            novaVenda.getItemVenda().add(itemVenda);

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade()));
            totalVenda = totalVenda.add(subtotal);
        }
        return vendaRepository.save(novaVenda);
    }
}
