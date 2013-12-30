<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Report TMT Overview</title>
<style type="text/css">
<!--
.style1 {
	color: #000066;
	font-weight: bold;
}
-->
</style>
</head>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="Overview" class="CP_Classes.ReportTMTOverview" scope="session"/>
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>

<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<script language="javascript">
function check(field)
{
	var clickedValue = 0;
	//check whether any checkbox selected
	
	if(field != null) {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked)	
				clickedValue = field[i].value;
		
		if(clickedValue == 0) 
			if(field.checked) 
				clickedValue = field.value;
	}
	
	return clickedValue;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function changeYear(form)
function changeYear(form)
{
	if(form.lstYear.value != "0") {
		form.action = "ReportTMTOverview.jsp?yearOfSurvey=" + form.lstYear.value;
		form.submit();
	}else
		alert("Please select a specific year.");
}

//void function generate(form)
function generate(form)
{
	if(check(form.chkSurvey) != 0) {
		form.action = "ReportTMTOverview.jsp?generate=1";
		form.submit();
	}else
		alert("Please select a specific survey.");
}

function proceed(form,field)
{
	form.action="ReportTMTOverview.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

</script>

<body class="fontstyle">
<%
String username=(String)session.getAttribute("username");

if (!logchk.isUsable(username)) {

%>
   
<script>
	parent.location.href = "index.jsp";
</script>
<%  
} else { 	

if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

	int compID = logchk.getCompany();
	int FKOrg = logchk.getOrg();
	int selectedYear = 0;
	
	if(request.getParameter("yearOfSurvey") != null)
		selectedYear = Integer.parseInt(request.getParameter("yearOfSurvey"));
	else if(request.getParameter("generate") != null) {
		 String [] strSurveyID = request.getParameterValues("chkSurvey");
		 
		 if(strSurveyID != null) {
		 	System.out.println("strSurveyID.length = " + strSurveyID.length);
		 	int surveyID [] = new int [strSurveyID.length];
			
			for(int i=0; i<strSurveyID.length; i++)
				surveyID[i] = Integer.parseInt(strSurveyID[i]);
			
			for(int i=0; i<surveyID.length; i++)
				System.out.println("SurveyID[" + i + "] = " + surveyID[i]);
			
			Date timeStamp = new java.util.Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			String temp  =  dFormat.format(timeStamp);
			
			String file_name = "TMT Overview Report " + temp + ".xls";
					
			Overview.Report(surveyID, logchk.getPKUser(), file_name);
			
			String output = Setting.getReport_Path() + "\\" + file_name;
			File f = new File (output);
			response.reset();
			response.setContentType ("application/xls");
			response.addHeader ("Content-Disposition", "attachment;filename=\"TMT Overview Report.xls\"");
			
			String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			
			int bit = 256;
			int i = 0;
	
	
			try {
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
			}catch (IOException ioe) {
				ioe.printStackTrace(System.out);
			}

			outs.flush();
			outs.close();
			in.close();	
		 }
	}
%>

<form name="OverviewReport" method="post">
<table class="tablesetting">
  <tr>
    <td colspan="3"><span class="style1">TMT Overview Report</span></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="105">Organisation</td>
    <td width="26">:</td>
    <td width="446"><select size="1" name="selOrg">
<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next()){
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
		if(logchk.getOrg() == PKOrg) {
%>
<option value=<%=PKOrg%> selected><%=OrgName%></option>
<%		
		}else{
%>
      <option value=<%=PKOrg%>><%=OrgName%></option>
<%		
		}	
	}
%>
    </select>
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Year of Survey</td>
    <td>:</td>
    <td><select name="lstYear" onChange="changeYear(this.form)">
<option	value="0"></option>	
<%
	//get the survey year from the the latest to earliest
	ResultSet rsYear = Overview.getSurveyYear(FKOrg);
	while(rsYear != null && rsYear.next()) {
		int year = rsYear.getInt("SurveyYear");
		if(selectedYear == year) {
%>	
<option value="<%=year%>" selected><%=year%></option>
<% 
		
		} else {
%>	
<option value="<%=year%>"><%=year%></option>
<% 
		}
	} 
%>
    </select></td>
  </tr>    
</table>
<p></p>
<div style='width:610px; height:200px; z-index:1; overflow:auto'>
<table class="tablesetting" border="1" bordercolor="#3399FF" >
  <tr class="header">
    <td width="27" align="center">&nbsp;</td>
    <td width="377">Survey Name</td>
	<td width="167">Job Level</td>
  </tr>
<%
	//get all the surveys within that specific year
	ResultSet rsSurvey = Overview.getSurveyByYear(selectedYear, FKOrg);
	while(rsSurvey != null && rsSurvey.next()) {
		int surveyID = rsSurvey.getInt("SurveyID");
		String surveyName = rsSurvey.getString("SurveyName");
		String jobLevel = rsSurvey.getString("JobLevelName");
%>	
 <tr bgcolor="FFFFCC" onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
    <td align="center"><input name="chkSurvey" type="checkbox" value="<%=surveyID%>"></td>
    <td><%=surveyName%></td>
	<td align="center"><%=jobLevel%></td>
  </tr>
<% 
	}
%>
</table>
</div>
<p></p>
<table class="tablesetting">
  <tr>
    <td align="right"><input name="btnGenerate" type="button" value="Generate Report" onClick="generate(this.form)"></td>
  </tr>
</table>
</form>
<% } %>
</body>
</html>
