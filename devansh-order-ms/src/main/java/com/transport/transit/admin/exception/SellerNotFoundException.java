package com.transport.transit.admin.exception;

public class SellerNotFoundException extends RuntimeException{
	
	public SellerNotFoundException() {
		super();
	}
	
	
	public SellerNotFoundException(String message) {
		super(message);
	}
}
