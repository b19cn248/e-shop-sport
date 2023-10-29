package com.shopsport.server.controller;

import com.shopsport.common.entity.Category;
import com.shopsport.server.service.CategoryService;
import com.shopsport.server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
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

    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    category.setImage(fileName);

    Category savedCategory = service.save(category);
    System.out.println(savedCategory.getImage());
    System.out.println(category.getImagePath());
    String uploadDir = CATEGORIES_IMAGES + savedCategory.getId() ;
    FileUploadUtil.cleanDir(uploadDir);
    System.out.println(uploadDir);
    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

    ra.addFlashAttribute(MESSAGE, SAVED_CATEGORY_SUCCESSFULLY);
    return CATEGORY_URL;
  }

  @GetMapping("/categories/edit/{id}")
  public String category(
        @PathVariable Integer id,
        Model model
  ) {
    model.addAttribute("category", service.get(id));
    System.out.println(service.get(id).getName());
    model.addAttribute("categories", service.listCategoriesUsedInForm());
    return "categories/category_form";

  }

  @GetMapping("/categories/delete/{id}")
  public String save(@PathVariable Integer id, Model model) {

    model.addAttribute(MESSAGE, "Delete category successfully");

    service.remove(id);

    return CATEGORY_URL;
  }

}