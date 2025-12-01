package com.controleestoque.api_estoque.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaRequestDTO {
    private Long clienteId;
    private List<ItemVendaDTO> itens;
}
