package com.transport.transit.admin.service;

import com.transport.transit.admin.controller.ProductNotFound;
import com.transport.transit.admin.exception.CartItemNotFound;


public interface CartService {
	
	public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound;
	public Cart getCartProduct(String token);
	public Cart removeProductFromCart(CartDTO cartDto,String token) throws ProductNotFound;
//	public Cart changeQuantity(Product product,Customer customer,Integer quantity);
	
	public Cart clearCart(String token);
	
}
