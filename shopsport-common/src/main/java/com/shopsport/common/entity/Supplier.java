package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntityWithUpdater {

  private String name;
  private String description;
  private String address;
}
