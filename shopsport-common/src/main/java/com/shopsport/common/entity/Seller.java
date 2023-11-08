package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends User {

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;
}
