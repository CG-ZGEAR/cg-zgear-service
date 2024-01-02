package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers () {
        List<UserDTO> users = userService.getAllUsers();
        System.out.println(users);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/registers")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UserDTO registeredUser = userService.registerUser(userDTO);

        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
