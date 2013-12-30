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
import jxl.format.PageOrientation;

public class Report_Deleted_ListOfRatersStatus_Survey
{
	private Setting server;
	private User user;
	
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
	
	public Report_Deleted_ListOfRatersStatus_Survey()
	{
		server = new Setting();
		user = new User();
	}
	
	public void write(String Survey_Name) throws IOException, WriteException, BiffException
	{
		String output = server.getDeleted_Path()+"\\ListOfDeletedRatersForSurvey_"+Survey_Name+".xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
		
		writesheet = workbook.getSheet(0); 
		writesheet.setName("List Of Deleted Raters Specific Survey");
		
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
	
	public void Header(String CompName, String OrgName, String SurveyName) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, 0, "List Of Deleted Raters Specific Survey",cellBOLD); 
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 2, 0);
					
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
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		

	}
	
	
	public boolean AllRaters(int SurveyID, int IsDeletedSurvey) 
		throws IOException, WriteException, SQLException, Exception
	{
		/* IsDeletedSurvey  = 0 -->Not deleted
		 * 					= 1 -->Deleted
		 **/
			
			boolean IsNull = false;
	
			String SurveyName="";
			String CompName=" ";
			String OrgName =" ";
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			if(IsDeletedSurvey == 0)
			{
				String extract_company = "SELECT * FROM tblConsultingCompany a, tblOrganization b, tblSurvey c WHERE a.CompanyID = b.FKCompanyID AND c.FKOrganization = b.PKOrganization AND SurveyID = "+ SurveyID;
				
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
		            System.err.println("Report_Deleted_ListOfRatersStatus.java - AllRaters- " + E);
		        }
		        finally
		        {
		        	ConnectionBean.closeRset(rs); //Close ResultSet
		        	ConnectionBean.closeStmt(st); //Close statement
		        	ConnectionBean.close(con); //Close connection
		        }
				
			}
			else if(IsDeletedSurvey == 1)
			{
				String sqlcommand1 = "SELECT * FROM tblConsultingCompany a,tblOrganization b,tblDeletedSurvey c WHERE a.CompanyID = b.FKCompanyID AND c.FKOrganization = b.PKOrganization AND DeletedSurveyID = "+ SurveyID;
							
				try
		        {          

					con=ConnectionBean.getConnection();
					st=con.createStatement();
					rs=st.executeQuery(sqlcommand1);

					if(rs.next())	
					{
						CompName = rs.getString("CompanyName");
						OrgName = rs.getString("OrganizationName");
						SurveyName = rs.getString("SurveyName");
					}
		        }
		        catch(Exception E) 
		        {
		            System.err.println("Report_Deleted_ListOfRatersStatus.java - AllRaters- " + E);
		        }
		        finally
		        {
		        	ConnectionBean.closeRset(rs); //Close ResultSet
		        	ConnectionBean.closeStmt(st); //Close statement
		        	ConnectionBean.close(con); //Close connection
		        }
			}
			
			write(SurveyName);
			Header(CompName,OrgName,SurveyName);		

			int row_data = 9;
			
			
			
			label= new Label(0, row_data-1, "Target",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.mergeCells(0, row_data-1, 3, row_data-1);
			
			label= new Label(4, row_data-1, "Rater",cellBOLD_Border);
			writesheet.addCell(label); 
			writesheet.mergeCells(4, row_data-1, 9, row_data-1);
			
			/* ---------------------------------------------START: Target-------------------------------------*/
			
			label= new Label(0, row_data, "Department",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(0,13);
			
			label= new Label(1, row_data, "Other Name",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(1,12);
			
			label= new Label(2, row_data, "Family Name",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(2,12);

			label= new Label(3, row_data, "Login Name",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(3,10);
			
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
			writesheet.setColumnView(8,10);
			
			label= new Label(9, row_data, "Rater Status",cellBOLD_Border);
			writesheet.addCell(label);
			writesheet.setColumnView(9,13);			
			
			int row = row_data+1;
			int no_Records = 0;
			
			String Relation=" ";
			String Status=" ";
			String TargetDetail[] = new String[14];
			String RaterDetail[] = new String[14];
			String Sql="";
			
			if(IsDeletedSurvey == 0)
			{		
				Sql= "SELECT * FROM tblSurvey a INNER JOIN tblDeletedAssignment ON a.SurveyID = tblDeletedAssignment.SurveyID INNER JOIN";
	            Sql = Sql + " tblOrganization ON a.FKOrganization = tblOrganization.PKOrganization INNER JOIN";
	            Sql = Sql + " [User] ON tblOrganization.PKOrganization = [User].FKOrganization AND tblDeletedAssignment.TargetLoginID = [User].PKUser";
				Sql = Sql + " WHERE (a.SurveyID = "+SurveyID+")";
				
				Sql = Sql + " ORDER BY TargetLoginID";
			}
			else if(IsDeletedSurvey == 1)
			{		
				Sql= "SELECT * FROM tblDeletedSurvey a INNER JOIN tblDeletedAssignment ON a.DeletedSurveyID = tblDeletedAssignment.SurveyID INNER JOIN";
                Sql = Sql + " tblOrganization ON a.FKOrganization = tblOrganization.PKOrganization INNER JOIN";
                Sql = Sql + " [User] ON tblDeletedAssignment.TargetLoginID = [User].PKUser";
				Sql = Sql + " WHERE (a.DeletedSurveyID = "+SurveyID+")";
				
				Sql = Sql + " ORDER BY TargetLoginID";
			}			
			
		
			try
	        {          

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(Sql);

				while(rs.next())	
				{
					String RaterCode = rs.getString("RaterCode");
					int RaterStatus = rs.getInt("RaterStatus");
					int RaterLoginID = rs.getInt("RaterLoginID");
					int TargetLoginID = rs.getInt("TargetLoginID");
					int NameSequence = rs.getInt("NameSequence");
					
					if(RaterCode.equals("SUP"))
						Relation = "Superior";
					else if(RaterCode.equals("SELF"))
						Relation = "Self";
					else if(RaterCode.equals("OTH"))
						Relation = "Others";
						
					if(RaterStatus == 0)
						Status = "Incomplete";
					if(RaterStatus == 1)
						Status = "Completed";	
					if(RaterStatus == 2)
						Status = "Unreliable";				
					
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
					
					label= new Label(9, row, Status,bordersData2);
					writesheet.addCell(label);
		
				
					no_Records++;
					row++;
					
				}
	        }
	        catch(Exception E) 
	        {
	            System.err.println("Report_Deleted_ListOfRatersStatus.java - AllRaters- " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }
			
			
			workbook.write();
			workbook.close(); 
			
			if(no_Records == 0)
			IsNull = true;

		return IsNull;
	}
	
	public boolean deleteRecord(int SurveyID)
	{
		String sql = "Delete from tblDeletedAssignment where SurveyID = " + SurveyID;
	
		Connection con = null;
		Statement st = null;

        boolean bIsDeleted = false;
        		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsDeleted=true;


  		
		} 
		catch (Exception E)
		{
			System.err.println("Report_Deleted_ListOfRatersStatus.java - deleteRecord - " + E);
			
		}

		finally
		{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return bIsDeleted;
		
	}
	
	public static void main (String[] args)throws SQLException, Exception
	{
		Report_Deleted_ListOfRatersStatus_Survey Rpt = new Report_Deleted_ListOfRatersStatus_Survey();

		Rpt.AllRaters(331,0);
		
			
	}
}	