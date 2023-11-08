package com.shopsport.client.repository;

import com.shopsport.common.entity.OrderPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, Integer> {
}
