package com.shopsport.server.service;

import com.shopsport.common.entity.Category;

import java.util.List;

public interface CategoryService {
  List<Category> list();

  List<Category> listCategoriesUsedInForm();

  Category save(Category category);

  void remove(Integer id);

  Category get(Integer id);
}
