package com.shopsport.server.controller;

import com.shopsport.common.entity.Promotion;
import com.shopsport.server.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/promotions")
public class PromotionController {

  private final PromotionService promotionService;

  @GetMapping
  public String listAll(Model model) {
    model.addAttribute("promotions", promotionService.listAll());
    return "promotions/promotions";
  }

  @GetMapping("/new")
  public String create(Model model) {
    model.addAttribute("promotion", new Promotion());
    return "promotions/promotion_form";
  }

  @PostMapping("/save")
  public String save(Model model, Promotion promotion) {
    promotionService.save(promotion);
    return "redirect:/promotions";
  }
}
