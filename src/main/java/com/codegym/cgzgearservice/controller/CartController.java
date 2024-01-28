package com.codegym.cgzgearservice.controller;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.OrderDTO;
import com.codegym.cgzgearservice.repository.UserRepository;
import com.codegym.cgzgearservice.service.CartService;
import com.codegym.cgzgearservice.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartDTO> addToCart(
            @PathVariable Long productId,
            HttpSession session,
            @AuthenticationPrincipal User authUser,
            @RequestParam(defaultValue = "1") int quantity) {
        CartDTO cartDTO;
        if (authUser == null){
             cartDTO = cartService.addToCart(null,session.getId(), productId, quantity);

        } else {
            com.codegym.cgzgearservice.entitiy.user.User user = userRepository.findUserByUsername(authUser.getUsername());
             cartDTO = cartService.addToCart(user,session.getId(), productId, quantity);
        }
        return ResponseEntity.ok()
                .header("Session-Id", session.getId())
                .body(cartDTO);
    }

    @GetMapping()
    public ResponseEntity<CartDTO> getCart(
            @AuthenticationPrincipal User authUser,
            HttpSession session) {

        CartDTO cartDTO;
        if (authUser == null){
            cartDTO = cartService.getCart(null,session.getId());

        } else {
            com.codegym.cgzgearservice.entitiy.user.User user = userRepository.findUserByUsername(authUser.getUsername());
            cartDTO = cartService.getCart(user,session.getId());
        }
        return ResponseEntity.ok()
                .header("Session-Id", session.getId())
                .body(cartDTO);

    }
    @PostMapping("/merge")
    public ResponseEntity<Void> mergeCarts(HttpSession session,
                                           @AuthenticationPrincipal User authUser) {

        com.codegym.cgzgearservice.entitiy.user.User user = userRepository.findUserByUsername(authUser.getUsername());
        cartService.mergeCarts(session.getId(), user);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCart(
            @RequestBody CartDTO cartDTO,
            @AuthenticationPrincipal User authUser,
            HttpSession session
    ){
        if (authUser == null){
            cartDTO = cartService.updateCart(null,session.getId(), cartDTO);

        } else {
            com.codegym.cgzgearservice.entitiy.user.User user = userRepository.findUserByUsername(authUser.getUsername());
            cartDTO = cartService.updateCart(user,session.getId(), cartDTO);
        }
        return ResponseEntity.ok()
                .body(cartDTO);

    }

    @PostMapping("/proceed")
    public ResponseEntity<OrderDTO> proceedToOrder(@RequestBody OrderDTO orderDTO,
                               @AuthenticationPrincipal User authUser,
                               HttpSession session
    ) {
        if (authUser == null){
            orderDTO = orderService.processOrder(null,session.getId(), orderDTO);

        } else {
            com.codegym.cgzgearservice.entitiy.user.User user = userRepository.findUserByUsername(authUser.getUsername());
            orderDTO = orderService.processOrder(user,session.getId(), orderDTO);
        }
        return ResponseEntity.ok()
                .body(orderDTO);
    }

}
