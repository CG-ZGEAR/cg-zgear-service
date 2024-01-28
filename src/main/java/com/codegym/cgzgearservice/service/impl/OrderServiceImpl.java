package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.constants.OrderStatus;
import com.codegym.cgzgearservice.dto.OrderDTO;
import com.codegym.cgzgearservice.dto.OrderItemDTO;
import com.codegym.cgzgearservice.entitiy.product.Order;
import com.codegym.cgzgearservice.entitiy.product.OrderItem;
import com.codegym.cgzgearservice.entitiy.user.Address;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.OrderRepository;
import com.codegym.cgzgearservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDTO processOrder(User user, String sessionId, OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setStatus(OrderStatus.PENDING);
        order.setDateCreated(LocalDateTime.now().toString());

        Address address = user.getAddress().stream()
                .filter(a -> a.getId().equals(orderDTO.getAddressId()))
                .findFirst()
                .orElse(null);
        order.setAddress(address);
        order.setTotal(orderDTO.getTotal());

        List<OrderItem> orderItems = orderDTO.getItems().stream()
                .map(itemDTO -> modelMapper.map(itemDTO, OrderItem.class))
                .collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }
}
