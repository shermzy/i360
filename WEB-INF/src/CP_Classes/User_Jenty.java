package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.voUser;

public class User_Jenty
{
	public User_Jenty()
	{
		//db = new Database();
	}
	
	/**
	 * Add a new record to the Age database.
	 *
	 * Parameters:
	 *		AgeRange
	 *
	 */
	public boolean addRecord(int FKDepartment, int FKDivision, int FKUserType, String FamilyName,String GivenName, 
							String LoginName, String Designation, String IDNumber, String Group_Section,String Password, 
							int IsEnabled, int FKCompanyID, int FKOrganization)
	{
		
		String query = "INSERT INTO [User] (FKDepartment, FKDivision, FKUserType, FamilyName, GivenName, LoginName, Designation, IDNumber, Group_Section, Password, IsEnabled, FKCompanyID,, FKOrganization) VALUES(" +FKDepartment+ ","+FKDivision+ "," +FKUserType+ ",'" +FamilyName+ "','" +GivenName+ "','" +LoginName+ "','" +Designation+ "','" +IDNumber+ "','" +Group_Section+ "','" +Password+ "'," +IsEnabled+ "," +FKCompanyID+ "," +FKOrganization+");";
		
		Connection con = null;
		Statement st = null;


		boolean bIsAdded = false;
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
            System.err.println("User_Jenty.java - addRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		return bIsAdded;
	}
	
	public boolean editRecord(int PKUser, int FKDepartment, int FKDivision, int FKUserType, String FamilyName, 
							String GivenName, String LoginName, String Designation, String IDNumber, String Group_Section,
							String Password, int IsEnabled) 
	{
		String query = "UPDATE [User] SET FKDepartment= " +FKDepartment+ ",";
		query = query+ "FKDivision= "+FKDivision+ ", FKUserType= " +FKUserType+ ", FamilyName= '" +FamilyName+ "',";
		query = query+ "GivenName= '" +GivenName+ "', LoginName= '" +LoginName+ "', Designation= '" +Designation+ "',";
		query = query+ "IDNumber= '" +IDNumber+ "', Group_Section= '" +Group_Section+ "', Password= '" +Password+ "',";
		query = query+ "IsEnabled= " +IsEnabled+ " WHERE PKUser=" +PKUser;
		
		boolean bIsUpdated = false;
        
		Connection con = null;
		Statement st = null;

		try	
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(query);
			if(iSuccess!=0)
				bIsUpdated=true;
			
		}
			
		catch(Exception E)
		{
	        System.err.println("User_Jenty.java - editRecord- " + E);
		}
		
		finally
	   	{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

	    }
		
		return bIsUpdated;
	}
	
	public boolean deleteRecord(int PKUser) throws SQLException, Exception
	{
		String sql = "Delete from [User] where PKUser = " + PKUser;
	
		boolean bIsDeleted = false;
 		
		Connection con = null;
		Statement st = null;

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
			System.err.println("User_Jenty.java - deleteRecord - " + E);
			
		}

		finally
		{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return bIsDeleted;
	}
	
	public Vector search_User(String FKDepartment, String FKDivision, String FKUserType, String FamilyName, 
							String GivenName, String LoginName, String Designation, String IDNumber, String Group_Section,
							int IsEnabled, int FKCompanyID, int FKOrganization)
	{
		String sSQL;
		Vector v = new Vector();
	
		sSQL = "SELECT * FROM [User] a, Division b, Department c, UserType d, [Group] e WHERE e.PKGroup = a.Group_Section AND b.PKDivision = a.FKDivision AND c.PKDepartment = a.FKDepartment AND d.PKUserType = a.FKUserType AND a.FKCompanyID="+FKCompanyID+" AND a.FKOrganization="+FKOrganization;
		
		if(FKDepartment != null  && FKDepartment.length() != 0)
		sSQL = sSQL + " AND a.FKDepartment ="+FKDepartment;
		
		if(FKDivision != null && FKDivision.length() != 0)
		sSQL = sSQL + " AND a.FKDivision =" +FKDivision;
		
		if(FKUserType != null && FKUserType.length() != 0)
		sSQL = sSQL + " AND a.FKUserType =" +FKUserType;
		
		if(FamilyName != null && FamilyName.length() != 0)
		sSQL = sSQL + " AND a.FamilyName LIKE '" +FamilyName+ "%'";
		
		if(GivenName != null && GivenName.length() != 0)
		sSQL = sSQL + " AND a.GivenName LIKE '" +GivenName+ "%'";
		
		if(LoginName != null && LoginName.length() != 0)
		sSQL = sSQL + " AND a.LoginName LIKE '" +LoginName+ "%'";
		
		if(Designation != null && Designation.length() != 0)
		sSQL = sSQL + " AND a.Designation LIKE '" +Designation+ "%'";

		if(IDNumber != null && IDNumber.length() != 0)
		sSQL = sSQL + " AND a.IDNumber LIKE '" +IDNumber+ "%'";
		
		if(Group_Section != null && Group_Section.length() != 0)
		sSQL = sSQL + " AND a.Group_Section LIKE '" +Group_Section+ "%'";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sSQL);
        	
