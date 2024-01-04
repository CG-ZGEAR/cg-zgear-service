package com.codegym.cgzgearservice.service;

public interface SercurityService {
    boolean isAuthenticated();
    boolean isValidToken(String token);
}
