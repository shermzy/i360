package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voEthnic;
import CP_Classes.vo.voLocation;

/**
 * This class implements all the operations for Location table in the database.
 */
 
public class Location
{

/**
 * Declaration of new object of class Database. 
 */
	
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Location";
 
	public Location()
	{
	
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		LocationName
	 *
	 */
	 
	public boolean addRecord(String LocationName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		String sql = "INSERT INTO Location (LocationName, FKOrganization) VALUES ('"+ LocationName +"', "+FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		boolean bIsAdded = false;
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
            System.err.println("Location.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
		
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, LocationName, sDetail[2], sDetail[11], sDetail[10]);
		
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database, no Event viewer calculation
	 *
	 * Parameters:
	 *		LocationName
	 *
	 */
	public boolean addRecord(String LocationName, int FKOrganization) throws SQLException, Exception 
	{
		//db.openDB();
		
		String sql = "INSERT INTO Location (LocationName, FKOrganization) VALUES ('"+ LocationName +"', "+FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		boolean bIsAdded = false;

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
            System.err.println("Location.java - addRecord - " + E);
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
	 *		PKLocation		- primary key
	 *		LocationName 
	 *
	 */
	 
	public boolean editRecord(int PKLocation, String LocationName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM Location WHERE PKLocation = "+PKLocation;
		
		/**ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("LocationName");
			
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
            	OldName = rs.getString("LocationName");
            }
          
       
        }
        catch(Exception E) 
        {
            System.err.println("Location.java - editRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		String sql = "UPDATE Location SET LocationName = '" + LocationName  + "', FKOrganization = "+ FKOrganization +" WHERE PKLocation = " + PKLocation;
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
		        System.err.println("Location.java - editRecord- " + E);
			}
			finally
	    	{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
	    	}
			
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+LocationName+")", sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		PKLocation 	- primary key
	 */
	 
	public boolean deleteRecord(int PKLocation, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM Location WHERE PKLocation = "+PKLocation;
		/**
		ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("LocationName");
		
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
            	OldName = rs.getString("LocationName");
            }
            
            
       
        }
        catch(Exception E) 
        {
            System.err.println("Location.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
        
		String sql = "Delete from Location where PKLocation = " + PKLocation;
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
			System.err.println("Location.java - deleteRecord - " + E);
			
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
	 * get Location
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllLocations(int iFKOrg){
		
		
    	Vector v = new Vector();

    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	
        String querySql = "SELECT * FROM Location WHERE LocationName != 'NA' AND FKOrganization="+iFKOrg+" ORDER BY LocationName";
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);            
            while(rs.next())
            {
            	voLocation vo = new voLocation();
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	vo.setPKLocation(rs.getInt("PKLocation"));
        		vo.setLocationName(rs.getString("LocationName"));
            	v.add(vo);
            }
            
            
       
        }
        catch(Exception E) 
        {
            System.err.println("Location.java - getLocation - " + E);
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
	 * check whether location already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(String sLocationName, int iFKOrg)
    {
    	boolean bIsExist = false;
    	String querySql = "SELECT * FROM Location WHERE LocationName = '"+sLocationName+"' AND FKOrganization="+iFKOrg;

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
            System.err.println("Location.java - existRecord - " + E);
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