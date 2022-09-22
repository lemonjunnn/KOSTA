package com.my.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString // 서로 has a relation을 가진 경우, 무한루프가 발생할 수 있어서 가능하면 사용하지 말것
public class ResultBean<T> {
  private int status = 0;
  private String message;
  private T t;
}
