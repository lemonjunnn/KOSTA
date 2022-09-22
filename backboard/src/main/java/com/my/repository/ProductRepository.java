package com.my.repository;

import java.util.List;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

/**
 * Interface of ProductRepositories
 * 
 * @author asus
 *
 */
public interface ProductRepository {
  /**
   * add product into repository
   * 
   * @param Product product
   * @throws AddException if there is duplication or don't keep input rule, occurs this exception
   */
  public void insert(Product product) throws AddException;

  /**
   * modify product which is already deposited in repository
   * 
   * @param Product product
   * @param String modifiedProductName
   * @param String modifiedProductPrice
   * @throws ModifyException if there is duplication or don't keep input rule, occurs this exception
   */
  public void update(Product product) throws ModifyException;

  /**
   * select all products in the repository
   * 
   * @return List
   * @throws FindException
   */
  public List<Product> selectAll() throws FindException;

  /**
   * 
   * @param String productNo
   * @return Product
   * @throws FindException
   */
  public Product selectByProductNo(String productNo) throws FindException;

  public List<Product> selectByProductNoOrName(String keyword) throws FindException;

}
