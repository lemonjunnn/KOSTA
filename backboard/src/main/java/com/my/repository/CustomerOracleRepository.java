package com.my.repository;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;

@Repository(value = "customerRepository")
public class CustomerOracleRepository implements CustomerRepository {
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  public void insert(Customer customer) throws AddException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      session.insert("com.my.mapper.CustomerMapper.insert", customer);
    } catch (Exception e) {
      throw new AddException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }


  public Customer selectById(String id) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      Customer customer = session.selectOne("com.my.mapper.CustomerMapper.selectById", id);
      return customer;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("고객이 없습니다");
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }
}
