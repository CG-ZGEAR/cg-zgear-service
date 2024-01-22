package com.codegym.cgzgearservice.dto.payload.request;

import lombok.Data;

/**
 * @author ADMIN
 */
@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
    private String newPassword;
}
