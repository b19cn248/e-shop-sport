package com.shopsport.client.config;

import com.shopsport.client.repository.BrandRepository;
import com.shopsport.client.repository.CategoryRepository;
import com.shopsport.client.repository.ProductRepository;
import com.shopsport.client.service.BrandService;
import com.shopsport.client.service.CategoryService;
import com.shopsport.client.service.ProductService;
import com.shopsport.client.service.impl.BrandServiceImpl;
import com.shopsport.client.service.impl.CategoryServiceImpl;
import com.shopsport.client.service.impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

  @Bean
  public ProductService productService(ProductRepository repository) {
    return new ProductServiceImpl(repository);
  }

  @Bean
  public BrandService brandService(BrandRepository repository) {
    return new BrandServiceImpl(repository);
  }

  @Bean
  public CategoryService categoryService(CategoryRepository repository) {
    return new CategoryServiceImpl(repository);
  }
}
