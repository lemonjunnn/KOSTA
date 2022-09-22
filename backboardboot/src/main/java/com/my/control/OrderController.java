package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;
import com.my.dto.ResultBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.OrderService;

@RestController
@RequestMapping("order/*")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping("addorder")
  public ResponseEntity<?> addOrder(@RequestBody OrderInfo orderInfo, HttpSession session)
      throws ServletException, IOException, AddException {
    Map<Product, Long> cart = (Map<Product, Long>) session.getAttribute("cart");
    if (cart == null) {
      cart = new HashMap<Product, Long>();
      session.setAttribute("cart", cart);
    }
    List<OrderLine> orderLines = orderInfo.getOrderLines();
    for (OrderLine orderLine : orderLines) {
      Product product = orderLine.getOrderProduct();
      Long orderQuantity = orderLine.getOrderQuantity();
      cart.put(product, orderQuantity);
    }

    if (cart == null || cart.size() == 0) {// 장바구니없거나 비어있는 경우
      return new ResponseEntity<>("장바구니가 비어있습니다.", HttpStatus.BAD_REQUEST);
    } else {
      // 로그인된 사용자인가 확인
      String loginedId = (String) session.getAttribute("loginInfo");
      if (loginedId == null) { // 로그인 안한 사용자인 경우
        return new ResponseEntity<>("로그인 하세요.", HttpStatus.BAD_REQUEST);
      } else {
        orderService.addOrder(orderInfo);// 주문추가
        session.removeAttribute("cart");// 장바구니 비우기
        return new ResponseEntity<>("주문성공", HttpStatus.OK);
      }
    }
  }

  @GetMapping("vieworder")
  public ResultBean<List<OrderInfo>> viewOrder(HttpSession session) throws FindException {
    ResultBean<List<OrderInfo>> resultBean = new ResultBean<List<OrderInfo>>();
    String loginedId = (String) session.getAttribute("loginInfo");
    if (loginedId == null) {
      resultBean.setStatus(0);
      resultBean.setMessage("로그인하세요.");
    } else {
      List<OrderInfo> orderInfos = orderService.viewOrder(loginedId);
      resultBean.setStatus(1);
      resultBean.setMessage("주문을 불러왔습니다.");
      resultBean.setT(orderInfos);
    }
    return resultBean;
  }
}
