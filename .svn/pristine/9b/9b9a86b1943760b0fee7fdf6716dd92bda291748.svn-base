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
<title>Individual Report</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="QE" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="USR" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="ExcelIndividual" class="CP_Classes.IndividualReport" scope="session"/> 
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SR" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="ORG" class="CP_Classes.Organization" scope="session"/>

<script language="javascript">

var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;
var myWindow;

	
function getID(form, ID, type)
{
	var typeSelected = "";
	
	//if(ID != 0) {
		switch(type) {
			case 1: typeSelected = "surveyID";
					  break;
			case 2: typeSelected = "groupID";
					  break;
			case 3: typeSelected = "targetID";
					  break;
		}
		var query = "IndividualReportToyota.jsp?" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
/*	} else {
		alert("<%=trans.tslt("Please select the options")%> !");
		return false;
	}
*/	return true;	
}

function confirmOpen(form, raterID, type)
{
	if(form.chkSendEmail.checked)
		type = 1
	
	if(raterID.value != 0) {
		myWindow=window.open('IndividualReportAll.jsp','IndividualReportPopUp','scrollbars=no, width=480, height=250');
		var query = "IndividualReportAll.jsp";
		myWindow.moveTo(x,y);
		myWindow.location.href = query;

		form.action = "IndividualReportToyota.jsp?open="+type;
		form.submit();
		return true;	
		
	}else {
		alert("<%=trans.tslt("Please select the survey name")%> !");
		return false;
	}	
}

