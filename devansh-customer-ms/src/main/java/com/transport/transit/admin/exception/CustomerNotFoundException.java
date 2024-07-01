package com.transport.transit.admin.exception;

public class CustomerNotFoundException extends RuntimeException{
	
	public CustomerNotFoundException() {
		super();
	}
	
	public CustomerNotFoundException(String message) {
		super(message);
	}
	
}
