package com.shopsport.server.repository;

import com.shopsport.common.entity.Brand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {

  @Autowired
  private BrandRepository repository;

  @Test
  void testFindAll() {
    List<Brand> brands = repository.listAll();

    brands.forEach(System.out::println);
    Assertions.assertThat(brands).isNotEmpty();
  }
}
