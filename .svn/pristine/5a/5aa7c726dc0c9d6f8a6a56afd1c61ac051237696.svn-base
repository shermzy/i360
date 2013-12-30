<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="user_jenty" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="DG" class="CP_Classes.DevelopmentGuide" scope="session"/>
<jsp:useBean id="RR" class="CP_Classes.RaterRelation" scope="session"/>

<jsp:useBean id="C" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="DRA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="ev" class="CP_Classes.EventViewer" scope="session"/>
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="Q" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="RS" class="CP_Classes.RatingScale" scope="session"/>
<jsp:useBean id="SR" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="DB" class="CP_Classes.Database" scope="session"/>   

<html>
<HEAD>
<TITLE>3-Sixty Profiler</TITLE>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<base target="_parent">
</HEAD>

<body>

<html>
<body>
<%
/* THIS PAGE IS NOT BEING USED */

response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);
%>
<form action="Logoff.jsp" name="Logoff">
<% 
/*Removing the session from the page indicating that the member has log out */
String username=(String)session.getAttribute("username");
    
  if (!logchk.isUsable(username)) 
  {%>
   
    	<jsp:forward page="Login.jsp"/>
<%  } 
  else 
  { 
session.removeAttribute("username");  
String value =" "; 

logchk.setPKUser(0);
logchk.setOrg(0);
logchk.setCompany(0);
logchk.setUserType(0);

user.set_selectedUser(0);
user.set_LoginName(value);

assignTR.setGroupID(0);
assignTR.setTargetID(0);
assignTR.set_selectedTargetID(0);
assignTR.set_selectedRaterID(0);
assignTR.set_NameSequence(0);
assignTR.set_selectedAssID(0);

CE_Survey.setJobPos_ID(0);
CE_Survey.setSurveyStatus(0);
CE_Survey.setPurpose(0);
CE_Survey.set_survOrg(0);
CE_Survey.setSurvey_ID(0);
CE_Survey.setCompetencyLevel(0);
CE_Survey.set_SurvRating(0);
CE_Survey.set_GroupID(0);
CE_Survey.set_CompLevel(0);

DG.setSurvey_ID(0);
DG.setType(0);

RR.setRelHigh(0);
RR.setRelSpec(0);


C.setSurveyID(0);
C.setGroupSection(0);
C.setTargetID(0);
C.setRaterID(0);

Comp.setComp("");
Comp.setOrgID(0);
Comp.setPKComp(0);

DRA.setFKCom(0);
DRARes.setFKComp(0);
DRARes.setResType(0);

ev.setSortType(0);
ev.setCompName("");
ev.setOrgName("");

KB.setFKComp(0);
KB.setIsComp(0);
KB.setAdded(0);
KB.setKBLevel(0);

Q.setJobPost("");
Q.setName("");
Q.setTotalComp(0);
Q.setTotalCurrComp(0);
Q.setAssignmentID(0);
Q.setFutureJob("");
Q.setTimeFrame("");
Q.setSurveyLevel(0);
Q.setChecked(0);
Q.setCurrID(0);

QR.setSurveyID(0);
QR.setJobPostID(0);
QR.setDivisionID(0);
QR.setDepartmentID(0);
QR.setGroupID(0);
QR.setTargetID(0);
QR.setRaterID(0);
QR.setPageLoad(0);

RDE.setSurveyID(0);
RDE.setGroupID(0);
RDE.setTargetID(0);
RDE.setJobPost("");
RDE.setRaterID(0);
RDE.setPageLoad(0);

RS.setRS(0);
RS.setScaleID(0);
RS.setRSType("");

SR.setSurveyID(0);
SR.setGroupID(0);
SR.setTargetID(0);
SR.setRaterID(0);
SR.setSurveyLevel(0);
SR.setAssignmentID(0);

%>
<script>
	parent.location.href="index.jsp?candidate="+'<%=logchk.getOrgCode()%>';
</script>

</script>
<table border="1" width="610" style="border-width: 0px" cellspacing="0" cellpadding="0">
	<tr>
		<td style="border-style: none; border-width: medium" height="21">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">
		<p align="center"><font face="Arial" size="2">
		<span style="mso-hansi-font-family:Arial;color:#000080"><b>
		<%=trans.tslt("You have logged off successfully")%>! 
		</b></span></font></td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">
		<p align="center">
		<span style="mso-hansi-font-family:Arial;color:#000080"><b>
		<font size="2" face="Arial"><%=trans.tslt("Thank you and good bye")%>.</font></b></span></td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
	</tr>
</table>
<%	}	%>

</form>
</body>
</html>