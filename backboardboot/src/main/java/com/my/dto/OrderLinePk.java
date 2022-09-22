package com.my.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OrderLinePk implements Serializable {

  private static final long serialVersionUID = 1L;
  private OrderInfo orderInfo;
  private Product orderProduct; // OrderLine has a Product relationship
}
