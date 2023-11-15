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

import java.util.List;

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

    if (cartItemRepository.getByCustomerId(productId, customer.getId()).isEmpty()) {
      cartItemRepository.save(new CartItem(customer, product, 1));
    } else cartItemRepository.increaseQuantity(customer.getId(), productId);

    return "OK";
  }

  @Override
  public Integer getNumberOfProduct() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    return cartItemRepository.getNumberOfProduct(customer.getId());
  }


  @Override
  public List<Product> listByCustomer() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    List<Product> products = cartItemRepository.findByCustomer(customer.getId());

    System.out.println(products.size());

    System.out.println(products);

    return products;

  }

  @Override
  public List<CartItem> listAll() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    return cartItemRepository.listAll(customer.getId());
  }

  @Override
  @Transactional
  public void remove(Integer id) {
    cartItemRepository.deleteById(id);
  }

  @Override
  public void updateQuantity(Integer id, Integer quantity) {
    log.info("(updateQuantity) id:{}", id);
    cartItemRepository.updateQuantity(id, quantity);
  }

  @Override
  public Double getTotalMoney() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    return cartItemRepository.getTotalMoney(customer.getId());
  }

  @Override
  @Transactional
  public void removeCustomerCart() {
    log.info("(removeCustomerCart)");
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    cartItemRepository.removeCustomerCart(customer.getId());
  }
}
