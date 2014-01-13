package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voDepartment;
import CP_Classes.vo.voDivision;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.voUser;
import CP_Classes.vo.voUserDemographic;
import CP_Classes.vo.votblAssignment;
import CP_Classes.vo.votblSurvey;
/**
 * This class implements all the operations for Rater's data entry, which is to be used in Survey, and Report..
 */
public class RatersDataEntry
{
	/**
	 * Declaration of new object of class Database.
	 */
	//private Database db;
	
	/**
	 * Bean Variable to store survey id.
	 */
	public int SurveyID;
	
	/**
	 * Bean Variable to store div id.
	 */
	public int DivID;
	
	/**
	 * Bean Variable to store dept id.
	 */
	public int DeptID;
	
	/**
	 * Bean Variable to store group id.
	 */
	public int GroupID;
	
	/**
	 * Bean Variable to store target id.
	 */
	public int TargetID;
	
	/**
	 * Bean Variable to store rater id.
	 */
	public int RaterID;
	
	/**
	 * Bean Variable to store the status of the page.
	 */
	public int PageLoad;
	
	/**
	 * Bean Variable to store job position.
	 */
	public String JobPost;
	

	/**
	 * Creates a new intance of RatersDataEntry object.
	 */
	public RatersDataEntry() {
		//db = new Database();
		SurveyID = 0;
		GroupID = 0;
		TargetID = 0;
		RaterID = 0;
		PageLoad = 0;
	}

	/**
	 * Store survey id to Bean Variable.
	 */
	public void setSurveyID(int SurveyID) {
		this.SurveyID = SurveyID;
	}
	
	/**
	 * Get bean variable surveyID.
	 */
	public int getSurveyID() {
		return SurveyID;
	}
	
	/**
	 * Store div id to Bean Variable.
	 */
	public void setDivID(int DivID) {
		this.DivID = DivID;
	}

	/**
	 * Get bean variable divID.
	 */
	public int getDivID() {
		return DivID;
	}
	
	/**
	 * Store dept id to Bean Variable.
	 */
	public void setDeptID(int DeptID) {
		this.DeptID = DeptID;
	}

	/**
	 * Get bean variable deptID.
	 */
	public int getDeptID() {
		return DeptID;
	}
	
	/**
	 * Store group id to Bean Variable.
	 */
	public void setGroupID(int GroupID) {
		this.GroupID = GroupID;
	}

	/**
	 * Get bean variable groupID.
	 */
	public int getGroupID() {
		return GroupID;
	}

	/**
	 * Store target id to Bean Variable.
	 */
	public void setTargetID(int TargetID) {
		this.TargetID = TargetID;
	}

	/**
	 * Get bean variable targetID.
	 */
	public int getTargetID() {
		return TargetID;
	}

	/**
	 * Store job position to Bean Variable.
	 */
	public void setJobPost(String JobPost) {
			this.JobPost = JobPost;
	}

	/**
	 * Get bean variable job position.
	 */
	public String getJobPost() {
			return JobPost;
	}

	/**
	 * Store rater id Bean Variable.
	 */
	public void setRaterID(int RaterID) {
		this.RaterID = RaterID;
	}

	/**
	 * Get bean variable raterID.
	 */
	public int getRaterID() {
		return RaterID;
	}
	
	/**
	 * Store page load status to Bean Variable.
	 */
	public void setPageLoad(int PageLoad) {
		this.PageLoad = PageLoad;
	}

	/**
	 * Get bean variable page load status.
	 */
	public int getPageLoad() {
		return PageLoad;
	}

