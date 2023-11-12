package com.shopsport.server.controller.rest;

import com.shopsport.common.entity.Brand;
import com.shopsport.common.entity.Category;
import com.shopsport.server.dto.CategoryDTO;
import com.shopsport.server.exception.brand.BrandNotFoundException;
import com.shopsport.server.exception.brand.BrandNotFoundRestException;
import com.shopsport.server.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BrandRestController {

  private final BrandService brandService;

  @PostMapping("/brands/check_unique")
  public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
    return brandService.checkUnique(id, name);
  }

  @GetMapping("/brands/{id}/categories")
  public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {
    List<CategoryDTO> listCategories = new ArrayList<>();

    try {
      Brand brand = brandService.get(brandId);
      Set<Category> categories = brand.getCategories();

      for (Category category : categories) {
        CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
        listCategories.add(dto);
      }

      return listCategories;
    } catch (BrandNotFoundException e) {
      throw new BrandNotFoundRestException();
    }
  }
}
