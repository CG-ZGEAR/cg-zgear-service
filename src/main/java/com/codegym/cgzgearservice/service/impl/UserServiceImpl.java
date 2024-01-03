package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service @Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<UserDTO> getUsers() {
        Iterable<User> entities = userRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public UserDTO remove(Long id) {
        User user= userRepository.findUserById(id);
        if (user==null){
            return null;
        }
        user.setActivated(false);
        user = userRepository.save(user);
        UserDTO removedDTO = modelMapper.map(user, UserDTO.class);
        return removedDTO;
    }
    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return modelMapper.map(user,UserDTO.class);
    }
    @Override
    public List<UserDTO> searchUsersByNameContains(String name) {
        List<User> userEntities= userRepository.findByUsernameContainsIgnoreCase(name);
        return StreamSupport.stream(userEntities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
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
        user.setActivated(true);
        userRepository.save(user);
        UserDTO savedDTO = modelMapper.map(user, UserDTO.class);
        return savedDTO;
    }


}