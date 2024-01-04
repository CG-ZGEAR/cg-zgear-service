package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        List<UserDTO> activeUsers = userService.getActiveUsers();
        return new ResponseEntity<>(activeUsers, HttpStatus.OK);
    }
    @GetMapping("/remove-user")
    public ResponseEntity<List<UserDTO>> getDeletedUsers() {
        List<UserDTO> deletedUsers = userService.getDeletedUsers();
        return new ResponseEntity<>(deletedUsers, HttpStatus.OK);
    }
    @PostMapping("/{userId}/lock")
    public ResponseEntity<String> lockUserAccount(@PathVariable long userId) {
        userService.lockAccount(userId);
        return ResponseEntity.ok("Account locked successfully");
    }

    @PostMapping("/{userId}/unlock")
    public ResponseEntity<String> unlockUserAccount(@PathVariable long userId) {
        userService.unlockAccount(userId);
        return ResponseEntity.ok("Account unlocked successfully");
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
    @PostMapping("/register")
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
