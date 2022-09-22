package com.example.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.A;

@Repository
public interface TestRepository extends CrudRepository<A, String> {
  public List<A> findAsByA4(String a4);

  public List<A> findAsByA4Like(String keyword);
}
