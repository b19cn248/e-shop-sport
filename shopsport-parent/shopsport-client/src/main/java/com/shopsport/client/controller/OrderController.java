package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import com.shopsport.client.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final CartItemService cartItemService;
  private final PromotionService promotionService;

  @GetMapping("/checkout")
  public String checkoutView(Model model) {
    model.addAttribute("cartItems", cartItemService.listAll());
    model.addAttribute("totalMoney", cartItemService.getTotalMoney());
    model.addAttribute("discountPercent", promotionService.getDiscountPercent());
    return "checkout";
  }
}
