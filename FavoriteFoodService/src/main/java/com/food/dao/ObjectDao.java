package com.food.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.food.model.Download;


public interface ObjectDao  {
	
	public boolean insertUpdateObject(String dsConfigName,
	                                  Map<String, String> params);
	
	public List<Object> readObjects(String dsConfigName,
	                                Map<String, String> params);
	
	
	
	public boolean insertObjectNotResource(String query, String userId, String itemId);

	public List<Object> readObjectsNotResource(String query);
	
	
	public boolean upload(String dsConfigName, 
            Map<String, String> params,
        MultipartFile file);

	public Download download(String dsConfigName,
               Map<String, String> params);
}
