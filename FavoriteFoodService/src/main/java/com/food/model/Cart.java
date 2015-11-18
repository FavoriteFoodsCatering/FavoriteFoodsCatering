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
	private String qty;
	private String delFromCart;
	
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
	
	

}
