package com.my.service;

import java.util.List;
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
    List<Product> products = productRepository.selectAll();
    return products;
  }

  public Product viewproduct(String productNo) throws FindException {
    Product product = productRepository.selectByProductNo(productNo);
    return product;
  }

  public List<Product> search(String keyword) throws FindException {
    List<Product> products = productRepository.selectByProductNoOrName(keyword);
    return products;
  }
}
