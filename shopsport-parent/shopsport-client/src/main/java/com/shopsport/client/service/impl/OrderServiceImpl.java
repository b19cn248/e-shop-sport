package com.shopsport.client.service.impl;

import com.shopsport.client.repository.CartItemRepository;
import com.shopsport.client.repository.CustomerRepository;
import com.shopsport.client.repository.OrderDetailRepository;
import com.shopsport.client.repository.OrderRepository;
import com.shopsport.client.service.OrderService;
import com.shopsport.common.entity.CartItem;
import com.shopsport.common.entity.Customer;
import com.shopsport.common.entity.Order;
import com.shopsport.common.entity.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  private final CustomerRepository customerRepository;
  private final CartItemRepository cartItemRepository;
  private final OrderDetailRepository orderDetailRepository;

  @Override
  @Transactional
  public void createOrder() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    Order order = new Order(UUID.randomUUID().toString(), cartItemRepository.getTotalMoney(customer.getId()), null, 0, customer);

    List<CartItem> cartItems = cartItemRepository.listAll(customer.getId());


    List<OrderDetail> orderDetails = new ArrayList<>();

    for (CartItem item : cartItems) {
      orderDetails.add(new OrderDetail(item.getQuantity(), item.getProduct().getImportPrice(), item.getProduct().getExportPrice(),
            order, item.getProduct()));
    }

    repository.save(order);
    orderDetailRepository.saveAll(orderDetails);

    cartItemRepository.removeCustomerCart(customer.getId());

  }

  @Override
  public List<Order> getOrder() {
    log.info("(getOrder)");

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    return repository.list(customer.getId());
  }
}
