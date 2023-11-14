package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CartController {
  private final CartItemService cartItemService;

  @GetMapping("/carts")
  public String cartView(Model model) {
    model.addAttribute("products", cartItemService.listByCustomer());
    return "cart";
  }
}
