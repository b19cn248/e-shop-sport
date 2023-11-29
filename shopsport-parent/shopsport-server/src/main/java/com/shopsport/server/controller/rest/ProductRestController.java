package com.shopsport.server.controller.rest;

import com.shopsport.server.dto.ProductResponse;
import com.shopsport.server.dto.RevenueStatisticsByMonth;
import com.shopsport.server.dto.TopProductResponse;
import com.shopsport.server.service.OrderService;
import com.shopsport.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

  private final ProductService service;
  private final OrderService orderService;

  @PostMapping("/products/check_unique")
  public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
    return service.checkUnique(id, name);
  }

  @GetMapping("/products/statics")
  public List<ProductResponse> statistics() {
    return service.statisticsByBrand();
  }

  @GetMapping("/products/top")
  public List<TopProductResponse> topProduct() {
    return orderService.getTopProduct();
  }

  @GetMapping("/products/revenue")
  public List<RevenueStatisticsByMonth> revenue() {
    return service.statisticsByMonth();
  }
}