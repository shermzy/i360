package CP_Classes;

import java.util.*; 
import java.util.Date;
import java.sql.*;
import java.io.*;
import java.text.*;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.votblSurveyRating;

import jxl.*;
import jxl.write.*; 
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.read.biff.BiffException;


/**
 * This class implements all the operations for Questionnaire Report in Excel.
 * It implements both J-Integra and JExcelAPI Interface.
 * J-Integra : Find and Replace.
 * JExcelAPI : All operations.
 */ 
public class ExportQuestionnaire {
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;

	/**
	 * Declaration of new object of class Questionnaire.
	 */
	private Questionnaire Q;
	
	/**
	 * Declaration of new object of class Database.
	 */
//	private Database db;
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	/**
	 * Local variable to store the filename to be saved.
	 */	
	private String savedFileName;
	
	/**
	 * Local variable to store the defaultPath of file and template.
	 */
	private String defaultPath;
	
	private int overwrite;
	
	/**
	 * Local variable to store the survey details which is to be used in all of the methods.
	 */
	private String surveyInfo [];
	
	private int surveyID;
	private int assignmentID;
	private int targetID;
	private int raterID;
	
	private int row;
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;	
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat bordersData3;
	private WritableCellFormat bordersData4;
	private File inputWorkBook, outputWorkBook;
	
	/**
	 * A file object for the input workbook.
	 */
	private File inputWorkbook;
	
		
	private WorkbookSettings ws;
    private Workbook w1;
    
    //Added a variable to capture error msg for the validation of the rater's questionniare inputs, Sebastian 29 July 2010
    private String valErrMsg = "";
        
	/**
	 * Creates a new intance of ExportQuestionnaire object.
	 */
	public ExportQuestionnaire() {
		ST = new Setting();
		Q = new Questionnaire();
	//	db = new Database();
		U = new User_Jenty();
		EV = new EventViewer();
		
		surveyInfo = new String [12];
	}
	
	public int getOverwrite() {
		return this.overwrite;
	}
	
	public void setOverwrite(int overwrite) {
		this.overwrite = overwrite;
	}
	
	/**
	 * Initialize all processes regarding to survey.
	 */
	public void InitializeSurvey(int surveyID, int targetID, int raterID, String fileName, int isOverwrite) throws SQLException, IOException{
		this.surveyID = surveyID;
		this.targetID = targetID;
		this.raterID = raterID;
		this.assignmentID = Q.AssignmentID(surveyID, targetID, raterID);
		
		savedFileName = fileName;
		
		surveyInfo = SurveyInfo();
		overwrite = 1;
	}	
	
