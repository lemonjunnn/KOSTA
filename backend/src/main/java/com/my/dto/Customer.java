package com.my.dto;

public class Customer {
  private String id;
  private String password;
  private String name;
  private String address;
  private int status;
  private int buildingno;

  public Customer() {}

  public Customer(String id, String password, String name, String address, int status,
      int buildingno) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.address = address;
    this.status = status;
    this.buildingno = buildingno;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getBuildingno() {
    return buildingno;
  }

  public void setBuildingno(int buildingno) {
    this.buildingno = buildingno;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", password=" + password + ", name=" + name + ", address="
        + address + ", status=" + status + ", buildingno=" + buildingno + "]";
  }
}
