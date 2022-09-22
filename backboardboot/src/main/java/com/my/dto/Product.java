package com.my.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
  @Id
  @Column(name = "product_no")
  private String productNo;
  @Column(name = "product_name")
  private String productName;
  @Column(name = "product_price")
  private Long productPrice;
  @Column(name = "product_info")
  private String productInfo;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  @CreationTimestamp
  @Column(name = "product_mfd")
  private Date productMfd;
}
