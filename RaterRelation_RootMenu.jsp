<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
                 
<jsp:useBean id="RaterRelation" class="CP_Classes.RaterRelation" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SurveyRelationSpecific" class ="CP_Classes.SurveyRelationSpecific" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Superior - Root Menu</title>
</head>
<script language="JavaScript">
function closeME(form)
{ 
	form.action = "RaterRelation_RootMenu.jsp?close=1";
	form.method="post";
	form.submit();
}

function add(form, field1)
{
	if(field1.value != "")
	{
		if (confirm("Add Specific Relation?")) {
			form.action = "RaterRelation_RootMenu.jsp?add=1";
			form.method="post";
			form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Specific Relation Name")%>");
	}
}
</script>
<body bgcolor="#FFFFCC">
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

if(request.getParameter("add") != null)
{	
	try
	{
		
		/*
		*Change(s): Use class SurveyRelationSpecific to manage relation specifics
		*Reason(s): To associate relation specific to survey instead of organization
		*Updated by: Liu Taichen
		*Updated on: 5 June 2012
		*/
		String branch_Name = request.getParameter("txtParent");
		boolean bRecordAdded = SurveyRelationSpecific.insert(QR.getSurveyID(),RaterRelation.getRelHigh(),branch_Name, logchk.getPKUser());
		
		/***************************
		** Edited Eric Lu 14/5/08 **
		***************************/
		if (bRecordAdded) {
			RaterRelation.setRelHigh(0);
			RaterRelation.setRelSpec(0);
%>		
	
	<script>
		alert("Added Rater Relation successfully");
		window.close();
		opener.location.href ='RaterRelation.jsp';
	</script>
<%
		} else {
//by Hemilda date 06/06/2008 change prompt message
%>

	<script>
		alert("Rater Relation Exist");
	</script>
<%
		}
	}
	catch (SQLException sqle) 
    {

		%>
			<script>
				alert("<%=trans.tslt("Record exists")%>");
			</script>
<%	}
}

if(request.getParameter("close") != null)
{	
	RaterRelation.setRelHigh(0);
	RaterRelation.setRelSpec(0);
	%>
	<script>
		window.close();
		opener.location.href ='RaterRelation.jsp';
	</script>
<%
}

%>
<form name="RaterRelation_RootMenu" action="RaterRelation_RootMenu.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-width: 0px">
	<tr>
		<td width="981" style="border-style:none; border-width:medium; " colspan="2">
		<b><font face="Arial" size="2" color="#000080"><%=trans.tslt("Root Menu")%></font></b></td>
	</tr>
	<tr>
		<td width="981" style="border-style:none; border-width:medium; " colspan="2">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="474" style="border-style:none; border-width:medium; ">
		<p align="left"><b><font face="Arial" size="2"><%=trans.tslt("Specific Relation Name")%>:</font></b></td>
		<td width="511" style="border-style:none; border-width:medium; ">
		<p align="center">
		<font face="Arial"><span style="font-size: 11pt">
		<input name="txtParent" size="30" style="float: left"></span></font></td>
	</tr>
	<tr>
		<td width="981" style="border-style:none; border-width:medium; " colspan="2">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="491" style="border-style:none; border-width:medium; ">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Cancel")%>" name="btnCancel" onclick="closeME(this.form)"></td>
		<td width="490" style="border-style:none; border-width:medium; ">
		<font size="2">
   
		<input type ="button" value="<%=trans.tslt("Add a specific Relation")%>" name="btnAdd" onclick="add(this.form, this.form.txtParent)"></td>
	</tr>
	</table>
</form>
<%	}	%>
</body>
</html>	