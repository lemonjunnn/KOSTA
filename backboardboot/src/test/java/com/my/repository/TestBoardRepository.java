package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.my.dto.Board;

@SpringBootTest
class TestBoardRepository {
  @Autowired
  BoardRepository boardRepository;
  Logger logger = LoggerFactory.getLogger(getClass());

  // @Test
  void testWritePost() {
    Board board = new Board();
    board.setBoardTitle("title");
    board.setBoardContent("content");
    board.setBoardId("asd");
    boardRepository.save(board);
  }

  // @Test
  void testWriteReply() {
    Board board = new Board();
    board.setBoardParentNo(4L);
    board.setBoardTitle("reply");
    board.setBoardContent("content of reply");
    board.setBoardId("asd");
    boardRepository.save(board);
  }

  // @Test
  void testViewPost() {
    Long boardPostNo = new Long(1);
    Optional<Board> board = boardRepository.findById(boardPostNo);
    assertTrue(board.isPresent());
  }

  @Test
  void testModifyPost() {
    Long boardPostNo = new Long(9);
    Optional<Board> board = boardRepository.findById(boardPostNo);
    board.ifPresent((b) -> {
      b.setBoardTitle("new title");
      boardRepository.save(b);
    });
  }

  // @Test
  void testDeletePost() {
    Long boardPostNo = new Long(4);
    boardRepository.deleteReply(boardPostNo);
    boardRepository.deleteById(boardPostNo);
    assertFalse(boardRepository.findById(boardPostNo).isPresent());
  }

  // @Test
  // void testFindAllPage() {
  // int currentPage = 1;
  // Pageable pegable = PageRequest.of(currentPage - 1, 3);
  // List<Board> boards = boardRepository.findAll(pegable);
  // boards.forEach((b) -> {
  // logger.error(b.toString());
  // });
  // }
  // @Test
  void testFindByPage() {
    int currentPage = 1;
    int startRow = 1;
    int endRow = 3;
    List<Board> boards = boardRepository.findByPage(startRow, endRow);
    assertEquals(3, boards.size());
    boards.forEach((b) -> {
      logger.error(b.toString());
    });
  }

  // @Test
  void testFindByPageWithKeyword() {
    int currentPage = 1;
    int startRow = 1;
    int endRow = 3;
    String keyword = "title";
    List<Board> boards = boardRepository.findByPageWithKeyword(keyword, startRow, endRow);
    assertEquals(3, boards.size());
    boards.forEach((b) -> {
      logger.error(b.toString());
    });
  }

}
