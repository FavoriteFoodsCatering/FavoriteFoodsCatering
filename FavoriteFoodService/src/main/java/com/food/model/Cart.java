/**
 * 
 */
package com.food.model;

/**
 * @author bkagidal
 *
 */
public class Cart {
	
	private String cartId;
	private String userId;
	private String itemId;
	private String itemName;
	private String description;
	private String imageUrl;
	private String qty;
	private String delFromCart;	
	private String orderDate;
	private String userPaymentId;
	private String expDate;
	private String amount;
	private String currency;
	private String netAmount;
	private String shiptoFirstName;
	private String shiptoMiddlName;
	private String shiptoLastName;
	private String shiptoAddress1;
	private String shiptoAddress2;
	private String shiptoCity;
	private String shiptoState;
	private String shiptoZipCode;
	private String shiptoCountry;
	private String discountPercentage;
	private String updateTime;
	private String numPpl;
	private String orderType;
	private String emailAddress;
	private String phoneNo;
	
	
	private String billtoFirstName;
	private String billtoMiddlName;
	private String billtoLastName;
	private String billtoAddress1;
	private String billtoAddress2;
	private String billtocity;
	private String billtostate;
	private String billtozipCode;
	private String billtoCountry;
	private String note;
	private String customerId;
	private String customerCardId;
	
	private MenuItem[] cartItem;
	
	private shipping_address shipping_address;
	private billing_address billing_address;
	private amount_money amount_money;
	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getDelFromCart() {
		return delFromCart;
	}
	public void setDelFromCart(String delFromCart) {
		this.delFromCart = delFromCart;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getUserPaymentId() {
		return userPaymentId;
	}
	public void setUserPaymentId(String userPaymentId) {
		this.userPaymentId = userPaymentId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getShiptoFirstName() {
		return shiptoFirstName;
	}
	public void setShiptoFirstName(String shiptoFirstName) {
		this.shiptoFirstName = shiptoFirstName;
	}
	public String getShiptoMiddlName() {
		return shiptoMiddlName;
	}
	public void setShiptoMiddlName(String shiptoMiddlName) {
		this.shiptoMiddlName = shiptoMiddlName;
	}
	public String getShiptoLastName() {
		return shiptoLastName;
	}
	public void setShiptoLastName(String shiptoLastName) {
		this.shiptoLastName = shiptoLastName;
	}
	public String getShiptoAddress1() {
		return shiptoAddress1;
	}
	public void setShiptoAddress1(String shiptoAddress1) {
		this.shiptoAddress1 = shiptoAddress1;
	}
	public String getShiptoAddress2() {
		return shiptoAddress2;
	}
	public void setShiptoAddress2(String shiptoAddress2) {
		this.shiptoAddress2 = shiptoAddress2;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public String getShiptoState() {
		return shiptoState;
	}
	public void setShiptoState(String shiptoState) {
		this.shiptoState = shiptoState;
	}
	public String getShiptoZipCode() {
		return shiptoZipCode;
	}
	public void setShiptoZipCode(String shiptoZipCode) {
		this.shiptoZipCode = shiptoZipCode;
	}
	public String getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getNumPpl() {
		return numPpl;
	}
	public void setNumPpl(String numPpl) {
		this.numPpl = numPpl;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getShiptoCountry() {
		return shiptoCountry;
	}
	public void setShiptoCountry(String shiptoCountry) {
		this.shiptoCountry = shiptoCountry;
	}
	public String getBilltoFirstName() {
		return billtoFirstName;
	}
	public void setBilltoFirstName(String billtoFirstName) {
		this.billtoFirstName = billtoFirstName;
	}
	public String getBilltoMiddlName() {
		return billtoMiddlName;
	}
	public void setBilltoMiddlName(String billtoMiddlName) {
		this.billtoMiddlName = billtoMiddlName;
	}
	public String getBilltoLastName() {
		return billtoLastName;
	}
	public void setBilltoLastName(String billtoLastName) {
		this.billtoLastName = billtoLastName;
	}
	public String getBilltoAddress1() {
		return billtoAddress1;
	}
	public void setBilltoAddress1(String billtoAddress1) {
		this.billtoAddress1 = billtoAddress1;
	}
	public String getBilltoAddress2() {
		return billtoAddress2;
	}
	public void setBilltoAddress2(String billtoAddress2) {
		this.billtoAddress2 = billtoAddress2;
	}
	public String getBilltocity() {
		return billtocity;
	}
	public void setBilltocity(String billtocity) {
		this.billtocity = billtocity;
	}
	public String getBilltostate() {
		return billtostate;
	}
	public void setBilltostate(String billtostate) {
		this.billtostate = billtostate;
	}
	public String getBilltozipCode() {
		return billtozipCode;
	}
	public void setBilltozipCode(String billtozipCode) {
		this.billtozipCode = billtozipCode;
	}
	public String getBilltoCountry() {
		return billtoCountry;
	}
	public void setBilltoCountry(String billtoCountry) {
		this.billtoCountry = billtoCountry;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public shipping_address getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(shipping_address shipping_address) {
		this.shipping_address = shipping_address;
	}
	public billing_address getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(billing_address billing_address) {
		this.billing_address = billing_address;
	}
	public amount_money getAmount_money() {
		return amount_money;
	}
	public void setAmount_money(amount_money amount_money) {
		this.amount_money = amount_money;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCardId() {
		return customerCardId;
	}
	public void setCustomerCardId(String customerCardId) {
		this.customerCardId = customerCardId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	

	public MenuItem[] getCartItem() {
		return cartItem;
	}
	public void setCartItem(MenuItem[] cartItem) {
		this.cartItem = cartItem;
	}

}
