package com.food.response;


public class SimpleResponse extends Response{

	
	public SimpleResponse(String status, Object msg) {
		super( status, msg);
	}
	
	public SimpleResponse(String status, Object msg,String count) {
		super( status, msg,count);
	}
	
	public SimpleResponse(String status,String request_data_type,String request_data_method, Object message) {
		super( status, request_data_type,request_data_method,message);
	}
	
	public SimpleResponse(String status,String request_data_type,String request_data_method, Object message,String count) {
		super( status, request_data_type,request_data_method,message,count);
	}
	
			
}
