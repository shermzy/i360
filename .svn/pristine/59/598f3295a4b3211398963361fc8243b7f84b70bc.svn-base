package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voJobLevel;
import CP_Classes.vo.voLocation;

/**
 * This class implements all the operations for Job Level table in the database.
 */
 
public class JobLevel
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	private Database db;
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "JobLevel";
 
	public JobLevel()
	{
		db = new Database();
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	/**
	 * Add a new record to the database.
	 *
	 * @param:
	 *		JobLevelName
	 *		FKOrganization
	 *		PKUser
	 *
	 */
	public boolean addRecord(String JobLevelName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded=false;
		String sql = "INSERT INTO JobLevel (JobLevelName, FKOrganization) VALUES ("+JobLevelName+","+FKOrganization+")";

		Connection con = null;
		Statement st = null;

		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.setString(1, JobLevelName);
		ps.setInt(2, FKOrganization);
		ps.executeUpdate();
		
		*/
		//db.closeDB();
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
            System.err.println("JobLevel.java - addRecord- " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
	
	
	
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, String.valueOf(JobLevelName), sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
	
	}
	
		/**
	 * Add a new record to the database without event viewer capturing
	 *
	 * @param:
	 *		JobLevelName
	 *		FKOrganization
	 *		PKUser
	 *
	 */
	public boolean addRecord(String JobLevelName, int FKOrganization) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded=false;
		String sql = "INSERT INTO JobLevel (JobLevelName, FKOrganization) VALUES ("+JobLevelName+","+FKOrganization+")";
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.setString(1, JobLevelName);
		ps.setInt(2, FKOrganization);
		ps.executeUpdate();
		*/
		//db.closeDB();

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
            System.err.println("JobLevel.java - addRecord- " + E);
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
	 * @param:
	 *		PKJobLevel
	 *		JobLevelName
	 *		FKOrganization
	 *		PKUser
	 *
	 */
	 
	public boolean editRecord(int PKJobLevel, String JobLevelName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM JobLevel WHERE PKJobLevel  = "+ PKJobLevel ;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobLevelName");
		
		rs1.close();
		db.openDB();*/

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
            	OldName = rs.getString("JobLevelName");
            }
            
          
       
        }
        catch(Exception E) 
        {
            System.err.println("JobLevel.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		String sql = "UPDATE JobLevel SET JobLevelName = '" + JobLevelName  + "' WHERE PKJobLevel = " + PKJobLevel +" AND FKOrganization="+FKOrganization;
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
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
		        System.err.println("JobLevel.java - editRecord- " + E);
			}
			finally
	    	{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

	    	}
			
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+JobLevelName+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * @param:
	 *		PKJobLevel
	 *		FKOrganization
	 *		PKUser
	 */
	 
	public boolean deleteRecord(int PKJobLevel, int FKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM JobLevel WHERE PKJobLevel  = "+ PKJobLevel ;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobLevelName");
		
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
            	OldName = rs.getString("JobLevelName");
            }
            
            
        }
        catch(Exception E) 
        {
            System.err.println("JobLevel.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		String sql = "Delete from JobLevel where PKJobLevel = " + PKJobLevel+" AND FKOrganization="+FKOrganization;
		
		/*
		 PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
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
			System.err.println("JobLevel.java - deleteRecord - " + E);
			
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
	 * get PKJobLevel based on JobLevelName.
	 *
	 * @param sJobLevelName	String
	 * @param iOrgID	int
	 *
	 * @return PKJobLevel
	 */
	public int getPKJobLevel(String sJobLevelName, int iOrgID) throws SQLException, Exception 
	{
		
		int iPKJobLevel = 0;
		String query = "SELECT PKJobLevel from JobLevel where JobLevelName = '" + sJobLevelName + "' AND FKOrganization = " + iOrgID;
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
        	rs=st.executeQuery(query);
        	if(rs.next())
    			iPKJobLevel = rs.getInt("PKJobLevel");
        }
        	catch(Exception E) 
            {
                System.err.println("JobLevel.java - getPKJobLevel - " + E);
            }
            finally
            {

            	ConnectionBean.closeRset(rs); //Close ResultSet
            	ConnectionBean.closeStmt(st); //Close statement
            	ConnectionBean.close(con); //Close connection

            }

		return iPKJobLevel;
	}
	/**
	 * Get Job Level
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllJobLevels(int iFKOrg){
		Vector v = new Vector();
		String querySql = "SELECT * FROM JobLevel WHERE FKOrganization="+iFKOrg+" AND JobLevelName != '-1' ORDER BY JobLevelName";
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
            	voJobLevel vo = new voJobLevel();
            	
            	vo.setPKJobLevel(rs.getInt("PKJobLevel"));
            	vo.setJobLevelName(rs.getString("JobLevelName"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	
            	v.add(vo);
            }
            
           
        }
        catch(Exception E) 
        {
            System.err.println("JobLevel.java - getJobLevel - " + E);
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
	 * check whether job level already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(String sJobLevelName, int iFKOrg)
    {
    	boolean bIsExist = false;
    	String querySql = "SELECT * FROM JobLevel WHERE JobLevelName = '"+sJobLevelName+"' AND FKOrganization="+iFKOrg;

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
            System.err.println("JobLevel.java - existRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return bIsExist;
    }



}