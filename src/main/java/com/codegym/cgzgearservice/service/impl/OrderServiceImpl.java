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
            String emailText = buildEmailText(order);
            message.setText(emailText);
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
    private String buildEmailText(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Dear %s,\n\n", order.getCustomerName()));
        sb.append("Thank you for your purchase at CodeGym Gear Service! We are preparing your order for shipment and will notify you when it is on its way.\n\n");
        sb.append(String.format("Order ID: %d\n", order.getId()));
        sb.append(String.format("Order Date: %s\n\n", order.getDateCreated())); // Assuming dateCreated is in a proper format. If not, use LocalDateTime.parse and format it.

        sb.append("Order Details:\n");
        String itemsSummary = order.getItems().stream()
                .map(item -> String.format("%d x %s - $%.2f", item.getQuantity(), item.getProduct().getProductName(), item.getSubTotal()))
                .collect(Collectors.joining("\n"));

        sb.append(itemsSummary);
        sb.append("\n\n");

        sb.append(String.format("Total: $%.2f\n\n", order.getTotal()));
        sb.append("Your order will be shipped to:\n");
        sb.append(String.format("%s\n\n", order.getAddress().toString()));
        sb.append("If you have any questions or need to make any changes to your order, please contact our customer service team at tranhuutjnh@gmail.com.\n\n");
        sb.append("Thank you for shopping with us.\n");
        sb.append("Best regards,\n");
        sb.append("The ZGear Service Team");

        return sb.toString();
    }

}
