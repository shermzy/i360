<%@ page import="java.sql.*,
				java.util.*,
                 java.io.*,
                 java.text.*,
                 java.util.Date "%>  
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="QuestionBean" class="CP_Classes.AdditionalQuestionBean" scope="session"/>
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.AdditionalQuestion"%>
<%@ page import="CP_Classes.AdditionalQuestionAns"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Additional Answer - iPhone Version</title> 
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
</head>

<SCRIPT LANGUAGE=JAVASCRIPT>
function Add(form, qn)
{

		form.action="AdditionalAnswers.jsp?save=2&add="+qn;
		form.method="post";
		form.submit();
}

function Save(form)
{

		form.action="AdditionalAnswers.jsp?save=1";
		form.method="post";
		form.submit();
	
}

function Prev(form)
{
	form.action="Questionnaire.jsp";
	form.method="post";
	form.submit();
}

function Finish(form)
{
	if(confirm("<%=trans.tslt("Once you click on the OK button, no more amendments can be made")%>."))
		{
			form.action="AdditionalAnswers.jsp?save=2&finish=1";
			form.method="post";
			form.submit();
		}
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
  SimpleDateFormat AddQADate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
  Date Now = new Date();
  String strDate = AddQADate.format(Now);
  //int surveyID = 529;
  Vector<AdditionalQuestion> v = AddQController.getQuestions(surveyID);
  
	
	if(request.getParameter("entry")!=null)
	{
		QuestionBean.resetAnswerCountList();
		for(int y=0;y<v.size();y++)
		{
			QuestionBean.addQuestionToAnswerCountList();
		}
	}
	
	if(request.getParameter("save") != null)
	{
		ArrayList<Integer> AL = QuestionBean.getAnswerCountList();
		for (int u=0;u<v.size();u++) //iterate through the question list and check for the answers and save them
		{
			AdditionalQuestion addqn = v.get(u);
			
			//get the answers count for this particular question
			for(int z=0;z<AL.get(u);z++)
			{
				//iterate through the number of answers for this question and save them all 
				if(request.getParameter("qid"+addqn.getAddQnId()+"aid"+z) != null)
				{
					int answerId = Integer.parseInt(request.getParameter("qid"+addqn.getAddQnId()+"aid"+z));
					String answer = request.getParameter("qid"+addqn.getAddQnId()+"ans"+z);
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
					//char firstchar = answer.charAt(0);
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
						}else
								{finalans = finalans + answer.charAt(b);
								b++;
								}					
						
						
					}		
				
				
				    answer = finalans;
					
					
					
					if(!answer.equals(""))
					{
						if(answerId==0)//answer is a newly added answer
						{
							//insert the answer into the database
									                    
							AddQController.saveAnswer(addqn.getAddQnId(), answer, assignmentID, raterID, strDate);
						    //System.out.println("when in jsp" + answer);  							
						}
						else
						{
							//update the answer value into the database
							AddQController.updateAnswer(answerId, addqn.getAddQnId(), answer, assignmentID, raterID, strDate);
						
						}
					}
				}
				
			}
		}
		
		if(request.getParameter("save").equals("1"))
		{
		%>
		<Script>
		alert("Saved successfully")
		location.href='AdditionalAnswers.jsp?entry=1';
		</Script>
		<%
		}
		else
		{
		
		}
	}
	
	if(request.getParameter("add") != null)
	{
		int posToAdd = Integer.parseInt(request.getParameter("add"));
		QuestionBean.addAnswer(posToAdd);
	}
	
	if(request.getParameter("finish")!=null)
	{
		int userType = logchk.getUserType();
		
		Questionnaire.SetRaterStatus(assignmentID, 1);
		
		%>
		<script language="javascript">
		<% // Change from "Thank You" to "Thank you" in below alert as requested by Ms Ros, Desmond 23 Oct 09 %>
		alert("<%=trans.tslt("Thank you for your participation")%>");
		</script>
		<%		
		if(userType == 1 || userType == 2) {
			%>
			<script language="javascript">
					//window.location.href = "SurveyList_Process.jsp";
					window.location.href = "RatersToDoList.jsp";
			</script>
			<%						
						} else if(userType == 3) {
			%>
			<script language="javascript">
					//window.location.href = "RatersDataEntry.jsp";
					window.location.href = "RatersToDoList.jsp";
			</script>
			<%						
						} else if(userType == 4) {		// raters
			%>
			<script language="javascript">
					window.location.href = "RatersToDoList.jsp";
			</script>
			<%
					}
						else
						{
							%>
							<script language="javascript">
									window.location.href = "RatersToDoList.jsp";
							</script>
							<%
						}
	}
  
  %>
  <div class="toolbar" style='min-width:420px;'> 
	<h1 id="pageTitle"></h1>
	<a title="Rater's To Do List" class="backButton" href="#" onclick="window.location.href='RatersToDoList.jsp'">To Do List</a>
	<a title="Log out" class="logoutButton" href="#" onclick="if(!confirm('Please make sure you have saved your ratings. Click \'OK\' to log off; Click \'Cancel\' to continue your rating.')){return false;};window.location.href='login.jsp?logout=1'">Exit</a>
