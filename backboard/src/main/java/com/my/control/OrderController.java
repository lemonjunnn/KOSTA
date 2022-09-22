package com.my.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.OrderService;

@Controller
public class OrderController {
  @Autowired
  private OrderService orderService;

  @GetMapping("addorder")
  @ResponseBody
  public Map<String, Object> addOrder(HttpSession session) throws ServletException, IOException {
    Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
    if (cart == null) {
      cart = new HashMap<Product, Integer>();
      session.setAttribute("cart", cart);
    }
    Product p1 = new Product();
    p1.setProductNo("C0001");
    Product p2 = new Product();
    p2.setProductNo("C0002");
    cart.put(p1, 2);
    cart.put(p2, 4);
    session.setAttribute("loginInfo", "rkdi");

    Map<String, Object> map = new HashMap<>();
    if (cart == null || cart.size() == 0) {// 장바구니없거나 비어있는 경우
      map.put("status", -1);
      map.put("msg", "주문실패: 장바구니가 비었습니다");
    } else {
      // 로그인된 사용자인가 확인
      String loginedId = (String) session.getAttribute("loginInfo");
      if (loginedId == null) { // 로그인 안한 사용자인 경우
        map.put("status", 0);
        map.put("message", "로그인하세요");
      } else {
        OrderInfo info = new OrderInfo();
        info.setOrderId(loginedId);
        List<OrderLine> lines = new ArrayList<>();
        for (Product p : cart.keySet()) {
          Integer quantity = cart.get(p);
          OrderLine line = new OrderLine();
          line.setOrderProduct(p);
          line.setOrderQuantity(quantity);
          lines.add(line);
        }
        info.setOrderLines(lines);
        try {
          orderService.addOrder(info);// 주문추가
          session.removeAttribute("cart");// 장바구니 비우기
          map.put("status", 1);
          map.put("messsge", "주문성공");
        } catch (AddException e) {
          e.printStackTrace();
        }
      }
    }
    return map;
  }

  @GetMapping("vieworder")
  @ResponseBody
  public Object viewOrder(HttpSession session) {
    session.setAttribute("loginInfo", "rkdi");
    // ---샘플 로그인아이디 ----

    Map<String, Object> map = new HashMap<>();
    String loginedId = (String) session.getAttribute("loginInfo");
    if (loginedId == null) {
      map.put("status", 0);
      map.put("message", "로그인하세요");
      return map;
    } else {
      try {
        List<OrderInfo> infos = orderService.viewOrder(loginedId);
        return infos;
      } catch (FindException e) {
        e.printStackTrace();
        map.put("status", -1);
        map.put("message", e.getMessage());
        return map;
      }
    }
  }
}
