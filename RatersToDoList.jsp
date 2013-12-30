<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  
				 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rater's To Do List</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<jsp:useBean id="RTDL" class="CP_Classes.RatersToDoList" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="DemographicEntry" class="CP_Classes.DemographicEntry" scope="session"/>
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>

<%@ page import="CP_Classes.PrelimQuestion"%>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;

function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	
	for (i = 0; i < field.length; i++) 
		if(field[i].checked) {		
			clickedValue = field[i].value;
			field[i].checked = false;
			isValid = 1;
		}
		
	if(isValid == 0 && field != null)  {
		if(field.checked) {
			clickedValue = field.value;
			isValid = 1;
		}
	}
	
	if (isValid == 1)
		return clickedValue;
	else
		alert("<%=trans.tslt("No record selected")%>!");
		
	isValid = 0;	
	
}

function confirmOpen(form, field)
{
	var value = check(field);
	
	if(value)
	{		
		form.action="RatersToDoList.jsp?open=" + value;
		form.method="post";
		form.submit();
	}
	
} 

</script>


<body>
<%
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
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
	int toggle = RTDL.getToggle();	//0=asc, 1=desc
	int type = 7; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		RTDL.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		RTDL.setSortType(type);
	} 
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/	
	
	
	int raterID = logchk.getPKUser();
	
	if(request.getParameter("open") != null) {
		int asgtID = Integer.parseInt(request.getParameter("open"));		
		int info [] = RTDL.assignmentInfo(asgtID);
		
		RDE.setSurveyID(info[0]);
		RDE.setTargetID(info[1]);
		RDE.setRaterID(info[2]);
		Questionnaire.setAssignmentID(asgtID);
		Vector<PrelimQuestion> v = PrelimQController.getQuestions(RDE.getSurveyID());
		// If any demographic is chosen, forward to demo page. Otherwise, proceed into Questionnaire
		if(DemographicEntry.bDemographicSelected(info[0])){
%>			<script>
				window.location.href = "DemographicEntry.jsp";
			</script>
<%		}
		else if(v.size() > 0){
%>			<script>
			window.location.href = "PrelimQAnswers.jsp?entry=1";
			</script>
<%		}
		else{
%>			<script>
				window.location.href = "Questionnaire.jsp";
			</script>
<%		}
	}
	//System.out.println("RaterID = " + raterID);
	Vector vList = RTDL.getToDoList(raterID);
%>
<form action="RatersToDoList.jsp" method="post" name="RatersToDoList">
<table border="0" width="592" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Rater's To Do List")%> </font></b></td>
	  <td>&nbsp;</td>
    </tr>	
	<tr>
	  <td colspan="4">&nbsp;</td>
    </tr>
	<tr>
	  <td colspan="4"><ul>
          <li><font face="Arial" size="2"><%=trans.tslt("To Open a survey, click on the Survey Name")%>. </font></li>
          </ul></td>
    </tr>
  </table>
<div style='width:610px; height:50%; z-index:1; overflow:auto;'> 
<table border="1" bordercolorlight = "#3399FF" bgcolor="#FFFFCC" width="593" font style='font-size:10.0pt;font-family:Arial'>
<tr>
<td colspan="8" align="center" bgcolor="navy" font style='font-size:10.0pt;font-family:Arial;color:white'><b><%=trans.tslt("Survey's Assignment")%></b></td>
</tr>
<th width="20" align="center" bgcolor="navy"><b><font style='font-size:10.0pt;font-family:Arial;color:white'>No</font></b></th>
<th width="300" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=1"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Survey Name")%></u></font></b></a></th>
<th width="100" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=2"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Target Name")%></u></font></b></a></th>
<th width="100" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=3"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Deadline")%></u></font></b></a></th>
<%//Changed by Alvis on 06-Aug-09: Header changed from "Relation" to "Your Relation to the Target"%>
<th width="100" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=4"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Your Relation to the Target")%></u></font></b></a></th>
<th width="100" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=5"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Survey Status")%></u></font></b></a></th>
<th width="100" align="center" bgcolor="navy"><a href="RatersToDoList.jsp?name=6"><b><font style='font-size:10.0pt;font-family:Arial;color:white'><u><%=trans.tslt("Assignment Status")%></u></font></b></a></th>

<%
	for(int i=0; i<vList.size(); i++) {		
		String [] sToDoList = new String[7];
		sToDoList = (String[])vList.elementAt(i);
		
		int asgtID = Integer.parseInt(sToDoList[0]);	
		String surveyName = sToDoList[1];
		String name = sToDoList[2];
		String deadline = sToDoList[3];
		String RT = sToDoList[4];
		int surveyStatus = Integer.parseInt(sToDoList[5]);
%>
<tr>

	<td align="center"><%=i+1%></td>
<%
	if(surveyStatus != 1) {
%>
	<td><a style="color:black;"><%=surveyName%></a></td>
<%
	}else {
%>	
    <td><a style="color:blue;" href="RatersToDoList.jsp?open=<%=asgtID%>"><%=surveyName%></a></td>
<% } %>
    <td><%=name%></td>
    <td align="center"><%=deadline%></td>
    <td align="center"><%=RT%></td>
<%
	
	String status = "";
	switch(surveyStatus) {
		case 0 : status = "N/A";
				 break;		
		case 1 : status = "Open";
				 break;
		case 2 : status = "Closed";
				 break;
		case 3 : status = "Not Commissioned / NC";
				 break;
	}
%>	
	<td align="center"><%=status%></td>
	
<%
	int raterStatus = Integer.parseInt(sToDoList[6]);
	String rStatus = "";
	switch(raterStatus) {
		case 0 : rStatus = "Incomplete";
				 break;		
	}
%>		
	<td align="center"><%=rStatus%></td>
</tr>

<% } %>
</table>
</div>
<p></p>
</form>
<% } %>
</body>
</html>

