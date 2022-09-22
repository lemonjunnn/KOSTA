package com.my.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.my.dto.Board;
import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.BoardRepository;

@Service
public class BoardService {
  private static final int CNT_PER_PAGE = 3;
  private static final int CNT_PER_PAGE_GROUP = 3;
  @Autowired
  private BoardRepository boardRepository;

  // 게시판페이지 보기 (그냥)
  public PageBean<Board> viewBoard(int currentPage) throws FindException {
    int endRow = currentPage * CNT_PER_PAGE;
    int startRow = endRow - CNT_PER_PAGE + 1;
    long totalCount = boardRepository.count();
    List<Board> boards = boardRepository.findByPage(startRow, endRow);
    PageBean<Board> pageBean =
        new PageBean<Board>(boards, totalCount, currentPage, CNT_PER_PAGE, CNT_PER_PAGE_GROUP);
    return pageBean;
  }

  // 게시판페이지 보기 (검색어별로)
  public PageBean<Board> searchBoard(String keyword, int currentPage) throws FindException {
    int endRow = currentPage * CNT_PER_PAGE;
    int startRow = endRow - CNT_PER_PAGE + 1;
    long totalCount = boardRepository.count();
    List<Board> boards = boardRepository.findByPageWithKeyword(keyword, startRow, endRow);
    PageBean<Board> pageBean =
        new PageBean<Board>(boards, totalCount, currentPage, CNT_PER_PAGE, CNT_PER_PAGE_GROUP);
    return pageBean;
  }

  // 글 쓰기
  public void writePost(Board board) throws AddException {
    board.setBoardParentNo(new Long(0));
    boardRepository.save(board);
  }

  // 답글 쓰기
  public void writeReply(Board board) throws AddException {
    if (board.getBoardParentNo() == 0) {
      throw new AddException("부모글 번호가 없습니다.");
    }
    boardRepository.save(board);
  }

  // 글 수정
  public void modifyPost(Board board) throws ModifyException {
    Optional<Board> boardOptional = boardRepository.findById(board.getBoardPostNo());
    if (!boardOptional.isPresent()) {
      throw new ModifyException("수정할 글이 없습니다.");
    }
    Board boardInRepository = boardOptional.get();
    String boardTitle = board.getBoardTitle();
    String boardContent = board.getBoardContent();
    boardInRepository.setBoardTitle(boardTitle);
    boardInRepository.setBoardContent(boardContent);
    boardRepository.save(boardInRepository);
  }

  // 글 삭제
  @Transactional(rollbackFor = DeleteException.class)
  public void deletePost(Long boardPostNo) throws DeleteException {
    boardRepository.deleteReply(boardPostNo);
    boardRepository.deleteById(boardPostNo);
  }

  // 글 보기
  public Board viewPost(Long boardPostNo) throws FindException {
    Optional<Board> boardOptional = boardRepository.findById(boardPostNo);
    if (boardOptional.isPresent()) {
      Board boardForview = boardOptional.get();
      boardForview.setBoardViewcount(boardForview.getBoardViewcount() + 1);
      boardRepository.save(boardForview);
    } else {
      throw new FindException("게시글이 없습니다");
    }

    Optional<Board> board = boardRepository.findById(boardPostNo);
    if (board.isPresent()) {
      return board.get();
    } else {
      throw new FindException("게시글이 없습니다");
    }
  }
}
