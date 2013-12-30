package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

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
import CP_Classes.UnicodeHelper;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblSurvey;

public class Report_ResultSummary
{
	/**
	 * Declaration of classes 
	 */
	private Setting server;
	private Create_Edit_Survey survey;
	private SurveyResult result;
	
	/**
	 * Declaration of Variables
	 */	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;
	private WritableFont fontBold, fontNOBold,fontFace;
	private WritableWorkbook workbook;
	private WritableCellFormat cellBOLD_Border;
	private WritableCellFormat cellNOBold;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat No_Borders, No_Borders_ctrAll,No_Borders_ctrAll_Bold, No_Borders_NoBold ;
	private File outputWorkBook, inputWorkBook;
	
	/**
	 * Creates a new intance of Create Edit Survey object.
	 */
	
	public Report_ResultSummary()
	{
		server = new Setting();
		survey = new Create_Edit_Survey();
		result = new SurveyResult();
	}
	
	/*--------------------------------------To initialize the workbook and borders -------------------------------*/
	
	public void write() throws IOException, WriteException, BiffException
	{
		String output = server.getReport_Path()+"\\SummaryReport.xls";
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(server.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Summary Report");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		fontNOBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		
		cellBOLD = new WritableCellFormat(fontBold); 
		
		cellBOLD_Border = new WritableCellFormat(fontBold); 
		cellBOLD_Border.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellBOLD_Border.setAlignment(Alignment.LEFT);
		cellBOLD_Border.setWrap(true);
		
		cellNOBold = new WritableCellFormat(fontNOBold); 
		
		bordersData1 = new WritableCellFormat();
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
	
/*--------------------------------------To initialize the headings -------------------------------*/	

	public void Header(int row_data, int arrComp[]) 
		throws IOException, WriteException, SQLException, Exception
	{
		Label label = new Label(0, row_data, "EMP_CODE", cellBOLD); 		
		writesheet.addCell(label);
		
		label = new Label(1, row_data, "NAME", cellBOLD); 		
		writesheet.addCell(label);
		
		label = new Label(2, row_data, "FAMILY_NAME", cellBOLD); 		
		writesheet.addCell(label);
		
		int col_data = 3;
		for(int i=0; i<arrComp.length; i++)
		{
			if(arrComp[i] == 0)
				break; //end of loop
				
			//Print CP Heading
			label = new Label(i + col_data, row_data, arrComp[i] + "_CP", cellBOLD); 		
			writesheet.addCell(label);
			
			//Print CPR Heading
			label = new Label(i + col_data + 1, row_data, arrComp[i] + "_CPR", cellBOLD); 		
			writesheet.addCell(label);
					
			//Print GAP Heading
			label = new Label(i + col_data + 2, row_data, arrComp[i] + "_GAP", cellBOLD); 		
			writesheet.addCell(label);
			
			col_data += 2;
		}
		
		//Date timestamp = new Date();
		//SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		//String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		//writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		//writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		
	}
	
	/*------------------------Generate Summary report by Survey-------------------------------------*/
	/* Currently only support KB level and with 2 Rating Task (CP & CPR or FPR)
	 *
	*/
	public boolean generateReport(int SurveyID) throws IOException, WriteException, SQLException, Exception
	{
		boolean IsNull = false;

		write();	
		
		String str = "";
		int row_data = 0;
		int arrComp[] = new int[100];
		String arrCompName[] = new String[100];

		Vector rs_Score;
		votblSurvey rs_SurveyDetail = survey.getSurveyDetail(SurveyID);
		if(rs_SurveyDetail != null) {
			return false;
		}
		
		str = "SELECT DISTINCT tblAssignment.TargetLoginID, [User].IDNumber, [User].GivenName, [User].FamilyName ";
		str = str + "FROM tblAssignment INNER JOIN [User] ON tblAssignment.TargetLoginID = [User].PKUser ";
		str = str + "WHERE (tblAssignment.SurveyID = " + SurveyID + ") ORDER BY [User].GivenName";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int counterComp = 0;
		
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(str);
        	
        	
    		while (rs.next())
    		{
    			//Loop all Competencies in the survey and store them in array
    			arrComp[counterComp] = rs.getInt("CompetencyID");
    			arrCompName[counterComp] = rs.getString("CompetencyName");
    			
    			//Print CompetencyID
    			label= new Label(0, row_data, String.valueOf(arrComp[counterComp]) ,cellBOLD_Border);
    			writesheet.addCell(label); 
    			writesheet.setColumnView(0,6);
    			
    			//Print CompetencyName
    			label= new Label(1, row_data, UnicodeHelper.getUnicodeStringAmp(arrCompName[counterComp]),cellBOLD_Border);
    			writesheet.addCell(label); 
    			writesheet.setColumnView(0,6);
    			writesheet.mergeCells(1, row_data, 5, row_data);
    			
    			counterComp++;	
    			row_data++;
    		}
            
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - generateReport - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
		str = "SELECT * FROM tblSurveyCompetency INNER JOIN ";
        str = str + "Competency ON tblSurveyCompetency.CompetencyID = Competency.PKCompetency AND ";
		str = str + "tblSurveyCompetency.CompetencyID = Competency.PKCompetency WHERE (tblSurveyCompetency.SurveyID = " + SurveyID + ") ORDER BY CompetencyID";
	
		

		row_data = row_data + 2 ; //Increase two space
		Header(row_data, arrComp);
		row_data++;
		
		Vector v = new Vector();
		try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(str);
        	
        	while(rs.next()){
        		
        		voUser vo = new voUser();
        		vo.setTargetLoginID(rs.getInt("TargetLoginID"));
        		vo.setIDNumber(rs.getString("IDNumber"));
        		vo.setGivenName(rs.getString("GivenName"));
        		vo.setFamilyName(rs.getString("FamilyName"));
        		v.add(vo);
        	}
        	
        }
        catch(Exception E) 
        {
            System.err.println("Report_ResultSummary.java - generateReport - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }

        //      List all Targets from the Survey
		for(int i=0; i<v.size(); i++)
		{
			voUser vo = (voUser)v.elementAt(i);
			int TargetID = vo.getTargetLoginID();
			label= new Label(0, row_data, vo.getIDNumber(),cellNOBold);
			writesheet.addCell(label); 
			writesheet.setColumnView(0,6);			
			
			label= new Label(1, row_data, vo.getGivenName(),cellNOBold);
			writesheet.addCell(label);
			writesheet.setColumnView(2,25);
			
			label= new Label(2, row_data, vo.getFamilyName(),cellNOBold);
			writesheet.addCell(label);
			writesheet.setColumnView(2,25);
			
			int col_data = 3;
			int prevComp = 0; //To store current CompID in the loop
			double prevCompScore = 0; //To store current Comp Score in the loop
			
			//Get Target's Scores
			rs_Score = result.getAvgMeanForGap(SurveyID, TargetID);
			for(int j=0; j<rs_Score.size(); j++)
			{
				String [] arr = (String[])rs_Score.elementAt(j);
				int iCompID = Integer.parseInt(arr[1]);
				
				double dResult = Double.parseDouble(arr[2]);
				dResult = Math.round(dResult * 100.0) / 100.0; // round to 2 decimal places
				
				
				//Determine col_data position by CompetencyID
				for (int k=0; k<arrComp.length; k++)
				{
					if(arrComp[k] == 0)
						break;
					
					if(arrComp[k] == iCompID)
					{
						col_data = (k * 3) + 3;
						if(prevComp == iCompID)
							col_data++; //PRINTING CPR & GAP
						break;
					}
				}
				
				System.out.println("TargetID = " + TargetID);
				System.out.println(col_data + " Printing CP or CPR for " + iCompID);
				label= new Label(col_data, row_data, String.valueOf(dResult),cellNOBold);
				writesheet.addCell(label); 
				writesheet.setColumnView(0,6);	
				
				if(prevComp != iCompID)
				{
					//System.out.println(col_data + "NO PRINT prevComp != iCompID, " + prevComp + " != " + iCompID);
					prevComp = iCompID; //New Competency Record
					prevCompScore = dResult;
				}
				else
				{
					prevComp = 0; //clear prevComp counter
					col_data++;
					//System.out.println(col_data + " GAP prevComp == iCompID, " + prevComp + " == " + iCompID);
					
					//Check if CPR or FPR, then print GAP
					double dGapScore = prevCompScore - dResult;
					dGapScore = Math.round(dGapScore * 100.0) / 100.0; // round to 2 decimal places
					
					label= new Label(col_data, row_data, String.valueOf(dGapScore),cellNOBold);
					writesheet.addCell(label); 
					writesheet.setColumnView(0,6);	
				}
				
				//col_data++;
			}
			
			row_data++;
		}
            
        
	        
		
		// END List all Targets from the Survey
		
		workbook.write();
		workbook.close(); 
		
		return IsNull;
	}
	


	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ResultSummary Rpt = new Report_ResultSummary();
		
		//Rpt.generateReport(442);
		Rpt.generateReport(462);
		//AssignTarget_Rater a = new AssignTarget_Rater();
		
	}
}	