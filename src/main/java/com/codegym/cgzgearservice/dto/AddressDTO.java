package com.codegym.cgzgearservice.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;

    private String street;

    private String city;

    private String district;

    private String ward;
}
