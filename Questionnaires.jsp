<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import="java.io.*,				 
                 java.lang.String,
                 java.lang.Object,
                 java.util.*,
                 java.text.*,
                 java.util.Date"%>  

<html>
<head>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User_Jenty" class="CP_Classes.User_Jenty" scope="session"/> 
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>
<jsp:useBean id="RT" class="CP_Classes.SurveyRating" scope="session"/>

<%@ page import="CP_Classes.PrelimQuestion"%>
<%@ page import="CP_Classes.AdditionalQuestion"%>
<%@ page import="CP_Classes.vo.voCompetency"%>
<%@ page import="CP_Classes.vo.voKeyBehaviour"%>
<%@ page import="CP_Classes.vo.voRatingTask"%>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<title>Questionnaire</title>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
.style1 {
	color: #CCFFCC;
	font-weight: bold;
}
.style2 {font-size: 14pt}
body {
	background-color: #FFFFFF;
}
.style3 {color: #FFCCCC}
-->
</style>

</head>

<%
if(logchk.getSystemMaintenance()==false) {
%>
<body>
<%
}else{
%>
<body onload="autoSaveMaintenance()">
<%
}
%>
<script language="javascript">


//Textarea Size Check
//By: Chun Pong
//Date: 10 Jun 2008
function checkTextAreaSize(txtAreaField, iMaxLimit) {
	if(txtAreaField.value.length > iMaxLimit) {
		alert("Maximum characters exceeded. Maximum characters allowed: " + iMaxLimit);
		txtAreaField.value = txtAreaField.value.substring(0,iMaxLimit);
	}
}

//Edited by Xuehai 25 May 2011. Removed 'void' to enable running on Chrome&Firefox
//Begin.
function disableButton(form)
{
	//Disable User to click again while system is processing
	form.btnNext.disabled = true;
	form.btnPrev.disabled = true;
	form.btnFinish.disabled = true;
	form.btnExit.disabled = true;
	form.btnSave.disabled = true;
}

function enableButton(form)
{
	//Disable User to click again while system is processing
	form.btnNext.disabled = false;
	form.btnPrev.disabled = false;
	form.btnFinish.disabled = false;
	form.btnExit.disabled = false;
	form.btnSave.disabled = false;
}

function goNext(form)
{
	//disableButton(form);
	
	form.action = "Questionnaires.jsp?go=1";
	form.method = "post";
	form.submit();
}

function goPrev(form)
{
	//disableButton(form);
	
	form.action = "Questionnaires.jsp?go=2";
	form.method = "post";
	form.submit();
}
//End. Removing 'void'

/*
 * Change(s): added autosave
 * Reason(s): to prevent the data from being lost because of session time out
 * Updated By :Liu Taichen
 * Updated On :19/06/2012
 */

function autoSave(){
	alert("Your session is about to expire. Any Response up to this point of time will be saved by the system. ");
	Questionnaire.action = "Questionnaires.jsp?go=3&finish=1";
	Questionnaire.method = "post";
	Questionnaire.submit();
	parent.location.href = "index.jsp";
	
}

function autoSaveMaintenance(){
	alert("The system will be undergoing maintenance. Your inputs so far will be automatically saved by the system. We are sorry for any inconvenience caused. ");
	Questionnaire.action = "Questionnaires.jsp?go=3&finish=1";
	Questionnaire.method = "post";
	Questionnaire.submit();
	parent.location.href = "index.jsp";
	
}

setTimeout("autoSave()",29.5*60*1000); 

function confirmBackToPrelim(form){
	form.action = "PrelimQAnswers.jsp?entry=1";
	form.method = "post";
	form.submit();
}

function confirmFinish(form, type)
{
	//type 1=save, 2=finish
	var clicked = false;
	
	if(type == 1) {
		<% 	// Change prompt from "are finished" to "have finished" as requested by Ms Ros, Desmond 23 Oct 09
			// Change word rating to ratings, Mark Oei 16 April 2010%>
		if(confirm("<%=trans.tslt("If you have finished and do not wish to change the ratings any further, click CANCEL here and click on the FINISH button instead. If you simply want to save your ratings for now, click OK here")%>."))
			clicked = true;
		else 
		{
			return false;				
		}
	}
	else if(type == 2)
		clicked = true;
	else {
		if(confirm("<%=trans.tslt("Your rating will be saved and your assignment status for this target will be set to incomplete")%>."))
			clicked = true;
		else 
		{
			return false;				
		}				
	}
					
	if(clicked == true) {
		form.action = "Questionnaires.jsp?go=3&finish=" + type;
		form.method = "post";
		form.submit();
	}
	
	return true;
}

<!--if (!document.layers&&!document.all&&!document.getElementById)
	//event="test"-->

function showtip(current,e,text){
	if (document.all||document.getElementById){
		thetitle=text.split('<br>')
	
		if (thetitle.length>1){
			thetitles=''

			for (i=0;i<thetitle.length;i++)
				thetitles+=thetitle[i]
				current.title=thetitles
		} else
			current.title=text
	
	} else if (document.layers){
		document.tooltip.document.write('<layer bgColor="white" style="border:1px solid black;font-size:12px;">'+text+'</layer>')
		document.tooltip.document.close()
		document.tooltip.left=e.pageX+5
		document.tooltip.top=e.pageY+5
		document.tooltip.visibility="show"
	}
}

function hidetip(){
	if (document.layers)
		document.tooltip.visibility="hidden"
}

//Add by James 4-June 2008
function displayExpiredWarning(){
	alert('Warning, your session is about to expire');
	if (window.opener && !window.opener.closed){
	    window.opener.location.href="Top_Login.jsp?logoff=1";
		self.close();
	    }else{
		window.location.href="Top_Login.jsp?logoff=1";
	}
	
}//End of displayExpiredWarning

//Add by James 4-June 2008
function displayWarning(){
var answer = confirm('This session will expire in two minutes. Would you like to extend this session?')
	if (answer){
		document.Questionnaire.action = "Questionnaires.jsp?go=3&finish=1";
		document.Questionnaire.method = "post";
		document.Questionnaire.submit();	
	}
	else{
		setTimeout("displayExpiredWarning()",2*60*1000);
	}
}//End of displayWarning

//let's call this function after 29 minutes of loading this page to the browser
//setTimeout("displayWarning()",27*60*1000); 

setTimeout("displayWarning()",27*60*1000); 

</script>

<%
	String username=(String)session.getAttribute("username");

	if (!logchk.isUsable(username)) 
	{
%> 
		<script>
			parent.location.href = "index.jsp";
		</script>
<%  
	} 

	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();
	int compID = logchk.getCompany();
	int orgID = logchk.getOrg();
	
	String radiobuttonID = "";
	String radiobuttonName = "";
	boolean internalfirstLoop = true;
	boolean internalfirstLoopForComment = true;
	String commentID = "";
	
	//---get survey, target and rater ID.
	int surveyID = RDE.getSurveyID();		
	int targetLoginID = RDE.getTargetID();
	int raterLoginID = RDE.getRaterID();
	
	
	//---beans which are set from Questionnaire.jsp
	int start = Questionnaire.getStartID();
	int assignmentID = Questionnaire.getAssignmentID();
	String name = Questionnaire.getName();
	String jobPost = Questionnaire.getJobPost();
	String timeFrame = Questionnaire.getTimeFrame();
	String futureJob = Questionnaire.getFutureJob();
	//add by Denise 14/12/2009 
	boolean hideNAOption = Questionnaire.getHideNAOption(surveyID);
	
	if(futureJob == null)
		futureJob = "";
	if(timeFrame == null)
		timeFrame = "";

	int RSID[] = Questionnaire.getSurveyRatingScale(surveyID); //RatingScale ID
	int RTID[] = Questionnaire.getRT();
	//int RSID[] = Questionnaire.getRS();
	
	//---check if comment needs to be included
	int included = Questionnaire.commentIncluded(surveyID);
	int selfIncluded = Questionnaire.SelfCommentIncluded(surveyID);
	
	
	//---check if it is self
	String rCode = SurveyResult.RaterCode(assignmentID);
	
	
	//---query database in Questionnaire.java
	int surveyLevel = Questionnaire.getSurveyLevel();
	String [] UserInfo = User_Jenty.getUserDetail(pkUser);

	int pk = Questionnaire.getCurrID();	
	
	
	String surveyName = Questionnaire.getSurveyInfo(surveyID); //get surveyName
		
	String raterName = Questionnaire.UserName(orgID, raterLoginID);

	int totalRatingTask = Questionnaire.getTotalSurveyRating(surveyID); // count total number of rating task.
	int totalAll = 0;
	
	if(surveyLevel == 0)
		totalAll = Questionnaire.TotalList(surveyID, 0);
	else
		totalAll = Questionnaire.TotalList(surveyID, 1);

	
	if(request.getParameter("finishall") != null) {

		String temp = surveyName + "(S); " + name + "(T); " + raterName + "(R); Complete";
		/*try {
			EV.addRecord("Finish", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
		}catch(SQLException SE) {}*/
		
		//18-Apr-08 (Rianto): Need to be commented off since Yuni had implemented the 
		//Manual calculation and Timer to run it automatically. The new feature is designed to reduce the processing load 
		//when users submitted their questionnaires
		//SurveyResult.Calculate(assignmentID, 0);		// calculation part, 0=not include/exclude rater				
		
		
		//Wei Han 13 Apr 2012 check if there are additional questions to be answered, if not proceed as usual
		Vector<AdditionalQuestion> v = AddQController.getQuestions(surveyID);
		if(v.size()>0)
		{
			%>
			<script language="javascript">
			window.location.href = "AdditionalAnswers.jsp?entry=1";
			</script>
			<% 
		}
		else
		{
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
		window.location.href = "SurveyList_Process.jsp";
</script>
<%						
			} else if(userType == 3) {
%>
<script language="javascript">
		window.location.href = "RatersDataEntry.jsp";
</script>
<%						
			} else if(userType == 4) {		// raters
%>
<script language="javascript">
		window.location.href = "RatersToDoList.jsp";
</script>
<%
		}
		}
	}
	
	//---get the list of competency assigned to the survey.
	
	
	int iPKComp = 0;
	int iPKKB = 0;
	// by Hemilda Date 13/08/2008 add compentecy name
	String sCompName="";
	String sCompDef = "";
	String sKBDef = "";
	Vector vKBList = new Vector();
	int totalCurrComp = Questionnaire.getTotalCurrComp();	// to mantain the total current competency
	int totalComp = Questionnaire.getTotalComp();


	int totalCompleted = Questionnaire.TotalResult(assignmentID);
	
	int complete = 0;
	if(totalCompleted == (totalAll * totalRatingTask))
		complete = 1;

	Vector vComp = Questionnaire.getCompetency(surveyID);
	voCompetency voComp = new voCompetency();
		
	if(vComp.size() > 0)
		voComp = (voCompetency)vComp.elementAt(0);
	
	if (voComp != null) {

		iPKComp = voComp.getCompetencyID();
		sCompDef = voComp.getCompetencyDefinition();
		sCompName = voComp.getCompetencyName();
		if(surveyLevel == 0)
			vKBList = KB.getKBList(iPKComp, 0, compID, orgID);
		else 
			vKBList = Questionnaire.getKBSurvey(surveyID, iPKComp);
	}
	else 
	{
		if(userType == 1 || userType == 2) {
%>			<script language="javascript">
				window.location.href = "SurveyList_Process.jsp";
			</script>
<%						
		} else if(userType == 3) {
%>			<script language="javascript">
				window.location.href = "RatersDataEntry.jsp";
			</script>
<%						
		} else if(userType == 4) {
%>			<script language="javascript">
				window.location.href = "RatersToDoList.jsp";
			</script>
<%		}
	}
	
	if(totalCurrComp == 0) {
	
		Questionnaire.setCurrID(iPKComp);
		
	}
	
	if(totalCurrComp == 0 && start == 1) {
		Questionnaire.setStartID(0);
		int total = Questionnaire.TotalResult(assignmentID, iPKComp);
		
		//System.out.println("---------------->" + assignmentID + "---" + iPKComp+ "---" + iPKKB);
		
		int counter = 1;
		int totalRating = 0;
		
		if(surveyLevel == 1)
			totalRating = vKBList.size() * totalRatingTask;
		else 
			totalRating = totalRatingTask;
		
		while(total == totalRating && counter < totalComp && counter < vComp.size()) 
		{
		
			voComp = (voCompetency)vComp.elementAt(counter);
			
			totalCurrComp++;
			iPKComp = voComp.getCompetencyID();
			sCompDef = voComp.getCompetencyDefinition();
			sCompName = voComp.getCompetencyName();
			Questionnaire.setCurrID(iPKComp);
							
			if(surveyLevel == 0)
				vKBList = KB.getKBList(iPKComp, 0, compID, orgID);
			else 
				vKBList = Questionnaire.getKBSurvey(surveyID, iPKComp);
				
			Questionnaire.setTotalCurrComp(totalCurrComp);	
			
			//System.out.println("========>" + totalCurrComp); 
			
			total = Questionnaire.TotalResult(assignmentID, iPKComp);
			
			if(surveyLevel == 1)
				totalRating = vKBList.size() * totalRatingTask;
			//System.out.println(assignmentID + "---" + iPKComp+ "---" + iPKKB + "----" + total);

			counter ++;
		}
	}
		
	if(request.getParameter("go") != null) 
	{
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
		System.out.println("Go = " + request.getParameter("go") + " " + strDate);

		totalCurrComp = Questionnaire.getTotalCurrComp();
		int go = Integer.parseInt(request.getParameter("go"));
	
		pk = Questionnaire.getCurrID();
		
		int arrPK[] = new int[1];
	    
	    if(surveyLevel == 1) {
			vKBList = Questionnaire.getKBSurvey(surveyID, pk);
			arrPK = new int[vKBList.size()];
			
			for(int i=0; i<vKBList.size(); i++) {
				voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
				arrPK[i] = voKB.getKeyBehaviourID();
				//System.out.println("----"+arrPK[i]);
			}
			

		} else {
			arrPK[0] = pk;
		}	
				
	
		if( (go == 1 && totalCurrComp < totalComp) || (go == 2 && totalCurrComp >= 0) || go == 3 ) {
		
			//System.out.println("OKKKKKKKK");
			for(int rs=0; rs<totalRatingTask; rs++) 
			{
				int RTStatus = Questionnaire.RTSetupStatus(RTID[rs], surveyID);
				
				if(RTStatus != 0) 
				{
					for(int i=0; i<arrPK.length; i++) {
						float oldResult = Questionnaire.CheckOldResultExist(assignmentID, arrPK[i], RTID[rs]);
						
						if(oldResult <= 0) {
							try {
								float score = 0;
								
								if (surveyLevel == 0) 
									score = Questionnaire.RTScore(RTID[rs], surveyID, arrPK[i]);
								 else 
									score = Questionnaire.RTScoreBehv(RTID[rs], surveyID, arrPK[i]);
	
								Questionnaire.addResult(assignmentID, arrPK[i], RTID[rs], score); 
							}catch(SQLException SE) {}
						}
					}
					
				} else {
					for(int i=0; i<arrPK.length; i++) {	
							
						String [] scaleValue = request.getParameterValues("rbtnScale" + "_" + arrPK[i] + "_" + RTID[rs]);
						if(scaleValue != null)
						{
							for(int j=0; j<scaleValue.length; j++) {
								if(scaleValue[j] != null){
									float value = Float.parseFloat(scaleValue[j]);
									float oldResult = Questionnaire.CheckOldResultExist(assignmentID, arrPK[i], RTID[rs]);
									
									//System.out.println("*************************"+oldResult);
									if(oldResult < 0) {
										try {
											Questionnaire.addResult(assignmentID, arrPK[i], RTID[rs], value); //add date
										}catch(SQLException SE) {}
									}								
									else if(oldResult >= 0 && oldResult != value)
										Questionnaire.updateOldResult(assignmentID, arrPK[i], RTID[rs], value);
								}
							}
						} // end if scaleValue is not null				
					} // end for loop				
				}
			}
		
			if((rCode.equals("SELF") && selfIncluded == 1) || !(rCode.equals("SELF"))&&included == 1)
			{
			    for(int i=0; i<arrPK.length; i++) {	

					int competency = arrPK[0];
					int keybehaviour = 0;
					
					if(surveyLevel == 1) {
						competency = Questionnaire.CompetencyID(arrPK[i]);
						keybehaviour = arrPK[i];
					}
					
					String commentName = "comments" + competency + keybehaviour;
					if(request.getParameter(commentName) != null) {
						
						String value = request.getParameter(commentName);
						//System.out.println(pk+ "======" + value);

						value = value.trim();
                                                //Removed by DeZ, 16.07.08, Fixed problem where Import Questionaires gives blank narrative comments even though data is available
						//value = Database.SQLFixer(value);
							
						String oldResult = Questionnaire.checkCommentExist(assignmentID, competency, keybehaviour);
						if(oldResult == null) {
							//System.out.println("OK");
							try {
								Questionnaire.addComment(assignmentID, competency, keybehaviour, value); 
							}catch(SQLException SE) {}
						}								
						else if(oldResult != null && !oldResult.equals(value)) {
							Questionnaire.updateComment(assignmentID, competency, keybehaviour, value);
							//System.out.println("OK2");

						}
					}
				}
			}
		
		}
		
		//go == 1 is next and go == 2 is prev
		
		if(go == 2 && (totalCurrComp-1) >= 0 )  {
			totalCurrComp--; 
		} else if(go == 1 && (totalCurrComp+1) < totalComp) {
			totalCurrComp++;
			System.gc();
		}
		
		if(go == 1 || go == 2) {		
			Questionnaire.setTotalCurrComp(totalCurrComp);			
			
			
			if(totalCurrComp >= 0 && totalCurrComp < totalComp && totalCurrComp < vComp.size()) 
			{
				voComp = (voCompetency)vComp.elementAt(totalCurrComp);
				iPKComp = voComp.getCompetencyID();
				sCompDef = voComp.getCompetencyDefinition();
				sCompName = voComp.getCompetencyName();
				Questionnaire.setCurrID(iPKComp);
				//System.out.println("-----------------------------"+totalCurrComp +"-----------------------------"+iPKComp);
		
				if(surveyLevel == 0)
					vKBList = KB.getKBList(iPKComp, 0, compID, orgID);
				else 
					vKBList = Questionnaire.getKBSurvey(surveyID, iPKComp);
	
				}
		}
		
		
		if(request.getParameter("finish") != null) {
			/*************if finish == 3 which indicates exit*******************************/
			if(Integer.parseInt(request.getParameter("finish")) == 3) {
				String temp = surveyName + "(S); " + name + "(T); " + raterName + "(R); Incomplete";
				/*try{
					EV.addRecord("Exit", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
				}catch(SQLException SE) {}*/
				
				if(userType == 1 || userType == 2) {
					%>
					<script language="javascript">
							window.location.href = "SurveyList_Process.jsp";
					</script>
					<%						
				} else if(userType == 3) {
					%>
					<script language="javascript">
							window.location.href = "RatersDataEntry.jsp";
					</script>
					<%						
				} else if(userType == 4) {
					%>
					<script language="javascript">
							window.location.href = "RatersToDoList.jsp";
					</script>
					<%						
				}
			}
			
			/*************if finish == 2 which indicates finish the questionnaire*******************************/
			
			if(Integer.parseInt(request.getParameter("finish")) == 2) { 
				totalCompleted = Questionnaire.TotalResult(assignmentID);
				//System.out.println(totalCompleted + "-------" + totalAll * totalRatingTask);
				//System.out.println("totalCompleted = " + totalCompleted + ", totalAll = " + totalAll + ", totalRatingTask = " + totalRatingTask);
					
				if(totalCompleted == (totalAll * totalRatingTask)) {
					int commentIncluded = Questionnaire.commentIncluded(surveyID);
						
					int commentInput = 1;
					
					if(targetLoginID == raterLoginID)
						commentIncluded = Questionnaire.SelfCommentIncluded(surveyID);
					
					if(commentIncluded == 1)
						commentInput = Questionnaire.checkCommentInput(assignmentID);
						
					Vector<AdditionalQuestion> v = AddQController.getQuestions(surveyID);
					if(commentInput == 0) {
						
						
						if(v.size()>0)
						{
							%>
							<script>
								if(confirm("<%=trans.tslt("No comments included. Do you want to continue")%>?"))
									window.location.href = "Questionnaires.jsp?finishall=1";
							</script>
							<%		
						}
						else
						{
						%>
						<script>
							if(confirm("<%=trans.tslt("No comments included. Do you want to finish")%>?"))
								window.location.href = "Questionnaires.jsp?finishall=1";
						</script>
						<%				
						}
					}else {		
						if(v.size()>0)
						{
							%>
							<script>
							<% // Change from "clicked" to click as requested by Ms Ros, Desmond 23 Oct 09 %>
									window.location.href = "Questionnaires.jsp?finishall=1";
							</script>
							<%		
						}
						else
						{
						%>
						<script>
						<% // Change from "clicked" to click as requested by Ms Ros, Desmond 23 Oct 09 %>
							if(confirm("<%=trans.tslt("Once you click on the OK button, no more amendments can be made")%>."))
								window.location.href = "Questionnaires.jsp?finishall=1";
						</script>
						<%		
						}
					}
				} else {
					String temp = surveyName + "(S); " + name + "(T); " + raterName + "(R); Incomplete";
					//EV.addRecord("Finish", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
					Questionnaire.setStartID(1);
					%>
					<script language="javascript">
					alert("You have not completed the questionnaire. Your rating will be saved and you will need to return to provide ratings to the rest of the questions. Your participation is critical to the sucess of this survey.");
					</script>
					<!----------------------------------------Incomplete Questionnaire------------------------------------------->
					<%								
					
					if(userType == 1 || userType == 2) {
						%>
						<script language="javascript">
								window.location.href = "Questionnaire.jsp";
						</script>
						<%						
					} else if(userType == 3) {
						%>
						<script language="javascript">
								window.location.href = "Questionnaire.jsp";
						</script>
						<%						
					} else if(userType == 4) {
						%>
						<script language="javascript">
								window.location.href = "Questionnaire.jsp";
						</script>
						<%						
					}			
				}
				
			
			} else {
				if(totalCurrComp >= 0 && totalCurrComp < totalComp && totalCurrComp < vComp.size()) {
					voComp = (voCompetency)vComp.elementAt(totalCurrComp);
					iPKComp = voComp.getCompetencyID();
					sCompDef = voComp.getCompetencyDefinition();
					sCompName = voComp.getCompetencyName();
					Questionnaire.setCurrID(iPKComp);
					
					if(surveyLevel == 0)
						vKBList = KB.getKBList(iPKComp, 0, compID, orgID);
					else 
						vKBList = Questionnaire.getKBSurvey(surveyID, iPKComp);
		
				}
			}
		}
	}	// end request.getParameter("go")

