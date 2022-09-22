package com.my.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.dto.Customer;
import com.my.dto.OrderInfo;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.OrderInfoRepository;

@Service
public class OrderService {
  @Autowired
  private OrderInfoRepository orderInfoRepository;

  public void addOrder(OrderInfo orderInfo) throws AddException {
    orderInfoRepository.save(orderInfo);
  }

  public List<OrderInfo> viewOrder(String orderId) throws FindException {
    Customer customer = new Customer();
    customer.setId(orderId);
    List<OrderInfo> orderInfos = (List<OrderInfo>) orderInfoRepository.findAllByCustomer(customer);
    if (orderInfos.size() == 0 || orderInfos == null) {
      throw new FindException("주문내역이 없습니다");
    }
    return orderInfos;
  }
}
