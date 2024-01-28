package com.codegym.cgzgearservice.entitiy.product;

import com.codegym.cgzgearservice.constants.OrderStatus;
import com.codegym.cgzgearservice.entitiy.user.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerEmail;

    @OneToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String orderDate;

    private String paymentDate;

    private Double total;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

}
