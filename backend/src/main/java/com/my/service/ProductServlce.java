package com.my.service;

import java.util.List;
import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.repository.ProductOracleRepository;
import com.my.repository.ProductRepository;

public class ProductServlce {
  private ProductRepository productRepository;

  public ProductServlce() {
    this.productRepository = new ProductOracleRepository();
  }

  public List<Product> productlist() throws FindException {
    List<Product> products = productRepository.selectAll();
    return products;
  }

  public Product viewproduct(String productNo) throws FindException {
    Product product = productRepository.selectByProductNo(productNo);
    return product;
  }
}
