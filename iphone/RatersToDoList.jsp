<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>
<jsp:useBean id="RTDL" class="CP_Classes.RatersToDoList" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="DemographicEntry" class="CP_Classes.DemographicEntry" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>

<%@ page import="CP_Classes.PrelimQuestion"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
	<link rel=apple-touch-icon href="icon.png" />
	<link rel="shortcut icon" href="icon.png" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Rater's To Do List - iPhone Version</title> 
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
</head>
<body>
<div class="toolbar"> 
	<h1 id="pageTitle"></h1>
	<a class="backButton" href="#" onclick="window.location.href='surveyIndex.jsp'">Welcome</a>
	<a title="Log out" class="logoutButton" href="#" onclick="window.location.href='login.jsp?logout=1'">Exit</a>
</div>
<ul id="todoList" title="Rater's To Do List" selected="true">
	<li>
		<ul style='padding-left:15px;'><li>To Open a survey, click on the Survey Name.</li></ul>
	</li>
	<%
		String username=(String)session.getAttribute("username");
		if (!logchk.isUsable(username)){
	%> 
		<script>
			window.location.href = "index.jsp";
		</script>
	<%}%>
	<%
	if(request.getParameter("open") != null) {
		String param = request.getParameter("open");
		if(param.indexOf("#")>=0) param = param.substring(0,param.length()-1); 
		int asgtID = Integer.parseInt(param);		
		int info [] = RTDL.assignmentInfo(asgtID);
		
		RDE.setSurveyID(info[0]);
		RDE.setTargetID(info[1]);
		RDE.setRaterID(info[2]);
		Vector<PrelimQuestion> v = PrelimQController.getQuestions(RDE.getSurveyID());
		if(v.size()==0){
	%>
	<jsp:forward page="Questionnaire.jsp" />
	<%  }else{
%>
		<jsp:forward page="PrelimQAnswers.jsp?entry=1" />
<%
	    }
	}%>
	<%
	int raterID = logchk.getPKUser();
	Vector vList = RTDL.getToDoList(raterID);
	for(int i=0; i<vList.size(); i++) {		
		String [] sToDoList = new String[7];
		sToDoList = (String[])vList.elementAt(i);
		
		int asgtID = Integer.parseInt(sToDoList[0]);	
		String surveyName = sToDoList[1];
		String name = sToDoList[2];
		String deadline = sToDoList[3];
		String RT = sToDoList[4];
		int surveyStatus = Integer.parseInt(sToDoList[5]);
		String status = "";
		switch(surveyStatus) {
			case 0 : status = "N/A";break;		
			case 1 : status = "Open";break;
			case 2 : status = "Closed";break;
			case 3 : status = "Not Commissioned / NC";break;
		}
		int raterStatus = Integer.parseInt(sToDoList[6]);
		String rStatus = "";
		switch(raterStatus) {
			case 0 : rStatus = "Incomplete";break;		
		}
		if(surveyStatus != 1) {
		%>
		<li style='background-color:#DCDCDC;border-bottom:1px solid #808080;'><b><%=i+1%>.&nbsp;<%=surveyName%></b></li>
		<%
		}else{
		%>
		<li style='background-color:#6495ED;border-bottom:1px solid #483D8B;'><a href="javascript:window.location.href='RatersToDoList.jsp?open=<%=asgtID%>'"><%=i+1%>.&nbsp;<%=surveyName%></a></li>
		<%}%>
		<li>
			<p>
				<i>Target Name:</i>&nbsp;<b><%=name%></b><br>
				<i>Deadline:</i>&nbsp;<b><%=deadline%></b><br>
				<i>Relation to the Target:</i>&nbsp;<b><%=RT%></b><br>
				<i>Survey Status:</i>&nbsp;<b><%=status%></b><br>
				<i>Assignment Status:</i>&nbsp;<b><%=rStatus%></b>
			</p>
		</li>
	<%
	}
	%>
</ul>
</body>
</html>