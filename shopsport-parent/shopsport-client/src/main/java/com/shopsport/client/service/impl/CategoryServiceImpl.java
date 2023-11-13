package com.shopsport.client.service.impl;

import com.shopsport.client.repository.CategoryRepository;
import com.shopsport.client.service.CategoryService;
import com.shopsport.common.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;

  @Override
  public List<Category> listAll() {

    log.info("(Lis all Categories)");
    return repository.findAll();
  }
}
