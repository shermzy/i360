<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Target Gap</title>
<meta http-equiv="Content-Type" content="text/html">
</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<%
	int surveyID = SurveyResult.getSurveyID();	
	int targetID = SurveyResult.getTargetID();
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);
		
	String compName;
	String KBName;
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	double Result;
	
	int totalComp = SurveyResult.TotalCompetency(surveyID);
	
	ResultSet kbTemp = SurveyResult.TotalKB(surveyID);		// total KB group by Competency
	int totalKB [] = new int[totalComp];
	int t=0;
	while(kbTemp.next())
		totalKB[t++] = kbTemp.getInt(2);
				
	int totalRT = SurveyResult.TotalRT(surveyID);
	//1=all
	
	ResultSet compOrKBList = SurveyResult.CompOrKBList(surveyID);	

	ResultSet targetResult = SurveyResult.TargetGapDisplayed(surveyID, targetID);
	ResultSet CompTrimmedMean = null;
	ResultSet AvgGap = null;
	
	if(surveyLevel == 1) {
		CompTrimmedMean = SurveyResult.getAvgMeanForGap(surveyID, targetID);
		AvgGap = SurveyResult.getAvgGap(surveyID, targetID);
	}

	ResultSet Gap = SurveyResult.getTargetGap(surveyID, targetID);

%>

<table border="1" font style='font-size:10.0pt;font-family:Arial' bordercolorlight = "#3399FF" bgcolor="#FFFFCC">
<th width="200" align="left" bgcolor="navy"><b><font style='color:white'>Competency</font></b></th>
<% if(surveyLevel == 1) {
%>
<th width="400" align="left" bgcolor="navy"><b><font style='color:white'>Key Behaviour</font></b></th>
<% } %>
<% for(int a=0; a<RTCode.length-1; a++) {
%>
<th width="100" align="center" bgcolor="navy"><b><font style='color:white'><%=RTCode[a]%></font></b></th>
<% } %>
<th width="100" align="center" bgcolor="navy"><b><font style='color:white'>Gap</font></b></th>

<%
	int j=0, comp=0;
	int temp = 0, temp1=0;
	while(compOrKBList.next()) {
		if(targetResult.next()) {
			Gap.next();
			temp1=1;
		}
		if(j == 0) {			
			compName = compOrKBList.getString("CompetencyName");
%>
<tr>
	<td align="left">
		<%=compName%>
	</td>
<%
		}
%>

<%
		double gap, rt1=0, rt2=0;
		if(surveyLevel == 1) {
			if(CompTrimmedMean.next() && j == 0) {
				temp1=1;
				AvgGap.next();
				rt1 = CompTrimmedMean.getDouble("Result");
%>	
				<td>&nbsp;</td>
				<td align="center"><strong><%=rt1%></strong></td>	
<% 				CompTrimmedMean.next();
				rt2 = CompTrimmedMean.getDouble("Result");
%>
				<td align="center"><strong><%=rt2%></strong></td>	
				<td align="center"><strong><%=AvgGap.getDouble(1)%></strong></td>
				</tr>
<%	
			}	
		}
%>


<%		
		if(surveyLevel == 1) {
			KBName = compOrKBList.getString("KeyBehaviour");
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
%>



<%			
		for(int i=0; i<totalRT-1; i++) {	
			if(temp1 == 1)
				Result = targetResult.getDouble("Result");
			else
				Result = 0;

%>
				<td align="center">
				<%=Result%>
				</td>
<% 		
			if(temp1 == 1) {
				if(i < totalRT - 1);
					targetResult.next();
			}
		}		 
%>	
<% if(temp1 == 1) { %>
<td align="center"><%=Gap.getDouble("Gap")%></td>	
<% } else { %>
<td align="center"><%=0%></td>	
<% } 
%>	



<%
		comp++;
		if(comp == totalComp)
			comp = 0;
%>


	

</tr>	
<% } %>
</table>

</body>
</html>
