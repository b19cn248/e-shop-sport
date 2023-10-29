package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
  private String warehouseId;
  private String origin;
  private String description;
  private Integer importPrice;
  private Integer exportPrice;
  private Integer quantity;

  public Product(Integer id, String code, String name, String unit, String warehouseId, String origin, String description, Integer importPrice, Integer exportPrice, Integer quantity) {
    super.setId(id);
    this.code = code;
    this.name = name;
    this.unit = unit;
    this.warehouseId = warehouseId;
    this.origin = origin;
    this.description = description;
    this.importPrice = importPrice;
    this.exportPrice = exportPrice;
    this.quantity = quantity;
  }
}
