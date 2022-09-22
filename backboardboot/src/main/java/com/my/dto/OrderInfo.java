package com.my.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_info")
@SequenceGenerator(name = "seq_order_no", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderInfo {
  // 단방향 관계용 코드
  // @Id
  // @Column(name = "order_info_no")
  // private Long orderNo;
  // @Column(name = "order_id")
  // private String orderId;
  // @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
  // @CreationTimestamp
  // @Column(name = "order_date")
  // private Date orderDate;
  // @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  // @JoinColumn(name="order_line_no")
  // private List<OrderLine> orderLines; // OrderInfo has an OrderLine relationship
  // 양방향 관계용 코드
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_no")
  @Column(name = "order_info_no")
  private Long orderNo;
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  @Column(name = "order_date")
  private Date orderDate;
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Customer customer;
  @OneToMany(mappedBy = "orderInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  // mappedBy 속성 : owner(일반적으로 자식 클래스)를 기준으로 매핑하겠다
  private List<OrderLine> orderLines; // OrderInfo has an OrderLine relationship
}