	/**
	 * Retrieves all survey details based on company and organization id.
	 */
	public Vector getSurvey(int compID, int orgID) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		String query = " ";
		//Changed query made by Ha 03/06/08 to list all Survey if the option for Organsation is ALL
		try {
			query = "SELECT DISTINCT tblSurvey.* FROM tblSurvey,tblAssignment, tblOrganization ";
			query = query + "WHERE tblSurvey.SurveyID = tblAssignment.SurveyID ";
			query = query + "AND tblSurvey.FKOrganization = tblOrganization.PKorganization";
			if (orgID !=0)
				query += " AND tblSurvey.FKOrganization = "+orgID ;
			else
				query += " AND tblSurvey.FKCompanyID = " +compID;
			query = query + " AND tblSurvey.SurveyStatus = 1 ";
			query = query + " AND ";
			query = query + "tblAssignment.RaterStatus = 0 AND tblAssignment.RaterLoginID <> 0 ";
			query = query + "ORDER BY tblSurvey.SurveyName";

			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()){
				votblSurvey vo = new votblSurvey();
				vo.setAdminAssigned(rs.getInt("AdminAssigned"));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setComment_Included(rs.getInt("Comment_Included"));
				vo.setDateOpened(rs.getString("DateOpened"));
				vo.setDeadlineSubmission(rs.getString("DeadlineSubmission"));
				vo.setEntryDate(rs.getString("EntryDate"));
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setInclude_Exclude(rs.getInt("Include_Exclude"));
				vo.setJobFutureID(rs.getInt("JobFutureID"));
				vo.setJobPositionID(rs.getInt("JobPositionID"));
				vo.setLevelOfSurvey(rs.getInt("LevelOfSurvey"));
				vo.setMAX_Gap(rs.getFloat("MAX_Gap"));
				vo.setMIN_Gap(rs.getFloat("MIN_Gap"));
				vo.setMonthYear(rs.getString("MonthYear"));
				vo.setNA_Included(rs.getInt("NA_Included"));
				vo.setNominationEndDate(rs.getString("NominationEndDate"));
				vo.setNominationStartDate(rs.getString("NominationStartDate"));
				vo.setPurposeID(rs.getInt("PurposeID"));
				vo.setReliabilityCheck(rs.getInt("ReliabilityCheck"));
				vo.setSelf_Comment_Included(rs.getInt("Self_Comment_Included"));
				vo.setSurveyID(rs.getInt("SurveyID"));
				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setSurveyType(rs.getInt("SurveyType"));
				vo.setTimeFrameID(rs.getInt("TimeFrameID"));
				
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
	 * Retrieve all divisions under surveyID
	 * @param surveyID
	 * @return
	 * @author Su See
	 */
	public Vector getDivision(int surveyID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			
			String query = "SELECT DISTINCT Division.PKDivision, Division.DivisionName FROM Division INNER JOIN ";
           	query += "tblAssignment ON Division.PKDivision = tblAssignment.FKTargetDivision WHERE ";
           	query += "(tblAssignment.SurveyID = " + surveyID + ") ORDER BY Division.DivisionName";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
           	con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	while(rs.next()){
           		voDivision vo=new voDivision();
           		vo.setPKDivision(rs.getInt("PKDivision"));
           		vo.setDivisionName(rs.getString("DivisionName"));
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
	 * Retrieve all depts based on survey and division
	 * @param surveyID
	 * @param divID
	 * @return
	 * @author Su See
	 */
	public Vector getDepartment(int surveyID, int divID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
		
			String query = "SELECT DISTINCT Department.PKDepartment, Department.DepartmentName, Department.Location ";
			query = query + "FROM tblAssignment INNER JOIN Department ON ";
			query = query + "tblAssignment.FKTargetDepartment = Department.PKDepartment ";
			query = query + "WHERE (tblAssignment.SurveyID = " + surveyID + ") ";
			
			if(divID > 0)
			{
				query = query + "AND Department.FKDivision = " + divID + " ";
			}
			
			query = query + "ORDER BY Department.DepartmentName ";
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				voDepartment vo=new voDepartment();
				vo.setPKDepartment(rs.getInt("PKDepartment"));
				vo.setDepartmentName(rs.getString("DepartmentName"));
				vo.setLocation(rs.getString("Location"));
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
	 * Retrieve groups based on survey and department
	 * @param surveyID
	 * @param deptID
	 * @return
	 * @author Su See
	 */
	public Vector getGroup(int surveyID, int deptID) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			
			String query = "SELECT DISTINCT [Group].PKGroup, [Group].GroupName FROM tblAssignment INNER JOIN ";
          	query += "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
           	query += "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE ";
           	query += "(tblAssignment.SurveyID = " + surveyID + ") ";
           	
           	if(deptID > 0)
           	{
           		query += "AND [Group].FKDepartment = " + deptID + " ";
           	}
           	
           	query += "ORDER BY [Group].GroupName ";
           	/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
			*/
           	con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voGroup vo=new voGroup();
           		vo.setPKGroup(rs.getInt("PKGroup"));
           		vo.setGroupName(rs.getString("GroupName"));
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
	 * Retrieves all group sections under the particular survey.
	 */
	public Vector getGroup(int surveyID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			String query = "SELECT DISTINCT [Group].PKGroup, [Group].GroupName FROM tblAssignment INNER JOIN ";
          	query += "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
         	query += "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup ";
			query += "WHERE (tblAssignment.SurveyID = " + surveyID + ") AND (tblAssignment.RaterStatus = 0) ";
			query += "ORDER BY [Group].GroupName";
			
			/*
			String query = "SELECT DISTINCT [User].Group_Section, [Group].GroupName ";
			query = query + "FROM tblAssignment INNER JOIN tblSurvey ON ";
			query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
			query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query = query + "[Group] ON [User].Group_Section = [Group].PKGroup ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " and tblAssignment.RaterStatus = 0";
			query = query + " ORDER BY [Group].GroupName";
			*/
		  	con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voGroup vo=new voGroup();
           		vo.setPKGroup(rs.getInt("PKGroup"));
           		vo.setGroupName(rs.getString("GroupName"));
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
	 * Retrieves all targets under the particular survey and group.
	 */
	public Vector getTarget(int surveyID, int group) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			String query = "SELECT DISTINCT tblAssignment.TargetLoginID, [User].GivenName, [User].FamilyName ";
			query += "FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
           	query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query += "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup ";
			query += "WHERE (tblAssignment.RaterStatus = 0) AND (tblSurvey.SurveyStatus = 1) AND ";
			query += "(tblSurvey.SurveyID = " + surveyID + ") AND (tblAssignment.FKTargetGroup = " + group + ") ";
			query += "ORDER BY tblAssignment.TargetLoginID ";

			/*
			String query = "SELECT DISTINCT tblAssignment.TargetLoginID, ";
			query = query + "[User].GivenName, [User].FamilyName ";
			query = query + "FROM tblAssignment INNER JOIN ";
			query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
			query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query = query + "[Group] ON [User].Group_Section = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
			query = query + "AND tblSurvey.SurveyID = " + surveyID + " AND [User].Group_Section = " + group;
			query = query + " AND tblAssignment.RaterStatus = 0";
			query = query + " ORDER BY tblAssignment.TargetLoginID";
			*/
			
			con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voUser vo=new voUser();
           		vo.setGivenName(rs.getString("GivenName"));
           		vo.setFamilyName(rs.getString("FamilyName"));
           		vo.setTargetLoginID(rs.getInt("TargetLoginID"));
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
	 * 	Retrieves all targets under the particular survey and group with ORDER BY Name/FamilyName
	 *	@param int surveyID
	 *	@param int group
	 *	@param int iNameSeq		0 = FamilyName, 1 = GivenName
	 */
	public Vector getTarget(int surveyID, int group, int iNameSeq) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			String query = "";
			
			if (iNameSeq == 0)
			{
				query = "SELECT DISTINCT tblAssignment.TargetLoginID, ";
				query = query + "[User].GivenName, [User].FamilyName ";
				query = query + "FROM tblAssignment INNER JOIN ";
				query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
				query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
				query = query + "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
				query = query + "AND tblSurvey.SurveyID = " + surveyID + " AND tblAssignment.FKTargetGroup = " + group;
				query = query + " AND tblAssignment.RaterStatus = 0";
				query = query + " ORDER BY [User].FamilyName";
			}
			else
			{
				query = "SELECT DISTINCT tblAssignment.TargetLoginID, ";
				query = query + "[User].GivenName, [User].FamilyName ";
				query = query + "FROM tblAssignment INNER JOIN ";
				query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
				query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
				query = query + "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
				query = query + "AND tblSurvey.SurveyID = " + surveyID + " AND tblAssignment.FKTargetGroup = " + group;
				query = query + " AND tblAssignment.RaterStatus = 0";
				query = query + " ORDER BY [User].GivenName";
			}
			
			con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voUser vo=new voUser();
           		vo.setGivenName(rs.getString("GivenName"));
           		vo.setFamilyName(rs.getString("FamilyName"));
           		vo.setTargetLoginID(rs.getInt("TargetLoginID"));
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
	 * Retrieves all raters under the particular survey, group, and target.
	 */
	public Vector getRater(int surveyID, int group, int targetLoginID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		try {
			String query = "SELECT DISTINCT tblAssignment.RaterLoginID, ";
			query = query + "[User].GivenName, [User].FamilyName FROM tblAssignment INNER JOIN ";
			query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
			query = query + "[User] ON tblAssignment.RaterLoginID = [User].PKUser INNER JOIN ";
			query = query + "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
			query = query + "AND tblSurvey.SurveyID = " + surveyID;			
			query = query + " AND tblAssignment.TargetLoginID = " + targetLoginID;
			query = query + " and tblAssignment.RaterStatus = 0";
			query = query + " ORDER BY tblAssignment.RaterLoginID";
			
			con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voUser vo=new voUser();
           		vo.setGivenName(rs.getString("GivenName"));
           		vo.setFamilyName(rs.getString("FamilyName"));
           		vo.setRaterLoginID(rs.getInt("RaterLoginID"));
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
	 * Retrieves all raters under the particular survey, group, and target with ORDER BY Name/FamilyName
	 *	@param int surveyID
	 *	@param int group
	 *	@param int targetLoginID
	 *	@param int iNameSeq		0 = FamilyName, 1 = GivenName
	 */
	public Vector getRater(int surveyID, int group, int targetLoginID, int iNameSeq) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector v=new Vector();
		
		try {
			String query = "";
			
			if(iNameSeq == 0)
			{
				query = "SELECT DISTINCT tblAssignment.RaterLoginID, ";
				query = query + "[User].GivenName, [User].FamilyName FROM tblAssignment INNER JOIN ";
				query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
				query = query + "[User] ON tblAssignment.RaterLoginID = [User].PKUser INNER JOIN ";
				query = query + "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
				query = query + "AND tblSurvey.SurveyID = " + surveyID;			
				query = query + " AND tblAssignment.TargetLoginID = " + targetLoginID;
				query = query + " and tblAssignment.RaterStatus = 0";
				query = query + " ORDER BY [User].FamilyName";	
			}
			else
			{	
				query = "SELECT DISTINCT tblAssignment.RaterLoginID, ";
				query = query + "[User].GivenName, [User].FamilyName FROM tblAssignment INNER JOIN ";
				query = query + "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
				query = query + "[User] ON tblAssignment.RaterLoginID = [User].PKUser INNER JOIN ";
				query = query + "[Group] ON tblAssignment.FKTargetGroup = [Group].PKGroup WHERE tblSurvey.SurveyStatus = 1 ";
				query = query + "AND tblSurvey.SurveyID = " + surveyID;			
				query = query + " AND tblAssignment.TargetLoginID = " + targetLoginID;
				query = query + " and tblAssignment.RaterStatus = 0";
				query = query + " ORDER BY [User].GivenName";	
			}
			
			
			con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	
           	while(rs.next()){
           		voUser vo=new voUser();
           		vo.setGivenName(rs.getString("GivenName"));
           		vo.setFamilyName(rs.getString("FamilyName"));
           		vo.setRaterLoginID(rs.getInt("RaterLoginID"));
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
	 * Retrieves a particular rater's information.
	 */
	public voUserDemographic getRaterInfo(int RaterLoginID) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		voUserDemographic vo=new voUserDemographic();
		
		try {
			String query = "Select * from [UserDemographic] where FKUser = " + RaterLoginID;
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
			*/
			con=ConnectionBean.getConnection();
           	st=con.createStatement();
           	rs=st.executeQuery(query);
           	if(rs.next()){
           		
           		vo.setFKAge(rs.getInt("FKAge"));
           		vo.setFKEthnic(rs.getInt("FKEthnic"));
           		vo.setFKGender(rs.getInt("FKGender"));
           		vo.setFKJobFunction(rs.getInt("FKJobFunction"));
           		vo.setFKJobLevel(rs.getInt("FKJobLevel"));
           		vo.setFKLocation(rs.getInt("FKLocation"));
           		vo.setFKUser(rs.getInt("FKUser"));
           		vo.setPKUserDemographic(rs.getInt("PKUserDemographic"));
           	   
           	
           		}
			
			
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		
		}
		return vo;
	}

	
	/**
	 * Get the job position for a particular survey.
	 */
	public String getJobPosition(int surveyID) {
		String sJobPos = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String query = "SELECT tblJobPosition.JobPosition FROM tblJobPosition INNER JOIN "+
        			   "tblSurvey ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID "+
        			   "WHERE (tblSurvey.SurveyID = "+ surveyID +")";
		
		try 
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

    		if(rs != null && rs.next())
    	    {
    	    	sJobPos = rs.getString("JobPosition");
    	  	}
            
      		
        }
        catch(Exception E) 
        {
            
            System.err.println("RatersDataEntry.java - getJobPosition - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
		return sJobPos;
	}

	/**
	 * Reset all the bean variables to default.
	 */
	public void refresh() {
		setGroupID(0);
		setTargetID(0);
		setRaterID(0);
	}
	
	
}