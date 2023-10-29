package com.shopsport.server.configuration;

import com.shopsport.server.repository.CategoryRepository;
import com.shopsport.server.repository.ProductRepository;
import com.shopsport.server.repository.RoleRepository;
import com.shopsport.server.repository.UserRepository;
import com.shopsport.server.service.CategoryService;
import com.shopsport.server.service.ProductService;
import com.shopsport.server.service.RoleService;
import com.shopsport.server.service.UserService;
import com.shopsport.server.service.impl.CategoryServiceImpl;
import com.shopsport.server.service.impl.ProductServiceImpl;
import com.shopsport.server.service.impl.RoleServiceImpl;
import com.shopsport.server.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServerConfiguration {

  @Bean
  public UserService userService(UserRepository repository, PasswordEncoder passwordEncoder) {
    return new UserServiceImpl(repository, passwordEncoder);
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
}
