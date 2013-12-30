<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
                 java.util.Vector"%>  

<html>
<head>
<title>Group Report</title>
<style type="text/css">
<!--
.style1 {font-size: 10pt}
-->
</style>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="QE" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="ExcelGroup" class="CP_Classes.GroupReport" scope="session"/>
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;

function getID(form, ID, type)
{
	var typeSelected = "";
	
	if(ID != 0) {
		switch(type) {
			case 1: typeSelected = "jobPost";
					  break;
			case 2: typeSelected = "surveyID";
					  break;
		}
		var query = "GroupReportToyota.jsp?" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
	} else {
		alert("<%=trans.tslt("Please select the options")%> !");
		return false;
	}
	return true;	
}

function confirmOpen(form)
{
	if(form.JobPost.value != 0 && form.surveyName.value != 0)  {
		myWindow=window.open('GroupReportAll.jsp','GroupReportPopUp','scrollbars=no, width=480, height=250');
		var query = "GroupReportAll.jsp";
		myWindow.moveTo(x,y);
		myWindow.location.href = query;

		form.action = "GroupReportToyota.jsp?open=1";
		form.submit();
		return true;	
	}else {
		alert("<%=trans.tslt("Please select the options")%> !");
		return false;
	}	
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="GroupReportToyota.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

function populateDept(form,field1, field2, field3)
{
	form.action="GroupReportToyota.jsp?div="+field1.value + "&surveyID=" + field2.value + "&jobPost=" + field3.value;
	form.method="post";
	form.submit();
}	

function populateGrp(form,field1, field2, field3, field4)
{
	form.action="GroupReportToyota.jsp?div="+field1.value + "&dept=" + field2.value + "&surveyID=" + field3.value + "&jobPost=" + field4.value;
	form.method="post";
	form.submit();
}	
</script>

<p>
  <%
//response.setHeader("Pragma", "no-cache");
//response.setHeader("Cache-Control", "no-cache");
//response.setDateHeader("expires", 0);

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
 	logchk.setOrg(PKOrg);
}

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

	int compID = logchk.getCompany();
%>

<form name="GroupReport" method="post" action="GroupReportToyota.jsp">
<table boQEr="0" width="460" cellspacing="0" cellpadding="0">
	<tr>
	  <td colspan="4" align="left"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Group Report")%> </font></b></td>
    </tr>
</table>
&nbsp;
<table boQEr="0" width="460" cellspacing="0" cellpadding="0" border="2" bordercolor="#3399FF">
	<tr>
	  <td align="right" bordercolor="#FFFFFF">&nbsp;</td>
	  <td bordercolor="#FFFFFF">&nbsp;</td>
	  <td bordercolor="#FFFFFF">&nbsp;</td>
	  <td align="left" bordercolor="#FFFFFF">&nbsp;</td>
    </tr>
	<tr>
		<td width="100" align="right" bordercolor="#FFFFFF"><font face="Arial" style="font-size: 11pt"><span class="style1"><%=trans.tslt("Organisation")%></span> 
		:</font></td>
		<td width="19" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="238" bordercolor="#FFFFFF"><select size="1" name="selOrg">
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
		<td width="93" align="left" bordercolor="#FFFFFF"><input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)"></td>
	</tr>
	<tr>
		<td width="100" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="19" bordercolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF">&nbsp;</td>
	</tr>
</table>

