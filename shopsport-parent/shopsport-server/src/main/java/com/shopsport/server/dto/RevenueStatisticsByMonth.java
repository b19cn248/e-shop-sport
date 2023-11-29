package com.shopsport.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatisticsByMonth {
  private Integer month;
  private Double totalMoney;
}
