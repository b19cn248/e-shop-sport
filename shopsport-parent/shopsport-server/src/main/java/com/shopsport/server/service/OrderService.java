package com.shopsport.server.service;

import com.shopsport.common.entity.Order;
import com.shopsport.server.dto.TopProductResponse;

import java.util.List;

public interface OrderService {
  List<Order> listAll();

  List<TopProductResponse> getTopProduct();
}
