package com.shopsport.server.controller;

import com.shopsport.common.entity.Brand;
import com.shopsport.common.entity.Product;
import com.shopsport.server.exception.product.ProductNotFoundException;
import com.shopsport.server.service.BrandService;
import com.shopsport.server.service.ProductService;
import com.shopsport.server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import static com.shopsport.server.constant.CommonConstants.ProductConstants.*;
import static com.shopsport.server.constant.CommonConstants.UserConstants.MESSAGE;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final BrandService brandService;

  @GetMapping("/products")
  public String viewPage(Model model) {
    model.addAttribute("products", productService.list());
    return "products/products";
  }

  @GetMapping("/products/new")
  public String newProduct(Model model) {

    Product product = new Product();
    product.setEnabled(true);
    product.setInStock(true);

    model.addAttribute("brands", brandService.list());
    model.addAttribute("pageTitle", "Create new product");
    model.addAttribute(PRODUCT, product);
    model.addAttribute("numberOfExistingExtraImages", 0);

    return "products/product_form";
  }

  @PostMapping("/products/save")
  public String saveProduct(
        Product product,
        RedirectAttributes ra,
        @RequestParam("fileImage") MultipartFile mainImageMultipart,
        @RequestParam("extraImage") MultipartFile[] extraImageMultipart,
        @RequestParam(name = "detailName", required = false) String[] detailNames,
        @RequestParam(name = "detailValue", required = false) String[] detailValues,
        @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
        @RequestParam(name = "imageNames", required = false) String[] imageNames,
        @RequestParam(name = "detailIDs", required = false) String[] detailIDs
  ) throws IOException {


    FileUploadUtil.setMainImageName(mainImageMultipart, product);
    FileUploadUtil.setExistingExtraImageNames(imageIDs, imageNames, product);
    FileUploadUtil.setNewExtraImageNames(extraImageMultipart, product);
    setProductDetails(detailIDs, detailNames, detailValues, product);

    Product savedProduct = productService.save(product);

    FileUploadUtil.saveUploadedImages(mainImageMultipart, extraImageMultipart, savedProduct);

    FileUploadUtil.deleteExtraImagesWereRemovedOnForm(product);

    ra.addFlashAttribute(MESSAGE, SAVE_PRODUCT_SUCCESSFULLY);

    return REDIRECT_PRODUCTS;
  }

  @GetMapping("/products/edit/{id}")
  public String editProduct(
        @PathVariable Integer id,
        Model model,
        RedirectAttributes ra
  ) {
    try {
      Product product = productService.get(id);
      List<Brand> brands = brandService.listAll();
      Integer numberOfExistingExtraImages = product.getImages().size();


      model.addAttribute(PRODUCT, product);
      model.addAttribute("brands", brands);
      model.addAttribute("pageTitle", "Edit product (ID: " + id + ")");
      model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);


      return "products/product_form";
    } catch (ProductNotFoundException e) {
      ra.addFlashAttribute(MESSAGE, e.getMessage());
      return REDIRECT_PRODUCTS;
    }
  }

  @GetMapping("/products/detail/{id}")
  public String viewProductDetail(
        @PathVariable Integer id,
        Model model,
        RedirectAttributes ra
  ) {
    try {
      Product product = productService.get(id);

      model.addAttribute(PRODUCT, product);

      return "products/product_detail_modal";
    } catch (ProductNotFoundException e) {
      ra.addFlashAttribute(MESSAGE, e.getMessage());
      return REDIRECT_PRODUCTS;
    }
  }

  private void setProductDetails(String[] detailIDs, String[] detailNames, String[] detailValues, Product product) {
    log.debug("(setProductDetails) detailIDS:{}, detailNames:{}, detailValues:{}, product:{}",
          detailIDs, detailNames, detailValues, product);

    if (detailNames == null || detailNames.length == 0) return;

    for (int count = 0; count < detailNames.length; count++) {
      String name = detailNames[count];
      String value = detailValues[count];
      Integer id = Integer.valueOf(detailIDs[count]);

      if (id != 0) {
        product.addDetail(id, name, value);
      } else if (!name.isEmpty() && !value.isEmpty()) {
        product.addDetail(name, value);
      }
    }
  }


}
