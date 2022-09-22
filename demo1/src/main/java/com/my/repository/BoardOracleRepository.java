package com.my.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.my.dto.Board;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

@Repository(value = "boardRepository")
public class BoardOracleRepository implements BoardRepository {
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public int selectTotalRowNumbersByPage() throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      int number = session.selectOne("com.my.mapper.BoardMapper.selectTotalRowNumbersByPage");
      return number;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public int selectTotalRowNumbersByKeyword(String keyword) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      int number =
          session.selectOne("com.my.mapper.BoardMapper.selectTotalRowNumbersByKeyword", keyword);
      return number;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public List<Board> selectBoardPostsByPage(int currentPage, int cntPerPage) throws FindException {
    SqlSession session = null;

    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      int startRow = (currentPage - 1) * cntPerPage + 1;
      int endRow = currentPage * cntPerPage;
      Map<String, Integer> map = new HashMap<String, Integer>();
      map.put("startRow", startRow);
      map.put("endRow", endRow);
      List<Board> boards =
          session.selectList("com.my.mapper.BoardMapper.selectBoardPostsByPage", map);
      if (boards.size() == 0) {
        throw new FindException("No posts in the board");
      }
      return boards;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("No posts in the board");
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public List<Board> selectBoardPostsByKeyword(String keyword, int currentPage, int cntPerPage)
      throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      int startRow = (currentPage - 1) * cntPerPage + 1;
      int endRow = currentPage * cntPerPage;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("keyword", keyword);
      map.put("startRow", startRow);
      map.put("endRow", endRow);
      List<Board> boards =
          session.selectList("com.my.mapper.BoardMapper.selectBoardPostsByKeyword", map);
      if (boards.size() == 0) {
        throw new FindException("No posts in the board");
      }
      return boards;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("No posts in the board");
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public Board selectBoardPostByBoardPostNo(int boardPostNo) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      Board board =
          session.selectOne("com.my.mapper.BoardMapper.selectBoardPostByBoardPostNo", boardPostNo);
      return board;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("Cannot Find the post (boardPostNo : " + boardPostNo + ")");
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public void updateBoardPost(Board board) throws ModifyException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      int rowcnt = session.update("com.my.mapper.BoardMapper.updateBoardPost", board);
      if (rowcnt != 1) {
        throw new ModifyException("수정된 row 수 : " + rowcnt);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ModifyException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  @Override
  public void deleteBoardPost(int boardPostNo) throws DeleteException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      deleteAllChildBoardPosts(session, boardPostNo);
      deleteParentBoardPost(session, boardPostNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DeleteException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  private void deleteAllChildBoardPosts(SqlSession session, int boardPostNo)
      throws DeleteException {
    try {
      session.delete("com.my.mapper.BoardMapper.deleteAllChildBoardPosts", boardPostNo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DeleteException(e.getMessage());
    }
  }

  private void deleteParentBoardPost(SqlSession session, int boardPostNo) throws DeleteException {
    try {
      int rowcnt = session.delete("com.my.mapper.BoardMapper.deleteParentBoardPost", boardPostNo);
      if (rowcnt == 0) {
        throw new DeleteException("삭제된 게시글이 없습니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new DeleteException(e.getMessage());
    }
  }

  @Override
  public void insertBoardPost(Board board) throws AddException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      session.insert("com.my.mapper.BoardMapper.insertBoardPost", board);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }
}
