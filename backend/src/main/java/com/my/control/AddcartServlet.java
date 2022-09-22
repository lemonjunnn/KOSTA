package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.my.dto.Product;


public class AddcartServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public AddcartServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();

    String productNo = request.getParameter("product_no");
    System.out.println("productNo : " + productNo);
    String quantityByString = request.getParameter("quantity");
    Integer quantity = Integer.parseInt(quantityByString);

    Map<Product, Integer> cart = (Map) session.getAttribute("cart");
    if (cart == null) {
      cart = new HashMap<Product, Integer>();
      session.setAttribute("cart", cart);
    }
    Product p = new Product();
    p.setProductNo(productNo);
    Integer oldQuantity = cart.get(p);
    if (oldQuantity != null) { // 상품이 있는 경우
      quantity += oldQuantity;
    }
    cart.put(p, quantity);
    // p값이 같은 key:value 쌍이 있으면, 거기에 새롭게 덮어씀. 아닌 경우 새로운 메모리 생성 (Product의 hashCode(), equals() 때문)



    // boolean exist = false; //상품의 존재여부 확인
    // outer : for (Map<Product, Integer> map : cart) {
    // Set<Product> products = map.keySet(); // 장바구니에 들어있는 상품들
    // inner : for (Product p : products) {
    // if (p.getProductNo().equals(productNo)) { // 이번에 추가한 상품이 장바구니에 이미 있는 경우, 수량만 증가
    // int oldQuantity = map.get(p);
    // map.put(p, oldQuantity + Integer.parseInt(quantity));
    // exist = true;
    // break outer;
    // }
    // }
    // // 이번에 추가한 상품이 장바구니에 없는 경우, 장바구니에 추가
    // if (!exist) {
    // Product p = new Product();
    // p.setProductNo(productNo);
    // map.put(p, Integer.parseInt(quantity));
    // cart.add(map);
    // }
    // }

    // 결과 출력
    System.out.println("장바구니 목록수(AddcartServlet) : " + cart.size() + "\n" + cart.toString());
  }
}
