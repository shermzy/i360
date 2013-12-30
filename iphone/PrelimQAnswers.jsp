<%@ page import="java.sql.*,
				java.util.*,
                 java.io.*,
                 java.text.*, 
                java.util.Date"%>  

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="PrelimQuestionBean" class="CP_Classes.PrelimQuestionBean" scope="session"/>  
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.PrelimQuestion"%>
<%@ page import="CP_Classes.PrelimQuestionAns"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Preliminary Answer - iPhone Version</title> 
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
</head>

<SCRIPT LANGUAGE=JAVASCRIPT>
function Add(form, qn){
		form.action="PrelimQAnswers.jsp?save=2&add="+qn;
		form.method="post";
		form.submit();
}

function Save(form){
		form.action="PrelimQAnswers.jsp?save=1";
		form.method="post";
		form.submit();
}

function Prev(form)
{
	form.action="RatersToDoList.jsp";
	form.method="post";
	form.submit();
}

function Finish(form){
		form.action="PrelimQAnswers.jsp?save=2&finish=1";
		form.method="post";
		form.submit();
}
</Script>
<body>
<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<% 	}
  
  int surveyID = RDE.getSurveyID();	
  int raterID = RDE.getRaterID();
  int assignmentID = Questionnaire.getAssignmentID();
  SimpleDateFormat PrelimQADate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
  Date Now = new Date();
  String strDate = PrelimQADate.format(Now);
	    
  Vector<PrelimQuestion> v = PrelimQController.getQuestions(surveyID);

	if(request.getParameter("entry")!=null)
	{
		PrelimQuestionBean.resetAnswerCountList();
		for(int y=0;y<v.size();y++)
		{
			PrelimQuestionBean.addQuestionToAnswerCountList();
		}
	}
	
	if(request.getParameter("save") != null){
		ArrayList<Integer> AL = PrelimQuestionBean.getAnswerCountList();
		for (int u=0;u<v.size();u++){ //iterate through the question list and check for the answers and save them
			PrelimQuestion prelimQn = v.get(u);
			
			int prelimRatingScaleID = PrelimQController.getPrelimRatingID(prelimQn.getPrelimQnId());
			if(prelimRatingScaleID == -1){//not using rating scale
				//get the answers count for this particular question
				for(int z=0;z<AL.get(u);z++)
				{
					//iterate through the number of answers for this question and save them all 
					if(request.getParameter("qid"+prelimQn.getPrelimQnId()+"aid"+z) != null)
					{
						int answerId = Integer.parseInt(request.getParameter("qid"+prelimQn.getPrelimQnId()+"aid"+z));
	
						
						
						String answer = request.getParameter("qid"+prelimQn.getPrelimQnId()+"ans"+z);
						String substring = "";
						String finalans = "";
						int i = answer.length();
						int a = 0;
						int b = 0;
						int j = 0;
						int unidec = 0;
						char indec = ' ';
						String hex = "";
						char c = ' ';
						char firstchar = answer.charAt(0);
						while (b<i){
							if (answer.charAt(b) == '&' && answer.charAt(b+1) == '#'){
								for(a=0;a<10;a++){
									if (answer.charAt(b+a) == ';') {
										substring = answer.substring(b+2, b+a);
										//System.out.println(substring);
										unidec = Integer.parseInt(substring);
										hex = Integer.toHexString(unidec);
										indec = (char) Integer.parseInt(hex, 16);
										//System.out.println(indec);
										finalans = finalans + indec;
										b=b+a+1;
										break;
									}
								}
							}else{
								finalans = finalans + answer.charAt(b);
								b++;
							}					
						}//end of while loop		
					    answer = finalans;
						if(!answer.equals("")){
							if(answerId==0){ //answer is a newly added answer
								//insert the answer into the database
								PrelimQController.saveAnswer(prelimQn.getPrelimQnId(), answer, assignmentID, raterID, strDate);
							    //System.out.println("when in jsp" + answer);  							
							}else{
								//update the answer value into the database
								PrelimQController.updateAnswer(answerId, prelimQn.getPrelimQnId(), answer, assignmentID, raterID, strDate);
							}
						}
					}
					
				}
			} else{//use rating scale
				if(request.getParameter("ratingRadioAns"+prelimQn.getPrelimQnId())!= null){
					String rating = request.getParameter("ratingRadioAns"+prelimQn.getPrelimQnId());
					Vector<PrelimQuestionAns> vp = PrelimQController.getAnswers(prelimQn.getPrelimQnId(), assignmentID, raterID);
					if(vp.size()<1){//answer not chosen yet so need insert new answer
						PrelimQController.saveAnswer(prelimQn.getPrelimQnId(), rating, assignmentID, raterID, strDate);
					}else{//answer chosen before so just update
						PrelimQController.updateAnswer(vp.get(0).getPrelimQnAnsID(), prelimQn.getPrelimQnId(), rating, assignmentID, raterID, strDate);
					}
				}
			}
		}
		
		if(request.getParameter("save").equals("1"))
		{
		%>
		<Script>
		alert("Saved successfully")
		location.href='PrelimQAnswers.jsp?entry=1';
		</Script>
		<%
		}
		else
		{
		
		}
	}
	
	if(request.getParameter("add") != null){
		int posToAdd = Integer.parseInt(request.getParameter("add"));
		PrelimQuestionBean.addAnswer(posToAdd);
		
	}
	
	if(request.getParameter("finish")!=null){
		for (int u=0;u<v.size();u++){ //iterate through the question list and check for the answers and save them
			PrelimQuestion prelimQn = v.get(u);
			int prelimRatingScaleID = PrelimQController.getPrelimRatingID(prelimQn.getPrelimQnId());
			
			if(prelimRatingScaleID == -1){ //not using rating scale so it doesnt matter, do nothing
		
			}else{ //use rating scale, must check if an answer is chosen
				Vector<PrelimQuestionAns> vAns = PrelimQController.getAnswers(prelimQn.getPrelimQnId(),assignmentID,raterID);
				if(vAns.size() < 1){
					%>
					<script language="javascript">
					alert("<%=trans.tslt("Please finish all the preliminary questions before moving to the next step.")%>");
					window.location.href = "PrelimQAnswers.jsp?entry=1";
					</script>
					<%
				}
			}
		}
		%>
		<script language="javascript">
		alert("<%=trans.tslt("Proceeding to Questionnaire")%>");
		window.location.href = "Questionnaire.jsp";
		</script>
		<%		
	}
  
  %>
  <div class="toolbar" style='min-width:420px;'> 
	<h1 id="pageTitle"></h1>
	<a title="Rater's To Do List" class="backButton" href="#" onclick="window.location.href='RatersToDoList.jsp'">To Do List</a>
	<a title="Log out" class="logoutButton" href="#" onclick="if(!confirm('Please make sure you have saved your ratings. Click \'OK\' to log off; Click \'Cancel\' to continue your rating.')){return false;};window.location.href='login.jsp?logout=1'">Exit</a>
