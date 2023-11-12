package com.shopsport.server.controller.rest;

import com.shopsport.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

  private final ProductService service;
  @PostMapping("/products/check_unique")
  public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
    return service.checkUnique(id, name);
  }
}