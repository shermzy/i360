package CP_Classes;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.lang.String;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblAssignment;
import CP_Classes.vo.votblConsultingCompany;
import CP_Classes.vo.votblOrganization;

/**
 * The Organization Class is to be used for Organization connections using JdbcOdbc Driver.
 * 
 * Change Log
 * ==========
 *
 * Date        By                Method(s)            				Change(s) 
 * =====================================================================================================
 * 26/09/11    Gwen Oh          getAllOrganizations()				Changed the SQL statement to get the company name as well
 *
 * 13/07/2012  liu Taichen		getEmailSubject_SurvID(int, int)	To allow retrieving of emailIndividualreport template
 *
 * 13/07/2012  liu Taichen		getEmailtemplate_SurvID(int, int)	To allow retrieving of emailIndividualreport template
 */

public class Organization
{

	private Division div;
	private Department dept;
	private Group G;
	private User U;
 	private EventViewer ev;
 	private MailHTMLStd Email;
	private Setting server;
	private EmailTemplate template;
 	
	private Create_Edit_Survey detail;
 	
 	private String sDetail[] = new String[13];
 	private String itemName = "Organization";
 	
 	public int SortType;
 	private int SortType_org;
	
	private int Toggle [];	// 0=asc, 1=desc
	private int Toggle_org [];	// 0=asc, 1=desc
 	
	public Organization()
	{
	
		ev = new EventViewer();
		div = new Division();
		dept = new Department();
		G = new Group();
		U = new User();
		
		Email = new MailHTMLStd();
		server = new Setting();
		template = new EmailTemplate();
		
		detail = new Create_Edit_Survey();
				
		Toggle = new int [6];
		
		for(int i=0; i<6; i++)
		{
			Toggle[i] = 0;
		}
		
		Toggle_org = new int [6];
		
		for(int i=0; i<6; i++)
		{
			Toggle_org[i] = 0;
		}
		
		SortType = 1;
		SortType_org = 1;
	}
	
	
	public static void main (String [] args)throws SQLException, Exception 
	{
		Organization org = new Organization();
		
		//org.checkRecord(50, "TMT", 17, 6404, "Toyota");
		/*MailHTMLStd Email = new MailHTMLStd();
		EmailTemplate template = new EmailTemplate();
		
		String content = template.ForgotPass_temp("admintmt", "admintmtpass") + " &#3651;&#3627;&#3657;&#3588;&#3623;&#3634;&#3617;&#3594;&#3656;&#3623;";
		System.out.println(content);
		String email = "yuni@pcc.com.sg";
		Email.sendMail("yuni@pcc.com.sg", email, "New Admin Assignment for TYT", content);
		*/
	}
	
