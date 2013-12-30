<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import="java.util.* "%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Questionnaire</title>

<meta http-equiv="Content-Type" content="text/html">

</head>

<body>

<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>

<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } else{

	int surveyID = RDE.getSurveyID();
	int targetID = RDE.getTargetID();
	int raterID = RDE.getRaterID();
	int orgID = logchk.getOrg();	
	
	int assignmentID = Questionnaire.AssignmentID(surveyID, targetID, raterID);
	Questionnaire.setAssignmentID(assignmentID);
	
	String timeFrame = Questionnaire.TimeFrame(surveyID);
	Questionnaire.setTimeFrame(timeFrame);
	
	int TotalCurrComp = 0;
	Questionnaire.setTotalCurrComp(TotalCurrComp);
	
	
	String name = Questionnaire.UserName(orgID, targetID);	
	String jobPost = RDE.getJobPosition(surveyID);

	Questionnaire.setName(name);
	Questionnaire.setJobPost(jobPost);
	
	String futureJob = Questionnaire.FutureJob(surveyID);
	Questionnaire.setFutureJob(futureJob);
		
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);
	Questionnaire.setSurveyLevel(surveyLevel);
			
	int totalComp = Questionnaire.TotalList(surveyID,0);		// total = total Competency = total questions
	Questionnaire.setTotalComp(totalComp);
	

	Vector vSurveyRating = Questionnaire.getSurveyRating(surveyID);
	int totalRatingTask = Questionnaire.getTotalSurveyRating(surveyID);		// total rating task for the specific survey
	int ratingTaskID[] = new int[totalRatingTask];
	int ratingScaleID[] = new int[totalRatingTask];
	int id = 0;
	
	for(int i=0; i<vSurveyRating.size(); i++) {
		int [] ids = new int [2];
		ids = (int[])vSurveyRating.elementAt(i);
	
		ratingTaskID[id] = ids[0];		// store all the ratings ID in an array
		ratingScaleID[id] = ids[1];
		id++;
	}
	
	Questionnaire.setRT(ratingTaskID);
	Questionnaire.setRS(ratingScaleID);
	Questionnaire.setStartID(1);

	
	if(!AddQController.getQuestionnaireHeader(surveyID).equals(""))
	{
	%>
	<jsp:forward page="QuestionnaireInstructions.jsp" />

	<% 
	}
	else
	{
		%>
		<jsp:forward page="Questionnaires.jsp" />
		<%
	}


}%>
</body>
</html>
