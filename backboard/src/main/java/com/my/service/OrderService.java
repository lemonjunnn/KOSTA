package com.my.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.dto.OrderInfo;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.OrderRepository;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  public void addOrder(OrderInfo orderInfo) throws AddException {
    orderRepository.insert(orderInfo);
  }

  public List<OrderInfo> viewOrder(String orderId) throws FindException {
    return orderRepository.selectByOrderId(orderId);
  }

}
