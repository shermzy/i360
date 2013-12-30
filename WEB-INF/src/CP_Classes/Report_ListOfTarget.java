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
 * 30/05/12	  Albert		   AllTargetWithRound()				to get all targets including the respective round 
 * 
 * 20/07/12   Liu Taichen      AllTargetWithRound()                     To also print out the email address
 * 
 */
public class Report_ListOfTarget
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
	private File outputWorkBook, inputWorkBook;
	
	public Report_ListOfTarget()
	{
		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();
		user_Jenty = new User_Jenty();
	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path() + "\\List Of Targets.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List Of Targets For Specific Survey");
		
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
		Label label = new Label(0, 0, "List Of Targets For Specific Survey",cellBOLD);
		if (server.LangVer == 2)
			label = new Label(0, 0, "DAFTAR TARGET UNTUK SURVEI SPESIFIK",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		String CompName=" ";
		String OrgName =" ";
		String SurveyName = " ";
		
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
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	
	public boolean AllTarget(int SurveyID, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		String SurveyName="";
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		 
		/* 	Definition 0:	List All Competencies
		 * 	Definition 1:	List All Competencies with Definitions
		 */
		
			write();
			iNameSequence = user_Jenty.NameSequence_BySurvey(SurveyID);
			
			Header(SurveyID);		

			int row_data = 9;

			
			/* ---------------------------------------------START: Target-------------------------------------*/
			
			label= new Label(0, row_data, "Department",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(0, row_data, "Departemen",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(0,15);
			
			//Depending on iNameSequence, Column will be adjusted automatically
			label= new Label(2 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(2 - iNameSequence, row_data, "Nama",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(2 - iNameSequence,13);
			
			label= new Label(iNameSequence + 1, row_data, "Family Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(iNameSequence + 1, row_data, "Nama Keluarga",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(iNameSequence + 1,13);

			label= new Label(3, row_data, "Login Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(3, row_data, "Nama Login",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(3,13);
			
			/* ---------------------------------------------START: Rater-------------------------------------*/
			
			int row = row_data+1;
			int no_Records = 1;

			String TargetDetail[] = new String[14];
					
/*			String Sql = "SELECT DISTINCT a.SurveyName, tblAssignment.TargetLoginID, tblOrganization.NameSequence FROM tblSurvey a INNER JOIN ";
            Sql = Sql + " tblAssignment ON a.SurveyID = tblAssignment.SurveyID INNER JOIN ";
            Sql = Sql + "  tblOrganization ON a.FKOrganization = tblOrganization.PKOrganization INNER JOIN [User] ON tblAssignment.RaterLoginID = [User].PKUser ";
			Sql = Sql + " WHERE (a.SurveyID = "+SurveyID+")";
			
			Sql = Sql + " ORDER BY TargetLoginID";
*/
			//modified by Jenty on 8 Sept 05, perviously display duplicate targets' name
		
			AssignTarget_Rater ATR = new AssignTarget_Rater();
			Vector v = ATR.getAllSurveyTargets(SurveyID);
			
			for(int i=0; i<v.size(); i++)	
			{
				voUser vo = (voUser)v.elementAt(i);
				SurveyName = vo.getSurveyName();
				int TargetLoginID = vo.getTargetLoginID();
				int NameSequence = vo.getNameSequence();
				
				TargetDetail = user.getUserDetail(TargetLoginID, NameSequence);

/* -------------------------------------------------------START: Target----------------------------------------------*/									
				label = new Label(0,row, TargetDetail[6],bordersData2);
				writesheet.addCell(label);
				
				label = new Label(1,row, TargetDetail[0],bordersData2);
				writesheet.addCell(label);
								
				label= new Label(2, row, TargetDetail[1] ,bordersData2);
				writesheet.addCell(label);
				
				label= new Label(3, row, TargetDetail[2],bordersData2);
				writesheet.addCell(label);

				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Targets for Survey "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public boolean AllTargetWithRound(int SurveyID, int PKUser, int round) 
			throws IOException, WriteException, SQLException, Exception
		{
			boolean IsNull = false;
			String SurveyName="";
			
				write();
				iNameSequence = user_Jenty.NameSequence_BySurvey(SurveyID);
				
				Header(SurveyID);		

				int row_data = 9;

				
				/* ---------------------------------------------START: Target-------------------------------------*/
				
				label= new Label(0, row_data, "Department",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(0, row_data, "Departemen",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(0,15);
				
				//Depending on iNameSequence, Column will be adjusted automatically
				label= new Label(2 - iNameSequence, row_data, "Other Name",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(2 - iNameSequence, row_data, "Nama",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(2 - iNameSequence,13);
				
				label= new Label(iNameSequence + 1, row_data, "Family Name",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(iNameSequence + 1, row_data, "Nama Keluarga",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(iNameSequence + 1,13);

				label= new Label(3, row_data, "Login Name",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(3, row_data, "Nama Login",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(3,13);
				
				label= new Label(4, row_data, "Round",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(4, row_data, "Ronde",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(4,13);
				
				
				
				label= new Label(5, row_data, "Email",cellBOLD_Border);
				if (server.LangVer == 2)
					label= new Label(5, row_data, "Email",cellBOLD_Border);
				writesheet.addCell(label);
				writesheet.setColumnView(5,50);
				
				
				
				/* ---------------------------------------------START: Rater-------------------------------------*/
				
				int row = row_data+1;
				int no_Records = 1;

				String TargetDetail[] = new String[15];
			
				AssignTarget_Rater ATR = new AssignTarget_Rater();
				Vector v = ATR.getAllSurveyTargets(SurveyID);
				
				for(int i=0; i<v.size(); i++)	
				{
					voUser vo = (voUser)v.elementAt(i);
					SurveyName = vo.getSurveyName();
					int TargetLoginID = vo.getTargetLoginID();
					int NameSequence = vo.getNameSequence();
					
					TargetDetail = user.getUserDetailWithRound(TargetLoginID, NameSequence);
					
					if(round!= -2){ //if not all
						if(TargetDetail[14]==null || Integer.parseInt(TargetDetail[14])!=round) continue;
					}

	/* -------------------------------------------------------START: Target----------------------------------------------*/									
					label = new Label(0,row, TargetDetail[6],bordersData2);
					writesheet.addCell(label);
					
					label = new Label(1,row, TargetDetail[0],bordersData2);
					writesheet.addCell(label);
									
					label= new Label(2, row, TargetDetail[1] ,bordersData2);
					writesheet.addCell(label);
					
					label= new Label(3, row, TargetDetail[2],bordersData2);
					writesheet.addCell(label);
					
					//add new label for Round
					label= new Label(4, row, TargetDetail[14],bordersData2);
					writesheet.addCell(label);
					
					label= new Label(5, row, TargetDetail[13],bordersData2);
					writesheet.addCell(label);

					no_Records++;
					row++;
					
				}
				
				workbook.write();
				workbook.close(); 
				
				sDetail = CE_Survey.getUserDetail(PKUser);
				ev.addRecord("Insert", itemName, "List Of Targets for Survey "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
				
				if(no_Records == 0)
				IsNull = true;

			return IsNull;
		}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ListOfTarget Rpt = new Report_ListOfTarget();
		
		Rpt.AllTarget(557,5);
		
			
	}
}	