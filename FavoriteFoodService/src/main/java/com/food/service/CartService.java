package com.food.service;

import org.springframework.http.ResponseEntity;

import com.food.model.RequestData;
import com.food.response.Response;

public interface CartService {
	
	public ResponseEntity<Response> addToCart(RequestData request);
	public ResponseEntity<Response> checkOut(RequestData request);
	public ResponseEntity<Response> submitOrder(RequestData request);

}
