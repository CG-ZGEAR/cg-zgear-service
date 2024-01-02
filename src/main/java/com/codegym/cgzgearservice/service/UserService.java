package com.codegym.cgzgearservice.service;



import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO);
    User getUserById(Long userId);
    List<UserDTO> getAllUsers();

    void DeleteUserById(Long userId);

}
