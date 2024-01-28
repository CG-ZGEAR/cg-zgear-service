package com.codegym.cgzgearservice.entitiy.product;

import com.codegym.cgzgearservice.dto.CartItemDTO;
import com.codegym.cgzgearservice.entitiy.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "carts")
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<CartItem> cartItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name="session_id")
    private String sessionId;

    @Column(name = "total")
    Double total;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public double getTotal() {
        this.total=0D;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getSubTotal();
        }
        return total;
    }

}
