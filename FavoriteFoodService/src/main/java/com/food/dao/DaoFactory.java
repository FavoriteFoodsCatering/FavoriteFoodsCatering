package com.food.dao;

import com.food.dao.impl.DaoFactoryImpl;

public abstract class DaoFactory {

	public abstract ObjectDao getObjectDao();

	public static DaoFactory getDaoFactory() {
		DaoFactory daoFactory = new DaoFactoryImpl();
		return daoFactory;
	}
}
