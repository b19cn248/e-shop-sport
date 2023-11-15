package com.shopsport.client.controller;

import com.shopsport.client.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final OrderService orderService;

  @GetMapping
  public String viewProfile() {
    orderService.createOrder();
    return "user_account";
  }
}
