package CP_Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.io.File;
import java.io.IOException;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.table.XTableChart;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voUser;
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

public class Report_DistributionOfRatingByCompetency
{

	private Setting server;
	private User user;
	private OpenOffice OO;
	private Setting ST;
	private User_Jenty user_Jenty;
	private EventViewer ev;
	private Create_Edit_Survey CE_Survey;
	
    private XMultiComponentFactory xRemoteServiceManager = null;
	private XComponent xDoc = null;
    private XSpreadsheet xSpreadsheet = null;
    private String storeURL;
    int contentCurRow, contentCurCol;
	private final int ROWHEIGHT = 560;
	
	
	public Report_DistributionOfRatingByCompetency()
	{

		server = new Setting();
		user = new User();
		ev = new EventViewer();
		CE_Survey = new Create_Edit_Survey();
		user_Jenty = new User_Jenty();
		OO = new OpenOffice();
		ST = new Setting();
	}
	
	public void write(int surveyID, String fileName) throws IOException, WriteException, BiffException
	{
		try {
			InitializeExcel(fileName);
			Header(surveyID);
			FillInTableAndDrawGraph(surveyID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			try {			
				OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
				OO.closeDoc(xDoc);	
			}catch (SQLException SE) {
				System.out.println("a " + SE.getMessage());
			}catch (IOException IO) {
				System.err.println(IO);
			}catch (Exception E) {
				System.out.println("b " + E.getMessage());
			}
		}
	}
	

	/**
	 * 	Initialize all the processes dealing with Excel Application.
	 */
	/**
	 * @param savedFileName
	 * @throws IOException
	 * @throws Exception
	 */
	public void InitializeExcel(String savedFileName) throws IOException, Exception 
	{	
		System.out.println("2. Excel Initialisation Starts");
		storeURL 	= "file:///" + ST.getOOReportPath() + savedFileName;

		String templateURL 	= "";
		if (ST.LangVer == 1){
			templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "Distribution of Ratings by Competency_Template.xls";
		}else if (ST.LangVer == 2) {
			templateURL 	= "file:///" + ST.getOOReportTemplatePath() + "Distribution of Ratings by Competency_Template.xls";
		}
		xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager");
		xDoc = OO.openDoc(xRemoteServiceManager, templateURL);
		//save as the template into a new file first. This is to avoid the template being used.		
		OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);		
		OO.closeDoc(xDoc);
		
		//open up the saved file and modify from there
		xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
		xSpreadsheet = OO.getSheet(xDoc, "Sheet1");
	}
	
	
	public void Header(int SurveyID) 
		throws IOException, WriteException, SQLException, Exception
	{
		
		String CompName=" ";
		String OrgName =" ";
		String SurveyName = " ";
		
		Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
		
		votblSurvey vo = CE_Survey.getSurveyDetail(SurveyID);
			
		CompName = vo.getCompanyName();
		OrgName = vo.getOrganizationName();
		SurveyName = vo.getSurveyName();
		int height = OO.countTotalRow(SurveyName, 40);
		int [] titlePosition = OO.findString(xSpreadsheet, "<Survey Name>");
		OO.wrapText(xSpreadsheet, titlePosition[0], titlePosition[0], titlePosition[1], titlePosition[1]);
		OO.setRowHeight(xSpreadsheet, titlePosition[1], titlePosition[0], height*ROWHEIGHT*2);
		OO.setCellAllignment(xSpreadsheet, titlePosition[0],
				titlePosition[0], titlePosition[1], titlePosition[1], 1, 2);
		OO.findAndReplace(xSpreadsheet, "<Survey Name>", SurveyName);
	}
	
	public void FillInTableAndDrawGraph(int SurveyID)
		throws IOException, WriteException, SQLException, Exception{
		boolean isKB = isKBLevel(SurveyID);
		int[] position = OO.findString(xSpreadsheet, "<table>");
		int contentCurRow = position[1];
		int contentCurCol = position[0];
		int tableStartRow = position[1];
		int tableStartCol =  position[0];
		int tableEndRow = position[1];
		int tableEndCol = position[0];
		int [] ratingScale = getRatingScale(SurveyID);
		// table header
		OO.findAndReplace(xSpreadsheet, "<table>", "");
		OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, contentCurRow, contentCurRow);
		contentCurCol+=2;

