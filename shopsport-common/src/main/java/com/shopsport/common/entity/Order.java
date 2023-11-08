package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntityWithUpdater {
  private String code;
  private Double totalMoney;
  private Double moneyPaid;
  private Integer status;
}
