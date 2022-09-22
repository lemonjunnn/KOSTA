package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class B {
  @Id
  @SequenceGenerator(name = "seq_post")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post")
  private int seq;
  private String title;
  @ManyToOne
  @JoinColumn(name = "m_id")
  private M m;
}
