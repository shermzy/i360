package CP_Classes;

import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.WritableFontRecord;
import jxl.write.biff.WritableFonts;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.ScriptStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import CP_Classes.UnicodeHelper;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.votblOrganization;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Report_ListOfCompetencies
{
	/**
	 * Declaration
	 */
	 
	private Setting server;
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	/**
	 * Declaration of Variables
	 */
	
	private String sDetail[] = new String[13];
 	private String itemName = "Report";
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace, superscript;
	private WritableWorkbook workbook;
	private File inputWorkBook, outputWorkBook;
	private WritableCellFormat cellBOLD_Border;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold ;
	
	/**
	 * Creates a new intance of Create Edit Survey object.
	 */
	
	public Report_ListOfCompetencies()
	{
		server = new Setting();
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/*--------------------------------------To initialize the workbook and borders -------------------------------*/
	
	public void write() throws IOException, WriteException, BiffException 
	{
		String output = server.getReport_Path() + "\\ListOfCompetencies.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List of Competencies");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		superscript = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		superscript.setScriptStyle(ScriptStyle.SUPERSCRIPT);
	
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
	
/*--------------------------------------To initialize the headings -------------------------------*/	

	public void Header(int FKCompanyID, int FKOrganizationID) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Competencies", cellBOLD); 
		
		if (server.LangVer == 2)
			label = new Label(0, 0, "DAFTAR KOMPETENSI", cellBOLD);
			
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
						
		label= new Label(2, row_title, CompName, No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		
					
		if(FKOrganizationID != 0)
		{
			label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
			
			if (server.LangVer == 2)
				label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
				
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			//writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
			
			label= new Label(2, row_title + 2 , OrgName ,No_Borders);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			//writesheet.setRowView(row_title+2,15);
		}
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	/*------------------------Generate report based on all competencies-------------------------------------*/
	
	public boolean AllCompetencies(int FKCompanyID, int FKOrganizationID, int Definition, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		/* 	If FKOrganization = 0 ---> SHOW all competency from all the organizations that handle by this
		 *	consulting company
		 */ 
		 
		/* 	Definition 0:	List All Competencies
		 * 	Definition 1:	List All Competencies with Definitions
		 */

			write();
			Header(FKCompanyID, FKOrganizationID);		

			int row_data = 7;
			
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
			 
			
						
			if(FKOrganizationID == 0)
			{
				label= new Label(col+1, row_data, "Organisation Name",cellBOLD_Border); 
				
				if (server.LangVer == 2)
					label= new Label(col+1, row_data, "Nama Organisasi",cellBOLD_Border); 
					
				writesheet.addCell(label);
				writesheet.setColumnView(col+2,25);
				col = col+1;
			}

			label= new Label(col+1, row_data, "System Generated",cellBOLD_Border);
			
			if (server.LangVer == 2)
				label= new Label(col+1, row_data, "Buatan Sistem",cellBOLD_Border);
				
			writesheet.addCell(label);
			writesheet.setColumnView(col+1,12);
			
			int row = row_data+1;
			int no_Records = 1;
			String SysGen =" ";
			
			Competency Comp = new Competency();
			
			Vector vComp = Comp.getAllCompetencies(FKCompanyID, FKOrganizationID);
			
			for(int i=0; i<vComp.size(); i++) 
			{
				voCompetency vo = (voCompetency)vComp.elementAt(i);
				String str_no_Records = String.valueOf(no_Records);
				String compName = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());
				String compDef = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyDefinition());
				int isSystemGenerated = vo.getIsSystemGenerated();
				String db_OrganizationName = UnicodeHelper.getUnicodeStringAmp(vo.getOrganisationName());
				
				if(isSystemGenerated == 1)
				{
					SysGen = "Yes";
					if (server.LangVer == 2)
						SysGen = "Ya";
				}
				else
				{
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
				
				if(FKOrganizationID == 0)
				{
					label = new Label(db_col+1,row, db_OrganizationName,bordersData2);
					writesheet.addCell(label);
					db_col = db_col+1;
				}
				
				label = new Label(db_col+1,row, SysGen, bordersData1);
				writesheet.addCell(label);
				
				no_Records++;
				row++;
				
			}
			
			workbook.write();
			workbook.close(); 
			
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Competencies", sDetail[2], sDetail[11], sDetail[10]);
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	/*------------------------Generate report based on all competencies with key behaviour-------------------------------------*/
	
	public boolean AllCompetencies_KeyBehav(int FKCompanyID, int FKOrganizationID, int PKUser) 
		throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
		
		write();
		Header(FKCompanyID, FKOrganizationID);
		
		int row = 7;
		int CompID = 0;
		int no_Records = 1;
		int no_Records_key = 1;
		int col =0;
		
		writesheet.setColumnView(0,6);
		writesheet.setColumnView(1,12);			
		writesheet.setColumnView(2,8);
		writesheet.setColumnView(3,90);
		
		//27 May 2008 by Hemilda - The headlines of column  in the wrong place
		if(FKOrganizationID != 0)
		{
			col = col + 1;
		}
		if(FKOrganizationID == 0)
		{
			label= new Label(col, row-2, "Organization Name",cellBOLD); 
			
			if (server.LangVer == 2)
				label= new Label(col, row-2, "Nama Organisasi",cellBOLD); 
				
			writesheet.addCell(label);
			writesheet.setColumnView(col,11);
			col = col+1;
		}

		label= new Label(col-1, row, "No.",cellBOLD_Border);
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
				
		//27 May 2008 by Hemilda - The headlines of column  in the wrong place
		col++;		
		label= new Label(col, row, "Organization Name",cellBOLD_Border); 
		if (server.LangVer == 2)
			label= new Label(col, row, "Nama Organisasi",cellBOLD); 
		writesheet.addCell(label);
		
		col++;	
		label= new Label(col, row, "System Generated",cellBOLD_Border);
		
		if (server.LangVer == 2)
			label= new Label(col, row, "Buatan Sistem",cellBOLD_Border);
		
		writesheet.addCell(label);
		writesheet.setColumnView(col,11);
		
		row = row +2;
		
		Competency Comp = new Competency();
		Vector vComp = Comp.getAllCompetencies(FKCompanyID, FKOrganizationID);
		
		for(int i=0; i<vComp.size(); i++) 
		{
			voCompetency vo = (voCompetency)vComp.elementAt(i);
			String str_no_Records = String.valueOf(no_Records);
			CompID = vo.getPKCompetency();
			String compName = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());
			String compDef = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyDefinition());
			int isSystemGenerated = vo.getIsSystemGenerated();
			String db_OrganizationName = UnicodeHelper.getUnicodeStringAmp(vo.getOrganisationName());
			
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
			
			String SysGen = "";
			if(isSystemGenerated == 1) {
				SysGen = "Yes";
				if (server.LangVer == 2)
				SysGen = "Ya";
			} else {
				SysGen = "No";
				if (server.LangVer == 2)
				SysGen = "Bukan";
			}
			
			db_col = db_col+1;
			
			//27 May 2008 by Hemilda - The headlines of column  in the wrong place
			if(FKOrganizationID == 0)
			{
				label = new Label(db_col+1,row, db_OrganizationName,No_Borders);
			}else{
				label = new Label(db_col+1,row, db_OrganizationName,No_Borders);
			}
			writesheet.addCell(label);
			db_col = db_col+1;
			
			label = new Label(db_col+1,row, SysGen, No_Borders);

			writesheet.addCell(label);
			writesheet.setColumnView(db_col,10);
			
			db_col = db_col+1;
			row = row+2;
	
			KeyBehaviour KB = new KeyBehaviour();
	
			Vector vKB = KB.getKBList(CompID);
			
			for(int j=0; j<vKB.size(); j++)
			{	
				voKeyBehaviour voKB = (voKeyBehaviour)vKB.elementAt(j);
				
				String str_no_Records_key = String.valueOf(no_Records_key);
				String keyBehavName = UnicodeHelper.getUnicodeStringAmp(voKB.getKeyBehaviour());
				int isSystemGenerated_key = voKB.getIsSystemGenerated();
				
				String SysGen_key = "";
				if(isSystemGenerated_key == 1) {
					SysGen_key  = "Yes";
					if (server.LangVer == 2)
						SysGen_key = "Ya";
				} else {
					SysGen_key  = "No";
					if (server.LangVer == 2)
						SysGen_key  = "Bukan";
				}
				
				//System.out.println("KB----"+isSystemGenerated_key+"----"+SysGen_key );
				label = new Label(0,row, str_no_Records_key,No_Borders_ctrAll);
				writesheet.addCell(label);
				
				label = new Label(3,row, keyBehavName,No_Borders_NoBold);
				writesheet.addCell(label);
				
						
				label = new Label(db_col,row, SysGen_key , No_Borders_NoBold);
				
				writesheet.addCell(label);
				
				
				no_Records_key++;
				row++;
				
			}	
			
						
			no_Records++;
			row++;
		}	
		
			workbook.write();
			workbook.close(); 
			
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Of Competencies with Key Behaviour", sDetail[2], sDetail[11], sDetail[10]);

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
		Report_ListOfCompetencies Rpt = new Report_ListOfCompetencies();

		Rpt.AllCompetencies_KeyBehav(1,0,7499);
		Rpt.AllCompetencies(1,1,0,5);
		
		System.out.println("DONE");
		
		/*
		if(Rpt.AllCompetencies(1,0,0))
		System.out.println("RptA1 error");*/
			
	}
}	