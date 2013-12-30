<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/> 
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/> 
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<%@ page import="CP_Classes.vo.voCompetency"%>
<%@ page import="CP_Classes.vo.voKeyBehaviour"%>
<%@ page import="CP_Classes.vo.voRatingTask"%>

<html>
<head>

<title>Display Questionnaires</title>
</head>

<body>

<%
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();
	int compID = logchk.getCompany();
	int orgID = logchk.getOrg();
	
	
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
		
		
	int iPKComp = 0;
	int iPKKB = 0;
	
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
<i>
<%		}
	}
	
	if(totalCurrComp == 0) {
		Questionnaire.setCurrID(iPKComp);
	}


	if(totalCurrComp == 0 && start == 1) {	
		Questionnaire.setStartID(0);
		int total = Questionnaire.TotalResult(assignmentID, iPKComp);
		
		//System.out.println(assignmentID + "---" + iPKComp+ "---" + iPKKB);
		
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
		int go = Integer.parseInt(request.getParameter("go"));
	
		pk = Questionnaire.getCurrID();
		
		//System.out.println("------>" + pk);
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
				
	
		System.out.println("go : " + go + " ---totalCurrComp : " + totalCurrComp + " ----totalComp : "+ totalComp);
		if( (go == 1 && totalCurrComp < totalComp) || (go == 2 && totalCurrComp >= 0) || go == 3 ) {
		
			System.out.println("OKKKKKKKK--------" + totalRatingTask);
			
			for(int rs=0; rs<totalRatingTask; rs++) 
			{
				int RTStatus = Questionnaire.RTSetupStatus(RTID[rs], surveyID);
				
				System.out.println("RTStatus : " + RTStatus + " --- arrPK : " + arrPK.length);
				if(RTStatus != 0) 
				{
					for(int i=0; i<arrPK.length; i++) {
						float oldResult = Questionnaire.CheckOldResultExist(assignmentID, arrPK[i], RTID[rs]);
						
						System.out.println("old result : " + oldResult);
						
						if(oldResult <= 0) {
							try {
								float score = 0;
								
								if (surveyLevel == 0) 
									score = Questionnaire.RTScore(RTID[rs], surveyID, arrPK[i]);
								 else {
									score = Questionnaire.RTScoreBehv(RTID[rs], surveyID, arrPK[i]);
									System.out.println("OK RTStatus == 0");
								}
								Questionnaire.addResult(assignmentID, arrPK[i], RTID[rs], score); 
							}catch(SQLException SE) {}
						}
					}
					
				} else {									
					for(int i=0; i<arrPK.length; i++) {	
							
						String [] scaleValue = request.getParameterValues("rbtnScale" + arrPK[i] + RTID[rs]);
						boolean a = scaleValue != null;
						System.out.println("++++++" + a);
						if(scaleValue != null)
						{
							
							for(int j=0; j<scaleValue.length; j++) {
								System.out.println("scale value : "+ scaleValue[j]);
								if(scaleValue[j] != null){
									System.out.println(scaleValue[j]);
									float value = Float.parseFloat(scaleValue[j]);
									float oldResult = Questionnaire.CheckOldResultExist(assignmentID, arrPK[i], RTID[rs]);
									
									System.out.println("*************************"+oldResult);
									if(oldResult < 0) {
										try {
											Questionnaire.addResult(assignmentID, arrPK[i], RTID[rs], value); //add date
										}catch(SQLException SE) {}
									}								
									else if(oldResult >= 0 && oldResult != value) {
										System.out.println("OK RTStatus == 0");
										Questionnaire.updateOldResult(assignmentID, arrPK[i], RTID[rs], value);
									}
								}
							}
						} // end if scaleValue is not null				
					} // end for loop				
				}
			}
		
			if((rCode.equals("SELF") && selfIncluded == 1) || included == 1) 
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
						value = Database.SQLFixer(value);
							
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
				
				Questionnaire.setCurrID(iPKComp);
				
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
						
				
					if(commentInput == 0) {
						%>
						<script>
							if(confirm("<%=trans.tslt("No comments included. Do you want to finish")%>?"))
								window.location.href = "Questionnaires.jsp?finishall=1";
						</script>
						<%				
					}else {		
						%>
						<script>
							if(confirm("<%=trans.tslt("Once you click FINISH, no more ammendment can be done")%>."))
								window.location.href = "Questionnaires.jsp?finishall=1";
						</script>
						<%		
					}
				} else {
					String temp = surveyName + "(S); " + name + "(T); " + raterName + "(R); Incomplete";
					//EV.addRecord("Finish", "Questionnaire", temp, UserInfo[2], UserInfo[11], UserInfo[10]);
					Questionnaire.setStartID(1);
					%>
					<script language="javascript">
					alert("<%=trans.tslt("You have not completed the questionnaire. Your rating will be saved and your assignment status for this target will be set to incomplete")%>.");
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
					
					Questionnaire.setCurrID(iPKComp);
					
					if(surveyLevel == 0)
						vKBList = KB.getKBList(iPKComp, 0, compID, orgID);
					else 
						vKBList = Questionnaire.getKBSurvey(surveyID, iPKComp);
		
				}
			}
		}

	}
%>

</i>
<form action="post">
<table width="610" border="1" font style='font-size:12.0pt;font-family:Arial'>
<th align="center" bgcolor="navy"><b><font style='color:white'><%=trans.tslt("Questionnaire")%><span class="style2"></span></font></b></th>
</table>
<p></p>

<table width="610" class="fontstyle">
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
<table>	

