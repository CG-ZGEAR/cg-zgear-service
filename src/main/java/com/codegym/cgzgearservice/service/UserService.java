package com.codegym.cgzgearservice.service;


import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.ResetPasswordRequest;
import com.codegym.cgzgearservice.dto.payload.response.ResetPasswordResponse;
import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserDTO> findAll(Pageable pageable);

    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    void DeleteUserById(Long userId);
    List<UserDTO> getDeletedUsers();
    List<UserDTO> getActiveUsers();
    void save(UserDTO userDto);
    Iterable<UserDTO> findUser(String input);
    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

}
