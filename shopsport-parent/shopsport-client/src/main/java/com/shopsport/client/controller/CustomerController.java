package com.shopsport.client.controller;

import com.shopsport.client.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

  private final OrderService orderService;

  @GetMapping("/orders/create")
  public String viewProfile() {
    log.info("(viewProfile)");
    orderService.createOrder();
    return "redirect:/customers";
  }

  @GetMapping("/customers")
  public String view(Model model) {
    log.info("(view)");
    model.addAttribute("orders", orderService.getOrder());
    return "user_account";
  }
}
