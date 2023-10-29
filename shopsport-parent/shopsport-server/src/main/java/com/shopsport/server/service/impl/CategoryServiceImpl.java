package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Category;
import com.shopsport.server.repository.CategoryRepository;
import com.shopsport.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;

  @Override
  public List<Category> list() {
    log.info("List All Categories");
    return listCategoriesUsedInForm();
  }

  @Override
  public List<Category> listCategoriesUsedInForm() {
    log.info("List Categories Used in Form");

    List<Category> categoriesUsedInForm = new ArrayList<>();

    for (Category category : repository.getRootCategories()) {

      categoriesUsedInForm.add(category);
      addChildren(categoriesUsedInForm, category, 0);
    }

    return categoriesUsedInForm;
  }

  @Override
  public Category save(Category category) {
    log.info("(save) category:{}", category);
    return repository.save(category);
  }

  @Override
  public void remove(Integer id) {
    log.info("(delete) id:{}", id);
    repository.delete(id);
  }

  @Override
  public Category get(Integer id) {
    log.info("(get) id:{}", id);
    return repository.findById(id).orElseThrow();
  }

  private void addChildren(List<Category> categories, Category parent, int subLevel) {
    int newSubLevel = subLevel + 1;
    for (Category subCategory : parent.getChildren()) {
      if (!subCategory.isDeleted()) {
        subCategory.setName(createIndentedName(subCategory.getName(), newSubLevel));
        categories.add(subCategory);
      }
      addChildren(categories, subCategory, newSubLevel);
    }
  }

  private String createIndentedName(String name, int subLevel) {
    return "--".repeat(subLevel) + name;
  }
}
