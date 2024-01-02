package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/role-users")
    public List<User> getUsersWithUserRole() {
        return userService.getUsersWithUserRole();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, @RequestBody Set<Role> userRoles) {
        try {
            userService.deleteUser(userId, userRoles);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
}
