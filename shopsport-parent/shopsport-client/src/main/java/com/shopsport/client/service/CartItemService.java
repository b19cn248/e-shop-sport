package com.shopsport.client.service;

import com.shopsport.common.entity.CartItem;
import com.shopsport.common.entity.Product;

import java.util.List;

public interface CartItemService {

  String addToCart(Integer productId);

  Integer getNumberOfProduct();

  List<Product> listByCustomer();

  List<CartItem> listAll();

  void remove(Integer id);

  void updateQuantity(Integer id, Integer quantity);

  Double getTotalMoney();
}
