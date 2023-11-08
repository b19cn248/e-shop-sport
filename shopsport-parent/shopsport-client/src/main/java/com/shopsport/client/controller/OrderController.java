package com.shopsport.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

  @GetMapping("/checkout")
  public String checkoutView() {
    return "checkout";
  }
}
