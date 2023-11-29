package com.shopsport.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopProductResponse {
  private String name;
  private Long quantity;

  public TopProductResponse(String name, Long quantity) {
    this.name = name;
    this.quantity = quantity;
  }
}
