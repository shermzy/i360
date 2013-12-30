package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.vo.voUser;
//Added by Ha 11/06/08
import CP_Classes.vo.votblSurvey;

import jxl.read.biff.BiffException;
import jxl.write.*; 
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;


public class Report_Nomination
{
	/**
	 * Declaration of classes 
	 */
	private Setting server;
	private EventViewer ev;
	private Create_Edit_Survey user;
	private AssignTarget_Rater assign;
	private GlobalFunc global;
	private User UserList;
	
	/**
	 * Declaration of Variables
	 */
	
	private String sDetail[] = new String[13];
 	private String itemName = "Report";
	private String sNominationHeader[] = new String[11]; //Nomination Header
	private static int TargetList[];
	private User_Jenty user_Jenty;
	private int iNameSequence = 0;
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border;
	private WritableCellFormat cellBOLD_BorderGreen;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold ;
	private File outputWorkBook, inputWorkBook;
	//Added by Ha 11/06/08
	private Create_Edit_Survey CE_Survey;
	
	
	/**
	 * Creates a new intance of Create Edit Survey object.
	 */
	
	public Report_Nomination()
	{
		server = new Setting();
		ev = new EventViewer();
		user = new Create_Edit_Survey();
		assign = new AssignTarget_Rater();
		global = new GlobalFunc();
		UserList = new User();
		user_Jenty = new User_Jenty();
		//Added by Ha 11/06/08
		CE_Survey = new Create_Edit_Survey();
	}
	
	/*--------------------------------------To initialize the workbook and borders -------------------------------*/
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\NominationReport.xls";
		
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Progressive Report Of Rater Nomination");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold); 
		
		cellBOLD_Border = new WritableCellFormat(fontBold); 
		cellBOLD_Border.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_Border.setAlignment(Alignment.LEFT);
		cellBOLD_Border.setVerticalAlignment(VerticalAlignment.TOP);
		cellBOLD_Border.setBackground(Colour.YELLOW);
		cellBOLD_Border.setWrap(true);
		
		cellBOLD_BorderGreen = new WritableCellFormat(fontBold); 
		cellBOLD_BorderGreen.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_BorderGreen.setAlignment(Alignment.LEFT);
		cellBOLD_BorderGreen.setVerticalAlignment(VerticalAlignment.TOP);
		cellBOLD_BorderGreen.setBackground(Colour.LIGHT_GREEN);
		cellBOLD_BorderGreen.setWrap(true); 
		
		bordersData1 = new WritableCellFormat();
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.LEFT);
		bordersData1.setVerticalAlignment(VerticalAlignment.TOP);
		bordersData1.setWrap(true);
		
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
	
