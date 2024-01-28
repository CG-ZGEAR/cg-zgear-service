package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.CartDTO;
import com.codegym.cgzgearservice.dto.OrderDTO;
import com.codegym.cgzgearservice.entitiy.user.User;

public interface OrderService {
    OrderDTO processOrder(User user, String sessionId, OrderDTO orderDTO);

}
