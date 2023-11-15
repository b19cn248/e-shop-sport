package com.shopsport.client.repository;

import com.shopsport.common.entity.CartItem;
import com.shopsport.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

  @Query(value = "select count(*) from CartItem ci where ci.customer.id = :customerId")
  Integer getNumberOfProduct(Integer customerId);

  @Query(value = "select ci.product from CartItem ci where ci.customer.id = :customerId")
  List<Product> findByCustomer(Integer customerId);

  @Query(value = "select ci from CartItem ci where ci.customer.id = :customerId")
  List<CartItem> listAll(Integer customerId);
}
