package CP_Classes;
import java.sql.*;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblConsultingCompany;


/**
 * This class implements all the operations for tblOrganization table in the database.
 */
 
public class ConsultingCompany
{
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
	private String itemName = "Consulting Company";
 
	public ConsultingCompany()
	{

		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/*
	 * Add a new record to the Consulting Company database.
	 * @Karen completed con pool
	 * To automatically create a consulting company organisation when a new consulting 
	 * company is added and to add an admin account for the consulting company organisation
	 * by calling addOrganisationByCons method in Organization.java
	 */
	public boolean addRecord(String CompanyName, String CompanyDesc, String OrgCode, int PKUser) throws SQLException, Exception 
	{	
		// Added to access method in Organization class
		// Mark Oei 09 Mar 2010
		Organization org = new Organization();
		
		boolean bIsAdded = false;
		
		String sql = "INSERT INTO tblConsultingCompany (CompanyName, CompanyDesc)";
		sql = sql +" VALUES ('"+CompanyName+"',"; 
		
		if(CompanyDesc != null)
			sql = sql +" '"+CompanyDesc+"'";
		
		sql = sql +" )";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
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
            System.err.println("ConsultingCompany.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, CompanyName, sDetail[2], sDetail[11], sDetail[10]);
		
		// Added to call method in Organization class to create an organization
		// with default Admin account and password
		// Mark Oei 09 Mar 2010
		org.addOrganisationByCons(OrgCode, CompanyName, getNewCompanyID(CompanyName), 0, PKUser, "Yes");
		
		return bIsAdded;
	}	
	
	/*
	 * Get the companyID of the new Consulting Company from the database.
	 * @param companyID
	 * @return int
	 * @author Mark Oei 
	 * @since v.1.3.12.63 (09 Mar 2010)
	 */
	public int getNewCompanyID(String CompanyName) throws SQLException, Exception 
	{	   
		int companyID = 0;
		CompanyName = "\'"+CompanyName+"\'";
		String sql = "SELECT * FROM tblConsultingCompany where CompanyName =" + CompanyName;
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
            {
            	companyID = rs.getInt("CompanyID");
            }           
		}
		catch(Exception E)
		{
            System.err.println("ConsultingCompany.java - getCompanyID - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
	
		return companyID;
	}	// End of getNewCompanyID
	
	
	/**
	 * checkRecordExist
	 * 
	 * @param CompanyName, CompanyDesc, PKUser, CompanyID, action
	 * @return boolean
	 * 			true if a company name already exist in database
	 * 			false if not	
	 * @author Ha by 10/06/08
	 * throw Exception: SQLException, Exception
	 * 
	 */
	public boolean checkRecordExist(String CompanyName, String CompanyDesc, int PKUser, int CompanyID, int action) throws SQLException, Exception 
	{
		boolean isExist = false;
		String check = "SELECT * FROM tblConsultingCompany ";
		check += "WHERE CompanyName = '"+CompanyName+"'"; 
		if (action==2) check += " and CompanyID <> "+CompanyID;//action = 2: edit company, action = 1 :add a new company
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs = st.executeQuery(check);
			if (rs.next()) 
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con);
				isExist = true; 
			}
			
		}
		catch (Exception e)
		{			
			System.err.println("ConsultingCompany.java - checkRecordExist - " + e);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
		return isExist;
	}
	
	
	/**
	 * Edit a record in the Consulting Company database.
	 *
	 */
	 
	public boolean editRecord(int CompanyID, String CompanyName, String CompanyDesc, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblConsultingCompany WHERE CompanyID = "+CompanyID;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("CompanyName");
		
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
            	OldName = rs.getString("CompanyName");
            }           
       
        }
        catch(Exception E) 
        {
            System.err.println("ConsultingCompany.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		String sql = "UPDATE tblConsultingCompany SET CompanyName = '" + CompanyName + "', CompanyDesc = '" +CompanyDesc+ "'";
		sql = sql +" WHERE CompanyID = " + CompanyID;
		
		
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();*/
		
		boolean bIsUpdated = false;
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
				bIsUpdated=true;

		}
		catch(Exception E)
		{
            System.err.println("Group.java - editRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+CompanyName+")", sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsUpdated;
	}
	
	/**
	 * Delete Consulting Company from database.
	 *
	 */
	 
	public boolean deleteRecord(int CompanyID, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblConsultingCompany WHERE CompanyID = "+CompanyID;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("CompanyName");
		
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
            	OldName = rs.getString("CompanyName");
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("ConsultingCompany.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
		String sql = "Delete from tblConsultingCompany where CompanyID = " + CompanyID;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.execute();*/
				
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
			System.err.println("ConsultingCompany.java - deleteRecord - " + E);
			
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
	 * Get Consulting Company
	 * 
	 * @param iConsultingCompanyID
	 * @return
	 * @author Eric Lu
	 */
	public votblConsultingCompany getConsultingCompany(int iConsultingCompanyID) 
	{
		votblConsultingCompany vo = new votblConsultingCompany();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String command = "SELECT * FROM tblConsultingCompany WHERE CompanyID = "+ iConsultingCompanyID;
		try {
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

			   if(rs.next())
	            {
	            	
	            	vo.setCompanyID(rs.getInt("CompanyID"));
	            	vo.setCompanyName(rs.getString("CompanyName"));
	            	vo.setCompanyDesc(rs.getString("CompanyDesc"));
	            	
	            }
			   
			   

		} catch (SQLException SE) {
			System.err.println("ConsultingCompany.java - getConsultingCompany - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			
			return vo;
		
		
	}
	
}