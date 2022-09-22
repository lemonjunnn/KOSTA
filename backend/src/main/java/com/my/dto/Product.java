package com.my.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Object of project --상품-- 상품번호 : "F0001"(food), "G0001"(goods), "D0001"(drink) - String 상품명 : "스콘"
 * "머그1", "머그2" "아메리카노" - String 가격 : 1000 2000, 2500 1000 - int 상품상세정보 : "~~" "~~", "~~" "~~" -
 * String 상품제조일자 : 2022/04/20 2022/04/21 2022/04/23 - Date
 * 
 *
 */
public class Product implements Serializable { // Serializable interface로 객체직렬화 가능
  // field
  private String productNo;
  private String productName;
  private int productPrice; // 객체직렬화에서 productPrice 변수는 제외됨
  private String productInfo;
  private Date productMfd;

  // constructor
  public Product() { // default constructor
  }

  public Product(String productNo, String productName, int productPrice, String productInfo,
      Date productMfd) { // full constructors
    this.productNo = productNo;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productInfo = productInfo;
    this.productMfd = productMfd;
  }

  @Override
  public String toString() {
    return "Product [productNo=" + productNo + ", productName=" + productName + ", productPrice="
        + productPrice + ", productInfo=" + productInfo + ", productMfd=" + productMfd + "]";
  }

  @Override
  public int hashCode() { // return hash code of productNo
    return Objects.hash(productNo);
  }

  @Override
  public boolean equals(Object obj) { // check productNo overlaps or not
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Product other = (Product) obj;
    return Objects.equals(productNo, other.productNo);
  }


  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(int productPrice) {
    this.productPrice = productPrice;
  }

  public String getProductInfo() {
    return productInfo;
  }

  public void setProductInfo(String productInfo) {
    this.productInfo = productInfo;
  }

  public Date getProductMfd() {
    return productMfd;
  }

  public void setProductMfd(Date productMfd) {
    this.productMfd = productMfd;
  }
}
