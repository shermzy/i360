<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<title>Questionnaire Instructions</title>
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
<form>

<table width="610" border="1" font style='font-size:12.0pt;font-family:Arial'>
<th align="center" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Instructions")%><span class="style2"></span></font></b></th>
</table>
<p></p>


  <div style="width:600px; margin-bottom:10px">
  <font face="Verdana" style="font-size:13px">
<%=AddQController.getQuestionnaireHeader(surveyID)%>
</font>
</div>
</br>

<INPUT type="button" value="Continue" onclick="Continue()"/> 

</form>
</body>
</html>