/*--------------------------------------To initialize the headings -------------------------------*/	

	//Changed by Ha 11/06/08
	/*Add parameter SurveyID FKOrganizationID, FKCompanyID
	 *To insert Company Name, Organisation Name, Survey Name in the header*/
	 
	
	public void Header(int SurveyID, int FKCompanyID, int FKOrganizationID) 
		throws IOException, WriteException, SQLException, Exception
	{
		// Set Nomination Header
		sNominationHeader[0] = "Division";
		sNominationHeader[1] = "Department";
		sNominationHeader[2] = "Section";
		sNominationHeader[3] = "Position";
		sNominationHeader[4] = "Employee Code";
		sNominationHeader[6 - iNameSequence] = "Name";
		sNominationHeader[iNameSequence + 5] = "Surname";
		sNominationHeader[7] = "Status";
		sNominationHeader[8] = "Superior Name";
		sNominationHeader[9] = "Superior Department";
		sNominationHeader[10] = "Superior Position";
		
		Label label = new Label(0, 0, "Progressive Report Of Rater Nomination",cellBOLD); 
		writesheet.addCell(label); 
		//writesheet.mergeCells(0, 0, 2, 0);
		
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
		
		int col_title = 0;
		for(int i=0; i<11; i++)
		{
		//Changed column from 2 to 8 by Ha 11/06/08
			if (i < 8)
				//TARGET DATA HEADER
				label= new Label(col_title, 8, sNominationHeader[i],cellBOLD_Border);
			else
				//SUPERVISOR DATA HEADER
				label= new Label(col_title, 8, sNominationHeader[i],cellBOLD_BorderGreen);
				
			writesheet.addCell(label); 
			writesheet.setColumnView(col_title,12);
			//writesheet.mergeCells(row_title, 2, 1, row_title);
			col_title++;
		}
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	
	/**
	 * Generate Nomination Report
	 * @param FKCompanyID
	 * @param FKOrganizationID
	 * @param SurveyID
	 * @param PKUser
	 * @return True if NULL
	 * @throws IOException
	 * @throws WriteException
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean NominationStatus(int FKCompanyID, int FKOrganizationID, int SurveyID, int PKUser) 
									throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		iNameSequence = user_Jenty.NameSequence_BySurvey(SurveyID);
		
		write();
		//Changed by Ha 11/06/08
		Header(SurveyID, FKOrganizationID, FKCompanyID);

		int row_data = 3;
		//int TargetLoginID = 0;	// parameter transferred to method, Mark Oei 28 April 2010
		String PrintToExcel[] = new String[11];
		
		/*TargetList = assign.getTargetList(SurveyID);
		String sTargetList = global.putArrayListToString(TargetList);
		ResultSet rs = UserList.getUserDetail_RptNomination(SurveyID,sTargetList,1);*/
		
		Vector rs = UserList.getUserDetail_RptNomination(SurveyID,1);

		//uncomment for debugging
		for (int i=0; i<rs.size(); i++){
			voUser vo = (voUser)rs.elementAt(i);
			System.out.println(vo.getFamilyName()+vo.getGivenName()+" "+vo.getRaterCode());
		}
		System.out.println("\n\n");
		
		//Changes made to code to allow removal of self target when supervisor is assign
		//but allow for self target to be displayed when no supervisor is assigned.
		//Mark Oei 28 April 2010
		if (rs.size()== 1) {	//insert data into Excel when there is only one target.
			voUser vo = (voUser)rs.elementAt(0);
			row_data = outputToExcel(SurveyID, row_data, PrintToExcel, vo);
		} else 
		{
			for(int j=0; j<(rs.size()-1); j++)
			{
				voUser vo = (voUser)rs.elementAt(j);
				voUser voNext = (voUser)rs.elementAt(j+1);
				String targetName = vo.getFamilyName()+vo.getGivenName();
				String nextTargetName = voNext.getFamilyName()+voNext.getGivenName();
				//System.out.println(j + " Current Target " + targetName + " Next Target " + nextTargetName + "\n");
				//insert data into Excel when targets are SELF only
				if (!targetName.equals(nextTargetName) && vo.getRaterCode().equals(voNext.getRaterCode())){
					//System.out.println("I am here");
					row_data = outputToExcel(SurveyID, row_data, PrintToExcel, vo);
					if ((j+1) == (rs.size()-1))
						row_data = outputToExcel(SurveyID, row_data, PrintToExcel, voNext);
					continue;
				} else 
				{	
					//Checking for current name and next name, current assignment is SELF and next assignment is SUP
					//then skip and get next record
					if (targetName.equals(nextTargetName) && vo.getRaterCode().equals("SELF") && voNext.getRaterCode().substring(0,3).equals("SUP")){
						//if this is the last record, insert into spreadsheet, else continue to next record
						if ((j+1) == (rs.size()-1)) {//insert the last data into Excel
							//System.out.println(" Current Target " + nextTargetName + " RaterCode " + voNext.getRaterCode());
							row_data = outputToExcel(SurveyID, row_data, PrintToExcel, voNext);
						} else 
						continue;
					} else {
						//Check if the second last record is SUP and the next record is SELF, insert the last 2 records
						//into Excel, else insert only 1
						if ((j+1) == (rs.size()-1) && vo.getRaterCode().substring(0,3).equals("SUP") && voNext.getRaterCode().equals("SELF")){
							//System.out.println(" j " + j + " " + rs.size() + " Current Target " + targetName + " RaterCode " + vo.getRaterCode());
							row_data = outputToExcel(SurveyID, row_data, PrintToExcel, vo);
							row_data = outputToExcel(SurveyID, row_data, PrintToExcel, voNext);
						}
						else
							row_data = outputToExcel(SurveyID, row_data, PrintToExcel, vo);
					}
				}
			}
		}
		
		workbook.write();
		workbook.close(); 
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, "Nomination Report", sDetail[2], sDetail[11], sDetail[10]);
		
		//if(no_Records == 0)
			//IsNull = true;

		return IsNull;
	}

	/** Insert data into Excel spreadsheet
	 * 
	 * @param SurveyID
	 * @param row_data
	 * @param PrintToExcel
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @throws WriteException
	 * @throws RowsExceededException
	 * 
	 * @author - Mark Oei
	 * @since v1.3.12.71	28 April 2010
	 */
	private int outputToExcel(int SurveyID, int row_data,
			String[] PrintToExcel, voUser vo) throws SQLException, Exception,
			WriteException, RowsExceededException {
		int TargetLoginID=0;
		TargetLoginID = vo.getPKUser();
		
		PrintToExcel[0] = vo.getDivisionName();
		PrintToExcel[1] = vo.getDepartmentName();
		PrintToExcel[2] = vo.getGroupName();
		PrintToExcel[3] = vo.getDesignation();
		PrintToExcel[4] = vo.getIDNumber();
		PrintToExcel[iNameSequence + 5] = vo.getFamilyName();
		PrintToExcel[6 - iNameSequence] = vo.getGivenName();
		PrintToExcel[iNameSequence + 7] = vo.getSupervisorFamilyName();
		PrintToExcel[8 - iNameSequence] = vo.getSupervisorGivenName();
		PrintToExcel[10] = vo.getSupervisorDesignation();
		PrintToExcel[9] = vo.getSupervisorDepartmentName();
		
		PrintToExcel[8] = PrintToExcel[7] + " " + PrintToExcel[8];
		//PrintToExcel[7] = "N/A";
		
		//Print to Excel
		for(int i=0; i<11; i++)
		{
			if (i == 7)
			{
				//NOMINATION STATUS
				int TotRaters = assign.getTotRaters(SurveyID, TargetLoginID);
				if(TotRaters >= 6)
				{
					PrintToExcel[7] = "Raters exceed 6";
					if(TotRaters == 6)
						PrintToExcel[7] = "Completed";
				}
				else
					PrintToExcel[7] = "Incomplete";
			}
			//Changed by Ha 11/06/08 from row_data-->row_data + 6
			label= new Label(i, row_data+6, PrintToExcel[i], bordersData1);
			writesheet.addCell(label); 
			writesheet.setColumnView(i,13);
		}
		row_data++;
		return row_data;
	}


	public static void main (String[] args)throws SQLException, Exception
	{
		Report_Nomination Rpt = new Report_Nomination();

		//Rpt.NominationStatus(11,36,459,1);
		Rpt.NominationStatus(1,1,455,6403);
		
		/*
		if(Rpt.NominationStatus(1,0,0))
		System.out.println("RptA1 error");*/
			
	}
}	