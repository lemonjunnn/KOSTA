package com.my.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Customer;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TestOrderController {
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

  @Test
  public void addOrderTest() throws Exception {
    OrderInfo orderInfo = new OrderInfo();
    List<OrderLine> orderLines = new ArrayList<OrderLine>();
    for (int i = 0; i < 3; i++) {
      OrderLine orderLine = new OrderLine();
      Product product = new Product();
      product.setProductNo("C000" + (i + 1));
      orderLine.setOrderProduct(product);
      orderLine.setOrderQuantity(new Long(i + 3));
      orderLines.add(orderLine);
    }
    Customer customer = new Customer();
    customer.setId("id17");
    orderInfo.setOrderLines(orderLines);
    orderInfo.setCustomer(customer);
    MvcResult result = mockMvc.perform(
        post("/order/addorder").contentType(MediaType.APPLICATION_JSON).content(toJson(orderInfo)))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  public void viewOrderTest() throws Exception {
    MockHttpSession session = new MockHttpSession();
    session.setAttribute("loginInfo", "id1");
    MockHttpServletRequestBuilder mockRequestBuilder =
        MockMvcRequestBuilders.get("/order/vieworder").session(session)
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

  private <T> String toJson(T data) throws JsonProcessingException {
    return objectMapper.writeValueAsString(data);
  }
}
