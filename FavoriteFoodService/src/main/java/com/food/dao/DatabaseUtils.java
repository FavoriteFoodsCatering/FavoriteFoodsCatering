package com.food.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.food.common.DAOException;
import com.food.common.DBConnectionPoolManager;
import com.food.common.ErrorLevel;



/**
 * The Class DatabaseUtil.
 */
public class DatabaseUtils {

    /** The logger. */
    protected static Log logger = LogFactory.getLog(DatabaseUtils.class);
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The CONNECTIO n_ poo l_ name. */
    private static  String CONNECTION_POOL_NAME="SDFSLA";
    
    /** The ea smart db pool manager. */
    //static EASmartDbPoolManager conPoolManager=new EASmartDbPoolManager();
    static DBConnectionPoolManager conPoolManager = new DBConnectionPoolManager();
    
    /**
     * Gets the db connection.
     * 
     * @return the db connection
     * @throws SQLException the sQL exception
     */
    public static Connection getDbConnection() throws SQLException {
        return conPoolManager.getConnection();
    }
    


    /**
     * Gets the connection.
     * 
     * @return the connection
     * @throws SQLException =
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return conPoolManager.getConnection();      
    }
    
        

    /**
     * Release connection.
     * 
     * @param connection the connection
     */
    public static void releaseConnection(Connection connection){
        conPoolManager.releaseConnection(connection);
    }
    
  
    public static void close( PreparedStatement ps, Connection con)throws DAOException{
       
        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing PreparedStatement.", null, ErrorLevel.ERROR);
        }
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing Connection.", null, ErrorLevel.ERROR);
        }
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con)throws DAOException{
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing ResultSet.", null, ErrorLevel.ERROR);
        }
        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing PreparedStatement.", null, ErrorLevel.ERROR);
        }
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing Connection.", null, ErrorLevel.ERROR);
        }
    }
    public static void close(ResultSet rs, Statement st, Connection con)throws DAOException{
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing ResultSet.", null, ErrorLevel.ERROR);
        }
        try {
            if (st != null)
                st.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing PreparedStatement.", null, ErrorLevel.ERROR);
        }
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            throw new DAOException(e, "Got error while closing Connection.", null, ErrorLevel.ERROR);
        }
    }
    public static void main(String[] args) throws Exception{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String user ="";
        conn = getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select * from DISP_USER where user_id=\"bkagidal\"");

        while (rs.next()) {
            user = rs.getString(2);
         }
        
        System.out.println("USER : "+user);
        close(rs,stmt,conn);
    }
}
