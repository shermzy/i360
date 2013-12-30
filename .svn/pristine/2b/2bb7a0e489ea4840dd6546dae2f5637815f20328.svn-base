package CP_Classes;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import util.Utils;

import CP_Classes.vo.voDevelopmentPlan;
import CP_Classes.vo.voKeyBehaviour;
import CP_Classes.vo.voUser;

import jxl.read.biff.BiffException;
import jxl.write.*; 
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.UnderlineStyle;

/**
 * This class implements all the method related to development plan report
 * 
 * @author yuni
 *
 */

public class DevelopmentPlanReport {

	/**
	 * Declaration of new object of class Setting.
	 */
	private static String item = "Report";
	private User USR;
	private EventViewer EV;
	private DevelopmentPlan DP;

	/**
	 * Declaration of new object of class Setting.
	 */
	private Setting ST;
	
	private String fileName;		//total column for a page landscape = 15

	private File inputWorkbook;
	private File outputWorkbook;
	private WorkbookSettings ws;
	private WritableWorkbook w;
    private Sheet [] Sh;
    
	private Label label;
	private WritableSheet writesheet;
	private WritableCellFormat cellBOLD;	
	private WritableCellFormat cellTitleBOLD;
	private WritableCellFormat cellStatusBOLD;
	private WritableFont fontBold, fontFace, fontFaceBold, fontStatusBold;
	private WritableWorkbook workbook;
	private	WritableCellFormat bordersData1;
	private WritableCellFormat bordersData2;
	private WritableCellFormat bordersData3;
	private WritableCellFormat bordersData4;
	private WritableCellFormat No_Borders;
	private WritableCellFormat No_Borders_Bold_Center;
	private WritableCellFormat No_Borders_No_Bold;
	private WritableCellFormat No_Borders_No_Bold_Center;
	private WritableCellFormat NoBorder_Header;
	private WritableCellFormat Borders_Bold_A;
	private WritableCellFormat NoBorder_Header_Center;
	private WritableCellFormat No_Borders_Bold_Left;
	private WritableCellFormat No_Borders_Normal;
	private WritableCellFormat No_Borders_No_Bold_Left;
	int row_title = 0;
	int size = 55;
	
	/**
	 * Create new instance of ExcelRaterResults object.
	 * @throws SQLException 
	 */
	public DevelopmentPlanReport() throws SQLException {
		USR  = new User();
		EV = new EventViewer();
		ST = new Setting();
		DP = new DevelopmentPlan();
		row_title = 2;
		
	}
	
	/**
	 * Initialize and set all excel application and alignment.
	 * @throws BiffException 
	 */
	public void write() throws IOException, WriteException, BiffException
	{
		
		fontFace = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
		fontFaceBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
		fontBold = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD); 
		fontStatusBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
		
		fontBold.setUnderlineStyle(UnderlineStyle.SINGLE);
		fontStatusBold.setUnderlineStyle(UnderlineStyle.SINGLE); 
		// font bold
		cellBOLD = new WritableCellFormat(fontFaceBold);
		cellBOLD.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellBOLD.setWrap(true);
		cellBOLD.setAlignment(Alignment.RIGHT);
		
		cellTitleBOLD = new WritableCellFormat(fontBold);
		cellTitleBOLD.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		cellStatusBOLD = new WritableCellFormat(fontStatusBold);
		cellStatusBOLD.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// border center, font no bold
		bordersData1 = new WritableCellFormat(fontBold);
		bordersData1.setBorder(Border.TOP, BorderLineStyle.THIN);
		
		// border left, set wrap			
		bordersData2 = new WritableCellFormat(fontFace);
		bordersData2.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData2.setWrap(true);
		bordersData2.setAlignment(Alignment.LEFT);
		bordersData2.setVerticalAlignment(VerticalAlignment.TOP);
		
		// border center, background grey, font bold (for header)
		bordersData3 = new WritableCellFormat(fontFace);
		bordersData3.setBorder(Border.ALL, BorderLineStyle.THIN);
		bordersData3.setWrap(true);
		bordersData3.setAlignment(Alignment.CENTRE);
		bordersData3.setVerticalAlignment(VerticalAlignment.TOP);
		
		// bold with border center
		bordersData4 = new WritableCellFormat(fontFace);
		bordersData4.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		bordersData4.setWrap(true);
		bordersData4.setAlignment(Alignment.LEFT);
		bordersData4.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		// font bold no border				
		No_Borders = new WritableCellFormat(fontFace);
		No_Borders.setWrap(true);
		No_Borders.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		Borders_Bold_A = new WritableCellFormat(fontBold);	
		Borders_Bold_A.setBorder(Border.NONE, BorderLineStyle.NONE);	
		Borders_Bold_A.setBorder(Border.LEFT, BorderLineStyle.THIN);
		Borders_Bold_A.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		Borders_Bold_A.setWrap(true);
		Borders_Bold_A.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		No_Borders_Bold_Center = new WritableCellFormat(fontBold);
		No_Borders_Bold_Center.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_Bold_Center.setAlignment(Alignment.CENTRE);
		
