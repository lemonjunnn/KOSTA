package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.my.dto.Product;
import com.my.dto.ResultBean;
import com.my.exception.FindException;
import com.my.service.ProductService;

@RestController
@RequestMapping("product/*")
public class ProductController {
  @Autowired
  private ProductService productService;


  @GetMapping(value = {"productlist"})
  public ResultBean<List<Product>> productlist() throws ServletException, IOException {
    ResultBean<List<Product>> resultBean = new ResultBean<List<Product>>();

    // business logic 호출
    try {
      List<Product> products = productService.productlist();
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
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "viewproduct")
  public Map<String, Object> viewProduct(
      @RequestParam(name = "product_no", required = true) String productNo)
      throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();

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
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "search")
  public Map<String, Object> search(@RequestParam(name = "keyword", required = true) String keyword)
      throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();

    try {
      List<Product> products = productService.search(keyword);
      map.put("status", 1);
      map.put("message", "search succeed");
      map.put("product", products);
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "search failed. " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "search failed. " + e.getMessage());
      e.printStackTrace();
    }
    return map;
  }
}

