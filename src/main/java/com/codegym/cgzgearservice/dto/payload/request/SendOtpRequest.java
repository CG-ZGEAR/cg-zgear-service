package com.codegym.cgzgearservice.dto.payload.request;

import lombok.Data;

/**
 * @author ADMIN
 */
@Data
public class SendOtpRequest {
    private String to;
    private String otp;
}