</div>
<div id="questionnaire" class="panel" title="Questionnaire" selected='true' style='min-width:420px;'>
  <form name="PrelimQAnswers" action="PrelimQAnswers.jsp" method="post" accept-charset="ISO-8859-1">
  
  
  <br/>
  <div style="width:590px">
  <font face="Verdana" style="font-size:13px">
<%=PrelimQController.getPrelimQnHeader(surveyID)%>
</font>
</div>
<br/>


<%
//iterate through the questions list
for(int i=0;i<v.size();i++){
	//get the prelim question currently in the list
	PrelimQuestion pq = v.get(i);
	
	Vector<PrelimQuestionAns> vp = PrelimQController.getAnswers(pq.getPrelimQnId(), assignmentID, raterID);
	out.println("<div style=\"width:600px\">");
	out.println("<table border=1 bordercolor=\"#3399FF\" width=\"600px\">");
	out.println("<tr>");
	out.println("<td bgcolor=\"#000080\">");
	out.println("<font color=\"white\"  face=\"Verdana\" style=\"font-weight: 700\" size=\"2\">");
	out.println(pq.getQuestion());
	out.println("</font>");
	out.println("</td>");
	out.println("</tr>");
	
	//We need to check if the particular question have a rating scale ID, if have then answer should be listed in bullet points
	//if there is no rating scale ID (-1) then answers should be listed out in text boxes
	int prelimRatingScaleID = PrelimQController.getPrelimRatingID(pq.getPrelimQnId());
	if(prelimRatingScaleID == -1){
		if(vp.size()>0||PrelimQuestionBean.getAnswerCount(i)>0){
			if(vp.size()>PrelimQuestionBean.getAnswerCount(i)){
				PrelimQuestionBean.setAnswerCount(i, vp.size());
			}
			for(int x=0; x<PrelimQuestionBean.getAnswerCount(i);x++){
				out.println("<tr>");
				out.println("<td bgcolor=\"#FFFFCC\">");
				out.println(x+1+") ");
				if(x<vp.size()){ //check if the answer is saved already, retrieve the answer from db
					PrelimQuestionAns pqa = vp.get(x);
					out.println("<input type=\"hidden\" name=qid"+pq.getPrelimQnId()+"aid"+x+" id=qid"+pq.getPrelimQnId()+"aid"+x+" value="+pqa.getPrelimQnAnsID()+" />");
					out.println("<textarea rows=2 cols=65 name=qid"+pq.getPrelimQnId()+"ans"+x+" id=qid"+pq.getPrelimQnId()+"ans"+x+">"+pqa.getAnswer()+"</textarea>");
				}else{			
					out.println("<input type=\"hidden\" name=qid"+pq.getPrelimQnId()+"aid"+x+" id=qid"+pq.getPrelimQnId()+"aid"+x+" value=0 />");
					out.println("<textarea rows=2 cols=65 name=qid"+pq.getPrelimQnId()+"ans"+x+" id=qid"+pq.getPrelimQnId()+"ans"+x+"></textarea>");
				}
				
				out.println("</td>");
				out.println("</tr>");
				
				out.println("<tr>");
				out.println("<td bgcolor=\"#FFFFCC\">");
				out.println("&nbsp;");
				out.println("</td>");
				out.println("</tr>");		
			}
		}else{
			PrelimQuestionBean.addAnswer(i);
			out.println("<tr>");
			out.println("<td bgcolor=\"#FFFFCC\">");
			out.println("1 ) ");
			out.println("<input type=\"hidden\" name=qid"+pq.getPrelimQnId()+"aid0 id=qid"+pq.getPrelimQnId()+"aid0 value=0 />");
			out.println("<textarea rows=2 cols=65 name=qid"+pq.getPrelimQnId()+"ans0 id=qid"+pq.getPrelimQnId()+"ans0></textarea>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td bgcolor=\"#FFFFCC\">");
			out.println("&nbsp;");
			out.println("</td>");
			out.println("</tr>");
		}
		
		
		out.println("</table>");
		out.println("</div>");
		out.println("<INPUT type=button value=Add Answer onclick=\"Add(this.form, "+i+")\"/>");
		out.println("<br/>");
		out.println("<br/>");
	}else{ //there is a rating scale
		Vector ratingList = PrelimQController.getAllRatingInVector(prelimRatingScaleID);
		if(vp.size()<1){//no answer chosen yet
			for(int k=0; k<ratingList.size(); k++){
				String rating = (String) ratingList.get(k);
				out.println("<tr>");
				out.println("<td bgcolor=\"#FFFFCC\">");
				out.println("<input type=radio name=ratingRadioAns"+pq.getPrelimQnId()+" value="+rating.replaceAll(" ","")+">"+rating);
				out.println("</td>");
				out.println("</tr>");
			}
		}else{ //answer chosen before
			String ansBefore = vp.get(0).getAnswer();
			for(int k=0; k<ratingList.size(); k++){
				String rating = (String) ratingList.get(k);
				if(ansBefore.equalsIgnoreCase(rating.replaceAll(" ",""))){
					out.println("<tr>");
					out.println("<td bgcolor=\"#FFFFCC\">");
					out.println("<input type=radio name=ratingRadioAns"+pq.getPrelimQnId()+" value="+rating.replaceAll(" ","")+" checked>"+rating);
					out.println("</td>");
					out.println("</tr>");
				}else{
					out.println("<tr>");
					out.println("<td bgcolor=\"#FFFFCC\">");
					out.println("<input type=radio name=ratingRadioAns"+pq.getPrelimQnId()+" value="+rating.replaceAll(" ","")+">"+rating);
					out.println("</td>");
					out.println("</tr>");
				}
			}		
		}
		out.println("</table>");
		out.println("</div>");
		out.println("<br/>");
	}
}

%>



<INPUT type="button" value="Prev" onclick="Prev(this.form)"/> 
<INPUT type="button" value="Save" onclick="Save(this.form)"/> 
<INPUT type="button" value="Finish" onclick="Finish(this.form)"/> 
</form>
</div>
</body>
</html>