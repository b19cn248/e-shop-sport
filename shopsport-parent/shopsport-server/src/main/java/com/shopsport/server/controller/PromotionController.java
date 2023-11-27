package com.shopsport.server.controller;

import com.shopsport.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/promotions")
public class PromotionController {

  private final OrderService orderService;

  @GetMapping
  public String viewPromotions(Model model) {
    model.addAttribute("promotions", orderService.listAll());
    return "promotions/promotions";
  }
}
