<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.Vector"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page import="CP_Classes.Calculation"%>
<%@ page import="CP_Classes.SurveyResult"%>  
<%//by  Yiting 19/09/2009 Fix jsp files to support Thai language"%>
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
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="ExcelGroup_NoCPR" class="CP_Classes.GroupReport_NoCPR" scope="session"/>
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
		//\\ changed by Ha 27/05/08 to refresh the group when changing survey name
		var query = "GroupReport_NoCPR.jsp?groupName=0&" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
	} else {
		alert("<%=trans.tslt("Please select the options")%>");
		return false;
	}
	return true;	
}

function confirmOpen(form)
{
	if(checkField(form)){
		//type 1=simplified;2=standard
		var type = 2; 
		if(form.chkReportType.checked)
			type = 1
	
		if(form.JobPost.value != 0 && form.surveyName.value != 0)  {
			myWindow=window.open('GroupReportAll_NoCPR.jsp','GroupReportPopUp','scrollbars=no, width=480, height=250');
			var query = "GroupReportAll_NoCPR.jsp";
			myWindow.moveTo(x,y);
			myWindow.location.href = query;
	
			form.action = "GroupReport_NoCPR.jsp?open="+type;
			form.submit();
			return true;	
		}else {
			alert("<%=trans.tslt("Please select the options")%>");
			return false;
		}
	}//End of checkField	
	
}
//This function is created by James. 16 Jun 2008 to make sure all field are selected.
function checkField(form){
	var msg = "Please select following information : \n";
	var valid = true;
	if(form.JobPost.selectedIndex == 0 ){
		msg += "  - Job Position \n";
		valid = false;
	}
	if(form.surveyName.selectedIndex == 0 ){
		msg += "  - Survey Name \n";
		valid = false;
	}
	
	if(valid){
		return valid;
	}else{
		alert(msg);
		return false;
	}
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="GroupReport_NoCPR.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	
//Changed by Ha 27/05/08 to set group to default when changing upper layer value
function populateDept(form,field1, field2, field3)
{
	form.action="GroupReport_NoCPR.jsp?groupName=0&div="+field1.value + "&surveyID=" + field2.value + "&jobPost=" + field3.value;
	form.method="post";
	form.submit();
}	

function populateGrp(form,field1, field2, field3, field4)
{
	form.action="GroupReport_NoCPR.jsp?groupName=0&div="+field1.value + "&dept=" + field2.value + "&surveyID=" + field3.value + "&jobPost=" + field4.value;
	form.method="post";
	form.submit();
}	
//Ended changes made by Ha


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
 	QR.setJobPostID(0);
 	QR.setSurveyID(0);
 	QR.setDivisionID(0);
 	QR.setDepartmentID(0);
 	QR.setGroupID(0);
 
}

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

	int compID = logchk.getCompany();
%>

