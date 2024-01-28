package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.constants.OrderStatus;
import com.codegym.cgzgearservice.dto.AddressDTO;
import com.codegym.cgzgearservice.dto.OrderDTO;
import com.codegym.cgzgearservice.dto.OrderItemDTO;
import com.codegym.cgzgearservice.entitiy.product.Order;
import com.codegym.cgzgearservice.entitiy.product.OrderItem;
import com.codegym.cgzgearservice.entitiy.user.Address;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.AddressRepository;
import com.codegym.cgzgearservice.repository.OrderRepository;
import com.codegym.cgzgearservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
    private final AddressRepository addressRepository;
    private final MailSender mailSender;
    @Override
    public OrderDTO processOrder(User user, String sessionId, OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setStatus(OrderStatus.PENDING);
        order.setDateCreated(LocalDateTime.now().toString());
        order.setTotal(orderDTO.getTotal());
        AddressDTO addressDTO = orderDTO.getAddressDTO();
        if (user != null) {
            order.setUser(user);
        }
        Address address;

        if (addressDTO.getId() != null) {
            address = addressRepository.findById(addressDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));
        } else {
            address = new Address();
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setDistrict(addressDTO.getDistrict());
            address.setWard(addressDTO.getWard());
            address.setUser(user);
            address.setIsDeleted(false);
            addressRepository.save(address);
        }
        order.setAddress(address);
        List<OrderItem> orderItems = orderDTO.getItems().stream()
                .map(itemDTO -> modelMapper.map(itemDTO, OrderItem.class))
                .collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);
        sendOrderConfirmationEmail(orderDTO.getCustomerEmail(), order);

        orderDTO.getAddressDTO().setId(address.getId());
        return orderDTO;
    }
    private void sendOrderConfirmationEmail(String customerEmail, Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tranhuutjnh@gmail.com");
            message.setTo(customerEmail);
            message.setSubject("Order Confirmation");
            message.setText("Thank you for your order! Your order has been confirmed." );
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

}
