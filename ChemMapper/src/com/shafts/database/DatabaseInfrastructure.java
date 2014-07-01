package com.shafts.database;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Database infrastructure
 * @author Alex
 * @version 2.0.0
 * @since May 12, 2014
 */
public abstract class DatabaseInfrastructure<T extends IConvertableFromResultSet> implements ISQLExecutionWrapper<T> {
	/**
	 * Gets connection to database.
	 * @return a connection to database.
	 */
	public abstract Connection getConnection();
	
	/**
	 * Method to log debug message.
	 * @param msg Debug message
	 */
	public void debug(String msg){ }
	
	public boolean execUpdate(Connection conn,String pstat,Object ... args){
		try{ getStmt(conn, pstat, args).executeUpdate(); return true; }
		catch(SQLException e){ error(e); }
		catch(NullPointerException e){ error(e); }
		return false;
	}
	
	public boolean execUpdate(String pstat,Object ... args){
		Connection conn=getConnection();
		try{ return execUpdate(conn,pstat,args); }
		finally{
			try { conn.close(); } 
			catch (SQLException e){ error(e); } 
		}
	}
	
	public List<T> execQuery(Class<T> ct, Connection conn,String pstat,Object ... args){
		ArrayList<T> r=new ArrayList<T>();
		try{
			ResultSet rs=getStmt(conn, pstat, args).executeQuery();
			Constructor<T> ctor=ct.getConstructor(ResultSet.class);
			while(rs.next())r.add(ctor.newInstance(rs));
		}
		catch(SQLException e){ error(e); }
		catch(NullPointerException e){ error(e); } 
		catch(ReflectiveOperationException e){ error(e); }  
		catch(RuntimeException e){ error(e); }
		return r;
	}
	
	public List<T> execQuery(Class<T> ct,String pstat,Object ... args){
		Connection conn=getConnection();
		try{ return execQuery(ct,conn,pstat,args); }
		finally{
			try { conn.close(); } 
			catch (SQLException e){ error(e); } 
		}
	}
	
	public T execQueryOne(Class<T> ct, Connection conn,String pstat,Object ... args){
		T r=null;
		List<T> q=execQuery(ct,conn,pstat,args);
		if(q.size()>=1)r=q.get(0);
		return r;
	}
	
	public T execQueryOne(Class<T> ct,String pstat,Object ... args){
		T r=null;
		List<T> q=execQuery(ct,pstat,args);
		if(q.size()>=1)r=q.get(0);
		return r;
	}
	
	public PreparedStatement getStmt(Connection conn,String pstat,Object ... args) throws SQLException{
		PreparedStatement stat=conn.prepareStatement(pstat);
		for(int i=0;i<args.length;i++)stat.setString(i+1,args[i].toString());
		debug(stat.toString());		
		return stat;
	}
	
	/**
	 * Joins together everything in E with sep separated.
	 * @param <T> Type of items in E
	 * @param out Writer to the output stream
	 * @param E Collection of items to be joined
	 * @param sep Separator char
	 * @throws IOException
	 */
	public <TI> void wjoin(Writer out,Collection<TI> E,char sep) throws IOException{
		for(Iterator<TI> i=E.iterator();i.hasNext();){
			out.write(i.next().toString());
			if(i.hasNext())out.write(sep);
		}
	}
	
	/**
	 * Handy method to log an exception.
	 * @param e the exception
	 */
	protected void error(Exception e){
		StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
        e.printStackTrace(pw);  
        debug(sw.toString());
	}
}

/**
 * Interface to being automatically filled in with a ResultSet.
 * @author Alex
 */
interface IConvertableFromResultSet{
	/**
	 * Fills in using ResultSet r.
	 * @param r the ResultSet for fetching data
	 * @throws SQLException 
	 */
	void fromResultSet(ResultSet r) throws SQLException;
}

/**
 * Wraps usage of PreparedStatement
 * @author Alex
 */
interface IPreparedStatementWrapper{
	/**
	 * Gets a PreparedStatement instance in a fancier way.
	 * @param conn Database connection
	 * @param pstat an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param args the parameter values
	 * @return a PreparedStatement instance with args all settled.
	 * @throws SQLException
	 */
	PreparedStatement getStmt(Connection conn,String pstat,Object ... args) throws SQLException;
}

/**
 * Wraps oftenly used execution of SQL.
 * @author Alex
 */
interface ISQLExecutionWrapper<T extends IConvertableFromResultSet> extends IPreparedStatementWrapper{
	/**
	 * Executes the SQL statement with some arguments where pstat must be an SQL 
     * Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; 
     * args should be all String. This also implies all data to be stored String.
     * @param conn Database connection
	 * @param pstat an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param args the parameter values
	 * @return True if operation is successful, false otherwise.
	 * @see IPreparedStatementWrapper#getStmt(Connection, String, String...)
	 */
	boolean execUpdate(Connection conn,String pstat,Object ... args);
	
	/**
	 * Executes the SQL statement with some arguments where pstat must be an SQL 
     * Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; 
     * args should be all String. This also implies all data to be stored String.
	 * @param pstat an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param args the parameter values
	 * @return True if operation is successful, false otherwise.
	 * @see IPreparedStatementWrapper#getStmt(Connection, String, String...)
	 */
	boolean execUpdate(String pstat,Object ... args);
	
	/**
	 * Executes the SQL query in this PreparedStatement object and returns 
     * the ResultSet object generated by the query.
	 * @param <T> Element type which extends IConvertableFromResultSet
	 * @param ct Class of T
	 * @param conn Database connection
	 * @param pstat an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param args the parameter values
	 * @return a list of records in type T.
	 * @see IPreparedStatementWrapper#getStmt(Connection, String, String...)
	 */
	List<T> execQuery(Class<T> ct, Connection conn,String pstat,Object ... args);
	
	/**
	 * Executes the SQL query in this PreparedStatement object and returns 
     * the ResultSet object generated by the query.
	 * @param <T> Element type which extends IConvertableFromResultSet
	 * @param ct Class of T
	 * @param pstat an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param args the parameter values
	 * @return a list of records in type T.
	 * @see IPreparedStatementWrapper#getStmt(Connection, String, String...)
	 */
	List<T> execQuery(Class<T> ct, String pstat,Object ... args);
}

/**
 * Root Exception of all exceptions generated by 
 * database infrastructure for Shafts project.
 * @author Alex
 */
class DatabaseInfrastructureException extends Exception{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2647878134741860788L;

	/**
	 * Constructor
	 */
	public DatabaseInfrastructureException(){ super(); }
	
	/**
	 * Constructor
	 * @param message Message to be carried with the exception
	 */
	public DatabaseInfrastructureException(String message){ super(message); }
}

