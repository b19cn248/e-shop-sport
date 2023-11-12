package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true, exclude = "product")
@ToString(callSuper = true, exclude = "product")
@Entity
@Table(name = "product_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail extends BaseEntityWithUpdater {

  private String name;
  private String value;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  public ProductDetail(Integer id, String name, String value, Product product) {
    super.setId(id);
    this.name = name;
    this.value = value;
    this.product = product;
  }
}
