package com.shopsport.client.service;

import com.shopsport.client.dto.ProductResponse;
import com.shopsport.common.entity.Product;

import java.util.List;

public interface ProductService {

  List<Product> list();

  Product get(Integer id);

  List<ProductResponse> findByBrandID(List<Integer> brandIDs);
}
