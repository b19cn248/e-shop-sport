package com.shopsport.server.repository;

import com.shopsport.common.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

  @Query(value = "SELECT sum(op.moneyDeducted) from OrderPromotion op" +
        " WHERE op.lastUpdatedAt BETWEEN :from AND :to")
  Double getMoneyDeductedOfPromotion(Long from, Long to);
}