	/**
	 * Check whether the Survey is Competency Level Analysis or Key Behaviour Level Analysis, given the SurveyID.
	 * Returns : 0 = Competency Level Analysis
	 *			 1 = Key Behaviour Level Analysis
	 */
	public int LevelOfSurvey(int surveyID)
	{
		String query = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		

		try {
			query = query + "select LevelOfSurvey from tblSurvey where SurveyID = " + surveyID;
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			rs.next();

			return rs.getInt(1);
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		return 0;
	}
	
	
	/**
	 * Retrieves rating task and rating scale information.
	 */
	public Vector SurveyRating() throws SQLException {
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	try {
			String query = "SELECT tblSurveyRating.RatingTaskID, tblRatingTask.RatingCode, tblRatingTask.RatingTask, ";
			query = query + "tblSurveyRating.ScaleID, tblScale.ScaleDescription, tblScale.ScaleRange, tblSurveyRating.AdminSetup FROM tblSurveyRating INNER JOIN ";
			query = query + "tblRatingTask ON tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query + "tblScale ON tblSurveyRating.ScaleID = tblScale.ScaleID WHERE ";
			query = query + "tblSurveyRating.SurveyID = " + surveyID;
			query = query + " ORDER BY tblSurveyRating.RatingTaskID, tblSurveyRating.ScaleID";
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
				//RatingTaskID,ScaleID,tblSurveyRating.AdminSetup,tblScale.ScaleDescription,tblScale.ScaleRange,
				//tblRatingTask.RatingCode, tblRatingTask.RatingTask
				votblSurveyRating vo=new votblSurveyRating();
				vo.setRatingTaskID(rs.getInt("RatingTaskID"));
				vo.setAdminSetup(rs.getInt("AdminSetup"));
				vo.setScaleID(rs.getInt("ScaleID"));
				vo.setRatingCode(rs.getString("RatingCode"));
				vo.setRatingTask(rs.getString("RatingTask"));
				vo.setScaleDescription(rs.getString("ScaleDescription"));
				vo.setScaleRange(rs.getInt("ScaleRange"));
				v.add(vo);
			}
			
		} catch (SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - SurveyRating -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
	
			return v;
	}
	
	
	/**
	 * Check rating exist.
	 * If exist then prompt whether want to overwrite or not.
	 */
	public int checkRatingExist(int ID) throws SQLException {
		int surveyLevel = LevelOfSurvey(ID);
		String query = "";
		
		if(surveyLevel == 0) 
			query = "SELECT tblResultCompetency.* FROM tblResultCompetency WHERE AssignmentID = " + ID;
		else 
			query = "SELECT tblResultBehaviour.* FROM tblResultBehaviour WHERE AssignmentID = " + ID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		

		try {
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			if(rs.next())
				return 1;
		} catch (SQLException SE) {
			System.err.println(SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		
		return 0;
	}
	

	/**
	 * Retrieves competencies or key behaviours under the surveyID.
	 */
	public Vector Competency() throws SQLException {
		
		
			Vector v=new Vector();
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;


		try {
			String query = "";

			query =  query + "SELECT Competency.* FROM Competency INNER JOIN ";
			query =  query + "tblSurveyCompetency ON Competency.PKCompetency = tblSurveyCompetency.CompetencyID ";
			query =  query + "AND Competency.PKCompetency = tblSurveyCompetency.CompetencyID ";
			query =  query + "WHERE tblSurveyCompetency.SurveyID = " + surveyID;
			query = query + " order by PKCompetency";
			
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
					//RatingTaskID,ScaleID,tblSurveyRating.AdminSetup,tblScale.ScaleDescription,tblScale.ScaleRange,
					//tblRatingTask.RatingCode, tblRatingTask.RatingTask
					voCompetency vo=new voCompetency();
					vo.setCompetencyDefinition(rs.getString("CompetencyDefinition"));
					vo.setCompetencyName(rs.getString("CompetencyName"));
					vo.setFKCompanyID(rs.getInt("FKCompanyID"));
					vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
					vo.setIsExpert(rs.getInt("IsExpert"));
					vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
					vo.setIsTraitOrSimulation(rs.getInt("IsTraitOrSimulation"));
					vo.setPKCompetency(rs.getInt("PKCompetency"));
					
					v.add(vo);
				}
				
			} catch (SQLException SE) {
				System.err.println(" ExportQuestionnaire.java - Competency -"+SE.getMessage());
			}finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

			}
		
		
				return v;
	}
	
	/**
	 * Retrieves competencies or key behaviours under the surveyID.
	 */
	public Vector KeyBehaviour(int pkComp) throws SQLException {
		/*
		String query = "";
		
				query =  query + "SELECT KeyBehaviour.* FROM KeyBehaviour INNER JOIN ";
				query =  query + "tblSurveyBehaviour ON ";
				query =  query + "KeyBehaviour.PKKeyBehaviour = tblSurveyBehaviour.KeyBehaviourID ";
				query =  query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID + " and CompetencyID = " + pkComp;
				query = query + " order by PKKeyBehaviour";
			
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			return rs;
			*/

		
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	try {
		String query = "";
		
		query =  query + "SELECT KeyBehaviour.* FROM KeyBehaviour INNER JOIN ";
		query =  query + "tblSurveyBehaviour ON ";
		query =  query + "KeyBehaviour.PKKeyBehaviour = tblSurveyBehaviour.KeyBehaviourID ";
		query =  query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID + " and CompetencyID = " + pkComp;
		query = query + " order by PKKeyBehaviour";
	
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				voKeyBehaviour vo=new voKeyBehaviour();
				//vo.setDescription(rs.getString("Description"));
				vo.setFKCompanyID(rs.getInt("FKCompanyID"));
				vo.setFKCompetency(rs.getInt("FKCompetency"));
				vo.setFKOrganizationID(rs.getInt("FKOrganizationID"));
				vo.setIsSystemGenerated(rs.getInt("IsSystemGenerated"));
				vo.setKBLevel(rs.getInt("KBLevel"));
				vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
				vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));
				v.add(vo);
			}
			
		} catch (SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - KeyBehaviour -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
	
			return v;
			
	}	

	/**
	 * Retrieves the survey details and stores it in an array, which will be used in all methods.
	 */
	public String [] SurveyInfo() throws SQLException {
		String [] info = new String[12];
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			String query = "SELECT tblSurvey.SurveyName, tblJobPosition.JobPosition, tblSurvey.JobFutureID, ";
			query = query + "tblSurvey.LevelOfSurvey, tblSurvey.DeadlineSubmission, tblSurvey.TimeFrameID, ";
			query = query + "tblSurvey.FKOrganization, tblAssignment.RaterCode, [User].FamilyName, ";
			query = query + "[User].GivenName, tblOrganization.NameSequence, tblSurvey.AnalysisDate ";
			query = query + "FROM tblSurvey INNER JOIN tblJobPosition ON ";
			query = query + "tblSurvey.JobPositionID = tblJobPosition.JobPositionID INNER JOIN ";
			query = query + "tblAssignment ON tblSurvey.SurveyID = tblAssignment.SurveyID INNER JOIN ";
			query = query + "[User] ON tblAssignment.TargetLoginID = [User].PKUser INNER JOIN ";
			query = query + "tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
			query = query + "WHERE tblSurvey.SurveyID = " + surveyID + " AND tblAssignment.RaterLoginID = " + raterID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
           /*           			
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			SimpleDateFormat day_view = new SimpleDateFormat ("dd MMMM yyyy");
			
			if(rs.next())
				for(int i=0; i<12; i++) {
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
				info[5] = "";
			
					
		} catch (SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - SurveyInfo -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
			return info;

	}
	
		/**
	 * Retrieve the future job description based on job position id.
	 */
	public String futureJob(int jobPostID) throws SQLException {
		String job = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String query = "Select * from tblJobPosition where JobPositionID = " + jobPostID;

		try {
			/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
		*/
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			
			if(rs.next())
				job = rs.getString("JobPosition");
		}catch(SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - futureJob -"+SE.getMessage());
		}finally{
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
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String query = "Select * from tblTimeFrame where TimeFrameID = " + timeframeID;

		try {

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				time = rs.getString("TimeFrame");
		}catch(SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - timeFrame -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
						

		return time;

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
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		try{
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
		}catch(SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - UserName -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
				
			
		return name;
	}


	/**
	 * Initialize and set all excel application and alignment.
	 */
	public void write() throws IOException, WriteException, BiffException
	{
		String output = ST.getReport_Path() + "\\" + savedFileName;
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(ST.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Questionnaire");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold);
		
		bordersData1 = new WritableCellFormat(fontFace);
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setWrap(true);
		bordersData1.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		bordersData2.setAlignment(Alignment.CENTRE);
		bordersData2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		bordersData3 = new WritableCellFormat(fontBold);
		bordersData3.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData3.setAlignment(Alignment.CENTRE);
		bordersData3.setBackground(Colour.GRAY_25);
		
		bordersData4 = new WritableCellFormat(fontFace);
		bordersData4.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData4.setAlignment(Alignment.LEFT);
		bordersData4.setWrap(true);
		bordersData4.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		/*Date timestamp = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		*/
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		
	}

	/**
	 * Writes header on excel.
	 */
	public void Header() 
		throws IOException, WriteException, SQLException, Exception
	{
				
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		String after;
		
		// Survey Level
		if(surveyLevel == 0)
			after = "Competency Level";
		else
			after = "Key Behaviour Level";
			
					
		int row_title = 0;
		label= new Label(0, row_title, "Questionnaire",cellBOLD);
		writesheet.addCell(label);
		
		row_title += 2;
		label= new Label(0, row_title, "Survey Name:",cellBOLD);
		writesheet.addCell(label); 
	
		
		label= new Label(1, row_title, surveyInfo[0],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Survey Level:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, after,cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
				
		label= new Label(0, row_title, "Job Position:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, surveyInfo[1],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Target Name:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, UserName(),cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Deadline for Submission:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, surveyInfo[4],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Rater:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, surveyInfo[7],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Future Job:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, surveyInfo[2],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;
		
		label= new Label(0, row_title, "Time Frame:",cellBOLD);
		writesheet.addCell(label); 
		
		label= new Label(1, row_title, surveyInfo[5],cellBOLD);
		writesheet.addCell(label); 
		row_title += 2;

		
		row = row_title;

	}
	
	/**
	 * Writes results on excel.
	 */
	public void printResults() throws IOException, WriteException, SQLException, Exception
	{
		int r = row;
		int c = 0;
		int totalRT = Q.getTotalSurveyRating(surveyID);
		
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		// check if comment needs to be included
		int included = Q.commentIncluded(surveyID);
		int selfIncluded = Q.SelfCommentIncluded(surveyID);
		String rCode = surveyInfo[7];
		
		
		label= new Label(c++, r, "Competency",bordersData3);
		writesheet.addCell(label); 
		writesheet.setColumnView(c-1, 22);
		
		if(surveyLevel == 1) {
			label= new Label(c++, r, "KeyBehaviour",bordersData3);
			writesheet.addCell(label);
			writesheet.setColumnView(c-1, 36);
		}
		
		int RTID [] = new int [totalRT];
		int RTStatus [] = new int [totalRT];
		
		Vector RT = SurveyRating();
		int start = 0;
		for(int i=0;i<RT.size();i++){
			votblSurveyRating vo=(votblSurveyRating)RT.elementAt(i);
			RTID[start] = vo.getRatingTaskID();
			String code = vo.getRatingCode();
			RTStatus[start] = vo.getAdminSetup();
			
			if(RTStatus[start] != 2) {			
				label= new Label(c++, r, code,bordersData3);						
				writesheet.addCell(label);			
			}
						
			start++;
		}
		/*
		while(RT.next()) {
			RTID[start] = RT.getInt("RatingTaskID");
			String code = RT.getString("RatingCode");
			RTStatus[start] = RT.getInt("AdminSetup");
			
			if(RTStatus[start] != 2) {			
				label= new Label(c++, r, code,bordersData3);						
				writesheet.addCell(label);			
			}
						
			start++;
		}
		*/
		//Changed by Ha 09/06/08
		if((rCode.equals("SELF") && selfIncluded == 1) || !(rCode.equals("SELF"))&&included == 1) {
			label= new Label(c++, r, "Comments",bordersData3);						
			writesheet.addCell(label);
			writesheet.setColumnView(c-1, 40);
		}
		
		r++;
		c=0;
		
		Vector comp = Competency();
		//while(comp.next()) {
		for(int k=0;k<comp.size();k++){
			voCompetency voComp=(voCompetency)comp.elementAt(k);
			int pkComp = voComp.getPKCompetency();
			String compName = UnicodeHelper.getUnicodeStringAmp(voComp.getCompetencyName());
			
			label= new Label(c, r, compName,bordersData1);
			writesheet.addCell(label); 
			
			if(surveyLevel == 1) {				
				Vector KB = KeyBehaviour(pkComp);
				/*
				while(KB.next()) {
										
					label= new Label(c+1, r, UnicodeHelper.getUnicodeStringAmp(KB.getString("KeyBehaviour")),bordersData1);
					writesheet.addCell(label);
					int d = c+2;
					for(int i=0; i<totalRT; i++) {					
						label= new Label(d++, r, "",bordersData2);
						writesheet.addCell(label);
					}
					if((rCode.equals("SELF") && selfIncluded == 1) || included == 1) {
						label= new Label(d, r, "",bordersData4);
						writesheet.addCell(label);
					}
					r++;					
				}
				*/
				for(int j=0;j<KB.size();j++){
					voKeyBehaviour vo=(voKeyBehaviour)KB.elementAt(j);
					
					label= new Label(c+1, r, UnicodeHelper.getUnicodeStringAmp(vo.getKeyBehaviour()),bordersData1);
					writesheet.addCell(label);
					int d = c+2;
					for(int i=0; i<totalRT; i++) {					
						label= new Label(d++, r, "",bordersData2);
						writesheet.addCell(label);
					}
					if((rCode.equals("SELF") && selfIncluded == 1) || !(rCode.equals("SELF"))&&included == 1) {
						label= new Label(d, r, "",bordersData4);
						writesheet.addCell(label);
					}
					r++;	
				}
			}else {
				int d = c+1;
				for(int i=0; i<totalRT; i++) {
					String rt = "";
					if(RTStatus[i] == 1) {
						//rt = Integer.toString(Q.RTScore(RTID[i], surveyID, pkComp));
						rt = Float.toString(Q.RTScore(RTID[i], surveyID, pkComp));
						label= new Label(d++, r, rt,bordersData2);
						writesheet.addCell(label);
					} else if(RTStatus[i] == 0) {										
						rt = "";
						label= new Label(d++, r, rt,bordersData2);
						writesheet.addCell(label);
					}					
				}
				//Changed by HA 09/06/08
				if((rCode.equals("SELF") && selfIncluded == 1) || !(rCode.equals("SELF"))&&included == 1) {
					label= new Label(d, r, "",bordersData4);
					writesheet.addCell(label);
				}
				r++;
			}
		}
	}

	/**
	 * This method is to call all other methods.
	 */
	public void Export(int surveyID, int targetID, int raterID, int pkUser, String fileName) throws SQLException, Exception, IOException, WriteException, BiffException {				
		InitializeSurvey(surveyID, targetID, raterID, fileName, 1);				
		
		write();
		Header();	
		printResults();
				
		workbook.write();
		workbook.close();
		
		String [] UserInfo = U.getUserDetail(pkUser);
		

		String temp = surveyInfo[0] + "(S); " + UserName() + "(T); " + UserName(Integer.parseInt(surveyInfo[10]), raterID) + "(R)";
		try {
			EV.addRecord("Finish Export Questionnaire", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
		}catch(SQLException SE) {}		
	}
	
/************************************************ IMPORT ******************************************************/
	/**
	 * Initialize the excel workbook.
	 */
	public void InitializeExcel(int type) throws IOException, WriteException, BiffException {							
		if(type == 1) //export
			defaultPath = ST.getReport_Path() + "\\";			
		else
			defaultPath = "";
			
		inputWorkbook = new File(defaultPath + savedFileName);
		
		ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		
		w1 = Workbook.getWorkbook(inputWorkbook);
	}


	public Vector QuestionnaireInfo() throws SQLException {
		String query = "";
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		
		if(surveyLevel == 0) {
		
			query = query + "SELECT tblSurveyCompetency.CompetencyID, Competency.CompetencyName, ";
			query = query + "tblSurveyRating.RatingTaskID, tblRatingTask.RatingCode, tblSurveyRating.AdminSetup";
			
			//Added 2 addition columns to retrieve the HideNA and ScaleRange value, Sebastian 29 July 2010
			query = query + ", tblSurvey.HideNA, tblScale.ScaleRange ";
			query = query + "FROM Competency INNER JOIN tblSurveyCompetency ON ";
			query = query + "Competency.PKCompetency = tblSurveyCompetency.CompetencyID AND ";
			query = query + "Competency.PKCompetency = tblSurveyCompetency.CompetencyID INNER JOIN ";
			query = query + "tblSurveyRating ON tblSurveyCompetency.SurveyID = tblSurveyRating.SurveyID INNER JOIN ";
			query = query + "tblRatingTask ON tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID ";
			
			//Added 2 addition join table, tblSurvey and tblScale to retrieve the HideNA and ScaleRange value, Sebastian 29 July 2010
			query = query + "INNER JOIN tblSurvey ON tblSurvey.SurveyID = tblSurveyRating.SurveyID ";
			query = query + "INNER JOIN tblScale ON tblScale.ScaleID = tblSurveyRating.ScaleID ";
			query = query + "WHERE tblSurveyCompetency.SurveyID = " + surveyID;
			query = query + " ORDER BY Competency.CompetencyName, tblSurveyRating.RatingTaskID";
			
		} else {
			
			query = query + "SELECT tblSurveyBehaviour.CompetencyID, Competency.CompetencyName, ";
			query = query + "KeyBehaviour.PKKeyBehaviour, KeyBehaviour.KeyBehaviour, tblSurveyRating.RatingTaskID, ";
			query = query + "tblRatingTask.RatingCode, tblSurveyRating.AdminSetup";
			
			//Added 2 addition columns to retrieve the HideNA and ScaleRange value, Sebastian 29 July 2010
			query = query + ", tblSurvey.HideNA, tblScale.ScaleRange ";
			query = query + "FROM tblSurveyRating INNER JOIN tblRatingTask ON tblSurveyRating.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query + "Competency INNER JOIN KeyBehaviour ON ";
			query = query + "Competency.PKCompetency = KeyBehaviour.FKCompetency INNER JOIN ";
			query = query + "tblSurveyBehaviour ON Competency.PKCompetency = tblSurveyBehaviour.CompetencyID AND ";
			query = query + "Competency.PKCompetency = tblSurveyBehaviour.CompetencyID AND ";
			query = query + "KeyBehaviour.PKKeyBehaviour = tblSurveyBehaviour.KeyBehaviourID ON ";
			query = query + "tblSurveyRating.SurveyID = tblSurveyBehaviour.SurveyID ";
			
			//Added 2 addition join table, tblSurvey and tblScale to retrieve the HideNA and ScaleRange value, Sebastian 29 July 2010
			query = query + "INNER JOIN tblSurvey ON tblSurvey.SurveyID = tblSurveyRating.SurveyID ";
			query = query + "INNER JOIN tblScale ON tblScale.ScaleID = tblSurveyRating.ScaleID ";
			query = query + "WHERE tblSurveyBehaviour.SurveyID = " + surveyID;
			query = query + " ORDER BY Competency.PKCompetency, PKKeyBehaviour, tblRatingTask.RatingCode";
		}
		/*
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			return rs;
			*/
		
		Vector v=new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


	try {
		
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				if(surveyLevel==0){
				//tblSurveyCompetency.CompetencyID, Competency.CompetencyName, ";
				//tblSurveyRating.RatingTaskID, tblRatingTask.RatingCode, tblSurveyRating.AdminSetup ";
					
					votblSurveyRating vo=new votblSurveyRating();
					vo.setAdminSetup(rs.getInt("AdminSetup"));
					vo.setRatingCode(rs.getString("RatingCode"));
					vo.setRatingTaskID(rs.getInt("RatingTaskID"));
					vo.setCompetencyID(rs.getInt("CompetencyID"));
					vo.setCompetencyName(rs.getString("CompetencyName"));
					
					//Capture the HideNA and Scale Range value of the competency survey. To determine the appropiate validation of rater's input. e.g if rater cannot put NA or empty for rater, validation is carried out to ensure rater inputted a value, Sebastian 29 July 2010
					vo.setSHideNA(rs.getInt("HideNA"));
					vo.setScaleRange(rs.getInt("ScaleRange"));
					
					v.add(vo);
				}else{
					//query = query + "SELECT tblSurveyBehaviour.CompetencyID, Competency.CompetencyName, ";
					//query = query + "KeyBehaviour.PKKeyBehaviour, KeyBehaviour.KeyBehaviour, ";
					votblSurveyRating vo=new votblSurveyRating();
					vo.setAdminSetup(rs.getInt("AdminSetup"));
					vo.setRatingCode(rs.getString("RatingCode"));
					vo.setRatingTaskID(rs.getInt("RatingTaskID"));
					vo.setCompetencyID(rs.getInt("CompetencyID"));
					vo.setPKKeyBehaviour(rs.getInt("PKKeyBehaviour"));
					vo.setKeyBehaviour(rs.getString("KeyBehaviour"));
					vo.setCompetencyName(rs.getString("CompetencyName"));

					//Capture the HideNA and Scale Range value of the Key Behaviour survey. To determine the appropiate validation of rater's input. e.g if rater cannot put NA or empty for rater, validation is carried out to ensure rater inputted a value, Sebastian 29 July 2010
					vo.setSHideNA(rs.getInt("HideNA"));
					vo.setScaleRange(rs.getInt("ScaleRange"));
					
					v.add(vo);
				}
				
			}
			
		} catch (SQLException SE) {
			System.err.println(" ExportQuestionnaire.java - QuestionnaireInfo -"+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
	
			return v;
	
	}

	public boolean ReadExcel(int rewrite) throws IOException, WriteException, BiffException, Exception {							
		Sheet [] Sh = w1.getSheets();
		int surveyLevel = Integer.parseInt(surveyInfo[3]);
		int row = 0;
		// check if comment needs to be included
		int included = Q.commentIncluded(surveyID);
		int selfIncluded = Q.SelfCommentIncluded(surveyID);
		String rCode = surveyInfo[7];


		try {
			Vector info = QuestionnaireInfo();
			//while(rs.next()) {

			for(int i=0;i<info.size();i++){
				//System.out.println("start");

				votblSurveyRating vo=(votblSurveyRating)info.elementAt(i);

				String comp = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());

				int pkKB = 0;
				String KB = "";
				String RT = vo.getRatingCode();

				if(surveyLevel == 1) {
					votblSurveyRating voSR=(votblSurveyRating)info.elementAt(i);
					pkKB = voSR.getPKKeyBehaviour();
					KB = UnicodeHelper.getUnicodeStringAmp(voSR.getKeyBehaviour());
				}														

				/*
				* Change(s) : Modified codes to add validation of input ratings using variable hideNA and scaleRange (Check if the rating is out of scale range or 0 is a valid rating)
				* Reason(s) : To implement validation of rater's input
				* Updated By: Sebastian
				* Updated On: 29 July 2010
				*/
				int hideNa = vo.getSHideNA(); //0 (survey allow NA rating or 0). 1 (survey don't allow NA rating or 0) 
				int scaleRange = vo.getScaleRange(); //if scale range of 10 (i.e rating can be from up to 10)

				Cell compName = Sh[0].findCell(comp);
				Cell KBName = Sh[0].findCell(KB);

				Cell RTName = Sh[0].findCell(RT);

				if(surveyLevel == 0)
					row = compName.getRow();
				else
					row = KBName.getRow();

				int r = row;

				int c = RTName.getColumn();

				System.out.println("c " + row + "--" + c);

				Cell content = Sh[0].getCell(c, r);

				int input = -1;

				//validate if value inputed is a number
				if(!content.getContents().equals(""))
				{
					try
					{
						input = Integer.parseInt(content.getContents());
					}
					catch (NumberFormatException e)
					{
						this.setValErrMsg("Input Ratings must be a number");
						return false;
					}
				}

				if(input >= 0) { //if input is a number bigger than -1, continue with validation
					if (hideNa == 1) //survey dont allow rating of 0
					{
						if (input == 0)
						{
							this.setValErrMsg("Survey is unable to accept input ratings that are 0");
							return false;
						}
					}
					
					if (input > scaleRange) //input rating is larger than the scale range
					{
						this.setValErrMsg("Survey is unable to accept input ratings that are over the max scale rating of " + scaleRange);
						return false;
					}
				}
				else { //delete
					this.setValErrMsg("Survey is unable to accept input ratings that are less than 0 or empty");
					return false;
				}
			}
			//End Validate input rating

			for(int i=0;i<info.size();i++){
				//System.out.println("start");

				votblSurveyRating vo=(votblSurveyRating)info.elementAt(i);

				int pkComp = vo.getCompetencyID();
				String comp = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());

				int pkKB = 0;
				String KB = "";
				if(surveyLevel == 1) {
					votblSurveyRating voSR=(votblSurveyRating)info.elementAt(i);
					pkKB = voSR.getPKKeyBehaviour();
					KB = UnicodeHelper.getUnicodeStringAmp(voSR.getKeyBehaviour());
				}

				//System.out.println(comp + "--" + KB);

				int RTID = vo.getRatingTaskID();
				String RT = vo.getRatingCode();
				int RTStatus = vo.getAdminSetup();

				if(RTStatus != 0) 
				{
					float oldResult = Q.CheckOldResultExist(assignmentID, pkComp, RTID);

					if(oldResult < 0) {						
						float score = Q.RTScore(RTID, surveyID, pkComp);

						if(surveyLevel == 0) {
							System.out.println("d " + pkComp + "--" +  RTID);
							try {										
								Q.addResult(assignmentID, pkComp, RTID, score); 
							}catch(SQLException SE) {}
						}else{
							try {			
								Q.addResult(assignmentID, pkKB, RTID, score); 
							}catch(SQLException SE) {}
						}		
					}
				} else {

					Cell compName = Sh[0].findCell(comp);
					Cell KBName = Sh[0].findCell(KB);

					if(surveyLevel == 1) {
						row = compName.getRow();
						//String strKB = "";
						Cell KBCell = Sh[0].getCell(1, row);
						//boolean valid = false;

						//while(valid == false) {
						//strKB = KBCell.getContents();

						//if(strKB.equals(KB))
						//valid = true;
						//else {							
						//	row++;
						KBCell = Sh[0].getCell(1, row);
						//System.out.println("row =  " + row);
						//}

						//}
					}


					Cell RTName = Sh[0].findCell(RT);

					if(surveyLevel == 0)
						row = compName.getRow();
					else
						row = KBName.getRow();

					int r = row;

					int c = RTName.getColumn();

					System.out.println("c " + row + "--" + c);

					Cell content = Sh[0].getCell(c, r);

					int input = -1;

					if(!content.getContents().equals(""))
						input = Integer.parseInt(content.getContents());
					//System.out.println(">>input = " + input);
					float oldResult = -1;

					if(surveyLevel == 0)
						oldResult = Q.CheckOldResultExist(assignmentID, pkComp, RTID);
					else							
						oldResult = Q.CheckOldResultExist(assignmentID, pkKB, RTID);

					if(input >= 0) {	

						if(oldResult < 0) {

							try {
								if(surveyLevel == 0){
									try{								
										Q.addResult(assignmentID, pkComp, RTID, input); 
									}catch(SQLException SE) { System.out.println(SE.getMessage());}

								}else {
									try {								
										Q.addResult(assignmentID, pkKB, RTID, input);
									}catch(SQLException SE) {}
								}
							}catch(SQLException SE) {}
							catch(Exception E) {}
						}				
						else if(oldResult >= 0 && oldResult != input) {
							System.out.println("ff " + oldResult + "--" + input);

							if(rewrite == 1) {	
								System.out.println("gg " + oldResult + "--" + input);						
								if(surveyLevel == 0) 								
									Q.updateOldResult(assignmentID, pkComp, RTID, input);					
								else
									Q.updateOldResult(assignmentID, pkKB, RTID, input);								
							}
						}
					}	else { //delete

						if(rewrite == 1) {
							if(surveyLevel == 0) {
								try{
									Q.deleteResult(assignmentID, pkComp, RTID);
								}catch(SQLException SE) {}
							}else{
								try{
									Q.deleteResult(assignmentID, pkKB, RTID);
								}catch(SQLException SE) {}
							}
						}
					}				
				}

				// Changed by Ha 02 June 08 re-edit by 09 June by Ha
				// comments part
				if((rCode.equals("SELF") && selfIncluded == 1 /*&& included == 1*/) || !(rCode.equals("SELF"))&&included == 1) {


					Cell Comment = Sh[0].findCell("Comments");

					int comCol = Comment.getColumn();

					Cell comInput = Sh[0].getCell(comCol, row);
					String strCom = UnicodeHelper.getUnicodeStringAmp(comInput.getContents());
					String oldResult = Q.checkCommentExist(assignmentID, pkComp, pkKB);

					// Added by DeZ, 16.07.08, To identify problem where Import Questionaires gives blank narrative comments even though data is available
					//System.out.println(">>strCom = " + strCom);
					//System.out.println(">>rewrite = " + rewrite);
					//System.out.println(">>oldResult = " + oldResult);

					if(strCom.equals("")) {
						//System.out.println(">>strCom is empty so delete the comment");
						if(rewrite == 1) {
							try{
								Q.deleteComment(assignmentID, pkComp, pkKB);
							}catch(SQLException SE) {System.out.println("ExportQuestionaire.java " +SE.getMessage());}
						}
					}else {
						// Changed by Ha 30/05/08: replace condition equals("") with == null
						if(oldResult==null) {							
							//System.out.println(">>No old results, adding comment");
							Q.addComment(assignmentID, pkComp, pkKB, strCom); 

						}								
						else if(oldResult != null && !oldResult.equals(strCom)) {

							if(rewrite == 1) {					
								//System.out.println(">>Updating comment");							
								Q.updateComment(assignmentID, pkComp, pkKB, strCom);
							}
						}
					}// end if strCom not null

				}// end if comment not null

				row++;

				System.out.println(row);

			}			
		} catch(SQLException SE){System.out.println("Error");}		
		return true;
	}
	
	/**
	 * This method is to call all other methods.
	 */
	public boolean Import(int surveyID, int targetID, int raterID, int pkUser, String fileName, int isOverwrite) throws SQLException, Exception, IOException, WriteException, BiffException {				
		
		boolean result = false; //return true if inputs in the questionnaire are captured successfully otherwise return false, Sebastian 29 July 2010
		
		try
		{
		InitializeSurvey(surveyID, targetID, raterID, fileName, isOverwrite);}
		catch (Exception e){System.out.println("Error 1");}
		try
		{
		InitializeExcel(2);} catch (Exception ex) {System.out.println("Error 2");}
		//Savepoint SP = db.setSavePoint("Read Excel");
		try {
			result = ReadExcel(isOverwrite);
		} catch(Exception E) {
			System.out.println("Error Importing - Input not completed");
			System.out.println(E.getMessage());
			return false;
			//db.setRollBack(SP);
		}finally{
			w1.close();
		}
		
/*		int totalRatingTask = Q.getTotalSurveyRating(surveyID);
		int totalAll = Q.TotalList(surveyID);
		int totalCompleted = Q.TotalResult(assignmentID);
		if(totalCompleted == (totalAll * totalRatingTask)) {
			Q.SetRaterStatus(assignmentID, 1);
			S.Calculate(assignmentID, 0);		// calculation part, 0=not include/exclude rater
		}
*/		
		String [] UserInfo = U.getUserDetail(pkUser);
		

		String temp = surveyInfo[0] + "(S); " + UserName() + "(T); " + UserName(Integer.parseInt(surveyInfo[10]), raterID) + "(R)";
		try {
			EV.addRecord("Finish Import Questionnaire", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
		}catch(SQLException SE) {}
				
		return result;
	}	

	public static void main (String [] args)throws SQLException, Exception {
		int surveyID = 445;//438;//431;
		int targetID = 6408;//3495;//112;
		int raterID = 6408;//3495;//112;
		//int assignmentID = 411;
		ExportQuestionnaire EQ = new ExportQuestionnaire();
		EQ.Export(surveyID, targetID, raterID, 6408, "\\Questionnaire.xls");
		//EQ.Import(surveyID, targetID, raterID, 5, "\\Questionnaire(1).xls", 1);
	}

	/**
	 * To set the error msg if there is a validation error or capturing error of the import questionnaire
	 * @param valErrMsg - Specify the error msg to display
	 * @author Sebastian 
     * @since v.1.3.12.87 (29 July 2010)
	*/
	public void setValErrMsg(String valErrMsg) {
		this.valErrMsg = valErrMsg;
	}

	/**
	 * To retrieve the error msg if there is a validation error or capturing error of the import questionnaire
	 * @return String - return the error msg sett
	 * @author Sebastian 
     * @since v.1.3.12.87 (29 July 2010)
	*/
	public String getValErrMsg() {
		return valErrMsg;
	}	
}