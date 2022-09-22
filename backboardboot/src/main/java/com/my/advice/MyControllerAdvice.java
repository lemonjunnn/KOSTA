package com.my.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {
  // @ExceptionHandler(FindException.class)
  // public ResponseEntity<String> findEceptionHandle(FindException e) {
  // return new ResponseEntity<>("※" + e.getMessage() + "※", HttpStatus.INTERNAL_SERVER_ERROR);
  // }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandle(Exception e) {
    return new ResponseEntity<>("※" + e.getMessage() + "※", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<String> validException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
