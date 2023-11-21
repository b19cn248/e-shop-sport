package com.shopsport.client.repository;

import com.shopsport.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Query(value = "select o from Order o where o.customer.id = :customerId")
  List<Order> list(Integer customerId);
}
