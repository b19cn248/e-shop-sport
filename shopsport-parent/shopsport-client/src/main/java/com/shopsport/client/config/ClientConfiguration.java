package com.shopsport.client.config;

import com.shopsport.client.repository.*;
import com.shopsport.client.service.*;
import com.shopsport.client.service.impl.*;
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

  @Bean
  public CartItemService cartItemService(CartItemRepository cartItemRepository, CustomerRepository customerRepository,
                                         ProductRepository productRepository) {
    return new CartItemServiceImpl(cartItemRepository, customerRepository, productRepository);
  }

  @Bean
  public OrderService orderService(
        OrderRepository orderRepository,
        CustomerRepository customerRepository,
        CartItemRepository cartItemRepository,
        OrderDetailRepository orderDetailRepository,
        PromotionRepository promotionRepository,
        OrderPromotionRepository orderPromotionRepository
  ) {
    return new OrderServiceImpl(
          orderRepository,
          customerRepository,
          cartItemRepository,
          orderDetailRepository,
          promotionRepository,
          orderPromotionRepository
    );
  }
}
