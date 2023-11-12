package com.shopsport.server.service;

import com.shopsport.common.entity.Product;
import com.shopsport.server.exception.product.ProductNotFoundException;

import java.util.List;

public interface ProductService {

  List<Product> list();

  Product save(Product product);

  String checkUnique(Integer id, String name);

  Product get(Integer id) throws ProductNotFoundException;
}
