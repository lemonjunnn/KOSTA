package com.my.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"orderNo"})
public class OrderLine {
  private int orderNo;
  private Product orderProduct; // OrderLine has a Product relationship
  private int orderQuantity;
}
