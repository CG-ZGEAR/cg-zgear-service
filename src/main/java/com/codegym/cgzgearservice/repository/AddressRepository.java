package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
