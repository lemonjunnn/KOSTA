package com.my.repository;

import org.springframework.data.repository.CrudRepository;
import com.my.dto.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
