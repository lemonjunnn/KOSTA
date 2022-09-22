package com.my.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.my.dto.Board;
import com.my.dto.PageBean;
import com.my.dto.ResultBean;
import com.my.exception.AddException;
import com.my.exception.DeleteException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.service.BoardService;
import net.coobird.thumbnailator.Thumbnailator;

@CrossOrigin(origins = "http://192.168.0.21, http://192.168.0.22, http://192.168.0.18") // 다른ip에서도
                                                                                        // ajax가능
// @RequestBody : Json 문자열을 파라미터로 가짐
// @Request
@RestController
@RequestMapping("board/*")
public class BoardController {
  private Logger logger = Logger.getLogger(getClass());
  @Autowired
  private BoardService boardService;

  @GetMapping(value = {"list", "list/{optionalCurrentPage}"})
  public ResultBean<PageBean<Board>> viewBoard(@PathVariable Optional<Integer> optionalCurrentPage)
      throws ServletException, IOException, FindException {
    ResultBean<PageBean<Board>> resultBean = new ResultBean<PageBean<Board>>();
    int currentPage;
    if (optionalCurrentPage.isPresent()) {
      currentPage = optionalCurrentPage.get();
    } else {
      currentPage = 1;
    }
    PageBean<Board> pageBean = boardService.viewBoard(currentPage);
    resultBean.setStatus(1);
    resultBean.setMessage("success to bring the page");
    resultBean.setT(pageBean);
    return resultBean;
  }

  @GetMapping(value = {"search", "search/{optionalKeyword}",
      "search/{optionalKeyword}/{optionalCurrentPage}"})
  // "search/{optionalCurrentPage}" 는 불가능함 (url상의 중간경로를 건너뛰는건 불가능함)
  public ResultBean<PageBean<Board>> searchBoard(@PathVariable Optional<String> optionalKeyword,
      @PathVariable Optional<Integer> optionalCurrentPage)
      throws ServletException, IOException, FindException {
    ResultBean<PageBean<Board>> resultBean = new ResultBean<PageBean<Board>>();
    int currentPage;
    if (optionalCurrentPage.isPresent()) {
      currentPage = optionalCurrentPage.get();
    } else {
      currentPage = 1;
    }
    String keyword;
    if (optionalKeyword.isPresent()) {
      keyword = optionalKeyword.get();
    } else {
      keyword = "";
    }
    PageBean<Board> pageBean = boardService.searchBoard(keyword, currentPage);
    resultBean.setStatus(1);
    resultBean.setMessage("success to bring the page");
    resultBean.setT(pageBean);
    return resultBean;
  }

  @GetMapping(value = {"view/{boardPostNo}"})
  public ResultBean<Board> viewPost(@PathVariable Long boardPostNo)
      throws ServletException, IOException, FindException {
    ResultBean<Board> resultBean = new ResultBean<Board>();
    Board board = boardService.viewPost(boardPostNo);
    resultBean.setStatus(1);
    resultBean.setMessage("loding succeed.");
    resultBean.setT(board);
    return resultBean;
  }