            while(rs.next())
            {
            	voUser vo = new voUser();
            	vo.setGroup_Section(rs.getInt("Group_Section"));
            	vo.setIDNumber(rs.getString("IDNumber"));
            	vo.setDesignation(rs.getString("Designation"));
            	vo.setLoginName(rs.getString("LoginName"));
            	vo.setGivenName(rs.getString("GivenName"));
            	vo.setFamilyName(rs.getString("FamilyName"));
            	vo.setFKUserType(rs.getInt("FKUserType"));
            	vo.setFKDivision(rs.getInt("FKDivision"));
            	vo.setFKDepartment(rs.getInt("FKDepartment"));
            	
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("User_Jenty.java - search_User - " + E);
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
	 * Get user's email address
	 * @param PKUser
	 * @return
	 * @throws SQLException
	 * @author Maruli
	 */
	public String getUserEmail(int PKUser) throws SQLException
	{
		
		String sEmail = "";
		String sql = "SELECT Email FROM [User] WHERE PKUser = " + PKUser;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);

			if(rs.next())
			{
				sEmail = rs.getString("Email");
			}
			 
        }
        catch(Exception E) 
        {
            System.err.println("User_Jenty.java - getUserEmail - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
      
		return sEmail;
	}
	
	/*-------------------------------------User detail--------------------------------------------*/
	public String[] getUserDetail(int PKUser)throws SQLException
	{
		String sDetail[] = new String[13];
		String sql = "SELECT [User].FamilyName, [User].GivenName, [User].LoginName, ";
		sql = sql + "[User].Designation, [User].IDNumber, [User].Password, [User].IsEnabled, ";
		sql = sql + "Department.DepartmentName, Division.DivisionName, UserType.UserTypeName, ";
		sql = sql + "[Group].GroupName, tblOrganization.OrganizationName, ";
		sql = sql + "tblConsultingCompany.CompanyName, tblOrganization.OrganizationLogo FROM [User] INNER JOIN Department ON ";
		sql = sql + "[User].FKDepartment = Department.PKDepartment INNER JOIN ";
		sql = sql + "Division ON [User].FKDivision = Division.PKDivision INNER JOIN ";
		sql = sql + "UserType ON [User].FKUserType = UserType.PKUserType INNER JOIN ";
		sql = sql + "[Group] ON [User].Group_Section = [Group].PKGroup INNER JOIN ";
		sql = sql + "tblConsultingCompany ON [User].FKCompanyID = tblConsultingCompany.CompanyID ";
		sql = sql + "INNER JOIN tblOrganization ON ";
		sql = sql + "[User].FKOrganization = tblOrganization.PKOrganization ";
		sql = sql + "WHERE [User].PKUser = " + PKUser;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);

			if(rs.next())
		    {
		    	/* User */
		    	sDetail[0] = rs.getString("FamilyName");
		    	sDetail[1] = rs.getString("GivenName");
		    	sDetail[2] = rs.getString("LoginName");
		    	sDetail[3] = rs.getString("Designation");
		    	sDetail[4] = rs.getString("IDNumber");
		    	sDetail[12] = rs.getString("Password");	
		    	sDetail[5] = rs.getString("IsEnabled");
		  		sDetail[6] = rs.getString("DepartmentName");
		  		sDetail[7] = rs.getString("DivisionName");
		  		sDetail[8] = rs.getString("UserTypeName");
		  		sDetail[9] = rs.getString("GroupName");	
		  		sDetail[10] = rs.getString("OrganizationName");
		  		sDetail[11] = rs.getString("CompanyName");	
		  		sDetail[12] = rs.getString("OrganizationLogo");
		  	}
			 
        }
        catch(Exception E) 
        {
            System.err.println("User_Jenty.java - getUserDEtail - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
	  	return sDetail;
	}
	/*------------------------------------------------end user detail--------------------------------------*/


	/**
	  * Retrieve NameSequence from tblOrganization.
	  *
	  * Input: OrgID
	  */
	public int NameSequence(int OrgID) {
		String query = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int iNameSeq = -1;
		
		try {
			query = query + "select NameSequence from tblOrganization ";
			query = query + " WHERE PKOrganization = " + OrgID;
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				iNameSeq = rs.getInt(1);
			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		return iNameSeq;
	}
	
	/**
	  * Retrieve NameSequence from tblOrganization.
	  *
	  *	Input: SurveyID
	  */
	public int NameSequence_BySurvey(int SurvID) {
		String query = "";
	
		int iNameSeq = -1;
		
		query = query + "SELECT tblOrganization.NameSequence FROM tblOrganization INNER JOIN ";
      	query = query + "tblSurvey ON tblOrganization.PKOrganization = tblSurvey.FKOrganization ";
		query = query + "WHERE (tblSurvey.SurveyID = " + SurvID + ")";
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
		    {
		    	iNameSeq = rs.getInt(1);
		  	}
			 
        }
        catch(Exception E) 
        {
            System.err.println("User_Jenty.java - getNameSequence_BySurvey - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
        return iNameSeq;

	}
	
	
}