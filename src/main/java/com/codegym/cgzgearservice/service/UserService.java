package com.codegym.cgzgearservice.service;

<<<<<<< HEAD
import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUsersWithUserRole();
    void deleteUser(Long userId, Set<Role> userRoles);

=======

import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO);
    User getUserById(Long userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Long userId);
>>>>>>> 3c010e10aec9ab88510650abf87db59638fd1117
}
