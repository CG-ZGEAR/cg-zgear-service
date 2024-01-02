package com.codegym.cgzgearservice.service.impl;



import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.getRoles();
        userRepository.save(user);
        UserDTO savedDTO = modelMapper.map(user, UserDTO.class);
        return savedDTO;
    }

    @Override
    public User updateUser(Long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        return dto;
    }


    public List<User> getUsersWithUserRole() {
        List<User> userList = userRepository.findByRoles_Name("ROLE_USER");
        return userList;
    }

    @Override
    public void deleteUser(Long userId, Set<Role> userRoles) {
        User userToDelete = userRepository.findById(userId).orElse(null);

        if (userToDelete == null) {
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
