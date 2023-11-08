package com.shopsport.client.repository;

import com.shopsport.common.entity.ProductPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, Integer> {
}
