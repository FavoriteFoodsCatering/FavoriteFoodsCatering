package com.food.dao.impl;

import com.food.dao.DaoFactory;
import com.food.dao.ObjectDao;


public class DaoFactoryImpl extends DaoFactory {

	@Override
	public ObjectDao getObjectDao() {
		// TODO Auto-generated method stub
		return new ObjectDaoImpl();
	}

}
