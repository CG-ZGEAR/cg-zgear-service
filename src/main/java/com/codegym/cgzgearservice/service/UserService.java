package com.codegym.cgzgearservice.service;


import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserDTO> findAll(Pageable pageable);

    UserDTO registerUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    void DeleteUserById(Long userId);

    void save(UserDTO userDto);

    Iterable<UserDTO> findUser(String input);

    Page<ManageUserDTO> getDeletedUsers(Pageable pageable);

    Page<ManageUserDTO> getActiveUsers(Pageable pageable);

    void lockAccount(long userId);

    void unlockAccount(long userId);

<<<<<<< HEAD
}
=======
    Page <UserDTO> search (SearchRequest searchRequest, Pageable  pageable);
    }
>>>>>>> ae36565c6ad1b5f24d845a9a3b46f831e2f3ce0d
