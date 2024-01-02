package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUsersWithUserRole();
    void deleteUser(Long userId, Set<Role> userRoles);

}
