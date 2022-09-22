package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.OrderInfo;
import com.my.exception.FindException;
import com.my.repository.OrderOracleRepository;
import com.my.repository.OrderRepository;


public class VieworderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public VieworderServlet() {}


  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    OrderRepository repository = new OrderOracleRepository();
    HttpSession session = request.getSession();
    String loginedId = (String) session.getAttribute("loginInfo");
    List<OrderInfo> orderinfos = null;
    try {
      orderinfos = repository.selectByOrderId(loginedId);
    } catch (FindException e) {
    }
    if (orderinfos == null) { // 상품 없음
      map.put("status", 0);
      map.put("message", "주문한 상품이 없습니다.");
    } else {
      map.put("status", 1);
      map.put("orderinfos", orderinfos);
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
    System.out.println("vieworderservlet's result : " + result);
  }

}