%>

<form name = "Questionnaire" method="post">

<table width="610" border="1" font style='font-size:12.0pt;font-family:Arial'>
<th align="center" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Questionnaire")%><span class="style2"></span></font></b></th>
</table>
<p></p>


<table width="610" class="fontstyle">
<%
	if(request.getParameter("saveSuccessful") != null) {
%>
	<tr>
<% 		
		if (futureJob.equals("")) { 

%>
	<td colspan="3" align="center"><font color="red"><b>Saved Successfully!</b></font></td>
	
<%
		} else {
%>
	<td colspan="6" align="center"><font color="red"><b>Saved Successfully!</b></font></td>	
<%
		}
%>
	</tr>
<%
	}
%>
	
	<tr><td width="80"><%=trans.tslt("Target Name")%></td>
		<td width="5"> : </td>
		<td width="250"><%=name%></td>
		
<% 
	if (futureJob != "") {
%>
	<td width="80" align="left"><%=trans.tslt("Future Job")%></td>
	<td width="5"> : </td>
	<td width="250"><%=futureJob%></td>
<% }%>
	</tr>
	<tr>
	<td></td>
	<td></td>
	<td></td>
	</tr>
	<tr><td width="80"><%=trans.tslt("Job Position")%></td>
		<td width="5"> : </td>
		<td><%=jobPost%></td>
