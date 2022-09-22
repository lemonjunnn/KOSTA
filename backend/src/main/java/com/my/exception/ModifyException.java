package com.my.exception;

/**
 * ProductManagerConsole에서 상품 내용을 수정하다 발생하는 예외
 * @author asus
 */
public class ModifyException extends Exception{
	//constructor
	public ModifyException() { //default constructor
		super();
	}
	public ModifyException(String message) {
		super(message);
	}
	
}

