package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblSurvey;

import jxl.read.biff.BiffException;
import jxl.write.*; 
import jxl.Workbook;
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
 * This class implements all the operations for Reliability in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelReliabilityIndex
{
	private Database db;
	private SurveyResult S;
	private RatingTask RT;
	private Questionnaire Q;
	
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
	private int targetID;
	private String targetName;
	private String SurveyName;
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
	private WritableCellFormat bordersData5;
	private WritableCellFormat No_Borders;
	private File inputWorkBook, outputWorkBook;
	
	/**
	 * Create new instance of ExcelReliabilityIndex object.
	 */
	public ExcelReliabilityIndex() {
		db = new Database();
		ST = new Setting();	
		S = new SurveyResult();
		RT = new RatingTask();
		Q = new Questionnaire();
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
            System.err.println("ExcelReliabilityIndex.java - SurveyInfo - " + E);
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
            System.err.println("ExcelReliabilityIndex.java - UserName - " + E);
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
		writesheet.setName("Reliability Index");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		//fontNOBold = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
		
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
		bordersData3.setVerticalAlignment(VerticalAlignment.CENTRE);
		bordersData3.setWrap(true);
		
		bordersData4 = new WritableCellFormat(fontBold);
		bordersData4.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData4.setAlignment(Alignment.CENTRE);
		
		bordersData5 = new WritableCellFormat(fontBold);
		bordersData5.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData5.setAlignment(Alignment.LEFT);
		
						
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
		
		Label label = new Label(0, 0, "Reliability Index",cellBOLD);
		if (ST.LangVer == 2)
			label = new Label(0, 0, "INDEX KEANDALAN",cellBOLD);
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
			//reliabilityCheck = rs.getInt("ReliabilityCheck");
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
		
		//String TargetDetail[] = new String[14];	
		
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
		
		int totalComp = Q.TotalList(surveyID);
	
		Vector kbTemp = S.TotalKB(surveyID);		// total KB group by Competency
		int totalKB [] = new int[totalComp];
		int t=0;
		for(int i=0; i<kbTemp.size(); i++) {
			int [] arr = (int[])kbTemp.elementAt(i);
			
			totalKB[t++] = arr[1];
		}
			
		String RTCode [] = S.RatingCode(surveyID);
		int RTID [] = S.RTID(surveyID);	
		
		int totalRaterCode = S.TotalRaterCode(surveyID, targetID);
		Vector Code = S.RaterCode(surveyID, targetID);
		String raterCode [] = new String[totalRaterCode];
		//String name [] = new String[totalRaterCode];
		int asgnt [] = new int [totalRaterCode];
		String reliability = "";
	
		int a=0;
		for(int i=0; i<Code.size(); i++) {
			String [] arr = (String[])Code.elementAt(i);
			asgnt[a] = Integer.parseInt(arr[0]);
			raterCode[a] = arr[1];		
			a++;
		}
	
		int totalRT   = S.TotalRT(surveyID);
		int totalSup  = S.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
		int totalOth  = S.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");
		int totalSelf = S.TotalRaterCodeSpecific(surveyID, targetID, "SELF");		
	
		//int totalOthCompleted  = S.TotalCompleted(surveyID, targetID, "OTH%");
		//int totalSelfCompleted = S.TotalCompleted(surveyID, targetID, "SELF");
		//int totalSupCompleted  = S.TotalCompleted(surveyID, targetID, "SUP%");

		Vector compOrKBList = null;
		
		for(int rt=0; rt<totalRT; rt++) {
			compOrKBList  = S.CompOrKBList(surveyID);			
			String RTName = RT.getRatingTask(RTCode[rt]);
		
			label = new Label(c++, r, RTCode[rt] + "(" + RTName + ")", cellBOLD); 
			writesheet.addCell(label);
			
			r++;
			
			c=0;
			
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
						
			if(totalSelf != 0) {
				label = new Label(c++, r, targetName + "(SELF)", bordersData3); 
				writesheet.addCell(label);	
			}
			
			for(int rc=totalOth+totalSelf; rc<raterCode.length; rc++) {
				label = new Label(c++, r, raterCode[rc], bordersData3); 
				writesheet.addCell(label);	
			}
			
			for(int rc=0; rc<totalOth; rc++) {
				label = new Label(c++, r, raterCode[rc], bordersData3); 
				writesheet.addCell(label);	
			}
			
			c=0;
			int id=0, kbid=0;
			String kb="";	
			int j=0;
			int x = 0;
			int counter = 0;
			for(int row=0; row < totalComp; row++) {	
				String [] arr = null;

				if(counter < compOrKBList.size()) {
					arr = (String[])compOrKBList.elementAt(counter);
					counter++;
				}
				if(j == 0) {			
					r++;
					id = Integer.parseInt(arr[0]);
					String compName = UnicodeHelper.getUnicodeStringAmp(arr[1]);	
					
					if(LevelOfSurvey == 1)
						label = new Label(c++, r, compName, bordersData5); 
					else
						label = new Label(c++, r, compName, bordersData2); 
					writesheet.addCell(label);
					
					if(LevelOfSurvey == 1) {
						
						int q=1;
						double score = 0;
						
						label = new Label(q++, r, "", bordersData2); 
						writesheet.addCell(label);
												
						if(totalSelf != 0) {
							score = S.getCompScore(asgnt[totalOth], RTID[rt], id);
							label = new Label(q++, r, Double.toString(score), bordersData4); 
							writesheet.addCell(label);
						}
				
						
						for(int sup=0; sup<totalSup; sup++) {
							score = S.getCompScore(asgnt[totalOth + totalSelf + sup], RTID[rt], id);							
							label = new Label(q++, r, Double.toString(score), bordersData4); 
							writesheet.addCell(label);
						}
				
		
						for(int oth=0; oth<totalOth; oth++) {
							score = S.getCompScore(asgnt[oth], RTID[rt], id);							
							label = new Label(q++, r, Double.toString(score), bordersData4); 
							writesheet.addCell(label);
						}
		
					}	
				}
				
				
				if(LevelOfSurvey == 1) {
					kbid = Integer.parseInt(arr[2]);
					id = kbid;
					kb = UnicodeHelper.getUnicodeStringAmp(arr[3]);
					j++;
					if(j == totalKB[x]) {
						j = 0;
						x++;
					}
					
					
					r++;
					label = new Label(0, r, "", bordersData2); 
					writesheet.addCell(label);
					
					label = new Label(1, r, kb, bordersData2); 
					writesheet.addCell(label);
					c = 2;	
				}
				
				
				double self = -1;
				if(totalSelf != 0) {
					self = S.IsResultExist(asgnt[totalOth], RTID[rt], id);
					if(self >= 0 ) {
						label = new Label(c++, r, Double.toString(self), bordersData1); 
						writesheet.addCell(label);
					} else {
						label = new Label(c++, r, "", bordersData1); 
						writesheet.addCell(label);
					}
				}
				
				//int flag = 0;
				for(int sup=0; sup<totalSup; sup++) {
					double sup1 = S.IsResultExist(asgnt[totalOth + totalSelf + sup], RTID[rt], id);
					if(sup1 >= 0 ) {			
						label = new Label(c++, r, Double.toString(sup1), bordersData1); 
						writesheet.addCell(label);
					} else {
						label = new Label(c++, r, "", bordersData1); 
						writesheet.addCell(label);
					}
				}
				
				//flag = 0;
				for(int oth=0; oth<totalOth; oth++) {
					double oth1 = S.IsResultExist(asgnt[oth], RTID[rt], id);
					if(oth1 >= 0 ) {	
						label = new Label(c++, r, Double.toString(oth1), bordersData1); 
						writesheet.addCell(label);
					} else {
						label = new Label(c++, r, "", bordersData1); 
						writesheet.addCell(label);
					}
				}
				
				c=0;
											
			}						
			r += 2;								
			c=0;

		}

		r -= 1;
		c = 1;
		if(LevelOfSurvey == 1)
			c = 2;
			
		int selfIndex = -1;
		if(totalSelf != 0) {
			S.RatersStatus(asgnt[totalOth]);
				if(selfIndex > 0) {
				//Changed by Ha 04/07/08 change the rater status 5 is unreliable, 5 is NA
		  			if(selfIndex == 2 || selfIndex == 4)
		  			{
						reliability = "Unreliable";
						if (ST.LangVer == 2)
							reliability = "Tidak Bisa Diandalkan";
		  			}
					else if (selfIndex == 5)
					{
						reliability = "NA";
						
					}
					else
					{
						reliability = "Reliable";
						if (ST.LangVer == 2)
							reliability = "Bisa Diandalkan";
					}
				
					label = new Label(c++, r, reliability, bordersData3); 
					writesheet.addCell(label);
				
				}  else {
					label = new Label(c++, r, "", bordersData3); 
					writesheet.addCell(label);
				}
		}
		
		
		for(int sup=0; sup<totalSup; sup++) {
			int sup1 =  S.RatersStatus(asgnt[totalOth+totalSelf+sup]);
			if(sup1 > 0 ) {	
			//Changed by Ha 04/07/08 change the rater status 5 is unreliable, 5 is NA
				if(sup1 == 2 || sup1 == 4)
				{
					reliability = "Unreliable";
						if (ST.LangVer == 2)
							reliability = "Tidak Bisa Diandalkan";
				}
				else if (sup1 == 5)
					reliability = "NA";
				else
				{
					reliability = "Reliable";
						if (ST.LangVer == 2)
							reliability = "Bisa Diandalkan";
				}
				
				label = new Label(c++, r, reliability, bordersData3); 
				writesheet.addCell(label);
					
			}  else {
				label = new Label(c++, r, "", bordersData3); 
				writesheet.addCell(label);
			}
		}
		
		
		for(int oth=0; oth<totalOth; oth++) {
			int sup1 =  S.RatersStatus(asgnt[oth]);
		//Changed by Ha 04/07/08 change the rater status 5 is unreliable, 5 is NA
			if(sup1 > 0 ) {	
				if(sup1 == 2 || sup1 == 4)
				{
					reliability = "Unreliable";
						if (ST.LangVer == 2)
							reliability = "Tidak Bisa Diandalkan";
				}
				else if (sup1 == 5)
					reliability = "NA";
				else
				{
					reliability = "Reliable";
						if (ST.LangVer == 2)
							reliability = "Bisa Diandalkan";
				}
					
				label = new Label(c++, r, reliability, bordersData3); 
					writesheet.addCell(label);
						
				}  else {
					label = new Label(c++, r, "", bordersData3); 
					writesheet.addCell(label);
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
				EV.addRecord("Print", "Reliability Index", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}

	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelReliabilityIndex Rpt = new ExcelReliabilityIndex();

		// competency level = 340
		// key behaviour level = 343
		//Rpt.WriteToReport(343,6);
		Rpt.WriteToReport(445,6410, 6408, "A");
		
			
	}
}