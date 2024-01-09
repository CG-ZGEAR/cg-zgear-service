package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.ResetPasswordRequest;
import com.codegym.cgzgearservice.dto.payload.response.ResetPasswordResponse;
import com.codegym.cgzgearservice.entitiy.user.Role;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> entities = userRepository.findAll(pageable);

        List<UserDTO> dtos = entities.getContent().stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!userDTO.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        Role role = roleRepository.findRoleByName("ROLE_USER");
        user.getRoles().add(role);
        user.setDeleted(false);
        user.setActivated(true);
        userRepository.save(user);
        UserDTO savedDTO = modelMapper.map(user, UserDTO.class);
        return savedDTO;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username doesn't exists");
        }
        if (!userDTO.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        userRepository.save(user);
        UserDTO savedDTO = modelMapper.map(user, UserDTO.class);
        return savedDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserDTO.class);
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
    public void save(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        if (!userDto.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        userRepository.save(user);

    }

    @Override
    public Iterable<UserDTO> findUser(String input) {
        Iterable<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().contains(input)) {
                userDTOS.add(modelMapper.map(user, UserDTO.class));
            }
        }
        return userDTOS;
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
//        String username = resetPasswordRequest.getUsername();
        String email = resetPasswordRequest.getEmail();
        String newPassword = resetPasswordRequest.getNewPassword();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
            userRepository.save(user);
            return new ResetPasswordResponse("Reset password successfully!", HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid email!");
        }
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
            if (!user.isActivated()) {
                user.setActivated(true);
                userRepository.save(user);
            }
        });
    }

    @Override
    public void unlockAccount(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            if (user.isActivated()) {
                user.setActivated(false);
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