<%	
	int OrgID = logchk.getOrg();	
	int pkUser = logchk.getPKUser();
	int nameSeq = User.NameSequence(OrgID);
	int userType = logchk.getUserType();	// 1= super admin
	
	ResultSet jobPostList = QE.getJobPost(compID, OrgID);
	ResultSet surveyList = null;
	ResultSet groupList  = null;
	ResultSet DivisionList = null;
	ResultSet DepartmentList = null;

		
	int jobPost = QE.getJobPostID();	
	int surveyID = QE.getSurveyID();
	int divID = QE.getDivisionID();
	int deptID = QE.getDepartmentID();
	int groupID  = QE.getGroupID();	
	
	if(jobPost != 0) 
		surveyList = QE.getSurvey(jobPost);
		
	if(surveyID != 0) {		
		//DivisionList = QE.getDivision(surveyID);
		DivisionList = QE.getDivision(surveyID, OrgID);
		//DepartmentList = QE.getDepartment(surveyID);
		DepartmentList = QE.getDepartment(surveyID, divID);
		//groupList = QE.getGroup(surveyID);
		groupList = QE.getGroup(surveyID, QE.getDivisionID(), QE.getDepartmentID());
	}

	if(request.getParameter("open") != null) {
		jobPost = QE.getJobPostID();		
		surveyID = QE.getSurveyID();
		
		if(request.getParameter("div") != "All Division")		
			divID = Integer.parseInt(request.getParameter("division"));//QE.getDivisionID();
			
		if(request.getParameter("dept") != "All Department")		
			deptID = Integer.parseInt(request.getParameter("department"));//QE.getDivisionID();
			
		if(request.getParameter("groupName") != "All Group")		
			groupID = Integer.parseInt(request.getParameter("groupName"));//QE.getDivisionID();	
		
		
/*		QE.setSurveyID(0);
		QE.setJobPostID(0);	
		QE.setDivisionID(0);	
		QE.setDepartmentID(0);	
		QE.setGroupID(0);	
		QE.setPageLoad(1);	
*/		
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		String file_name = "GroupReport" + temp + ".xls";
		String temp1 = "";
		System.out.println(surveyID + "--" +  groupID + "--" +  deptID + "--" + divID);
		
		ExcelGroup.ReportToyota(surveyID, groupID, deptID, divID, pkUser, file_name);	
		
/*		try {			
			ExcelGroup.ReportToyota(surveyID, groupID, deptID, divID, pkUser, file_name);	
		} catch (SQLException SE) {
			timeStamp = new java.util.Date();
			temp  =  dFormat.format(timeStamp);
			file_name = "GroupReport" + temp + ".xls";
			ExcelGroup.ReportToyota(surveyID, groupID, deptID, divID, pkUser, file_name);	
		} catch (Exception E) {
			timeStamp = new java.util.Date();
			temp  =  dFormat.format(timeStamp);
			file_name = "GroupReport" + temp + ".xls";
			ExcelGroup.ReportToyota(surveyID, groupID, deptID, divID, pkUser, file_name);	
		}
*/
	
		//String file_name = "GroupReport.xls";		
		//String output = "C:\\temp\\Report\\" + file_name;
		String output = Setting.getReport_Path() + "\\" + file_name;
		File f = new File (output);
	
		//set the content type(can be excel/word/powerpoint etc..)
		response.reset();
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"GroupReport.xls\"");
			
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			
			
			int bit = 256;
			int i = 0;
	
	
				try {
						while ((bit) >= 0) {
							bit = in.read();
							outs.write(bit);
						}
						//System.out.println("" +bit);
	
						} catch (IOException ioe) {
							ioe.printStackTrace(System.out);
						}
				//		System.out.println( "\n" + i + " bytes sent.");
				//		System.out.println( "\n" + f.length() + " bytes sent.");
						outs.flush();
						outs.close();
						in.close();	
				
		
	}	
	
	if(request.getParameter("jobPost") == null) {
		if(request.getParameter("surveyID") == null) {
			QE.setJobPostID(0);
			QE.setSurveyID(0);
			QE.setGroupID(0);
			QE.setDepartmentID(0);
			QE.setDivisionID(0);
		}
	}
		
	if(request.getParameter("jobPost") != null) {
		int ID = Integer.parseInt(request.getParameter("jobPost"));
		QE.setJobPostID(ID);
		
		QE.setSurveyID(0);
		QE.setGroupID(0);
		QE.setDepartmentID(0);
		QE.setDivisionID(0);
		
		DivisionList = null;
		DepartmentList = null;
		groupList = null;	
		
		surveyList = QE.getSurvey(ID);
	}
	else if(request.getParameter("surveyID") != null) {
		surveyID = Integer.parseInt(request.getParameter("surveyID"));
		QE.setSurveyID(surveyID);
	
		QE.setGroupID(0);
		QE.setDivisionID(0);			
		QE.setDepartmentID(0);			
		
		//DivisionList = QE.getDivision(surveyID);
		//DepartmentList = QE.getDepartment(surveyID);
		//groupList = QE.getGroup(surveyID);
		DivisionList = QE.getDivision(surveyID, OrgID);
		DepartmentList = QE.getDepartment(surveyID, divID);
		groupList = QE.getGroup(surveyID, QE.getDivisionID(), QE.getDepartmentID());

	} 
	
	if(request.getParameter("surveyID") != null && request.getParameter("jobPost") != null)
	{
		int ID = Integer.parseInt(request.getParameter("jobPost"));
		QE.setJobPostID(ID);
		
		surveyID = Integer.parseInt(request.getParameter("surveyID"));
		QE.setSurveyID(surveyID);
		
		if(request.getParameter("div") != null)
		{
			divID = Integer.parseInt(request.getParameter("div"));
			QE.setDivisionID(divID);
		}
		if(request.getParameter("dept") != null)
		{
			deptID = Integer.parseInt(request.getParameter("dept"));
			QE.setDepartmentID(deptID);
		}
		
		DivisionList = QE.getDivision(surveyID, OrgID);
		DepartmentList = QE.getDepartment(surveyID, divID);
		groupList = QE.getGroup(surveyID, QE.getDivisionID(), QE.getDepartmentID());
	}
	
	
	