		No_Borders_Bold_Center.setBorder(Border.LEFT, BorderLineStyle.THIN);
		No_Borders_Bold_Center.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		
		// font no bold no border			
		No_Borders_No_Bold = new WritableCellFormat(fontFace);
		No_Borders_No_Bold.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_No_Bold.setWrap(true);
		No_Borders_No_Bold.setVerticalAlignment(VerticalAlignment.CENTRE);
		No_Borders_No_Bold.setBorder(Border.LEFT, BorderLineStyle.THIN);
		No_Borders_No_Bold.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		
		
		No_Borders_No_Bold_Left = new WritableCellFormat(fontFace);
		No_Borders_No_Bold_Left.setVerticalAlignment(VerticalAlignment.CENTRE);
		No_Borders_No_Bold_Left.setAlignment(Alignment.LEFT);
		
//		 font no bold no border				
		No_Borders_Normal = new WritableCellFormat(fontFace);
		No_Borders_Normal.setBorder(Border.NONE, BorderLineStyle.NONE);
		No_Borders_Normal.setWrap(true);
		No_Borders_Normal.setVerticalAlignment(VerticalAlignment.JUSTIFY);
	
		// font no bold no border center
		No_Borders_No_Bold_Center = new WritableCellFormat(fontFace);		
		No_Borders_No_Bold_Center.setWrap(true);
		No_Borders_No_Bold_Center.setVerticalAlignment(VerticalAlignment.CENTRE);		
		No_Borders_No_Bold_Center.setAlignment(Alignment.CENTRE);
		No_Borders_No_Bold_Center.setBorder(Border.LEFT, BorderLineStyle.THIN);
		No_Borders_No_Bold_Center.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		
		
		// no border with grey background
		NoBorder_Header = new WritableCellFormat(fontBold);		
		NoBorder_Header.setBackground(Colour.GRAY_25);
		
		NoBorder_Header_Center = new WritableCellFormat(fontBold);		
		NoBorder_Header_Center.setBackground(Colour.GRAY_25);
		NoBorder_Header_Center.setAlignment(Alignment.CENTRE);
		NoBorder_Header_Center.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		No_Borders_Bold_Left = new WritableCellFormat(fontBold);
		No_Borders_Bold_Left.setAlignment(Alignment.LEFT);
		No_Borders_Bold_Left.setWrap(true);
		No_Borders_Bold_Left.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		String sTemplate = "Individual_Development_Plan_Template.xls";
		
		ws = new WorkbookSettings();
	    ws.setLocale(new Locale("en", "EN"));
	    
	    System.out.println("Reading...");
	    String input = ST.getReport_Path_Template() + "/" + sTemplate;
	    inputWorkbook = new File(input);
	    Workbook inputFile = Workbook.getWorkbook(inputWorkbook);
	
	    System.out.println("Writing...");
	    String output = ST.getReport_Path() + "/" +fileName;
		
		if(inputWorkbook.exists())  
			inputFile = Workbook.getWorkbook(inputWorkbook);

		outputWorkbook = new File(output);		
		
		w = Workbook.createWorkbook(outputWorkbook, inputFile, ws);
    	Sh = w.getSheets();
    	writesheet = w.getSheet(0); 
		
