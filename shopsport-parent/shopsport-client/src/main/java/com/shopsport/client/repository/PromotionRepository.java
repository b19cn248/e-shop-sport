package com.shopsport.client.repository;

import com.shopsport.common.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

  @Query("SELECT p FROM Promotion p WHERE p.startTime <= :currentDateStr AND p.endTime >= :currentDateStr")
  List<Promotion> findActivePromotions(String currentDateStr);
}
