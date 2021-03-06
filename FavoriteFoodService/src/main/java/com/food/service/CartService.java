package com.food.service;

import org.springframework.http.ResponseEntity;

import com.food.model.RequestData;
import com.food.response.Response;

public interface CartService {
	
	public ResponseEntity<Response> addToCart(RequestData request);
	public ResponseEntity<Response> checkOut(RequestData request);
	public ResponseEntity<Response> submitOrder(RequestData request);
	public ResponseEntity<Response> previewMenu(RequestData request);
	//public ResponseEntity<Response> chargeCreditCard(RequestData request);
	public ResponseEntity<Response> chargeCreditCardWithSquare(RequestData request);
	public ResponseEntity<Response> listSquareLocation(RequestData request);
	public ResponseEntity<Response> createCustomer(RequestData request);

}
