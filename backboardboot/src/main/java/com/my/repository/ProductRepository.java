package com.my.repository;

import org.springframework.data.repository.CrudRepository;
import com.my.dto.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
  public Iterable<Product> findAllByProductNoContainingOrProductNameContaining(String keyword1,
      String keyword2);
}
