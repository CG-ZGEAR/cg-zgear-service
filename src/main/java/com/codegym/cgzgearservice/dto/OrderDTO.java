package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private String customerName;

    private String customerEmail;

    private AddressDTO addressDTO;

    private Double total;

    private List<OrderItemDTO> items;
}
