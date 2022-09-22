package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;

public class CustomerOracleRepository implements CustomerRepository {

  public void insert(Customer customer) throws AddException {
    Connection con = null;
    PreparedStatement pstmt = null;
    String insertSQL =
        "INSERT INTO customer (id, password, name, address, status, buildingno) VALUES( ? , ? , ? , ? , 1 , ? )";
    try {
      con = MyConnection.getConnection();
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setString(1, customer.getId());
      pstmt.setString(2, customer.getPassword());
      pstmt.setString(3, customer.getName());
      pstmt.setString(4, customer.getAddress());
      pstmt.setInt(5, customer.getBuildingno());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new AddException(e.getMessage());
    } finally {
      MyConnection.close(pstmt, con);
      System.out.println("DB와의 연결이 해제되었습니다.");
    }
  }

  public Customer selectById(String id) throws FindException {
    Customer customer = new Customer();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectSQL = "SELECT * FROM customer WHERE id = ? ";
    try {
      con = MyConnection.getConnection();
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        customer.setId(id);
        customer.setPassword(rs.getString("password"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setStatus(rs.getInt("status"));
        customer.setBuildingno(rs.getInt("buildingno"));
        return customer;
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new FindException(e.getMessage());
    } finally {
      MyConnection.close(rs, pstmt, con);
      System.out.println("DB와의 연결이 해제되었습니다.");
    }
  }
}
