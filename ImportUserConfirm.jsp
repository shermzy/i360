<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>

<html>
<head>
<title>Import User</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style></head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/> 
<jsp:useBean id="ExportExcel" class="CP_Classes.ExportQuestionnaire" scope="session"/>    
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>  
<jsp:useBean id="S" class="CP_Classes.SurveyResult" scope="session"/> 
<jsp:useBean id="Q" class="CP_Classes.Questionnaire" scope="session"/> 
<jsp:useBean id="ImportUser" class="CP_Classes.Import" scope="session"/>
<jsp:useBean id="org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;

function getID(form, ID, type)
{
	var typeSelected = "";
	
	if(ID != 0) {
		switch(type) {
			case 1: typeSelected = "surveyID";
					  break;
			case 2: typeSelected = "groupID";
					  break;
			case 3: typeSelected = "targetID";
					  break;
			case 4: typeSelected = "raterID";
					  break;
		}
		var query = "RatersDataEntry.jsp?" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
	} else {
		alert("<%=trans.tslt("Please select the options")%> !");
		return false;
	}
	return true;	
}

function confirmImport(form)
{
	var myWindow=window.open('ImportUser.jsp?selOrg=' + form.selOrg.value,'windowRef','scrollbars=no, width=400, height=200');
	myWindow.moveTo(x,y);
	myWindow.location.href = 'ImportUser.jsp';
}

/*------------------------------------------------------------start: Login modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="RatersDataEntry.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	
</script>

<p>
<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%>
    <script>
	parent.location.href = "index.jsp";
	</script>
<%}%>

<form name="RatersDataEntry" method="post" action="">
<table border="0" width="593" cellspacing="0" cellpadding="0" style='font-size:10.0pt;font-family:Arial'>
	<tr>
	  <td colspan="4" align="left" bordercolor="#FFFFFF"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Import User")%> </font></b></td>
    </tr>
 	
    <tr>
    <td>
    <br>
	<p>
    <font size="2"><%=trans.tslt("Organisation")%><b> 
:</b> <select size="1" name="selOrg">
<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next())
	{
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
		if(logchk.getOrg() == PKOrg)
		{%>
			<option value=<%=PKOrg%> selected><%=OrgName%></option>
	<%	}
		else	
		{%>
			<option value=<%=PKOrg%>><%=OrgName%></option>
	<%	}
	}
%>
</select></font>

    </td>
    </tr>
	<tr>
<%
	if(request.getParameter("path") != null) 
	{
		String reportPath = Setting.getUploadPath() + "\\" + request.getParameter("path");
		int overwrite = ExportExcel.getOverwrite();
		
		//out.println("ORG = " + logchk.getOrg());
		
		//int CompanyID = org.getCompanyID(logchk.getOrg());
		ImportUser.Import(1, reportPath, logchk.getCompany(), logchk.getOrg());
%>
	<td colspan="4" align="left" bordercolor="#FFFFFF">&nbsp;<p>
	<font size="2" face="Arial"><%=trans.tslt("Users Imported Successfully, Please see " + Setting.getReport_Path() + "\\ErrorLog.txt for the details of imported failed data")%> </font>
	<p>
	</td>
<%		
	}
%>
	</tr>
	<tr>
	
	</tr>
  </table>
<p><input type = "button" name="cmdImport" value="Import" onclick="confirmImport(this.form)"></p>
<p>&nbsp;</p>
</form>

<table border="0" width="610" height="26">
	<tr>
		<font size="2">
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="Login.jsp">Home</a>&nbsp;|
		<a color="navy" face="Arial">&nbsp;</a><a style="TEXT-DECORATION: none; color:navy;" href="mailto:3SixtyProfiler@pcc.com.sg?subject=Regarding:">Contact 
		Us</a><a color="navy" face="Arial" href="termofuse.htm"><span style="color: #000080; text-decoration: none"> 
		| Terms of Use </span></a>|
		<span style="color: #000080; text-decoration: none">
		<a style="TEXT-DECORATION: none; color:navy;" href="http://www.pcc.com.sg/" target="_blank">
		PCC Website</a></span></font></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright ? 2004 Pacific 
		Century Consulting Pte Ltd. All Rights Reserved.</font></td>
	</tr>
</table>
</font>
</body>
</html>