<% 
	if (timeFrame!= "") {
%>
	<td width="84" align="left"><%=trans.tslt("Time Frame")%></td>
	<td width="5"> : </td>
	<td><%=timeFrame%></td>
<% }%>
	</tr>
	<tr>
	<td></td>
	<td></td>
	<td></td>
	</tr>
	<tr><td width="157"><%=trans.tslt("Competency Name")%></td>
		<td width="4"> : </td>
		<td><%=sCompName%></td>
	</tr>
<table>	
<table width="610" class="fontstyle">	
<%
// Hide Competency Definition textbox in Questionnaire if no Competency Definition is available in the database, Desmond 11 August 2010
if(sCompDef.length()>0 && !sCompDef.equalsIgnoreCase("")) {
	
	/*
     *Change(s): added check on the HideCompDesc before printing the competency description
     *Reason(s): To allow toggling of the competency description
     *Updated By: Liu Taichen
     *Updated On: 12/07/2012
     */
	votblSurvey voSurvey = CE_Survey.getSurveyDetail(surveyID);
	if(voSurvey.getHideCompDesc() == 0){
%>
	<tr><td width="157"><%=trans.tslt("Competency Description")%></td>
		<td width="4"> : </td>
		<td><textarea name="compStatement" cols="82" rows="3" style='width:100%;height:80px;' readonly><%=sCompDef%></textarea></td>
	</tr>
<%}
}%>
	<tr>
	<td></td>
	<td></td>
	<td></td>
	</tr>
	<tr>
	<td></td>
	<td></td>
	<td></td>
	</tr>

    
