CREATE TABLE board(
    board_post_no NUMBER,
    board_parent_no NUMBER,
    board_title VARCHAR2(30),
    board_content VARCHAR2(200),
    board_date DATE DEFAULT SYSDATE,
    board_id VARCHAR2(5),
    board_viewcnt NUMBER,
    CONSTRAINT board_post_no_pk PRIMARY KEY(board_post_no),
    CONSTRAINT board_id_fk FOREIGN KEY(board_id) REFERENCES customer(id)
    );

CREATE SEQUENCE board_seq;

INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  0,                      '1번글',         '글1내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  1,                      '2_1번글_답',         '글2내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  0,                      '3번글',         '글3내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  1,                      '4_1번글_답',         '글4내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  2,                      '5_2번글_답',         '글5내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  4,                      '6_4번글_답',         '글5내용',        'asd',        0);
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  3,                      '7_3번글_답',         '글5내용',        'asd',        0);

-- 총게시글 수
SELECT COUNT(*) FROM board;


-- 페이지당 3개의 글 가져오기 
SELECT *
FROM (
      SELECT ROWNUM r,a.* 
      FROM (SELECT LEVEL, b.*
            FROM board b
            START WITH board_parent_no = 0
            CONNECT BY PRIOR board_post_no = board_parent_no
            ORDER SIBLINGS BY board_post_no DESC
            ) a
      )
WHERE r BETWEEN 1 AND 3;

--게시글 검색
SELECT *
FROM (
      SELECT ROWNUM r,a.* 
      FROM (SELECT LEVEL, b.*
            FROM board b
            WHERE board_title LIKE '% ?? %' OR board_content LIKE '% ?? %' 
            START WITH board_parent_no = 0
            CONNECT BY PRIOR board_post_no = board_parent_no
            ORDER SIBLINGS BY board_post_no DESC
            ) a
      )
WHERE r BETWEEN 1 AND 3;

--게시글 상세 + 조회수 1 증가
SELECT * FROM board WHERE board_post_no = ?;
UPDATE board
SET board_viewcnt = board_viewcnt + 1
WHERE board_post_no = ?;

--게시글 수정
UPDATE board
SET board_content = ?
WHERE board_post_no = ?;

--글 삭제 (답글들도 모두 삭제)
--1 답글 삭제     
DELETE FROM board
WHERE board_post_no IN (SELECT board_post_no
                        FROM board
                        START WITH board_parent_no = 1
                        CONNECT BY PRIOR board_post_no = board_parent_no);
--2 글 삭제
DELETE FROM board
WHERE board_post_no = ?;

-- 글 쓰기
INSERT INTO board(board_post_no, board_parent_no, board_title, board_content, board_id, board_viewcnt)
VALUES (board_seq.NEXTVAL,  ?,                      ?,         ?,        'asd',        0);