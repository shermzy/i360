<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Development Resources</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>

<script language="javascript" src="script/cal2.js"></script>       
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/cal_conf3.js"></script>
<script language="javascript" src="script/cal_validation.js"></script>
<jsp:useBean id="DP" class="CP_Classes.DevelopmentPlan" scope="session"/>
<jsp:useBean id="DR" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="DA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "CP_Classes.vo.voDevelopmentPlan" %>
<%@ page import = "CP_Classes.vo.votblSurvey" %>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

var x = parseInt(window.screen.width) / 2 - 215;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 175; 


function check(field, option)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	
	if( field == null ) {
		isValid = 2;
	
	} else {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked) {		
				clickedValue = field[i].value;
				//field[i].checked = false;
				isValid = 1;
			}
    
		if(isValid == 0 && field != null)  {
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
		}
    }
	
	if(option == 1) {
		if(isValid == 1)
			return clickedValue;
		else if(isValid == 0)
			alert("Please select a resource type");
		else if(isValid == 2)
			alert("No record available");
	} else {
	
		if(isValid == 1)
			return clickedValue;
		else if(isValid == 0)
			alert("No record selected");
		else if(isValid == 2)
			alert("No record available");
	
	}
	
	isValid = 0;	
	
}


//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmEdit(form) {	
function confirmEdit(form) {	
	
	form.action="EditDevelopmentPlan.jsp?update=1&survey="+EditDevPlan.surveyID.value;
	form.method="post";
	form.submit();
	
	// Added by Tracy 12 Aug 08**************************
	//Transfer to DevelopmentPlan.jsp and refresh the page after confirm update
	alert("Update Successfully");
	opener.location.href="DevelopmentPlan.jsp?survey="+EditDevPlan.surveyID.value;
	window.close();
	//***************************************************
}
</script>

<%	
// Added by Tracy 12 Aug 08****************************
// Get surveyID from URL string and save it in a textbox for reference
String strSurveyID= (String)request.getParameter("survey");
int intSurveyID=Integer.parseInt(strSurveyID);
//*****************************************************

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

	int raterID = logchk.getPKUser();
	int DisplayNo, ResID, pkComp, fkComp, res;
	String resName [] = new String [6];

	resName[1] = trans.tslt("Development Activities");
	resName[2] = trans.tslt("Books");
	resName[3] = trans.tslt("Web Resources");
	resName[4] = trans.tslt("Training Courses");
	resName[5] = trans.tslt("AV Resources"); // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
	
	String CompName, DRARes, origin;
	DisplayNo = 1;
	ResID = 0;
	DRARes = "";
	CompName = "";
	pkComp = 0;
	fkComp = DP.getFKComp();
	res = DP.getOption();

	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	Vector vRes = new Vector();
	
	String sProposedDate = "";
	String sCompletionDate = "";
	String sProcess = "";
	String sResource = "";
	
	if(request.getParameter("update") != null)
	{ 
		
		sCompletionDate = request.getParameter("CompletionDate");
		
		DP.updateRecord(DP.getPKDevelopmentPlan(), sCompletionDate);
%>
<script>
//*************Commented by Tracy 12 Aug 08*************
//var surveyID='<%=session.getAttribute("surveyID")%>';
//alert(surveyID);
//alert("Update Successfully");

//opener.location.href="DevelopmentPlan.jsp";
//window.close();
//******************************************************
</script>


<%
	}
	
	if(request.getParameter("edit") != null) {
		int iPKDevelopmentPlan = Integer.parseInt(request.getParameter("edit"));
		
		DP.setPKDevelopmentPlan(iPKDevelopmentPlan);
		voDevelopmentPlan voDev = DP.getDevelopmentPlan(iPKDevelopmentPlan);
		CompName = voDev.getCompetencyName();
		sProcess = voDev.getProcess();
		sResource = voDev.getResource();
		sProposedDate = voDev.getProposedDate();
		sCompletionDate = voDev.getCompletionDate();
		
	}
/*-------------------------------------------------------------------end login modification 1--------------------------------------*/


/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	/*int toggle = DRAResQuery.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		DRAResQuery.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		DRAResQuery.setSortType(type);									
	} */
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	
%>

<form name="EditDevPlan" method="post" action="EditDevelopmentPlan.jsp">
<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	<tr>
	  <td colspan="4"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Individual Development Plan") %> </font></b></td>
    </tr>
	
	 <tr>
      <td width="131">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="355">&nbsp;</td>
      <td width="75" align="center">&nbsp;</td>
    </tr>
	
</table>
 

<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	 <tr>
	  <td><input type="hidden" size="15" name="surveyID" value="<%=intSurveyID%>"></td>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
    
    </tr>
    <tr>
      <td><%= trans.tslt("Competency") %></td>
      <td>:</td>
      <td><%=CompName%> </td>
	</tr>
	 <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
    
    </tr>
    <tr>
      <td><%= trans.tslt("Development Activities/Resources") %></td>
      <td>:</td>
      <td> <%=sResource%></td>
	</tr>
	 <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
    
    </tr>
	<tr>
      <td><%= trans.tslt("Development Review Process") %></td>
      <td>:</td>
      <td><%=sProcess%></td>
	</tr>  
	 <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
    
    </tr>
    <tr>
      <td><%= trans.tslt("Proposed Completion Date") %></td>
      <td>:</td>
      <td><input type=text size="15" name="ProposedDate" value="<%=sProposedDate%>" disabled>
	  </td>
    
    </tr>	
    <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
     
    </tr>
	<tr>
      <td><%= trans.tslt("Actual Completion Date") %></td>
      <td>:</td>
      <td><input type=text size="15" name="CompletionDate" value="<%=sCompletionDate%>">
	  <img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onclick="javascript:showCal('EditCompletionDate')">
  
	  </td>
     <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>
     
    </tr>
    </tr>	
</table>

<p></p>
<input type="button" name="Add" value="<%= trans.tslt("Submit") %>" onClick="confirmEdit(this.form)">
<input type="button" name="btnCancel" value="<%= trans.tslt("Cancel") %>"  onclick = "window.close()">
<%

}
%>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div align="center">
  <%@ include file="Footer.jsp"%>
</div>
</body>
</html>