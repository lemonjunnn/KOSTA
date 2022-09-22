package com.my.exception;

/**
 * repository에 상품이 하나도 저장되지 않았을 경우에 발생하는 예외
 * @author asus
 */
public class FindException extends Exception{
	//constructor
	public FindException() { //default constructor
		super();
	}
	public FindException(String message) {
		super(message);
	}
	
}
