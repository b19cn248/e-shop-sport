package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promotions")
@EqualsAndHashCode(callSuper = true)
public class Promotion extends BaseEntityWithUpdater {

  private String name;
  private String startTime;
  private String endTime;
  private Double discountPercent;
}
