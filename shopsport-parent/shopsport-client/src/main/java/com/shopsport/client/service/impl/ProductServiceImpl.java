package com.shopsport.client.service.impl;

import com.shopsport.client.repository.ProductRepository;
import com.shopsport.client.service.ProductService;
import com.shopsport.common.entity.Product;
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

  @Override
  public Product get(Integer id) {
    log.info("(get) id:{}", id);
    return repository.findById(id).orElseThrow();
  }
}
