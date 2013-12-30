package CP_Classes;

import java.sql.*;
import java.util.Vector;

import util.Utils;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCluster;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voDepartment;
import CP_Classes.vo.voDivision;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.voJob;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblOrganization;
import CP_Classes.vo.votblSurvey;
import CP_Classes.vo.votblSurveyBehaviour;
import CP_Classes.vo.votblSurveyCompetency;
import CP_Classes.vo.votblSurveyCluster;

/**
 * This class implements all the operations for Competency table in the
 * database.
 * 
 * Change Log
 * ==========
 *
 * Date        By                Method(s)            			Change(s) 
 * =====================================================================================================
 * 17/10/11    Gwen Oh          clearSurveyData()			Changed SQL statement to make sure that user to be deleted is
 * 															not a rater or target of another survey
 * 
 * 01/06/12	   Albert			getUserDetailWithRound()	get details of user including the round field
 * 
 * 15/06/12	   Albert			Constructor					add a new data member useCluster including its setter and getter method
 * 								changeUseClusterOption()	create method to change the useCluster option of a particular survey
 * 								getUseClusterOption()		to get the useCluster option of a particular survey
 * 								copySurvey()				modify so that it copies the useCluster option also
 * 
 * 18/06/12	   Albert			addCluster(), delCluster()	add new methods to add and delete clusters for each survey
 * 
 * 19/06/12	   Albert			filterRecordCluster()		add new method to list all the cluster available to choose
 * 
 * 20/06/12	   Albert			delCompetency()				add override method to delete competency that is included in a cluster
 * 
 * 21/06/12	   Albert			del_Survey_Competency()		add method to delete competency of a particular survey
 *  							changeUseClusterOption()	modify the method so that it update survey status to not commissioned if cluster option is changed
 *  							addCompetency()				add override method to add competency with cluster
 *  							filterRecord()				add override method to retrieve competencies based on cluster id also
 *  							addKeyBehaviour()			add override method to add KB with cluster
 *  
 * 22/06/12	   Albert			copySurvey()				modify method so it copies cluster, comptencies, and KBs

 *
 * 12/07/2012  Liu Taichen      getSurveyDetail(int)        let the method to also retrieve the field HideCompDesc
 *
 * 12/07/2012  Liu Taichen      getSurveyDetail(int, int)   let the method to also retrieve the field HideCompDesc
 *
 * 12/07/2012  Liu Taichen      updateRecord(String, int, String,  to also change the HideCompDesc Field
 * 								String, int, String, int, String, 
 * 								int, int, int, int, int, String, 
 * 								String, int, int, int, int)

 * 
 * 09/07/12	   Albert			getBreakCPR()				to retrieve the breakCPR option of a particular survey
 * 								addRecord()					modify method so it add record also insert the breakCPR to the table
 * 								copySurvey()				modify so that it also copies the breakCPR option
 * 								changeBreakCPR()			added to update survey status if breakCPR is changed and update breakCPR to database
 * 								updateRecord()				modify so it also updates breakCPR 
 * 
 * 12/07/12	   Albert			filterRecord()				modify filtering so it shows user generated and system generated

 *
 * 30/07/12    Liu Taichen      mergeOption                  created this field to store the information for merging of relation
 *
 * 30/07/12    Liu Taichen      updateRecord()				to also update the mergeOption to the database
 * 
 * 20/08/13    Jinghan          editRating()                add two more parameters to change the description and display code for a rating task.
 * 
 * 10/08/13	   Jinghan			CopySurvey()				Add calls to copyPrelimQn() and copyPrelimQnHeader().
 * 								copyPrelimQ(int)			Copy all preliminary question for a given foreign key of the survey and call addPrelimQn 
 * 															to add entries to the database. 
 * 								addPrelimQn(int, String, int) Create new entries in the database and assign them with new primary keys and 
 * 															new survey ID as foreign keys.
 * 								copyPrelimQnHeader(int)		Copy preliminary question header from a given survey ID and call addPremlimQnHead() to
 * 															add entries to database.
 * 								addPrelimQnHeader(int, String) Create new entries in the database and assign them with new primary keys and 
 * 															new survey ID as foreign keys.
 * 								copyAdditionalQn(int) 		Copy additional questions from a given survey ID and call addAdditionalQn to add 
 * 															new entries to the database.
 * 								addAdditionalQn(int, String) Create new entries in the database and assign them with new primary keys and 
 * 															new survey ID as foreign keys.
 * 								copyAdditionalQnAnsHeader(int) Copy additional question header from a given survey ID and call addAdditionalQn to add 
 * 															new entries to the database.
 * 								addAdditionalQnAnsHeader(int, String) Create new entries in the database and assign them with new primary keys and 
 * 															new survey ID as foreign keys.
 * 17/09/13		Jinghan			clearSurvey()				Add sql queries to delete additional and preliminary questions and headers.
 * 				
 * 								
 */
public class Create_Edit_Survey {
	/**
	 * Declaration of classes
	 */

	private Setting setting;

	private Cluster cluster;

	private Competency competency;

	private KeyBehaviour KB;

	private RatingScale scale;

	private EventViewer ev;

	/**
	 * Declaration of Variables
	 */
	private String UserDetail[] = new String[13];

	private String itemName = "Survey";

	private int JobPos_ID = 0;

	private int Survey_ID = 0;

	private int clusterID =0;

	private int SurveyStatus = 0;

	private int CompetencyLevel = 0;

	private int useCluster = 0;

	private int breakCPR = 0;
	
	private int hideKBDesc = 0;

	private int Purpose = 0;

	private int survOrg = 0;

	private int SurvRating = 0;

	private int GroupID = 0;

	private int CompLevel = 0;

	private int RatScale = 0;

	private int DivID = 0;

	private int DeptID = 0;

	private int JobCat = 0;
	
	private int HideCompDesc = 0;

	private int MergeRelation = 0;
	
	public int SortType;

	private int Toggle[]; // 0=asc, 1=desc

	private double minPassScore=1.0;

	/**
	 * Creates a new intance of Create Edit Survey object.
	 */
	public Create_Edit_Survey() {

		competency = new Competency();
		KB = new KeyBehaviour();
		scale = new RatingScale();
		setting = new Setting();
		ev = new EventViewer();

		Toggle = new int[6];

		for (int i = 0; i < 6; i++) {
			Toggle[i] = 0;
		}
		SortType = 1;
	}

