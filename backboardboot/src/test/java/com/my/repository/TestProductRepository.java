package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.my.dto.Product;

@SpringBootTest
class TestProductRepository {
  @Autowired
  ProductRepository productRepository;
  Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  void testFindAll() {
    List<Product> products = (List<Product>) productRepository.findAll();
    int expectedNumber = 2;
    assertEquals(expectedNumber, products.size());
  }

  @Test
  void testFindByProductNo() {
    String productNo = "C0001";
    Optional<Product> productOptional = productRepository.findById(productNo);
    assertTrue(productOptional.isPresent());
  }

  @Test
  void testFindAllByProductNoContainingOrProductNameContaining() {
    String keyword = "C";
    List<Product> products = (List<Product>) productRepository
        .findAllByProductNoContainingOrProductNameContaining(keyword, keyword);
    int expectedNumber = 4;
    assertEquals(expectedNumber, products.size());
  }
}
