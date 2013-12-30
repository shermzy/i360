package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
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
import jxl.format.PageOrientation;

/**
 * This class implements all the operations for target gap in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelTargetGap
{
	private Database db;
	private SurveyResult S;
	private Calculation C;
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	private int surveyID;
	private String SurveyName;
	private int targetID;
	private String targetName;
	private int LevelOfSurvey;
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
	 * Create new instance of ExcelTargetGap object.
	 */
	public ExcelTargetGap() {
		db = new Database();
		ST = new Setting();	
		S = new SurveyResult();
		C = new Calculation();
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
            	
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ExcelRatersGap.java - SurveyInfo - " + E);
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
		writesheet.setName("Target Gap");
		
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
		
		bordersData4 = new WritableCellFormat(fontBold);
		bordersData4.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData4.setAlignment(Alignment.CENTRE);
						
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
		
		Label label = new Label(0, 0, "Target Gap",cellBOLD); 
		if (ST.LangVer == 2)
			label = new Label(0, 0, "SELISIH TARGET",cellBOLD); 
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 3, 0);
		
		
		String CompName=" ";		
		String OrgName =" ";
		int NameSequence=0;
		String SurveyLevel = " ";

		
		votblSurvey rs = SurveyInfo();	
				
		if(rs != null)
		{
			SurveyName = rs.getSurveyName();
			LevelOfSurvey = rs.getLevelOfSurvey();
			CompName = rs.getCompanyName();
			OrgName = rs.getOrganizationName();
			NameSequence = rs.getNameSequence();
						
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
	
		/*Date timestamp = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		*/
	}
	
	/**
	 * Writes results on excel.
	 */
	public void printResults() throws IOException, WriteException, SQLException, Exception
	{
		int r = 12;
		int c = 0;
				
		int gapType = C.GapType(surveyID);
		String RTCode [] = S.RatingCode(surveyID);
		int totalComp = S.TotalCompetency(surveyID);
		
		Vector kbTemp = S.TotalKB(surveyID);		// total KB group by Competency
		int totalKB [] = new int[totalComp];
		int t=0;
		for(int i=0; i<kbTemp.size(); i++) {
			int [] arr = (int[])kbTemp.elementAt(i);
			
			totalKB[t++] = arr[1];
		}		

		Vector compOrKBList = S.CompOrKBList(surveyID);	
		Vector targetResult = S.TargetGapDisplayed(surveyID, targetID);
		Vector Gap = S.getTargetGap(surveyID, targetID);
		
		Vector CompTrimmedMean = null;
		//ResultSet AvgGap = null;
	
		if(LevelOfSurvey == 1) {
			CompTrimmedMean = S.getAvgMeanForGap(surveyID, targetID);
			//AvgGap = S.getAvgGap(surveyID, targetID);
		}

		if (ST.LangVer == 1)				 		 		 		
 			label = new Label(c++, r, "Competency", bordersData3);
 		else if (ST.LangVer == 2)
 			label = new Label(c++, r, "Kompetensi", bordersData3);
		writesheet.addCell(label);
		
		if(LevelOfSurvey == 1) {
			if (ST.LangVer == 1)
				label = new Label(c, r, "Key Behaviour", bordersData3); 
			else if (ST.LangVer == 2)
				label = new Label(c, r, "Perilaku Kunci", bordersData3); 
			writesheet.addCell(label);
			writesheet.setColumnView(c, 45);
			c++;
		}
		
		
		if(gapType == 1 || gapType == 2) {
			for(int a=0; a<2; a++) {
				label = new Label(c++, r, RTCode[a], bordersData3); 
				writesheet.addCell(label);			
			}											
		}	
		
		if (ST.LangVer == 1)
			label = new Label(c++, r, "Gap", bordersData3); 
		else if (ST.LangVer == 2)
			label = new Label(c++, r, "Selisih", bordersData3); 
		writesheet.addCell(label);			
		
		r++;
		c = 0;
		int j=0, comp=0;
		int temp = 0, temp1=0;
		
		int targetCounter = 0;
		int trimCounter = 0;
		int gapCounter = 0;
		for(int i=0; i<compOrKBList.size(); i++) {
			String [] arr = (String[])compOrKBList.elementAt(i);
			
			String [] targetArr = null;
			String [] gapArr = null;
			if(targetCounter < targetResult.size()) {
				targetArr = (String[])targetResult.elementAt(targetCounter);
				gapArr = (String[])Gap.elementAt(gapCounter);
				temp1=1;
				gapCounter++;
			}
			if(j == 0) {			
				String compName = arr[1];
				compName = UnicodeHelper.getUnicodeStringAmp(compName);
				
				label = new Label(c++, r, compName, bordersData2); 
				writesheet.addCell(label);	
			}
			
			double rt1=0, rt2=0;
			if(LevelOfSurvey == 1) {				
				if(j == 0) {
					temp1=1;
					//AvgGap.next();
					String trimArr[] = null;
					if(trimCounter < CompTrimmedMean.size()) {
						trimArr = (String[]) CompTrimmedMean.elementAt(trimCounter);
						rt1 = Double.parseDouble(trimArr[2]);
						
						trimCounter++;
					}
					label = new Label(c++, r, "", bordersData2); 
					writesheet.addCell(label);
					
					label = new Label(c++, r, Double.toString(rt1), bordersData4); 
					writesheet.addCell(label);
					
					if(trimCounter < CompTrimmedMean.size()) {
						trimArr = (String[]) CompTrimmedMean.elementAt(trimCounter);
						rt2 = Double.parseDouble(trimArr[2]);
						trimCounter++;
					}
					label = new Label(c++, r, Double.toString(rt2), bordersData4); 
					writesheet.addCell(label);	
					
					double totalGap = rt1 - rt2;
					
					totalGap = Math.round(totalGap * 100.0) / 100.0;
					
					label = new Label(c++, r, Double.toString(totalGap), bordersData4); 
					writesheet.addCell(label);
					
					r++;
				}
			
				String KBName = arr[3];
				KBName = UnicodeHelper.getUnicodeStringAmp(KBName);
				
				label = new Label(0, r, "", bordersData2); 
				writesheet.addCell(label);
				
				label = new Label(1, r, KBName, bordersData2); 
				writesheet.addCell(label);
				
				j++;
			
				if(j == totalKB[temp]) {
					j = 0;
					temp++;
				}
			}
			
			if(LevelOfSurvey == 0)
				c = 1;
			else
				c = 2;
				
			double Result;
			for(int k=0; k<2; k++) {	
				if(temp1 == 1 && targetArr != null)
					Result = Double.parseDouble(targetArr[2]);
				else
					Result = 0;
					
				label = new Label(c++, r, Double.toString(Result), bordersData1); 
				writesheet.addCell(label);
				
				//if(k < 1) {
					targetCounter++;
					if(targetCounter < targetResult.size()) {
						targetArr = (String[])targetResult.elementAt(targetCounter);
						
					}
				//}
			}
				
			
			if(temp1 == 1 && gapArr != null) {	
				label = new Label(c++, r, gapArr[1], bordersData1); 
				writesheet.addCell(label);					
			} else {
				label = new Label(c++, r, Double.toString(0), bordersData1); 
				writesheet.addCell(label);					
			}
			
			comp++;
			if(comp == totalComp)
				comp = 0;
		
			r++;
			c = 0;
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
				EV.addRecord("Print", "Target Gap", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}


	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelTargetGap Rpt = new ExcelTargetGap();

		// competency level = 340
		// key behaviour level = 343
		Rpt.WriteToReport(431, 112, 5, "TargetGap.xls");
		
			
	}
}