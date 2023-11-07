package com.shopsport.client.controller;

import com.shopsport.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public String viewHomePage(Model model) {
    model.addAttribute("products", productService.list());
    return "products";
  }
}
