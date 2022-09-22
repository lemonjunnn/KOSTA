$(() => {
  let loginedId = "asd"; //테스트용

  //function showList(pageNo){
  function showBoard(url) {
    $.ajax({
      // url: '/backboard/boardlist',
      // data: 'currentPage=' + pageNo,
      url: url,
      method: "get",
      success: (jsonObj) => {
        if (jsonObj.status == 1) {
          console.log(jsonObj);
          let pageBeanObj = jsonObj.t;

          //게시글 div를 원본으로 한다. 복제본만든다
          let $board = $("div.board").first();
          //나머지 게시글 div는 삭제한다
          $("div.board").not($board).remove();

          let $boardParent = $board.parent();
          $(pageBeanObj.boards).each((index, board) => {
            let $boardCopy = $board.clone(); //복제본
            $boardCopy.find("div.board_post_no").html(board.boardPostNo);
            $boardCopy.find("div.board_parent_no").html(board.boardParentNo);
            $boardCopy.find("div.board_title").html(board.boardTitle);
            $boardCopy.find("div.board_date").html(board.boardDate);
            $boardCopy.find("div.board_id").html(board.boardId);
            $boardCopy.find("div.board_viewcnt").html(board.boardViewcnt);
            $boardCopy.find("div.arrow").addClass("down");
            $boardParent.append($boardCopy);
          });

          let $pagegroup = $("div.pagegroup");
          let $pagegroupHtml = "";
          if (pageBeanObj.startPage > 1) {
            $pagegroupHtml += '<span class="prev">PREV</span>';
          }
          for (let i = pageBeanObj.startPage; i <= pageBeanObj.endPage; i++) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            if (pageBeanObj.currentPage == i) {
              //현재페이지인 경우 <span>태그 안만듦
              // $pagegroupHtml += i;
              $pagegroupHtml += '<span class="disabled">' + i + "</span>";
            } else {
              $pagegroupHtml += "<span>" + i + "</span>";
            }
          }
          if (pageBeanObj.endPage < pageBeanObj.totalPage) {
            $pagegroupHtml += "&nbsp;&nbsp;";
            $pagegroupHtml += '<span class="next">NEXT</span>';
          }

          $pagegroup.html($pagegroupHtml);
        } else {
          alert(jsonObj.message);
        }
      },
      error: (jqXHR) => {
        alert("에러:" + jqXHR.status);
      },
    });
  }

  //---페이지 로드되자 마자 게시글1페이지 검색 START---
  showBoard(`${backPath}/board/list/1`);
  //---페이지 로드되자 마자 게시글1페이지 검색 END---

  //---페이지 그룹의 페이지를 클릭 START---
  $("div.pagegroup").on("click", "span:not(.disabled)", () => {
    // span 태그 중 속성값이 .disabled가 아닌 요소들
    let pageNo = 1;
    if ($(this).hasClass("prev")) {
      pageNo = parseInt($(this).next().html()) - 1;
    } else if ($(this).hasClass("next")) {
      pageNo = parseInt($(this).prev().html()) + 1;
    } else {
      pageNo = parseInt($(this).html());
    }
    let keyword = $("div.search>div.searchInput>input[name=keyword]")
      .val()
      .trim();
    let url = "";
    if (keyword == "") {
      url = `${backPath}/board/list/` + pageNo;
    } else {
      url = `${backPath}/board/search/` + pageNo + "/" + keyword;
    }
    showBoard(url);
    return false;
  });
  //---페이지 그룹의 페이지를 클릭 END---

  //---검색 클릭 START---
  $("div.search>div.searchInput>a").click(() => {
    let keyword = $("div.search>div.searchInput>input[name=keyword]")
      .val()
      .trim();
    let url = `${backPath}/board/search/` + keyword + "/1";
    showBoard(url);
    return false;
  });
  //---검색 클릭 END---

  //---arrow화살표클릭 START---
  $("div.boardlist").on(
    "click",
    "div.board>div.cell>div.summary>div.arrow",
    () => {
      if ($(this).hasClass("down")) {
        let boardPostNo = $(this).siblings("div.board_post_no").html();
        let $viewcnt = $(this).siblings("div.board_viewcnt");
        let $detail = $(this).parents("div.cell").find("div.detail");
        let $boardContent = $detail.find("input.board_content");
        let $modifyNremove = $detail.find("div.modifyNremove");

        $.ajax({
          url: `${backPath}/view/` + boardPostNo,
          method: "get",
          success: (jsonObj) => {
            if (jsonObj.status == 1) {
              console.log(jsonObj);
              let board = jsonObj.t;
              $viewcnt.html(board.boardViewcnt);
              if (loginedId == board.boardId) {
                $boardContent.removeAttr("readonly");
                $boardContent.css("outline", "auto");
                $modifyNremove.show();
              } else {
                $boardContent.attr("readonly", "readonly");
                $boardContent.css("outline", "none");
                $modifyNremove.hide();
              }
              $boardContent.val(board.boardContent);
              $detail.show();
            } else {
              alert(jsonObj.message);
            }
          },
          error: (jqXHR) => {
            alert("에러:" + jqXHR.status);
          },
        });
        $(this).addClass("up");
        $(this).removeClass("down");
      } else if ($(this).hasClass("up")) {
        $(this).addClass("down");
        $(this).removeClass("up");
        $(this).parents("div.cell").find("div.detail").hide();
      }
    }
  );
  //---arrow화살표클릭 END---

  //---수정버튼 클릭 START---
  $("div.boardlist").on(
    "click",
    "div.board div.modifyNremove>button.modify",
    () => {
      let boardPostNo = $(this)
        .parents("div.cell")
        .find("div.board_post_no")
        .html();
      let boardContent = $(this)
        .parents("div.cell")
        .find("input.board_content")
        .val()
        .trim();
      if (boardContent == "") {
        alert("글 내용을 입력하세요.");
        return false;
      }

      $.ajax({
        url: `${backPath}/board/` + boardPostNo,
        method: "PUT",
        timeout: 0,
        headers: {
          "Content-Type": "application/json",
        },
        data: JSON.stringify({
          boardPostNo: boardPostNo,
          boardContent: boardContent,
        }),
        success: (jsonObj) => {
          alert("수정성공");
        },
        error: (jqXHR, textStatus) => {
          alert(
            "수정 실패. 사유 :" +
              jqXHR.status +
              ", jqXHR.responseText:" +
              jqXHR.responseText
          );
        },
      });
      return false;
    }
  );
  //---수정버튼 클릭 END---

  //---삭제버튼 클릭 START---
  $("div.boardlist").on(
    "click",
    "div.board div.modifyNremove>button.remove",
    () => {
      alert("in 삭제");
      let boardPostNo = $(this)
        .parents("div.cell")
        .find("div.board_post_no")
        .html();
      $.ajax({
        url: `${backPath}/board/` + boardPostNo,
        method: "DELETE",
        success: () => {
          location.href = "./viewboard.html";
        },
        error: (jqXHR, textStatus) => {
          alert(
            "삭제 실패. 사유 :" +
              jqXHR.status +
              ", jqXHR.responseText:" +
              jqXHR.responseText
          );
        },
      });
      return false;
    }
  );
  //---삭제버튼 클릭 END---

  //--답글저장 버튼 클릭 START --
  $("div.boardlist").on("click", "div.board div.reply button", () => {
    let boardParentNo = $(this)
      .parents("div.cell")
      .find("div.board_post_no")
      .html();

    let boardTitle = $(this).siblings("input[name=board_title]").val();
    let boardContent = $(this).siblings("textarea[name=board_content]").val();
    alert(
      "in 답글 부모글번호:" +
        boardParentNo +
        ", boardTitle:" +
        boardTitle +
        ", boardContent:" +
        boardContent
    );
    $.ajax({
      url: `${backPath}/board/reply/` + boardParentNo,
      method: "POST",
      timeout: 0,
      headers: {
        "Content-Type": "application/json",
      },
      data: JSON.stringify({
        boardTitle: boardTitle,
        boardContent: boardContent,
      }),
      success: () => {
        location.href = "./boardlist.html";
      },
      error: (jqXHR, textStatus) => {
        alert(
          "답글 에러:" +
            jqXHR.status +
            ", jqXHR.responseText:" +
            jqXHR.responseText
        );
      },
    });
    return false;
    //---답글저장버튼 클릭 END---
  });
});
