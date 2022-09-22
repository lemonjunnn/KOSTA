package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.my.dto.Product;
import com.my.exception.FindException;


@RunWith(SpringRunner.class)
// Spring 컨테이너용 XML파일 설정
@ContextConfiguration(
    locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
public class ProductOracleRepositoryTest {
  private Logger logger = Logger.getLogger(getClass());

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testSelectByProductNo() throws FindException {
    // fail("Not yet implemented");
    String productNo = "C0001";
    String expectedProductName = "아메리카노";
    int expectedProductPrice = 2345;
    Product product = productRepository.selectByProductNo(productNo);
    assertEquals(expectedProductName, product.getProductName());

    assertEquals(expectedProductPrice, product.getProductPrice());
  }

  @Test
  public void testSelectAll() throws FindException {
    // fail("Not yet implemented");
    int expectedSize = 126;
    List<Product> products = productRepository.selectAll();
    assertTrue(expectedSize == products.size());
  }
}