<form name="GroupReport" method="post" action="GroupReport_NoCPR.jsp">
<table boQEr="0" width="460" cellspacing="0" cellpadding="0">
	<tr>
	  <td colspan="4" align="left"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Group Report NO CPR")%> </font></b></td>
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
		<td width="238" bordercolor="#FFFFFF"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)" >
			<option value="0" selected><%=trans.tslt("All")%>
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
		<td width="93" align="left" bordercolor="#FFFFFF">&nbsp;</td>
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

	Vector jobPostList = QR.getJobPost(compID, OrgID);
	Vector surveyList = null;
	Vector groupList  = null;
	Vector DivisionList = null;
	Vector DepartmentList = null;	
	
	
	//Changed made by Ha 27/05/08 to set respective value to default when changing upper layer
	if (request.getParameter("jobPost")!=null)
	{		
	    int id = Integer.parseInt(request.getParameter("jobPost"));
		
	    QR.setJobPostID(id);
		QR.setSurveyID(0);
		QR.setDivisionID(0);
		QR.setDepartmentID(0);
		QR.setGroupID(0);
		
		DivisionList = null;
		DepartmentList = null;
		groupList = null;	
		
		surveyList = QR.getSurvey(id);
	}
	if (request.getParameter("surveyID")!=null)
	{		
	    int id = Integer.parseInt(request.getParameter("surveyID"));
		QR.setSurveyID(id);
		QR.setDivisionID(0);
		QR.setDepartmentID(0);
		QR.setGroupID(0);
		QR.setGroupID(0);
		QR.setDivisionID(0);			
		QR.setDepartmentID(0);			
		
		DivisionList = QR.getDivision(id);
		DepartmentList = QR.getDepartment(id, QR.getDivisionID());
		groupList = QR.getGroup(id, QR.getDivisionID(), QR.getDepartmentID());
	}
	if (request.getParameter("div")!=null)
	{		
	    int id = Integer.parseInt(request.getParameter("div"));
		QR.setDivisionID(id);
		QR.setDepartmentID(0);
		QR.setGroupID(0);
		
		QR.setDivisionID(id);
		DivisionList = QR.getDivision(QR.getSurveyID());
		DepartmentList = QR.getDepartment(QR.getSurveyID(), QR.getDivisionID());
		groupList = QR.getGroup(QR.getSurveyID(), QR.getDivisionID(), QR.getDepartmentID());
	}
	
	if (request.getParameter("dept")!=null)
	{		
	    int id = Integer.parseInt(request.getParameter("dept"));
		QR.setDepartmentID(id);
		QR.setGroupID(0);
		DivisionList = QR.getDivision(QR.getSurveyID());
		DepartmentList = QR.getDepartment(QR.getSurveyID(),  QR.getDivisionID());
		groupList = QR.getGroup(QR.getSurveyID(), QR.getDivisionID(), QR.getDepartmentID());
	}
	
	if (request.getParameter("groupName")!=null)
	{		
		int id = Integer.parseInt(request.getParameter("groupName"));	
		QR.setGroupID(id);
		System.out.println("id: "+id);
		DivisionList = QR.getDivision(QR.getSurveyID());
		DepartmentList = QR.getDepartment(QR.getSurveyID(),  QR.getDivisionID());
		groupList = QR.getGroup(QR.getSurveyID(),  QR.getDivisionID(), QR.getDepartmentID());
	}
	//End of change made by Ha
	int jobPost = QR.getJobPostID();	
	int surveyID = QR.getSurveyID();	
	int divID = QR.getDivisionID();
	int deptID = QR.getDepartmentID();
	int groupID  = QR.getGroupID();
	System.out.println("ID; "+groupID);
	if(jobPost != 0) 
		surveyList = QR.getSurvey(jobPost);
		
	if(surveyID != 0) {				
		DivisionList = QR.getDivision(surveyID);
		DepartmentList = QR.getDepartment(surveyID, QR.getDivisionID());
		groupList = QR.getGroup(surveyID, QR.getDivisionID(), QR.getDepartmentID());
	}

	
		if(request.getParameter("open") != null) {
		
			int type = Integer.parseInt(request.getParameter("open"));
			Vector assignment = new Vector();
			SurveyResult s = new SurveyResult();
			jobPost = QR.getJobPostID();		
			surveyID = QR.getSurveyID();
		  
	
			//if(request.getParameter("div") != "All Division")
				//divID = Integer.parseInt(request.getParameter("div"));//QR.getDivisionID();
			
			//if(request.getParameter("dept") != "All Department")		
				//deptID = Integer.parseInt(request.getParameter("dept"));//QR.getDivisionID();
			
			//if(request.getParameter("groupName") != "All Group")		
				//groupID = Integer.parseInt(request.getParameter("groupName"));//QR.getDivisionID();	
			// Added by Ha 26/05/08 adding checking null value-re-edit by Ha 06/06/08 name of parameter
			String sDivID = request.getParameter("div");			
			if (sDivID !=null)
			{
				if(!(sDivID.equals("0")))
				divID = Integer.parseInt(sDivID);			
			}
			String sDeptID = request.getParameter("dept");			
			if (sDeptID !=null)
			{
				if(!(sDeptID.equals("0")))
				deptID = Integer.parseInt(sDeptID);
			}
			String sGroupID = request.getParameter("groupName");			
			if (sGroupID !=null)
			{
				if(!(sGroupID.equals("0")))
				groupID = Integer.parseInt(sGroupID);
			}			
			
	/*		//QR.setSurveyID(0);
			//QR.setJobPostID(0);	
			//QR.setDivisionID(0);	
			//QR.setDepartmentID(0);	
			//QR.setGroupID(0);	
			//QR.setPageLoad(1);	
	*/		
			Vector Target = new Vector();
			Date timeStamp = new java.util.Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			String temp  =  dFormat.format(timeStamp);
			String file_name = "GroupReport" + temp + ".xls";
			String temp1 = "";
			System.out.println(surveyID + "--" +  groupID + "--" +  deptID + "--" + divID);
			//Changed by Ha 17/06/08 to automatically calculate if not all the raters have been calculated
			// when generating the report
			Target = s.TargetID(surveyID, divID, deptID, groupID);
			
			for (int j = 0; j < Target.size(); j++)
				{
					voUser vo = (voUser)Target.get(j);
					if (!s.checkCalculationStatusComplete(vo.getTargetLoginID(), surveyID ,divID, deptID, groupID))
					{						
						 if (!s.isAllRaterRated(surveyID, groupID, vo.getTargetLoginID()))                	 
			                 s.CalculateStatus(vo.getTargetLoginID(),surveyID, divID, deptID, groupID, 1);
			             else
			                 s.CalculateStatus(vo.getTargetLoginID(),surveyID, divID, deptID, groupID, 0);						
					}
				}				
			
			
			ExcelGroup_NoCPR.Report(surveyID, groupID, deptID, divID, pkUser, file_name, type);
			System.out.println("pkUser "+pkUser );
			System.out.println("File name "+file_name );
			System.out.println("Type "+type);
			
	/*		try {			
				ExcelGroup_NoCPR.Report(surveyID, groupID, deptID, divID, pkUser, file_name);	
			} catch (SQLException SE) {
				timeStamp = new java.util.Date();
				temp  =  dFormat.format(timeStamp);
				file_name = "GroupReport" + temp + ".xls";
				ExcelGroup_NoCPR.Report(surveyID, groupID, deptID, divID, pkUser, file_name);	
			} catch (Exception E) {
				timeStamp = new java.util.Date();
				temp  =  dFormat.format(timeStamp);
				file_name = "GroupReport" + temp + ".xls";
				ExcelGroup_NoCPR.Report(surveyID, groupID, deptID, divID, pkUser, file_name);	
			}
	*/
	
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
	
			} catch (IOException ioe) {
				ioe.printStackTrace(System.out);
			}
				
			outs.flush();
			outs.close();
			in.close();	
	    
	}	
		/*if(request.getParameter("jobPost") == null) {
			if(request.getParameter("surveyID") == null) {
				QR.setJobPostID(0);
				QR.setSurveyID(0);
				QR.setGroupID(0);
				QR.setDepartmentID(0);
				QR.setDivisionID(0);
			}
		}
			
		if(request.getParameter("jobPost") != null) {
			int ID = Integer.parseInt(request.getParameter("jobPost"));
			QR.setJobPostID(ID);
			
			QR.setSurveyID(0);
			QR.setGroupID(0);
			QR.setDepartmentID(0);
			QR.setDivisionID(0);
			
			DivisionList = null;
			DepartmentList = null;
			groupList = null;	
			
			surveyList = QR.getSurvey(ID);
		}
		else if(request.getParameter("surveyID") != null) {
			surveyID = Integer.parseInt(request.getParameter("surveyID"));
			QR.setSurveyID(surveyID);
		
			QR.setGroupID(0);
			QR.setDivisionID(0);			
			QR.setDepartmentID(0);			
			
			DivisionList = QR.getDivision(surveyID);
			DepartmentList = QR.getDepartment(surveyID, QR.getDivisionID());
			groupList = QR.getGroup(surveyID, QR.getDivisionID(), QR.getDepartmentID());
		} 
		
		if(request.getParameter("surveyID") != null && request.getParameter("jobPost") != null)
		{
			int ID = Integer.parseInt(request.getParameter("jobPost"));
			QR.setJobPostID(ID);
			
			surveyID = Integer.parseInt(request.getParameter("surveyID"));
			QR.setSurveyID(surveyID);
			QR.setDivisionID(0);
			QR.setDepartmentID(0);
			QR.setGroupID(0);
			
			DivisionList = QR.getDivision(surveyID);
			DepartmentList = QR.getDepartment(QR.getSurveyID(), QR.getDivisionID());
			groupList = QR.getGroup(QR.getSurveyID(), QR.getDivisionID(), QR.getDepartmentID());
		}
			if(request.getParameter("div") != null)
			{
				QR.setDepartmentID(0);
				QR.setGroupID(0);
				divID = Integer.parseInt(request.getParameter("div"));
				QR.setDivisionID(divID);
				DivisionList = QR.getDivision(QR.getSurveyID());
				DepartmentList = QR.getDepartment(QR.getSurveyID(), QR.getDivisionID());
				groupList = QR.getGroup(QR.getSurveyID(), QR.getDivisionID(), QR.getDepartmentID());
			}
			
			if(request.getParameter("dept") != null)
			{	
				QR.setGroupID(0);
				deptID = Integer.parseInt(request.getParameter("dept"));
				QR.setDepartmentID(deptID);
				DivisionList = QR.getDivision(QR.getSurveyID());
				DepartmentList = QR.getDepartment(QR.getSurveyID(),  QR.getDivisionID());
				groupList = QR.getGroup(QR.getSurveyID(), QR.getDivisionID(), QR.getDepartmentID());
			}
			
			if(request.getParameter("groupName") != null)
			{
				groupID = Integer.parseInt(request.getParameter("groupName"));
				QR.setGroupID(groupID);
				DivisionList = QR.getDivision(QR.getSurveyID());
				DepartmentList = QR.getDepartment(QR.getSurveyID(),  QR.getDivisionID());
				groupList = QR.getGroup(QR.getSurveyID(),  QR.getDivisionID(), QR.getDepartmentID());
			}*/
	
	
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
          <% 
		    /*********************
			* Edit By James 14-Nov 2007
			***********************/
	
		 // while(jobPostList.next()) {
		 for(int i=0;i<jobPostList.size();i++){
		 	votblJobPosition voJob=(votblJobPosition)jobPostList.elementAt(i);
	  		int ID = voJob.getJobPositionID();
			String name = voJob.getJobPosition();
			
			jobPost = QR.getJobPostID();
						
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
      <td width="100" align="right" bordercolor="#FFFFCC"><%=trans.tslt("Survey")%> 
		: </td>
      <td width="10" bordercolor="#FFFFCC">&nbsp;</td>
	  <td width="230" bordercolor="#FFFFCC">
	  <select name="surveyName" onchange = "getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 2)">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <% if(surveyList != null) { 
			/*********************
			* Edit By James 14-Nov 2007
			***********************/
	
	  		//while(surveyList.next()) {
			for(int j=0;j<surveyList.size();j++){
				votblSurvey voSurvey=(votblSurvey)surveyList.elementAt(j);
				
	  			int ID = voSurvey.getSurveyID();
				String name = voSurvey.getSurveyName();
		
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
          <option value=<%=t%>><%=trans.tslt("All")%>
          <% 	
		   if(DivisionList != null) {
		   /*********************
			* Edit By James 14-Nov 2007
			***********************/
	
	  			//while(DivisionList.next()) {
				for(int k=0;k<DivisionList.size();k++){
				voDivision voD=(voDivision)DivisionList.elementAt(k);
				
				
					int ID = voD.getPKDivision();
					String name = voD.getDivisionName();
					
				divID = QR.getDivisionID();
		  		if(divID != 0 && ID == divID) 
				{
	  %>			<option value = <%=ID%> selected><%=name%>
      <% 		} 
		  		else 
				{ 
	  %>    		<option value = <%=ID%>><%=name%>
        <%  	  
		 		}
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
          <option value=<%=t%>><%=trans.tslt("All")%>
        <% 	if(DepartmentList != null) {
			
			/*********************
			* Edit By James 14-Nov 2007
			***********************/
	
	  			//while(DepartmentList.next()) {
				for(int l=0;l<DepartmentList.size();l++){
					voDepartment voDep=(voDepartment)DepartmentList.elementAt(l);
					
				
					int ID = voDep.getPKDepartment();
					String name =voDep.getDepartmentName();
						
				deptID = QR.getDepartmentID();		
			if(deptID != 0 && ID == deptID) 
			{
	  %>		<option value = <%=ID%> selected><%=name%>
       <% 	} else 
	   		{ 
       %>  		<option value = <%=ID%>><%=name%>
        <% 	
		  	}
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
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Group")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="groupName">
	  <option value=<%=t%>><%=trans.tslt("All")%>
	  <% if(groupList != null) { 
			/*********************
			* Edit By James 14-Nov 2007
			***********************/
	
		  	//while(groupList.next()) {
			for(int m=0;m<groupList.size();m++){
			voGroup voG=(voGroup)groupList.elementAt(m);
	  			int ID = voG.getPKGroup();
				String name = voG.getGroupName();
			
				groupID = QR.getGroupID();
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
      <td bordercolor="#FFFFCC">
		<p align="right">
		<input type="checkbox" name="chkReportType" value="ON" checked></td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">Simplified Report</td>
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
<input type="button" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form)" disabled>  	
<%
}
%>
</td>
    </tr>
  </table>
  

<% } %>
</form>

<p></p>
<%@ include file="Footer.jsp"%>
</table>
</body>
</html>