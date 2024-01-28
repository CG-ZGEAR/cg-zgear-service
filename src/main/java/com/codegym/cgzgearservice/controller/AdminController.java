package com.codegym.cgzgearservice.controller;


import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.dto.payload.request.SearchRequest;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.UserService;
import com.codegym.cgzgearservice.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")

public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user-list")
    public ResponseEntity<Page<UserDTO>> getUserList(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/foo/list")
    public Page<User> handleList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/user-list/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/user-list/user/save/{id}")
    public ResponseEntity<String> saveOrUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.save(userDTO);
        return ResponseEntity.ok("Save/Update completed!");
    }

    @GetMapping("/user-list/find-user/{input}")
    public ResponseEntity<Iterable<UserDTO>> findUser(@PathVariable String input) {
        return new ResponseEntity<>(userService.findUser(input), HttpStatus.OK);
    }

    @PostMapping("/search")
    public Page<UserDTO> search(@RequestBody SearchRequest searchRequest, Pageable pageable) {
        Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), 5);
        return userService.search(searchRequest, modifiedPageable);
    }

}
