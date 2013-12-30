package CP_Classes.common;

import java.sql.*;
import java.util.Vector;

/******************************************************
 * 
 * THIS IS USING "YUNI"'s CONNECTION POOLING METHOD 
 * ALL CODES UNDER SQLMETHOD.java HAVE BEEN MODIFIED TO WORK WITH THE CONNECTION
 * @author yuni
 *
 */

/*public class sqlMethod {
	
	private static ResultSet rs=null;
	private static Connection connection=null;
	private static Statement st=null;
	private static int num=0;
	private static float numf=0;
	private static double numd=0;
	private static int[] arrNum;
	 
	private static ConnectionPool connectionPool;
	 
  	public static void main(String args[])
  	{
  		sqlMethod S = new sqlMethod();
  		//System.out.println("Getting from DB = " + S.bQuery("SELECT * FROM USERTYPE"));
  	}
  
  	public sqlMethod() {
  		
  	}
  
  	public static ResultSet rsQuery(String sql)
  	{
  		try
  		{
  		
  		  connection = connectionPool.getConnection();
  		  st=connection.createStatement();
  		  rs=st.executeQuery(sql);
       
  		}
  		catch(Exception e)
  		{
  			System.out.println("sqlMethod rsQuery is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return rs;
  	}

  	public static int iQuery(String sql)
  	{
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
  			rs=st.executeQuery(sql);
  			num = 0; //Clear num
       
  			if(rs.next())
  				num=rs.getInt(1);
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
  		}
  		catch(Exception e)
  		{
  			//close();
  			System.out.println("sqlMethod iQuery is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return num;
  	}*/

  
  	/** Query to DB (Can define number of fields to be retrieved)
  	 * @param sql
  	 * @param iNo
  	 * @return
  	 */
  /*	public static int[] iQuery(String sql, int iNo)
  	{
  		arrNum = new int[iNo];
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
  			rs=st.executeQuery(sql);
       
  			for(int i=0; i<iNo; i++)
  				arrNum[i]= 0; //Clear arrNum
       
  			if(rs.next())
  			{
  				for(int i=0; i<iNo; i++)
  					arrNum[i]=rs.getInt(i+1);
  			}
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
  		}
  		catch(Exception e)
  		{
  			//close();
  			System.out.println("sqlMethod iQuery is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return arrNum;
  	}

  	public static float fQuery(String sql)
  	{
  		try
  		{
  			connection = connectionPool.getConnection();
  			st=connection.createStatement();
  			rs=st.executeQuery(sql);
  			if(rs.next())
  				numf=rs.getFloat(1);

  		}
  		catch(Exception e)
  		{
  			//close();
  			System.out.println("sqlMethod fQuery is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return numf;
  	}

  	public static double dQuery(String sql)
  	{
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
  			rs=st.executeQuery(sql);
  			if(rs.next())
  				numd=rs.getDouble(1);
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
  		}
  		catch(Exception e)
  		{
  			//close();
  			System.out.println("sqlMethod dQuery is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return numd;
  	}

  	public static void close()
  	{
  		try
  		{
  			/*if (connection != null) {
  				connection.close();
  				connection = null;
  				
  			}*/
  			
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
 /* 		} 
  		catch(Exception er)
  		{
  			System.out.println("con='" + connection + "' close is abnormal "+er);
  		}

  	}
  
  	public static void closeStmt()
  	{
  		try
  		{
  			//System.out.println("Statement='" + st + "'");
  			if (st != null) {
  				st.close();
  				st = null;
  			}
  		} 
  		catch(Exception er)
  		{
  			System.out.println("st='" + st + "' close is abnormal "+er);
  		}

  	}
  
  	public static void closeRset()
  	{
  		try
  		{
  			//System.out.println("Statement='" + st + "'");
  			if (rs != null) {
  				rs.close();
  				rs = null;
  			}
  		} 
  		catch(Exception er)
  		{
  			System.out.println("st='" + st + "' close is abnormal "+er);
  		}

  	}
  
  
  	/**
  	 *
  	 * @param sql
  	 * @return 
  	 */
  /*	public static boolean bInsert(String sql)
  	{
  		boolean b=false;
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
  			int i=st.executeUpdate(sql);
  			if(i!=0)
  				b=true;
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();

  		}
  		catch(Exception e)
  		{
  			// close();
  			System.out.println("sqlMethod bInsert is abnormal "+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return b;
  }

  	public static boolean bDoBatch(Vector vQuery)
  	{
  		boolean b=false;
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
       
       
  			for(int i=0; i<vQuery.size(); i++) {
  				st.addBatch((String)vQuery.elementAt(i));
    	  
  			}
       
  			int [] arr=st.executeBatch();
       
  			for(int i=0; i<arr.length; i++) {
  				if(i!=0)
  					b=true;
  				else
  					break;
  			}
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
       
  		}
  		catch(Exception e)
  		{
  			// close();
  			System.out.println("sqlMethod bInsert is abnormal "+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return b;
  	}
  
  /**
   *
   * @param sql
   * @return
   */
  	/*public static boolean bUpdate(String sql)
  	{
  		boolean b=false;
  		try
  		{
  			connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
  			int i=st.executeUpdate(sql);
       
  			if(i!=0)
  				b=true;
       
  			//if(Settings.getConnectionType() == 1)
  				//ConnectionBean.close();
       
  		}
  		catch(Exception e)
  		{
  			System.out.println("sqlMethod bUpdate is abnormal"+e);
  		}
  		finally
  		{
  			connectionPool.free(connection);
  		}
  		return b;
  }
  
  	/**
  	 *
  	 * @param sql
  	 * @return
  	 */
  /*	public static boolean bDelete(String sql)
  	{
	    boolean b=false;
	    try
	    {
  		    connection = connectionPool.getConnection();
  	  		st=connection.createStatement();
	    	int i=st.executeUpdate(sql);
	    	if(i!=0)
	    		b=true;
	    	
	    }
	    catch(Exception e)
	    {
	    	//close();
	    	System.out.println("sqlMethod bDelete is abnormal"+e);
	    }
	    finally
	    {
	    	connectionPool.free(connection);
	    }
	    return b;
	}

	/** Initialize the connection pool when servlet is
    *  initialized. To avoid a delay on first access, load
    *  the servlet ahead of time yourself or have the
    *  server automatically load it after reboot.
    */
   
  /*  public static void init() {
	     String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	     String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=360cp";
	     String username = "360CP";
	     String password = "raffles";
	     String JNDIName = "jdbc/sps";
	     
	     try {
	    	
		       connectionPool =
	         new ConnectionPool(driver, url, username, password,
	                            initialConnections(),
	                            maxConnections(),
	                            true);
	    	
	     } catch(SQLException sqle) {
		       System.err.println("Error making pool: " + sqle);
		       connectionPool = null;
	     }
	}

  	public void destroy() {
  		connectionPool.closeAllConnections();
  	}

   /** Override this in subclass to change number of initial
    *  connections.
    */
   
  /* 	protected static int initialConnections() {
   		return(8);
   	}

   /** Override this in subclass to change maximum number of 
    *  connections.
    */

 /*  	protected static int maxConnections() {
	   return(32);
   	}
}
*/