	public void setClusterID(int value){
		clusterID = value;
	}
	public int getClusterID(){
		return clusterID;
	}
	/**
	 * ------------------------------Survey Detail
	 * section--------------------------------------------------
	 * 
	 * /** Get Survey informations
	 * 
	 * Parameters - SurveyID
	 * 
	 * Returns - Result Set with survey's information
	 * 
	 */
	public votblSurvey getSurveyDetail(int SurveyID) throws SQLException,
	Exception {
		votblSurvey vo = null;

		String sql = "SELECT * FROM tblSurvey a, tblJobPosition b, tblOrganization c, tblConsultingCompany d WHERE c.FKCompanyID = d.CompanyID AND a.JobPositionID=b.JobPositionID AND a.FKOrganization= c.PKOrganization AND SurveyID = "
				+ SurveyID;

		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs =
		 * stmt.executeQuery(sql); return rs;
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				vo = new votblSurvey();

				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setJobPositionID(rs.getInt("JobPositionID"));
				vo.setDateOpened(Utils.convertDateFormat(rs
						.getDate("DateOpened")));
				vo.setLevelOfSurvey(rs.getInt("LevelOfSurvey"));
				vo.setDeadlineSubmission(Utils.convertDateFormat(rs
						.getDate("DeadlineSubmission")));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setJobFutureID(rs.getInt("JobFutureID"));
				vo.setTimeFrameID(rs.getInt("TimeFrameID"));
				vo.setReliabilityCheck(rs.getInt("ReliabilityCheck"));
				vo.setNA_Included(rs.getInt("NA_Included"));
				vo.setMAX_Gap(rs.getFloat("MAX_Gap"));
				vo.setMIN_Gap(rs.getFloat("MIN_Gap"));
				vo.setComment_Included(rs.getInt("Comment_Included"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setOrganizationName(rs.getString("OrganizationName"));
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setCompanyName(rs.getString("CompanyName"));
				vo.setJobPosition(rs.getString("JobPosition"));
				vo.setTimeFrameID(rs.getInt("TimeFrameID"));
				vo.setJobFutureID(rs.getInt("JobFutureID"));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setSelf_Comment_Included(rs.getInt("Self_Comment_Included"));
				vo.setDMapBreakdown(rs.getInt("DMapBreakdown"));
				vo.setNameSequence(rs.getInt("NameSequence"));
				vo.setOpenedDate(rs.getDate("DateOpened"));
				vo.setDeadlineSubmissionDate(rs.getDate("DeadlineSubmission"));
				vo.setStartDateNomination(rs.getDate("NominationStartDate"));
				vo.setEndDateNomination(rs.getDate("NominationEndDate"));

				vo.setHideCompDesc(rs.getInt("HideCompDesc"));
                vo.setMergeRelation(rs.getInt("MergeRelation"));
                vo.setSurveyID(rs.getInt("SurveyID"));
				// Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self and/or Superior as rater
				//System.out.println(">>Create Edit Survey >> Frm DB: " + rs.getBoolean("AutoAssignSelf"));
				vo.setAutoSelf( rs.getBoolean("AutoAssignSelf") );
				vo.setAutoSup( rs.getBoolean("AutoAssignSuperior") );			

			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - getSurveyDetail - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return vo;

	}

	public Vector getSurveyDetail(int FKCompanyID, int FKOrganizationID)
			throws SQLException, Exception {
		Vector v = new Vector();
		String Sql = "SELECT * FROM tblSurvey a, tblOrganization b, tblJobPosition c";
		Sql = Sql
				+ " WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID AND a.FKCompanyID = "
				+ FKCompanyID;

		if (FKOrganizationID != 0)
			Sql = Sql + "AND a.FKOrganization = " + FKOrganizationID;

		Sql = Sql + "ORDER BY SurveyName";

		if (FKOrganizationID == 0)
			Sql = Sql + ", OrganizationName ";

		/*
		 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet rs =
		 * stmt.executeQuery(sql); return rs;
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(Sql);

			while (rs.next()) {

				votblSurvey vo = new votblSurvey();

				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setJobPositionID(rs.getInt("JobPositionID"));
				vo.setDateOpened(Utils.convertDateFormat(rs
						.getDate("DateOpened")));
				vo.setLevelOfSurvey(rs.getInt("LevelOfSurvey"));
				vo.setDeadlineSubmission(Utils.convertDateFormat(rs
						.getDate("DeadlineSubmission")));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setJobFutureID(rs.getInt("JobFutureID"));
				vo.setTimeFrameID(rs.getInt("TimeFrameID"));
				vo.setReliabilityCheck(rs.getInt("ReliabilityCheck"));
				vo.setNA_Included(rs.getInt("NA_Included"));
				vo.setMAX_Gap(rs.getFloat("MAX_Gap"));
				vo.setMIN_Gap(rs.getFloat("MIN_Gap"));
				vo.setComment_Included(rs.getInt("Comment_Included"));
				vo.setFKOrganization(rs.getInt("FKOrganization"));
				vo.setOrganizationName(rs.getString("OrganizationName"));
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));

				vo.setJobPosition(rs.getString("JobPosition"));
				vo.setTimeFrameID(rs.getInt("TimeFrameID"));
				vo.setJobFutureID(rs.getInt("JobFutureID"));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setSelf_Comment_Included(rs.getInt("Self_Comment_Included"));
				vo.setDMapBreakdown(rs.getInt("DMapBreakdown"));
                vo.setHideCompDesc(rs.getInt("HideCompDesc"));
				v.add(vo);
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - getSurveyDetail - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return v;

	}

	/**
	 * Add Survey informations
	 * 
	 * Parameters - String SurveyName, int JobPositionID, String MonthYear,
	 * String DateOpened, int LevelOfSurvey, String DeadlineSubmission, int
	 * SurveyStatus, String AnalysisDate, int ReliabilityCheck,int
	 * JobFutureID,int TimeFrameID ,int NA_Included, int FKOrganization, int
	 * FKCompanyID, String Min_Gap, String Max_Gap, int Comment_Included, int
	 * PKUser)
	 * 
	 * The new survey will be assigned with a new ID which will be made unique
	 * through out. The system will extract out the new ID for future uses.
	 * 
	 * Event viewer will capture this event.
	 */
	public boolean addRecord(String SurveyName, int JobPositionID,
			String MonthYear, String DateOpened, int LevelOfSurvey,
			String DeadlineSubmission, int SurveyStatus, String AnalysisDate,
			int ReliabilityCheck, int JobFutureID, int TimeFrameID,
			int NA_Included, int FKOrganization, int FKCompanyID,
			String Min_Gap, String Max_Gap, int Comment_Included, int PKUser, int useCluster, int breakCPR, int hideKBDesc)
					throws SQLException, Exception {
		boolean bIsAdded = false;
		//Added to not to display Self comments box when Include comments is not checked
		//Mark Oei 19 Mar 2010
		int Self_Comment_Included = 1;
		if (Comment_Included == 0)
			Self_Comment_Included = 0;

		// db.openDB();
		String sql = "INSERT INTO tblSurvey (SurveyName,JobPositionID,MonthYear,DateOpened,LevelOfSurvey,DeadlineSubmission, ";
		sql = sql
				+ "SurveyStatus,AnalysisDate, ReliabilityCheck,JobFutureID,TimeFrameID,NA_Included, FKOrganization, ";
		sql = sql
				+ "FKCompanyID, Min_Gap, Max_Gap, Comment_Included, AdminAssigned, Self_Comment_Included, NominationStartDate, NominationEndDate, useCluster, breakCPR, hideKBDesc)";
		sql = sql + "VALUES ('" + SurveyName.trim() + "'," + JobPositionID
				+ ",'" + MonthYear + "','" + DateOpened + "'," + LevelOfSurvey
				+ ", ";
		sql = sql + "'" + DeadlineSubmission + "'," + SurveyStatus + ",'"
				+ AnalysisDate + "'," + ReliabilityCheck + ", " + JobFutureID
				+ ", ";
		sql = sql + TimeFrameID + "," + NA_Included + "," + FKOrganization
				+ "," + FKCompanyID + ", '" + Min_Gap + "', '" + Max_Gap
				+ "', " + Comment_Included + ", " + PKUser + ", " + Self_Comment_Included; //Added Self_Comment_Included column, Mark Oei 19 Mar 2010
		sql = sql + ", '" + DateOpened + "', '" + DeadlineSubmission + "' ," + useCluster + " ,"+breakCPR+", "+hideKBDesc+")";
		
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addRecord - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		/* To take the new survey id */
		// db.openDB();
		String sql1 = "SELECT * FROM tblSurvey WHERE SurveyName = '"
				+ SurveyName.trim() + "' AND JobPositionID = " + JobPositionID
				+ " AND FKOrganization= " + FKOrganization
				+ " AND FKCompanyID =" + FKCompanyID;
		/*
		 * Statement stmt = db.con.createStatement(); ResultSet rs =
		 * stmt.executeQuery(sql1); if(rs.next())
		 * setSurvey_ID(rs.getInt("SurveyID")); db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			if (rs.next())
				setSurvey_ID(rs.getInt("SurveyID"));
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addRecord - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		UserDetail = getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, SurveyName.trim(), UserDetail[2],
				UserDetail[11], UserDetail[10]);


		return bIsAdded;
	}

	/**
	 * Edit Survey informations
	 * 
	 * Parameters - String SurveyName, int JobPositionID, String MonthYear,
	 * String DateOpened, int LevelOfSurvey, String DeadlineSubmission, int
	 * SurveyStatus, String AnalysisDate, int ReliabilityCheck,int
	 * NA_Included,int SurveyID, int FKOrganization, int FKCompanyID, String
	 * Min_Gap, String Max_Gap, int Comment_Included, int PKUser, int useCluster, int breakCPR
	 * int HideCompDesc
	 * 
	 * Event viewer will capture this event.
	 */

	public boolean updateRecord(String SurveyName, int JobPositionID,
			String MonthYear, String DateOpened, int LevelOfSurvey,
			String DeadlineSubmission, int SurveyStatus, String AnalysisDate,
			int ReliabilityCheck, int NA_Included, int SurveyID,
			int FKOrganization, int FKCompanyID, String Min_Gap,
			String Max_Gap, int Comment_Included, int PKUser, int useCluster, int HideCompDesc, int breakCPR, int hideKBDesc, int MergeRelation)
			throws SQLException, Exception {

		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String OldName = "";
		//Added to not to display Self comments box when Include comments is not checked
		//Mark Oei 19 Mar 2010
		int Self_Comment_Included = 1;
		if (Comment_Included == 0)
			Self_Comment_Included = 0;



		// Added by DeZ, 27.06.08, switch survey status to Not Commissioned when Survey Level is changed
		int OldLevelOfSurvey = -1;

		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + SurveyID;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next()) {
				OldName = rs.getString("SurveyName");
				OldLevelOfSurvey = rs.getInt("LevelOfSurvey");
				//System.out.println(">>UpdateRecord>>Survey Level from DB: " + OldLevelOfSurvey);
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateRecord - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		// Added by DeZ, 27.06.08, switch survey status to Not Commissioned when Survey Level is changed
		// Check to make sure that OldLevelOfSurvey is successfully fetched from DB
		if(OldLevelOfSurvey == -1 ) {
			// Error in retrieving LevelOfSurvey from DB, update error
			return false;
		}

		// Survey Level needs updating, switch survey status to Not Commissioned
		if( LevelOfSurvey != OldLevelOfSurvey ) {
			SurveyStatus = 3;
			//System.out.println(">>updateRecord>>Survey Level changed, Survey Status switched to Not Commissioned");
		}

		// End Added by DeZ, 27.06.08

		String sql = "UPDATE tblSurvey SET SurveyName ='" + SurveyName.trim()
				+ "',JobPositionID = " + JobPositionID + " ,MonthYear ='"
				+ MonthYear + "'";
		sql = sql + ", DateOpened='" + DateOpened + "',LevelOfSurvey = "
				+ LevelOfSurvey + ",DeadlineSubmission='" + DeadlineSubmission
				+ "'";
		sql = sql + ", SurveyStatus =" + SurveyStatus + ",AnalysisDate='"
				+ AnalysisDate + "',ReliabilityCheck =" + ReliabilityCheck;
		sql = sql + ", NA_Included=" + NA_Included;
		sql = sql + ", FKOrganization=" + FKOrganization + ", Min_Gap = '"
				+ Min_Gap + "', Max_Gap = '" + Max_Gap
				+ "', Comment_Included =" + Comment_Included + ", Self_Comment_Included =" + Self_Comment_Included +", useCluster = " + useCluster+", breakCPR = " + breakCPR+", hideKBDesc = " + hideKBDesc; //Added Self_Comment_Included column, Mark Oei 19 Mar 2010
		
		/*
		 * Change(s) added a line of sql command
		 * Reason(s) to update the HideCompDesc column as well
		 * Updated By : Liu Taichen
		 * Updated On : 12/07/2012
		 * 
		 */
		sql = sql + ", HideCompDesc = " + HideCompDesc;
		sql = sql + ", MergeRelation = " + MergeRelation;
		sql = sql + " WHERE SurveyID =" + SurveyID + " AND FKCompanyID ="
				+ FKCompanyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateRecord - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		UserDetail = getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "(" + OldName + ") - ("
				+ SurveyName.trim() + ")", UserDetail[2], UserDetail[11],
				UserDetail[10]);
		return bIsUpdated;
	}

	public boolean updateSurveyStatus(int iSurveyID, int iSurveyStatus, String sOrganisationName) {

		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;

		String sql = "UPDATE tblSurvey SET SurveyStatus =" + iSurveyStatus ;
		sql = sql + " WHERE SurveyID =" + iSurveyID;

		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			//by Hemilda Date 19/08/2008 Add message to identify which update, survey close or survey open
			if (iSuccess != 0){
				bIsUpdated = true;

				if (iSurveyStatus ==1)
				{
					System.out.println("Change Open Status for Survey Id "+iSurveyID);
				}else if (iSurveyStatus ==2){
					System.out.println("Change Close Status for Survey Id "+iSurveyID);
				}
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateSurveyStatus - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		try {
			ev.addRecord("Update", "Create/Edit Survey","Survey Status", "System", sOrganisationName, sOrganisationName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bIsUpdated;
	}

	// Added by DeZ, 26/06/08, Auto switch survey status to Not Comissioned whenever changes made to survey
	/**
	 * Update Survey Status without event recording
	 * @param surveyID      - ID of the survey
	 * @param iSurveyStatus - Status to update survey to
	 * @return true if successfully update survey status
	 * @author Desmond
	 */
	public boolean updateSurveyStatus(int iSurveyID, int iSurveyStatus) {

		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;

		String sql = "UPDATE tblSurvey SET SurveyStatus =" + iSurveyStatus ;
		sql = sql + " WHERE SurveyID =" + iSurveyID;

		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateSurveyStatus - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		//		try {
		//			ev.addRecord("Update", "Create/Edit Survey","Survey Status", "System", sOrganisationName, sOrganisationName);
		//		} catch (SQLException e) {
		// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (Exception e) {
		// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		return bIsUpdated;

	} // End updateSurveyStatus()

	/**
	 * To Update Nomination Dates By default Nomination Dates is the
	 */
	public boolean updateNominationDates(String NomStartDate,
			String NomEndDate, int SurveyID) throws SQLException, Exception {
		// db.openDB();
		String sql = "UPDATE tblSurvey SET NominationStartDate ='"
				+ NomStartDate + "', NominationEndDate = '" + NomEndDate
				+ "' WHERE SurveyID =" + SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - updateNominationDates - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;
	}

	/**
	 * check Survey competency
	 * 
	 * @param iSurveyID
	 * @return true if it exists
	 * @author yuni
	 */
	public boolean checkSurveyCompetency(int iSurveyID) {
		String sqlcheck = "SELECT * FROM tblSurveyCompetency WHERE SurveyID = "
				+ iSurveyID;
		String sql = "";
		int counter = 0;
		int count = 0;
		int competencyID;
		boolean bExist = false;
		Connection con1 = null;
		Statement st1 = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlcheck);
			//Added by Ha 30/05/08 to check whether it has at least 1 KB for each competency
			while (rs!= null && rs.next())
			{
				count++;
				competencyID = rs.getInt("CompetencyID");
				sql = "";
				sql += "SELECT * FROM  tblSurvey, tblSurveyBehaviour ";
				sql += "WHERE tblSurvey.SurveyID = tblSurveyBehaviour.SurveyID AND tblSurvey.SurveyID = "+iSurveyID +
						" AND tblSurveyBehaviour.CompetencyID in (SELECT CompetencyID from tblSurveyCompetency";
				sql += " WHERE CompetencyID = "+competencyID +" )";

				try
				{
					int i = 0;					
					con1 = ConnectionBean.getConnection();
					st1 = con1.createStatement();
					rs1 = st1.executeQuery(sql);
					while (rs1.next())					
						i++;					
					if (i >0)
						counter++;	

				}
				catch (Exception e)
				{
					System.out.println("Create_Edit_Survey.java-checkSurveyCompetency-inner loop "+e.getMessage());
				}	
				finally
				{
					ConnectionBean.closeRset(rs1);
					ConnectionBean.closeStmt(st1); // Close statement
					ConnectionBean.close(con1);
				}
			}

			if (count>0&&counter == count)
				bExist = true;// Ended of changed part made by Ha 30/05/08


		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - checkSurveyCompetency - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bExist;
	}

	//Added by HA 09/06/08 to check whether a survey has been added with a Competency or not
	public boolean checkCompetencyExist(int surveyID)
	{
		boolean isExist = false;
		String sqlcheck = "SELECT * FROM tblSurveyCompetency WHERE SurveyID = "
				+ surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlcheck);
			if (rs.next()) isExist = true;
		}
		catch (Exception ex)
		{
			System.out.println("Create_Edit_Survey.java - checkCompetencyExist - "
					+ ex.getMessage());
		}
		finally
		{
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return isExist;
	}
	/**
	 * check Survey Name
	 * 
	 * @param iSurveyID
	 * @return true if it exists
	 * @author thant thura
	 */
	public boolean checkSurveyName(String  sSurveyName) {
		// Add by Santoso (2008-11-04) 
		// Add the organization on the where clause also, to allow same name in different organization
		String sqlcheck = "SELECT * FROM tblSurvey WHERE SurveyName = '"
				+ sSurveyName+"' and FKOrganization="+survOrg;

		boolean bExist = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlcheck);

			if (rs.next())
				bExist = true;

		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - checkSurveyName - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bExist;
	}
	/**
	 * check Survey Name and survery ID
	 * 
	 * @param iSurveyID
	 * @return true if it exists
	 * @author thant thura
	 */
	public boolean checkSurveyName(String  sSurveyName,int iSurveryID) {
		// Add by Santoso (2008-11-04) 
		// Add the organization on the where clause also, to allow same name in different organization
		String sqlcheck = "SELECT * FROM tblSurvey WHERE SurveyName = '"
				+ sSurveyName+"' and surveyID <> " +iSurveryID 
				+ " and FKOrganization=" + survOrg;

		boolean bExist = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlcheck);

			if (rs.next())
				bExist = true;

		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - checkSurveyName 2 - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bExist;
	}
	/**
	 * Only fews minors information allowed to change when one or more Raters
	 * have completed their Questionnaire
	 * 
	 * Parameters - String MonthYear, String DateOpened,String
	 * DeadlineSubmission, int SurveyStatus, String AnalysisDate, int SurveyID,
	 * String Min_Gap, String Max_Gap, int PKUser
	 * 
	 * 
	 * Event viewer will capture this event.
	 */
	public boolean updateRecord_AfterRaterComplete(String MonthYear,
			String DeadlineSubmission, int SurveyStatus,
			String AnalysisDate, int SurveyID, 
			int PKUser) throws SQLException, Exception {
		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String OldName = "";
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + SurveyID;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next())
				OldName = rs.getString("SurveyName");
		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - updateRecord_AfterRaterComplete - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("SurveyName"); rs1.close();
		 * 
		 * db.openDB();
		 */


		String sql = "UPDATE tblSurvey SET MonthYear = '" + MonthYear + "'"
				+ ", DeadlineSubmission = '" + DeadlineSubmission + "'";
		sql = sql + ", SurveyStatus = " + SurveyStatus + ", AnalysisDate = '"
				+ AnalysisDate + "'";
		sql = sql + " WHERE SurveyID = " + SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - updateRecord_AfterRaterComplete - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		UserDetail = getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "(" + OldName + ") - (" + OldName
				+ ")", UserDetail[2], UserDetail[11], UserDetail[10]);


		return bIsUpdated;
	}

	/**
	 * When a new Future Rating Task selected. Users need to filled in
	 * additional information for te surveys.
	 * 
	 * Parameters - int SurveyID, int JobFutureID, int TimeFrameID, int PKUser
	 * 
	 * 
	 * Event viewer will capture this event.
	 */

