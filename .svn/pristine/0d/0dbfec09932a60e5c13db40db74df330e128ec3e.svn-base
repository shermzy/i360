package CP_Classes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voImportance;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.voRatingResult;
import CP_Classes.vo.voRatingTask;
import CP_Classes.vo.voUser;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.table.XTableChart;


/**
 * This class implements all the operations for Group Report in Excel.
 * It implements OpenOffice API.
 */
public class GroupReport_NoCPR {

	private Calculation C;
	private Questionnaire Q;
	private OpenOffice OO;
	private RatingScale rscale;
	private GlobalFunc G;
	private Setting ST; //Declaration of new object of class Setting.
	private User_Jenty U; // Declaration of new object of class User.
	private EventViewer EV; //Declaration of new object of class EventViewer.
	private AssignTarget_Rater assign;
	
	private XMultiComponentFactory xRemoteServiceManager = null;
	private XComponent xDoc = null;
	private XSpreadsheet xSpreadsheet = null;
	private XSpreadsheet xSpreadsheet2 = null;
	private XSpreadsheet xSpreadsheet3 = null;
	private String storeURL;
	
	private final int BGCOLOR = 12632256;
	private final int ROWHEIGHT = 560;
	
	//---global variable---
	private String surveyInfo [];
	private int surveyID;
	private int groupSection; 
	private int deptID;		// department ID
	private int divID;		// division ID
	
	private int arrN []; //To print N (No of Raters) for Simplified report

	private int iReportType; //1=Simplified Report "No Competencies charts", 2=Standard Report
	int startColumn = 0;
	int endColumn = 0;
	int row = 0;
	int column = 0;
	
	private boolean hasCP = false; // true if CP is chosen
	private boolean hasCPR = false; // true if CPR is chosen
	private boolean hasFPR = false; // true if FPR is chosen
	
	private Vector vGap;	// this is to store the gap of each competency so does not need to reopen another resultset
	private Vector vCompDetails;
	private Vector vRatingTask;
	private Vector vCP;
	private Vector vCPR;
	private Vector vCompID;
	
	private HashMap CPCPRMap;
	private HashMap CompIDGapMap;
	
	private int iPastSurveyID;		// For Toyota combined report
	
	/**
	 * Creates a new intance of GroupReport object.
	 */
	public GroupReport_NoCPR() {
		
		ST 	= new Setting();
		C 	= new Calculation();
		Q 	= new Questionnaire();
		U 	= new User_Jenty();
		EV 	= new EventViewer();
		OO	= new OpenOffice();
		G	= new GlobalFunc();
		rscale = new RatingScale();
		assign = new AssignTarget_Rater();
		
		vGap = new Vector();
		CPCPRMap = new HashMap();
		CompIDGapMap = new HashMap();
		vCompDetails = new Vector();
		vRatingTask = new Vector();
		vCP = new Vector();
		vCPR = new Vector();
		
		hasCP = false;
		hasCPR = false;
		hasFPR = false;
		
		startColumn = 0;
		endColumn = 12;					
	}
	
	/***************************START - INITIALISATION***************************************/
	
	/**
	 * Initialize all the processes dealing with Excel Application.
	 * 
	 * @param savedFileName
	 * @throws IOException
	 * @throws Exception
	 */
	public void InitializeExcel(String savedFileName) throws IOException, Exception 
	{	
		System.out.println("2. Excel Initialisation Starts");
		storeURL 	= "file:///" + ST.getOOReportPath() + savedFileName;
		String templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "Group Report Template_noCPR.xls";
		
		xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager");
		xDoc = OO.openDoc(xRemoteServiceManager, templateURL);
		
		//save as the template into a new file first. This is to avoid the template being used.		
		OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);		
		OO.closeDoc(xDoc);
		
