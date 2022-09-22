package com.my.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class LombokTest {
  // @Autowired
  // Board board;

  // @Test(expected = NullPointerException.class)
  // public void test() {
  // board.setBoardPostNo(1);
  // board.setBoardId(null);
  // System.out.println(board.toString());
  // }

  @Test
  public void test2() {
    String getVersion = org.springframework.core.SpringVersion.getVersion();

    System.out.println(getVersion);
  }
}
