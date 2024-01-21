package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.CartItemDTO;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.CartService;
import com.codegym.cgzgearservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Secured("ROLE_USER")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartDTO> addToCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal User authUser,
            @RequestParam(defaultValue = "1") int quantity) {

        com.codegym.cgzgearservice.entitiy.user. User user = userRepository.findUserByUsername(authUser.getUsername());
        CartDTO cartDTO = cartService.addToCart(user, productId, quantity);

        return ResponseEntity.ok(cartDTO);
    }
}
