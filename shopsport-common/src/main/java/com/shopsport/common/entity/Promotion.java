package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promotions")
public class Promotion extends BaseEntityWithUpdater {

  private String startTime;
  private String endTime;
  private int discountPercent;

  private double moneyDeducted;
}
