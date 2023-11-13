package com.shopsport.server.controller;

import com.shopsport.common.entity.Brand;
import com.shopsport.common.entity.Category;
import com.shopsport.server.exception.brand.BrandNotFoundException;
import com.shopsport.server.service.BrandService;
import com.shopsport.server.service.CategoryService;
import com.shopsport.server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BrandController {


  private final BrandService brandService;

  private final CategoryService categoryService;

  @GetMapping("/brands")
  public String listFirstPage(Model model) {
    return listByPage(1, model, "name", "asc", null);
  }

  @GetMapping("/brands/page/{pageNum}")
  public String listByPage(
        @PathVariable(name = "pageNum") int pageNum, Model model,
        @Param("sortField") String sortField, @Param("sortDir") String sortDir,
        @Param("keyword") String keyword
  ) {
    Page<Brand> page = brandService.listByPage(pageNum, sortField, sortDir, keyword);
    List<Brand> listBrands = page.getContent();

    long startCount = (pageNum - 1) * 10 + 1;
    long endCount = startCount + 10 - 1;
    if (endCount > page.getTotalElements()) {
      endCount = page.getTotalElements();
    }

    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

    model.addAttribute("currentPage", pageNum);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("startCount", startCount);
    model.addAttribute("endCount", endCount);
    model.addAttribute("totalItems", page.getTotalElements());
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", reverseSortDir);
    model.addAttribute("keyword", keyword);
    model.addAttribute("listBrands", listBrands);

    return "brands/brands";
  }

  @GetMapping("/brands/new")
  public String newBrand(Model model) {
    List<Category> listCategories = categoryService.listCategoriesUsedInForm();

    System.out.println(listCategories);

    model.addAttribute("listCategories", listCategories);
    Brand brand = new Brand();
    System.out.println(brand);
    model.addAttribute("brand", brand);
    model.addAttribute("pageTitle", "Create New Brand");

    return "brands/brand_form";
  }

  @PostMapping("/brands/save")
  public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
                          RedirectAttributes ra) throws IOException {

    System.out.println(brand.getCreatedBy());
    System.out.println(brand.getCreatedAt());

    if (!multipartFile.isEmpty()) {
      String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
      brand.setLogo(fileName);

      Brand savedBrand = brandService.save(brand);
      String uploadDir = "../brand-logos/" + savedBrand.getId();

      FileUploadUtil.cleanDir(uploadDir);
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

    } else {
      brandService.save(brand);
    }

    ra.addFlashAttribute("message", "The brand has been saved successfully.");
    return "redirect:/brands";
  }

  @GetMapping("/brands/edit/{id}")
  public String editBrand(@PathVariable(name = "id") Integer id, Model model,
                          RedirectAttributes ra) {
    try {
      Brand brand = brandService.get(id);
      System.out.println(brand.getCreatedAt());
      System.out.println(brand.getCreatedBy());
      List<Category> listCategories = categoryService.listCategoriesUsedInForm();

      model.addAttribute("brand", brand);
      model.addAttribute("listCategories", listCategories);
      model.addAttribute("pageTitle", "Edit Brand (ID: " + id + ")");

      return "brands/brand_form";
    } catch (BrandNotFoundException ex) {
      ra.addFlashAttribute("message", ex.getMessage());
      return "redirect:/brands";
    }
  }

  @GetMapping("/brands/delete/{id}")
  public String deleteBrand(@PathVariable(name = "id") Integer id,
                            Model model,
                            RedirectAttributes redirectAttributes) {
    try {
      brandService.delete(id);
      String brandDir = "../brand-logos/" + id;
      FileUploadUtil.removeDir(brandDir);

      redirectAttributes.addFlashAttribute("message",
            "The brand ID " + id + " has been deleted successfully");
    } catch (BrandNotFoundException ex) {
      redirectAttributes.addFlashAttribute("message", ex.getMessage());
    }

    return "redirect:/brands";
  }
}
