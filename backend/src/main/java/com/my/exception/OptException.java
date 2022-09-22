package com.my.exception;

/**
 * ProductManagerConsole에서 menu 에 없는 항목을 선택했을때 발생하는 예외
 * @author asus
 */
public class OptException extends Exception{
	//constructor
	public OptException() { //default constructor
		super();
	}
	public OptException(String message) {
		super(message);
	}
	
}

