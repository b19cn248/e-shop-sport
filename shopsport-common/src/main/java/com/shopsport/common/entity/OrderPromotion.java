package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPromotion extends BaseEntityWithUpdater {

  private Double moneyDeducted;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "promotion_id")
  private Promotion promotion;
}
