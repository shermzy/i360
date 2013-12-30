package CP_Classes;

import java.sql.*;
import java.math.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblSurvey;

import jxl.write.*; 
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.WorkbookSettings;
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
import jxl.format.PaperSize;

public class Report_ToyotaIDP
{
	private Database db;
	private Setting setting;
	private Create_Edit_Survey CE_Survey;
	private AssignTarget_Rater ATR;
	private SurveyResult S;
	private Calculation C;
	
	/**
	 *	Declaration of Excel API variables
	 */
	private File inputWorkbook;
	private File outputWorkbook;
	private WorkbookSettings ws;
	private WritableWorkbook w;
	private WritableSheet writesheet;
	private Label label;
	private WritableFont fontBold;
	private WritableCellFormat withBorders, withBorders_Center;

	
	/**
	 *	Declaration of variables
	 */
	private String fileName_fix = "ToyotaIDP.xls";
	private String Header_str ="";
	
	/**
	 * Creates a new intance of ToyotaIDP object.
	 */
	public Report_ToyotaIDP()
	{
		db = new Database();
		setting = new Setting();
		CE_Survey = new Create_Edit_Survey();
		S = new SurveyResult();
		C = new Calculation();
		ATR = new AssignTarget_Rater();
	}
	
	public void sheetsetting(WritableSheet s) throws SQLException, Exception
	{
		s.getSettings().setOrientation(PageOrientation.LANDSCAPE);
		s.setHeader(null, null, Header_str);
		s.getSettings().setPaperSize(PaperSize.A4);
		//s.setHeader(orgName, "", Survey_Name + "\n" + targetDetail [0] + targetDetail [1]);
		s.setFooter("©Copyright 3Sixty Profiler is a product of Pacific Century Consulting Pte Ltd.", "", "");

	}
	
	/**
	 * Initialize the template and do the necessary replacement of strings ie.name,department etc
	 */
	
	public void wbInitialize(String fileName) throws IOException, WriteException, BiffException
	{
		ws = new WorkbookSettings();
	    ws.setLocale(new Locale("en", "EN"));
	    
	    System.out.println("Reading...");
	    String input = setting.getReport_Path_Template() + "\\" + fileName_fix;
	    inputWorkbook = new File(input);
	    Workbook inputFile = Workbook.getWorkbook(inputWorkbook);
	
	    System.out.println("Writing...");

		String output = setting.getReport_Path() + "\\" +fileName;

		outputWorkbook = new File(output);		
    	w = Workbook.createWorkbook(outputWorkbook, inputFile, ws);
		
		writesheet = w.getSheet(0);
	}
	
	/**
	 * Initialize the fonts and borders
	 */
	
	public void fontInit()	throws IOException, WriteException, BiffException
	{
		fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 

		withBorders = new WritableCellFormat(fontBold);
		withBorders.setBorder(Border.LEFT, BorderLineStyle.THIN);
		withBorders.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		withBorders.setBorder(Border.TOP, BorderLineStyle.THIN);
		withBorders.setWrap(true);
		withBorders.setVerticalAlignment(VerticalAlignment.CENTRE);
	
		withBorders_Center = new WritableCellFormat(fontBold);
		withBorders_Center.setBorder(Border.ALL, BorderLineStyle.THIN);
		withBorders_Center.setAlignment(Alignment.CENTRE);
		withBorders_Center.setVerticalAlignment(VerticalAlignment.CENTRE);
	}
	
