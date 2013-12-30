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
<title>Survey Result</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<style type="text/css">
<!--
.style1 {
	color: #0066FF;
	font-weight: bold;
}
.style7 {color: #000099; font-weight: bold; font-family: Arial;}
.style8 {color: #000000}
.style12 {
	font-style: italic;
	color: #FFFFFF;
	font-weight: bold;
	font-size: 10pt;
}
.style13 {
	color: #003399;
	border-left-style: solid;
	border-left-width: 1px;
	border-right-style: solid;
	border-right-width: 1px;
	border-bottom-style: solid;
	border-bottom-width: 1px;
}
.style15 {color: #003399}
-->
</style>
<script language="javascript">
function checkedAll(form, checkAll, field)
{	
	var alreadyCheck = 0;

	if(field != null) {
		
		if(checkAll.checked == true) {
			for(var i=0; i<field.length; i++) {
				field[i].checked = true;
				alreadyCheck == 1;
			}
			
			if(alreadyCheck == 0)
				field.checked = true;
		}
		else {
			for(var i=0; i<field.length; i++) {
				field[i].checked = false;	
				alreadyCheck == 1;
			}
			
			if(alreadyCheck == 0)
				field.checked = false;
				
		}
	
	}
}
	
</script>
</head>

<body>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurvey"%> 
<%@ page import="CP_Classes.vo.voUser"%> 
<script language="javascript">

var x; 
var y; 

function surveyOption(form, field, type)
{
	var isValid = 0;
	var checkedValue = 0;
	
	//check which radio button clicked	
	for (i = 0; i < field.length; i++) 
		if(field[i].checked) {
			checkedValue = field[i].value;
			//field[i].checked = false;
			isValid = 1;
		}
	
	if (isValid == 1)
	{		
		var myWindow, height, width;
		
		if(type == 2) {
			height = 200;
			width = 250;
			x = parseInt(window.screen.width) / 2 - 125; 
			y = parseInt(window.screen.height) / 2 - 100;   
		} else {
			height = 150;
			width = 350;
			x = parseInt(window.screen.width) / 2 - 175; 
			y = parseInt(window.screen.height) / 2 - 75;   
		}
		myWindow=window.open('','windowRef','scrollbars=no, width=' + width + ', height=' + height);
		myWindow.moveTo(x,y);
		myWindow.location.href = 'SurveyOptions.jsp?type=' + type + "&clicked=" + checkedValue;
	}
	else
		alert("<%=trans.tslt("No record selected")%>!");
	isValid = 0;
	
	return false;
	//form.action = "SurveyOptions.jsp?type=" + type;
	//form.submit();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function closeSurvey(form)
function closeSurvey(form)
{
	if(confirm("<%=trans.tslt("No more rater can be added once the survey is closed")%>")) {
		form.action = "SurveyResult.jsp?close=1";
		form.submit();
	}
}

//void function openSurvey(form)
function openSurvey(form)
{
	if(confirm("<%=trans.tslt("Are you sure you want to re-open the Survery")%>?")) {
		form.action = "SurveyResult.jsp?open=1";
		form.submit();
	}
}

//void function populateDept(form, field)
function populateDept(form, field)
{
	form.action = "SurveyResult.jsp?div=" + field.value;
	form.submit();
}

//void function populateGrp(form, field1, field2)
function populateGrp(form, field1, field2)
{
	form.action = "SurveyResult.jsp?div=" + field1.value + "&dept=" + field2.value;
	form.submit();
}

</script>

<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  { 
%>	<script>
		parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  { 	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	int divID =  SurveyResult.getDivID();
	int deptID = SurveyResult.getDeptID();
	int groupID = SurveyResult.getGroupID();
	
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
	
	int surveyID = CE_Survey.getSurvey_ID();
	String strReliability = "";
	int nameSeq = User.NameSequence(OrgID);
	
	int reliability = Calculation.ReliabilityCheck(surveyID);
	if(reliability == 0)
		strReliability = "Trimmed Mean";
	else
		strReliability = "Reliability Index";
		
	votblSurvey SurveyInfo = SurveyResult.SurveyInfo(surveyID);
	
	String surveyName = SurveyInfo.getSurveyName();
	String dateOpened = day_view.format(SurveyInfo.getOpenedDate());
	
	String surveyLevel = "";
	int Level = Calculation.LevelOfSurvey(surveyID);
	if(Level == 0)
		surveyLevel = "Competency";
	else
		surveyLevel = "Key Behaviour";	
		
	String deadline = day_view.format(SurveyInfo.getDeadlineSubmissionDate());
	
	int surveyStatus = SurveyInfo.getSurveyStatus();
	String jobPost = RDE.getJobPosition(surveyID);

	SurveyResult.setSurveyID(surveyID);
	SurveyResult.setSurveyLevel(Level);
	
	
	//ResultSet groupSection = SurveyResult.GroupSection(surveyID);
	Vector targetID = null;
	Vector raterID = null;
	
	int target = 0, rater = 0;
	
	if(request.getParameter("btnSearch") != null) {
		groupID = Integer.parseInt(request.getParameter("lstGroup"));
		deptID = Integer.parseInt(request.getParameter("lstDepartment"));
		divID = Integer.parseInt(request.getParameter("selDiv"));

		SurveyResult.setGroupID(groupID);
		SurveyResult.setDeptID(deptID);
		SurveyResult.setDivID(divID);
	}else {
		divID = -1;
		deptID = -1;
		groupID = -1;
		
		SurveyResult.setDeptID(divID);
		SurveyResult.setDeptID(deptID);
		SurveyResult.setGroupID(groupID);
	}
	
%>

<form action="SurveyResult.jsp" method="post" name="SurveyResult">
<table width="610" border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="91" height="24" align="right"><%=trans.tslt("Survey Name")%> : </td>
    <td width="6">&nbsp;</td>
    <td width="159">
      <input type="text" name="surveyName" value="<%=surveyName%>" readonly>
    </td>
    <td width="9">&nbsp;</td>
    <td width="150" align="right"><%=trans.tslt("Date Opened")%> : </td>
    <td width="9">&nbsp;</td>
    <td width="156"><input type="text" name="dateOpened" value="<%=dateOpened%>" readonly></td>
  </tr>
  <tr>
    <td height="26" align="right"><%=trans.tslt("Level")%> : </td>
    <td>&nbsp;</td>
    <td><input type="text" name="surveyLevel" value="<%=surveyLevel%>" readonly></td>
    <td>&nbsp;</td>
    <td align="right"><%=trans.tslt("Deadline Submission")%> : </td>
    <td>&nbsp;</td>
    <td><input type="text" name="deadline" value="<%=deadline%>" readonly></td>
  </tr>  
  <tr>
    <td align="right"><%=trans.tslt("Job Position")%> : </td>
    <td>&nbsp;</td>
    <td><input type="text" name="jobPost" value="<%=jobPost%>" readonly></td>
    <td>&nbsp;</td>
    <td align="right"><%=trans.tslt("Reliability Check")%> : </td>
    <td>&nbsp;</td>
    <td><input type="text" name="reliability" value="<%=strReliability%>" readonly></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>	  	
	<td>&nbsp;</td>
<% if (userType == 1 && surveyStatus == 2) {
%>	
  	<td><input name="btnOpen" type="button" value="<%=trans.tslt("Re-Open Survey")%>" onClick="openSurvey(this.form)"></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>
  	<td>&nbsp;</td>
  	<td>&nbsp;</td>
	<td>&nbsp;</td>
<% if(surveyStatus == 1) {
%>	  		
	<td><input name="btnClose" type="button" value="<%=trans.tslt("Close Survey")%>" onClick="closeSurvey(this.form)"></td>
<% } else {
%>
<td>&nbsp;</td>
<% } %>
  </tr>
</table>
<hr width="610" align="left">


<table border="0" width="610" cellspacing="0" cellpadding="0" style='font-size:10.0pt;font-family:Arial'>
	<tr>
		<td colspan="3">
		<font face="Arial" style="font-weight: 700" color="#000080" size="2"><%=trans.tslt("Process Result")%> </font></td>
	</tr>
	<tr>
		<td colspan="3">
		<ul>
			<li><font face="Arial" size="2">
			<%=trans.tslt("To view a Target's result,click on the radio button next to the target.")%> </font></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td colspan="3"><ul>
			<li><font face="Arial" size="2">
			<%=trans.tslt("To view a Rater's input, click on the radio button next to the rater")%> </font></li>
		</ul></td>
	</tr>
	<tr>
		<td colspan="3"><ul>
			<li><font face="Arial" size="2">
			<%=trans.tslt("To calculate the scores for Target(s) prior to the completion of all raters, tick next to the target and click on the calculate button.")%> </font></li>
		</ul></td>
	</tr>

	<tr>
	  <td colspan="3">&nbsp;</td>
    </tr>
	<tr>
	  <td width="100"><%= trans.tslt("Division") %></td>
      <td width="14">:</td>
      <td width="496"><select name="selDiv" onChange="populateDept(this.form, this.form.selDiv)">
<% 		
		if(request.getParameter("div") != null)
		{
			if (request.getParameter("div").equals(""))
				divID = 0;
			else
				divID = Integer.parseInt(request.getParameter("div"));
		}
		if(divID == 0) {
%>			<option value=0 selected>All Divisions</option>
        <%
		} else {
%>			<option value=0>All Divisions</option>
        <%
		}	  
	
	//Vector vDiv = SurveyResult.getvDiv();
	//if(vDiv.isEmpty())
	Vector vDiv = SurveyResult.getSurveyDiv(surveyID, logchk.getOrg());

	for(int i=0; i<vDiv.size(); i++) {
		String [] row = (String[])(vDiv.elementAt(i));
		int Division_ID = Integer.parseInt(row[0]);
		String Division_Desc = row[1];
	
	if(divID != 0 && divID == Division_ID)
	{
	%>
        <option value=<%=Division_ID%> selected><%=Division_Desc%></option>
        <%	}else{%>
        <option value=<%=Division_ID%>><%=Division_Desc%></option>
        <%	}
}	

%>
      </select></td>
	</tr>
	<tr>
	  <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
	</tr>
	<tr>
	  <td width="100"><%= trans.tslt("Department") %></td>
      <td width="14">:</td>
      <td width="496"><select name="lstDepartment" onChange="populateGrp(this.form, this.form.selDiv, this.form.lstDepartment)">
   <% 
   	if(request.getParameter("dept") != null)
	{
		if(request.getParameter("dept").length() > 0)
		{
   			deptID = Integer.parseInt(request.getParameter("dept"));
		}
	}
	
	if(deptID == 0) {
%>
        <option value=0 selected>All Departments</option>
        <%
		} else {
%>
        <option value=0>All Departments</option>
        <%
}
	Vector vDept = SurveyResult.getvDept();
	
	if(vDept.isEmpty())
		vDept = SurveyResult.getSurveyDept(surveyID, divID);
		
	for(int i=0; i<vDept.size(); i++) {
		String [] row = (String[])(vDept.elementAt(i));
		int dep_ID = Integer.parseInt(row[0]);
		String dep_Name = row[1];
		
	if(deptID != 0 && deptID == dep_ID)
	{
	%>
        <option value=<%=dep_ID%> selected><%=dep_Name%></option>
        <%	}else{%>
        <option value=<%=dep_ID%>><%=dep_Name%></option>
        <%	}
}	%>
      </select></td>
	</tr>
	<tr>
	  <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
	</tr>
	<tr>
	  <td><%=trans.tslt("Group")%></td>
      <td>:</td>
      <td><select name="lstGroup">
<% 
	if(request.getParameter("dept") != null)
	{
		if (request.getParameter("dept").equals(""))
			deptID = 0;
		else
			deptID = Integer.parseInt(request.getParameter("dept"));
	}
		
if(groupID == 0) {
%>
<option value=0 selected>All Groups</option>
<%
} else {
%>
<option value=0>All Groups</option>
<%
}
	Vector vGroup = SurveyResult.getvGroup();
	String selectedGroupName = "";
	
	if(vGroup.isEmpty())
		vGroup = SurveyResult.getSurveyGroup(surveyID, deptID);
		
	for(int i=0; i<vGroup.size(); i++) {
		String [] row = (String[])(vGroup.elementAt(i));
		int Group_ID = Integer.parseInt(row[0]);
		String GroupName = row[1];
		
	if(groupID != 0 && groupID == Group_ID)
	{
		selectedGroupName = GroupName;
	%>
		<option value=<%=Group_ID%> selected><%=GroupName%></option>
<%	}else{%>
		<option value=<%=Group_ID%>><%=GroupName%></option>
<%	}
}	%>				  
      </select></td>
	</tr>
	<tr>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
    </tr>
	<tr>
	  <td colspan="3"><input type="submit" name="btnSearch" value="<%=trans.tslt("Show") %>"></td>
    </tr>	
  </table>
<p><hr width="610" align="left"></p> 
<table width="609" border="0">
  <tr>
    <td width="609" valign="top">
	<table width="100%" border="1" align="left" style='font-size:11.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
  	<%
	String surveyImage = "";
	switch(surveyStatus) { 
		case 1 : surveyImage = "images/ViewSurveyOpen.bmp";
				 break;
		case 2 : surveyImage = "images/ViewSurveyClosed.bmp";
				 break;
		case 3 : surveyImage = "images/ViewSurveyClosed.bmp";
				 break;	
	}
	
	%>
	 
   <p> <span class="style7"><img src=<%=surveyImage%>>     <%=surveyName%></span></p>
 
	<tr height="20"><td width=50 bgcolor="#000080">&nbsp;<font size="2" style="color: white"><%=trans.tslt("Group") %></font></td>
	<td width="19" bgcolor="#000080">
	<input type="checkbox" name="chkAll" onClick="checkedAll(this.form, this.form.chkAll,this.form.ckbTarget)" >
	</td>
	
	<td width="200" bgcolor="#000080" align="center">&nbsp;<font size="2" style="color: white"><%=trans.tslt("Target") %></font></td>
		<td width="300" bgcolor="#000080" align="center">&nbsp;<font size="2" style="color: white"><%=trans.tslt("Rater") %></font></td><td width="40" bgcolor="#000080" align="center">&nbsp; 
	<font size="2" style="color: white"><%=trans.tslt("Calculation Status") %></font></td> </tr>
	<%
	SurveyResult.setSurveyID(surveyID);
	
	if(groupID > 0) {
		int group = groupID;
		String groupName = selectedGroupName;
		targetID = SurveyResult.TargetID(surveyID, divID, deptID, group);
		if(targetID.size() != 0) {
		for(int j=0; j<targetID.size(); j++) {
			voUser voUsr = (voUser)targetID.elementAt(j);
			target = voUsr.getTargetLoginID();
			String givenName = voUsr.getGivenName();
			String familyName = voUsr.getFamilyName();
			
			String targetName = "";
			
			//0=familyname first
				if(nameSeq == 0)
					targetName = familyName + " " + givenName;
				else
					targetName = givenName + " " + familyName;
				if(j==0){
				%> 
					<tr height="0">
   							<td rowspan="<%=(targetID.size()+1)%>">
   							<span class="style7">&nbsp;<%=groupName%></span></td>	
					</tr>
				<tr>
				<%
				
				}
				System.out.println(group);
				raterID = SurveyResult.RaterID(surveyID, group, target);
				boolean bIsAllRated =SurveyResult.isAllRated(surveyID, group, target);
				boolean bIsAllCalculated=SurveyResult.isAllCalculated(surveyID,group,target);

				System.out.println(bIsAllRated + "--------------------------" + bIsAllCalculated);
				if(bIsAllRated==false){
				%>
				<td>&nbsp;</td>
				<%}
				else if(bIsAllRated==true&&bIsAllCalculated==true){
				%>
				<td>&nbsp;</td>
				<%
				}
				else{
				String cName="chkTarget"+j;
				System.out.println("Check Box Name : "+cName);
				%>
				<td ><input type="checkbox" name="ckbTarget" value="<%=target%>" /></td>
				<%}%>
				<td><span class="style1"><input name="survey" type="radio" value="<%=target%>" onClick="surveyOption(this.form, this.form.survey, 2)">&nbsp;<%=targetName%></span></td>
 				<td>
 				<table border="0" cellspacing="0" cellpadding=0 >
 		<%  
 		int iAdminStatus=-1;
		for(int k=0; k<raterID.size(); k++) {
			voUser voUsrRater = (voUser)raterID.elementAt(k);
			rater = voUsrRater.getRaterLoginID();
			
			String name [] = SurveyResult.RatersName(rater);
			
			givenName = name[0];
			familyName = name[1];
			
			String raterName = "";
			
			//0=familyname first
				if(nameSeq == 0)
					raterName = familyName + " " + givenName;
				else
					raterName = givenName + " " + familyName;
			
			
			int assignment = Calculation.AssignmentID(surveyID, target, rater);
			int status = SurveyResult.RatersStatus(assignment);
			int calculationStatus=SurveyResult.CalculationStatus(assignment);
			if(k==0){
			iAdminStatus=SurveyResult.AdminCalcStatus(assignment);
			}
			String image = "";
	String raterCode = SurveyResult.RaterCode(assignment);
	switch(status) {
		case 0 : image = "images/RaterIncomplete.bmp";
				 break;
		case 1 : image = "images/RaterCompleted.bmp";
				 break;
		case 2 : image = "images/RaterUnreliable.bmp";
				 break;
		case 3 : image = "images/RaterExcluded.bmp";
				 break;	
		case 4 : image = "images/RaterCompleted.bmp";
				 break;	 
		case 5 : image = "images/RaterUnreliable.bmp";
				 break;
	}
	
			if(rater != 0) {
			%>
		<tr>			
			 <td width="279"><span class="style8"><input name="survey" type="radio" value="<%=assignment%>" onClick="surveyOption(this.form, this.form.survey, 3)"><img src=<%=image%>>     <%=raterName + " (" + raterCode + ")"%></span>
			 <%
			 if(calculationStatus!=0&&bIsAllCalculated==false){%>
			 <img src="images/abacus.PNG">
			 <%}%>
			 </td>
    	</tr>
 			<%}
 		}//End for Loop
 		
 		%>
 				
 				</table>
 				</td>
 				<%
 				String sCalStatus="";
 				if(iAdminStatus==1){
 				sCalStatus="Admin Calc";
 				}else if(iAdminStatus==0){
 				sCalStatus="SystemCalc";
 				}
 				%>
 				<td>&nbsp;<%=sCalStatus%></td>
				
				
							
 				</tr>
				<%
			}//End for Loop
		}//End if loop
	}//End if loop
	%>
	
	
 </table>

</td>
</tr>
<tr><td align=right> <input type="button" value="Calculate"> </td>	</tr>
<tr>
    <td width="196">
	<table width="178" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#0099FF">
  <tr height="20">
    <td colspan="2" align="center" bordercolor="#0099FF" bgcolor="#000099" style="border-style: solid; border-width: 1px"><span class="style12">
	<%=trans.tslt("LEGEND")%></span></td>
    </tr>
  <tr height="20">
    <td width="32" align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/ViewSurveyOpen.bmp"></td>
    <td width="131" bgcolor="#FFFFCC" class="style13">&nbsp;<%=trans.tslt("Survey Open")%></td>
  </tr>
  <tr height="20">
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/ViewSurveyClosed.bmp"></td>
    <td bgcolor="#FFFFCC" class="style8" style="border-style: solid; border-width: 1px; color: #003399;">&nbsp;<%=trans.tslt("Survey Closed")%></td>
  </tr>
 
  <tr height="20">
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/abacus.PNG"></td>
    <td bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><span class="style15">&nbsp;<%=trans.tslt("Rater Calculated")%></span></td>
  </tr>
  <tr height="20">
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/RaterCompleted.bmp"></td>
    <td bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><span class="style15">&nbsp;<%=trans.tslt("Rater Completed")%></span></td>
  </tr>
  <tr height="20">
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/RaterIncomplete.bmp"></td>
    <td bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><span class="style15">&nbsp;<%=trans.tslt("Rater Incomplete")%></span></td>
  </tr>
  <tr height="20">
    <td align="center" bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><img src="images/RaterUnreliable.bmp"></td>
    <td bgcolor="#FFFFCC" style="border-style: solid; border-width: 1px"><span class="style15">&nbsp;<%=trans.tslt("Rater Unreliable")%></span></td>
  </tr>
</table></td>
  </tr>
  <p></p>
<%@ include file="Footer.jsp"%>
<%
	if(request.getParameter("close") != null) {
		Date today = new Date();
		SurveyResult.CloseSurvey(surveyID, pkUser, day_view.format(today));
%>
<script>
	alert("<%=trans.tslt("Survey has been closed")%> !");
</script>

<%	
	} else if(request.getParameter("open") != null) {
		SurveyResult.OpenSurvey(surveyID, pkUser);
%>
<script>
	alert("<%=trans.tslt("Survey status has been set to Open")%> !");
</script>

<%	
	}
			
	}
%>

</form>
</body>
</html>