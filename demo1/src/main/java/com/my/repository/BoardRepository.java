package com.my.repository;

import java.util.List;
import com.my.dto.Board;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public interface BoardRepository {

  public int selectTotalRowNumbersByPage() throws FindException;

  public int selectTotalRowNumbersByKeyword(String keyword) throws FindException;

  public List<Board> selectBoardPostsByPage(int currentPage, int cntPerPage) throws FindException;

  public List<Board> selectBoardPostsByKeyword(String keyword, int currentPage, int cntPerPage)
      throws FindException;

  public Board selectBoardPostByBoardPostNo(int boardPostNo) throws FindException;

  public void updateBoardPost(Board board) throws ModifyException;

  public void deleteBoardPost(int boardPostNo) throws DeleteException;

  public void insertBoardPost(Board board) throws AddException;
}
