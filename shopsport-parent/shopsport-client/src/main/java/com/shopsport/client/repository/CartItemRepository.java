package com.shopsport.client.repository;

import com.shopsport.common.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
