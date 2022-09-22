package com.my.dto;

import java.util.List;
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
public class PageBean<T> {
  private List<T> boards;
  private int currentPage;
  private int totalPage;
  private int startPage;
  private int endPage;
  private int cntPerPageGroup;

  /**
   * 
   * @param boards 페이지의 목록
   * @param totalRows DB에서 select할 수 있는 총 행수
   * @param currentPage 검색할페이지
   * @param cntPerPageGroup //페이지그룹별 보여줄 페이지수
   * @param cntPerPage //한페이지당 보여줄 목록수
   */
  public PageBean(List<T> boards, int totalRows, int currentPage, int cntPerPageGroup,
      int cntPerPage) {
    this.boards = boards;
    this.currentPage = currentPage;
    this.cntPerPageGroup = cntPerPageGroup;

    this.totalPage = (int) Math.ceil((double) totalRows / cntPerPage);

    if (this.currentPage > this.totalPage) {
      this.currentPage = this.totalPage;
    }

    this.endPage = (int) (Math.ceil((double) currentPage / cntPerPageGroup) * cntPerPageGroup);
    this.startPage = this.endPage - cntPerPageGroup + 1;
    if (this.totalPage < this.endPage) {
      this.endPage = this.totalPage;
    }
  }
}
