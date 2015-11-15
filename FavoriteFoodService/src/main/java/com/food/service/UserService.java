package com.food.service;

import org.springframework.http.ResponseEntity;

import com.food.model.RequestData;
import com.food.response.Response;

public interface UserService {

		public ResponseEntity<Response> getUsers(RequestData request);
		public ResponseEntity<Response> addUser(RequestData request);
		public ResponseEntity<Response> checkUser(RequestData request);
		public ResponseEntity<Response> createUserProfile(RequestData request);
		
	
}
