package com.my.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.my.dto.Customer;

@SpringBootTest
class TestCustomerRepository {
  @Autowired
  CustomerRepository customerRepository;
  Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  void testFindById() {
    String id = "id1";
    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    assertTrue(optionalCustomer.isPresent());
    Customer customer = optionalCustomer.get();
    String expectedName = "name";
    assertEquals(expectedName, customer.getName());
  }

  @Test
  void testSave() {
    Customer customer = new Customer();
    customer.setId("id3");
    customer.setPassword("pwd3");
    customer.setName("name3");
    customer.setAddress("address3");
    customerRepository.save(customer);
  }
}
