package com.my.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.my.dto.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
  @Query(nativeQuery = true,
      value = "     DELETE FROM\r\n" + "        board\r\n"
          + "        WHERE board_post_no IN (SELECT board_post_no\r\n" + "        FROM board\r\n"
          + "        START WITH\r\n" + "        board_parent_no = ?1 \r\n"
          + "        CONNECT BY PRIOR board_post_no =\r\n" + "        board_parent_no)")
  public void deleteReply(Long boardPostNo);

  @Query(nativeQuery = true,
      value = "     SELECT *\r\n" + "        FROM (\r\n" + "        SELECT ROWNUM r,a.*\r\n"
          + "        FROM (SELECT LEVEL, b.*\r\n" + "        FROM board b\r\n"
          + "        START WITH board_parent_no = 0\r\n" + "        CONNECT BY PRIOR\r\n"
          + "        board_post_no = board_parent_no\r\n"
          + "        ORDER SIBLINGS BY board_post_no DESC\r\n" + "        ) a\r\n" + "        )\r\n"
          + "        WHERE r BETWEEN ?1 AND ?2 ")
  List<Board> findByPage(int startRow, int endRow);

  @Query(nativeQuery = true,
      value = "     SELECT *\r\n" + "        FROM (\r\n" + "        SELECT ROWNUM r, a.*\r\n"
          + "        FROM (SELECT level,\r\n" + "        b.*\r\n" + "        FROM board b\r\n"
          + "        WHERE board_title LIKE '%' || ?1 || '%'   OR board_id LIKE\r\n"
          + "        '%' || ?1 || '%' \r\n" + "        START WITH board_parent_no = 0\r\n"
          + "        CONNECT BY PRIOR\r\n" + "        board_post_no = board_parent_no\r\n"
          + "        ORDER SIBLINGS BY board_post_no DESC\r\n" + "        ) a\r\n" + "        )\r\n"
          + "        WHERE r BETWEEN ?2 AND ?3 ")
  List<Board> findByPageWithKeyword(String keyword, int startRow, int endRow);
  // List<Board> findAll(org.springframework.data.domain.Pageable paging);

}
