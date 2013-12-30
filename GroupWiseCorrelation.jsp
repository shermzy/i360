<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Reliability Index</title>

<meta http-equiv="Content-Type" content="text/html">

</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="RatingTask" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<table width="800" border="1" style='font-size:11.0pt;font-family:Arial' font>
<%
	//int surveyID = SurveyResult.getSurveyID();
	//int surveyLevel = SurveyResult.getSurveyLevel();	
	int surveyID = 246;
	int assignmentID = 67;
	int surveyLevel = 0;
	int targetID = 3;
	int raterID = 5;
	int type = 1;		// 1=all,2= others
	
	String compName = "";
	String KBName;
	String name = SurveyResult.RaterName(raterID);
	
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	int Result;
	int totalKB = SurveyResult.TotalKB(surveyID);		// total KB group by Competency
	int totalComp = SurveyResult.TotalCompetency(surveyID);	
	int totalRT = SurveyResult.TotalRT(surveyID);
	
	ResultSet ratersID = SurveyResult.RatersID(surveyID, targetID, raterID);
	int totalRaters = Calculation.totalRaters(surveyID, targetID);
	String raterName [] = new String [totalRaters-1];
	int ID [] = new int [totalRaters-1];
	ResultSet IndividualResult [] = new ResultSet[totalRaters];
	
	double pearson [] = new double [totalRaters - 1];
	int reliable = SurveyResult.getReliability(assignmentID);

	int r = 0;
	while(ratersID.next()) {
		ID[r] = ratersID.getInt("PKUser");
		raterName[r] = ratersID.getString("GivenName") + " " + ratersID.getString("FamilyName");
		r++;
	}
%>
<th width="180" align="left" bgcolor="navy"><b>
<font size="2" style="color: white"><%= trans.tslt("Rating Task") %></font></b><font size="2"></th>
<th width="177" align="left" bgcolor="navy"></font><b>
<font size="2" style="color: white"><%= trans.tslt("Competency") %></font></b></th>
<% if(surveyLevel == 1) {
%>
<th width="306" align="left" bgcolor="navy"><b>
<font style='color:white' size="2"><%= trans.tslt("Key Behaviour") %></font></b></th>
<% } %>
<th width="50" align="left" bgcolor="navy"><b>
<font style='color:white' size="2"><%=name%></font></b></th>
<%
	for(int a=0; a<totalRaters-1; a++) {	
%>
<th width="50" align="left" bgcolor="navy"><b>
<font style='color:white' size="2"><%=raterName[a]%></font></b></th>
<%	}
%>

<%	

	IndividualResult[0] = SurveyResult.IndividualResult(assignmentID);
	IndividualResult[0].next();
	//Calculation.calculateReliability(surveyID, targetID, raterID); 
	
	for(int i=1; i<totalRaters; i++) {
		int asgntID = Calculation.AssignmentID(surveyID, targetID, ID[i-1]);
			
		pearson[i-1] = SurveyResult.getPearson(assignmentID, ID[i-1]);
		
		IndividualResult[i] = SurveyResult.IndividualResult(asgntID);		
		IndividualResult[i].next();					
	}

if(surveyLevel == 0) {			
	for(int rt=0; rt<totalRT; rt++) {
		String RTName = RatingTask.getRatingTask(RTCode[rt]);
		for(int comp=0; comp<totalComp; comp++) {
%>
<tr>
<%	
	if(comp == 0) {	
%>
<td align="left" width="180">
     <font size="2">
     <%=RTCode[rt] + "(" + RTName + ")"%>
	 </font>
	 </td>
<%	} else { %>
<td align="left"><font size="2">&nbsp;
     
</font>
     
</td>
<%	}	
	compName = IndividualResult[0].getString("CompetencyName");
%>
<td align="left">
     <font size="2">
     <%=compName%>
</td>
<td align="center">
<%=IndividualResult[0].getInt("Result")%>
</font>
</td>
<%	
	for(int s=1; s<totalRaters; s++) {		
%>
<td align="center">
<font size="2">
<%=IndividualResult[s].getInt("Result")%>
</font>
</td>
<%	
	IndividualResult[s].next();
	}
%>
</tr>
<%
	IndividualResult[0].next();
	}
	} // for RT
%>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<% 
	if(reliable == 1) {
%>
<td align="center"><font size="2"><%= trans.tslt("Reliable") %></font></td>
<% } else { %>
<td><font size="2"><%= trans.tslt("Unreliable") %></font></td>
<%
	}
		for(int a=0; a<totalRaters-1; a++) {
%>
<td align="center"><font size="2"><%=pearson[a]%></font></td>
<% } 
%>
</tr>
<% } %>



<%
/************************************************* Key Behaviour Level *************************************************/

if(surveyLevel == 1) {			
	for(int rt=0; rt<totalRT; rt++) {
		String RTName = RatingTask.getRatingTask(RTCode[rt]);
		for(int comp=0; comp<totalComp; comp++) {
			int j=0;
%>
<%	
	if(comp == 0) {	
%>
<tr>
<td align="left" width="100">
     <font size="2">
     <%=RTCode[rt] + "(" + RTName + ")"%>
	 </font>
	 </td>
<%	} else { %>
<td align="left"><font size="2">&nbsp; 
</font> 
</td>
<%	}	
	if(j == 0) {
		compName = IndividualResult[0].getString("CompetencyName");
%>
<td align="left" width="100">
     <font size="2">
     <%=compName%>
</font>
</td>
<%	} else { %>
<td align="left"><font size="2">&nbsp;

</font>

</td>
<% }
	for(int kb=0; kb<totalKB; kb++) {				
		KBName = IndividualResult[0].getString("KeyBehaviour");
		
	if(j == 0) {
%>
<td align="left" width="200">
     <font size="2">
     <%=KBName%>
</font>
</td>
<% } else {
%>
<tr>
<td align="left"><font size="2">&nbsp;     
</td>
<td align="left">&nbsp;     
</td>
<td align="left">
     <%=KBName%>
</font>
</td>
<% } %>
<td align="center">
<font size="2">
<%=IndividualResult[0].getInt("Result")%>
</font>
</td>
<%	
	IndividualResult[0].next();
	j++;
		if(j == totalKB)
			j = 0;
	for(int s=1; s<totalRaters; s++) {		
%>
<td align="center">
<font size="2">
<%=IndividualResult[s].getInt("Result")%>
</font>
</td>
<%	
	IndividualResult[s].next();
	}
%>
</tr>
<%
	}
	}	// for Competency
	} // for RT
%>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<% 
	if(reliable == 1) {
%>
<td align="center"><font size="2"><%= trans.tslt("Reliable") %></font></td>
<% } else { %>
<td><font size="2"><%= trans.tslt("Unreliable") %></font></td>
<%
	}
		for(int a=0; a<totalRaters-1; a++) {
%>
<td align="center"><font size="2"><%=pearson[a]%></font></td>
<% } 
%>
</tr>
<% } 
	
%>

</table>
</body>
</html>
