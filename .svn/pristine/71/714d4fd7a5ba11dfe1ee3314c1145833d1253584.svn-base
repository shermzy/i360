package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voDivision;

/**
 * This class implements all the operations for Division table in the database.
 */
 
public class Division
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */

	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Division";
 
	public Division()
	{
		
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	/**
	 * check division exist in the database
	 * 
	 * @param DivisionName
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int checkDivExist(String DivisionName) throws SQLException, Exception
	{
		String command = "SELECT * FROM [Division] WHERE DivisionName = '"+ DivisionName + "'";
		int iPKDivision = 0; 

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
            	iPKDivision = rs.getInt("PKDivision");
            }
            
          
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - checkDivExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return iPKDivision;
	}
	
	/**
	 * Check Division Exist in the database.
	 * @param DivisionName
	 * @param FKOrganization
	 * @return 0 if not exist, else return PKDivision
	 * @throws SQLException
	 * @throws Exception
	 */
	public int checkDivExist(String DivisionName, int FKOrganization) throws SQLException, Exception
	{
		int iPKDivision = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String command = "SELECT * FROM [Division] WHERE DivisionName = '"+ DivisionName + "' and FKOrganization = " + FKOrganization;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKDivision = rs1.getInt("PKDivision");
		
		rs1.close();
		db.closeDB();*/
		
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

                        
            if(rs.next())
            {
            	iPKDivision = rs.getInt("PKDivision");
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - checkDivExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		return iPKDivision;
	}
	
	/**
	 * Check Division Exist in the database.
	 * @param DivisionName
	 * @param FKOrganization
	 * @return 0 if not exist, else return PKDivision
	 * @throws SQLException
	 * @throws Exception
	 */
	public int checkDivExist(String DivisionName, String DivisionCode, int FKOrganization) throws SQLException, Exception
	{
		int iPKDivision = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String command = "SELECT * FROM [Division] WHERE (DivisionName = '"+ DivisionName + "' OR DivisionCode = '"+ DivisionCode + "') and FKOrganization = " + FKOrganization;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKDivision = rs1.getInt("PKDivision");
		
		rs1.close();
		db.closeDB();*/
		
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

                        
            if(rs.next())
            {
            	iPKDivision = rs.getInt("PKDivision");
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - checkDivExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		return iPKDivision;
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		String DivisionName, int FKOrganization, int PKUser
	 *
	 */ 
	public boolean addRecord(String DivisionName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Division (DivisionName, FKOrganization) VALUES ('"+ DivisionName+"', "+FKOrganization+")";
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();*/

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
            System.err.println("Division.java - addRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
			
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, DivisionName, sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		String DivisionName, String DivisionCode, int FKOrganization, int PKUser
	 *
	 */ 
	public  boolean addRecord(String DivisionName, String DivisionCode, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		boolean bIsAdded = false;
		String sql = "INSERT INTO Division (DivisionName, DivisionCode, FKOrganization) VALUES ('"+ DivisionName+"', '"+ DivisionCode +"', "+FKOrganization+")";
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
            System.err.println("Division.java - addRecord- " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		if(PKUser != 0) {
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, DivisionName + ", Code = " + DivisionCode, sDetail[2], sDetail[11], sDetail[10]);
		}
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database without logging into event viewer
	 *
	 * Parameters:
	 *		String DivisionName, int FKOrganization, int PKUser
	 *
	 */ 
	public boolean addRecord(String DivisionName, int FKOrganization) throws SQLException, Exception 
	{
		//db.openDB();
		String sql = "INSERT INTO Division (DivisionName, FKOrganization) VALUES ('"+ DivisionName+"', "+FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
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
            System.err.println("Division.java - addRecord- " + E);
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
	 *		PKDivision		- primary key
	 *		DivisionName 
	 *
	 */
	 
	public boolean editRecord(int PKDivision, String DivisionName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM Division WHERE PKDivision = "+ PKDivision;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("DivisionName");
		
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
            	OldName = rs.getString("DivisionName");
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - editRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
        
		String sql = "UPDATE Division SET DivisionName = '" + DivisionName  + "' WHERE PKDivision = " + PKDivision+" AND FKOrganization="+FKOrganization;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();*/
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
	        System.err.println("Division.java - Division- " + E);
		}
		
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

    	}

		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+DivisionName+")", sDetail[2], sDetail[11], sDetail[10]);
		return bIsUpdated;
	}
	
	/**
	 * Edit a record in the database.
	 *
	 * Parameters:
	 *		PKDivision		- primary key
	 *		DivisionName 
	 *		DivisionCode
	 */
	 
	public boolean editRecord(int PKDivision, String DivisionName, String DivisionCode, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM Division WHERE PKDivision = "+ PKDivision;
		

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
            	OldName = rs.getString("DivisionName");
            }
            
      
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("DivisionName");
		
		rs1.close();
		db.openDB();*/
		String sql = "UPDATE Division SET DivisionName = '" + DivisionName  + "', DivisionCode = '" + DivisionCode + "' WHERE PKDivision = " + PKDivision+" AND FKOrganization="+FKOrganization;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();*/
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
	        System.err.println("Division.java - Division- " + E);
		}
		
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


    	}
		
		if(PKUser != 0) {
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+DivisionName+", Code = " + DivisionCode + ")", sDetail[2], sDetail[11], sDetail[10]);
		}
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		PKDivision 	- primary key
	 */
	 
	public boolean deleteRecord(int PKDivision, int FKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM Division WHERE PKDivision = "+ PKDivision;

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
            	OldName = rs.getString("DivisionName");
            }
           
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("DivisionName");
		
		rs1.close();
		db.openDB();*/
		String sql = "Delete from Division where PKDivision = " + PKDivision+" AND FKOrganization = "+FKOrganization;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		
		db.closeDB();*/
		
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
			System.err.println("Division.java - deleteRecord - " + E);
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
	 * Get Division
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllDivisions(int iFKOrg){
		
		
		Vector v = new Vector();

        String querySql = "SELECT * FROM Division WHERE FKOrganization="+iFKOrg+" ORDER BY DivisionName";
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
            	voDivision vo = new voDivision();
            	
        		vo.setPKDivision(rs.getInt("PKDivision"));
            	vo.setDivisionCode(rs.getString("DivisionCode"));
            	vo.setDivisionName(rs.getString("DivisionName"));
            	vo.setDivision(rs.getString("DivisionName"));
            	
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	
            	v.add(vo);
            }
            
          
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - getDivision - " + E);
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
	 * Get Division
	 * 
	 * @param iFKOrg
	 * @return
	 * @author Yuni
	 */
	public Vector getTargetDivisions(int iSurveyID) {
		
		
		Vector v = new Vector();

		String command1 = "SELECT DISTINCT Division.PKDivision, Division.DivisionName FROM Division INNER JOIN ";
        command1 += "tblAssignment ON Division.PKDivision = tblAssignment.FKTargetDivision WHERE ";
        command1 += "(tblAssignment.SurveyID = " + iSurveyID + ") ORDER BY Division.DivisionName";
		
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(command1);
            
            while(rs.next())
            {
            	voDivision vo = new voDivision();
            	
        		vo.setPKDivision(rs.getInt("PKDivision"));
            	vo.setDivisionName(rs.getString("DivisionName"));
            	vo.setDivision(rs.getString("DivisionName"));
            	
            	
            	v.add(vo);
            }
            
          
       
        }
        catch(Exception E) 
        {
            System.err.println("Division.java - getDivision - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }

        
        return v;
	}

}