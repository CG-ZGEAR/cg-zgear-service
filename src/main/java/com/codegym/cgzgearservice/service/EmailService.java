package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.payload.request.SendOtpRequest;

/**
 * @author ADMIN
 */
public interface EmailService {
    void sendOtp(SendOtpRequest sendOtpRequest);


}
