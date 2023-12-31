package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Order;
import com.shopsport.server.dto.TopProductResponse;
import com.shopsport.server.repository.OrderDetailRepository;
import com.shopsport.server.repository.OrderRepository;
import com.shopsport.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  private final OrderDetailRepository orderDetailRepository;

  @Override
  public List<Order> listAll() {
    return repository.findAll();
  }

  @Override
  public List<TopProductResponse> getTopProduct() {
    log.info("(getTopProduct)");
    return orderDetailRepository.findTopSellingProducts(PageRequest.of(0, 5));
  }


}
