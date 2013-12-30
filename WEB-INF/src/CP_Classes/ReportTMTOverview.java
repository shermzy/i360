package CP_Classes;

import java.io.*;
import java.sql.*;
//import java.util.*;
//import java.math.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblSurvey;

import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.table.XTableChart;


/**
 * This class implements all the operations for Individual Report in Excel.
 * It implements OpenOffice API.
 */  
	public class ReportTMTOverview { 	
	private Database db;
	private OpenOffice OO;
	private Calculation C;
	private GlobalFunc G;
	private JobPosition JobPos;
	
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	

	/**
	 * Declaration of new object of class EventViewer.
	 */
//	private String savedFileName;
//	private String defaultPath;
//	private String output;
	private String jobLevel;
	
	//The 12 Competencies must be ordered this way
	private String strComp [] = {"Kaizen", "Problem Solving", "Global Perspective", "Innovative Thinking",
								"Policy Management", "Strategic Leadership", "Achievement Oriented", "Customer Focus",
								"Cost and Quality", "People Development", "Mutual Trust", "Integrity"};
	
	private final int BGCOLOR = 12632256;
	private final int ROWHEIGHT = 560;
	
	private XMultiComponentFactory xRemoteServiceManager = null;
	private XComponent xDoc = null;
	private XSpreadsheet xSpreadsheet = null;
	private XSpreadsheet xSpreadsheet2 = null;
	private String storeURL;
	
	private int row;
	private int column;
	
	private Vector vDiv;
	private Vector vDept;
	private double avgCPRAll;	//Avg CPR of TMT Management (Overall)
	private Vector vDivResult;
	private Vector vPosResult;
	private Vector vPosResultDetail; //Position's Result (SurvID, CompID, Survey's CP score)
	private Vector vDivResultDetail; //Division's Result (DivID, CompID, Div's CP score)
	private Vector vClasScore; 		//Classified Scores (Div, CompID, IsStrength, rank)
	private Vector vClasScorePos; 	//Classified Scores For Position(SurveyID, CompID, IsStrength, rank)
	
	
	/**
	 * Creates a new intance of IndividualReport object.
	 */
	public ReportTMTOverview() {
		System.out.println("Overview Report Constructor");
		ST 	= new Setting();
		db 	= new Database();
		OO	= new OpenOffice();
		C	= new Calculation();
		G	= new GlobalFunc();
		JobPos	= new JobPosition();
		jobLevel = "";
		
		vDiv 		= new Vector();
		vDept 		= new Vector();
		vDivResult 	= new Vector();
		vDivResultDetail 	= new Vector();
		vPosResultDetail 	= new Vector();
		vPosResult 	= new Vector();
		vClasScore	= new Vector();
		vClasScorePos = new Vector();
			
	}
	
	/***************************************** SPREADSHEET ********************************************************/
	
	/**
	 * Initialize all the processes dealing with Excel Application.
	 */
	public void InitializeExcel(String savedFileName) throws SQLException, IOException, Exception {
		System.out.println("Clearing all existing public variables value");
		//Clear all existing public variables value
		vDiv.removeAllElements();
		vDept.removeAllElements();
		vDivResult.removeAllElements();
		vDivResultDetail.removeAllElements();
		vPosResultDetail.removeAllElements();
		vPosResult.removeAllElements();
		vClasScore.removeAllElements();
		vClasScorePos.removeAllElements();
		jobLevel = "";
		//end Clear all existing public variables value
		
		storeURL 	= "file:///" + ST.getOOReportPath() + savedFileName;
		String templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "TMT Overview Report Template.xls";
		
		xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=8100;urp;StarOffice.ServiceManager");
		xDoc = OO.openDoc(xRemoteServiceManager, templateURL);
		//System.out.println("Loaded successfully");
		
		//save as the template into a new file first. This is to avoid the template being used.		
		OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);		
		OO.closeDoc(xDoc);
		
		//open up the saved file and modify from there
		xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
		
		xSpreadsheet = OO.getSheet(xDoc, "Sheet1");
		xSpreadsheet2 = OO.getSheet(xDoc, "Sheet2");
		
		System.out.println("Excel Initialisation Completed");
	}
	
	/**
	 * Retrieves the competency id based on competency name.
	 */
	public int getCompetencyID(String strComp, int surveyID []) throws  SQLException, IOException, Exception
	{
		int iCompID = 0;
		
		String query = "SELECT DISTINCT Competency.PKCompetency FROM Competency INNER JOIN ";
		query += "tblSurveyCompetency ON Competency.PKCompetency = tblSurveyCompetency.CompetencyID AND ";
		query += "Competency.PKCompetency = tblSurveyCompetency.CompetencyID ";
		query += "WHERE tblSurveyCompetency.SurveyID IN (";
		
		for(int i=0; i<surveyID.length; i++) {
			if(i != 0)
				query += ",";
			
			query += surveyID[i];
		}
		
		
		query += ") AND Competency.CompetencyName LIKE '%" + strComp + "%'";
		
		//if(db.con == null)
			//db.openDB();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
            while(rs.next())
            {
            	iCompID = rs.getInt(1);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getCompetencyID - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }

        return iCompID;
	}	
	
	/**
	 * Get all the Competency ID for TMT Competencies.
	 * The order of the competencies are hardcoded.
	 */
	public Vector getCompetencies(int surveyID [], String strComp []) throws SQLException, IOException, Exception {
		
		Vector vCompID = new Vector();
		
		//Now look for the ID for each competency in this order
		for(int i=0; i<strComp.length; i++)
			vCompID.add(Integer.toString(getCompetencyID(strComp[i], surveyID)));
				
			
		return vCompID;
	}
	
	/********************************* OVERVIEW RESULT *********************************************************/
	
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
	 * This is a little bit messy, wrong design previously.
	 *
	 * @param int SurveyID []	store all the survey ID that include in the scoring process
	 * @param Vector vCompID	All the competency ID required for TMT, default there are 12 fixed competencies in fixed order
	 * @param String rtCode		to determine which rating task score, rtCode can be either "CP", "CPR", "FPR"
	 * @param Vector vResult	the empty vector passed in, to be returned with the score
	 *
	 */
	public void getCompAvg(int surveyID [], Vector vCompID, String rtCode, Vector vResult) throws SQLException, IOException, Exception
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID[0]);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID[0]); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		
		Vector vCompCP 	= new Vector();
		Vector vScore 	= new Vector();
		
		if(surveyLevel == 1) {	//KB Level
			
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = '" + rtCode + "') ";
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
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND tblRatingTask.RatingCode = 'CP' ";
			
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			query += "GROUP BY " + tableName + ".CompetencyID";
		}
		
		//if(db.con == null)
			//db.openDB();
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
        	while(rs.next()) {			
    			String fkComp 	= rs.getString("CompetencyID");
    			String score	= rs.getString("AvgMean");
    			
    			vCompCP.add(fkComp);
    			vScore.add(score);
    		}
    		
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getCompAvg - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		
		//copy all the score into the correct order (TMT Order of Competency)
		for(int i=0; i<vCompID.size(); i++) {
			
			String score = "0";
			
			int element = vCompCP.indexOf(vCompID.elementAt(i));
			
			if(element != -1)
				score = (String)vScore.elementAt(element);	
				
			vResult.add(score);
				
		}

	}	
	
	/** (To be used only for section 7 "highestAndLowestGrouping")
	 * Get the competency avg mean for CP .
	 * The table to store Competency Level and KB Level Result are not the same.
	 *
	 * For Competency Level:
	 *		ReliabilityIndex is stored in tblAvgMean
	 *		TrimmedMean is stored in tblTrimmedMean
	 *
	 * For KBLevel, both are stored in tblAvgMean
	 *
	 * This is a little bit messy, wrong design previously.
	 *
	 * @param int SurveyID []	store all the survey ID that include in the scoring process
	 * @param Vector vCompID	All the competency ID required for TMT, default there are 12 fixed competencies in fixed order
	 * @param String rtCode		to determine which rating task score, rtCode can be either "CP", "CPR", "FPR"
	 * @param Vector vResult	the empty vector passed in, to be returned with the score
	 *
	 */
	public void getCompAvg(int surveyID, Vector vCompID) throws SQLException, IOException, Exception
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		if(surveyLevel == 1) {	//KB Level
			
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "WHERE tblAvgMean.SurveyID = '" + surveyID + "'";
			query += " AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = 'CP') ";
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ")";
			query += " GROUP BY tblAvgMean.CompetencyID ORDER BY AvgMean";
			
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
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID = '" + surveyID + "'";
			query += " AND tblRatingTask.RatingCode = 'CP' ";
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			query += "GROUP BY " + tableName + ".CompetencyID ORDER BY AvgMean";
		}
		
		//if(db.con == null)
			//db.openDB();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
        	while(rs.next()) {	
    			String fkComp 	= rs.getString("CompetencyID");
    			String score	= rs.getString("AvgMean");
    			//System.out.println("vPosResultDetail sSurvID = " + surveyID + ", fkComp = " + fkComp + ", Score = " + score);
    			vPosResultDetail.add(new String[] {Integer.toString(surveyID), fkComp, score});
    		}
    		
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getCompAvg - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
		
		
		/*
		//copy all the score into the correct order (TMT Order of Competency)
		for(int i=0; i<vCompID.size(); i++) {
			
			String score = "0";
			
			int element = vCompCP.indexOf(vCompID.elementAt(i));
			
			if(element != -1)
				score = (String)vScore.elementAt(element);	
				
			vResult.add(score);
		}
		*/

	}
	
	public void overviewResult(int surveyID [], Vector vCompID, String [] strComp) throws SQLException, IOException, Exception {
		
		double CP = 0, CPR = 0, gap = 0, totalCP = 0, totalCPR = 0;
//		double minScore = 0;	//look for min score of the competency score to be put in competency range
//		double maxScore = 0;	//look for max score of the competency score to be put in competency range
		
		int [] address = OO.findString(xSpreadsheet, "<1>");
		
		column = address[0];
		row = address[1];
		
		int startRow = row;
		
		
		/**
		 * to store all the gap for each competency.
		 * this is required to do sorting, it will be easier this way.
		 */
//		Vector vResult = new Vector();
		
		
		/**
		 * Write the legend for the chart first.
		 * CPR must be in the column before CP, in order to display the exact chart from TMT.
		 */		
		row++;
		
		Vector vResultCP 	= new Vector();	//store the compID and CP score
		Vector vResultCPR 	= new Vector();	//store the compID and CPR score
		Vector vUnsorted 	= new Vector();	//store the competency ID and the gap (unsorted)
				
		getCompAvg(surveyID, vCompID, "CP", vResultCP);
		getCompAvg(surveyID, vCompID, "CPR", vResultCPR);
		
		
		double tempMaxScore = Double.valueOf((String)vResultCP.elementAt(0)).doubleValue();
		double tempMinScore = Double.valueOf((String)vResultCP.elementAt(0)).doubleValue();
		
		/**
		 * Fill in Data Range
		 */
		for(int i=0; i<vCompID.size(); i++) {
			
			CP 	= 0;
			CPR = 0;
			gap = 0;
			
			CP 	= Double.valueOf((String)vResultCP.elementAt(i)).doubleValue();
			CPR = Double.valueOf((String)vResultCPR.elementAt(i)).doubleValue();
			gap = CP - CPR;
			
			if(tempMaxScore < CP)
				tempMaxScore = CP;
			if(tempMinScore > CP)
				tempMinScore = CP;
			
			totalCP 	+= CP;
			totalCPR 	+= CPR;
			
			if(CP!=0)
				CP = Math.round(CP * 100.0) / 100.0;
			if(CPR != 0)
				CPR = Math.round(CPR * 100.0) / 100.0;	
			if(gap != 0)
				gap = Math.round(gap * 100.0) / 100.0;
									 					
			vUnsorted.add(new String [] {strComp[i], Double.toString(gap)});
			
			//write to Sheet 2
			OO.insertString(xSpreadsheet2, strComp[i], row, 0);
			OO.insertNumeric(xSpreadsheet2, CPR, row, 1);
			OO.insertNumeric(xSpreadsheet2, CP, row, 2);
			
			
			row++;
		}
		
		/**
		 * Draw Chart and Set Chart Properties
		 */
/*		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, 0, 2, chartStart, row-1, "Overview", 22000, 10000, chartStart, 0);
		xtablechart = OO.setChartTitle(xtablechart, "Competency");
		xtablechart = OO.setAxes(xtablechart, "Competencies", "Results", 10, 1, false);
		OO.setChartProperties(xtablechart, false, true, true, true, true);
*/		
		//Do Sorting Here, 1 = sort DESC
		Vector vSorted = G.sorting(vUnsorted, 1);
		double avgCPR 	= totalCPR / (double)vCompID.size();
		double avgCP	= totalCP / (double)vCompID.size();
		double avgGap	= avgCP - avgCPR;
		
		if(avgCPR != 0)
			avgCPR = Math.round(avgCPR * 100.0) / 100.0;
		
		if(avgCP != 0)
			avgCP = Math.round(avgCP * 100.0) / 100.0;
			
		if(avgGap != 0)
			avgGap = Math.round(avgGap * 100.0) / 100.0;
		
		avgCPRAll	= avgCPR;
		
		row = startRow;	
		OO.insertString(xSpreadsheet, "Avg CPR = " + Double.toString(avgCPR), row++, 10);
		OO.insertString(xSpreadsheet, "Avg CP = " + Double.toString(avgCP), row++, 10);
		OO.insertString(xSpreadsheet, "Gap = " + Double.toString(avgGap), row++, 10);
		row++;
		
		OO.insertString(xSpreadsheet, "Top 3 Highest Score", row, 10);
		OO.insertString(xSpreadsheet, "Gap", row++,13);
		/**
		 * Get top 3 highest gap score
		 */
		for(int i=0; i<3; i++) {
			OO.insertString(xSpreadsheet, Integer.toString(i+1) + ". " + ((String [])vSorted.elementAt(i))[0], row, 10);
			OO.insertString(xSpreadsheet, ((String [])vSorted.elementAt(i))[1], row++, 13);
		}
		
		row++;
		OO.insertString(xSpreadsheet, "Top 3 Lowest Score", row, 10);
		OO.insertString(xSpreadsheet, "Gap", row++, 13);
		
		/**
		 * Get top 3 lowest gap score
		 */
		for(int i=0; i<3; i++) {
			OO.insertString(xSpreadsheet, Integer.toString(i+1) + ". " + ((String [])vSorted.elementAt(vSorted.size() - i - 1))[0], row, 10);
			OO.insertString(xSpreadsheet, ((String [])vSorted.elementAt(vSorted.size() - i - 1))[1], row++, 13);
		}
		
		row = startRow + 24;
		OO.insertString(xSpreadsheet, "Competency Range " + Double.toString(tempMinScore) + " - " + Double.toString(tempMaxScore), row++, 0);
		row++;
		
//		OO.insertString(xSpreadsheet, "Avg. CPR = Avg. CPR of all TMT Management (" + jobLevel + ") in all divisions", row++, 0);
//		OO.insertString(xSpreadsheet, "Avg. CP = Avg. CP of all TMT Management (" + jobLevel + ") in all divisions", row++, 0);
	}
	
	
	/********************************* AVERAGE RESULT BY POSITION *************************************************/
	
	/**
	 * Retrieve the position for all the survey name.
	 */
	public String [] getPositionName(int surveyID []) throws SQLException, IOException, Exception  {

		String query = "SELECT DISTINCT tblJobPosition.JobPosition, tblJobPosition.JobLevelName FROM tblSurvey INNER JOIN ";
		query += "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query += "WHERE tblSurvey.SurveyID IN (";
		
		for(int i=0; i<surveyID.length; i++) {
			if(i != 0)
				query += ",";
			
			query += surveyID[i];
		}
		
		query += ") ";
		query += " ORDER BY tblJobPosition.JobLevelName"; //JobPosition";

	
		
		String [] strPos = new String [surveyID.length];
		
		int i=0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	while(rs != null && rs.next())
        		strPos[i++] = rs.getString(1);
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getPositionName - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
			
		return strPos;	
	}
	
	public void avgByPosition(int surveyID [], Vector vCompID, String strPos []) throws SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<2>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		OO.findAndReplace(xSpreadsheet, "<2>", "");
		
		Vector vResultCP 	= new Vector();
		Vector vResultCPR 	= new Vector();
		
		OO.insertString(xSpreadsheet2, "CP", startChart, column+1);
		OO.insertString(xSpreadsheet2, "CPR", startChart, column+2);
		startChart++;
		 
		/**
		 * Temporary array just to store one survey ID at one time.
		 * Because this chart requires each avg CP/CPR score for each position/survey, we need to do one survey at one time.
		 */
		int [] tempSurvey = new int [1];
		double tempMaxScore = 0;
		double tempMinScore = 0;
		
		//for(int i=0; i<surveyID.length; i++)
		for(int i=surveyID.length-1; i>=0; i--)
		{	
			tempSurvey[0] = surveyID[i];
			getCompAvg(tempSurvey, vCompID, "CP", vResultCP);
			getCompAvg(tempSurvey, vCompID, "CPR", vResultCPR);
			
			/**
			 * Travel through vResultCP and vResultCPR to get the avg score from all competencies
			 * It has to be done this way, cannot do a group by just under 1 surveyID, the result will be wrong.
			 */
			 double totalCP=0, totalCPR = 0;
			 for(int j=0; j<vResultCP.size(); j++) {
			 	if(tempMaxScore < Double.valueOf((String)vResultCP.elementAt(j)).doubleValue())
			 		tempMaxScore = Double.valueOf((String)vResultCP.elementAt(j)).doubleValue();
			 	
			 	if(tempMinScore > Double.valueOf((String)vResultCP.elementAt(j)).doubleValue())
			 		tempMinScore = Double.valueOf((String)vResultCP.elementAt(j)).doubleValue();
			 			
			 	totalCP += Double.valueOf((String)vResultCP.elementAt(j)).doubleValue();
			 }
			 
			 for(int j=0; j<vResultCPR.size(); j++)
			 	totalCPR += Double.valueOf((String)vResultCPR.elementAt(j)).doubleValue();
			 	
			 double avgCP 	= totalCP / vResultCP.size();
			 double avgCPR 	= totalCPR / vResultCPR.size();
			 
			 if(avgCP != 0)
			 	avgCP = Math.round(avgCP * 100.0) / 100.0;
			 	
			 if(avgCPR != 0)
			 	avgCPR = Math.round(avgCPR * 100.0) / 100.0;
			
			vPosResult.add(new String [] {Double.toString(avgCP), Double.toString(avgCPR)});
			
			 //write to Sheet2, preparing data range for chart
			 OO.insertString(xSpreadsheet2, strPos[i], startChart, column);
			 OO.insertNumeric(xSpreadsheet2, avgCP, startChart, column+1);
			 OO.insertNumeric(xSpreadsheet2, avgCPR, startChart, column+2);
			 startChart++;			 
		}
		
		/**
		 * Draw Chart and Set Chart Properties
		 */
		/* 
		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+2, row, startChart-1, "Position", 30000, 10000, row, column);
		xtablechart = OO.setChartTitle(xtablechart, "");
		xtablechart = OO.setAxes(xtablechart, "Positions", "Results", 10, 1, 0, 0);		
		OO.setChartProperties(xtablechart, false, true, true, true, true);
		*/
		
		OO.setSourceData(xSpreadsheet, xSpreadsheet2, 1, column, column+2, row, startChart-1);
		
		row = startChart + 21;
		//OO.setFontSize(16); 
		//OO.setFontBold(xSpreadsheet, column, column, row+1, row+1);
		//OO.setFontSize(xSpreadsheet, column, column, row+1, row+1, 16);
		//OO.insertString(xSpreadsheet, "Competency Range " + Double.toString(tempMinScore) + " - " + Double.toString(tempMaxScore), row++, column);
		OO.insertString(xSpreadsheet, "Competency Range " + Double.toString(tempMinScore) + " - " + Double.toString(tempMaxScore), 39, column);
		row++;
		
		//OO.insertString(xSpreadsheet, "Avg. CPR = Avg. CPR of each position in all divisions classified by position", row++, column);
		//OO.insertString(xSpreadsheet, "Avg. CP = Avg. CP of each position in all divisions classified by position", row++, column);
		
	}
	

	/********************************* AVERAGE RESULT BY DIVISION *************************************************/
	
	/**
	 * Retrieve all the division for targets assigned under the selected survey.
	 */
	public Vector getDivision(int surveyID []) throws SQLException, IOException, Exception  {

		/*
		String query = "SELECT DISTINCT Division.PKDivision, Division.DivisionName, Division.DivisionCode FROM tblAssignment INNER JOIN ";
		query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN Division ON ";
		query += "[User].FKDivision = Division.PKDivision ";
		query += "WHERE tblAssignment.SurveyID IN (";
		*/
		String query = "SELECT DISTINCT Division.PKDivision, Division.DivisionName, Division.DivisionCode FROM tblAssignment INNER JOIN ";
		query += "Division ON tblAssignment.FKTargetDivision = Division.PKDivision ";
		query += "WHERE tblAssignment.SurveyID IN (";
		
		for(int i=0; i<surveyID.length; i++) {
			if(i != 0)
				query += ",";
			
			query += surveyID[i];
		}
		
		query += ") ";
		query += " ORDER BY Division.DivisionName, Division.DivisionCode";

	
		String pkDiv 	= "";
		String name		= "";
		String divCode  = "";
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
        	while(rs.next()) {
    			pkDiv 	= rs.getString("PKDivision");
    			name		= rs.getString("DivisionName").trim();
    			if (rs.getString("DivisionCode") != null)
    				divCode = rs.getString("DivisionCode").trim();
    			else
    				divCode = "";
    				
    			vDiv.add(new String [] {pkDiv, name, divCode});
    		}
    		
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getDivision - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		return vDiv;
	}
	
	
	/**
	 * Get the competency avg mean for CP and CPR by Division.
	 * The table to store Competency Level and KB Level Result are not the same.
	 *
	 * For Competency Level:
	 *		ReliabilityIndex is stored in tblAvgMean
	 *		TrimmedMean is stored in tblTrimmedMean
	 *
	 * For KBLevel, both are stored in tblAvgMean
	 *
	 * This is a little bit messy, wrong design previously.
	 *
	 * @param int SurveyID []	store all the survey ID that include in the scoring process
	 * @param Vector vCompID	All the competency ID required for TMT, default there are 12 fixed competencies in fixed order
	 * @param String rtCode		to determine which rating task score, rtCode can be either "CP", "CPR", "FPR"
	 * @param Vector vResult	the empty vector passed in, to be returned with the score
	 *
	 */
	public double getCompAvgByDivision(int surveyID [], Vector vCompID, String rtCode, int FKDiv) throws SQLException, IOException, SQLException
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID[0]);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID[0]); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		if(surveyLevel == 1) {	//KB Level
			
			/*
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN [User] ON tblAvgMean.TargetLoginID = [User].PKUser ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			*/
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = '" + rtCode + "') ";
			//query += " and [User].FKDivision = " + FKDiv;	
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;	
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ")";
			//query += " GROUP BY [User].FKDivision, tblAvgMean.CompetencyID ORDER BY AvgMean ";
			query += " GROUP BY tblAssignment.FKTargetDivision, tblAvgMean.CompetencyID ORDER BY AvgMean ";
			
		}else {	// Competency Level
			
			if(reliabilityCheck == 0) {
				tableName = "tblTrimmedMean";
				columnName = "TrimmedMean";
			}
			else {
				tableName = "tblAvgMean";
				columnName = "AvgMean";
			}
				
			/*
			query = "SELECT " + tableName + ".CompetencyID, ROUND(AVG(" + tableName + "." + columnName + "), 2) AS AvgMean ";
			query += "FROM " + tableName + " INNER JOIN tblRatingTask ON ";
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN [User] ON " + tableName + ".TargetLoginID = [User].PKUser ";
			query += " WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			*/	
			query = "SELECT " + tableName + ".CompetencyID, ROUND(AVG(" + tableName + "." + columnName + "), 2) AS AvgMean ";
			query += "FROM " + tableName + " INNER JOIN tblRatingTask ON ";
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON " + tableName + ".TargetLoginID = tblAssignment.TargetLoginID ";
			query += " WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND tblRatingTask.RatingCode = 'CP' ";
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;			
			//query += " and [User].FKDivision = " + FKDiv;			
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			query += "GROUP BY tblAssignment.FKTargetDivision, " + tableName + ".CompetencyID ORDER BY AvgMean ";
			//query += "GROUP BY [User].FKDivision, " + tableName + ".CompetencyID ORDER BY AvgMean ";
		}
		
		
		double score = 0;
		int total = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
		
			while(rs.next()) {			
				score	+= rs.getDouble("AvgMean");
				total++;				
			}
		
		}
	    catch(Exception E) 
	    {
	        System.err.println("ReportTMTOverview.java - getCompAvgByDivision - " + E);
	    }
	    finally
	    {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
	    }
		
		double avgScore = score / (double)total;		
			
		return avgScore;

	}
	
	/** (To be used only for section 7 "highestAndLowestGrouping"
	 * Get the competency avg mean for CP by Division.
	 * The table to store Competency Level and KB Level Result are not the same.
	 *
	 * For Competency Level:
	 *		ReliabilityIndex is stored in tblAvgMean
	 *		TrimmedMean is stored in tblTrimmedMean
	 *
	 * For KBLevel, both are stored in tblAvgMean
	 *
	 * This is a little bit messy, wrong design previously.
	 *
	 * @param int SurveyID []	store all the survey ID that include in the scoring process
	 * @param Vector vCompID	All the competency ID required for TMT, default there are 12 fixed competencies in fixed order
	 * @param Vector vResult	the empty vector passed in, to be returned with the score
	 *
	 */
	public void getCompAvgByDivision(int surveyID [], Vector vCompID, int FKDiv) throws SQLException, IOException, SQLException
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID[0]);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID[0]); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		
		if(surveyLevel == 1) {	//KB Level
			
			/*
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN [User] ON tblAvgMean.TargetLoginID = [User].PKUser ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			*/
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = 'CP') ";
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;	
			//query += " and [User].FKDivision = " + FKDiv;	
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ")";
			query += " GROUP BY tblAssignment.FKTargetDivision, tblAvgMean.CompetencyID ORDER BY AvgMean ";
			//query += " GROUP BY [User].FKDivision, tblAvgMean.CompetencyID ORDER BY AvgMean ";
			
		}else {	// Competency Level
			
			if(reliabilityCheck == 0) {
				tableName = "tblTrimmedMean";
				columnName = "TrimmedMean";
			}
			else {
				tableName = "tblAvgMean";
				columnName = "AvgMean";
			}
				
			/*
			query = "SELECT " + tableName + ".CompetencyID, ROUND(AVG(" + tableName + "." + columnName + "), 2) AS AvgMean ";
			query += "FROM " + tableName + " INNER JOIN tblRatingTask ON ";
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN [User] ON " + tableName + ".TargetLoginID = [User].PKUser ";
			query += " WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			*/	
			query = "SELECT " + tableName + ".CompetencyID, ROUND(AVG(" + tableName + "." + columnName + "), 2) AS AvgMean ";
			query += "FROM " + tableName + " INNER JOIN tblRatingTask ON ";
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON " + tableName + ".TargetLoginID = tblAssignment.TargetLoginID ";
			query += " WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND tblRatingTask.RatingCode = 'CP' ";
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;			
			//query += " and [User].FKDivision = " + FKDiv;			
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			query += "GROUP BY tblAssignment.FKTargetDivision, " + tableName + ".CompetencyID ORDER BY AvgMean ";
			//query += "GROUP BY [User].FKDivision, " + tableName + ".CompetencyID ORDER BY AvgMean ";
		}
		
		double score = 0;
		int total = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
		
			while(rs.next()) {			
				score	+= rs.getDouble("AvgMean");
				
				//System.out.println("FKDiv = " + FKDiv + ", CompID = " + rs.getString("CompetencyID") + ", Score = " + score);
				vDivResultDetail.add(new String[] {Integer.toString(FKDiv), rs.getString("CompetencyID"), Double.toString(score)});
				
				total++;
			}
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getCompAvgByDivision - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
	}
	
	public void avgByDivision(int surveyID [], Vector vCompID) throws SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<3>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		OO.findAndReplace(xSpreadsheet, "<3>", "");
		
		OO.insertString(xSpreadsheet2, "CP", startChart, column+1);
		OO.insertString(xSpreadsheet2, "CPR", startChart, column+2);
		startChart++;
		
		double tempMaxScore = 0;
		double tempMinScore = 0;
		
		for(int i=0; i<vDiv.size(); i++) {
			
			int pkDiv 		= Integer.parseInt(((String [])vDiv.elementAt(i))[0]);
			String divName 	= ((String [])vDiv.elementAt(i))[1];
			
			//get the score
			double avgCP 	= getCompAvgByDivision(surveyID, vCompID, "CP", pkDiv);
			double avgCPR 	= getCompAvgByDivision(surveyID, vCompID, "CPR", pkDiv);
			
			if(avgCP != 0)
				avgCP = Math.round(avgCP * 100.0) / 100.0;
			if(avgCPR != 0)
				avgCPR = Math.round(avgCPR * 100.0) / 100.0;
			
			vDivResult.add(new String [] {Double.toString(avgCP), Double.toString(avgCPR)});

			
			if(tempMaxScore < avgCP)
		 		tempMaxScore = avgCP;
		 	
		 	if(tempMinScore > avgCP)
		 		tempMinScore = avgCP;
			 		
			//write to Sheet2, preparing data range for chart
			 OO.insertString(xSpreadsheet2, divName, startChart, column);
			 OO.insertNumeric(xSpreadsheet2, avgCP, startChart, column+1);
			 OO.insertNumeric(xSpreadsheet2, avgCPR, startChart, column+2);
			 startChart++;
			
		}
		
		/**
		 * Draw Chart and Set Chart Properties
		 */
		/* 
		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+2, row, startChart-1, "Division", 25000, 10000, row, column);
		xtablechart = OO.setChartTitle(xtablechart, "");
		xtablechart = OO.setAxes(xtablechart, "Divisions", "Results", 10, 1, 0, 0);
		OO.setChartProperties(xtablechart, false, true, true, true, true);
		*/
		
		OO.setSourceData(xSpreadsheet, xSpreadsheet2, 2, column, column+2, row, startChart-1);
		
		row = row + 23;
		OO.insertString(xSpreadsheet, "Competency Range " + Double.toString(tempMinScore) + " - " + Double.toString(tempMaxScore), row++, column);
		row++;
		
		//OO.insertString(xSpreadsheet, "Avg. CPR = Avg. CPR of all TMT Management (" + jobLevel + ")", row++, column);
		//OO.insertString(xSpreadsheet, "Avg. CP = Avg. CP of all TMT Management (" + jobLevel + ")", row++, column);
	}
	
	
	
	/********************************* AVERAGE RESULT BY DEPARTMENT ***********************************************/
	
	
	/**
	 * Retrieve all the division for targets assigned under the selected survey.
	 */
	public Vector getDepartment(int surveyID [], int FKDiv) throws SQLException, IOException, Exception  {
		/*
		String query = "SELECT DISTINCT [User].FKDepartment, Department.DepartmentCode FROM ";
		query += "tblAssignment INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
		query += "Department ON [User].FKDepartment = Department.PKDepartment ";
		query += "WHERE [User].FKDivision = " + FKDiv + " and tblAssignment.SurveyID IN (";
		*/
		
		String query = "SELECT DISTINCT Department.PKDepartment, Department.DepartmentCode FROM tblAssignment INNER JOIN ";
      	query += "Department ON tblAssignment.FKTargetDepartment = Department.PKDepartment ";
		query += "WHERE tblAssignment.FKTargetDivision = " + FKDiv + " AND tblAssignment.SurveyID IN (";
		
		for(int i=0; i<surveyID.length; i++) {
			if(i != 0)
				query += ",";
			
			query += surveyID[i];
		}
		
		query += ") ";
		query += " ORDER BY Department.DepartmentCode";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
        	while(rs.next()) {
    			String pkDept 	= rs.getString("PKDepartment");
    			String name		= rs.getString("DepartmentCode");
    			
    			if(name != null)
    				name = name.trim();
    			
    			vDept.add(new String [] {pkDept, name});			
    		}
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getDepartment - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
        
		return vDept;
	}
	
	
	
	/**
	 * Get the competency avg mean for CP and CPR by Division.
	 * The table to store Competency Level and KB Level Result are not the same.
	 *
	 * For Competency Level:
	 *		ReliabilityIndex is stored in tblAvgMean
	 *		TrimmedMean is stored in tblTrimmedMean
	 *
	 * For KBLevel, both are stored in tblAvgMean
	 *
	 * This is a little bit messy, wrong design previously.
	 *
	 * @param int SurveyID []	store all the survey ID that include in the scoring process
	 * @param Vector vCompID	All the competency ID required for TMT, default there are 12 fixed competencies in fixed order
	 * @param String rtCode		to determine which rating task score, rtCode can be either "CP", "CPR", "FPR"
	 * @param Vector vResult	the empty vector passed in, to be returned with the score
	 *
	 */
	public double getCompAvgByDepartment(int surveyID [], Vector vCompID, String rtCode, int FKDiv, int FKDept) throws SQLException, IOException, Exception
	{
		int surveyLevel 		= C.LevelOfSurvey(surveyID[0]);
		int reliabilityCheck 	= C.ReliabilityCheck(surveyID[0]); // 0=trimmed mean
		
		String query 		= "";
		String tableName 	= "";
		String columnName 	= "";
		
		if(surveyLevel == 1) {	//KB Level
			/*
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN [User] ON tblAvgMean.TargetLoginID = [User].PKUser ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			*/
			query = "SELECT tblAvgMean.CompetencyID, ROUND(AVG(tblAvgMean.AvgMean), 2) AS AvgMean ";
			query += "FROM tblAvgMean INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON tblAvgMean.TargetLoginID = tblAssignment.TargetLoginID ";
			query += "WHERE tblAvgMean.SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND (tblAvgMean.Type = 1) AND (tblRatingTask.RatingCode = '" + rtCode + "') ";
			//query += " and [User].FKDivision = " + FKDiv;
			//query += " AND [User].FKDepartment = " + FKDept;	
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;
			query += " AND tblAssignment.FKTargetDepartment = " + FKDept;
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ")";
			//query += " GROUP BY [User].FKDivision, [User].FKDepartment, tblAvgMean.CompetencyID";
			query += " GROUP BY tblAssignment.FKTargetDivision, tblAssignment.FKTargetDepartment, tblAvgMean.CompetencyID";
			
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
			query += tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query += "INNER JOIN tblAssignment ON " + tableName + ".TargetLoginID = tblAssignment.TargetLoginID ";
			query += " WHERE ";
			query += tableName + ".Type = 1 AND " + tableName + ".SurveyID IN (";
			
			for(int i=0; i<surveyID.length; i++) {
				if(i != 0)
					query += ",";
				
				query += surveyID[i];
			}
			
			query += ") AND tblRatingTask.RatingCode = 'CP' ";
			//query += " and [User].FKDivision = " + FKDiv;
			//query += " AND [User].FKDepartment = " + FKDept;	
			query += " and tblAssignment.FKTargetDivision = " + FKDiv;
			query += " AND tblAssignment.FKTargetDepartment = " + FKDept;
			query += " and CompetencyID IN (";
			
			for(int i=0; i<vCompID.size(); i++) {
				if(i != 0)
					query += ",";
				
				query += vCompID.elementAt(i);
			}
			
			query += ") ";
			
			//query += "GROUP BY [User].FKDivision, [User].FKDepartment, " + tableName + ".CompetencyID";
			query += "GROUP BY tblAssignment.FKTargetDivision, tblAssignment.FKTargetDepartment, " + tableName + ".CompetencyID";
		}
		
		double score = 0;
		int total = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
        	while(rs.next()) {			
	   			score	+= rs.getDouble("AvgMean");
	   			total++;				
        	}
   		
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getCompAvgByDepartment - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
        
		double avgScore = score / (double)total;
			
		return avgScore;
	}
	
	
	public void avgByDepartment(int surveyID [], Vector vCompID) throws SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<4>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		Vector vUnsorted = new Vector();
		
		OO.findAndReplace(xSpreadsheet, "<4>", "");
		
		OO.insertString(xSpreadsheet2, "Department", startChart, column);
		//OO.insertString(xSpreadsheet2, "Result", startChart, column+1);
		startChart++;

		//since we still need the division here, we need to make the variable global, and the avgscore as well.		
		
		/**
		 * Keep track one row can have 4 charts
		 * After 4 charts, move to the next row, which is about 23 rows from the previous.
		 *
		 */
		int totalChart = 0;
		 		
		for(int i=0; i<vDiv.size(); i++) {					
			
			startChart = row+1;
			
			int pkDiv 		= Integer.parseInt(((String [])vDiv.elementAt(i))[0]);
			String divName 	= ((String [])vDiv.elementAt(i))[1];
			String divCode 	= ((String [])vDiv.elementAt(i))[2];
			
			if (divCode.equals(""))
					divCode = "";
					
			vDept.removeAllElements();
			vDept = getDepartment(surveyID, pkDiv);
			vUnsorted.removeAllElements();
			
			//loop through each department to get the avgCP and avgCPR for each department
			for(int j=0; j<vDept.size(); j++) {
				
				int pkDept 		= Integer.parseInt(((String [])vDept.elementAt(j))[0]);
				String deptName = ((String [])vDept.elementAt(j))[1];
				
				//get the department CP and CPR
				double avgCP = getCompAvgByDepartment(surveyID, vCompID, "CP", pkDiv, pkDept);
				
				if(avgCP != 0)
					avgCP = Math.round(avgCP * 100.0) / 100.0;
				
				if(deptName == null)
					deptName = "";
					
				//Need to store all the result into vector and do sorting
				vUnsorted.add(new String [] {deptName, Double.toString(avgCP)});
			}
			
			//Do Sorting Here, 1 = sort DESC
			Vector vSorted = G.sorting(vUnsorted, 1);
			
			int startRow = startChart-2;	
			double avgCPDept = 0;
			double totalCP = 0;
			
			//write to sheet 2 and display each chart accordingly
			OO.insertString(xSpreadsheet, "Top 3 Highest", startRow+4, column+7);
			OO.insertString(xSpreadsheet, "CP", startRow+4, column+9);
			
			OO.insertString(xSpreadsheet, "Top 3 Lowest", startRow+9, column+7);
			OO.insertString(xSpreadsheet, "CP", startRow+9, column+9);
			
			int temp1 = startRow + 5;
			int temp2 = startRow + 10;
			
			for(int j=0; j<vSorted.size(); j++) {			
				
				double cp = Double.valueOf(((String [])vSorted.elementAt(j))[1]).doubleValue();
				totalCP += cp;
					
				OO.insertString(xSpreadsheet2, ((String [])vSorted.elementAt(j))[0], startChart, column);			
				OO.insertNumeric(xSpreadsheet2, cp, startChart, column+1);
				
				if(j < 3) {
					OO.insertString(xSpreadsheet, ((String [])vSorted.elementAt(j))[0], temp1, column+7);
					OO.insertNumeric(xSpreadsheet, cp, temp1, column+9);
					temp1++;
				}
				if(j >= vSorted.size()-3) {
					OO.insertString(xSpreadsheet, ((String [])vSorted.elementAt(j))[0], temp2, column+7);
					OO.insertNumeric(xSpreadsheet, cp, temp2++, column+9);
				}
								
				startChart++;
			}
			
			OO.setCellAllignment(xSpreadsheet, column+7, column+9, startRow+5, startRow+temp2, 1, 1);
			
			avgCPDept = totalCP / (double)vSorted.size();
			
			if(avgCPDept != 0)
				avgCPDept = Math.round(avgCPDept * 100.0) / 100.0;
			
			OO.insertString(xSpreadsheet, "Avg.CPR = " + Double.toString(avgCPRAll), startRow+1, column+7);			
			OO.insertString(xSpreadsheet, "Avg CP = " + Double.toString(avgCPDept), startRow+2, column+7);	
			
			OO.setFontSize(xSpreadsheet, column+7, column+9, startRow+1, temp2, 16);
			//insert top 3 highest and lowest

			/**
			 * Draw Chart and Set Chart Properties
			 */
			OO.setFontSize(10); 
			XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+1, row, startChart-1, divName, 15000, 10000, row, column);
			xtablechart = OO.setChartTitle(xtablechart, divName);
			xtablechart = OO.setAxes(xtablechart, "Department", "Results", 10, 1, 0, 0);
			
			OO.setChartProperties(xtablechart, false, true, true, true, false);
			//OO.showLegend(xtablechart, false);
			
			OO.setFontSize(16); 
			
			column += 10;
			
			totalChart++;
			if(totalChart == 4 && i < vDiv.size()-1) {
				
				row += 15;
				OO.insertRows(xSpreadsheet, 0, 40, row, row+16, 16, 1);
				totalChart = 0;
				column = 0;
				row++;
			}
			
		}
		
		column = 0;
	}
	
	
	/****************************** AVERAGE RESULT BY DIVISION AMONG POSITIONS ************************************/
	
	
	public void avgByDivAmgPos(int surveyID [], Vector vCompID, String strPos []) throws SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<5>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		OO.findAndReplace(xSpreadsheet, "<5>", "");
		
		int c = column + 1;
		for(int i=0; i<surveyID.length; i++)
			OO.insertString(xSpreadsheet2, strPos[i], startChart, c++);
		
		int startColumn = 29;
		
		OO.insertString(xSpreadsheet, "Division", startChart, startColumn);
		OO.mergeCells(xSpreadsheet, startColumn, startColumn+7, startChart, startChart);
		OO.insertString(xSpreadsheet, "Avg.CPR", startChart, startColumn+8);
		OO.mergeCells(xSpreadsheet, startColumn+8, startColumn+9, startChart, startChart);
		
		
		OO.insertString(xSpreadsheet, "Avg.CP", startChart, startColumn+10);
		OO.mergeCells(xSpreadsheet, startColumn+10, startColumn+11, startChart, startChart);
		//OO.setCellAllignment(xSpreadsheet, startColumn+8, startColumn+10, startChart, startChart, 1, 3);

		OO.setFontBold(xSpreadsheet, startColumn, startColumn+11, startChart, startChart);
		
		
		startChart++;
		
		//declare this so that the column can be dynamic, just in case TMT change the template
		c = column;
		
		//Loop through each division
		for(int i=0; i<vDiv.size(); i++) {			
						
			c = column;
			
			int pkDiv 		= Integer.parseInt(((String [])vDiv.elementAt(i))[0]);
			String divName 	= ((String [])vDiv.elementAt(i))[1];
			
			OO.insertString(xSpreadsheet2, divName, startChart, c++);
			
			//Every division, look for CPScore for each Survey/Position
			for(int j=0; j<surveyID.length; j++) {

				/**
				 * Declare one array just to store 1 surveyID
				 * So that we can reuse the getCompAvgByDivision function
				 * Try to reuse that function, so that when the calculation changes, there is only 1 function affected.
				 */
				 int [] pkSurvey = new int [1];
				 pkSurvey[0] = surveyID[j];
				 
				//get the CPScore
				double avgCP 	= getCompAvgByDivision(pkSurvey, vCompID, "CP", pkDiv);
								
				if(avgCP != 0)
					avgCP = Math.round(avgCP * 100.0) / 100.0;
				
				//write to sheet 2 and display each chart accordingly				
				OO.insertNumeric(xSpreadsheet2, avgCP, startChart, c++);				
			}
			
			/**
			 * Need to add CPR and AvgCP in order to draw the line
			 * But currently have no time to look through how to do it in OO, so I just wrote it next to the side.
			 * 
			 * This is TEMPORARY until I find out the way to do it.
			 */
			 
			OO.insertString(xSpreadsheet, divName, startChart, startColumn);
			OO.mergeCells(xSpreadsheet, startColumn, startColumn+7, startChart, startChart);
			//OO.setRowHeight(xSpreadsheet, startChart, startColumn, ROWHEIGHT*OO.countTotalRow(divName, 25));
			OO.insertNumeric(xSpreadsheet, Double.valueOf(((String [])vDivResult.elementAt(i))[1]).doubleValue(), startChart, startColumn+8);
			OO.mergeCells(xSpreadsheet, startColumn+8, startColumn+9, startChart, startChart);
			OO.insertNumeric(xSpreadsheet, Double.valueOf(((String [])vDivResult.elementAt(i))[0]).doubleValue(), startChart, startColumn+10);
			OO.mergeCells(xSpreadsheet, startColumn+10, startColumn+11, startChart, startChart);
			
			/**
			 * Add dummy column.
			 * This is required in order to display just 2 lines for CP and CPR
			 * If there are 4 positions, then there must be 2 more dummy columns.
			 * Total dummy+CP+CPR columns must equal to total positions
			 */
			//for(int j=0; j<surveyID.length-2; j++)
				//OO.insertNumeric(xSpreadsheet2, 0, startChart, c++);
			
			startChart++;
			
		}
		
		OO.setTableBorder(xSpreadsheet, startColumn, startColumn+11, row, startChart-1, true, true, true, true, true, true);
		
		/**
		 * Draw Chart and Set Chart Properties
		 */
		/* 
		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+surveyID.length, row, startChart-1, "DivAmgPos", 55000, 10000, row, column);
		xtablechart = OO.setChartTitle(xtablechart, "");
		xtablechart = OO.setAxes(xtablechart, "Division", "CPScale", 10, 1, 0, 0);
		OO.setChartProperties(xtablechart, false, true, true, true, false);
		*/
		
		OO.setSourceData(xSpreadsheet, xSpreadsheet2, 3, column, column+surveyID.length, row, startChart-1);
		XTableChart xtablechart = OO.getChartByIndex(xSpreadsheet, 3);
		OO.setChartProperties(xtablechart, false, true, true, true, false);
		
		row = startChart + 19;
		//OO.insertString(xSpreadsheet, "Avg.CPR = Avg.CPR of each position for all positions", row++, column);
		//OO.insertString(xSpreadsheet, "Avg.CP = Avg.CP of each position for all positions", row, column);
	}
	

	
	
	
	/****************************** AVERAGE RESULT BY POSITION AMONG DIVISIONS ************************************/
	
	
	
	public void avgByPosAmgDiv(int surveyID [], Vector vCompID, String strPos []) throws SQLException, IOException, SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<6>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		OO.findAndReplace(xSpreadsheet, "<6>", "");
		
		int c = column + 1;
		for(int i=0; i<vDiv.size(); i++) 
			OO.insertString(xSpreadsheet2, ((String [])vDiv.elementAt(i))[1], startChart, c++);
		
		int startColumn = 29;
		
		OO.insertString(xSpreadsheet, "Position", startChart, startColumn);
		OO.mergeCells(xSpreadsheet, startColumn, startColumn+7, startChart, startChart);
		OO.insertString(xSpreadsheet, "Avg.CPR", startChart, startColumn+8);
		OO.mergeCells(xSpreadsheet, startColumn+8, startColumn+9, startChart, startChart);
		OO.insertString(xSpreadsheet, "Avg.CP", startChart, startColumn+10);
		OO.mergeCells(xSpreadsheet, startColumn+10, startColumn+11, startChart, startChart);
		//OO.setCellAllignment(xSpreadsheet, startColumn+8, startColumn+10, startChart, startChart, 1, 3);
		
		OO.setFontBold(xSpreadsheet, startColumn, startColumn+11, startChart, startChart);	
		startChart++;
		
		//declare this so that the column can be dynamic, just in case TMT change the template
		c = column;
		
		//Loop through each position/survey
		for(int i=0; i<surveyID.length; i++) {			
			
			c = column;
			OO.insertString(xSpreadsheet2, strPos[i], startChart, c++);
			
			int [] pkSurvey = new int [1];
			pkSurvey[0] = surveyID[i];
			
			//Every survey, look for CPScore for each division
			for(int j=0; j<vDiv.size(); j++) {

				int pkDiv = Integer.parseInt(((String [])vDiv.elementAt(j))[0]);
				
				/**
				 * Declare one array just to store 1 surveyID
				 * So that we can reuse the getCompAvgByDivision function
				 * Try to reuse that function, so that when the calculation changes, there is only 1 function affected.
				 */
				//get the CPScore
				double avgCP 	= getCompAvgByDivision(pkSurvey, vCompID, "CP", pkDiv);
				
				
				if(avgCP != 0)
					avgCP = Math.round(avgCP * 100.0) / 100.0;
								
				//write to sheet 2 and display each chart accordingly
				OO.insertNumeric(xSpreadsheet2, avgCP, startChart, c++);
				
			}
			
			//need to add CPR and AvgCP in order to draw the line
			//OO.insertNumeric(xSpreadsheet2, avgCPRAll, startChart, c++);
			//OO.insertNumeric(xSpreadsheet2, avgCPAll, startChart, c++);
			
			OO.insertString(xSpreadsheet, strPos[i], startChart, startColumn);
			OO.mergeCells(xSpreadsheet, startColumn, startColumn+7, startChart, startChart);
			
			//OO.setRowHeight(xSpreadsheet, startChart, 25, ROWHEIGHT*OO.countTotalRow(strPos[i], 25));
			OO.insertNumeric(xSpreadsheet, Double.valueOf(((String [])vPosResult.elementAt(i))[1]).doubleValue(), startChart, startColumn+8);
			OO.mergeCells(xSpreadsheet, startColumn+8, startColumn+9, startChart, startChart);
			OO.insertNumeric(xSpreadsheet, Double.valueOf(((String [])vPosResult.elementAt(i))[0]).doubleValue(), startChart, startColumn+10);
			OO.mergeCells(xSpreadsheet, startColumn+10, startColumn+11, startChart, startChart);
			
			/**
			 * Add dummy column.
			 * This is required in order to display just 2 lines for CP and CPR
			 * If there are 4 positions, then there must be 2 more dummy columns.
			 * Total dummy+CP+CPR columns must equal to total positions
			 */
			//for(int j=0; j<vDiv.size()-2; j++)
				//OO.insertNumeric(xSpreadsheet2, 0, startChart, c++);
			
			startChart++;
			
		}
		
		OO.setTableBorder(xSpreadsheet, startColumn, startColumn + 11, row, startChart-1, true, true, true, true, true, true);
		
		/**
		 * Draw Chart and Set Chart Properties
		 */
		/* 
		XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+(vDiv.size()), row, startChart-1, "PosAmgDiv", 55000, 10000, row, column);
		//XTableChart xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet2, column, column+3+1, row, startChart-1, "PosAmgDiv", 55000, 10000, row, column);
		xtablechart = OO.setChartTitle(xtablechart, "");
		xtablechart = OO.setAxes(xtablechart, "Position", "CPScale", 10, 1, 0, 0);
		OO.setChartProperties(xtablechart, false, true, true, true, false);
		*/
		
		OO.setSourceData(xSpreadsheet, xSpreadsheet2, 4, column, column+(vDiv.size()), row, startChart-1);
		
		row = startChart + 21;
		//OO.insertString(xSpreadsheet, "Avg.CPR = Avg.CPR of each position for all divisions", row++, column);
		//OO.insertString(xSpreadsheet, "Avg.CP = Avg.CP of each position for all divisions", row, column);
	}
	
	
	
	/************************************ TOP 3 HIGHEST AND LOWEST SCORE *****************************************/
	
	/**
	 *	Get top 3 highest and lowest division score
	 *	@param surveyID	Integer array containing surveyID
	 *	@param vCompID	Vector containing CompetencyID
	 *	@param strPos	String array containing position names
	 */
	public void highestAndLowestGrouping(int surveyID [], Vector vCompID, String strPos []) throws SQLException, IOException, SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<7>");
	
		column = address[0];
		row = address[1];

		int startChart = row;
		
		OO.findAndReplace(xSpreadsheet, "<7>", "");
	
		//declare this so that the column can be dynamic, just in case TMT change the template
