package com.shopsport.server.repository;

import com.shopsport.common.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CategoryRepositoryTests {

  @Autowired
  private CategoryRepository repository;

  @Test
  void testCreateRootCategory() {
    Category category = Category.builder().name("Giay").enabled(true).build();
    Category savedCategory = repository.save(category);

    Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
  }

  @Test
  void testCreateQuanAoCategory() {
    Category category = Category.builder().name("Quan Ao").enabled(true).build();
    Category savedCategory = repository.save(category);

    Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
  }

  @Test
  void testCreatePhuKienCategory() {
    Category category = Category.builder().name("Phu Kien").enabled(true).build();
    Category savedCategory = repository.save(category);

    Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
  }

  @Test
  void testCreateSubCategory() {
    Category parent = new Category(1);
    Category subCategory = Category.builder().name("Giay Da bong").parent(parent).build();

    Category savedCategory = repository.save(subCategory);

    Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
  }

  @Test
  void testCreateSub1Category() {
    Category parent = new Category(1);
    Category subCategory = Category.builder().name("Giay Chay Bo").parent(parent).build();

    Category savedCategory = repository.save(subCategory);

    Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);
  }

  @Test
  void testCreateSub2Category() {
    Category parent = new Category(2);
    Category subCategory1 = Category.builder().name("Quan the thao").parent(parent).build();
    Category subCategory2 = Category.builder().name("Ao the thao").parent(parent).build();

    repository.saveAll(List.of(subCategory1, subCategory2));

  }

  @Test
  void testCreateSub5Category() {
    Category parent = new Category(7);
    Category subCategory1 = Category.builder().name("Quan short").parent(parent).build();
    Category subCategory2 = Category.builder().name("Quan dai tap gym").parent(parent).build();
    Category subCategory3 = Category.builder().name("Quan yoga").parent(parent).build();

    repository.saveAll(List.of(subCategory1, subCategory2, subCategory3));

  }

  @Test
  void test() {
    Category parent = new Category(9);
    Category subCategory1 = Category.builder().name("Quan short nam").parent(parent).build();

    repository.save(subCategory1);
  }

  @Test
  void testGetCategory() {
    Category category = repository.findById(1).orElseThrow();
    System.out.println(category.getName());

    Set<Category> children = category.getChildren();

    for (Category subCategory : children) {
      System.out.println(subCategory.getName());
    }

  }

  @Test
  void testPrint() {
    Iterable<Category> categories = repository.findAll();

    for (Category category : categories) {
      if (category.getParent() == null) {
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();

        for (Category subCategory : children) {
          System.out.println("--" + subCategory.getName());
          printChildren(subCategory, 1);
        }
      }
    }
  }

  private void printChildren(Category parent, int subLevel) {
    int newSubLevel = subLevel + 1;

    Set<Category> children = parent.getChildren();

    for (Category subCategory : children) {
      for (int i = 0; i < newSubLevel; i++) {
        System.out.print("--");
      }
      System.out.println(subCategory.getName());
      printChildren(subCategory, newSubLevel);
    }
  }

}
