package com.food.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.food.dao.DaoFactory;
import com.food.dao.ObjectDao;
import com.food.model.RequestData;
import com.food.response.Response;
import com.food.response.SimpleResponse;
import com.food.service.UserService;

@Controller
@RequestMapping("/ffc/services/user")
public class UserServiceImpl implements UserService {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	@RequestMapping(value="/getUsers" ,method = RequestMethod.POST)
	public ResponseEntity<Response> getUsers(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("roleId", request.getUser_data().getRoleId());

		
		String entityName = "GET_USERS";

		List<Object> readObject = null;
		readObject = dao.readObjects(entityName, queryParams);

		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), readObject);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	

	
}
