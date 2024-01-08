package com.codegym.cgzgearservice.service;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.entitiy.user.User;

import java.util.List;

public interface UserService {


    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    void DeleteUserById(Long userId);
    List<ManageUserDTO> getDeletedUsers();
    List<ManageUserDTO> getActiveUsers();
    void lockAccount(long userId);
    void unlockAccount(long userId);
    }
