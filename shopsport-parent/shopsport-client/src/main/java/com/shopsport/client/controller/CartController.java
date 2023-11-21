package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CartController {
  private final CartItemService cartItemService;

  @GetMapping("/carts")
  public String cartView(Model model) {
    model.addAttribute("cartItems", cartItemService.listAll());
    model.addAttribute("totalMoney", cartItemService.getTotalMoney());
    model.addAttribute("numberOfProducts", cartItemService.getNumberOfProduct());
    return "cart";
  }

  @GetMapping("/carts/add/{id}")
  public String addToCart(@PathVariable Integer id) {
    cartItemService.addToCart(id);
    return "redirect:/carts";
  }
}