	/**
	 * Add a new record to the Organization database.
	 * @param OrganizationCode
	 * @param OrganizationName
	 * @param FKCompanyID
	 * @param NameSequence
	 * @param PKUser
         * @param nomRater
	 * @throws SQLException
	 * @throws Exception
	 */
        // Changed by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
	public boolean addRecord(String OrganizationCode, String OrganizationName, int FKCompanyID, int NameSequence, int PKUser, String nomRater) throws SQLException, Exception 
	{
		Connection con = null;
		Statement st = null;


		boolean bIsAdded = false;
		// Changed by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
		String sql = "INSERT INTO tblOrganization (OrganizationCode, OrganizationName, FKCompanyID, NameSequence, NominationModule)";
		sql = sql +" VALUES ('"+ OrganizationCode+"', '"+OrganizationName+"', "+FKCompanyID+", "+NameSequence +", '"+ Boolean.parseBoolean(nomRater) +"')";
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			System.out.println(iSuccess);
			if(iSuccess!=0)
			bIsAdded=true;


		}
		catch(Exception E)
		{
            System.err.println("Organization.java - AddRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		/*
		 * Codes commented to prevent addition of Admin account when a new
		 * organisation is added
		 * Mark Oei 09 Mar 2010
		 */
		
//		System.out.println("1. Add Organization");
//						
//		// add default under the organization.		
//		String defaultName = "NA";
//		int FKOrganization = checkOrgExist(OrganizationCode, OrganizationName, FKCompanyID);
//		System.out.println("testing " + FKOrganization);
//		System.out.println("2. Check Organization Exist");
//		if(FKOrganization != 0) {
//			div.addRecord(defaultName, FKOrganization, PKUser);
//			System.out.println("3. Add Division");
//			
//			dept.addRecord(defaultName, FKOrganization, PKUser);
//			System.out.println("4. Add Department");
//			
//			G.addRecord(defaultName, FKOrganization, PKUser);
//			System.out.println("5. Add Group");
//			
//			int FKDivision = div.checkDivExist(defaultName, FKOrganization);
//			int FKDepartment = dept.checkDeptExist(defaultName, FKOrganization);
//			int FKGroup = G.checkGroupExist(defaultName, FKOrganization);
//			
//			/*System.out.println("FKDivision = " + FKDivision);
//			System.out.println("FKDepartment = " + FKDepartment);
//			System.out.println("FKGroup = " + FKGroup);*/
//			
//			// Link newly created Department with Division
//			dept.linkDepartment(FKDivision, FKDepartment);
//			// Link newly created Group with Department
//			G.linkGroup(FKDepartment, FKGroup);
//			
//			Date timeStamp = new java.util.Date();
//			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
//			String temp  =  dFormat.format(timeStamp);
//			String loginName = OrganizationCode + "admin";
//			String password = OrganizationCode + temp;
//			int userType = 6;
//			 
//			U.addRecord(FKDepartment, FKDivision, userType, "Admin", "Admin", 
//							loginName, "NA", "NA", FKGroup, password, 1, FKCompanyID, FKOrganization, "NA", PKUser);
//			
//			System.out.println("6. Add User");
//			int userExist = U.checkUserExist(FKDepartment, FKDivision, userType, "Admin", "Admin", loginName, "NA", "NA", FKGroup, password, 1, FKCompanyID, FKOrganization);
//			
//			System.out.println("FKDivision = " + FKDivision + ", FKDepartment = " + FKDepartment + ", FKGroup = " + FKGroup + " and User Exist = " + userExist);
//							
//			if(userExist != 0) {
//				try {
//					U.insertRelation(userExist, userExist, 0);
//				}catch(SQLException SE) {System.out.println(SE.getMessage());}
//				
//				String content = template.ForgotPass_temp(loginName, password);
//				String email = "3SixtyAdmin@pcc.com.sg";
//				//Edited By Roger 13 June 2008
//				Email.sendMail(server.getAdminEmail(), email, "New Admin Assignment for " + OrganizationName, content, FKOrganization);
//			}
//			
//			System.out.println("8. Add User Relation");
//		}
//		
//		sDetail = detail.getUserDetail(PKUser);
//		ev.addRecord("Insert", itemName, OrganizationName, sDetail[2], sDetail[11], sDetail[10]);
		
	   return bIsAdded;
	}	// End Method for addRecord
	
	
	/**
	 * Edit a record in the Organization database.
	 *
	 */
	// Changed by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
	public boolean editRecord(int PKOrganization, String OrganizationCode, String OrganizationName, int FKCompanyID, int NameSequence, int PKUser, String nomRater) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblOrganization WHERE PKOrganization = "+PKOrganization;
		
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
            	OldName = rs.getString("OrganizationName");
            }
            
