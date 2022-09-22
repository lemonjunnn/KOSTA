package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.my.dto.Customer;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;

@SpringBootTest
class OrderInfoRepositoryTest {
  @Autowired
  private OrderInfoRepository repository;

  // @Test
  void testInsert() {
    OrderInfo info = new OrderInfo();
    Customer customer = new Customer();
    info.setOrderNo(new Long(555));
    customer.setId("id1");
    info.setCustomer(customer);
    List<OrderLine> lines = new ArrayList<>();

    Product proudct1 = new Product();
    proudct1.setProductNo("C0001");
    OrderLine line1 = new OrderLine();
    line1.setOrderInfo(info);
    line1.setOrderQuantity(new Long(1));
    line1.setOrderProduct(proudct1);
    lines.add(line1);

    Product proudct2 = new Product();
    proudct2.setProductNo("C0002");
    OrderLine line2 = new OrderLine();
    line2.setOrderInfo(info);
    line2.setOrderQuantity(new Long(2));
    line2.setOrderProduct(proudct2);
    lines.add(line2);



    info.setOrderLines(lines);
    repository.save(info);
  }

  @Test
  void testFindAllByOrderId() {
    String orderId = "id1";
    Customer customer = new Customer();
    customer.setId(orderId);
    List<OrderInfo> optOrderInfos = (List<OrderInfo>) repository.findAllByCustomer(customer);
    int expectedSize = 2;
    assertEquals(expectedSize, optOrderInfos.size());
    OrderInfo orderInfo1 = optOrderInfos.get(0);
    OrderInfo orderInfo2 = optOrderInfos.get(1);

    Long expectedOrderNo1 = new Long(3);
    Long expectedOrderNo2 = new Long(4);
    Long orderNo1 = orderInfo1.getOrderNo();
    Long orderNo2 = orderInfo2.getOrderNo();
    assertEquals(expectedOrderNo1, orderNo1);
    assertEquals(expectedOrderNo2, orderNo2);
  }

  // @Test
  void testFindById() {
    Optional<OrderInfo> optOrderInfo = repository.findById(new Long(3));
    assertTrue(optOrderInfo.isPresent());
    OrderInfo info = optOrderInfo.get();

    String id = info.getCustomer().getId();

    List<OrderLine> lines = info.getOrderLines();
    OrderLine line = lines.get(0);
    Product product = line.getOrderProduct();
    Long quantity = line.getOrderQuantity();
    assertEquals("id1", id);
    assertEquals("C0001", product.getProductNo());
    assertEquals(new Long(1), quantity);
  }

}
