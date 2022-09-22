package com.my.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestOrderLineRepository {
  @Autowired
  OrderInfoRepository orderInfoRepository;
  @Autowired
  ProductRepository productRepository;
  Logger logger = LoggerFactory.getLogger(getClass());

  // @Test
  // void testInsert() {
  // OrderLinePk orderLinePk = new OrderLinePk();
  // OrderInfo orderInfo = orderInfoRepository.findById(new Long(4)).get();
  // orderLinePk.setOrderInfo(orderInfo);
  // Product product = new Product();
  // String productNo = "C0003";
  // product.setProductNo(productNo);
  // orderLinePk.setOrderProduct(product);
  //
  // Long orderQuantity = new Long(14);
  // OrderLine orderLine = new OrderLine();
  // orderLine.setOrderInfo(orderInfo);
  // orderLine.setOrderProduct(product);
  // orderLine.setOrderQuantity(orderQuantity);
  // orderLineRepository.save(orderLine);
  // }

  @Test
  void testFindById() {
    // OrderLinePk orderLinePk = new OrderLinePk();
    // OrderInfo orderInfo = orderInfoRepository.findById(new Long(3)).get();
    // Product product = productRepository.findById("C0001").get();
    // orderLinePk.setOrderInfo(orderInfo);
    // orderLinePk.setOrderProduct(product);
    // Optional<OrderLine> orderLine = orderLineRepository.findById(orderLinePk);
    // assertTrue(orderLine.isPresent());
  }
}
