package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
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

public class ExportDRARes
{
	private Database db;
	private Setting server;
	private Create_Edit_Survey CE_Survey;
	private EventViewer ev;
	
	private String sDetail[] = new String[13];
	private String itemName = "Export";
	
	private Label label;
	private WritableSheet writesheet;
	private WritableSheet writesheet2;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat bordersData3;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold ;
	private File inputWorkBook, outputWorkBook;
	
	public ExportDRARes()
	{
		db = new Database();
		server = new Setting();
		CE_Survey = new Create_Edit_Survey();
		ev = new EventViewer();
	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\List Of Development Resources.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List Of Development Resources");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold); 
		
		cellBOLD_Border = new WritableCellFormat(fontBold); 
		cellBOLD_Border.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_Border.setAlignment(Alignment.CENTRE);
		cellBOLD_Border.setWrap(true);
		cellBOLD_Border.setBackground(Colour.GRAY_25);

		bordersData1 = new WritableCellFormat();
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.CENTRE);
		
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		
		bordersData3 = new WritableCellFormat(fontFace);
		bordersData3.setAlignment(Alignment.LEFT);
		bordersData3.setWrap(true);
		
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
		

		//Edited by Su See
		int col = 0;
		writesheet2 = workbook.createSheet("ToBeDeleted", 1); 
		label= new Label(col, 0 , "Competency Name", cellBOLD_Border);
		
		writesheet2.addCell(label);
		//writesheet.setColumnView(2,25);
		
		col +=1;
		label= new Label(col, 0 , "Resource", cellBOLD_Border);
		writesheet2.addCell(label);
		//writesheet2.setColumnView(3,35);
		
