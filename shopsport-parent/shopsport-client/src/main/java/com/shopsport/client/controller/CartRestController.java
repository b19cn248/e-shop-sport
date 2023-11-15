package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartRestController {

  private final CartItemService cartItemService;

  @GetMapping("/add-to-cart/{id}")
  public String addToCart(@PathVariable Integer id) {
    return cartItemService.addToCart(id);
  }

  @GetMapping("/carts/{id}")
  public String removeItem(@PathVariable Integer id) {
    cartItemService.remove(id);
    return "success";
  }

  @GetMapping("/carts/update/{id}")
  public String updateQuantity(
        @PathVariable Integer id,
        @RequestParam Integer quantity
  ) {
    cartItemService.updateQuantity(id, quantity);
    return "success";
  }
}
