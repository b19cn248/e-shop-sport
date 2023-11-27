package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import com.shopsport.client.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
  private final CartItemService cartItemService;
  private final PromotionService promotionService;

  @GetMapping
  public String cartView(Model model) {
    model.addAttribute("cartItems", cartItemService.listAll());
    model.addAttribute("totalMoney", cartItemService.getTotalMoney());
    model.addAttribute("numberOfProducts", cartItemService.getNumberOfProduct());
    model.addAttribute("discountPercent", promotionService.getDiscountPercent());
    return "cart";
  }

  @GetMapping("/add/{id}")
  public String addToCart(@PathVariable Integer id) {
    cartItemService.addToCart(id);
    return "redirect:/carts";
  }
}
