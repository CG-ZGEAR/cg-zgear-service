package com.codegym.cgzgearservice.entitiy.user;

import com.codegym.cgzgearservice.entitiy.product.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    @NotBlank
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    @NotBlank
    private String password;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private String date;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "avatar", length = 50)
    private String avatar;

    @Column(name = "activated", nullable = false, columnDefinition = "BIT default true")
    private boolean activated;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BIT default true")
    private boolean isDeleted;

    @Column(name = "otp_code", length = 6)
    private String otpCode;

    @Column(name = "otpexpiration")
    private LocalDateTime otpExpiration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Address> address;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Review> reviews;
}

