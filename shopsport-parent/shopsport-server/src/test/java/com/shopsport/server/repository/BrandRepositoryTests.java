package com.shopsport.server.repository;

import com.shopsport.common.entity.Brand;
import com.shopsport.common.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {

  @Autowired
  private BrandRepository repository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testFindAll() {
    List<Brand> brands = repository.listAll();

    brands.forEach(System.out::println);
    Assertions.assertThat(brands).isNotEmpty();
  }

  @Test
  void  addBrands() {
    Category football = categoryRepository.findById(1).orElseThrow();
    Category basketball = categoryRepository.findById(2).orElseThrow();
    Category tennis = categoryRepository.findById(3).orElseThrow();
    Category swimming = categoryRepository.findById(4).orElseThrow();

    List<Brand> brands = new ArrayList<>();

    Set<Category> nikeCategories = new HashSet<>(Arrays.asList(football, basketball, tennis));
    Brand nike = new Brand("Nike", nikeCategories);
    brands.add(nike);

    // Brand 2 - Adidas
    Set<Category> adidasCategories = new HashSet<>(Arrays.asList(football, basketball, swimming));
    Brand adidas = new Brand("Adidas", adidasCategories);
    brands.add(adidas);

    // Brand 3 - Puma
    Set<Category> pumaCategories = new HashSet<>(Arrays.asList(swimming, basketball, tennis));
    Brand puma = new Brand("Puma", pumaCategories);
    brands.add(puma);

    repository.saveAll(brands);
  }
}
