package com.codegym.cgzgearservice.service;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
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
    void save(UserDTO userDto);
    Iterable<UserDTO> findUser(String input);
    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

    List<ManageUserDTO> getDeletedUsers();
    List<ManageUserDTO> getActiveUsers();
    void lockAccount(long userId);
    void unlockAccount(long userId);
    }
