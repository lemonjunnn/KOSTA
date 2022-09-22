package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.my.dto.Board;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;


@RunWith(SpringRunner.class)
// Spring 컨테이너용 XML파일 설정
@ContextConfiguration(
    locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardOracleRepositoryTest {
  private Logger logger = Logger.getLogger(getClass());

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private Board board;

  @Test
  public void testSelectTotalRowNumbersByPage() throws FindException {
    int realNumber = boardRepository.selectTotalRowNumbersByPage();
    int expectedNumber = 8;
    assertEquals(expectedNumber, realNumber);
  }

  @Test
  public void testSelectTotalRowNumbersByKeyword() throws FindException {
    String keyword = "1";
    int realNumber = boardRepository.selectTotalRowNumbersByKeyword(keyword);
    int expectedNumber = 3;
    assertEquals(expectedNumber, realNumber);
  }

  @Test
  public void testSelectBoardPostsByPage() throws FindException {
    int currentPage = 2;
    int cntPerPage = 3;
    int expectedSize = 3;
    int[] expectedBoardPostNoArr = {1, 4, 6};
    List<Board> boards = boardRepository.selectBoardPostsByPage(currentPage, cntPerPage);
    assertNotNull(boards);
    assertEquals(expectedSize, boards.size());

    for (int i = 0; i < boards.size(); i++) {
      assertEquals(expectedBoardPostNoArr[i], boards.get(i).getBoardPostNo());
    }
  }

  @Test
  public void testSelectBoardPostsByKeyword() throws FindException {
    int currentPage = 1;
    int cntPerPage = 3;
    String keyword = "1";
    int expectedSize = 3;
    int[] expectedBoardPostNoArr = {1, 4, 2};
    List<Board> boards =
        boardRepository.selectBoardPostsByKeyword(keyword, currentPage, cntPerPage);
    assertNotNull(boards);
    assertEquals(expectedSize, boards.size());

    for (int i = 0; i < boards.size(); i++) {
      assertEquals(expectedBoardPostNoArr[i], boards.get(i).getBoardPostNo());
    }
  }

  @Test
  public void testSelectBoardPostByBoardPostNo() throws FindException {
    int boardPostNo = 1;
    String expectedBoardTitle = "1번글";
    String expectedBoardContent = "new content";
    String expectedBoardId = "asd";
    int expectedBoardParentNo = 0;
    Board board = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(board);
    assertEquals(expectedBoardTitle, board.getBoardTitle());
    assertEquals(expectedBoardContent, board.getBoardContent());
    assertEquals(expectedBoardId, board.getBoardId());
    assertEquals(expectedBoardParentNo, board.getBoardParentNo());
  }

  @Test
  public void testUpdateBoardViewcnt() throws FindException, ModifyException {
    int boardPostNo = 1;
    Board board1 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(board1);
    int expectedBoardViewcnt = board1.getBoardViewcnt() + 1;

    Board board = new Board();
    board.setBoardPostNo(boardPostNo);
    board.setBoardViewcnt(-1);
    boardRepository.updateBoardPost(board);
    Board board2 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertEquals(expectedBoardViewcnt, board2.getBoardViewcnt());
  }

  @Test
  public void testUpdateBoardContent() throws FindException, ModifyException {
    int boardPostNo = 2;
    Board board1 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(board1);
    String beforeContent = board1.getBoardContent();
    int beforeViewcnt = board1.getBoardViewcnt();
    Board board = new Board();
    board.setBoardPostNo(boardPostNo);
    board.setBoardContent("new content2");
    boardRepository.updateBoardPost(board);
    Board board2 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotEquals(beforeContent, board2.getBoardContent());
    assertEquals(beforeViewcnt, board2.getBoardViewcnt());
  }

  @Test(expected = FindException.class)
  public void testDeleteBoardPost() throws FindException, DeleteException {
    int boardPostNo = 18;
    boardRepository.deleteBoardPost(boardPostNo);
    Board board1 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNull(board1);
  }

  @Test
  public void testInsertBoardPost() throws FindException, AddException {
    Board board = new Board();
    board.setBoardId("asd");
    board.setBoardParentNo(10);
    board.setBoardTitle("insert한 글");
    board.setBoardContent("insert한 글의 내용");
    boardRepository.insertBoardPost(board);
    int boardPostNo = board.getBoardPostNo();
    Board board1 = boardRepository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(board1);
    assertNotEquals(0, board.getBoardPostNo());
    assertEquals("insert한 글", board.getBoardTitle());
    assertEquals("insert한 글의 내용", board.getBoardContent());
  }
}
