/**
 * 
 */
package CP_Classes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voUser;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;
/**
 * This class implements all the operations for Group Summary Report in Excel.
 * It implements OpenOffice API.
 * 
 * Change Log
 * ==========
 *
 * Date        By				Method(s)            														Change(s) 
 * =============================================================================================================================================
 * 29/05/12	   Albert	InitializeSurvey(int,int,Vector<Integer>,Vector<Integer>)					Create override methods to accommodate the changes made
 * 						Report(int,int,Vector<Integer>,Vector<Integer>,String)
 * 						getTargetInfo(int,int,Vector<Integer>,Vector<Integer>)
 * 						
 * 
 */
/**
 * This class is used to generate Group Summary Report
 * @author Qiao Li
 *
 */
public class GroupSummaryReport {
	private OpenOffice OO = new OpenOffice();
	private Setting ST = new Setting();
	
	private XMultiComponentFactory xRemoteServiceManager = null;
	private XComponent xDoc = null;
	private XSpreadsheet xSpreadsheet = null;
	
    private String storeURL;
    private int surveyID;
	private String surveyInfo [];
	
	private int numRaterType = 0;
	private Vector Competency;//a vector contains voCompetency objects
	private Vector CPRaterType = new Vector(0);//contains int rater type numbers for CP raters
	private Vector CPRaterCode = new Vector(0);//contains Strings of CP rater codes
	private Vector otherRaterType = new Vector(0);//contains int rater type numbers for CPR or FPR
	private Vector otherRaterCode = new Vector(0);//contains Strings of "CPR" or "FPR"
	private Vector Target; //a vector contains voUser objects for this report
	private double[][][] value;
	