    	Date timestamp = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		String temp = dFormat.format(timestamp);
		//System.out.println(temp);
		writesheet.setHeader("", "", "Pacific Century Consulting Pte Ltd.");
		writesheet.setFooter("Date of printing: " + temp +  "\n" + "Copyright © 3-Sixty Profiler® is a product of Pacific Century Consulting Pte Ltd.", "", "Page &P of &N");
	}
	
	/**
	 * Print development plan
	 * 
	 * @param iSurveyID
	 * @param iTargetLoginID
	 * @throws IOException
	 * @throws WriteException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void printPlan(int iSurveyID, int iTargetLoginID) 
		throws IOException, WriteException, SQLException, Exception
	{

		
		/*double merge = (double)compName.length() / (double)60;				
		
		BigDecimal BD = new BigDecimal(merge);
		BD.setScale(0, BigDecimal.ROUND_UP);
		BigInteger BI = BD.toBigInteger();
		int totalMerge = BI.intValue() + 1;
		
		writesheet.mergeCells(4, row_title, totalColumn, row_title);
		int rowView = 275;
		int mergeView = rowView * totalMerge;
		writesheet.setRowView(row_title, mergeView);*/
		
		int iColumn = 0;
		int iRow = 0;
		
		//Retrieve the list of competencies
		//Edited by Roger 20 June 2008
		//Vector v = DP.getCompetencyList(iSurveyID, iTargetLoginID);
		Vector v = DP.getCompetencyListByDevelopmentPlanSurveyId(iSurveyID, iTargetLoginID);
		
		Cell cName = Sh[0].findCell("<Name>");
		Cell cJobPosition = Sh[0].findCell("<JobPosition>");
		Cell cSupervisor = Sh[0].findCell("<Supervisor>");
		Cell cOrganisation = Sh[0].findCell("<Organisation>");
		
		//Retrieve the survey info
		String [] info = DP.getSurveyInfo(iSurveyID, iTargetLoginID);
		String sName = "";
		String sOrganisationName = "";
		String sJobPosition = "";
		String sSupervisor = DP.getSupervisorName(iTargetLoginID);
		
		sName = info[0];
		sJobPosition = sJobPosition + info[1];
		sOrganisationName = info[2];
			
	
		if(cName != null) {
			label= new Label(cName.getColumn(), cName.getRow(), sName, bordersData4);
			writesheet.addCell(label); 
		}
		
		if(cJobPosition != null) {
			label= new Label(cJobPosition.getColumn(), cJobPosition.getRow(), sJobPosition, bordersData4);
			writesheet.addCell(label); 
		}

		if(cSupervisor != null) {
//System.out.println("PrintPlan >> sSupervisor = " + sSupervisor );
			label= new Label(cSupervisor.getColumn(), cSupervisor.getRow(), sSupervisor, bordersData4);
			writesheet.addCell(label); 
		}
		
		if(cOrganisation != null) {
			label= new Label(cOrganisation.getColumn(), cOrganisation.getRow(), sOrganisationName, bordersData4);
			writesheet.addCell(label); 
		}
		
		
		iColumn = 0;
		iRow = 6;
		
		int iOriginalColumn = iColumn;
		writesheet.removeRow(iRow);
		
		//Retrieve CP and CPR scores
		HashMap CPMap = DP.CPCPR(iSurveyID, iTargetLoginID, "CP");
		HashMap CPRMap = DP.CPCPR(iSurveyID, iTargetLoginID, "CPR");
		
		for(int i=0; i<v.size(); i++) {
			voDevelopmentPlan vo = (voDevelopmentPlan)v.elementAt(i);
		
			int iOriginalRow = iRow;
			int iKBRow = iRow;
			int iResourceRow = iRow;
			iColumn = iOriginalColumn;
			
			int iFKCompetency = vo.getFKCompetency();
			String sCompetencyName = UnicodeHelper.getUnicodeStringAmp(vo.getCompetencyName());

			
			Vector vKB = DP.getKeyBehaviourList(iFKCompetency);
			
			//Vector vDevPlan = DP.getDevelopmentPlan(iTargetLoginID, iFKCompetency);
			//Edited by Roger 20 June 2008
			Vector vDevPlan = DP.getDevelopmentPlanBySurveyId(iTargetLoginID, iFKCompetency, iSurveyID);
			
			iColumn++;
			
			for(int j=0; j<vKB.size(); j++) {
				voKeyBehaviour voKB = (voKeyBehaviour)vKB.elementAt(j);
				String sKB = voKB.getKeyBehaviour();
				
				iKBRow = iRow;
				label= new Label(iColumn, iRow, UnicodeHelper.getUnicodeStringAmp(sKB), bordersData2);
				writesheet.addCell(label);
				
				if(iRow == 6) {
					writesheet.setRowView(iRow, countTotalRow(UnicodeHelper.getUnicodeStringAmp(sKB),27));
					
				}
					
				
				iRow++;
			}

			if(vKB.size() < vDevPlan.size()) {
				label= new Label(iColumn, iRow, "", bordersData2);
				writesheet.addCell(label);
				writesheet.mergeCells(iColumn, iRow, iColumn, iRow+(vDevPlan.size()-vKB.size()-1));
				
			}
			
			iRow = iOriginalRow;
			
			iColumn = iColumn+2;
			for(int j=0; j<vDevPlan.size(); j++) {
				voDevelopmentPlan voDP = (voDevelopmentPlan)vDevPlan.elementAt(j);

				String sType = voDP.getType();
				String sResource = UnicodeHelper.getUnicodeStringAmp(voDP.getResource());
				String sTimeFrame = voDP.getProposedDate();
				String sCompletionDate = voDP.getCompletionDate();
				String sProcess = UnicodeHelper.getUnicodeStringAmp(voDP.getProcess());
				
				iResourceRow = iRow;
                                				
				label= new Label(iColumn+1, iRow, sType, bordersData2);
				writesheet.addCell(label);
				//if(iRow == 6) 
					//writesheet.setRowView(iRow, countTotalRow(UnicodeHelper.getUnicodeStringAmp(sType),6));
				
				
				label= new Label(iColumn+2, iRow, sResource, bordersData2);
				writesheet.addCell(label);
				//if(iRow == 6)
					//writesheet.setRowView(iRow, countTotalRow(sResource,27));
					
				
				label= new Label(iColumn+3, iRow, sTimeFrame, bordersData3);
				writesheet.addCell(label);
				
				label= new Label(iColumn+4, iRow, sProcess, bordersData2);
				writesheet.addCell(label);
				//if(iRow == 6) 
					//writesheet.setRowView(iRow, countTotalRow(UnicodeHelper.getUnicodeStringAmp(sProcess),13));
					
				
				label= new Label(iColumn+5, iRow, sCompletionDate, bordersData3);
				writesheet.addCell(label);
				
                                // Added by DeZ, 17.07.08, fix row height of generated individual development plan report
                                // Find the 'highest' cell's height and set it as row height
                                int highestH = countTotalRow(UnicodeHelper.getUnicodeStringAmp(sType),6);
                                int temp = countTotalRow(sResource,27);
//System.out.println(">>PrintPlan >> HighestH = " + highestH + "\n>> PrintPlan >> temp = " + temp );
                                if( highestH < temp ) { highestH = temp;}
//System.out.println(">>PrintPlan >> HighestH = " + highestH + "\n>> PrintPlan >> temp = " + temp );                                
                                temp = countTotalRow(UnicodeHelper.getUnicodeStringAmp(sProcess),13);
                                
                                if( highestH < temp ) { highestH = temp;}
//System.out.println(">>PrintPlan >> HighestH = " + highestH );
                                // Changed by DeZ, 18.07.08, Readjust row height to accomodate longest Development Resources
                                writesheet.setRowView(iRow, highestH);
                                
				iRow++;
			}
			
			if(vKB.size() > vDevPlan.size()) {
				label= new Label(iColumn+1, iRow, "", bordersData2);
				writesheet.addCell(label);
				writesheet.mergeCells(iColumn+1, iRow, iColumn+5, iRow+(vKB.size()-vDevPlan.size()-1));
				
			}

			if(iKBRow < iResourceRow)
				iRow = iResourceRow+1;
			else
				iRow = iKBRow+1;
			
			label= new Label(iOriginalColumn, iOriginalRow, sCompetencyName, bordersData2);
			writesheet.addCell(label); 
			writesheet.mergeCells(iOriginalColumn, iOriginalRow, iOriginalColumn, iRow-1);
			
			label= new Label(iOriginalColumn+2, iOriginalRow, ((Double)CPMap.get(new Integer(iFKCompetency))).toString(), bordersData4);
			writesheet.addCell(label);
			writesheet.mergeCells(iOriginalColumn+2, iOriginalRow, iOriginalColumn+2, iRow-1);
			
			label= new Label(iOriginalColumn+3, iOriginalRow, ((Double)CPRMap.get(new Integer(iFKCompetency))).toString(), bordersData4);
			writesheet.addCell(label);
			writesheet.mergeCells(iOriginalColumn+3, iOriginalRow, iOriginalColumn+3, iRow-1);
			
		}
	}
	

	/**
	 * Method called from JSP.
	 */
	public void Report(int iSurveyID, int iTargetLoginID, String fileName) 
		throws IOException, WriteException, SQLException, Exception
	{
			
			this.fileName = fileName;
			write();			
			
			printPlan(iSurveyID, iTargetLoginID);	
			
			w.write();
			w.close(); 
			
			
	}
	
	public int countTotalRow(String input, double totalChar) {

		double merge = (double)input.length() / (double)totalChar;
		BigDecimal BD = new BigDecimal(merge);
		BD.setScale(0, BD.ROUND_UP);
		BigInteger BI = BD.toBigInteger();
		int totalMerge = BI.intValue() + 1;
		
                // Changed by DeZ, 18.07.08, Readjust row height to accomodate longest Development Resources
		//int rowView = 270;
                int rowView = 350;
		int mergeView = rowView * totalMerge;
		
		return mergeView;
	}
	
	
	public static void main (String[] args)throws SQLException, Exception
	{
		DevelopmentPlanReport Rpt = new DevelopmentPlanReport();
		
		Rpt.Report(470, 6639, "CandInput.xls"); // kb
		
		//Rpt.WriteToReport(115);

	}
	
}
