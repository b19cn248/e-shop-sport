package com.shopsport.server.configuration;

import com.shopsport.server.repository.*;
import com.shopsport.server.service.*;
import com.shopsport.server.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServerConfiguration {

  @Bean
  public UserService userService(UserRepository repository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
    return new UserServiceImpl(repository, passwordEncoder, customerRepository);
  }

  @Bean
  public RoleService roleService(RoleRepository repository) {
    return new RoleServiceImpl(repository);
  }

  @Bean
  public ProductService productService(ProductRepository repository) {
    return new ProductServiceImpl(repository);
  }

  @Bean
  public CategoryService categoryService(CategoryRepository repository) {
    return new CategoryServiceImpl(repository);
  }

  @Bean
  public BrandService brandService(BrandRepository repository) {
    return new BrandServiceImpl(repository);
  }
}
