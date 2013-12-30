<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import = "java.lang.*" %>

<html>
<head>
<title>Target Questionnaire</title>

<meta http-equiv="Content-Type" content="text/html">

</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;

function getID(form, ID, type)
{
	var typeSelected = "";
	
	if(ID != 0) {
		switch(type) {
			case 1: typeSelected = "surveyID";
					  break;
			case 2: typeSelected = "groupID";
					  break;
			case 3: typeSelected = "targetID";
					  break;
			case 4: typeSelected = "raterID";
					  break;
		}
		var query = "TargetQuestionnaire.jsp?" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
	} else {
		alert("<%=trans.tslt("Please select the options")%> !");
		form.action = "TargetQuestionnaire.jsp";
		form.submit();
		return false;
	}
	return true;	
}

function confirmOpen(form, raterID)
{
	if(raterID != 0) {
		var myWindow=window.open('OpenQuestionnaire.jsp','windowRef','scrollbars=no, width=400, height=400');
		myWindow.moveTo(x,y);
    	myWindow.location.href = 'OpenQuestionnaire.jsp';	
	}else {
		alert("<%=trans.tslt("Please select the options")%> !");
		form.action = "TargetQuestionnaire.jsp";
		form.submit();
		return false;
	}
	return true;	
}
</script>

<p>
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
	int pkUser = logchk.getPKUser();
	
	ResultSet surveyList = RDE.getSurvey();
	ResultSet groupList = RDE.getGroup();
	ResultSet targetList = surveyList;
	ResultSet raterList = surveyList;

	int surveyID = RDE.getSurveyID();
	
	if(surveyID != 0)
		targetList = RDE.getTarget(surveyID);
	
	int target = RDE.getTargetID();
	if(target != 0)
		raterList = RDE.getRater(surveyID, target);
		
		
	if(request.getParameter("surveyID") != null) {
		int ID = Integer.parseInt(request.getParameter("surveyID"));
		RDE.setSurveyID(ID);	
		RDE.setPageLoad(1);	
	}
	else if(request.getParameter("groupID") != null) {
		String groupID = request.getParameter("groupID");
		RDE.setGroupID(Integer.parseInt(groupID));		
	} 
	else if(request.getParameter("targetID") != null) {
		int ID = RDE.getSurveyID();
		int targetID = Integer.parseInt(request.getParameter("targetID"));
		RDE.setTargetID(targetID);
		raterList = RDE.getRater(ID, targetID);
	}else if(request.getParameter("raterID") != null) {
		String raterID = request.getParameter("raterID");
		RDE.setRaterID(Integer.parseInt(raterID));
	}
%>
</p>
<form name="RatersDataEntry" method="post" action="">
  <table width="200" border="0" font style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td><%=trans.tslt("Survey's Name")%> : </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <% int t = 0; %>
	  <td>
	  <select name="surveyName" onchange = "getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 1)">
	  <option value=<%=t%>>Please select one
	  <% while(surveyList.next()) {
	  		int ID = surveyList.getInt(1);
			String name = surveyList.getString(2);
			int selectedSurvey = RDE.getSurveyID();
			
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
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><%=trans.tslt("Group Name")%> : </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
	  <td><select name="groupName" onchange="getID(this.form, this.form.groupName.options[groupName.selectedIndex].value, 2)">
	  <option value=<%=t%>>Please select one
	  <% while(groupList.next()) {
	  		int ID = groupList.getInt(1);
			String name = groupList.getString(3);
			int selectedGroup = RDE.getGroupID();
			
			if(selectedGroup != 0 && ID == selectedGroup) {
	  %>
	  	<option value = <%=ID%> selected><%=name%>
		<% } else { %>
		<option value = <%=ID%>><%=name%>	  
		<% }
			} %>
      </select></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><%=trans.tslt("Target's Name")%> : </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
	  <td><select name="targetName" onchange="getID(this.form, this.form.targetName.options[targetName.selectedIndex].value, 3)">
	  <option value=<%=t%>>Please select one
	  <% while(targetList.next()) {
			int loginID = targetList.getInt(1);
			String familyName = targetList.getString(2);
			String givenName = targetList.getString(3);
			
			int selectedTarget = RDE.getTargetID();
			
			if(loginID == selectedTarget) {
	  %>
	  <option value = <%=loginID%> selected><%=givenName + "-" + familyName%>	
	 <% } else { %>
	  	<option value = <%=loginID%>><%=givenName + "-" + familyName%>	  
		<% }
			} %>
      </select></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><%=trans.tslt("Rater's Name")%> : </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
	  <td><select name="raterName" onchange="getID(this.form, this.form.raterName.options[raterName.selectedIndex].value, 4)">
	  <option value=<%=t%>>Please select one
	  <% while(raterList.next()) {
			int loginID = raterList.getInt(1);
			String familyName = raterList.getString(2);
			String givenName = raterList.getString(3);
			
			int selectedTarget = RDE.getRaterID();
			
			if(loginID == selectedTarget) {
	  %>
	  <option value = <%=loginID%> selected><%=givenName + "-" + familyName%>	
	 <% } else { %>
	  	<option value = <%=loginID%>><%=givenName + "-" + familyName%>	  
		<% } 
		} %>
      </select></td>
    </tr>
  </table>
  
  <p></p>
  <p>
    <input type="submit" name="btnOpen" value="<%=trans.tslt("Open Questionnaire")%>" onclick = "confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value)">
    <input type="submit" name="btnClose" value="<%=trans.tslt("Close")%>">
</p>
</form>
<% } %>
<p>&nbsp; </p>
</body>
</html>
