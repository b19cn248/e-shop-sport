package com.shopsport.client.service;

import com.shopsport.common.entity.Order;

import java.util.List;

public interface OrderService {

  void createOrder();

  List<Order> getOrder();
}
