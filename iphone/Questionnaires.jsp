<%@ page pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%@ page import="java.io.*,		 
                 java.lang.String,
                 java.lang.Object,
                 java.util.* "%>

<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/> 
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>

<%@ page import="CP_Classes.PrelimQuestion"%>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<%@ page import="CP_Classes.AdditionalQuestion"%>
<%@ page import="CP_Classes.vo.voCompetency"%>
<%@ page import="CP_Classes.vo.voKeyBehaviour"%>
<%@ page import="CP_Classes.vo.voRatingTask"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
	<link rel="apple-touch-icon" href="icon.png" />
	<link rel="shortcut icon" href="icon.png" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Questionnaire - iPhone Version</title> 
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
	<script type="application/x-javascript" src="questionnaires.js"></script>

	<script language="javascript">
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
	
	function displayWarning(){
		var answer = confirm('This session will expire in two minutes. Would you like to extend this session?')
		if (answer){
			document.Questionnaire.action = "Questionnaires.jsp?go=3&finish=1";
			document.Questionnaire.method = "post";
			document.Questionnaire.submit();	
		}
	}//End of displayWarning
	
	function confirmBackToPrelim(form){
		form.action = "PrelimQAnswers.jsp?entry=1";
		form.method = "post";
		form.submit();
	}

	setTimeout("autoSave()",29.5*60*1000);
	setTimeout("displayWarning()",27*60*1000);
	</script>

<%
	String username=(String)session.getAttribute("username");

	if (!logchk.isUsable(username)) 
	{
%> 
		<script language="javascript">
			window.location.href = "index.jsp";
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
	String [] UserInfo = User.getUserDetail(pkUser);

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
	alert("<%=trans.tslt("Thank you for your participation")%>");
</script>
<script language="javascript">
		window.location.href = "RatersToDoList.jsp";
</script>
<%}
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
%>			
			<script language="javascript">
				window.location.href = "RatersToDoList.jsp";
			</script>
