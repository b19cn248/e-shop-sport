package com.shopsport.client.service.impl;

import com.shopsport.client.repository.PromotionRepository;
import com.shopsport.client.service.PromotionService;
import com.shopsport.client.utils.DateUtils;
import com.shopsport.common.entity.Promotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

  private final PromotionRepository repository;

  @Override
  public Double getDiscountPercent() {

    log.info("(getDisCountPercent)");

    List<Promotion> activePromotions = repository.findActivePromotions(DateUtils.getDateNow());

    return activePromotions.stream()
          .mapToDouble(Promotion::getDiscountPercent)
          .sum();
  }

  @Override
  public List<Promotion> listPromotions() {
    log.info("list promotions");
    return repository.findActivePromotions(DateUtils.getDateNow());
  }

}
