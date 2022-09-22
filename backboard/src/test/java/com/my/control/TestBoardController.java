package com.my.control;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// 스프링컨테이너(ApplicationContext)구동
@RunWith(SpringRunner.class)
@ContextConfiguration(
    locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

@WebAppConfiguration // WebApplicationContext type's container
public class TestBoardController {
  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;// mock object

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void viewBoardTest() throws Exception {
    // 게시글페이지별 목록 테스트
    String uri = "/viewboard";
    String currentPage = "2";
    MockHttpServletRequestBuilder mockRequestBuilder =
        MockMvcRequestBuilders.get(uri).accept(org.springframework.http.MediaType.APPLICATION_JSON);
    mockRequestBuilder.param("current_page", currentPage);
    // 응답정보: ResultAction
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);// 요청
    resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 응답상태코드200번 성공 예상
    // resultActions.andExpect(jsonPath("status",is(1)));//json객체의 status프로퍼티값이 1 예상
    org.hamcrest.Matcher<Integer> matcher;
    JsonPathResultMatchers jsonPathMatcher;
    ResultMatcher resultMatcher;
    jsonPathMatcher = jsonPath("status");
    resultMatcher = jsonPathMatcher.exists();
    resultActions.andExpect(resultMatcher);
    // 게시글페이지그룹의 시작페이지값 t.startPage
    int expectedStartPage = 1;
    matcher = org.hamcrest.CoreMatchers.is(expectedStartPage);
    resultMatcher = jsonPath("t.startPage", matcher);
    resultActions.andExpect(resultMatcher);
  }


  @Test
  public void searchBoardTest() throws Exception {
    String uri = "/searchboard";
    MockHttpServletRequestBuilder mockRequestBuilder =
        MockMvcRequestBuilders.get(uri).accept(org.springframework.http.MediaType.APPLICATION_JSON);
    mockRequestBuilder.param("current_page", "2334");
    mockRequestBuilder.param("keyword", "ế");
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder); // 응답요청
    resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 응답 성공여부
    // resultActions.andExpect(MockMvcResultMatchers.content().string("welcome")); // 응답내용 출력
    org.hamcrest.Matcher<Integer> matcher;
    JsonPathResultMatchers jsonPathMatcher;
    ResultMatcher resultMatcher;
    jsonPathMatcher = jsonPath("status");
    resultMatcher = jsonPathMatcher.exists();
    resultActions.andExpect(resultMatcher);
    // 게시글페이지그룹의 시작페이지값 t.startPage
    int expectedStartPage = 1;
    matcher = org.hamcrest.CoreMatchers.is(expectedStartPage);
    resultMatcher = jsonPath("t.startPage", matcher);
    resultActions.andExpect(resultMatcher);
  }

}
