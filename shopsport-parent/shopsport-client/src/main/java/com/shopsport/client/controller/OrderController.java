package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final CartItemService cartItemService;

  @GetMapping("/checkout")
  public String checkoutView(Model model) {
    model.addAttribute("cartItems", cartItemService.listAll());
    model.addAttribute("totalMoney", cartItemService.getTotalMoney());
    return "checkout";
  }
}