<%
	}
	
	if(totalCurrComp == 0) {
		Questionnaire.setCurrID(iPKComp);
	}
	
	if(totalCurrComp == 0 && start == 1) {
		Questionnaire.setStartID(0);
		int total = Questionnaire.TotalResult(assignmentID, iPKComp);
		
		int counter = 1;
		int totalRating = 0;
		
		if(surveyLevel == 1)
			totalRating = vKBList.size() * totalRatingTask;
		else 
			totalRating = totalRatingTask;
		
		while(total == totalRating && counter < totalComp && counter < vComp.size()){
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
			
			total = Questionnaire.TotalResult(assignmentID, iPKComp);
			
			if(surveyLevel == 1)
				totalRating = vKBList.size() * totalRatingTask;

			counter ++;
		}
	}
		
	if(request.getParameter("go") != null){

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
			}
		} else {
			arrPK[0] = pk;
		}	
	
		if( (go == 1 && totalCurrComp < totalComp) || (go == 2 && totalCurrComp >= 0) || go == 3 ) {
		
			for(int rs=0; rs<totalRatingTask; rs++){
				int RTStatus = Questionnaire.RTSetupStatus(RTID[rs], surveyID);
				
				if(RTStatus != 0){
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
				
				%>
				<script language="javascript">
						window.location.href = "RatersToDoList.jsp";
				</script>
				<%
			}
			
			/*************if finish == 2 which indicates finish the questionnaire*******************************/
			
			if(Integer.parseInt(request.getParameter("finish")) == 2) { 
				totalCompleted = Questionnaire.TotalResult(assignmentID);
				
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
						<script language="javascript">
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
									window.location.href = "Questionnaires.jsp?finishall=1";
							</script>
							<%		
						}
						else
						{
						%>
						<script language="javascript">
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
					<script language="javascript">
							window.location.href = "Questionnaire.jsp";
					</script>
						<%						
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
<div class="toolbar" style='min-width:420px;'> 
	<h1 id="pageTitle"></h1>
	<a title="Rater's To Do List" class="backButton" href="#" onclick="window.location.href='RatersToDoList.jsp'">To Do List</a>
	<a title="Log out" class="logoutButton" href="#" onclick="if(!confirm('Please make sure you have saved your ratings. Click \'OK\' to log off; Click \'Cancel\' to continue your rating.')){return false;};window.location.href='login.jsp?logout=1'">Exit</a>
</div>
<div id="questionnaire" class="panel" title="Questionnaire" selected='true' style='min-width:420px;'>
<form name = "Questionnaire" method="post">
<p style="font-size:14px">
<%=trans.tslt("Target Name")%>:&nbsp;<%=name%><br>
<%if (futureJob != ""){%><%=trans.tslt("Future Job")%>:&nbsp;<%=futureJob%><br><%}%>
<%=trans.tslt("Job Position")%>:&nbsp;<%=jobPost%><br>
<%if (timeFrame!= ""){%><%=trans.tslt("Time Frame")%>:&nbsp;<%=timeFrame%><br><%}%>
<%=trans.tslt("Competency Name")%>:&nbsp;<%=sCompName%><br>
</p>
<%
	int[] arrID = new int[1];
%>
<%if((sCompDef.length()>0 && !sCompDef.equalsIgnoreCase("")) || surveyLevel == 0){%>
<fieldset> 
	<%if(sCompDef.length()>0 && !sCompDef.equalsIgnoreCase("")){
		/*
	     *Change(s): added check on the HideCompDesc before printing the competency description
	     *Reason(s): To allow toggling of the competency description
	     *Updated By: Liu Taichen
	     *Updated On: 12/07/2012
	     */
		votblSurvey voSurvey = CE_Survey.getSurveyDetail(surveyID);
		if(voSurvey.getHideCompDesc() == 0){
	
	%>
    <div class="row" style="text-align:left"> 
         <%=trans.tslt("Competency Description")%><br>
         <textarea name="compStatement" cols="82" rows="3" style='width:100%;height:50px;' readonly><%=sCompDef%></textarea>
    </div>
    <%}
	}%>
    <%
	if(surveyLevel == 0){
		arrID[0] = Questionnaire.getCurrID();
		if(CE_Survey.getHideKBDesc(surveyID)==0){
	%>
    <div class="row"> 
        <%=trans.tslt("Key Behaviours")%><br>
        <ul>
    	<%for(int i=0; i<vKBList.size(); i++) {
			voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
			sKBDef = voKB.getKeyBehaviour();
		%>
         <li><%=sKBDef%></li>
    	<%}%>
        </ul>
    </div>
    <%  }
    }//end if surveyLevel==0%>
</fieldset>
<%} //end if sCompDef after fieldset%>
<%
if(surveyLevel != 0){
	arrID = new int[vKBList.size()];
	for(int i=0; i<vKBList.size(); i++) {
	
		voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
		iPKKB = voKB.getKeyBehaviourID();
		sKBDef = voKB.getKeyBehaviour();
		arrID[i] = iPKKB;
	}
}
%>

<%
for(int i=0; i<arrID.length; i++) {
	int ID = 0;

	if(surveyLevel == 0) {
		ID = arrID[i];
	}else if(surveyLevel == 1){
%>
	<fieldset>
<%
		voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
		ID = voKB.getKeyBehaviourID();
		sKBDef = voKB.getKeyBehaviour();
			
		if(Questionnaire.hasCompleted(assignmentID, ID, RTID.length)) {
%>
	    <div class="row">
	    	<table style='border:0px;width:100%;'>
	    		<tr>
	    			<td style='font-weight:bold;text-align:left;'>
	    				<img src="../images/RaterCompleted.bmp"><%=i+1%>. <%=sKBDef%>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	    <%}else{%>
	    <div class="row"> 
	         <table style='border:0px;width:100%;'>
	    		<tr>
	    			<td style='font-weight:bold;text-align:left;'>
	    				<img src="../images/RaterIncomplete.bmp"><%=i+1%>. <%=sKBDef%>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	    <%}
	    }%>
	    
	    <div class="row">
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
		            int RTSetup = Questionnaire.RTSetupStatus(iRTID, surveyID);
		
		            if(RTSetup != 2) {
		                iCount++;
		            }
		        }//End of Rating Task Vector For-Loop
				%>
		        <table width="100%" border="0"  font style='font-size:10.0pt;font-family:Arial'>
		            <tr align="center" height="5">
		            	<td>&nbsp;</td>
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
		                <td style='height:10px;text-align:center;font-size:12px;' colspan=<%=colspan[lowValue]%>><b><%=scaleDesc%></b></td>
		<%					
		                lowValue++;
		                highValue++;
		               }
		            } //End of Ranking Scale Vector For-Loop
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
		
		            /************************** CHECK EITHER TO SHOW OR HIDE RATING TASK *********************************************/
		            int RTSetup = Questionnaire.RTSetupStatus(iRTID, surveyID);
		            
		            //Rating Task Setup Not 2 Check
		            if(RTSetup != 2) {
		
		            oldRS = Questionnaire.CheckOldResultExist(assignmentID, ID, iRTID);
		            
		            // Updated descriptors displayed when mouse-over Rating Task Code (i.e. CP or CPR) in Questionnaire to include a simple description, Desmond 11 August 2010
		            String descriptor = sRTName;
		            
		            if( sRTCode.equalsIgnoreCase("CPR") ) {
		            	descriptor += " - Level of performance expected of the Target";
		            } else if( sRTCode.equalsIgnoreCase("CP") ) {
		            	descriptor += " - Actual level of performance of the Target now";
		            } else {
		            	// Just use Rating Task Name, so don't do anything here
		            }
		%>
			<tr align="center">
				<td width ="60" bgcolor="99CCFF"><a onMouseOver="showtip(this,event,'<%=descriptor%>')" onMouseout="hidetip()"><%=sRTCode%></td>				
		<%
			int j=0;
			
			int totalValue = 0;
			//Denise 17/12/2009 to find the number of rating value
			for (int x=0; x<totalRS; x++){
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
				if(RTSetup == 1)
					oldRS = (int)Questionnaire.RTScore(iRTID, surveyID, iPKComp);
				
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
		}		else {
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
		<p style='text-align:left;font-size:13px;'><%=trans.tslt("Narrative Comments")%>:</p>
		<%
				int competency = iPKComp;
				int keybehaviour = 0;
			
				if(surveyLevel == 1) {
					keybehaviour = ID;
				}
	
				//System.out.println("comment : " + assignmentID + ", " + competency + ", " + keybehaviour);
				String oldComment = Questionnaire.checkCommentExist(assignmentID, competency, keybehaviour);
				String commentName = "comments" + competency + keybehaviour;
				
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
			<%}%>
			</div>
    	</fieldset>
<%}%>

<input name="totalCompleted" type="hidden" id="totalCompleted" value="<%=complete%>">
<input type="hidden" id="radiobuttonName" name="radiobuttonName" value="<%= radiobuttonName%>">
<input type="hidden" id="radiobuttonID" name="radiobuttonID" value="<%= radiobuttonID%>">
<input type="hidden" id="commentID" name="commentID" value="<%= commentID%>">
<table border="0" width="100%" >
	<tr>
		<td style='width:30%;text-align:center'><%=totalCurrComp + 1%> of <%=totalComp%></td>
		<td align="center">
<%
				Vector<PrelimQuestion> v = PrelimQController.getQuestions(RDE.getSurveyID());
				if(v.size() > 0){
%>
					<input type=button class="commonButton" value="Back to Prelim Questions" name="btnBackToPrelim" onClick="confirmBackToPrelim(this.form)">
<%
				}
%>
			<input type=button class="commonButton" value="Save" name="btnSave" onClick="confirmFinish(this.form, 1)">
			<input type=button class="commonButton" value="Finish" name="btnFinish" onClick="return confirmFinish(this.form, 2)">
		</td>
		<td>
		<%
			int total = totalCurrComp;
			if(total == 0) {
		%>
			<input type=button class="commonButton" name="btnPrev" value="  <  " disabled>

		<% 			
			} else {
		%>
			<input type="button" class="commonButton" name="btnPrev" value="  <  " onclick="javascript:goPrev(this.form)">
		<% 
			} 
			if(total+1 == totalComp) {
		%>
			<input type=button class="commonButton" name="btnNext" value="  >  " disabled>
		<%
			} else {
		%>
			<input type=button class="commonButton" name="btnNext" value="  >  " onClick="javascript:goNext(this.form)">
		<%
			}
		%>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>