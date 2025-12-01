package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.exception.EstoqueInsuficienteException;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.service.VendaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    
    private final VendaService vendaService;
    private final VendaRepository vendaRepository;

    @GetMapping
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        return vendaRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrarVenda(@RequestBody VendaRequestDTO request) {
        try {
            Venda vendaRegistrada = vendaService.registrarVenda(request);
            return new ResponseEntity<>(vendaRegistrada, HttpStatus.CREATED);
        } catch (EstoqueInsuficienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        if (!vendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vendaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

