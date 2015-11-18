package com.food.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.Category;
import com.food.model.MenuItem;

public class ServiceUtil {
	
	
	private static MenuItem menu = null;

	 public static  Map<String,Category> buildMenuItems(List<Object> readObject){
		 
		  String categoryId;
		  Category categoryObj= null;
		  Map<String,Category> menuList = new HashMap<String, Category>();
		 
		 for(Object object:readObject){
				
				categoryId = ((Map)object).get("categoryId").toString();
	
				if(menuList != null && menuList.keySet().contains(categoryId)){
					categoryObj = menuList.get(categoryId);
					categoryObj = buildcategory(object,categoryObj);
				}else{
					
					categoryObj = buildcategory(object,null);
					
				}
				
				menuList.put(categoryId, categoryObj);
			}
		 
		
		return menuList;
		
	}
	 
	 private static Category buildcategory(Object obj,Category categoryObj){
			
		 
			List<MenuItem> menuItemsList;
		
			if(categoryObj== null){
				categoryObj= new Category();
				menuItemsList = new ArrayList<MenuItem>();
			}else{
				menuItemsList = categoryObj.getMenuItems();
			}
			
			 menu = new MenuItem();
			
			
			Map myMap= (Map)obj;
			
			categoryObj.setCategoryId(myMap.get("categoryId").toString());
			categoryObj.setCategoryDesc(myMap.get("categoryName").toString());
			
			menu.setItemId(myMap.get("itemId").toString());
			menu.setItemName(myMap.get("itemName").toString());
			menu.setActive(myMap.get("active").toString());
			menu.setCategoryId(myMap.get("categoryId").toString());
			menu.setExtraPerPerson(myMap.get("extraPerPerson").toString());
			menu.setImageUrl(myMap.get("imageUrl").toString());
			menu.setItemDesc(myMap.get("description").toString());
			menu.setRate(myMap.get("rate").toString());
			menu.setServes(myMap.get("serves").toString());
			menu.setTaxable(myMap.get("taxable").toString());
			
			menuItemsList.add(menu);
			
			categoryObj.setMenuItems(menuItemsList);
			
			return categoryObj;
		}
	 
	 public static String getCartId(String extCartId,String ordCartId){
		 
		 
		 if(extCartId.equalsIgnoreCase("0"))
			  return String.valueOf(Integer.parseInt(ordCartId) +1);
		 else
			 return extCartId;
		 
		 
	 }


}
