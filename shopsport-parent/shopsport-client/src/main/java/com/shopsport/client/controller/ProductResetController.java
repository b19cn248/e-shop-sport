package com.shopsport.client.controller;

import com.shopsport.client.dto.ProductResponse;
import com.shopsport.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductResetController {

  private final ProductService productService;

  @GetMapping("/find-by-brand")
  public List<ProductResponse> viewByBrand(@RequestParam List<Integer> brandIDs) {
    return productService.findByBrandID(brandIDs);
  }
}
