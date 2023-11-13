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
    return listCategoriesUsedInTable();
  }

  @Override
  public Category save(Category category) {
    log.info("(save) category:{}", category);
    System.out.println(category.getCreatedAt());
    System.out.println(category.getCreatedBy());
    category.setCreatedAt(category.getCreatedAt());
    category.setCreatedBy(category.getLastUpdatedBy());
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
  public List<Category> listCategoriesUsedInTable() {
    log.info("List Categories Used in Table");

    List<Category> categoriesUsedInTable = new ArrayList<>();

    for (Category category : repository.getRootCategories()) {

      categoriesUsedInTable.add(category);
      addChildrenInTable(categoriesUsedInTable, category, 0);
    }

    return categoriesUsedInTable;
  }


  private void addChildren(List<Category> categories, Category parent, int subLevel) {
    int newSubLevel = subLevel + 1;
    for (Category subCategory : parent.getChildren()) {
      if (!subCategory.isDeleted()) {
        String name = createIndentedName(subCategory.getName(), newSubLevel);
        categories.add(Category.copyIdAndName(subCategory.getId(), name));
      }
      addChildren(categories, subCategory, newSubLevel);
    }
  }

  private void addChildrenInTable(List<Category> categories, Category parent, int subLevel) {
    int newSubLevel = subLevel + 1;
    for (Category subCategory : parent.getChildren()) {
      if (!subCategory.isDeleted()) {
        String name = createIndentedName(subCategory.getName(), newSubLevel);
        categories.add(Category.copyFull(subCategory, name));
      }
      addChildren(categories, subCategory, newSubLevel);
    }
  }

  private String createIndentedName(String name, int subLevel) {
    return "--".repeat(subLevel) + name;
  }
}
