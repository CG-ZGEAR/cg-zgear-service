package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.entitiy.product.Cart;
import com.codegym.cgzgearservice.entitiy.product.CartItem;
import com.codegym.cgzgearservice.entitiy.product.Product;
import com.codegym.cgzgearservice.entitiy.product.ProductDiscount;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.CartItemRepository;
import com.codegym.cgzgearservice.repository.CartRepository;
import com.codegym.cgzgearservice.repository.ProductRepository;
import com.codegym.cgzgearservice.service.CartService;
import com.codegym.cgzgearservice.service.ProductDiscountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductDiscountService productDiscountService;

    @Override
    public CartDTO addToCart(User user, Long productId, int quantity) {

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            Double price = getPriceForCartItem(productRepository.findProductByIdAndAvailableIsTrue(productId), existingItem.getQuantity());
            existingItem.setPrice(price);
        } else {
            CartItem cartItem = new CartItem();
            Product product = productRepository.findProductByIdAndAvailableIsTrue(productId);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(getPriceForCartItem(product, 1));
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return cartDTO;
    }

    private Double getPriceForCartItem(Product product, int quantity) {
        List<ProductDiscount> discounts = productDiscountService.getCurrentDiscountsForProduct(product.getId());
        Double price = product.getPrice();
        for (ProductDiscount productDiscount : discounts) {
            if (productDiscount.getDiscountType().equals("FIXED_AMOUNT")) {
                price = price - productDiscount.getDiscountAmount();
            } else {
                price = price - price * (productDiscount.getDiscountAmount() / 100);
            }
            return price * quantity;
        }
        return price;
    }

}