	public boolean updateFuture(int SurveyID, int JobFutureID, int TimeFrameID,
			int PKUser) throws SQLException, Exception {
		boolean bIsUpdated = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String OldName = "";
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + SurveyID;
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("SurveyName"); rs1.close();
		 * 
		 * db.openDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next())
				OldName = rs.getString("SurveyName");
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateFuture  - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		String sql = "UPDATE tblSurvey SET";
		sql = sql + " JobFutureID = " + JobFutureID + ", TimeFrameID = "
				+ TimeFrameID;
		sql = sql + " WHERE SurveyID =" + SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate();
		 * 
		 * db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - updateFuture - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		UserDetail = getUserDetail(PKUser);
		ev.addRecord("Update", itemName, "(" + OldName + ") - (" + OldName
				+ ")", UserDetail[2], UserDetail[11], UserDetail[10]);
		return bIsUpdated;
	}

	/**
	 * Deletion of survey
	 * 
	 * 
	 * Parameters - int SurveyID, int PKUser
	 * 
	 * 
	 * Event viewer will capture this event by storing the survey name .
	 */

	public boolean delSurvey(int SurveyID, int PKUser) throws SQLException, Exception {
		return delSurvey(SurveyID, PKUser, null);
	}
	/**
	 * Added by xuehai, 09 Jun 2011.
	 *  With option to choose whether clear user data when deleting a survey
	 *  
	 * @param clearUserData, true:clear user data;
	 */
	public boolean delSurvey(int SurveyID, int PKUser, String clearUserData) throws SQLException,
	Exception {
		boolean bIsDeleted = false;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String OldName = "";
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + SurveyID;
		/*
		 * ResultSet rs1 = db.getRecord(command); if(rs1.next()) OldName =
		 * rs1.getString("SurveyName"); rs1.close();
		 * 
		 * db.openDB();
		 */
		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next())
				OldName = rs.getString("SurveyName");
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delSurvey - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		//Added by Xuehai, 09 Jun 2011. When deleting a survey, clear other data related to it.
		if(clearUserData!=null){
			this.clearSurveyData(SurveyID, clearUserData);
		}

		String sql = "DELETE FROM tblSurvey WHERE SurveyID=" + SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql); ps.execute();
		 * db.closeDB();
		 */
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();

			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delSurvey - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		UserDetail = getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, UserDetail[2],
				UserDetail[11], UserDetail[10]);

		return bIsDeleted;
	}

	/**
	 * Added by Xuehai, 09 Jun 2011. When deleting a survey, clear other data related to it.
	 * 
	 * @param SurveyID
	 */

	private void clearSurveyData(int SurveyID, String clearUserData){
		if(clearUserData==null || clearUserData.length()==0 || "0".equals(clearUserData)){
			return;
		}
		//**Targets
		//User

		//**Raters
		//User

		//**Target-rater assignments
		//tblRatingSetup
		//tbllevelofAgreement
		//--tblTrimmedMean
		//tblGap
		//tblDevelopmentPlan
		//tblAvgMean
		//tblAvgMeanByRater
		//tblAssignment
		//tblComment
		//tblResultCompetency
		//tblResultBehaviour
		//tblSurveyCompetency
		//tblSurveyBehaviour
		//tblSurveyDemos
		//--tblSurvRatingTask
		//tblSurveyRating
		//tblReliable

		Connection con = null;
		Statement st = null;

		//The array 'sqls' has three parts: 
		// sqls[0] contains data of Target;
		// sqls[1] refers to Raters;
		// sqls[2] refers to Targets-Raters Assignment
		String[][] sqls = {
				{//Delete User where it is the target only for this survey and not a rater for any other survey.
					"DELETE FROM [User] WHERE [User].PKUser in (SELECT TargetLoginID from tblAssignment where SurveyID = " + SurveyID + 
					") and [User].PKUser not in (SELECT TargetLoginID from tblAssignment where SurveyID <> " + SurveyID + 
					//Gwen Oh - 17/10/2011: Make sure the target is not a rater for any other survey
					") and [User].PKUser not in (SELECT RaterLoginID from tblAssignment where SurveyID <> " + SurveyID + ")"
				},
				{//Delete User where it is the rater only for this survey and not a target for any other survey.
					"DELETE FROM [User] WHERE [User].PKUser in (SELECT RaterLoginID from tblAssignment where SurveyID = " + SurveyID + 
					") and [User].PKUser not in (SELECT RaterLoginID from tblAssignment where SurveyID <> " + SurveyID + 
					//Gwen Oh - 17/10/2011: Make sure the target is not a target for any other survey
					") and [User].PKUser not in (SELECT TargetLoginID from tblAssignment where SurveyID <> " + SurveyID + ")"
				},
				{
					"DELETE FROM tbllevelofAgreement WHERE SurveyID=" + SurveyID,
					"DELETE FROM tblRatingSetup WHERE SurveyID=" + SurveyID,
					//"DELETE FROM tblTrimmedMean WHERE SurveyID=" + SurveyID,
					"DELETE FROM tblGap WHERE SurveyID=" + SurveyID,
					"DELETE FROM tblDevelopmentPlan WHERE FKSurveyID=" + SurveyID,
					"DELETE FROM tblAvgMean WHERE SurveyID=" + SurveyID,
					"DELETE FROM tblAvgMeanByRater WHERE SurveyID=" + SurveyID,
					"DELETE FROM tblComment WHERE AssignmentID in " + 
							"( select AssignmentID from tblAssignment where SurveyID=" + SurveyID + " )",
							"DELETE FROM tblResultCompetency WHERE AssignmentID in " + 
									"( select AssignmentID from tblAssignment where SurveyID=" + SurveyID + " )",
									"DELETE FROM tblResultBehaviour WHERE AssignmentID in " + 
											"( select AssignmentID from tblAssignment where SurveyID=" + SurveyID + " )",
											"DELETE FROM tblAssignment WHERE SurveyID=" + SurveyID,
											"DELETE FROM tblSurveyCompetency WHERE SurveyID=" + SurveyID,
											"DELETE FROM tblSurveyBehaviour WHERE SurveyID=" + SurveyID,
											"DELETE FROM tblSurveyDemos WHERE SurveyID=" + SurveyID,
											// "DELETE FROM tblSurvRatingTask WHERE FKRatingTaskID in "  +
											// " ( select RatingTaskID from tblSurveyRating where SurveyID=" + SurveyID + ")",
											"DELETE FROM tblSurveyRating WHERE SurveyID=" + SurveyID,
											"DELETE FROM tblReliable WHERE SurveyID=" + SurveyID,
					"DELETE FROM tbl_PrelimQn WHERE FKSurveyID = " + SurveyID,
					"DELETE FROM tbl_PrelimQnHeader WHERE FKSurveyID = " + SurveyID,
					"DELETE FROM tbl_AdditionalQn WHERE FKSurveyID = " + SurveyID,
					"DELETE FROM tbl_AdditionalQuestionAnsHeader WHERE FKSurveyID = " + SurveyID
					
				}
		};
		try {

			con = ConnectionBean.getConnection();
			for(int i=0;i<sqls.length;i++){
				//if clearUserData contains "1", Clear data 'Targets'
				//if clearUserData contains "2", Clear data 'Rates'
				//if clearUserData contains "3", Clear data 'Survey-Rater Assignment'
				if(clearUserData.contains(String.valueOf(i+1))){
					for(int j=0;j<sqls[i].length;j++){
						try{
							st = con.createStatement();
							int deletedCount = st.executeUpdate(sqls[i][j]);
							System.out.println("i: " + i + ", j: " + j);
							System.out.println("SQL: " + sqls[i][j]);
							//System.out.println("--Effected rows: " + deletedCount);
						}catch(Exception ex){
							System.out.println("Error: Create_Edit_Survey.java - clearSurveyData - " + ex.getMessage());
						}finally{
							ConnectionBean.closeStmt(st); // Close statement
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: Create_Edit_Survey.java - clearSurveyData - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.close(con); // Close connection
		}
	}

	/*
	 * -----------------------------end survey detail-----------------------------
	 */

	/*
	 * -----------------------------Section for competency in the survey------------------------------
	 */

	/**
	 * Add Cluster into a survey
	 * 
	 * 
	 * @param - String ClusterID, int SurveyID
	 * 
	 */
	public boolean addCluster(int ClusterID, int SurveyID) throws SQLException, Exception {
		boolean bIsUpdated = false;
		String sql = "INSERT INTO tblSurveyCluster (SurveyID, ClusterID) VALUES ("+ SurveyID + "," + ClusterID +")";

		Connection con = null;
		Statement st = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			//Update survey status to not commissioned if successful
			if (iSuccess != 0) {
				// Changes made to survey, switch to non-comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>addCompetency>>Changes made to survey, switched status to Not Comissioned");
					bIsUpdated = true;
				}
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addCluster - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;
	}

	/*
	 * This will delete the selected member from the database by passing the
	 * member ID
	 */

	/**
	 * Delete Cluster from a survey. This action will also delete all the competencies related to the chosen
	 * cluster.
	 * 
	 * @param - int SurveyID, String CompID
	 * 
	 * @return true if deleted successfully else false
	 * 
	 */

	public boolean delCluster(int SurveyID, String ClusterID) throws SQLException, Exception { 
		String sql = "DELETE FROM tblSurveyCluster WHERE SurveyID="
				+ SurveyID + " AND ClusterID = '" + ClusterID + "'";
		String sql2 = "DELETE FROM tblSurveyCompetency WHERE SurveyID="
				+ SurveyID + " AND ClusterID = '" + ClusterID + "'";

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {                
			con = ConnectionBean.getConnection();
			st = con.createStatement();

			// Execute Query to remove Cluster
			int iSuccess = st.executeUpdate(sql);

			// Successfully delete cluster
			if (iSuccess != 0) {
				// Execute query to delete related added Key Behaviour(s)
				st.executeUpdate(sql2);

				// update survey status to Not Comissioned whenever changes made to survey
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					bIsUpdated = true;
				}
			}                        
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delCluster - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;  
	}

	/*
	 * -------------------------------------------end cluster section------------------------------------
	 */


	/*
	 * -----------------------------Section for competency in the survey------------------------------
	 */

	/**
	 * Add Competencies into a survey
	 * 
	 * 
	 * Parameters - String CompetencyID, int SurveyID, String CompetencyLevel
	 * 
	 */
	public boolean addCompetency(int CompetencyID, int SurveyID,
			int CompetencyLevel) throws SQLException, Exception {
		boolean bIsUpdated = false;
		// db.openDB();
		String sql = "INSERT INTO tblSurveyCompetency (SurveyID, CompetencyID, CompetencyLevel) VALUES ("
				+ SurveyID + "," + CompetencyID + ", " + CompetencyLevel + ")";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			// Changed by DeZ, 26/06/08, update survey status to Not Comissioned whenever changes are made to survey
			if (iSuccess != 0) {

				// Changes made to survey, switch to non-comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>addCompetency>>Changes made to survey, switched status to Not Comissioned");
					bIsUpdated = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addCompetency - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;

	}

	/**
	 * Add Competencies into a survey with cluster
	 * 
	 * 
	 * Parameters - String CompetencyID, int SurveyID, String CompetencyLevel
	 * 
	 */
	public boolean addCompetency(int CompetencyID, int SurveyID, int CompetencyLevel, int ClusterID) throws SQLException, Exception {
		boolean bIsUpdated = false;
		// db.openDB();
		String sql = "INSERT INTO tblSurveyCompetency (SurveyID, CompetencyID, CompetencyLevel, ClusterID) VALUES ("
				+ SurveyID + "," + CompetencyID + ", " + CompetencyLevel + ", "+ClusterID+")";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			// Changed by DeZ, 26/06/08, update survey status to Not Comissioned whenever changes are made to survey
			if (iSuccess != 0) {

				// Changes made to survey, switch to non-comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>addCompetency>>Changes made to survey, switched status to Not Comissioned");
					bIsUpdated = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addCompetency - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;

	}

	/*
	 * This will delete the selected member from the database by passing the
	 * member ID
	 */

	/**
	 * Delete Competencies from a survey If key behaviours chosen - This action
	 * will also delete all the key behaviours related to the chosen
	 * competencies.
	 * 
	 * Parameters - int SurveyID, String CompID
	 * 
	 * Returns true if deleted successfully else false
	 * 
	 * Edited by Eric Lu 21/5/08
	 * Converted void function to boolean function
	 * 
	 * Changed by DeZ 26/06/08
	 * Clean up and change codes to auto switch survey status to Not Comissioned
	 * whenever changes made to survey
	 * 
	 */

	public boolean delCompetency(int SurveyID, String CompID) throws SQLException,
	Exception { /*
	 * db.openDB(); String sql = "DELETE FROM
	 * tblSurveyCompetency WHERE SurveyID="+ SurveyID+" AND
	 * CompetencyID = '"+CompID+"'"; PreparedStatement ps =
	 * db.con.prepareStatement(sql); ps.execute();
	 * db.closeDB();
	 * 
	 * db.openDB(); String sql2 = "DELETE FROM
	 * tblSurveyBehaviour WHERE SurveyID="+ SurveyID+" AND
	 * CompetencyID = '"+CompID+"'"; PreparedStatement ps2 =
	 * db.con.prepareStatement(sql2); ps2.execute();
	 * db.closeDB();
	 */
		String sql = "DELETE FROM tblSurveyCompetency WHERE SurveyID="
				+ SurveyID + " AND CompetencyID = '" + CompID + "'";
		String sql2 = "DELETE FROM tblSurveyBehaviour WHERE SurveyID="
				+ SurveyID + " AND CompetencyID = '" + CompID + "'";

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();

			// Execute Query to remove Competency
			int iSuccess = st.executeUpdate(sql);

			// Successfully delete Competency
			if (iSuccess != 0) {

				// Execute query to delete related added Key Behaviour(s)
				st.executeUpdate(sql2);

				// Added by DeZ, 26/06/08, update survey status to Not Comissioned whenever changes made to survey
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>delCompetency>>Changes made to survey, status switched to Not Comissioned");
					bIsUpdated = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delCompetency - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		//		try {
		//			con = ConnectionBean.getConnection();
		//			st = con.createStatement();
		//			st.executeUpdate(sql2);
		//		} catch (Exception ex) {
		//			System.out.println("Create_Edit_Survey.java - delCompetency - "
		//					+ ex.getMessage());
		//		} finally {
		//			ConnectionBean.closeStmt(st); // Close statement
		//			ConnectionBean.close(con); // Close connection
		//		}

		return bIsUpdated;

	} // End delCompetency()

	public boolean delCompetency(int SurveyID, int ClusterID, String CompID) throws SQLException,Exception {

		String sql = "DELETE FROM tblSurveyCompetency WHERE SurveyID="+ SurveyID +" AND ClusterID="+ ClusterID +" AND CompetencyID = '" + CompID + "'";
		String sql2 = "DELETE FROM tblSurveyBehaviour WHERE SurveyID="+ SurveyID +" AND ClusterID="+ ClusterID +" AND CompetencyID = '" + CompID + "'";

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();

			// Execute Query to remove Competency
			int iSuccess = st.executeUpdate(sql);

			// Successfully delete Competency
			if (iSuccess != 0) {

				// Execute query to delete related added Key Behaviour(s)
				st.executeUpdate(sql2);

				// Added by DeZ, 26/06/08, update survey status to Not Comissioned whenever changes made to survey
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>delCompetency>>Changes made to survey, status switched to Not Comissioned");
					bIsUpdated = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delCompetency - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;

	} // End delCompetency()

	/*
	 * -------------------------------------------end competency
	 * section------------------------------------
	 */

	/*
	 * -----------------------------------------section for key
	 * behaviour---------------------------------------
	 */

	/**
	 * Add key behaviours into a survey with a cluster
	 * 
	 * 
	 * Parameters - int SurveyID, int CompetencyID, int KeyBehaviourID, int ClusterID
	 * 
	 */

	public boolean addKeyBehaviour(int SurveyID, int CompetencyID,int KeyBehaviourID, int ClusterID) throws SQLException, Exception {
		String sql = "INSERT INTO tblSurveyBehaviour (SurveyID, ClusterID, CompetencyID, KeyBehaviourID) VALUES ("
				+ SurveyID + ","+ClusterID+"," + CompetencyID + "," + KeyBehaviourID + ")";

		Connection con = null;
		Statement st = null;
		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0) {
				// Switch status to Not Comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					bIsAdded = true;
				}

			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addKeyBehaviour - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;
	}

	/**
	 * Add key behaviours into a survey
	 * 
	 * 
	 * Parameters - int SurveyID, int CompetencyID, String KeyBehaviourID
	 * 
	 */

	public boolean addKeyBehaviour(int SurveyID, int CompetencyID,
			int KeyBehaviourID) throws SQLException, Exception {
		// db.openDB();
		String sql = "INSERT INTO tblSurveyBehaviour (SurveyID, CompetencyID, KeyBehaviourID) VALUES ("
				+ SurveyID + "," + CompetencyID + "," + KeyBehaviourID + ")";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			// Changed by DeZ, 26/06/08, update survey status to Not Comissioned whenever changes made to survey
			if (iSuccess != 0) {

				// Switch status to Not Comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println("addKeyBehaviour>>Changes made to survey, switched status to Not Comissioned");
					bIsAdded = true;
				}

			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addKeyBehaviour - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;
	}

	/*
	 * This will delete the selected member from the database by passing the
	 * member ID
	 */

	/**
	 * Delete specific Key behaviour from a survey
	 * 
	 * Parameters - int SurveyID, String KeyBehaviourID
	 * 
	 */

	public boolean delKeyBehaviour(int SurveyID, String KeyBehaviourID)
			throws SQLException, Exception {
		// db.openDB();
		String sql = "DELETE FROM tblSurveyBehaviour WHERE SurveyID="
				+ SurveyID + " AND KeyBehaviourID =" + KeyBehaviourID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql); ps.execute();
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0) {

				// Survey has been changed, switch survey status to Non-comissioned
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>delKeyBehaviour>>Changes made to survey, switched status to Not Comissioned");
					bIsDeleted = true;
				}

			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delKeyBehaviour - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsDeleted;
	}

	/**
	 * Delete all Competencies from the specific survey
	 * 
	 * Parameters - int SurveyID
	 * 
	 */
	public boolean del_Survey_Competency(int SurveyID) throws SQLException, Exception {
		String sql = "DELETE FROM tblSurveyCompetency WHERE SurveyID="+ SurveyID;

		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - del_Survey_Competency - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsDeleted;
	}

	/**
	 * Delete all Key behaviours from the specific survey
	 * 
	 * Parameters - int SurveyID, String KeyBehaviourID
	 * 
	 */
	public boolean del_Survey_KeyBehaviour(int SurveyID) throws SQLException,
	Exception {
		// db.openDB();
		String sql = "DELETE FROM tblSurveyBehaviour WHERE SurveyID="
				+ SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql); ps.execute();
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception ex) {
			System.out
			.println("Create_Edit_Survey.java - del_Survey_KeyBehaviour - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsDeleted;

	}

	/*
	 * ------------------------------------------------end key behaviour section
	 * ----------------------------
	 */

	/*-------------------------------------Demographic Section-----------------------------------------------*/

	/**
	 * Add Demographics into a survey
	 * 
	 * 
	 * Parameters - int SurveyID, String DemographicID
	 * 
	 */

	public boolean addDemos(int SurveyID, String DemographicID)
			throws SQLException, Exception {
		// db.openDB();
		String sql = "INSERT INTO tblSurveyDemos (SurveyID, DemographicID) VALUES ("
				+ SurveyID + ",'" + DemographicID + "')";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addDemos - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;
	}

	/**
	 * Add Demographics into a survey
	 * 
	 * 
	 * Parameters - int SurveyID, String [] DemographicID
	 * 
	 */

	public boolean addDemos(int SurveyID, String[] DemographicID)
			throws SQLException, Exception {
		// db.openDB();
		String check = " ";

		String sql = "INSERT INTO tblSurveyDemos (SurveyID, DemographicID) VALUES (?,?)";
		/*
		 * ps = db.con.prepareStatement(sql); for (int i = 0; i <
		 * DemographicID.length; i++) { if(!DemographicID[i].equals(check)) {
		 * ps.setInt(1, SurveyID); ps.setString(2, DemographicID[i]);
		 * ps.executeUpdate(); check = DemographicID[i]; } } db.closeDB();
		 */
		Connection con = null;
		PreparedStatement ps = null;
		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			ps = con.prepareStatement(sql);
			int iSuccess = 0;
			for (int i = 0; i < DemographicID.length; i++) {
				if (!DemographicID[i].equals(check)) {
					ps.setInt(1, SurveyID);
					ps.setString(2, DemographicID[i]);
					int iUpdate = ps.executeUpdate();

					if (iUpdate != 0)
						iSuccess++;

					check = DemographicID[i];
				}
			}
			if (iSuccess == DemographicID.length) {
				bIsAdded = true;
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addDemos - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closePStmt(ps);

			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;
	}

	/**
	 * Delete specific Demographic from a survey
	 * 
	 * 
	 * Parameters - int SurveyID, String DemographicID
	 * 
	 */

	public boolean delDemos(int SurveyID, String DemographicID)
			throws SQLException, Exception {
		// db.openDB();
		String sql = "DELETE FROM tblSurveyDemos WHERE SurveyID=" + SurveyID
				+ " AND DemographicID ='" + DemographicID + "'";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql); ps.execute();
		 * db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delDemos - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsDeleted;

	}

	/*
	 * ------------------------------------------------END DEMOGRAPHIC SECTION
	 * ----------------------------
	 */

	/*
	 * ------------------------------------------------ RATING SCALE SECTION
	 * ----------------------------
	 */

	/**
	 * When a purpose of rating task is chosen. Survey information will be
	 * updated
	 * 
	 * 
	 * Parameters - int SurveyID, int PurposeID
	 * 
	 */

	public boolean editPurpose(int SurveyID, int PurposeID)
			throws SQLException, Exception {
		// db.openDB();
		String sqlx = "UPDATE tblSurvey SET PurposeID = " + PurposeID
				+ " WHERE SurveyID = " + SurveyID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sqlx);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sqlx);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - editPurpose - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;

	}

	/**
	 * Add rating task into a survey
	 * 
	 * 
	 * Parameters - int SurveyID, String ScaleID, String RatingTaskID, String
	 * RatingTaskName
	 * 
	 */

	public boolean addRating(int SurveyID, String ScaleID, String RatingTaskID,
			String RatingTaskName) throws SQLException, Exception {

		// db.openDB();
		String sql = "INSERT INTO tblSurveyRating (SurveyID, ScaleID, RatingTaskID, RatingTaskName) VALUES ("
				+ SurveyID
				+ ", '"
				+ ScaleID
				+ "','"
				+ RatingTaskID
				+ "', '"
				+ RatingTaskName + "')";
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			// Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
			if (iSuccess != 0) {
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println(">>addRating>>Changes made to survey, switched status to Not Commissioned");
					bIsAdded = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - addRating - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;

	}

	/**
	 * Edit rating task into a survey
	 * 
	 * 
	 * Parameters - int SurveyID, int RatingTaskID, String RatingTaskName, 
	 * String RatingTaskDesc, String RTDisplayCode
	 * 
	 * Method modified editRating could also edit task description and task display code.
	 */

	public boolean editRating(int SurveyID, int RatingTaskID, String RatingTaskName,
			 String RatingTaskDesc, String RTDisplayCode) throws SQLException, Exception {
		// db.openDB();
		String sql = "UPDATE tblSurveyRating SET RatingTaskName ='"
				+ RatingTaskName + "', RTDescription = '" + RatingTaskDesc
				+ "', RTDisplayCode = '" + RTDisplayCode
				+ "' WHERE SurveyID =" + SurveyID
				+ " AND RatingTaskID = " + RatingTaskID;
		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql);
		 * ps.executeUpdate(); db.closeDB();
		 */
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
			
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - editRating - "
					+ ex.getMessage());
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsUpdated;

	}

	/**
	 * Delete rating task from a survey
	 * 
	 * 
	 * Parameters - int SurveyID, String RatingTaskID
	 * 
	 */

	public boolean delRating(int SurveyID, String RatingTaskID)
			throws SQLException, Exception {
		// db.openDB();
		String sql = "DELETE FROM tblSurveyRating WHERE SurveyID=" + SurveyID
				+ " AND RatingTaskID ='" + RatingTaskID + "'";

		// Added by DeZ, 24/06/08, fix problem with delete rating task but fixed values not changed
		String sql2  = "DELETE FROM tblRatingSetup WHERE SurveyID = "+SurveyID
				+ " AND RatingTaskID ='" + RatingTaskID + "'";

		/*
		 * PreparedStatement ps = db.con.prepareStatement(sql); ps.execute();
		 * db.closeDB();
		 */

		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();

			// Changed by DeZ, 24/06/08, fix problem with delete rating task but fixed values not changed
			int iSuccessRating = st.executeUpdate(sql);
			//System.out.println(">>delRating>>Successfully deleted rating");
			// Added by DeZ, 24/06/08, fix problem with delete rating task but fixed values not changed
			st.executeUpdate(sql2);
			//System.out.println(">>delRating>>Successfully deleted rating setup");
			// Added by DeZ, 24/06/08, fix problem with delete rating task but fixed values not changed
			// Changed by DeZ, 26/06/08, no need to check for errors of sql query2;
			if( iSuccessRating != 0 ) {
				// Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
				if( this.updateSurveyStatus(SurveyID, 3) ) {
					//System.out.println("delRating>>Changes made to survey, switched status to Not Comissioned");
					bIsDeleted = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - delRating - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsDeleted;

	}

	/*
	 * ------------------------------------------------END RATING SCALE SECTION
	 * ----------------------------
	 */

	/*----------------------------------------START: COPY SURVEY---------------------------------------------*/

	/**
	 * Copy a survey This action will create a copy of a survey and also copied
	 * all the attributes
	 * 
	 * Parameters - int SurveyID, String SurveyName, int OrgID, int Status, int
	 * PKUser
	 * 
	 */

	public void CopySurvey(int SurveyID, String SurveyName, int OrgID,
			int Status, int PKUser) throws SQLException, Exception {
		int iNewClusterID = 0;
		int iNewCompetencyID = 0;
		int iNewCompetencyLevel = 0;
		int NewKB = 0;
		String NewScaleID = " ";
		int JobPosID1 = 0;
		int JobFutureID = 0;
		int TimeFrameID = 0;
		int NA_Included = 0;
		// String month = "";
		String DateOpened = "";
		String DeadlineDate = "";
		String AnalysisDate = "";
		int ReliabilityCheck = 0;
		int LevelofSurvey = 0;
		int FKOrg = 0;
		int FKCompanyID = 0;
		String MinGap = "";
		String MaxGap = "";
		int Comment_Included = 0;
		int hideNA = 0;
		int split_other = 0;
		int useCluster = 0;
		int prelimQ = 0;

		votblSurvey voSurvey = getSurveyDetail(SurveyID);
		if (voSurvey != null) {
			JobPosID1 = voSurvey.getJobPositionID();
			// month = rs_SurveyDetail.getDate("MonthYear").toString();
			DateOpened = voSurvey.getOpenedDate().toString();
			LevelofSurvey = voSurvey.getLevelOfSurvey();
			DeadlineDate = voSurvey.getDeadlineSubmissionDate().toString();
			AnalysisDate = voSurvey.getAnalysisDate();
			JobFutureID = voSurvey.getJobFutureID();
			TimeFrameID = voSurvey.getTimeFrameID();
			ReliabilityCheck = voSurvey.getReliabilityCheck();
			NA_Included = voSurvey.getNA_Included();
			FKOrg = voSurvey.getFKOrganization();
			FKCompanyID = voSurvey.getFKCompanyID();
			MinGap = "" + voSurvey.getMIN_Gap();
			MaxGap = "" + voSurvey.getMAX_Gap();
			Comment_Included = voSurvey.getComment_Included();
			useCluster = voSurvey.getUseCluster();
			breakCPR = voSurvey.getBreakCPR();
			hideKBDesc = voSurvey.getHideKBDesc();
		}

		addRecord(SurveyName, JobPosID1, "NA", DateOpened, LevelofSurvey,
				DeadlineDate, Status, AnalysisDate, ReliabilityCheck,
				JobFutureID, TimeFrameID, NA_Included, OrgID, FKCompanyID,
				MinGap, MaxGap, Comment_Included, PKUser, useCluster, breakCPR, hideKBDesc);

		copyPrelimQn(SurveyID);
		copyPrelimQnHeader(SurveyID);
		copyAdditionalQn(SurveyID);
		copyAdditionalQnAnsHeader(SurveyID);
		
		Connection con = null;
		Statement st = null;


		ResultSet rs_Cluster = null;
		ResultSet rs_Cluster2 = null;
		ResultSet rs_Comp = null;
		ResultSet rs_Comp2 = null;
		ResultSet rs_KB = null;
		ResultSet rs_KB2 = null;
		ResultSet rs_SurvDemos = null;
		ResultSet rs_Rating = null;
		ResultSet rs_Scale = null;
		ResultSet rs_Scale2 = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();

			//Denise 04/01/2010 to add the hide NA and split other, useCluster and breakCPR to the survey
			String sql = "update tblSurvey set hideNA = " + getHideNAOption(SurveyID) + ", splitOthers = " +
					getSplitOthersOption(SurveyID) + ", useCluster = "+getUseClusterOption(SurveyID)+ ", breakCPR = "+getBreakCPR(SurveyID)+" where surveyID = " + this.Survey_ID;
			int result = st.executeUpdate(sql);
			
			useCluster = getUseClusterOption(SurveyID);
			/*---------------------------------------- START Check for cluster ----------------------------------*/
			if(useCluster==0){//not using cluster
				SurveyCluster SClust = new SurveyCluster();
				Vector vCluster = SClust.getSurveyCluster(SurveyID);
				for (int i = 0; i < vCluster.size(); i++) {
					votblSurveyCluster vo = (votblSurveyCluster) vCluster.elementAt(i);

					int ClusterID = vo.getClusterID();

					if (FKOrg != OrgID) {
						String sql_Cluster = "SELECT * FROM Cluster WHERE PKCluster= "	+ ClusterID;
						rs_Cluster = st.executeQuery(sql_Cluster);
						if (rs_Cluster.next()) {
							String ClusterName = rs_Cluster.getString("ClusterName");

							cluster.insertCluster(ClusterName, OrgID, PKUser);

							String sql_Cluster2 = "SELECT * FROM Cluster WHERE ClusterName= '"+ ClusterName + "'";
							sql_Cluster2 = sql_Cluster2	+ " AND FKCompanyID= " + FKCompanyID+ " AND FKOrganizationID = " + OrgID;

							rs_Cluster2 = st.executeQuery(sql_Cluster2);
							if (rs_Cluster2.next()) {
								iNewClusterID = rs_Cluster2.getInt("PKCluster");
							}

							addCluster(iNewClusterID, getSurvey_ID());
						} else {
							addCluster(ClusterID, getSurvey_ID());
						}

					} else {
						addCluster(ClusterID, getSurvey_ID());
					}
				}


				/*---------------------------------------- END Check for Cluster -----------------------------------*/

				/*---------------------------------------- START Check for competency ----------------------------------*/

				SurveyCompetency SC = new SurveyCompetency();
				Vector vComp = SC.getSurveyCompetency(SurveyID);

				con = ConnectionBean.getConnection();
				st = con.createStatement();

				for (int i = 0; i < vComp.size(); i++) {
					votblSurveyCompetency vo = (votblSurveyCompetency) vComp.elementAt(i);

					int db_CompLevel = vo.getCompetencyLevel();
					int CompID = vo.getCompetencyID();

					if (FKOrg != OrgID) {
						String sql_Competency = "SELECT * FROM Competency WHERE PKCompetency= "+ CompID + " AND IsSystemGenerated=0";
						rs_Comp = st.executeQuery(sql_Competency);
						if (rs_Comp.next()) {
							String compName = rs_Comp.getString("CompetencyName");
							String compDef = rs_Comp.getString("CompetencyDefinition");

							competency.addRecord(compName, compDef, FKCompanyID,OrgID, PKUser, 2);

							String sql_Competency2 = "SELECT * FROM Competency WHERE CompetencyName= '"
									+ compName + "'";
							sql_Competency2 = sql_Competency2
									+ " AND FKCompanyID= " + FKCompanyID
									+ " AND FKOrganizationID = " + OrgID;

							rs_Comp2 = st.executeQuery(sql_Competency2);
							if (rs_Comp2.next()) {
								iNewCompetencyID = rs_Comp2.getInt("PKCompetency");
								iNewCompetencyLevel = rs_Comp2
										.getInt("CompetencyLevel");
							}

							addCompetency(iNewCompetencyID, getSurvey_ID(),
									iNewCompetencyLevel);
						} else {
							addCompetency(CompID, getSurvey_ID(), db_CompLevel);
						}

					} else {
						addCompetency(CompID, getSurvey_ID(), db_CompLevel);
					}
				}
				/*---------------------------------------- END Check for competency -----------------------------------*/

				/*---------------------------------------- START Check for key behaviour ------------------------------*/

				SurveyKB SKB = new SurveyKB();

				Vector vKB = SKB.getSurveyKB(SurveyID);

				for (int j = 0; j < vKB.size(); j++) {
					votblSurveyBehaviour voKB = (votblSurveyBehaviour) vKB
							.elementAt(j);
					int db_CompID = voKB.getCompetencyID();
					int KBID = voKB.getKeyBehaviourID();

					if (FKOrg != OrgID) {
						int db_NewCompetency = iNewCompetencyID;
						String sql_KB = "SELECT * FROM KeyBehaviour WHERE PKKeyBehaviour= "
								+ KBID + " AND IsSystemGenerated=0";
						rs_KB = st.executeQuery(sql_KB);
						if (rs_KB.next()) {
							String KBName = rs_KB.getString("KeyBehaviour");
							int db_KBLevel = rs_KB.getInt("KBLevel");

							KB.addRecord(db_NewCompetency, KBName, db_KBLevel,
									FKCompanyID, OrgID, PKUser, 2);

							String sql_KB2 = "SELECT * FROM KeyBehaviour WHERE FKCompetency= '"
									+ iNewCompetencyID + "'";
							sql_KB2 = sql_KB2 + " AND KeyBehaviour = '" + KBName
									+ "' AND KBLevel= " + db_KBLevel
									+ "  AND FKCompanyID= " + FKCompanyID
									+ " AND FKOrganizationID = " + OrgID;

							rs_KB2 = st.executeQuery(sql_KB2);
							if (rs_KB2.next())
								NewKB = rs_KB2.getInt("PKKeyBehaviour");

							addKeyBehaviour(getSurvey_ID(), db_NewCompetency, NewKB);
						} else {
							addKeyBehaviour(getSurvey_ID(), db_CompID, KBID);
						}

					} else {
						addKeyBehaviour(getSurvey_ID(), db_CompID, KBID);
					}
				}

				/*---------------------------------------- END Check for key behaviour -----------------------------------*/

			} else{ //using cluster
				SurveyCluster SClust = new SurveyCluster();
				SurveyCompetency SC = new SurveyCompetency();
				SurveyKB SKB = new SurveyKB();

				Vector vCluster = SClust.getSurveyCluster(SurveyID);
				for (int i = 0; i < vCluster.size(); i++) {
					votblSurveyCluster vo = (votblSurveyCluster) vCluster.elementAt(i);

					int ClusterID = vo.getClusterID();

					if (FKOrg != OrgID) {
						String sql_Cluster = "SELECT * FROM Cluster WHERE PKCluster= "	+ ClusterID;
						rs_Cluster = st.executeQuery(sql_Cluster);
						if (rs_Cluster.next()) {
							String ClusterName = rs_Cluster.getString("ClusterName");

							cluster.insertCluster(ClusterName, OrgID, PKUser);

							String sql_Cluster2 = "SELECT * FROM Cluster WHERE ClusterName= '"+ ClusterName + "'";
							sql_Cluster2 = sql_Cluster2	+ " AND FKCompanyID= " + FKCompanyID+ " AND FKOrganizationID = " + OrgID;

							rs_Cluster2 = st.executeQuery(sql_Cluster2);
							if (rs_Cluster2.next()) {
								iNewClusterID = rs_Cluster2.getInt("PKCluster");
							}

							addCluster(iNewCompetencyID, getSurvey_ID());
						} else {
							addCluster(ClusterID, getSurvey_ID());
						}

					} else {
						addCluster(ClusterID, getSurvey_ID());
					}

					Vector vComp = SC.getSurveyClusterCompetency(SurveyID,ClusterID);
					for (int j = 0; j < vComp.size(); j++) {
						votblSurveyCompetency voComp = (votblSurveyCompetency) vComp.elementAt(j);

						int db_CompLevel = voComp.getCompetencyLevel();
						int CompID = voComp.getCompetencyID();

						if (FKOrg != OrgID) {
							String sql_Competency = "SELECT * FROM Competency WHERE PKCompetency= "+ CompID + " AND IsSystemGenerated=0";
							rs_Comp = st.executeQuery(sql_Competency);
							if (rs_Comp.next()) {
								String compName = rs_Comp.getString("CompetencyName");
								String compDef = rs_Comp.getString("CompetencyDefinition");

								competency.addRecord(compName, compDef, FKCompanyID,OrgID, PKUser, 2);

								String sql_Competency2 = "SELECT * FROM Competency WHERE CompetencyName= '"
										+ compName + "'";
								sql_Competency2 = sql_Competency2
										+ " AND FKCompanyID= " + FKCompanyID
										+ " AND FKOrganizationID = " + OrgID;

								rs_Comp2 = st.executeQuery(sql_Competency2);
								if (rs_Comp2.next()) {
									iNewCompetencyID = rs_Comp2.getInt("PKCompetency");
									iNewCompetencyLevel = rs_Comp2
											.getInt("CompetencyLevel");
								}

								addCompetency(iNewCompetencyID, getSurvey_ID(),iNewCompetencyLevel, ClusterID);
							} else {
								addCompetency(CompID, getSurvey_ID(), db_CompLevel, ClusterID);
							}

						} else {
							addCompetency(CompID, getSurvey_ID(), db_CompLevel, ClusterID);
						}
					}

					Vector vKB = SKB.getSurveyClusterKB(SurveyID, ClusterID);
					for (int j = 0; j < vKB.size(); j++) {
						votblSurveyBehaviour voKB = (votblSurveyBehaviour) vKB.elementAt(j);
						int db_CompID = voKB.getCompetencyID();
						int KBID = voKB.getKeyBehaviourID();

						if (FKOrg != OrgID) {
							int db_NewCompetency = iNewCompetencyID;
							String sql_KB = "SELECT * FROM KeyBehaviour WHERE PKKeyBehaviour= "+ KBID + " AND IsSystemGenerated=0";
							rs_KB = st.executeQuery(sql_KB);
							if (rs_KB.next()) {
								String KBName = rs_KB.getString("KeyBehaviour");
								int db_KBLevel = rs_KB.getInt("KBLevel");

								KB.addRecord(db_NewCompetency, KBName, db_KBLevel,FKCompanyID, OrgID, PKUser, 2);

								String sql_KB2 = "SELECT * FROM KeyBehaviour WHERE FKCompetency= '"
										+ iNewCompetencyID + "'";
								sql_KB2 = sql_KB2 + " AND KeyBehaviour = '" + KBName
										+ "' AND KBLevel= " + db_KBLevel
										+ "  AND FKCompanyID= " + FKCompanyID
										+ " AND FKOrganizationID = " + OrgID;

								rs_KB2 = st.executeQuery(sql_KB2);
								if (rs_KB2.next())
									NewKB = rs_KB2.getInt("PKKeyBehaviour");

								addKeyBehaviour(getSurvey_ID(), db_NewCompetency, NewKB,ClusterID);
							} else {
								addKeyBehaviour(getSurvey_ID(), db_CompID, KBID, ClusterID);
							}

						} else {
							addKeyBehaviour(getSurvey_ID(), db_CompID, KBID, ClusterID);
						}
					}
				}

			}
			/*---------------------------------------- START Check for demographic ------------------------------*/

			String sql_SurveyDemos = "SELECT * FROM tblSurveyDemos WHERE SurveyID="
					+ SurveyID;

			rs_SurvDemos = st.executeQuery(sql_SurveyDemos);
			while (rs_SurvDemos.next()) {
				String db_DemoID = rs_SurvDemos.getString("DemographicID");

				addDemos(getSurvey_ID(), db_DemoID);
			}

			/*---------------------------------------- END Check for demographic -----------------------------------*/

			/*---------------------------------------- START Check for RatingTask ------------------------------*/

			String sql_SurveyRating = "SELECT * FROM tblSurveyRating WHERE SurveyID="
					+ SurveyID;

			rs_Rating = st.executeQuery(sql_SurveyRating);
			while (rs_Rating.next()) {
				String db_RatingTaskID = rs_Rating.getString("RatingTaskID");
				String db_ScaleID = rs_Rating.getString("ScaleID");
				String db_RatingTaskName = rs_Rating
						.getString("RatingTaskName");
				int ScaleID = Integer.parseInt(db_ScaleID);

				if (FKOrg != OrgID) {
					String sql_Scale = "SELECT * FROM tblScale WHERE ScaleID= "
							+ ScaleID + " AND IsSystemGenerated=0";
					rs_Scale = st.executeQuery(sql_Scale);
					if (rs_Scale.next()) {
						String ScaleName = rs_Scale
								.getString("ScaleDescription");
						String ScaleType = rs_Scale.getString("ScaleType");
						int defaultValue = rs_Scale.getInt("ScaleDefault");
						int ScaleRange = rs_Scale.getInt("ScaleRange");

						scale.addtblScale(ScaleName, ScaleType, defaultValue,
								FKCompanyID, OrgID, ScaleRange, PKUser, 2);

						String sql_Scale2 = "Select * from tblScale ";
						sql_Scale2 = sql_Scale2 + "where ScaleDescription = '"
								+ ScaleName + "' and ScaleType = '" + ScaleType;
						sql_Scale2 = sql_Scale2 + "' and FKCompanyID = "
								+ FKCompanyID + " and FKOrganizationID = "
								+ OrgID;

						rs_Scale2 = st.executeQuery(sql_Scale2);
						if (rs_Scale2.next())
							NewScaleID = rs_Scale2.getString("ScaleID");

						addRating(getSurvey_ID(), NewScaleID, db_RatingTaskID,
								db_RatingTaskName);
					} else {
						addRating(getSurvey_ID(), db_ScaleID, db_RatingTaskID,
								db_RatingTaskName);
					}

				} else {
					addRating(getSurvey_ID(), db_ScaleID, db_RatingTaskID,
							db_RatingTaskName);
				}
			}

		} catch (SQLException ex) {
			System.out.println("Create_Edit_Survey.java - CopySurvey - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs_KB); // Close ResultSet
			ConnectionBean.closeRset(rs_KB2);
			ConnectionBean.closeRset(rs_SurvDemos);
			ConnectionBean.closeRset(rs_Rating);
			ConnectionBean.closeRset(rs_Scale);
			ConnectionBean.closeRset(rs_Scale2);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		/*---------------------------------------- END Check for RatingTask -----------------------------------*/

	}

	/*----------------------------------------END: Copy Survey---------------------------------------------*/

	/*-------------------------------------User detail--------------------------------------------*/

	/**
	 * Extract the User's Detail
	 * 
	 * 
	 * Parameters - int PKUser
	 * 
	 */

	public String[] getUserDetail(int PKUser) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sDetail[] = new String[16];
		String sql = "SELECT * FROM [User] a, Department b, Division c, UserType d, [Group] e, tblOrganization f, tblConsultingCompany g";
		sql = sql
				+ " WHERE a.FKDepartment = b.PKDepartment AND a.FKDivision= c.PKDivision AND a.FKUserType360 = d.PKUserType";
		sql = sql + " AND a.Group_Section = e.PKGroup AND a.PKUser = " + PKUser;
		sql = sql
				+ " AND a.FKOrganization = f.PKOrganization AND a.FKCompanyID = g.CompanyID";

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs != null && rs.next()) {
				/* User */

				sDetail[0] = rs.getString("FamilyName");
				sDetail[1] = rs.getString("GivenName");
				sDetail[2] = rs.getString("LoginName");
				sDetail[3] = rs.getString("Designation");
				sDetail[4] = rs.getString("IDNumber");
				sDetail[5] = rs.getString("IsEnabled");
				sDetail[12] = rs.getString("Email");
				sDetail[6] = rs.getString("DepartmentName");
				sDetail[7] = rs.getString("DivisionName");
				sDetail[8] = rs.getString("UserTypeName");
				sDetail[9] = rs.getString("GroupName");
				sDetail[10] = rs.getString("OrganizationName");
				sDetail[11] = rs.getString("CompanyName");
				/*
				 * Change : include the id into the details
				 * Reason : because many duplicate name "NA"
				 * Add by : Johanes
				 * Add on : 26/10/2009
				 */	
				sDetail[13] = rs.getString("FKDepartment");
				sDetail[14] = rs.getString("FKDivision");
				sDetail[15] = rs.getString("Group_section");
			}

		} catch (Exception E) {

			System.err.println("Create_Edit_Survey .java - getUserDetail - "
					+ E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return sDetail;
	}

	/**
	 * Extract the User's Detail including the round
	 * 
	 * 
	 * Parameters - int PKUser
	 * 
	 */

	public String[] getUserDetailWithRound(int PKUser) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sDetail[] = new String[17];
		String sql = "SELECT * FROM [User] a, Department b, Division c, UserType d, [Group] e, tblOrganization f, tblConsultingCompany g";
		sql = sql
				+ " WHERE a.FKDepartment = b.PKDepartment AND a.FKDivision= c.PKDivision AND a.FKUserType360 = d.PKUserType";
		sql = sql + " AND a.Group_Section = e.PKGroup AND a.PKUser = " + PKUser;
		sql = sql
				+ " AND a.FKOrganization = f.PKOrganization AND a.FKCompanyID = g.CompanyID";

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs != null && rs.next()) {
				/* User */

				sDetail[0] = rs.getString("FamilyName");
				sDetail[1] = rs.getString("GivenName");
				sDetail[2] = rs.getString("LoginName");
				sDetail[3] = rs.getString("Designation");
				sDetail[4] = rs.getString("IDNumber");
				sDetail[5] = rs.getString("IsEnabled");
				sDetail[12] = rs.getString("Email");
				sDetail[6] = rs.getString("DepartmentName");
				sDetail[7] = rs.getString("DivisionName");
				sDetail[8] = rs.getString("UserTypeName");
				sDetail[9] = rs.getString("GroupName");
				sDetail[10] = rs.getString("OrganizationName");
				sDetail[11] = rs.getString("CompanyName");
				/*
				 * Change : include the id into the details
				 * Reason : because many duplicate name "NA"
				 * Add by : Johanes
				 * Add on : 26/10/2009
				 */	
				sDetail[13] = rs.getString("FKDepartment");
				sDetail[14] = rs.getString("FKDivision");
				sDetail[15] = rs.getString("Group_section");
				sDetail[16] = rs.getString("Round");
			}

		} catch (Exception E) {

			System.err.println("Create_Edit_Survey .java - getUserDetail - "
					+ E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return sDetail;
	}
	/*------------------------------End User Detail---------------------------------*/

	/*-------------------------------------User Info----------------------------------------*/
	public String[] getUserInfo(String userNameQuoted, String sOrgLogo)
			throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sDetail[] = new String[9];
		String sql = "SELECT * FROM [User] a, tblConsultingCompany b, tblOrganization c WHERE a.LoginName ="
				+ userNameQuoted
				+ " AND a.FKUserType360 != 14 AND a.FKCompanyID = b.CompanyID AND a.FKOrganization = c.PKOrganization and OrganizationCode = '"
				+ sOrgLogo + "'";


		try {
			con = ConnectionBean.getConnection();

			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs != null && rs.next()) {
				/* User */

				sDetail[0] = Integer.toString(rs.getInt("PKUser"));
				sDetail[1] = rs.getString("LoginName");
				sDetail[2] = rs.getString("Password");
				sDetail[3] = Integer.toString(rs.getInt("IsEnabled"));
				sDetail[4] = Integer.toString(rs.getInt("CompanyID"));
				sDetail[5] = rs.getString("CompanyName");
				sDetail[6] = Integer.toString(rs.getInt("PKOrganization"));
				sDetail[7] = rs.getString("OrganizationName");
				sDetail[8] = rs.getString("OrganizationCode");
			}

		} catch (Exception E) {

			System.err.println("Create_Edit_Survey.java - getUserInfo - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return sDetail;
	}

	/*------------------------------------------------end userInfo--------------------------------------*/

	/*-----------------------------------------START: allow change in a Survey-----------------------------*/

	/**
	 * Check if a survey is allowed to be edited.
	 * 
	 * 
	 * Parameters - int SurveyID
	 * 
	 */

	public boolean Allow_SurvChange(int SurveyID) throws SQLException,
	Exception {	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		boolean bSurvChange = true;
		Calculation C = new Calculation();

		int surveyLevel = C.LevelOfSurvey(SurveyID);
		String sTB = "tblResultCompetency";

		if (surveyLevel == 1)
			sTB = "tblResultBehaviour";

		// Check whether any rater had "completed" questionnaire
		// String sql = "SELECT * FROM tblAssignment a, tblSurvey b WHERE
		// a.SurveyID = b.SurveyID AND b.SurveyID = "+SurveyID+" AND
		// a.RaterStatus = 1 OR b.SurveyID = "+SurveyID+" AND b.SurveyStatus =
		// 2";

		// (rianto) 24-Nov-05: Can't just check for "completed" status, will
		// create data inconsistency
		// Check if any rater has started rating (eventhough has not
		// "Completed")
		String sql = "SELECT * FROM " + sTB + " INNER JOIN tblAssignment ON ";
		sql += sTB + ".AssignmentID = tblAssignment.AssignmentID INNER JOIN ";
		sql += "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID WHERE (tblAssignment.SurveyID = "
				+ SurveyID + ")";

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				bSurvChange = false;
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - Allow_SurvChange - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return bSurvChange;
	}

	/*-----------------------------------------END: allow change in a Survey-------------------------------*/

	/**
	 * For each deleted survey the system will captured all the necessary
	 * information related and stored it.
	 * 
	 * 
	 * Parameters - int SurveyID, String filename, int FKOrganization, int
	 * FKCompanyID, int PKUser
	 * 
	 */
	public boolean addDeletedSurvey(int SurveyID, String filename,
			int FKOrganization, int FKCompanyID, int PKUser)
					throws SQLException, Exception {
		return addDeletedSurvey(SurveyID, filename, FKOrganization, FKCompanyID, PKUser, null);
	}

	/**
	 * Added by Xuehai, 09 Jun 2011. 
	 * Provide option to choose whether clear user data when deleting a survey.
	 * 
	 * @param clearUserData 
	 */
	public boolean addDeletedSurvey(int SurveyID, String filename,
			int FKOrganization, int FKCompanyID, int PKUser, String clearUserData)
					throws SQLException, Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		boolean bIsAdded = false;
		if (setting.getallowDeleteFunc()) {

			String Survey_Name = "";

			votblSurvey vo = getSurveyDetail(SurveyID);
			if (vo != null) {

				Survey_Name = vo.getSurveyName();
			}

			// db.openDB();
			String sql = "INSERT INTO tblDeletedSurvey (SurveyName,filename,FKOrganization,FKCompanyID)";
			sql = sql + " VALUES('" + Survey_Name + "', '" + filename + "', "
					+ FKOrganization + ", " + FKCompanyID + ")";
			/*
			 * PreparedStatement ps = db.con.prepareStatement(sql);
			 * ps.executeUpdate();
			 * 
			 * db.closeDB();
			 */
			try {
				con = ConnectionBean.getConnection();
				st = con.createStatement();
				int iSuccess = st.executeUpdate(sql);

				String sql1 = "SELECT * FROM tblDeletedSurvey WHERE SurveyName = '"
						+ Survey_Name + "'";

				rs = st.executeQuery(sql1);
				if (rs.next()) {
					int DeletedSurveyID = rs.getInt("DeletedSurveyID");

					/*
					 * to update the tbldeletedassignment SurveyID with a new
					 * surveyid
					 */

					String sql2 = "UPDATE tblDeletedAssignment SET SurveyID = "
							+ DeletedSurveyID
							+ ", IsDeletedSurvey = 1 WHERE SurveyID = "
							+ SurveyID;
					ps1 = con.prepareStatement(sql2);
					ps1.executeUpdate();
				}
				if (iSuccess != 0)
					bIsAdded = true;

			} catch (Exception ex) {
				System.out
				.println(" Create_Edit_Survey.java -addDeletedSurvey - "
						+ ex.getMessage());
			} finally {
				ConnectionBean.closePStmt(ps1);
				ConnectionBean.closeRset(rs); // Close ResultSet
				ConnectionBean.closeStmt(st); // Close statement
				ConnectionBean.close(con); // Close connection

			}

			/* To take the new survey id */
			/*
			 * db.openDB();
			 * 
			 * 
			 * Statement stmt = db.con.createStatement(); ResultSet rs =
			 * stmt.executeQuery(sql1);
			 */

		}

		delSurvey(SurveyID, PKUser, clearUserData);
		return bIsAdded;
	}

	public String[] getUserDetail_ADV(int PKUser) throws SQLException,
	Exception {
		// modified by Jenty on 18th March 2005
		// SQL Statement didn't link up User table with Org and Comp table.

		// db.openDB();
		int NameSequence = 0;
		String sDetail[] = new String[14];
		// String sql = "SELECT * FROM tblOrganization f, [User] a, Department
		// b, Division c, UserType d, [Group] e, tblConsultingCompany g";
		// sql = sql +" WHERE a.FKDepartment = b.PKDepartment AND a.FKDivision=
		// c.PKDivision AND a.FKUserType360 = d.PKUserType";
		// sql = sql +" AND a.Group_Section = e.PKGroup AND a.PKUser = "+PKUser;

		String sql = "SELECT * FROM Department b INNER JOIN [User] a ON ";
		sql = sql
				+ "b.PKDepartment = a.FKDepartment INNER JOIN Division c ON a.FKDivision = c.PKDivision INNER JOIN ";
		sql = sql
				+ "UserType d ON a.FKUserType360 = d.PKUserType INNER JOIN [Group] e ON ";
		sql = sql
				+ "a.Group_Section = e.PKGroup INNER JOIN tblConsultingCompany g ON ";
		sql = sql
				+ "a.FKCompanyID = g.CompanyID INNER JOIN tblOrganization f ON a.FKOrganization = f.PKOrganization ";
		sql = sql + "WHERE a.PKUser = " + PKUser;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {


			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			String FamilyName;

			if (rs.next()) {
				// User
				String OrgName = rs.getString("OrganizationName");
				NameSequence = rs.getInt("NameSequence");

				FamilyName = rs.getString("FamilyName");
				String OtherName = rs.getString("GivenName");

				if (NameSequence == 0) {
					sDetail[0] = FamilyName;
					sDetail[1] = OtherName;
				} else if (NameSequence == 1) {
					sDetail[0] = OtherName;
					sDetail[1] = FamilyName;
				}

				sDetail[2] = rs.getString("LoginName");
				sDetail[3] = rs.getString("Designation");
				sDetail[4] = rs.getString("IDNumber");
				sDetail[12] = rs.getString("Password");
				sDetail[5] = rs.getString("IsEnabled");
				sDetail[13] = rs.getString("Email");
				sDetail[6] = rs.getString("DepartmentName");
				sDetail[7] = rs.getString("DivisionName");
				sDetail[8] = rs.getString("UserTypeName");
				sDetail[9] = rs.getString("GroupName");
				sDetail[10] = OrgName;
				sDetail[11] = rs.getString("CompanyName");
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - getUserDetail_ADV - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return sDetail;
	}

	/**
	 * Retrieves all Clusters based on Company and Organization ID.
	 */
	public Vector FilterRecordCluster(int OrgID, int SurveyID) throws SQLException, Exception {
		Vector v = new Vector();

		String query = "SELECT * FROM Cluster WHERE";
		query += "(Cluster.FKOrganization = " + OrgID+ ") AND (Cluster.PKCluster NOT IN ";
		query += "(SELECT ClusterID FROM tblSurveyCluster WHERE SurveyID = "+ SurveyID + ")) ORDER BY ";

		if (SortType == 1)
			query = query + "ClusterName";

		if (Toggle[SortType - 1] == 1)
			query = query + " DESC";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voCluster vo = new voCluster();
				vo.setClusterName(rs.getString("ClusterName"));
				vo.setClusterID(rs.getInt("PKCluster"));
				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("SurveyCompetency.java - filterRecordCluster - "+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

	/**
	 * Retrieves all Competencies based on Company and Organization ID and Clusters.
	 */
	public Vector FilterRecord(int OrgID, int SurveyID, int ClusterID) throws SQLException,Exception {

		Vector v = new Vector();

		String query = "SELECT * FROM Competency INNER JOIN tblOrgCluster ON Competency.PKCompetency = tblOrgCluster.FKCompetency INNER JOIN tblOrigin ON Competency.IsSystemGenerated = tblOrigin.PKIsSystemGenerated WHERE ";
		query += "(tblOrgCluster.FKCluster = "+ClusterID+") AND (Competency.PKCompetency NOT IN ";
		query += "(SELECT CompetencyID FROM tblSurveyCompetency WHERE (SurveyID = "+ SurveyID + " AND ClusterID = "+ClusterID+"))) ORDER BY ";

		if (SortType == 1)
			query = query + "CompetencyName";
		else if (SortType == 2)
			query = query + "CompetencyDefinition";
		else if (SortType == 3)
			query = query + "IsSystemGenerated";

		if (Toggle[SortType - 1] == 1)
			query = query + " DESC";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voCompetency vo = new voCompetency();
				vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setOrigin(rs.getString("Description"));

				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("SurveyCompetency.java - FilterRecord - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

	/**
	 * Retrieves all Competencies based on Company and Organization ID.
	 */
	public Vector FilterRecord(int OrgID, int SurveyID) throws SQLException,
	Exception {

		Vector v = new Vector();

		String query = "SELECT * FROM Competency INNER JOIN tblOrigin ON Competency.IsSystemGenerated = ";
		query = query
				+ "tblOrigin.PKIsSystemGenerated WHERE (Competency.IsSystemGenerated = 1) ";
		query = query + "AND (Competency.PKCompetency NOT IN ";
		query = query
				+ "(SELECT CompetencyID FROM tblSurveyCompetency WHERE SurveyID = "
				+ SurveyID + "))  OR ";
		query = query + "(Competency.FKOrganizationID = " + OrgID
				+ ") AND (Competency.PKCompetency NOT IN ";
		query = query
				+ "(SELECT CompetencyID FROM tblSurveyCompetency WHERE SurveyID = "
				+ SurveyID + ")) ORDER BY ";

		if (SortType == 1)
			query = query + "CompetencyName";
		else if (SortType == 2)
			query = query + "CompetencyDefinition";
		else if (SortType == 3)
			query = query + "IsSystemGenerated";

		if (Toggle[SortType - 1] == 1)
			query = query + " DESC";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				voCompetency vo = new voCompetency();
				vo
				.setCompetencyDefinition(rs
						.getString("CompetencyDefinition"));
				vo.setCompetencyName(rs.getString("CompetencyName"));
				vo.setCompetencyID(rs.getInt("PKCompetency"));
				vo.setOrigin(rs.getString("Description"));

				v.add(vo);

			}

		} catch (SQLException SE) {
			System.out.println("SurveyCompetency.java - FilterRecord - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;
	}

	/** by Hemilda Date 17/08/2008
	 * Method used for executing a static SQL statement and returning the
	 * results it produces. Results produced will be stored in ResultSet. return
	 * vector of votblSurvey
	 * to get the list of open survey for all org with status open
	 * for timer calculation
	 */
	public Vector getRecord_Survey() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			String query = "SELECT  * FROM   tblOrganization ,tblSurvey,tblConsultingCompany " +
					"WHERE  tblOrganization.PKOrganization = tblSurvey.FKOrganization AND tblOrganization.FKCompanyID = tblSurvey.FKCompanyID and "+
					" tblSurvey.FKCompanyID = tblConsultingCompany.CompanyID and "+
					"	    (tblSurvey.SurveyStatus = 1) "+
					" ORDER BY tblOrganization.PKOrganization, tblOrganization.FKCompanyID, tblSurvey.LevelOfSurvey ";
			/*
			 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet
			 * rs = stmt.executeQuery(query);
			 * 
			 */
			//System.out.println(query);
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				votblSurvey vo = new votblSurvey();
				vo.setSurveyID(rs.getInt("SurveyID"));
				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setCompanyName(rs.getString("CompanyName"));
				vo.setOrganizationName(rs.getString("OrganizationName"));
				vo.setDateOpened(Utils.convertDateFormat(rs.getDate("DateOpened")));
				vo.setDeadlineSubmission(Utils.convertDateFormat(rs.getDate("DeadlineSubmission")));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setOrganizationName(rs.getString("OrganizationName"));

				v.add(vo);
			}
		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getRecord_Survey - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return v;
	}

	/**
	 * Method used for executing a static SQL statement and returning the
	 * results it produces. Results produced will be stored in ResultSet. return
	 * vector of votblSurvey
	 */
	public Vector getRecord_Survey(int iCompanyID, int iOrgID,
			int iJobPositionID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			String query = "SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization ";

			if (iOrgID != 0)
				query = query + " AND a.FKOrganization = " + iOrgID;
			else if(iCompanyID != 0)
				query = query + "	AND a.FKCompanyID = " + iCompanyID;

			if (iJobPositionID != 0)
				query = query + " AND a.JobPositionID = " + iJobPositionID;

			int SortType = getSortType();
			query = query + " ORDER BY ";

			if (SortType == 1)
				query = query + "SurveyName";
			else if (SortType == 2)
				query = query + "DateOpened";
			else if (SortType == 3)
				query = query + "DeadlineSubmission";
			else if (SortType == 4)
				query = query + "AnalysisDate";
			else if (SortType == 5)
				query = query + "SurveyStatus";
			else if (SortType == 6)
				query = query + "OrganizationName";

			if (Toggle[SortType - 1] == 1)
				query = query + " DESC";
			/*
			 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet
			 * rs = stmt.executeQuery(query);
			 * 
			 */

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				votblSurvey vo = new votblSurvey();
				vo.setSurveyID(rs.getInt("SurveyID"));
				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setDateOpened(Utils.convertDateFormat(rs
						.getDate("DateOpened")));
				vo.setDeadlineSubmission(Utils.convertDateFormat(rs
						.getDate("DeadlineSubmission")));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setOrganizationName(rs.getString("OrganizationName"));

				v.add(vo);
			}
		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getRecord_Survey - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return v;
	}

	/**
	 * Method used for executing a static SQL statement and returning the
	 * results it produces. Results produced will be stored in ResultSet. return
	 * vector of votblSurvey
	 */
	public Vector getRecord_Survey(int iPKUser, int iJobPos_ID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			String query = "SELECT DISTINCT tblSurvey.SurveyID, tblSurvey.SurveyName, DateOpened, tblSurvey.DeadlineSubmission, SurveyStatus, AnalysisDate, tblOrganization.OrganizationName ";
			query += "FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
			query += "tblUserRelation ON tblAssignment.TargetLoginID = tblUserRelation.User1 ";
			query += " INNER JOIN tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization WHERE ";
			// Modified by DeZ, 21.07.08, fix problem where Opened survey not shown when superior nominates rater where subordinate is target
			query += " tblUserRelation.User2 = " + iPKUser
					+ " AND (tblSurvey.SurveyStatus = 3 OR tblSurvey.SurveyStatus = 1)";
			if (iJobPos_ID != 0)
				query = query + " AND JobPositionID = " + iJobPos_ID;
			/*
			 * db.openDB(); Statement stmt = db.con.createStatement(); ResultSet
			 * rs = stmt.executeQuery(query);
			 * 
			 */
			//System.out.println(">>sql = " + query);
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				votblSurvey vo = new votblSurvey();
				vo.setSurveyID(rs.getInt("SurveyID"));
				vo.setSurveyName(rs.getString("SurveyName"));
				vo.setDateOpened(Utils.convertDateFormat(rs
						.getDate("DateOpened")));
				vo.setDeadlineSubmission(Utils.convertDateFormat(rs
						.getDate("DeadlineSubmission")));
				vo.setSurveyStatus(rs.getInt("SurveyStatus"));
				vo.setAnalysisDate(rs.getString("AnalysisDate"));
				vo.setOrganizationName(rs.getString("OrganizationName"));

				v.add(vo);
			}
		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getRecord_Survey - "
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return v;
	}

	/**
	 * Insert a lower and upper limit for either Gap or CP Range in tblSurvey based on SurveyID
	 * @param SurveyID
	 * @param minPassScore
	 * @author Kian Hwee
	 * @since Ver.1.3.12.62 (02 MAR 2010)
	 **/
	public boolean setMinPassScore(int SurveyID, double lowerLimit, double upperLimit)
	{
		//Add one more attribute -- upperCPScore, set MinPassScore to 0.0 as default
		//Insert lowerCPScore and upperCPScore to Min_Gap and Max_Gap column
		//Mark Oei 16 April 2010
		String sql = "UPDATE tblSurvey SET Min_Gap="+lowerLimit+", Max_Gap="+upperLimit;
		sql +=	", minPassScore="+lowerLimit+ " WHERE SurveyID="+SurveyID+"";

		Connection con = null;
		Statement st = null;
		boolean isSuccessful = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0) {
				isSuccessful = true;
			}

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - setMinPassScore - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}		
		return isSuccessful;
	}// End of setMinPassScore()
	/**
	 * Retrieve Minimum Pass Score from table tblSurvey base on SurveyID
	 * @param SurveyID
	 * @author Kian Hwee
	 * @since Ver.1.3.12.62 (02 MAR 2010)
	 **/
	public double getMinPassScore(int SurveyID)
	{
		String sql = "SELECT minPassScore FROM tblSurvey WHERE SurveyID='"+SurveyID+"'";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		double currentMinPassScore = 0;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			currentMinPassScore=rs.getDouble("minPassScore");

		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - getMinPassScore - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return currentMinPassScore;
	}// End of getMinPassScore()

	/*------------------------------------- This get/set Only rubbish-------------------------------*/
	public void setJobPos_ID(int JobPos_ID) {
		this.JobPos_ID = JobPos_ID;
	}

	public int getJobPos_ID() {
		return JobPos_ID;
	}

	public void setSurveyStatus(int SurveyStatus) {
		this.SurveyStatus = SurveyStatus;
	}

	public int getSurveyStatus() {
		return SurveyStatus;
	}

	public void setPurpose(int Purpose) {
		this.Purpose = Purpose;
	}

	public int getPurpose() {
		return Purpose;
	}

	public void set_survOrg(int survOrg) {
		this.survOrg = survOrg;
	}

	public int get_survOrg() {
		return survOrg;
	}

	/*------------------------------------------	end of rubbish-----------------------	*/

	/** To capture all the necessary beans for the system */

	public void setSurvey_ID(int Survey_ID) {
		this.Survey_ID = Survey_ID;
	}

	public int getSurvey_ID() {
		return Survey_ID;
	}

	public void set_DivID(int DivID) {
		this.DivID = DivID;
	}

	public int get_DivID() {
		return DivID;
	}

	public void set_DeptID(int DeptID) {
		this.DeptID = DeptID;
	}

	public int get_DeptID() {
		return DeptID;
	}

	public void setCompetencyLevel(int CompetencyLevel) {
		this.CompetencyLevel = CompetencyLevel;
	}

	public int getCompetencyLevel() {
		return CompetencyLevel;
	}

	public void setUseCluster(int useCluster) {
		this.useCluster = useCluster;
	}

	public int getUseCluster() {
		return useCluster;
	}

	public void set_SurvRating(int SurvRating) {
		this.SurvRating = SurvRating;
	}

	public int get_SurvRating() {
		return SurvRating;
	}

	public void set_GroupID(int GroupID) {
		this.GroupID = GroupID;
	}

	public int get_GroupID() {
		return GroupID;
	}

	public void set_CompLevel(int CompLevel) {
		this.CompLevel = CompLevel;
	}

	public int get_CompLevel() {
		return CompLevel;
	}



	public static void main(String[] args) throws SQLException, Exception {
		Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
		// String MonthYear, String DateOpened, String DeadlineSubmission,
		// int SurveyStatus, String AnalysisDate, int SurveyID, String Min_Gap,
		// String Max_Gap,
		// int PKUser
		// float max = 1;
		// float min = -1;

		try {
			CE_Survey.updateRecord_AfterRaterComplete("NA", " ",
					1, "10/26/2003", 436, 1070);
		} catch (SQLException SE) {
			System.out.println(SE);
		}
	}

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
		return Toggle[SortType - 1];
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

	public void setRatScale(int RatScale) {
		this.RatScale = RatScale;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getRatScale() {
		return RatScale;
	}

	public void setJobCat(int jobCat) {
		JobCat = jobCat;
	}

	public int getJobCat() {
		return JobCat;
	}

	public Vector getSurveys(int iFKcomp, int iFKOrg) {
		String query = "SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization ";

		if (iFKOrg != 0)
			query = query + " AND a.FKOrganization = " + iFKOrg;
		else
			query = query + "	AND a.FKCompanyID = " + iFKcomp;

		query += " order by SurveyName";

		// int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		// String Surv_Name = rs_SurveyDetail.getString("SurveyName");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				votblSurvey vo = new votblSurvey();

				vo.setSurveyID(rs.getInt("SurveyID"));
				vo.setSurveyName(rs.getString("SurveyName"));
				v.add(vo);
			}

		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getSurveys-"
					+ SE.getMessage());

		} finally {
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}


		return v;

	}

	public Vector getTargets(int iTargetID) {

		String query = "SELECT * FROM [User] b, tblOrganization c";
		query = query
				+ " WHERE b.FKOrganization = c.PKOrganization AND PKUser = "
				+ iTargetID;

		// int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		// String Surv_Name = rs_SurveyDetail.getString("SurveyName");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				votblOrganization vo = new votblOrganization();
				vo.setNameSequence(rs.getInt("NameSequence"));
				v.add(vo);
			}

		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getTargets -"
					+ SE.getMessage());

		} finally {
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;

	}

	/**
	 * Get Division
	 * 
	 * @param iFKOrg
	 * @return
	 * @author James
	 */
	public Vector getAllDivisions(int iSurveyID) {

		String command1 = "SELECT DISTINCT Division.PKDivision, Division.DivisionName FROM Division INNER JOIN ";
		command1 += "tblAssignment ON Division.PKDivision = tblAssignment.FKTargetDivision WHERE ";
		command1 += "(tblAssignment.SurveyID = " + iSurveyID
				+ ") ORDER BY Division.DivisionName";

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command1);

			while (rs.next()) {
				voDivision vo = new voDivision();

				vo.setPKDivision(rs.getInt("PKDivision"));
				vo.setDivisionName(rs.getString("DivisionName"));

				v.add(vo);
			}

		} catch (SQLException SE) {
			System.err.println("Create_Edit_Survey.java - getAllDivisions -"
					+ SE.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * 
	 * @param iFKOrg
	 * @return Vector of voJob
	 * @author Thant Thura Myo
	 * @since 8 Jan 2008
	 * @see Normination_AssignTR.jsp
	 */
	public Vector getJobForOrg(int iFKOrg) {
		Vector v = new Vector();
		String command = "SELECT * FROM tblJobPosition WHERE FKOrganization ="
				+ iFKOrg;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			while (rs.next()) {
				voJob vo = new voJob();
				int JobPosition_ID = rs.getInt("JobPositionID");
				String JobPosition_Desc = rs.getString("JobPosition");
				vo.setJobPositionID(JobPosition_ID);
				vo.setJobPosition(JobPosition_Desc);

				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getJobForOrg - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	public Vector getAllDepartments(int iSurveyID, int iDivID) {
		String command2 = "SELECT DISTINCT Department.PKDepartment, Department.DepartmentName, Department.Location ";
		command2 = command2 + "FROM tblAssignment INNER JOIN Department ON ";
		command2 = command2
				+ "tblAssignment.FKTargetDepartment = Department.PKDepartment ";
		command2 = command2 + "WHERE (tblAssignment.SurveyID = " + iSurveyID
				+ ") ";
		command2 = command2 + "AND Department.FKDivision = " + iDivID + " ";
		command2 = command2 + "ORDER BY Department.DepartmentName ";

		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command2);

			while (rs.next()) {

				voDepartment vo = new voDepartment();
				vo.setPKDepartment(rs.getInt("PKDepartment"));
				vo.setDepartmentName(rs.getString("DepartmentName"));
				vo.setLocation(rs.getString("Location"));

				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getAllDepartments - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	public Vector getAllGroups(int iSurveyID, int iDeptID) {

		String query2 = "SELECT DISTINCT c.PKGroup, c.GroupName FROM tblAssignment a, [User] b, [Group] c WHERE a.RaterLoginID = b.PKUser AND b.Group_Section = c.PKGroup ";
		query2 = query2 + " AND SurveyID = " + iSurveyID;
		query2 = query2 + " AND c.FKDepartment =" + iDeptID;
		query2 = query2 + " ORDER BY c.GroupName";

		// String querySql = "SELECT * FROM [Group] WHERE
		// FKOrganization="+iFKOrg+ " AND FKDepartment =" + iDepID + " ORDER BY
		// GroupName";
		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query2);

			while (rs.next()) {

				voGroup vo = new voGroup();
				vo.setGroupName(rs.getString("GroupName"));
				vo.setPKGroup(rs.getInt("PKGroup"));

				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getAllGroups - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	public Vector getAllUsers(int iSeq, int iSurveyID, int iGroupID) {
		String query1 = "SELECT DISTINCT PKUser, NameSequence, ";
		if (iSeq == 0)
			query1 += " FamilyName + ' ' + GivenName as FullName ";
		else
			query1 += " GivenName + ' ' +  FamilyName as FullName ";

		query1 += " FROM tblAssignment a, [User] b, tblOrganization c";
		query1 += " WHERE b.FKOrganization = c.PKOrganization AND a.TargetLoginID=b.PKUser ";
		query1 += "AND a.SurveyID =" + iSurveyID + " AND a.FKTargetGroup= "
				+ iGroupID;

		query1 += " order by FullName";

		// String sSql="SELECT * FROM UserType WHERE ApplicationType = 2 AND
		// PKUserType != 1 AND ApplicationType = 2 AND PKUserType != 9";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query1);

			while (rs.next()) {
				voUser vo = new voUser();
				vo.setPKUser(rs.getInt("PKUser"));
				vo.setFullName(rs.getString("FullName"));
				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getAllUser- " + E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	public Vector getAllUsers(int iSeq, int iSurveyID) {
		String query1 = "SELECT DISTINCT b.TargetLoginID, PKUser, ";

		String orderBy = "";
		// System.out.println("Sequence = " + seq);
		if (iSeq == 0)
			query1 += " FamilyName + ' ' + GivenName as FullName, ";
		else
			query1 += " GivenName + ' ' +  FamilyName as FullName, ";

		query1 += " NameSequence FROM tblSurvey a, tblAssignment b, [User] c, tblOrganization d WHERE c.FKOrganization = d.PKOrganization AND a.SurveyID = b.SurveyID AND b.TargetLoginID=c.PKUser AND a.SurveyID ="
				+ iSurveyID;
		query1 += " order by FullName";

		// String sSql="SELECT * FROM UserType WHERE ApplicationType = 2 AND
		// PKUserType != 1 AND ApplicationType = 2 AND PKUserType != 9";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query1);

			while (rs.next()) {
				voUser vo = new voUser();
				vo.setPKUser(rs.getInt("PKUser"));
				vo.setFullName(rs.getString("FullName"));
				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getAllUsers- " + E);
		} finally {

			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * get the survey name
	 * 
	 * @param iSurveyID
	 * @return
	 */
	public String getSurveyName(int iSurveyID) {
		String sSurveyName = "";

		String commandx = "SELECT * FROM tblSurvey WHERE SurveyID = "
				+ iSurveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(commandx);

			if (rs.next()) {

				sSurveyName = rs.getString("SurveyName");
			}

		} catch (Exception E) {
			System.err
			.println("Create_Edit_Survey.java - getSurveyName - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return sSurveyName;
	}

	/**
	 * get Survey ID
	 * 
	 * @param iSurveyID
	 * @return
	 */

	//Added by Tracy 25 Aug 08**************************************
	public int getSurveyOrgID(String sNewSurveyName, int sOrgID) {
		int iSurveyID = 0;

		// Edited by Tracy 14 Aug 08, Get survey ID based on survey Name and Organization Name
		String query = "SELECT * FROM tblSurvey WHERE SurveyName = '"
				+ sNewSurveyName + "' and FKOrganization=" + sOrgID + "";
		//*************************************

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {

				iSurveyID = rs.getInt("SurveyID");
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getSurveyOrgID - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iSurveyID;
	} 
	//************************************************************************

	public int getSurveyID(String sNewSurveyName) {
		int iSurveyID = 0;

		// Edited by Tracy 14 Aug 08, Get survey ID based on survey Name and Organization Name
		String query = "SELECT * FROM tblSurvey WHERE SurveyName = '" + sNewSurveyName + "'";
		//*************************************

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {

				iSurveyID = rs.getInt("SurveyID");
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - getSurveyID - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return iSurveyID;
	} 


	/**
	 * check Survey by survery name and organization id.
	 * 
	 * @param surveyname, orgId
	 * @return
	 */
	public boolean checkCopySurvey(String sNewSurveyName, int orgId) {
		boolean valid = true;

		String query = "SELECT * FROM tblSurvey WHERE SurveyName = '"
				+ sNewSurveyName + "' AND FKOrganization="+orgId;



		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				valid = false;
			}

		} catch (Exception E) {
			System.err.println("Create_Edit_Survey.java - checkCopySurvey - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return valid;
	} 

	/**
	 * get result for checking of Rating assigned to the survey
	 * Added by junwei on 4 March 2008
	 * @param surveyID
	 * @return true if Rating is assign to the survey
	 */
	public boolean checkRating(int surveyID){

		String query = "SELECT * FROM tblSurvey ts, " +
				"tblSurveyRating tsr WHERE ts.SurveyID =" + surveyID + " AND " +
				"ts.SurveyID = tsr.SurveyID";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				return true;
			}
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - checkRating - " + E);
		}
		finally{
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return false;
	}

	/**
	 * get result for checking of Behaviour and Rating assigned to the survey
	 * Added by junwei on 4 March 2008
	 * @param surveyID
	 * @return true if Behaviour and Rating is assign to the survey
	 */
	public boolean checkBehaviourRating(int surveyID){
		//Changed  by Ha 30/05/08 from SurveyName = SurveyID
		String query = "SELECT * FROM tblSurvey ts, tblSurveyBehaviour tsb, " +
				"tblSurveyRating tsr WHERE ts.SurveyID =" + surveyID + " AND ts.SurveyID = tsb.SurveyID AND " +
				"ts.SurveyID = tsr.SurveyID";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				return true;
			}
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - checkBehaviourRating - " + E);
		}
		finally{
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return false;
	}

	/**
	 * getSurveyLevel
	 * 
	 * @param surveyID
	 * @return integer
	 * 			level of the survey	0 = Competency Level
	 * 								1 = Key Behaviour Level
	 * @author Ha by 11/06/08
	 * 
	 * 
	 */

	public int getSurveyLevel(int surveyID)
	{
		int level = 0;
		String query = "SELECT * FROM tblSurvey WHERE SurveyID = "+surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if(rs.next()){
				level = rs.getInt("LevelOfSurvey");
			}
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - getSurveyLevel - " + E);
		}
		finally{
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return level;

	}

	/**
	 * changeHideNAOption
	 * 
	 * @ param hideNA value
	 * @param surveyID
	 * @author Denise by 14/12/09
	 * 
	 * change the hide NA  option of one particular survey
	 * */

	public void changeHideNAOption(int hideNA, int surveyID)
	{
		String query = "Update tblSurvey SET HideNA = " + hideNA + " Where surveyID = " + surveyID;
		Connection con = null;
		Statement st = null;		
		try{		
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			st.executeUpdate(query);				
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - changeHideNAOption - " + E);
		}
		finally{
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		} 
	}

	/**
	 * getBreakCPR
	 * 
	 * @param surveyID
	 * @author Albert by 9/07/12
	 * get the breakCPR option of a particular survey
	 * */

	public int getBreakCPR(int surveyID) {
		String query = "Select breakCPR from tblSurvey Where surveyID = " + surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				return rs.getInt("breakCPR");
		} catch (SQLException E) {
			System.err.println("Create_Edit_Survey.java - getBreakCPR - "+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return 0;
	}
	/**
	 * changeBreakCPR option
	 * 
	 * @param breakCPR value
	 * @param surveyID
	 * @author Albert 15/06/2012
	 * 
	 * change the breakCPR  option of one particular survey
	 * */

	public void changeBreakCPR(int breakCPR, int surveyID){
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + surveyID;
		Connection con = null;
		Statement st = null;	
		ResultSet rs = null;

		boolean bIsUpdated = false;
		int oldBreakCPR = -1;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next()) {
				oldBreakCPR = rs.getInt("breakCPR");
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeBreakCPR - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		if (oldBreakCPR == -1){
			System.out.println("There is an error retrieving the breakCPR!");
		}
		if( breakCPR != oldBreakCPR ) {
			SurveyStatus = 3;
		}

		// End Added by DeZ, 27.06.08

		String sql = "UPDATE tblSurvey SET SurveyStatus =" + SurveyStatus;
		sql = sql + " WHERE SurveyID =" + surveyID;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeBreakCPR - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		String query = "Update tblSurvey SET breakCPR = " + breakCPR + " Where surveyID = " + surveyID;		
		try{		
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			st.executeUpdate(query);				
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - changeBreakCPR - " + E);
		}
		finally{
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		} 
	}

	/**
	 * getHideKBDesc
	 * */

	public int getHideKBDesc(int surveyID) {
		String query = "Select hideKBDesc from tblSurvey Where surveyID = " + surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				return rs.getInt("hideKBDesc");
		} catch (SQLException E) {
			System.err.println("Create_Edit_Survey.java - getHideKBDesc - "+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return 0;
	}
	
	/**
	 * changeHideKBDesc option
	 * */

	public void changeHideKBDesc(int hideKBDesc, int surveyID){
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + surveyID;
		Connection con = null;
		Statement st = null;	
		ResultSet rs = null;

		boolean bIsUpdated = false;
		int oldhideKBDesc = -1;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next()) {
				oldhideKBDesc = rs.getInt("hideKBDesc");
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeHideKBDesc - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		if (oldhideKBDesc == -1){
			System.out.println("There is an error retrieving the breakCPR!");
		}
		if( hideKBDesc != oldhideKBDesc ) {
			SurveyStatus = 3;
		}

		// End Added by DeZ, 27.06.08

		String sql = "UPDATE tblSurvey SET SurveyStatus =" + SurveyStatus;
		sql = sql + " WHERE SurveyID =" + surveyID;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeHideKBDesc - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		String query = "Update tblSurvey SET hideKBDesc = " + hideKBDesc + " Where surveyID = " + surveyID;		
		try{		
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			st.executeUpdate(query);				
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - changeHideKBDesc - " + E);
		}
		finally{
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		} 
	}
	
	/**
	 * getUseClusterOption
	 * 
	 * 
	 * @param surveyID
	 * @author Albert by 15/06/12
	 * 
	 *         get the useCluster option of one particular survey
	 * */

	public int getUseClusterOption(int surveyID) {
		String query = "Select useCluster from tblSurvey Where surveyID = " + surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				return rs.getInt("useCluster");
		} catch (SQLException E) {
			System.err
			.println("Create_Edit_Survey.java - getUseClusterOption - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return 0;
	}
	/**
	 * changeUseClusterOption
	 * 
	 * @ param useCluster value
	 * @param surveyID
	 * @author Albert 15/06/2012
	 * 
	 * change the useCluster  option of one particular survey
	 * */

	public void changeUseClusterOption(int useCluster, int surveyID){
		String command = "SELECT * FROM tblSurvey WHERE SurveyID =" + surveyID;
		Connection con = null;
		Statement st = null;	
		ResultSet rs = null;

		boolean bIsUpdated = false;
		int oldUseCluster = -1;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);
			if (rs.next()) {
				oldUseCluster = rs.getInt("UseCluster");
			}
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeUseClusterOption - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		if (oldUseCluster == -1){
			System.out.println("There is an error retrieving the useCluster!");
		}
		if( useCluster != oldUseCluster ) {
			SurveyStatus = 3;
		}

		// End Added by DeZ, 27.06.08

		String sql = "UPDATE tblSurvey SET SurveyStatus =" + SurveyStatus;
		sql = sql + " WHERE SurveyID =" + surveyID;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsUpdated = true;
		} catch (Exception ex) {
			System.out.println("Create_Edit_Survey.java - changeUseClusterOption - "+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		String query = "Update tblSurvey SET useCluster = " + useCluster + " Where surveyID = " + surveyID;		
		try{		
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			st.executeUpdate(query);				
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - changeUseClusterOption - " + E);
		}
		finally{
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		} 
	}

	/**
	 * getHideNAOption
	 * 
	 * 
	 * @param surveyID
	 * @author Denise by 14/12/09
	 * 
	 * get the hide NA  option of one particular survey
	 * */

	public int getHideNAOption(int surveyID)
	{
		String query = "Select HideNA from tblSurvey Where surveyID = " + surveyID; //Denise 08/01/2010 change from select * to select hideNA
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try{	
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				return rs.getInt("HideNA");					
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - checkBehaviourRating - " + E);
		}
		finally{
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return 0;
	}

	/**
	 * getSplitOthersOption
	 * 
	 * 
	 * @param surveyID
	 * @author Qiao Li by 17/12/09
	 * 
	 *         get the SplitOthers option of one particular survey
	 * */

	public int getSplitOthersOption(int surveyID) {
		String query = "Select SplitOthers from tblSurvey Where surveyID = " + surveyID; //Denise 08/01/2010 change from select * to select SplitOthers
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				return rs.getInt("SplitOthers");
		} catch (SQLException E) {
			System.err
			.println("Create_Edit_Survey.java - getSplitOthersOption - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return 0;
	}

	/**
	 * changeSplitOthersOption
	 * 
	 * @param splitOthers value
	 * @param surveyID
	 * @author Qiao Li by 17/12/09
	 * 
	 * change the splitOthers  option of one particular survey
	 * */

	public void changeSplitOthersOption(int splitOthers, int surveyID)
	{
		String query = "Update tblSurvey SET SplitOthers = " + splitOthers + " Where surveyID = " + surveyID;
		Connection con = null;
		Statement st = null;		
		try{		
			con = ConnectionBean.getConnection();
			st = con.createStatement();	
			st.executeUpdate(query);				
		}
		catch(SQLException E){
			System.err.println("Create_Edit_Survey.java - checkBehaviourRating - " + E);
		}
		finally{
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		} 
	}

	/**
	 * getNAExclude
	 * 
	 * @param surveyID
	 * @author Denise by 08/01/2010
	 * 
	 * get the NA excluded  option of one particular survey
	 * */

	public int getNA_Included(int surveyID)
	{		
		String query = "Select NA_Included from tblSurvey ";
		query += "where SurveyID = " + surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
		{      		
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs != null && rs.next())
			{
				return rs.getInt("NA_Included"); 
			}
		}
		catch(Exception E) 
		{
			System.err.println("Questionnaire.java - getSurveyNA_IncludedOption - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return 0; //default value
	}

	public int getHideCompDesc() {
		return HideCompDesc;
	}

	public void setHideCompDesc(int hideCompDesc) {
		this.HideCompDesc = hideCompDesc;
	}

	public int getMergeRelation() {
		return MergeRelation;
	}

	public void setMergeRelation(int MergeRelation) {
		this.MergeRelation = MergeRelation;
	}
	
	/**
	 * 	Copy the prelim question details from database based on FKSurveyID and PrelimQnID.
	 * 	Then create a new row with new PrelimQnID and FKSurveyID.
	 *	
	 * 
	 * 	@param FKSurveyID, PrelimQnID, newFKSurveyID
	 * 
	 * */
	public void copyPrelimQn(int FKSurveyID) {
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		
		String sql1 = "SELECT * FROM tbl_PrelimQn WHERE FKSurveyID = " + FKSurveyID ;
		String Question;
		
		int PrelimQnRatingScaleID;		
		int newFKSurveyID = getSurvey_ID();
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			
			con.setAutoCommit(false);
		
			while(rs.next())
			{
				Question = rs.getString("Question");
				PrelimQnRatingScaleID = rs.getInt("PrelimRatingScaleID");	
				addPrelimQn(newFKSurveyID, Question, PrelimQnRatingScaleID);
			}
			
			con.commit();
			
		}
		catch(Exception E) {
			System.err.println("Create_Edit_Survey.java - copyPrelimQn - " + E);
		}	
	}
	
	/**
	 * 	Under assumption that each survey only has one prelim question header.
	 * 
	 *  @author Jinghan
	 * 
	 * 	@param FKSurveyID
	 * 
	 * */
	public void copyPrelimQnHeader(int FKSurveyID) {
		String sql1 = "SELECT * FROM tbl_PrelimQnHeader WHERE FKSurveyID = " + FKSurveyID;
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		int newFKSurveyID = getSurvey_ID();
		String content;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			
			if(rs.next())
			{
				content = rs.getString("PrelimQnHeaderContent"); 
				addPrelimQnHeader(newFKSurveyID, content);
			} else
			{
				System.out.println("There is no header from previous survey.");
			}
			
		} 
		catch(Exception E)
		{
			System.err.println("Create_Edit_Survey.java - copyPrelimQnHeader - " + E);
		}
		
	}
	
	/**
	 * 	Copy additional question from a given survey ID.
	 * 
	 *	@author Jinghan
	 *
	 * 	@param FKSurveyID
	 * */
	
	public void copyAdditionalQn(int FKSurveyID) {
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		
		String sql1 = "SELECT * FROM tbl_AdditionalQn WHERE FKSurveyID = " + FKSurveyID ;
		String Question;
			
		int newFKSurveyID = getSurvey_ID();
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			
			con.setAutoCommit(false);
		
			while(rs.next())
			{
				Question = rs.getString("Question");
				addAdditionalQn(newFKSurveyID, Question);
			}
			
			con.commit();
			
		}
		catch(Exception E) {
			System.err.println("Create_Edit_Survey.java - copyAdditionalQn - " + E);
		}	
	}
	
	/**
	 * 	Copy additional question answer header for a given survey ID.
	 * 
	 * 	@author Jinghan
	 * 
	 * 	@param FKSurveyID
	 * 
	 * */
	public void copyAdditionalQnAnsHeader(int FKSurveyID) {
		String sql1 = "SELECT * FROM tbl_AdditionalQuestionAnsHeader WHERE FKSurveyID = " + FKSurveyID;
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		int newFKSurveyID = getSurvey_ID();
		String content;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			
			if(rs.next())
			{
				content = rs.getString("AddQnAnsHeaderContent"); 
				addAdditionalQnAnsHeader(newFKSurveyID, content);
			} else
			{
				System.out.println("There is no header from previous survey.");
			}
			
		} 
		catch(Exception E)
		{
			System.err.println("Create_Edit_Survey.java - copyAdditionalQnAnsHeader - " + E);
		}
		
	}
	
	/**
	 * 	Called by copyPrelimQn. Add new entries with parameters passed in to the database.
	 * 
	 * 	@author Jinghan
	 * 
	 * 	@param SurveyID, Question, RatingScaleID
	 * 
	 * */
	public void addPrelimQn(int SurveyID, String Question, int RatingScaleID)
	{
		String sql = "INSERT INTO tbl_PrelimQn ( FKSurveyID, Question, PrelimRatingScaleID ) VALUES ( '" 
				+ SurveyID + "', '" + Question + "', '" + RatingScaleID + "')"; ;
		Connection con = null;
		Statement st = null;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			st.executeUpdate(sql);
	
		}
		catch (Exception E)
		{
			System.err.println("Creat_Edit_Survey.java - addPrelimQn - " + E);
		}
	}
	
	/**
	 * 
	 * 	Called by copyPrelimQnHeader. Add new entries using parameters passed in to the database.
	 * 
	 * 	@author Jinghan
	 * 
	 * 	@param SurveyID, content
	 * */
	public void addPrelimQnHeader(int SurveyID, String content)
	{
		String sql = "INSERT INTO tbl_PrelimQnHeader (PrelimQnHeaderContent, FKSurveyID) VALUES ('"
				+ content + "', '"+ SurveyID + "')";
		Connection con = null;
		Statement st = null;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			st.executeUpdate(sql);
		}
		catch (Exception E) {
			System.err.println("Creat_Edit_Survey.java - addPrelimQnHeader - " + E);
		}
	}
	
	/**
	 * Called by copyAddionalQn. Add new entries using parameters passed in to the database.
	 * 
	 * @author Jinghan
	 * 
	 * @param SurveyID, content
	 * */
	public void addAdditionalQn(int SurveyID, String content)
	{
		String sql = "INSERT INTO tbl_AdditionalQn (Question, FKSurveyID) VALUES ('"
				+ content + "', '"+ SurveyID + "')";
		Connection con = null;
		Statement st = null;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			st.executeUpdate(sql);
		}
		catch (Exception E) {
			System.err.println("Creat_Edit_Survey.java - addAdditionalQn - " + E);
		}
	}
	
	/**
	 * 	Called by copyAdditionalQnAnsHeader. 
	 * 	Add new entries using parameters passed in to the database.
	 * 
	 * 	@author Jinghan
	 * 
	 * 	@param SurveyID, content
	 * */
	public void addAdditionalQnAnsHeader(int SurveyID, String content)
	{
		String sql = "INSERT INTO tbl_AdditionalQuestionAnsHeader (AddQnAnsHeaderContent, FKSurveyID) VALUES ('"
				+ content + "', '"+ SurveyID + "')";
		Connection con = null;
		Statement st = null;
		
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			st.executeUpdate(sql);
		}
		catch (Exception E) {
			System.err.println("Creat_Edit_Survey.java - addAdditionalQnAnsHeader - " + E);
		}
	}
}


