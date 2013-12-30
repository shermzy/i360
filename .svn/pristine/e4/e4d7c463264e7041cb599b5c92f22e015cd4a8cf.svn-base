package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.vo.voUser;
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
import jxl.format.Colour;
import jxl.format.PageOrientation;
/**
 * 
 * Change Log
 * ==========
 *
 * Date        By				Method(s)            					Change(s) 
 * ================================================================================================
 * 31/05/12	  Albert		   AllRatersWithRound()				to get all raters including the respective round 
 * 
 * 
 */
public class Report_ListOfRatersStatus_Survey
{

	private Setting server;
	private User user;
	private User_Jenty user_Jenty;
	private EventViewer ev;
	private Create_Edit_Survey CE_Survey;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Report";
	private int iNameSequence = 0;
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold ;
	private File inputWorkBook, outputWorkBook;
	
	public Report_ListOfRatersStatus_Survey()
	{

		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();
		user_Jenty = new User_Jenty();
	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path() + "\\List Of Raters Status Surveys.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List Of Raters Status For Specific Survey");
				
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
	
	
	public void Header(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Raters Status For Specific Survey",cellBOLD);
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "DAFTAR STATUS PENILAI UNTUK SURVEI SPESIFIK",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		
		String CompName=" ";
		String OrgName =" ";
		String SurveyName = " ";
		
		Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
		
		votblSurvey vo = CE_Survey.getSurveyDetail(SurveyID);
			
		CompName = vo.getCompanyName();
		OrgName = vo.getOrganizationName();
		SurveyName = vo.getSurveyName();
		
			
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
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , UnicodeHelper.getUnicodeStringAmp(OrgName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		//writesheet.setRowView(row_title+2,15);
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , UnicodeHelper.getUnicodeStringAmp(SurveyName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		//writesheet.setRowView(row_title+4,15);

		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright 锟�-Sixty Profiler锟絠s a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");	
	}
	
	/**
         * Generates List of Raters and outputs it as an Excel sheet
         *  
         * @param SurveyID ID of the survey
         * @param PKUser UserID of user calling this method, for authentication checking
         * @param showUnreliable Show Unreliable status or display it as Completed instead
         * 
         * Updates:
         * 21 July 08 - Added parameter to add new feature to display or hide unreliable status, DeZ
         */
	public boolean AllRaters(int SurveyID, int PKUser, String showUnreliable, boolean includeDetail)
		throws IOException, WriteException, SQLException, Exception
	{
		// with detail
		int targetStartCol = 0;
		int targetLastCol = 3;
		int raterStartCol = 4;
		int raterLastCol = 13;
		
		// simplify version
		if(!includeDetail){
			targetLastCol = 2;
			raterStartCol = 3;
			raterLastCol = 7;
		}

		
		boolean IsNull = false;
			
			String SurveyName="";
			write();
			iNameSequence = user_Jenty.NameSequence_BySurvey(SurveyID);
			Header(SurveyID);
			
			int row_data = 9;
			
			
			label= new Label(targetStartCol, row_data-1, "Target",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(targetStartCol, row_data-1, "Target",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.mergeCells(targetStartCol, row_data-1, targetLastCol, row_data-1);
			
			label= new Label(raterStartCol, row_data-1, "Rater",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(raterStartCol, row_data-1, "Penilai",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.mergeCells(raterStartCol, row_data-1, raterLastCol, row_data-1);
			
			if(includeDetail){
				label= new Label(raterLastCol+1, row_data-1, "Wave",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(raterLastCol+1, row_data-1, "Wave",cellBOLD_Border);
				writesheet.addCell(label); 
				writesheet.mergeCells(raterLastCol+1, row_data-1, raterLastCol+1, row_data);
			}
			
			/* ---------------------------------------------START: Target-------------------------------------*/
			int iColPtr = targetStartCol;
			
			if(includeDetail){
				label= new Label(iColPtr, row_data, "Department",cellBOLD_Border);
				
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Departemen",cellBOLD_Border);
				
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,15);
				iColPtr++;
			}
			
			//Depending on iNameSequence, Column will be adjusted automatically
			label= new Label(iColPtr+1 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr+1 - iNameSequence, row_data, "Nama",cellBOLD_Border);			
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr+1 - iNameSequence,13);
			
			label= new Label(iNameSequence + iColPtr, row_data, "Family Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iNameSequence + iColPtr, row_data, "Nama Keluarga",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iNameSequence + iColPtr,13);
			iColPtr += 2;


			label= new Label(iColPtr, row_data, "Login Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Nama Login",cellBOLD_Border);	
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);			
			iColPtr ++;
			
			/* ---------------------------------------------START: Rater-------------------------------------*/
			
			
			if(includeDetail){
				label= new Label(iColPtr, row_data, "Department",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Departemen",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,13);
				iColPtr ++;
			}
			
			label= new Label(iColPtr+1 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(iColPtr+1 - iNameSequence, row_data, "Nama",cellBOLD_Border);			
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr+1 - iNameSequence,13);
			
			label= new Label(iNameSequence + iColPtr, row_data, "Family Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iNameSequence + iColPtr, row_data, "Nama Keluarga",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iNameSequence + iColPtr,13);
			iColPtr += 2;
			
			label= new Label(iColPtr, row_data, "Rater Relation",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Hubungan Penilai",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);
			iColPtr ++;
			
			label= new Label(iColPtr, row_data, "Login Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Nama Login",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);
			iColPtr ++;
			
			if(includeDetail){
				label= new Label(iColPtr, row_data, "Office Tel",cellBOLD_Border);		
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Translation required",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,13);
				iColPtr ++;
				
				label= new Label(iColPtr, row_data, "Handphone",cellBOLD_Border);		
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Translation required",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,13);
				iColPtr ++;
				
				label= new Label(iColPtr, row_data, "Mobile Provider",cellBOLD_Border);		
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Translation required",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,13);	
				iColPtr ++;
				
				label= new Label(iColPtr, row_data, "Remarks",cellBOLD_Border);		
				if (server.LangVer == 2)
					label= new Label(iColPtr, row_data, "Translation required",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iColPtr,13);
				iColPtr ++;
			}
			
			
			label= new Label(iColPtr, row_data, "Rater Status",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Status Penilai",cellBOLD_Border);		
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);	
			
		
			
			int row = row_data+1;
			int no_Records = 1;
			
			String Relation=" ";
			String Status=" ";
			String TargetDetail[] = new String[14];
			String RaterDetail[] = new String[14];
		
			AssignTarget_Rater ATR = new AssignTarget_Rater();
			
			Vector v = ATR.getTargetRater(SurveyID);
			
			for(int i=0; i<v.size(); i++)
			{
				voUser vo = (voUser)v.elementAt(i);
				
				SurveyName = vo.getSurveyName();
				String RaterCode = vo.getRaterCode();
				int RaterStatus = vo.getRaterStatus();
				int RaterLoginID = vo.getRaterLoginID();
				int TargetLoginID = vo.getTargetLoginID();
				int NameSequence = vo.getNameSequence();
				
				//1-Mar-05: Commented off by Rianto
				//if(RaterCode.equals("SUP"))
				if(RaterCode.substring(0, 3).equals("SUP"))
					Relation = "Superior";
				//else if(RaterCode.equals("SELF"))
				else if(RaterCode.substring(0, 4).equals("SELF"))
					Relation = "Self";
				//else if(RaterCode.equals("OTH"))
				else if(RaterCode.substring(0, 3).equals("OTH"))
					Relation = "Others";
				//END 1-Mar-05: Commented off by Rianto
				/*
				 * Change(s) : Added logic to include new categories Peers and Subordinates
				 * Reason(s) : Rater Relations not reflected correctly because of the the addition of new categories Peers and Subordinates
				 * Updated By: Desmond
				 * Updated On: 23 Oct 2009
				 */
				else if(RaterCode.substring(0, 3).equals("SUB"))
					Relation = "Subordinate";
				else if(RaterCode.substring(0, 4).equals("PEER"))
					Relation = "Peer";
				else if(RaterCode.substring(0, 3).equals("ADD"))
					Relation = "Additional Rater";
				else if(RaterCode.substring(0, 3).equals("DIR"))
					Relation = "Direct Report";
				else if(RaterCode.substring(0, 3).equals("IDR"))
					Relation = "Indirect Report";
				else
					Relation = "Undefined"; // To indicate rater relations that have not been explicitly handled

				

				if(RaterStatus == 0)
                                    Status = "Incomplete";
				if(RaterStatus == 1)
                                    Status = "Completed";
                                
                                // Changed by DeZ, 21.07.08, Add new feature to display or hide unreliable status
				if(RaterStatus == 2)
                                {
                                    Status = "Unreliable";
                                    if( showUnreliable.equalsIgnoreCase("hide") ) Status = "Completed";
                                }
				
				TargetDetail = ATR.getAssignmentDetail(TargetLoginID, TargetLoginID, NameSequence);
				RaterDetail = ATR.getAssignmentDetail(TargetLoginID, RaterLoginID, NameSequence);

/* -------------------------------------------------------START: Target----------------------------------------------*/		
				iColPtr = 0;
				if(includeDetail){
					//Department
					label = new Label(iColPtr,row, TargetDetail[6],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
				}
				
				//FamilyName or OtherName
				label = new Label(iColPtr, row, TargetDetail[0],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				//FamilyName or OtherName
				label= new Label(iColPtr, row, TargetDetail[1] ,bordersData1);
				writesheet.addCell(label);
				iColPtr ++;
				
				label= new Label(iColPtr, row, TargetDetail[2],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
			
/* -------------------------------------------------------START: Rater----------------------------------------------*/		
				if(includeDetail){
					// Department
					label= new Label(iColPtr, row, RaterDetail[6],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
				}
				
				//FamilyName or OtherName
				label= new Label(iColPtr, row, RaterDetail[0],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				//FamilyName or OtherName
				label= new Label(iColPtr, row, RaterDetail[1],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				label= new Label(iColPtr, row, Relation,bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				label= new Label(iColPtr, row, RaterDetail[2],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				if(includeDetail){
					label= new Label(iColPtr, row, RaterDetail[15],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
					
					label= new Label(iColPtr, row, RaterDetail[16],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
					
					label= new Label(iColPtr, row, RaterDetail[17],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
					
					label= new Label(iColPtr, row, RaterDetail[18],bordersData2);
					writesheet.addCell(label);
					iColPtr ++;
				}
				
				
				// Status
				label= new Label(iColPtr, row, Status,bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				if(includeDetail){
					label= new Label(iColPtr, row, RaterDetail[19],bordersData2);
					writesheet.addCell(label);
				}

				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Raters Status for Survey "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	/**
     * Generates List of Raters and outputs it as an Excel sheet
     *  
     * @param SurveyID ID of the survey
     * @param PKUser UserID of user calling this method, for authentication checking
     * @param showUnreliable Show Unreliable status or display it as Completed instead
     * 
     * Updates:
     * 21 July 08 - Added parameter to add new feature to display or hide unreliable status, DeZ
     */
public boolean AllRatersWithRound(int SurveyID, int PKUser, int round, String showUnreliable, boolean includeDetail)
	throws IOException, WriteException, SQLException, Exception
{
	boolean IsNull = false;
	int targetStartCol = 0;
	int targetLastCol = 3;
	int raterStartCol = 4;
	int raterLastCol = 13;
	
	// simplify version
	if(!includeDetail){
		targetLastCol = 2;
		raterStartCol = 3;
		raterLastCol = 7;
	}
		
		String SurveyName="";
		write();
		iNameSequence = user_Jenty.NameSequence_BySurvey(SurveyID);
		Header(SurveyID);
		
		int row_data = 9;
		
		
		label= new Label(targetStartCol, row_data-1, "Target",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(targetStartCol, row_data-1, "Target",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(targetStartCol, row_data-1, targetLastCol, row_data-1);
		
		label= new Label(raterStartCol, row_data-1, "Rater",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(raterStartCol, row_data-1, "Penilai",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(raterStartCol, row_data-1, raterLastCol, row_data-1);
		
		label= new Label(raterLastCol+1, row_data-1, "Round",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(raterLastCol+1, row_data-1, "Ronde",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(raterLastCol+1, row_data-1, raterLastCol+1, row_data);
		
		label= new Label(raterLastCol+2, row_data-1, "Wave",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(raterLastCol+2, row_data-1, "Need translation",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(raterLastCol+2, row_data-1, raterLastCol+2, row_data);

		/* ---------------------------------------------START: Target-------------------------------------*/
		int iColPtr = targetStartCol;
		
		if(includeDetail){
			label= new Label(iColPtr, row_data, "Department",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Departemen",cellBOLD_Border);		
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,15);
			iColPtr ++;
		}
		
		//Depending on iNameSequence, Column will be adjusted automatically
		label= new Label(iColPtr+1 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(iColPtr+1 - iNameSequence, row_data, "Nama",cellBOLD_Border);			
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr+1 - iNameSequence,13);
		
		label= new Label(iNameSequence + iColPtr, row_data, "Family Name",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(iNameSequence + iColPtr, row_data, "Nama Keluarga",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(iNameSequence + iColPtr,13);
		iColPtr += 2;

		label= new Label(iColPtr, row_data, "Login Name",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(iColPtr, row_data, "Nama Login",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr,13);
		iColPtr ++;

		
		/* ---------------------------------------------START: Rater-------------------------------------*/
		
		
		if(includeDetail){
			label= new Label(iColPtr, row_data, "Department",cellBOLD_Border);		
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Departemen",cellBOLD_Border);		
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);
			iColPtr ++;
		}
		
		label= new Label(iColPtr+1 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(iColPtr+1 - iNameSequence, row_data, "Nama",cellBOLD_Border);			
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr+1 - iNameSequence,13);
		
		label= new Label(iNameSequence + iColPtr, row_data, "Family Name",cellBOLD_Border);		
		if (server.LangVer == 2)
			label= new Label(iNameSequence + iColPtr, row_data, "Nama Keluarga",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(iNameSequence + iColPtr,13);
		iColPtr += 2;
		
		label= new Label(iColPtr, row_data, "Rater Relation",cellBOLD_Border);		
		if (server.LangVer == 2)
			label= new Label(iColPtr, row_data, "Hubungan Penilai",cellBOLD_Border);		
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr,13);
		iColPtr ++;
		
		label= new Label(iColPtr, row_data, "Login Name",cellBOLD_Border);		
		if (server.LangVer == 2)
			label= new Label(iColPtr, row_data, "Nama Login",cellBOLD_Border);		
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr,13);	
		iColPtr ++;
		
		if(includeDetail){
			label= new Label(iColPtr, row_data, "Office Tel",cellBOLD_Border);		
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Office Tel",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);	
			iColPtr ++;
			
			label= new Label(iColPtr, row_data, "Handphone",cellBOLD_Border);		
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Handphone",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);	
			iColPtr ++;
			
			label= new Label(iColPtr, row_data, "Mobile Provider",cellBOLD_Border);		
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Mobile Provider",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);
			iColPtr ++;
			
			label= new Label(iColPtr, row_data, "Remarks",cellBOLD_Border);		
			if (server.LangVer == 2)
				label= new Label(iColPtr, row_data, "Remarks",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iColPtr,13);
			iColPtr ++;
		}
		label= new Label(iColPtr, row_data, "Rater Status",cellBOLD_Border);		
		if (server.LangVer == 2)
			label= new Label(iColPtr, row_data, "Status Penilai",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(iColPtr,13);	
		iColPtr ++;
		
		int row = row_data+1;
		int no_Records = 1;
		
		String Relation=" ";
		String Status=" ";
		String TargetDetail[] = new String[15];
		String RaterDetail[] = new String[15];
	
		AssignTarget_Rater ATR = new AssignTarget_Rater();
		
		Vector v = ATR.getTargetRater(SurveyID);
		
		for(int i=0; i<v.size(); i++)
		{
			voUser vo = (voUser)v.elementAt(i);
			
			SurveyName = vo.getSurveyName();
			String RaterCode = vo.getRaterCode();
			int RaterStatus = vo.getRaterStatus();
			int RaterLoginID = vo.getRaterLoginID();
			int TargetLoginID = vo.getTargetLoginID();
			int NameSequence = vo.getNameSequence();
			
			//1-Mar-05: Commented off by Rianto
			//if(RaterCode.equals("SUP"))
			if(RaterCode.substring(0, 3).equals("SUP"))
				Relation = "Superior";
			//else if(RaterCode.equals("SELF"))
			else if(RaterCode.substring(0, 4).equals("SELF"))
				Relation = "Self";
			//else if(RaterCode.equals("OTH"))
			else if(RaterCode.substring(0, 3).equals("OTH"))
				Relation = "Others";
			//END 1-Mar-05: Commented off by Rianto
			/*
			 * Change(s) : Added logic to include new categories Peers and Subordinates
			 * Reason(s) : Rater Relations not reflected correctly because of the the addition of new categories Peers and Subordinates
			 * Updated By: Desmond
			 * Updated On: 23 Oct 2009
			 */
			else if(RaterCode.substring(0, 3).equals("SUB"))
				Relation = "Subordinate";
			else if(RaterCode.substring(0, 4).equals("PEER"))
				Relation = "Peer";
			/*
			 * Change(s) : Added logic to include new categories Additional Rater
			 * Reason(s) : Rater Relations not reflected correctly because of the the addition of new categories Additional Rater
			 * Updated By: Su Lingtong
			 * Updated On: 7 Sep 2012
			 */
			else if(RaterCode.substring(0, 3).equals("ADD"))
				Relation = "Additional Rater";
			//Added by Albert
			else if(RaterCode.substring(0, 3).equals("DIR"))
				Relation = "Direct Report";
			else if(RaterCode.substring(0, 3).equals("IDR"))
				Relation = "Indirect Report";
			else
				Relation = "Undefined"; // To indicate rater relations that have not been explicitly handled

			

			if(RaterStatus == 0)
                                Status = "Incomplete";
			if(RaterStatus == 1)
                                Status = "Completed";
                            
                            // Changed by DeZ, 21.07.08, Add new feature to display or hide unreliable status
			if(RaterStatus == 2)
                            {
                                Status = "Unreliable";
                                if( showUnreliable.equalsIgnoreCase("hide") ) Status = "Completed";
                            }
			
			TargetDetail = ATR.getAssignmentDetail(TargetLoginID, TargetLoginID, NameSequence);
			RaterDetail = ATR.getAssignmentDetail(TargetLoginID, RaterLoginID, NameSequence);
			if(round!= -2){ //if not all
				if(TargetDetail[14]==null || RaterDetail[14]==null || Integer.parseInt(TargetDetail[14])!=round || Integer.parseInt(RaterDetail[14])!=round) continue;
			}
/* -------------------------------------------------------START: Target----------------------------------------------*/		
			iColPtr = 0;
			if(includeDetail){
				label = new Label(iColPtr,row, TargetDetail[6],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
			}
			
			//FamilyName or OtherName
			label = new Label(iColPtr, row, TargetDetail[0],bordersData2);
			writesheet.addCell(label);
			iColPtr ++; 
			
			//FamilyName or OtherName
			label= new Label(iColPtr, row, TargetDetail[1] ,bordersData1);
			writesheet.addCell(label);
			iColPtr ++;
			
			label= new Label(iColPtr, row, TargetDetail[2],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;

/* -------------------------------------------------------START: Rater----------------------------------------------*/	
			if(includeDetail){
				label= new Label(iColPtr, row, RaterDetail[6],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
			}
			
			//FamilyName or OtherName
			label= new Label(iColPtr, row, RaterDetail[0],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			//FamilyName or OtherName
			label= new Label(iColPtr, row, RaterDetail[1],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			label= new Label(iColPtr, row, Relation,bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			label= new Label(iColPtr, row, RaterDetail[2],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			if(includeDetail){
				label= new Label(iColPtr, row, RaterDetail[15],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				label= new Label(iColPtr, row, RaterDetail[16],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
				
				label= new Label(iColPtr, row, RaterDetail[17],bordersData2);
				writesheet.addCell(label);
				iColPtr ++; 
				
				label= new Label(iColPtr, row, RaterDetail[18],bordersData2);
				writesheet.addCell(label);
				iColPtr ++;
			}
			
			label= new Label(iColPtr, row, Status,bordersData2);
			writesheet.addCell(label);
			iColPtr ++;

			label = new Label(iColPtr,row,RaterDetail[14],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			label = new Label(iColPtr,row,RaterDetail[19],bordersData2);
			writesheet.addCell(label);
			iColPtr ++;
			
			no_Records++;
			row++;
			
		}
		
		workbook.write();
		workbook.close(); 
		
		sDetail = CE_Survey.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, "List Of Raters Status for Survey "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
		
		if(no_Records == 0)
		IsNull = true;

	return IsNull;
}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ListOfRatersStatus_Survey Rpt = new Report_ListOfRatersStatus_Survey();

		
		boolean flag = Rpt.AllRaters(445,1,"show", false);
		System.out.println("flag "+flag);
		
			
	}
}	