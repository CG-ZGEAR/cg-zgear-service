package com.codegym.cgzgearservice.dto.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

public class LoginResponse {
    @NotBlank
    private String message;

    @NotBlank
    private List<String> roles;

    @Nullable
    private String token;

    public LoginResponse() {
        super();
    }

    public LoginResponse(String message, @Nullable String token,List<String> roles) {
        this.message = message;
        this.roles = roles;
        this.token = token;
    }

    public LoginResponse(String message, String token, boolean isAdmin) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
