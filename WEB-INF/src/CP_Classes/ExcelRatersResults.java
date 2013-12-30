package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.math.*;
import java.io.File;
import java.io.IOException;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voGroup;
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
import jxl.format.PageOrientation;


/**
 * This class implements all the operations for ExcelRaterResults in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelRatersResults
{
	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	
	/**
	 * Declaration of new object of class Database.
	 */
	private Database db;
	
	/**
	 * Declaration of new object of class SurveyResult.
	 */
	private SurveyResult S;
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	//Added by Roger 24 June 2008
	private Questionnaire Q;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	
	private int surveyID;
	private int assignmentID;	
	private String targetName;
	private int LevelOfSurvey;
	private String SurveyName;
	private String raterName;
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
	 * Create new instance of ExcelRaterResults object.
	 */
	public ExcelRatersResults() {
		db = new Database();
		ST = new Setting();	
		S = new SurveyResult();
		U = new User_Jenty();
		EV = new EventViewer();
		
		//Added by Roger 24 June 2008 
		Q = new Questionnaire();
	}
	
	/**
	 * Retrieves survey details.
	 */
	public votblSurvey SurveyInfo() throws SQLException, Exception {
		
		String query = "SELECT tblSurvey.SurveyID, tblSurvey.SurveyName, tblSurvey.LevelOfSurvey, ";
		query = query + "tblConsultingCompany.CompanyName, tblOrganization.OrganizationName, ";
		query = query + "tblOrganization.NameSequence, tblAssignment.RaterCode,tblAssignment.TargetLoginID,tblAssignment.RaterLoginID ";
		query = query + "FROM tblAssignment INNER JOIN tblSurvey ON ";
		query = query + "tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
		query = query + "tblConsultingCompany ON tblSurvey.FKCompanyID = tblConsultingCompany.CompanyID ";
		query = query + "INNER JOIN tblOrganization ON ";
		query = query + "tblSurvey.FKOrganization = tblOrganization.PKOrganization ";
		query = query + "WHERE  tblAssignment.AssignmentID = " + assignmentID;
		
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
            	
            	vo.setSurveyID(rs.getInt("SurveyID"));
            	vo.setSurveyName(rs.getString("SurveyName"));
            	vo.setLevelOfSurvey(rs.getInt("LevelOfSurvey"));
            	vo.setCompanyName(rs.getString("CompanyName"));
            	vo.setOrganizationName(rs.getString("OrganizationName"));
            	vo.setNameSequence(rs.getInt("NameSequence"));
            	vo.setRaterCode(rs.getString("RaterCode"));
            	vo.setTargetLoginID(rs.getInt("TargetLoginID"));
            	vo.setRaterLoginID(rs.getInt("RaterLoginID"));
            	
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("ExcelRatersResults.java - SurveyInfo - " + E);
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
	 * @param targetID
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String UserName(int nameSeq, int targetID) throws SQLException, Exception {
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
		writesheet.setName("Rater's Results");
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		cellBOLD = new WritableCellFormat(fontBold);
		
		bordersData1 = new WritableCellFormat(fontFace);
		bordersData1.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData1.setAlignment(Alignment.CENTRE);
					
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		bordersData2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
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
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
		*/
	}
	
	/**
	 * Writes header on excel.
	 */
	public void Header(int assignmentID) 
		throws IOException, WriteException, SQLException, Exception
	{
		this.assignmentID = assignmentID;
		
		Label label = new Label(0, 0, "Raters' Result",cellBOLD);
		if (ST.LangVer == 2)
			label = new Label(0, 0, "HASIL PENILAI",cellBOLD);
		writesheet.addCell(label); 
		writesheet.mergeCells(0, 0, 3, 0);
				
		String CompName="";		
		String OrgName ="";		
		int NameSequence=0;
		String SurveyLevel = "";
		String raterCode = "";
		int targetID = 0;
		int raterID = 0;
		
		votblSurvey rs = SurveyInfo();	
				
		if(rs != null)	
		{
			surveyID = rs.getSurveyID();
			SurveyName = rs.getSurveyName();
			LevelOfSurvey = rs.getLevelOfSurvey();
			
			CompName = rs.getCompanyName();
			OrgName = rs.getOrganizationName();
			NameSequence = rs.getNameSequence();
			raterCode =rs.getRaterCode();
			targetID = rs.getTargetLoginID();
			raterID = rs.getRaterLoginID();
						
			if(LevelOfSurvey == 0)
				SurveyLevel = "Competency Level";
				if (ST.LangVer == 2)
					SurveyLevel = "Tingkat Kompetensi";
			else if(LevelOfSurvey == 1)
				SurveyLevel = "Key Behaviour Level";
				if (ST.LangVer == 2)
					SurveyLevel = "Tingkat Perilaku Kunci";
				
			targetName = UserName(NameSequence, targetID);
			raterName = UserName(NameSequence, raterID);			
		
		}

			
		int row_title = 2;
		
		label= new Label(0, row_title, "Company :",cellBOLD);
		if (ST.LangVer == 2)
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

		if (ST.LangVer == 1)
			label= new Label(0, row_title + 8 , "Rater Code: ",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title + 8 , "Kode Penilai: ",cellBOLD);
		writesheet.addCell(label);
		
		label= new Label(1, row_title + 8 , raterCode ,No_Borders);
		writesheet.addCell(label);
		
	}
	

	/**
	 * Writes results on excel.
	 */
	public void printResults() throws IOException, WriteException, SQLException, Exception
	{
		int c = 0;
		int r = 12;
		int totalCells = 0;
		
		String RTCode [] = S.RatingCode(surveyID);
		
		int totalComp = S.TotalCompetency(surveyID);
		int totalRT = S.TotalRT(surveyID);
		
		Vector kbTemp = S.TotalKB(surveyID);		// total KB group by Competency
		int totalKB [] = new int[totalComp];
		int t=0;

		for(int i=0; i<kbTemp.size(); i++) {
			int [] arr = (int[])kbTemp.elementAt(i);
			
			totalKB[t++] = arr[1];
		}
		int RTID [] = S.RTID(surveyID);	
		
		if (ST.LangVer == 1)
			label = new Label(c++, r, "Competency", bordersData3);
		else if (ST.LangVer == 2)
			label = new Label(c++, r, "Kompetensi", bordersData3);
		writesheet.addCell(label);
		writesheet.setColumnView(c, 20);
		totalCells++;
		
		if(LevelOfSurvey == 1) {
			if (ST.LangVer == 1)
				label = new Label(c, r, "Key Behaviour", bordersData3); 
			else if (ST.LangVer == 2)
				label = new Label(c, r, "Perilaku Kunci", bordersData3); 
			writesheet.addCell(label);
			writesheet.setColumnView(c, 38);
			totalCells++;
			c++;
		}
		
		for(int a=0; a<RTCode.length; a++) {
			label = new Label(c++, r, RTCode[a], bordersData3); 
			writesheet.addCell(label);
			totalCells++;
		}
		
		r++;
		String compName = "";
		
		Vector compOrKBList = S.CompOrKBList(surveyID);			
		int j=0, comp=0;
		int id = 0;
		double compScore = 0;
		
		for(int i=0; i<compOrKBList.size(); i++) {
			String [] arr = (String[])compOrKBList.elementAt(i);
			
			c = 0;
			
			if(j == 0) {
				id = Integer.parseInt(arr[0]);
				compName = arr[1];
				
				label = new Label(c++, r, UnicodeHelper.getUnicodeStringAmp(compName), bordersData2); 
				writesheet.addCell(label);
				
				if(LevelOfSurvey == 1) {
					label = new Label(c++, r, "", bordersData2); 
					writesheet.addCell(label);
					
					for(int k=0; k<totalRT; k++) {
						compScore = S.getCompScore(assignmentID, RTID[k], id);
						
						int roundScore = (int)(compScore * 100);
						
						compScore = roundScore/100;
						
						if(compScore > 0)
							label = new Label(c++, r, Double.toString(compScore), bordersData4); 
						else
							label = new Label(c++, r, "", bordersData4); 
						writesheet.addCell(label);
					}
					r++;
				}
			}
			
			if(LevelOfSurvey == 1) {
				c = 0;
				
				label = new Label(c++, r, "", bordersData2); 
				writesheet.addCell(label);
				
				
				
				id = Integer.parseInt(arr[2]);
				String KBName = arr[3];
				
			
				label = new Label(1, r, UnicodeHelper.getUnicodeStringAmp(KBName), bordersData2); 
				writesheet.addCell(label);	
				
				j++;
				if(j == totalKB[comp]) {
				
					j = 0;
					comp++;
				}			
				
				c++;
			}
			//Added code to display value 0 as blank when Hide NA option is chosen or NA is excluded from calculation. Kian Hwee 17 March 2010
			Create_Edit_Survey CESurvey = new Create_Edit_Survey();
			int NA_Included = CESurvey.getNA_Included(surveyID);
			for(int k=0; k<totalRT; k++) {			
				double Result = S.IsResultExist(assignmentID, RTID[k], id);
				System.out.println(k+" Result: "+Result);
				if(Result > 0 || NA_Included==1) {
					label = new Label(c++, r, Double.toString(Result), bordersData1); 
					writesheet.addCell(label);
				} else {
					label = new Label(c++, r, "", bordersData2); 
					writesheet.addCell(label);
				}
				
			}				
					
			r++;	
		}
		
		
		// Writing the Comments
		
		
		int selfIncluded = Q.SelfCommentIncluded(surveyID);
		int included = Q.commentIncluded(surveyID);
		
		double merge = 0;
		int totalMerge = 0;
		
		totalCells--;		// JExcelAPi starts from 0
		
		//Edited by Roger 24 June 2008
		// Fix the bug where self comment always not displayed
		//if(! S.RaterCode(assignmentID).equals("SELF")) {
		if ((S.RaterCode(assignmentID).equals("SELF") && selfIncluded ==1) || (!S.RaterCode(assignmentID).equals("SELF") && included ==1) ) {
		r++;
		if (ST.LangVer == 1)
			label = new Label(0, r++, "Narrative Comments", No_Borders); 
		else if (ST.LangVer == 2)
			label = new Label(0, r++, "Komentar Naratif", No_Borders); 
		writesheet.addCell(label);
		
		
		
		if (ST.LangVer == 1)
			label = new Label(0, r, "Competency", bordersData3);
		else if (ST.LangVer == 2)
			label = new Label(0, r, "Kompetensi", bordersData3); 
		writesheet.addCell(label);
		
		//Added by Roger 23 June 2008 add one more column
		int columnCell = 1;
		if(LevelOfSurvey == 1) {
			label = new Label(columnCell, r, "Key Behaviour", bordersData3);
			if (ST.LangVer == 2) {
				label = new Label(columnCell, r, "Perilaku Kunci", bordersData3);	
			}
			columnCell++;
			writesheet.addCell(label);
		}
		//-------------------
		
		label = new Label(columnCell, r, "Narrative Comments", bordersData3); 
		writesheet.addCell(label);
		writesheet.mergeCells(columnCell, r, 6, r);
		r++;
		
		
		Vector compComment = S.CompListSurvey(surveyID);			
		String com = "";
		
		id = 0;
		
		for(int l=0; l<compComment.size(); l++)
		{
			int start = 0;
			voCompetency voComp = (voCompetency)compComment.elementAt(l);
			
			//id = voComp.getCompetencyID();
			id = voComp.getPKCompetency();
			compName = voComp.getCompetencyName();
		
			merge = (double)compName.length() / (double)26;				
				
			BigDecimal BDComp = new BigDecimal(merge);
			BigInteger BIComp = BDComp.toBigInteger();
			int compMerge = BIComp.intValue();
			
			//Edited by Roger 23 June 2008
			//bug fix - narrative comment not display
			label = new Label(0, r, compName, bordersData2); 
			writesheet.addCell(label);
			
			Vector vComment = S.getComment(assignmentID, id);
			Vector kbNames = S.getKeyBehaviourNames(assignmentID, id);

			//Added by Roger 23 June 2008
			//Add additional line when display narrative comment for survey with keybehaviour
			if (start == 0 && LevelOfSurvey == 1) {
				label = new Label(1, r,"", bordersData2);
				writesheet.addCell(label);
				label = new Label(2, r,"", bordersData2);
				writesheet.mergeCells(2, r, 6, r);
				writesheet.addCell(label);
				r++;
			}
			
			label = new Label(0, r, compName, bordersData2); 
			writesheet.addCell(label);
			
			for(int m=0; m<vComment.size(); m++)
			{		

				String sComment = (String)vComment.elementAt(m);
				String kbName = "";
				if (LevelOfSurvey == 1) {
					kbName = (String)vComment.elementAt(m);
				}
				
				if(start != 0) {
					label = new Label(1, r, "", bordersData2);
					writesheet.addCell(label);		
					
					label = new Label(0, r, "", bordersData2); 
					writesheet.addCell(label);
				}
				start++;				
				
				//Edited by Roger 23 June 2008
				int cellColumn = 1;
				if (LevelOfSurvey == 1) {
					label = new Label(cellColumn, r, compName, bordersData2);
					writesheet.addCell(label);		
					cellColumn++;
				}
				
				
				com = sComment;
				merge = (double)com.length() / (double)70;				
				
				BigDecimal BD = new BigDecimal(merge);
				BD.setScale(0, BD.ROUND_UP);
				BigInteger BI = BD.toBigInteger();
				totalMerge = BI.intValue();
				if(totalMerge < compMerge)
						totalMerge = compMerge;
						
				writesheet.mergeCells(cellColumn, r, 6, r);
				writesheet.setRowView(r, 500*totalMerge);
				
				label = new Label(cellColumn, r, com, bordersData2); 
				writesheet.addCell(label);
				r++;
			}
			if (start == 0) {
				writesheet.mergeCells(1, r, 6, r);
				writesheet.setRowView(r, 500*compMerge);
				label = new Label(1, r++, "Nil", bordersData2); 
				writesheet.addCell(label);
			}
				
		}
		}
			
		
	}
	
	
	/**
	 * Method called from JSP.
	 */
	public void WriteToReport(int assignmentID, int pkUser, String fileName) 
		throws IOException, WriteException, SQLException, Exception
	{
			this.fileName = fileName;
			write();
			Header(assignmentID);	
			printResults();
			
			
			//WritableImage wi = new WritableImage(1, 1, 500, 500, new File("C:/Documents and Settings/Jenty/My Documents/My Pictures/Doggy.png"));	
			//writesheet.addImage(wi);
			workbook.write();
			

			workbook.close(); 
			
			String [] UserInfo = U.getUserDetail(pkUser);
		
			try {
				String temp = SurveyName + "(S); " + targetName + "(T); " + raterName + "(R)";
				EV.addRecord("Print", "Rater Result", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}

	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelRatersResults Rpt = new ExcelRatersResults();

		Rpt.WriteToReport(6240, 6408, "AB.xls"); // kb
		//Rpt.WriteToReport(115);
		
			
	}
}