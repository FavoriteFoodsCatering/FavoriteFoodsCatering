package com.food.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.food.common.DAOException;
import com.food.dao.DatabaseUtils;

public class CommonUtil {
	private static CommonUtil _instance = null;
	private static ResourceBundle appConfigRB = null;
	private final Log logger = LogFactory.getLog(getClass());
	
	private CommonUtil() {
		appConfigRB = ResourceBundle.getBundle("DataServiceSQLMap");
	}

	public synchronized static CommonUtil getInstance() {
		if (_instance == null) {
			_instance = new CommonUtil();
		}
		return _instance;
	}

	public Map<String, String> getLimit(String pageno, String limit) {

		Map<String, String> map = new HashMap<String, String>();
		if (pageno == null || "".equals(pageno.trim())) {
			pageno = "1";
		}
		if (limit == null || "".equals(limit.trim())) {
			limit = "10";
		}
		try {
			int pNo = Integer.parseInt(pageno.trim());
			int lmt = Integer.parseInt(limit.trim());

			map.put("from", "" + ((pNo * lmt) - (lmt)));
			map.put("to", "" + (lmt));
		} catch (Exception e) {
			 logger.error("SQL:", e);
		}
		return map;
	}

	public String getQuery(String dsConfigName, Map<String, String> params) {
		String query = null;
		
		try {
			query = appConfigRB.getString(dsConfigName);

			if (dsConfigName != null && params != null) {
				for (String key : params.keySet()) {
					String pv = params.get(key);
					String pattern = "http." + key;
					try {
						query = query.replaceFirst(pattern, ((pv != null) ? pv.toString(): "null"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		logger.info(query);
		return query;
	}

	public String getQueryNotParams(String dsConfigName, String userId, String itemId) {
		String query = null;

		try {
			query = appConfigRB.getString(dsConfigName);
			query = String.format(query, userId, itemId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		logger.info(query);
		return query;
	}
	
	public String getQueryNotResource(String dsConfigName) {
		String query = null;

		try {
			query = appConfigRB.getString(dsConfigName);
			query = String.format(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		logger.info(query);
		return query;
	}
	
	public void getObjectFromJson(Object obj, JSONObject json)
			throws IllegalAccessException, InvocationTargetException,
			JSONException {
		if (json != null) {
			Iterator itr = json.keys();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				BeanUtils.setProperty(obj, key, json.getString(key));
			}
		}
	}

	private Map<String, String> readFields(ResultSetMetaData metaData)
			throws SQLException {
		Map<String, String> columnMap = new HashMap<String, String>();
		int count = metaData.getColumnCount();
		for (int i = 0; i < count; i++) {
			columnMap.put(metaData.getColumnLabel(i + 1),
					metaData.getColumnTypeName(i + 1));
		}
		return columnMap;
	}

	public List<Object> getObjects(ResultSet rs) throws IOException,
			SQLException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, JSONException, InvocationTargetException {
		ResultSetMetaData rsmd = rs.getMetaData();
		Map<String, String> columnMap = readFields(rsmd);
		List<Object> listUser = new ArrayList<Object>();
		
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			String name = null;
			Object val = null;
			for (Map.Entry<String, String> entry : columnMap.entrySet()) {
				name = entry.getKey();
				if (entry.getValue().equals("TIMESTAMP")
						|| entry.getValue().equals("DATE")) {
					try {
						val = rs.getTimestamp(name);
					} catch (Exception e) {
						logger.error(name);
					}
						
				} else if (entry.getValue().equals("CLOB")) {
					Clob clob = rs.getClob(name);
					if (clob != null) {
						if ((int) clob.length() > 0) {
							val = (Object) clob.getSubString(1,
									(int) clob.length());
						}
					}
				} else if (entry.getValue().equals("BLOB")) {
					val = (Object) readBLOBToFileStream((rs.getBlob(name) != null) ? rs
							.getBlob(name) : null);
				} else {
					val = rs.getObject(name);
				}
				map.put(name, val != null ? val.toString() : val);
			}
			listUser.add(map);
		}
		return listUser;
	}

	public String readBLOBToFileStream(Blob image) throws IOException,
			SQLException {
		int BUFFER_SIZE = 4096;
		String imageBase64 = null;
		byte[] buffer = null;
		if (image == null)
			return imageBase64;
		try {
			buffer = new byte[BUFFER_SIZE];
			buffer = image.getBytes(1, (int) image.length());
			imageBase64 = Base64.encodeBase64String(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageBase64;
	}

	public String getCount(String dsConfigName, Map<String, String> params) {
		CommonUtil util = CommonUtil.getInstance();
		String query = util.getQuery(dsConfigName, params);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String count = null;
		try {
			conn = DatabaseUtils.getConnection();
			stmt = conn
					.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int cnt = rs.getInt("COUNT");
				count = "" + cnt;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// close(rs,stmt,conn);
			try {
				DatabaseUtils.close(rs, stmt, conn);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return count;
		// return listUser;
	}

}
