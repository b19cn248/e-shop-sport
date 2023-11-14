package com.shopsport.client.repository;

import com.shopsport.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Optional<Customer> findByEmail(String email);
}
