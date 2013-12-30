package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblRelationSpecific;
import CP_Classes.vo.votblTimeFrame;

/**
 * This class implements all the operations for Time Frame table in the database.
 */
 
public class TimeFrame
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Time Frame";
 
	public TimeFrame()
	{
		
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		TimeFrame
	 *
	 */
	 
	public boolean addRecord(String TimeFrame, int TimeType, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
	
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;

		if(TimeType == 1)
			TimeFrame = TimeFrame +" Month(s)";
		else
			TimeFrame = TimeFrame +" Year(s)";	
					
		String sql = "INSERT INTO tblTimeFrame (TimeFrame, TimeType, FKOrganization) VALUES ('"+ TimeFrame +"', "+ TimeType +", "+FKOrganization+")";
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		*/
		

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
            System.err.println("TimeFrame.java - addRecord- " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection



        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, TimeFrame, sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the database.
	 *
	 * Parameters:
	 *		TimeFrameID		- primary key
	 *		TimeFrame 
	 *
	 */
	 
	public boolean editRecord(int TimeFrameID, String TimeFrame, int TimeType, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblTimeFrame WHERE TimeFrameID = "+TimeFrameID;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("TimeFrame");
		
		rs1.close();
		db.openDB();
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
            //ResultSet rs=sqlMethod.rsQuery(command);
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
          
            if(rs.next())
            {
            	OldName = rs.getString("TimeFrame");
            }
       
       
        }
        catch(Exception E) 
        {
            System.err.println("TimeFrame.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
		if(TimeType == 1)
			TimeFrame = TimeFrame +" Month(s)";
		else
			TimeFrame = TimeFrame +" Year(s)";
		
		String sql = "UPDATE tblTimeFrame SET TimeFrame = '" + TimeFrame  + "', TimeType =" + TimeType  + ", FKOrganization ="+ FKOrganization +" WHERE TimeFrameID = " + TimeFrameID;
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		*/
		 boolean bIsUpdated = false;


			try	
			{
				//bIsUpdated = sqlMethod.bUpdate(sql);

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0)
				bIsUpdated=true;

			}
				
			catch(Exception E)
			{
		        System.err.println("TimeFrame.java - editRecord- " + E);
			}
			
			finally
	    	{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

	    	}
			
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+TimeFrame+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		TimeFrameID 	- primary key
	 */
	 
	public boolean deleteRecord(int TimeFrameID, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblTimeFrame WHERE TimeFrameID = "+TimeFrameID;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("TimeFrame");
		
		rs1.close();
		db.openDB();
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
        {          
            //ResultSet rs=sqlMethod.rsQuery(command);
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
                  
            if(rs.next())
            {
            	OldName = rs.getString("TimeFrame");
            }
            
         
       
        }
        catch(Exception E) 
        {
            System.err.println("TimeFrame.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		String sql = "Delete from tblTimeFrame where TimeFrameID = " + TimeFrameID;
		/*
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
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
				System.err.println("TimeFrame.java - deleteRecord - " + E);
				
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
	 * Get Time Frame
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllTimeFrames(int iFKOrg){
		
		Vector v = new Vector();
		String query = "SELECT * FROM tblTimeFrame WHERE FKOrganization="+iFKOrg+" ORDER BY TimeType, TimeFrame";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
        try
        { 
        	
        	
           // ResultSet rs=sqlMethod.rsQuery(query);
        	con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
                        
            while(rs.next())
            {
            	votblTimeFrame vo = new votblTimeFrame();
            	
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	vo.setTimeFrame(rs.getString("TimeFrame"));
            	vo.setTimeFrameID(rs.getInt("TimeFrameID"));
            	vo.setTimeType(rs.getInt("TimeType"));
            	
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("TimeFrame.java - getTimeFrame - " + E);
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
	 * check whether timeframe of the timetype already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	/**
	 *	checked by junwei 28 feb 2008
	 */
	public boolean existRecord(String sTimeFrame, int iTimeType, int iFKOrg)
    {
		if(iTimeType == 1)
			sTimeFrame = sTimeFrame +" Month(s)";
		else
			sTimeFrame = sTimeFrame +" Year(s)";
		
    	boolean bIsExist = false;
    	//Modfiy the SQL by James 30-June 2008
    	String querySql = "SELECT * FROM tblTimeFrame WHERE TimeFrame = '"+sTimeFrame+"' AND FKOrganization='"+iFKOrg+"'";

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
            System.err.println("JobPosition.java - existRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return bIsExist;
    }

	public int getPKTimeFrame(String sTimeFrame, int iTimeType, int iFKOrg) throws SQLException, Exception 
	{
		int iPKTimeFrame = 0;
		
		String querySql = "SELECT * FROM tblTimeFrame WHERE TimeFrame = '"+sTimeFrame+"' AND TimeType = "+iTimeType+" AND FKOrganization="+iFKOrg;
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
    			iPKTimeFrame = rs.getInt("PKTimeFrame");
        }
    	catch(Exception E) 
        {
            System.err.println("TimeFrame.java - getPKTiemFrame - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }

		return iPKTimeFrame;
	}
	
	/*****************************************************
	 * Added by junwei on March 5 2008 to cehck time frame
	 * @param sTimeFrame
	 * @param iTimeType
	 * @param iFKOrg
	 * @return true if time frame exist in database else false
	 ********************************************************/
	public boolean checkTimeFrameExist(String sTimeFrame, int iTimeType, int iFKOrg) {
		
		if(iTimeType == 1)
			sTimeFrame = sTimeFrame +" Month(s)";
		else
			sTimeFrame = sTimeFrame +" Year(s)";
		
		String querySql = "SELECT * FROM tblTimeFrame WHERE TimeFrame = '"+sTimeFrame+"' " +
				"AND TimeType = "+iTimeType+" AND FKOrganization="+iFKOrg;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
        	
        	if(rs.next())
        		return true;
    			
        }
    	catch(Exception E) 
        {
            System.err.println("TimeFrame.java - checkTimeFrameExist - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return false;
	}
}