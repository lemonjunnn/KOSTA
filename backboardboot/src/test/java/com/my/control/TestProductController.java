package com.my.control;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TestProductController {
  @Autowired
  private MockMvc mockMvc; // 모의 객체 : "흉내"내는 "가짜" 모듈
  @Autowired
  ObjectMapper objectMapper;



  @Test
  public void productlistTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
        .get("/product/productlist").accept(org.springframework.http.MediaType.APPLICATION_JSON);
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
  public void viewProductTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder =
        MockMvcRequestBuilders.get("/product/viewproduct/C0001")
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
  public void searchTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
        .get("/product/search/C").accept(org.springframework.http.MediaType.APPLICATION_JSON);
    int expectedStatus = 1;
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    org.hamcrest.Matcher<Integer> matcher;
    ResultMatcher resultMatcher;

    matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
    resultMatcher = jsonPath("status", matcher); // jsonPath를 이용하여 totalCount값의 일치여부를 check
    resultActions.andExpect(resultMatcher);
  }


}
