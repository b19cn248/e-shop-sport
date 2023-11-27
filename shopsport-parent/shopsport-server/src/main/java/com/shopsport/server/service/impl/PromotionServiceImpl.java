package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Promotion;
import com.shopsport.server.repository.PromotionRepository;
import com.shopsport.server.service.PromotionService;
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
  public List<Promotion> listAll() {
    log.info("(list All Promotions)");
    return repository.findAll();
  }
}
