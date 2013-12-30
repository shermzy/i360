package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.vo.votblOrganization;
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


public class Report_ListOfSurveys
{
	private Setting server;
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Report";
	
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
	
	public Report_ListOfSurveys()
	{
		server = new Setting();
		ev = new EventViewer();
		user = new Create_Edit_Survey();

	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path() + "\\List Of Surveys.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List Of Surveys");
		
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
	
	
	public void Header(int FKCompanyID, int FKOrganizationID) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Surveys",cellBOLD);
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "DAFTAR SURVEI",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		
		String CompName=" ";
		String OrgName =" ";
		
		Organization Org = new Organization();
		
		votblOrganization voOrg = Org.getAllOrganizations(FKCompanyID, FKOrganizationID);
		
		CompName = voOrg.getCompanyName();
		
		if(FKOrganizationID != 0)
			OrgName = voOrg.getOrganizationName();
			
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
		
					
		if(FKOrganizationID != 0)
		{
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
		}
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	
	public boolean AllSurveys(int FKCompanyID, int FKOrganizationID, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		 
			write();
			Header(FKCompanyID, FKOrganizationID);		

			int row_data = 7;
			
			label= new Label(0, row_data, "S/No.",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			
			
			label= new Label(1, row_data, "Job Position",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(1, row_data, "Posisi Pekerjaan",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(1,18);
			writesheet.setColumnView(2,20);
			writesheet.mergeCells(1, row_data, 2, row_data);
			

			
			label= new Label(3, row_data, "Survey Name",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(3, row_data, "Nama Survei",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(3,42);
			//writesheet.mergeCells(3, row_data, 4, row_data);

			
			int col = 3;
			
			if(FKOrganizationID == 0)
			{
				label= new Label(col+1, row_data, "Organisation Name",cellBOLD_Border); 
				writesheet.addCell(label);
				writesheet.setColumnView(col+1,25);
				col = col+1;
			}

			
			int row = row_data+1;
			int no_Records = 1;
				
			Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
			
			Vector vSurvey = CE_Survey.getSurveyDetail(FKCompanyID, FKOrganizationID);
			for(int i=0; i<vSurvey.size(); i++) {
				votblSurvey vo = (votblSurvey)vSurvey.elementAt(i);
				
				String str_no_Records = String.valueOf(no_Records);
				String SurveyName = vo.getSurveyName();
				//String MonthYear = month_view.format(rs_comp.getDate("MonthYear"));
				String db_OrganizationName = vo.getOrganizationName();
				String JobPos = vo.getJobPosition();
				
				label = new Label(0,row, str_no_Records,bordersData1);
				writesheet.addCell(label);
				
				label = new Label(1,row, JobPos,bordersData2);
				writesheet.addCell(label);
				writesheet.mergeCells(1, row, 2, row);
								
			//	label= new Label(3, row, MonthYear ,bordersData1);
			//	writesheet.addCell(label);
				
				label= new Label(3, row, UnicodeHelper.getUnicodeStringAmp(SurveyName),bordersData2);
				writesheet.addCell(label);
										
				int db_col = 3;
								
	
				if(FKOrganizationID == 0)
				{
					label = new Label(db_col+1,row, UnicodeHelper.getUnicodeStringAmp(db_OrganizationName),bordersData2);
					writesheet.addCell(label);
					db_col = db_col+1;
				}
				
			
				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Surveys", sDetail[2], sDetail[11], sDetail[10]);
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ListOfSurveys Rpt = new Report_ListOfSurveys();

		
		Rpt.AllSurveys(10,20,3);
		
			
	}
}	