		if(isKB){
			OO.insertString(xSpreadsheet, "Rating", contentCurRow, contentCurCol+2);
			OO.setCellAllignment(xSpreadsheet, contentCurCol+2,
					contentCurCol+2, contentCurRow, contentCurRow, 1, 2);
			OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, contentCurRow, contentCurRow);
			OO.mergeCells(xSpreadsheet, contentCurCol+2, contentCurCol+2+ratingScale[1]-ratingScale[0], contentCurRow, contentCurRow);
		}else{
			OO.insertString(xSpreadsheet, "Rating", contentCurRow, contentCurCol);
			OO.setCellAllignment(xSpreadsheet, contentCurCol,
					contentCurCol, contentCurRow, contentCurRow, 1, 2);
			OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+ratingScale[1]-ratingScale[0], contentCurRow, contentCurRow);
		}
		
		contentCurRow++;
		contentCurCol = 0;
		// table sub-header
		OO.insertString(xSpreadsheet, "Competency", contentCurRow, contentCurCol);
		OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, contentCurRow, contentCurRow);
		contentCurCol += 2;
		if(isKB){
			OO.insertString(xSpreadsheet, "KeyBehaviour", contentCurRow, contentCurCol);
			OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, contentCurRow, contentCurRow);
			contentCurCol += 2;
		}
		
		
		for(int i = ratingScale[0]; i <= ratingScale[1]; i++){
			OO.insertNumeric(xSpreadsheet, i, contentCurRow, contentCurCol);
			OO.setCellAllignment(xSpreadsheet, contentCurCol,
					contentCurCol, contentCurRow, contentCurRow, 1, 2);
			
			OO.setColumnWidth(xSpreadsheet, contentCurRow, contentCurCol, 700);
			contentCurCol++;
		}
		//reset column
		contentCurCol = 0;
		contentCurRow++;
		// rating calculation
		
		TreeMap<Integer, String> competencyDict = getCompetencyList(SurveyID);
		TreeMap<Integer,TreeMap<Integer,String>> KBDict = new TreeMap<Integer, TreeMap<Integer,String>>();
		TreeMap<Integer,String> KBDictAbbreviate = new TreeMap<Integer, String>();
		Vector<Integer> targetList = getTargetList(SurveyID);
		TreeMap<Integer, Vector<Integer>> finalResultDict = new TreeMap<Integer, Vector<Integer>>();
		TreeMap<Integer, TreeMap<Integer, Vector<Integer>>> KBFinalResultDict = new TreeMap<Integer, TreeMap<Integer, Vector<Integer>>>();
		TreeMap<Integer, int[]> graphDict = new TreeMap<Integer, int[]>(); // store comp
		TreeMap<Integer, int[]> kbgraphDict = new TreeMap<Integer, int[]>(); // store kb
		for(Map.Entry<Integer, String> entry : competencyDict.entrySet()){
			int compID = entry.getKey();
			// cal and store data for kb
			if(isKB){
				TreeMap<Integer, String> KBList = getKBList(compID);
				// create and store abbreviation of kb name
				int kbNum = 1;
				for(Map.Entry<Integer, String> kbInfo : KBList.entrySet()){
					KBDictAbbreviate.put(kbInfo.getKey(), "KB"+kbNum);
					kbNum++;
				}
				KBDict.put(compID,KBList);// store all KB info
				TreeMap<Integer, Vector<Integer>>KBResultsforComp = new TreeMap<Integer, Vector<Integer>>();
				for(Map.Entry<Integer, String> kbentry : KBList.entrySet()){
					Vector<Integer> KBIndividualResult = new Vector<Integer>();
					for(int i = 0; i <targetList.size(); i++){
						int targetID = targetList.get(i);
						KBIndividualResult.add(calculateAvgKBRating(SurveyID, compID, kbentry.getKey(), targetID));
					}
					KBResultsforComp.put(kbentry.getKey(), KBIndividualResult);
				}
				KBFinalResultDict.put(compID, KBResultsforComp);
			}
			// cal and store data for comp
			Vector<Integer>individualResult = new Vector<Integer>();
			//System.out.println("targetNum is "+targetList.size());
			for(int i = 0; i < targetList.size(); i++){
				int targetID = targetList.get(i);
				individualResult.add(calculateAvgRating(SurveyID, compID,targetID));
			}
			finalResultDict.put(compID, individualResult);
			
		}
		
		// print left header
		int rowValueCopy = contentCurRow;
		for(Map.Entry<Integer, String> entry : competencyDict.entrySet()){
			String compName = entry.getValue();
			OO.insertString(xSpreadsheet, compName, rowValueCopy, contentCurCol);
			OO.setCellAllignment(xSpreadsheet, contentCurCol,
					contentCurCol, rowValueCopy, rowValueCopy, 1, 2);
			int height = OO.countTotalRow(compName, 20);
			OO.setRowHeight(xSpreadsheet, rowValueCopy, contentCurCol, height*ROWHEIGHT);
			OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, rowValueCopy, rowValueCopy);
			rowValueCopy++;
			if(isKB){
				OO.mergeCells(xSpreadsheet, contentCurCol+2, contentCurCol+3, rowValueCopy, rowValueCopy);
				OO.mergeCells(xSpreadsheet, contentCurCol+2, contentCurCol+3, rowValueCopy-1, rowValueCopy-1);
				TreeMap<Integer, String> kbs = KBDict.get(entry.getKey());
				for(Map.Entry<Integer,String>kbentry:kbs.entrySet()){
					String kbName = kbentry.getValue();
					kbName += " ("+ KBDictAbbreviate.get(kbentry.getKey()) +")";
					OO.insertString(xSpreadsheet, kbName, rowValueCopy, contentCurCol+2);
					OO.setCellAllignment(xSpreadsheet, contentCurCol+2,
							contentCurCol+3, rowValueCopy, rowValueCopy, 1, 2);
					height = OO.countTotalRow(kbName, 20);
					OO.setRowHeight(xSpreadsheet, rowValueCopy, contentCurCol+2, height*ROWHEIGHT);
					OO.mergeCells(xSpreadsheet, contentCurCol, contentCurCol+1, rowValueCopy, rowValueCopy);
					OO.mergeCells(xSpreadsheet, contentCurCol+2, contentCurCol+3, rowValueCopy, rowValueCopy);
					rowValueCopy++;
				}
			}
			
		}
		
		// print rating in table
	//	if(isKB){
	//		for(Map.Entry<Integer,, V>)
	//		contentCurCol = 4;
	//	}else{
			for(Map.Entry<Integer, Vector<Integer>> entry : finalResultDict.entrySet()){
				int[] displayAns = new int[ratingScale[1]+1];
				Vector<Integer>result = entry.getValue();
				for(Integer value : result){
					displayAns[value] = displayAns[value] + 1;
				}
				contentCurCol = 2;
				if(isKB){
					contentCurCol = 4;
				}
				graphDict.put(entry.getKey(), displayAns);
				for(int i = ratingScale[0]; i < displayAns.length; i++){
					OO.insertNumeric(xSpreadsheet, displayAns[i], contentCurRow, contentCurCol);
					OO.setCellAllignment(xSpreadsheet, contentCurCol,
							contentCurCol, contentCurRow, contentCurRow, 1, 2);
					contentCurCol++;
				}
				contentCurRow++;
				if(isKB){
					TreeMap<Integer,Vector<Integer>> kbListAndAns = KBFinalResultDict.get(entry.getKey()); // all kb under this comp
					for(Map.Entry<Integer, Vector<Integer>> ansentry: kbListAndAns.entrySet()){ 
						Vector<Integer> kbresult = ansentry.getValue();
						int [] kbdisplayAns = new int[ratingScale[1]+1];
						for(Integer value: kbresult){
							kbdisplayAns[value] = kbdisplayAns[value]+1;
						}
						contentCurCol = 4;
						kbgraphDict.put(ansentry.getKey(),kbdisplayAns);
						for(int i = ratingScale[0]; i < kbdisplayAns.length;i++){
							OO.insertNumeric(xSpreadsheet, kbdisplayAns[i], contentCurRow, contentCurCol);
							OO.setCellAllignment(xSpreadsheet, contentCurCol,
									contentCurCol, contentCurRow, contentCurRow, 1, 2);
							contentCurCol++;
						}
						contentCurRow++;
					}
				}
				
			}
		//}
		
		tableEndCol = contentCurCol-1;
		tableEndRow = contentCurRow-1;
		OO.setTableBorder(xSpreadsheet, tableStartCol, tableEndCol,
				tableStartRow, tableEndRow, true, true, true, true, true,
				true);
		
		// draw graph
		contentCurRow+= 3;
		for(Map.Entry<Integer, int[]> entry : graphDict.entrySet()){
			String compName = competencyDict.get(entry.getKey());
			if(isKB){
				contentCurRow = drawGraph(contentCurRow, ratingScale[0], ratingScale[1], compName, entry.getKey(), entry.getValue(),KBDict, kbgraphDict,KBDictAbbreviate);
			}else{
				contentCurRow = drawGraph(contentCurRow, ratingScale[0], ratingScale[1], compName,entry.getKey(), entry.getValue(), null, null, null);
			}
		}
		
	}
	
	public int[] getRatingScale(int SurveyID){
		int [] result = new int [2];
		// get min
		String query = "Select * from tblSurvey ";
		query += "where SurveyID = " + SurveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next()){
	  			if (rs.getInt("HideNA") == 1)
	  				result[0] = 1;
	  			else
	  				result[0] = 0;
	  		}
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - getRatingScale - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
	  	
		// get max
		query = "SELECT MAX(tblScaleValue.HighValue) AS MAXIMUM FROM tblSurveyRating INNER JOIN ";
		query += "tblScaleValue ON tblSurveyRating.ScaleID = tblScaleValue.ScaleID WHERE ";
		query += "tblSurveyRating.SurveyID = " + SurveyID;
		con = null;
		st = null;
		rs = null;
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(query);
	  		
	  		if(rs.next())
				result[1] = rs.getInt(1);
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - getRatingScale - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        	
		return result;
	}
	
	public int calculateAvgKBRating(int SurveyID, int compID, int KBID, int targetID){
		Vector<Integer> ratings = new Vector<Integer>();
		String sql = "Select  AvgMean from tblAvgMean where SurveyID ="+SurveyID+" and CompetencyID ="
				+ compID+" and KeyBehaviourID = "+KBID+" and TargetLoginID = "+ targetID + " and Type = 1 and RatingTaskID = 1";
		
		//System.out.println(sql);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next())
	  			ratings.add(rs.getInt("AvgMean"));
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - calculateAvgRating - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		// calculate average
		int sum = 0;
		for(int i = 0; i < ratings.size(); i++){
			sum += ratings.get(i);
		}
		int avg = (int)(Math.round((double)(sum)/(double)(ratings.size()))); // need double check later
		return avg;
	}
	
	
	

	public int calculateAvgRating(int SurveyID, int compID, int targetID){
		Vector<Integer> ratings = new Vector<Integer>();
		String sql = "";
		// calculate for Competency level survey
		if(isKBLevel(SurveyID)){
			sql = "Select avg(AvgMean) as AvgMean from tblAvgMean where SurveyID ="+SurveyID+" and CompetencyID ="
					+ compID+" and TargetLoginID = "+ targetID + " and Type = 1";
		}else{
			/*sql = "Select * from tblResultCompetency a inner join tblAssignment b on " +
					"a.AssignmentID = b.AssignmentID where RatingTaskID = 1 and CompetencyID = "+compID+
					" and TargetLoginID != RaterLoginID and TargetLoginID = "+targetID+" and SurveyID = "+ SurveyID;*/
			
			sql = " Select AvgMean from tblAvgMean where SurveyID ="+SurveyID+" and CompetencyID ="
					+ compID+" and TargetLoginID = "+ targetID + " and Type = 1 and RatingTaskID = 1";
		}
		//System.out.println(sql);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next())
	  			ratings.add(rs.getInt("AvgMean"));
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - calculateAvgRating - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		// calculate average
		int sum = 0;
		for(int i = 0; i < ratings.size(); i++){
			sum += ratings.get(i);
		}
		int avg = (int)(Math.round((double)(sum)/(double)(ratings.size()))); // need double check later
		return avg;
	}
	
	public Vector<Integer> getTargetList(int SurveyID){
		Vector<Integer> result = new Vector<Integer>();
		String sql = "Select * from tblAssignment where RTRelation = 2 " +
				"and SurveyID = "+SurveyID;
		System.out.println(sql);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next())
	  			result.add(rs.getInt("TargetLoginID"));
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - getTargetList - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return result;
	}
	
	
	
	public TreeMap<Integer, String> getKBList(int compID){
		TreeMap<Integer, String> result = new TreeMap<Integer, String>();
		String sql = "Select * from KeyBehaviour where FKCompetency = "+compID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next()){
	  			result.put(rs.getInt("PKKeyBehaviour"), rs.getString("KeyBehaviour"));
	  		}
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - getCompetencyList - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return result;
	}
	
	public TreeMap<Integer, String> getCompetencyList (int SurveyID){
		TreeMap<Integer, String> result = new TreeMap<Integer, String>();
		String sql = "Select * from tblSurveyCompetency inner join " +
				"Competency on PKCompetency = CompetencyID where SurveyID = "+ SurveyID + 
				" order by CompetencyName";
		System.out.println(sql);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next()){
	  			result.put(rs.getInt("CompetencyID"), rs.getString("CompetencyName"));
	  		}
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - getCompetencyList - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return result;
	}
	
	public int drawGraph(int row, int minScale, int maxScale, String compName,int compID, int[] datas,TreeMap<Integer,TreeMap<Integer,String>> KBDict, TreeMap<Integer, int[]> kbdatas, TreeMap<Integer, String> kbAbbreviate) throws IOException, Exception 
	{	
		int TOPMARGIN = 3;
		int c = 2;
		int total = maxScale; // x axis max
        OO.insertString(xSpreadsheet, compName, row, c);
        row += TOPMARGIN;
        int r = row;
		XSpreadsheet xSpreadsheet1 = OO.getSheet(xDoc, "Sheet3");
		
		int maxTotal = 0;//this is to set the maximum height of Y Axis
		OO.insertString(xSpreadsheet1, compName, r-1, c+1);
		for(int i=minScale; i<=total; i++) 
		{
				OO.insertNumeric(xSpreadsheet1, i, r, c);
				OO.insertNumeric(xSpreadsheet1, datas[i], r, c+1);

				if(maxTotal < datas[i])
					maxTotal = datas[i];	
				r++;
		}
		
		// kb level
		if(kbdatas != null && KBDict != null){
			c= 4;
			TreeMap<Integer,String> kbUnderComp = KBDict.get(compID);
			for(Map.Entry<Integer, String>entry : kbUnderComp.entrySet()){
				r = row;
				int KBID = entry.getKey();
				int[] ans = kbdatas.get(KBID);
				OO.insertString(xSpreadsheet1, kbAbbreviate.get(KBID), r-1, c);
				for(int i=minScale; i<=total; i++) {
					OO.insertNumeric(xSpreadsheet1, ans[i], r++, c);
					if(maxTotal < ans[i])
						maxTotal = ans[i];	
				}
				c++;
			}
		}
		
		String sXAxis = "Ratings";
		String sYAxis = "No. of Candidates";

        if (ST.LangVer == 2){
        	sXAxis = "Nilai";
        	sYAxis = "No. of Candidates";
        }
        
        XTableChart xtablechart = null;
        
        // draw kb if kb involve
        if(kbdatas != null){
        	xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet1, 2, c-1, row-1, r-1, "Distribution"+(row-1), 15000, 17000, row-1, 0);
        }else{
        	xtablechart = OO.getChart(xSpreadsheet, xSpreadsheet1, c, c+1, row-1, r-1, "Distribution"+(row-1), 15000, 15000, row-1, 0);
        }
       
		OO.setFontSize(8);
		double step = 1;
		if(maxTotal > 10)
			step = Math.round(maxTotal / 10.0);
		xtablechart = OO.setAxes(xtablechart, sXAxis, sYAxis, maxTotal, step, 0, 0,0);
		OO.setChartProperties(xtablechart, false, true, true, true, true);
		OO.showLegend(xtablechart, true);
		OO.setLegendPosition(xtablechart, 4);
		OO.drawGridLines(xtablechart, 0);

		OO.changeChartType("com.sun.star.chart.LineDiagram", xtablechart);
		
		return r+(17000/ROWHEIGHT)-TOPMARGIN;
	}
	
	public boolean isKBLevel(int surveyID){
		String sql = "Select * from tblSurvey where SurveyID = " + surveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		System.out.println(sql);
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		while(rs.next()){
	  			if(rs.getInt("LevelOfSurvey") == 0)
	  				return false;
	  			else
	  				return true;
	  		}
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - isKBLevel - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return true;
	}
	
	public boolean checkPCAllGenerated(int SurveyID){
		String sql = "Select * from tblAvgMean where SurveyID = " + SurveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		System.out.println(sql);
	  	try 
        {          
	  		con=ConnectionBean.getConnection();
	  		st=con.createStatement();
	  		rs=st.executeQuery(sql);
	  		
	  		if(rs.next())
	  			return true;
	  		else
	  			return false;
		
        }
        catch(Exception E) 
        {
            
            System.err.println("Report_DistributionOfRatingByCompetency.java - checkPCAllGenerated - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		return false;
	}
}