		col +=1;
		label= new Label(col, 0 , "Type", cellBOLD_Border);
		writesheet2.addCell(label);
		//writesheet2.setColumnView(5,20);
		
	}
	
	public void Header(int OrgID) throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Development Resources",cellBOLD);
		if (server.LangVer == 2)
			label = new Label(0, 0, "Daftar Sumber-Sumber Perkembangan",cellBOLD);
		
		writesheet.addCell(label);
		writesheet.mergeCells(0, 0, 2, 0);
		
		String CompName=" ";
		String extract_company=" ";
		String OrgName =" ";
		
		extract_company = "SELECT * FROM tblConsultingCompany c, tblOrganization o WHERE c.CompanyID = o.FKCompanyID AND o.PKOrganization = " + OrgID + "";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(extract_company);
           
        	if(rs.next())
    		{
    			CompName = rs.getString("CompanyName");
    			OrgName = rs.getString("OrganizationName");
    		}
			
        }
        catch(Exception E) 
        {
            System.err.println("ExportDRA.java - Header - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
			
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
		
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		
		/*Date timestamp = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		*/	
	}
	
	public boolean AllResources(int CompanyID, int OrgID, int PKUser) throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;
					
			write();
			Header(OrgID);		

			int row_data = 6;
			
			label= new Label(0, row_data, "S/No.",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);
			
			label= new Label(1, row_data, "Competency Name",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(1, row_data, "Nama Kompetensi",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(1,25);
			//Edited by Su See (Remove all merge cells)
			//writesheet.mergeCells(1, row_data, 2, row_data);
						
			label= new Label(2, row_data, "Resource",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(2, row_data, "Sumber",cellBOLD_Border);
			
			writesheet.addCell(label);
			//col n width
			writesheet.setColumnView(2,70);
			//writesheet.mergeCells(3, row_data, 4, row_data);
			
			label= new Label(3, row_data, "Type", cellBOLD_Border);
			if (server.LangVer == 2)
				label = new Label(3, row_data, "Tipe", cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(3,18);
			
			label= new Label(4, row_data, "System Generated",cellBOLD_Border);
			if (server.LangVer == 2)
				label= new Label(4, row_data, "Buatan Sistem",cellBOLD_Border);
			
			writesheet.addCell(label);
			writesheet.setColumnView(4,12);
			
			int row = row_data+1;
			int no_Records = 1;
			String ResType = " ";
			String SysGen =" ";
			
//			String Sql = "SELECT c.CompetencyName, d.Resource, d.ResType, d.IsSystemGenerated FROM tblDRARes d INNER JOIN Competency c ON d.CompetencyID = c.PKCompetency";
//			Sql = Sql +" WHERE (d.FKCompanyID = "+CompanyID+") AND (d.FKOrganizationID = "+OrgID+")";
//			Sql = Sql +" ORDER BY c.CompetencyName";
//			Edited By Su See 07-05-2005
			
			String command = "SELECT Competency.CompetencyName, tblDRARes.Resource, tblDRARes.ResType, tblDRARes.IsSystemGenerated";
			command += " FROM tblDRARes INNER JOIN tblOrigin ON tblDRARes.IsSystemGenerated = tblOrigin.PKIsSystemGenerated INNER JOIN Competency ON tblDRARes.CompetencyID = Competency.PKCompetency";
			command +=" WHERE (tblDRARes.FKCompanyID = "+ CompanyID +") AND (tblDRARes.FKOrganizationID = "+ OrgID + ") OR (tblDRARes.IsSystemGenerated = 1)";
			command +=" ORDER BY tblDRARes.IsSystemGenerated, Competency.CompetencyName, tblDRARes.ResType, tblDRARes.Resource";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

	        try
	        {          
	        	con=ConnectionBean.getConnection();
	        	st=con.createStatement();
	        	rs=st.executeQuery(command);
	           
	        	while(rs.next())	
				{
					String str_no_Records = String.valueOf(no_Records);
					String compName = UnicodeHelper.getUnicodeStringAmp(rs.getString("CompetencyName"));
					String resource = UnicodeHelper.getUnicodeStringAmp(rs.getString("Resource"));
					int RES = rs.getInt("ResType");
					int isSystemGenerated = rs.getInt("IsSystemGenerated");
					
					if (server.LangVer == 1) {
						if(RES == 1)
							ResType = "Book";
						else if(RES == 2)
							ResType = "Web Resource";
						else if(RES == 3)
							ResType = "Training Course";
						else if(RES == 4)
							ResType = "AV Resource"; // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
					}
					else if(server.LangVer == 2) {
						if(RES == 1)
							ResType = "Buku";
						else if(RES == 2)
							ResType = "Sumber Situs";
						else if(RES == 3)
							ResType = "Kursus Pelatihan";
						else if(RES == 4)
							ResType = "Sumber In-House";
					}
					
					//Edited By Su See
					if(isSystemGenerated == 1)
					{
						SysGen = "Yes";
						if (server.LangVer == 2)
							SysGen = "Ya";
					}
					else
					{	SysGen = "No";
						if (server.LangVer == 2)
							SysGen = "Bukan";
					}
					
					label = new Label(0,row, str_no_Records, bordersData1);
					writesheet.addCell(label);
					
					label = new Label(1,row, compName, bordersData2);
					writesheet.addCell(label);
					//writesheet.mergeCells(1, row, 2, row);
																	
					label= new Label(2, row, resource, bordersData2);
					writesheet.addCell(label);
					//writesheet.mergeCells(3, row, 4, row);				
					
					label = new Label(3, row, ResType, bordersData1);
					writesheet.addCell(label);
					
					
					label = new Label(4, row, SysGen, bordersData1);
					writesheet.addCell(label);
					
					no_Records++;
					row++;
				}
				
	        }
	        catch(Exception E) 
	        {
	            System.err.println("ExportDRA.java - AllResources - " + E);
	        }
	        finally
	        {
		        ConnectionBean.closeRset(rs); //Close ResultSet
		        ConnectionBean.closeStmt(st); //Close statement
		        ConnectionBean.close(con); //Close connection
		
	        }
		
			workbook.write();
			workbook.close(); 
			
			sDetail = CE_Survey.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, "List Development Resources", sDetail[2], sDetail[11], sDetail[10]);

			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExportDRARes Rpt = new ExportDRARes();
		Rpt.AllResources(6403,1,1);
	}
}