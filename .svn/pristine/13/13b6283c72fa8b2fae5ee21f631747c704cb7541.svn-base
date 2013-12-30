package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voEthnic;

/**
 * This class implements all the operations for Ethnic table in the database.
 */
 
public class EthnicGroup
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */
	//private Database db;
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Ethnic Group";
 
	public EthnicGroup()
	{
		//db = new Database();
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		EthnicDesc
	 *
	 */
	 
	public boolean addRecord(String EthnicDesc, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		
		String sql = "INSERT INTO Ethnic (EthnicDesc, FKOrganization) VALUES ('"+ EthnicDesc +"', "+FKOrganization+")";
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
            System.err.println("EthnicGroup.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, EthnicDesc, sDetail[2], sDetail[11], sDetail[10]);

		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		EthnicDesc
	 *
	 */
	 
	public boolean addRecord(String EthnicDesc, int FKOrganization) throws SQLException, Exception 
	{
		String sql = "INSERT INTO Ethnic (EthnicDesc, FKOrganization) VALUES ('"+ EthnicDesc +"', "+FKOrganization+")";
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
            System.err.println("EthnicGroup.java - addRecord - " + E);
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
	 *		PKEthnic		- primary key
	 *		EthnicDesc 
	 *
	 */
	 
	public boolean editRecord(int PKEthnic, String EthnicDesc, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM Ethnic WHERE PKEthnic = "+PKEthnic;
		
		/**
		 * ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("EthnicDesc");
		
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
            	OldName = rs.getString("EthnicDesc");
            }
           
       
        }
        catch(Exception E) 
        {
            System.err.println("EthnicGroup.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		String sql = "UPDATE Ethnic SET EthnicDesc  = '" + EthnicDesc  + "', FKOrganization = "+ FKOrganization +" WHERE PKEthnic = " + PKEthnic;
		
		/**
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
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
	        System.err.println("EthnicGroups.java - editRecord- " + E);
		}
		finally
    	{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


    	}
		
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+EthnicDesc+")", sDetail[2], sDetail[11], sDetail[10]);
	
	   return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		PKEthnic 	- primary key
	 */
	 
	public boolean deleteRecord(int PKEthnic, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM Ethnic WHERE PKEthnic = "+PKEthnic;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		/**ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("EthnicDesc");
		
		rs1.close();
		db.openDB();
		*/
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);            
            if(rs.next())
            {
            	OldName = rs.getString("EthnicDesc");
            }
            
            rs.close();
            rs = null;
       
        }
        catch(Exception E) 
        {
            System.err.println("EthnicGroup.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection



        }
        
		String sql = "Delete from Ethnic where PKEthnic = " + PKEthnic;
		/**
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
			System.err.println("EthnicGroup.java - deleteRecord - " + E);
			
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
	 * get Ethnic
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 * 
	 */
	public Vector getAllEthnics(int iFKOrg)
    {
		
    	Vector v = new Vector();

        String querySql = "SELECT * FROM Ethnic WHERE EthnicDesc != 'NA' AND FKOrganization="+iFKOrg+" ORDER BY EthnicDesc" ;

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
            	voEthnic vo = new voEthnic();
            	
        		vo.setPKEthnic(rs.getInt("PKEthnic"));
        		vo.setEthnicDesc(rs.getString("EthnicDesc"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	v.add(vo);
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("EthnicGroup.java - getEthnic - " + E);
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
	 * check whether ethnic group already exists
	 * 
	 * @param iFKOrg
	 * @return true if it exists else false
	 * @author Yuni
	 */
	public boolean existRecord(String sEthnicDesc, int iFKOrg)
    {
    	boolean bIsExist = false;
    	String querySql = "SELECT * FROM Ethnic WHERE EthnicDesc = '"+sEthnicDesc+"' AND FKOrganization="+iFKOrg;

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
            System.err.println("EthnicGroup.java - existRecord - " + E);
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