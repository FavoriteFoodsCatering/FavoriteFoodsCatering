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
	
	
	@Override
	@RequestMapping(value="/addUser" ,method = RequestMethod.POST)
	public ResponseEntity<Response> addUser(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userName", request.getUser_data().getUserName());
		queryParams.put("password", request.getUser_data().getPassword());

		
		String entityName = "INSERT_USER";

		boolean insertFlag ;
		insertFlag = dao.insertUpdateObject(entityName, queryParams);

		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), insertFlag);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	
	@Override
	@RequestMapping(value="/checkUser" ,method = RequestMethod.POST)
	public ResponseEntity<Response> checkUser(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userName", request.getUser_data().getUserName());
	
		
		String entityName = "CHECK_USER";
		boolean isexists=false;
		
		List<Object> readObject ;
		readObject = dao.readObjects(entityName, queryParams);
		
		if(! ((Map)readObject.get(0)).get("count").equals("0"))
			isexists =true;

		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), isexists);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	

	@Override
	@RequestMapping(value="/createUserProfile" ,method = RequestMethod.POST)
	public ResponseEntity<Response> createUserProfile(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userId", request.getUser_data().getUserId());
		queryParams.put("creditCardNumber", request.getUser_data().getCreditCardNumber());
		queryParams.put("creditCardExpiryMonth", request.getUser_data().getCreditCardExpiryMonth());
		queryParams.put("creditCardExpiryYear", request.getUser_data().getCreditCardExpiryYear());
		queryParams.put("cvcCode", request.getUser_data().getCvcCode());
		queryParams.put("billtoFirstName", request.getUser_data().getBilltoFirstName());
		queryParams.put("billtoMiddlName", request.getUser_data().getBilltoMiddlName());
		queryParams.put("billtoLastName", request.getUser_data().getBilltoLastName());
		queryParams.put("billtoAddress1", request.getUser_data().getBilltoAddress1());
		queryParams.put("billtoAddress2", request.getUser_data().getBilltoAddress2());
		queryParams.put("billtocity", request.getUser_data().getBilltocity());
		queryParams.put("billtostate", request.getUser_data().getBilltostate());
		queryParams.put("billtozipCode", request.getUser_data().getBilltozipCode());
		

		
		String entityName = "CREATE_USER_PROFILE";

		boolean insertFlag ;
		insertFlag = dao.insertUpdateObject(entityName, queryParams);

		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), insertFlag);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	
	
}
