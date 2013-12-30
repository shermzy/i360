package CP_Classes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voAge;
import CP_Classes.vo.voDepartment;
import CP_Classes.vo.voEthnic;
import CP_Classes.vo.voGender;
import CP_Classes.vo.voJobFunction;
import CP_Classes.vo.voJobLevel;
import CP_Classes.vo.voLocation;
import CP_Classes.vo.votblSurveyDemos;
import CP_Classes.vo.votblSurveyRating;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;

/**
 * This class implements all the operations for Questionnaire Report in Excel.
 * It implements both J-Integra and JExcelAPI Interface.
 * J-Integra : Find and Replace.i
 * JExcelAPI : All operations.
 */ 
public class ExcelQuestionnaire { 

	private Setting ST;
	private OpenOffice OO;
	private Questionnaire Q;
	private DemographicEntry DE;
	private SurveyResult S;
	
	private int row;
	private int column;
	private int surveyID;
	private int targetID;
	private int raterID;
	private int orgID;
	private String timeFrame;
	private String surveyInfo [];
	private int scale [];
	private int rating [];
	private int totalColumn;
	private int totalRows;

    private XMultiComponentFactory xRemoteServiceManager = null;
	private XComponent xDoc = null;
    private XSpreadsheet xSpreadsheet = null;
    private String storeURL;
    private final int BGCOLOR = 12632256;
	private final int ROWHEIGHT = 560;
    
	/**
	 * Creates a new intance of ExcelQuestionnaire object.
	 */
	public ExcelQuestionnaire() {
		ST = new Setting();
		Q = new Questionnaire();
		DE = new DemographicEntry();
		S = new SurveyResult();
		OO = new OpenOffice();
		
		surveyInfo = new String [13];
		totalColumn = 41; //Denise 20/12/2009 reduce totalcomlumn to fit A4 page 
		
		totalRows = 51;
	}
	
