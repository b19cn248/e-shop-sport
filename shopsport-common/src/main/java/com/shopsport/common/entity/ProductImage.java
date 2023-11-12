package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true, exclude = "product")
@ToString(callSuper = true, exclude = "product")
@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage extends BaseEntityWithUpdater {

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Transient
  public String getImagePath() {
    return "/product-images/" + product.getId() + "/extras/" + this.name;
  }


  public ProductImage(Integer id, String name, Product product) {
    super.setId(id);
    this.name = name;
    this.product = product;
  }
}
