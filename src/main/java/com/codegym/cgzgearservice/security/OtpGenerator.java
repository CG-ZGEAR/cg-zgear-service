package com.codegym.cgzgearservice.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {

    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;

    public String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(OTP_CHARACTERS.length());
            char randomChar = OTP_CHARACTERS.charAt(randomIndex);
            otp.append(randomChar);
        }

        return otp.toString();
    }

    public static void main(String[] args) {
        OtpGenerator otpGenerator = new OtpGenerator();
        String otp = otpGenerator.generateOtp();
        System.out.println("Generated OTP: " + otp);
    }
}

