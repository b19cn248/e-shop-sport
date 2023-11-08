package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntityWithUpdater {

  private String code;
  private String name;
  private String unit;
  private String origin;
  private String description;
  private Integer importPrice;
  private Integer exportPrice;
  private Integer quantity;
  private String image;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  public Product(Integer id, String code, String name, String unit, String origin, String description, Integer importPrice, Integer exportPrice, Integer quantity) {
    super.setId(id);
    this.code = code;
    this.name = name;
    this.unit = unit;
    this.origin = origin;
    this.description = description;
    this.importPrice = importPrice;
    this.exportPrice = exportPrice;
    this.quantity = quantity;
  }

  @Transient
  public String getImagePath() {
    return "/images/" + this.image;
  }

  @Transient
  public String getDesc() {
    return this.getDescription() + " o " + this.warehouse.getDescription();
  }
}
