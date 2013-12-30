<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Development Resources</title>

<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<script language="javascript" src="script/cal2.js"></script>       
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/cal_conf3.js"></script>
<script language="javascript" src="script/cal_validation.js"></script>

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

//Edited by Xuehai, 27 May 2011. Remove 'void' to enable it running on Chrome&Firefox
//void function confirmAdd(form, field) {	
function confirmAdd(form, field) {	
	var value = check(field);
	//alert(value);
	//Edited by Roger 30 June 2008
	//add date validation for date field
	if(value) {
	
		if (form.ProposedDate.value == "") {
			alert("Please fill up Proposed Date");
		} else if (checkdate(form.ProposedDate) == false){

		} else if (form.CompletionDate.value!=""  && checkdate(form.CompletionDate) == false) {

		} else {
			form.action="AddDevelopmentPlan.jsp?add="+value+"&survey="+UpdateDevPlan.surveyID.value;
			form.method="post";
			form.submit();
			
			//***********Added by Tracy 12 Aug 08
			//Transfer to DevelopmentPlan.jsp and refresh the page after confirm add
			alert("Added successfully");
			opener.location.href="DevelopmentPlan.jsp?survey="+UpdateDevPlan.surveyID.value;
			window.close();
			//**********************************
		}
	}
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form, field)
{
	if(form.Competency.value == 0) {
		alert("Please select Competency");
		return false;
	
	} else {
		if(check(field, 1)){
			form.action="AddDevelopmentPlan.jsp?show=1&survey="+UpdateDevPlan.surveyID.value;
			form.method="post";
			form.submit();
		}
	}
}

function changeSurvey(form, field)
{

	form.action="AddDevelopmentPlan.jsp?survey="+field;
	form.method="post";
	form.submit();
	
}



</script>

</head>

<body>


<jsp:useBean id="DP" class="CP_Classes.DevelopmentPlan" scope="session"/>
<jsp:useBean id="DR" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="DA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "CP_Classes.vo.voDevelopmentPlan" %>
<%@ page import = "CP_Classes.vo.votblSurvey" %>
  

<%	

