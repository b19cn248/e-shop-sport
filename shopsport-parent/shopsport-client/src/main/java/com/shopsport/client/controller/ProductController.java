package com.shopsport.client.controller;

import com.shopsport.client.service.BrandService;
import com.shopsport.client.service.CategoryService;
import com.shopsport.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  private final BrandService brandService;

  private final CategoryService categoryService;

  @GetMapping
  public String viewHomePage(Model model) {
    model.addAttribute("products", productService.list());
    model.addAttribute("brands", brandService.listAll());
    model.addAttribute("categories", categoryService.listAll());
    return "home";
  }

  @GetMapping("/detail/{id}")
  public String viewDetailProduct(Model model, @PathVariable Integer id) {
    model.addAttribute("product", productService.get(id));
    return "detail_product";
  }
}
