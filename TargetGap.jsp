<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 CP_Classes.vo.*,
                 java.lang.String"%>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Target Gap</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<body>

<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="Excel5" class="CP_Classes.ExcelTargetGap" scope="session"/>    
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<script language="javascript">
function printResult(form) {
	form.action = "TargetGap.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>


<%
	int surveyID = SurveyResult.getSurveyID();	
	int targetID = SurveyResult.getTargetID();
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	// by Hoa 03/12/2008 init survey bean to check for NA inclusion later
	votblSurvey vo = CE_Survey.getSurveyDetail(surveyID);
	int NA_Included = vo.getNA_Included();
	
/**************************************************** REPORT EXCEL *********************************************************************/
	if(request.getParameter("print") != null) {
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "TargetGap" + temp + ".xls";
		
		Excel5.WriteToReport(surveyID, targetID, pkUser, file_name);	
	
		
		String output = Setting.getReport_Path() + "//" + file_name;
		File f = new File (output);
	
		response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"TargetGap.xls\"");
			
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			
			
			int bit = 256;
			int i = 0;
	
	
				try {
						while ((bit) >= 0) {
							bit = in.read();
							outs.write(bit);
						}
						//System.out.println("" +bit);
	
						} catch (IOException ioe) {
							ioe.printStackTrace(System.out);
							outs.flush();
							outs.close();
							in.close();	
						}
				//		System.out.println( "\n" + i + " bytes sent.");
				//		System.out.println( "\n" + f.length() + " bytes sent.");
						outs.flush();
						outs.close();
						in.close();	
	}
	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/
	
	
	int gapType = Calculation.GapType(surveyID);
		
	String compName;
	String KBName;
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	double Result;
	
	int totalComp = SurveyResult.TotalCompetency(surveyID);
	int totalKB [] = new int[totalComp];
	int t=0;
	
	Vector kbTemp = null;
	
	if(surveyLevel == 1) {
		kbTemp = SurveyResult.TotalKB(surveyID);		// total KB group by Competenc
		
		for(int i=0; i<kbTemp.size(); i++) {
			int [] arr = (int[])kbTemp.elementAt(i); 
			totalKB[t++] = arr[1];
		}
	}
	
	int totalRT = SurveyResult.TotalRT(surveyID);
	//1=all
	
	Vector compOrKBList = SurveyResult.CompOrKBList(surveyID);	
	Vector targetResult = SurveyResult.TargetGapDisplayed(surveyID, targetID);
	
	Vector CompTrimmedMean = null;
	
	if(surveyLevel == 1) {
		CompTrimmedMean = SurveyResult.getAvgMeanForGap(surveyID, targetID);
	}
	
	Vector Gap = SurveyResult.getTargetGap(surveyID, targetID);	
%>
	
<form name="form1" method="post" action="TargetGap.jsp">

<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td colspan="3"><strong><%=trans.tslt("TARGET GAP")%></strong></td>
  </tr>
  <tr>
    <td width="193">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="573">&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Company Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.CompanyName(compID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Organisation Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.OrganizationName(OrgID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Survey Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.SurveyName(surveyID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Target Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.UserName(OrgID, targetID)%></strong></td>
  </tr>
  <tr>
    <td height="5">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="left"><strong><%=SurveyResult.SurveyLevel(surveyID)%></strong></td>
  </tr>
</table>

<p></p>
<table border="1" style='font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC" bordercolor="#3399FF">
<th width="200" align="left" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<% if(surveyLevel == 1) {
%>
<th width="400" align="left" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% } %>
<% 
	if(gapType == 1 || gapType == 2) {
		for(int a=0; a<2; a++) {
%>
<th width="100" align="center" bgcolor="navy"><b><font style='color:white'><%=RTCode[a]%></font></b></th>
<% } 
}%>
<th width="100" align="center" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Gap")%></font></b></th>
<%
	int j=0, comp=0;
	int temp = 0, temp1=0;
	int compCounter = 0;
	int targetCounter = 0;
	for(int k=0; k<compOrKBList.size(); k++)
	{
		String [] arr = (String[]) compOrKBList.elementAt(k);
		
		String [] arrTR = null;
		String [] arrGap = null;
		
		if(targetCounter<targetResult.size()) {
			
			arrTR = (String[])targetResult.elementAt(targetCounter); 
			
			if(k<Gap.size())
				arrGap = (String[])Gap.elementAt(k);
			
			temp1=1;
		}
		
		if(j == 0)
		{			
			compName = arr[1];
%>
			<tr>
				<td align="left">
					<%=compName%>
				</td>
<%		}
		
		double gap, rt1=0, rt2=0;
		
		// Survey is at KB level, show CP, CPR/FPR & Gap for each KB
		if(surveyLevel == 1)
		{
			if(j == 0)
			{
				temp1=1;
					
				String [] arrTrimmedMean = null;
				
		
				if(compCounter<CompTrimmedMean.size()) {
					arrTrimmedMean = (String [])CompTrimmedMean.elementAt(compCounter);
					rt1 = Double.parseDouble(arrTrimmedMean[2]);
					
					compCounter++;
				}
%>	
				<td>&nbsp;</td>
				<td align="center"><strong><%=rt1%></strong></td>
<% 				
				
				if(compCounter<CompTrimmedMean.size()) {
					arrTrimmedMean = (String [])CompTrimmedMean.elementAt(compCounter);
					rt2 = Double.parseDouble(arrTrimmedMean[2]);
					
					compCounter++;
				}
				
				gap = Math.round((rt1 - rt2) * 100.0)/100.0;				
%>
				<td align="center"><strong><%=rt2%></strong></td>
				<td align="center"><strong><%=gap%></strong></td>
				</tr>
<%			}
			
			KBName = arr[3];
%>
			<tr>
			<td>&nbsp;</td>
			<td align="left"><%=KBName%></td>			
<% 
			j++;
			
			if(j == totalKB[temp]) {
				j = 0;
				temp++;
			}
		}
		
	
		
		
		for(int i=0; i<2; i++)
		{
			if(temp1 == 1 && arrTR != null)
			{
				Result = Double.parseDouble(arrTR[2]);
				System.out.println("Result "+Result);
			}
			else
				Result = 0;
%>
			<td align="center">
			<%=Result%>
			</td>
<% 		
			//Comment off by Ha 18/06/08 to display the CP, CPR correcly in the Gap table
			//if(i < 1) {
				targetCounter++;
				if(targetCounter<targetResult.size()) 
					arrTR = (String[])targetResult.elementAt(targetCounter); 
				
			//}
		} // end if(surveyLevel == 1)
		/**
		 * Updated by Hoa 03/12/08
		 * Handle NullPointerException when Gap value is null.
		 * When Gap is null and 
		 *  - NA is included in calculation then display Gap = 0
		 *  - NA is NOT included in calculation then display Gap = NA
		 **/
		if(temp1 == 1 && arrGap != null) { 
			if(arrGap[1]==null && NA_Included==1){ %>
				<td align="center"><%=0%></td>
		  <%} else if(arrGap[1]==null && NA_Included==0) { %>
		  		<td align="center"><%="NA"%></td>
		  <%} else { %>
			<td align="center"><%=Double.parseDouble(arrGap[1])%></td>	
		 <% }
		} else { %>
<td align="center"><%=0%></td>	
<% } 
		comp++;
		if(comp == totalComp)
			comp = 0;
%>
</tr>	
<% } %>
</table>
<p></p>
<p>
<%
	if(compID != 2 || logchk.getUserType() == 1) {
%>	
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>">
<% } else { %>
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>" disabled>
<% } %>
</p>
</form>
</body>
</html>