<table width="610" class="fontstyle">	
	<tr><td width="157"><%=trans.tslt("Competency Statement")%></td>
		<td width="4"> : </td>
		<td><textarea name="compStatement" cols="82" rows="3" readonly><%=sCompDef%></textarea></td>
	</tr>
	
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
%>
	<tr>
	<td><%=trans.tslt("Key Behaviours")%></td>
	<td>:</td>
	<td><table border="1" font style='font-size:10.0pt;font-family:Arial' width="515" height="5">
<tr>
<td>
<div style='width:504px; height:60px; z-index:1; overflow:auto'>
<table border="1" font style='font-size:10.0pt;font-family:Arial' width="487" bgcolor="#FFFFCC">
<%
	} else {
		arrID = new int[vKBList.size()];
	}
	
	for(int i=0; i<vKBList.size(); i++) {
	
		voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
		iPKKB = voKB.getKeyBehaviourID();
		sKBDef = voKB.getKeyBehaviour();
		
		if(surveyLevel == 0) {
%>
		<tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'">
		<td><%=sKBDef%></td>
	</tr>

<%		
		} else {
			arrID[i] = iPKKB;
		}
	}

	if(surveyLevel == 0) {

%>
	</table></div></td>
	
	</tr></table></td></tr>

<%
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
		
		} else if(surveyLevel == 1) { 
			voKeyBehaviour voKB = (voKeyBehaviour)vKBList.elementAt(i);
			ID = voKB.getKeyBehaviourID();
			sKBDef = voKB.getKeyBehaviour();
				
			if(Questionnaire.hasCompleted(assignmentID, ID, RTID.length)) {
%>			
			<tr bgcolor="navy" font style='color:white'>
			<td bordercolor="#000080"><%=i+1%>. <%=sKBDef%></font></td>
			<td align ="right" bordercolor="#000080"><img src="images/RaterCompleted.bmp"></td>

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
	
		for(int k=0; k<RSID.length; k++) {
			
			if(RSID[k] == -1) 
				break;
		
		/************************** CHECK EITHER TO SHOW OR HIDE RATING TASK *********************************************/
			int RTSetup = Questionnaire.RTSetupStatus(RSID[k], surveyID);
			//System.out.println(RT[RS] + "---" + RTSetup);
			if(RTSetup != 2) {
					
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

				for(int j=0; j<vRScale.size(); j++) {	
				    String [] sRS = new String[3];
				    sRS = (String[])vRScale.elementAt(j);
				    	
					low[lowValue] = Integer.parseInt(sRS[0]);
					high[highValue] = Integer.parseInt(sRS[1]);
					scaleDesc = sRS[2];
		
					colspan[lowValue] = high[highValue] - low[lowValue] + 1;
		

%>
				<td height="10" width="80" bgcolor="#CCCCCC" colspan="<%=colspan[lowValue]%>" align="center"><b><%=scaleDesc%></b></td>
					
<%
				
					lowValue++;
					highValue++;
				}
					
%>
	</tr>

<%
				float oldRS = 0;
				
				Vector vRatingTask = Questionnaire.getSurveyRatingTask(surveyID, RSID[k]);
				
				int iRTID = 0;
				String sRTName = "";
				String sRTCode;
				String colours [] = {"#FFFFCC", "#CCFFCC", "#CCFFCC"};
			
				for(int l=0; l<vRatingTask.size(); l++) { 
				
					voRatingTask voRT = (voRatingTask)vRatingTask.elementAt(l);
				
					iRTID = voRT.getRatingTaskID();
					sRTName = voRT.getRatingTaskName();
					sRTCode = voRT.getRatingCode();
					
					oldRS = Questionnaire.CheckOldResultExist(assignmentID, ID, iRTID);
					//99CCFF
%>
	<tr align="center">
		<td width ="60" bgcolor="99CCFF"><a onMouseOver="showtip(this,event,'<%=sRTName%>')" onMouseout="hidetip()"><%=sRTCode%></td>				
	
<%
					int j=0;
					while(j < totalRS) {
						lowValue = low[j];
						highValue = high[j];

						do {
%>	
<td bgcolor="<%=colours[l]%>">
<%		
							if(RTSetup == 1) 		//Fixed by admin in Survey Setup and display in Questionnaire
								oldRS = (int)Questionnaire.RTScore(iRTID, surveyID, iPKComp);
			
							if(oldRS >= 0 && oldRS == lowValue) {
								if(RTSetup == 1) {
%>							
							<input type="radio" name="<%="rbtnScale"+ID+iRTID%>" value="<%=lowValue%>" disabled>
<%		
								} else {
%>			
							<input type="radio" name="<%="rbtnScale"+ID+iRTID%>" value="<%=lowValue%>" checked>
<%		
								}
						
							} else {
			
								if(RTSetup == 1) {
%>			
					<input type="radio" name="<%="rbtnScale"+ID+iRTID%>" value="<%=lowValue%>" disabled>
<%		
								} else {
%>			
					<input type="radio" name="<%="rbtnScale"+ID+iRTID%>" value="<%=lowValue%>">
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
					} while(lowValue <= highValue);
						j++;
					}		//end do
%>
				</tr>
				
<%
				} // end RatingTask
%>				
			
				</table>
				<p></p>
				
<% 
			} //end if
		
		} // end for
		
		
%>

<%
	
				if((rCode.equals("SELF") && selfIncluded == 1) || included == 1) {
	
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
%>

					<textarea name=<%=commentName%> cols="96" rows="5" style="font-size: 10pt; font-family: Arial"><%=oldComment%></textarea>
<% 				} %>

<!------------------------------------------------------- ADD COMMENTS ------------------------------------------------------------------->



<strong>
</td></tr>


<%		
	
	} // end Competency / KBList
%>

	</table>
</form>
</body>
</html>

