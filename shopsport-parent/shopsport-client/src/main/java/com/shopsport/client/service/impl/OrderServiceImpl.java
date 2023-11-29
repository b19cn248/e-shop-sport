package com.shopsport.client.service.impl;

import com.shopsport.client.repository.*;
import com.shopsport.client.service.OrderService;
import com.shopsport.client.utils.DateUtils;
import com.shopsport.common.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  private final CustomerRepository customerRepository;
  private final CartItemRepository cartItemRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final PromotionRepository promotionRepository;
  private final OrderPromotionRepository orderPromotionRepository;

  @Override
  @Transactional
  public void createOrder() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Customer customer = customerRepository.findByEmail(username).orElseThrow();

    Order order = new Order(UUID.randomUUID().toString(), cartItemRepository.getTotalMoney(customer.getId()), 0.0, 0, customer);

    List<CartItem> cartItems = cartItemRepository.listAll(customer.getId());


    List<OrderDetail> orderDetails = cartItems.stream()
          .map(item -> new OrderDetail(item.getQuantity(), item.getProduct().getImportPrice(),
                item.getProduct().getExportPrice(), order, item.getProduct()))
          .toList();

    List<Promotion> promotions = promotionRepository.findActivePromotions(DateUtils.getDateNow());

    List<OrderPromotion> orderPromotions = promotions.stream()
          .map(promotion -> new OrderPromotion(order.getTotalMoney() * promotion.getDiscountPercent() / 100, order, promotion))
          .toList();

    repository.save(order);
    orderDetailRepository.saveAll(orderDetails);
    orderPromotionRepository.saveAll(orderPromotions);

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
