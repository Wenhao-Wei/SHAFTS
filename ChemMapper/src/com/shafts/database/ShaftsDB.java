package com.shafts.database;

import java.sql.*;

/**
 * Database infrastructure for Shafts project
 */
public class ShaftsDB<T extends IConvertableFromResultSet> extends DatabaseInfrastructure<T> {
	/**
	 * Gets connection to database.
	 * @return a connection to database.
	 */
	public Connection getConnection(){
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://114.215.171.155:3306/shafts?user=iadmin&password=1234qwer&useUnicode=true&characterEncoding=utf-8"); 		
	    }
		catch(SQLException e){ error(e); }
		catch(ClassNotFoundException e){ error(e); }
		return conn;
	}
	
	/**
	 * Method to log debug message.
	 * @param msg Debug message
	 */
	@Override
	public void debug(String msg){ System.err.println(msg); }
	
}