	public void write(int SurveyID, int PKUser, String filename) throws IOException, WriteException, BiffException, SQLException, Exception
	{
		wbInitialize(filename);
		fontInit();
		String TargetDetail[] = CE_Survey.getUserDetail_ADV(PKUser);
		votblSurvey rs_SurveyDetail = CE_Survey.getSurveyDetail(SurveyID);
		
		String jobPost = "";
		
		if(rs_SurveyDetail != null) {
			jobPost = rs_SurveyDetail.getJobPosition();
		}
		
		String FullName = TargetDetail[0].toUpperCase()+" "+TargetDetail[1].toUpperCase();
		
		label= new Label(1, 5, TargetDetail[4].toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		//Group_Section
		label= new Label(11, 5, ATR.getTargetGroup(SurveyID, PKUser).toUpperCase(),withBorders);
		//label= new Label(11, 5, TargetDetail[9].toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		label= new Label(1, 6, FullName,withBorders);
		writesheet.addCell(label);
		
		label= new Label(11, 6, jobPost.toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		label= new Label(1, 7, TargetDetail[10].toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		label= new Label(11, 7, TargetDetail[3].toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		//Division
		label= new Label(1, 8, ATR.getTargetDivision(SurveyID, PKUser).toUpperCase(),withBorders);
		//label= new Label(1, 8, TargetDetail[7].toUpperCase(),withBorders);
		writesheet.addCell(label);
		
		//Department
		label= new Label(1, 9, ATR.getTargetDepartment(SurveyID, PKUser).toUpperCase(),withBorders);
		//label= new Label(1, 9, TargetDetail[6].toUpperCase(),withBorders);
		writesheet.addCell(label);

		printResults(SurveyID, PKUser);
		
		w.write();
    	w.close();
	}
	
	/**
	 * Writes results on excel.
	 */
	public void printResults(int surveyID, int targetID) throws IOException, WriteException, SQLException, Exception
	{
		
		// modified by Jenty on 18th March 2005
		// modified together with SurveyResult.java
		
		int r = 15;
		int c = 0;
		int LevelOfSurvey = C.LevelOfSurvey(surveyID);		
		int totalComp = S.TotalCompetency(surveyID);

		Vector compOrKBList = CompOrKBList(surveyID);	
		Vector targetResult = TargetGapDisplayed(surveyID, targetID);
		Vector Gap = S.getTargetGap(surveyID, targetID);
		
		Vector CompTrimmedMean = null;
		//ResultSet AvgGap = null;
	
		if(LevelOfSurvey == 1) //{
			CompTrimmedMean = S.getAvgMeanForGap(surveyID, targetID);
			//AvgGap = S.getAvgGap(surveyID, targetID);
		//}
		
		c = 0;
		int comp=0;
		int temp1=0;
		int counter=1;
		
		int compCount = 0;
		int gapCounter = 0;
		for(int k=0; k<compOrKBList.size(); k++)
		{
			voCompetency vo = (voCompetency)compOrKBList.elementAt(k);
			String [] gapArr = null;
			if(targetResult.size() != 0)
			{
				if(gapCounter < Gap.size())
					gapArr = (String[])Gap.elementAt(gapCounter);
				
				temp1=1;
			}
		
			writesheet.insertRow(r);
			
			label = new Label(c++, r, Integer.toString(counter), withBorders_Center); 
			writesheet.addCell(label);
				
			String compName = vo.getCompetencyName().trim();
			
			double merge = (double)compName.length() / (double)41;
			
			int thai = compName.indexOf("&#");
			if(thai != -1)
				merge = (double)compName.length() / (double)41 / 3;
				
			label = new Label(c, r, UnicodeHelper.getUnicodeStringAmp(compName), withBorders); 
			writesheet.addCell(label);	
			
			BigDecimal BD = new BigDecimal(merge);
			BD.setScale(0, BD.ROUND_UP);
			BigInteger BI = BD.toBigInteger();
			int totalMerge = BI.intValue() + 1;

			int rowHeight = 300;
			writesheet.setRowView(r, rowHeight * totalMerge);		
			
			c++;
			double rt1=0, rt2=0;
			
			if(LevelOfSurvey == 1) {
			
				//AvgGap.next();
				if(compCount < CompTrimmedMean.size()) {
					String [] arr = (String[])CompTrimmedMean.elementAt(compCount);
					rt1 = Double.parseDouble(arr[2]);					
					rt1 = Math.round(rt1 * 100.0) / 100.0;
					compCount ++;
				}
				
				if(compCount < CompTrimmedMean.size()) {
					String [] arr = (String[])CompTrimmedMean.elementAt(compCount);
					rt2 = Double.parseDouble(arr[2]);		
					rt2 = Math.round(rt2 * 100.0) / 100.0;
					compCount ++;
				}
				
				
				label = new Label(c++, r, Double.toString(rt2), withBorders_Center); 
				writesheet.addCell(label);	
				label = new Label(c++, r, Double.toString(rt1), withBorders_Center); 
				writesheet.addCell(label);
				
				double avg = rt1 - rt2;
				
				avg = Math.round(avg * 100.0) / 100.0;
				
				//label = new Label(c++, r, Double.toString(AvgGap.getDouble(1)), withBorders_Center);
				label = new Label(c++, r, Double.toString(avg), withBorders_Center);
				writesheet.addCell(label);
				
				for(int i=0; i<10; i++) {				
					label = new Label(c, r, "", withBorders_Center); 
					writesheet.addCell(label);
					if(i == 8) {
						writesheet.mergeCells(c, r, c+3, r);
						c += 3;
					}
					c++;
					
				}
				
				c = 2;
				
			}else {
				
				String [] arr = (String[])targetResult.elementAt(k);
				
				c=3;			
				double Result;
				for(int i=0; i<2; i++) {	
					if(temp1 == 1)
						Result = Double.parseDouble(arr[3]);
					else
						Result = 0;
						
					label = new Label(c--, r, Double.toString(Result), withBorders_Center); 
					writesheet.addCell(label);
					
					if(i < 1 && i<targetResult.size())		//modified to cater amendment on SurveyResult.java
						arr = (String[])targetResult.elementAt(i);
					
				}
				
				c=4;
				label = new Label(c++, r, gapArr[1], withBorders_Center); 
				writesheet.addCell(label);					
				
				
		}
		comp++;
		if(comp == totalComp)
			comp = 0;
	
		r++;
		c = 0;
		counter++;
		
		}
				

	}
	

	/**
	 * Get the Competency or Key Behaviour List for the particular Survey.
	 */
	public Vector CompOrKBList(int surveyID) {
		String query = "";
		
		query = query + "SELECT Competency.PKCompetency, Competency.CompetencyName FROM Competency ";
		query = query + "INNER JOIN tblSurveyCompetency ON ";
		query = query + "Competency.PKCompetency = tblSurveyCompetency.CompetencyID WHERE ";
		query = query + "tblSurveyCompetency.SurveyID = " + surveyID + " ORDER BY Competency.PKCompetency";

		Vector v = new Vector();
    	

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
            	
            	voCompetency vo = new voCompetency();
            	vo.setCompetencyID(rs.getInt(1));
            	vo.setCompetencyName(rs.getString(2));
            	
            	v.add(vo);
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("Report_ToyotaIDP.java - ComporKBList - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }

        return v;
	}
	
	/**
	  * Retrieves the trimmedMean/AvgMean results on each Competency to be displayed in target gap.
	  */
	public Vector TargetGapDisplayed(int surveyID, int targetID) {
		String query = "";
		int surveyLevel = C.LevelOfSurvey(surveyID);
		int reliabilityCheck = C.ReliabilityCheck(surveyID); // 0=trimmed mean

		String tableName = "";
		String columnName = "";

		Vector v = new Vector();
		
		if(surveyLevel == 0) {
			if(reliabilityCheck == 0) {
				tableName = "tblTrimmedMean";
				columnName = "TrimmedMean";
			}
			else {
				tableName = "tblAvgMean";
				columnName = "AvgMean";
			}

			query = query + "SELECT " + tableName + ".RatingTaskID, Competency.PKCompetency, Competency.CompetencyName, ";
			query = query + tableName + "." + columnName + " AS Result FROM " + tableName + " INNER JOIN ";
			query = query + "tblRatingTask ON " + tableName + ".RatingTaskID = tblRatingTask.RatingTaskID ";
			query = query + "INNER JOIN Competency ON " + tableName + ".CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE " + tableName + ".Type = 1 AND " + tableName + ".SurveyID = " + surveyID;
			query = query + " AND " + tableName + ".TargetLoginID = " + targetID;
			query = query + " and tblRatingTask.RatingCode IN ('CP', 'CPR', 'FPR')";
			query = query + " ORDER BY " + tableName + ".CompetencyID, " + tableName + ".RatingTaskID";

		} else {
			query = query + "SELECT distinct tblAvgMean.RatingTaskID, Competency.PKCompetency, Competency.CompetencyName, ";
			query = query + "cast(avg(tblAvgMean.AvgMean) as numeric(38, 2)) AS Result FROM ";
			query = query + "tblAvgMean INNER JOIN Competency ON ";
			query = query + "tblAvgMean.CompetencyID = Competency.PKCompetency ";
			query += "INNER JOIN tblRatingTask ON tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID WHERE ";
			query = query + "tblAvgMean.SurveyID = " + surveyID + " AND tblAvgMean.TargetLoginID = " + targetID;
			query = query + "  AND tblAvgMean.Type = 1 ";
			query += "AND tblRatingTask.RatingCode IN ('CP', 'CPR', 'FPR') ";
            query += "group by tblAvgMean.RatingTaskID, Competency.PKCompetency, Competency.CompetencyName ";
			query = query + " order by Competency.PKCompetency,tblAvgMean.RatingTaskID";
		}

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
            	String [] arr = new String[4];
            	arr[0] = rs.getString(1);
            	arr[1] = rs.getString(2);
            	arr[2] = rs.getString(3);
            	arr[3] = rs.getString(4);
            	
            	v.add(arr);
            }
       
        }
        catch(Exception E) 
        {
            System.err.println("Report_ToyotaIDP.java - TargetGapDisplayed - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }

		return v;

	}

	public static void main (String[] args)throws SQLException, Exception
	{
		Report_ToyotaIDP Rpt = new Report_ToyotaIDP();
		//Rpt.write(438,3495,"idptoyota.xls");
		Rpt.write(450,112,"idptoyota.xls");
	}
}