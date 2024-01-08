package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository){
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



        @Override
        public void DeleteUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User userToDelete = userOptional.get();
            userToDelete.setDeleted(true);
            userRepository.save(userToDelete);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        }
        @Override
        public List<ManageUserDTO> getDeletedUsers() {
            List<User> deletedUsers = userRepository.findByIsDeletedTrue();
            return deletedUsers.stream()
                    .map(this::convertToManageUserDTO)
                    .collect(Collectors.toList());
        }
    @Override
    public List<ManageUserDTO> getActiveUsers() {
        List<User> activeUsers = userRepository.findByIsDeletedFalse();
        return activeUsers.stream()
                .map(user -> {
                    ManageUserDTO manageUserDTO = convertToManageUserDTO(user);
                    manageUserDTO.setDeleted(false); 
                    return manageUserDTO;
                })
                .collect(Collectors.toList());
    }


    public void lockAccount(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            if (!user.isLocked()) {
                user.setLocked(true);
                userRepository.save(user);
            }
        });
    }

    @Override
    public void unlockAccount(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            if (user.isLocked()) {
                user.setLocked(false);
                userRepository.save(user);
            }
        });
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        return dto;
    }
    private ManageUserDTO convertToManageUserDTO(User user) {
        ManageUserDTO dto = modelMapper.map(user, ManageUserDTO.class);
        return dto;
    }
    }
