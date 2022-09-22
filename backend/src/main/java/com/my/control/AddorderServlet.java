package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.my.dto.OrderLine;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.repository.OrderOracleRepository;
import com.my.repository.OrderRepository;


public class AddorderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;


  public AddorderServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    String result = "";

    HttpSession session = request.getSession();
    Map<Product, Integer> cart = (Map) session.getAttribute("cart");

    // start sample data
    // if (cart == null) {
    // cart = new HashMap<Product, Integer>();
    // }
    // cart.put(new Product("C0001", "아메리카노", 1000), 3);
    // cart.put(new Product("C0002", "아이스아메리카노", 1000), 6);
    // session.setAttribute("loginInfo", "asd");
    // end sample data

    if (cart == null || cart.size() == 0) { // 카트에 아무것도 없는 경우
      map.put("status", -1);
      map.put("message", "주문실패 : 장바구니가 비어있습니다.");
      result = mapper.writeValueAsString(map);
    } else {
      String loginedId = (String) session.getAttribute("loginInfo");
      if (loginedId == null) {
        map.put("status", 0);
        map.put("message", "주문실패 : 로그인되어있지 않습니다.");
        result = mapper.writeValueAsString(map);
      } else {
        OrderRepository repository = new OrderOracleRepository();
        OrderInfo info = new OrderInfo();
        info.setOrderId(loginedId);
        List<OrderLine> lines = new ArrayList<OrderLine>();
        for (Product p : cart.keySet()) {
          Integer quantity = cart.get(p);
          OrderLine line = new OrderLine();
          line.setOrderProduct(p);
          line.setOrderQuantity(quantity);
          lines.add(line);
        }
        info.setLines(lines);
        try {
          repository.insert(info);

        } catch (AddException e) {
          e.printStackTrace();
        }
        map.put("status", 1);
        map.put("message", "주문성공");
        result = mapper.writeValueAsString(map);
      }
    }

    out.print(result);
  }

}
