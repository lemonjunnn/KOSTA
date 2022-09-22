package com.my.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.CustomerRepository;

@Service()
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

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