  @PostMapping(value = "write")
  // ResponseEntity<?> -> 응답코드를 자기가 조절할 수 있음, 다른 return type은 응답코드를 자기가 조절할 수 없음
  // 스프링에서 파일업/다운로드를 하려면 parameter에 @RequestPart Annotation이 필요
  // 응답코드 500 : 파일업로드 실패, 글쓰기 실패
  // 200 : 파일업로드 성공, 썸네일 생성, 글쓰기 성공
  public ResponseEntity<?> writePost(@RequestPart(required = false) List<MultipartFile> letterFiles,
      @RequestPart(required = false) MultipartFile imageFile, @RequestBody Board board,
      @RequestPart(required = false) String greeting,
      // @PathVariable는 폼객체 전달받기에 쓰기엔 부적합
      HttpSession session) throws AddException, IOException {
    logger.info(greeting);// 게시글내용 DB에 저장
    String loginedId = (String) session.getAttribute("loginInfo");
    board.setBoardId(loginedId);
    boardService.writePost(board);
    // //파일 경로 생성
    String saveDirectory = "c:\\files";
    if (!new File(saveDirectory).exists()) {
      logger.info("업로드 실제경로생성");
      new File(saveDirectory).mkdirs();
    }

    Long wroteBoardNo = board.getBoardPostNo();// 저장된 글의 글번호
    // letterFiles 저장
    int savedletterFileCnt = 0;// 서버에 저장된 파일수
    if (letterFiles != null) {
      for (MultipartFile letterFile : letterFiles) {
        long letterFileSize = letterFile.getSize();
        if (letterFileSize > 0) {
          String letterOriginFileName = letterFile.getOriginalFilename(); // 자소서 파일원본이름얻기
          // 지원서 파일들 저장하기
          logger.info("지원서 파일이름: " + letterOriginFileName + " 파일크기: " + letterFile.getSize());
          // 저장할 파일이름을 지정한다 ex) 글번호_letter_XXXX_원본이름
          String letterfileName =
              wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginFileName;
          File savevdLetterFile = new File(saveDirectory, letterfileName);// 파일생성
          FileCopyUtils.copy(letterFile.getBytes(), savevdLetterFile);
          logger.info("지원서 파일저장:" + savevdLetterFile.getAbsolutePath());
          savedletterFileCnt++;
        } // end if(letterFileSize > 0)
      }
    } // end if(letterFiles != null)
    logger.info("저장된 letter 파일개수: " + savedletterFileCnt);


    File thumbnailFile = null;
    long imageFileSize = imageFile.getSize();
    int imageFileCnt = 0; // 서버에 저장된 이미지파일수
    if (imageFileSize > 0) {
      // 이미지파일 저장하기
      String imageOrignFileName = imageFile.getOriginalFilename(); // 이미지파일원본이름얻기
      logger.info("이미지 파일이름:" + imageOrignFileName + ", 파일크기: " + imageFile.getSize());

      // 저장할 파일이름을 지정한다 ex) 글번호_image_XXXX_원본이름
      String imageFileName =
          wroteBoardNo + "_image_" + UUID.randomUUID() + "_" + imageOrignFileName;
      // 이미지파일생성
      File savedImageFile = new File(saveDirectory, imageFileName);
      FileCopyUtils.copy(imageFile.getBytes(), savedImageFile);
      logger.info("이미지 파일저장:" + savedImageFile.getAbsolutePath());

      // 파일형식 확인
      String contentType = imageFile.getContentType();
      if (!contentType.contains("image/")) { // 이미지파일형식이 아닌 경우
        return new ResponseEntity<>("적절한 이미지파일 형식이 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // 이미지파일인 경우 섬네일파일을 만듦
      String thumbnailName = "s_" + imageFileName; // 섬네일 파일명은 s_글번호_XXXX_원본이름
      thumbnailFile = new File(saveDirectory, thumbnailName);
      FileOutputStream thumbnailOS;
      thumbnailOS = new FileOutputStream(thumbnailFile);
      InputStream imageFileIS = imageFile.getInputStream();
      int width = 100;
      int height = 100;
      Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
      logger.info(
          "섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기:" + thumbnailFile.length());
      // 이미지 썸네일다운로드하기
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length() + "");
      responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
      responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
          "inline; filename=" + URLEncoder.encode("a", "UTF-8"));
      logger.info("섬네일파일 다운로드");
      return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders,
          HttpStatus.OK);
    } // end if(imageFileSize > 0)
    else {
      logger.error("이미지파일이 없습니다");
      return new ResponseEntity<>("이미지파일이 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "reply/{boardParentNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  // MediaType은 UTF-8을 지원해주지 않으므로, MIME을 직접 입력하는게 더 좋은 방법임 (* JSON은 예외)
  public ResponseEntity<?> writeReply(@PathVariable Long boardParentNo, @RequestBody Board board,
      HttpSession session) throws AddException {
    if (board.getBoardTitle() == null || board.getBoardTitle().equals("")
        || board.getBoardContent() == null || board.getBoardContent().equals("")) {
      return new ResponseEntity<>("글 제목/내용이 없습니다", HttpStatus.BAD_REQUEST);
    }
    String loginedId = (String) session.getAttribute("loginInfo");
    board.setBoardId(loginedId);
    board.setBoardParentNo(boardParentNo);

    boardService.writeReply(board);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(value = "{boardPostNo}", produces = MediaType.APPLICATION_JSON_VALUE) // 응답형식 : JSON
  public ResponseEntity<?> modify(@PathVariable Long boardPostNo, @RequestBody Board board,
      HttpSession session) throws ModifyException {
    String loginedId = (String) session.getAttribute("loginInfo");
    if (board.getBoardTitle() == null || board.getBoardTitle().equals("")
        || board.getBoardContent() == null || board.getBoardContent().equals("")) {
      return new ResponseEntity<>("글 내용이 없습니다", HttpStatus.BAD_REQUEST);
    } else if (!board.getBoardId().equals(loginedId)) {
      return new ResponseEntity<>("다른 사용자의 글은 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
    boardService.modifyPost(board);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "{boardPostNo}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> remove(@PathVariable Long boardPostNo, HttpSession session)
      throws DeleteException, FindException {
    String loginedId = (String) session.getAttribute("loginInfo");
    Board board = null;

    board = boardService.viewPost(boardPostNo);

    String idInBoard = board.getBoardId();
    if (loginedId.equals(idInBoard)) {
      boardService.deletePost(boardPostNo);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>("ID가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

  }
}
