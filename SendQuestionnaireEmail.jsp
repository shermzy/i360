<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt7" class="CP_Classes.Report_ListOfTarget" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="email" class="CP_Classes.ReminderCheck" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
function proceed(form,field)
{
	form.action="SendPartEmail.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function updateCboSup(form)
{
	form.action="SendPartEmail.jsp";
	form.method="post";
	form.submit();
}
</script>



<body>
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
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	CE_Survey.set_survOrg(var2);
}

if(request.getParameter("btnPreview") != null)
{
	int SurveyID = new Integer(request.getParameter("selSurvey")).intValue();
	int iReminder = 0;
	
	if(request.getParameter("chkReminder") != null)
		iReminder = 1;
	else
		iReminder = 0;
		
	//System.out.println("chkReminder = " + iReminder);
	//System.out.println("optSend = " + request.getParameter("optSend"));
	
	if(request.getParameter("optSend") != null)
	{
		if(request.getParameter("optSend").equals("1"))
		{
			email.Sup_Nominate(SurveyID,iReminder);
		}
		else
		{
			System.out.println(request.getParameter("selSup"));
			if(request.getParameter("selSup") != null)
			{
				int SupID = new Integer(request.getParameter("selSup")).intValue();
				email.Sup_Nominate(SurveyID,SupID, iReminder);
				Email_Participant_Reminder(SurveyID, ''); // (int SurveyID, String date)
			}
			else
			{
			%>
			<script language = javascript>
			alert("Supervisor Combo box is empty");
			</script>
			<%
			}
		}
	}
				
	out.println(trans.tslt("Emails Sent") + "!");
}


%>
<form name="SendPartEmail" action="SendPartEmail.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td><b>
		<font face="Arial" size="2" color="#000080">Send Participants Emails</font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bgcolor="#FFFFCC" bordercolor="#3399FF">
		<tr>
		<td width="117" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
		<td width="228" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="154" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp; </td>
	</tr>
		<tr>
		<td width="117" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<b><font face="Arial" size="2">&nbsp;Organisations:</font></b></td>
		<td width="228" style="border-style: none; border-width: medium">
		<p align="left">		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">

<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next())
	{
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
	if(CE_Survey.get_survOrg() == PKOrg)
	{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%	}
	else	
	{%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%	}	
}%>
</select><font size="2"> </font>
</td>
		<td width="154" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> </td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><b><font face="Arial" size="2">&nbsp;Survey Name:</font></b></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onchange="updateCboSup(this.form)">
    	
<%
	int iSurveyID=0;
	if(request.getParameter("selSurvey") != null)
	{	
		//System.out.println("selSurvey = '" + request.getParameter("selSurvey") + "'");
		
		if (request.getParameter("selSurvey").equals(""))
			iSurveyID = 0;
		else
		{
			iSurveyID = Integer.parseInt(request.getParameter("selSurvey"));	
		}
	}
	
	boolean anyRecord=false;
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
	
	if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();

	ResultSet rs_SurveyDetail = db.getRecord(query);	
		
	while(rs_SurveyDetail.next())
	{
		anyRecord=true;
		int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		String Surv_Name = rs_SurveyDetail.getString("SurveyName");
		
			if(iSurveyID!= 0 && iSurveyID== Surv_ID)
			{%>
				<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>	
		<%	}else
			{%>
				<option value=<%=Surv_ID%>><%=Surv_Name%></option>
			<%	}
	}%>
</select></td>
	</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> &nbsp;</td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<b><font face="Arial" size="2">
		<% if(request.getParameter("chkReminder") != null) 
		{	System.out.println("Setting chkReminder to checked");
			
		%>
			<input type="checkbox" name="chkReminder" value="1" checked>
		<%
		} 
		else 
		{ 
			%>
			
			<input type="checkbox" name="chkReminder" value="0">
		<%}%>
		
		&nbsp;</font></b></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
   
    	<b>Is Reminder Emails</b></td>
			</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<input type="radio" value="1" name="optSend" checked></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<b>Send Nomination Emails to all Supervisors</b></td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font size="2">
   
		<input type="radio" value="2" name="optSend"></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<b>Send Nomination Email to Specific Supervisor</b></td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p>&nbsp;</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   <b><font face="Arial" size="2">&nbsp;<%=trans.tslt("Supervisor Name")%>:</font></b>
    	<select size="1" name="selSup">
<%
	
	if(request.getParameter("selSurvey") != null)
	{
	//System.out.println("selSurvey not null");
	query ="SELECT DISTINCT  [User].PKUser, [User].GivenName AS GivenName, [User].FamilyName AS FamilyName, [User].FKOrganization ";
	query = query + "FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID INNER JOIN ";
	query = query + "[User] INNER JOIN tblUserRelation ON [User].PKUser = tblUserRelation.User2 ON tblAssignment.TargetLoginID = tblUserRelation.User1 ";
	query = query + "WHERE ";

	if(CE_Survey.get_survOrg() != 0)
		query = query+"[User].FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"[User].FKCompanyID = "+logchk.getCompany();
	
	query = query + " AND (tblSurvey.SurveyID = " + request.getParameter("selSurvey") + ") ORDER BY [User].GivenName, [User].FamilyName";
	
	ResultSet rs_Sup = db.getRecord(query);		
	while(rs_Sup.next())
	{
		int iPKUser = rs_Sup.getInt("PKUser");
		String iGivenName = rs_Sup.getString("GivenName");
		String iFamilyName = rs_Sup.getString("FamilyName");
%>
	<option value=<%=iPKUser%> selected><%=iGivenName + ", " + iFamilyName%></option>
<%
	}
	} //end if(request.getParameter("selSurvey") != null)
	%>
</select></td>
		</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="116" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
		<p align="right">
		</td>
		<td width="228" align="center" style="border-style: none; border-width: medium"> <font size="2">
   
		<p align="left"> </td>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   <%if(anyRecord)
   	{%>
<% if(logchk.getCompany() != 2 || logchk.getUserType() == 1) {
%>	
		<input type="Submit" value="Send Emails" name="btnPreview" style="float: left">
<% } else {
%>	
		<input type="Submit" value="Send Emails" name="btnPreview" style="float: left" disabled>
<%
} %>		
<%	}%>		
		</td>
	</tr>
	<tr>
		<td width="116" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="228" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
	</tr>
</table>
</form>
<%	}	%>
<table border="0" width="610" height="26">
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
	</tr>
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
		<font size="2">
   
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright ? 2004 Pacific 
		Century Consulting Pte Ltd. All Rights Reserved.</font></td>
		</tr>
</table>
</body>
</html>