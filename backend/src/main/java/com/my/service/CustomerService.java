package com.my.service;

import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.CustomerOracleRepository;
import com.my.repository.CustomerRepository;

public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService() {
    this.customerRepository = new CustomerOracleRepository();
  }

  public void signup(Customer customer) throws AddException {
    customerRepository.insert(customer);
  }

  public Customer idDuplicationCheck(String id) throws FindException {
    Customer customer = customerRepository.selectById(id);
    return customer;
  }

  public Customer login(String id, String password) throws FindException {
    Customer customer = customerRepository.selectById(id);
    if (customer == null || !customer.getPassword().equals(password)) {
      throw new FindException();
    }
    return customer;
  }


}
