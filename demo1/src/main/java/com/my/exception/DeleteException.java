package com.my.exception;

public class DeleteException extends Exception {
  // constructor
  public DeleteException() { // default constructor
    super();
  }

  public DeleteException(String message) {
    super(message);
  }

}

