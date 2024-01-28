package com.codegym.cgzgearservice.dto;

import lombok.Data;

@Data
public class ManageUserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String date;
    private String gender;
    private String phoneNumber;
    private String avatar;
    private boolean activated;
    private boolean isDeleted;
}

