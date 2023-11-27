package com.shopsport.server.repository;

import com.shopsport.common.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}
