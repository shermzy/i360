package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;

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
import jxl.format.PageOrientation;

public class Report_ListOfRaters_Survey
{
	private Database db;
	private Setting server;
	private User user;
	
	private EventViewer ev;
	private Create_Edit_Survey CE_Survey;
	
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
	
	public Report_ListOfRaters_Survey()
	{
		db = new Database();
		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();

	}
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\List Of Raters for Specific Survey.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("List Of Raters For Specific Survey"); 
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
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
		Label label = new Label(0, 0, "List Of Raters For Specific Survey",cellBOLD); 
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
		
		
		String CompName=" ";
		String extract_company=" ";
		String OrgName =" ";
		String SurveyName = " ";
		
		extract_company = "SELECT * FROM tblConsultingCompany a, tblOrganization b, tblSurvey c WHERE a.CompanyID = b.FKCompanyID AND c.FKOrganization = b.PKOrganization AND SurveyID = "+ SurveyID;
			
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
    			SurveyName = rs.getString("SurveyName");
    		}
            
        }
        catch(Exception E) 
        {
            System.err.println("Report_ListOfRaters_Survey.java - Header - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		int row_title = 2;
		
		label= new Label(0, row_title, "Company:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.setColumnView(1,15);
		writesheet.mergeCells(0, row_title, 1, row_title);
						
		label= new Label(2, row_title, CompName, No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);

		label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 2, 1, row_title + 2);
		
		label= new Label(2, row_title + 2 , OrgName ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.setRowView(row_title+2,15);
		
		label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.mergeCells(0, row_title + 4, 1, row_title + 4);
		
		label= new Label(2, row_title + 4 , SurveyName ,No_Borders);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,6);
		writesheet.setRowView(row_title+4,15);	
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setPageSetup(PageOrientation.LANDSCAPE);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright �3-Sixty Profiler�is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	/**
	 * 
	 * @param SurveyID
	 * @param PKUser
	 * @return
	 * @throws IOException
	 * @throws WriteException
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean AllRaters(int SurveyID, int PKUser) throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;

		String SurveyName="";
		write();
		Header(SurveyID);		

		int row_data = 9;
		
		label= new Label(0, row_data-1, "Target",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, row_data-1, 3, row_data-1);
		
		label= new Label(4, row_data-1, "Rater",cellBOLD_Border);
		writesheet.addCell(label); 
		writesheet.mergeCells(4, row_data-1, 8, row_data-1);
		
		/* ---------------------------------------------START: Target-------------------------------------*/
		
		label= new Label(0, row_data, "Department",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(0,15);
		
		label= new Label(1, row_data, "Other Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(1,13);
		
		label= new Label(2, row_data, "Family Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(2,13);

		label= new Label(3, row_data, "Login Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(3,13);
		
		/* ---------------------------------------------START: Rater-------------------------------------*/
		
		label= new Label(4, row_data, "Department",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(4,13);
		
		label= new Label(5, row_data, "Other Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(5,13);
		
		label= new Label(6, row_data, "Family Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(6,13);
		
		label= new Label(7, row_data, "Rater Relation",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(7,13);
		
		label= new Label(8, row_data, "Login Name",cellBOLD_Border);
		writesheet.addCell(label);
		writesheet.setColumnView(8,13);
	
		int row = row_data+1;
		int no_Records = 1;
		
		String Relation=" ";
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
    			SurveyName = rs.getString("SurveyName");
    			String RaterCode = rs.getString("RaterCode");
    			int RaterLoginID = rs.getInt("RaterLoginID");
    			int TargetLoginID = rs.getInt("TargetLoginID");
    			int NameSequence = rs.getInt("NameSequence");
    			
    			if(RaterCode.equals("SUP"))
    				Relation = "Superior";
    			else if(RaterCode.equals("SELF"))
    				Relation = "Self";
    			else if(RaterCode.equals("OTH"))
    				Relation = "Others";
    			else if(RaterCode.equals("ADD"))
    				Relation = "Additional Rater"; //edited by Lingtong
    			
    			TargetDetail = user.getUserDetail(TargetLoginID, NameSequence);
    			RaterDetail = user.getUserDetail(RaterLoginID, NameSequence);

    /* -------------------------------------------------------START: Target----------------------------------------------*/									
    			label = new Label(0,row, TargetDetail[6],bordersData2);
    			writesheet.addCell(label);
    			
    			label = new Label(1,row, TargetDetail[1],bordersData2);
    			writesheet.addCell(label);
    							
    			label= new Label(2, row, TargetDetail[0] ,bordersData1);
    			writesheet.addCell(label);
    			
    			label= new Label(3, row, TargetDetail[2],bordersData2);
    			writesheet.addCell(label);

    /* -------------------------------------------------------START: Rater----------------------------------------------*/													
    			label= new Label(4, row, RaterDetail[6],bordersData2);
    			writesheet.addCell(label);
    			
    			label= new Label(5, row, RaterDetail[1],bordersData2);
    			writesheet.addCell(label);
    			
    			label= new Label(6, row, RaterDetail[0],bordersData2);
    			writesheet.addCell(label);
    			
    			label= new Label(7, row, Relation,bordersData2);
    			writesheet.addCell(label);
    			
    			label= new Label(8, row, RaterDetail[2],bordersData2);
    			writesheet.addCell(label);

    			no_Records++;
    			row++;			
    		}
    		
            
        }
        catch(Exception E) 
        {
            System.err.println("Report_ListOfRaters_Survey.java - AllRaters - " + E);
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
		ev.addRecord("Insert", itemName, "List Of Raters for Survey "+SurveyName, sDetail[2], sDetail[11], sDetail[10]);
		
		if(no_Records == 0)
		IsNull = true;

		return IsNull;
	}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ListOfRaters_Survey Rpt = new Report_ListOfRaters_Survey();

		Rpt.AllRaters(352,3);
	}
}	