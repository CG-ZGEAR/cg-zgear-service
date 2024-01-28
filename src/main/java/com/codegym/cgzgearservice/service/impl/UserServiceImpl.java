package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.SearchRequest;
import com.codegym.cgzgearservice.entitiy.user.Role;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.security.JwtTokenProvider;
import com.codegym.cgzgearservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    final JwtTokenProvider jwtTokenProvider;

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
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(10));
                existingUser.setPassword(hashedPassword);
            }
            if (userDTO.getUsername() != null) {
                existingUser.setUsername(userDTO.getUsername());
            }
            if (userDTO.getFullName() != null) {
                existingUser.setFullName(userDTO.getFullName());
            }
            if (userDTO.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDTO.getPhoneNumber());
            }
            if (userDTO.getGender() != null) {
                existingUser.setGender(userDTO.getGender());
            }
            if (userDTO.getEmail() != null) {
                existingUser.setEmail(userDTO.getEmail());
            } else {
                throw new IllegalArgumentException("Email cannot be null");
            }
            if (userDTO.getDate() != null) {
                existingUser.setDate(userDTO.getDate());
            }
            if (userDTO.getAvatar() != null) {
                existingUser.setAvatar(userDTO.getAvatar());
            }
            userRepository.save(existingUser);
            return convertToUserDTO(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }


    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserDTO.class);
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
        Page<User> deletedUsersPage = userRepository.findByIsDeletedTrue(pageable);
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


    private UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setDate(user.getDate());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public Page<UserDTO> search(SearchRequest searchRequest, Pageable pageable) {
        Page<User> userPage = userRepository.findByUsernameContainingOrFullNameContainingOrEmailContaining(
                searchRequest.getUsername(),
                searchRequest.getFullName(),
                searchRequest.getEmail(),
                pageable
        );
        return userPage.map(this::convertToUserDTO);
    }

    @Override
    public UserDTO getUserByToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findUserByUsername(username);
        return convertToUserDTO(user);
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


