package com.my.service;

import java.util.Optional;
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
    customerRepository.save(customer);
  }

  public Customer idDuplicationCheck(String id) throws FindException {
    Optional<Customer> customerOptional = customerRepository.findById(id);
    if (!customerOptional.isPresent()) {
      return null;
    }
    throw new FindException("이미 사용중인 id입니다.");
  }

  public Customer login(String id, String password) throws FindException {
    Optional<Customer> customerOptional = customerRepository.findById(id);
    if (customerOptional.isPresent()) {
      Customer customer = customerOptional.get();
      String passwordInRepository = customer.getPassword();
      if (password.equals(passwordInRepository)) {
        return customer;
      }
      throw new FindException("비밀번호가 일치하지 않습니다.");
    }
    throw new FindException("없는 id입니다.");
  }
}
