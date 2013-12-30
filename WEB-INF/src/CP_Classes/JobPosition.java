package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;

import CP_Classes.vo.votblJobPosition;

/**
 * This class implements all the operations for Job Position table in the database.
 */
 
public class JobPosition
{
	/**
	 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
	 */

	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "JobPosition";
 
	public JobPosition()
	{
		
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	/**
	 * Retrieve the position for all specific survey name.
	 */
	public String getPositionName(int surveyID) throws SQLException, Exception  
	{
		String query = "SELECT DISTINCT tblJobPosition.JobPosition FROM tblSurvey INNER JOIN ";
		query += "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query += "WHERE tblSurvey.SurveyID = '" + surveyID + "'";
		query += " ORDER BY tblJobPosition.JobPosition";
		String strPos = "";
/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
	
		
		
		
		while(rs != null && rs.next())
			strPos = rs.getString(1);
			*/	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
            
			while(rs != null && rs.next())
				strPos = rs.getString(1);
            
       }
        catch(Exception E) 
        {
            System.err.println("JobPosition.java - getPositionName - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return strPos;
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		JobPosition
	 *
	 */
	 
	public boolean addRecord(String JobPosition, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded=false;
		String sql = "INSERT INTO tblJobPosition (JobPosition, FKOrganization) VALUES ('"+ JobPosition +"',"+ FKOrganization+")";;
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
            System.err.println("JobPosition.java - addRecord- " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

 }
		
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, JobPosition, sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the database.
	 *
	 * Parameters:
	 *		JobPositionID		- primary key
	 *		JobPosition 
	 *
	 */
	 
	public boolean editRecord(int JobPositionID, String JobPosition, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblJobPosition WHERE JobPositionID  = "+ JobPositionID ;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobPosition");
		
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
            	OldName = rs.getString("JobPosition");
            }
            
          
       
        }
        catch(Exception E) 
        {
            System.err.println("JobPosition.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		String sql = "UPDATE tblJobPosition SET JobPosition = '" + JobPosition  + "' WHERE JobPositionID = " + JobPositionID+" AND FKOrganization="+FKOrganization;
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
	        System.err.println("JobPosition.java - editRecord- " + E);
		}
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

    	}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+JobPosition+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		JobPositionID 	- primary key
	 */
	 
	public boolean deleteRecord(int JobPositionID, int FKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblJobPosition WHERE JobPositionID  = "+ JobPositionID ;
		/*
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("JobPosition");
		
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
            	OldName = rs.getString("JobPosition");
            }
            
        
       
        }
        catch(Exception E) 
        {
            System.err.println("JobPosition.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		String sql = "Delete from tblJobPosition where JobPositionID = " + JobPositionID+" AND FKOrganization="+FKOrganization;
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
			System.err.println("JobPosition.java - deleteRecord - " + E);
			
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
	 * Get JobPosition
	 * 
	 * @param iFKOrg
	 * @return
	 * @author james
	 */
	public Vector getAllJobPositions(int iFKOrg){
		String querySql = "SELECT * FROM tblJobPosition WHERE FKOrganization="+iFKOrg+" ORDER BY JobPosition";
	
		Vector v = new Vector();

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
            	votblJobPosition vo = new votblJobPosition();
            	
            	vo.setJobPositionID(rs.getInt("JobPositionID"));
            	vo.setJobPosition(rs.getString("JobPosition"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	
            	
            	v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("JobPosition.java - getJobPosition - " + E);
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
	 * Get all the Job Positions
	 * 
	 * @param iFKOrg
	 * @param iFKComp
	 * @return Vector
	 * @author Yuni
	 */
	public Vector getAllJobPositions(int iFKComp, int iFKOrg){
		
		String command = "SELECT * FROM tblJobPosition ";
		
		if(iFKOrg != 0)
		{
			command = command+"WHERE FKOrganization ="+ iFKOrg;
		}
		else
		{
			command = command+"INNER JOIN tblOrganization ON tblJobPosition.FKOrganization = tblOrganization.PKOrganization INNER JOIN ";
	        command = command+"tblConsultingCompany ON tblOrganization.FKCompanyID = tblConsultingCompany.CompanyID ";
			command = command+"WHERE (tblConsultingCompany.CompanyID = "+iFKComp+")";
		}
			
		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(command);

                        
            while(rs.next())
            {
            	votblJobPosition vo = new votblJobPosition();
            	
            	vo.setJobPositionID(rs.getInt("JobPositionID"));
            	vo.setJobPosition(rs.getString("JobPosition"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	
            	
            	v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("JobPosition.java - getAllJobPositions - " + E);
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
	 * check whether job position already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(String sJobPosition, int iFKOrg)
    {
    	boolean bIsExist = false;
    	String querySql = "SELECT * FROM tblJobPosition WHERE JobPosition = '"+sJobPosition+"' AND FKOrganization="+iFKOrg;

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
	
	public int getPKJobPosition(String sJobPosition, int iOrgID) throws SQLException, Exception 
	{
		
		int iPKJobPosition = 0;
		String query = "SELECT PKJobPosition from JobPosition where JobPosition = '" + sJobPosition + "' AND FKOrganization = " + iOrgID;
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
    			iPKJobPosition = rs.getInt("PKJobPosition");
        }
    	catch(Exception E) 
        {
            System.err.println("JobPosition.java - getPKJobPosition - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }

		return iPKJobPosition;
	}

}