package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.CartItemDTO;
import com.codegym.cgzgearservice.dto.ProductDTO;
import com.codegym.cgzgearservice.entitiy.product.Cart;
import com.codegym.cgzgearservice.entitiy.product.CartItem;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.CartItemRepository;
import com.codegym.cgzgearservice.repository.CartRepository;
import com.codegym.cgzgearservice.repository.ProductRepository;
import com.codegym.cgzgearservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;
    @Override
    public CartDTO addToCart(User user, Long productId, int quantity) {

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(productRepository.findProductByIdAndAvailableIsTrue(productId));
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);

        }
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        return cartDTO;
    }

}
