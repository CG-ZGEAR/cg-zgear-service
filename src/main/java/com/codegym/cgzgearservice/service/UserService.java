package com.codegym.cgzgearservice.service;


import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserById(Long userId);
    UserDTO registerUser(UserDTO UserDTO);
    UserDTO remove(Long id);
    UserDTO findUserByUsername(String username);
    UserDTO findUserByEmail(String email);

    List<UserDTO> searchUsersByNameContains(String name);

    UserDTO update(UserDTO userDTO);
}