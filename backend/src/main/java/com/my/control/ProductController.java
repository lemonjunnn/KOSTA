package com.my.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.service.ProductServlce;

public class ProductController implements Controller {

  private ProductServlce productService;

  public ProductController() {
    this.productService = new ProductServlce();
  }

  public String execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String servletPath = request.getServletPath();
    if ("/productlist".equals(servletPath)) {
      return productlist(request, response);
    } else if ("/viewproduct".equals(servletPath)) {
      return viewProduct(request, response);
    }
    return null;
  }

  private String productlist(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();

    // business logic 호출
    List<Product> products = new ArrayList<Product>();
    try {
      products = productService.productlist();
      map.put("products", products);
      map.put("status", 1);
      map.put("message", "productlist 가져오기 성공");

    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "productlist 가져오기 실패. " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "productlist 가져오기 실패. " + e.getMessage());
      e.printStackTrace();
    }

    // 결과 출력
    String result = mapper.writeValueAsString(map);
    System.out.println("productlist() in ProductController : " + result);
    return result;
  }


  private String viewProduct(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    String productNo = request.getParameter("product_no");

    try {
      Product product = productService.viewproduct(productNo);
      map.put("status", 1);
      map.put("message", "viewproduct succeed");
      map.put("product", product);
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "viewproduct failed. " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "viewproduct failed. " + e.getMessage());
      e.printStackTrace();
    }
    // 가입 성공여부 출력 {"status":1 - 성공, 2 - 실패)
    String result = mapper.writeValueAsString(map);
    System.out.println("viewProduct() in ProductController : " + result);
    return result;
  }
}

