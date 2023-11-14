package com.shopsport.client.repository;

import com.shopsport.common.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

  @Query(value = "select count(*) from CartItem ci where ci.customer.id = :customerId")
  Integer getNumberOfProduct(Integer customerId);
}
