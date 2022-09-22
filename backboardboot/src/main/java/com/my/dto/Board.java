package com.my.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "board")
@SequenceGenerator(name = "seq_board_post_no", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"boardPostNo"})
@ToString // 서로 has a relation을 가진 경우, 무한루프가 발생할 수 있어서 가능하면 사용하지 말것
@DynamicInsert
@DynamicUpdate
public class Board {
  @Transient
  private int level;
  @Id
  @Column(name = "board_post_no")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_board_post_no")
  private Long boardPostNo;
  @Column(name = "board_parent_no")
  @ColumnDefault(value = "0")
  private Long boardParentNo;
  @Column(name = "board_title")
  private String boardTitle;
  @Column(name = "board_content")
  private String boardContent;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  @Column(name = "board_date")
  @CreationTimestamp
  private Date boardDate;
  @Column(name = "board_id")
  private String boardId;
  @Column(name = "board_viewcount")
  @ColumnDefault(value = "0")
  private Long boardViewcount;
}
