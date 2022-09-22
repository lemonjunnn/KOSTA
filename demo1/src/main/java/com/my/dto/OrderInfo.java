package com.my.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class OrderInfo {
  private int orderNo;
  private String orderId;

  @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
  private Date orderDate;
  private List<OrderLine> orderLines; // OrderInfo has an OrderLine relationship
}
