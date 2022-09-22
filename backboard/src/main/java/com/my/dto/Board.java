package com.my.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"boardPostNo"})
@ToString // 서로 has a relation을 가진 경우, 무한루프가 발생할 수 있어서 가능하면 사용하지 말것
public class Board {
  private int level;
  private int boardPostNo;
  private int boardParentNo;
  private String boardTitle;
  private String boardContent;
  @JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
  private Date boardDate;
  @NonNull
  private String boardId;
  private int boardViewcnt;
}
