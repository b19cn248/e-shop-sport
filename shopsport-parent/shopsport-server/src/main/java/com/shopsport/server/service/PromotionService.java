package com.shopsport.server.service;

import com.shopsport.common.entity.Promotion;

import java.util.List;

public interface PromotionService {

  List<Promotion> listAll();

  void save(Promotion promotion);

  Promotion detail(Integer id);
}
