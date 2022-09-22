package com.my.repository;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.exception.AddException;
import com.my.exception.FindException;

@Repository(value = "orderRepository")
public class OrderOracleRepository implements OrderRepository {
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  public void insert(OrderInfo orderInfo) throws AddException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      insertInfo(session, orderInfo);
      insertLines(session, orderInfo.getOrderLines());
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddException(e.getMessage());
    } finally {
      if (session != null) {
        session.close(); // connection을 끊는다는 의미가 아니며, DBCP의 connection pool에 돌려준다는 의미임 (안써도 됨)
      }
    }
  }

  private void insertInfo(SqlSession session, OrderInfo orderInfo) throws SQLException {
    session.insert("com.my.mapper.OrderMapper.insertOrderInfo", orderInfo);
  }

  private void insertLines(SqlSession session, List<OrderLine> orderLines) throws SQLException {
    for (OrderLine orderLine : orderLines) {
      session.insert("com.my.mapper.OrderMapper.insertOrderLine", orderLine);
    }
  }


  public List<OrderInfo> selectByOrderId(String orderId) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession();
      List<OrderInfo> infos =
          session.selectList("com.my.mapper.OrderMapper.selectByOrderId", orderId);

      if (infos.size() == 0) {
        throw new FindException("주문내역이 없습니다");
      }
      return infos;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException(e.getMessage());
    } finally {
      session.close();
    }
  }


}
