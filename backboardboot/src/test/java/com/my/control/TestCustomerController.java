package com.my.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Customer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TestCustomerController {
  @Autowired
  private MockMvc mockMvc; // 모의 객체 : "흉내"내는 "가짜" 모듈
  @Autowired
  ObjectMapper objectMapper;


  // @Test
  public void signupTest() throws Exception {
    int expectedStatus = 1;
    Customer customer = new Customer();
    customer.setId("newid");
    customer.setPassword("newpw");
    customer.setName("new");
    customer.setAddress("new");

    MvcResult result = mockMvc.perform(
        post("/customer/signup").contentType(MediaType.APPLICATION_JSON).content(toJson(customer)))
        .andExpect(status().isOk()).andReturn();
  }


  // @Test
  public void idDuplicationCheckTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder =
        MockMvcRequestBuilders.get("/customer/idduplicationcheck/idn")
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
  public void loginTest() throws Exception {
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("id", "id2");
    requestParams.add("password", "1");
    MvcResult result = mockMvc
        .perform(
            get("/customer/login").contentType(MediaType.APPLICATION_JSON).params(requestParams))
        .andExpect(status().isOk()).andReturn();
  }


  // @Test
  public void logoutTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
        .get("/customer/loginstatus").accept(org.springframework.http.MediaType.APPLICATION_JSON);
    int expectedStatus = 1;
    ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
  }

  private <T> String toJson(T data) throws JsonProcessingException {
    return objectMapper.writeValueAsString(data);
  }


}
