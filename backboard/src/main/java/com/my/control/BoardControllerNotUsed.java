package com.my.control;


import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.dto.Board;
import com.my.dto.PageBean;
import com.my.dto.ResultBean;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.service.BoardService;


public class BoardControllerNotUsed {
  private Logger logger = Logger.getLogger(getClass());
  @Autowired
  private BoardService boardService;

  @GetMapping(produces = "application/json;charset=UTF-8", value = "viewbaord")
  @ResponseBody
  public ResultBean<PageBean<Board>> viewBoard(
      @RequestParam(name = "current_page", required = false, defaultValue = "1") int currentPage)
      throws ServletException, IOException {
    ResultBean<PageBean<Board>> resultBean = new ResultBean<PageBean<Board>>();
    try {
      PageBean<Board> pageBean = boardService.viewBoard(currentPage);
      resultBean.setStatus(1);
      resultBean.setMessage("success to bring the page");
      resultBean.setT(pageBean);
    } catch (FindException e) {
      resultBean.setStatus(0);
      resultBean.setMessage("failure to bring the page");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setStatus(0);
      resultBean.setMessage("failure to bring the page");
      e.printStackTrace();
    }
    return resultBean;
  }

  @GetMapping(produces = "application/json;charset=UTF-8", value = "searchboard")
  @ResponseBody
  public ResultBean<PageBean<Board>> searchBoard(
      @RequestParam(required = false, defaultValue = "") String keyword,
      @RequestParam(name = "current_page", required = false, defaultValue = "1") int currentPage)
      throws ServletException, IOException {
    ResultBean<PageBean<Board>> resultBean = new ResultBean<PageBean<Board>>();
    try {
      PageBean<Board> pageBean = boardService.searchBoard(keyword, currentPage);
      resultBean.setStatus(1);
      resultBean.setMessage("success to bring the page");
      resultBean.setT(pageBean);
    } catch (FindException e) {
      resultBean.setStatus(0);
      resultBean.setMessage("failure to bring the page");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setStatus(0);
      resultBean.setMessage("failure to bring the page");
      e.printStackTrace();
    }
    return resultBean;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "viewpost")
  @ResponseBody
  public ResultBean<Board> viewPost(@RequestBody int boardPostNo)
      throws ServletException, IOException {
    ResultBean<Board> resultBean = new ResultBean<Board>();
    try {
      Board board = new Board();
      board.setBoardPostNo(boardPostNo);
      boardService.modifyPost(board);
      board = boardService.viewPost(boardPostNo);
      resultBean.setStatus(1);
      resultBean.setMessage("loding succeed.");
      resultBean.setT(board);
    } catch (FindException e) {
      resultBean.setStatus(0);
      resultBean.setMessage("loding failed.");
      e.printStackTrace();
    } catch (ModifyException e) {
      resultBean.setStatus(0);
      resultBean.setMessage("loding failed.");
      e.printStackTrace();
    } catch (Exception e) {
      resultBean.setStatus(0);
      resultBean.setMessage("loding failed.");
      e.printStackTrace();
    }
    return resultBean;
  }
  //
  // @PostMapping("writepost")
  // // ResponseEntity<?> -> 응답코드를 자기가 조절할 수 있음, 다른 return type은 응답코드를 자기가 조절할 수 없음
  // // 스프링에서 파일업/다운로드를 하려면 parameter에 @RequestPart Annotation이 필요
  // // 응답코드 500 : 파일업로드 실패, 글쓰기 실패
  // // 200 : 파일업로드 성공, 썸네일 생성, 글쓰기 성공
  // public ResponseEntity<?> writePost(@RequestPart(required = false) List<MultipartFile>
  // letterFiles,
  // @RequestPart(required = false) MultipartFile imageFile, Board board, String greeting,
  // HttpSession session, ServletContext servletContext) {
  // logger.info("요청전달데이터 title=" + board.getBoardTitle() + ", content=" + board.getBoardContent());
  // logger.info("letterFiles.size()=" + letterFiles.size());
  // logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()="
  // + imageFile.getOriginalFilename());
  // logger.info(greeting);// 게시글내용 DB에 저장
  // try {
  // // String loginedId = (String)session.getAttribute("loginInfo");
  // // ---로그인대신할 샘플데이터--
  // String loginedId = "id1";
  // // ----------------------
  // board.setBoardId(loginedId);
  // boardService.writePost(board);
  // return new ResponseEntity<>(HttpStatus.OK);
  // } catch (AddException e1) {
  // e1.printStackTrace();
  // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  // } // //파일 경로 생성
  // String saveDirectory = "c:\\files";
  // if (!new File("c:\\files").exists()) {
  // logger.info("업로드 실제경로생성");
  // new File("c:\\files").mkdirs();
  // }
  //
  // int wroteBoardNo = board.getBoardPostNo();// 저장된 글의 글번호
  // // letterFiles 저장
  // int savedletterFileCnt = 0;// 서버에 저장된 파일수
  // if (letterFiles != null) {
  // for (MultipartFile letterFile : letterFiles) {
  // long letterFileSize = letterFile.getSize();
  // if (letterFileSize > 0) {
  // String letterOriginFileName = letterFile.getOriginalFilename(); // 자소서 파일원본이름얻기
  // // 지원서 파일들 저장하기
  // logger.info("지원서 파일이름: " + letterOriginFileName + " 파일크기: " + letterFile.getSize());
  // // 저장할 파일이름을 지정한다 ex) 글번호_letter_XXXX_원본이름
  // String letterfileName =
  // wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginFileName;
  // File savevdLetterFile = new File(saveDirectory, letterfileName);// 파일생성
  // try {
  // FileCopyUtils.copy(letterFile.getBytes(), savevdLetterFile);
  // logger.info("지원서 파일저장:" + savevdLetterFile.getAbsolutePath());
  // } catch (IOException e) {
  // e.printStackTrace();
  // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  // }
  // savedletterFileCnt++;
  // } // end if(letterFileSize > 0)
  // }
  // } // end if(letterFiles != null)
  // logger.info("저장된 letter 파일개수: " + savedletterFileCnt);
  // return new ResponseEntity<>(HttpStatus.OK);
  // }
  //
  // File thumbnailFile = null;
  // long imageFileSize = imageFile.getSize();
  // int imageFileCnt = 0; // 서버에 저장된 이미지파일수
  // if(imageFileSize>0)
  // {
  // // 이미지파일 저장하기
  // String imageOrignFileName = imageFile.getOriginalFilename(); // 이미지파일원본이름얻기
  // logger.info("이미지 파일이름:" + imageOrignFileName + ", 파일크기: " + imageFile.getSize());
  //
  // // 저장할 파일이름을 지정한다 ex) 글번호_image_XXXX_원본이름
  // String imageFileName = wroteBoardNo + "_image_" + UUID.randomUUID() + "_" + imageOrignFileName;
  // // 이미지파일생성
  // File savedImageFile = new File(saveDirectory, imageFileName);
  // try {
  // FileCopyUtils.copy(imageFile.getBytes(), savedImageFile);
  // logger.info("이미지 파일저장:" + savedImageFile.getAbsolutePath());
  //
  // // 파일형식 확인
  // String contentType = imageFile.getContentType();
  // if (!contentType.contains("image/")) { // 이미지파일형식이 아닌 경우
  // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  // }
  // // 이미지파일인 경우 섬네일파일을 만듦
  // String thumbnailName = "s_" + imageFileName; // 섬네일 파일명은 s_글번호_XXXX_원본이름
  // thumbnailFile = new File(saveDirectory, thumbnailName);
  // FileOutputStream thumbnailOS;
  // thumbnailOS = new FileOutputStream(thumbnailFile);
  // InputStream imageFileIS = imageFile.getInputStream();
  // int width = 100;
  // int height = 100;
  // Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
  // logger.info(
  // "섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기:" + thumbnailFile.length());
  // // 이미지 썸네일다운로드하기
  // HttpHeaders responseHeaders = new HttpHeaders();
  // responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length() + "");
  // responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
  // responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
  // "inline; filename=" + URLEncoder.encode("a", "UTF-8"));
  // logger.info("섬네일파일 다운로드");
  // return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders,
  // HttpStatus.OK);
  //
  // } catch (IOException e2) {
  // e2.printStackTrace();
  // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  // }
  // }// end if(imageFileSize > 0)
  // else
  // {
  // logger.error("이미지파일이 없습니다");
  // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  // }

}
