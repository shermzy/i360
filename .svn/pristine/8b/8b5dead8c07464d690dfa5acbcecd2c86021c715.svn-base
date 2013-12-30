package CP_Classes;

import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.votblSurvey;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;

public class Report_DeleteSurvey
{
	private Database db;
	private Setting server;
	private User user;
	private Create_Edit_Survey CE_Survey;
	
	private Label label;
	private WritableSheet writesheet, writesheet2;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace,fontTitle ;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border,cellBOLD_Title;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold;
	private File outputWorkBook, inputWorkBook;
	
	public Report_DeleteSurvey()
	{
		db = new Database();
		server = new Setting();
		user = new User();
		CE_Survey = new Create_Edit_Survey();

	}
	
	public void write(String SurveyName) throws IOException, WriteException, BiffException
	{
		String output = server.getDeleted_Path()+"\\Survey_"+SurveyName+".xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Details of Survey: " + SurveyName);
		writesheet2 = workbook.getSheet(1);
		writesheet2.setName("List of Targets/Raters");
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
			
	}
	
	public void fontInit()throws IOException, WriteException
	{
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		fontTitle = new WritableFont(WritableFont.TIMES, 13, WritableFont.BOLD); 
		
		cellBOLD_Title = new WritableCellFormat(fontTitle);
		cellBOLD = new WritableCellFormat(fontBold); 
		
		cellBOLD_Border = new WritableCellFormat(fontBold); 
		cellBOLD_Border.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_Border.setAlignment(Alignment.CENTRE);
		cellBOLD_Border.setWrap(true);
		
		bordersData1 = new WritableCellFormat(fontFace);
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.CENTRE);
					
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
				
		No_Borders_ctrAll = new WritableCellFormat(fontFace);
		No_Borders_ctrAll.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_ctrAll.setAlignment(Alignment.CENTRE);
		
		No_Borders_ctrAll_Bold = new WritableCellFormat(fontBold);
		No_Borders_ctrAll_Bold.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_ctrAll_Bold.setAlignment(Alignment.CENTRE);
		
		No_Borders = new WritableCellFormat(fontBold);
		No_Borders.setBorder(Border.NONE, BorderLineStyle.NONE);
		
		No_Borders_NoBold = new WritableCellFormat(fontFace);
		No_Borders_NoBold.setBorder(Border.NONE, BorderLineStyle.NONE);
		
	}
	
	public String DeleteReport(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
		String Survey_Name="";
		
		votblSurvey rs_SurveyDetail = CE_Survey.getSurveyDetail(SurveyID);
		
		Survey_Name = rs_SurveyDetail.getSurveyName();
		
		String filename = "Survey_"+Survey_Name+".xls";
		
		write(Survey_Name);
		fontInit();
		SurveyDetails(SurveyID);
		
		workbook.write();
		workbook.close(); 
		
		return filename;

	}
		
	public void SurveyDetails(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
		int row = 0;
		int NA_Included=0;
		int LevelofSurvey =0;
		int db_SurveyStatus=0;
		int ReliabilityCheck =0;
		int JobFutureID=0;
		int TimeFrameID=0;
		String Survey_Name="";
		
		String DateOpened = "NA";
		String DeadlineDate = "NA";
		String AnalysisDate = "NA";
		String SurveyStatus = "";
		String OrgName ="";
		String CompanyName ="";
		String Reliability="";
		String strLevelofSurvey="";
		String strNA_Included="";
		String strJobFuture="";
		String strTimeFrame="";
		
		SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
		
		votblSurvey vo = CE_Survey.getSurveyDetail(SurveyID);
		
		Survey_Name = vo.getSurveyName();
		DateOpened = vo.getDateOpened();
		LevelofSurvey = vo.getLevelOfSurvey();
		DeadlineDate = vo.getDeadlineSubmission();
		db_SurveyStatus = vo.getSurveyStatus();
		AnalysisDate = vo.getAnalysisDate();	
		JobFutureID = vo.getJobFutureID();
		TimeFrameID = vo.getTimeFrameID();
		ReliabilityCheck = vo.getReliabilityCheck();
		NA_Included = vo.getNA_Included();
		OrgName = vo.getOrganizationName();	
		CompanyName = vo.getCompanyName();
		
		if(db_SurveyStatus == 1)
			SurveyStatus="Open";
		else if(db_SurveyStatus == 2)
			SurveyStatus="Closed";
		else if(db_SurveyStatus == 3)
			SurveyStatus="Not Commissioned";
		
		if(ReliabilityCheck == 0)
			Reliability = "Trimmed Mean";
		else if(ReliabilityCheck == 1)
			Reliability = "Reliability Index";
		
		if(LevelofSurvey == 0)
			strLevelofSurvey = "Competency Level";
		else if(LevelofSurvey == 1)
			strLevelofSurvey = "Key Behaviour Level";
		
		
		if(NA_Included == 1)
			strNA_Included = "Yes";
		else if(NA_Included == 0)
			strNA_Included = "No";
			
		if(AnalysisDate == null)
			AnalysisDate = "NA";
		
		writesheet.setColumnView(1,25);
		writesheet.setColumnView(0,6);
		
		Label label= new Label(0, row, "REPORT ON DELETED SURVEY",cellBOLD_Title);
		writesheet.addCell(label); 

		
		label= new Label(0, row+2, "Company Name:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+2, 1, row+2);
		
		label= new Label(2, row+2, CompanyName,No_Borders_NoBold);
		writesheet.addCell(label); 
		
		label= new Label(0, row+3, "Organization Name:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+4, 1, row+4);
		
		label= new Label(2, row+3 , OrgName ,No_Borders_NoBold);
		writesheet.addCell(label); 
		
		label= new Label(0, row+6, "SURVEY DETAILS",cellBOLD_Title);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+6, 1, row+6);
		
		row = row+8;
		
		label= new Label(0, row, "Survey Name:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row, 1, row);
		
		label= new Label(2, row, Survey_Name ,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+1, "Survey Status:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+1, 1, row+1);
		
		label= new Label(2, row+1, SurveyStatus,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+2, "Reliability:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+2, 1, row+2);
		
		label= new Label(2, row+2, Reliability,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+3, "Level Of Survey:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+3, 1, row+3);
		
		label= new Label(2, row+3, strLevelofSurvey,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+4, "NA Included into Calculation:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+4, 1, row+4);
		
		label= new Label(2, row+4, strNA_Included,No_Borders_NoBold);
		writesheet.addCell(label);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String command = "SELECT * FROM tblJobPosition WHERE JobPositionID="+JobFutureID;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

			if(rs.next())
				strJobFuture = rs.getString("JobPosition");
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - SurveyDetails - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }	

		label= new Label(0, row+5, "Future job:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+5, 1, row+5);
		
		label= new Label(2, row+5, strJobFuture,No_Borders_NoBold);
		writesheet.addCell(label);
		
		String command1 = "SELECT * FROM tblTimeFrame WHERE TimeFrameID="+TimeFrameID;
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command1);

			if(rs.next())
				strTimeFrame = rs.getString("TimeFrame");
			
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - SurveyDetails - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }		
		label= new Label(0, row+6, "Time Frame:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+6, 1, row+6);
		
		label= new Label(2, row+6, strTimeFrame,No_Borders_NoBold);
		writesheet.addCell(label);
		
		/*-----------------------------------Dates------------------------------------------------------*/
		
		row  = row +8;

		label= new Label(0, row+1, "Opened Date:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+1, 1, row+1);
		
		label= new Label(2, row+1, DateOpened,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+2, "Deadline:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+2, 1, row+2);
		
		label= new Label(2, row+2, DeadlineDate,No_Borders_NoBold);
		writesheet.addCell(label);
		
		label= new Label(0, row+3, "Analysis Date:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row+3, 1, row+3);
		
		label= new Label(2, row+3, AnalysisDate ,No_Borders_NoBold);
		writesheet.addCell(label);
		
		/*-------------------------------------------------Demographic---------------------------------------*/
		
		row  = row +5;
		
		label= new Label(0, row, "DEMOGRAPHICS OPTION",cellBOLD_Title);
		writesheet.addCell(label); 
		
		row  = row +2;
		String command_Demo = "SELECT * FROM tblSurveyDemos a, tblDemographicSelection b";
		command_Demo = command_Demo+" WHERE a.DemographicID = b.DemographicID AND a.SurveyID = "+SurveyID;
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_Demo);

			while(rs.next())
			{
				String DemoName = rs.getString("DemographicName");
				
				label= new Label(1, row, "-  "+DemoName ,No_Borders_NoBold);
				writesheet.addCell(label);
				row++;
			}
			
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - SurveyDetails - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }		
		
		row  = row +2;
		
		label= new Label(0, row, "RATING TASK",cellBOLD_Title);
		writesheet.addCell(label); 
		
		row  = row +2;
		String command_Rating = "SELECT a.RatingTaskID, a.RatingTaskName, c.ScaleDescription";
		command_Rating = command_Rating + " FROM tblSurveyRating a, tblRatingTask b, tblScale c";
		command_Rating = command_Rating + " WHERE a.RatingTaskID = b.RatingTaskID AND a.ScaleID = c.ScaleID AND SurveyID="+SurveyID;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_Rating);

			while(rs.next())
			{
				String RatName = rs.getString("RatingTaskName");
				String ScaleDesc = rs.getString("ScaleDescription");
				
				label= new Label(0, row, RatName,cellBOLD);
				writesheet.addCell(label);
				
				label= new Label(1, row+1, ScaleDesc,No_Borders_NoBold);
				writesheet.addCell(label);
				
				row = row+3;
			}
			
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - SurveyDetails - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }		
	
		row = row+1;
		writesheet.addRowPageBreak(row);
		
		label= new Label(0, row, "COMPETENCY LIST",cellBOLD_Title);
		writesheet.addCell(label); 
		
		row = row+2;
		
		if(LevelofSurvey ==0)
			row = AllCompetencies(SurveyID,1,row);
		else if(LevelofSurvey ==1)
			row = AllCompetencies_KeyBehav(SurveyID,row);
			
		int drow = AllRaters(SurveyID);
		drow = AllDeletedRaters(SurveyID,drow);
		
		
	}
	
		
	public int AllCompetencies(int SurveyID, int Definition, int xrow) 
		throws IOException, WriteException, SQLException, Exception
	{
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		 
		/* 	Definition 0:	List All Competencies
		 * 	Definition 1:	List All Competencies with Definitions
		 */
	
			int row_data = xrow;
			
			label= new Label(0, row_data, "S/No.",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			writesheet.setRowView(row_data,25);			
			
			label= new Label(1, row_data, "Competency Name",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(2,25);
			writesheet.mergeCells(1, row_data, 2, row_data);
			
			int col = 2;
			
			if(Definition == 1)
			{
				label= new Label(col+1, row_data, "Definition",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(col+1,35);
				writesheet.setColumnView(col+2,35);
				writesheet.mergeCells(col+1, row_data, col+2, row_data);
				col = col+2;
			}
			 
			
			label= new Label(col+1, row_data, "System Generated",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(col+1,12);
			
			int row = row_data+1;
			int no_Records = 1;
			String SysGen =" ";
					
			String Sql = "SELECT * FROM Competency a INNER JOIN tblSurveyCompetency b ON a.PKCompetency = b.CompetencyID";
			Sql = Sql +" WHERE (b.SurveyID = "+SurveyID+") AND (a.IsSystemGenerated = 1) OR (b.SurveyID = "+SurveyID+") AND (a.IsSystemGenerated = 0) ";
			Sql = Sql +" ORDER BY isSystemGenerated, CompetencyName";
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			try
	        {          

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(Sql);

				while(rs.next())	
				{
					String str_no_Records = String.valueOf(no_Records);
					String compName = rs.getString("CompetencyName");
					String compDef = rs.getString("CompetencyDefinition");
					int isSystemGenerated = rs.getInt("isSystemGenerated");
					
					if(isSystemGenerated == 1)
						SysGen = "Yes";
					else
						SysGen = "No";				
					
					
					label = new Label(0,row, str_no_Records,bordersData1);
					writesheet.addCell(label);
					
					label = new Label(1,row, compName,bordersData2);
					writesheet.addCell(label);
					writesheet.mergeCells(1, row, 2, row);
									
					int db_col = 2;
									
					if(Definition == 1)
					{
						label= new Label(db_col+1, row, compDef,bordersData2);
						writesheet.addCell(label);
						writesheet.setColumnView(2,35);
						writesheet.setRowView(row,26);
						writesheet.mergeCells(db_col+1, row, db_col+2, row);
						db_col = db_col+2;
					}
					
					
					label = new Label(db_col+1,row, SysGen, bordersData1);
					writesheet.addCell(label);
					
					no_Records++;
					row++;
					
				}
	        }
	        catch(Exception E) 
	        {
	            System.err.println("Report_DeletedSurvey.java - AllCompetencies - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }	
				
			writesheet.addRowPageBreak(row+1);
			
			return row+2;						
	}
	
	public int AllCompetencies_KeyBehav(int SurveyID, int xrow) 
		throws IOException, WriteException, SQLException, Exception
	{
		
		int row = xrow;
		
		int CompID = 0;
		int no_Records = 1;
		int no_Records_key = 1;
		String SysGen =" ";
		int col = 6;
		Vector v = new Vector();
		
		writesheet.setColumnView(0,6);
		//writesheet.setColumnView(1,12);			

		writesheet.setColumnView(3,100);
				
		label= new Label(col, row, "System Generated",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(col,12);
		
		row = xrow +2;
		
		String Sql = "SELECT * FROM Competency a INNER JOIN tblSurveyCompetency b ON a.PKCompetency = b.CompetencyID";
		Sql = Sql +" WHERE (b.SurveyID = "+SurveyID+") AND (a.IsSystemGenerated = 1) OR (b.SurveyID = "+SurveyID+") AND (a.IsSystemGenerated = 0) ";
		Sql = Sql +" ORDER BY isSystemGenerated, CompetencyName";
		
						
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(Sql);

			while(rs.next())	
			{		
				CompID = rs.getInt("PKCompetency");
				
				String compName = rs.getString("CompetencyName");
				String compDef = rs.getString("CompetencyDefinition");
				int isSystemGenerated = rs.getInt("isSystemGenerated");
				
				voCompetency vo = new voCompetency();
				vo.setCompetencyID(CompID);
				vo.setCompetencyName(compName);
				vo.setCompetencyDefinition(compDef);
				vo.setIsSystemGenerated(isSystemGenerated);
				
				v.add(vo);
			}
			
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - AllCompetencies_KeyBehav - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }	
        
        for(int i=0; i<v.size(); i++) {
        	voCompetency vo = (voCompetency)v.elementAt(i);
        	
        	CompID = vo.getCompetencyID();
			
			String compName = vo.getCompetencyName();
			String compDef = vo.getCompetencyDefinition();
			int isSystemGenerated = vo.getIsSystemGenerated();
			
        	String str_no_Records = String.valueOf(no_Records);
        	
			label = new Label(0,row, str_no_Records,No_Borders_ctrAll_Bold);
			writesheet.addCell(label);
			
			label = new Label(1,row, compName,No_Borders);
			writesheet.addCell(label);
			
										
			int db_col = 2;
							
			label= new Label(db_col, row+1, compDef, No_Borders);
			writesheet.addCell(label);
			
			if(isSystemGenerated == 1)
				SysGen = "Yes";
			else
				SysGen = "No";
			
			db_col = db_col+3;
			
			label = new Label(db_col+1,row, SysGen, No_Borders);
			writesheet.addCell(label);
			
			db_col = db_col+1;
			row = row+2;
	
	
			String Sql_KeyBehav = "SELECT * FROM KeyBehaviour WHERE FKCompetency ="+CompID+" ORDER BY KeyBehaviour";

			try
	        {          
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(Sql_KeyBehav);

				while(rs.next())	
				{	
					String str_no_Records_key = String.valueOf(no_Records_key);
					String keyBehavName = rs.getString("KeyBehaviour");
					int isSystemGenerated_key = rs.getInt("isSystemGenerated");
			
					if(isSystemGenerated_key == 1)
					SysGen = "Yes";
					else
					SysGen = "No";
					
					label = new Label(0,row, str_no_Records_key,No_Borders_ctrAll);
					writesheet.addCell(label);
					
					label = new Label(3,row, keyBehavName,No_Borders_NoBold);
					writesheet.addCell(label);
								
					label = new Label(db_col,row, SysGen, No_Borders_NoBold);
					writesheet.addCell(label);
					
					
					no_Records_key++;
					row++;
					
				}	
				
	        }
	        catch(Exception E) 
	        {
	            System.err.println("Report_DeletedSurvey.java - AllCompetencies_KeyBehav - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }	
			
			
						
			no_Records++;
			row++;
		}
       
		
		
		writesheet.addRowPageBreak(row+1);
		
		return row+2;
	}	
	
	public int AllRaters(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
			int row_data = 3;
			
			Label label = new Label(0, 0, "LIST OF TARGETS AND RATERS",cellBOLD_Title); 
			writesheet2.addCell(label); 
			writesheet2.mergeCells(0, 0, 2, 0);
			
			label= new Label(0, row_data-1, "Target",cellBOLD_Border);
			writesheet2.addCell(label); 
			writesheet2.mergeCells(0, row_data-1, 3, row_data-1);
			
			label= new Label(4, row_data-1, "Rater",cellBOLD_Border);
			writesheet2.addCell(label); 
			writesheet2.mergeCells(4, row_data-1, 9, row_data-1);
			
			/* ---------------------------------------------START: Target-------------------------------------*/
			
			label= new Label(0, row_data, "Department",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(0,15);
			
			label= new Label(1, row_data, "Other Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(1,13);
			
			label= new Label(2, row_data, "Family Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(2,13);

			label= new Label(3, row_data, "Login Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(3,13);
			
			/* ---------------------------------------------START: Rater-------------------------------------*/
			
			
			
			label= new Label(4, row_data, "Department",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(4,13);
			
			label= new Label(5, row_data, "Other Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(5,13);
			
			label= new Label(6, row_data, "Family Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(6,13);
			
			label= new Label(7, row_data, "Rater Relation",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(7,13);
			
			label= new Label(8, row_data, "Login Name",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(8,13);
			
			label= new Label(9, row_data, "Rater Status",cellBOLD_Border);
			writesheet2.addCell(label);
			writesheet2.setColumnView(9,13);			
			
			int row = row_data+1;
			int no_Records = 1;
			
			String Relation=" ";
			String Status=" ";
			String TargetDetail[] = new String[14];
			String RaterDetail[] = new String[14];
					
			String Sql = "SELECT * FROM tblSurvey a INNER JOIN tblAssignment ON a.SurveyID = tblAssignment.SurveyID INNER JOIN";
            Sql = Sql + " tblOrganization ON a.FKOrganization = tblOrganization.PKOrganization INNER JOIN";
            Sql = Sql + " [User] ON tblOrganization.PKOrganization = [User].FKOrganization AND tblAssignment.RaterLoginID = [User].PKUser";
			Sql = Sql + " WHERE (a.SurveyID = "+SurveyID+")";
			Sql = Sql + " ORDER BY TargetLoginID";			
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			try
	        {          
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(Sql);

				while(rs.next())	
				{
					String RaterCode = rs.getString("RaterCode");
					int RaterStatus = rs.getInt("RaterStatus");
					int RaterLoginID = rs.getInt("RaterLoginID");
					int TargetLoginID = rs.getInt("TargetLoginID");
					int NameSequence = rs.getInt("NameSequence");
					
					if(RaterCode.equals("SUP"))
						Relation = "Superior";
					else if(RaterCode.equals("SELF"))
						Relation = "Self";
					else if(RaterCode.equals("OTH"))
						Relation = "Others";
						
					if(RaterStatus == 0)
						Status = "Incomplete";
					if(RaterStatus == 1)
						Status = "Completed";	
					if(RaterStatus == 2)
						Status = "Unreliable";				
					
					TargetDetail = user.getUserDetail(TargetLoginID, NameSequence);
					RaterDetail = user.getUserDetail(RaterLoginID, NameSequence);

	/* -------------------------------------------------------START: Target----------------------------------------------*/									
					label = new Label(0,row, TargetDetail[6],bordersData2);
					writesheet2.addCell(label);
					
					label = new Label(1,row, TargetDetail[1],bordersData2);
					writesheet2.addCell(label);
									
					label= new Label(2, row, TargetDetail[0] ,bordersData1);
					writesheet2.addCell(label);
					
					label= new Label(3, row, TargetDetail[2],bordersData2);
					writesheet2.addCell(label);

	/* -------------------------------------------------------START: Rater----------------------------------------------*/													
					label= new Label(4, row, RaterDetail[6],bordersData2);
					writesheet2.addCell(label);
					
					label= new Label(5, row, RaterDetail[1],bordersData2);
					writesheet2.addCell(label);
					
					label= new Label(6, row, RaterDetail[0],bordersData2);
					writesheet2.addCell(label);
					
					label= new Label(7, row, Relation,bordersData2);
					writesheet2.addCell(label);
					
					label= new Label(8, row, RaterDetail[2],bordersData2);
					writesheet2.addCell(label);
					
					label= new Label(9, row, Status,bordersData2);
					writesheet2.addCell(label);
		
				
					no_Records++;
					row++;
					
				}
				
	        }
	        catch(Exception E) 
	        {
	            System.err.println("Report_DeletedSurvey.java - AllRaters - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }	
			
			writesheet.addRowPageBreak(row+1);
			
			return row+5;
		
	}
	
	public int AllDeletedRaters(int SurveyID, int xrow) 
		throws IOException, WriteException, SQLException, Exception
	{
		int row_data = xrow+5;
			
		Label label = new Label(0, row_data-3, "LIST OF DELETED TARGETS AND RATERS",cellBOLD_Title); 
		writesheet2.addCell(label); 
		writesheet2.mergeCells(0, row_data-3, 3, row_data-3);
		
		label= new Label(0, row_data-1, "Target",cellBOLD_Border);
		writesheet2.addCell(label); 
		writesheet2.mergeCells(0, row_data-1, 3, row_data-1);
			
		label= new Label(4, row_data-1, "Rater",cellBOLD_Border);
		writesheet2.addCell(label); 
		writesheet2.mergeCells(4, row_data-1, 9, row_data-1);
		
		/* ---------------------------------------------START: Target-------------------------------------*/
		
		label= new Label(0, row_data, "Department",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(0,15);
		
		label= new Label(1, row_data, "Other Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(1,13);
		
		label= new Label(2, row_data, "Family Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(2,13);

		label= new Label(3, row_data, "Login Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(3,13);
			
		/* ---------------------------------------------START: Rater-------------------------------------*/
		
		label= new Label(4, row_data, "Department",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(4,13);
		
		label= new Label(5, row_data, "Other Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(5,13);
		
		label= new Label(6, row_data, "Family Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(6,13);
		
		label= new Label(7, row_data, "Rater Relation",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(7,13);
		
		label= new Label(8, row_data, "Login Name",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(8,13);
		
		label= new Label(9, row_data, "Rater Status",cellBOLD_Border);
		writesheet2.addCell(label);
		writesheet2.setColumnView(9,13);
						
		int row = row_data+1;
		int no_Records = 1;
		
		String Relation=" ";
		String Status=" ";
		String TargetDetail[] = new String[14];
		String RaterDetail[] = new String[14];
				
		String Sql = "SELECT * FROM tblSurvey a INNER JOIN tblDeletedAssignment ON a.SurveyID = tblDeletedAssignment.SurveyID INNER JOIN";
        Sql = Sql + " tblOrganization ON a.FKOrganization = tblOrganization.PKOrganization INNER JOIN";
        Sql = Sql + " [User] ON tblOrganization.PKOrganization = [User].FKOrganization AND tblDeletedAssignment.RaterLoginID = [User].PKUser";
		Sql = Sql + " WHERE (a.SurveyID = "+SurveyID+")";
		Sql = Sql + " ORDER BY TargetLoginID";
			
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(Sql);

			while(rs.next())	
			{
				String RaterCode = rs.getString("RaterCode");
				int RaterStatus = rs.getInt("RaterStatus");
				int RaterLoginID = rs.getInt("RaterLoginID");
				int TargetLoginID = rs.getInt("TargetLoginID");
				int NameSequence = rs.getInt("NameSequence");
				
				if(RaterCode.equals("SUP"))
					Relation = "Superior";
				else if(RaterCode.equals("SELF"))
					Relation = "Self";
				else if(RaterCode.equals("OTH"))
					Relation = "Others";
					
				if(RaterStatus == 0)
					Status = "Incomplete";
				if(RaterStatus == 1)
					Status = "Completed";	
				if(RaterStatus == 2)
					Status = "Unreliable";				
				
				TargetDetail = user.getUserDetail(TargetLoginID, NameSequence);
				RaterDetail = user.getUserDetail(RaterLoginID, NameSequence);

	/* -------------------------------------------------------START: Target----------------------------------------------*/									
				label = new Label(0,row, TargetDetail[6],bordersData2);
				writesheet2.addCell(label);
				
				label = new Label(1,row, TargetDetail[1],bordersData2);
				writesheet2.addCell(label);
								
				label= new Label(2, row, TargetDetail[0] ,bordersData1);
				writesheet2.addCell(label);
				
				label= new Label(3, row, TargetDetail[2],bordersData2);
				writesheet2.addCell(label);

	/* -------------------------------------------------------START: Rater----------------------------------------------*/													
				label= new Label(4, row, RaterDetail[6],bordersData2);
				writesheet2.addCell(label);
				
				label= new Label(5, row, RaterDetail[1],bordersData2);
				writesheet2.addCell(label);
				
				label= new Label(6, row, RaterDetail[0],bordersData2);
				writesheet2.addCell(label);
				
				label= new Label(7, row, Relation,bordersData2);
				writesheet2.addCell(label);
				
				label= new Label(8, row, RaterDetail[2],bordersData2);
				writesheet2.addCell(label);
				
				label= new Label(9, row, Status,bordersData2);
				writesheet2.addCell(label);

				no_Records++;
				row++;
			}
			
			
        }
        catch(Exception E) 
        {
            System.err.println("Report_DeletedSurvey.java - AllDeletedRaters - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }	
		
		
		writesheet2.addRowPageBreak(row+1);
		return row+2;
	}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_DeleteSurvey Rpt = new Report_DeleteSurvey();
		
		String filename = Rpt.DeleteReport(445);
		
	}
}