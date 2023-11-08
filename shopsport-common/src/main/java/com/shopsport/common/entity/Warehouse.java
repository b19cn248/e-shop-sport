package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse extends BaseEntity{
  private String name;
  private String description;
}
