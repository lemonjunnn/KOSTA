package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "a_table")
@DynamicInsert // null인 행은 insert하지 않음
@DynamicUpdate
@NoArgsConstructor
@Getter
@Setter
@ToString
public class A {
  @Id
  @Column(name = "a1_c", length = 13)
  private String a1;
  @Column(name = "a2_c", nullable = false, precision = 5, scale = 2)
  private BigDecimal a2;
  // @CreationTimestamp = entity가 table에 insert되는 시점의 날자데이터를 자동 기록함
  // @UpdateTimestamp = entity가 table에 update되는 시점의 날자데이터를 자동 기록함
  @CreationTimestamp
  @Column(name = "a3_c")
  private Date a3;
  // @ColumnDefault = 기본값 설정
  // @ColumnDefault(value = "hel")
  @Column(name = "a4_c", length = 10)
  private String a4 = "hel";
  // @Transient = 테이블에서 제외하고 싶은 field
  @Transient
  private String a5;
}
