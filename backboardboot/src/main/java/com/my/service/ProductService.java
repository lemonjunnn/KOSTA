package com.my.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.repository.ProductRepository;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public List<Product> productlist() throws FindException {
    List<Product> products = (List<Product>) productRepository.findAll();
    if (products.size() != 0 && products != null) {
      return products;
    }
    throw new FindException("상품이 한개도 없습니다.");
  }


  public Product viewproduct(String productNo) throws FindException {
    Optional<Product> productOptional = productRepository.findById(productNo);
    if (productOptional.isPresent()) {
      Product product = productOptional.get();
      return product;
    }
    throw new FindException("찾으려는 상품이 없습니다. (상품번호 : " + productNo + ")");
  }

  public List<Product> search(String keyword) throws FindException {
    List<Product> products = (List<Product>) productRepository
        .findAllByProductNoContainingOrProductNameContaining(keyword, keyword);
    if (products.size() != 0 && products != null) {
      return products;
    }
    throw new FindException("찾으려는 상품이 한개도 없습니다. (검색어 : " + keyword + ")");
  }
}
