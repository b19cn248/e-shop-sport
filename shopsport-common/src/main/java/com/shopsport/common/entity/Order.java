package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntityWithUpdater {
  private String code;
  private Double totalMoney;
  private Double moneyPaid;
  private Integer status;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderDetail> orderDetails = new ArrayList<>();

  public Order(String code, Double totalMoney, Double moneyPaid, Integer status, Customer customer) {
    this.code = code;
    this.totalMoney = totalMoney;
    this.moneyPaid = moneyPaid;
    this.status = status;
    this.customer = customer;
  }
}
