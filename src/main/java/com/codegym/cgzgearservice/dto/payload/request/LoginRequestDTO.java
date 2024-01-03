package com.codegym.cgzgearservice.dto.payload.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}