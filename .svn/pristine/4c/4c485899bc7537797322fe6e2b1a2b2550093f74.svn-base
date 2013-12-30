<html>

<head>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<title>3-Sixty Profiler Guides</title>
<base target="main">
</head>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<body bgcolor="#E2E6F1">

<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	window.close();
	</script>
<%  } 
  else 
  { 	%>


<table border="0" width="100%" height="402" style="border-left-width: 0px; border-right-width: 2px; border-top-width: 0px; border-bottom-width: 0px">

	<tr>
		<td style="border-left-style: none; border-left-width: medium" colspan="2"><b>
		<font face="Arial" color="#000080">&nbsp;3-Sixty Profiler<sup><font size="1"> 
		</font></sup></font></b>
		<sup> 
		<font face="Times New Roman" color="#000080">&trade;</font><font face="Arial" color="#000080"> <b>
		<font size="1">&nbsp;</font></b></font></sup><b><font face="Arial" color="#000080">-&nbsp; 
		<%=trans.tslt("Step by step Guide")%></font></b></td>
	</tr>
	<tr>
		<td width="96%" height="19" style="border-left-style: none; border-left-width: medium" colspan="2">
		<table border="0" width="96%" cellspacing="0" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
			</tr>

		</table>
		</td>
	</tr>

	<tr>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" bgcolor="#647BBB" height="20">
		
		<b><font face="Arial" size="2" color="#00FF00">&nbsp; <%=trans.tslt("Step 1: Set-up Demographics")%> &amp; &nbsp;</font></b><b>
		<font face="Arial" size="2" color="#00FF00">&nbsp;<br>&nbsp; <%=trans.tslt("Organisations")%></font></b></td>
		<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#647BBB" width="52%" height="20"><b>
		<font face="Arial" size="2" color="#00FF00">&nbsp; <%=trans.tslt("Step 2: Set-up Users")%> &amp; <%=trans.tslt("Jobs")%></font></b></td>
	</tr>
	<tr>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" height="130">
		<blockquote>
			<ol>
				<li><font face="Arial" size="2"><a href="OrganizationList.jsp"><%=trans.tslt("Organisations")%></a></font></li>
				<li><font face="Arial" size="2"><a href="Division.jsp"><%=trans.tslt("Division")%></a></font></li>
				<li><font face="Arial" size="2"><a href="Department.jsp"><%=trans.tslt("Department")%></a></font></li>
				<li><font face="Arial" size="2"><a href="Group.jsp"><%=trans.tslt("Group")%></a></font></li>
			</ol>
		</blockquote>
		</td>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" width="52%" height="146">
		<blockquote>
			<ol>
				<li><font face="Arial" size="2"><a href="User.jsp"><%=trans.tslt("Users")%></a></font></li>
				<li><font face="Arial" size="2"><a href="JobFunction.jsp"><%=trans.tslt("Job Function")%></a></font></li>
				<li><font face="Arial" size="2"><a href="JobLevel.jsp"><%=trans.tslt("Job Level")%></a></font></li>
				<li><font face="Arial" size="2"><a href="JobPosition.jsp"><%=trans.tslt("Job Position")%></a></font></li>
			</ol>
		</blockquote>
		</td>
	</tr>
	<tr>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" bgcolor="#647BBB" height="20"><b>
		<font face="Arial" size="2" color="#00FF00">&nbsp; <%=trans.tslt("Step 3: Set-up System Libraries")%></font></b></td>
		<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#647BBB" width="52%" height="22">&nbsp; <b>
		<font face="Arial" size="2" color="#00FF00"><%=trans.tslt("Step 4: Set-up Surveys")%> &amp; &nbsp;</font>
		<font face="Arial" size="2" color="#00FF00">&nbsp;<br>&nbsp; 
		<%=trans.tslt("Questionnaires")%></font></b></td>
	</tr>
	<tr>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" height="140">
		<blockquote>
			<ol>
				<li><font face="Arial" size="2"><a href="Competency.jsp"><%=trans.tslt("Competencies")%></a></font></li>
				<li><font face="Arial" size="2"><a href="KeyBehaviour.jsp"><%=trans.tslt("Key Behaviours")%></a></font></li>
				<li><font face="Arial" size="2"><a href="RatingTask.jsp"><%=trans.tslt("Rating Task")%></a></font></li>
				<li><font face="Arial" size="2"><a href="RatingScale.jsp"><%=trans.tslt("Rating Scale")%></a></font></li>
			</ol>
		</blockquote>
		</td>
		<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" width="52%">
		<blockquote>
			<ol>
				<li><font size="2" face="Arial"><a href="SurveyList_Create.jsp"><%=trans.tslt("Create Survey")%></a></font></li>
				<li><font size="2" face="Arial"><a href="SurveyList_AssignTR.jsp"><%=trans.tslt("Assign Target/Rater")%></a></font></li>
				<li><font size="2" face="Arial"><a href="RatersDataEntry.jsp"><%=trans.tslt("Rater's Data Entry")%></a></font></li>
				<li><font face="Arial" size="2"><a href="SurveyList_Process.jsp"><%=trans.tslt("Process Result")%></a></font></li>
			</ol>
		</blockquote>
		</td>
	</tr>
	</table>
<%}	%>
</body>

</html>