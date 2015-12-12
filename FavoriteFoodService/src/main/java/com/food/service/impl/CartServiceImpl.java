/**
 * 
 */
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
import com.food.model.RequestData;
import com.food.response.Response;
import com.food.response.SimpleResponse;
import com.food.service.CartService;
import com.food.util.ServiceUtil;

/**
 * @author bkagidal
 *
 */

@Controller
@RequestMapping("/ffc/services/cart")
public class CartServiceImpl implements CartService {

	
	@Override
	@RequestMapping(value="/addToCart" ,method = RequestMethod.POST)
	public ResponseEntity<Response> addToCart(@RequestBody  RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userId", request.getCart_data().getUserId());
		
		String entityName = "GET_CART_ID_N";
		List<Object> readObject =  dao.readObjects(entityName, queryParams);
		String exitCartId = ((Map)readObject.get(0)).get("cartId").toString();
		
		entityName = "GET_CART_ID_Y";
		List<Object> readObject1 =  dao.readObjects(entityName, queryParams);
		String ordCartId = ((Map)readObject1.get(0)).get("cartId").toString();
		
		String cartId = ServiceUtil.getCartId(exitCartId, ordCartId);
		System.out.println(cartId);
		
		entityName="INSERT_CART";
		
		queryParams.clear();
		queryParams.put("cartId", cartId);
		queryParams.put("itemId", request.getCart_data().getItemId());
		queryParams.put("userId", request.getCart_data().getUserId());
		queryParams.put("qty", request.getCart_data().getQty());
		
		boolean insertFlag ;
		insertFlag = dao.insertUpdateObject(entityName, queryParams);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", insertFlag);
		result.put("cartId", cartId);
		
		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), result);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}
	
	@RequestMapping(value="/checkOut" ,method = RequestMethod.POST)
	public ResponseEntity<Response> checkOut(@RequestBody  RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userId", request.getCart_data().getUserId());
		queryParams.put("cartId", request.getCart_data().getCartId());
		
		String entityName = "GET_TOTAL_AMT";

		List<Object> readObject = null;
		
		
		
		readObject = dao.readObjects(entityName, queryParams);
		
		String amount = ((Map)readObject.get(0)).get("totalAmt").toString();
		System.out.println(amount);
		
		entityName = "GET_NO_TAX_AMT";
		
		List readObject1 = dao.readObjects(entityName, queryParams);
		
		String noTaxAmt = ((Map)readObject1.get(0)).get("noTaxAmt").toString();
		System.out.println(noTaxAmt);
		
		double taxableAmt = Double.parseDouble(amount)-Double.parseDouble(noTaxAmt);
		
		double tax =(taxableAmt*8.75)/100;
		
		double totalAmount= Double.parseDouble(amount) + tax;
		
		System.out.println(totalAmount);
		
		Map<String,Object> result = new HashMap<String,Object>();
		//result.put("status", insertFlag);
		result.put("amount", Double.parseDouble(amount));
		result.put("tax", tax);
		result.put("totalAmount", totalAmount);
		
		
		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), result);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		
		
		
		return entity;
		
	}
	
	@Override
	@RequestMapping(value="/submitOrder" ,method = RequestMethod.POST)
	public ResponseEntity<Response> submitOrder(@RequestBody  RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userId", request.getCart_data().getUserId());
		
		String entityName = "SUBMIT_ORDER";
		
		queryParams.clear();
		queryParams.put("cartId", request.getCart_data().getCartId());
		queryParams.put("userId", request.getCart_data().getUserId());
		queryParams.put("orderDate", request.getCart_data().getOrderDate());
		queryParams.put("userPaymentId", request.getCart_data().getUserPaymentId());
		queryParams.put("itemId", request.getCart_data().getItemId());
		queryParams.put("quantity", request.getCart_data().getQty());
		queryParams.put("amount", request.getCart_data().getAmount());
		queryParams.put("netAmount", request.getCart_data().getNetAmount());
		queryParams.put("shiptoFirstName", request.getCart_data().getShiptoFirstName());
		queryParams.put("shiptoMiddlName", request.getCart_data().getShiptoMiddlName());
		queryParams.put("shiptoLastName", request.getCart_data().getShiptoLastName());
		queryParams.put("shiptoAddress1", request.getCart_data().getShiptoAddress1());
		queryParams.put("shiptoAddress2", request.getCart_data().getShiptoAddress2());
		queryParams.put("shiptoCity", request.getCart_data().getShiptoCity());
		queryParams.put("shiptoState", request.getCart_data().getShiptoState());
		queryParams.put("shiptoZipCode", request.getCart_data().getShiptoZipCode());
		queryParams.put("discountPercentage", request.getCart_data().getDiscountPercentage());
		
		
		boolean insertFlag ;
		insertFlag = dao.insertUpdateObject(entityName, queryParams);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", insertFlag);
		
		
		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), result);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
	}

}