package com.shopsport.client.service.impl;

import com.shopsport.client.repository.CartItemRepository;
import com.shopsport.client.repository.CustomerRepository;
import com.shopsport.client.repository.ProductRepository;
import com.shopsport.client.service.CartItemService;
import com.shopsport.common.entity.CartItem;
import com.shopsport.common.entity.Customer;
import com.shopsport.common.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public String addToCart(Integer productId) {

    log.info("(addToCart) productId:{}", productId);

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();
    Product product = productRepository.findById(productId).orElseThrow();

    cartItemRepository.save(new CartItem(customer, product));

    return "OK";
  }

  @Override
  public Integer getNumberOfProduct() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    return cartItemRepository.getNumberOfProduct(customer.getId());
  }
}