%>

  <table width="460" boQEr="0" style='font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC" border="2" bordercolor="#3399FF">
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;
      </td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Job Position")%>: </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <% int t = 0; %>
      <td bordercolor="#FFFFCC">
        <select name="JobPost" onchange = "getID(this.form, this.form.JobPost.options[JobPost.selectedIndex].value, 1)">
          <option value=<%=t%>><%=trans.tslt("Please select one")%>
          <% while(jobPostList.next()) {
	  		int ID = jobPostList.getInt("JobPositionID");
			String name = jobPostList.getString("JobPosition");
			
			jobPost = QE.getJobPostID();
						
			if(jobPost != 0 && ID == jobPost) {
	  %>
          <option value = <%=ID%> selected><%=name%>
          <% } else {  %>
          <option value = <%=ID%>><%=name%>
          <% }
		   } 
	  %>
        </select></td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td width="100" align="right" bordercolor="#FFFFCC"><%=trans.tslt("Survey's Name")%> 
		: </td>
      <td width="10" bordercolor="#FFFFCC">&nbsp;</td>
	  <td width="230" bordercolor="#FFFFCC">
	  <select name="surveyName" onchange = "getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 2)">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <% if(surveyList != null) { 
	  		while(surveyList.next()) {
	  			int ID = surveyList.getInt(1);
				String name = surveyList.getString(2);
		
			if(surveyID != 0 && ID == surveyID) {
	  %>
	  	<option value = <%=ID%> selected><%=name%>
	  <% } else {  %>
	  	<option value = <%=ID%>><%=name%>	  
	  <% }
		   } 
		  }
	  %>
      </select></td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Division")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC"><select name="division" onChange="populateDept(this.form, this.form.division, this.form.surveyName, this.form.JobPost)">
          <option value=<%=t%>><%=trans.tslt("All Division")%>
          <% 	if(DivisionList != null) {
	  			while(DivisionList.next()) {
					int ID = DivisionList.getInt(1);
					String name = DivisionList.getString(2);
					
				divID = QE.getDivisionID();
			if(divID != 0 && ID == divID) {
	  %>
          <option value = <%=ID%> selected><%=name%>
          <% } else { %>
          <option value = <%=ID%>><%=name%>
          <% }
			} 
			}%>
          </select></td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Department")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC"><select name="department" onChange="populateGrp(this.form, this.form.division, this.form.department, this.form.surveyName, this.form.JobPost)">
          <option value=<%=t%>><%=trans.tslt("All Department")%>
          <% 	if(DepartmentList != null) {
	  			while(DepartmentList.next()) {
	 				int ID = DepartmentList.getInt(1);
					String name = DepartmentList.getString(2);
						
				deptID = QE.getDepartmentID();		
			if(deptID != 0 && ID == deptID) {
	  %>
          <option value = <%=ID%> selected><%=name%>
          <% } else { %>
          <option value = <%=ID%>><%=name%>
          <% }
			} 
			}%>
          </select></td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Group Name")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="groupName">
	  <option value=<%=t%>><%=trans.tslt("All Group")%>
	  <% if(groupList != null) { 
		  	while(groupList.next()) {
	  			int ID = groupList.getInt("PKGroup");
				String name = groupList.getString("GroupName");
			
				groupID = QE.getGroupID();
				if(groupID != 0 && ID == groupID) {
	  %>
	  	<option value = <%=ID%> selected><%=name%>
		<% } else { %>
		<option value = <%=ID%>><%=name%>	  
		<% }
			} }%>
      </select></td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
<td align="left" bordercolor="#FFFFCC">
<% if(compID != 2 || userType == 1) {
%>
<input type="button" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form)">
<%
} else {
%>   
<input type="button" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form)">  	
<%
}
%>
</td>
    </tr>
  </table>
  

<% } %>
</form>


<table border="0" width="610" height="26">
	<tr>
   
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
</body>
</html>