	/**
	 * Retrieve the future job description based on job position id.
	 */
	public String futureJob(int jobPostID) throws SQLException {
		String job = "";
		
		String query = "Select * from tblJobPosition where JobPositionID = " + jobPostID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next())
	  			job = rs.getString("JobPosition");
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - futureJob - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return job;
	}
	
	/**
	 * Retrieves timeframe description based on timeframe id.
	 */
	public String timeFrame(int timeframeID) throws SQLException {
		String time = "";
		
		String query = "Select * from tblTimeFrame where TimeFrameID = " + timeframeID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next())
				time = rs.getString("TimeFrame");
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - futureJob - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		return time;
	}
	
	/**
	 * Retrieves the survey details and stores it in an array, which will be used in all methods.
	 */
	public String [] SurveyInfo(int iSurveyID) throws SQLException {
		//added in information of assignmentID -> change from 13 to 14
		//Qiao Li 04 Jan 2010
		String [] info = new String[14];

		String query = "SELECT tblSurvey.SurveyName, tblJobPosition.JobPosition, tblSurvey.JobFutureID, tblSurvey.LevelOfSurvey, ";
		query = query + "tblSurvey.DeadlineSubmission, tblSurvey.TimeFrameID, tblSurvey.FKOrganization, ";
		query = query + "tblAssignment.RaterCode, [User].GivenName, [User].FamilyName, tblOrganization.NameSequence, tblSurvey.AnalysisDate, tblOrganization.OrganizationName ";
		//added in information of assignmentID
		//Qiao Li 04 Jan 2010
		query = query + ", tblAssignment.AssignmentID ";
		query = query + "FROM tblSurvey INNER JOIN ";
		query = query + "tblAssignment ON tblSurvey.SurveyID = tblAssignment.SurveyID INNER JOIN ";
		query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		query = query + "INNER JOIN tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
		query = query + "INNER JOIN tblJobPosition ON tblSurvey.JobPositionID = tblJobPosition.JobPositionID ";
		query = query + "WHERE tblSurvey.SurveyID = " + iSurveyID + " AND tblAssignment.RaterLoginID = " + raterID;
		query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
		try {
			con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
			if(rs.next()) {
				
				
				SimpleDateFormat day_view = new SimpleDateFormat ("dd MMMM yyyy");
				
				//added in one more information about assignmentID
				//Qiao Li 04 Jan 2010
				for(int i=0; i<14; i++) {
				
					if(i == 4)
						info[i] = day_view.format(rs.getDate(i+1));
					else
						info[i] = rs.getString(i+1);
					
				}
				
				if(Integer.parseInt(info[2]) != 0)
					info[2] = futureJob(Integer.parseInt(info[2]));
				else
					info[2] = "";
				
				if(Integer.parseInt(info[5]) != 0)
					info[5] = timeFrame(Integer.parseInt(info[5]));
				else
					info[2] = "";
			
			}
		
		}
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - SurveyInfo - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		return info;
	}
	/**
	 * 
	 * @param ratingScaleID
	 * @return
	 * @author Xukun
	 */
	public Vector getPrelimRatingOptions(int ratingScaleID)
	{
		String query = "SELECT RatingScale from tbl_PrelimQnRatingScale where PrelimRatingScaleID =" + ratingScaleID+
				" order by RatingSequence";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();
		try{
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		while(rs.next()){
	  			v.add(rs.getString("RatingScale"));
	  		}
		}catch(Exception e){
            System.err.println("ExcelQuestionnaire.java - getPrelimRatingOptions - " + e);
		}finally{
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}

	/**
	 * Retrieves rating task and rating scale information
	 * @param iSurveyID
	 * @return
	 * @throws SQLException
	 * @author Maruli, modified by Yuni
	 */
	public Vector SurveyRating(int iSurveyID) throws SQLException 
	{
		/*
		 * WARNING: THIS IS A TEMPORARY SOLUTION FOR SPS, WILL NEED TO CHANGE CODES SO THAT CPR ALWAYS APPEAR ON TOP EVEN IF THERE'S FPR, etc.
		 * Change(s) : Added DESC behind "ORDER BY tblSurveyRating.RatingTaskID" in below query so that CPR appears on top
		 * Reason(s) : CPR and CP appears in the wrong order, CPR should always be on top
		 * Updated By: Desmond
		 * Updated On: 23 Oct 2009
		 */
		String query = "SELECT tblSurveyRating.RatingTaskID, tblRatingTask.RatingCode, tblRatingTask.RatingTask, tblSurveyRating.RatingTaskName, ";
		query = query + "tblSurveyRating.ScaleID, tblScale.ScaleDescription, tblScale.ScaleRange FROM tblSurveyRating INNER JOIN ";
		query = query + "tblRatingTask ON tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
		query = query + "tblScale ON tblSurveyRating.ScaleID = tblScale.ScaleID WHERE ";
		query = query + "tblSurveyRating.SurveyID = " + iSurveyID;
		query = query + " ORDER BY tblSurveyRating.RatingTaskID DESC, tblSurveyRating.ScaleID";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		while(rs.next()) {
				votblSurveyRating vo = new votblSurveyRating();
				vo.setRatingTaskName(rs.getString("RatingTaskName"));
				vo.setRatingTask(rs.getString("RatingTask"));
				vo.setRatingTaskID(rs.getInt("RatingTaskID"));
				vo.setRatingCode(rs.getString("RatingCode"));
				vo.setScaleID(rs.getInt("ScaleID"));
				vo.setScaleDescription(rs.getString("ScaleDescription"));
				vo.setScaleRange(rs.getInt("ScaleRange"));
				
				v.add(vo);
				
	  		}
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - SurveyRating - " + E);
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
	 * Get the maximum scale, which is to be used in the alignment process.
	 * @param iSurveyID
	 * @return
	 * @throws SQLException
	 * @author Maruli
	 */
	public int maxScale(int iSurveyID) throws SQLException {
		int max = 0;

		String query = "SELECT MAX(tblScaleValue.HighValue) AS MAXIMUM FROM tblSurveyRating INNER JOIN ";
		query += "tblScaleValue ON tblSurveyRating.ScaleID = tblScaleValue.ScaleID WHERE ";
		query += "tblSurveyRating.SurveyID = " + iSurveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next())
				max = rs.getInt(1);
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - maxscale - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
        
        return max;
	}
	/**
	 * @author xukun
	 * @param iSurveyID
	 * @return
	 * @throws SQLException
	 */
	public int minScale(int iSurveyID) throws SQLException {
		int min = 0;

		String query = "SELECT Min(tblScaleValue.LowValue) AS MINIMUM FROM tblSurveyRating INNER JOIN ";
		query += "tblScaleValue ON tblSurveyRating.ScaleID = tblScaleValue.ScaleID WHERE ";
		query += "tblSurveyRating.SurveyID = " + iSurveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next())
				min = rs.getInt(1);
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - maxscale - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
        
        return min;
	}
	
	
	/**
	 * Retrieves competencies or key behaviours under the surveyID.
	 */
	public Vector Competency(int iSurveyID) throws SQLException {
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		
		if(surveyLevel == 0) {
			/* Change (s): added in CompetencyName for competency level survey
			 * Reason: enable Questionnaire for Competency Level Survey
			 * Updated By: Qiao Li 28 Dec 2009
			 */
			query =  query + "SELECT Competency.CompetencyName, Competency.CompetencyDefinition, Competency.PKCompetency" +
					" FROM Competency INNER JOIN ";
			query =  query + "tblSurveyCompetency ON Competency.PKCompetency = tblSurveyCompetency.CompetencyID ";
			query =  query + "AND Competency.PKCompetency = tblSurveyCompetency.CompetencyID ";
			query =  query + "WHERE tblSurveyCompetency.SurveyID = " + iSurveyID;
			query =  query + "ORDER BY tblSurveyCompetency.CompetencyID";
		}else {
			
			/*
			 * Change(s) : Included INNER JOIN to Competency table to retrieve CompetencyName
			 * Reason(s) : To Add Competency Name before its list of Key Behaviour statements in the Questionnaire Report
			 * Updated By: Desmond
			 * Updated On: 23 Oct 2009
			 */
			
			query =  query + "SELECT Competency.CompetencyName, KeyBehaviour.KeyBehaviour FROM KeyBehaviour ";
			query =  query + "INNER JOIN tblSurveyBehaviour ON KeyBehaviour.PKKeyBehaviour = tblSurveyBehaviour.KeyBehaviourID ";
			query =  query + "INNER JOIN Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
			query =  query + "WHERE tblSurveyBehaviour.SurveyID = " + iSurveyID;
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();

	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		while(rs.next()) {
				String[] name = new String[3];
				if (surveyLevel == 0){
					//added one more information(Competency ID) Qiao Li 28 Dec 2009
					name[0] = rs.getString(1);
					name[1] = rs.getString(2);
					name[2] = rs.getString(3); 
				}
				else{
					name[0] = rs.getString(1);
					name[1] = rs.getString(2); // Change pattern of name to CompetencyName-KeyBehaviourStatement , Desmond 23 Oct 09
					name[2] = "";
				}
				v.add(name);
	  		}
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - Competency - " + E);
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
	 * Get the username based on the name sequence.
	 */
	public String UserName() {
		String name = "";
		
		int nameSeq = Integer.parseInt(surveyInfo[10]);	//0=familyname first

		String familyName = surveyInfo[9];
		String GivenName = surveyInfo[8];
				
		if(nameSeq == 0)
			name = familyName + " " + GivenName;
		else
			name = GivenName + " " + familyName;
			
		return name;		
	}
	
	
	/**
	 * Retrieves username based on name sequence and User ID.
	 */
	public String UserName(int nameSeq, int targetID) throws SQLException, Exception {
		String name = "";
		String query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next()) {
	  			String familyName = rs.getString(1);
				String GivenName = rs.getString(2);
					
				if(nameSeq == 0)
					name = familyName + " " + GivenName;
				else
					name = GivenName + " " + familyName;
	  		}
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - UserName - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		return name;
	}
	
	/**
	 * Get the predefined rating statement based on the rating code.
	 */
	public String RatingStatement(String RTCode) {
		String statement = "Please rate what you think is the ";
		if (ST.LangVer == 2)
			statement = "Mohon nilai apa yang anda anggap ";
		
		/*
		 * Change(s) : Re-organize logic use for generating the statement
		 * Reason(s) : Rating Statement does not display for CPR
		 * Updated By: Desmond
		 * Updated On: 23 Oct 2009
		 */
		
		if(RTCode.equals("CP")) {
			if (ST.LangVer == 2)
				statement = statement + "tingkat proficiency yang didemonstrasikan oleh Target untuk setiap pernyataan yang diberikan.";
			else // Default (ST.LangVer == 1) English
				statement = statement + "level of proficiency demonstrated by the Target currently for each of the given statement provided.";
		}
		else if(RTCode.equals("CPR")) {
			if (ST.LangVer == 2) {
				statement = statement + "tingkat proficiency yang diperlukan untuk pernyataan yang diberikan dalam pekerjaan yang ditujukan.";
			} else {// Default (ST.LangVer == 1) English
				// Updated rating statement for CPR in Excel Questionnaire, Desmond 10 August 2010
				statement = statement + "level of proficiency required of the target.";
			}
		}
		else if(RTCode.equals("FPR")) {
			if (ST.LangVer == 2)
				statement = statement + "tingkat proficiency yang diperlukan untuk pernyataan yang diberikan dalam pekerjaan yang ditujukan dalam " + timeFrame + ".";
			else // Default (ST.LangVer == 1) English
				statement = statement + "required level of proficiency for the given statement in the targeted job in " + timeFrame + ".";
		}
		else if(RTCode.equals("IN")) {
			if (ST.LangVer == 2)
				statement = statement + "kepentingan untuk pernyataan yang diberikan dalam pekerjaan yang ditujukan.";
			else // Default (ST.LangVer == 1) English
				statement = statement + "importance for the given statement in the targeted job.";
		}
		else if(RTCode.equals("IF")) {
			if (ST.LangVer == 2)
				statement = statement + "kepentingan untuk pernyataan yang diberikan dalam pekerjaan yang ditujukan dalam " + timeFrame + ".";
			else // Default (ST.LangVer == 1) English
				statement = statement + "importance for the given statement in the targeted job in " + timeFrame + ".";
		}
			
		return statement;
	}


	/**
	 * 	Initialize all the processes dealing with Excel Application.
	 */
	/**
	 * @param savedFileName
	 * @throws IOException
	 * @throws Exception
	 */
	public void InitializeExcel(String savedFileName) throws IOException, Exception 
	{	
		System.out.println("2. Excel Initialisation Starts");
		storeURL 	= "file:///" + ST.getOOReportPath() + savedFileName;

		String templateURL 	= "";
		if (ST.LangVer == 1){
			templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "QuestionnaireTemplate.xls";
		}else if (ST.LangVer == 2) {
			templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "QuestionnaireTemplate_INA.xls";
		}
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
	 * Initialize all processes regarding to survey.
	 */
	public void InitializeSurvey(int surveyID, int targetID, int raterID, String fileName) throws SQLException, IOException
	{
		System.out.println("3. Initialize Survey");
		
		column = 0;
		this.surveyID = surveyID;
		this.targetID = targetID;
		this.raterID = raterID;
		
		surveyInfo = SurveyInfo(surveyID);

		orgID = Integer.parseInt(surveyInfo[6]);
		
		scale = new int [2];
		rating = new int [2];
	}
	
	/**
	 * Replace one string with another
	 */
	public void Replacement() throws Exception, IOException 
	{
		System.out.println("4. Replacement");
		
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		String after;
		
		/* Change:added in brackets to have correct if-else statement matchings 
		 * Reason: print out "Key Behaviour Level" even when surveyLevel == 0
		 * Updated by:Qiao Li 28 Dec 2009
		*/
		if(surveyLevel == 0){
			after = "(Competency Level)";
			if (ST.LangVer == 2)
				after = "(Tingkat Kompetensi)";
		}
		else{
			after = "(Key Behaviour Level)";
			if (ST.LangVer == 2)
				after = "(Tingkat Perilaku Kunci)";
		}
			
		OO.findAndReplace(xSpreadsheet, "<Comp/KB Level>", after);
		OO.findAndReplace(xSpreadsheet, "<Job Position>", surveyInfo[1]);
		OO.findAndReplace(xSpreadsheet, "<Target Name>", UserName());
		
		if (ST.LangVer == 1) {
			// For English (Default)
			
			OO.findAndReplace(xSpreadsheet, "<Deadline for Submission:>", "Deadline for Submission: " + surveyInfo[4]);
			//added in the assignmentID
			//Qiao Li 04 Jan 2010
			OO.findAndReplace(xSpreadsheet, "<Rater: Rater Type>", "Rater: " + surveyInfo[7] + " (" + surveyInfo[13] + ")");
			OO.findAndReplace(xSpreadsheet, "<Survey Name:>", "Survey Name: " + surveyInfo[0]);
			
			/*
			 * Change(s) : Added logic to check if Future Job Exists before adding Future Job and Time Frame to final report
			 * Reason(s) : Future Job and Time Frame information should only appear when there's FPR rating
			 * Updated By: Desmond
			 * Updated On: 23 Oct 2009
			 */
			if( surveyInfo[2].equalsIgnoreCase("") ) {
				OO.findAndReplace(xSpreadsheet, "<Future Job:>", "");
				OO.findAndReplace(xSpreadsheet, "<Time Frame:>", "");
			} else {
				OO.findAndReplace(xSpreadsheet, "<Future Job:>", "Future Job: " + surveyInfo[2]);
				OO.findAndReplace(xSpreadsheet, "<Time Frame:>", "Time Frame: " + surveyInfo[5]);
			}
		}
		else if (ST.LangVer == 2) {
			// For Bahasa Indonesia
			OO.findAndReplace(xSpreadsheet, "<Deadline for Submission:>", "Batas Waktu Pengiriman: " + surveyInfo[4]);
			//added in the assignmentID
			//Qiao Li 04 Jan 2010
			OO.findAndReplace(xSpreadsheet, "<Rater: Rater Type>", "Penilai: " + surveyInfo[7]+ " (" + surveyInfo[13] + ")");
			OO.findAndReplace(xSpreadsheet, "<Survey Name:>", "Nama Survei: " + surveyInfo[0]);
			
			/*
			 * Change(s) : Added logic to check if Future Job Exists before adding Future Job and Time Frame to final report
			 * Reason(s) : Future Job and Time Frame information should only appear when there's FPR rating
			 * Updated By: Desmond
			 * Updated On: 23 Oct 2009
			 */
			if( surveyInfo[2].equalsIgnoreCase("") ) {
				OO.findAndReplace(xSpreadsheet, "<Future Job:>", "");
				OO.findAndReplace(xSpreadsheet, "<Time Frame:>", "");
			} else {
				OO.findAndReplace(xSpreadsheet, "<Future Job:>", "Pekerjaan Masa Depan: " + surveyInfo[2]);
				OO.findAndReplace(xSpreadsheet, "<Time Frame:>", "Jangka Waktu: " + surveyInfo[5]);
			}
		}
	}
	
	/**
	 * Writes all the demographics fields on the excel sheet.
	 */
	public void InsertDemographics() throws SQLException, IOException, Exception
	{
		System.out.println("5. Insert Demographics");
		
		int [] address = OO.findString(xSpreadsheet, "<demographics>");
		
		column = address[0];
		row = address[1];
		
		Vector demoSelected = DE.AllSelectedDemographic(surveyID);
		
		/*
		 * Change(s) : Added logic to remove <demographics> when no Demographics information is recorded in database
		 * Reason(s) : The word "Demographics" should not even be displayed when no Demographics information is added
		 * Updated By: Desmond
		 * Updated On: 23 Oct 2009
		 */
		if(demoSelected.size() == 0 ) {
			OO.findAndReplace(xSpreadsheet, "<demographics>", "");
			return;
		}
		
		if(ST.LangVer == 1)
			OO.findAndReplace(xSpreadsheet, "<demographics>", "Demographics");
		else
			OO.findAndReplace(xSpreadsheet, "<demographics>", "Demografis");
		
		row++;
		
		for(int j=0; j<demoSelected.size(); j++)
		{
			votblSurveyDemos vo = (votblSurveyDemos)demoSelected.elementAt(j);
			int demoID = vo.getDemographicID();
			
			if(demoID <= 7)
			{
				String demo = vo.getDemographicName();
				
				OO.insertString(xSpreadsheet, demo + ":", row, column);
				OO.mergeCells(xSpreadsheet, column, column+10, row, row);
				
				column += 13;
				int c = column;
									
				Vector demoOptions = new Vector();
				switch(demoID) {
					case 1 : demoOptions = DE.getAllEthnic(orgID);
							 break;	
					case 2 : demoOptions = DE.getAllAgeRange(orgID);
							 break;	
					case 3 : demoOptions = DE.getAllGender(orgID);
							 break;	
					case 4 : demoOptions = DE.getAllLocation(orgID);
							 break;	
					case 5 : demoOptions = DE.getAllJobFunction(orgID);
							 break;	
					case 6 : demoOptions = DE.getAllJobLevel(orgID);
							 break;	
					case 7 : demoOptions = DE.getAllDepartment(orgID);
							 break;	
				}
				
				int temp = 0;
				String name2 = "", name3 = "", name = "";
				String option = "";	
														
				for(int i=0; i<demoOptions.size(); i++)
				{
					/*int col = 2;
				
					if(demoID == 7)
						col = 3;
					
					option = demoOptions.getString(col);*/
					
					switch(demoID) {
						case 1 : voEthnic voE = (voEthnic)demoOptions.elementAt(i);
								 option = voE.getEthnicDesc();
								 break;	
						case 2 : voAge voA = (voAge)demoOptions.elementAt(i);
								 option = Integer.toString(voA.getAgeRangeTop());
								 break;	
						case 3 : voGender voG = (voGender)demoOptions.elementAt(i);
								 option = voG.getGenderDesc();
								 break;	
						case 4 : voLocation voL = (voLocation)demoOptions.elementAt(i);
								 option = voL.getLocationName();
								 break;	
						case 5 : voJobFunction voJF = (voJobFunction)demoOptions.elementAt(i);
								 option = voJF.getJobFunctionName();
								 break;	
						case 6 : voJobLevel voJL = (voJobLevel)demoOptions.elementAt(i);
								 option = voJL.getJobLevelName();
								 break;	
						case 7 : voDepartment voD = (voDepartment)demoOptions.elementAt(i);
								 option = voD.getDepartmentName();
								 break;	
					}
					
					name = option;
					
					if(demoID == 2)
					{
						if(temp == 0) {								
							option = "Below " + option;				
							temp++;
						}
						else {
							name3 = Integer.toString(Integer.parseInt(option)-1);
							option =  name2 + " - " + name3;
						}
						
						name2 = name;
					}
					
					OO.insertString(xSpreadsheet, "__   " + UnicodeHelper.getUnicodeStringAmp(option), row, c);
					OO.mergeCells(xSpreadsheet, c, c+12, row, row);
					c += 14;
				
					int index = i+1;
					if(index < demoOptions.size())
					{
						i++;
						switch(demoID) {
							case 1 : voEthnic voE = (voEthnic)demoOptions.elementAt(index);
									 option = voE.getEthnicDesc();
									 break;	
							case 2 : voAge voA = (voAge)demoOptions.elementAt(index);
									 option = Integer.toString(voA.getAgeRangeTop());
									 break;	
							case 3 : voGender voG = (voGender)demoOptions.elementAt(index);
									 option = voG.getGenderDesc();
									 break;	
							case 4 : voLocation voL = (voLocation)demoOptions.elementAt(index);
									 option = voL.getLocationName();
									 break;	
							case 5 : voJobFunction voJF = (voJobFunction)demoOptions.elementAt(index);
									 option = voJF.getJobFunctionName();
									 break;	
							case 6 : voJobLevel voJL = (voJobLevel)demoOptions.elementAt(index);
									 option = voJL.getJobLevelName();
									 break;	
							case 7 : voDepartment voD = (voDepartment)demoOptions.elementAt(index);
									 option = voD.getDepartmentName();
									 break;	
						}
						name = option;
						
						if(demoID == 2) {
							name3 = Integer.toString(Integer.parseInt(option)-1);
							option =  name2 + " - " + name3;																
						}
						
						name2 = name;
						
						OO.insertString(xSpreadsheet, "__   " + UnicodeHelper.getUnicodeStringAmp(option), row, c);
						OO.mergeCells(xSpreadsheet, c, c+12, row, row);
						c += 14;
					}
					
					row++;
					OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
					scale[1] += 1;
					rating[1] += 1;
					c = column;
				} 	
				
				if(demoID == 2)
				{
					OO.insertString(xSpreadsheet, "__   Above " + UnicodeHelper.getUnicodeStringAmp(name3), row, c);
					OO.mergeCells(xSpreadsheet, c, c+12, row, row);
					c += 14;
				}				
				
				column = 0;
				
				row++;
				OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
									
				rating[1] += 1;
				scale[1] += 1;
									
				row++;
				OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
				scale[1] += 1;
				rating[1] += 1;
			}
		}
	}
	
	/**
	 * Writes all the rating scales on the excel sheet. add in rating example for prelim and questionaire
	 */
	public void InsertRS(int iSurveyID) throws SQLException, IOException, Exception
	{
		System.out.println("5. Printing Rating Scale");
		
		int [] address = OO.findString(xSpreadsheet, "<rating task>");
		
		column = address[0];
		row = address[1];	
		
		OO.findAndReplace(xSpreadsheet, "<rating task>", "");

		int maxScale = maxScale(iSurveyID) + 1;
		int totalCells = totalColumn / maxScale;
		if (totalCells <6) // Denise 29/12/2009 6 is the estimated number for the longest words
			totalCells = 6;
		int totalMerge = 0;		// total cells to be merged after rounding
		double merge = 0;		// total cells to be merged before rounding
		boolean hideNA = Q.getHideNAOption(surveyID);			
		Vector v = SurveyRating(iSurveyID);	
		
		int count = 0;
		// add prelim example here
		Vector<PrelimQuestion> pqs = Q.getPrelimQuestion(surveyID);
		if(pqs.size()>0){
			PrelimQuestion pq = pqs.get(0);
			String header = (count+1)+". Preliminary Example: Rate the statement " +
					"by highlighting in yellow as shown below:";
			OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 2, 1);
			OO.insertString(xSpreadsheet, header, row, column);
			OO.setFontBold(xSpreadsheet, column, column, row, row);
			row+=2;
			OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
			OO.insertString(xSpreadsheet, pq.getQuestion(), row, column);
			row++;
			int ratingScaleId = pq.getPrelimRatingScaleId();
			Vector<String> options = getPrelimRatingOptions(ratingScaleId);
			for(int j=0; j<options.size();j++){
				//insert the options
				OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
				OO.insertString(xSpreadsheet, options.get(j), row, 1);	
				OO.mergeCells(xSpreadsheet, 1, totalColumn, row, row);
				OO.wrapText(xSpreadsheet, 1, totalColumn, row, row);	
				
				if(j == 0){
					OO.setBGColor(xSpreadsheet, 1, totalColumn, row, row, 0xffff00);
				}
				row++;
			}
			count++;
			row++;
		}
		
		for(int i=0; i<v.size(); i++) {
			votblSurveyRating vo = (votblSurveyRating)v.elementAt(i);
			count++;
			
			String code = vo.getRatingCode();
			String ratingTask = vo.getRatingTaskName();
			int scaleID = vo.getScaleID();
			//Denise 29/12/2009 insert row here
			OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
			OO.insertString(xSpreadsheet, count + ". " + ratingTask, row, column);
			OO.setFontBold(xSpreadsheet, column, column, row, row);
			
			String statement = RatingStatement(code);

			row++;
			OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
			scale[1] += 1;
			
			OO.insertString(xSpreadsheet, statement, row, column+1);
			OO.mergeCells(xSpreadsheet, column+1, totalColumn, row, row);
			
			// add rating scale	
			row = row + 2;
//			OO.insertRows(xSpreadsheet, 1, 1, row, row+2, 2, 1);
			scale[1] += 2;
							
			int c = 1;
			int r = row;
			int to = c;
			
			Vector RS = Q.getRatingScale(scaleID);
			column += 1;
			
			OO.insertRows(xSpreadsheet, 1, 1, row, row+3, 3, 1);
			for(int j=0; j<RS.size(); j++) {
				String [] sRS = new String[3];
		    	
				sRS = (String[])RS.elementAt(j);
				
				int low = Integer.parseInt(sRS[0]);
				int high = Integer.parseInt(sRS[1]);
				String desc = sRS[2];
				//Denise 29/12/2009 to hide NA if required
				if (!(hideNA && (desc.equalsIgnoreCase("NA") || desc.equalsIgnoreCase("N/A") || desc.equals("Not applicable")
      					|| desc.contains("NA") || desc.contains("N/A")|| desc.contains("Not applicable") ||desc.contains("Not Applicable"))))
				{
					
					if (column + totalCells > totalColumn)
					{
						row += 3;
						column = 1;
						OO.insertRows(xSpreadsheet, 1, 1, row, row+3, 3, 1);
					}
					OO.insertString(xSpreadsheet, desc, row, column);	// add in scale description
					OO.setCellAllignment(xSpreadsheet, column, column, row, row, 1, 2);
					OO.setCellAllignment(xSpreadsheet, column, column, row, row, 2, 2);
				
					r = row + 1;
					c = column;
				
				
					int start = c; // start merge cell
					String temp = "";

					while(low <= high) {						
						if(low > 1)
							temp += "    ";
						temp = temp + Integer.toString(low);

						low++;						
					}
				
					OO.insertString(xSpreadsheet, temp, r, c);	// add in rating scale value
					OO.setCellAllignment(xSpreadsheet, c, c, r, r, 1, 2);
				
					to = start+totalCells-1;	// merge cell for rating scale value
			
					OO.mergeCells(xSpreadsheet, start, to, r, r);
					OO.setTableBorder(xSpreadsheet, start, to, r, r, true, true, true, true, true, true);

					OO.mergeCells(xSpreadsheet, start, to, row, row);	// merge cell for rating scale description
					OO.setTableBorder(xSpreadsheet, start, to, row, row, true, true, true, true, true, true);
					OO.setBGColor(xSpreadsheet, start, to, row, row, BGCOLOR);
				
					merge = (double)desc.trim().length() / (double)(totalCells);				
					
					BigDecimal BD = new BigDecimal(merge);
					BD.setScale(0, BD.ROUND_UP);
					BigInteger BI = BD.toBigInteger();
					totalMerge = BI.intValue() + 1;
				
					OO.setRowHeight(xSpreadsheet, row, start, (500 * totalMerge));
				
					column = to + 1;
				}//end if to insert Rating scale
			}
			row = r + 2;
	//		OO.insertRows(xSpreadsheet, 1, 1, row, row+3, 3, 1);
			scale[1] += 2;							
			column = 0;					
		}
		
		// add rating example here
		Vector vCompetency = Competency(iSurveyID);
		String[] info = ((String[])vCompetency.get(0));
		OO.insertRows(xSpreadsheet, 1, 1, row, row+1, 1, 1);
		OO.insertString(xSpreadsheet, info[0], row, 1);
		OO.setFontBold(xSpreadsheet, column, column, row, row);
		row++;
		
		int mergeColumn = 20;
		int mergeRow = info[1].length()/(mergeColumn*3);
		OO.insertRows(xSpreadsheet, 1, 1, row, row+1, mergeRow+3, 1);
		OO.insertString(xSpreadsheet, info[1], row, 1);
		OO.mergeCells(xSpreadsheet, column+1, column+1+mergeColumn, row, row+mergeRow);
		OO.setCellAllignment(xSpreadsheet, column+1, column+1, row, row, 2, 1);
		
		int scale = maxScale(surveyID);
		int startScale = minScale(surveyID);
		if(startScale == 0){
			if(hideNA){
				startScale = 1;
			}
		}
		column += mergeColumn+3;// move right from the question description
		int highlightNum = scale - 2;
		TreeMap<Integer, String> ratingTasks = Q.getRatingTasks(surveyID);
		for(Map.Entry<Integer, String> entry : ratingTasks.entrySet()){
			if(entry.getKey() == 5){
				highlightNum = scale-2;
			}else if(entry.getKey() == 4){
				highlightNum = scale-2;
			}else if(entry.getKey() == 3){
				highlightNum = scale-2;
			}else if(entry.getKey() == 2){
				highlightNum = scale-2;
			}else if(entry.getKey() == 1){
				highlightNum = scale/2 + 1;
			}
			int cprCol = column;
			OO.insertString(xSpreadsheet, entry.getValue(), row, cprCol);
			OO.mergeCells(xSpreadsheet, cprCol, cprCol+10, row, row);
			cprCol += 11;
			
			for(int i = startScale; i <= scale; i++){
				OO.insertString(xSpreadsheet, Integer.toString(i), row, cprCol);
				if(i == highlightNum){
					OO.setBGColor(xSpreadsheet, cprCol, cprCol, row, row, 0xffff00);
				}
				cprCol++;
			}
			row++;
		}
		/*
		OO.insertString(xSpreadsheet, "Current Proficiency", row, column);
		OO.mergeCells(xSpreadsheet, column, column+10, row, row);
		column += 11;
		
		
		for(int i = startScale; i <= scale; i++){
			OO.insertString(xSpreadsheet, Integer.toString(i), row, column);
			if(i == highlightNum){
				OO.setBGColor(xSpreadsheet, column, column, row, row, 0xffff00);
			}
			column++;
		}
		*/
		
	}
	
	/**
	 * locate start position in excel file before load in questions
	 * author xukun
	 */
	public void locateStartPosition() throws SQLException, IOException, Exception
	{
		int [] address = OO.findString(xSpreadsheet, "<scale>");
		column = address[0];
		row = address[1];	
		OO.findAndReplace(xSpreadsheet, "<scale>", "");
	}
	
	/**
	 * Writes all competency / key behaviours and the scale on the excel sheet.
	 */
	public void InsertCompetency(int iSurveyID) throws SQLException, IOException, Exception
	{
		System.out.println("6. Printing Competency");
		
		/* Change(s): added in surveyLevel info
		 * Reason: enable Questionnaire for Competency Level Survey
		 * Updated by: Qiao Li 28 Dec 2009
		 */
		int surveyLevel = Integer.parseInt(surveyInfo[3]);

		
		Vector vCompetency = Competency(iSurveyID);
		int count = 0;
		int countComp = 0;		// count total competency for each page = 4 then page break
				
		int maxScale = maxScale(iSurveyID);	
		int RTStart = totalColumn - maxScale - 12;
		int scaleStart;
		
		int totalMerge = 0;		// total cells to be merged after rounding
		double merge = 0;		// total cells to be merged before rounding
		int totalMergeClm = RTStart - 2;

		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, "Statements", row, column);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Pernyataan", row, column);
		OO.mergeCells(xSpreadsheet, column, column+RTStart-1, row, row);
		OO.setCellAllignment(xSpreadsheet, column, column+RTStart-1, row, row, 1, 2);
		OO.setBGColor(xSpreadsheet, column, column+RTStart-1, row, row, BGCOLOR);
		OO.setFontBold(xSpreadsheet, column, column+RTStart-1, row, row);
		
		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, "Rating Tasks", row, RTStart);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Tugas Penilaian", row, RTStart);
		OO.mergeCells(xSpreadsheet, RTStart, totalColumn, row, row);
		OO.setCellAllignment(xSpreadsheet, RTStart, totalColumn, row, row, 1, 2);
		OO.setBGColor(xSpreadsheet, RTStart, totalColumn, row, row, BGCOLOR);
		OO.setFontBold(xSpreadsheet, RTStart, totalColumn, row, row);
		
		int rowStart = row;
		int rowFinish = row + totalRows;
		int c = 0;
		
		String currComp = ""; // temporary variable to store current Competency Name, Desmond 23 Oct 09
		
		for(int i=0; i<vCompetency.size(); i++)
		{
						
			int noRow = 0;
			count++;
			
			String[] name = (String[])vCompetency.elementAt(i);
			String statement = name[0] + " - " + name[1] + " - " + name[2];
			
			merge = (double)statement.trim().length() / (double)(totalMergeClm * 2);
			
			// Split CompetencyName-KeyBehaviourStatement to CompetencyName and KeyBehaviourStatement
			String[] temp = name;
			//Denise 30/12/2009 estimate the no of row to print out competency
			noRow  = (int)Math.ceil(merge) + 10;		
			if (surveyLevel == 0) // if survey level  = 0. Plus the number of KB 
				noRow += KeyBehaviour(Integer.parseInt(temp[2])).size();
						
			
			//Denise change from 6 to noRow
			if((rowFinish - row) < noRow && rowFinish != row) {
				OO.insertPageBreak(xSpreadsheet, c, c, row);
				rowStart = row;
				rowFinish = rowStart + totalRows;				
			}
				
			row++;
			column++;
			/*
			 * Change(s) : Added codes to insert Competency Name at the top of each list of KB statements
			 * Reason(s) : Questionnaire Report at KB level only shows all the KB statements without 
			 * 				grouping them under their respective Competency Name
			 * Updated By: Desmond
			 * Updated On: 23 Oct 2009
			 */
			if(!currComp.equalsIgnoreCase(temp[0])) {
				
				currComp = temp[0]; // Update currComp to reflect new Current Competency
				
				//Insert Competency Name for its set of KB statements before listing the KB statements
				OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(currComp), row, column);
				
				// To apply formatting Cell bold
				OO.setFontBold(xSpreadsheet, column, column, row, row);
				
				// Reset count so that the the numbering of the list of KB statements for each Competency starts from 1
				count = 1;
			}

			row++;
			//do not show number for Competency Level Survey
			//Qiao Li 29 Dec 2009
			if (surveyLevel==1){
				OO.insertString(xSpreadsheet, Integer.toString(count), row, column-1);
			}
			//column++;
			OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(temp[1]), row, column);
			
			// Set Cell Alignment to Top for each KB Statement and the numbering next to it, Desmond 26 Oct 09
			OO.setCellAllignment(xSpreadsheet, column-1, column, row, row, 2, 1);
			
			Vector v = SurveyRating(iSurveyID);
			
			int statementStart = 0;
			int statementRow = row;
			
			int bigger = 0; //compare if totalRT rows more than statement, then will take the bigger one

			for(int j=0; j<v.size(); j++) {
				votblSurveyRating vo = (votblSurveyRating)v.elementAt(j);
				
				c = RTStart;
				statementStart++;
				
				scaleStart = totalColumn - maxScale;
				
				String ratingTask = vo.getRatingTask();
				int scaleRange = vo.getScaleRange();
				
				OO.insertString(xSpreadsheet, ratingTask, row, c);
				OO.mergeCells(xSpreadsheet, c, c+10, row, row);
				OO.setCellAllignment(xSpreadsheet, c, c+10, row, row, 2, 2);
				
				for(int k=0; k<=scaleRange; k++) {
				//Denise 29/12/2009 hide 0 value if required
					if (!(Q.getHideNAOption(surveyID) && k==0))
						OO.insertString(xSpreadsheet, Integer.toString(k), row, scaleStart++);
					//Denise 4/1/2009 set the alignment 
					OO.setCellAllignment(xSpreadsheet, scaleStart - 1, scaleStart-1, row, row, 2, 2);
				}
				row++;
				bigger++;												
			}
				
			column = 0;	
							
						
			BigDecimal BD = new BigDecimal(merge);
			BD.setScale(0, BD.ROUND_UP);
			BigInteger BI = BD.toBigInteger();
			totalMerge = BI.intValue();
			
			OO.mergeCells(xSpreadsheet, 1, column+RTStart-2, statementRow, statementRow + totalMerge);
			
			if(totalMerge >= bigger)
				row = row + totalMerge - bigger + 1;
			
			/* Change(s): printed out KB for competency level survey
			 * Reason: enable Questionnaire for Competency Level Survey
			 * Updated by: Qiao Li 29 Dec 2009
			 */
			String m_input = "";
			String input = "";
			if (surveyLevel == 0 && !Q.isKBHidden(iSurveyID)){//added in key behaviours for competency level survey   xukun edit here for hidden KB
				int compID = Integer.parseInt(temp[2]);
				Vector kb = KeyBehaviour(compID);
				
				OO.insertString(xSpreadsheet, "Key Behaviours: ", row, 1);
				row++;
				
				for (int j = 0; j < kb.size();j++){
					OO.insertString(xSpreadsheet,Integer.toString(j+1), row, 1);
					input = (String)kb.get(j);
					input=input.replaceAll("\n", "");
					
					OO.insertString(xSpreadsheet, input,row,2);

					if (input.length()>110){//to check whether the number of char exceed page width
											//need to change if the font size changes
						//Alter to 41 to fit in the page (Qiao Li 04 Jan 2009)
						OO.mergeCells(xSpreadsheet, 2, 41, row, row+1);//assume endColumn==41
						OO.setCellAllignment(xSpreadsheet, 1, 1, row, ++row, 2, 2);
					}
					row++;
				}
			}
			
			//check for self
			int selfIncluded = Q.SelfCommentIncluded(surveyID);
			int included = Q.commentIncluded(surveyID);
			
			int assignmentID = Q.AssignmentID(surveyID, targetID, raterID);
			String rCode = S.RaterCode(assignmentID);
						
			if(included == 1) {
			
				if((selfIncluded == 1 && rCode.equals("SELF")) || !rCode.equals("SELF")) {
				
					if((rowFinish - row) < 8 && rowFinish != row) {
						OO.insertPageBreak(xSpreadsheet, c, c, row);
						rowStart = row;
						rowFinish = rowStart + totalRows;				
					}
			
					row++;
					if (ST.LangVer == 1)
						OO.insertString(xSpreadsheet, "Comments:", row, 1);
					else if (ST.LangVer == 2)
						OO.insertString(xSpreadsheet, "Komentar:", row, 1);
					OO.mergeCells(xSpreadsheet, 1, 5, row, row);
					row++;			
					
					OO.insertString(xSpreadsheet, "", row, 1);
					OO.mergeCells(xSpreadsheet, 1, totalColumn, row, row+4);
				
					row += 6;
					
					countComp++;	
				} else 				
					row++;									
			}										
		}
		
		row += 1;

	}

	public void insertEndNote() throws Exception
	{

		if (ST.LangVer == 1)
			OO.insertString(xSpreadsheet, "Thank you for your participation", row, 1);
		else if (ST.LangVer == 2)
			OO.insertString(xSpreadsheet, "Terima kasih untuk partisipasi anda", row, 1);
		OO.mergeCells(xSpreadsheet, 1, totalColumn, row, row);

	}
	
	/**
	 * 
	 * @throws Exception
	 * @author xukun
	 */
	public void insertPrelimQuestions(int surveyID)  throws SQLException, IOException, Exception
	{
		
		PrelimQuestionController pqc = new PrelimQuestionController();
		Vector<PrelimQuestion> questions = pqc.getQuestions(surveyID);
		if(questions.size()>0)
		{
			try{
				OO.insertPageBreak(xSpreadsheet, 0, 30, row);
				row++;
				OO.insertString(xSpreadsheet,  "Preliminary Questions", row, 0);
	            OO.setFontSize(xSpreadsheet, column, column, row, row, 14);
				OO.setFontBold(xSpreadsheet, column, column, row, row);
				OO.setBGColor(xSpreadsheet, 0, totalColumn, row, row, BGCOLOR);
				row++;
				
				int startborder = row+1;
				
				//insert the dynamic header for the answer 
				String answerHeader = pqc.getPrelimQnHeader(surveyID);
				OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(answerHeader).trim(), row, 0);			
				OO.mergeCells(xSpreadsheet, 0, totalColumn, row, row);
				OO.wrapText(xSpreadsheet, 0, totalColumn, row, row);
				OO.setBGColor(xSpreadsheet, 0, totalColumn, row, row, BGCOLOR);
				OO.setCellAllignment(xSpreadsheet, 0, totalColumn, row, row, 2, 1);
				
				OO.setTableBorder(xSpreadsheet, 1, totalColumn, row, row, false, false, true, true, true, true);	

				row++;
				

				
				for(int i=0;i<questions.size();i++)
				{
					//insert the question
					OO.insertString(xSpreadsheet, questions.get(i).getQuestion(), row, 0);	
					OO.setFontBold(xSpreadsheet, 0, 0, row, row);
					OO.mergeCells(xSpreadsheet, 0, totalColumn, row, row);
					OO.wrapText(xSpreadsheet, 0, totalColumn, row, row);
					//OO.setTableBorder(xSpreadsheet, 4, totalColumn, row, row, false, false, true, true, true, true);	
					
					row++;
					int ratingScaleId = questions.get(i).getPrelimRatingScaleId();
					Vector<String> options = getPrelimRatingOptions(ratingScaleId);
					for(int j=0; j<options.size();j++){
						//insert the options
						OO.insertString(xSpreadsheet, options.get(j), row, 1);	
						OO.mergeCells(xSpreadsheet, 1, totalColumn, row, row);
						OO.wrapText(xSpreadsheet, 1, totalColumn, row, row);
						//OO.setTableBorder(xSpreadsheet, 1, totalColumn, row, row, false, false, true, true, true, true);	
						
						row++;
					}
					row++;
				}
			}catch(Exception e){
				System.out.println("Prelim exception");
			}
		}
	}
	
	public void insertAdditionalQuestions(int surveyID)
	{
		AdditionalQuestionController aqc = new AdditionalQuestionController();
		Vector<AdditionalQuestion> questions  = aqc.getQuestions(surveyID);
		if(questions.size()>0)
		{
			try{
				
				OO.insertPageBreak(xSpreadsheet, 0, 30, row);
				row++;
				OO.insertString(xSpreadsheet,  "Additional Questions", row, 0);
	            OO.setFontSize(xSpreadsheet, column, column, row, row, 14);
				OO.setFontBold(xSpreadsheet, column, column, row, row);
				row++;
				
				int startborder = row+1;
				
				
				//insert the dynamic header for the answer 
				String answerHeader = aqc.getAnswerHeader(surveyID);
				OO.insertString(xSpreadsheet, UnicodeHelper.getUnicodeStringAmp(answerHeader).trim(), row, 1);			
				OO.mergeCells(xSpreadsheet, 1, totalColumn, row, row);
				OO.wrapText(xSpreadsheet, 1, totalColumn, row, row);
				OO.setBGColor(xSpreadsheet, 1, totalColumn, row, row, BGCOLOR);
				OO.setCellAllignment(xSpreadsheet, 1, totalColumn, row, row, 2, 1);
				
				OO.setTableBorder(xSpreadsheet, 1, totalColumn, row, row, false, false, true, true, true, true);	

				row++;
				
				for(int i=0;i<questions.size();i++)
				{
					//insert the numbering for the questions
					OO.insertNumeric(xSpreadsheet, i+1, row, 1);	
					OO.setFontBold(xSpreadsheet, 1, 1, row, row);
					OO.mergeCells(xSpreadsheet, 1, 3, row, row+6);
					OO.setCellAllignment(xSpreadsheet, 1, 1, row, row+6, 2, 2);
					OO.setCellAllignment(xSpreadsheet, 1, 1, row, row+6, 1, 2);
					OO.setTableBorder(xSpreadsheet, 1, 1, row, row+6, false, false, true, true, true, true);	
					
					//insert the question
					OO.insertString(xSpreadsheet, questions.get(i).getQuestion(), row, 4);	
					OO.setFontBold(xSpreadsheet, 4, 4, row, row);
					OO.mergeCells(xSpreadsheet, 4, totalColumn, row, row);
					OO.wrapText(xSpreadsheet, 4, totalColumn, row, row);
					OO.setTableBorder(xSpreadsheet, 4, totalColumn, row, row, false, false, true, true, true, true);	
					
					row++;
					//insert the spaces for answering the question 
					OO.insertString(xSpreadsheet, "1)", row, 4);
					OO.mergeCells(xSpreadsheet, 4, totalColumn, row, row+1);
					OO.setCellAllignment(xSpreadsheet, 4, totalColumn, row, row, 2, 1);
					OO.setTableBorder(xSpreadsheet, 4, totalColumn, row, row+1, false, false, true, true, true, true);	
					row+=2;
					OO.insertString(xSpreadsheet, "2)", row, 4);
					OO.mergeCells(xSpreadsheet, 4, totalColumn, row, row+1);
					OO.setCellAllignment(xSpreadsheet, 4, totalColumn, row, row, 2, 1);
					OO.setTableBorder(xSpreadsheet, 4, totalColumn, row, row+1, false, false, true, true, true, true);	
					row+=2;
					OO.insertString(xSpreadsheet, "3)", row, 4);
					OO.mergeCells(xSpreadsheet, 4, totalColumn, row, row+1);
					OO.setCellAllignment(xSpreadsheet, 4, totalColumn, row, row, 2, 1);
					OO.setTableBorder(xSpreadsheet, 4, totalColumn, row, row+1, false, false, true, true, true, true);	
					row+=2;
				}
				
				
			}catch(Exception e)
			{
				
			}
			
			row++;
			
		}
		
	}
	
	/**
	 * Print out questionnaire report
	 * @param surveyID
	 * @param targetID
	 * @param raterID
	 * @param pkUser
	 * @param fileName
	 */
	public void QuestionnaireReport(int surveyID, int targetID, int raterID, int pkUser, String fileName)
	{
		try {
			
			System.out.println(surveyID + ", " + targetID + ", " + pkUser);
			System.out.println("1. Questionnaire Generation Starts");
			
			InitializeExcel(fileName);
			InitializeSurvey(surveyID, targetID, raterID, fileName);
			Replacement();
			//comment off the demographics part as the default tempate is set to be without demographics
			//Qiao Li 04 Jan 2010
			//InsertDemographics();
			InsertRS(surveyID);
			locateStartPosition();
			insertPrelimQuestions(surveyID);
			InsertCompetency(surveyID);
			insertAdditionalQuestions(surveyID);
			insertEndNote();
			
			// Remove extra space between "Profiler锟�in footer, Desmond 23 Oct 09
			OO.insertHeaderFooter(xDoc, surveyInfo[12], UserName() + "\n" + surveyInfo[1], "Copyright 锟�-Sixty Profiler锟絠s a product of Pacific Century Consulting Pte Ltd.");
			
			System.out.println("===== Questionnaire Generation Completed =====");
		}
		catch (SQLException SE) {
			System.out.println("ExcelQuestionnaire.java - Report - SQLException - " + SE);
		} catch(IOException IE){
			System.err.println("ExcelQuestionnaire.java - Report - IOException - " + IE);
		} catch (Exception E) {
			System.out.println("ExcelQuestionnaire.java - Report - Exception - " + E);
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
	
	public static void main (String [] args)throws SQLException, Exception {
		int surveyID = 415;
		int targetID = 97;
		int raterID = 97;
		
		ExcelQuestionnaire EQ = new ExcelQuestionnaire();
		
		EQ.QuestionnaireReport(466, 6403, 6622, 5, "Questionnaire1.xls");
	}
	
	/**
	 * Retrieves KeyBehaviour according to Competency ID for Competency Level Survey
	 * @param compID
	 * @author Qiao Li 28 Dec 2009
	 */
	public Vector KeyBehaviour(int compID) throws SQLException {
		String query = "";

		/*
		 * Change (s): added in CompetencyName for competency level survey
		 * Reason: enable Questionnaire for Competency Level Survey Updated By:
		 * Qiao Li 28 Dec 2009
		 */
		query = query + "SELECT KeyBehaviour FROM KeyBehaviour ";
		query = query + "WHERE FKCompetency = " + compID;
		query = query + "ORDER BY PKKeyBehaviour";

		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		Vector v = new Vector();

	  	try 
        {          

	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		while(rs.next()) {
				v.add(rs.getString(1));
	  		}
		
        }
        catch(Exception E) 
        {
            
            System.err.println("ExcelQuestionnaire.java - KeyBehaviour - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
        return v;
	}
}