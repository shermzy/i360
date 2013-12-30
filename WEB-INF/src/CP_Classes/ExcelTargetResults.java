package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.math.*;
import java.io.File;
import java.io.IOException;

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
import CP_Classes.UnicodeHelper;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.votblSurvey;

/**
 * This class implements all the operations for target results in Excel.
 * It implements JExcelAPI Interface.
 */ 
public class ExcelTargetResults
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
	//private int reliabilityCheck;
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
	 * Create new instance of ExcelTargetResults object.
	 */
	public ExcelTargetResults() {
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
            System.err.println("ExcelTargetResults.java - SurveyInfo - " + E);
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
            System.err.println("ExcelTargetResult.java - UserName - " + E);
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
		writesheet.setName("Raters' Input");
		
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
		bordersData2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		bordersData3 = new WritableCellFormat(fontBold);
		bordersData3.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData3.setAlignment(Alignment.CENTRE);
		bordersData3.setVerticalAlignment(VerticalAlignment.CENTRE);
		bordersData3.setBackground(Colour.GRAY_25);
		bordersData3.setWrap(true);
		
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
		
		Label label = new Label(0, 0, "Raters' Input",cellBOLD); 
		if (ST.LangVer == 2)
			label = new Label(0, 0, "MASUKAN PENILAI",cellBOLD); 
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
			CompName = UnicodeHelper.getUnicodeStringAmp(rs.getCompanyName());
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
		
		label= new Label(0, row_title, "Company:",cellBOLD);
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
		
		String TargetDetail[] = new String[14];	
		
		if (ST.LangVer == 1)
			label= new Label(0, row_title + 6, "Target Name:",cellBOLD);
		else if (ST.LangVer == 2)
			label= new Label(0, row_title + 6, "Nama Target:",cellBOLD);
		writesheet.addCell(label); 
				
		label= new Label(1, row_title + 6 , targetName ,No_Borders);
		writesheet.addCell(label); 

		label= new Label(0, row_title + 8 , SurveyLevel,No_Borders);
		writesheet.addCell(label); 
		
		//writesheet.setPageSetup(PageOrientation.LANDSCAPE);
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
		int asgnt [] = new int [totalRaterCode];
			
		int a=0;
		for(int i=0; i<Code.size(); i++) {
			String [] arr = (String [])Code.elementAt(i);
			asgnt[a] = Integer.parseInt(arr[0]);
			raterCode[a] = arr[1];		
			a++;
		}
	
		int totalRT   = S.TotalRT(surveyID);
		int totalSup  = S.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
		int totalOth  = S.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");
		int totalSelf = S.TotalRaterCodeSpecific(surveyID, targetID, "SELF");
		
//		int totalOthCompleted  = S.TotalCompleted(surveyID, targetID, "OTH%");
//		int totalSelfCompleted = S.TotalCompleted(surveyID, targetID, "SELF");
//		int totalSupCompleted  = S.TotalCompleted(surveyID, targetID, "SUP%");

		Vector compOrKBList = null;
		
		/** LOOP THRU TOTAL RATING TASK (SELF,SUP,OTH)- Roger**/
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
				label = new Label(c, r, "Key Behaviour", bordersData3);
				if (ST.LangVer == 2)
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
				String arr [] = null;
				
				if(counter < compOrKBList.size()) {
					arr = (String[])compOrKBList.elementAt(counter);
					counter++;
				}	
				
				if(j == 0) {			
					r++;
					id = Integer.parseInt(arr[0]);
					
					String compName = UnicodeHelper.getUnicodeStringAmp(arr[1]);
					label = new Label(c++, r, compName, bordersData2); 
					writesheet.addCell(label);
					
					if(LevelOfSurvey == 1) {
						int q=1;
						double compScore = 0;
												
						label = new Label(q++, r, "", bordersData2); 
						writesheet.addCell(label);
																		
						if(totalSelf != 0) {
							compScore = S.getCompScore(asgnt[totalOth], RTID[rt], id);
							
							if(compScore > 0)
								label = new Label(q++, r, Double.toString(compScore), bordersData4); 
							else
								label = new Label(q++, r, "", bordersData4); 
							writesheet.addCell(label);
						}
						
						for(int sup=0; sup<totalSup; sup++) {
							compScore = S.getCompScore(asgnt[totalOth + totalSelf + sup], RTID[rt], id);
							
							if(compScore > 0)
								label = new Label(q++, r, Double.toString(compScore), bordersData4); 
							else
								label = new Label(q++, r, "", bordersData4); 
							writesheet.addCell(label);
						}
						
						for(int oth=0; oth<totalOth; oth++) {
							compScore = S.getCompScore(asgnt[oth], RTID[rt], id);
							
							if(compScore > 0)
								label = new Label(q++, r, Double.toString(compScore), bordersData4); 
							else
								label = new Label(q++, r, "", bordersData4); 
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
		
		
		// Comments
		int totalMerge = 0;
		double merge = 0;
		String com = "";
		r += 2;
		int id = 0;
		String compName = "";
		
		
		
		//Edited by Roger 19 June 2008
		//Add a key behaviour column if if survey is key behaviour level
		///////////////////////////////SELF//////////////////////////////
		
		int selfIncluded = Q.SelfCommentIncluded(surveyID);
		int included = Q.commentIncluded(surveyID);
		
		if(selfIncluded == 1 && totalSelf == 1) {
			if (ST.LangVer == 1)
				label = new Label(0, r++, "Narrative Comments by SELF", No_Borders); 
			else if (ST.LangVer == 2)
				label = new Label(0, r++, "Komentar Naratif oleh DIRI SENDIRI", No_Borders); 
			writesheet.addCell(label);
			label = new Label(0, r, "Competency", bordersData3);
			if (ST.LangVer == 2)
				label = new Label(0, r, "Kompetensi", bordersData3);
			writesheet.addCell(label);
			
			//Added by Roger 19 June 2008 add one more column
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
			//label = new Label(1, r, "Narrative Comments", bordersData3);
			if (ST.LangVer == 2) {
				label = new Label(columnCell, r, "Komentar Naratif", bordersData3);
				//label = new Label(1, r, "Komentar Naratif", bordersData3);
			}
			writesheet.addCell(label);
			writesheet.mergeCells(columnCell, r, 6, r);
			r++;
			
			//Roger - compComment is actually competency- nothing to do with comment
			Vector compComment = S.CompListSurvey(surveyID);	

			id = 0;
			
			for(int l=0; l<compComment.size(); l++)
			{
				int start = 0;
				voCompetency voComp = (voCompetency)compComment.elementAt(l);
				//Changed by Ha 13/06/08: from getCompetencyID to getPKCompetency
				id = voComp.getPKCompetency();
				compName = voComp.getCompetencyName();
				
				compName = UnicodeHelper.getUnicodeStringAmp(compName);
				merge = (double)compName.length() / (double)26;				
				
				BigDecimal BDComp = new BigDecimal(merge);
				BDComp.setScale(0, BDComp.ROUND_UP);
				BigInteger BIComp = BDComp.toBigInteger();
				int compMerge = BIComp.intValue();
				
				label = new Label(0, r, compName, bordersData2); 
				writesheet.addCell(label);
				
				Vector vComment = S.getComment(asgnt[totalOth], id);
				
				//Added by Roger 18 July 2008
				//Remove those empty string comment
				//Start-------
				Vector comments = new Vector();
				for(int m=0; m<vComment.size(); m++) {
					String temp = (String)vComment.elementAt(m);
					if (temp!=null && !"".equals(temp.trim())) {
						comments.add(temp);
					}
				}
				//End----------
				//Added by Roger 19 June 2008
				Vector kbNames = S.getKeyBehaviourNames(asgnt[totalOth], id);
				//--------------------------
				
				//Removed by Roger 18 July 2008
				//Unnecessary label. 
				
				//Start remove-------------------------
				//label = new Label(0, r, compName, bordersData2); 
				//writesheet.addCell(label);
				//End remove --------------------------
				
				
				//Edited by Roger 18th July 2008
				//use comments instead of vComment
				for(int m=0; m<comments.size(); m++)
				{
					String sComment = (String)comments.elementAt(m);
					//Added by Roger 19 June 2008
					String kbName = "";
					if (kbNames.size() > 0) {
						kbName = (String)kbNames.elementAt(m);
					}
					//---------------------------
					
					//Added by Roger 23 June 2008
					//Add additional line when display narrative comment for survey with keybehaviour
					if (start == 0 && LevelOfSurvey == 1 && comments.size() > 0) {
						label = new Label(1, r,"", bordersData2);
						writesheet.addCell(label);
						label = new Label(2, r,"", bordersData2);
						writesheet.mergeCells(2, r, 6, r);
						writesheet.addCell(label);
						r++;
					}
			
					if(start != 0) {
						label = new Label(1, r, "", bordersData2); 
						writesheet.addCell(label);		
						
						label = new Label(0, r, "", bordersData2); 
						writesheet.addCell(label);
					}
					start++;
					
					//Added by Roger 19 June 2008
					columnCell = 1;
					if (LevelOfSurvey == 1) {
						label = new Label(columnCell, r,kbName, bordersData2);
						writesheet.addCell(label);
						
						columnCell++;
					}
					//--------------------------
					
					com = UnicodeHelper.getUnicodeStringAmp(sComment);	
					merge = (double)com.length() / (double)70;				
				
					BigDecimal BD = new BigDecimal(merge);
					BigInteger BI = BD.toBigInteger();
					totalMerge = BI.intValue();
					if(totalMerge < compMerge)
						totalMerge = compMerge;
									
					writesheet.mergeCells(columnCell, r, 6, r);
					//writesheet.mergeCells(1, r, 6, r);
					writesheet.setRowView(r, 500*totalMerge);
	
					label = new Label(columnCell, r, com, bordersData2); 
					//label = new Label(1, r, com, bordersData2); 
					writesheet.addCell(label);
					r++;
				}
				if (start == 0) {
					//Added by Roger 19 June 2008
					columnCell = 1;	
					if (LevelOfSurvey == 1) {
						label = new Label(columnCell, r, "", bordersData2);
						writesheet.addCell(label);
						columnCell++;
					}
					
					writesheet.mergeCells(columnCell, r, 6, r);
					writesheet.setRowView(r, 500*compMerge);
					//--------------------------
					
					label = new Label(columnCell, r++, "Nil", bordersData2); 
					writesheet.addCell(label);
				}
					
			}
			
			r += 2;
		}		
		
		////////////////////////////////SUP//////////////////////////////			
		
		if(included == 1){
			
			for(int rc=totalOth+totalSelf, sup=0; rc<raterCode.length; rc++, sup++) {	
				if (ST.LangVer == 1)
					label = new Label(0, r++, "Narrative Comments by " + raterCode[rc], No_Borders);
				else if (ST.LangVer == 2)
					label = new Label(0, r++, "Komentar Naratif oleh " + raterCode[rc], No_Borders);
				writesheet.addCell(label);
				
				label = new Label(0, r, "Competency", bordersData3);
				if (ST.LangVer == 2)
					label = new Label(0, r, "Kompetensi", bordersData3);
				writesheet.addCell(label);
				
				//Added by Roger 19 June 2008 add one more column
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
				
				//label = new Label(1, r, "Narrative Comments", bordersData3);
				label = new Label(columnCell, r, "Narrative Comments", bordersData3);
				if (ST.LangVer == 2)
					label = new Label(columnCell, r, "Komentar Naratif", bordersData3); 
					//label = new Label(1, r, "Komentar Naratif", bordersData3); 
				writesheet.addCell(label);
				writesheet.mergeCells(columnCell, r, 6, r);
				r++;
				
				Vector compComment = S.CompListSurvey(surveyID);			
				
				id = 0;
				
				for(int l=0; l<compComment.size(); l++)
				{
					int start = 0;
					voCompetency voComp = (voCompetency)compComment.elementAt(l);
					//Changed by Ha 13/06/08: from getCompetencyID to getPKCompetency
					id = voComp.getPKCompetency();
					compName = voComp.getCompetencyName();
				
					compName = UnicodeHelper.getUnicodeStringAmp(compName);
					merge = (double)compName.length() / (double)26;				
					
					BigDecimal BDComp = new BigDecimal(merge);
					BDComp.setScale(0, BDComp.ROUND_UP);
					BigInteger BIComp = BDComp.toBigInteger();
					int compMerge = BIComp.intValue();
					
					label = new Label(0, r, compName, bordersData2); 
					writesheet.addCell(label);
					
					Vector vComment = S.getComment(asgnt[totalOth + totalSelf + sup], id);
					
					//Added by Roger 18 July 2008
					//Remove those empty string comment
					//Start-------
					Vector comments = new Vector();
					for(int m=0; m<vComment.size(); m++) {
						String temp = (String)vComment.elementAt(m);
						if (temp!=null && !"".equals(temp.trim())) {
							comments.add(temp);
						}
					}
					
					//Added by Roger 19 June 2008
					//Edited by Roger 23 June 2008
					//Fix bug when displaying supervisor keybehaviour narrative comment
					Vector kbNames = S.getKeyBehaviourNames(asgnt[totalOth + totalSelf + sup], id);
					
					//---------------------------
					
					//Added by Roger 23 June 2008
					//Add additional line when display narrative comment for survey with keybehaviour
					if (start == 0 && LevelOfSurvey == 1 && comments.size() > 0) {
						label = new Label(1, r,"", bordersData2);
						writesheet.addCell(label);
						label = new Label(2, r,"", bordersData2);
						writesheet.mergeCells(2, r, 6, r);
						writesheet.addCell(label);
						r++;
					}
					
					
					//Removed by Roger 18 July 2008
					//Unnecessary label. 
					
					//Start remove-------------------------
					//label = new Label(0, r, compName, bordersData2); 
					//writesheet.addCell(label);
					//End remove --------------------------
					
					
					//Edit by Roger 18 July 2008
					//use comments instead of vComments
					for(int m=0; m<comments.size(); m++)			
					{
						String sComment = (String)comments.elementAt(m);
						

						
						//Added by Roger 19 June 2008
						String kbName = "";
						if (kbNames.size() > 0) {
							kbName = (String)kbNames.elementAt(m);
						}
						//---------------------------
						if(start != 0) {
							label = new Label(1, r, "", bordersData2); 
							writesheet.addCell(label);															
						}
						start++;
						
						//Added by Roger 19 June 2008
						columnCell = 1;
						if (LevelOfSurvey == 1) {
							label = new Label(columnCell, r,kbName, bordersData2);
							writesheet.addCell(label);
							
							label = new Label(0, r, "", bordersData2); 
							writesheet.addCell(label);
							
							columnCell++;
						}
						//--------------------------
						
						
						
						com = UnicodeHelper.getUnicodeStringAmp(sComment);	
						merge = (double)com.length() / (double)70;				
					
						BigDecimal BD = new BigDecimal(merge);
						BigInteger BI = BD.toBigInteger();
						totalMerge = BI.intValue();
						if(totalMerge < compMerge)
							totalMerge = compMerge;
						
						writesheet.mergeCells(columnCell, r, 6, r);
						//writesheet.mergeCells(1, r, 6, r);
						writesheet.setRowView(r, 500*totalMerge);
						
						label = new Label(columnCell, r, com, bordersData2); 
						//label = new Label(1, r, com, bordersData2); 
						writesheet.addCell(label);
						r++; 
					}
					if (start == 0) {
						//Added by Roger 19 June 2008
						columnCell = 1;	
						if (LevelOfSurvey == 1) {
							label = new Label(columnCell, r, "", bordersData2); 
							writesheet.addCell(label);
							columnCell++;
						}
						//--------------------------
						
						writesheet.mergeCells(columnCell, r, 6, r);
						writesheet.setRowView(r, 500*compMerge);
						label = new Label(columnCell, r++, "Nil", bordersData2); 
						writesheet.addCell(label);
					}
						
				}
			}
			
			////////////////////////////////OTH//////////////////////////////			
			r += 2;
			
			for(int oth=0; oth<totalOth; oth++) {		
				if (ST.LangVer == 1)
					label = new Label(0, r++, "Narrative Comments by " + raterCode[oth], No_Borders);
				else if (ST.LangVer == 2)
					label = new Label(0, r++, "Komentar Naratif oleh " + raterCode[oth], No_Borders);
				writesheet.addCell(label);
				
				label = new Label(0, r, "Competency", bordersData3);
				if (ST.LangVer == 2)
					label = new Label(0, r, "Kompetensi", bordersData3);
				writesheet.addCell(label);
				
				//Added by Roger 19 June 2008 add one more column
				int columnCell = 1;
				if(LevelOfSurvey == 1) {
					label = new Label(columnCell, r, "Key Behaviour", bordersData3);
					if (ST.LangVer == 2) {
						label = new Label(columnCell, r, "Perilaku Kunci", bordersData3);	
					}
					columnCell++;
					writesheet.addCell(label);
				}
				
				label = new Label(columnCell, r, "Narrative Comments", bordersData3);
				if (ST.LangVer == 2)
					label = new Label(columnCell, r, "Komentar Naratif", bordersData3);
				writesheet.addCell(label);
				writesheet.mergeCells(columnCell, r, 6, r);
				r++;
				
				Vector compComment = S.CompListSurvey(surveyID);	
				
				System.out.println("---------------------compComment -------------------");
				
				for (int i=0; i < compComment.size(); i++) {
					voCompetency voComp = (voCompetency)compComment.elementAt(i);
					System.out.println(voComp.getCompetencyName());
				}
				
				System.out.println("-------------------------------------");
				
				id = 0;
				
				for(int l=0; l<compComment.size(); l++)
				{
					int start = 0;
					voCompetency voComp = (voCompetency)compComment.elementAt(l);
					//Changed by Ha 13/06/08: from getCompetencyID to getPKCompetency
					id = voComp.getPKCompetency();
					compName = voComp.getCompetencyName();
				
					compName = UnicodeHelper.getUnicodeStringAmp(compName);
					merge = (double)compName.length() / (double)26;				
					
					BigDecimal BDComp = new BigDecimal(merge);
					BDComp.setScale(0, BDComp.ROUND_UP);
					BigInteger BIComp = BDComp.toBigInteger();
					int compMerge = BIComp.intValue();
						
					label = new Label(0, r, compName, bordersData2); 
					writesheet.addCell(label);
					
					Vector vComment = S.getComment(asgnt[oth], id);
					
					//Added by Roger 18 July 2008
					//Remove those empty string comment
					//Start-------
					Vector comments = new Vector();
					for(int m=0; m<vComment.size(); m++) {
						String temp = (String)vComment.elementAt(m);
						if (temp!=null && !"".equals(temp.trim())) {
							comments.add(temp);
						}
					}
					//End----------
					
					//Added by Roger 19 June 2008
					Vector kbNames = S.getKeyBehaviourNames(asgnt[oth], id);
					//-------------------------
					
					//Added by Roger 23 June 2008
					//Add additional line when display narrative comment for survey with keybehaviour
					if (start == 0 && LevelOfSurvey == 1 && comments.size()>0) {
						label = new Label(1, r,"", bordersData2);
						writesheet.addCell(label);
						label = new Label(2, r,"", bordersData2);
						writesheet.mergeCells(2, r, 6, r);
						writesheet.addCell(label);
						r++;
					}
					
					//Removed by Roger 18 July 2008
					//Unnecessary label. 
					
					//Start remove-------------------------
					//label = new Label(0, r, compName, bordersData2); 
					//writesheet.addCell(label);
					//End remove --------------------------
					
					
					//Edit by Roger 18 July 2008
					//use comments instead of vComments
					for(int m=0; m<comments.size(); m++)
					{
						String sComment = (String)comments.elementAt(m);
						
						//Added by Roger 19 June 2008
						String kbName = "";
						if (kbNames.size() > 0) {
							kbName = (String)kbNames.elementAt(m);
						}
						//-------------------------
						
						if(start != 0) {
							label = new Label(1, r, "", bordersData2); 
							writesheet.addCell(label);															
						}
						start++;
						
						//Added by Roger 19 June 2008
						columnCell = 1;
						if (LevelOfSurvey == 1) {
							label = new Label(columnCell, r,kbName, bordersData2);
							writesheet.addCell(label);
							
							label = new Label(0, r, "", bordersData2); 
							writesheet.addCell(label);
							
							columnCell++;
						}
						//--------------------
						
						com = UnicodeHelper.getUnicodeStringAmp(sComment);	
						merge = (double)com.length() / (double)70;				
					
						BigDecimal BD = new BigDecimal(merge);
						BigInteger BI = BD.toBigInteger();
						totalMerge = BI.intValue();										
						
						if(totalMerge < compMerge)
							totalMerge = compMerge;
						//Edit by Roger 19 June 2008					
						//writesheet.mergeCells(1, r, 6, r);
						writesheet.mergeCells(columnCell, r, 6, r);
						writesheet.setRowView(r, 500*totalMerge);
		
						//label = new Label(1, r, com, bordersData2); 
						label = new Label(columnCell, r, com, bordersData2); 
						writesheet.addCell(label);
						r++;
					}
					if (start == 0) {
						//Added by Roger 19 June 2008
						columnCell = 1;	
						if (LevelOfSurvey == 1) {
							label = new Label(columnCell, r, "", bordersData2); 
							writesheet.addCell(label);
							columnCell++;
						}
						//--------------------------
						writesheet.mergeCells(columnCell, r, 6, r);
						writesheet.setRowView(r, 500*compMerge);

						
						label = new Label(columnCell, r++, "Nil", bordersData2); 
						writesheet.addCell(label);
					}
						
				}
				r += 2;
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
				EV.addRecord("Print", "Target Result", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
			} catch(SQLException SE) {
				System.out.println(SE.getMessage());
			}

	}

	
	public static void main (String[] args)throws SQLException, Exception
	{
		ExcelTargetResults Rpt = new ExcelTargetResults();

		// competency level = 340
		// key behaviour level = 343
		//Rpt.WriteToReport(343,6);
		Rpt.WriteToReport(464, 13941, 124, "TargetResult.xls");
			
	}
}