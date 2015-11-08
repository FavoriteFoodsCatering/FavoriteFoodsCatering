package com.food.model;

public class RequestData {

	private String request_data_type;
	private String request_data_method;
	private User user_data;

	public String getRequest_data_type() {
		return request_data_type;
	}

	public void setRequest_data_type(String request_data_type) {
		this.request_data_type = request_data_type;
	}

	public String getRequest_data_method() {
		return request_data_method;
	}

	public void setRequest_data_method(String request_data_method) {
		this.request_data_method = request_data_method;
	}

	public User getUser_data() {
		return user_data;
	}

	public void setUser_data(User user_data) {
		this.user_data = user_data;
	}

	

}
