package com.shopsport.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
  private Integer id;
  private String name;
  private Double importPrice;
  private Double exportPrice;
  private Double disCount;
  private Integer quantity;
  private String mainImage;

  public ProductResponse(Integer id, String name, Double importPrice, Double exportPrice, Double disCount, Integer quantity, String mainImage) {
    this.id = id;
    this.name = name;
    this.importPrice = importPrice;
    this.exportPrice = exportPrice;
    this.disCount = disCount;
    this.quantity = quantity;
    this.mainImage = mainImage;
  }
}
