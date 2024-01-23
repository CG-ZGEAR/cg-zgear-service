package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.SearchRequest;
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
    public Page<ManageUserDTO> getActiveUsers(Pageable pageable) {
        Page<User> activeUsersPage = userRepository.findByIsDeletedFalse(pageable);
        return activeUsersPage.map(this::convertToManageUserDTO);
    }
    @Override
    public Page<ManageUserDTO> getDeletedUsers(Pageable pageable) {
        Page <User> deletedUsersPage = userRepository.findByIsDeletedTrue(pageable);
        return deletedUsersPage.map(this::convertToManageUserDTO);
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

    @Override
    public Page<UserDTO> search(SearchRequest searchRequest, Pageable pageable) {
        Page<User> userPage = userRepository.searchUsers(
                searchRequest.getUsername(),
                searchRequest.getFullName(),
                searchRequest.getEmail(),
                pageable
        );
        return userPage.map(userEntity -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setFullName(userEntity.getFullName());
            userDTO.setEmail(userEntity.getEmail());
            return userDTO; 
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