//		int c = column;
		
		int iTotDev = 0; 					//To store total number of Competencies under Development zone
		Vector vSurvey = new Vector();
		Vector vCompOutput = new Vector(); 	//To store Competency name to be printed in Strength zone
		Vector vStrength = new Vector();
		Vector vStrengthPos = new Vector();
		Vector vOutput = new Vector(); 		//(DivID, CompID, Definition (S1, S2, ..))
		Vector vOutputPos = new Vector(); 	//(SurveyID, CompID, Definition (S1, S2, ..))
		Vector vDivOutput = new Vector();
		
		int maxRowHeight = 1;

		

		//Loop all divisions and look for CP Score for each division based on all surveys (i.e. surveyID)
		for(int j=0; j<vDiv.size(); j++) 
		{	
			int pkDiv = Integer.parseInt(((String [])vDiv.elementAt(j))[0]);
//			String divName = ((String [])vDiv.elementAt(j))[1];
//			String divCode = ((String [])vDiv.elementAt(j))[2];
			
			//System.out.println("vDivResultDetail.size() = " + vDivResultDetail.size());
			//System.out.println(pkDiv + ". DivCode = " + divCode);
			
			//get the CPScore per division and competency
			vDivResultDetail.clear(); //Clear vector, so that vDivResultDetail only contain results from the current pkDiv
			//getCompAvgByDivision(pkSurvey, vCompID, pkDiv);
			getCompAvgByDivision(surveyID, vCompID, pkDiv);
			
			//Assuming that Total Competency surely >= 6.
			//(what's the reason of the assumption? ans: So that there're enough competencies for S1,S2,S3,D1,D2,D3)
			
			//Classify under Top 3 Strength & Development then store into vClasScore
			if (vDivResultDetail.size() > 0)
			{
				//System.out.println("STORING INTO vClasScore: " + ((String[])vDivResultDetail.elementAt(0))[0] + ", " + ((String[])vDivResultDetail.elementAt(0))[1]);
				//Development
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(0))[0], ((String[])vDivResultDetail.elementAt(0))[1], "0", "1"});
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(1))[0], ((String[])vDivResultDetail.elementAt(1))[1], "0", "2"});
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(2))[0], ((String[])vDivResultDetail.elementAt(2))[1], "0", "3"});
				//Strength
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-1))[0], ((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-1))[1], "1", "1"});
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-2))[0], ((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-2))[1], "1", "2"});
				vClasScore.add(new String[] {((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-3))[0], ((String[])vDivResultDetail.elementAt(vDivResultDetail.size()-3))[1], "1", "3"});
			}
			
			
			//Print all divisions into Excel and store DivisionID into vDivOutput
			OO.insertString(xSpreadsheet, ((String[])vDiv.elementAt(j))[2], startChart, j);
			//Set Wrap Text
			OO.mergeCells(xSpreadsheet, j, j, startChart, startChart);
			//OO.setRowHeight(xSpreadsheet, startChart, j, ROWHEIGHT*OO.countTotalRow(((String[])vDiv.elementAt(j))[1],5));
			vDivOutput.add(((String[])vDiv.elementAt(j))[0]);
			//System.out.println("INSERTED vDivOutput: " + ((String[])vDiv.elementAt(j))[0]);
			
			int temp = OO.countTotalRow(((String[])vDiv.elementAt(j))[1],5);
			if(temp > maxRowHeight)
				maxRowHeight = temp;
		}
		
			
		/*** PRINT HEADER and store all clasified scores into vClasScorePos and vClasScore ***/
		//Loop through each position/survey
		for(int i=0; i<surveyID.length; i++) 
		{		
			int [] pkSurvey = new int [1];
			pkSurvey[0] = surveyID[i];
			
			vSurvey.add(Integer.toString(surveyID[i]));
			
			vPosResultDetail.clear(); //Clear vector, so that vDivResultDetail only contain results from the pkDiv
			//vPosResultDetail.removeAllElements();
			getCompAvg(surveyID[i], vCompID);

			//Assuming that Total Competency surely >= 6
			//(what's the reason of the assumption? ans: So that there're enough competencies for S1,S2,S3,D1,D2,D3)
			
			//Classify under Top 3 Strength & Development then store into vClasScorePos
			if (vPosResultDetail.size() > 0)
			{
				//Development
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(0))[0], ((String[])vPosResultDetail.elementAt(0))[1], "0", "1"});
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(1))[0], ((String[])vPosResultDetail.elementAt(1))[1], "0", "2"});
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(2))[0], ((String[])vPosResultDetail.elementAt(2))[1], "0", "3"});
				
				//Strength
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-1))[0], ((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-1))[1], "1", "1"});
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-2))[0], ((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-2))[1], "1", "2"});
				vClasScorePos.add(new String[] {((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-3))[0], ((String[])vPosResultDetail.elementAt(vPosResultDetail.size()-3))[1], "1", "3"});
			}
			//Print SurveyName into Excel
			OO.insertString(xSpreadsheet, JobPos.getPositionName(surveyID[i]), startChart, vDiv.size() + i + 1 + 3);
			//Set Wrap Text
			OO.mergeCells(xSpreadsheet, vDiv.size() + i + 1 + 3, vDiv.size() + i + 1 + 3, startChart, startChart);
			
			int temp = OO.countTotalRow(JobPos.getPositionName(surveyID[i]),5);
			if(temp > maxRowHeight)
				maxRowHeight = temp;
		}
		
		// If there is no data in the survey skip this whole section (Print nothing on section 7)
		if (vClasScorePos.size() > 0 && vClasScore.size() > 0)
		{	
			OO.insertString(xSpreadsheet, "Competency", startChart, vDiv.size()); //Print "Competency" into Excel
			OO.mergeCells(xSpreadsheet, vDiv.size(), vDiv.size()+3, startChart, startChart);

			//OO.insertString(xSpreadsheet, "Conclusion", startChart, vDiv.size()+2+3); //Print "Conclusion" into Excel
			//OO.mergeCells(xSpreadsheet, vDiv.size()+2+3, vDiv.size()+2+3, startChart, startChart);
			OO.insertString(xSpreadsheet, "Conclusion", startChart, vDiv.size()+4+vSurvey.size()); //Print "Conclusion" into Excel
			OO.mergeCells(xSpreadsheet, vDiv.size()+4+vSurvey.size(), vDiv.size()+4+vSurvey.size(), startChart, startChart);
			
			OO.setRowHeight(xSpreadsheet, startChart, vDiv.size()+2+3, ROWHEIGHT*maxRowHeight);
			
			/*** END PRINT HEADER ***/
			
			/*
			//Loop all Competencies
			String sComp = "";
			for(int iLoop=0; iLoop<vCompID.size(); iLoop++) 
			{
				sComp = sComp + vCompID.get(iLoop);
				if (iLoop < vCompID.size() -1)
					sComp = sComp + ", ";
			}
			System.out.println("Competencies = (" + sComp + ")");
			*/
			
			/** (old) Determine whether "Strength" existed in any of the competency per division and per survey
			 *	If Exist (isStrength = 1), store into vStrength to be printed after Development section
			 *	If Does not Exist (isStrength = 0), print out to excel directly
			*/
			/** Determine whether "Strength" is more than "Development" per division and per survey
			 *	If More Strength (isStrength = 1), store into vStrength to be printed after Development section
			 *	If More Development Area (isStrength = 0), print out to excel directly
			*/
	
			//Loop all Competencies
			for(int iLoop=0; iLoop<vCompID.size(); iLoop++)
			{
				int isStrength = 0; //No of Strengths
				int isDev = 0; //No of Developments
				
				//Loop all Divisions
				for(int iLoop2=0; iLoop2<vDiv.size(); iLoop2++) 
				{
					//System.out.println("Division = " + ((String[])vDiv.elementAt(iLoop2))[1]);
					//Loop all Classified Scores
					for(int iLoop3=0; iLoop3<vClasScore.size(); iLoop3++) 
					{
						if(vClasScore.size() > 0 && ((String[])vClasScore.elementAt(iLoop3))[1].equals(vCompID.get(iLoop)))
						{
							if(((String[])vClasScore.elementAt(iLoop3))[0].equals(((String[])vDiv.elementAt(iLoop2))[0]))
							{	
								String sDef = "D";
									
								if(((String[])vClasScore.elementAt(iLoop3))[2].equals("1")) //Check the record isStrength
								{
									isStrength++;
									sDef = "S";
								}
								else
									isDev++;
									
								sDef = sDef + ((String[])vClasScore.elementAt(iLoop3))[3];
								
								vOutput.add(new String[] {((String[])vDiv.elementAt(iLoop2))[0], strComp[iLoop], sDef});
								//System.out.println(((String[])vDiv.elementAt(iLoop2))[0] + ", " + strComp[iLoop] + ", " + sDef);
							}
						}
					}
					//System.out.println("NEXT DIVISION");
				}
				
				//Loop through each position/survey
				for(int i=0; i<surveyID.length; i++) 
				{
					//Loop all Classified Scores for Position/Survey
					for(int iLoop3=0; iLoop3<vClasScorePos.size(); iLoop3++) 
					{
						if(vClasScorePos.size() > 0 && ((String[])vClasScorePos.elementAt(iLoop3))[1].equals(vCompID.get(iLoop)))
						{
							if(((String[])vClasScorePos.elementAt(iLoop3))[0].equals(Integer.toString(surveyID[i])))
							{	
								String sDef = "D";
									
								if(((String[])vClasScorePos.elementAt(iLoop3))[2].equals("1")) //Check the record isStrength
								{
									isStrength++;
									sDef = "S";
								}
								else
									isDev++;
									
								sDef = sDef + ((String[])vClasScorePos.elementAt(iLoop3))[3];
								
								vOutputPos.add(new String[] {Integer.toString(surveyID[i]), strComp[iLoop], sDef});
								//System.out.println(surveyID[i] + ", " + strComp[iLoop] + ", " + sDef);
							}
						}
					}
					//System.out.println("NEXT POSITION/SURVEY");
				}
				
				//System.out.println("Competency = " + strComp[iLoop] + "\nStrength(+" + isStrength + ")\nDev("+isDev+")");
				
				//Print to Excel Document if Strength was not found (isStrength <> 1)
				//if(isStrength != 1)
				if(isDev > isStrength)
				{
					startChart++;
					iTotDev++;
					
					//System.out.println("Print Development to Excel : " + (String)vCompID.get(iLoop));
					if (vOutput.size() > 0)
					{
						for(int i = 0; i <vOutput.size(); i++)
						{
							//Search the Division code for the Division order columns in Excel
							int iDivCol = vDivOutput.indexOf(((String[])vOutput.elementAt(i))[0]);
							if(iDivCol != -1)
								//System.out.println("Print Output at Div Col("+iDivCol+") : " + (((String[])vOutput.elementAt(i))[0]) + ", " + (((String[])vOutput.elementAt(i))[1]) + ", " +  (((String[])vOutput.elementAt(i))[2]));
								OO.insertString(xSpreadsheet, (((String[])vOutput.elementAt(i))[2]), startChart, iDivCol);
						}
					}
					
					if (vOutputPos.size() > 0)
					{
						for(int i = 0; i <vOutputPos.size(); i++)
						{
							//System.out.println("SurveyID.length = " + surveyID.length);
							//Search the SurveyID to get order columns in Excel
							int iSurvID = vSurvey.indexOf(((String[])vOutputPos.elementAt(i))[0]);
							if(iSurvID != -1)
								//System.out.println("Print Output at Div Col("+iSurvID+") : " + (((String[])vOutputPos.elementAt(i))[0]) + ", " + (((String[])vOutputPos.elementAt(i))[1]) + ", " +  (((String[])vOutputPos.elementAt(i))[2]));
								OO.insertString(xSpreadsheet, (((String[])vOutputPos.elementAt(i))[2]), startChart, vDiv.size() + iSurvID + 1 + 3);
						}
					}
					
					//Print Competencies' name in Excel
					OO.insertString(xSpreadsheet, strComp[iLoop], startChart, vDiv.size());
					//Set Wrap Text
					OO.mergeCells(xSpreadsheet, vDiv.size(), vDiv.size() + 3, startChart, startChart);
					OO.setRowHeight(xSpreadsheet, startChart, vDiv.size(), ROWHEIGHT*OO.countTotalRow(strComp[iLoop],20));
				
				}
				else
				{
					//Store vOutput into vStrength
					if (vOutput.size() > 0)
						for(int i = 0; i <vOutput.size(); i++)
							vStrength.add(new String[] {(((String[])vOutput.elementAt(i))[0]), (((String[])vOutput.elementAt(i))[1]), (((String[])vOutput.elementAt(i))[2])});
				
					//Store vOutputPos into vStrengthPos
					if (vOutputPos.size() > 0)
						for(int i = 0; i <vOutputPos.size(); i++)
							vStrengthPos.add(new String[] {(((String[])vOutputPos.elementAt(i))[0]), (((String[])vOutputPos.elementAt(i))[1]), (((String[])vOutputPos.elementAt(i))[2])});
				
					vCompOutput.add(strComp[iLoop]);
				
				}
				vOutput.clear(); //Clear for next use
				vOutputPos.clear(); //Clear for next use
				
				//System.out.println("NEXT COMPETENCY");	
			}
			
			int curRow = startChart; //To store current row to be used when displaying Survey's Classified Score below
			
			//Print all the remaining Competencies that belong to Strength zone
			for(int i = 0; i<vCompOutput.size(); i++)
			{
				startChart++;
				OO.insertString(xSpreadsheet, (String)vCompOutput.elementAt(i), startChart, vDiv.size());
				OO.mergeCells(xSpreadsheet, vDiv.size(), vDiv.size()+3, startChart, startChart);
				OO.setRowHeight(xSpreadsheet, startChart, vDiv.size(), ROWHEIGHT*OO.countTotalRow((String)vCompOutput.elementAt(i),20));
				
				//System.out.println("Row Height = " + ROWHEIGHT*OO.countTotalRow((String)vCompOutput.elementAt(i),12));
			}
			
			//Print vStrength to Excel Document
			//System.out.println("\nPrint STRENGTH to Excel");
			
			//Print all classified scores by division
			if (vStrength.size() > 0)
			{
				for(int i = 0; i <vStrength.size(); i++)
				{
					//Search the Division code for the Division order columns in Excel
					int iDivCol = vDivOutput.indexOf(((String[])vStrength.elementAt(i))[0]);
					int printRow = curRow + vCompOutput.indexOf(((String[])vStrength.elementAt(i))[1]) + 1;
					
					if(iDivCol != -1)
						//System.out.println("Print Output at Div Col("+iDivCol+") : " + (((String[])vOutput.elementAt(i))[0]) + ", " + (((String[])vOutput.elementAt(i))[1]) + ", " +  (((String[])vOutput.elementAt(i))[2]));
						OO.insertString(xSpreadsheet, (((String[])vStrength.elementAt(i))[2]), printRow, iDivCol);
				}
			}
		
			//Print all classified scores by Position/Survey
			if (vStrengthPos.size() > 0)
			{
				for(int i = 0; i <vStrengthPos.size(); i++)
				{
					//Search the Survey to get order columns in Excel
					int iSurvCol = vSurvey.indexOf(((String[])vStrengthPos.elementAt(i))[0]);
					int printRow = curRow + vCompOutput.indexOf(((String[])vStrengthPos.elementAt(i))[1]) + 1;
					
					if(iSurvCol != -1)
						OO.insertString(xSpreadsheet, (((String[])vStrengthPos.elementAt(i))[2]), printRow, iSurvCol + vDiv.size()+1+3);
				}
			}
			
			//startChart = The last row of the table by this point
			
			//Added by Jenty
			//Colour for Strength Zone
			OO.setBGColor(xSpreadsheet, 0, vSurvey.size() + 4 + vDiv.size(), row+1+iTotDev, startChart, 0x8FFFFF); //curRow+1
			
			/*** ALIGNMENT ADJUSMENT ***/
			OO.setTableBorder(xSpreadsheet, column, column+vDiv.size()+vSurvey.size()+1+3, row, startChart, true, true, true, true, true, true);
			OO.setFontBold(xSpreadsheet, column, column+vDiv.size()+vSurvey.size()+1+3, row, startChart);
			OO.setCellAllignment(xSpreadsheet, column, column+vDiv.size()+vSurvey.size()+1+3, row, startChart, 1, 2);
			OO.setCellAllignment(xSpreadsheet, column, column+vDiv.size()+vSurvey.size()+1+3, row, startChart, 2, 2);
			
			//Merge cell for Development Zone
			OO.mergeCells(xSpreadsheet, column+vDiv.size()+vSurvey.size()+1+3, column+vDiv.size()+vSurvey.size()+1+3, row+1, row+iTotDev);
			OO.insertString(xSpreadsheet, "Dev. Area Zone", row +1, column+vDiv.size()+vSurvey.size()+1+3);
			
			//Merge cell for Strength Zone
			OO.mergeCells(xSpreadsheet, column+vDiv.size()+vSurvey.size()+1+3, column+vDiv.size()+vSurvey.size()+1+3, row+1+iTotDev, startChart);
			OO.insertString(xSpreadsheet, "Strength Zone", row+1+iTotDev, column+vDiv.size()+vSurvey.size()+1+3);
			
			//Set BG Colour
			OO.setBGColor(xSpreadsheet, column, column+vDiv.size()-1, row, row, 0x8080FF); //Header colour for Divisions
			OO.setBGColor(xSpreadsheet, column+vDiv.size(), column+vDiv.size(), row, row, 0x80FFFF); //Header colour for Competency
			OO.setBGColor(xSpreadsheet, column+vDiv.size()+1+3, column+vDiv.size()+vSurvey.size()+3, row, row, 0x8080FF); //Header colour for Surveys
			OO.setBGColor(xSpreadsheet, column+vDiv.size()+vSurvey.size()+1+3,  column+vDiv.size()+vSurvey.size()+1+3, row, row, 0x80FFFF); //Header colour for "Conclusion"
			
			/*** END ALIGNMENT ADJUSMENT ***/
		}	// end if (vPosResultDetail.size() > 0)
	}

	/************************************ COUNT TARGET GROUP *****************************************/
	
	/**
	 * Retrieve total targets assigned under the selected survey group by Division.
	 */
	public int countTarget(int surveyID, int FKDiv) throws SQLException, IOException, Exception  {

		/*
		String query = "SELECT COUNT(DISTINCT tblAssignment.TargetLoginID) AS Total FROM tblAssignment INNER JOIN ";
		query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN Division ON ";
		query += "[User].FKDivision = Division.PKDivision INNER JOIN tblSurvey ON ";
		query += "tblAssignment.SurveyID = tblSurvey.SurveyID WHERE tblAssignment.SurveyID = " + surveyID;
		query += " AND tblAssignment.RaterStatus = 1 AND Division.PKDivision = " + FKDiv;
		*/
		String query = "SELECT COUNT(DISTINCT tblAssignment.TargetLoginID) AS Total FROM tblAssignment INNER JOIN ";
		query += "Division ON tblAssignment.FKTargetDivision = Division.PKDivision INNER JOIN tblSurvey ON ";
		query += "tblAssignment.SurveyID = tblSurvey.SurveyID WHERE tblAssignment.SurveyID = " + surveyID;
		query += " AND tblAssignment.RaterStatus = 1 AND Division.PKDivision = " + FKDiv;


		int iCount = 0;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
        	if(rs.next()) {
    			iCount = rs.getInt(1);
    		}
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - countTarget - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
					
		return iCount;
	}
	
	
	public void displayTargetGroup(int surveyID [], String strPos []) throws SQLException, IOException, SQLException, IOException, Exception {

		int [] address = OO.findString(xSpreadsheet, "<Total>");
	
		column = address[0];
		row = address[1];

		int startTable = row;
		
		OO.findAndReplace(xSpreadsheet, "<Total>", "");
		
		int c = column + 1;
		
		for(int j=0; j<vDiv.size(); j++) {
		
			OO.insertString(xSpreadsheet, ((String[])vDiv.elementAt(j))[2], row, c);
			//OO.mergeCells(xSpreadsheet, c, c, row, row);			
			c++;
		}
		
		OO.setFontBold(xSpreadsheet, column, column+vDiv.size()+1, row, row);
		OO.setBGColor(xSpreadsheet, column, column+vDiv.size()+1, row, row, BGCOLOR);
		
		row++;
		
		int totalPerSurvey = 0;
		int finalTotalPerSurvey = 0;
		int totalPerDiv [] = new int [vDiv.size()];
		
		for(int i=0; i<surveyID.length; i++) 
		{
			c = column;	
			OO.insertString(xSpreadsheet, strPos[i], row, c);	
			OO.mergeCells(xSpreadsheet, c, c, row, row);
			c++;
			
			totalPerSurvey = 0;
			
			for(int j=0; j<vDiv.size(); j++) 
			{
				int FKDiv = Integer.parseInt(((String[])vDiv.elementAt(j))[0]);
				int total = countTarget(surveyID[i], FKDiv);
				
				totalPerSurvey += total;
				totalPerDiv[j] += total;
				
				OO.insertNumeric(xSpreadsheet, total, row, c++);
			}
			
			OO.insertNumeric(xSpreadsheet, totalPerSurvey, row, c++);
			finalTotalPerSurvey = finalTotalPerSurvey + totalPerSurvey;
			row++;
		}

		OO.insertString(xSpreadsheet, "Total", startTable, c-1);
		OO.insertNumeric(xSpreadsheet, finalTotalPerSurvey, row, c-1);
		
		c = column;
		OO.insertString(xSpreadsheet, "Total", row, c++);
		
		for(int i=0; i<vDiv.size(); i++)
			OO.insertNumeric(xSpreadsheet, totalPerDiv[i], row, c++);
				
		OO.setTableBorder(xSpreadsheet, column, column+vDiv.size()+1, startTable, row, true, true, true, true, true, true);
		OO.setCellAllignment(xSpreadsheet, column, column+vDiv.size()+1, startTable, row, 2, 2);
		OO.setCellAllignment(xSpreadsheet, column, column+vDiv.size()+1, startTable+1, row, 1, 2);
	}
	
	
	/********************************************* REPLACE JOB LEVEL *****************************************/
	
	/**
	 * Replace the job level tag.
	 */
	public void replaceJobLevel(int FKSurvey []) throws SQLException, IOException, Exception  {

		Vector rs = getJobLevel(FKSurvey);
		
		int i=0;
		for(int j=0; j<rs.size(); j++) {
			
			String sJobLevelName = (String)rs.elementAt(j);
			if (sJobLevelName != null)
			{
				if(i > 0)
					jobLevel += ";";
				jobLevel += sJobLevelName.trim();
			}
			i++;
		}
		
		//replacement
		OO.findAndReplace(xSpreadsheet, "<job level>", jobLevel);
	}	
	
	
	/********************************* CALL FROM JSP *********************************************************/
	
	/**
	 * Method to call all methods from JSP.
	 */
	public void Report(int [] surveyID, int pkUser, String fileName) {
		
		try {
			
			System.out.println("================ Overview Report Generation Starts ================ ");
			
			System.out.println("Initialise Excel");			
			InitializeExcel(fileName);
			
			/**
			 * Get the position name for each survey
			 */
			String [] strPos =  getPositionName(surveyID);		 
			
			//replace the job level
			replaceJobLevel(surveyID);
		
			//get all the competencies ID to be used for the whole report.
			System.out.println("Get all competencies ID");
			Vector vCompID  = getCompetencies(surveyID, strComp);
			
			//get the division here so that it can be reused
			vDiv = getDivision(surveyID);
			
			System.out.println("Display Target Group of 360 Assessment");	
			displayTargetGroup(surveyID, strPos);
			
			System.out.println("1. Overview Result of 360 Assessment");			
			overviewResult(surveyID, vCompID, strComp);
			
			System.out.println("2. Average Result By Position");			
			avgByPosition(surveyID, vCompID, strPos);
			
			System.out.println("3. Average Result By Division");			
			avgByDivision(surveyID, vCompID);
			
			//must run together with the 1st one, if not the AvgCPR = 0		
			System.out.println("4. Average Result By Department");	
			avgByDepartment(surveyID, vCompID);
						
			/**
			 * Declare 2 vectors
			 * 1 to store SurveyID and DivID
			 * 2 to store the CP Score
			 * 
			 * These 2 vectors are used in point 5 and 6.
			 * The advantage to use these 2 vectors is to reduce processing power and time.
			 *
			 */
			
			//dun have enough time to do properly, leave it for reference first, I'll come back and enhance.
			
 			Vector vPosAndDiv = new Vector();
			Vector vPosAndDivResult = new Vector();
			
			for(int i=0; i<surveyID.length; i++) {
				
				int [] pkSurvey = new int [1];
				pkSurvey[0] = surveyID[i];
			
				for(int j=0; j<vDiv.size(); j++) {
					int pkDiv = Integer.parseInt(((String [])vDiv.elementAt(j))[0]);
					double avgCP 	= getCompAvgByDivision(pkSurvey, vCompID, "CP", pkDiv);
				
				
					if(avgCP != 0)
						avgCP = Math.round(avgCP * 100.0) / 100.0;
						
					vPosAndDiv.add(new String [] {Integer.toString(pkSurvey[0]), Integer.toString(pkDiv)});
					vPosAndDivResult.add(new Double(avgCP));
				}
			}
			
			//In order to run this, must run no. 3 to get the vDiv
			//thought of getting vDiv out here
			System.out.println("5. Average Result By Division Among Positions");
			avgByDivAmgPos(surveyID, vCompID, strPos);
			
			//must run no. 2 first
			System.out.println("6. Average Result By Position Among Divisions");
			avgByPosAmgDiv(surveyID, vCompID, strPos);
			
			System.out.println("7. Highest And Lowest Grouping");
			highestAndLowestGrouping(surveyID, vCompID, strPos);
			
		}catch(SQLException SE){
			System.err.println("ReportTMTOverview.java - Report - SQLException - " + SE);		
		}
		catch(IOException IE){
			System.err.println("ReportTMTOverview.java - Report - IOException - " + IE);
		}
		catch(Exception E){
			System.err.println("ReportTMTOverview.java - Report - Exception - " + E);
		}finally {
			System.out.println("Close Database");
		
			try {	
				System.out.println("Store Document");
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
						
				System.out.println("Close Document");
				//rianto: Take note of this closeDoc
				//Closing it will cause OO to crash when running in JSP mode
				//But, Not closing it will cause too many OO instances unclosed
				OO.closeDoc(xDoc);
				System.out.println("============= Overview Report Generation Completed =============");
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (IOException IO) {
				System.err.println(IO);
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
				}
		}
	}
	
	
	
	/************************************ JSP ************************************************************/
	
	/**
	 * Retrieves the year of survey.
	 */
	public Vector getSurveyYear(int FKOrg) throws  SQLException, IOException, Exception
	{
		
		String query = "SELECT DISTINCT YEAR(DateOpened) AS SurveyYear FROM tblSurvey WHERE ";
		query += "FKOrganization = " + FKOrg;
		query += " ORDER BY SurveyYear DESC";
		
		Vector v = new Vector();
    	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
            while(rs.next())
            {
            	v.add(new Integer(rs.getInt(1)));
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getSurveyYear - " + E);
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
	 * Retrieves all the survey within the specific year.
	 */
	public Vector getSurveyByYear(int year, int FKOrg) throws  SQLException, IOException, Exception
	{
		
		//String query = "SELECT DISTINCT SurveyID, SurveyName FROM tblSurvey WHERE FKOrganization = " + FKOrg;
		//query += " AND (YEAR(DateOpened) = " + year + ") ORDER BY SurveyName";
		
		/*
		String query = "SELECT DISTINCT tblSurvey.SurveyID, tblSurvey.SurveyName, JobLevel.JobLevelName ";
		query += "FROM UserDemographic INNER JOIN JobLevel ON ";
		query += "UserDemographic.FKJobLevel = JobLevel.PKJobLevel INNER JOIN tblSurvey INNER JOIN ";
		query += "tblAssignment ON tblSurvey.SurveyID = tblAssignment.SurveyID ON ";
		query += "UserDemographic.FKUser = tblAssignment.TargetLoginID WHERE ";
		query += "tblSurvey.FKOrganization = " + FKOrg;
		query += " AND (YEAR(tblSurvey.DateOpened) = " + year + ") AND ";
		query += "(JobLevel.JobLevelName <> '-1') ORDER BY tblSurvey.SurveyName";
		*/
		
		String query = "SELECT DISTINCT tblSurvey.SurveyID, tblSurvey.SurveyName, tblJobPosition.JobLevelName ";
		query += "FROM UserDemographic INNER JOIN tblSurvey INNER JOIN tblAssignment ON ";
		query += "tblSurvey.SurveyID = tblAssignment.SurveyID ON ";
		query += "UserDemographic.FKUser = tblAssignment.TargetLoginID INNER JOIN ";
    	query += "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query += "WHERE (tblSurvey.FKOrganization = " + FKOrg + ") AND (YEAR(tblSurvey.DateOpened) = " + year + ") ";
		query += "ORDER BY tblJobPosition.JobLevelName"; //tblSurvey.SurveyName";

		Vector v = new Vector();
    	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
            while(rs.next())
            {
            	
            	votblSurvey vo = new votblSurvey();
            	vo.setSurveyID(rs.getInt(1));
            	vo.setSurveyName(rs.getString(2));
            	vo.setJobLevelName(rs.getString(3));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getSurveyByYear - " + E);
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
	 * Retrieves all the job level based by on the survey.
	 */
	public Vector getJobLevel(int FKSurvey []) throws  SQLException, IOException, Exception
	{
		/*
		String query = "SELECT DISTINCT JobLevel.JobLevelName FROM UserDemographic INNER JOIN ";
		query += "tblAssignment ON UserDemographic.FKUser = tblAssignment.TargetLoginID INNER JOIN ";
		query += "JobLevel ON UserDemographic.FKJobLevel = JobLevel.PKJobLevel WHERE ";
		query += "JobLevel.JobLevelName <> '-1' AND tblAssignment.SurveyID IN (";
		*/
		
		String query = "SELECT DISTINCT tblJobPosition.JobLevelName FROM UserDemographic INNER JOIN ";
		query += "tblAssignment ON UserDemographic.FKUser = tblAssignment.TargetLoginID INNER JOIN ";
		query += "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
		query += "tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query += "WHERE tblAssignment.SurveyID IN (";
		
		for(int i=0; i<FKSurvey.length; i++) {
			
			if(i > 0)
				query += ", ";
			
			query += FKSurvey[i];
		}
		
		query += ") ";
		query += "ORDER BY tblJobPosition.JobLevelName";
		
		Vector v = new Vector();
    	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
            while(rs.next())
            {
            	v.add(rs.getString(1));
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ReportTMTOverview.java - getJobLevel - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }

        
        return v;
	}
	
	
	public static void main (String [] args) {
		
		//int surveyID [] = {432, 433, 434, 435, 436};
		int surveyID [] = {443};
		
		ReportTMTOverview TMT = new ReportTMTOverview();
		
		TMT.Report(surveyID, 1, "TMT Overview Report.xls");
		
		System.exit(1);
	}
	
}