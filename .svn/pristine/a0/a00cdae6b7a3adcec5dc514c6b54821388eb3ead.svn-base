package CP_Classes;

import java.io.Serializable;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblOrganization;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * The Database Class is to be used for Database connections using JdbcOdbc Driver.
 */
public class Database implements Serializable
{
	//public Connection con;
	private String name, password, dbName;

	/**
	 * Creates a new Database object.
	 * Throws: ClassNotFoundException - if a driver access error occurs or no specified driver available.
	 */
	public Database () {
		
		name     = "360cp";
		
		dbName   = "360cp";
		
		//name = "sa";
		//password = "082010yog";
		password = "raffles";
		//dbName = "360";
		
		try {
			//String driver = "sun.jdbc.odbc.JdbcOdbcDriver"; //ODBC
			//String driver = "net.sourceforge.jtds.jdbc.Driver"; //JTDS
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //MS JDBC
 			Class.forName(driver);
		} catch (ClassNotFoundException cnfe) {
  			System.err.println("Database.java : Couldn't find driver class ");
  			System.err.println(cnfe.getMessage());
  			cnfe.printStackTrace();
		}
	}



	/**
	 * Attempts to establish a connection with the data source that specified as the parameter dbName.
	 */
	/*public void openDB() {
		try {
			while(con == null)
			{
				DriverManager.registerDriver(new SQLServerDriver());
    			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=" + dbName, name, password);
				//System.out.println("Open DB");
				//con = DriverManager.getConnection("jdbc:odbc:" + dbName, name, password); //ODBC
				//con = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433;DatabaseName=" + dbName, name, password); //JTDS
			}
		}
		catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}
	}*/

	/**
	 * Attempts to establish a connection with the data source that specified as the parameter dbName.
	 */
	/*public void openDB() {
		try {
			DriverManager.registerDriver(new SQLServerDriver());
			con = DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=" + dbName, name, password);
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
			while(con == null)
				openDB();
		}
	}*/

	/**
	 * Releases this Database object's database and JDBC resources immediately instead of waiting for them to be automatically released.
	 * Note: A Database object is automatically closed when it is garbage collected.
	 * Certain fatal errors also close a Database object.
	 * Throws: SQLException - if a database access error occurs
	 */
	/*public void closeDB() {
		try {
			if(con != null)
				con.close();
			
			con = null;
			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}

	}*/
	/**
	 * Truncate SQL Log
	*/
	public boolean truncateSQLLog() 
	{
		
		String query = "USE [360cp] DBCC SHRINKFILE('360cp_LOG', TRUNCATEONLY)";
		
		Connection con = null;
		Statement st = null;
		boolean bIsAdded = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			/*
			int iSuccess=st.executeUpdate(query);
			if(iSuccess!=0)
				bIsAdded=true;
				*/
			
			bIsAdded = st.execute(query);

		}
		catch(Exception E)
		{
            System.err.println("Database.java - truncateSQLLog - SHRINKFILE " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

			query = "BACKUP LOG [360cp] WITH TRUNCATE_ONLY";
			
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess=st.executeUpdate(query);
				if(iSuccess!=0)
					bIsAdded=true;

			}
			catch(Exception E)
			{
	            System.err.println("Database.java - truncateSQLLogBackUp - " + E);
			}
			finally
	        {
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
	        }
			
        }
		
		return bIsAdded;
	}


/*	
	public Savepoint setSavePoint(String name) {
		try {
			Savepoint SP = con.setSavepoint(name);
			return SP;
			
		} catch (SQLException SE) {
			System.err.println("bb" + SE.getMessage());
		}
		return null;		
	}	
	
	public void setRollBack(Savepoint name) {
		try {
			con.rollback(name);				
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}
		
	}	
*/

	/**
	 * Method used for executing a static SQL statement and returning the results it produces.
	 * Results produced will be stored in ResultSet.
	 */
	/*public ResultSet getRecord(String query) {
		try {
			openDB();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// ********* stmt and rs need to be closed !!! *************************** //
			
			return rs;
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}
		return null;
	}*/

	/**
	 * Method used to get the list of Organization based on CompanyID.
	 * Results produced will be stored in ResultSet.
	 */
	public Vector OrganizationList(int compID) {
		
		String query = "SELECT tblOrganization.PKOrganization, tblOrganization.OrganizationName FROM ";
		query = query + "tblConsultingCompany INNER JOIN tblOrganization ON ";
		query = query + "tblConsultingCompany.CompanyID = tblOrganization.FKCompanyID WHERE ";
		query = query + "tblConsultingCompany.CompanyID = " + compID;

		Vector v = new Vector();
    	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
            while(rs.next())
            {
            	votblOrganization vo = new votblOrganization();
            	vo.setPKOrganization(rs.getInt(1));
            	vo.setOrganizationName(rs.getString(2));
   
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("Database.java - OrganizationList - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		return v;
			
	}	
	
	
	/**
	 * Fix the String input to SQL Database.
	 * Add one more aphrostrophy to each aphrostrophy found in the String.
	 */
	public String SQLFixer(String statement) {
						
		String newStatement = statement.trim();
		String left = "";
		String right = "";
		
		int start = 0;
		int end = statement.indexOf("'");
		
		if(end >= 0)
			newStatement = "";
		
		while(end >= 0) {			
			left = statement.substring(start, end+1) + "'";	
			right = statement.substring(end+1, statement.length());
			newStatement = newStatement + left;
			
			statement = right;
			
			end = right.indexOf("'");									
		}
		
		newStatement = newStatement + right;

		return newStatement;
	}
}