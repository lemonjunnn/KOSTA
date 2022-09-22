package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.my.dto.Customer;
import com.my.dto.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
  public Iterable<OrderInfo> findAllByCustomer(Customer customer);
}
