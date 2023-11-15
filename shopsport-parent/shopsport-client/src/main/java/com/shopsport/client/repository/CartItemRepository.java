package com.shopsport.client.repository;

import com.shopsport.common.entity.CartItem;
import com.shopsport.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

  @Query(value = "select count(*) from CartItem ci where ci.customer.id = :customerId")
  Integer getNumberOfProduct(Integer customerId);

  @Query(value = "select ci.product from CartItem ci where ci.customer.id = :customerId")
  List<Product> findByCustomer(Integer customerId);

  @Query(value = "select ci from CartItem ci where ci.customer.id = :customerId")
  List<CartItem> listAll(Integer customerId);

  @Query(value = "select ci.product from CartItem ci where ci.product.id = :productId and ci.customer.id = :customerId")
  Optional<Product> getByCustomerId(Integer productId, Integer customerId);

  @Modifying
  @Transactional
  @Query(value = "update CartItem ci set ci.quantity = ci.quantity + 1 where ci.product.id = :productId" +
        " AND ci.customer.id = :customerId")
  void increaseQuantity(Integer customerId, Integer productId);

  @Modifying
  @Transactional
  @Query(value = "update CartItem ci set ci.quantity = :quantity where ci.id = :id")
  void updateQuantity(Integer id, Integer quantity);

  @Query(value = "select sum (ci.quantity * p.exportPrice * (1-p.disCount/100)) from CartItem ci" +
        " JOIN ci.product p WHERE ci.customer.id = :customerId")
  Double getTotalMoney(Integer customerId);

  @Query(value = "delete from CartItem ci where ci.customer.id = :customerId")
  @Modifying
  @Transactional
  void removeCustomerCart(Integer customerId);
}
