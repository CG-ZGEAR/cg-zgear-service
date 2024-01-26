package com.codegym.cgzgearservice.controller;


import com.codegym.cgzgearservice.dto.payload.request.*;

import com.codegym.cgzgearservice.dto.payload.response.LoginResponse;
import com.codegym.cgzgearservice.security.JwtTokenProvider;
import com.codegym.cgzgearservice.service.ForgotPasswordService;
import com.codegym.cgzgearservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thành công!", roles, token), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thất bại!", null, null), HttpStatus.BAD_REQUEST);
        }

    }

        @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
            return ResponseEntity.ok("Đăng xuất thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng xuất thất bại.");
        }
    }


    @PostMapping("/otp/send")
    public ResponseEntity<String> sendOtp(@RequestBody SendMailRequest sendMailRequest) {
        forgotPasswordService.sendOtpAndSaveToDatabase(sendMailRequest);
        return  new ResponseEntity<>("OTP sent successfully.",HttpStatus.OK);
    }

    @PostMapping("reset-password/verify-otp")
    public ResponseEntity<String> verifyOtpAndResetPassword(
            @RequestBody VerifyOtpRequest verifyOtpRequest) {
        forgotPasswordService.verifyOtpAndResetPassword(verifyOtpRequest);
        return ResponseEntity.ok("Password reset successfully.");
    }

}