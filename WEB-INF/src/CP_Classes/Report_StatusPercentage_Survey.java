package CP_Classes;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.vo.voUser;
import CP_Classes.vo.voDepartment;
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
 * 25/06/12	  Albert		   ~							Added this java file for creating report of surveys' status percentage 
 * 
 * 
 */
public class Report_StatusPercentage_Survey
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
	
	public Report_StatusPercentage_Survey()
	{

		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();
		user_Jenty = new User_Jenty();
	}
	
	public void write(String file_name) throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path() + "\\"+file_name;
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Percentage of Surveys' Status");
				
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
	
	
	public void Header(int SurveyID) throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List of Survey Status - Percentage Incomplete",cellBOLD);
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "Daftar Status Survei - Persentase Belum Selesai",cellBOLD);
		
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
		writesheet.setColumnView(0,35);

		label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , UnicodeHelper.getUnicodeStringAmp(OrgName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , UnicodeHelper.getUnicodeStringAmp(SurveyName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);

		writesheet.setPageSetup(PageOrientation.LANDSCAPE);	
	}
	
	public void HeaderWithRound(int SurveyID, int round) throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List of Survey Status - Percentage Incomplete",cellBOLD);
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "Daftar Status Survei - Persentase Belum Selesai",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		
		String CompName=" ";
		String OrgName =" ";
		String SurveyName = " ";
		String sRound = ""+round;
		
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
		writesheet.setColumnView(0,35);

		label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , UnicodeHelper.getUnicodeStringAmp(OrgName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , UnicodeHelper.getUnicodeStringAmp(SurveyName) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);

		label= new Label(0, row_title + 6, "Round:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 6, "Ronde:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		writesheet.mergeCells(0, row_title + 6, 1, row_title + 6);
		
		label= new Label(2, row_title + 6 , UnicodeHelper.getUnicodeStringAmp(sRound) ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,35);
		
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		
	}
	
	/**
         * Generate a report of survey status percentage based on the raters departments
         *  
         * @param SurveyID ID of the survey
         * @param PKUser UserID of user calling this method, for authentication checking
         * 
         */
	public boolean AllPercentage(int SurveyID, int PKUser, String file_name) throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
			
			String SurveyName="";
			write(file_name);
			Header(SurveyID);
			
			int row_data = 9;
			
			
			label= new Label(0, row_data-1, "Department",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(0, row_data-1, "Departemen",cellBOLD_Border);
			writesheet.addCell(label); 
			
			label= new Label(1, row_data-1, "Total No. of Raters",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(1, row_data-1, "Jumlah Penilai",cellBOLD_Border);
			writesheet.addCell(label); 
			
			label= new Label(2, row_data-1, "No. of Incomplete",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(2, row_data-1, "Jumlah Belum Selesai",cellBOLD_Border);
			writesheet.addCell(label); 
			
			label= new Label(3, row_data-1, "% Incomplete",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(3, row_data-1, "% Belum Selesai",cellBOLD_Border);
			writesheet.addCell(label); 
			
			int row = row_data+1;
			int no_Records = 1;
			
		
			AssignTarget_Rater ATR = new AssignTarget_Rater();
			Vector v = ATR.getAllRaterDepartment(SurveyID);
			
			Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
			votblSurvey vo = CE_Survey.getSurveyDetail(SurveyID);
			SurveyName = vo.getSurveyName();
			DecimalFormat df = new DecimalFormat("#.##");
			
			for(int i=0; i<v.size(); i++)
			{
				voDepartment voDept = (voDepartment)v.elementAt(i);
				
				int PKDepartment = voDept.getPKDepartment();
				String deptName = voDept.getDepartmentName();
				String totalRaters = ""+ATR.getTotalRaters(SurveyID,PKDepartment);
				String totalIncomplete = ""+ATR.getTotalRatersIncomplete(SurveyID, PKDepartment);
				String percentage = "";
				
				if(totalRaters.equals("0")){
					percentage = "0%";
				} else{
					percentage = ""+(df.format((ATR.getTotalRatersIncomplete(SurveyID,PKDepartment)/ATR.getTotalRaters(SurveyID,PKDepartment)*100))) +"%";
				}
									
				label = new Label(0,row, deptName,bordersData2);
				writesheet.addCell(label);
				
				label = new Label(1, row, totalRaters,bordersData2);
				writesheet.addCell(label);
				
				label= new Label(2, row, totalIncomplete ,bordersData1);
				writesheet.addCell(label);
				
				label= new Label(3, row, percentage,bordersData2);
				writesheet.addCell(label);

				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Surveys Status Percentage "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	/**
     * Generate a report of survey status percentage based on the raters departments
     *  
     * @param SurveyID ID of the survey
     * @param PKUser UserID of user calling this method, for authentication checking
     * 
     */
public boolean AllPercentageWithRound(int SurveyID, int PKUser, int round, String file_name) throws IOException, WriteException, SQLException, Exception{
	boolean IsNull = false;
	
	String SurveyName="";
	write(file_name);
	HeaderWithRound(SurveyID, round);
	
	int row_data = 11;
	
	
	label= new Label(0, row_data-1, "Department",cellBOLD_Border);
	if (server.LangVer == 2)
		label= new Label(0, row_data-1, "Departemen",cellBOLD_Border);
	writesheet.addCell(label); 
	
	label= new Label(1, row_data-1, "Total No. of Raters",cellBOLD_Border);
	if (server.LangVer == 2)
		label= new Label(1, row_data-1, "Jumlah Penilai",cellBOLD_Border);
	writesheet.addCell(label); 
	
	label= new Label(2, row_data-1, "No. of Incomplete",cellBOLD_Border);
	if (server.LangVer == 2)
		label= new Label(2, row_data-1, "Jumlah Belum Selesai",cellBOLD_Border);
	writesheet.addCell(label); 
	
	label= new Label(3, row_data-1, "% Incomplete",cellBOLD_Border);
	if (server.LangVer == 2)
		label= new Label(3, row_data-1, "% Belum Selesai",cellBOLD_Border);
	writesheet.addCell(label); 
	
	int row = row_data+1;
	int no_Records = 1;
	

	AssignTarget_Rater ATR = new AssignTarget_Rater();
	Vector v = ATR.getAllRaterDepartment(SurveyID, round);
	
	Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
	votblSurvey vo = CE_Survey.getSurveyDetail(SurveyID);
	SurveyName = vo.getSurveyName();
	DecimalFormat df = new DecimalFormat("#.##");
	
	for(int i=0; i<v.size(); i++)
	{
		voDepartment voDept = (voDepartment)v.elementAt(i);
		
		int PKDepartment = voDept.getPKDepartment();
		String deptName = voDept.getDepartmentName();
		String totalRaters = ""+ATR.getTotalRaters(SurveyID,PKDepartment,round);
		String totalIncomplete = ""+ATR.getTotalRatersIncomplete(SurveyID, PKDepartment,round);
		String percentage="";
		if(totalRaters.equals("0")){
			percentage = "0%";
		} else{
			percentage = ""+(df.format((ATR.getTotalRatersIncomplete(SurveyID,PKDepartment,round)/ATR.getTotalRaters(SurveyID,PKDepartment,round)*100))) +"%";
		}
									
		label = new Label(0,row, deptName,bordersData2);
		writesheet.addCell(label);
		
		label = new Label(1, row, totalRaters,bordersData2);
		writesheet.addCell(label);
		
		label= new Label(2, row, totalIncomplete ,bordersData1);
		writesheet.addCell(label);
		
		label= new Label(3, row, percentage,bordersData2);
		writesheet.addCell(label);

		no_Records++;
		row++;
		
	}
	
	workbook.write();
	workbook.close(); 
	
	sDetail = CE_Survey.getUserDetail(PKUser);
	ev.addRecord("Insert", itemName, "List Of Surveys Status Percentage "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
	
	if(no_Records == 0)
	IsNull = true;

	return IsNull;
}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_StatusPercentage_Survey Rpt = new Report_StatusPercentage_Survey();

		
		boolean flag = Rpt.AllPercentage(445,1,"");
		System.out.println("flag "+flag);
		
			
	}
}	