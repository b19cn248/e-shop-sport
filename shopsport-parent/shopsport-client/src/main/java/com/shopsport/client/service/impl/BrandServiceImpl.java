package com.shopsport.client.service.impl;

import com.shopsport.client.repository.BrandRepository;
import com.shopsport.client.service.BrandService;
import com.shopsport.common.entity.Brand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

  private final BrandRepository repository;

  @Override
  public List<Brand> listAll() {
    log.info("(List all brands)");

    return repository.findAll();
  }
}
