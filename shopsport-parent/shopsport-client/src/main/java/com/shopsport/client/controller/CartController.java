package com.shopsport.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

  @GetMapping("/carts")
  public String cartView() {
    return "cart";
  }
}
