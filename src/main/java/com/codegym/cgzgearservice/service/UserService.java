package com.codegym.cgzgearservice.service;



import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO);
    User getUserById(Long userId);
    List<UserDTO> getAllUsers();
    List<User> getUsersWithUserRole();
    void deleteUser(Long userId, Set<Role> userRoles);
}
