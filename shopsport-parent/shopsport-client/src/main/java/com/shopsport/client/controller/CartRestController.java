package com.shopsport.client.controller;

import com.shopsport.client.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartRestController {

  private final CartItemService cartItemService;

  @GetMapping("/add-to-cart/{id}")
  public String addtoCart(@PathVariable Integer id) {
    return cartItemService.addToCart(id);
  }
}
