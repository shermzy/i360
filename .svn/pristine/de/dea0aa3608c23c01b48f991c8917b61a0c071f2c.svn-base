<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  
                 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                        
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="RptX" class="CP_Classes.Report_DeleteSurvey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
<!--
// -->

function check(field)
{
	var check= false;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
			check = true;
	}
	if(field != null)
	{
		if(field.checked)
		check = true;
	}

	return check;
		
}
function AssignTarget(form,field)
{
	if(check(field))
	{
		form.action="SurveyList.jsp?assign=1";
		form.method="post";
		form.submit();	 
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}
}

function Add(form,field) 
{ 
	form.action="SurveyList.jsp?add=1";
	form.method="post";
	form.submit();
}
//var flag = false;	
function survey(form,field)
{
	if(check(field))
	{
		form.action="SurveyList.jsp?survey=1";
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function process(form,field)
{
	if(check(field))
	{
		form.action="SurveyList.jsp?process=1";
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function job(form,field)
{
	form.action="SurveyList.jsp?job=1";
	form.method="post";
	form.submit();	
}

function copy(form,field)
{
	if(check(field))
	{
		form.action="SurveyList.jsp?copy=1";
		form.method="post";
		form.submit();	
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function DeleteAway(form,field)
{
	if(check(field))
	{
		if(confirm("Delete Survey?"))
		{
			form.action="SurveyList.jsp?del=1";
			form.method="post";
			form.submit();	
		}
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

</SCRIPT>


<body>
<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	<jsp:forward page="Login.jsp"/>
<%  } 
  else 
  { 

assignTR.setGroupID(0);

if(request.getParameter("job") != null)
{
	int var1 = new Integer(request.getParameter("selJob")).intValue();
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	CE_Survey.set_survOrg(var2);
	CE_Survey.setJobPos_ID(var1);
}

if(request.getParameter("survey") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>
	<jsp:forward page="SurveyDetail.jsp"/>	
	<%
}

if(request.getParameter("process") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>
	<jsp:forward page="SurveyResult.jsp"/>	
	<%
}

if(request.getParameter("add") != null)
{
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
%>
	<script>
	window.location = "SurveyDetail.jsp";
</script>
<%
}	

if(request.getParameter("assign") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1);
	assignTR.setGroupID(0);
	assignTR.setTargetID(0);
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
	assignTR.set_selectedAssID(0);
%>
	<jsp:forward page="AssignTarget_Rater.jsp"/>	
	<%
}

if(request.getParameter("copy") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>	
<script>	
	window.open('CopySurvey.jsp','windowRef','scrollbars=no, width=300, height=300');
</script>
<%    
}

if(request.getParameter("del") != null)
{
	int FKOrg = 0;
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	
	ResultSet rs_Survey = CE_Survey.getSurveyDetail(var1);
		if(rs_Survey.next())
			FKOrg = rs_Survey.getInt("FKOrganization");
	
		String filename = RptX.DeleteReport(var1);
		CE_Survey.addDeletedSurvey(var1, filename, FKOrg, logchk.getCompany(), logchk.getPKUser());

	
}
%>

<form name="SurveyList" action="SurveyList.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
				<td width="14%"><b><font face="Arial" size="2"><%=trans.tslt("Job Position")%>: </font></b></td>
				<td width="24%"><font face="Arial">
				<span style="font-size: 11pt">
				<select size="1" name="selJob" >

	<option value ="0" selected>List all survey</option>
<%
	ResultSet rs_JobPosition = db.getRecord("SELECT * FROM tblJobPosition");		
	while(rs_JobPosition.next())
	{
		int JobPosition_ID = rs_JobPosition.getInt("JobPositionID");
		String JobPosition_Desc = rs_JobPosition.getString("JobPosition");

		if(CE_Survey.getJobPos_ID() == JobPosition_ID)
		{
%>			
<option value=<%=JobPosition_ID%> selected><%=JobPosition_Desc%></option>
<%		}
		else
		{
%>			<option value=<%=JobPosition_ID%>><%=JobPosition_Desc%></option>
<%		}	
		
	}%>				

				</select></span></font></td>
				<td width="154">
				<b>
				<font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></b></td>
		<td width="169"><font face="Arial"><span style="font-size: 11pt">
		<select size="1" name="selOrg">
		<option value ="0"  selected>List all Organization</option>
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
</select></span></font></td>

		<td width="289">
<table border="0" width="96%" cellspacing="0" cellpadding="0">
	<tr>
		<font size="2">
   
		<td width="29%">&nbsp;</td>
		<td width="71%"> <font size="2">
   
    	<input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onclick="job(this.form)"></td>
	</tr>
</table>


				</td>

			</tr>
	<tr>
				<td width="14%">&nbsp;</td>
				<td width="24%">&nbsp;</td>
				<td width="154">&nbsp;</td>
		<td width="169">&nbsp;</td>

		<td width="289">&nbsp;</td>

			</tr>
	<tr>
				<td width="14%"> <font face="Arial" size="2">
   
    			<p align="left">
    			<a onclick="clickAll(this.form)">
				&nbsp;</a></td>
				<td width="24%">&nbsp;</td>
				<td width="154">&nbsp;</td>
		<td width="169">&nbsp;</td>

		<td width="289">&nbsp;</td>

			</tr>
	</table>
<table width="833" border="1" style='font-size:11.0pt;font-family:Arial'>
<th bgcolor="#000080" width="854"><b><font style='color:white' size="2"><%=trans.tslt("Survey")%></font></b></th>
</table>

  <div style='width:837px; height:301px; z-index:1; overflow:auto'>
<table width="833" border="1" style='font-size:11.0pt;font-family:Arial'>
<tr>
<th width="4%" bgcolor="#000080">&nbsp;</th>
<th width="40%" bgcolor="#000080"><b><font size="2" style="color: white"><%=trans.tslt("Name")%></font></b></th>
<th width="115" bgcolor="#000080"><b><font style='color:white'><%=trans.tslt("Opening Date")%></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><%=trans.tslt("Deadline")%></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><%=trans.tslt("Analysis Date")%></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><%=trans.tslt("Status")%></font></b></th>
<th width="82" bgcolor="#000080"></font><b><font size="2" style="color: white"><%=trans.tslt("Organization")%></font></b></th>

  <%	
	int Survey_ID=0;
	int JobPosID1=0;
	String Survey_Name=" ";
	String month = " ";
	String DateOpened = " ";
	String DeadlineDate = " ";
	int db_SurveyStatus=0;
	String SurveyStatus=" ";
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
	SimpleDateFormat month_view= new SimpleDateFormat ("MM/yyyy");
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
	
	
	if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
		
	if(CE_Survey.getJobPos_ID() != 0)
		query = query+" AND a.JobPositionID = "+CE_Survey.getJobPos_ID();

	query = query+" ORDER BY SurveyStatus, DateOpened ";
	
		ResultSet rs_SurveyDetail = db.getRecord(query);		
		while(rs_SurveyDetail.next())
		{
	
			int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
			String Surv_Name = rs_SurveyDetail.getString("SurveyName");
			DateOpened = day_view.format(rs_SurveyDetail.getDate("DateOpened"));
			DeadlineDate = day_view.format(rs_SurveyDetail.getDate("DeadlineSubmission"));
			db_SurveyStatus = rs_SurveyDetail.getInt("SurveyStatus");
			String anal_Date = rs_SurveyDetail.getString("AnalysisDate");
			String OrganizationName = rs_SurveyDetail.getString("OrganizationName");
				
		if(anal_Date == null)
			anal_Date = " ";
					
		if(db_SurveyStatus == 1)
			SurveyStatus ="Open";
		else if(db_SurveyStatus == 2)
			SurveyStatus ="Closed";										
		else if(db_SurveyStatus == 3)
			SurveyStatus ="Not Commissioned";
		else if(db_SurveyStatus == 0)
			SurveyStatus ="Not Available";
%>

<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFFF'">
				<td width="4%" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080">
				<p align="center">
				<font face="Arial"><span style="font-size: 10pt">
				<font color="#FFC36B" size="3">
				<input type="radio" value=<%=Surv_ID%> name="Survey"></font></span></font></td>
				<td width="40%" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=Surv_Name%></td>
				

    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=DateOpened%></td>
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=DeadlineDate%></td>
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=anal_Date%>&nbsp</td>
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=SurveyStatus%></td>
     <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px" bordercolor="#808080"><font size="2"><%=OrganizationName%></font></td>

  </tr>
  <%	}	%>
</table>
</div>


<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="15%">&nbsp;</td>
		<td width="18%">&nbsp;</td>
		<td width="19%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td align="right" colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="15%">
		<input type="button" value="<%=trans.tslt("Add New Survey")%>" name="btnAddSurvey" onclick="Add(this.form)"></td>
		<td width="18%">
		<p align="center">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Copy Survey")%>" name="btnCopy" onclick="copy(this.form,this.form.Survey)"></td>
		<td width="19%">
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Assign Target/Rater")%>" name="btnAssign" style="float: right" onclick="AssignTarget(this.form,this.form.Survey)"></td>
		<td align="right" width="20%">
		<p align="center">
		<input type="button" value="<%=trans.tslt("View Survey Detail")%>" name="btnSurvey" style="float: right" onclick="survey(this.form,this.form.Survey)"></td>
		<td align="right" width="16%">
		<p align="center"> 
		<input type="button" value="<%=trans.tslt("Process Result")%>" name="btnProcess" onclick ="process(this.form,this.form.Survey)"></td>
		<td align="right" width="9%">
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Delete")%>" name="btnDel" style="float: right" onclick="DeleteAway(this.form, this.form.Survey)"></td>
		<td align="right" width="4%">&nbsp;</td>
	</tr>
</table>
</form>
<%}	%>
</body>


</html>