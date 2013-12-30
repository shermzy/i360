<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
                 java.util.*"%>  
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Add new survey</title>

<script type="text/javascript">

function closeWindow(){
	window.close();
}

function checkFields(){
	// array of radio buttons
	var radiobuttonArray = document.CreateSurveyOptions.checkOptions;
	// -1 means none of the rb selected
	var selectedRb = -1;
	
	for(i=0;i<radiobuttonArray.length;i++){
		if(radiobuttonArray[i].checked){
			selectedRb = i;
			// break after detecting which rb is selected
			break;
		}
	}
	
	// if -1, no rb selected, else it specifies the rb selected in the rb array 
	if(selectedRb == -1){
		alert("<%=trans.tslt("Please select an option")%>!");
	}else{
		proceed(selectedRb)
	}		
}

function proceed(selectedRb){
	switch(selectedRb){
		case 0 :
			// Copy from job category
			if(document.CreateSurveyOptions.selJobCat.value!=0){
				CreateSurveyOptions.action="Survey_Setup.jsp?proceed="+ document.CreateSurveyOptions.selOrg.value + 
					"&jobCat="+document.CreateSurveyOptions.selJobCat.value;
				CreateSurveyOptions.method="post";
			}else{
				alert("<%=trans.tslt("Please select Job Category")%>");
			}
			break;
		case 1:
			// Copy from survey
			if(CreateSurveyOptions.copySurvey.value!=0){
				CreateSurveyOptions.action="Survey_Setup.jsp?copy=1";
				CreateSurveyOptions.method="post";
			}else{
				alert("<%=trans.tslt("Please select Survey")%>");
			}
			break;
		case 2:
			// Create new survey
			closeWindow();
			opener.location.href = "SurveyDetail.jsp?new=1";
			break;
	}
}

function changeOrg(){
	document.CreateSurveyOptions.action = "Survey_Setup.jsp?changeOrg=" + document.CreateSurveyOptions.selOrg.value;
	document.CreateSurveyOptions.method = "post";

	
	document.CreateSurveyOptions.submit();
	
}
</script>
<%
if(request.getParameter("copy") != null){
	int var1 = new Integer(request.getParameter("copySurvey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>
<script type="text/javascript">
closeWindow();
window.open('CopySurvey.jsp','windowRef','scrollbars=no, width=300, height=300');
</script>
<%
}

if(request.getParameter("copyJobCat") != null)
{
	int PKOrg = new Integer(request.getParameter("copyJobCat")).intValue();
	CE_Survey.set_survOrg(PKOrg);
}

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
 	<script type="text/javascript">
 	closeWindow();
 	opener.location.href = "SurveyDetail.jsp";
 	</script>
 <%
}

if(request.getParameter("changeOrg") != null){
	int var2 = new Integer(request.getParameter("changeOrg")).intValue();
	CE_Survey.set_survOrg(var2);
	
}
%>

</head>
<body bgcolor="#E2E6F1">
<form name="CreateSurveyOptions" method="post" action="">
<table width="562">
<tr>
<td colspan="2" height="46">
<b><font face="Arial" size="2"><%=trans.tslt("Add new survey")%></font></b>
</td>
<tr>

<tr>
<td height="47"><font face="Arial" size="2"><%=trans.tslt("Organisation : ")%></font></td>

<td height="47" >
<font face="Arial" size="2">
<select size="1" name="selOrg" id="selOrg" onchange="changeOrg()">
		<!-- <option value ="0"  selected><%=trans.tslt("All")%></option> -->
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
</select>
<input type="submit" value="Show" onclick="changeOrg()"/>
</font>
</td>
</tr>
<td width="214" height="43">
<input type="radio" id="copyFromJobCategory" name="checkOptions"/> 
<font face="Arial" size="2"><%=trans.tslt("Copy from Job Category")%></font>
</td>

<td width="338" height="43">
<font face="Arial" size="2"><font size="2">
   
    <select size="1" name="selJobCat" id="selJobCat">
    	<option value ="0"  selected><%=trans.tslt("Please select one")%></option>
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
</select>
</font>
</tr>
<tr>
<td width="214" height="41">
<input type="radio" id="copyFromSurvey" name="checkOptions"/>
<font face="Arial" size="2"><%=trans.tslt("Copy from Survey")%></font>
</td>

<td width="338" height="41">
<font face="Arial" size="2">
	<select size="1" name="copySurvey" id="copySurvey">
		<option value ="0" selected><%=trans.tslt("Please select one")%></option>
<%
	String Survey_Name=" ";
	Vector rs_SurveyDetail = CE_Survey.getRecord_Survey(logchk.getCompany(), CE_Survey.get_survOrg(), CE_Survey.getJobPos_ID());
	
	for(int i=0;i<rs_SurveyDetail.size();i++){
		votblSurvey voSurv=(votblSurvey)rs_SurveyDetail.elementAt(i);
	
		String Surv_Name = voSurv.getSurveyName();
		int Surv_ID = voSurv.getSurveyID();
%>
		<option value="<%=Surv_ID%>"><%=Surv_Name%></option>
<%
	}
%>
</select>
</font>
</td>

<tr>

<td colspan=2 height="45">
<input type="radio" id="create" name="checkOptions"/>
<font face="Arial" size="2"><%=trans.tslt("Create Survey from scratch")%></font>
</td>

</tr>

<tr>
<td align="right" colspan=2>
<input type="submit" id="submit" onclick="checkFields()" value="<%=trans.tslt("Proceed")%>"></input>
<input type="button" id="cancel" onclick="closeWindow()" value="<%=trans.tslt("Cancel")%>"></input>
</td>
</tr>

</table>
</form>
</body>
</html>