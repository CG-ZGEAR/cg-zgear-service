package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.payload.request.SendMailRequest;
import com.codegym.cgzgearservice.dto.payload.request.SendOtpRequest;
import com.codegym.cgzgearservice.dto.payload.request.VerifyOtpRequest;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.security.OtpGenerator;
import com.codegym.cgzgearservice.service.ForgotPasswordService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * @author ADMIN
 */
@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final OtpGenerator otpGenerator;

    @Override
    public void sendOtpAndSaveToDatabase(SendMailRequest sendMailRequest) {
        SendOtpRequest sendOtpRequest = new SendOtpRequest();
        sendOtpRequest.setTo(sendMailRequest.getEmail());
        sendOtpRequest.setOtp(otpGenerator.generateOtp());

        emailService.sendOtp(sendOtpRequest);
        saveOtpToDatabase(sendOtpRequest.getTo(), sendOtpRequest.getOtp());
    }

    @Override
    public void verifyOtpAndResetPassword(VerifyOtpRequest verifyOtpRequest) {
        Optional<User> optionalUser = userRepository.findByEmailAndOtpCodeAndOtpExpirationAfter(
                verifyOtpRequest.getEmail(),
                verifyOtpRequest.getOtp(),
                LocalDateTime.now());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(verifyOtpRequest.getNewPassword()));
            user.setOtpCode(null);
            user.setOtpExpiration(null);
            userRepository.save(user);
        }
    }


    private void saveOtpToDatabase(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setOtpCode(otp);
            user.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
            userRepository.save(user);
        }
    }
}
