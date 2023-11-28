package com.shopsport.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
  private String brandName;
  private Long quantity;

  public ProductResponse(String brandName, Long quantity) {
    this.brandName = brandName;
    this.quantity = quantity;
  }
}
