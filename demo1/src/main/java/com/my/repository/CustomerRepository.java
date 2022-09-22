package com.my.repository;

import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;

public interface CustomerRepository {
  public void insert(Customer customer) throws AddException;

  public Customer selectById(String id) throws FindException;
}
