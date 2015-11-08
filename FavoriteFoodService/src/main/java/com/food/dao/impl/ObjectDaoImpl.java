package com.food.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.food.common.DAOException;
import com.food.dao.DatabaseUtils;
import com.food.dao.ObjectDao;
import com.food.model.Download;
import com.food.util.CommonUtil;

public class ObjectDaoImpl implements ObjectDao {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public boolean insertUpdateObject(String dsConfigName,
			Map<String, String> params) {
		boolean flag = true;
		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQuery(dsConfigName, params);

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseUtils.getConnection();
			logger.debug("con===" + conn);
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int numRows = stmt.executeUpdate();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtils.close(stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public List<Object> readObjects(String dsConfigName,
			Map<String, String> params) {

		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQuery(dsConfigName, params);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> listUser = new ArrayList<Object>();
		try {
			conn = DatabaseUtils.getConnection();
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			listUser = util.getObjects(rs);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				DatabaseUtils.close(rs, stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return listUser;
	}

	@Override
	public boolean upload(String dsConfigName, Map<String, String> params,
			MultipartFile fis) {
		boolean flag = false;
		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQuery(dsConfigName, params);

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseUtils.getConnection();
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			stmt.setAsciiStream(1, fis.getInputStream(), (int) fis.getSize());
			int numRows = stmt.executeUpdate();
			if (numRows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtils.close(stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public Download download(String dsConfigName, Map<String, String> params) {

		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQuery(dsConfigName, params);

		Download file = new Download();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtils.getConnection();
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				InputStream is = rs.getAsciiStream(1);
				file.setInputStream(is);
				file.setFileName(rs.getString(2));
				file.setFilesize(rs.getString(3));
				file.setMimeType(rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtils.close(rs, stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	@Override
	public boolean insertObjectNotResource(String dsConfigName, String userId,
			String itemId) {
		boolean flag = true;

		Connection conn = null;
		PreparedStatement stmt = null;
		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQueryNotParams(dsConfigName, userId, itemId);

		try {
			conn = DatabaseUtils.getConnection();
			logger.debug("con===" + conn);
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int numRows = stmt.executeUpdate();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtils.close(stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public List<Object> readObjectsNotResource(String dsConfigName) {

		CommonUtil util = CommonUtil.getInstance();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = util.getQueryNotResource(dsConfigName);
		List<Object> listUser = new ArrayList<Object>();
		logger.debug(query);
		try {
			conn = DatabaseUtils.getConnection();
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			listUser = util.getObjects(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtils.close(rs, stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return listUser;
	}
}
