package com.food.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.food.dao.DaoFactory;
import com.food.dao.ObjectDao;
import com.food.model.Category;
import com.food.model.RequestData;
import com.food.response.Response;
import com.food.response.SimpleResponse;
import com.food.service.MenuService;
import com.food.util.ServiceUtil;

@Controller
@RequestMapping("/ffc/services/menu")
public class MenuServiceImpl implements MenuService {

	@Override
	@RequestMapping(value="/getMenuItems" ,method = RequestMethod.POST)
	public ResponseEntity<Response> getMenuItems(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		//queryParams.put("roleId", request.getUser_data().getRoleId());

		
		String entityName = "GET_MENU_ITEMS";

		List<Object> readObject = null;
		
		
		
		readObject = dao.readObjects(entityName, queryParams);
		
		Map<String,Category> menuList = null;
		
		//Comment this method if there is no need for category wise menuitems
		menuList = ServiceUtil.buildMenuItems(readObject);

		
		
		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), menuList);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	
	@Override
	@RequestMapping(value="/getItemDetails" ,method = RequestMethod.POST)
	public ResponseEntity<Response> getItemDetails(@RequestBody RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("itemId", request.getMenu_data().getItemId());

		
		String entityName = "GET_ITEM_DETAILS";

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
	@RequestMapping(value = "/isServiceAvailable", method = RequestMethod.POST)
	public ResponseEntity<Response> isServiceAvailable(@RequestBody RequestData request) {
		System.out.println("Start Serviceq");
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("zipCode", request.getMenu_data().getZipCode());

		String entityName = "IS_SERVICE_AVAILABLE";

		boolean isexists = false;

		List<Object> readObject;
		readObject = dao.readObjects(entityName, queryParams);

		if (!((Map)readObject.get(0)).get("count").equals("0"))
			isexists = true;

		SimpleResponse reponse = new SimpleResponse("" + true, request.getRequest_data_type(),
				request.getRequest_data_method(), isexists);

		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse, HttpStatus.OK);

		return entity;
	}
	
}