	private int row = 1;
	
	
	/**
	 * Retrieves the survey details and stores in an array.
	 */
	private String [] SurveyInfo() throws SQLException
	{
		int infoLength = 7;
		String [] info = new String[infoLength];
		
		String query = "SELECT tblSurvey.LevelOfSurvey, tblJobPosition.JobPosition, tblSurvey.AnalysisDate, ";
		query = query + "tblOrganization.OrganizationCode, tblOrganization.OrganizationName, tblSurvey.SurveyName,tblOrganization.OrganizationLogo FROM tblSurvey INNER JOIN ";
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
  			   for(int i=0; i<infoLength; i++) {
  				   info[i] = rs.getString(i+1);		
  				   
  			   }
  		   }
			
  	   	}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - SurveyInfo - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return info;
	}	
	
	/**
	 * Retrieves competencies under the surveyID of this instance.
	 */
	private Vector getCompetencyByName() throws SQLException 
	{
		String query = "";
		Vector v = new Vector();
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		if(surveyLevel == 0) {
			query = query + "SELECT tblSurveyCompetency.CompetencyID, Competency.CompetencyName, ";
			query = query + "CompetencyDefinition FROM tblSurveyCompetency INNER JOIN Competency ON ";
			query = query + "tblSurveyCompetency.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblSurveyCompetency.SurveyID = " + surveyID;
			query = query + " ORDER BY Competency.CompetencyName";
			
		} else {
			
			query = query + "SELECT DISTINCT tblSurveyBehaviour.CompetencyID, Competency.CompetencyName, ";
			query = query + "Competency.CompetencyDefinition FROM Competency INNER JOIN ";
			query = query + "tblSurveyBehaviour ON Competency.PKCompetency = tblSurveyBehaviour.CompetencyID ";
			query = query + "AND Competency.PKCompetency = tblSurveyBehaviour.CompetencyID ";
			query = query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID;
			query = query + " ORDER BY Competency.CompetencyName";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		
  		   while(rs.next()) {
  			   voCompetency vo = new voCompetency();
  			   vo.setCompetencyID(rs.getInt("CompetencyID"));
  			   vo.setCompetencyName(rs.getString("CompetencyName"));
  			   vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
  			   v.add(vo);
		
  		   }
			
  	   	}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - CompetencyByName - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	
	/**
	 * Retrieves targets under the surveyID of this instance.
	 */
	private Vector getTargetInfo() throws SQLException 
	{
		String query = "";
		Vector v = new Vector();
		
		query = query + "SELECT Distinct tblAvgMean.TargetLoginID, [User].GivenName, [User].FamilyName ";
		query = query + "FROM tblAvgMean INNER JOIN [User] ON ";
		query = query + "tblAvgMean.TargetLoginID = [User].PKUser ";
		query = query + "WHERE SurveyID = " + surveyID;
		query = query + " order by [User].GivenName";


		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		
  		   while(rs.next()) {
  			   voUser vo = new voUser();
  			   vo.setTargetLoginID(rs.getInt("TargetLoginID"));
  			   vo.setGivenName(rs.getString("GivenName"));
  			   vo.setFamilyName(rs.getString("FamilyName"));
  			   v.add(vo);
		
  		   }
			
  	   	}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - getTargetInfo - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}
	/**
	 * Retrieves targets under the surveyID of this instance.
	 */
	private Vector getTargetInfo(int surveyID, int divID, Vector<Integer> deptID, Vector<Integer> groupSection) throws SQLException 
	{
		String query = "";
		Vector v = new Vector();
		
		query = query + "SELECT Distinct tblAvgMean.TargetLoginID, [User].GivenName, [User].FamilyName ";
		query = query + "FROM tblAvgMean INNER JOIN [User] ON ";
		query = query + "tblAvgMean.TargetLoginID = [User].PKUser ";
		query = query + "WHERE SurveyID = " + surveyID;
		
		if (groupSection!=null && groupSection.elementAt(0) > 0){
			if(groupSection.size()==1) query += "AND [User].Group_Section = " + groupSection.elementAt(0) + " ";
			else{
				for(int i=0; i<groupSection.size(); i++){
					if(groupSection.elementAt(i)==0) break;
					else {
						if(i==0) query = query + "AND ([User].Group_Section = " + groupSection.elementAt(i) + " ";
						else if(i==(groupSection.size()-1)) query = query + "OR [User].Group_Section = " + groupSection.elementAt(i) + ") ";
						else query = query + "OR [User].Group_Section = " + groupSection.elementAt(i) + " ";
					}
				}
			}
		}

		if (deptID!=null && deptID.elementAt(0) > 0){
			if(deptID.size()==1) query += "AND [User].FKDepartment = " + deptID.elementAt(0) + " ";
			else{
				for(int i=0; i<deptID.size(); i++){
					if(deptID.elementAt(i)==0) break;
					else {
						if(i==0) query = query + "AND ([User].FKDepartment = " + deptID.elementAt(i) + " ";
						else if(i==(deptID.size()-1)) query = query + "OR [User].FKDepartment = " + deptID.elementAt(i) + ") ";
						else query = query + "OR [User].FKDepartment = " + deptID.elementAt(i) + " ";
					}
				}
			}
		}
		query = query + " order by [User].GivenName";


		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

  	   	try{
  		   con=ConnectionBean.getConnection();
  		   st=con.createStatement();
  		   rs=st.executeQuery(query);
  		
  		   while(rs.next()) {
  			   voUser vo = new voUser();
  			   vo.setTargetLoginID(rs.getInt("TargetLoginID"));
  			   vo.setGivenName(rs.getString("GivenName"));
  			   vo.setFamilyName(rs.getString("FamilyName"));
  			   v.add(vo);
		
  		   }
			
  	   	}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - getTargetInfo - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
  	   	
		System.out.println(" GroupSummarySPF.java   now target size is " +v.size() + "with survey ID is " + surveyID);
		return v;
	}
	
	/**
	 * get other rating tasks besides CP for the particular survey.
	 */
	private void getOtherRT() throws SQLException 
	{
		String query = "SELECT distinct tblRatingTask.RatingTaskID, tblRatingTask.RatingCode ";
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
		
  		   if(rs.next()){
	 			otherRaterType.add(new Integer(rs.getInt("RatingTaskID")));
	 			otherRaterCode.add(rs.getString("RatingCode"));
  		   }
  		}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - getOtherRT - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

	}
	/**
	 * retrieve the value for splitOthersOption according to the surveyID of this instance
	 * @return splitOthersOption
	 */
	private int getSplitOthersOption() {
		String query = "Select * from tblSurvey Where surveyID = " + surveyID;
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
					.println("GroupSummaryReport.java - getSplitOthersOption - "
							+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return 0;
	}
	
	/**
	 * Retrieve the information for various rater types of CP Raters
	 * and store inside the vectors of this instance
	 */
	private void getCPRT() 
	{
		CPRaterType.add(new Integer(1));
		CPRaterCode.add("CP(All)");
		CPRaterType.add(new Integer(2));
		CPRaterCode.add("CP(Sup)");
		if (getSplitOthersOption() == 0) {
			CPRaterType.add(new Integer(3));
			CPRaterCode.add("CP(Others)");
			CPRaterType.add(new Integer(4));
			CPRaterCode.add("CP(Self)");
		} else {
			CPRaterType.add(new Integer(5));
			CPRaterCode.add("CP(Sub)");
			CPRaterType.add(new Integer(6));
			CPRaterCode.add("CP(Peers)");
			CPRaterType.add(new Integer(4));
			CPRaterCode.add("CP(Self)");
		}

	}
	
	/**find out how many columns (Rating Tasks) are there for each competency and fill in the details
	 * 
	 */
	private void initializeRatingTaskInfo(){
		try{
			CPRaterType = new Vector(0);
			CPRaterCode = new Vector(0);
			otherRaterType = new Vector(0);
			otherRaterCode = new Vector(0);
			getOtherRT();
			getCPRT();
		}
		catch (SQLException E) {
			System.err.println("GroupSummaryReport.java - initializeRatingTaskInfo - "+ E);
		}
	}
	
	/**
	 * retrieve the result value according to targetLoginID, Competency, Rating Task
	 * @param TargetLoginID
	 * @param CompetencyID
	 * @param RatingTaskID
	 * @param Type
	 * @return the result value
	 */
	private double getSummaryValue(int TargetLoginID, int CompetencyID, int RatingTaskID, int Type){
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[0]);
		if(surveyLevel == 0) {
		
			query = query + "SELECT AvgMean as Result ";
			query = query + "FROM tblAvgMean WHERE SurveyID = " + surveyID + " AND TargetLoginID = " + TargetLoginID;
			query = query + " AND CompetencyID = " + CompetencyID + " and RatingTaskID = " + RatingTaskID;
			if (RatingTaskID==1){
				query += "AND [Type] = " + Type;
			}
			query = query + " ORDER BY Type";
			
		} else {
			System.out.println("SurveyLevel is not 0");
			query = "SELECT CAST(AVG(AvgMean) AS numeric(38, 2)) AS Result ";
			query = query + "FROM tblAvgMean WHERE SurveyID = " + surveyID + " AND TargetLoginID = " + TargetLoginID;
			query = query + " AND CompetencyID = " + CompetencyID + " and RatingTaskID = " + RatingTaskID;	
			if (RatingTaskID==1){
				query += "AND [Type] = " + Type;
			}
			query = query + " GROUP BY CompetencyID, Type ORDER BY Type";
		}
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs != null & rs.next())
				//System.out.println("The result : " + rs.getDouble("Result"));
				return rs.getDouble("Result");
		
		} catch (SQLException E) {
			System.err
					.println("GroupSummaryReport.java - getSummaryValue - "
							+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return 0;
		
	}

	/**
	 * retrieve all the results for each target in this survey and put them into the value array
	 */
	private void InitializeValue(){
		int numOtherRater = otherRaterType.size();
		numRaterType = CPRaterType.size()+numOtherRater;
		value = new double[Target.size()][Competency.size()][numRaterType];
		for (int i = 0; i < Target.size();i++){
			for (int j = 0; j < Competency.size();j++){
				for (int k = 0; k < numRaterType;k++){
					if (k < otherRaterType.size()){
						value[i][j][k]= getSummaryValue(((voUser) Target.elementAt(i)).getTargetLoginID(),
								((voCompetency) Competency.get(j)).getCompetencyID(), 
								((Integer) otherRaterType.elementAt(k)).intValue(), 1);
					}
					else{
						value[i][j][k]= getSummaryValue(((voUser) Target.elementAt(i)).getTargetLoginID(),
								((voCompetency) Competency.get(j)).getCompetencyID(), 1, 
								((Integer) CPRaterType.elementAt(k-numOtherRater)).intValue());
					}
				}
			}
		}
	}
	
	/**
	 * Initializes all processes dealing with Survey.
	 */
	private void InitializeSurvey(int surveyID) throws SQLException, IOException
	{
		
		//column = 0;
		this.surveyID = surveyID;
		surveyInfo = SurveyInfo();
		Competency = getCompetencyByName();
		Target = getTargetInfo();
		initializeRatingTaskInfo();
		InitializeValue();
		
	}
	private void InitializeSurvey(int surveyID, int divID, Vector<Integer> deptIDList, Vector<Integer> groupIDList) throws SQLException, IOException
	{
		//column = 0;
		this.surveyID = surveyID;
		surveyInfo = SurveyInfo();
		Competency = getCompetencyByName();
		Target = getTargetInfo(surveyID, divID, deptIDList, groupIDList);
		initializeRatingTaskInfo();
		InitializeValue();
		
	}
	
	/***************************************** SPREADSHEET ********************************************************/
	
	
	private void InitializeExcel(String sSavedFileName, String sTemplateName) throws IOException, Exception
	{		
		storeURL 	= "file:///" + ST.getOOReportPath() + sSavedFileName;
		String templateURL 	= "file:///" + ST.getOOReportTemplatePath() + sTemplateName;
		
		xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager");
		
		xDoc = OO.openDoc(xRemoteServiceManager, templateURL);
		
		
		//save as the template into a new file first. This is to avoid the template being used.		
		OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);		
		OO.closeDoc(xDoc);
		
		//open up the saved file and modify from there
		xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
		xSpreadsheet = OO.getSheet(xDoc, "Sheet1");
	}
	
	/**
	 * Replace words with <> tags with another word
	 * @throws Exception
	 * @throws IOException
	 */
	public void Replacement() throws Exception, IOException
	{
		String company = surveyInfo[3];
		String org = surveyInfo[4];
		String survey = surveyInfo[5];
		
		OO.findAndReplace(xSpreadsheet, "<Company>", company);
		OO.findAndReplace(xSpreadsheet, "<Org>", org);
		OO.findAndReplace(xSpreadsheet, "<Survey Name>", survey);
	}
	
	/**
	 * put in the competency names and rater task codes in the spreadsheet
	 */
	private void insertCompetency(){
		try {
			int column = 1;
			
			for (int i = 0; i < Competency.size(); i++) {
				String statement = ((voCompetency) Competency.get(i)).getCompetencyName();
				OO.insertString(xSpreadsheet,
						UnicodeHelper.getUnicodeStringAmp(statement),
						row, column);
				OO.mergeCells(xSpreadsheet, column, column+numRaterType-1, row, row);
				OO.setRowHeight(xSpreadsheet, row, column, 550);
				String RT = "";
				int j = 0;
				for (j= 0; j < otherRaterCode.size();j++){
					RT = (String) otherRaterCode.get(j)+"(All)";
					OO.insertString(xSpreadsheet, RT, row+1, column);
					column++;
				}
				for (j= 0; j < CPRaterCode.size();j++){
					RT = (String) CPRaterCode.get(j);
					OO.insertString(xSpreadsheet, RT, row+1, column);
					column++;
				}
			}
			OO.setFontBold(xSpreadsheet, 1, column-1, row, row);
			OO.setTableBorder(xSpreadsheet, 1, column-1, row, row+1, true, true, true,true, true, true);
			
			//Insert new column 'No. below maxValue/2', Chun Yeong 1 Aug 2011
			OO.insertString(xSpreadsheet, "No. below " + MaxScale()/(2*1.0), row, column);
			
			row +=2;
		} 
		catch (Exception e) {
			System.err.println("GroupSummaryReport.java - insertCompetency - " + e);
		}
	}
	
	/**
	 * put in the target names in the spreadsheet
	 */
	private void insertUser(){
		try {
			int column = 0;
			String target = "";
			int u_row = 0;
			if(Target.size()!=0){
				for (u_row = row; u_row < row + Target.size(); u_row++) {
					target = ((voUser) Target.get(u_row - row)).getGivenName()+" "
							+ ((voUser) Target.get(u_row - row)).getFamilyName();
					System.out.println("This is the target" + target);
					OO.insertString(xSpreadsheet, UnicodeHelper .getUnicodeStringAmp(target), u_row, column);
				}
				OO.setTableBorder(xSpreadsheet, column, column, row, u_row-1, true, true, true,true, true, true);
				OO.setFontBold(xSpreadsheet, column, column, row, u_row-1);
			
				//Add a new row 'Number highlighted', Chun Yeong 1 Aug 2011
				OO.insertString(xSpreadsheet, "Number highlighted", u_row, column);
			}
		} catch (Exception e) {
			System.err.println("GroupSummaryReport.java - insertUser - " + e);
		}
		
	}
	/**
	 * put in all the report information from the survey
	 */
	private void InsertValue(){
		int[] address;
		
		//Denise 06/01/2010 to check the hideNA and NA excluded option of the survey
		Create_Edit_Survey CE = new Create_Edit_Survey();
		boolean hideZero = CE.getHideNAOption(surveyID)==1||CE.getNA_Included(surveyID) ==0;

		
		try {
			address = OO.findString(xSpreadsheet, "<Report>");

			row = address[1];
			
			int column = 1;
			OO.findAndReplace(xSpreadsheet, "<Report>", "");
			insertCompetency();
			insertUser();
			
			int start_row = row;
			
			//variables for column count and row count
			int[] colCount = new int[numRaterType*Competency.size()];
			int supCount = 0;
			int subCount = 0;
			int peerCount = 0;
			int selfCount = 0;
			String NoBelowPar = "";
			
			if(Target.size()!=0){
				for (int i = 0; i < Target.size();i++){
					supCount = 0;
					subCount = 0;
					peerCount = 0;
					selfCount = 0;
					column = 1;
					for (int j = 0; j < Competency.size();j++){
						for (int k = 0; k < numRaterType;k++){
							if (!(hideZero && value[i][j][k] ==0)){ //Denise 06/01/2010 if the survey requires hideNA or NA excluded then not output number 0
								OO.insertNumeric(xSpreadsheet, value[i][j][k], row, column);
								//if value is less than the maxValue/2 , Chun Yeong 1 Aug 2011
								//Color the cell, increase the horizontal count, increase the vertical count
								if(value[i][j][k] < (MaxScale()/(2*1.0))){
									OO.setBGColor(xSpreadsheet, column, column, row, row, new Integer(0xFFFF00));
									colCount[column-1]++;
									if(k == 2) supCount++;
									if(k == 3) subCount++;
									if(k == 4) peerCount++;
									if(k == 5) selfCount++;	
								}
							}
							column++;
						}
					}
				
					//Insert Numeric value for column, Chun Yeong 1 Aug 2011
					NoBelowPar = "";
					if(supCount != 0) NoBelowPar += supCount + " SUP ";
					if(subCount != 0) NoBelowPar += subCount + " SUB ";
					if(peerCount != 0) NoBelowPar += peerCount + " PEER ";
					if(selfCount != 0) NoBelowPar += selfCount + " SELF ";
					OO.insertString(xSpreadsheet, NoBelowPar, row, column);
					row++;
				}
			}
			OO.setTableBorder(xSpreadsheet, 1, column-1, start_row, row-1, true,true,true,true,true,true);
			OO.setCellAllignment(xSpreadsheet, 1, column-1,start_row, row-1, 1, 3);
			
			//Insert Numeric value for row
			for(int i = 0; i < colCount.length; i++){
				OO.insertNumeric(xSpreadsheet, colCount[i], row, i+1);
			}
		} catch (Exception e) {
			System.err.println("GroupSummaryReport.java - InsertValue - " + e);
		}
	}
	/**
	 * generate Group Summary Report from Group Summary Report Template.xls
	 * @param surveyID
	 * @param fileName
	 */
	public void Report(int surveyID, String fileName) 
	{	
		try {
			String templateName = "Group Summary Report Template.xls"; 
			InitializeExcel(fileName, templateName);
			
			
			InitializeSurvey(surveyID);
			Replacement();
			
			InsertValue();

			Date timestamp = new Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			String temp = dFormat.format(timestamp);
			
			OO.insertHeaderFooter(xDoc, "", "", 
					"Date of printing: " + temp + "\n" + "Copyright � 3-Sixty Profiler� is a product of Pacific Century Consulting Pte Ltd.");			
			
			
		}catch (SQLException SE) {
			System.out.println("a " + SE.getMessage());
		}catch (Exception E) {
			System.out.println("b " + E.getMessage());
			E.printStackTrace();
		} finally {      
				
			try {
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
				OO.closeDoc(xDoc);	
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (IOException IO) {
				System.err.println(IO);
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
			}
   		}
	}
	
	/**
	 * generate Group Summary Report from Group Summary Report Template.xls
	 * @param surveyID
	 * @param fileName
	 */
	public void Report(int surveyID, int divID, Vector<Integer> deptIDList, Vector<Integer> groupIDList, String fileName) 
	{	
		try {
			String templateName = "Group Summary Report Template.xls"; 
			InitializeExcel(fileName, templateName);
			
			
			InitializeSurvey(surveyID, divID, deptIDList, groupIDList);
			Replacement();
			
			InsertValue();

			Date timestamp = new Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			String temp = dFormat.format(timestamp);
			
			OO.insertHeaderFooter(xDoc, "", "", 
					"Date of printing: " + temp + "\n" + "Copyright � 3-Sixty Profiler� is a product of Pacific Century Consulting Pte Ltd.");			
			
			
		}catch (SQLException SE) {
			System.out.println("a " + SE.getMessage());
		}catch (Exception E) {
			System.out.println("b " + E.getMessage());
			E.printStackTrace();
		} finally {      
				
			try {
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
				OO.closeDoc(xDoc);	
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (IOException IO) {
				System.err.println(IO);
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
			}
   		}
	}
	
	/**
	 * Get the maximum scale, which is to be used in the alignment process.
	 * @author Chun Yeong
	 * @since v1.3.12.113 //1 Aug 2011
	 */
	public int MaxScale() throws SQLException 
	{	
		int total = 0;
		
		String query = "SELECT MAX(tblScale.ScaleRange) AS Result FROM ";
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
		
			if(rs.next())
				total = rs.getInt(1);

  		}catch(Exception ex){
			System.out.println("GroupSummaryReport.java - MaxScale - "+ex.getMessage());
		}
		finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return total;
	}

	public static void main(String[] args) throws SQLException, IOException{
		GroupSummaryReport test = new GroupSummaryReport();
		test.InitializeSurvey(499);

	}

}
