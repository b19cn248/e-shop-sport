package com.shopsport.client.config;

import com.shopsport.client.repository.ProductRepository;
import com.shopsport.client.service.ProductService;
import com.shopsport.client.service.impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

  @Bean
  public ProductService productService(ProductRepository repository) {
    return new ProductServiceImpl(repository);
  }
}
