package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.service.ProductService;

@RestController
@RequestMapping("product/*")
public class ProductController {
  @Autowired
  private ProductService productService;


  @GetMapping(value = {"productlist"})
  public Map<String, Object> productlist() throws ServletException, IOException, FindException {
    Map<String, Object> map = new HashMap<String, Object>();

    // business logic 호출
    List<Product> products = productService.productlist();
    map.put("products", products);
    map.put("status", 1);
    map.put("message", "productlist 가져오기 성공");
    return map;
  }

  @GetMapping(value = {"viewproduct/{productNo}"})
  public Map<String, Object> viewProduct(@PathVariable String productNo)
      throws ServletException, IOException, FindException {
    Map<String, Object> map = new HashMap<String, Object>();

    Product product = productService.viewproduct(productNo);
    map.put("status", 1);
    map.put("message", "viewproduct succeed");
    map.put("product", product);

    return map;
  }

  @GetMapping(value = {"search", "search/{optionalKeyword}"})
  public Map<String, Object> search(
      @RequestParam(name = "keyword", required = true) Optional<String> optionalKeyword)
      throws ServletException, IOException, FindException {
    Map<String, Object> map = new HashMap<String, Object>();
    String keyword;
    if (optionalKeyword.isPresent()) {
      keyword = optionalKeyword.get();
    } else {
      keyword = "";
    }
    List<Product> products = productService.search(keyword);
    map.put("status", 1);
    map.put("message", "search succeed");
    map.put("product", products);

    return map;
  }
}

