package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Product;
import com.shopsport.server.repository.ProductRepository;
import com.shopsport.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;

  @Override
  public List<Product> list() {
    log.info("(list all Product)");
    return repository.findAll();
  }
}
