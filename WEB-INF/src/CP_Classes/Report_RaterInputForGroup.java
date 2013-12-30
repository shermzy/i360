package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblAssignment;
import CP_Classes.vo.votblSurvey;
import CP_Classes.vo.votblSurveyBehaviour;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WriteException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;

public class Report_RaterInputForGroup
{
	private Setting server;
	private User user;
	private EventViewer ev;
	private Create_Edit_Survey CE_Survey;
	
	private String sDetail[] = new String[13];
	private String raterdetail[] = new String[13];
 	private String itemName = "Report";
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold;
	private File outputWorkBook, inputWorkBook;
	
	public Report_RaterInputForGroup()
	{
		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();

	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\Raters Input For Group.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Raters' Input For Group");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold); 
		
		cellBOLD_Border = new WritableCellFormat(fontBold); 
		cellBOLD_Border.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_Border.setAlignment(Alignment.CENTRE);
		cellBOLD_Border.setWrap(true); 
		cellBOLD_Border.setBackground(Colour.GRAY_25); 
		
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
		No_Borders_NoBold.setWrap(true);
		
	}
	
	
	public void Header(int SurveyID, int RaterID) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "Raters' Input For Group",cellBOLD);
		if (server.LangVer == 2)
			label = new Label(0, 0, "MASUKAN PENILAI UNTUK GRUP",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		
		String CompName=" ";
		String OrgName =" ";
		String SurveyName = " ";
		int NameSequence=0;
		String SurveyLevel = " ";
		
		
		votblSurvey voSurvey = CE_Survey.getSurveyDetail(SurveyID);
		if(voSurvey != null)	
		{
			
			CompName = voSurvey.getCompanyName();
			OrgName = voSurvey.getOrganizationName();
			SurveyName = voSurvey.getSurveyName();
			NameSequence = voSurvey.getNameSequence();
			int LevelOfSurvey = voSurvey.getLevelOfSurvey();
			
			if(LevelOfSurvey == 0)
				SurveyLevel = "Competency Level";
				if (server.LangVer == 2)
					SurveyLevel = "Tingkat Kompetensi";
			else if(LevelOfSurvey == 1)
				SurveyLevel = "Key Behaviour Level";
				if (server.LangVer == 2)
					SurveyLevel = "Tingkat Perilaku Kunci";
			
		}
		int row_title = 2;
		
		label= new Label(0, row_title, "Company:",cellBOLD);
		if (server.LangVer == 2)
			label= new Label(0, row_title, "Nama Perusahaan:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.setColumnView(1,15);
		writesheet.mergeCells(0, row_title, 1, row_title);
						
		label= new Label(2, row_title, UnicodeHelper.getUnicodeStringAmp(CompName), No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);

		label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		if (server.LangVer == 2)
			label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , UnicodeHelper.getUnicodeStringAmp(OrgName) ,No_Borders);
		writesheet.addCell(label); 
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		if (server.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , UnicodeHelper.getUnicodeStringAmp(SurveyName) ,No_Borders);
		writesheet.addCell(label); 
		
		String RaterDetail[] = new String[14];
		RaterDetail = user.getUserDetail(RaterID, NameSequence);
		
		label= new Label(0, row_title + 6, "Rater Name:",cellBOLD);
		if (server.LangVer == 2)
			label= new Label(0, row_title + 6, "Nama Penilai:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row_title + 6, 1, row_title + 6);
		
		label= new Label(2, row_title + 6 , RaterDetail[0]+", "+RaterDetail[1] ,No_Borders);
		writesheet.addCell(label); 
		
		label= new Label(0, row_title + 8, "Group Name:",cellBOLD);
		if (server.LangVer == 2)
			label= new Label(0, row_title + 8, "Nama Grup:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row_title + 8, 1, row_title + 8);
		
		label= new Label(2, row_title + 8 , RaterDetail[9] ,No_Borders);
		writesheet.addCell(label); 


		label= new Label(0, row_title + 10 , SurveyLevel,No_Borders);
		writesheet.addCell(label); 
	
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	public void printTargetHeader(int Result_col,int row_data, int SurveyID, int RaterLoginID)throws IOException, WriteException, SQLException, Exception
	{
		int xTargetLoginID = 0;
	
		AssignTarget_Rater ATR = new AssignTarget_Rater();
		Vector v = ATR.getTargetDetail(SurveyID, RaterLoginID);

		for(int i=0; i<v.size(); i++)
		{
			voUser vo = (voUser)v.elementAt(i);
			int TargetLoginID = vo.getTargetLoginID();
			int NameSequence = vo.getNameSequence();
			
			if(xTargetLoginID != TargetLoginID)
			{
				String TargetDetail[] = new String[13];
				TargetDetail = user.getUserDetail(TargetLoginID, NameSequence);
				
				label= new Label(Result_col, row_data, TargetDetail[0]+", "+TargetDetail[1], cellBOLD_Border);
				writesheet.addCell(label);
	
				Result_col = Result_col+1;	
				xTargetLoginID = TargetLoginID;
			}
			
		}
	}
	
	public boolean AllTargets(int SurveyID, int RaterLoginID, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 	consulting company
		 	Definition 0:	List All Competencies
		  	Definition 1:	List All Competencies with Definitions
		*/
			
		Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
		String OldName = CE_Survey.getSurveyName(SurveyID);
		
		write();
		Header(SurveyID, RaterLoginID);		
		
		int i=0 ;
		int xAssID =0 ;
		int row=0;
		int row_data = 15;

		int xRatingTaskID  = 0;
		int Result_col = 2;
		int LevelOfSurvey =0;
		int [] arr_AssID = new int[50];
			
		AssignTarget_Rater ATR = new AssignTarget_Rater();
		Vector v = ATR.getRaterAssignmentIDs(SurveyID, RaterLoginID);
		
		for(int j=0; j<v.size(); j++)
		{
			votblAssignment vo = (votblAssignment)v.elementAt(j);
			int AssID = vo.getAssignmentID();
			
			if( xAssID!= AssID)
			{
				arr_AssID[i] = AssID;
			
				i++;
				xAssID = AssID;
			}
			
		}

		Vector vRating = ATR.getSurveyDetail(SurveyID);
		
		for(int j=0; j<vRating.size(); j++)
		{
			votblAssignment vo = (votblAssignment)vRating.elementAt(j);
			int col = 0;
			
			int RatingTaskID = vo.getRatingTaskID();
			String rating = vo.getRatingTaskName();
			LevelOfSurvey = vo.getLevelOfSurvey();
			int AssignmentID = vo.getAssignmentID();
			
			if(xRatingTaskID  != RatingTaskID )
			{
				label= new Label(col, row_data-1, rating, No_Borders);
				writesheet.addCell(label);
				
				/* ---------------------------------------------START: Competency Level-------------------------------------*/

				label= new Label(col, row_data, "Competency",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(col, row_data, "Kompetensi",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(0,15);
				
				if(LevelOfSurvey == 0)
				{
					Result_col = 1;
					
					printTargetHeader(Result_col,row_data,SurveyID,RaterLoginID);
					
					
					
					Vector vComp = ATR.getCompetencies(AssignmentID);
					
    				row = row_data+1;
					int xCompID = 0;
									
					for(int k=0; k<vComp.size(); k++)
					{
						voCompetency voComp = (voCompetency)vComp.elementAt(k);
						
						col = 0;
						int CompID = voComp.getCompetencyID();
						String CompName = voComp.getCompetencyName();
					
						if(xCompID != CompID)
						{
							label= new Label(col, row, UnicodeHelper.getUnicodeStringAmp(CompName) , bordersData2);
							writesheet.addCell(label);
							
							printRaterResult_Comp(Result_col, row, arr_AssID,CompID, RatingTaskID);
							
							xCompID = CompID;
							row = row+1;
						}
					}		
					
					col = col+1;
				}
				
				/* --------------------------------START: Key Behaviour Level-----------------------------*/
				if(LevelOfSurvey == 1)
				{
					Result_col = 2;
					
					printTargetHeader(Result_col,row_data,SurveyID,RaterLoginID);
					
					label= new Label(col+1, row_data, "Key Behaviour Statement",cellBOLD_Border);
					if (server.LangVer == 2)
						label= new Label(col+1, row_data, "Pernyataan Perilaku Kunci",cellBOLD_Border);
					writesheet.addCell(label);
					writesheet.setColumnView(1,25);
					
					
					
					SurveyKB SKB = new SurveyKB();
					Vector vKB = SKB.getSurveyKB(SurveyID);
					
					row = row_data+1;
					int xCompID = 0;
					int xKeyID = 0;
					
					for(int k=0; k<vKB.size(); k++)
					{
						votblSurveyBehaviour voKB = (votblSurveyBehaviour)vKB.elementAt(k);
						col = 0;
						int CompID = voKB.getCompetencyID();
						String CompName = voKB.getCompetencyName();
						int KeyID = voKB.getKeyBehaviourID();
						String KeyName = voKB.getKBName();
				
						if(xCompID != CompID)
						{
							label= new Label(col, row, UnicodeHelper.getUnicodeStringAmp(CompName) , bordersData2);
							writesheet.addCell(label);
							
							label= new Label(col+1, row, " " , bordersData2);
							writesheet.addCell(label);
							
							xCompID = CompID;
						}
						else
						{
							label= new Label(col, row, " " , bordersData2);
							writesheet.addCell(label);
							
							label= new Label(col+1, row, " " , bordersData2);
							writesheet.addCell(label);
							
							label= new Label(col, row+1, " " , bordersData2);
							writesheet.addCell(label);
						}
					
						if(xKeyID != KeyID)
						{
							label= new Label(col, row+1, " " , bordersData2);
							writesheet.addCell(label);
							
							label= new Label(col+1, row+1, UnicodeHelper.getUnicodeStringAmp(KeyName) , bordersData2);
							writesheet.addCell(label);
							
							xKeyID = KeyID;
						}
						else
						{
							label= new Label(col+1, row-1, " " , bordersData2);
							writesheet.addCell(label);
						}
						
						printRaterResult_KB(Result_col, row, arr_AssID,KeyID, RatingTaskID);
						
						row = row + 2; 
					}					

				}
				
					xRatingTaskID  = RatingTaskID ;	
			}

			row_data = row + 2;
			
		}
			
		workbook.write();
		workbook.close(); 
		
		sDetail = CE_Survey.getUserDetail(PKUser);
		raterdetail = CE_Survey.getUserDetail(RaterLoginID);
		ev.addRecord("Insert", itemName, raterdetail[0]+", "+raterdetail[1]+"(Rater) Input for Group for Survey "+OldName, sDetail[2], sDetail[11], sDetail[10]);

		return IsNull;
	}
	
	public void printRaterResult_KB(int Result_col, int row, int [] arr_AssID, int KeyID, int RatingTaskID)
		throws IOException, WriteException, SQLException, Exception
	{
		for(int d = 0; d<arr_AssID.length; d++)
		{
			if(arr_AssID[d] != 0)
			{
				//System.out.println(arr_AssID[d] + "--" + KeyID + "--" + RatingTaskID);
				
				AssignTarget_Rater ATR = new AssignTarget_Rater();
				
				double result = ATR.getKBResult(arr_AssID[d], KeyID, RatingTaskID);
				
				if(result != -1)	
				{
					double SurvResult = Math.round(result * 100.0) / 100.0;
				
				
					//String resultSQL_inner = "SELECT * FROM tblResultBehaviour a, tblAssignment b WHERE a.AssignmentID = b.AssignmentID AND a.AssignmentID = "+arr_AssID[d];
					//resultSQL_inner = resultSQL_inner + " AND KeyBehaviourID = "+KeyID+" AND RatingTaskID = "+RatingTaskID;
				
			
					label= new Label(Result_col, row, " " , bordersData2);
					writesheet.addCell(label);
							
					Number num= new Number(Result_col, row+1, SurvResult, bordersData1);
					writesheet.addCell(num);
				}
				else
				{
					label= new Label(Result_col, row, " " , bordersData2);
					writesheet.addCell(label);
					
					label= new Label(Result_col, row+1, " ", bordersData2);
					writesheet.addCell(label);
				}
				
				Result_col = Result_col+1;
			}
		
		}
	}
	
	public void printRaterResult_Comp(int Result_col, int row, int [] arr_AssID, int CompID, int RatingTaskID)
		throws IOException, WriteException, SQLException, Exception
	{
		for(int d = 0; d<arr_AssID.length; d++)
		{
			if(arr_AssID[d] != 0)
			{
				AssignTarget_Rater ATR = new AssignTarget_Rater();
				
				double result = ATR.getCompResult(arr_AssID[d], CompID, RatingTaskID);
				
				if(result !=-1)	
				{
					double SurvResult = Math.round(result * 100.0) / 100.0;
					Number num = new Number(Result_col, row, SurvResult, bordersData1);
					writesheet.addCell(num);
				}
				else
				{
					label= new Label(Result_col, row, " ", bordersData2);
					writesheet.addCell(label);
				}
				
				Result_col = Result_col+1;
			}
		}
	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_RaterInputForGroup Rpt = new Report_RaterInputForGroup();

		// competency level = 340
		// key behaviour level = 343
		Rpt.AllTargets(465,6414,5);
	}
}	