<%
	int [] arrID = new int[1];
	
	if(surveyLevel == 0) {
		arrID[0] = Questionnaire.getCurrID();
		if(CE_Survey.getHideKBDesc(surveyID)==0){
%>
	<tr>
	<td><%=trans.tslt("Key Behaviours")%></td>
	<td>:</td>
	<td><table border="1" font style='font-size:10.0pt;font-family:Arial' width="515" height="8">
<tr>
<td>
<div style='width:504px; height:60px; z-index:1; overflow:auto'>
<table border="1" font style='font-size:10.0pt;font-family:Arial' width="487" bgcolor="#FFFFCC">
<%
	}
	} else {
		arrID = new int[vKBList.size()];
	}
	
	for(int i=0; i<vKBList.size(); i++) {
	
		voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
		iPKKB = voKB.getKeyBehaviourID();
		sKBDef = voKB.getKeyBehaviour();
		
		if(surveyLevel == 0) {
		if(CE_Survey.getHideKBDesc(surveyID)==0){
%>
		<tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'">
		<td><%=sKBDef%></td>
	</tr>

<%		
		}
		} else {
			arrID[i] = iPKKB;
		}
	}

	if(surveyLevel == 0) {
	if(CE_Survey.getHideKBDesc(surveyID)==0){
%>
	</table></div></td>
	
	</tr></table></td></tr>

<%
	}
	}	

