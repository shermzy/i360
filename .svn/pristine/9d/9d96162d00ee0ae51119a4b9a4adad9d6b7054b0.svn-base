package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;

import CP_Classes.vo.voJobFunction;

/**
 * This class implements all the operations for Job Function table in the database.
 */
 
public class JobFunction
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Job Function";
 
	public JobFunction()
	{
		
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		JobFunctionName
	 *
	 */
	public boolean addRecord(String JobFunctionName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO JobFunction (JobFunctionName, FKOrganization) VALUES ('"+ JobFunctionName +"',"+ FKOrganization+")";
		Connection con = null;
		Statement st = null;

		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;

		}
		catch(Exception E)
		{
            System.err.println("JobFunction.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
			
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
	
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, JobFunctionName, sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		JobFunctionName
	 *
	 */
	public boolean addRecord(String JobFunctionName, int FKOrganization) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded=false;
		String sql = "INSERT INTO JobFunction (JobFunctionName, FKOrganization) VALUES ('"+ JobFunctionName +"',"+ FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		Connection con = null;
		Statement st = null;


		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;

		}
		catch(Exception E)
		{
            System.err.println("JobFunction.java - addRecord- " + E);
		}
		finally
        {
		
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		return bIsAdded;
	}
	
	
	/**
	 * Edit a record in the database.
	 *
	 * Parameters:
	 *		PKJobFunction		- primary key
	 *		JobFunctionName 
	 *
	 */
	 
	public boolean editRecord(int PKJobFunction, String JobFunctionName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM JobFunction WHERE PKJobFunction = "+ PKJobFunction;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobFunctionName");
		
		rs1.close();
		db.openDB();
		*/

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

                        
            if(rs.next())
            {
            	OldName = rs.getString("JobFunctionName");
            }
            
            rs.close();
            rs = null;
       
        }
        catch(Exception E) 
        {
            System.err.println("JobFunction.java - JobFunction - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		String sql = "UPDATE JobFunction SET JobFunctionName = '" + JobFunctionName  + "' WHERE PKJobFunction = " + PKJobFunction+" AND FKOrganization="+FKOrganization;
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
        boolean bIsUpdated = false;
        
		try	
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;


	
		}
			
		catch(Exception E)
		{
	        System.err.println("JobFunction.java - JobFunction- " + E);
		}
		
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


    	}
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+JobFunctionName+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		PKJobFunction 	- primary key
	 */
	 
	public boolean deleteRecord(int PKJobFunction, int FKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM JobFunction WHERE PKJobFunction = "+ PKJobFunction;
		

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;



		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

            if(rs.next())
            {
            	OldName = rs.getString("JobFunctionName");
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("JobFunction.java - deleteRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobFunctionName");
		
		rs1.close();
		db.openDB();
		*/
		String sql = "Delete from JobFunction where PKJobFunction = " + PKJobFunction+" AND FKOrganization="+FKOrganization;
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
       boolean bIsDeleted = false;
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("JobFunction.java - deleteRecord - " + E);
			
		}

		finally
		{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
		return bIsDeleted;
	}
	/**
	 * Get JobFunction
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllJobFunctions(int iFKOrg){
		
		Vector v = new Vector();
		String querySql = "SELECT * FROM JobFunction WHERE FKOrganization="+iFKOrg+" AND JobFunctionName !='NA' ORDER BY JobFunctionName";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
          
            while(rs.next())
            {
            	voJobFunction vo = new voJobFunction();
            	
            	vo.setFKOrganization(rs.getInt("PKJobFunction"));
        		vo.setJobFunctionName(rs.getString("JobFunctionName"));
            	vo.setPKJobFunction(rs.getInt("PKJobFunction"));
        		v.add(vo);
            }
            
         
       
        }
        catch(Exception E) 
        {
            System.err.println("JobFunction.java - getJobFunction - " + E);
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
	 * check whether job function already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(String sFunctionName, int iFKOrg)
    {
    	boolean bIsExist = false;
    	String querySql = "SELECT * FROM JobFunction WHERE JobFunctionName = '"+sFunctionName+"' AND FKOrganization="+iFKOrg;

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
        
        	if(rs.next())

            	bIsExist = true;
       
        }
        catch(Exception E) 
        {
            System.err.println("JobFunction.java - existRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return bIsExist;
    }

	public int getPKJobFunction(String sFunctionName, int iOrgID) throws SQLException, Exception 
	{
		
		int iPKJobFunction = 0;
		String querySql = "SELECT * FROM JobFunction WHERE JobFunctionName = '"+sFunctionName+"' AND FKOrganization="+iOrgID;
		
		/*	
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next())
			iPKJobLevel = rs.getInt("PKJobLevel");
				
		db.closeDB();
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
        	if(rs.next())
        		iPKJobFunction = rs.getInt("PKJobFunction");
        }
    	catch(Exception E) 
        {
            System.err.println("JobFunction.java - getPKJobFunction - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }

		return iPKJobFunction;
	}
}