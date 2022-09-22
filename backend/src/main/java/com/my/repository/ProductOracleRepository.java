package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;

public class ProductOracleRepository implements ProductRepository {

  public void insert(Product product) throws AddException {
    Connection con = null;
    PreparedStatement pstmt = null;
    String insertSQL =
        "INSERT INTO product (product_no, product_name, product_price, product_info, product_mfd) VALUES( ? , ? , ? , ? , ? )";
    try {
      con = MyConnection.getConnection();
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setString(1, product.getProductNo());
      pstmt.setString(2, product.getProductName());
      pstmt.setInt(3, product.getProductPrice());
      pstmt.setString(4, product.getProductInfo());
      pstmt.setDate(5, new java.sql.Date(product.getProductMfd().getTime()));
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new AddException(e.getMessage());
    } finally {
      MyConnection.close(pstmt, con);
      System.out.println("DB와의 연결이 해제되었습니다.");
    }
  }

  public void modify(String modifiedProductNo, String modifiedProductName, int modifiedProductPrice)
      throws AddException {
    // TODO Auto-generated method stub

  }

  public List<Product> selectAll() throws FindException {
    List<Product> products = new ArrayList<Product>();

    // Database
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectProductAllSQL = "SELECT * FROM product ORDER BY product_no ASC";
    try {
      con = MyConnection.getConnection(); // �삁�쟾�뿉 DB�뿰寃곗슜�쑝濡� 留뚮뱺 MyConnection class 瑜� �솢�슜�븿
      pstmt = con.prepareStatement(selectProductAllSQL);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String productNo = rs.getString("product_no");
        String productName = rs.getString("product_name");
        int productPrice = rs.getInt("product_price");
        String productInfo = rs.getString("product_info");
        java.sql.Date productMfd = rs.getDate("product_mfd");

        Product product =
            new Product(productNo, productName, productPrice, productInfo, productMfd);
        products.add(product);
      }
      if (products.size() == 0) {
        throw new FindException("상품이 없습니다");
      }
      return products;
    } catch (SQLException e) {
      throw new FindException(e.getMessage());
    } finally {
      MyConnection.close(rs, pstmt, con);
      System.out.println("DB와의 연결이 해제되었습니다.");
    }
  }

  public Product selectByProductNo(String productNo) throws FindException {
    // Database
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectProductNoSQL = "SELECT * FROM product WHERE product_no = ?";
    try {
      con = MyConnection.getConnection();
      pstmt = con.prepareStatement(selectProductNoSQL);
      pstmt.setString(1, productNo);
      rs = pstmt.executeQuery();
      System.out.println("productNo : " + productNo);
      if (rs.next()) {
        String productName = rs.getString("product_name");
        int productPrice = rs.getInt("product_price");
        String productInfo = rs.getString("product_info");
        java.sql.Date productMfd = rs.getDate("product_mfd");
        Product product =
            new Product(productNo, productName, productPrice, productInfo, productMfd);
        return product;
      } else {
        throw new FindException("상품번호를 찾을 수 없습니다");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FindException(e.getMessage());
    } finally {
      MyConnection.close(rs, pstmt, con);
      System.out.println("DB와의 연결이 해제되었습니다.");
    }
  }

  public List<Product> selectByProductNoOrName(String keyword) throws FindException {
    // TODO Auto-generated method stub
    return null;
  }



}
