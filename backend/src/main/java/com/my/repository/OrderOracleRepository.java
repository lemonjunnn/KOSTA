package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;

public class OrderOracleRepository implements OrderRepository {


  public void insert(OrderInfo info) throws AddException {
    Connection con = null;
    try {
      con = MyConnection.getConnection();
      insertInfo(con, info); // 같은 connection안에서 orderinfo, orderline 처리
      insertLines(con, info.getLines());

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(null, con);
    }
  }

  private void insertInfo(Connection con, OrderInfo info) throws SQLException {
    PreparedStatement pstmt = null;
    String insertInfoSQL =
        "INSERT INTO order_info(ORDER_NO,ORDER_ID,ORDER_DATE) VALUES (order_seq.NEXTVAL, ?, SYSDATE)";
    pstmt = con.prepareStatement(insertInfoSQL);
    pstmt.setString(1, info.getOrderId());
    pstmt.executeUpdate();
  }

  private void insertLines(Connection con, List<OrderLine> lines) throws SQLException {
    PreparedStatement pstmt = null;
    String insertLineSQL =
        "INSERT INTO order_line(ORDER_NO, ORDER_PRODUCT_NO,ORDER_QUANTITY) VALUES (order_seq.CURRVAL, ?, ?)";
    pstmt = con.prepareStatement(insertLineSQL);
    for (OrderLine line : lines) {
      String productNo = line.getOrderProduct().getProductNo();
      int orderQuantity = line.getOrderQuantity();
      pstmt.setString(1, productNo);
      pstmt.setInt(2, orderQuantity);
      pstmt.addBatch();
    }
    pstmt.executeBatch();
  }


  public List<OrderInfo> selectByOrderId(String orderId) throws FindException {
    List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection();
      // order_info

      String selectInfoSQL = "SELECT * FROM order_info WHERE order_id= ?";
      pstmt = con.prepareStatement(selectInfoSQL);
      pstmt.setString(1, orderId);
      rs = pstmt.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      List<String> listForAllElementsOfOrderInfo = new ArrayList<String>();
      while (rs.next()) {
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          String ElementOfOrderInfo = rs.getString(i);
          listForAllElementsOfOrderInfo.add(ElementOfOrderInfo);
        }
        String orderNoByString = listForAllElementsOfOrderInfo.get(0);
        int orderNo = Integer.parseInt(orderNoByString);
        Date orderDate = null;
        try {
          orderDate =
              new SimpleDateFormat("yyyy-mm-dd").parse(listForAllElementsOfOrderInfo.get(2));
        } catch (ParseException e) {
        }
        // order_line
        List<String> listFORAllElementsOfOrderLine = new ArrayList<String>();
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        OrderLine orderLine = null;
        String selectLineSQL = "SELECT * FROM order_line WHERE order_no= ?";
        pstmt = con.prepareStatement(selectLineSQL);
        pstmt.setString(1, orderNoByString);
        rs = pstmt.executeQuery();
        rsmd = rs.getMetaData();
        while (rs.next()) {
          for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String ElementOfOrderLine = rs.getString(i);
            listFORAllElementsOfOrderLine.add(ElementOfOrderLine);
          }
          String productNo = listFORAllElementsOfOrderLine.get(1);
          int orderQuantity = Integer.parseInt(listFORAllElementsOfOrderLine.get(2));

          // product
          List<String> listFORAllElementsOfProduct = new ArrayList<String>();
          Product product = null;
          String selectProductSQL = "SELECT * FROM product WHERE product_no= ?";
          pstmt = con.prepareStatement(selectProductSQL);
          pstmt.setString(1, productNo);
          rs = pstmt.executeQuery();
          rsmd = rs.getMetaData();
          while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
              String ElementOfProduct = rs.getString(i);
              listFORAllElementsOfProduct.add(ElementOfProduct);
            }
          }
          System.out.println("product size" + listFORAllElementsOfProduct.size());
          String productName = listFORAllElementsOfProduct.get(1);
          int productPrice = Integer.parseInt(listFORAllElementsOfProduct.get(2));
          System.out.println(productName);
          System.out.println(productPrice);
          String productInfo = listFORAllElementsOfProduct.get(3);
          Date productMfd = null;
          try {
            productMfd =
                new SimpleDateFormat("yyyy-mm-dd").parse(listFORAllElementsOfProduct.get(4));
          } catch (ParseException e) {
          }
          product = new Product(productNo, productName, productPrice, productInfo, productMfd);


          orderLine = new OrderLine(orderNo, product, orderQuantity);
          orderLines.add(orderLine);
        }


        OrderInfo orderInfo = new OrderInfo(orderNo, orderId, orderDate, orderLines);
        orderInfos.add(orderInfo);


      }
      System.out.println("orderInfos : " + orderInfos.toString());

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return orderInfos;
  }

}