/*------------------------------------------------------------start: Login modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="IndividualReportToyota.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	


function populateDept(form, field)
{
	form.action="IndividualReportToyota.jsp?div=" + field.value;
	form.method="post";
	form.submit();
}

function populateGrp(form, field1, field2)
{
	form.action="IndividualReportToyota.jsp?div="+ field1.value + "&dept=" + field2.value;
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
  {%> 
   
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
<form name="IndividualReport" method="post" action="IndividualReportToyota.jsp">
<table boQEr="0" width="439" cellspacing="0" cellpadding="0" font style='font-size:10.0pt;font-family:Arial'>
	<tr>
	  <td colspan="4" align="left"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Individual Report")%> </font></b></td>
    </tr>

</table>
&nbsp;
<table boQEr="0" width="439" cellspacing="0" cellpadding="0" style='font-size:10.0pt;font-family:Arial' border="2" bordercolor="#3399FF">
	<tr>
	  <td align="right" bordercolor="#FFFFFF">&nbsp;</td>
	  <td bordercolor="#FFFFFF">&nbsp;</td>
	  <td bordercolor="#FFFFFF">&nbsp;</td>
	  <td align="right" bordercolor="#FFFFFF">&nbsp;</td>
    </tr>
	<tr>
		<td width="100" align="right" bordercolor="#FFFFFF"><%=trans.tslt("Organisation")%> 
		:</td>
		<td width="19" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="207" bordercolor="#FFFFFF"><select size="1" name="selOrg">
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
		<td width="103" align="left" bordercolor="#FFFFFF"><input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)"></td>
	</tr>
	<tr>
		<td width="100" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="19" bordercolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF">&nbsp;</td>

	</tr>
</table>

<%	
	int OrgID = logchk.getOrg();	
	
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	int nameSeq = User.NameSequence(OrgID);

	ResultSet surveyList = QE.getSurvey(compID, OrgID);
	ResultSet divisionList = null;
	ResultSet departmentList = null;
	ResultSet groupList  = null;
	ResultSet targetList = null;
	ResultSet raterList  = null;
		
	int surveyID = QE.getSurveyID();
	int divisionID = QE.getDivisionID();
	int departmentID = QE.getDepartmentID();
	int groupID  = QE.getGroupID();	
	int target   = QE.getTargetID();	
	
	if(surveyID != 0)
		divisionList = QE.getDivision(surveyID);
		departmentList = QE.getDepartment(surveyID, divisionID);
		groupList = QE.getGroup(surveyID, QE.getDivisionID(), QE.getDepartmentID());
	
	if(groupID != 0)
		targetList = QE.getTarget(surveyID, divisionID, departmentID, groupID);
	
	if(request.getParameter("open") != null) 
	{
		int type = Integer.parseInt(request.getParameter("open"));
		System.out.println("[IndividualReportToyota] Type = " + type);
		
		surveyID = QE.getSurveyID();
		divisionID = QE.getDivisionID();
		departmentID = QE.getDepartmentID();
		groupID = QE.getGroupID();
		
		target = Integer.parseInt(request.getParameter("targetName"));
		QE.setTargetID(target);
		
		if(target != 0) 
		{
			String surveyName = "";
			ResultSet rsSurvey = SR.SurveyInfo(surveyID);
			if(rsSurvey.next())
				surveyName = rsSurvey.getString("SurveyName");
			
			int iNameSeq = ORG.getNameSeq(logchk.getOrg());
			
			String sUserEmail = User.getUserEmail(logchk.getPKUser());	// Email of the person who gen the report

			String [] userInfo = USR.getUserDetail(target, iNameSeq);
			String targetName = userInfo[0] + " " + userInfo[1];
			
			Date timeStamp = new java.util.Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			String temp =  dFormat.format(timeStamp);
			
			String file_name = "IndividualReport" + temp + ".xls";
			
			//System.out.println(surveyID + " -- " + target + " -- " + pkUser);
			//try {
				ExcelIndividual.ReportToyota(surveyID, target, pkUser, file_name);	
			
			if(type == 1)	// User check "Send via Email"
			{
				ExcelIndividual.sendIndividualReport(targetName, surveyName, sUserEmail, file_name);
			}

			String output = Setting.getReport_Path() + "\\" + file_name;
			File f = new File (output);
		
			//set the content type(can be excel/word/powerpoint etc..)
			response.reset();
			response.setContentType ("application/xls");
			//set the header and also the Name by which user will be prompted to save
			response.addHeader ("Content-Disposition", "attachment;filename=\"IndividualReport.xls\"");
		
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
			} catch (IOException ioe) {
				ioe.printStackTrace(System.out);
			}
			
			outs.flush();
			outs.close();
			in.close();	
	
		} else {	//generate multiple reports here
		
			target = Integer.parseInt(request.getParameter("targetName"));
			QE.setTargetID(target);
			
			divisionID = Integer.parseInt(request.getParameter("selDiv"));
			QE.setDivisionID(divisionID);
			
			departmentID = Integer.parseInt(request.getParameter("selDept"));
			QE.setDepartmentID(departmentID);
			
			groupID = Integer.parseInt(request.getParameter("groupName"));
			QE.setGroupID(groupID);
	
			// extract all targets under this group
			int total = 1; //total report generated
			Vector allTarget = ExcelIndividual.AllTargets(surveyID, divisionID, departmentID, groupID, OrgID);
			int totalReports = allTarget.size();
			
			String sUserEmail = User.getUserEmail(logchk.getPKUser());
			
			for(int i=0; i<totalReports; i++) 
			{
				// for delay purpose
				if(i != 0)
					for(int j=0;j<100000;j++);
					
				//generate individual report.
				String [] data = (String[])(allTarget.elementAt(i));
				String surveyName 	= data[0];
				int targetID 		= Integer.parseInt(data[1]);
				String targetName	= data[2];
				%>
				<script>
				window.status = "Generating Individual Report for " + "<%=targetName%> (<%=total%> of <%=totalReports%>.............)";
				</script>
				<%				
	
				Date timeStamp = new java.util.Date();
				SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
				String temp  =  dFormat.format(timeStamp);
				
				String file_name = "Individual Report of " +targetName+ " for " +surveyName+ " (" + temp + ").xls";
				
				request.getSession().setMaxInactiveInterval(1800);
				System.out.println("Generating Individual Report for " + targetName + " (" + total + " of " + totalReports + ").............");
				ExcelIndividual.ReportToyota(surveyID, targetID, pkUser, file_name);
				
				if(type == 1)	// User check "Send via Email"
				{
					ExcelIndividual.sendIndividualReport(targetName, surveyName, sUserEmail, file_name);
				}
					
				System.out.println("Completed Individual Report for " + targetName + " (" + total + " of " + totalReports + ").............");	
				
				%>
				<script>
					window.status = "Completed for "+ "<%=targetName%> (<%=total%> of <%=totalReports%>)";
				</script>
				<%				
					total++;
				}
				
				String path = "J:/tomcat/webapps/i360/Report/";
				
				if(type == 1)
				{
					%>
					<script>
						alert("<%=totalReports%> Report(s) had been Generated Successfully and sent to <%=sUserEmail%>");
					</script>
					<%
				}
				else
				{
					%>
					<script>
						alert("<%=totalReports%> Report(s) had been Generated Successfully.\nThe generated reports are stored in <%=path%>");
					</script>
					<%
				}
			}
		} // if(target != 0)
	
	if(request.getParameter("surveyID") != null) {
		int ID = Integer.parseInt(request.getParameter("surveyID"));
		QE.setSurveyID(ID);
		QE.setDivisionID(0);
		QE.setDepartmentID(0);
		QE.setGroupID(0);
		QE.setGroupID(0);
		QE.setRaterID(0);
		QE.setTargetID(0);	
		QE.setPageLoad(1);	
		
		divisionList = QE.getDivision(ID);
		departmentList = QE.getDepartment(ID);
		groupList = QE.getGroup(ID, QE.getDivisionID(), QE.getDepartmentID());
		targetList = QE.getTarget(ID,  QE.getDivisionID(),  QE.getDepartmentID(), QE.getGroupID());
	}
	else if(request.getParameter("groupID") != null) {
		int group = Integer.parseInt(request.getParameter("groupID"));
		QE.setGroupID(group);
		QE.setRaterID(0);
		QE.setTargetID(0);			
		targetList = QE.getTarget(QE.getSurveyID(),  QE.getDivisionID(),  QE.getDepartmentID(), QE.getGroupID());
	} 
	else if(request.getParameter("targetID") != null) {
		int ID = QE.getSurveyID();
		int targetID = Integer.parseInt(request.getParameter("targetID"));
		QE.setTargetID(targetID);
		QE.setRaterID(0);
		raterList = QE.getRater(surveyID, groupID, targetID);
	}
	
	if (request.getParameter("div") != null)
	{
		QE.setDepartmentID(0);
		QE.setGroupID(0);
		int div = Integer.parseInt(request.getParameter("selDiv"));
		QE.setDivisionID(div);
		int ID = QE.getSurveyID();
		departmentList = QE.getDepartment(ID, QE.getDivisionID());
		groupList = QE.getGroup(ID, QE.getDivisionID(), QE.getDepartmentID());
		targetList = QE.getTarget(ID,  QE.getDivisionID(),  QE.getDepartmentID(), QE.getGroupID());
	}
	if(request.getParameter("dept") != null)
	{
		QE.setGroupID(0);
		int dept = Integer.parseInt(request.getParameter("selDept"));
		QE.setDepartmentID(dept);
		int ID = QE.getSurveyID();
		groupList = QE.getGroup(ID, QE.getDivisionID(), QE.getDepartmentID());
		targetList = QE.getTarget(ID,  QE.getDivisionID(),  QE.getDepartmentID(), QE.getGroupID());
	}
%>

  <table width="438" boQEr="0" style='font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC" border="2" bordercolor="#3399FF">
    <tr>
      <td width="119" align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td width="1" bordercolor="#FFFFCC">&nbsp;</td>
	  <td width="110" bordercolor="#FFFFCC">&nbsp;
	  </td>
    </tr>
    <tr>
      <td width="119" align="right" bordercolor="#FFFFCC"><%=trans.tslt("Survey's Name")%> 
		:</td>
      <td width="1" bordercolor="#FFFFCC">&nbsp;</td>
      <% int t = 0; %>
	  <td width="110" bordercolor="#FFFFCC">
	  <select name="surveyName" onchange = "getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 1)">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <% while(surveyList.next()) {
	  		int ID = surveyList.getInt(1);
			String name = surveyList.getString(2);
			int selectedSurvey = QE.getSurveyID();
			
			if(selectedSurvey != 0 && ID == selectedSurvey) {
	  %>
	  	<option value = <%=selectedSurvey%> selected><%=name%>
	  <% } else {  %>
	  	<option value = <%=ID%>><%=name%>	  
	  <% }
		   } 
	  %>
      </select></td>
    </tr>
	  <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Division")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="selDiv" onchange="populateDept(this.form, this.form.selDiv)">
	  <option value=<%=t%>><%=trans.tslt("All Divisions")%>
	  <% if(divisionList != null) { 
		  	while(divisionList.next()) {
	  			int ID = divisionList.getInt("PKDivision");
				String name = divisionList.getString("DivisionName");
				int selectedDiv = QE.getDivisionID();
			
				if(selectedDiv != 0 && ID == selectedDiv) {
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
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Department")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="selDept" onchange="populateGrp(this.form, this.form.selDiv, this.form.selDept)">
	  <option value=<%=t%>><%=trans.tslt("All Departments")%>
	  <% if(departmentList != null) { 
		  	while(departmentList.next()) {
	  			int ID = departmentList.getInt("PKDepartment");
				String name = departmentList.getString("DepartmentName");
				int selectedDept = QE.getDepartmentID();
			
				if(selectedDept != 0 && ID == selectedDept) {
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
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Group Name")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="groupName" onchange="getID(this.form, this.form.groupName.options[groupName.selectedIndex].value, 2)">
	  <option value=<%=t%>><%=trans.tslt("All Groups")%>
	  <% if(groupList != null) { 
		  	while(groupList.next()) {
	  			int ID = groupList.getInt("PKGroup");
				String name = groupList.getString("GroupName");
				int selectedGroup = QE.getGroupID();
			
				if(selectedGroup != 0 && ID == selectedGroup) {
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
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Target's Name")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="targetName">
	  <option value=<%=t%>><%=trans.tslt("All Targets")%>
	  <% 	if(targetList != null) {
	  			while(targetList.next()) {
				int loginID = targetList.getInt(1);
				
				String name=targetList.getString("FullName");
	
				int selectedTarget = QE.getTargetID();
		
				if(loginID == selectedTarget) {
	  %>
	  <option value = <%=loginID%> selected><%=name%>	
	 <% } else { %>
	  	<option value = <%=loginID%>><%=name%>	  
		<% }
			} 
			}%>
      </select></td>
    </tr>
    <tr>
    	<td bordercolor="#FFFFCC">
		<p align="right">
		<input type="checkbox" name="chkSendEmail" value="ON"></td>
    	<td bordercolor="#FFFFCC">&nbsp;</td>
    	<td bordercolor="#FFFFCC">Send via Email</td>
    </tr>
	<tr>
		<td bordercolor="#FFFFCC" colspan="3">
		</td>
		<td width="238" bordercolor="#FFFFCC"> 
<% if(compID != 2 || userType == 1) {
%>   
    	<input type="button" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form, this.form.surveyName, 2)">
	<% } else { 
%>   
    	<input type="button" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form, this.form.surveyName, 2)" disabled>
	<%	
	}%>
    	</td>   
    	
    </tr>
    
  </table>
  
<% } %>
</form>

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