</div>
<div id="questionnaire" class="panel" title="Questionnaire" selected='true' style='min-width:420px;'>
    <form name="AdditionalAnswers" action="AdditionalAnswers.jsp" method="post">
  
  
  <br/>
  <div>
  <font face="Verdana" style="font-size:13px">
<%=AddQController.getAnswerHeader(surveyID)%>
</font>
</div>
<br/>


<%
//iterate through the questions list
for(int i=0;i<v.size();i++)
{
	//get the additional question currently in the list
	AdditionalQuestion aq = v.get(i);
	
	Vector<AdditionalQuestionAns> va = AddQController.getAnswers(aq.getAddQnId(), assignmentID, raterID);
	out.println("<div>");
	out.println("<table border=1 bordercolor=\"#3399FF\" width=\"450px\">");
	out.println("<tr>");
	out.println("<td bgcolor=\"#000080\">");
	out.println("<font color=\"white\"  face=\"Verdana\" style=\"font-weight: 700\" size=\"2\">");
	out.println(aq.getQuestion());
	out.println("</font>");
	out.println("</td>");
	out.println("</tr>");
	//for each question iterate through the answers if there are answers tagged to the question 
	
	if(va.size()>0||QuestionBean.getAnswerCount(i)>0)
	{
		if(va.size()>QuestionBean.getAnswerCount(i))
		{
			QuestionBean.setAnswerCount(i, va.size());
		}
		for(int x=0; x<QuestionBean.getAnswerCount(i);x++)
		{
			
			out.println("<tr>");
			out.println("<td bgcolor=\"#FFFFCC\">");
			out.println(x+1+") ");
			if(x<va.size()) //check if the answer is saved already, retrieve the answer from db
			{
				AdditionalQuestionAns aqa = va.get(x);
				out.println("<input type=\"hidden\" name=qid"+aq.getAddQnId()+"aid"+x+" id=qid"+aq.getAddQnId()+"aid"+x+" value="+aqa.getAddQnAnsID()+" />");
				out.println("<textarea rows=3 cols=65 name=qid"+aq.getAddQnId()+"ans"+x+" id=qid"+aq.getAddQnId()+"ans"+x+">"+aqa.getAnswer()+"</textarea>");
			}
			else
			{
				out.println("<input type=\"hidden\" name=qid"+aq.getAddQnId()+"aid"+x+" id=qid"+aq.getAddQnId()+"aid"+x+" value=0 />");
				out.println("<textarea rows=3 cols=65 name=qid"+aq.getAddQnId()+"ans"+x+" id=qid"+aq.getAddQnId()+"ans"+x+"></textarea>");
			}
			
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td bgcolor=\"#FFFFCC\">");
			out.println("&nbsp;");
			out.println("</td>");
			out.println("</tr>");
			
		}
	}
	else
	{
		QuestionBean.addAnswer(i);
		out.println("<tr>");
		out.println("<td bgcolor=\"#FFFFCC\">");
		out.println("1 ) ");
		out.println("<input type=\"hidden\" name=qid"+aq.getAddQnId()+"aid0 id=qid"+aq.getAddQnId()+"aid0 value=0 />");
		out.println("<textarea rows=3 cols=65 name=qid"+aq.getAddQnId()+"ans0 id=qid"+aq.getAddQnId()+"ans0></textarea>");
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
	out.println("<br/>");
}

%>



<INPUT type="button" value="Prev" onclick="Prev(this.form)"/> 
<INPUT type="button" value="Save" onclick="Save(this.form)"/> 
<INPUT type="button" value="Finish" onclick="Finish(this.form)"/> 
</form>
</div>
</body>
</html>