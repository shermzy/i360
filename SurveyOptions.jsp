<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Survey Options</title>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body,td,th {
	color: #000099;
	font-weight: bold;
}
body {
	background-color: #FFFFCC;
}
-->
</style>

<meta http-equiv="Content-Type" content="text/html">
</head>

<body>

<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function redirect(form, type)
function redirect(form, type)
{
	var url;
	
	// 1=target results, 2=trimmedmean/avgmean, 3=targetg gap, 4=reliability index
	switch(type) {
		case 1 : url = "TargetResults.jsp";
				 break;
		case 2 : url = "TargetTrimmedMean.jsp";
				 break;
		case 3 : url = "TargetTrimmedMean.jsp";
				 break;
		case 4 : url = "TargetGap.jsp";
				 break;
		case 5 : url = "ReliabilityIndex.jsp";
				 break;	
		case 6 : url = "RatersResult.jsp";
				 break;					 
	}
	
	window.close();
	window.open(url);
	//opener.location.href = url;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function IncludeExclude(form, type)
function IncludeExclude(form, type)
{
	// 1=include, 2=exclude
	form.action = "SurveyOptions.jsp?include=" + type;
	form.submit();
}

</script>


<form action="SurveyResult.jsp" method="post" name="SurveyOptions">

<%
	int pkUser = logchk.getPKUser();
	
	int assignmentID = 0;
	int targetID = 0;
	String calculate = "";
	int surveyID = SurveyResult.getSurveyID();
	int reliability = Calculation.ReliabilityCheck(surveyID);		// get which reliability check used in the survey
	int gapType = Calculation.GapType(surveyID);
	
	if(reliability == 0)
		calculate = " Trimmed Mean";
	else
		calculate = " Average Mean";
	
	if(request.getParameter("include") != null) {
		
		assignmentID = SurveyResult.getAssignmentID();
		//Changed by Ha 16/06/08 to change status of rater with specified id only
		int raterID = SurveyResult.getRaterID(assignmentID);
		
		if(Integer.parseInt(request.getParameter("include")) == 1)
		{
		
			SurveyResult.IncludeExcludeRater(assignmentID, Integer.parseInt(request.getParameter("include")), pkUser, raterID);
		}
		else if(Integer.parseInt(request.getParameter("include")) == 2) //2=exclude
		{
			
			SurveyResult.IncludeExcludeRater(assignmentID, Integer.parseInt(request.getParameter("include")), pkUser, raterID);
		}
%>
<script>
	alert("<%=trans.tslt("Rater Status has been updated")%> !");
	window.close();
	//opener.location.href("SurveyResult.jsp");
	opener.location.href="SurveyResult.jsp";
</script>
<%					
	}

	// type = 2 --> target level; type = 3 --> rater level
	if(request.getParameter("type") != null) {
		if(Integer.parseInt(request.getParameter("type")) == 2) {
			targetID = Integer.parseInt(request.getParameter("clicked"));
			SurveyResult.setTargetID(targetID);
%>
					
<table width="502" border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="28"><input name="options" type="radio" onClick="redirect(this.form, 1)"></td>
    <td width="10">&nbsp;</td>
	<td width="450"><%=trans.tslt("View Raters' Input")%></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td width="10">&nbsp;</td>
  </tr>
  <tr>
<% if(reliability == 0) {
%>
    <td><input name="options" type="radio" onClick="redirect(this.form, 2)"></td>
<%	} else {
%>
	<td><input name="options" type="radio" onClick="redirect(this.form, 3)"></td>	
<% } %>
    <td>&nbsp;</td>
	<td><%=trans.tslt("View by Rater's Group")%></td>
  </tr>

<%
	if(gapType == 1 || gapType == 2) {
%>
  <tr>
    <td width="10">&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  <tr>
	<td><input name="options" type="radio" onClick="redirect(this.form, 4)"></td>
    <td>&nbsp;</td>
	<td><%=trans.tslt("View Target Gap")%></td>
  </tr>
<% } %>  
<%	
	if(reliability == 1) {
%>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  <tr>
	<td><input name="options" type="radio" onClick="redirect(this.form, 5)"></td>
    <td>&nbsp;</td>
	<td><%=trans.tslt("View Reliability Index")%></td>
  </tr>
<%	} %>	  
  
</table>				
<%					
	}
	else {
		assignmentID = Integer.parseInt(request.getParameter("clicked"));
		SurveyResult.setAssignmentID(assignmentID);
%>
<table width="502" border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="28"><input name="options" type="radio" onClick="redirect(this.form, 6)"></td>
    <td width="10">&nbsp;</td>
	<td width="450"><%=trans.tslt("View Rater's Input")%></td>
  </tr>
<%
	if(reliability == 1) {
%>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  
<!-----------------------------------------------need to check if rater is unreliable, then include these options----------------------->  
<%
	int status = SurveyResult.RatersStatus(assignmentID);
	int userType = logchk.getUserType();	// 1= super admin
	 //Changed by Ha 16/06/08 
	 
	//Option set rater back to included will appear only if the rater status is excluded and user role is admin
	if(status == 3&&(userType==1||userType==4||userType==5||userType==6)) {
%>

  <tr>
    <td><input name="options" type="radio" onClick="IncludeExclude(this.form, 1)"></td>
    <td>&nbsp;</td>
	<td><%=trans.tslt("Reinstate Rater Status to Included")%></td>
  </tr>
<%	}
	//Option set rater back to excluded will appear only if the rater status is unreliable and user role is admin
	else if((status == 2) && (userType == 1||userType==4||userType==5||userType==6)) {
%>
   <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  <tr>
    <td><input name="options" type="radio" onClick="IncludeExclude(this.form, 2)"></td>
    <td>&nbsp;</td>
	<td><%=trans.tslt("Set Rater Status to Excluded")%></td>
  </tr>
<% } %>
</table>
<%											
		}
	}
	}	
%>
</form>	
</body>
</html>
