package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voAge;
import CP_Classes.vo.voDepartment;
import CP_Classes.vo.voGender;
import CP_Classes.vo.voJobFunction;
import CP_Classes.vo.voJobLevel;
import CP_Classes.vo.voLocation;
import CP_Classes.vo.voUserDemographic;
import CP_Classes.vo.votblSurveyDemos;
import CP_Classes.vo.voEthnic;
public class DemographicEntry
{
	
	/**
	 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Competency.
	 */
	
	/**
	 * Creates a new intance of Demographic object.
	 */
	public DemographicEntry() {
		
	}

/********************** ALL SELECTED DEMOGRAPHIC TO BE SHOWN IN DEMOGRAPHIC LIST ***********************/

	/**
	 * Retrieves all selected demographics to be prompted to rater when fill in the questionnaire.
	 * The demographics are selected from create/edit survey.
	 */
	public Vector AllSelectedDemographic(int surveyID) {
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "select tblSurveyDemos.DemographicID, tblDemographicSelection.DemographicName,TableName ";
			query = query + "from tblSurveyDemos  INNER JOIN tblDemographicSelection ON ";
			query = query + "tblSurveyDemos.DemographicID = tblDemographicSelection.DemographicID ";
			query = query + "where tblSurveyDemos.SurveyID = " + surveyID;
			query = query + " order by tblSurveyDemos.DemographicID";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			*/

			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			 while(rs.next())
	            {
	            	votblSurveyDemos vo = new votblSurveyDemos();
	            	vo.setDemographicID(rs.getInt("DemographicID"));
	            	vo.setDemographicName(rs.getString("DemographicName"));
	            	vo.setTablename(rs.getString("TableName"));
	            	v.add(vo);
	            }
	            
			
		} catch (Exception SE) {
			System.err.println("DemographicEntry.java - AllSelectedDemographic- "+SE.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	/**
	 * Check the existance of the demographic under the particular survey and demographic id in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int SelectedDemographic(int surveyID, int demoID) {
		int exist = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "select DemographicID from tblSurveyDemos where SurveyID = " + surveyID;
			query = query + " and DemographicID = " + demoID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())
				exist = 1;
				*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}
	
	/**
	 * Check whether any demographic is selected in this survey
	 * @param surveyID
	 * @return True if exist. Otherwise False.
	 * @author Maruli
	 */
	public boolean bDemographicSelected(int surveyID) 
	{
		boolean bExist = false;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		String sql = "SELECT DemographicID FROM tblSurveyDemos WHERE SurveyID = " + surveyID;
		
		try 
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);


    		if(rs != null && rs.next())
    	    {
    	    	bExist = true;
    	  	}
        
        }
        catch(Exception E) 
        {
            System.err.println("DemographicEntry.java - bDemographicSelected - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }


		return bExist;
	}
	
	/**
	 * Check the existance of job function under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int JobFunctionSelected(int surveyID) {
		int exist = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Job Function'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())
				exist = 1;
				*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}
	
	/**
	 * Check the existance of job level under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int JobLevelSelected(int surveyID) {
		int exist = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Job Level'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}
	
	/**
	 * Check the existance of age under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int AgeSelected(int surveyID) {
		int exist = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Age Group'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			
			/*db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		} finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return exist;
	}
	
	/**
	 * Check the existance of gender under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int GenderSelected(int surveyID) {
		int exist = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Gender'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}
	
	/**
	 * Check the existance of ethnic group under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int EthnicSelected(int surveyID) {
		int exist = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Ethnic Group'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		return exist;
	}
	
	/**
	 * Check the existance of location under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int LocationSelected(int surveyID) {
		int exist = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Location'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		} finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}
	
	/**
	 * Check the existance of department under the particular survey in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int DepartmentSelected(int surveyID) {
		int exist = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		
		try {
			String query = "SELECT DISTINCT tblSurveyDemos.DemographicID FROM tblDemographicSelection INNER JOIN ";
			query = query + "tblSurveyDemos ON tblDemographicSelection.DemographicID = tblSurveyDemos.DemographicID ";
			query = query + "WHERE tblDemographicSelection.DemographicName = 'Department'";
			query = query + " and tblSurveyDemos.SurveyID = " + surveyID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				exist = 1;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		} finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return exist;
	}

/*******************************************************************************************************/

	/**
	 * Get the PKJobFunction based on Job Function Name.
	 */	
	public int getPKJobFunction(String JobFunction, int OrgID) 
	{
		int PKJobFunction = 0; //return 0 if failed to find PK
		
		if(JobFunction.equals(""))
			return getNA(1); //Return NA
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try 
		{
			
			String query = "Select PKJobFunction from JobFunction where JobFunctionName = '" + JobFunction + "' AND FKOrganization = " + OrgID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if (rs.next())
				PKJobFunction = rs.getInt("PKJobFunction");
			//db.closeDB();
		} 
		catch (SQLException SE) 
		{
			System.err.println(SE.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return PKJobFunction;
	}
	

	/**
	 * Get the PKJobLevel based on JobLevel Name.
	 */	
	public int getPKJobLevel(String JobLevel, int OrgID) 
	{
		//int PKJobLevel = 0; //return 0 if failed to find PK
		if(JobLevel.equals(""))
			return getNA(2); //Return NA
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int PKJobLevel = 0;
		try 
		{
			
			String query = "Select PKJobLevel from JobLevel where JobLevelName = '" + JobLevel + "' AND FKOrganization = " + OrgID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if (rs.next())
				PKJobLevel = rs.getInt("PKJobLevel");
			//db.closeDB();
		} 
		catch (SQLException SE) 
		{
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return PKJobLevel;
	}
	
	/**
	 * Get the PKGender based on Gender Name.
	 */	
	public int getPKGender(String Gender) 
	{
		int PKGender = 0; //return 0 if failed to find PK
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try 
		{
			String query = "Select PKGender from Gender where GenderDesc = '" + Gender + "'";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if (rs.next())
				PKGender = rs.getInt("PKGender");
			//db.closeDB();
		} 
		catch (SQLException SE) 
		{
			System.err.println(SE.getMessage());
		}finally{

			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		return PKGender;
	}
	
	/**
	 * Get the PKEthnic based on Ethnic Name.
	 */	
	public int getPKEthnic(String Ethnic, int OrgID) 
	{
		int PKEthnic = 0; //return 0 if failed to find PK
		
		if(Ethnic.equals(""))
			return getNA(5); //Return NA
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		
		try 
		{
			
			String query = "Select PKEthnic from Ethnic where EthnicDesc = '" + Ethnic + "' AND FKOrganization = " + OrgID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if (rs.next())
				PKEthnic = rs.getInt("PKEthnic");
			//db.closeDB();
		} 
		catch (SQLException SE) 
		{
			System.err.println(SE.getMessage());
		}finally{
			
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		
		}
		return PKEthnic;
	}
	
	/**
	 * Get the PKLocation based on Location Name.
	 */	
	public int getPKLocation(String Location, int OrgID) 
	{
		int PKLocation = 0; //return 0 if failed to find PK
		if(Location.equals(""))
			return getNA(6); //Return NA
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try 
		{
			
			String query = "Select PKLocation from Location where LocationName = '" + Location + "' AND FKOrganization = " + OrgID;
			/*db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if (rs.next())
				PKLocation = rs.getInt("PKLocation");
			//db.closeDB();
			
		} 
		catch (SQLException SE) 
		{
			System.err.println(SE.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return PKLocation;
	}
	

	/**
	 * Get the job function name based on job function id.
	 */	
	public String JobFunction(int JobFunction) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String jobFunct="";
		
		try {
			String query = "Select JobFunctionName from JobFunction where PKJobFunction = " + JobFunction;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next()){		

			jobFunct = rs.getString(1);
			}
			//db.closeDB();
			
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		} finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return jobFunct;
	}


	/**
	 * Get the job level description based on job level id.
	 */
	public String JobLevel(int JobLevel) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String jobLevel ="";
		try {
			String query = "Select JobLevelName from JobLevel where PKJobLevel = " + JobLevel;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


			if(rs.next()){
			jobLevel = rs.getString(1);
			}
			//db.closeDB();
			
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return jobLevel;
	}

	/**
	 * Get the age range based on age range id.
	 */
	public String AgeRange(int AgeRange) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String ageRange = "";
		try {
			String query = "Select AgeRangeTop from Age where PKAge = " + AgeRange;
			/*db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);


			if(rs.next()){

		    ageRange=rs.getString(1);
			}
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return ageRange;
	}

	/**
	 * Get the gender based on gender id.
	 */
	public String Gender(int Gender) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String gender ="";
		
		try {
			String query = "Select GenderDesc from Gender where PKGender = " + Gender;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next()){

			 gender = rs.getString(1);
			}
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return gender;
	}
	
	/**
	 * Get the ethnic group based on ethnic group id.
	 */
	public String Ethnic(int Ethnic) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String ethnic="";
		try {
			String query = "Select EthnicDesc from Ethnic where PKEthnic = " + Ethnic;
			/*db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				ethnic = rs.getString(1);

			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return ethnic;
	}

	/**
	 * Get the location based on location id.
	 */
	public String Location(int Location) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String location="";

		try {
			String query = "Select LocationName from Location where PKLocation = " + Location;
			/*
			 db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				location = rs.getString(1);

			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return location;
	}

	/**
	 * Get the department based on department id.
	 */
	public String Department(int Department) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String department="";
		try {
			String query = "Select DepartmentName from Department where PKDepartment = " + Department;
			/*db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			
			if(rs.next())
				department = rs.getString(1);

			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return department;
	}

	/**
	 * Get all job functions under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllJobFunction(int FKOrg) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v=new Vector();
		
		try {
			String query = "Select * from JobFunction where JobFunctionName <> 'NA' and FKOrganization = " + FKOrg;
			query = query + " order by JobFunctionName";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				voJobFunction vo=new voJobFunction();
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setJobFunctionName(rs.getString("JobFunctionName"));
				vo.setPKJobFunction(rs.getInt("PKJobFunction"));
				
				v.add(vo);
			}
	
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}

	/**
	 * Get all job levels under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllJobLevel(int FKOrg) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			String query = "Select * from JobLevel where JobLevelName <> '-1' and FKOrganization = " + FKOrg;
			query = query + " order by JobLevelName";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				voJobLevel vo=new voJobLevel();
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setJobLevelName(rs.getString("JobLevelName"));
				vo.setPKJobLevel(rs.getInt("PKJobLevel"));
				
				v.add(vo);
			}
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
		
	/**
	 * Get all age range under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllAgeRange(int FKOrg) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v=new Vector();
		
		try {
			String query = "Select * from Age where AgeRangeTop <> -1 and FKOrganization = " + FKOrg;
			query = query + " order by AgeRangeTop";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()){
				
				voAge vo=new voAge();
				vo.setAgeRangeTop(rs.getInt("AgeRangeTop"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setPKAge(rs.getInt("PKAge"));
				
		
				v.add(vo);
				
			}
		
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}

	/**
	 * Get all gender under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	
	public Vector getAllGender(int FKOrg) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v=new Vector();
		try {
			String query = "Select * from Gender where GenderDesc <> 'NA'";
			query = query + " order by GenderDesc";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()){
				voGender vo=new voGender();
				vo.setGenderDesc(rs.getString("GenderDesc"));
				vo.setPKGender(rs.getInt("PKGender"));
				
				v.add(vo);
				
			}
	
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}

	/**
	 * Get all ethnic groups under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllEthnic(int FKOrg) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v=new Vector();
		
		try {
			String query = "Select * from Ethnic where EthnicDesc <> 'NA' and FKOrganization = " + FKOrg;
			query = query + " order by EthnicDesc";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()){
				voEthnic vo=new voEthnic();
				vo.setEthnicDesc(rs.getString("EthnicDesc"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setPKEthnic(rs.getInt("PKEthnic"));
				
				v.add(vo);
				}
			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}

	/**
	 * Get all locations under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllLocation(int FKOrg) {
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "Select * from Location where LocationName <> 'NA' and FKOrganization = " + FKOrg;
			query = query + " order by LocationName";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
            
            while(rs.next())
            {
            	voLocation vo = new voLocation();
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	vo.setLocationName(rs.getString("LocationName"));
            	vo.setPKLocation(rs.getInt("PKLocation"));
            	
            	v.add(vo);
            }
			return v;
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}

	/**
	 * Get all departments under the particular organization.
	 * This is to be listed out in combo box at raters data entry.
	 */
	public Vector getAllDepartment(int FKOrg) {
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try {
			String query = "Select * from Department where FKOrganization = " + FKOrg;
			query = query + " order by DepartmentName";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
            {
            	voDepartment vo=new voDepartment();
            	
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	vo.setDepartmentCode(rs.getString("DepartmentCode"));
            	vo.setDepartmentName(rs.getString("DepartmentName"));
            	vo.setFKDivision(rs.getInt("FKDivision"));
            	vo.setLocation(rs.getString("Location"));
            	vo.setPKDepartment(rs.getInt("PKDepartment"));
            	
            	v.add(vo);
            }

			return v;
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	
	/**
	 * Get the primary key that is assigned as 'NA'.
	 * We have to include 'NA' because we need to link to all the demographics tables.
	 */
	public int getNA(int type) {
		String tblName = "";
		String columnName = "";
		int id = 0;
		
		switch(type) {
			case 1 : tblName = "JobFunction";
					 columnName = "JobFunctionName";	
					 break;
			case 2 : tblName = "JobLevel";
					 columnName = "JobLevelName";	
					 break;
			case 3 : tblName = "Age";
					 columnName = "AgeRangeTop";	
					 break;
			case 4 : tblName = "Gender";
					 columnName = "GenderDesc";	
					 break;
			case 5 : tblName = "Ethnic";
					 columnName = "EthnicDesc";	
					 break;
			case 6 : tblName = "Location";
					 columnName = "LocationName";	
					 break;
		}
		
		
		String query = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try {
			if(type == 2 || type == 3)
				query = "Select * from " + tblName + " where " + columnName  + " = -1";
			else
				query = "Select * from " + tblName + " where " + columnName  + " = 'NA'";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				id = rs.getInt(1);
			
			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return id;
	}
	
	/**
	 * Update the demographics record.
	 * This is done in Rater's data entry.
	 */
	public boolean UpdateRecord(int jobFunct, int age, int gender, int ethnic, int location, int pkUserDemographic) {
		Connection con = null;
		Statement st = null;
	
		boolean bIsUpdated=false;
		try {
			String sql = "UPDATE [UserDemographic] ";
			sql = sql + "SET FKAge = " + age + ", FKEthnic = " + ethnic + ", FKGender = " + gender;
			sql = sql + ", FKJobFunction = " + jobFunct + ", FKLocation = " + location;
			sql = sql + " WHERE PKUserDemographic = " + pkUserDemographic;
			/*
			db.openDB();
			PreparedStatement ps = db.con.prepareStatement(sql);
			ps.executeUpdate();
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;


			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsUpdated;
	}
	
	/**
	 * Update the demographics record based on PKUser.
	 */
	public boolean UpdateRecord(int pkUser, int age, int ethnic, int gender, int jobFunct, int jobLevel, int location) {
		if(age == 0)
			age = getNA(3);
		if(ethnic == 0)
			ethnic = getNA(5);
		if(gender == 0)
			gender = getNA(4);
		if(jobFunct == 0)
			jobFunct = getNA(1);
		if(jobLevel == 0)
			jobLevel = getNA(2);
		if(location == 0)
			location = getNA(6);
		
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		try {
			String sql = "UPDATE [UserDemographic] ";
			sql = sql + "SET FKAge = " + age + ", FKEthnic = " + ethnic + ", FKGender = " + gender;
			sql = sql + ", FKJobFunction = " + jobFunct + ", FKJobLevel = " + jobLevel + ", FKLocation = " + location;
			sql = sql + " WHERE FKUser = " + pkUser;
			/*
			db.openDB();
			PreparedStatement ps = db.con.prepareStatement(sql);
			ps.executeUpdate();
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;

		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
	
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return bIsUpdated;
	}
	
	/**
	 * Insert new demographics record to database.
	 * This is use only when the rater never used the system before.
	 */
	public boolean InsertRecord(int pkUser, int age, int ethnic, int gender, int jobFunct, int jobLevel, int location) throws SQLException, Exception  {
				
		if(age == 0)
			age = getNA(3);
		if(ethnic == 0)
			ethnic = getNA(5);
		if(gender == 0)
			gender = getNA(4);
		if(jobFunct == 0)
			jobFunct = getNA(1);
		if(jobLevel == 0)
			jobLevel = getNA(2);
		if(location == 0)
			location = getNA(6);
					
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		try{
			String sql = "Insert into [UserDemographic] values(";
			sql = sql + pkUser + ", " + age + ", " + ethnic + ", " + gender;
			sql = sql + ", " + jobFunct + ", " + jobLevel + ", " + location + ")";
			/*
			db.openDB();
			PreparedStatement ps = db.con.prepareStatement(sql);
			ps.executeUpdate();
			db.closeDB();
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
	
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		System.out.println("insert");
			return bIsUpdated;
	}
	
	/**
	 * Delete the demographics record.
	 */
	public boolean deleteRecord(int pkDemo) throws SQLException, Exception {
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted=false;
		try{
		String sql = "Delete from [UserDemographic] where PKUserDemographic = " + pkDemo;
		/*	
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		int iSuccess = st.executeUpdate(sql);
		if(iSuccess!=0)
		bIsDeleted=true;


		}catch(SQLException e){
			System.err.println("DemographicEntry.java - deleteRecord -"+e.getMessage());
		}finally{
	
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		System.out.println("insert");
			return bIsDeleted;
	}
	
	/**
	 * get PKUserDemographic based on PKUser.
	 */
	public int getPKUserDemographic(int PKUser) throws SQLException, Exception 
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		int PKUserDemographic = 0;
		try{
		String query = "SELECT PKUserDemographic from [UserDemographic] where FKUser = " + PKUser;
		/*	
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);

		//db.closeDB();

		if(rs.next())
			PKUserDemographic = rs.getInt("PKUserDemographic");
		}catch(SQLException e){
			System.err.println("DemographicEntry.java - getPKUserDemographic -"+e.getMessage());
		}finally{
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		
		return PKUserDemographic;
	}
	
	
	/**
	 * get UserDemographic Details based on PKUser.
	 */
	public Vector getUserDemographicDetail(int PKUser) throws SQLException, Exception 
	{

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v=new Vector();
		try{
			
		
		String query = "SELECT * FROM UserDemographic INNER JOIN ";
      	query = query + "JobFunction ON UserDemographic.FKJobFunction = JobFunction.PKJobFunction INNER JOIN ";
        query = query + "Location ON UserDemographic.FKLocation = Location.PKLocation INNER JOIN ";
        query = query + "JobLevel ON UserDemographic.FKJobLevel = JobLevel.PKJobLevel INNER JOIN ";
        query = query + "Gender ON UserDemographic.FKGender = Gender.PKGender INNER JOIN ";
        query = query + "Ethnic ON UserDemographic.FKEthnic = Ethnic.PKEthnic INNER JOIN ";
        query = query + "Age ON UserDemographic.FKAge = Age.PKAge WHERE (UserDemographic.FKUser = " + PKUser + ")";
		/*	
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		*/
        con=ConnectionBean.getConnection();
        st=con.createStatement();
        rs=st.executeQuery(query);
        
        while(rs.next()){
        	voUserDemographic vo=new voUserDemographic();
        	vo.setFKAge(rs.getInt("FKAge"));
        	vo.setFKEthnic(rs.getInt("FKEthnic"));
        	vo.setFKGender(rs.getInt("FKGender"));
        	vo.setFKJobFunction(rs.getInt("FKJobFunction"));
        	vo.setFKJobLevel(rs.getInt("FKJobLevel"));
        	vo.setFKLocation(rs.getInt("FKLocation"));
        	vo.setFKUser(rs.getInt("FKUser"));
        	vo.setPKUserDemographic(rs.getInt("PKUserDemographic"));
        	vo.setLocation(rs.getString("LocationName"));
        	vo.setEthnic(rs.getString("EthnicDesc"));
        	//27/05/2008 by Hemilda - No Gender column in UserDemographic table, show error in resin
        	vo.setGender(rs.getString("GenderDesc"));
//        	27/05/2008 by Hemilda - No JobFunction column in UserDemographic table, show error in resin
        	vo.setJobFunction(rs.getString("JobFunctionName"));
//        	27/05/2008 by Hemilda - No JobLevel column in UserDemographic table, show error in resin
        	vo.setJobLevel(rs.getString("JobLevelName"));
        	v.add(vo);
        }

		}catch(SQLException SE){
			System.err.println("DemographicEntry.java - getUserDemographicDetail-"+SE.getMessage());
			
		}
		finally{
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		return v;
	}
	


}