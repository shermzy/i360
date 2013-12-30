package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voAge;
/**
 * This class implements all the operations for Age table in the database.
 */
 
public class AgeRange
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Age Range";
 
	public AgeRange()
	{
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/**
	 * Add a new record to the Age database.
	 *
	 * Parameters:
	 *		AgeRange
	 *
	 */
	public boolean addRecord(int AgeRangeTop, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		
		String sql = "INSERT INTO Age (AgeRangeTop,FKOrganization) VALUES ("+ AgeRangeTop +", "+FKOrganization+")";
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
            System.err.println("AgeRange.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, String.valueOf(AgeRangeTop), sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the Age database.
	 *
	 * Parameters:
	 *		PKAge		- primary key
	 *		AgeRange 
	 *
	 */
	public boolean editRecord(int PKAge, int AgeRangeTop, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM Age WHERE PKAge = "+PKAge;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("AgeRangeTop");
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
            	OldName = rs.getString("AgeRangeTop");
            }
            
         
        }
        catch(Exception E) 
        {
            System.err.println("AgeRange.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		String sql = "UPDATE Age SET AgeRangeTop = " + AgeRangeTop + ", FKOrganization = "+FKOrganization+" WHERE PKAge = " + PKAge;
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
	        System.err.println("AgeRange.java - editRecord- " + E);
		}
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
    	}

		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+AgeRangeTop+")", sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the Age database.
	 *
	 * Parameters:
	 *		PKAge 	- primary key
	 */
	public boolean deleteRecord(int PKAge, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM Age WHERE PKAge = "+PKAge;
		

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;



		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("AgeRangeTop");
		rs1.close();
		
		db.openDB();*/
		
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

                        
            if(rs.next())
            {
            	OldName = rs.getString("AgeRangeTop");
            }
         
       
        }
        catch(Exception E) 
        {
            System.err.println("AgeRange.java - deleteRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		String sql = "Delete from Age where PKAge = " + PKAge;
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
			System.err.println("AgeRange.java - deleteRecord - " + E);
			
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
	 * get Age
	 * 
	 * @param iFKOrg
	 * @return Vector of voAge objects
	 * @author Yuni
	 */
	public Vector getAllAges(int iFKOrg)
    {

    	Vector v = new Vector();

        String querySql = "SELECT * FROM Age WHERE AgeRangeTop > 0 AND FKOrganization="+iFKOrg+" ORDER BY AgeRangeTop ";
      
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
            	voAge vo = new voAge();

        		vo.setPKAge(rs.getInt("PKAge"));
        		vo.setAgeRangeTop(rs.getInt("AgeRangeTop"));
            	
            	v.add(vo);
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("AgeRange.java - getAge - " + E);
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
	 * check whether age range already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(int iAgeRange, int iFKOrg)
    {
    	boolean bIsExist = false;
        String querySql = "SELECT * FROM Age WHERE AgeRangeTop ="+iAgeRange+" AND FKOrganization="+iFKOrg;
      
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
            System.err.println("AgeRange.java - existRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return bIsExist;
    }
	
	/**
	 * check whether age range already exists for edit
	 * Date : 05/06/2008
	 * @param iFKOrg and AgeID
	 * @return true if it exists else false
	 * @author Hemilda
	 */
	public boolean existRecord_Edit(int iAgeRange, int iFKOrg , int PKAge)
    {
    	boolean bIsExist = false;
        String querySql = "SELECT * FROM Age WHERE AgeRangeTop ="+iAgeRange+" AND FKOrganization="+iFKOrg+" AND PKAge <> " + PKAge;
      
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
            System.err.println("AgeRange.java - existRecord - " + E);
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