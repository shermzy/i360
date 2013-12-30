package CP_Classes;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
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

/**
 * This class implements all the operations for ExcelTargetTrimmedMean/AvgMean in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelTargetTrimmedMean
{
	private Database db;
	private SurveyResult S;
	
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	private int surveyID;
	private String SurveyName;
	private int targetID;
	private String targetName;
	private int LevelOfSurvey;
	private int reliabilityCheck;
	private String fileName;
	
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;	
	private WritableFont fontBold, fontFace;
	private WritableWorkbook workbook;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat bordersData3;
	private WritableCellFormat bordersData4;
	private WritableCellFormat No_Borders;
	private File inputWorkBook, outputWorkBook;
	
	/**
	 * Create new instance of ExcelTargetTrimmedMean object.
	 */
	public ExcelTargetTrimmedMean() {
		db = new Database();
		ST = new Setting();	
		S = new SurveyResult();
		U = new User_Jenty();
		EV = new EventViewer();
	}
	
	/**
	 * Retrieves survey details.
	 */
	public votblSurvey SurveyInfo() throws SQLException, Exception {
		
		String query = "SELECT tblSurvey.SurveyName, tblSurvey.LevelOfSurvey, tblSurvey.ReliabilityCheck, ";
		query = query + "tblConsultingCompany.CompanyName, tblOrganization.OrganizationName, tblOrganization.NameSequence ";
		query = query + "FROM tblSurvey INNER JOIN tblConsultingCompany ON ";
		query = query + "tblSurvey.FKCompanyID = tblConsultingCompany.CompanyID INNER JOIN ";
		query = query + "tblOrganization ON tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
		query = query + "WHERE tblSurvey.SurveyID = " + surveyID;
		
		
		votblSurvey vo = new votblSurvey();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
        	
            while(rs.next())
            {
            	vo.setSurveyName(rs.getString("SurveyName"));
            	vo.setLevelOfSurvey(rs.getInt("LevelOfSurvey"));
            	vo.setCompanyName(rs.getString("CompanyName"));
            	vo.setOrganizationName(rs.getString("OrganizationName"));
            	vo.setNameSequence(rs.getInt("NameSequence"));
            	vo.setReliabilityCheck(rs.getInt("ReliabilityCheck"));
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ExcelTargetTrimmedMean.java - SurveyInfo - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }
        
        return vo;
	}
	
	/**
	 * Retrieves username based on name sequence.
	 *
	 * @param nameSeq
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String UserName(int nameSeq) throws SQLException, Exception {
		String name = "";
		
		
		String query = "SELECT FamilyName, GivenName FROM [User] WHERE PKUser = " + targetID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
           
        	if(rs.next()) {
    			String familyName = rs.getString(1);
    			String GivenName = rs.getString(2);
    				
    			if(nameSeq == 0)
    				name = familyName + " " + GivenName;
    			else
    				name = GivenName + " " + familyName;
    			
    			
    		}
    			
        }
        catch(Exception E) 
        {
            System.err.println("ExcelRaterResult.java - UserName - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }
				
			
		return name;
	}
	
	/**
	 * Initialize and set all excel application and alignment.
	 */
	public void write() throws IOException, WriteException, BiffException
	{
		String output = ST.getReport_Path() + "\\" + fileName;
		outputWorkBook = new File(output);
		
		inputWorkBook = new File(ST.getReport_Path_Template() + "\\HeaderFooter.xls");
		Workbook inputFile = Workbook.getWorkbook(inputWorkBook);
		
		workbook = Workbook.createWorkbook(outputWorkBook, inputFile);
			
		writesheet = workbook.getSheet(0);
		writesheet.setName("Target Trimmed Mean");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold);
		
		bordersData1 = new WritableCellFormat(fontFace);
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.CENTRE);
					
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		
		bordersData3 = new WritableCellFormat(fontBold);
		bordersData3.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData3.setAlignment(Alignment.CENTRE);
		bordersData3.setBackground(Colour.GRAY_25);
		bordersData3.setWrap(true);
		
		bordersData4 = new WritableCellFormat(fontBold);
		bordersData4.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData4.setAlignment(Alignment.CENTRE);
		bordersData4.setWrap(true);
						
		No_Borders = new WritableCellFormat(fontBold);
		No_Borders.setBorder(Border.NONE, BorderLineStyle.NONE);
		
		/*Date timestamp = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		*/	
	}
	
	/**
	 * Writes header on excel.
	 */
	public void Header(int SurveyID, int TargetID) 
		throws IOException, WriteException, SQLException, Exception
	{
		this.surveyID = SurveyID;
		this.targetID = TargetID;
						
		String CompName=" ";		
		String OrgName =" ";
		int NameSequence=0;
		String SurveyLevel = " ";

		
		votblSurvey rs = SurveyInfo();	
				
		if(rs != null)	
		{
			SurveyName 			= rs.getSurveyName();
			LevelOfSurvey 		= rs.getLevelOfSurvey();
			reliabilityCheck 	= rs.getReliabilityCheck();
			CompName 			= rs.getCompanyName();
			OrgName 			= rs.getOrganizationName();
			NameSequence 		= rs.getNameSequence();
						
			if(LevelOfSurvey == 0)
				SurveyLevel = "Competency Level";
				if (ST.LangVer == 2)
					SurveyLevel = "Tingkat Kompetensi";
			else if(LevelOfSurvey == 1)
				SurveyLevel = "Key Behaviour Level";
				if (ST.LangVer == 2)
					SurveyLevel = "Tingkat Perilaku Kunci";
				
			targetName = UserName(NameSequence);			
		
		}
		
		String temp = "TRIMMED MEAN";
		if (ST.LangVer == 2)
			temp = "TRIM MEAN";
		if(reliabilityCheck == 1)
			temp = "RELIABILITY INDEX";
			if (ST.LangVer == 2)
				temp = "INDEX KEANDALAN";
				
		Label label = new Label(0, 0, "Raters' Input By Group - " + temp,cellBOLD); 
		if (ST.LangVer == 2)
			label = new Label(0, 0, "MASUKAN PENILAI BERDASARKAN GRUP - " + temp,cellBOLD); 
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 3, 0);
		

			
		int row_title = 2;
		
		if (ST.LangVer == 1)
			label= new Label(0, row_title, "Company:",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title, "Nama Perusahaan:",cellBOLD);
		writesheet.addCell(label); 
		writesheet.setColumnView(0,20);
								
		label= new Label(1, row_title, CompName, No_Borders);
		writesheet.addCell(label); 
		
		if (ST.LangVer == 1)
			label= new Label(0, row_title + 2, "Organisation:",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title + 2, "Nama Organisasi:",cellBOLD);
		writesheet.addCell(label); 
				
		label= new Label(1, row_title + 2 , OrgName ,No_Borders);
		writesheet.addCell(label); 
		
		if (ST.LangVer == 1)
			label= new Label(0, row_title + 4, "Survey Name:",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title + 4, "Nama Survei:",cellBOLD);
		writesheet.addCell(label); 
				
		label= new Label(1, row_title + 4 , SurveyName ,No_Borders);
		writesheet.addCell(label); 
		
		if (ST.LangVer == 1)
			label= new Label(0, row_title + 6, "Target Name:",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title + 6, "Nama Target:",cellBOLD);
		writesheet.addCell(label); 
				
		label= new Label(1, row_title + 6 , targetName ,No_Borders);
		writesheet.addCell(label); 


		label= new Label(0, row_title + 8 , SurveyLevel,No_Borders);
		writesheet.addCell(label); 
	
	}
	
	/**
	 * Writes results on excel.
	 */
	public void printResults() throws IOException, WriteException, SQLException, Exception
	{
		int r = 12;
		int c = 0;
				
		int totalSup  = S.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
		int totalOth  = S.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");
		int totalSelf = S.TotalRaterCodeSpecific(surveyID, targetID, "SELF");
		
		// 1=all,2= others, 3=sup
	
		Vector vRT 		= S.getRatingTask(surveyID);
		Vector vComp 	= S.CompList(surveyID);
		
		
		if(vRT != null) {
			for(int rt=0; rt<vRT.size(); rt++) {
		
				int rtid = Integer.parseInt(((String [])vRT.elementAt(rt))[0]);
				String rtName = ((String [])vRT.elementAt(rt))[1] + " (" + ((String [])vRT.elementAt(rt))[2] + ")";
					
				r++;
				label = new Label(c, r, rtName, cellBOLD); 
				writesheet.addCell(label);
				r++;
				
				String temp = "Competency";
				if (ST.LangVer == 2)
					temp = "Kompetensi";
					
	 			label = new Label(c, r, temp, bordersData3); 
				writesheet.addCell(label);
				c++;
					
				if(LevelOfSurvey == 1) {
					temp = "Key Behaviour";
					if (ST.LangVer == 2)
						temp = "Perilaku Kunci";
	 				label = new Label(c, r, temp, bordersData3); 
					writesheet.addCell(label);
					writesheet.setColumnView(c, 45);	
					c++;
				}
				
				if(totalSelf > 0) { 
					if (ST.LangVer == 1)
						label = new Label(c++, r, "SELF(" + targetName + ")", bordersData3); 
					else if (ST.LangVer == 2)
						label = new Label(c++, r, "DIRI SENDIRI(" + targetName + ")", bordersData3); 
					writesheet.addCell(label);
				}
				if(totalSup > 0) { 
					if (ST.LangVer == 1)
						label = new Label(c++, r, "SUP", bordersData3); 
					else if (ST.LangVer == 2)
						label = new Label(c++, r, "SUP", bordersData3); 
					writesheet.addCell(label);
				}
				if(totalOth > 0) { 
					if (ST.LangVer == 1)
						label = new Label(c++, r, "OTHER", bordersData3); 
					else if (ST.LangVer == 2)
						label = new Label(c++, r, "LAINNYA", bordersData3); 
					writesheet.addCell(label);
				}
				
				if (ST.LangVer == 1)
					label = new Label(c++, r, "ALL", bordersData3); 
				else if (ST.LangVer == 2)
					label = new Label(c++, r, "SEMUA", bordersData3); 
				writesheet.addCell(label);
				
				//write all the competencies and KBs here
				for(int comp=0; comp<vComp.size(); comp++) {
					
					r++;
					c=0;
				
					int fkComp 		= Integer.parseInt(((String [])vComp.elementAt(comp))[0]);					
					String compName = ((String [])vComp.elementAt(comp))[1];
					compName 		= UnicodeHelper.getUnicodeStringAmp(compName);
					
					double self = 0, sup=0, oth=0, all=0;
					
					label = new Label(c++, r, compName, bordersData2); 
					writesheet.addCell(label);
					
					if(LevelOfSurvey == 1) {
						
						if(reliabilityCheck == 0)
							all = S.TrimmedMeanAll(surveyID, targetID, fkComp, rtid);
						else
						 	all = S.getAvgMean(surveyID, targetID, 1, fkComp, rtid);
						 	
						label = new Label(c++, r, "", bordersData4); 
						writesheet.addCell(label);
					
					}else {
						
						if(reliabilityCheck == 0)
							all = S.getTargetTrimmedMean(surveyID, targetID, 1, fkComp, 0, rtid);
						else 						
						 	all = S.getTargetAvgMean(surveyID, targetID, 1, fkComp, 0, rtid);
					}
						
					
					if(totalSelf > 0) {
						if(LevelOfSurvey == 1)
							self = S.getAvgMean(surveyID, targetID, 4, fkComp, rtid);
						else
							self = S.TargetResult(surveyID, targetID, "SELF", fkComp, 0, rtid);
						
						
						label = new Label(c++, r, Double.toString(self), bordersData4); 
						writesheet.addCell(label);
					}
					
					if(totalSup > 0) {
		
						if(LevelOfSurvey == 1)
							sup = S.getAvgMean(surveyID, targetID, 2, fkComp, rtid);
						else {
							
							if(reliabilityCheck == 0)
								sup = S.getTargetTrimmedMean(surveyID, targetID, 2, fkComp, 0, rtid);
							else
								sup = S.getTargetAvgMean(surveyID, targetID, 2, fkComp, 0, rtid);
						}
						
						label = new Label(c++, r, Double.toString(sup), bordersData4); 
						writesheet.addCell(label);
					}
					
					if(totalOth > 0) {
		
						if(LevelOfSurvey == 1)
							oth = S.getAvgMean(surveyID, targetID, 3, fkComp, rtid);
						else {
							
							if(reliabilityCheck == 0)
								oth = S.getTargetTrimmedMean(surveyID, targetID, 3, fkComp, 0, rtid);
							else
								oth = S.getTargetAvgMean(surveyID, targetID, 3, fkComp, 0, rtid);
						}
						
						label = new Label(c++, r, Double.toString(oth), bordersData4); 
						writesheet.addCell(label);
					}
					
					label = new Label(c++, r, Double.toString(all), bordersData4); 
					writesheet.addCell(label);
					
					
					//write all the KBs
					if(LevelOfSurvey == 1) {
						
						Vector vKB = S.KBList(surveyID, fkComp);
						if(vKB != null) {
							
							for(int kb=0; kb<vKB.size(); kb++) {
								
								c=0;
								r++;
								
								int fkKB 		= Integer.parseInt(((String [])vKB.elementAt(kb))[0]);
								String kbName 	= ((String [])vKB.elementAt(kb))[1];
								kbName			= UnicodeHelper.getUnicodeStringAmp(kbName);
								
								label = new Label(c++, r, "", bordersData2); 
								writesheet.addCell(label);
								
								label = new Label(c++, r, kbName, bordersData2); 
								writesheet.addCell(label);
								
								
								if(totalSelf > 0) {
									self = S.TargetResult(surveyID, targetID, "SELF", fkComp, fkKB, rtid);
									
									label = new Label(c++, r, Double.toString(self), bordersData1); 
									writesheet.addCell(label);
								}
								
								
								if(totalSup > 0) {
		
									if(reliabilityCheck == 0)
										sup = S.getTargetTrimmedMean(surveyID, targetID, 2, fkComp, fkKB, rtid);
									else 		
										sup = S.getTargetAvgMean(surveyID, targetID, 2, fkComp, fkKB, rtid);
									
									label = new Label(c++, r, Double.toString(sup), bordersData1); 
									writesheet.addCell(label);
								}
								
								if(totalOth > 0) {
					
									if(reliabilityCheck == 0)
										oth = S.getTargetTrimmedMean(surveyID, targetID, 3, fkComp, fkKB, rtid);
									else 		
										oth = S.getTargetAvgMean(surveyID, targetID, 3, fkComp, fkKB, rtid);
									
									label = new Label(c++, r, Double.toString(oth), bordersData1); 
									writesheet.addCell(label);
								}
								
								if(reliabilityCheck == 0)
									all = S.getTargetTrimmedMean(surveyID, targetID, 1, fkComp, fkKB, rtid);
								else 		
									all = S.getTargetAvgMean(surveyID, targetID, 1, fkComp, fkKB, rtid);
								
								label = new Label(c++, r, Double.toString(all), bordersData1); 
								writesheet.addCell(label);					
							}
						}
					}//end kb
					
				
				}//end comp
				
				
				
								
				r += 2;
				c = 0;
			}
		}
		
				
	}
	
	/**
	 * Method called from JSP.
	 */
	public void WriteToReport(int SurveyID, int TargetID, int pkUser, String fileName) 
		throws IOException, WriteException, SQLException, Exception
	{
			this.fileName = fileName;
			
			write();
			Header(SurveyID, TargetID);	
			printResults();
				
			workbook.write();
			workbook.close(); 
			
			String [] UserInfo = U.getUserDetail(pkUser);
		
			try {
				String temp = SurveyName + "(S); " + targetName + "(T)";
				EV.addRecord("Print", "Raters Input By Group", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}

	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelTargetTrimmedMean Rpt = new ExcelTargetTrimmedMean();

		// competency level = 340
		// key behaviour level = 343
		Rpt.WriteToReport(438,4275, 7, "ABC.xls");
		
			
	}
}