            rs.close();
            rs = null;
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
			
		
                // Changed by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
		String sql = "UPDATE tblOrganization SET OrganizationCode = '" + 
                        OrganizationCode + "', OrganizationName = '" + 
                        OrganizationName + "', FKCompanyID = " + FKCompanyID+
                        ", NameSequence = " + NameSequence + 
                        ", NominationModule = '" + Boolean.parseBoolean(nomRater) + "'";
		sql = sql +" WHERE PKOrganization = " + PKOrganization;

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
	        System.err.println("Organization.java - editRecord - " + E);
		}
		finally
	    {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


	    }
		
		sDetail = detail.getUserDetail(PKUser);
		try {
			ev.addRecord("Update", itemName, "("+OldName+") - ("+OrganizationName+")", sDetail[2], sDetail[11], sDetail[10]);
			}catch(SQLException SE) {}
			return bIsUpdated;

	}
	
	
	/**
	 * Delete an existing record from the Age database.
	 *
	 */
	
	public boolean deleteRecord(int PKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM tblOrganization WHERE PKOrganization = "+PKOrganization;
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
            	OldName = rs.getString("OrganizationName");
            }
            
          
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - deleteRecord - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
		String sql = "Delete from tblOrganization where PKOrganization = " + PKOrganization;
		
		boolean bIsDeleted = false;
		
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;
  		
		} catch (Exception E){
			System.err.println("Organization.java - deleteRecord - " + E);
			
		}finally {
			ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
		}
		
		sDetail = detail.getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsDeleted;
	}


	/**
	 * Set the logo parh.
	 */
	public boolean editLogo(int PKOrganization, String path, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM tblOrganization WHERE PKOrganization = "+PKOrganization;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("OrganizationLogo");
			
		rs1.close();
		db.openDB();*/
		
		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

		                
		    if(rs.next())
		    {
		    	OldName = rs.getString("OrganizationLogo");
		    }
		        
        
		   
		    }
		    catch(Exception E) 
		    {
		        System.err.println("Organization.java - editLogo - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
		
		String sql = "UPDATE tblOrganization SET OrganizationLogo = '" + path + "' WHERE PKOrganization = " + PKOrganization;
		
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
	        System.err.println("Organization.java - editLogo- " + E);
		}
		finally
	    {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
	    }
		
		sDetail = detail.getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "("+OldName+") - ("+path+")", sDetail[2], sDetail[11], sDetail[10]);

		return bIsUpdated;
	}
	
	
	/**
	 * Check Organization exist.
	 */
	public int checkOrgExist(String OrganizationCode, String OrganizationName, int FKCompanyID) throws SQLException, Exception 
	{
		int iPKOrganization = 0;
		//Changed by Ha 09/06/08 two org are the same if they have the same name
		
		String command = "SELECT * FROM tblOrganization WHERE OrganizationName = '" + OrganizationName + "'";
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKOrganization = rs1.getInt("PKOrganization");
			
		rs1.close();*/

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
            	iPKOrganization = rs.getInt("PKOrganization");
            }
            
            
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - checkOrgExist - " + E);
        }
        finally
        {
        ConnectionBean.closeRset(rs); //Close ResultSet
        ConnectionBean.closeStmt(st); //Close statement
        ConnectionBean.close(con); //Close connection


        }

		return iPKOrganization;
	}
	
	/**
	 * 	Method used for executing a static SQL statement and returning the results it produces.
	 * 	Results produced will be stored in ResultSet.
	 *	Sorting for company(type = 1) and org(type =2)
	 */
