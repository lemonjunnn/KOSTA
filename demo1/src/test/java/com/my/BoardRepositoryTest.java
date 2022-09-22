package com.my;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.my.dto.Board;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.BoardRepository;

/*
 * Junit5버전은
 * 
 * @SpringBootTest에 @RunWith(SpringRunner.class)가 포함되어있고, public 메서드아니어도 됨
 */
@SpringBootTest
public class BoardRepositoryTest {
  @Autowired
  private BoardRepository repository;

  // @Test
  public void testSelectByPage() throws FindException {
    int currentPage = 1; // sample 행수 : 7건, 1페이지3건, 2페이지는3건, 3페이지는 1건
    int cntPerPage = 3;
    int expectedSize = 3;
    int[] expectedBoardNoArr = {3, 7, 1};
    List<Board> list = repository.selectBoardPostsByPage(currentPage, cntPerPage);
    assertNotNull(list);
    assertEquals(expectedSize, list.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(expectedBoardNoArr[i], list.get(i).getBoardPostNo());
    }
  }

  // @Test
  public void testSelectCount() throws FindException {
    int expectedCnt = 9;
    int cnt = repository.selectTotalRowNumbersByPage();
    assertEquals(expectedCnt, cnt);

    expectedCnt = 9;
    cnt = repository.selectTotalRowNumbersByKeyword("번");
    assertEquals(expectedCnt, cnt);
  }

  // @Test
  public void testSelectByWord() throws FindException {
    int expectedSize = 1;// 검색어"1번"의 총행수는 3행이다. 1페이지별 2건씩 보면서 2페이지의 행수를 예상
    String word = "1번";
    int currentPage = 2;
    int cntPerPage = 2;
    List<Board> list = repository.selectBoardPostsByKeyword(word, currentPage, cntPerPage);
    assertEquals(expectedSize, list.size());
  }

  // @Test
  public void testSelectByBoardNo() throws FindException {
    String expectedBoardTitle = "3번글";// 검색어"1번"의 총행수는 3행이다. 1페이지별 2건씩 보면서 2페이지의 행수를 예상
    String expectedBoardId = "id1";

    int boardPostNo = 3;
    Board board = repository.selectBoardPostByBoardPostNo(boardPostNo);

    assertNotNull(board);
    assertEquals(expectedBoardTitle, board.getBoardTitle());
    assertEquals(expectedBoardId, board.getBoardId());
  }

  @Test
  public void testUpdateViewCount() throws ModifyException, FindException {
    // 조회수 1증가 테스트
    int boardNo = 1; // 1번 글

    Board b1 = repository.selectBoardPostByBoardPostNo(boardNo);
    assertNotNull(b1);
    int expectedViewcount = b1.getBoardViewcnt() + 1; // 예상조회수 : 조회수 1증가전의 글 조회수+1

    Board b = new Board();
    b.setBoardPostNo(boardNo);
    b.setBoardViewcnt(-1);
    repository.updateBoardPost(b);// 조회수 1증가

    Board b2 = repository.selectBoardPostByBoardPostNo(boardNo); // 조회수 1증가후의 글 조회수
    assertEquals(expectedViewcount, b2.getBoardViewcnt());
  }

  // @Test
  public void testUpdateContent() throws FindException, ModifyException {
    // 내용 수정 테스트
    int boardPostNo = 1;
    Board b1 = repository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(b1);
    String beforeContent = b1.getBoardContent();
    int beforeViewcount = b1.getBoardViewcnt();

    String expectedContent = beforeContent + "a";
    Board b = new Board();
    b.setBoardPostNo(boardPostNo);
    b.setBoardContent(expectedContent);
    repository.updateBoardPost(b);

    Board b2 = repository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotEquals(beforeContent, b2.getBoardContent());
    assertEquals(expectedContent, b2.getBoardContent());
    assertEquals(beforeViewcount, b2.getBoardViewcnt());
  }

  // @Test(expected = FindException.class)
  // @Test
  public void testDelete() throws DeleteException, FindException {
    int boardPostNo = 3;
    repository.deleteBoardPost(boardPostNo);
    assertThrows(FindException.class, () -> {
      repository.selectBoardPostByBoardPostNo(boardPostNo);
    }); // 람다식 사용
  }

  @Test
  public void testInsertBoard() throws AddException, FindException {

    // 글쓰기용 테스트
    String expectedBoardTitle = "새글";
    String expectedBoardContent = "새글내용";
    String expectedBoardId = "asd";
    Board b = new Board();
    b.setBoardTitle(expectedBoardTitle);
    b.setBoardContent(expectedBoardContent);
    b.setBoardId(expectedBoardId);

    repository.insertBoardPost(b);

    int boardPostNo = b.getBoardPostNo();
    assertNotEquals(0, boardPostNo);

    Board b1 = repository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(b1);
    assertEquals(expectedBoardTitle, b1.getBoardTitle());
    assertEquals(expectedBoardContent, b1.getBoardContent());
    assertEquals(expectedBoardId, b1.getBoardId());

  }

  // @Test
  public void testInsertReply() throws AddException, FindException {
    // 답글쓰기용 테스트
    int expectedBoardParentNo = 8;
    String expectedBoardTitle = "새글답";
    String expectedBoardContent = "새글답_내용";
    String expectedBoardId = "asd";
    Board b = new Board();
    b.setBoardParentNo(expectedBoardParentNo);
    b.setBoardTitle(expectedBoardTitle);
    b.setBoardContent(expectedBoardContent);
    b.setBoardId(expectedBoardId);

    repository.insertBoardPost(b);

    int boardPostNo = b.getBoardPostNo();
    assertNotEquals(0, boardPostNo);

    Board b1 = repository.selectBoardPostByBoardPostNo(boardPostNo);
    assertNotNull(b1);
    assertEquals(expectedBoardTitle, b1.getBoardTitle());
    assertEquals(expectedBoardContent, b1.getBoardContent());
    assertEquals(expectedBoardId, b1.getBoardId());

  }
}