int intSurveyID= new Integer(request.getParameter("survey")).intValue();
//int intSurveyID=Integer.parseInt(strSurveyID);

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
	resName[5] = trans.tslt("AV Resources"); // Change Resource category from "In-house Resources" to "AV Resources", Desmond 10 May 2011
	
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
	
	if(request.getParameter("show") != null)
	{ 
		int comp = new Integer(request.getParameter("Competency")).intValue();
		res = new Integer(request.getParameter("rbtnRes")).intValue();
		
		DP.setFKComp(comp);
		DP.setOption(res);
		fkComp = comp;
		
		if(res == 1)
			vRes = DA.getDevelopmentActivities(comp, raterID);
		else
			vRes = DR.getDevelopmentResources(comp,res-1, raterID);
	}

	
	if(request.getParameter("survey") != null)
	{ 
		int FKSurvey = new Integer(request.getParameter("survey")).intValue();
		
		DP.setFKSurvey(FKSurvey);
		
	}
	
	if(request.getParameter("add") != null)
	{ 
		ResID = new Integer(request.getParameter("add")).intValue();
		
		int comp = 0;
		if(request.getParameter("Competency") != null) {
			comp = new Integer(request.getParameter("Competency")).intValue();
		}
		
		if(request.getParameter("rbtnRes") != null) {
			res = new Integer(request.getParameter("rbtnRes")).intValue();
		}
		
		sProposedDate = request.getParameter("ProposedDate");
		sCompletionDate = request.getParameter("CompletionDate");
		//Edited by Roger 20 June 2008
		//include survey id to addRecord calls
		int surveyId = new Integer(request.getParameter("Survey")).intValue();
		
		if(res == 1)
			//DP.addRecord(raterID, comp, ResID, 0, sProposedDate, sCompletionDate);
			DP.addRecord(raterID, comp, ResID, 0, sProposedDate, sCompletionDate, surveyId);
		else
			//DP.addRecord(raterID, comp, 0, ResID, sProposedDate, sCompletionDate);
			DP.addRecord(raterID, comp, 0, ResID, sProposedDate, sCompletionDate, surveyId);
			
%>
<script>
// Commented by Tracy 12 Aug 08*********************
//alert("Added successfully");
//opener.location.href="DevelopmentPlan.jsp";
//window.close();
//****************************************************
</script>


<%
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

<form name="UpdateDevPlan" method="post" action="Update DevelopmentPlan.jsp">
<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	<tr>
	  <td colspan="4"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Individual Development Plan") %> </font></b></td>
    </tr>
	<tr>
	  <td colspan="4"><ul>
	    <li><font face="Arial" size="2"><%= trans.tslt("Select the Survey then Click on the dropdown list next to Competency to show the list of Competencies that you require development in") %>.</font></li>
	    <li><font face="Arial" size="2"><%= trans.tslt("Select the Resource Type by clicking next to the appropriate radio button and click on the Show button") %>.</font></li>
	  	<li><font face="Arial" size="2"><%= trans.tslt("Select the Development Activities/Resources item from the list. Click on the Calendar icon to fill in the Proposed Completion Date and click on the Submit button") %>.</font></li>
	  
	  </ul></td>
	 <tr>
      <td width="131">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="355">&nbsp;</td>
      <td width="75" align="center">&nbsp;</td>
    </tr>
    <tr><td><td><input type="hidden" size="15" name="surveyID" value="<%=intSurveyID%>" ></td></tr>
    <tr>
      <td><%= trans.tslt("Survey") %></td>
      <td>:</td>
      <td><select name="Survey" onchange="changeSurvey(this.form, this.form.Survey)">
        <option value=0 selected><%=trans.tslt("All")%>
        <%
			//Edit by Roger 1 July 2008
			//display survey base on target instead
			Vector vSurvey = DP.getSurveysByTargetDevCompetency(pkUser);
			//Vector vSurvey = DP.getSurveys(pkUser);
			for(int i=0; i<vSurvey.size(); i++) {
				votblSurvey vo = (votblSurvey)vSurvey.elementAt(i);
			
				int pkSurvey = vo.getSurveyID();
				String sSurveyName = vo.getSurveyName();
				if(pkSurvey != 0 && pkSurvey == DP.getFKSurvey()) {
		%>
        <option value = <%=pkSurvey%> selected><%=sSurveyName%>
        <%		

				}else {
		%>
        <option value = <%=pkSurvey%>><%=sSurveyName%>
        <%
				}
			}
		%>
        </select></td>
      <td align="left">&nbsp;</td>
    </tr>	
    <tr>
      <td width="131">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="355">&nbsp;</td>
      <td width="75" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td><%= trans.tslt("Competency") %></td>
      <td>:</td>
      <td><select name="Competency">
        <% int t = 0;
		%>
        <option value=<%=t%> selected><%=trans.tslt("Please select")%>
        <%
			Vector vComp = DP.getDevelopmentCompetency(DP.getFKSurvey(),pkUser);
			for(int i=0; i<vComp.size(); i++) {
				voCompetency vo = (voCompetency)vComp.elementAt(i);
			
				pkComp = vo.getCompetencyID();
				CompName = vo.getCompetencyName();
				if(pkComp != 0 && pkComp == fkComp) {
		%>
        <option value = <%=pkComp%> selected><%=CompName%>
        <%		

				}else {
		%>
        <option value = <%=pkComp%>><%=CompName%>
        <%
				}
			}
		%>
        </select></td>
      <td align="left">&nbsp;</td>
    </tr>	
	 <tr>
	   <td>&nbsp;</td>
	   <td>&nbsp;</td>
	   <td>&nbsp;</td>
	   <td>&nbsp;</td>
    </tr>
	
</table>
 
<table width="588" border="0" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
	<td>Resource Type :</td>
	<td></td>
	</tr>
	
      <% for(int i=1; i<resName.length; i++) {
	  %>
	  <tr height="30">
	  	<td width="400">
		
	  	<% if(res == i) {
	  	%>

	  	<input name="rbtnRes" type="radio" value=<%=i%> checked>
	  	<% res = 0;
	  	}else {
	  	%>

	  	<input name="rbtnRes" type="radio" value=<%=i%>>
	  	<% }
	  	%>
	  	<%=resName[i]%></td>
		<% if(i == resName.length-1) { 
		%>
		<td width="178"><input type="button" value="<%= trans.tslt("Show") %>" name="btnShow" onclick="proceed(this.form, this.form.rbtnRes)"></td>
    	<% 
			} else {
		%>
		<td width="178">&nbsp;</td>
    	<%
			}
		%>
	</tr>
		<% } %>
   
</table>
  
<p></p>


<div style="width:720px; height:300px; z-index:1; overflow:auto;"> 
<table width="700" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
<th width="26" bgcolor="navy">&nbsp;

</th>

<th bgcolor="navy" align="center" width="658"><b>
<font style='font-family:Arial;color:white'><%= trans.tslt("Development Activities/Resources") %></font></b></th>
<% 	
	/*String arr[] = {"Completion of activity or feedback from supervisor and peers", "Book summary or book review discussion with supervisor", "Course completion certificate"};
	String resources[] = {"Identify effective decision makers to 'bounce ideas off' prior to finalizing a decision.",
	 					"Innovation at Work, Katherine E. Holt, Ph.D, Publisher: ASTD, March 2003, ISBN: 1-56286-351-7",
						"Creating and Marketing Innovative Products: From Concept to Commercialisation, www.nus.edu.sg/nex"};
	String competency[] = {"Decision Making (Problem Solving)",
	 					"Innovation",
						"Innovation"};
	String completion []= {"18 Oct 2007",
	 					"&nbsp;",
						"&nbsp;"};
	String timeframe[]= {"18 Oct 2007",
	 					"31 Oct 2007",
						"02 Dec 2007"};*/
	
	
	for(int i=0; i<vRes.size(); i++) {
		String arr[] = (String[])vRes.elementAt(i);
		
		ResID = Integer.parseInt(arr[0]);
		String Resource = arr[1];
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFCC'">
       <td align="center">
	   		<input type="radio" name="radioRes" value=<%=ResID%>><font size="2">
	   </td>
	  
	   <td align="left">
		<%=Resource%>
      </td>
   </tr>
<% 	DisplayNo++;
	} 
	
	if(vRes.size() == 0) {
%>
		 <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFCC'">
     
	   <td colspan="2" align="center">
		No Resource available
      </td>
   </tr>
 <% 
	}
	
%>
</table>
</div>

<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	
	 <tr>
      <td width="175">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="386">&nbsp;</td>

    </tr>
    <tr>
      <td><%= trans.tslt("Proposed Completion Date") %></td>
      <td>:</td>
      <td><input type=text size="15" name="ProposedDate">
	  <img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onclick="javascript:showCal('ProposedCompletionDate')">
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
      <td><input type=text size="15" name="CompletionDate">
	  <img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onclick="javascript:showCal('CompletionDate')">
  
	  </td>

    </tr>	
</table>

<p></p>
<input type="button" name="Add" value="<%= trans.tslt("Submit") %>" onClick="confirmAdd(this.form, this.form.radioRes)">
<input type="button" name="btnCancel" value="<%= trans.tslt("Cancel") %>"  onclick = "window.close()">
<%

}
%>
<p></p>
<div align="center">
  <%@ include file="Footer.jsp"%>
</div>
</body>
</html>