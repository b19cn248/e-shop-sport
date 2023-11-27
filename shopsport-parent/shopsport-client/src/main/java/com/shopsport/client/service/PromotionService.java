package com.shopsport.client.service;

import com.shopsport.common.entity.Promotion;

import java.util.List;

public interface PromotionService {
  Double getDiscountPercent();

  List<Promotion> listPromotions();
}
