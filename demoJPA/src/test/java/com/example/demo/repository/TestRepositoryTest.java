package com.example.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.entity.A;
import com.example.demo.entity.B;
import com.example.demo.entity.M;

@SpringBootTest
// @Transactional
class TestRepositoryTest {

  @Autowired
  private TestRepository repository;
  @Autowired
  private MRepository mRepository;
  @Autowired
  private BRepository bRepository;
  Logger logger = LoggerFactory.getLogger(getClass());

  // @Test
  void testSave() {
    A a = new A();
    a.setA1("a1");
    a.setA2(new BigDecimal(1.2));
    repository.save(a); // persist()호출 -> SELECT -> 반환행없음 -> INSERT 구문 실행

    A a2 = new A();
    a2.setA1("a1");
    a2.setA2(new BigDecimal(1.3));
    a2.setA4("turtle");
    repository.save(a2);// persist()호출 -> SELECT -> 반환행있음 -> UPDATE구문 실행
  }

  // @Test
  void testFindById() {
    Optional<A> optA = repository.findById("a6");
    assertTrue(optA.isPresent());
    String expectedA4 = "turtle";
    A a = optA.get();
    assertEquals(expectedA4, a.getA4());
  }

  // @Test
  void testDeleteById() {
    repository.deleteById("a1");
    Optional<A> optA = repository.findById("a1");
    assertFalse(optA.isPresent());
  }

  // @Test
  void testFindAll() {
    Iterable<A> list = repository.findAll();
    logger.error(list.toString());
  }

  // @Test
  void testFindByA4() {
    List<A> list = repository.findAsByA4("turtle");
    logger.error(list.toString());
  }

  // @Test
  void testCount() {
    long number = repository.count();
    logger.error("total row : " + number);
  }

  // @Test
  void testFindByA1Like() {
    List<A> list = repository.findAsByA4Like("%tu%");
    logger.error(list.toString());
  }

  // @Test
  void testSaveMB() {
    for (int i = 0; i < 10; i++) {
      M m = new M();
      m.setId("id" + i);
      m.setName("name" + i);
      m.setRoll("roll" + i);
      mRepository.save(m); // persist()호출 -> SELECT -> 반환행없음 -> INSERT 구문 실행
      assertEquals("id" + i, m.getId());
      B b = new B();
      b.setTitle("title");
      b.setM(m);
      bRepository.save(b); // persist()호출 -> SELECT -> 반환행없음 -> INSERT 구문 실행
    }
  }

  @Test
  void testFindAllMB() {
    Iterable<M> mlist = mRepository.findAll();
    logger.error(mlist.toString());
    Iterable<B> blist = bRepository.findAll();
    logger.error(blist.toString());
  }

  @Test
  void testDeleteAllMB() {
    bRepository.deleteAll();
    mRepository.deleteAll();
    Iterable<M> mlist = mRepository.findAll();
    logger.error("delete" + mlist.toString());
    Iterable<B> blist = bRepository.findAll();
    logger.error("delete" + blist.toString());

  }
}
