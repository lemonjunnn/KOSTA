package com.my.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.dto.Board;
import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.BoardRepository;

@Service()
public class BoardService {
  private static final int CNT_PER_PAGE = 3;
  private static final int CNT_PER_PAGE_GROUP = 3;
  @Autowired
  private BoardRepository boardRepository;

  // 게시판페이지 보기 (그냥)
  public PageBean<Board> viewBoard(int currentPage) throws FindException {
    int totalRows = boardRepository.selectTotalRowNumbersByPage();
    PageBean<Board> pageBean =
        new PageBean<Board>(null, totalRows, currentPage, CNT_PER_PAGE_GROUP, CNT_PER_PAGE);
    int currentPageInPageBean = pageBean.getCurrentPage();

    List<Board> boards =
        boardRepository.selectBoardPostsByPage(currentPageInPageBean, CNT_PER_PAGE);
    pageBean.setBoards(boards);
    return pageBean;
  }

  // 게시판페이지 보기 (검색어별로)
  public PageBean<Board> searchBoard(String keyword, int currentPage) throws FindException {
    int totalRows = boardRepository.selectTotalRowNumbersByKeyword(keyword);
    PageBean<Board> pageBean =
        new PageBean<Board>(null, totalRows, currentPage, CNT_PER_PAGE_GROUP, CNT_PER_PAGE);
    int currentPageInPageBean = pageBean.getCurrentPage();

    List<Board> boards =
        boardRepository.selectBoardPostsByKeyword(keyword, currentPageInPageBean, CNT_PER_PAGE);
    pageBean.setBoards(boards);
    return pageBean;
  }

  // 글 쓰기
  public void writePost(Board board) throws AddException {
    board.setBoardParentNo(0);
    boardRepository.insertBoardPost(board);
  }

  // 답글 쓰기
  public void writeReply(Board board) throws AddException {
    if (board.getBoardParentNo() == 0) {
      throw new AddException("부모글 번호가 없습니다.");
    }
    boardRepository.insertBoardPost(board);
  }

  // 글 수정
  public void modifyPost(Board board) throws ModifyException {
    boardRepository.updateBoardPost(board);
  }

  // 글 삭제
  public void deletePost(int boardPostNo) throws DeleteException {
    boardRepository.deleteBoardPost(boardPostNo);
  }

  // 글 보기
  public Board viewPost(int boardPostNo) throws FindException {
    Board board = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    return board;
  }

  // (+ 조회수 1증가)
  public void addViewcnt(int boardPostNo) throws ModifyException {
    Board board = new Board();
    board.setBoardPostNo(boardPostNo);
    board.setBoardViewcnt(-1);
    boardRepository.updateBoardPost(board);
  }

}