%>	

</table>

<p></p>

<% 
	if(surveyLevel == 0) {
%>	
  <table width="600" class="fontstyle" border="0">
<%
	} else {
%>
  <table width="600" class="fontstyle" border="3" bordercolor="#99ccff" cellspacing="0">

<%
	}
	for(int i=0; i<arrID.length; i++) {
		int ID = 0;
		
	
		if(surveyLevel == 0) {
			ID = arrID[i];
			/*
			*Change(s): added a row to display whether the competency rating is completed
			*Reason(s): to let the user know of the status of the completion of the questionnaire
			*Updated By: Liu Taichen
			*Updated On: 29/June/2012
			*
			*/
			if(Questionnaire.isCompletedCompetency(assignmentID, ID)){
			%>			
			<tr bgcolor="navy">
			<td align ="right" bordercolor="#000080" bgcolor="navy" colspan = "2"><img src="images/RaterCompleted.bmp"></td>
			</tr>
<%	
			}
			else{
				
				%>			
				<tr bgcolor="yellow" font style='color:black'>
				<td align ="right" bordercolor="yellow"  colspan = "2"><img src="images/RaterIncomplete.bmp"></td>

	<%			
				
			}
		} else if(surveyLevel == 1) { 
			voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
			ID = voKB.getKeyBehaviourID();
			sKBDef = voKB.getKeyBehaviour();
				
			if(Questionnaire.hasCompleted(assignmentID, ID, RTID.length)) {
%>			
			<tr bgcolor="navy" font style='color:white'>
			<td bordercolor="#000080"><%=i+1%>. <%=sKBDef%></font></td>
			<td align ="right" bordercolor="#000080" bgcolor="navy"><img src="images/RaterCompleted.bmp"></td>

<%
			} else {
%>			
			<tr bgcolor="yellow" font style='color:black'>
			<td bordercolor="yellow"><%=i+1%>. <%=sKBDef%></font></td>
			<td align ="right" bordercolor="yellow"><img src="images/RaterIncomplete.bmp"></td>

<%				
			}
			
%>		
		
		</tr>


		
<%
		}
%>

		<tr>
		
<%
		if(surveyLevel == 1) {
%>		
			<td colspan="2">
<%
		}		
%>

<%			
    //Rating Scale ID For-Loop
    for(int k=0; k<RSID.length; k++) {

        if(RSID[k] == -1) 
            break;

        float oldRS = 0;

        Vector vRatingTask = Questionnaire.getSurveyRatingTask(surveyID, RSID[k]);

        int iRTID = 0;
        String sRTName = "";
        String sRTCode;
        String colours [] = {"#FFFFCC", "#CCFFCC", "#CCFFCC"};

        int iCount = 0;
        
        //Rating Task Vector For-Loop
        for(int l=0; l<vRatingTask.size(); l++) {
            voRatingTask voRT = (voRatingTask)vRatingTask.elementAt(l);

            iRTID = voRT.getRatingTaskID();

            /************************** CHECK EITHER TO SHOW OR HIDE RATING TASK *********************************************/
            int RTSetup = Questionnaire.RTSetupStatus(iRTID, surveyID);

            if(RTSetup != 2) {
                iCount++;
            }
        } //End of Rating Task Vector For-Loop
%>		
        <table width="600" border="0"  font style='font-size:10.0pt;font-family:Arial'>
            <tr align="center" height="5">
            	<td width="60">&nbsp;</td>
<%
        Vector vRScale = Questionnaire.getRatingScale(RSID[k]);
        int totalRS   = Questionnaire.getTotalRS(RSID[k]);
       
        int low [] = new int [totalRS];
        int high [] = new int [totalRS];
        int lowValue=0, highValue=0;
        int colspan[] = new int[totalRS];
        String scaleDesc = "";
        String sScaleNADesc = "";

        //iCount Check
        if(iCount != 0) {
            //Ranking Scale Vector For-Loop
            for(int j=0; j<vRScale.size(); j++) {	
                String [] sRS = sRS = new String[3];
                sRS = (String[])vRScale.elementAt(j);
                int value = Integer.parseInt(sRS[0]);
                
                //Denise 17/12/2009 to hide the 0 value if required
                if (value==0 && hideNAOption) value ++;
                low[lowValue] = value; 
                high[highValue] = Integer.parseInt(sRS[1]);
                scaleDesc = sRS[2];

                colspan[lowValue] = high[highValue] - low[lowValue] + 1;
    	
               //edit by Denise 17/12/2009 	to hide the NA value. change equals to equalsIgnoreCase
               if(hideNAOption && (scaleDesc.equalsIgnoreCase("NA") || scaleDesc.equalsIgnoreCase("N/A") || scaleDesc.equals("Not applicable")
              					|| scaleDesc.contains("NA") || scaleDesc.contains("N/A")|| scaleDesc.contains("Not applicable") ||scaleDesc.contains("Not Applicable")||(high[highValue]==0))) {                 					 
                     totalRS --;
                } else { 
%>
                <td height="10" bgcolor="#CCCCCC"  colspan=<%=colspan[lowValue]%> align="center"><b><%=scaleDesc%></b></td>
<%					
                //}
                
                lowValue++;
                highValue++;
               }
            } //End of Ranking Scale Vector For-Loop
            
/*            if(!sScaleNADesc.equals("")) {
%>
                <td height="10" width="80" bgcolor="#CCCCCC" colspan="1" align="center"><b><%=sScaleNADesc%></b></td>
<%			
            }*/
        } //End of iCount Check
%>
</tr>

<%
        //Rating Task Vector For-Loop
        for(int l=0; l<vRatingTask.size(); l++) {
            voRatingTask voRT = (voRatingTask)vRatingTask.elementAt(l);

            iRTID = voRT.getRatingTaskID();
            sRTName = voRT.getRatingTaskName();
            sRTCode = voRT.getRatingCode();
            String RTDesc = RT.getRatingTaskDesc(surveyID, iRTID);
            String RTDisplayCode = RT.getRTDisplayCode(surveyID, iRTID);

            /************************** CHECK EITHER TO SHOW OR HIDE RATING TASK *********************************************/
            int RTSetup = Questionnaire.RTSetupStatus(iRTID, surveyID);
            
            //Rating Task Setup Not 2 Check
            if(RTSetup != 2) {

            oldRS = Questionnaire.CheckOldResultExist(assignmentID, ID, iRTID);
            
            // Updated descriptors displayed when mouse-over Rating Task Code (i.e. CP or CPR) in Questionnaire to include a simple description, Desmond 11 August 2010
            String descriptor = sRTName;
            
            // Updated display code and description to show the specific ones edited by user. 
            if(RTDisplayCode == null)
            	RTDisplayCode = sRTCode;
            
            // The description can be edited during the edition session.
           	if(RTDesc == null || RTDesc == "")
           	{
           		if( sRTCode.equalsIgnoreCase("CPR") ) {
                	RTDesc = " - Level of performance expected of the Target";
                } else if( sRTCode.equalsIgnoreCase("CP") ) {
                	RTDesc = " - Actual level of performance of the Target now";
                } else if( sRTCode.equalsIgnoreCase("IN")) {
                	// For current importance description
                	RTDesc = " - Importance of the competency / key behaviour to the job presently.";
                } else if( sRTCode.equalsIgnoreCase("IF")) {
                	// For future importance description
                	RTDesc = " - Importance of the competency / key behaviour to the job in the future.";
                } else {
                	RTDesc = "";
                }
           	} else
           	{
           		RTDesc = " - " + RTDesc;
           	}
            
            descriptor += RTDesc;
%>
	<tr align="center">
		<td width ="60" bgcolor="99CCFF"><a onMouseOver="showtip(this,event,'<%=descriptor%>')" onMouseout="hidetip()"><%=RTDisplayCode%></td>				
<%
int j=0;

int totalValue = 0;
//Denise 17/12/2009 to find the number of rating value
for (int x=0; x<totalRS; x++)
{
	totalValue += (high[x] -low[x]+1);
}
while(j < totalRS) {

lowValue = low[j];
highValue = high[j];

while(lowValue <= highValue) {
if (internalfirstLoop != true) {
radiobuttonName = radiobuttonName + "," + Integer.toString(ID) + "_" + Integer.toString(iRTID);
radiobuttonID = radiobuttonID + "," + Integer.toString(ID) + "_" + Integer.toString(iRTID) + "_" + Integer.toString(lowValue);
} else {
radiobuttonName = Integer.toString(ID) + "_" + Integer.toString(iRTID);
radiobuttonID = Integer.toString(ID) + "_" + Integer.toString(iRTID) + "_" + Integer.toString(lowValue);
internalfirstLoop = false;
}//change to adjust the size of the rating value
%>	
<td width = <%=100/(totalValue+1)%>% bgcolor="<%=colours[l]%>">
<%		

if(RTSetup == 1) 		//Fixed by admin in Survey Setup and display in Questionnaire
oldRS = (int)Questionnaire.RTScore(iRTID, surveyID, iPKComp);

//System.out.println(RTSetup + "---"+iRTID+"----------------------->"+oldRS + "======"+ lowValue);
if(oldRS >= 0 && oldRS == lowValue) {
if(RTSetup == 1) {
%>							
<input type="radio" id="<%="rbtnScale"+ "_" + ID + "_" + iRTID + "_" + lowValue%>" name="<%="rbtnScale"+ "_" + ID + "_" + iRTID%>" value="<%=lowValue%>" disabled checked></br>
<%		
} else {
%>			
<input type="radio" id="<%="rbtnScale"+ "_" + ID + "_" + iRTID + "_" + lowValue%>" name="<%="rbtnScale"+ "_" + ID + "_" + iRTID%>" value="<%=lowValue%>" checked></br>
<%		
}

} else {

if(RTSetup == 1) {
%>			
<input type="radio" id="<%="rbtnScale"+ "_" + ID + "_" + iRTID + "_" + lowValue%>" name="<%="rbtnScale"+ "_" + ID + "_" + iRTID%>" value="<%=lowValue%>" disabled></br>
<%		
} else {
%>			
<input type="radio" align = "center" id="<%="rbtnScale"+ "_" + ID + "_" + iRTID + "_" + lowValue%>" name="<%="rbtnScale"+ "_" + ID + "_" + iRTID%>" value="<%=lowValue%>"></br>
<% 		
}
} 
if(lowValue == 0) {
%>		
<%="N/A"%>
<%			
} else {
%>		
<%=lowValue%>	
<% 	
} 
%>
</td>
<%				
lowValue++;
} ; //end do while
j++;
} //End of While-Loop


%>
</tr>

<%
            } //End of Rating Task Setup Not 2 Check
        } //Rating Task Vector For-Loop
%>				
</table>
<p></p>

<% 
    } //End of Rating Scale ID For-Loop
%>

<%
		
	if ((rCode.equals("SELF") && selfIncluded == 1) || !(rCode.equals("SELF"))&&included == 1){
	
%>
<p></p>
<font class="fontstyle"><%=trans.tslt("Narrative Comments")%>:</font></strong>
<br>
<%
					int competency = iPKComp;
					int keybehaviour = 0;
				
					if(surveyLevel == 1) {
						keybehaviour = ID;
					}
		
					//System.out.println("comment : " + assignmentID + ", " + competency + ", " + keybehaviour);
					String oldComment = Questionnaire.checkCommentExist(assignmentID, competency, keybehaviour);
					String commentName = "comments" + competency + keybehaviour;
					
					//System.out.println("----->" + oldComment);
					if(oldComment == null)
						oldComment = "";
					
					if (internalfirstLoopForComment != true) {
						commentID = commentID + "," + commentName;
					} else {
						commentID = commentName;
						internalfirstLoopForComment = false;
					}
%>

					<textarea name=<%=commentName%> cols="96" rows="5" style='width:100%;height:80px;' style="font-size: 10pt; font-family: Arial" onKeyUp="checkTextAreaSize(this,4000);"><%=oldComment%></textarea>
<% 				} %>

<!------------------------------------------------------- ADD COMMENTS ------------------------------------------------------------------->



<strong>
</td></tr>


<%		
		
	} // end Competency / KBList