		//open up the saved file and modify from there
		xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
		xSpreadsheet = OO.getSheet(xDoc, "Sheet1");
		System.out.println("END OF INTIALISATION");
	}
	
	/**
	 * Initialize all the processes dealing with Excel Application.
	 */
	public void InitializeExcelToyota(String savedFileName) throws IOException, Exception 
	{	
		System.out.println("2. Excel Initialisation Starts");
		
		storeURL 	= "file:///" + ST.getOOReportPath() + savedFileName;
		String templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "Group Report Template Combined.xls";
		
		xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=8100;urp;StarOffice.ServiceManager");
		xDoc = OO.openDoc(xRemoteServiceManager, templateURL);
		
		//save as the template into a new file first. This is to avoid the template being used.		
		OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
		OO.closeDoc(xDoc);
		
		xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
		xSpreadsheet = OO.getSheet(xDoc, "GroupReport Page1");
		xSpreadsheet2 = OO.getSheet(xDoc, "GroupReport Page2");
		xSpreadsheet3 = OO.getSheet(xDoc, "Sheet3");
	}
	/****************************END OF INITIALISATION**************************************/
	
	
	
	
	/***************************START - SURVEY INITIALISATION********************************/
	/**
	 * 	Initializes all processes dealing with Survey
	 * 
	 *	@param int SurveyID
	 *	@param int DeptID	PKDepartment
	 *	@param int DivID	PKDivision
	 */
	public void InitializeSurvey(int surveyID, int groupSection, int deptID, int divID) throws SQLException, IOException
	{
		System.out.println("1. Survey Initialisation Starts");

		this.surveyID = surveyID;
		this.groupSection = groupSection;
		this.deptID = deptID;
		this.divID = divID;

		surveyInfo = new String [7];
		surveyInfo = SurveyInfo();	
		
		//Initialise array for arrN
		arrN = new int[73 * 10 * 6]; //size = max 73 competencies * max 10 KBs * max 6 Rating
			
	}	
	
	/**
	 * 	Initializes all processes dealing with Survey
	 *	@param int SurveyID
	 *	@param int DeptID	PKDepartment
	 *	@param int DivID	PKDivision
	 */
	public void InitializeSurveyToyota(int surveyID, int groupSection, int deptID, int divID) throws SQLException, IOException
	{
		System.out.println("1. Survey Initialisation Starts");
		
		column = 0;
		this.surveyID = surveyID;
		this.groupSection = groupSection;
		this.deptID = deptID;
		this.divID = divID;

		surveyInfo = new String [10];
		surveyInfo = SurveyInfoToyota();		
	}	
	
	/**
	 * Retrieves the survey details and stores in an array
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String [] SurveyInfo() throws SQLException {
		String [] info = new String[7];
		
		String query = "SELECT tblSurvey.LevelOfSurvey, tblJobPosition.JobPosition, tblSurvey.AnalysisDate, ";
		query = query + "tblOrganization.NameSequence, tblSurvey.SurveyName, tblOrganization.OrganizationName, tblOrganization.OrganizationLogo FROM tblSurvey INNER JOIN ";
		query = query + "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID INNER JOIN ";
		query = query + "tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
		query = query + "WHERE tblSurvey.SurveyID = " + surveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		
  			if(rs.next()) {
  				for(int i=0; i<7; i++)
  					info[i] = rs.getString(i+1);
  			}
  			
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - SurveyInfo - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		return info;

	}
	
	/**
	 * 	Retrieves the survey details and stores in an array
	 *	
	 *	@return String SurveyInfo	Array
	 */
	public String [] SurveyInfoToyota() throws SQLException {
		String [] info = new String[11];
				
		String query = "SELECT Surv.LevelOfSurvey, JobPos.JobPosition, [User].IDNumber, [User].FamilyName, [User].GivenName, ";
		query = query + "Org.NameSequence, Surv.SurveyName, Comp.CompanyName, Dept.DepartmentName, Grp.GroupName, Surv.JobPositionID ";
		query = query + "FROM tblSurvey Surv INNER JOIN ";
		query = query + "tblJobPosition JobPos ON Surv.JobPositionID = JobPos.JobPositionID INNER JOIN ";
		query = query + "tblAssignment Assign ON Surv.SurveyID = Assign.SurveyID INNER JOIN ";
		query = query + "[User] ON Assign.TargetLoginID = [User].PKUser INNER JOIN ";
		query = query + "tblOrganization Org ON Surv.FKOrganization = Org.PKOrganization INNER JOIN ";
		query = query + "tblConsultingCompany Comp ON Surv.FKCompanyID = Comp.CompanyID AND [User].FKCompanyID = Comp.CompanyID AND ";
		query = query + "Org.FKCompanyID = Comp.CompanyID INNER JOIN [Group] Grp ON [User].Group_Section = Grp.PKGroup INNER JOIN ";
		query = query + "Department Dept ON [User].FKDepartment = Dept.PKDepartment AND Org.PKOrganization = Dept.FKOrganization ";
		query = query + "WHERE Surv.SurveyID = " + surveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		
  			if(rs.next()) {
				for(int i=0; i<11; i++)
					info[i] = rs.getString(i+1);
  			}
  			
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - SurveyInfoToyota - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return info;
	}
	
	/**
	 * 	Retrieves the group name based on groupID
	 *
	 *	@return String GroupName
	 */
	public String getGroupName() throws SQLException {
		String info = "";
		
		String query = "SELECT * from [Group] where PKGroup = " + groupSection;
								
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
	 			info = rs.getString("GroupName");


  		}catch(Exception ex){
			System.out.println("GroupReport.java - getGroupName - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return info;
	}	
	
	/**
	 * 	Retrieves the department name based on deptID
	 *	
	 *	@return String DepartmentName
	 */
	public String getDeptName() throws SQLException {
		String info = "";	

		String query = "SELECT * from Department where PKDepartment = " + deptID;
								
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
	 			info = rs.getString("DepartmentName");


  		}catch(Exception ex){
			System.out.println("GroupReport.java - getDeptName - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return info;
	}	
	
	/**
	 * 	Retrieves the division name based on divID
	 *	
	 *	@return String DivisionName
	 */
	public String getDivName() throws SQLException 
	{
		String info = "";
		
		String query = "SELECT * from Division where PKDivision = " + divID;
								
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
	 			info = rs.getString("DivisionName");


  		}catch(Exception ex){
			System.out.println("GroupReport.java - getDivName - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return info;
	}	
	
	/**
	 * Retrieves all the job level based by on the survey.
	 */
	public String getJobLevel(int FKSurvey) throws SQLException, IOException, Exception
	{
		String sJobLevel = "";
		
		String query = "SELECT DISTINCT tblJobPosition.JobLevelName FROM tblSurvey INNER JOIN ";
		query += "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query += "WHERE (tblSurvey.SurveyID = " + FKSurvey + ")";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
	 			sJobLevel = rs.getString("JobLevelName");


  		}catch(Exception ex){
			System.out.println("GroupReport.java - getJobLevel - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return sJobLevel;
	}
	/******************************END - SURVEY INITIALISATION***************************/

	
	
	/************************START - CP/CPR****************************************/
	/**
	 * print CP and CPR
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 * @see generateReport()
	 */
	public void printCPvsCPR() throws SQLException, IOException, Exception 
	{
		System.out.println("4. Generating CP Versus CPR");
	
		int [] address = OO.findString(xSpreadsheet, "<CP versus CPR Graph>");
		
		column = address[0];
		row = address[1];
		
		OO.findAndReplace(xSpreadsheet, "<CP versus CPR Graph>", "");
		
		//check if CP exists
		if(hasCP)
		{
			
			vCP = getCPCPR("CP");
		}
		
		//check if either CPR or FPR exists.
		if(hasCPR)
		{
			
			vCPR = getCPCPR("CPR");
		}
		else if(hasFPR)
		{
			
			vCPR = getCPCPR("FPR");
		}
		
		drawLineChart();
		
	}
	
	/**
	 * get rating task which match CP, CPR or FPR.
	 * 
	 * @return vector of voSurveyRating
	 * @throws SQLException
	 * @see generateReport()
	 */
	public Vector getRatingTask() throws SQLException {
		Vector v = new Vector();
		
		String query = "";
		
		query = query + "SELECT tblSurveyRating.RatingTaskID, tblRatingTask.RatingCode, ";
		query = query + "tblSurveyRating.RatingTaskName FROM tblSurveyRating INNER JOIN ";
		query = query + "tblRatingTask ON tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID ";
		query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
		query = query + " and tblRatingTask.RatingCode in('CP', 'CPR', 'FPR')";
		query = query + " ORDER BY tblSurveyRating.RatingTaskID";
						

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
			while(rs.next()) 
			{
				voRatingTask vo = new voRatingTask();
				vo.setRatingTaskID(rs.getInt("RatingTaskID"));
				vo.setRatingCode(rs.getString("RatingCode"));
				vo.setRatingTaskName(rs.getString("RatingTaskName"));
				v.add(vo);
			}
		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getRatingTask - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 * check whether hasCP, hasCPR, hasFPR exist or not.
	 * @see generateReport()
	 */
	public void checkCPCPR() {
		
		for(int i=0; i<vRatingTask.size(); i++ )
		{
			voRatingTask vo = (voRatingTask)vRatingTask.elementAt(i);
			String RTCode = vo.getRatingCode();
			
			if(RTCode.equals("CP"))
				hasCP = true;
			
			if(RTCode.equals("CPR")) 
				hasCPR = true;
			
			if(RTCode.equals("FPR")) 
				hasFPR = true;
		}
		
	}
	
	/**
	 * 	Retrieves the results under that particular rating code
	 *	@param String RTCode	Rating Task Code
	 *	
	 *	@return Vector CPCPR
	 *	@see printCPvsCPR() 
	 */
	public Vector getCPCPR(String RTCode) throws SQLException 
	{
		String query = "";
	
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		
	
		if(reliabilityCheck == 0) 	// trimmed mean
		{		
			query = query + "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
			query += "ROUND(AVG(tblTrimmedMean.TrimmedMean) ,2) AS Result FROM ";
			query += "tblTrimmedMean INNER JOIN Competency ON ";
			query += "tblTrimmedMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query += "tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query += "[User] ON [User].PKUser = tblTrimmedMean.TargetLoginID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
			query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "' AND ";
			query += "tblTrimmedMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
			query += "WHERE SurveyID = " + surveyID + " AND RaterCode <> 'SELF' AND ";
			query += "RaterStatus IN (1, 2, 4) ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				
			query += ") ";
			//Added by Ha 21/06/08 GROUP BY SurveyID 
			query += " GROUP BY tblTrimmedMean.SurveyID, Competency.PKCompetency, Competency.CompetencyName ";
			query += "ORDER BY Competency.CompetencyName";
			
		}
		else 
		{
			query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
			query += "ROUND(AVG(tblAvgMean.AvgMean),2) AS Result FROM ";
			query += "tblAvgMean INNER JOIN Competency ON ";
			query += "tblAvgMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query += "[User] ON [User].PKUser = tblAvgMean.TargetLoginID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID;
			query += " AND tblAvgMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "' AND ";
			query += "tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
			query += "WHERE SurveyID = " + surveyID + " AND RaterCode <> 'SELF' AND ";
			query += "RaterStatus IN (1, 2, 4) ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") ";
			//Added by Ha 21/06/08 GROUP BY SurveyID 
			query += " GROUP BY tblAvgMean.SurveyID, Competency.PKCompetency, Competency.CompetencyName ";
			query += "ORDER BY Competency.CompetencyName";

		}
	
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  			   voRatingResult vo = new voRatingResult();
  			   vo.setCompetencyID(rs.getInt("CompetencyID"));
  			   vo.setCompetencyName(rs.getString("CompetencyName"));
  			   vo.setResult(rs.getDouble("Result"));
				
  			   v.add(vo);
				
  		   }
	
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getCPCPR - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
		return v;
	}	
	
	/**
	 * 	Draw line chart for CPCPR or CPFPR.
	 *
	 *	@see printCPvsCPR()
	 */
	public void drawLineChart() throws IOException, Exception 
	{

		
		XSpreadsheet xSpreadsheet2 = OO.getSheet(xDoc, "Sheet2");
		
		int r = row;
		int c = 0;
		
		int totalCol = 3; // 1 for comp, 2 with CP, 3 with CPR
		
		int total = totalCompetency();		// 1 for all
		
		c=1;
		OO.insertString(xSpreadsheet2, "CP", r, c);
		
		c++;
		
		if(hasCPR)
			OO.insertString(xSpreadsheet2, "CPR", r, c);
		else if(hasFPR)
			OO.insertString(xSpreadsheet2, "FPR", r, c);
		//else
		//	OO.insertString(xSpreadsheet2, "CPR", r, c);
		
		r++;
		
		for(int i=0; i<total; i++) 
		{		
			
			c = 0;
			int compID = 0;
			String compName = " ";
			double dCP = 0;
			double dCPR = 0;
			
			voRatingResult voCP = (voRatingResult)vCP.elementAt(i);
			
			if(voCP != null) {
				
				compID = voCP.getCompetencyID();
				
				compName = UnicodeHelper.getUnicodeStringAmp(voCP.getCompetencyName());
				
				dCP = voCP.getResult();	
			} 
			//System.out.println("Competency Name "+compName);

			OO.insertString(xSpreadsheet2, compName, r, c);
			c++;
			
			OO.insertNumeric(xSpreadsheet2, dCP, r, c);
			c++;
			
			if(hasCPR || hasFPR) {
				voRatingResult voCPR = (voRatingResult)vCPR.elementAt(i);
				
				if(voCPR != null)
					dCPR = voCPR.getResult();
				else
					dCPR = 0;	
				
				
			}else //if no CPR/FPR is chosen, set the CPR as 0 
				dCPR = 0;			
		
			double gap = Math.round((dCP - dCPR) * 100.0) / 100.0;
			if(hasCPR || hasFPR)
				OO.insertNumeric(xSpreadsheet2, dCPR, r, c);
	
			c++;

			double [] dArrTemp = new double [2];
			dArrTemp[0] = dCP;
			dArrTemp[1] = dCPR;

			CPCPRMap.put(new Integer(compID), (double[])dArrTemp);
			vGap.add(new String[]{compName, Double.toString(gap)});
			CompIDGapMap.put(new Integer(compID), Double.toString(gap));
			
			r++;
			
		}

		if(hasCPR || hasFPR)
			OO.setSourceData(xSpreadsheet, xSpreadsheet2, 0, c-totalCol, c-1, row, r-1);
		else
			OO.setSourceData(xSpreadsheet, xSpreadsheet2, 0, c-totalCol, c-2, row, r-1);
		
		total = total + row - 1;
		row--;
	
	}
	
	
	/**
	 * Count the total competencies in the particular survey
	 * 
	 * @return int TotalComp	Total Competency
	 * @throws SQLException
	 * 
	 * @see drawChart()
	 */
	public int totalCompetency() throws SQLException 
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		
		int total = 0;
		
		if(surveyLevel == 0) {
			
			query = query + "SELECT  COUNT(CompetencyID) AS Total FROM tblSurveyCompetency ";
			query = query + "WHERE SurveyID = " + surveyID;
			
		}else {
			query = query + "SELECT COUNT(DISTINCT CompetencyID) AS Total FROM ";
			query = query + "tblSurveyBehaviour WHERE SurveyID = " + surveyID;
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   if(rs.next()) {
  			   total = rs.getInt(1);
				
  		   }
	
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - totalCompetency - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return total;
		
	}
	
	/*************************END CP/CPR****************************************************/
	
	
	
	//****Added by Tracy 26 aug 08*************
	//************Print Rating Title based on CPR or FPR
	public void printGapTitle(int surveyID) throws SQLException, IOException, Exception
	{
		System.out.println("5.1 Gap Title Insertion Starts");
		
		int [] address = OO.findString(xSpreadsheet, "<Gap Title>");
		
		OO.findAndReplace(xSpreadsheet, "<Gap Title>", "");		
		
		column = address[0];
		row = address[1];
		int i=0;
		Vector RTaskID= new Vector();
	    Vector RTaskName=new Vector();
		
		//Get Rating from database according to s urvey ID
		String query = "SELECT a.RatingTaskID as RTaskID, b.RatingTask as RTaskName FROM tblSurveyRating a ";
		query += "INNER JOIN tblRatingTask b ON a.RatingTaskID=b.RatingTaskID  WHERE a.SurveyID = "+ surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {
				RTaskID.add(i,new Integer(rs.getInt("RTaskID")));
				RTaskName.add(i,new String(rs.getString("RTaskName")));
				i++;
			}
		
		//Check CPR or FPR
		String pType="";
		String CPR="";
		String CP="";
		String FPR="";
		
		for (int n=0; RTaskID.size()-1>=n; n++ ) {
			if (((Integer)RTaskID.elementAt(n)).intValue()==1) {
				CP=RTaskName.elementAt(n).toString();
			}else if (((Integer)RTaskID.elementAt(n)).intValue()==2){
				CPR=RTaskName.elementAt(n).toString();
				pType="C";
			}else if (((Integer)RTaskID.elementAt(n)).intValue()==3){
				FPR=RTaskName.elementAt(n).toString();
				pType="F";
			}
		}
		
		String title = "";
		if (pType.equals("C"))
		//changed by Hemilda 15/09/2008 change the word and make it fit with the width of column
			title= "Gap = " + CPR + " (All) minus " + CP + " (All) : Strengths and Development Areas Report";
		else if (pType.equals("F"))
			title= "Gap = " + FPR + " (All) minus " + CP + " (All) : Strengths and Development Areas Report";
		
		//Insert title to excel file
		OO.insertString(xSpreadsheet, title, row, 0);	
	
		OO.mergeCells(xSpreadsheet, startColumn, endColumn, row, row);
		OO.setRowHeight(xSpreadsheet, row, 1, ROWHEIGHT*OO.countTotalRow(title, 90));			

		} catch (Exception E) {
			System.err.println("SurveyResult.java - GroupSection - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
	}
	
	//**********End Tracy Add 26 aug 08
	
	/**************************START GAP****************************************************/
	/**
	 * print gap
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 * 
	 * @see generateReport()
	 */
	public void printGap() throws SQLException, IOException, Exception {
		
		System.out.println("5. Gap Insertion Starts");
					
		int [] address = OO.findString(xSpreadsheet, "<Gap>");
		
		column = address[0];
		row = address[1];
		int c = 0;
		
		vGap = G.sorting(vGap, 1);
		
		OO.findAndReplace(xSpreadsheet, "<Gap>", "");
		
		double MinMaxGap [] = getMinMaxGap();
		
		double low = MinMaxGap[0];
		double high = MinMaxGap[1];
		
		if (hasCPR || hasFPR)	// If CPR or FPR is chosen in this survey
		{
			String title = "COMPETENCY";
	
			if (ST.LangVer == 2)
				title = "KOMPETENSI";
			
			OO.insertString(xSpreadsheet, title, row, c);
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
	
			row++;
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+2, 2, 1);
			
			int startBorder = row;
			
			if (ST.LangVer == 1){
				OO.insertString(xSpreadsheet, "STRENGTH", row, c);
				OO.insertString(xSpreadsheet, "Gap >= " + high, row, 10);
			}
			else if (ST.LangVer == 2){
				OO.insertString(xSpreadsheet, "KEKUATAN", row, c);
				OO.insertString(xSpreadsheet, "Selisih >= " + high, row, 10);
			}
			
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, endColumn-1, row, row, BGCOLOR);					
			row++;
			
	
			for(int i=0; i<vGap.size(); i++) {
				double gap = Double.valueOf(((String [])vGap.elementAt(i))[1]).doubleValue();
				
				if(gap >= high) {
					String compName = ((String [])vGap.elementAt(i))[0];
					
					OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);			
					
					OO.insertString(xSpreadsheet, compName, row, c);
					OO.insertNumeric(xSpreadsheet, gap, row, 10);
					row++;	
				}
			}
			
			row++;
			int endBorder = row;
			OO.setTableBorder(xSpreadsheet, startColumn, endColumn-1, startBorder, endBorder, false, false, true, true, true, true);
	
			startBorder = endBorder + 1;		
			row++;	
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+2, 2, 1);
							
			if (ST.LangVer == 1){
				OO.insertString(xSpreadsheet, "MEET EXPECTATIONS", row, c);
				OO.insertString(xSpreadsheet, low + " < Gap < " + high, row, 10);
			}
			else if (ST.LangVer == 2){			
				OO.insertString(xSpreadsheet, "MEMENUHI PENGHARAPAN", row, c);
				OO.insertString(xSpreadsheet, low + " < Selisih < " + high, row, 10);
			}
			
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, endColumn-1, row, row, BGCOLOR);						
			row++;
			
			for(int i=0; i<vGap.size(); i++) {
				double gap = Double.valueOf(((String [])vGap.elementAt(i))[1]).doubleValue();
				
				if(gap < high && gap > low) {
					String compName = ((String [])vGap.elementAt(i))[0];				
					
					OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);			
					
					OO.insertString(xSpreadsheet, compName, row, c);
					OO.insertNumeric(xSpreadsheet, gap, row, 10);
					row++;
				}
			}
				
			row++;
			endBorder = row;
			OO.setTableBorder(xSpreadsheet, startColumn, endColumn-1, startBorder, endBorder, false, false, true, true, true, true);
										
			startBorder = endBorder + 1;		
			row++;
			
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+2, 2, 1);					
			
			if (ST.LangVer == 1){
				OO.insertString(xSpreadsheet, "DEVELOPMENTAL AREA", row, c);
				OO.insertString(xSpreadsheet, "Gap <= " + low, row, 10);
			}
			else if (ST.LangVer == 2){
				OO.insertString(xSpreadsheet, "AREA PERKEMBANGAN", row, c);
				OO.insertString(xSpreadsheet, "Selisih <= " + low, row, 10);
			}
			
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, endColumn-1, row, row, BGCOLOR);
							
			row++;
					
			for(int i=0; i<vGap.size(); i++) {
				double gap = Double.valueOf(((String [])vGap.elementAt(i))[1]).doubleValue();
				
				if(gap <= low) {
					String compName = ((String [])vGap.elementAt(i))[0];
					
					OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);			
					
					OO.insertString(xSpreadsheet, compName, row, c);
					OO.insertNumeric(xSpreadsheet, gap, row, 10);
					row++;	
				}
			}
			
			endBorder = row;
			OO.setTableBorder(xSpreadsheet, startColumn, endColumn-1, startBorder, endBorder, false, false, true, true, true, true);
		} else {
			// Delete the rows with Gap Table description from the report
			OO.deleteRows(xSpreadsheet, 0, 12, 89, 102, 13, 1);
		}
		System.out.println("5.Gap insertion completed");
	}
	
	/**
	 * get minimum and maximum gap.
	 * 
	 * @return min and max gap in an array
	 * @throws SQLException
	 * @see printGap()
	 */
	public double [] getMinMaxGap() throws SQLException 
	{
		double gap [] = new double [2];
		
		String query = "Select MIN_gap, MAX_Gap from tblSurvey where SurveyID = " + surveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   if(rs.next()) {
  			   gap[0] = rs.getDouble(1);
  			   gap[1] = rs.getDouble(2);
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getMinMaxGap - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		return gap;
	}	

	/****************************END GAP****************************************************/
	
	
	
	/***************************START COMPETENCY********************************************/
	/**
	 * This print the competency chart
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 * @see generateReport()
	 */
	
//	Added by Tracy 01 Sep 08**********************************
	public void printCompGap(int surveyID) throws SQLException, Exception {
		System.out.println("6.1 Competency Gap Insertion Starts");
		
		int i=0;
		Vector RTaskID= new Vector();
	    Vector RTaskName=new Vector();
		
		//Get Rating from database according to s urvey ID
		String query = "SELECT a.RatingTaskID as RTaskID, b.RatingTask as RTaskName FROM tblSurveyRating a ";
		query += "INNER JOIN tblRatingTask b ON a.RatingTaskID=b.RatingTaskID  WHERE a.SurveyID = "+ surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while (rs.next()) {
				RTaskID.add(i,new Integer(rs.getInt("RTaskID")));
				RTaskName.add(i,new String(rs.getString("RTaskName")));
				i++;
			}
		
		//Check CPR or FPR
		String pType="";
		String CPR="";
		String FPR="";
		
		for (int n=0; RTaskID.size()-1>=n; n++ ) {
			if (((Integer)RTaskID.elementAt(n)).intValue()==1) {
				//CP=RTaskName.elementAt(n).toString();
			}else if (((Integer)RTaskID.elementAt(n)).intValue()==2){
				CPR=RTaskName.elementAt(n).toString();
				pType="C";
			}else if (((Integer)RTaskID.elementAt(n)).intValue()==3){
				FPR=RTaskName.elementAt(n).toString();
				pType="F";
			}
		}
		
		String RPTitle = "";
		if (pType.equals("C"))
			RPTitle= CPR ;
		else if (pType.equals("F"))
			RPTitle= FPR ;
		
		//Insert title to excel file
		OO.findAndReplace(xSpreadsheet, "<CompRP>", RPTitle);		
			

		} catch (Exception E) {
			System.err.println("SurveyResult.java - GroupSection - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
	}
	// End add by Tracy 01 Sep 08*******************************
	
	public void printCompetency() throws SQLException, IOException, Exception 
	{
		System.out.println("6. Competency/Key Behaviour Report");
		
		int [] address = OO.findString(xSpreadsheet, "<Report>");
		OO.findAndReplace(xSpreadsheet, "<Report>", "");
		
		column = address[0];
		row = address[1];
		
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		  
		int totalOth = 0;
		int totalSup = 0;
		int totalSelf = 0;
		
		int totalAll = totalOth + totalSup;// get the total number of raters exclude self.(ALL)
		int total = 0;// to determine the number of variables in y-axis.
		
		//to determine the y legend in charts
		if(totalSelf > 0)
			total = 1 + getTotalOtherRaters() + 1;		// 1(self) + other raters + 1(all)
		else
			total = 0 + getTotalOtherRaters() + 1;		// 1(self) + other raters + 1(all)
		
		/*
		 * First page of Competency rank will print out "Competency Report" and a blank after that
		 * This will cause the page break to go haywire. So we have to reduce the insertion of rows by 2
		 * iInitial will keep track of the first instance of generation of Competency Report
		 */
		//chart related variable
		int iN = 0; //To be used as counter for arrN
		int iInitial = 0;
		int count = 0; // to count total chart for each page, max = 2;
		//int rowTotal = row + 1;
		
		int add = 13/total;
	
		String level = "Competency";
		if (ST.LangVer == 2)
			level = "Kompetensi";
		
		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, level + " Report", row, 0);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Laporan " + level, row, 0);
		
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
		row += 2;
		
		int endRow = row;
		int [] ID = {0,0};
		
		for(int i=0; i<vCompDetails.size(); i++)
		{
			//System.out.println("before---" + iInitial + "--" + i + "---" + endRow + "---" + total + "---" + iN + "---" +  totalAll + "---" + count);
			
			if(i != 0) {
				count = ID[1];
			}
			//int compID = ((voCompetency)(vCompDetails.elementAt(i))).getCompetencyID();
			//int totalOth = getTotalRaters("OTH%");
			//int totalSup = getTotalRaters("SUP%");
			//int totalSelf = getTotalRaters("SELF");
			ID = generateChart(0, null, 0, iInitial, i, endRow, total, iN, totalAll, add, totalSelf, count, vCompDetails.size());
			//System.out.println("after---" + iInitial + "--" + i +  "---" + endRow + "---" + total + "---" + iN + "---" +  totalAll + "---" + count);
			
			int compID = ID[0];
			if(surveyLevel == 1) {
				
				Vector vKBDetails = getKBList(ID[0]);
				for(int j=0; j<vKBDetails.size(); j++) {
					count = ID[1];
					ID = generateChart(1, vKBDetails, compID, iInitial, j, endRow, total, iN, totalAll, add, totalSelf, count, vKBDetails.size());
				
					iInitial++;
				}
				
			}
		
			iInitial++;
		} // while Comp			
		System.out.println("6.End of printing competency");
	}
	
	/**
	 * Print the value of total raters on the left column of the graph
	 * @param totalRater
	 * @param startRow
	 * @param totalData
	 * @throws Exception 
	 */
	private void printNumeric(int[] totalRater, int startRow, int totalData) throws Exception {
		// if not a simplified report, print the numeric (since it has charts)
		if(iReportType != 2) {
			return;
		}
		int[] rowPos = new int[totalData];
		if (totalData == 2) {
			rowPos[1] = startRow + 10;
			rowPos[0] = startRow + 4;
		} else if (totalData == 3) {
			rowPos[2] = startRow + 11;
			rowPos[1] = startRow + 7;
			rowPos[0] = startRow + 3;
		}
		
		for (int i = 0; i < totalData; i++) {
			OO.insertNumeric(xSpreadsheet, totalRater[i], rowPos[i], 0);
		}
	}
	
	/**
	 * CHART TEMPLATE
	 * type = 0 to generate competency chart
	 * type = 1 to generate KB chart.
	 * 
	 * @param type
	 * @param iInitial
	 * @param i
	 * @param startRow
	 * @param endRow
	 * @param r1
	 * @param total
	 * @param iN
	 * @param totalAll
	 * @param add
	 * @param totalSelf
	 * @param count
	 * @throws Exception
	 */
	public int[] generateChart(int type, Vector v, int iFKComp, int iInitial, int i, int endRow, int total, int iN, int totalAll, int add, int totalSelf, int count, int size) throws Exception {
		
		//--common
		//int RTID = 0;
		int KBID = 0;
		int ID[] = {0,0};
		int r =0 ;
		int rowTotal = 0;
		String sGap = "";
		double dCPScore = -1;
		double dCPRScore = -1;
		int startRow = row;
		//int r1 = 1;
		
		//competency variable
		int iCompID = iFKComp;
		String sCompName = "";
		String sCompDef = "";
		
		//KB varibale
		String sKB ="";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		  
		
		if(type == 0) {
//		OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+20, 20, 1);
			if(iInitial == 0 || iInitial == 1)
				OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+18, 18, 1);
			else
				OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+19, 19, 1);
		
			
			voCompetency voComp = (voCompetency)vCompDetails.elementAt(i);
			iCompID = voComp.getCompetencyID();
			ID[0] = iCompID;
			sCompName = voComp.getCompetencyName();
			sCompDef = voComp.getCompetencyDefinition();
			
			startRow = row;
			
			OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(sCompName), row, 0);
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, endColumn, row, row, BGCOLOR);							
			row++;
			
			//r1 = row;
			OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(sCompDef), row, 0);
			
			OO.mergeCells(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setRowHeight(xSpreadsheet, row, 1, ROWHEIGHT*OO.countTotalRow(sCompDef, 90));
			row++;
		
			r = 0;
			
			rowTotal = row + 11;
		}
		
		if(type == 1) {
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+20, 20, 1);
			voKeyBehaviour vo = (voKeyBehaviour)v.elementAt(i);
			KBID = vo.getKeyBehaviourID();
			ID[0] = KBID;
			sKB = vo.getKeyBehaviour();
			
			startRow = row;
			//r1 = row;
			
			int no = i+1;
			OO.insertString(xSpreadsheet, no + ". " + UnicodeHelper.getUnicodeStringAmp(sKB), row, 0);
			OO.mergeCells(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setRowHeight(xSpreadsheet, row, 0, ROWHEIGHT*OO.countTotalRow(sKB, 90));
	
			row += 2;
			
			rowTotal = row + 11;														
			
			r = 0;
		}
		
		
		//Changed by Ha 08/07/08 from total to total + 1
		String [] Rating = new String [total+1];
		double [] Result = new double [total+1];
		int[] totalRater = new int[total+1];
		for(int j=0; j<vRatingTask.size(); j++) {
			voRatingTask voSurvey = (voRatingTask)vRatingTask.elementAt(j);
			
			//RTID = voSurvey.getRatingTaskID();
			String RTCode = voSurvey.getRatingCode();
			
			//Common variable
			double dSelfScore = -1; 
			
			
			//Comp variable
			double [] CPCPRScore = {-1,-1};
			
			if(type == 0) {
				CPCPRScore = (double[])CPCPRMap.get(new Integer(iCompID));
				dCPScore = CPCPRScore[0];
				dCPRScore = CPCPRScore[1];
			} 
			
			if(RTCode.equals("CP")) {
			
				if(type == 1)
					dCPScore = getKBCPCPR(iCompID, KBID, RTCode);
					
				if(dCPScore != -1) 
				{	
					Rating[r] = RTCode + " (ALL)";
					Result[r] = dCPScore;
					//int totalOth = getTotalRaters("OTH%");
					//int totalSup = getTotalRaters("SUP%");
					//int totalSelf = getTotalRaters("SELF");
					totalAll = totalRater("OTH%",1,iCompID, KBID) + totalRater("SUP%",1,iCompID, KBID);
					totalRater[r++] =  totalAll;
					
					arrN[iN] = totalAll;
					iN++;
					
//					if(iReportType == 2) 
//						
//						OO.insertNumeric(xSpreadsheet, totalAll, rowTotal, 0);					
//					rowTotal -= add;
					
//					r++;
				}
				
				totalSelf = totalRater("SELF",1,iCompID, KBID);
				if(totalSelf != 0) {
					
					if(type == 0)
						dSelfScore = getCPSelf(iCompID);
					else 
						dSelfScore = getKBCPSelf(iCompID, KBID);
					
					if(dSelfScore != -1 ) 
					{	
						Rating[r] = RTCode + " (SELF)";							
						Result[r] = dSelfScore;
						totalRater[r++] =  totalSelf;
						
						arrN[iN] = totalSelf;
						iN++;
//						if(iReportType == 2) 
//							OO.insertNumeric(xSpreadsheet, totalSelf, rowTotal+2, 0);
//						rowTotal -= add;
					}
				}
			}
			else if(RTCode.equals("CPR") || RTCode.equals("FPR"))
			{
				
				if(type == 1)
					dCPRScore = getKBCPCPR(iCompID, KBID, RTCode);
				
				if(dCPRScore != -1) {
				
					Rating[r] = RTCode + " (ALL)";
					Result[r] = dCPRScore;
					
					if (RTCode.equals("CPR"))
						totalAll = totalRater("OTH%",2,iCompID, KBID) + totalRater("SUP%",2,iCompID, KBID);
					else if (RTCode.equals("FPR"))
						totalAll = totalRater("OTH%",3,iCompID, KBID) + totalRater("SUP%",3,iCompID, KBID);
					totalRater[r++] =  totalAll;
					arrN[iN] = totalAll;
					iN++;
					
//					if(iReportType == 2) 
//						
//						OO.insertNumeric(xSpreadsheet, totalAll, rowTotal+4, 0);
//					rowTotal -= add;
				}
			}												
		}//while RT
		
		// Add by Santoso(2008-10-27) to print out the total rater
		printNumeric(totalRater, row, r);
		
		row++;

		int maxScale = getMaxScale();
	
		//start draw chart from here
		drawChart(Rating, Result, 1, maxScale);
		
		column = 9;		//write the importance n gap
		int rtemp = row;

		voImportance voImp = getImportance(iCompID, KBID);
	
		if(voImp != null) 
		{
			String task = voImp.getRatingTask();
			
			if(task != null) {
				double taskResult = voImp.getResult();
				
				OO.insertString(xSpreadsheet, task + ": " + taskResult, rtemp, column);
				OO.mergeCells(xSpreadsheet, column, endColumn, rtemp, rtemp+1);
				OO.setCellAllignment(xSpreadsheet, column, endColumn, rtemp, rtemp+1, 2, 1);
				
				rtemp += 3;
			}
		}

		if (hasCPR || hasFPR)	// If CPR is chosen in this survey
		{

			if(type == 0) {
				sGap = (String) CompIDGapMap.get(new Integer(iCompID));
			} else {
				sGap = Double.toString(Math.round((dCPScore - dCPRScore) * 100.0) / 100.0);
			}
			
			if (ST.LangVer == 1)
				OO.insertString(xSpreadsheet, "Gap = " + sGap, rtemp, column);
			else if (ST.LangVer == 2)
				OO.insertString(xSpreadsheet, "Selisih = " + sGap, rtemp, column);
			
			OO.mergeCells(xSpreadsheet, column, endColumn, rtemp, rtemp+1);
			OO.setCellAllignment(xSpreadsheet, column, endColumn, rtemp, rtemp+1, 2, 1);		
		}				
		rtemp+=3;
	
		double LOA = 0;

		if(surveyLevel == 0 || KBID != 0)
			LOA = LevelOfAgreement(iCompID, KBID);
		else
			LOA = AvgLevelOfAgreement(iCompID);
		
		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, "Level Of Agreement: " + LOA + "%", rtemp, column);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Tingkat Persetujuan: " + LOA + "%", rtemp, column);
		
		OO.mergeCells(xSpreadsheet, column, endColumn, rtemp, rtemp+1);
		OO.setCellAllignment(xSpreadsheet, column, endColumn, rtemp, rtemp+1, 2, 1);
		
		column = 0;											
		count++;
		
		
		if(count == 2) {
			count = 0;
			if(iInitial == 0 || iInitial == 1)
				row += 15;
			else
				row += 16;
			
		
				OO.insertPageBreak(xSpreadsheet, startColumn, endColumn, row);
		} else {
			column = 0;
			if(iInitial == 0 || iInitial == 1)
				row += 15;
			else
				row += 16;
		}
		ID[1] = count;
		
		endRow = row - 1;
		//comp name and definition
		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, startRow, startRow+1, 
						false, false, true, true, true, true);
		//total sup n others				
		OO.setTableBorder(xSpreadsheet, startColumn, startColumn, startRow+2, endRow, 
						false, false, true, true, true, true);
						
		//chart
		OO.setTableBorder(xSpreadsheet, startColumn+1, 8, startRow+2, endRow, 
						false, false, true, true, true, true);
		OO.setTableBorder(xSpreadsheet, 9, endColumn, startRow+2, endRow, 
						false, false, true, true, true, true);
		
		OO.setCellAllignment(xSpreadsheet, startColumn, startColumn, startRow+2, endRow, 1, 2);
		
		return ID;	
	}

	
	/**
	 * 	Retrieves Key Behaviour lists based on CompetencyID
	 *
	 * @param compID
	 * @return
	 * @throws SQLException
	 */
	public Vector getKBList(int compID) throws SQLException {
		String query = "";
		
		Vector v = new Vector();
		
		query = query + "SELECT DISTINCT tblSurveyBehaviour.KeyBehaviourID, KeyBehaviour.KeyBehaviour ";
		query = query + "FROM tblSurveyBehaviour INNER JOIN KeyBehaviour ON ";
		query = query + "tblSurveyBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID + " AND ";
		query = query + "tblSurveyBehaviour.CompetencyID = " + compID;
		query = query + " ORDER BY tblSurveyBehaviour.KeyBehaviourID";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  			   voKeyBehaviour vo = new voKeyBehaviour();
  			   vo.setKeyBehaviourID(rs.getInt("KeyBehaviourID"));
  			   vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
  			   v.add(vo);
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getKBList - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}

	/**
	 * 	Draw bar chart for competency report.
	 */
	public void drawChart(String Rating [], double Result [], int type, int maxScale) throws IOException, Exception 
	{	
		//System.out.println("draw open");
		XSpreadsheet xSpreadsheet2 = OO.getSheet(xDoc, "Sheet2");
		int r = row;
		int c = 0;
		
		if(iReportType == 1)
		{
			//Print heading for "N" and align
			OO.insertString(xSpreadsheet, "N", r, c+5);
			OO.setCellAllignment(xSpreadsheet, c+5, c+5, r, r, 1, 1);
		}
			
		for(int i=0; i<Rating.length; i++) 
		{
			if (Rating[i] !=null)
			{
				r++;
				OO.insertString(xSpreadsheet, Rating[i], r, c+2);
				OO.mergeCells(xSpreadsheet, c+2, c+3, r, r);
				
				OO.insertNumeric(xSpreadsheet, Result[i], r, c+4);
				OO.insertNumeric(xSpreadsheet, arrN[i], r, c+5);
			}
		}
		
		r = row; //reset
		if(iReportType == 2) //Standard Report
		{
			//draw chart
	 		for(int i=0; i<Rating.length; i++) 
	 		{
	 			if (Rating[i] !=null)
	 			{
					OO.insertString(xSpreadsheet2, Rating[i], r, c);
					OO.insertNumeric(xSpreadsheet2, Result[i], r, c+1);
					r++;
	 			}
			}
	
	 		long then = System.currentTimeMillis();
	 		long now = System.currentTimeMillis();
	 		
			XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, c, c+1, row-1, r-1, Integer.toString(row), 9000, 7800, row, 2);
			OO.setFontSize(8);
			long now1 = System.currentTimeMillis();
			now = System.currentTimeMillis();
			xtablechart = OO.setChartTitle(xtablechart, "");
			 
			now1 = System.currentTimeMillis();
			//System.out.println("set chart title: " + (now1 - now) / 1000);
			///System.out.println("MaxScale---" + maxScale);
			xtablechart = OO.setAxes(xtablechart, "", "", maxScale, 1, 0, 0,0);
			now = System.currentTimeMillis();
			//System.out.println("SEt axis: " + (now - now1) / 1000);
			OO.setChartProperties(xtablechart, false, true, false, true, true);
			OO.showLegend(xtablechart, false);
			now = System.currentTimeMillis();
			//System.out.println( "Time : " + (now - then)/1000 );
		}
		
		//System.out.println("draw - close");
	}

	/**
	 * get CP score for self
	 * @param iFKComp
	 * @return
	 * @throws SQLException
	 * @see generateChart
	 */
	public double getCPSelf(int iFKComp) throws SQLException 
	{
		String query = "";
		
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		
		double dScore = -1;
		
		if(reliabilityCheck == 0) 	// trimmed mean
		{		
			query = query + "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
			query += "ROUND(AVG(tblTrimmedMean.TrimmedMean), 2) AS Result FROM ";
			query += "tblTrimmedMean INNER JOIN Competency ON ";
			query += "tblTrimmedMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query += "tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query += "[User] ON [User].PKUser = tblTrimmedMean.TargetLoginID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
			query += " AND tblTrimmedMean.Type = 4 AND tblRatingTask.RatingCode = 'CP' AND ";
			query += "tblTrimmedMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
			query += "WHERE SurveyID = " + surveyID + " AND Competency.PKCompetency = " + iFKComp + " AND RaterCode = 'SELF' AND ";
			query += "RaterStatus IN (1, 2, 4) ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				
			query += ") ";
			
			query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
			query += "ORDER BY Competency.CompetencyName";
			
		}
		else 
		{
			query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
			query += "ROUND(AVG(tblAvgMean.AvgMean), 2) AS Result FROM ";
			query += "tblAvgMean INNER JOIN Competency ON ";
			query += "tblAvgMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query += "[User] ON [User].PKUser = tblAvgMean.TargetLoginID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID;
			query += " AND tblAvgMean.Type = 4 AND tblRatingTask.RatingCode = 'CP' AND ";
			query += "tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
			query += "WHERE SurveyID = " + surveyID + " AND Competency.PKCompetency = " + iFKComp + " AND RaterCode = 'SELF' AND ";
			query += "RaterStatus IN (1, 2, 4) ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				
			query += ") ";
			
			query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
			query += "ORDER BY Competency.CompetencyName";

		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   dScore = rs.getDouble("Result");
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getCPSelf - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return dScore;
	}	
	
	/**
	 * 	Retrieves the KB results under that particular rating code
	 *	@param String RTCode	Rating Task Code
	 *	@param iKBID
	 *
	 *	@return CP/CPR score
	 *	@see generateChart()
	 */
	public double getKBCPCPR(int iCompID, int iKBID, String RTCode) throws SQLException 
	{
		String query = "";

		double dScore = -1;
	
		query = "SELECT ROUND(AVG(tblAvgMean.AvgMean), 2) AS Result FROM ";
		query += "tblAvgMean INNER JOIN ";
		query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
		query += "[User] ON [User].PKUser = tblAvgMean.TargetLoginID ";
		query += "WHERE tblAvgMean.SurveyID = " + surveyID ;
		query += " AND CompetencyID = " + iCompID + " AND KeyBehaviourID = " + iKBID+ " AND tblAvgMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "' AND ";
		query += "tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
		query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
		query += "WHERE SurveyID = " + surveyID + " AND RaterCode <> 'SELF' AND ";
		query += "RaterStatus IN (1, 2, 4) ";
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		query += ") ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   dScore = rs.getDouble("Result");
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getKBCPCPR - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return dScore;
	}	
	
	/**
	 * Retrieve the value for CP Self at KB Level
	 * @param iKBID
	 * @return CP Score at KB Level
	 * @throws SQLException
	 * @see generateChart()
	 */
	public double getKBCPSelf(int iCompID, int iKBID) throws SQLException 
	{
		String query = "";

		double dScore = -1;
	
		query = "SELECT ROUND(AVG(tblAvgMean.AvgMean), 2) AS Result FROM ";
		query += "tblAvgMean INNER JOIN ";
		query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
		query += "[User] ON [User].PKUser = tblAvgMean.TargetLoginID ";
		query += "WHERE tblAvgMean.SurveyID = " + surveyID ;
		query += " AND CompetencyID = " + iCompID + " AND KeyBehaviourID = " + iKBID+ " AND tblAvgMean.Type = 4 AND tblRatingTask.RatingCode = 'CP' AND ";
		query += "tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
		query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
		query += "WHERE SurveyID = " + surveyID + " AND RaterCode = 'SELF' AND ";
		query += "RaterStatus IN (1, 2, 4) ";
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		query += ") ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   dScore = rs.getDouble("Result");
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getKBCPSelf - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		return dScore;
	}	
	
	/**
	 * 	Count total raters in that particular group.
	 *	@param int group	GroupID (1=ALL, 2=SUP, 3=OTH, 4=SELF)
	 *
	 *	@return int TotalRaters		Total no. of raters
	 */
	public int getTotalRaters(int group) throws SQLException 
	{
		String query = "";
		String filter = "";
		int total = 0;
		
		switch(group) {	// 1 for all, 4 for self
			case 1: filter = "tblAssignment.RaterCode <> 'SELF'";
					break;	
			case 2: filter = "tblAssignment.RaterCode LIKE 'SUP%'";
					break;	
			case 3: filter = "tblAssignment.RaterCode LIKE 'OTH%'";
					break;	
			case 4: filter = "tblAssignment.RaterCode = 'SELF'";
					break;	
		}
		
		query = "SELECT COUNT(tblAssignment.RaterLoginID) AS Total FROM ";
		query = query + "tblAssignment INNER JOIN [User] ON ";
		query = query + "tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.RaterStatus IN (1, 2, 4)";
		query = query + " AND " + filter;
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
		
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   total = rs.getInt(1);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalRaters - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return total;
		
		
	}
	
	/**by Santoso 2008/10/29
	 * Count total rater for the particular survey
	 * for competency level
	 */	
	public int totalRater(String raterCode, int iRatingTaskID, int iCompetencyID, int KBID) throws SQLException 
	{	
		int total = 0;
		SurveyResult SR = new SurveyResult();
		Calculation cal = new Calculation();
		String query = "select count(*) AS Total ";
		query = query + " From( ";
		query = query + " SELECT DISTINCT tblAssignment.RaterCode, tblAssignment.TargetLoginID";
		query = query + " FROM         tblAssignment INNER JOIN ";
		query = query + " tblResultBehaviour ON tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
		query = query + " KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + " WHERE     (tblAssignment.SurveyID =  " + surveyID + ") " ;
		if (cal.NAIncluded(surveyID)==0) {
			query = query + " AND RaterCode LIKE '"+raterCode+"' and RaterStatus in(1,2,4)";
			query = query + " AND (tblResultBehaviour.Result <> 0)";
		}
		else
			query = query + " AND RaterCode LIKE '"+raterCode+"' and RaterStatus in(1,2,4,5)";

		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
		
		
		query = query + "  AND (tblResultBehaviour.RatingTaskID = "+iRatingTaskID+") "; 
		if (KBID == 0) {
			query = query + "and (KeyBehaviour.FKCompetency = "+iCompetencyID +") ";
		} else {
			query = query + "and (KeyBehaviour.PKKeyBehaviour = "+KBID +") ";
		}
		query = query + "  ) table1 ";


		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
	 			total = rs.getInt(1);


  		}catch(Exception ex){
			System.out.println("IndividualReport.java - totalRater - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return total;

	}
	
	/**
	 * Returns total number of raters specified in the raterType
	 * This method is a fix for getTotalRaters (table tblResultBehaviour is used in the query)
	 * @param sRaterType
	 * @param iRatingTaskID
	 * @param iCompetencyID
	 * @return
	 * @throws SQLException
	 */
	public int getTotalRaters1(String sRaterType, int iRatingTaskID, int iCompetencyID) throws SQLException
	{
		StringBuilder query = new StringBuilder();
		int total = 0;
		Calculation cal = new Calculation();
		query.append("select max(table1.Cnt)AS Total  From( ")
		.append("SELECT COUNT(tblAssignment.RaterCode) AS Cnt,tblResultBehaviour.KeyBehaviourID ")  
		.append("FROM tblAssignment INNER JOIN  tblResultBehaviour ") 
		.append("ON tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ") 
		.append("INNER JOIN  KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ")  
		.append("INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser ") 
		.append("WHERE (tblAssignment.SurveyID =").append(surveyID).append(") ")
		.append("AND RaterCode LIKE '").append(sRaterType).append("' ");
		if(divID != 0) 
			query.append(" AND tblAssignment.FKTargetDivision = ").append(divID);
			
		if(deptID != 0) 
			query.append(" AND tblAssignment.FKTargetDepartment = ").append(deptID);
			
		if(groupSection != 0)
			query.append(" AND tblAssignment.FKTargetGroup = ").append(groupSection);
			
		if (cal.NAIncluded(surveyID) == 0)
		{
			query.append(" AND tblAssignment.RaterStatus IN (1,2,4) ");
			query.append(" AND (tblResultBehaviour.Result <> 0) ");
		}
		else
		{
			query.append(" AND tblAssignment.RaterStatus IN (1,2,4,5) ");				
		}
		query.append(" AND (tblResultBehaviour.RatingTaskID = ").append(iRatingTaskID).append(") ")
		.append("and (KeyBehaviour.FKCompetency = ").append(iCompetencyID).append(") ")  
		.append("group by tblResultBehaviour.KeyBehaviourID   ) table1"); 
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query.toString());
  		   
  		   if(rs.next()) {
  			   total = rs.getInt(1);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalRaters - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		System.out.println(query);	
		return total;
	}
	
	/**
	 * get the total number of raters. specify the rater type in the parameter
	 * 
	 * @param RaterType
	 * @return
	 * @throws SQLException
	 * @see printCompetency
	 * edit by Ha 07/07/08 add Rating task and CompetencyId to method signature
	 * To calculate total raters for each rating task of each Competency
	 */
	public int getTotalRaters(String sRaterType, int iRatingTaskID, int iCompetencyID) throws SQLException 
	{
		String query = "";
		int total = 0;
		Calculation cal = new Calculation();
		//Query changed by HA 07/07/08 so the total rater can be calculated correctly 
		//
		query = query + "SELECT COUNT(tblAssignment.RaterCode) AS Total FROM tblAssignment INNER JOIN ";
		query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + " INNER JOIN tblResultCompetency ON tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
		
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		query = query + " AND tblAssignment.RaterCode LIKE '"+ sRaterType +"'";
		if (cal.NAIncluded(surveyID) == 0)
		{
			query = query + " AND tblAssignment.RaterStatus IN (1,2,4)";
			query = query + " AND tblResultCompetency.Result <> 0 ";
		}
		else
		{
			query = query + " AND tblAssignment.RaterStatus IN (1,2,4,5)";				
		}
		query = query  + " AND tblResultCompetency.RatingTaskID = "+iRatingTaskID;
		query = query  + " AND tblResultCompetency.CompetencyID = "+iCompetencyID;
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   total = rs.getInt(1);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalRaters - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		System.out.println(query);	
		return total;
	}
	
	/**
	 * 	Count total other rating tasks besides CP for the particular survey
	 *
	 *	@return int total	Total no. of RatingCode in tblRatingTask
	 *	@see printCompetency
	 */
	public int getTotalOtherRaters() throws SQLException 
	{
		String query = "";
		int total = 0;

		query = query + "SELECT COUNT(distinct(tblRatingTask.RatingCode)) AS TotalRT ";
		query = query + "FROM tblSurveyRating INNER JOIN tblRatingTask ON ";
		query = query + "tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID ";
		query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
		query = query + " AND (tblRatingTask.RatingCode = 'CPR' or tblRatingTask.RatingCode = 'FPR')";
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   total = rs.getInt(1);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalOtherRaters - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		return total;
	}

	/**
	 * Get the maximum rating scale for alignment purposes.
	 * 
	 * @return int MaxScale
	 * @throws SQLException
	 */
	public int getMaxScale() throws SQLException {
		String query = "";
		int total = 0;
		
		query = query + "SELECT MAX(tblScale.ScaleRange) AS Result FROM ";
		query = query + "tblScale INNER JOIN tblSurveyRating ON ";
		query = query + "tblScale.ScaleID = tblSurveyRating.ScaleID WHERE ";
		query = query + "tblSurveyRating.SurveyID = " + surveyID;
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   total = rs.getInt(1);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getMaxScale - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		return total;
	}
	
	public voImportance getImportance(int compID, int KBID) throws SQLException {
		voImportance vo = new voImportance() ;
		
		String query = "";
		
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		
		if(reliabilityCheck == 0 && KBID == 0) 	// trimmed mean
		{		
			query = query + "SELECT ROUND(AVG(tblTrimmedMean.TrimmedMean), 2) AS Result, RatingTask ";
			query = query + "FROM tblTrimmedMean INNER JOIN ";
			query = query + "tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "WHERE tblTrimmedMean.CompetencyID = " + compID;									
			//Added by Ha 21/07/08 TrimmMean.Type = 1 to calculate the average CI in the group report correctly
			//Problem with old query: calculate the average mean of CI using wrong value
			query = query + " AND (tblRatingTask.RatingCode = 'IF' or tblRatingTask.RatingCode = 'IN') AND (tblTrimmedMean.SurveyID = "+surveyID+") AND tblTrimmedMean.Type = 1 AND (tblTrimmedMean.TargetLoginID IN ";
			query = query + "(SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query = query + "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";                                              
			query = query + "WHERE SurveyID = " +surveyID+ " AND RaterCode <> 'SELF' AND RaterStatus IN (1, 2, 4) ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				
			query += "))  Group BY tblRatingTask.RatingTask";
		}
		else 
		{
			
			query = query + "SELECT ROUND(AVG(tblAvgMean.AvgMean),2) AS Result, RatingTask ";
			query = query + "FROM tblAvgMean INNER JOIN ";
			query = query + "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "WHERE tblAvgMean.CompetencyID = " + compID;

			if(KBID != 0) 
				query = query + " AND tblAvgMean.KeyBehaviourID = " + KBID;										
			//Added by Ha 21/07/08 tblAvgMean.Type = 1 to calculate the average IN of group correctly
			query = query + " AND (tblRatingTask.RatingCode = 'IF' or tblRatingTask.RatingCode = 'IN') AND (tblAvgMean.SurveyID = "+surveyID+") AND tblAvgMean.Type = 1 AND (tblAvgMean.TargetLoginID IN ";
			query = query + "(SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
			query = query + "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";                                              
			query = query + "WHERE SurveyID = " +surveyID+ " AND RaterCode <> 'SELF' AND RaterStatus IN (1, 2, 4) ";
			
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
			
			query += "))  Group BY tblRatingTask.RatingTask";
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   if(rs.next()) {
  			   //Added Rounding by Ha 07/07/08, sometimes the Round in SQL does not work
  			   	vo.setRatingTask(rs.getString("RatingTask"));
  			   	double result = rs.getDouble("Result");
				BigDecimal bd = new BigDecimal(result);
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
				result = bd.doubleValue();
  			   	vo.setResult(result);
  		   }

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getImportance - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		return vo;
	}
	
	/**
	 * 	Retrieves the group level of agreement from tblLevelOfAgreement based on Competency and Key Behaviour.
	 * 	KBID = 0 if it is Competency Level Analysis.
	 *  type = 0 Competency Level
	 *  type = 1 KB Level
	 *  
	 *	@param int compID	CompetencyID
	 *	@param int KBID		KeyBehaviourID
	 *
	 *	@return double LevelofAgreement
	 *
	 *	@see getLOABase(int iNoOfRaters)
	 * 	@see MaxScale(int surveyID)
	 */
	/*public double LevelOfAgreement(int compID, int KBID) throws SQLException, Exception
	{
		String query = "";
		
		//double LOA = 0;
		
		double LOA = 100; //Default 100%
		
		
		query = query + "SELECT ROUND(AVG(tblLevelOfAgreement.LevelOfAgreement), 2) AS LOA ";
		query = query + "FROM tblLevelOfAgreement INNER JOIN ";
		query = query + "tblRatingTask ON tblLevelOfAgreement.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
		query = query + "[User] ON [User].PKUser = tblLevelOfAgreement.TargetLoginID ";
		query = query + " WHERE tblLevelOfAgreement.CompetencyID = " + compID;
		
		if(KBID != 0) 
			query = query + " AND tblLevelOfAgreement.KeyBehaviourID = " + KBID;										
		
		query = query + " AND (tblRatingTask.RatingCode = 'CP') AND (tblLevelOfAgreement.SurveyID = "+surveyID+") AND (tblLevelOfAgreement.TargetLoginID IN ";
		query = query + "(SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
		query = query + "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";                                              
		query = query + "WHERE SurveyID = " +surveyID+ " AND RaterCode <> 'SELF' AND RaterStatus IN (1, 2, 4) ";
		
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		query += "))";
	
		if(db.con == null) 
			db.openDB();
			
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		if(rs.next())
			LOA = rs.getDouble("LOA");
		
		
		rs.close();
		stmt.close();
		return LOA;
	}*/
	
	/**
	 * 	Retrieves the group level of agreement from tblLevelOfAgreement based on Competency and Key Behaviour.
	 * 	KBID = 0 if it is Competency Level Analysis.
	 * 
	 *	@param int compID	CompetencyID
	 *	@param int KBID		KeyBehaviourID
	 *
	 *	@return double LevelofAgreement
	 *
	 *	@see getLOABase(int iNoOfRaters)
	 * 	@see MaxScale(int surveyID)
	 *  
	 *  Modified by Jenty 20 Oct 06 to fix the LOA for KB
	 *  LOA for group must be the average of the LOA for each individual, cannot take from raters' input
	 */
	public double LevelOfAgreement(int compID, int KBID) throws SQLException, Exception
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		//double LOA = 0;
		
		//Get No of Raters
		int iNoOfRaters = assign.getTotRatersCompleted(surveyID, groupSection, deptID, divID);
		//int iBase = C.getLOABase(iNoOfRaters);
		//int iMaxScale = rscale.getMaxScale(surveyID);
		double LOA = 100; //Default 100%
		
		if(surveyLevel == 0) 
		{
/*			query = query + "SELECT CAST(100 - (STDEV(tblResultCompetency.Result * 10 / " + iMaxScale + ") * " + iBase + ") AS numeric(38, 2)) AS LOA ";
			query = query + "FROM [User] INNER JOIN tblAssignment ON ";
			query = query + "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN ";
			query = query + "tblResultCompetency ON ";
			query = query + "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
			query = query + "INNER JOIN tblRatingTask ON ";
			query = query + "tblResultCompetency.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;			
			query = query + " and tblResultCompetency.CompetencyID = " + compID;										
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;
			
			query = query + " AND tblAssignment.RaterStatus IN (1, 2, 4) AND ";
			query = query + "tblAssignment.RaterCode <> 'SELF' ";
			query = query + "AND tblRatingTask.RatingCode = 'CP' ";
			query = query + "GROUP BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID ";
			query = query + "ORDER BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID";
*/			
			/**
			 * This portion is modified by Jenty
			 * 20 Oct 06
			 */
			query = "SELECT ROUND(AVG(LevelOfAgreement), 2) AS LOA FROM tblLevelOfAgreement WHERE SurveyID = " + surveyID + 
					"AND CompetencyID = " + compID + " and TargetLoginID IN " + 
					"(SELECT DISTINCT TargetLoginID FROM tblAssignment WHERE SurveyID = " + surveyID;
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") GROUP BY CompetencyID";
			
		} 
		else 
		{
/*			query = query + "SELECT CAST(100 - STDEV(tblResultBehaviour.Result * 10 / " + iMaxScale + ") * " + iBase + " AS numeric(38, 2)) AS LOA ";
			query = query + "FROM [User] INNER JOIN tblAssignment ON ";
			query = query + "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN ";
			query = query + "tblResultBehaviour ON ";
			query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
			query = query + "KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "INNER JOIN tblRatingTask ON ";
			query = query + "tblResultBehaviour.RatingTaskID = tblRatingTask.RatingTaskID ";
								
			
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			
			query = query + " and KeyBehaviour.FKCompetency = " + compID;
			query = query + " and tblResultBehaviour.KeyBehaviourID = " + KBID;
								
			query = query + " and tblAssignment.RaterStatus IN (1, 2, 4) AND tblAssignment.RaterCode <> 'SELF' ";
			query = query + "AND tblRatingTask.RatingCode = 'CP' ";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;

				
			query = query + " GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ORDER BY ";
			query = query + "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID";
*/
			/**
			 * This portion is modified by Jenty
			 * 20 Oct 06
			 */
			query = "SELECT ROUND(AVG(LevelOfAgreement), 2) AS LOA FROM tblLevelOfAgreement WHERE SurveyID = " + surveyID + 
					"AND CompetencyID = " + compID + " AND KeyBehaviourID = " + KBID + " and TargetLoginID IN " + 
					"(SELECT DISTINCT TargetLoginID FROM tblAssignment WHERE SurveyID = " + surveyID;
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") GROUP BY CompetencyID, KeyBehaviourID";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  			//Filter if more than 1 rater, get LOA from calculation, else if 1 Rater, LOA = 100%
  			if(iNoOfRaters > 1)	{
  				if(rs.next())
  					LOA = rs.getDouble("LOA");
  		
  			}

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - LevelOfAgreement - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		
		return LOA;
	}
	
	/**
	 * Calculate the average of group level of agreement for each competency.
	 * This is only apply for KB Level Analysis.
	 *
	 * @param compID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	/*public double AvgLevelOfAgreement(int compID) throws SQLException, Exception
	{
		String query = "";
		//double LOA = -1;
		//Get No of Raters
		int iNoOfRaters = getTotRatersCompleted(surveyID, groupSection, deptID, divID);
		int iBase = C.getLOABase(iNoOfRaters);
		double LOA = 100; //Default 100%
		
		int iMaxScale = rscale.getMaxScale(surveyID); //Get Maximum Scale of this survey
		
		//commented off by yuni
		query = query + "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
		query = query + "CAST(100 - (STDEV(tblResultBehaviour.Result * 10 / " + iMaxScale + ") * " + iBase + ") AS numeric(38, 2)) AS LOA ";
		query = query + "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
		query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
		query = query + "tblRatingTask ON tblResultBehaviour.RatingTaskID = tblRatingTask.RatingTaskID ";
		query = query + "INNER JOIN KeyBehaviour ON ";
		query = query + "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.RaterStatus IN (1, 2, 4) AND KeyBehaviour.FKCompetency = " + compID;
		query = query + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP'";
		
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			//query = query + " AND [User].FKDivision = " + divID;
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			//query = query + " AND [User].FKDepartment = " + deptID;
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			//query = query + " AND [User].Group_Section = " + groupSection;

					
		query = query + " GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency";		

		if(db.con == null) db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		//Filter if more than 1 rater, get LOA from calculation, else if 1 Rater, LOA = 100%
		if(iNoOfRaters > 1)
			if(rs.next())
				LOA = rs.getDouble("LOA");
			
		return LOA;
	}*/
	
	public double AvgLevelOfAgreement(int compID) throws SQLException, Exception
	{
		//String query = "";
		//double LOA = -1;
		//Get No of Raters
		int iNoOfRaters = getTotRatersCompleted(surveyID, groupSection, deptID, divID);
		int iBase = C.getLOABase(iNoOfRaters);
		double avg = 100;
		double LOA = 0; //Default 100%
		
		int iMaxScale = rscale.getMaxScale(surveyID); //Get Maximum Scale of this survey
		
		// Change by Santoso
		// Update query similar to the one in individual report
		String query;
		query = "SELECT tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, tblAssignment.TargetLoginID, count(*) as numOfRaters, ";
		query = query + "stDev(tblAvgMeanByRater.AvgMean * 10 / " + iMaxScale + ") AS LOA ";
		query = query + "FROM tblAssignment INNER JOIN " ;
        query = query + "tblAvgMeanByRater ON tblAssignment.AssignmentID = tblAvgMeanByRater.AssignmentID INNER JOIN ";
        query = query + "tblRatingTask ON tblAvgMeanByRater.RatingTaskID = tblRatingTask.RatingTaskID ";
        query += " INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser ";
        query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.RaterStatus IN (1, 2, 4) AND tblAvgMeanByRater.CompetencyID = " + compID;
		query = query + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP' ";
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
		
		query = query + "GROUP BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, tblAssignment.TargetLoginID";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
//  		Filter if more than 1 rater, get LOA from calculation, else if 1 Rater, LOA = 100%
  			if(iNoOfRaters > 1) {
  				double sum = 0;
  				int count = 0;
  				
  				while(rs.next()) {
  					// Change by Santoso (2008-10-29) : adapt the code from IndividualReport
  					LOA = 100-rs.getDouble("LOA")*C.getLOABase(rs.getInt("numOfRaters"));
  					BigDecimal bd = new BigDecimal(LOA);
  					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); //round to 2 decimal place
  					LOA = bd.doubleValue();
  					sum = sum + LOA;
  					count ++;
  					//System.out.println(LOA +"--------" + rs.getInt("TargetLoginID"));
  				}
  				
  				avg = sum/count;
  				avg = Math.round(avg*100)/100.0;
  			
  			}

  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - AvgLevelOfAgreement - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return avg;
	}
	
	//not in use
	public double AvgLevelOfAgreement(int compID, int noOfRaters) throws SQLException 
	{	
		String query = "";
		int iBase = C.getLOABase(noOfRaters);
		double LOA = -1;
		int iMaxScale = rscale.getMaxScale(surveyID); //Get Maximum Scale of this survey
		
		/*query = query + "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
		query = query + "cast((100-(stDev(tblResultBehaviour.Result * 10 / " + iMaxScale + ") * " + iBase + ")) AS numeric(38, 2)) AS LOA ";
		query = query + "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
		query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
		query = query + "tblRatingTask ON tblResultBehaviour.RatingTaskID = tblRatingTask.RatingTaskID ";
		query = query + "INNER JOIN KeyBehaviour ON ";
		query = query + "tblResultBehaviour.KeyBehaviourrID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.TargetLoginID = " + targetID + " AND ";
		query = query + "tblAssignment.RaterStatus IN (1, 2, 4) AND KeyBehaviour.FKCompetency = " + compID;
		query = query + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP' ";
		query = query + "GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency";		
		*/
		
		// ORIGINAL by yuni. 
		/*query = query + "SELECT tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, ";
		query = query + "cast((100-(stDev(tblAvgMeanByRater.AvgMean * 10 / " + iMaxScale + ") * " + iBase + ")) AS numeric(38, 2)) AS LOA ";
		query = query + "FROM tblAssignment INNER JOIN " ;
        query = query + "tblAvgMeanByRater ON tblAssignment.AssignmentID = tblAvgMeanByRater.AssignmentID INNER JOIN ";
        query = query + "tblRatingTask ON tblAvgMeanByRater.RatingTaskID = tblRatingTask.RatingTaskID ";
        query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.TargetLoginID = " + targetID + " AND ";
		query = query + "tblAssignment.RaterStatus IN (1, 2, 4) AND tblAvgMeanByRater.CompetencyID = " + compID;
		query = query + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP' ";
		query = query + "GROUP BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID";		
		*/
		
		query += "SELECT DISTINCT ";
        query += "tblAssignment.TargetLoginID, tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, ";
        query += "CAST(100 - STDEV(tblAvgMeanByRater.AvgMean * 10 / "+iMaxScale+") * "+iBase+" AS numeric(38, 2)) AS LOA, [User].LoginName, [User].GivenName ";
        query += "FROM tblAssignment INNER JOIN ";
        query += "tblAvgMeanByRater ON tblAssignment.AssignmentID = tblAvgMeanByRater.AssignmentID INNER JOIN ";
        query += "tblRatingTask ON tblAvgMeanByRater.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
        query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
        query += "WHERE (tblAssignment.SurveyID = "+surveyID+") AND (tblAssignment.RaterStatus IN (1, 2, 4)) AND (tblAvgMeanByRater.CompetencyID = "+compID+") AND ";
        query += "(tblAssignment.RaterCode <> 'SELF') AND (tblRatingTask.RatingCode = 'CP') ";
        query += "GROUP BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, tblAssignment.TargetLoginID, [User].LoginName, [User].GivenName ";
     
		double sum = 0;
		double avg = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   
  		   while(rs.next()) {
  			   LOA = rs.getDouble("LOA");
  			   sum = sum + LOA;
  		   }
 		
  		   avg = sum/noOfRaters;
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - AvgLevelOfAgreement - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return avg;
	}	
	/**
	 * get the total numbers completed for a particular survey
	 * 
	 * @param SurveyID
	 * @param GroupSect
	 * @param DeptID
	 * @param DivID
	 * @return integer total
	 * @throws SQLException
	 * @throws Exception
	 * @see AvgLevelOfAgreement()
	 */
	public int getTotRatersCompleted(int SurveyID, int GroupSect, int DeptID, int DivID) throws SQLException, Exception 
	{
		int TotRatersCompleted = 0;
		
		String command = "SELECT COUNT(*) as TotRatersCompleted FROM tblAssignment INNER JOIN ";
		command = command + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		command = command + "WHERE (tblAssignment.SurveyID = " + SurveyID + ") AND (tblAssignment.RaterStatus IN (1,2,4)) ";
		command = command + "AND (tblAssignment.RaterCode NOT LIKE '%self%') ";
		
		if (GroupSect != 0)
			command = command + "AND (tblAssignment.FKTargetGroup = '" + GroupSect + "') ";
			
		if (DeptID != 0)
			command = command + "AND (tblAssignment.FKTargetDepartment = " + DeptID + ") ";

		if (DivID != 0)
			command = command + "AND (tblAssignment.FKTargetDivision = " + DivID + ")";
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(command);
  		 
  		   if(rs.next())
  			   TotRatersCompleted = rs.getInt("TotRatersCompleted");
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotRatersCompleted - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return TotRatersCompleted;
	}
	
	/*************************END COMPETENCY*************************************************************/
	
	
	
	/**************************COMMENTS****************************************************************/
	/**
	 * Write comments on excel.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 * @see generateReport
	 */
	public void printComments() throws SQLException, IOException, Exception 
	{
		System.out.println("      - Insert Comments");
	
		//check for the survery level
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		
		column = 0;
		
		OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+2, 2, 1);
		row++;
		
		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, "Narrative Comments", row, column);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Komentar Naratif", row, column);
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);	
		row += 2;
		
		int startBorder = row;
		int endBorder = 1;
		int selfIncluded = Q.SelfCommentIncluded(surveyID);
		int column = 0;

		int count = 0;			
	
		int borderArr [] = new int [2];
		
		for(int i=0; i<vCompDetails.size(); i++)
		{
			
			voCompetency voComp = (voCompetency)vCompDetails.elementAt(i);
			count++;
			int compID = voComp.getCompetencyID();
			String statement = voComp.getCompetencyName();
			
			if(i == 0)
			{
				OO.insertPageBreak(xSpreadsheet, startColumn, endColumn, row-3);
			}
		
			//OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+5, 1, 1);
			
			OO.insertString(xSpreadsheet, Integer.toString(count) + ".", row, column);								
			
			OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(statement), row, column+1);
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, endColumn, row, row, BGCOLOR);
			
			int KBID = 0;
		
			if(surveyLevel == 0) {
			
				borderArr = generateComments(compID, KBID, selfIncluded, startBorder, endBorder);
			
				startBorder = borderArr[0];
				endBorder = borderArr[1];
				
				//Added by Tracy 01 Sep 08
				//Adjust rows in "Narrative comments" part so that this part does not
				// overlap with "Group Distribution report"
				OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
				//End Tracy add 01 Sep 08**

			} else if(surveyLevel == 1) {
				Vector vKBDetails = getKBList(compID);			
				row++;
				
				for(int j=0; j<vKBDetails.size(); j++) {
					voKeyBehaviour voKB = (voKeyBehaviour)vKBDetails.elementAt(j);
					
					KBID = voKB.getKeyBehaviourID();
					String KB = voKB.getKeyBehaviour();
					
					OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
					OO.insertString(xSpreadsheet, "-", row, column);
					OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(KB), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(KB, 100));
					OO.setCellAllignment(xSpreadsheet, startColumn, startColumn, row, row, 2, 1);
												 					
					//int isThai = KB.indexOf("&#");
					borderArr = generateComments(compID, KBID, selfIncluded, startBorder, endBorder);
					startBorder = borderArr[0];
					endBorder = borderArr[1];
				}
				
			} 
			
			/*if(i == (vCompDetails.size() -1))
			{
				OO.insertPageBreak(xSpreadsheet, startColumn, endColumn, row);
			}*/
		
		}
		System.out.println("End of inserting comment");
	}
	
	public int [] generateComments(int compID, int KBID, int selfIncluded, int startBorder, int endBorder) throws Exception {
		
		int start = 0;
		int chartArr [] = {0,0}; 
		row++;
		
		int countRecord = 0;
		
		/*=========================SELF======================================================*/
		
		
		if(start == 0) {																
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			
			if (ST.LangVer == 1)
				OO.insertString(xSpreadsheet, "Self", row, column+1);
			else if (ST.LangVer == 2)
				OO.insertString(xSpreadsheet, "Diri Sendiri", row, column+1);
				
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setFontItalic(xSpreadsheet, startColumn, endColumn, row, row);
			
			row++;								
			start++;
		}
		
		if(selfIncluded == 1) {
			Vector selfComments = getComments("SELF", compID, KBID);
			
			if(selfComments != null) {
				for(int i=0; i<selfComments.size(); i++) {
					
					String [] arr = (String[])selfComments.elementAt(i);
					String comment = arr[1].trim();
					
					OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
					
					/*String [] comments = comment.split("\n");
					
					for(int m=0; m<comments.length; m++) {
						comments[m] = comments[m].trim();
						if(m!=0) 
							row = row+1;
						
						OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comments[m]), row, column+1);
						OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
						OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
						
					}*/
					OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comment), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
					
					OO.setCellAllignment(xSpreadsheet, startColumn, startColumn, row, row, 2, 1);
					
					row++;		
					countRecord++;
				}//while(selfComments.next())
			
			} 
		}//if(selfIncluded)*/
	
		if(countRecord == 0) {
			OO.insertString(xSpreadsheet, "- No comments given", row, column+1);
			row++;
		}
		
		start = 0;
		countRecord = 0;
		
		if(start == 0) 
		{				
			
	
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			
				
			OO.insertString(xSpreadsheet, "Supervisors", row, column+1);
			OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);			
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);	
			OO.setFontItalic(xSpreadsheet, startColumn, endColumn, row, row);
			
			
			row++;						
			start++;
		}
	
		Vector supComments = getComments("SUP%", compID, KBID);
		Vector othComments = getComments("OTH%", compID, KBID);
		
		/* ================================== SUPERVISOR ======================================== */
		
		if(supComments != null) {		
			
			for(int i=0; i<supComments.size(); i++) {
				
				String [] arr = (String[])supComments.elementAt(i);
				String comment = arr[1].trim();
				
				OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);				
				
				/*String [] comments = comment.split("\n");
				
				for(int m=0; m<comments.length; m++) {
					comments[m] = comments[m].trim();
					if(m!=0) 
						row = row+1;
				
					OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comments[m]), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
					
				}*/
				
				//String comment = selfComments.getString("Comment");
				
					OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comment), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
					
					OO.setCellAllignment(xSpreadsheet, startColumn, startColumn, row, row, 2, 1);
					
					row++;
					countRecord++;
				
				
			}
		
		} 
		
		if(countRecord == 0) {
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			OO.insertString(xSpreadsheet, "- No comments given", row, column+1);
			row++;
		}
		
		start = 0;
		countRecord = 0;
		
		/* ================================== OTHERS ======================================== */
		
		if(start == 0) 
		{
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			if (ST.LangVer == 1)
				OO.insertString(xSpreadsheet, "Others", row, column+1);					
			else if (ST.LangVer == 2)
				OO.insertString(xSpreadsheet, "Lainnya", row, column+1);
									
			OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
			OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
			OO.setFontItalic(xSpreadsheet, startColumn, endColumn, row, row);
			
			start++;
			row++;
		}	
		
		if(othComments != null) {
			for(int i=0; i<othComments.size(); i++) {
				
				String [] arr = (String[])othComments.elementAt(i);
				String comment = arr[1].trim();
										
				OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
				/*String [] comments = comment.split("\n");
				
				for(int m=0; m<comments.length; m++) {
					comments[m] = comments[m].trim();
					if(m!=0) 
						row = row+1;
				
					OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comments[m]), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
					
				}*/
				
					OO.insertString(xSpreadsheet, "- " + UnicodeHelper.getUnicodeStringAmp(comment), row, column+1);
					OO.mergeCells(xSpreadsheet, column+1, endColumn, row, row);
					OO.setRowHeight(xSpreadsheet, row, column+1, ROWHEIGHT*OO.countTotalRow(comment, 100));
					
					OO.setCellAllignment(xSpreadsheet, startColumn, startColumn, row, row, 2, 1);
					
					
					row++;	
					countRecord++;
				
			}
		
		} 
		
		if(countRecord == 0) {
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
			OO.insertString(xSpreadsheet, "- No comments given", row, column+1);
			row++;
		}
		
		OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+1, 1, 1);
		endBorder = row;
		chartArr[1] = endBorder;

		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, startBorder, endBorder, 
						false, false, true, true, true, true);
	    row++;
		startBorder = row;

		chartArr[0] = startBorder;
		
		return chartArr;
	}
	
	/**
	 * 
	 * Retrieves all the comments input upon fill in the questionnaire
	 * @param String raterCode
	 * @param int compID	CompetencyID
	 * @param int KBID		KeyBehaviourID
	 * @return
	 * @throws SQLException
	 * 
	 * @see printComments
	 */
	public Vector getComments(String raterCode, int compID, int KBID) throws SQLException 
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		
		if(surveyLevel == 0) {
			query = query + "SELECT Competency.CompetencyName, tblComment.Comment ";
			query = query + "FROM tblAssignment INNER JOIN tblComment ON ";
			query = query + "tblAssignment.AssignmentID = tblComment.AssignmentID INNER JOIN ";
			query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query = query + "Competency ON tblComment.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.RaterCode LIKE '" + raterCode + "'";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;
			
			query = query + " AND Competency.PKCompetency = " + compID;
			query = query + " ORDER BY tblComment.Comment";
		} else {
			query = query + "SELECT Competency.CompetencyName, tblComment.Comment ";
			query = query + "FROM tblAssignment INNER JOIN tblComment ON ";
			query = query + "tblAssignment.AssignmentID = tblComment.AssignmentID INNER JOIN ";
			query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query = query + "Competency ON tblComment.CompetencyID = Competency.PKCompetency ";
			query = query + "INNER JOIN KeyBehaviour ON ";
			query = query + "tblComment.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.RaterCode LIKE '" + raterCode + "'";
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;
			
			query = query + " AND Competency.PKCompetency = " + compID;
			query = query + " AND KeyBehaviour.PKKeyBehaviour = " + KBID;
			query = query + " ORDER BY tblComment.Comment";												
		}
			

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		 
  		   while(rs.next()) {
  			   String [] arr = new String[2];
  			   arr[0] = rs.getString(1);
  			   arr[1] = rs.getString(2);
  			   v.add(arr);
 		
  		   }
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getComments - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**************************end COMMENTS**************************************************************************/
	
	
	
	/***********************START COMPETENCY DISTRIBUTION REPORT***************************************/
	/**
	 * Returns rating scale of the given surveyID
	 */
	private int getRatingScale(int surveyID) {
		String query = "select MAX(ScaleRange) from tblScale INNER JOIN tblSurveyRating " +
						"ON tblScale.ScaleID=tblSurveyRating.ScaleID " +
						"WHERE surveyID = " + surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int result = 10; // default set to 10
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		
		} catch (Exception e) {
			System.err.println("GroupReport.java - getRatingScale - " + e);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return result;
	}
	/**
	 * 	Write Group Competency Distribution Report
	 */
	public void printTotalTargets() throws IOException, Exception 
	{	
		System.out.println("7. Group Competency Distribution Report");
		
		//Added by Tracy 01 Sep 08**********************************
		//int [] titleAddress= OO.findString(xSpreadsheet, "<for CP>");
		String RTaskName="";
		
		// Get CP Rating from database according to survey ID
		String query = "SELECT b.RatingTask as RTaskName FROM tblSurveyRating a ";
		query += "INNER JOIN tblRatingTask b ON a.RatingTaskID=b.RatingTaskID  WHERE a.SurveyID = "+ surveyID + " AND a.RatingTaskID=1";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()) {
				RTaskName= rs.getString("RTaskName");
			}
		
		} catch (Exception E) {
			System.err.println("SurveyResult.java - GroupSection - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		OO.findAndReplace(xSpreadsheet, "<for CP>", "for " + RTaskName);
		
		// End add by Tracy 01 Sep 08******************************
		
		int [] address = OO.findString(xSpreadsheet, "<Total Targets>");

		OO.findAndReplace(xSpreadsheet, "<Total Targets>", " ");
		
		column = address[0];
		row = address[1];
		
		column = 1;
		int count = 0;

		
		for(int a=0; a<vCompDetails.size(); a++)
		{
			//OO.insertRows(xSpreadsheet, startColumn, endColumn, row, row+15, 15, 0);
			OO.insertRows(xSpreadsheet, startColumn, endColumn, row+1, row+16, 15, 0);
			
			int c=0;

			voCompetency voComp = (voCompetency)vCompDetails.elementAt(a);
			int compID = voComp.getCompetencyID();
			String compName = voComp.getCompetencyName();
			
			int total = 0;
		
			// Get total number of Targets in this survey
			// 2 or more targets with same ratings are considered as 1
			total = getTotalAllTargetResults(compID);
			
			int targets [] = new int [total];
			int  result [] = new int [total];
		
			Vector totalTargets = null;
		
			// Get the ratings of all the target in this survey
			totalTargets = getAllResults(compID);
		
			// Count total no of targets for each ratings
			for(int i=0; i<totalTargets.size(); i++) {
				
				result[c] = ((Integer)totalTargets.elementAt(i)).intValue();
				
				targets[c] = totalTargetBasedScore(compID, result[c]);
				
				c++;
			
			}
			
			// Add end of scale if needed
			int ratingScale = getRatingScale(surveyID);
			if (result[c-1] < ratingScale) {
				int[] temp = new int[total+1];
				System.arraycopy(targets, 0, temp, 0, targets.length);
				targets = temp;
				targets[targets.length-1] = 0;
				temp = new int[total+1];
				System.arraycopy(result, 0, temp, 0, result.length);
				result = temp;
				result[result.length - 1] = ratingScale;
			}
		
			OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(compName), row, column);
			OO.setFontBold(xSpreadsheet, column, column, row, row);
			OO.setFontSize(xSpreadsheet, column, column, row, row, 12);
			row = row + 2;
			
			long now = System.currentTimeMillis();
			drawTotalTargets(result, targets);
			long then = System.currentTimeMillis();
			
			System.out.println("Comp Report : " + (then-now)/1000);
			
			count++;
			
			row = row + 13;
			
			if(count == 2 && a != (vCompDetails.size()-1)) {
				count = 0;
				//Insert page break each time after printed 2 charts
				OO.insertPageBreak(xSpreadsheet, startColumn, endColumn, row);
			}			
					
		}	// for(int a=0; a<vCompInfo.size(); a++)
	}
	
	/**
	 * 	Draw line chart for total targets on each rating.
	 */
	public void drawTotalTargets(int result [], int targets []) throws IOException, Exception 
	{			
		int r = row;
		int c = 2;
		int total = result.length;
		
		XSpreadsheet xSpreadsheet2 = OO.getSheet(xDoc, "Sheet2");
		
		// Change by Santoso (2008-11-12)
		// For open office 2.4.1 : reverse the data
//		OO.insertNumeric(xSpreadsheet2, 0, r, c);
//		OO.insertNumeric(xSpreadsheet2, 0, r, c+1);
//		r++;
		
		int maxTotal = 0;//this is to set the maximum height of Y Axis
		for(int i=0; i<total; i++) 
		{
			// Change by Santoso (2008-11-12)
			// For open office 2.4.1 : reverse the data
			OO.insertNumeric(xSpreadsheet2, result[result.length - i - 1], r, c);
			OO.insertNumeric(xSpreadsheet2, targets[result.length - i - 1], r, c+1);
			
			if(maxTotal < targets[i])
				maxTotal = targets[i];
				
			r++;
		}
		// Change by Santoso (2008-11-12)
		// For open office 2.4.1 : reverse the data
		OO.insertNumeric(xSpreadsheet2, 0, r, c);
		OO.insertNumeric(xSpreadsheet2, 0, r, c+1);
		
		String sXAxis = "Ratings";
		String sYAxis = "Number of Targets";

        if (ST.LangVer == 2){
        	sXAxis = "Nilai";
        	sYAxis = "Jumlah Target";
        }
        
        //draw chart 
//		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, c, c+1, row-1, r-1, "Distribution"+(row-1), 10000, 7000, row-1, c);
        // Change by Santoso (2008-11-12)
		// For open office 2.4.1 : reverse the data
        XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, c, c+1, row-1, r, "Distribution"+(row-1), 10000, 7000, row-1, c);
		OO.setFontSize(8);
		xtablechart = OO.setChartTitle(xtablechart, "");
		 sXAxis = "Score";
		 sYAxis = "Number of Targets";
		xtablechart = OO.setAxes(xtablechart, sXAxis, sYAxis, maxTotal, 1, 0, 0,0);
			
		//OO.setAxes(xtablechart, sXAxis, sYAxis, true, false, false, 0, 0);
		//xtablechart = OO.setAxes(xtablechart, sXAxis, sYAxis, maxTotal, 1, false);
		//OO.setChartProperties(xtablechart, false, true, false, true, true);	
		OO.setChartProperties(xtablechart, true, true, true, true, true);
		OO.showLegend(xtablechart, false);
		
		//need to change to LineDiagram and set the scale of xAxis, and also the width of the line
		OO.changeChartType("com.sun.star.chart.LineDiagram", xtablechart);
	}
	
	/**
	 * 	Retrieves the total number of targets and ratings based on competencyID
	 *	@param int compID	CompetencyID
	 *
	 *	@return int TotalAllTarget
	 */
	public int getTotalAllTargetResults(int compID) throws SQLException 
	{
		int totalTarget = 0;
			
		String query = "";
		
		int reliability = C.ReliabilityCheck(surveyID);
		
		if(reliability == 0) 
		{
			/*query = "SELECT COUNT(*) AS Total FROM (SELECT DISTINCT CAST(AVG(tblTrimmedMean.TrimmedMean) AS int) AS Average ";
			query += "FROM tblTrimmedMean INNER JOIN tblAssignment ON tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID + " AND tblTrimmedMean.Type = 1 ";
			query += "AND tblTrimmedMean.CompetencyID = " + compID + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP' ";
			query += " GROUP BY tblTrimmedMean.TargetLoginID ";
			
			if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += "HAVING (";
			
			if(divID != 0) 
			{
				query += " tblAssignment.FKTargetDivision = " + divID;
				
				if(deptID != 0 || groupSection != 0)
					query += " AND ";
			}
			if(deptID != 0) 
			{
				query += " tblAssignment.FKTargetDepartment = " + deptID;
				
				if(groupSection != 0)
					query += " AND ";
			}
			if(groupSection != 0)
				query += " tblAssignment.FKTargetGroup = " + groupSection;
				
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += ")";
			query += ") DERIVEDTBL"; 
			*/
			// Change by Santoso
			// add round
			query = "SELECT count(distinct CAST(ROUND(tblTrimmedMean.TrimmedMean, 0) AS int)) AS Total ";
			query += "FROM tblTrimmedMean INNER JOIN ";
			query += "tblAssignment ON tblTrimmedMean.SurveyID = tblAssignment.SurveyID AND ";
			query += "tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
			query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = 'CP' AND ";
			query += "tblTrimmedMean.CompetencyID = " + compID + " and RaterCode <> 'SELF'";				
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;
		} 
		else 
		{
			//Changed by Ha 09/07/08 from CAST to CAST (ROUND) to round to nearest number 
			// Changed by Santoso 2008/10/29, the query is fine but sometimes
			// Use double round to have the correct rounded value because avg might produce value like 9.499999
			query = "SELECT COUNT(*) AS Total FROM (SELECT DISTINCT CAST (ROUND(ROUND(AVG(tblAvgMean.AvgMean),2),0) as int) AS Average ";
			query += "FROM tblAvgMean INNER JOIN tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID + " AND tblAvgMean.Type = 1 ";
			query += "AND tblAvgMean.CompetencyID = " + compID + " AND tblAssignment.RaterCode <> 'SELF' AND tblRatingTask.RatingCode = 'CP'";
			query += " GROUP BY tblAvgMean.TargetLoginID ";
			
			if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += "HAVING (";
			
			if(divID != 0) 
			{
				query += " tblAssignment.FKTargetDivision = " + divID;
				
				if(deptID != 0 || groupSection != 0)
					query += " AND ";
			}
			if(deptID != 0) 
			{
				query += " tblAssignment.FKTargetDepartment = " + deptID;
				
				if(groupSection != 0)
					query += " AND ";
			}
			if(groupSection != 0)
				query += " tblAssignment.FKTargetGroup = " + groupSection;
				
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += ")";
			query += ") DERIVEDTBL";
		}
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   if(rs.next()) {
  			   totalTarget = rs.getInt(1);
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalAllTargetResults - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
System.out.println(">>>"+query);		
		return totalTarget;
	}			
	
	/**
	 * 	Retrieves all distinct result from particular survey and competencyid
	 *	@param int compID	CompetencyID
	 *	
	 *	@return ResultSet AllResults
	 */
	public Vector getAllResults(int compID) throws SQLException 
	{
		int reliability = C.ReliabilityCheck(surveyID);
		
		String query = "";
		
		if(reliability == 0) {
			/*query = "SELECT DISTINCT CAST(AVG(tblTrimmedMean.TrimmedMean) AS int) AS Result FROM tblTrimmedMean ";
			query += "INNER JOIN tblAssignment ON tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID + " AND tblTrimmedMean.Type = 1 ";
			query += "AND tblRatingTask.RatingCode = 'CP' AND tblTrimmedMean.CompetencyID = " + compID + " AND tblAssignment.RaterCode <> 'SELF'";
			query += " GROUP BY tblTrimmedMean.CompetencyID, tblTrimmedMean.TargetLoginID ";
			
			if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += "HAVING (";
			
			if(divID != 0) 
			{
				query += " tblAssignment.FKTargetDivision = " + divID;
				
				if(deptID != 0 || groupSection != 0)
					query += " AND ";
			}
			if(deptID != 0) 
			{
				query += " tblAssignment.FKTargetDepartment = " + deptID;
				
				if(groupSection != 0)
					query += " AND ";
			}
			if(groupSection != 0)
				query += " tblAssignment.FKTargetGroup = " + groupSection;
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += ")";*/
			// Change by santoso : round the trimmed mean before casting
			query = "SELECT distinct CAST(ROUND(tblTrimmedMean.TrimmedMean,0) AS int) AS Result ";
			query += "FROM tblTrimmedMean INNER JOIN ";
			query += "tblAssignment ON tblTrimmedMean.SurveyID = tblAssignment.SurveyID AND ";
			query += "tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
			query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = 'CP' AND ";
			query += "tblTrimmedMean.CompetencyID = " + compID + " and RaterCode <> 'SELF'";				
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				//query = query + " AND [User].FKDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				//query = query + " AND [User].FKDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				//query = query + " AND [User].Group_Section = " + groupSection;
				

			query += " GROUP BY CAST(Round(tblTrimmedMean.TrimmedMean, 0) AS int)";
			
		} else {
			//Changed by Ha 09/07/08 from CAST to CAST (ROUND) to round to nearest number 
			// Changed by Santoso 2008/10/29, the query is fine but sometimes
			// Use double round to have the correct rounded value because avg might produce value like 9.499999
			query = "SELECT DISTINCT CAST(ROUND(ROUND(AVG(tblAvgMean.AvgMean),2),0) as int) AS Result FROM tblAvgMean ";
			query += "INNER JOIN tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID + " AND tblAvgMean.Type = 1 ";
			query += "AND tblRatingTask.RatingCode = 'CP' AND tblAvgMean.CompetencyID = " + compID + " AND tblAssignment.RaterCode <> 'SELF'";
			query += " GROUP BY tblAvgMean.CompetencyID, tblAvgMean.TargetLoginID ";
			
			if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += "HAVING (";
			
			if(divID != 0) 
			{
				query += " tblAssignment.FKTargetDivision = " + divID;
				
				if(deptID != 0 || groupSection != 0)
					query += " AND ";
			}
			if(deptID != 0) 
			{
				query += " tblAssignment.FKTargetDepartment = " + deptID;
				
				if(groupSection != 0)
					query += " AND ";
			}
			if(groupSection != 0)
				query += " tblAssignment.FKTargetGroup = " + groupSection;
			
			if(divID != 0 || deptID != 0 || groupSection != 0)
				query += ")";
		}
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   while(rs.next()) {
  				   
				v.add(new Integer(rs.getInt(1)));
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getAllResults - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
System.out.println("GET RESULT "+query);	
		return v;
	}
	
	/**
	 * 	Retrieves total target based on the score
	 *	@param int compID	CompetencyID
	 *	@param int score	
	 *
	 *	@return int TargetBasedScore
	 */
	public int totalTargetBasedScore(int compID, int score) throws SQLException 
	{
		int reliability = C.ReliabilityCheck(surveyID);
		int iTotalTarget = 0;
		String query = "";
		
		if(reliability == 0) {
			/*query = "SELECT COUNT(*) AS Total FROM ";
			query += "(SELECT tblTrimmedMean.TargetLoginID FROM tblTrimmedMean INNER JOIN ";
			query += "tblAssignment ON tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID + " AND tblRatingTask.RatingCode = 'CP' ";
			query += "AND tblTrimmedMean.CompetencyID = " + compID + " AND tblTrimmedMean.Type = 1 ";
			query += "GROUP BY tblTrimmedMean.TargetLoginID ";
	        
	        if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			
			query += "HAVING (CAST(AVG(tblTrimmedMean.TrimmedMean) AS int) = " + score + ") ";
			
			if(divID != 0)
				query += "AND tblAssignment.FKTargetDivision = " + divID;
			if(deptID != 0)
				query += "AND tblAssignment.FKTargetDepartment = " + deptID;
			if(groupSection != 0)
				query += "AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") DERIVEDTBL";*/
			
			
			query = "SELECT COUNT(distinct tblTrimmedMean.TargetLoginID) AS Total FROM tblTrimmedMean INNER JOIN ";
			query += "tblAssignment ON tblTrimmedMean.SurveyID = tblAssignment.SurveyID AND ";
			query += "tblTrimmedMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
			query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = 'CP' AND ";
			query += "tblTrimmedMean.CompetencyID = " + compID + " and RaterCode <> 'SELF'";				
			
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
				
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
				
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
				
			// Change by Santoso : add round
			query += " GROUP BY CAST(ROUND(tblTrimmedMean.TrimmedMean, 0) AS int) ";
			query += " HAVING (CAST(ROUND(tblTrimmedMean.TrimmedMean, 0) AS int) = " + score + ") ";
			
		} else {
			query = "SELECT COUNT(*) AS Total FROM ";
			query += "(SELECT tblAvgMean.TargetLoginID FROM tblAvgMean INNER JOIN ";
			query += "tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID + " AND tblRatingTask.RatingCode = 'CP' ";
			query += "AND tblAvgMean.CompetencyID = " + compID + " AND tblAvgMean.Type = 1 ";
			query += "GROUP BY tblAvgMean.TargetLoginID ";
	        
	        if(divID != 0) 
				query += ", tblAssignment.FKTargetDivision ";
			if(deptID != 0) 
				query += ", tblAssignment.FKTargetDepartment ";
			if(groupSection != 0)
				query += ", tblAssignment.FKTargetGroup ";
			//Changed by Ha 09/07/08 from CAST to CAST (ROUND) to round to nearest number
			// Changed by Santoso 2008/10/29, the query is fine but sometimes
			// Use double round to have the correct rounded value because avg might produce value like 9.499999
			query += "HAVING (CAST(ROUND(ROUND(AVG(tblAvgMean.AvgMean),2),0) AS int) = " + score + ") ";
			
			if(divID != 0)
				query += "AND tblAssignment.FKTargetDivision = " + divID;
			if(deptID != 0)
				query += "AND tblAssignment.FKTargetDepartment = " + deptID;
			if(groupSection != 0)
				query += "AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") DERIVEDTBL";
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		   if(rs.next()) {
  			   iTotalTarget = rs.getInt(1);
  		   }
 		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getAllResults - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return iTotalTarget;
	}
	/*****************************END DISTRIBUTION REPORT************************************************/
	
	
	
	
	/*******************************TARGET RANK***********************************************/
	/**
	 *	 Writes target rank to excel.
	 */
	public void printTargetRank() throws SQLException, IOException, Exception 
	{
		if (hasCPR)	// If CPR is chosen in this survey
		{
			System.out.println("8. Group Ranking Report");
			
			// Added by Tracy 01 Sep 08***********************
			// Add heading "GROUP RANKING TABLE" before the graph
			OO.findAndReplace(xSpreadsheet, "<Table title>", "GROUP RANKING TABLE");
			
			//End add by Tracy 01 Sep 08**********************
			int maxScale = getMaxScale();
			
			int [] address = OO.findString(xSpreadsheet, "<Group Rank>");
			
			column = address[0];
			row = address[1];
					
			OO.findAndReplace(xSpreadsheet, "<Group Rank>", " ");
						
			int total = getTotalTarget();
	
			double gap [] = new double [total];
			String target [] = new String [total];
			HashMap NameMap = new HashMap();
			
			int i=0;
		
			//1. get all the completed raters.
			Vector rsRank = getAllTargets();	
	
			//2. get all the gap and target name
			Vector vGap = new Vector();
			
			
			for(int j=0; j<rsRank.size(); j++) {	
				
				int fkUser = ((Integer)rsRank.elementAt(j)).intValue();
				
				voUser vo = getTargetGap(fkUser);
				double dGap = vo.getGap();
				String sName = vo.getName();
			
				NameMap.put(new Integer(fkUser), sName);
				
				vGap.add(new String [] {Integer.toString(fkUser), Double.toString(dGap)});
			
			}
		
			Vector vSorted = G.sorting(vGap, 1);
		
			int startBorder = row;
			String sName = "Name";
			String sGapScore = "Gap Score";
			
			if (ST.LangVer == 2)
			{
				sName = "Nama";
				sGapScore = "Nilai Selisih";
			}
	
			OO.insertString(xSpreadsheet, "S/N", row, column);
			OO.insertString(xSpreadsheet, sName, row, column+2);
			OO.insertString(xSpreadsheet, sGapScore, row, column+7);
			OO.setFontBold(xSpreadsheet, startColumn, column+7, row, row);
			OO.setBGColor(xSpreadsheet, startColumn, column+8, row, row, BGCOLOR);
			
			row++;		
									
			for(int j=0; j<vSorted.size(); j++) {
				
				int user = Integer.parseInt(((String [])vSorted.elementAt(j))[0]);
				target[i] 	= (String) NameMap.get(new Integer(user));					
				gap[i] 		= Double.valueOf(((String [])vSorted.elementAt(j))[1]).doubleValue();
				//Added checking by Ha 01/07/08 to get rid of null value in the report
				if (target[i] !=null)
				{
					OO.insertNumeric(xSpreadsheet, i+1, row, column);
					OO.insertString(xSpreadsheet, target[i], row, column+2);
					OO.insertNumeric(xSpreadsheet, gap[i], row, column+7);
					i++;					
					row++;
				}
			}
			
			int endBorder = row-1;
			
			OO.setTableBorder(xSpreadsheet, 0, 8, startBorder, endBorder, false, false, true, true, true, true);
	    	
			row++;
			
			drawChartRank(target, gap, 2, maxScale);
		}
		else
		{
			System.out.println("9. Removing Group Ranking Report (No CPR)");
			
			int [] address = OO.findString(xSpreadsheet, "This report lists the Targets in ranked order based on the aggregate of all their competency gaps");
			
			column = address[0];
			row = address[1];

			OO.deleteChart(xSpreadsheet, "Object 2");
			OO.deleteRows(xSpreadsheet, 0, 12, row-6, row+46, 52, 1);
		}
	}
	
	/**
	 * 	Count total records in tblGap for the particular survey, group, department, and division.
	 *	
	 *	@return int TotalTarget
	 */
	public int getTotalTarget() throws SQLException 
	{
		int total = 0;

		String query = "SELECT COUNT(DISTINCT tblAssignment.TargetLoginID) AS Total ";
		query += "FROM         tblAssignment INNER JOIN ";
		query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query += "WHERE     tblAssignment.SurveyID =  " + surveyID;
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next())
			total = rs.getInt(1);
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getTotalTarget - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return total;
	}
	
	/**
	 * 	Retrieves all targets under that particular survey.
	 *	
	 *	@return ResultSet All targets under a survey
	 */
	public Vector getAllTargets() throws SQLException 
	{
		String query = "SELECT DISTINCT tblAssignment.TargetLoginID FROM tblAssignment INNER JOIN ";
		query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser WHERE ";
		query += "tblAssignment.SurveyID = " + surveyID;
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
		query += " ORDER BY tblAssignment.TargetLoginID ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
			while(rs.next()) 
			{
				v.add(new Integer(rs.getInt(1)));
			}
		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - getAllTargets - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 * Add by Santoso : copied from IndividualReport.CPCPR
	 * Retrieves the results under that particular rating code.
	 */
	private Vector getCPCPR(String RTCode, int targetID) throws SQLException 
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		
		if(reliabilityCheck == 0) 
		{			
			query = "SELECT tblTrimmedMean.CompetencyID, Competency.CompetencyName, tblTrimmedMean.TrimmedMean as Result ";
			query = query + "FROM tblTrimmedMean INNER JOIN tblRatingTask ON ";
			query = query + "tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "INNER JOIN Competency ON ";
			query = query + "tblTrimmedMean.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblTrimmedMean.SurveyID = " + surveyID + " AND ";
			query = query + "tblTrimmedMean.TargetLoginID = " + targetID + " AND tblTrimmedMean.Type = 1 AND ";
			query = query + "tblRatingTask.RatingCode = '" + RTCode + "' ";
			query = query + "ORDER BY Competency.CompetencyID";
		} 
		else 
		{
			if(surveyLevel == 0) 
			{
				query = "SELECT tblAvgMean.CompetencyID, Competency.CompetencyName, tblAvgMean.AvgMean as Result ";
				query = query + "FROM tblAvgMean INNER JOIN tblRatingTask ON ";
				query = query + "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "INNER JOIN Competency ON ";
				query = query + "tblAvgMean.CompetencyID = Competency.PKCompetency ";
				query = query + "WHERE tblAvgMean.SurveyID = " + surveyID + " AND ";
				query = query + "tblAvgMean.TargetLoginID = " + targetID + " AND tblAvgMean.Type = 1 AND ";
				query = query + "tblRatingTask.RatingCode = '" + RTCode + "' ORDER BY Competency.CompetencyID";
			} 
			else 
			{
				query = "SELECT tblAvgMean.CompetencyID, Competency.CompetencyName, ";
				query = query + "CAST(AVG(tblAvgMean.AvgMean) AS numeric(38, 2)) AS Result ";
				query = query + "FROM tblRatingTask INNER JOIN tblAvgMean ON ";
				query = query + "tblRatingTask.RatingTaskID = tblAvgMean.RatingTaskID ";
				query = query + "INNER JOIN Competency ON ";
				query = query + "tblAvgMean.CompetencyID = Competency.PKCompetency ";
				query = query + "WHERE tblAvgMean.SurveyID = " + surveyID + " AND ";
				query = query + "tblAvgMean.TargetLoginID = " + targetID + " AND tblAvgMean.Type = 1 AND ";
				query = query + "tblRatingTask.RatingCode = '" + RTCode + "' GROUP BY tblAvgMean.CompetencyID, ";
				query = query + "Competency.CompetencyName";
			}
		}
		//System.out.println("cpcpr "+query);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   while(rs.next()) {
	 			String [] arr = new String[3];
	 			arr[0] = rs.getString(1);
	 			arr[1] = rs.getString(2);
	 			arr[2] = rs.getString(3);
	 			v.add(arr);
  		   }
  		}catch(Exception ex){
			System.out.println("GroupReport.java - CPCPR - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return v;
	}

	/**
	 * Add by Santoso (2008-10-31)
	 * To calculate the Gap value that belongs to the specified targetLoginID
	 * @param targetLoginID
	 * @return
	 * @throws SQLException
	 */
	private double getGap(int targetLoginID) throws SQLException {
		//System.out.println("4. CP Versus CPR Starts");
		List competencyList = getCompetency(0);
		List ratingTaskList = getRatingTask();
		List CP = null, CPR = null;
		// get CP & CPR value
		for (Iterator iteratorRating = ratingTaskList.iterator(); iteratorRating.hasNext();) {
			voRatingTask ratingTask = (voRatingTask) iteratorRating.next();
			String RTCode = ratingTask.getRatingCode();
			if(RTCode.equals("CP") || RTCode.equals("CPR") || RTCode.equals("FPR")) {
				if(RTCode.equals("CP"))
					CP = getCPCPR(RTCode, targetLoginID);
				else {
					CPR = getCPCPR(RTCode, targetLoginID);
				}
			}
		}

		double gap = 0d;
		for (int i = 0; i < competencyList.size(); i++) {
			double dCP = 0;
			double dCPR = 0;
			voCompetency competency = (voCompetency) competencyList.get(i);

			if(CP.size() != 0 && i<CP.size()) {
				String arr [] = (String[])CP.get(i);
				dCP = Double.parseDouble(arr[2]);
			}
			
			if(CPR.size() != 0 && i<CPR.size()) {
				String arr [] = (String[])CPR.get(i);
				dCPR = Double.parseDouble(arr[2]);
			}
			
			gap += Math.round((dCP - dCPR) * 100.0) / 100.0;
		}
		return gap;
	}	
	
	/**
	 * 	Get the gap username based on the name sequence
	 *	@param int TargetID
	 *
	 *	@return String Username
	 */
	public voUser getTargetGap(int targetID) throws SQLException 
	{
		String query = "";
		String name = "";

		int nameSeq = Integer.parseInt(surveyInfo[3]);	//0=familyname first
		//Changed by HA 08/07/08 change from Average Gap to Sum Gap
		// don't retrieve the value from tblGap (for competency)
		// just get user's full name
		query += "SELECT [User].PKUser, [User].FamilyName, [User].GivenName " +
				  "FROM [User] WHERE [User].PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		voUser vo = new voUser();
 		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   
  		   if(rs.next()) 
  		   {
  			   double dGap = getGap(targetID);
  			   vo.setGap(dGap);
	 			
  			   String family = rs.getString("FamilyName");
  			   String given = rs.getString("GivenName");
	 			
  			   if(nameSeq == 0)
  				   name =  family + " " + given;
  			   else
  				   name = given + " " + family;		
	 			
  			   vo.setName(name);
  		   }	
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getTargetGap - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
	
		return vo;
	}
	
	
	/**
	 * 	Draw Group Ranking Report chart
	 */
	public void drawChartRank(String Rating [], double Result [], int type, int maxScale) throws IOException, Exception 
	{
		int r = row;
		int c = 0;
		XSpreadsheet xSpreadsheet2 = OO.getSheet(xDoc, "Sheet2");
		OO.insertString(xSpreadsheet2, "Targets", r, c);
		OO.insertString(xSpreadsheet2, "Gap", r, c+1);
		r++;
			
		for(int i=Rating.length-1; i>=0; i--) 
		{
			//Added checking null by Ha 01/07/08 to get rid of "0" row in the chart
			if (Rating[i] != null)
			{
				OO.insertString(xSpreadsheet2, Rating[i], r, c);
				OO.insertNumeric(xSpreadsheet2, Result[i], r, c+1);
				r++;
			}
		}
		
		OO.setSourceData(xSpreadsheet, xSpreadsheet2, 1, c, c+1, row, r-1);
		
		row++;
	}

	/*********************************END TARGET RANK**********************************************/
	
	
	/******************************REPLACEMENT***************************************************/
	/**
	 * Replace one string with another, this is used only if we are using template.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public void Replacement() throws Exception, IOException {
		// job position
		System.out.println("3. Replacement Starts");
		OO.findAndReplace(xSpreadsheet, "<Job Position>", surveyInfo[1]);
	}
	
	/**
	 * 	Replace one string with another, this is used only if we are using template.
	 */
	public void ReplacementToyota(String sDiv, String sDept, String sGrp, String sJobLvl) throws Exception, IOException {
		System.out.println("3. Replacement Starts");
		
		OO.findAndReplace(xSpreadsheet, "<Position>", sJobLvl.toUpperCase());
		OO.findAndReplace(xSpreadsheet, "<Job Title>", surveyInfo[1].toUpperCase());
		OO.findAndReplace(xSpreadsheet, "<Division>", sDiv.toUpperCase());
		OO.findAndReplace(xSpreadsheet, "<Department>", sDept.toUpperCase());
		OO.findAndReplace(xSpreadsheet, "<Section>", sGrp.toUpperCase());
		OO.findAndReplace(xSpreadsheet2, "<Job Position>", surveyInfo[1].toUpperCase());
	}

	/**************************END OF REPLACEMENT******************************************/
	

	
	
	/************************START REPORT GENERATION************************************************************/
	/**
	 * Method to call all report generation method.
	 * 
	 * @param surveyID
	 * @param groupSection
	 * @param deptID
	 * @param divID
	 * @param pkUser
	 * @param fileName
	 * @param type
	 * @throws SQLException
	 * @throws Exception
	 * @throws IOException
	 * @see Report()
	 */
	public void generateReport(int surveyID, int groupSection, int deptID, int divID, int pkUser, String fileName, int type) throws SQLException, Exception, IOException 
	{		
		try 
		{
			vGap.clear();
			CPCPRMap.clear();
			CompIDGapMap.clear();
			vCompDetails.clear();
			vRatingTask.clear();
			vCP.clear();
			vCPR.clear();
			
			InitializeExcel(fileName);
		
			Replacement();
			InsertLogo();
			
			
			vCompDetails = getCompetency(0);
			vRatingTask = getRatingTask();
			iReportType = type;
			//to check for CP / CPR and assign value to variables.
			checkCPCPR();
			
			printCPvsCPR();			
			// Added by Tracy 26 aug 08******************
			//Print Rating title based on CPR or FPR*********
			printGapTitle(surveyID);
			//End Tracy add 26 aug 08***************
			
			try {printGap();} catch (Exception e) {System.out.println("Error2 "+e.getMessage());}
			try {printCompGap(surveyID);} catch (Exception e){System.out.println("Error3 "+e.getMessage());}
			try {printCompetency();} catch (Exception e){System.out.println("Error4 "+e.getMessage());}
			
			int included = Q.commentIncluded(surveyID);
			//Added by Ha 23/06/08 to print out the self comment
			int selfIncluded = Q.SelfCommentIncluded(surveyID);
			//by Hemilda 10/10/2008 remove the comments part
			//if(included == 1||selfIncluded==1)
			//	try {printComments();} catch (Exception e){System.out.println("Print comment "+e.getMessage());}

			try {printTotalTargets();} catch (Exception e){System.out.println("Error4 "+e.getMessage());}
			try {printTargetRank();} catch (Exception e){System.out.println("Error5 "+e.getMessage());}
			
			Date timestamp = new Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			String temp = dFormat.format(timestamp);
			System.out.println("Insert Header Footer");
			OO.insertHeaderFooter(xDoc, surveyInfo[1], surveyInfo[4], "Date of printing: " + temp + "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.");
			
			
			System.out.println("================ Group Report Completed ================");

		}catch(SQLException SE) {
			System.out.println("1Store Document");
			OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
			System.out.println("SQL " + SE.getMessage());
			OO.closeDoc(xDoc);
		}catch(Exception E) {
			System.out.println("2Store Document");
			OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
			System.out.println("DD " + E.getMessage());
			OO.closeDoc(xDoc);
		} finally {
			
			try {
				System.out.println("Store Document");
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
				System.out.println("CLosing OO");
				OO.closeDoc(xDoc);
				System.out.println("OO Closed");
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
			}   
    	}
	}
	
	/**
	 * get competency
	 * 
	 * @param iOrder
	 * @return
	 * @throws SQLException
	 * @see generateReport()
	 */
	public Vector getCompetency(int iOrder) throws SQLException {
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		Vector v = new Vector();
		
		if(surveyLevel == 0) {
			query = query + "SELECT tblSurveyCompetency.CompetencyID, Competency.CompetencyName, ";
			query = query + "CompetencyDefinition FROM tblSurveyCompetency INNER JOIN Competency ON ";
			query = query + "tblSurveyCompetency.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblSurveyCompetency.SurveyID = " + surveyID;
			
			if (iOrder == 0)
				query = query + " ORDER BY tblSurveyCompetency.CompetencyID";
			else
				query = query + " ORDER BY tblSurveyCompetency.CompetencyID DESC";
				
		} else {
			
			query = query + "SELECT DISTINCT tblSurveyBehaviour.CompetencyID, Competency.CompetencyName, ";
			query = query + "Competency.CompetencyDefinition FROM Competency INNER JOIN ";
			query = query + "tblSurveyBehaviour ON Competency.PKCompetency = tblSurveyBehaviour.CompetencyID ";
			query = query + "AND Competency.PKCompetency = tblSurveyBehaviour.CompetencyID ";
			query = query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID;
			
			if (iOrder == 0)
				query = query + " ORDER BY tblSurveyBehaviour.CompetencyID";
			else
				query = query + " ORDER BY tblSurveyBehaviour.CompetencyID DESC";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
	
  			while(rs.next()) {
  				int compID = rs.getInt("CompetencyID");
  				String compName = UnicodeHelper.getUnicodeStringAmp(rs.getString("CompetencyName").trim());
  				String compDef = UnicodeHelper.getUnicodeStringAmp(rs.getString("CompetencyDefinition").trim());
  				
  				voCompetency vo = new voCompetency();
  				vo.setCompetencyID(compID);
  				vo.setCompetencyName(compName);
  				vo.setCompetencyDefinition(compDef);
  				v.add(vo);
  			}

		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getCompetency - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 *	Insert Logo
	 */
	public void InsertLogo() throws IOException, Exception 
	{	
		if(surveyInfo[6] != "") //Org Logo
		{
			File F = new File(ST.getOOLogoPath()+ surveyInfo[6]); //directory where the file supposed to be stored
			
			//System.out.println(ST.getOOLogoPath()+ surveyInfo[6]);
			
			if(F.exists())
				OO.replaceLogo(xSpreadsheet, xDoc, "<Logo>", ST.getOOLogoPath()+ surveyInfo[6]);
			else
				OO.replaceLogo(xSpreadsheet, xDoc, "<Logo>", "");
		}
	}
	/************************END REPORT GENERATION***************************************************/
	
	
	/**************************START - JSP METHODS******************************/
	
	public String Report(int surveyID, int groupSection, int deptID, int divID, int pkUser, String fileName, int type) throws SQLException, Exception, IOException {				
		System.out.println("----Group Report Generation Starts----");
		
		
		InitializeSurvey(surveyID, groupSection, deptID, divID);
		
		String jobName = surveyInfo[1].replaceAll("/", "-");
		
		String save = jobName + " (" + surveyInfo[4] + ")";
		
		generateReport(surveyID, groupSection, deptID, divID, pkUser, fileName, type);
		
		
		//---adding event viewer
		String [] UserInfo = U.getUserDetail(pkUser);
		
		try {
			String groupSect = "All";
			String deptName = "All";
			String divName = "All";
			
			if(groupSection != 0)
				groupSect = getGroupName();
			if(deptID != 0)
				deptName = getDeptName();
			if(divID != 0)
				divName = getDivName();
			
			String temp = surveyInfo[4] + "(S); " + groupSect + "(G); " + deptName + "(Dept); " + divName + "(Div)";
			
			EV.addRecord("Print", "Rater Result", temp, UserInfo[2], UserInfo[11], UserInfo[10]);

		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
	
		
		return save;
	
	}
	
	/***********************END - JSP FUNCTIONS*************************/
	
	
	
	
	/**
	 * 	Get the username based on targetID
	 *	@param int TargetID
	 *
	 *	@return String Username
	 */
	public String UserNameToyota(int targetID) throws SQLException 
	{
		String query = "";
		String name = "";
		
		int nameSeq = Integer.parseInt(surveyInfo[5]);	//0=familyname first
		
		query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if(rs.next()) 
  		   {
				String family = rs.getString("FamilyName");
				String given = rs.getString("GivenName");
				
				if(nameSeq == 0)
					name =  family + " " + given;
				else
					name = given + " " + family;				
  		   }	
		
  	   	}catch(Exception ex){
			System.out.println("GroupReport.java - UserNameToyota - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		

		return name;
	}
	
	/**
	 * 	Get user's department based user id
	 *	@param int TargetID
	 *
	 *	@return String DepartmentName
	 */
	public String UserDepartment(int targetID) throws SQLException 
	{
		String query = "";
		String sDepartment = "";
		
		query =  "SELECT D.DepartmentCode AS DepartmentName FROM [User] U INNER JOIN Department D ON U.FKDepartment = D.PKDepartment ";
		query =  query + "WHERE U.PKUser = " + targetID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
	
  		   if(rs.next()) 
  		   {
  			   sDepartment = rs.getString("DepartmentName");			
  		   }
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - UserDepartment - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
			

		return sDepartment;
	}
	/**
	 * 	Retrieve the average or trimmed mean result based on competency and key behaviour for each group
	 *	@param int group	GroupID (1=ALL, 2=SELF)
	 *	@param int compID	CompetencyID
	 *	@param int RTID		RatingTaskID
	 *	@param int KBID		KeyBehaviourID
	 *
	 *	@return ResultSet MeanResult	Average/Trimmed Mean result
	 */
	/*public ResultSet MeanResultToyota(int group, int RTID, int compID, int KBID) throws SQLException 
	{
		String query = "";
		
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		int reliabilityCheck = C. ReliabilityCheck(surveyID);

		String formula = "";
		int totalCount = getTotalRaters(group);
		String filter = "";
		
		switch(group) 
		{	// 1 for all, 4 for self
			case 1: filter = "and tblAssignment.RaterCode <> 'SELF'";
					break;	
			case 4: filter = "and tblAssignment.RaterCode = 'SELF'";
					break;	
		}	

		if(totalCount == 1)
			formula = "sum(tblResultCompetency.Result) AS Result ";
		else if(totalCount == 2)
			formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2)) AS Result ";
		else 
		{
			if(reliabilityCheck == 0) 
			{
				formula = "cast(cast((Sum(tblResultCompetency.Result)-(Max(tblResultCompetency.Result)+";
				formula = formula + "Min(tblResultCompetency.Result))) as float)/(Count(tblResultCompetency.Result)-2) as numeric(38,2)) AS Result ";
			}
			else
				formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2))  AS Result ";
		}

		if(totalCount != 0) 
		{
			if(surveyLevel == 0) 
			{
				query = query + "SELECT  tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
				query = query + "RatingTaskID, " + formula + " FROM [User] ";
				query = query + "INNER JOIN (tblAssignment INNER JOIN tblResultCompetency ON ";
				query = query + "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID) ";
				query = query + "ON [User].PKUser = tblAssignment.TargetLoginID INNER JOIN Competency ON ";
				query = query + "tblResultCompetency.CompetencyID = Competency.PKCompetency ";
									
				query = query + "Where SurveyID = " + iPastSurveyID;
				query = query + " and tblResultCompetency.CompetencyID = " + compID;
				query = query + " and tblResultCompetency.RatingTaskID = " + RTID + " AND tblAssignment.RaterStatus IN (1,2,4) ";
				query = query + filter;
				
				if(divID != 0) 
					query = query + " AND tblAssignment.FKTargetDivision = " + divID;
					//query = query + " AND [User].FKDivision = " + divID;
				if(deptID != 0) 
					query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
					//query = query + " AND [User].FKDepartment = " + deptID;
				if(groupSection != 0)
					query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
					//query = query + " AND [User].Group_Section = " + groupSection;
				
				query = query + " GROUP BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID, ";
				query = query + "Competency.CompetencyName";
				query = query + " ORDER BY tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
				query = query + "tblResultCompetency.RatingTaskID";
			} 
			else 
			{
				query = query + "SELECT KeyBehaviour.FKCompetency, Competency.CompetencyName, ";
				query = query + "tblResultBehaviour.KeyBehaviourID, KeyBehaviour.KeyBehaviour, ";
				query = query + "tblResultBehaviour.RatingTaskID, ";
				query = query + "CAST(AVG(CAST(tblResultBehaviour.Result AS float)) AS numeric(38, 2)) AS Result ";
				query = query + " FROM [User] INNER JOIN tblAssignment INNER JOIN ";
				query = query + "tblResultBehaviour ON ";
				query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ON ";
				query = query + "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN KeyBehaviour ";
				query = query + "ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour INNER JOIN ";
				query = query + "Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
				
				query = query + "WHERE tblAssignment.SurveyID = " + iPastSurveyID;
				query = query + " and KeyBehaviour.FKCompetency = " + compID;
				//query = query + " and tblResultBehaviour.KeyBehaviourID = " + KBID;
				query = query + " and tblResultBehaviour.RatingTaskID = " + RTID  + " AND tblAssignment.RaterStatus IN (1,2,4) ";
				query = query + filter;
				
				if(divID != 0) 
					query = query + " AND tblAssignment.FKTargetDivision = " + divID;
					//query = query + " AND [User].FKDivision = " + divID;
				if(deptID != 0) 
					query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
					//query = query + " AND [User].FKDepartment = " + deptID;
				if(groupSection != 0)
					query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
					//query = query + " AND [User].Group_Section = " + groupSection;

				
				query = query + " GROUP BY tblResultBehaviour.RatingTaskID, ";
				query = query + "KeyBehaviour.FKCompetency, Competency.CompetencyName, tblResultBehaviour.KeyBehaviourID, ";
				query = query + "KeyBehaviour.KeyBehaviour ORDER BY KeyBehaviour.FKCompetency, ";
				query = query + "Competency.CompetencyName, tblResultBehaviour.KeyBehaviourID, ";
				query = query + "KeyBehaviour.KeyBehaviour, tblResultBehaviour.RatingTaskID";
			}
			
			if(db.con == null) 
				db.openDB();
				
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
		
			return rs;
		}
		else
			return null;

	}*/
	
	/**
	 * 	Writes CP versus CPR/FPR to excel.
	 */
	public void InsertCPvsCPRToyota() throws SQLException, IOException, Exception 
	{
		System.out.println("4. Generating CP Versus CPR");
		String RTCode = "";
		int type = 1;
		
		int total = totalCompetency();		// 1 for all
		//Vector PastCP = null;
		double dPastCP = 0;
		
		if(hasCP) {
			vCP = getCPCPR("CP");
			
			dPastCP = PastCP(RTCode);
			
		}
		if(hasCPR) 
			vCPR = getCPCPR("CPR");
		else if(hasFPR)
			vCPR = getCPCPR("FPR");
		
		int [] address = OO.findString(xSpreadsheet, "<Chart Title>");
		
		column = address[0];
		row = address[1];
		row++;
		
		String title = "Current Proficiency Vs Required Proficiency";
		
		int r = row+1;
		int c = 0;
		
		OO.insertString(xSpreadsheet3, "CP", r, c+1);
		if(type == 1)
			OO.insertString(xSpreadsheet3, "CPR", r, c+2);
		else {
			OO.insertString(xSpreadsheet3, "FPR", r, c+2);
			title = "Current Proficiency Vs Future Required Proficiency";
		}
		OO.insertString(xSpreadsheet3, "Prev. CP", r, c+3);
		r++;
		
		OO.findAndReplace(xSpreadsheet, "<Chart Title>", title);
		
		int compID = 0;
		String compName = "";
		double dCP = 0;
		double dCPR = 0;
		
		for(int i=0; i<total; i++) 
		{								
			dCP = 0;
			dCPR = 0;
			dPastCP = 0;
			
			voRatingResult voCP = (voRatingResult)vCP.elementAt(i);
			
			if(voCP != null) {
				compID = voCP.getCompetencyID();
				compName = UnicodeHelper.getUnicodeStringAmp(voCP.getCompetencyName());
				dCP = voCP.getResult();	
			} else {
				compID = voCP.getCompetencyID();
				compName = UnicodeHelper.getUnicodeStringAmp(voCP.getCompetencyName());
				dCP = 0;	
			}
	
			if(hasCPR || hasFPR) {
				voRatingResult voCPR = (voRatingResult)vCPR.elementAt(i);
				
				if(voCPR != null)
					dCPR = voCPR.getResult();
				else
					dCPR = 0;	
			}else //if no CPR/FPR is chosen, set the CPR as 0 
				dCPR = 0;			
			
		
			
			double gap = Math.round((dCP - dCPR) * 100.0) / 100.0;
		
			double [] dArrTemp = new double [2];
			dArrTemp[0] = dCP;
			dArrTemp[1] = dCPR;

			CPCPRMap.put(new Integer(compID), (double[])dArrTemp);
			vGap.add(new String[]{compName, Double.toString(gap)});
			CompIDGapMap.put(new Integer(compID), Double.toString(gap));
			vCompID.add(new Integer(compID));
			
			r++;

			OO.insertNumeric(xSpreadsheet3, dCP, r, c+1);
			OO.insertNumeric(xSpreadsheet3, dCPR, r, c+2);
			OO.insertNumeric(xSpreadsheet3, dPastCP, r, c+3);
			
			r++;
		}	
	}
	
	/**
	 * 	Write target gap results to excel worksheet.
	 */
	public void InsertGapToyota() throws SQLException, IOException, Exception {
		
		System.out.println("5. Gap Insertion Starts");
					
		row = 9;
		column = 23;
		startColumn = 23;
		endColumn = 34;
		
		double MinMaxGap [] = getMinMaxGap();
		double dGap = 0;
		String sCompName = "";
		
		double low = MinMaxGap[0];
		double high = MinMaxGap[1];
		
		vGap = G.sorting(vGap, 1);
		String title = "COMPETENCY";
		
		OO.insertString(xSpreadsheet, title, row, column);
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, row, row, false, false, true, true, true, true);
		row++;
		
		int startBorder = row;
		
		OO.insertString(xSpreadsheet, "STRENGTH", row, column);
		OO.insertString(xSpreadsheet, "Gap >= " + high, row, column+10);
		
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
		OO.setBGColor(xSpreadsheet, startColumn, endColumn, row, row, BGCOLOR);					
		row++;
		
		for(int a=0; a<vGap.size(); a++)
		{
			dGap = Double.valueOf(((String [])vGap.elementAt(a))[1]).doubleValue();
			
			if (dGap >= high)
			{
				sCompName = ((String [])vGap.elementAt(a))[0];
				
				OO.insertString(xSpreadsheet, sCompName, row, column);
				OO.insertNumeric(xSpreadsheet, dGap, row, column+10);
				row++;	
			}
			
		}
	
		row++;
		int endBorder = row + 1;
		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, startBorder, endBorder, false, false, true, true, true, true);

		startBorder = endBorder + 1;		
		row++;
		
		OO.insertString(xSpreadsheet, "MEET EXPECTATIONS", row, column);
		OO.insertString(xSpreadsheet, low + " < Gap < " + high, row, column+10);
	
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
		OO.setBGColor(xSpreadsheet, startColumn, endColumn, row, row, BGCOLOR);						
		row++;
		
		for(int b=0; b<vGap.size(); b++)
		{
			dGap = Double.valueOf(((String [])vGap.elementAt(b))[1]).doubleValue();
			
			if (dGap > low && dGap < high)
			{
				sCompName = ((String [])vGap.elementAt(b))[0];
				
				OO.insertString(xSpreadsheet, sCompName, row, column);
				OO.insertNumeric(xSpreadsheet, dGap, row, column+10);
				row++;	

			}
		}
		
		row++;
		endBorder = row;
		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, startBorder, endBorder, false, false, true, true, true, true);
									
		startBorder = endBorder + 1;		
		row++;
		
		OO.insertString(xSpreadsheet, "DEVELOPMENTAL AREA", row, column);
		OO.insertString(xSpreadsheet, "Gap <= " + low, row, column+10);
		
		OO.setFontBold(xSpreadsheet, startColumn, endColumn, row, row);
		OO.setBGColor(xSpreadsheet, startColumn, endColumn, row, row, BGCOLOR);				
		row++;
		
		for(int d=0; d<vGap.size(); d++)
		{
			dGap = Double.valueOf(((String [])vGap.elementAt(d))[1]).doubleValue();
			
			if (dGap <= low)
			{
				sCompName = ((String [])vGap.elementAt(d))[0];
			
				OO.insertString(xSpreadsheet, sCompName, row, column);
				OO.insertNumeric(xSpreadsheet, dGap, row, column+10);
				row++;	
			}
		}
		
		endBorder = row;
		OO.setTableBorder(xSpreadsheet, startColumn, endColumn, startBorder, endBorder, false, false, true, true, true, true);
	}
	
	/**
	 * 	Write competency report to excel.
	 */
	public void InsertCompetencyToyota() throws SQLException, IOException, Exception 
	{
		System.out.println("6. Competency Report");
		
		column = 0;
		row = 32;
		startColumn = 0;
		endColumn = 4;
		
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		
		int iCompCount = 1; // Counter to count the no of competencies
		int iCompRow = 33;	// Counter to store Row of <Comp Name X>
		int iCompCol = 0;	// Counter to count Column of <Comp Name X>
		
//		int totalOth = totalOth();
//		int totalSup = totalSup();
		int totalSelf = 0;
		//Dont use anymore, change to avoid error only by Ha 07/07/08
		if(getTotalRaters("SELF",1,1) != 0)
			totalSelf = 1;
			
		//int totalAll = totalOth + totalSup;
		int total = totalSelf + getTotalOtherRaters() + 1;		// 1 for all
		
		String [] Rating = new String [total];
		double [] Result = new double [total];
		
		if(surveyLevel == 0) 
		{				
			for(int i=0; i<vCompDetails.size(); i++)
			{
				int RTID = 0;			
				int KBID = 0;
						
				voCompetency voComp = (voCompetency)vCompDetails.elementAt(i);
				int iCompID = voComp.getCompetencyID();
				String sCompName = voComp.getCompetencyName();
				String sCompDef = voComp.getCompetencyDefinition();	
				
				String RTCode = "";
				
				Vector result = null;
				Vector resultSUP = null;
				Vector resultOTH = null;
				Vector self = null;
				
				int x = 0;
				int r = row;
				
				
				/**
				 * EXTRA CARE *******************************************************************************
				 */
				
				for(int j=0; j<vRatingTask.size(); j++) {
					voRatingTask voSurvey = (voRatingTask)vRatingTask.elementAt(j);
					
					RTID = voSurvey.getRatingTaskID();
					RTCode = voSurvey.getRatingCode();
					
					result = MeanResult(1, RTID, iCompID, KBID);
					resultSUP = MeanResult(2, RTID, iCompID, KBID);
					resultOTH = MeanResult(3, RTID, iCompID, KBID);
					self = MeanResult(4, RTID, iCompID, KBID);
					
					if(RTCode.equals("CP")) {
						
						for(int k=0; k<result.size(); k++)
						{	
							String [] arr = (String[])result.elementAt(k);
							Result[x] = Double.parseDouble(arr[3]);
							Rating[x] = RTCode + " (ALL)";
							
							
							OO.insertString(xSpreadsheet3, Rating[x], r+3, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r+3, column+1);
						}
						
						for(int k=0; k<resultSUP.size(); k++)
						{	
							String [] arr = (String[])resultSUP.elementAt(k);
							Result[x] = Double.parseDouble(arr[3]);	
							Rating[x] = RTCode + " (Supervisors)";
							//Result[x] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r+1, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r+1, column+1);
						}
						
						for(int k=0; k<resultOTH.size(); k++)
						{	
							String [] arr = (String[])resultOTH.elementAt(k);
							Result[x] = Double.parseDouble(arr[3]);	
							Rating[x] = RTCode + " (Others)";
							//Result[x] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r, column+1);
						}
						
						if(totalSelf != 0) {
							for(int k=0; k<self.size(); k++)
							{	
								String [] arr = (String[])self.elementAt(k);
								Result[x] = Double.parseDouble(arr[3]);
								Rating[x] = RTCode + " (SELF)";							
								//Result[x] = self.getDouble("Result");
								
								OO.insertString(xSpreadsheet3, Rating[x], r+2, column);
								OO.insertNumeric(xSpreadsheet3, Result[x], r+2, column+1);
							}
							
						}
					}
					else if(RTCode.equals("CPR") || RTCode.equals("FPR"))
					{
						for(int k=0; k<result.size(); k++)
						{	
							String [] arr = (String[])result.elementAt(k);
							Result[x+1] = Double.parseDouble(arr[3]);
							Rating[x] = RTCode + " (All)";
							//Result[x+1] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r+5, column);
							OO.insertNumeric(xSpreadsheet3, Result[x++], r+5, column+1);
						}
					}
				}	//while RT
				
				// Insert Gap into <Comp Name X>
				double gap = 0;
				
				if(CompIDGapMap.containsKey(new Integer(iCompID))) {
					gap = Double.valueOf((String) CompIDGapMap.get(new Integer(iCompID))).doubleValue();
				}
				
				OO.insertString(xSpreadsheet, " Gap = " + gap, iCompRow, iCompCol);

    			row = row + 7;
    			iCompCount++;
    			iCompCol = iCompCol + 6;
    			
    			/*
    			 *	After 4 competencies name are printed, increase rows to print the next 4 competencies
    			 *	At the same time reduce column to 0 to start printing from Left to Right again
    			 */
    			if (iCompCount == 5 || iCompCount == 9)
    			{
    				iCompCol = 0;
    				iCompRow = iCompRow + 14;
    			}
				
			}// while Comp

		}
		else 
		{	
			Vector result = null;
			Vector resultSUP = null;
			Vector resultOTH = null;
			Vector self = null;
			
			for(int i=0; i<vCompDetails.size(); i++)
			{
				int RTID = 0;
				
				voCompetency voComp = (voCompetency)vCompDetails.elementAt(i);
				int iCompID = voComp.getCompetencyID();
				
				String RTCode = "";
		
				int x = 0;
				int r = row;
				
				for(int j=0; j<vRatingTask.size(); j++) {
					voRatingTask voSurvey = (voRatingTask)vRatingTask.elementAt(j);

					RTID = voSurvey.getRatingTaskID();
					RTCode = voSurvey.getRatingCode();
										
					result = KBMean(1, RTID, iCompID);
					resultSUP = KBMean(2, RTID, iCompID);
					resultOTH = KBMean(3, RTID, iCompID);
					self = KBMean(4, RTID, iCompID);
					
					if(RTCode.equals("CP")) {
						for(int k=0; k<result.size(); k++)
						{	
							String [] arr = (String[])result.elementAt(k);
							Result[x] = Double.parseDouble(arr[2]);
							Rating[x] = RTCode + " (ALL)";
							//Result[x] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r+3, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r+3, column+1);
						}
						
						for(int k=0; k<resultSUP.size(); k++)
						{	
							String [] arr = (String[])resultSUP.elementAt(k);
							Result[x] = Double.parseDouble(arr[2]);
							Rating[x] = RTCode + " (Supervisors)";
							//Result[x] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r+1, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r+1, column+1);
							
						}
						
						for(int k=0; k<resultOTH.size(); k++)
						{	
							String [] arr = (String[])resultOTH.elementAt(k);
							Result[x] = Double.parseDouble(arr[2]);
							Rating[x] = RTCode + " (Others)";
							//Result[x] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r, column);
							OO.insertNumeric(xSpreadsheet3, Result[x], r, column+1);
							
						}
						
						if(totalSelf != 0) {
							for(int k=0; k<self.size(); k++)
							{	
								String [] arr = (String[])self.elementAt(k);
								Result[x] = Double.parseDouble(arr[2]);
								Rating[x] = RTCode + " (SELF)";
								//Result[x] = self.getDouble("Result");
							
								OO.insertString(xSpreadsheet3, Rating[x], r+2, column);
								OO.insertNumeric(xSpreadsheet3, Result[x], r+2, column+1);

							}
						}
					}
					else if(RTCode.equals("CPR") || RTCode.equals("FPR"))
					{
						for(int k=0; k<result.size(); k++)
						{	
							String [] arr = (String[])result.elementAt(k);
							Result[x+1] = Double.parseDouble(arr[2]);	
							Rating[x] = RTCode + " (All)";
							//Result[x+1] = result.getDouble("Result");
							
							OO.insertString(xSpreadsheet3, Rating[x], r+5, column);
							OO.insertNumeric(xSpreadsheet3, Result[x+1], r+5, column+1);
						}
					}
				}	//while RT
				
//				 Insert Gap into <Comp Name X>
				double gap = 0;
				
				if(CompIDGapMap.containsKey(new Integer(iCompID))) {
					gap = Double.valueOf((String) CompIDGapMap.get(new Integer(iCompID))).doubleValue();
				}
				
				OO.insertString(xSpreadsheet, " Gap = " + gap, iCompRow, iCompCol);

    			row = row + 7;
    			iCompCount++;
    			iCompCol = iCompCol + 6;
    			
    			/*
    			 *	After 4 competencies name are printed, increase rows to print the next 4 competencies
    			 *	At the same time reduce column to 0 to start printing from Left to Right again
    			 */
    			if (iCompCount == 5 || iCompCount == 9)
    			{
    				iCompCol = 0;
    				iCompRow = iCompRow + 14;
    			}					
			} // while Comp
		}
		
		Vector vResult = getCompAvg(surveyID, vCompID, "CP");
		
		int r = 36;
		
		for(int v=0;v<vResult.size();v++)
		{
			double dAvgCPPos = Double.parseDouble((String)vResult.elementAt(v));
			
			OO.insertString(xSpreadsheet3, "Avg. CP of Position", r, column);
			OO.insertNumeric(xSpreadsheet3, dAvgCPPos, r, column+1);

			r = r + 7;
		}
	}
	
	/**
	 *	 Writes target rank to excel.
	 */
	public void InsertTargetRankToyota() throws SQLException, IOException, Exception 
	{
		System.out.println("7. Group Ranking Report");
		
		Vector vRankingData = new Vector();
		Vector vAvgCP = new Vector();
		int startColumn = 0;
		int endColumn = 15;
		int maxScale = getMaxScale();		
		
		int fkUser = 0;
		double gap1 = 0;
		
		double MinMaxGap [] = getMinMaxGap();
		double low = MinMaxGap[0];
		double high = MinMaxGap[1];
		
		OO.findAndReplace(xSpreadsheet2, "<low>", Double.toString(low));
		OO.findAndReplace(xSpreadsheet2, "<high>", Double.toString(high));
		
		int i=0;
		
		Vector vUser = getAllTargets();
		
		double avgCP [] = new double [vUser.size()];
		double gap [] = new double [vUser.size()];
		String target [] = new String [vUser.size()];
		String department [] = new String [vUser.size()];
		
		for(int v=0; v<vUser.size(); v++)
		{
			fkUser = ((Integer)vUser.elementAt(v)).intValue();
			
			target[v] = UserNameToyota(fkUser);
				
			department[v] = UserDepartment(fkUser);
							
			avgCP[v] = getAvgMeanForGap(surveyID, fkUser);
							
			gap1 = getCompGap(fkUser);
			gap[v] = Math.round(gap1 * 100.0) / 100.0;
			
			vAvgCP.add(new String[]{target[v], department[v], Double.toString(avgCP[v])});
			vRankingData.add(new String[]{target[v], department[v], Double.toString(gap[v])});
			
			/*
			fkUser = Integer.parseInt((String)vUser.elementAt(v));
			if (v > 560)
				System.out.println("FK User = " + fkUser);
				
			target[v] = UserNameToyota(fkUser);
			if (v > 560)
				System.out.println("Target = " + target[v]);
				
			department[v] = UserDepartment(fkUser);
			if (v > 560)
				System.out.println("Department = " + department[v]);
				
			avgCP[v] = getAvgMeanForGap(surveyID, fkUser);
			if (v > 560)
				System.out.println("Avg CP = " + avgCP[v]);
				
			gap1 = getCompGap(fkUser);
			gap[v] = Math.round(gap1 * 100.0) / 100.0;
			if (v > 560)
				System.out.println("Gap = " + gap[v]);
				
			//System.out.println("v = " + v + ", Target = " + target[v] + ", Dept = " + department[v] + ", Gap = " + gap[v]);
			
			vAvgCP.add(new String[]{target[v], department[v], Double.toString(avgCP[v])});
			vRankingData.add(new String[]{target[v], department[v], Double.toString(gap[v])});
			*/
		}
		/**********/
		
		vRankingData = sortRanking(vRankingData, 1);
		vAvgCP = sortRanking(vAvgCP, 0);
		
		/*	GAP SCORE exceed 100
		while(Rank.next()) 
		{	
			target[i] = UserNameToyota(Rank.getInt("TargetLoginID"));
			
			department[i] = UserDepartment(Rank.getInt("TargetLoginID"));
			
			avgCP[i] = getAvgMeanForGap(surveyID, Rank.getInt("TargetLoginID"));
			
			gap[i] = Rank.getDouble("Total");
			
			vAvgCP.add(new String[]{target[i], department[i], Double.toString(avgCP[i])});
			vRankingData.add(new String[]{target[i], department[i], Double.toString(gap[i])});
			
			i++;
		}
		*/
		
		double dAvgCP = 0;
		double dGap = 0;
		String sTarget = "";
		String sDept = "";
		
		row = 17;
		column = 1;
		
		// For Average CP Table
		int totalRow03 = 0;	// Scale 0 - 3
		int iRow03 = row;	// Increment counter for Scale 0 - 3
		int totalRow35 = 0;	// Scale 3 - 5
		int iRow35 = row;	// Increment counter for Scale 3 - 5
		int totalRow57 = 0;	// Scale 5 - 7
		int iRow57 = row;	// Increment counter for Scale 5 - 7
		int totalRow79 = 0;	// Scale 7 - 9
		int iRow79 = row;	// Increment counter for Scale 7 - 9
		int totalRow910 = 0; // Scale 9 - 10
		int iRow910 = row;	 // Increment counter for Scale 9 - 10
		int totalCRCP = 34;	//28;	 // Total Current Row
		
		// For Competency Gap Table
		int totalRowsS = 0;	// Strength
		int rS = 0;			// Increment counter for Strength
		int totalRowsM = 0; // Meet Expectation
		int rM = 0;			// Increment counter for Meet Expectation
		int totalRowsD = 0; // Dev. Area
		int rD = 0;			// Increment counter for Dev. Area
		int totalCRGAP = 26;	//19;	// Total Current Row

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ AVG CP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		for(i=0; i<vAvgCP.size(); i++) 
		{
			sTarget = ((String [])vAvgCP.elementAt(i))[0];
			sDept = ((String [])vAvgCP.elementAt(i))[1];
			dAvgCP = Double.valueOf(((String [])vAvgCP.elementAt(i))[2]).doubleValue();
			
			if(dAvgCP <= 3)
			{
				// Since the template is fix with totalCRCP rows (Determine by totalCRCP)
				// Should we need more than totalCRCP rows, we have to insert new rows
				if (totalRow03 >= totalCRCP-1)
				{
					// When a new row is inserted, it will cause cell to unmerged
					// So we have to merged all the cells where the row is inserted
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, iRow03-1, iRow03, 1, 1);
					OO.mergeCells(xSpreadsheet2, column, column+3, iRow03, iRow03);
					OO.mergeCells(xSpreadsheet2, column+4, column+7, iRow03, iRow03);
					OO.mergeCells(xSpreadsheet2, column+8, column+11, iRow03, iRow03);
					OO.mergeCells(xSpreadsheet2, column+12, column+15, iRow03, iRow03);
					OO.mergeCells(xSpreadsheet2, column+16, column+19, iRow03, iRow03);
					totalCRCP++;	// Increment totalCR to current no. of rows
				}
				
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow03, column);
				iRow03++;		// Increment row to print target name on next row
				totalRow03++;	// Increment total row which are filled with names
			}
			else if(dAvgCP > 3 && dAvgCP <= 5)
			{
				if (totalRow35 >= totalCRCP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, iRow35-1, iRow35, 1, 1);
					OO.mergeCells(xSpreadsheet2, column, column+3, iRow35, iRow35);
					OO.mergeCells(xSpreadsheet2, column+4, column+7, iRow35, iRow35);
					OO.mergeCells(xSpreadsheet2, column+8, column+11, iRow35, iRow35);
					OO.mergeCells(xSpreadsheet2, column+12, column+15, iRow35, iRow35);
					OO.mergeCells(xSpreadsheet2, column+16, column+19, iRow35, iRow35);
					totalCRCP++;
				}
				
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow35, column+3);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow35, column+4);
				iRow35++;
				totalRow35++;
			}
			else if(dAvgCP > 5 && dAvgCP <= 7)
			{
				if (totalRow57 > totalCRCP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, iRow57-1, iRow57, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+2, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+3, column+5, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+6, column+8, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+9, column+11, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+12, column+14, iRow57, iRow57);*/
					OO.mergeCells(xSpreadsheet2, column, column+3, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+4, column+7, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+8, column+11, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+12, column+15, iRow57, iRow57);
					OO.mergeCells(xSpreadsheet2, column+16, column+19, iRow57, iRow57);
					totalCRCP++;
				}
				
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow57, column+6);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow57, column+8);
				iRow57++;
				totalRow57++;
			}
			else if(dAvgCP > 7 && dAvgCP <= 9)
			{
				if (totalRow79 >= totalCRCP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, iRow79-1, iRow79, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+2, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+3, column+5, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+6, column+8, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+9, column+11, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+12, column+14, iRow79, iRow79);*/
					OO.mergeCells(xSpreadsheet2, column, column+3, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+4, column+7, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+8, column+11, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+12, column+15, iRow79, iRow79);
					OO.mergeCells(xSpreadsheet2, column+16, column+19, iRow79, iRow79);
					totalCRCP++;
				}
				
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow79, column+9);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow79, column+12);
				iRow79++;
				totalRow79++;
			}
			else if(dAvgCP > 9)
			{
				if (totalRow910 >= totalCRCP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, iRow910-1, iRow910, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+2, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+3, column+5, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+6, column+8, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+9, column+11, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+12, column+14, iRow910, iRow910);*/
					OO.mergeCells(xSpreadsheet2, column, column+3, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+4, column+7, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+8, column+11, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+12, column+15, iRow910, iRow910);
					OO.mergeCells(xSpreadsheet2, column+16, column+19, iRow910, iRow910);
					totalCRCP++;
				}
				
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow910, column+12);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", iRow910, column+16);
				iRow910++;
				totalRow910++;
			}
		}
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END AVG CP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		
		row = row + totalCRCP + 9;
		rD = row;
		rM = row;
		rS = row;
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COMPETENCY GAP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		for(i=0; i<vRankingData.size(); i++) 
		{
			sTarget = ((String [])vRankingData.elementAt(i))[0];
			target[i] = ((String[])vRankingData.elementAt(i))[0];
			
			sDept = ((String [])vRankingData.elementAt(i))[1];
			department[i] = ((String[])vRankingData.elementAt(i))[1];
			
			dGap = Double.valueOf(((String [])vRankingData.elementAt(i))[2]).doubleValue();
			gap[i] = Double.valueOf(((String [])vRankingData.elementAt(i))[2]).doubleValue();
			
			if(dGap <= low) 
			{
				// Since the template is fix with 19 rows (Determine by totalCRGAP)
				// Should we need more than 19 rows, we have to insert new rows
				if (totalRowsD >= totalCRGAP-1)
				{
					// When a new row is inserted, it will cause cell to unmerged
					// So we have to merged all the cells where the row is inserted
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, rD-1, rD, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+4, rD, rD);
					OO.mergeCells(xSpreadsheet2, column+5, column+9, rD, rD);
					OO.mergeCells(xSpreadsheet2, column+10, column+14, rD, rD);*/
					OO.mergeCells(xSpreadsheet2, column, column+6, rD, rD);
					OO.mergeCells(xSpreadsheet2, column+7, column+13, rD, rD);
					OO.mergeCells(xSpreadsheet2, column+14, column+19, rD, rD);
					totalCRGAP++;	// Increment totalCR to current no. of rows
				}
				
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", rD, column);
				rD++;			// Increment row to print target name on next row
				totalRowsD++;	// Increment total row which are filled with names
			}
			else if(dGap > low && dGap < high)
			{
				if (totalRowsM >= totalCRGAP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, rM-1, rM, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+4, rM, rM);
					OO.mergeCells(xSpreadsheet2, column+5, column+9, rM, rM);
					OO.mergeCells(xSpreadsheet2, column+10, column+14, rM, rM);*/
					OO.mergeCells(xSpreadsheet2, column, column+6, rM, rM);
					OO.mergeCells(xSpreadsheet2, column+7, column+13, rM, rM);
					OO.mergeCells(xSpreadsheet2, column+14, column+19, rM, rM);
					totalCRGAP++;
				}
					
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", rM, column+5);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", rM, column+7);
				rM++;
				totalRowsM++;
			}
			else if(dGap >= high)
			{
				if (totalRowsS >= totalCRGAP-1)
				{
					OO.insertRows(xSpreadsheet2, startColumn, endColumn, rS-1, rS, 1, 1);
					/*OO.mergeCells(xSpreadsheet2, column, column+4, rS, rS);
					OO.mergeCells(xSpreadsheet2, column+5, column+9, rS, rS);
					OO.mergeCells(xSpreadsheet2, column+10, column+14, rS, rS);*/
					OO.mergeCells(xSpreadsheet2, column, column+6, rS, rS);
					OO.mergeCells(xSpreadsheet2, column+7, column+13, rS, rS);
					OO.mergeCells(xSpreadsheet2, column+14, column+19, rS, rS);
					totalCRGAP++;
				}
					
				//OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", rS, column+10);
				OO.insertString(xSpreadsheet2, sTarget + " (" + sDept + ")", rS, column+14);
				rS++;
				totalRowsS++;
			}
		}
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END COMPETENCY GAP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		// Draw Ranking Chart
		drawChartRankToyota(target, department, gap, 2, maxScale);
	}
	
	/**
	 * 	Draw Group Ranking Report chart
	 */
	public void drawChartRankToyota(String Rating [], String Department [], double Result [], int type, int maxScale) throws IOException, Exception 
	{
		row = 10;
		int c = 18;
		int r = row;

		for(int i=Rating.length-1; i>=0; i--) 
		{
			//System.out.println("Rating[" + i + "] = " + Rating[i]);
			OO.insertString(xSpreadsheet3, Rating[i] + " (" + Department[i] + ")", r, c);
			OO.insertNumeric(xSpreadsheet3, Result[i], r, c+1);
			r++;
		}

		//set source data
		/*
		XTableChart xtablechart = OO.getChart(xSpreadsheet2, xSpreadsheet3, c, c+1, row-1, r-1, "Ranking", 27500, 30500, row, c);
		xtablechart = OO.setChartTitle(xtablechart, "");	
		xtablechart = OO.setAxes(xtablechart, "", "", true, true, true, 0, 0);
		OO.setChartProperties(xtablechart, true, true, true, true, true);
		*/
		OO.setSourceData(xSpreadsheet2, xSpreadsheet3, 0, c, c+1, row, r-1);

	}
	
	public double PastCP(String RTCode) throws SQLException 
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		double dPastCP = 0;
		
		boolean bPastSurveyExist = false;

		/*
		 *	Check whether there are any existing survey in the same Job Position as chosen survey
		 */
		query = "SELECT MAX(tblSurvey.SurveyID) AS SurveyID ";
		query = query + "FROM tblSurvey INNER JOIN tblAssignment ON tblSurvey.SurveyID = tblAssignment.SurveyID ";
		query = query + "WHERE (JobPositionID = " + surveyInfo[10] +") ";
		query = query + "AND tblSurvey.SurveyID < " + surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  	   		con=ConnectionBean.getConnection();
  	   		st=con.createStatement();
  	   		rs=st.executeQuery(query);
	
  		 	if (rs.next() != false)
  		 	{
 			//rsPastSurvey.next();
  		 		iPastSurveyID = rs.getInt("SurveyID");

  		 		if (iPastSurveyID > 0)
  		 		{
  		 			query = "SELECT DISTINCT TargetLoginID FROM tblAssignment WHERE SurveyID = " + iPastSurveyID;
 			
 				//Statement stmtID = db.con.createStatement();
 				//ResultSet rsPastTarget = stmtID.executeQuery(query);
 				//rsPastTarget.next();
  		 		}
 						
  		 		bPastSurveyExist = true;
  		 	}
 		// ~~~ END Past Survey Exist ~~~
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - PastCP - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		if (bPastSurveyExist == true)
		{
			if(surveyLevel == 0) 
			{
				if(reliabilityCheck == 0) 	// trimmed mean
				{		
					query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
					query += "ROUND(AVG(tblTrimmedMean.TrimmedMean), 2) AS Result FROM ";
					query += "tblTrimmedMean INNER JOIN Competency ON ";
					query += "tblTrimmedMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
					query += "tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
					query += "[User] ON [User].PKUser = tblTrimmedMean.TargetLoginID ";
					query += "WHERE tblTrimmedMean.SurveyID = " + iPastSurveyID;
					query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "' AND ";
					query += "tblTrimmedMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
					query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
					query += "WHERE SurveyID = " + surveyID + " AND RaterCode <> 'SELF' AND ";
					query += "RaterStatus IN (1, 2, 4) ";
					
					if(divID != 0) 
						query = query + " AND tblAssignment.FKTargetDivision = " + divID;
						//query = query + " AND [User].FKDivision = " + divID;
					if(deptID != 0) 
						query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
						//query = query + " AND [User].FKDepartment = " + deptID;
					if(groupSection != 0)
						query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
						//query = query + " AND [User].Group_Section = " + groupSection;
						
					query += ") ";
					
					//if(deptID != 0)
					//	query = query + " AND [User].FKDepartment = " + deptID;
					//if(divID != 0)
					//	query = query + " AND [User].FKDivision = " + divID;
					//if(groupSection != 0)
					//	query = query + " AND [User].Group_Section = " + groupSection;
						
					query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
					query += "ORDER BY Competency.PKCompetency";
					
				}
				else 
				{
					query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
					query += "ROUND(AVG(tblAvgMean.AvgMean), 2) AS Result FROM ";
					query += "tblAvgMean INNER JOIN Competency ON ";
					query += "tblAvgMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
					query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
					query += "[User] ON [User].PKUser = tblAvgMean.TargetLoginID ";
					query += "WHERE tblAvgMean.SurveyID = " + surveyID;
					query += " AND tblAvgMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "' AND ";
					query += "tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment INNER JOIN ";
					query += "[USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
					query += "WHERE SurveyID = " + iPastSurveyID + " AND RaterCode <> 'SELF' AND ";
					query += "RaterStatus IN (1, 2, 4) ";
					
					if(divID != 0) 
						query = query + " AND tblAssignment.FKTargetDivision = " + divID;
						//query = query + " AND [User].FKDivision = " + divID;
					if(deptID != 0) 
						query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
						//query = query + " AND [User].FKDepartment = " + deptID;
					if(groupSection != 0)
						query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
						//query = query + " AND [User].Group_Section = " + groupSection;
	
					query += ") ";
					
					//if(deptID != 0)
					//	query = query + " AND [User].FKDepartment = " + deptID;
					//if(divID != 0)
					//	query = query + " AND [User].FKDivision = " + divID;
					//if(groupSection != 0)
					//	query = query + " AND [User].Group_Section = " + groupSection;
						
					query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
					query += "ORDER BY Competency.PKCompetency";
					
				}
			}
			else 
			{
				if(reliabilityCheck == 1) 
				{
					query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
					query += "ROUND(AVG(tblAvgMean.AvgMean), 2) AS Result ";
					query += "FROM tblAvgMean INNER JOIN Competency ON ";
					query += "tblAvgMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
					query += "tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
					query += " inner join [User] on [User].PKUser = tblAvgMean.TargetLoginID ";
					query += "WHERE tblAvgMean.SurveyID = " + surveyID;
					query += " AND tblAvgMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "'";
					query += " AND tblAvgMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment ";
					query += "INNER JOIN [USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
					query += "WHERE SurveyID = " + iPastSurveyID + " AND RaterCode <> 'SELF' AND ";
					query += "RaterStatus IN (1, 2, 4) ";
					
					if(divID != 0) 
						query = query + " AND tblAssignment.FKTargetDivision = " + divID;
						//query = query + " AND [User].FKDivision = " + divID;
					if(deptID != 0) 
						query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
						//query = query + " AND [User].FKDepartment = " + deptID;
					if(groupSection != 0)
						query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
						//query = query + " AND [User].Group_Section = " + groupSection;

					query += ") ";
					
					//if(deptID != 0)
					//	query = query + " AND [User].FKDepartment = " + deptID;
					//if(divID != 0)
					//	query = query + " AND [User].FKDivision = " + divID;
					//if(groupSection != 0)
					//	query = query + " AND [User].Group_Section = " + groupSection;
						
					query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
					query += "ORDER BY Competency.PKCompetency";
		
				}
				else 
				{
					query = "SELECT Competency.PKCompetency AS CompetencyID, Competency.CompetencyName, ";
					query += "ROUND(AVG(tblTrimmedMean.TrimmedMean), 2) AS Result ";
					query += "FROM tblTrimmedMean INNER JOIN Competency ON ";
					query += "tblTrimmedMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
					query += "tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
					query += " inner join [User] on [User].PKUser = tblTrimmedMean.TargetLoginID ";
					query += "WHERE tblTrimmedMean.SurveyID = " + surveyID;
					query += " AND tblTrimmedMean.Type = 1 AND tblRatingTask.RatingCode = '" + RTCode + "'";
					query += " AND tblTrimmedMean.TargetLoginID IN (SELECT TargetLoginID FROM tblAssignment ";
					query += "INNER JOIN [USER] ON [USER].PKUser = tblAssignment.TargetLoginID ";
					query += "WHERE SurveyID = " + iPastSurveyID + " AND RaterCode <> 'SELF' AND ";
					query += "RaterStatus IN (1, 2, 4) ";
					
					if(divID != 0) 
						query = query + " AND tblAssignment.FKTargetDivision = " + divID;
						//query = query + " AND [User].FKDivision = " + divID;
					if(deptID != 0) 
						query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
						//query = query + " AND [User].FKDepartment = " + deptID;
					if(groupSection != 0)
						query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
						//query = query + " AND [User].Group_Section = " + groupSection;
						
					query += ") ";
					
					//if(deptID != 0)
					//	query = query + " AND [User].FKDepartment = " + deptID;
					//if(divID != 0)
					//	query = query + " AND [User].FKDivision = " + divID;
					//if(groupSection != 0)
					//	query = query + " AND [User].Group_Section = " + groupSection;
						
					query += " GROUP BY Competency.PKCompetency, Competency.CompetencyName ";
					query += "ORDER BY Competency.PKCompetency";
				}
			}
			
	  	   	try{
	  	   		con=ConnectionBean.getConnection();
	  	   		st=con.createStatement();
	  	   		rs=st.executeQuery(query);
		
	  		 	if (rs.next())
	  		 	{
	  		 		dPastCP = rs.getDouble("Result");
	  		 	}
	 		// ~~~ END Past Survey Exist ~~~
			
	  		}catch(Exception ex){
				System.out.println("GroupReport.java - PastCP - "+ex.getMessage());
			}
			finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
			
		}	//	bPastSurveyExist

		return dPastCP;
	}
	
	/**
	 * 	Method to call all methods from JSP.
	 *	@param int SurveyID
	 *	@param int GroupSection
	 *	@param int DeptID
	 *	@param int DivID
	 *	@param int PKUser
	 *	@param String Filename	Excel Filename
	 */
	public void ReportToyota(int surveyID, int groupSection, int deptID, int divID, int pkUser, String fileName) 
	{		
		try 
		{
			ST = new Setting();
			C = new Calculation();
			Q = new Questionnaire();
			EV = new EventViewer();
			assign = new AssignTarget_Rater();
			rscale = new RatingScale();
			
			vCompDetails.clear();
			vRatingTask.clear();
			vCompID.clear();
			
			vCompDetails = getCompetency(0);
			vRatingTask = getRatingTask();
			checkCPCPR();
			
			System.out.println("----Group Report Generation Starts----");

			InitializeSurveyToyota(surveyID, groupSection, deptID, divID);
			InitializeExcelToyota(fileName);
			
			String groupSect = "All";
			String deptName = "All";
			String divName = "All";
			String sJobLevel = "";
			sJobLevel = getJobLevel(surveyID);
			
			if(groupSection != 0)
				groupSect = getGroupName();
			if(deptID != 0)
				deptName = getDeptName();
			if(divID != 0)
				divName = getDivName();
			
			ReplacementToyota(divName, deptName, groupSect, sJobLevel);
			
			vCompDetails = getCompetency(1);
			
			InsertCPvsCPRToyota();
			InsertGapToyota();
			InsertCompetencyToyota();
			InsertTargetRankToyota();
			
			System.out.println("----Group Report Generation Completed----");

			String [] UserInfo = U.getUserDetail(pkUser);

			try {
				String temp = surveyInfo[4] + "(S); " + groupSect + "(G); " + deptName + "(Dept); " + divName + "(Div)";
				
				EV.addRecord("Print", "Rater Result", temp, UserInfo[2], UserInfo[11], UserInfo[10]);

			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}

		} catch (Exception SE) {
			System.err.println("a " + SE.getMessage());
		} finally {
			
			try {
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
				//OO.closeDoc(xDoc);
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
			}   
    	}

	}
	
	public Vector MeanResult(int group, int RTID, int compID, int KBID) throws SQLException 
	{
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		int reliabilityCheck = C. ReliabilityCheck(surveyID);
		
		String formula = "";
		int totalCount = getTotalRaters(group);
		String filter = "";
		

		Vector v = new Vector();
		
		switch(group) 
		{	// 1 for all, 4 for self
			case 1: filter = "and tblAssignment.RaterCode <> 'SELF'";
					break;
			case 2: filter = "and tblAssignment.RaterCode LIKE 'SUP%'";
					break;
			case 3: filter = "and tblAssignment.RaterCode LIKE 'OTH%'";
					break;
			case 4: filter = "and tblAssignment.RaterCode = 'SELF'";
					break;	
		}	

		//What's the need to check for totalCount? To determine the need to go through reliability check?
		if(totalCount == 1)
			formula = "sum(tblResultCompetency.Result) AS Result ";
		else if(totalCount == 2)
			formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2)) AS Result ";
		else 
		{
			if(reliabilityCheck == 0) 
			{	//Trimmed Mean
				formula = "cast(cast((Sum(tblResultCompetency.Result)-(Max(tblResultCompetency.Result)+";
				formula = formula + "Min(tblResultCompetency.Result))) as float)/(Count(tblResultCompetency.Result)-2) as numeric(38,2)) AS Result ";
			}
			else //Reliability Index
				formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2))  AS Result ";
		}

		if(totalCount != 0) 
		{
			if(surveyLevel == 0) 
			{
				query = query + "SELECT  tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
				query = query + "RatingTaskID, " + formula + " FROM [User] ";
				query = query + "INNER JOIN (tblAssignment INNER JOIN tblResultCompetency ON ";
				query = query + "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID) ";
				query = query + "ON [User].PKUser = tblAssignment.TargetLoginID INNER JOIN Competency ON ";
				query = query + "tblResultCompetency.CompetencyID = Competency.PKCompetency ";
				
				query = query + "Where SurveyID = " + surveyID;
				query = query + " and tblResultCompetency.CompetencyID = " + compID;
				query = query + " and tblResultCompetency.RatingTaskID = " + RTID + " AND tblAssignment.RaterStatus IN (1,2,4) ";
				query = query + filter;
				
				if(divID != 0) 
					query = query + " AND tblAssignment.FKTargetDivision = " + divID;
					//query = query + " AND [User].FKDivision = " + divID;
				if(deptID != 0) 
					query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
					//query = query + " AND [User].FKDepartment = " + deptID;
				if(groupSection != 0)
					query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
					//query = query + " AND [User].Group_Section = " + groupSection;
				
				query = query + " GROUP BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID, ";
				query = query + "Competency.CompetencyName";
				query = query + " ORDER BY tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
				query = query + "tblResultCompetency.RatingTaskID";
			} 
			else 
			{
				//16-Aug-06 (Rianto): Added to fix Group report issue of displaying wrong value in the charts
				//					  Quick solution. May have faster and more effecient existing methods. Change if needed.
				if(KBID == 0) //To retrieve Competency results (one usage is in GroupReport)
				{
					if(group == 4) //Only for Self
					{
						/*
						query = query + "SELECT KeyBehaviour.FKCompetency, Competency.CompetencyName, tblResultBehaviour.RatingTaskID, CAST(AVG(CAST(tblResultBehaviour.Result AS float)) AS numeric(38, 2)) AS Result ";
						query = query + "FROM [User] INNER JOIN tblAssignment INNER JOIN tblResultBehaviour ON tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ON [User].PKUser = tblAssignment.TargetLoginID INNER JOIN ";
						query = query + "KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour INNER JOIN ";
						query = query + "Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
						query = query + "WHERE (tblAssignment.SurveyID = 463) AND (KeyBehaviour.FKCompetency = 5) AND (tblResultBehaviour.RatingTaskID = 1) AND ";
						query = query + "(tblAssignment.RaterStatus IN (1, 2, 4)) AND (tblAssignment.RaterCode = N'SELF') ";
						query = query + "GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, Competency.CompetencyName ";
						query = query + "ORDER BY KeyBehaviour.FKCompetency, Competency.CompetencyName, tblResultBehaviour.RatingTaskID ";
						*/
						query += "SELECT tblAvgMean.CompetencyID, Competency.CompetencyName, tblAvgMean.RatingTaskID, CAST(AVG(CAST(tblAvgMean.AvgMean AS float)) AS numeric(38, 2)) AS Result ";
						query += "FROM tblAvgMean INNER JOIN Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency ";
						query += "WHERE (tblAvgMean.SurveyID = " + surveyID + ") AND (tblAvgMean.CompetencyID = " + compID + ") AND (tblAvgMean.Type = 4) AND (tblAvgMean.RatingTaskID = 1) "; //Only for CP
						query += "GROUP BY tblAvgMean.CompetencyID, Competency.CompetencyName, tblAvgMean.RatingTaskID ";
			
						//System.out.println("Self\n" + query);
					}
					else
					{	//For CP ALL & CPR ALL
						if(reliabilityCheck == 0) 
						{	//Trimmed Mean
							query+= "SELECT Competency.PKCompetency, Competency.CompetencyName,tblTrimmedMean.RatingTaskID, CAST(AVG(CAST(tblTrimmedMean.TrimmedMean AS float)) AS numeric(38, 2)) AS Result ";
							query+= "FROM tblTrimmedMean INNER JOIN Competency ON tblTrimmedMean.CompetencyID = Competency.PKCompetency ";
						    query+= "WHERE (tblTrimmedMean.SurveyID = '" + surveyID + "') AND (tblTrimmedMean.RatingTaskID = '" + RTID + "') AND (tblTrimmedMean.CompetencyID = " + compID + ") ";
						    query+= "GROUP BY Competency.CompetencyName, Competency.PKCompetency ";
						    //System.out.println(query);
						}
						else
						{	//Avg Mean
							query+= "SELECT Competency.PKCompetency, Competency.CompetencyName, tblAvgMean.RatingTaskID, CAST(AVG(CAST(tblAvgMean.AvgMean AS float)) AS numeric(38, 2)) AS Result ";
							query+= "FROM tblAvgMean INNER JOIN Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency ";
						    query+= "WHERE (tblAvgMean.SurveyID = '" + surveyID + "') AND (tblAvgMean.RatingTaskID = '" + RTID + "') AND (tblAvgMean.CompetencyID = " + compID + ") ";
						    query+= "GROUP BY Competency.CompetencyName, Competency.PKCompetency ";
						    //System.out.println(query);
						}
					}
				}	
				else
				{
					query = query + "SELECT KeyBehaviour.FKCompetency, Competency.CompetencyName, ";
					
					query = query + "tblResultBehaviour.RatingTaskID, ";
					
					query = query + "CAST(AVG(CAST(tblResultBehaviour.Result AS float)) AS numeric(38, 2)) AS Result, ";
					query = query + "tblResultBehaviour.KeyBehaviourID, KeyBehaviour.KeyBehaviour ";
					query = query + " FROM [User] INNER JOIN tblAssignment INNER JOIN ";
					query = query + "tblResultBehaviour ON ";
					query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ON ";
					query = query + "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN KeyBehaviour ";
					query = query + "ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour INNER JOIN ";
					query = query + "Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
					
					query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
					query = query + " and KeyBehaviour.FKCompetency = " + compID;
					query = query + " and tblResultBehaviour.KeyBehaviourID = " + KBID;
					query = query + " and tblResultBehaviour.RatingTaskID = " + RTID  + " AND tblAssignment.RaterStatus IN (1,2,4) ";
					query = query + filter;
					
					if(divID != 0) 
						query = query + " AND tblAssignment.FKTargetDivision = " + divID;
						//query = query + " AND [User].FKDivision = " + divID;
					if(deptID != 0) 
						query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
						//query = query + " AND [User].FKDepartment = " + deptID;
					if(groupSection != 0)
						query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
						//query = query + " AND [User].Group_Section = " + groupSection;
					
					query = query + " GROUP BY tblResultBehaviour.RatingTaskID, ";
					query = query + "KeyBehaviour.FKCompetency, Competency.CompetencyName, tblResultBehaviour.KeyBehaviourID, ";
					query = query + "KeyBehaviour.KeyBehaviour ORDER BY KeyBehaviour.FKCompetency, ";
					query = query + "Competency.CompetencyName, tblResultBehaviour.KeyBehaviourID, ";
					query = query + "KeyBehaviour.KeyBehaviour, tblResultBehaviour.RatingTaskID";
					
					//System.out.println("KB != 0\n" + query);
				} //end if(KBID == 0)
				
			}
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			
	  	   	try{
	  		   con=ConnectionBean.getConnection();
	  		   st=con.createStatement();
	  		   rs=st.executeQuery(query);
			
	  		   while(rs.next()) {
		 			String [] arr = new String[4];
		 			arr[0] = rs.getString(1);
		 			arr[1] = rs.getString(2);
		 			arr[2] = rs.getString(3);
		 			arr[3] = rs.getString(4);
		 			v.add(arr);
	  		   }
	  		}catch(Exception ex){
				System.out.println("GroupReport.java - MEanResult - "+ex.getMessage());
			}
			finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
			
			return v;
		}
		else
			return null;
	}

	/**
	 * 	Retrieves the average mean of KB for a specific competency
	 * 	This is only applied in KB Level Analysis
	 *	@param int group	GroupID		1=All, 2=Sup, 3=Oth, 4=Self
	 *	@param int RTID		RatingTaskID
	 *	@param int compID	CompetencyID
	 *
	 *	@return ResultSet KBMean
	 */
	public Vector KBMean(int group, int RTID, int compID) throws SQLException 
	{
		String query = "";
		//int totalCount = TotalRaters(group);
		String filter = "";
		
		switch(group) {	
			case 1: filter = "and tblAssignment.RaterCode <> 'SELF'";
					break;
			case 2: filter = "and tblAssignment.RaterCode LIKE 'SUP%'";
					break;
			case 3: filter = "and tblAssignment.RaterCode LIKE 'OTH%'";
					break;
			case 4: filter = "and tblAssignment.RaterCode = 'SELF'";
					break;	
		}	

		query = "SELECT KeyBehaviour.FKCompetency, Competency.CompetencyName, ";					
		query = query + "CAST(AVG(CAST(tblResultBehaviour.Result AS float)) AS numeric(38, 2)) AS Result";
		query = query + " FROM [User] INNER JOIN tblAssignment INNER JOIN ";
		query = query + "tblResultBehaviour ON ";
		query = query + "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ON ";
		query = query + "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN KeyBehaviour ";
		query = query + "ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour INNER JOIN ";
		query = query + "Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
		query = query + " and KeyBehaviour.FKCompetency = " + compID;
		query = query + " and tblResultBehaviour.RatingTaskID = " + RTID  + " AND tblAssignment.RaterStatus IN (1,2,4) ";
		query = query + filter;
		
		if(divID != 0) 
			query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			//query = query + " AND [User].FKDivision = " + divID;
		if(deptID != 0) 
			query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			//query = query + " AND [User].FKDepartment = " + deptID;
		if(groupSection != 0)
			query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			//query = query + " AND [User].Group_Section = " + groupSection;
			
		query = query + " GROUP BY KeyBehaviour.FKCompetency, Competency.CompetencyName, ";
		query = query + "KeyBehaviour.KeyBehaviour";
		query = query + " ORDER BY KeyBehaviour.FKCompetency, ";
		query = query + "Competency.CompetencyName";
		
		//System.out.println(query);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   while(rs.next()) {
	 			String [] arr = new String[3];
	 			arr[0] = rs.getString(1);
	 			arr[1] = rs.getString(2);
	 			arr[2] = rs.getString(3);
	 			v.add(arr);
  		   }
  		}catch(Exception ex){
			System.out.println("IndividualReport.java - KBMEan - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}	
	
	/**
	 * Get the competency avg mean for CP and CPR.
	 * The table to store Competency Level and KB Level Result are not the same.
	 *
	 * For Competency Level:
	 *		ReliabilityIndex is stored in tblAvgMean
	 *		TrimmedMean is stored in tblTrimmedMean
	 *
	 * For KBLevel, both are stored in tblAvgMean
	 *
	 */
	public Vector getCompAvg(int surveyID, Vector vCompID, String rtCode)
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		Vector vCompCP 	= new Vector();
		Vector vScore 	= new Vector();
		Vector vResult = new Vector();
		
		if(surveyLevel == 1) {	//KB Level
			
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID = " + surveyID;
			query += " AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = '" + rtCode + "') ";
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ")";
			query += " GROUP BY tblAvgMean.CompetencyID";
			
		}else {	// Competency Level
			
			if(reliabilityCheck == 0) {
				tableName = "tblTrimmedMean";
				columnName = "TrimmedMean";
			}
			else {
				tableName = "tblAvgMean";
				columnName = "AvgMean";
			}
				
			query = "SELECT " + tableName + ".CompetencyID, ROUND(AVG(" + tableName + "." + columnName + "), 2) AS AvgMean ";
			query += "FROM " + tableName + " INNER JOIN tblRatingTask ON ";
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID = " + surveyID;
			query += " AND tblRatingTask.RatingCode = 'CP' ";
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			query += "GROUP BY " + tableName + ".CompetencyID";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   while(rs.next()) {			
	 			String fkComp 	= rs.getString("CompetencyID");
	 			String score	= rs.getString("AvgMean");
	 			
	 			vCompCP.add(fkComp);
	 			vScore.add(score);
  		   }
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getCompAvg - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		//copy all the score into the correct order
		for(int i=0; i<vCompID.size(); i++) {
			
			String score = "0";
			
			String sCompScore = (String)vCompID.elementAt(i).toString();
			int element = vCompCP.indexOf(sCompScore);
			
			if(element != -1)
				score = (String)vScore.elementAt(element);
				
			vResult.add(score);
				
		}
		return vResult;
	}
	
	/**
	 * 	Retrieves the average mean from tblAvgMean/tblTrimmedMean based on each Competency to be displayed in target gap.
	 *	@param int SurveyID
	 *	@param int TargetID		PKUser
	 *
	 *	@return double AvgCP	Average CP for particular target
	 */
	public double getAvgMeanForGap(int surveyID, int targetID) {
		
		String query = "";
		double dAvg = 0;
		int reliability = C.ReliabilityCheck(surveyID);
		
		
		if(reliability == 1) {
			query = "Select cast(AVG(AvgMean) as numeric(38,2)) as Result from tblAvgMean ";
			query += "INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "where SurveyID = " + surveyID;
			query = query + " AND TargetLoginID = " + targetID;
			query = query + " and Type = 1";
			query += " AND (tblRatingTask.RatingCode IN ('CP')) ";
		} else {
			query = "Select ROUND(AVG(TrimmedMean), 2) as Result from tblTrimmedMean ";
			query += "INNER JOIN tblRatingTask ON tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "where SurveyID = " + surveyID;
			query = query + " and TargetLoginID = " + targetID + " and Type = 1 ";
			query += " AND (tblRatingTask.RatingCode IN ('CP')) ";
		}
			
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		   if (rs.next())
				dAvg = rs.getDouble("Result");
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getAvgMeanForGap - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
			
		return dAvg;
	}
	
	public double getCompGap(int FKUser) throws SQLException 
	{
		String query = "SELECT CompetencyID, round(AVG(Gap), 2) AS Gap FROM tblGap WHERE SurveyID = " + surveyID;
		query += " AND TargetLoginID = " + FKUser + " GROUP BY CompetencyID ORDER BY CompetencyID";
		double gap = 0;
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
		
  		 while(rs.next())
 			gap += rs.getDouble(2);	
 		
		
  		}catch(Exception ex){
			System.out.println("GroupReport.java - getCompGap - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return gap;
	}
	
	/*
	  * Sort the gap.
	  *	@param int type		0 = DESC, 1 = ASC
	 */
	public Vector sortRanking(Vector vGapLocal, int type) throws SQLException, Exception 
	{
		Vector vLocal = (Vector) vGapLocal.clone();
		Vector vSorted = new Vector();
		double max  = 0; //highest score
		double temp = 0; //temp score
		int curr  = 0; //curr highest element
		
		while(!vLocal.isEmpty()) 
		{
			max = Double.valueOf(((String [])vLocal.elementAt(0))[2]).doubleValue();
			curr = 0;
			
			// do sorting here
			for(int t=1; t<vLocal.size(); t++) {
				temp = Double.valueOf(((String [])vLocal.elementAt(t))[2]).doubleValue();
			
				if(type == 0) {
					if(temp > max) {
						max = temp;
						curr = t;
					}
				} else {
					if(temp < max) {
						max = temp;
						curr = t;
					}
				}
			}
			
			String info [] = {((String [])vLocal.elementAt(curr))[0], ((String [])vLocal.elementAt(curr))[1], ((String [])vLocal.elementAt(curr))[2]};
			vSorted.add(info);
			
			vLocal.removeElementAt(curr);
		}
		
		return vSorted;
	}
	
	//*****************MAIN************************************************/
	
	public static void 	main (String [] args)throws SQLException, Exception 
	{	
		GroupReport_NoCPR IR = new GroupReport_NoCPR();

		int surveyID = 475;
				int groupSection = 0;
				int deptID = 0;
				int divID = 0;
				
				long now = System.currentTimeMillis();
				//new java.util.Date(t1)
				
				
				//IR.Report(surveyID, groupSection, deptID, divID, 6404, "GroupReport(PacRim Staff).xls", 2); //type = 1 (simple), 2 (full)
				//IR.ReportToyota(surveyID, groupSection, deptID, divID, 101, "GroupReportToyota.xls");
				long then = System.currentTimeMillis();
				
				//IR.Report(568, groupSection, deptID, divID, 6403, "GroupReport(TEST).xls", 1); //type = 1 (simple), 2 (full)
				IR.Report(487,509,66,1,6403,"Report11.xls",1);
				//System.out.println((then-now) / 1000);
				System.exit(1);
		
	}
}