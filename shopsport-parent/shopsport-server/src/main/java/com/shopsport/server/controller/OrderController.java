package com.shopsport.server.controller;


import com.shopsport.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public String viewOrders(Model model) {
    model.addAttribute("orders", orderService.listAll());

    return "orders/orders";
  }
}
