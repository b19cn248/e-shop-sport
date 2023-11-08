package com.shopsport.server.repository;

import com.shopsport.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ProductRepositoryTests {

  @Autowired
  private ProductRepository productRepository;

  @Test
  void createProducts() {
    Product product1 = new Product(1, "P001", "Laptop Dell", "Piece", "W01", "Dell Gaming Laptop", 1000, 1200, 50);
    Product product2 = new Product(2, "P002", "Smartphone Samsung", "Piece", "South Korea", "Samsung Galaxy S23", 800, 950, 30);
    Product product3 = new Product(3, "P003", "Headphones Sony", "Pair", "Japan", "Sony Noise Cancelling", 150, 180, 100);
    Product product4 = new Product(4, "P004", "Camera Canon", "Piece", "Japan", "Canon EOS 250D", 500, 600, 25);
    Product product5 = new Product(5, "P005", "Gaming Mouse", "Piece", "China", "RGB Gaming Mouse", 25, 40, 200);

    productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
  }

  @Test
  void createProduct() {
    Product product1 = new Product(6, "P006", "Mu dep vaizz lozzz",
          "Cai", "USA", "Mu nay di danh goft thi tuyet con me no voi", 1000, 1200, 50);

    productRepository.save(product1);
  }
}
