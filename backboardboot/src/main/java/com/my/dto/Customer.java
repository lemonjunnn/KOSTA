package com.my.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Customer {
  @Id
  @Column(name = "id", length = 15)
  private String id;
  @Column(name = "password", length = 12)
  private String password;
  @Column(name = "name")
  private String name;
  @Column(name = "address")
  private String address;
  @Column(name = "status")
  @ColumnDefault(value = "1")
  private Long status;
  @Column(name = "buildingno")
  @ColumnDefault(value = "1")
  private Long buildingno;
}
