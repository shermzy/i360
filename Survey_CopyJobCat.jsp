<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
                 java.util.*"%>  
                 
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Copy From Job Category</title>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

/*	choosing organization*/

function proceed(form,field, field2)
{
	form.action="Survey_CopyJobCat.jsp?proceed="+field.value + "&jobCat="+field2.value;
	form.method="post";
	form.submit();
}

function changeOrg(form,field)
{
	form.action="Survey_CopyJobCat.jsp?Org="+field.value;
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
	int iFKJobCat = Integer.parseInt(request.getParameter("jobCat"));
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
 	CE_Survey.set_survOrg(PKOrg);
 	CE_Survey.setJobCat(iFKJobCat);
 	%>
 	<script>
 	window.close();
 	opener.location.href = "SurveyDetail.jsp";
 	</script>
 <%
}

if(request.getParameter("copyJobCat") != null)
{
	int PKOrg = new Integer(request.getParameter("copyJobCat")).intValue();
	CE_Survey.set_survOrg(PKOrg);
}

if(request.getParameter("Org") != null)
{
	int PKOrg = new Integer(request.getParameter("Org")).intValue();
	CE_Survey.set_survOrg(PKOrg);
}

%>
<form name="survey" action="Survey_CopyJobCat.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
	<td><font size="2" face="Arial"><b>Copy From Job Category</b></font></td>
	</tr>
	<tr>
		<td>
		<ul>
			<li><font size="2" face="Arial"><%=trans.tslt("Choose a Job Category for the survey")%>.</font></li>
		</ul>
		</td>
	</tr>
</table>

<table border="0" width="95%" cellspacing="0" cellpadding="0" height="19">
	<tr>
		<td width="15%"><font face="Arial" size="2"><%=trans.tslt("Organisation")%>:</font></td>
		<td width="7%"> <font size="2">
   
    	<p align="left">
   
    	</td>
		<td width="59%"> <font size="2">
   
    	<select size="1" name="selOrg" onchange = "changeOrg(this.form, this.form.selOrg)">
<%
	Vector vOrg = logchk.getOrgList(logchk.getCompany());
	
	for(int i=0; i<vOrg.size(); i++)
	{
		votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
		int PKOrg = vo.getPKOrganization();
		String OrgName = vo.getOrganizationName();
	
		if(logchk.getOrg() == PKOrg)
		{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%		}
		else	
		{
%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%		}	
	}
%>
</select></td>
	<td></td>
<tr>
	<tr>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>

<tr>
		<td width="15%"><font face="Arial" size="2"><%=trans.tslt("Job Category")%>:</font></td>
		<td width="7%"> <font size="2">
   
    	<p align="left">
   
    	</td>
		<td width="59%"> <font size="2">
   
    	<select size="1" name="selJobCat">
<%
	Vector vJobCat = JobCat.getJobCategory(CE_Survey.get_survOrg());	
	voJobCategory voJobCat = new voJobCategory();
	for(int i=0; i<vJobCat.size(); i++) {
    	int j = i+1;
		voJobCat = (voJobCategory)vJobCat.elementAt(i);

		int iFKJobCat = voJobCat.getPKJobCategory();
		String sJobCatName 	= voJobCat.getJobCategoryName();
	
	if(JobCat.getJobCatID() == iFKJobCat)
	{
%>
	<option value=<%=iFKJobCat%> selected><%=sJobCatName%></option>

<%	}
	else	
	{%>
	<option value=<%=iFKJobCat%>><%=sJobCatName%></option>
<%	}	
}%>
</select></td>
		<td width="19%"> <font size="2">
   
    	<input type="button" value="<%=trans.tslt("Proceed")%>" name="btnShow" onclick="proceed(this.form,this.form.selOrg, this.form.selJobCat)" style="float: right"></td>
	</tr>
	</table>
</form>
<%	}	%>

</body>
</html>