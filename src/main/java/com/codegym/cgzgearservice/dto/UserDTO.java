package com.codegym.cgzgearservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String date;
    private String gender;
    private String phoneNumber;
    private String avatar;
}

