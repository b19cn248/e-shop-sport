package com.shopsport.server.controller;

import com.shopsport.common.entity.Category;
import com.shopsport.server.service.CategoryService;
import com.shopsport.server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

import static com.shopsport.server.constant.CommonConstants.CategoryConstants.*;
import static com.shopsport.server.constant.CommonConstants.UserConstants.MESSAGE;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

  private final CategoryService service;

  @GetMapping("/categories")
  public String listAll(Model model) {
    model.addAttribute("listCategories", service.list());

    return "categories/categories";
  }

  @GetMapping("/categories/new")
  public String newCategory(Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("pageTitle", CATEGORY_PAGE_TITLE);
    model.addAttribute("categories", service.listCategoriesUsedInForm());
    return CATEGORY_FORM;
  }

  @PostMapping("/categories/save")
  public String saveCategory(Category category,
                             @RequestParam("fileImage") MultipartFile multipartFile,
                             RedirectAttributes ra) throws IOException {

    if (!multipartFile.isEmpty()) {
      String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      category.setImage(fileName);

      Category savedCategory = service.save(category);
      String uploadDir = CATEGORIES_IMAGES + savedCategory.getId();
      FileUploadUtil.cleanDir(uploadDir);
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    } else {
      if (category.getImage().isEmpty()) category.setImage(null);
      service.save(category);
    }


    ra.addFlashAttribute(MESSAGE, SAVED_CATEGORY_SUCCESSFULLY);
    return CATEGORY_URL;
  }

  @GetMapping("/categories/edit/{id}")
  public String category(
        @PathVariable Integer id,
        Model model
  ) {
    Category category = service.get(id);
    log.info("category : {}", category);
    model.addAttribute("category", category);

    model.addAttribute("categories", service.listCategoriesUsedInForm());

    log.info("category : {}", category);
    return "categories/category_form";

  }

  @GetMapping("/categories/delete/{id}")
  public String save(@PathVariable Integer id, Model model) {

    model.addAttribute(MESSAGE, "Delete category successfully");
    service.remove(id);
    return CATEGORY_URL;
  }

}