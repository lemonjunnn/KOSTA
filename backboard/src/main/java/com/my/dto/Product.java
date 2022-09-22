package com.my.dto;

import java.util.Date;
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
@EqualsAndHashCode(of = {"productNo"})
public class Product {
  private String productNo;
  private String productName;
  private int productPrice;
  private String productInfo;
  @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
  private Date productMfd;
}
