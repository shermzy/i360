<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
				 java.util.*,
*"%>  
                 
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

/*	choosing organization*/

function proceed(form,field)
{
	form.action="Survey_AddSurvey_Org.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

</script>
<body style="text-align: left" bgcolor="#E2E6F1">
<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 
  

if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
 	CE_Survey.set_survOrg(PKOrg);
 	%>
 	<script>
 	window.close();
 	opener.location.href = "SurveyDetail.jsp";
 	</script>
 <%
}

%>
<form name="survey" action="Survey_AddSurvey_Org.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<ul>
			<li><font size="2" face="Arial"><%=trans.tslt("Choose an Organisation for the survey")%>.</font></li>
		</ul>
		</td>
	</tr>
</table>

<table border="0" width="95%" cellspacing="0" cellpadding="0" height="19">
	<tr>
		<td width="15%"><b><font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></b></td>
		<td width="7%"> <font size="2">
   
    	<p align="left">
   
    	&nbsp;</td>
		<td width="59%"> <font size="2">
   
    	<select size="1" name="selOrg">
<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next())
	{
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
	if(logchk.getOrg() == PKOrg)
	{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%	}
	else	
	{%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%	}	
}%>
</select></td>
		<td width="19%"> <font size="2">
   
    	<input type="button" value="<%=trans.tslt("Proceed")%>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)" style="float: right"></td>
	</tr>
	</table>
</form>
<%	}	%>

</body>
</html>