/**
 * 
 */
package com.food.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.food.model.Cart;
import com.food.model.MenuItem;
import com.food.model.RequestData;
import com.food.response.Response;
import com.food.response.SimpleResponse;
import com.food.service.CartService;
import com.food.util.ServiceUtil;

import net.authorize.Environment;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

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
		String totalAmt = ((Map)readObject.get(0)).get("totalAmt").toString();
		System.out.println(totalAmt);
		
		entityName = "GET_NO_TAX_AMT";		
		List readObject1 = dao.readObjects(entityName, queryParams);	
		String noTaxAmt = " ";
		if( ((Map)readObject1.get(0)).get("noTaxAmt")!=null)
		 	noTaxAmt = ((Map)readObject1.get(0)).get("noTaxAmt").toString();
		else
			noTaxAmt="0";
			 
		System.out.println(noTaxAmt);		
		double taxableAmt = Double.parseDouble(totalAmt)-Double.parseDouble(noTaxAmt);		
		double tax =(taxableAmt*8.75)/100;		
		double netAmount= Double.parseDouble(totalAmt) + tax;
		System.out.println(netAmount);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("totalAmt", Double.parseDouble(totalAmt));
		result.put("tax", tax);
		result.put("netAmount", netAmount);
		
		Map<String, String> queryItemParams = new HashMap<String, String>();
		queryItemParams.put("cartId", request.getCart_data().getCartId());
		queryItemParams.put("userId", request.getCart_data().getUserId());
		String entityItemName = "GET_ITEM_DETAILS";
		List<Object> readItemObject = null;
		readItemObject = dao.readObjects(entityItemName, queryItemParams);
		Map<String, List<Object>> items = new HashMap<String, List<Object>>();
		List<Object> itemsLst = new ArrayList<Object>();
		if(null != readItemObject) {	
			for(int i=0; i<readItemObject.size(); i++) {
				Cart mnu = new Cart();
				mnu.setItemId(((Map)readItemObject.get(i)).get("itemId").toString());
				mnu.setItemName(((Map)readItemObject.get(i)).get("itemName").toString());
				mnu.setDescription(((Map)readItemObject.get(i)).get("description").toString());
				//mnu.setQty(((Map)readItemObject.get(i)).get("quantity").toString());
				mnu.setImageUrl(((Map)readItemObject.get(i)).get("imageUrl").toString());			
				//mnu.setAmount(((Map)readItemObject.get(i)).get("price").toString());
				itemsLst.add(mnu);
			}			
		}
		//items.put("items", itemsLst);
		result.put("itemsObj", itemsLst);
				
		SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), result);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
		
	}
	
	@RequestMapping(value="/previewMenu" ,method = RequestMethod.POST)
	public ResponseEntity<Response> previewMenu(@RequestBody  RequestData request) {
		
		DaoFactory factory = DaoFactory.getDaoFactory();
		ObjectDao dao = factory.getObjectDao();
		System.out.println("No of ppl :" + request.getCart_data().getNumPpl());
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("userId", request.getCart_data().getUserId());
		queryParams.put("cartId", request.getCart_data().getCartId());	
		queryParams.put("numPpl", request.getCart_data().getNumPpl());
		queryParams.put("noPpl", request.getCart_data().getNumPpl());
		Map<String,Object> result = new HashMap<String,Object>();
		
		String entityShipName = "GET_SHIP_ADDRESS";
		List<Object> readShipObject = null;		
		readShipObject = dao.readObjects(entityShipName, queryParams);		
		Map<String, String> shipMap = new HashMap();
		shipMap.put("add1", ((Map)readShipObject.get(0)).get("Address1").toString());
		shipMap.put("add2", ((Map)readShipObject.get(0)).get("Address2").toString());
		shipMap.put("city", ((Map)readShipObject.get(0)).get("City").toString());
		shipMap.put("state", ((Map)readShipObject.get(0)).get("State").toString());
		shipMap.put("zip", ((Map)readShipObject.get(0)).get("Zip").toString());
		result.put("shipAddress", shipMap);
		
		String entityPayName = "GET_PAY_METHOD";
		List<Object> readPayObject = null;		
		readPayObject = dao.readObjects(entityPayName, queryParams);		
		String cardNo = ((Map)readPayObject.get(0)).get("cardNo").toString();
		String cardType = ((Map)readPayObject.get(0)).get("cardType").toString();
		String cardExp = ((Map)readPayObject.get(0)).get("cardExp").toString();
		result.put("cardNo", cardNo);
		result.put("cardType", cardType);
		result.put("cardExp", cardExp);
		
		String entityName = "GET_EVENT_AMT";
		List<Object> readObject = null;		
		readObject = dao.readObjects(entityName, queryParams);		
		String totalAmt = ((Map)readObject.get(0)).get("totalAmt").toString();
		System.out.println(totalAmt);
		
				
		//double taxableAmt = Double.parseDouble(totalAmt)-Double.parseDouble(noTaxAmt);		
		double tax =(Double.parseDouble(totalAmt)*8.75)/100;		
		double netAmount= Double.parseDouble(totalAmt) + tax;
		System.out.println(netAmount);
		
		result.put("totalAmt", Double.parseDouble(totalAmt));
		result.put("tax", tax);
		result.put("netAmount", netAmount);
		
		Map<String, String> queryItemParams = new HashMap<String, String>();
		queryItemParams.put("cartId", request.getCart_data().getCartId());
		queryItemParams.put("userId", request.getCart_data().getUserId());
		String entityItemName = "GET_ITEM_DETAILS";
		List<Object> readItemObject = null;
		readItemObject = dao.readObjects(entityItemName, queryItemParams);
		Map<String, List<Object>> items = new HashMap<String, List<Object>>();
		List<Object> itemsLst = new ArrayList<Object>();
		if(null != readItemObject) {	
			for(int i=0; i<readItemObject.size(); i++) {
				Cart mnu = new Cart();
				mnu.setItemId(((Map)readItemObject.get(i)).get("itemId").toString());
				mnu.setItemName(((Map)readItemObject.get(i)).get("itemName").toString());
				mnu.setDescription(((Map)readItemObject.get(i)).get("description").toString());
				//mnu.setQty(((Map)readItemObject.get(i)).get("quantity").toString());
				mnu.setImageUrl(((Map)readItemObject.get(i)).get("imageUrl").toString());			
				//mnu.setAmount(((Map)readItemObject.get(i)).get("price").toString());
				itemsLst.add(mnu);
			}			
		}
		//items.put("items", itemsLst);
		result.put("itemsObj", itemsLst);
				
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

	@Override
	@RequestMapping(value="/chargeCreditCard" ,method = RequestMethod.POST)
	public ResponseEntity<Response> chargeCreditCard(@RequestBody  RequestData request) {
		//Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        System.out.println("Credit card number : " + request.getCart_data().getUserPaymentId());
        System.out.println("Credit card Exp : " + request.getCart_data().getExpDate());
        System.out.println("Net Amt : " + request.getCart_data().getNetAmount());
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName("576xNPzL");
        merchantAuthenticationType.setTransactionKey("3244bEEYctn6K545");
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(request.getCart_data().getUserPaymentId());
        creditCard.setExpirationDate(request.getCart_data().getExpDate());
        paymentType.setCreditCard(creditCard);

        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setAmount(new BigDecimal(request.getCart_data().getNetAmount()));

        // Make the API Request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();


        CreateTransactionResponse response = controller.getApiResponse();
        Map<String,Object> returnResult = new HashMap<String,Object>();
        if (response!=null) {

            // If API Response is ok, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                TransactionResponse result = response.getTransactionResponse();
                if (result.getResponseCode().equals("1")) {
                    System.out.println(result.getResponseCode());
                    System.out.println("Successful Credit Card Transaction");
                    System.out.println(result.getAuthCode());
                    System.out.println(result.getTransId());
                }
                else
                {
                    System.out.println("Failed Transaction"+result.getResponseCode());
                }
                returnResult.put("status", result.getResponseCode());
            }
            else
            {
                System.out.println("Failed Transaction:  "+response.getMessages().getResultCode());
            }
        }

        SimpleResponse reponse = new SimpleResponse("" + true,
				request.getRequest_data_type(),
				request.getRequest_data_method(), returnResult);
		
		ResponseEntity<Response> entity = new ResponseEntity<Response>(reponse,
				HttpStatus.OK);
		
		return entity;
		
    }
	
}
