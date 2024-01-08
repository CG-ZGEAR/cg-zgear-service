package com.codegym.cgzgearservice.dto.payload.response;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RegisterResponse {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;

    public RegisterResponse(String username, String password, String email, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public RegisterResponse() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
