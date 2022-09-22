package com.my.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Board;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestBoardController2 {
  @Autowired
  private MockMvc mockMvc; // 모의 객체 : "흉내"내는 "가짜" 모듈
  @Autowired
  ObjectMapper objectMapper;
  protected MockHttpSession session;

  @Before // 1
  public void setUp() throws Exception {
    session = new MockHttpSession();

    session.setAttribute("loginInfo", "id1");
  }

  @After // 2
  public void clean() {
    session.clearAttributes();
  }

  // @Test
  public void viewBoardTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.get("/board/list/1")
        .accept(org.springframework.http.MediaType.APPLICATION_JSON);
    int expectedStatus = 1;
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    org.hamcrest.Matcher<Integer> matcher;
    ResultMatcher resultMatcher;

    matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
    resultMatcher = jsonPath("status", matcher); // jsonPath를 이용하여 totalCount값의 일치여부를 check
    resultActions.andExpect(resultMatcher);
  }


  // @Test
  public void searchBoardTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
        .get("/board/search/title/1").accept(org.springframework.http.MediaType.APPLICATION_JSON);
    int expectedStatus = 1;
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    org.hamcrest.Matcher<Integer> matcher;
    ResultMatcher resultMatcher;

    matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
    resultMatcher = jsonPath("status", matcher); // jsonPath를 이용하여 totalCount값의 일치여부를 check
    resultActions.andExpect(resultMatcher);
  }

  // @Test
  public void viewPostTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.get("/board/view/9")
        .accept(org.springframework.http.MediaType.APPLICATION_JSON);
    int expectedStatus = 1;
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    org.hamcrest.Matcher<Integer> matcher;
    ResultMatcher resultMatcher;

    matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
    resultMatcher = jsonPath("status", matcher); // jsonPath를 이용하여 totalCount값의 일치여부를 check
    resultActions.andExpect(resultMatcher);
  }

  @Test
  public void writeReplyTest() throws Exception {
    Board board = new Board();
    board.setBoardTitle("reply of 9");
    board.setBoardContent("content of reply");

    MvcResult result = mockMvc
        .perform(
            post("/board/reply/9").contentType(MediaType.APPLICATION_JSON).content(toJson(board)))
        .andExpect(status().isOk()).andReturn();
  }

  // @Test
  public void modifyTest() throws Exception {
    Board board = new Board();
    board.setBoardPostNo(24L);
    board.setBoardId("asd");
    board.setBoardTitle("modified title");
    board.setBoardContent("modified content");

    MvcResult result = mockMvc
        .perform(put("/board/24").contentType(MediaType.APPLICATION_JSON).content(toJson(board)))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  @Transactional
  @Rollback
  public void deleteTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.delete("/board/25")
        .accept(org.springframework.http.MediaType.APPLICATION_JSON);
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
  }

  private <T> String toJson(T data) throws JsonProcessingException {
    return objectMapper.writeValueAsString(data);
  }


}
