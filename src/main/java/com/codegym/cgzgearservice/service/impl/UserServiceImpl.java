package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersWithUserRole() {
        List<User> userList = userRepository.findByRoles_Name("ROLE_USER");
        return userList;
    }

    @Override
    public void deleteUser(Long userId, Set<Role> userRoles) {
        User userToDelete = userRepository.findById(userId).orElse(null);

        if (userToDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }

        boolean isAdmin = userRoles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            userToDelete.setDeleted(true);
            userRepository.save(userToDelete);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not have permission to delete");
        }

    }

}
