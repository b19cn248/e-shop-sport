package com.shopsport.server.controller;

import com.shopsport.common.entity.Brand;
import com.shopsport.common.entity.Product;
import com.shopsport.common.entity.ProductImage;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shopsport.server.constant.CommonConstants.ProductConstants.SAVE_PRODUCT_SUCCESSFULLY;
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
    model.addAttribute("product", product);
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

    System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");


    FileUploadUtil.setMainImageName(mainImageMultipart, product);
    setExistingExtraImageNames(imageIDs, imageNames, product);
    FileUploadUtil.setNewExtraImageNames(extraImageMultipart, product);
    setProductDetails(detailIDs, detailNames, detailValues, product);


    Product savedProduct = productService.save(product);

    FileUploadUtil.saveUploadedImages(mainImageMultipart, extraImageMultipart, savedProduct);

    deleteExtraImagesWereRemovedOnForm(product);

    ra.addFlashAttribute(MESSAGE, SAVE_PRODUCT_SUCCESSFULLY);

    return "redirect:/products";
  }

  private void deleteExtraImagesWereRemovedOnForm(Product product) {

    String extraImageDir = "product-images/" + product.getId() + "/extras";

    Path dirPath = Paths.get(extraImageDir);

    try {
      Files.list(dirPath).forEach(file -> {
        String fileName = file.toFile().getName();
        if (!product.containsImageName(fileName)) {
          try {
            Files.delete(file);
            log.info("Deleted extra image: " + fileName);
          } catch (IOException e) {
            log.error("Could not delete extra image: " + fileName);
          }
        }
      });
    } catch (IOException e) {
      log.error("Could not list directory: " + dirPath);
    }

  }

  private void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, Product product) {

    if (imageIDs == null || imageIDs.length == 0) return;

    Set<ProductImage> images = new HashSet<>();

    for (int i = 0; i < imageIDs.length; i++) {
      Integer id = Integer.parseInt(imageIDs[i]);
      String name = imageNames[i];
      images.add(new ProductImage(id, name, product));
    }

    product.setImages(images);

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


      model.addAttribute("product", product);
      model.addAttribute("brands", brands);
      model.addAttribute("pageTitle", "Edit product (ID: " + id + ")");
      model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);


      return "products/product_form";
    } catch (ProductNotFoundException e) {
      ra.addFlashAttribute(MESSAGE, e.getMessage());
      return "redirect:/products";
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

      model.addAttribute("product", product);

      return "products/product_detail_modal";
    } catch (ProductNotFoundException e) {
      ra.addFlashAttribute(MESSAGE, e.getMessage());
      return "redirect:/products";
    }
  }

  private void setProductDetails(String[] detailIDs, String[] detailNames, String[] detailValues, Product product) {
    log.debug("(setProductDetails) detailNames:{}, detailValues:{}, product:{}", detailNames, detailValues, product);
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
