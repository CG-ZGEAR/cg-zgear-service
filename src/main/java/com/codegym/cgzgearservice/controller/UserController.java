package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        System.out.println(users);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/registers")
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


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.DeleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/deleted-users")
    public ResponseEntity<List<UserDTO>> getDeletedUsers() {
        List<UserDTO> deletedUsers = userService.getDeletedUsers();
        return new ResponseEntity<>(deletedUsers, HttpStatus.OK);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        List<UserDTO> activeUsers = userService.getActiveUsers();
        return new ResponseEntity<>(activeUsers, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId){
    UserDTO user = userService.getUserById(userId);
    if (userDTO == null){
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    userDTO.setId(user.getId());
    return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }
}
