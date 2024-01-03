package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    UserDTO deleteUser(Long userId);
}
