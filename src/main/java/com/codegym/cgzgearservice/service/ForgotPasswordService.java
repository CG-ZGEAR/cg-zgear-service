package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.payload.request.SendMailRequest;
import com.codegym.cgzgearservice.dto.payload.request.VerifyOtpRequest;

/**
 * @author ADMIN
 */
public interface ForgotPasswordService {

    void sendOtpAndSaveToDatabase(SendMailRequest sendMailRequest);

    void verifyOtpAndResetPassword(VerifyOtpRequest verifyOtpRequest);
}
