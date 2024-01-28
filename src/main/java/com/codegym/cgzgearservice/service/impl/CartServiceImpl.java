package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.CartItemDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductDiscountService productDiscountService;

    @Override
    public CartDTO addToCart(User user, String sessionId, Long productId, int quantity) {
        Cart cart = checkIfCartExist(user, sessionId);
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            Double price = getPriceForCartItem(existingItem);
            existingItem.setSubTotal(price);
        } else {
            CartItem cartItem = new CartItem();
            Product product = productRepository.findProductByIdAndAvailableIsTrue(productId);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setSubTotal(getPriceForCartItem(cartItem));
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return cartDTO;
    }

    @Override
    public CartDTO getCart(User user, String sessionId) {
        Cart cart = checkIfCartExist(user, sessionId);
        Double total=0.0;
        for (CartItem cartItem: cart.getCartItems()){
            total = getPriceForCartItem(cartItem);
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        for (CartItemDTO cartItemDTO : cartDTO.getCartItems()) {
            cartItemDTO.setProductImageUrl(productRepository.findProductByIdAndAvailableIsTrue(cartItemDTO.getProductId()).getProductImages().get(0).getUrl());
            Double productPrice = productRepository.findProductByIdAndAvailableIsTrue(cartItemDTO.getProductId()).getPrice();
            cartItemDTO.setProductPrice(productPrice);
            cartItemDTO.setSubTotal(productPrice*cartItemDTO.getQuantity());
        }
        cartDTO.setTotalPrice(total);
        return cartDTO;

    }

    @Override
    public void mergeCarts(String sessionId, User user) {
        Cart sessionCart = cartRepository.findCartBySessionId(sessionId);
        Cart userCart = cartRepository.findByUser(user);

        if (userCart == null || userCart.getCartItems().isEmpty()) {
            if (sessionCart != null) {
                Cart newUserCart = new Cart();
                newUserCart.setUser(user);
                for (CartItem item : sessionCart.getCartItems()) {
                    CartItem cartItem = new CartItem();
                    cartItem.setProduct(item.getProduct());
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setCart(newUserCart);
                    newUserCart.getCartItems().add(cartItem);
                }
                cartRepository.save(newUserCart);
                cartRepository.delete(sessionCart);

            }
        }
    }

    @Override
    public CartDTO updateCart(User user, String sessionId, CartDTO cartDTO) {
        Cart existingCart = checkIfCartExist(user, sessionId);
        if (existingCart != null) {
            List<CartItem> existingItems = existingCart.getCartItems();
            List<CartItemDTO> updatedItems = cartDTO.getCartItems();

            List<CartItem> itemsToRemove = existingItems.stream()
                    .filter(existingItem ->
                            updatedItems.stream().noneMatch(dto -> dto.getProductId().equals(existingItem.getProduct().getId())))
                    .collect(Collectors.toList());
            existingItems.removeAll(itemsToRemove);
            itemsToRemove.forEach(cartItemRepository::delete);
            if (existingItems.isEmpty()) {
                cartRepository.delete(existingCart);
                return new CartDTO();
            }
            for (CartItemDTO dto : updatedItems) {
                CartItem existingItem = existingItems.stream()
                        .filter(item -> item.getProduct().getId().equals(dto.getProductId()))
                        .findFirst()
                        .orElse(null);

                if (existingItem != null) {
                    existingItem.setQuantity(dto.getQuantity());
                } else {
                    CartItem newItem = modelMapper.map(dto, CartItem.class);
                    existingItems.add(newItem);
                }
            }

            existingCart.setCartItems(existingItems);
            cartRepository.save(existingCart);
            return getCart(user, sessionId);
        } else {            Cart newCart = modelMapper.map(cartDTO, Cart.class);
            if(user != null) {
                newCart.setUser(user);
            } else {
                newCart.setSessionId(sessionId);
            }
            Cart savedCart = cartRepository.save(newCart);
            return modelMapper.map(savedCart, CartDTO.class);
        }
    }

    private Cart checkIfCartExist(User user, String sessionId) {
        Cart cart;
        if (user == null) {
            cart = cartRepository.findCartBySessionId(sessionId);
            if (cart == null) {
                cart = new Cart();
                cart.setSessionId(sessionId);
                cartRepository.save(cart);
            }

        } else {
            cart = cartRepository.findByUser(user);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cartRepository.save(cart);
            }
        }
        return cart;
    }

    private Double getPriceForCartItem(CartItem cartItem) {

        List<ProductDiscount> discounts = productDiscountService.getCurrentDiscountsForProduct(cartItem.getProduct().getId());
        Double price = cartItem.getProduct().getPrice();
        for (ProductDiscount productDiscount : discounts) {
            if (productDiscount.getDiscountType().equals("FIXED_AMOUNT")) {
                price = price - productDiscount.getDiscountAmount();
            } else {
                price = price - price * (productDiscount.getDiscountAmount() / 100);
            }
            return price* cartItem.getQuantity();
        }
        return price*cartItem.getQuantity();
    }

}
