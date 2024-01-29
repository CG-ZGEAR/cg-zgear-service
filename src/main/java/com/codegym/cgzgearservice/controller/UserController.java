package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.ManageUserDTO;
import com.codegym.cgzgearservice.dto.UserDTO;
import com.codegym.cgzgearservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<ManageUserDTO>> getActiveUsers(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<ManageUserDTO> activeUsersPage = userService.getActiveUsers(pageable);
        return new ResponseEntity<>(activeUsersPage, HttpStatus.OK);
    }

    @GetMapping("/remove-user")
    public ResponseEntity<Page<ManageUserDTO>> getDeletedUsers(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<ManageUserDTO> deletedUsersPage = userService.getDeletedUsers(pageable);
        return new ResponseEntity<>(deletedUsersPage, HttpStatus.OK);
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

        @PutMapping("/update/{userId}")
        public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
            UserDTO user = userService.getUserById(userId);
            if (userDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            userDTO.setId(user.getId());
            return new ResponseEntity<>(userService.updateUser( userId,  userDTO), HttpStatus.OK);
        }

    @GetMapping("/detail")
    public ResponseEntity<UserDTO> getUser(HttpServletRequest httpRequest) {
        return new ResponseEntity<>(userService.getUserByToken(httpRequest), HttpStatus.OK);
    }
}