/*
	public Vector getRecord_Sorted(String sSQL, int type) 
	{
		try {
			
			//int SortType = getSortType();
			sSQL = sSQL + " ORDER BY ";
			Vector v = new Vector();
			
			if(type == 1)
			{
				if(SortType== 1)
					sSQL = sSQL + "CompanyName";
				else if(SortType == 2)
					sSQL = sSQL + "CompanyDesc";
				
				if(Toggle[SortType - 1] == 1)
					sSQL = sSQL + " DESC";
			}
			
			else
			{
				if(SortType_org == 1)
					sSQL = sSQL + "OrganizationName";
				else if(SortType_org == 2)
					sSQL = sSQL + "OrganizationCode";
				else if(SortType_org == 3)
					sSQL = sSQL + "NameSequence";
					
				if(Toggle_org[SortType_org - 1] == 1)
					sSQL = sSQL + " DESC";
			}	
					
			//db.openDB();
			//Statement stmt = db.con.createStatement();
			//ResultSet rs = stmt.executeQuery(sSQL);
			ResultSet rs=sqlMethod.rsQuery(sSQL);
            while(rs.next())
            {
            	if(type == 1) //Company
    			{
            		voCompany vo = new voCompany();
            		
    			}
            	else{
            		voOrganization vo = new voOrganization();
            		
            	}
            }
            
			
			return v;
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}
		return null;
	}
	*/

	
	
	/**
	 * GetRecord_Sorted
	 * @author James
	 * Date : 25- oct 07
	 */
	//@karen not completed
	public Vector getRecord_Sorted(String sSQL, int type) 
	{
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			//int SortType = getSortType();
			sSQL = sSQL + " ORDER BY ";
			
			if(type == 1)
			{
				if(SortType== 1)
					sSQL = sSQL + "CompanyName";
				else if(SortType == 2)
					sSQL = sSQL + "CompanyDesc";
				
				if(Toggle[SortType - 1] == 1)
					sSQL = sSQL + " DESC";
			}
			
			else
			{
				if(SortType_org == 1)
					sSQL = sSQL + "OrganizationName";
				else if(SortType_org == 2)
					sSQL = sSQL + "OrganizationCode";
				else if(SortType_org == 3)
					sSQL = sSQL + "NameSequence";
					
				if(Toggle_org[SortType_org - 1] == 1)
					sSQL = sSQL + " DESC";
			}	
		
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sSQL);

			   while(rs.next())
	            {
	            	votblConsultingCompany vo = new votblConsultingCompany();
	            	vo.setCompanyDesc(rs.getString("CompanyDesc"));
	            	vo.setCompanyName(rs.getString("CompanyName"));
	            	vo.setCompanyID(rs.getInt("CompanyID"));
	            	v.add(vo);
	            }
			   
			   

		} catch (SQLException SE) {
			System.err.println("Organization.java - getRecord_Sorted - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			
			return v;
		
		
	}
	/**
	 * Get Organization
	 * 
	 * @param iFKCompanyID
	 * @return
	 * @author James
	 */
	public votblOrganization getOrganization(int iOrgID) 
	{
		votblOrganization vo = new votblOrganization();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String command = "SELECT * FROM tblOrganization WHERE PKOrganization= "+ iOrgID;
		try {
			
			con=ConnectionBean.getConnection();
			System.out.println("con:" + con);
			st=con.createStatement();
			rs=st.executeQuery(command);
			
			   if(rs.next())
	            {
	            	vo.setEmailNom(rs.getString("EmailNom"));
	            	vo.setEmailNomRemind(rs.getString("EmailNomRemind"));
	            	vo.setEmailPart(rs.getString("EmailPart"));
	            	vo.setEmailPartRemind(rs.getString("EmailPartRemind"));
	            	vo.setExtraModule(rs.getInt("ExtraModule"));
	            	vo.setFKCompanyID(rs.getInt("FKCompanyID"));
	            	vo.setNameSequence(rs.getInt("NameSequence"));
	            	vo.setOrganizationCode(rs.getString("OrganizationCode"));
	            	vo.setOrganizationLogo(rs.getString("OrganizationLogo"));
	            	vo.setOrganizationName(rs.getString("OrganizationName"));
	            	vo.setPKOrganization(rs.getInt("PKOrganization"));
                        
                        // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
                        vo.setNomRater(rs.getBoolean("NominationModule"));
	            }
			   
			   

		} catch (SQLException SE) {
			System.err.println("Organization.java - getOrganization - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			
			return vo;
		
		
	}
	
	
	public Vector getAllOrganizations(int iFKCompanyID) 
	{
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		/*
		 * Change(s) : Changed the SQL statement to get the company name as well
		 * Reason(s) : Able to set the selected company name when company selection is changed in OrganizationList.jsp
		 * Updated By: Gwen Oh
		 * Updated On: 26 Sep 2011
		 */
		//String sSQL="SELECT * FROM tblOrganization WHERE FKCompanyID= "+ iFKCompanyID;
    	String sSQL = "SELECT tblOrganization.*, tblConsultingCompany.CompanyName FROM " +
    			"tblOrganization INNER JOIN tblConsultingCompany ON tblOrganization.FKCompanyID = tblConsultingCompany.CompanyID " +
    			"WHERE tblOrganization.FKCompanyID=" + iFKCompanyID;
		try {
			/*
			 * Re-edited by Eric Lu 15-May-08
			 * 
			 * Added sort and toggle functionality for getting organizations
			 */
			sSQL = sSQL + " ORDER BY ";
			
			if(SortType_org == 1)
				sSQL = sSQL + "OrganizationName";
			else if(SortType_org == 2)
				sSQL = sSQL + "OrganizationCode";
			else if(SortType_org == 3)
				sSQL = sSQL + "NameSequence";
			
			if(Toggle_org[SortType_org - 1] == 1)
				sSQL = sSQL + " DESC";	
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sSQL);

			   while(rs.next())
	            {
	            	votblOrganization vo = new votblOrganization();
	            	vo.setEmailNom(rs.getString("EmailNom"));
	            	vo.setEmailNomRemind(rs.getString("EmailNomRemind"));
	            	vo.setEmailPart(rs.getString("EmailPart"));
	            	vo.setEmailPartRemind(rs.getString("EmailPartRemind"));
	            	vo.setExtraModule(rs.getInt("ExtraModule"));
	            	vo.setFKCompanyID(rs.getInt("FKCompanyID"));
	            	vo.setNameSequence(rs.getInt("NameSequence"));
	            	vo.setOrganizationCode(rs.getString("OrganizationCode"));
	            	vo.setOrganizationLogo(rs.getString("OrganizationLogo"));
	            	vo.setOrganizationName(rs.getString("OrganizationName"));
	            	vo.setPKOrganization(rs.getInt("PKOrganization"));
	            	//Gwen Oh - 26/09/2011: Set the company name
	            	vo.setCompanyName(rs.getString("CompanyName"));
	            	
	            	v.add(vo);
	            }
			   
			   

		} catch (SQLException SE) {
			System.err.println("Organization.java - getAllOrganizations - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			
			return v;
		
		
	}
	
	
	/**
	 * Get Organisation ID by User email
	 *
	 */
	public int getOrgIDbyEmail(String UserEmail) throws SQLException, Exception
	{
		String query = "Select COUNT(*) as TotRecord from tblEmail";
		int count = 0;
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
            	count = rs.getInt(1);
            }
            
          
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
			
		return count;
	}
	
	/**
	 * Get Email Template by SurveyID
	 *
	 * @param SurveyID	int SurveyID
	 * @param int iOpt	Template Option: 0=Nom Email, 1=Nom Reminder, 2=Participant, 3=Participant Reminder, 4=SELF
	 *
	 * @return	EmailTemplate
	 */
	public String getEmailTemplate_SurvID(int SurveyID, int iOpt) throws SQLException, Exception
	{
		String sTemplate = "EmailNom";
		if(iOpt == 1)
			sTemplate = "EmailNomRemind";
		else if(iOpt == 2)
			sTemplate = "EmailPart";
		else if(iOpt == 3)
			sTemplate = "EmailPartRemind";
		else if(iOpt ==4 )
			sTemplate = "EmailIndividualReport";
		else if(iOpt == 5)
			sTemplate = "EmailSelf";
		
		String query = "SELECT " + sTemplate + " FROM tblOrganization INNER JOIN tblSurvey ON ";
		query = query + "tblOrganization.PKOrganization = tblSurvey.FKOrganization ";
		query = query + "WHERE (tblSurvey.SurveyID = " + SurveyID + ")";
		
		String sOrgTemplate = "";
		
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
            	sOrgTemplate = rs.getString(1);
            }
            
      
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - getEmailTemplate_SurvID - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        //Edit by Roger 27 June 2008
        //Check for null value
        if (sOrgTemplate == null) {
        	sOrgTemplate = "";
        }
			
		return sOrgTemplate;	
	}
	
	/**
	 * Get Email Subject by SurveyID
	 *
	 * @param SurveyID	int SurveyID
	 * @param int iOpt	Template Option: 0=Nom Email, 1=Nom Reminder, 2=Participant, 3=Participant Reminder, 4=SELF
	 * @author Johanes
	 * @return	EmailSubject
	 */
	public String getEmailSubject_SurvID(int SurveyID, int iOpt) throws SQLException, Exception
	{
		String sTemplate = "EmailNomSubject";
		if(iOpt == 1)
			sTemplate = "EmailNomRemindSubject";
		else if(iOpt == 2)
			sTemplate = "EmailPartSubject";
		else if(iOpt == 3)
			sTemplate = "EmailPartRemindSubject";
		else if(iOpt ==4 )
			sTemplate = "EmailIndividualReportSubject";
		else if(iOpt == 5)
			sTemplate = "EmailSelf";
		
		String query = "SELECT " + sTemplate + " FROM tblOrganization INNER JOIN tblSurvey ON ";
		query = query + "tblOrganization.PKOrganization = tblSurvey.FKOrganization ";
		query = query + "WHERE (tblSurvey.SurveyID = " + SurveyID + ")";
		
		String sOrgSubject = "";
		
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
            	sOrgSubject = rs.getString(1);
            }

        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - getEmailSubject_SurvID - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
         if (sOrgSubject == null) {
        	sOrgSubject = "";
        }
			
		return sOrgSubject;	
	}

	/**
	 * Get Company ID by OrganisationID
	 * @param OrgID
	 * @return PKCompany
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getCompanyID(int OrgID) throws SQLException, Exception
	{
		String query = "Select FKCompanyID from tblOrganization WHERE PKOrganization = " + OrgID;
		
		/*db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);*/
		int iCompanyID = 0;
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
            	iCompanyID = rs.getInt(1);
            }
          
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - getCompanyID - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

			
		return iCompanyID;	
	}
	
	/**
	 * Get Company ID by Organisation Code
	 * @param sOrgCode
	 * @return PKOrganisation
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getPKOrg(String sOrgCode) throws SQLException, Exception
	{
		String query = "SELECT PKOrganization FROM tblOrganization WHERE OrganizationCode = '" + sOrgCode + "'";
		
		/*db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iPKOrganization = 0;
		try
        {  
			con=ConnectionBean.getConnection();
			System.out.println(con +" connection null");
			st=con.createStatement();
			System.out.println(st + "st");
			rs=st.executeQuery(query);
         
            if(rs.next())
            {
            	iPKOrganization = rs.getInt(1);
            }
            
            
       
        }
        catch(Exception E) 
        {
            System.err.println("The ERROR IS HERE ->Organization.java - getPKOrg - " + E);
            E.printStackTrace();
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

			
		return iPKOrganization;	
	}
	
	/**
	 * Get organisation's name sequence
	 * @param iOrgID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @author Maruli
	 */
	public int getNameSeq(int iOrgID) throws SQLException, Exception
	{
		String query = "SELECT NameSequence FROM tblOrganization WHERE PKOrganization ="+iOrgID;
		int iNameSeqe = 0;

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
            	iNameSeqe = rs.getInt(1);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - getNameSeq - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

		return iNameSeqe;
	}
	
        /**
	 * Get organisation's nomination rater status
	 * @param iOrgID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @author Desmond
	 */
        public boolean getNomRater(int iOrgID) throws SQLException, Exception
	{
		String query = "SELECT NominationModule FROM tblOrganization WHERE PKOrganization ="+iOrgID;
		boolean iNomRater = true;

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
                        iNomRater = rs.getBoolean(1);
                    }

                }
                catch(Exception E) 
                {
                    System.err.println("Organization.java - getNomRater - " + E);
                }
                finally
                {
                        ConnectionBean.closeRset(rs); //Close ResultSet
                        ConnectionBean.closeStmt(st); //Close statement
                        ConnectionBean.close(con); //Close connection
                }

		return iNomRater;
	} // End getNomRater()
        
        
	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */	
	public void setToggle(int toggle) {
		Toggle[SortType - 1] = toggle;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int getToggle() {
		return Toggle [SortType - 1];
	}	
	
	/**
	 * Store Bean Variable Sort Type.
	 */
	public void setSortType(int SortType) {
		this.SortType = SortType;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getSortType() {
		return SortType;
	}
	
	/**
	 * Store Bean Variable Sort Type.
	 */
	public void setSortType_org(int SortType_org) {
		this.SortType_org = SortType_org;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getSortType_org() {
		return SortType_org;
	}
	
	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */	
	public void setToggle_org(int toggle) {
		Toggle_org[SortType_org - 1] = toggle;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int getToggle_org() {
		return Toggle_org[SortType_org - 1];
	}
	
	public String getOrganisationName(int iFKOrg)
    {
		String sOrgName = "";
		String querySql = "SELECT * FROM tblOrganization WHERE PKOrganization = "+iFKOrg;					
		
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
        
        	if(rs.next())
        		sOrgName = rs.getString("OrganizationName");
       
        }
        catch(Exception E) 
        {
            System.err.println("Organization.java - getOrganizationName - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
        return sOrgName;
    }
	
	public votblOrganization getAllOrganizations(int iFKCompanyID, int iOrganisationID) 
	{
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		votblOrganization vo = new votblOrganization();
    	
		String sql = "";
		if(iOrganisationID != 0)
		{
			sql = "SELECT * FROM tblConsultingCompany a, tblOrganization b";
			sql = sql + " WHERE a.CompanyID = b.FKCompanyID AND a.CompanyID = "+ iFKCompanyID+" AND b.PKOrganization ="+ iOrganisationID;	
		}
		else
		{
			sql = "SELECT * FROM tblConsultingCompany WHERE CompanyID = "+ iFKCompanyID;
		}
	
		
		try {
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);

			   if(rs.next())
	            {
	            	//27 May 2008 by Hemilda - cause iOrganisationID == 0 the query only retrieve tblConsultingCompany column
				   if(iOrganisationID != 0)
					{
		            	vo.setEmailNom(rs.getString("EmailNom"));
		            	vo.setEmailNomRemind(rs.getString("EmailNomRemind"));
		            	vo.setEmailPart(rs.getString("EmailPart"));
		            	vo.setEmailPartRemind(rs.getString("EmailPartRemind"));
		            	vo.setExtraModule(rs.getInt("ExtraModule"));
		            	vo.setFKCompanyID(rs.getInt("FKCompanyID"));
		            	vo.setNameSequence(rs.getInt("NameSequence"));
		            	vo.setOrganizationCode(rs.getString("OrganizationCode"));
		            	vo.setOrganizationLogo(rs.getString("OrganizationLogo"));
		            	vo.setOrganizationName(rs.getString("OrganizationName"));
		            	vo.setPKOrganization(rs.getInt("PKOrganization"));
					}
	            	vo.setCompanyName(rs.getString("CompanyName"));
	            }
			   
			   

		} catch (SQLException SE) {
			System.err.println("Organization.java - getAllOrganizations - "+SE.getMessage());
		}
		finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
			return vo;
	}
	
	/*
	 * Add a new record to the Organization table, 
	 * creates an admin account when SA creates a new consulting company
	 * @param OrganizationCode (based on Company Name)
	 * @param OrganizationName (based on Company Description)
	 * @param FKCompanyID
	 * @param NameSequence
	 * @param PKUser
     * @param nomRater
	 * @throws SQLException
	 * @throws Exception
	 * @author: Mark Oei
	 * @since v.1.3.12.63 09 Mar 2010
	 */
	public boolean addOrganisationByCons(String OrganizationCode, String OrganizationName, int FKCompanyID, int NameSequence, int PKUser, String nomRater) throws SQLException, Exception 
	{
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;

		String sql = "INSERT INTO tblOrganization (OrganizationCode, OrganizationName, FKCompanyID, NameSequence, NominationModule)";
		sql = sql +" VALUES ('"+ OrganizationCode+"', '"+OrganizationName+"', "+FKCompanyID+", "+NameSequence +", '"+ Boolean.parseBoolean(nomRater) +"')";
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			System.out.println(iSuccess);
			if(iSuccess!=0)
			bIsAdded=true;
		}
		catch(Exception E)
		{
            System.err.println("Organization.java - AddRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
        }
		
		System.out.println("1. Add Organization");
						
		// add default under the organization.		
		String defaultName = "NA";
		int FKOrganization = checkOrgExist(OrganizationCode, OrganizationName, FKCompanyID);
	    //Change to disable print statement. Used for debugging only
	    //Mark Oei 19 Mar 2010
		//System.out.println("testing " + FKOrganization);
		System.out.println("2. Check Organization Exist");
		if(FKOrganization != 0) {
			// Add Division
			div.addRecord(defaultName, FKOrganization, PKUser);
			System.out.println("3. Add Division");
			// Add Department
			dept.addRecord(defaultName, FKOrganization, PKUser);
			System.out.println("4. Add Department");
			// Add Group
			G.addRecord(defaultName, FKOrganization, PKUser);
			System.out.println("5. Add Group");
			// Check whether exists
			int FKDivision = div.checkDivExist(defaultName, FKOrganization);
			int FKDepartment = dept.checkDeptExist(defaultName, FKOrganization);
			int FKGroup = G.checkGroupExist(defaultName, FKOrganization);
			// Create links
			dept.linkDepartment(FKDivision, FKDepartment);
			G.linkGroup(FKDepartment, FKGroup);
			// Establish new admin account and password
			Date timeStamp = new java.util.Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			String temp  =  dFormat.format(timeStamp);
			String loginName = OrganizationCode + "admin";
			String password = OrganizationCode + temp;
			int userType = 6;
			// Insert record into database
			U.addRecord(FKDepartment, FKDivision, userType, "Admin", "Admin", 
							loginName, "NA", "NA", FKGroup, password, 1, FKCompanyID, FKOrganization, "NA", PKUser);
			
			System.out.println("6. Add User");
			int userExist = U.checkUserExist(FKDepartment, FKDivision, userType, "Admin", "Admin", loginName, "NA", "NA", FKGroup, password, 1, FKCompanyID, FKOrganization);
			
			System.out.println("FKDivision = " + FKDivision + ", FKDepartment = " + FKDepartment + ", FKGroup = " + FKGroup + " and User Exist = " + userExist);
							
			if(userExist != 0) {
				try {
					U.insertRelation(userExist, userExist, 0);
				}catch(SQLException SE) {System.out.println(SE.getMessage());}
				// Send email notification
				String content = template.ForgotPass_temp(loginName, password);
				String email = "3SixtyAdmin@pcc.com.sg";
				//Edited By Roger 13 June 2008
				Email.sendMail(server.getAdminEmail(), email, "New Admin Assignment for " + OrganizationName, content, FKOrganization);
			}
			
			System.out.println("8. Add User Relation");
		}
		
		sDetail = detail.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, OrganizationName, sDetail[2], sDetail[11], sDetail[10]);
		
	   return bIsAdded;
	}	// End Method for addOrganisationByCons
	
	
	/* Method Name : isConsulting
	 * Checks whether login organisation is a Consulting Company
	 * @param sOrgName
	 * @param orgCode
	 * @author Mark Oei
	 * @since v.1.3.12.63 (09 Mar 2010)
	*/
	public boolean isConsulting(String orgName){
		String sOrgName = "";
		orgName = "\'"+orgName+"\'";
		String querySql = "SELECT * FROM tblConsultingCompany WHERE CompanyName = "+orgName;					
	    //Change to disable print statement. Used for debugging only
	    //Mark Oei 19 Mar 2010
		//System.out.println("testing " + orgName);
       Connection con = null;
       Statement st = null;
       ResultSet rs = null;

       try
       {          
       	con=ConnectionBean.getConnection();
       	st=con.createStatement();
       	rs=st.executeQuery(querySql);
       
       	if(rs.next())
       		sOrgName = rs.getString("CompanyName");
       }
       catch(Exception E) 
       {
           System.err.println("Organization.java - isConsulting - " + E);
       }
       finally
       {

       	ConnectionBean.closeRset(rs); //Close ResultSet
       	ConnectionBean.closeStmt(st); //Close statement
       	ConnectionBean.close(con); //Close connection

       }
       //Change to disable print statement. Used for debugging only
       //Mark Oei 19 Mar 2010
       //System.out.println("testing " + sOrgName);
       if ((sOrgName == null)||(sOrgName == ""))
       	return false;
       else
       	return true;	
	}	// End of isConsulting 
	
}