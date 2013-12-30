<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Target Result</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="RatingTask" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="Excel3" class="CP_Classes.ExcelTargetTrimmedMean" scope="session"/>   
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
function printResult(form) {
	form.action = "TargetAvgMean.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>


<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> 
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
	int surveyID = SurveyResult.getSurveyID();
	int targetID = SurveyResult.getTargetID();
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);

	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
	int nameSeq = User.NameSequence(OrgID);


/**************************************************** REPORT EXCEL *********************************************************************/
	if(request.getParameter("print") != null) {
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "RaterInputByGroup" + temp + ".xls";
		
		Excel3.WriteToReport(surveyID, targetID, pkUser, file_name);	
	
		String output = Setting.getReport_Path() + "//" + file_name;
		File f = new File (output);
	
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"RaterInputByGroup.xls\"");
			
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
						}
				//		System.out.println( "\n" + i + " bytes sent.");
				//		System.out.println( "\n" + f.length() + " bytes sent.");
						outs.flush();
						outs.close();
						in.close();	
	}
	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/



	String compName;
	String KBName;
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	int Result;

	
	int totalRT = SurveyResult.TotalRT(surveyID);
	int totalComp = SurveyResult.TotalCompetency(surveyID);
	ResultSet kbTemp = SurveyResult.TotalKB(surveyID);		// total KB group by Competency
	int totalKB [] = new int[totalComp];
	int t=0;
	while(kbTemp.next())
		totalKB[t++] = kbTemp.getInt(2);
	
	ResultSet CompOrKBList = null;
	ResultSet targetResultSelf = null;
	ResultSet targetResultSup = null;
	ResultSet targetResultOther = null;
	ResultSet targetResultAll = null;
	
	// 1=all,2= others, 3=sup
	targetResultSelf = SurveyResult.TargetResult(surveyID, targetID, "SELF");
	targetResultAll = SurveyResult.getTargetAvgMean(surveyID, targetID, 1);
	targetResultSup = SurveyResult.getTargetAvgMean(surveyID, targetID, 2);
	targetResultOther = SurveyResult.getTargetAvgMean(surveyID, targetID, 3);
		
	String raterName [] = SurveyResult.RatersName(targetID);
	String name = "";
	
	if(nameSeq == 0)
		name = raterName[1] + " " + raterName[0];
	else
		name = raterName[0] + " " + raterName[1];
		
	ResultSet avgSup = null;
	ResultSet avgOth = null;
	ResultSet avgAll = null;
	ResultSet avgSelf = null;
	
	if(surveyLevel == 1) {
		avgAll = SurveyResult.getAvgMean(surveyID, targetID, 1);
		avgSup = SurveyResult.getAvgMean(surveyID, targetID, 2);
		avgOth = SurveyResult.getAvgMean(surveyID, targetID, 3);
		avgSelf = SurveyResult.getAvgMean(surveyID, targetID, 4);
	}
	
	int totalSup = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
	int totalOth = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");	
	int totalSelf = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SELF");
	
%>

<form name="form1" method="post" action="TargetAvgMean.jsp">

<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td colspan="3"><strong><%=trans.tslt("RATERS' INPUT BY GROUP - AVERAGE MEAN")%> </strong></td>
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

<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">

<% for(int a=0; a<RTCode.length; a++) {
	CompOrKBList = SurveyResult.CompOrKBList(surveyID);
	String RTName = RatingTask.getRatingTask(RTCode[a]);
	if(surveyLevel == 0) {
%>	
	<th width="300" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=RTCode[a] + "(" + RTName + ")"%></font></b></th>
<%		
	}else {
%>
	<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=RTCode[a] + "(" + RTName + ")"%></font></b></th>
	<th width="500" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% } %>
<% if(totalSelf > 0) { %>
	<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%="SELF(" + name + ")"%></font></b></th>
<% 
	} %>	
<% if(totalSup > 0) { %>

	<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("SUP")%></font></b></th>
<% 
	} %>
<% if(totalOth > 0) { %>	
	<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("OTHER")%></font></b></th>
<% 
} %>	
	<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("ALL")%></font></b></th>




<%
	int j=0;
	int total = 0;
	if(surveyLevel == 0)
		total = totalComp;
	else {
		for(int h=0; h<totalComp; h++)
			total += totalKB[h];
	}
	
	int x=0;	
		
	for(int c=0; c<total; c++) {
		CompOrKBList.next();
		compName = CompOrKBList.getString("CompetencyName");
		if(j == 0) {			
%>
<tr>
	<td align="left" bordercolor="#3399FF">
		<%=compName%>
	</td>
<%		
		if(surveyLevel == 1) {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<% if(totalSelf > 0) {
	if(avgSelf.next()) {
%>
<td align="center" bordercolor="#3399FF"><b><%=avgSelf.getDouble("Result")%></b></td>

<% } else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	}
	}
	if(totalSup > 0) {
		if(avgSup.next()) {

%>
<td align="center" bordercolor="#3399FF"><b><%=avgSup.getDouble("Result")%></b></td>

<% } else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	}}
	
	if(totalOth > 0) {
		if(avgOth.next()) {
%>
<td align="center" bordercolor="#3399FF"><b><%=avgOth.getDouble("Result")%></b></td>

<% } else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	} }
	if(avgAll.next()) {
%>
<td align="center" bordercolor="#3399FF"><b><%=avgAll.getDouble("Result")%></b></td>
<% } else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	} %>
</tr>
<td align="left" bordercolor="#3399FF">&nbsp;</td>
<%	}
		}else {
%>
<tr>
	<td align="left" bordercolor="#3399FF">&nbsp;</td>
<%	}			
		if(surveyLevel == 1) {
			j++;
			if(j == totalKB[x]) {
				j = 0;
				x++;
			}
			KBName = CompOrKBList.getString("KeyBehaviour");
%>
	<td align="left" bordercolor="#3399FF">
		<%=KBName%>
	</td>			
<%		}	
%>
<% if(totalSelf > 0) {
	if(targetResultSelf.next()) {
%>
	<td align="center" bordercolor="#3399FF">
		<%=targetResultSelf.getDouble("Result")%>
	</td>
<%	} else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	} }
	if(totalSup > 0) {
		if(targetResultSup.next()) {
%>
	<td align="center" bordercolor="#3399FF">
		<%=targetResultSup.getDouble("Result")%>
	</td>
<%	} else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	}}
	if(totalOth > 0) {
		if(targetResultOther.next()) {
%>	
	<td align="center" bordercolor="#3399FF">
		<%=targetResultOther.getDouble("Result")%>
	</td>
<%	} else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	}}
	if(targetResultAll.next()) {
%>		
	<td align="center" bordercolor="#3399FF">
		<%=targetResultAll.getDouble("Result")%>
	</td>
<%	} else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%
	}
		}
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
<% } %>
</body>
</html>
