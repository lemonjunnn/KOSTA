package com.my.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_line")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(OrderLinePk.class)
public class OrderLine {
  @Id
  @ManyToOne
  @JoinColumn(name = "order_line_no") // 자식의 foreign key 역할을 할 column name을 적는 어노테이션
  private OrderInfo orderInfo;
  @Id
  @ManyToOne
  @JoinColumn(name = "order_product_no", nullable = false)
  private Product orderProduct; // OrderLine has a Product relationship
  @Column(name = "order_quantity")
  private Long orderQuantity;
}

