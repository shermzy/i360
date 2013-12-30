package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.votblSurvey;
import CP_Classes.vo.votblSurveyCompetency;

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


public class Report_ListOfCompetencies_Survey
{
	private Setting server;
	private Create_Edit_Survey CE_Survey;
	private EventViewer ev;
	
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
	
	public Report_ListOfCompetencies_Survey()
	{
		server = new Setting();
		CE_Survey = new Create_Edit_Survey();
		ev = new EventViewer();
	}
	
	public void write(String Survey_Name) throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\List Of Competencies for Specific Survey.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List of Competencies For Specific Survey");
		
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
		No_Borders.setWrap(true);
		
		No_Borders_NoBold = new WritableCellFormat(fontFace);
		No_Borders_NoBold.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_NoBold.setWrap(true);
		
	}
	
	
	public void Header(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Competencies",cellBOLD); 
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "Daftar Kompetensi",cellBOLD); 
		
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
						
		label= new Label(2, row_title, CompName, No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);

		label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , OrgName ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		//writesheet.setRowView(row_title+2,15);
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		
		if (server.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , SurveyName ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		//writesheet.setRowView(row_title+4,15);

		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
	}
	
	
	public boolean AllCompetencies(int SurveyID, int Definition, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		 
		/* 	Definition 0:	List All Competencies
		 * 	Definition 1:	List All Competencies with Definitions
		 */
		
		String Survey_Name="";

		votblSurvey voSurvey = CE_Survey.getSurveyDetail(SurveyID);
		
		Survey_Name = voSurvey.getSurveyName();
		
			
			write(Survey_Name);
			Header(SurveyID);		

			int row_data = 8;
			
			label= new Label(0, row_data, "S/No.",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			//writesheet.setRowView(row_data,25);			
			
			label= new Label(1, row_data, "Competency Name",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(1, row_data, "Nama Kompetensi",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(2,25);
			writesheet.mergeCells(1, row_data, 2, row_data);
			
			int col = 2;
			
			if(Definition == 1)
			{
				label= new Label(col+1, row_data, "Definition",cellBOLD_Border);
				
				if (server.LangVer == 2)
					label= new Label(col+1, row_data, "Definisi",cellBOLD_Border);
				
				writesheet.addCell(label);
				writesheet.setColumnView(col+1,35);
				writesheet.setColumnView(col+2,35);
				writesheet.mergeCells(col+1, row_data, col+2, row_data);
				col = col+2;
			}
			 
			
			label= new Label(col+1, row_data, "System Generated",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(col+1, row_data, "Buatan Sistem",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(col+1,12);
			
			int row = row_data+1;
			int no_Records = 1;
			String SysGen =" ";
					
			SurveyCompetency SC = new SurveyCompetency();
			Vector vComp = SC.getSurveyCompetency(SurveyID);
			
			for(int i=0; i<vComp.size(); i++)	
			{
				votblSurveyCompetency vo = (votblSurveyCompetency)vComp.elementAt(i);
				String str_no_Records = String.valueOf(no_Records);
				String compName = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());
				String compDef = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyDefinition());
				int isSystemGenerated = vo.getIsSystemGenerated();
				
				if(isSystemGenerated == 1) {				
					SysGen = "Yes";
					if (server.LangVer == 2)
						SysGen = "Ya";
				}else {				
					SysGen = "No";
					if (server.LangVer == 2)
						SysGen = "Bukan";
				}
				
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
					writesheet.setColumnView(2,30);
					writesheet.setRowView(row, countTotalRow(compDef,100));
					
					writesheet.mergeCells(db_col+1, row, db_col+2, row);
					db_col = db_col+2;
				}
				
				
				label = new Label(db_col+1,row, SysGen, bordersData1);
				writesheet.addCell(label);
				
				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Competencies for "+Survey_Name, sDetail[2], sDetail[11], sDetail[10]);

			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public boolean AllCompetencies_KeyBehav(int SurveyID, int PKUser) throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		String Survey_Name="";

		votblSurvey voSurvey = CE_Survey.getSurveyDetail(SurveyID);
		
		Survey_Name = voSurvey.getSurveyName();
		
			
		write(Survey_Name);
		Header(SurveyID);
		
		int row = 8;
		int CompID = 0;
		int no_Records = 1;
		int no_Records_key = 1;
		String SysGen =" ";
		int col = 0;
		
		writesheet.setColumnView(0,6);
		writesheet.setColumnView(1,13);			
		writesheet.setColumnView(2,10);
		writesheet.setColumnView(3,90);
		
		label= new Label(col++, row, "No.",cellBOLD_Border);
		writesheet.addCell(label);
		
		label= new Label(col, row, "Competency",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(col, row, "Kompetensi",cellBOLD_Border);
		
		writesheet.addCell(label);
		
		col++;
		label= new Label(col, row, "Definition",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(col, row, "Definisi",cellBOLD_Border);
		
		writesheet.addCell(label);
		
		col++;
		label= new Label(col, row, "Key Behaviour",cellBOLD_Border);
		if (server.LangVer == 2)
			label= new Label(col, row, "Perilaku Kunci",cellBOLD_Border);
		
		writesheet.addCell(label);
			
		col++;	
		label= new Label(col, row, "System Generated",cellBOLD_Border);
		
		if (server.LangVer == 2)
			label= new Label(col, row, "Buatan Sistem",cellBOLD_Border);
		
		writesheet.addCell(label);
		writesheet.setColumnView(col,11);
		
		row = row +2;
		
		SurveyCompetency SC = new SurveyCompetency();
		Vector vComp = SC.getSurveyCompetency(SurveyID);
		
		for(int i=0; i<vComp.size(); i++)	
		{
			votblSurveyCompetency vo = (votblSurveyCompetency)vComp.elementAt(i);
			String str_no_Records = String.valueOf(no_Records);
			String compName = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());
			String compDef = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyDefinition());
			int isSystemGenerated = vo.getIsSystemGenerated();
			
			CompID = vo.getCompetencyID();
				
			label = new Label(0,row, str_no_Records,No_Borders_ctrAll_Bold);
			writesheet.addCell(label);
			
			label = new Label(1,row, compName,No_Borders);
			writesheet.addCell(label);
			writesheet.mergeCells(1, row, 3, row);
										
			int db_col = 2;
							
			label= new Label(db_col, row+1, compDef, No_Borders);
			writesheet.addCell(label);
			writesheet.setRowView(row+1, countTotalRow(compDef,100));
			writesheet.mergeCells(db_col, row+1, db_col+1, row+1);
			
			if(isSystemGenerated == 1) {			
				SysGen = "Yes";
				if (server.LangVer == 2)
					SysGen = "Ya";
			}else {			
				SysGen = "No";
				if (server.LangVer == 2)
					SysGen = "Bukan";
			}
			
			db_col += 2;
			
			label = new Label(db_col,row, SysGen, No_Borders);
			writesheet.addCell(label);
			writesheet.setColumnView(db_col,10);
			
			//db_col = db_col+1;
			row = row+2;
	
			KeyBehaviour KB = new KeyBehaviour();
			
			Vector vKB = KB.getKBList(CompID);
			
			for(int j=0; j<vKB.size(); j++)
			{	
				voKeyBehaviour voKB = (voKeyBehaviour)vKB.elementAt(j);
				
				String str_no_Records_key = String.valueOf(no_Records_key);
				String keyBehavName = UnicodeHelper.getUnicodeStringAmp(voKB.getKeyBehaviour());
				int isSystemGenerated_key = voKB.getIsSystemGenerated();
		
				if(isSystemGenerated_key == 1) {
				
					SysGen = "Yes";
					if (server.LangVer == 2)
						SysGen = "Ya";
				} else {
				
					SysGen = "No";
					if (server.LangVer == 2)
						SysGen = "Bukan";
				}
				
				label = new Label(0,row, str_no_Records_key,No_Borders_ctrAll);
				writesheet.addCell(label);
				
				label = new Label(3,row, keyBehavName,No_Borders_NoBold);
				writesheet.addCell(label);
							
				label = new Label(db_col,row, SysGen, No_Borders_NoBold);
				writesheet.addCell(label);
				
				
				no_Records_key++;
				row++;
				
			}	
			
						
			no_Records++;
			row++;
		}	
		
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Competencies with Key Behaviour for "+Survey_Name, sDetail[2], sDetail[11], sDetail[10]);
	
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public int countTotalRow(String input, double totalChar) {
		
		
		double merge = (double)input.length() / (double)totalChar;
		BigDecimal BD = new BigDecimal(merge);
		BD.setScale(0, BD.ROUND_UP);
		BigInteger BI = BD.toBigInteger();
		int totalMerge = BI.intValue() + 1;
		
		//Edited by Roger 2 July 2008
		//Fix the cell height problem of competency list
		int rowView = 300;
		int mergeView = rowView * totalMerge;
				
		return mergeView;
	}

	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ListOfCompetencies_Survey Rpt = new Report_ListOfCompetencies_Survey();
		
		Rpt.AllCompetencies(445,2,5);

	}
}	