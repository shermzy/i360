<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
	<link rel=apple-touch-icon href="icon.png" />
	<link rel="shortcut icon" href="icon.png" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Questionnaire Instructions</title>
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
</head>
<script language="javascript">
function Continue()
{
	window.location ="Questionnaires.jsp"
}
</script>
<body>
<%
int surveyID = RDE.getSurveyID();	
%>
	<div class="toolbar"> 
		<h1 id="pageTitle"></h1>
		<a class="backButton" href="#" onclick="window.location.href='about.jsp'">&nbsp;About</a>
		<a title="Log out" class="logoutButton" href="#" onclick="window.location.href='login.jsp?logout=1'">Exit</a>
	</div>
	
		<ul id="Instructions" title="Instructions" selected="true">
		
		<li><p><%=AddQController.getQuestionnaireHeader(surveyID)%>
</p>
	
</li>
<li style='background-color:#6495ED;border-bottom:1px solid #483D8B;'><a href="#" onclick="javascript:window.location.href='Questionnaires.jsp'">Continue</a></li>

	</ul>
</body>
</html>