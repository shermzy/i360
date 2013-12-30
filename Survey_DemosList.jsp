
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CompetencyQuery" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SD" class="CP_Classes.SurveyDemo" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurveyDemos"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Survey - Demographics</title>
</head>
  <SCRIPT LANGUAGE="JavaScript">
function check(field)
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
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("No record selected");
	else if(isValid == 2)
		alert("No record available");
	
	isValid = 0;	
	
}

function closeWindow()
{
	window.close();
}
function ConfirmAdd(form,field) 
{
	if(check(field))
	{
		// Edited by Eric Lu 22/5/08
		// Displays confirm box when adding demographics
		if (confirm("Add Demographics?")) {
			form.action = "Survey_DemosList.jsp?add=1"
			form.method = "post";
			form.submit();
		}
	}
	
}


</script>
<body bgcolor="#DEE3EF">
<%	
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  {
  	
	if(request.getParameter("add") != null)
	{
		String [] chkSelect = new String[10];
		int SurveyID = CE_Survey.getSurvey_ID();
 	    chkSelect = request.getParameterValues("chkDemo");
 	    
 	    // Edited by Eric Lu 22/5/08
 	    // Added boolean to determine whether demographics are added successfully
 	    // If successful, confirm box with successful message pops up
 	    // Else, confirm box with unsuccessful message pops up
 	    boolean bDemosAdded = true;
 	    
		try
		{
			if (!CE_Survey.addDemos(SurveyID,chkSelect))
				bDemosAdded = false;	
		}
		catch(SQLException sqle)
		{
			bDemosAdded = false;
		}
 
 		if (bDemosAdded) {
 			%>
 				<script>
 					alert("Added successfully");
 				</script>
 			<%
 		} else {
 			%>
 				<script>
 					alert("Added unsuccessfully");
 				</script>
 			<%
 		}

%>					
<script>window.close()
 		opener.location.href = 'SurveyDemos.jsp';</script>
<%
	}
%>
<form name="Survey_DemosList" method="post" action="Survey_DemosList.jsp">
<div style="width:169px; height:272px; z-index:1; overflow:auto"> 
<table border="1" bordercolor="#3399FF" width="160">

<th bgcolor="navy" colspan="2">
	<font size="2">
   
    	<span style="font-family: Arial; color: #FFFFFF"><%=trans.tslt("Demographic Option")%> </span></th>
<%
	Vector v = SD.getDemoNotAssigned(CE_Survey.getSurvey_ID());
	
	for(int i=0; i<v.size(); i++)
	{
		votblSurveyDemos vo = (votblSurveyDemos)v.elementAt(i);
		int DemoID = vo.getDemographicID();
		String DemoName = vo.getDemographicName();
				
%>		
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFFF'">
       <td bgcolor="#FFFFCC" bordercolor="#3399FF" width="20">
	   		<font face="Arial" span style="font-size: 11.0pt; font-family: Arial">
	   		<input type="checkbox" name="chkDemo" value=<%=DemoID%>></font><font span style='font-family:Arial' size="2">
            </font>
	   </td>
	   <td bgcolor="#FFFFCC" bordercolor="#3399FF" width="124">
           <font span style='font-family:Arial' size="2"><%= DemoName %></font><font size="2" face="Arial">&nbsp;
	   	</font>
	   </td>
   </tr>
<%	}	%>
</table>
</div>
<table border="0" width="75%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="210">
<input type="button" name="Add" value="<%=trans.tslt("Add")%>" onclick="ConfirmAdd(this.form,this.form.chkDemo)"></td>
		<td>
		<p align="center">
		<input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onclick="closeWindow()" style="float: right"></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>