%>
      
	</table>
<input name="totalCompleted" type="hidden" id="totalCompleted" value="<%=complete%>">
<table border="1" width="610" class="fontstyle"  bgcolor="navy">
	<tr>
		<td width="179">
		<% // Removed "Factor" from bottom left corner of screen as requested by Ms Ros, Desmond 23 Oct 09 %>
		<p align="left" font style='color:white'> <%=totalCurrComp + 1%> 
		of <%=totalComp%></font></td>
		<td align="center">
			<table border="0" width="60%">
			<tr>
<%
				Vector<PrelimQuestion> v = PrelimQController.getQuestions(RDE.getSurveyID());
				if(v.size() > 0){
%>
					<td width="35">
					<input type=button value="Back to Preliminary Questions" name="btnBackToPrelim" onClick="confirmBackToPrelim(this.form)"></td>
<%
				}
%>
				<td width="35">
				<input type=button value="Save" name="btnSave" onClick="confirmFinish(this.form, 1)"></td>
				<td width="35">
				<input type=button value="Finish" name="btnFinish" onClick="return confirmFinish(this.form, 2)"></td>
			</tr>
			</table>
		</td>
		
		<td>
		<div align="right">
		<table border="0" width="42%">
			<tr>
				<input type="hidden" id="radiobuttonName" name="radiobuttonName" value="<%= radiobuttonName%>">
				<input type="hidden" id="radiobuttonID" name="radiobuttonID" value="<%= radiobuttonID%>">
				<input type="hidden" id="commentID" name="commentID" value="<%= commentID%>">


<%
			int total = totalCurrComp;

			if(total == 0) {
%>	  
			<td width="35"><input type=button name="btnPrev" value="   <   " disabled></td>

<% 			
			} else {
%>
			<td width="35"><input type="button" name="btnPrev" value="   <   " onClick="goPrev(this.form)">

<% 
			} 
			
			if(total+1 == totalComp) {
%>
		
			<td><input type=button name="btnNext" value="   >   " disabled></td>
<% 
			} else {
%>
				
			<td><input type=button name="btnNext" value="   >   " onClick="goNext(this.form)"></td>
<% 
			}
%>
			</tr>
		</table>
		</div>
		</td>
	</tr>
</table>


</form>
</body>
<p></p>
<%@ include file="Footer.jsp"%></html>