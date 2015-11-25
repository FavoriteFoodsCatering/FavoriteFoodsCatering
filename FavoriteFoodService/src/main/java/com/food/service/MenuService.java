package com.food.service;

import org.springframework.http.ResponseEntity;

import com.food.model.RequestData;
import com.food.response.Response;

public interface MenuService {
	
	public ResponseEntity<Response> getMenuItems(RequestData request);
	public ResponseEntity<Response> isServiceAvailable(RequestData request);
	public ResponseEntity<Response> getItemDetails(RequestData request);

}
