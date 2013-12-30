<%@ page import="java.sql.*,
                 java.io.*,
                 javazoom.upload.*,
                 java.lang.String,
                 java.util.Vector,
                 CP_Classes.vo.votblUserRelation,
				 CP_Classes.vo.votblOrganization,
				 CP_Classes.vo.votblSurvey,
				 CP_Classes.AssignTarget_Rater,
				 CP_Classes.vo.voUser,
				 CP_Classes.vo.voCoachSession"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="email" class="CP_Classes.ReminderCheck" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="user_Jenty" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="CoachingEmail" class="Coach.CoachingEmail" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
</head>
<script language = "javascript">
function proceed(form,field)
{
	form.action="EmailNotification.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function proceed(form)
{
	form.action="EmailNotification.jsp?proceed="+"1";
	form.method="post";
	form.submit();
}

function preview(form, field)
{
	//field is selsur
	if(field.value != 0) {
		var getRadio = document.getElementsByName("SendToAllorIndividual");
		var selectedRadio=-1;
		
		//Get the selected radio button
		for(i = 0; i < getRadio.length; i++){
			if(getRadio[i].checked){
				selectedRadio = i;
			}
		}
		//alert("SendToAllorIndividual:"+selectedRadio);
		// 0: send to all
		// 1: send to individual
		if((selectedRadio == 0)){ // send to all
			form.action="EmailNotification.jsp?preview=" + field.value;
			form.method="post";
			form.submit();
		}
		else{
			if(form.selTarget.value != 0){
				form.action="EmailNotification.jsp?preview=" + field.value;
				form.method="post";
				form.submit();
			}
			else{
				alert("Please select Target");
			}
		}
		
	} else {
		alert("Please select Survey");
	}
}
function advancedNotification(form){
		var myWindow=window.open('EmailNotificationUserSelection.jsp?action='+1,'windowRef','scrollbars=yes, width=600, height=600');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'EmailNotificationUserSelection.jsp?action='+1;
	}
function advancedReminder(form){
		var myWindow=window.open('EmailNotificationUserSelection.jsp?action='+2,'windowRef','scrollbars=yes, width=600, height=600');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'EmailNotificationUserSelection.jsp?action='+2;
	}
</script>




<body>
	<%
	String username=(String)session.getAttribute("username");
	int errorFlag = 0;
	 if(!logchk.isUsable(username)){
	  %> <font size="2">
	<script>
	parent.location.href = "../index.jsp";
	</script>
<%  } 
  else 
  {   
  
	if(request.getParameter("proceed") != null)
	{ 
		int OrgPK=Integer.parseInt(request.getParameter("selOrg"));
		CE_Survey.set_survOrg(OrgPK);
 		logchk.setOrg(OrgPK);
 		int SurveyID=Integer.parseInt(request.getParameter("selSurvey"));
 		CE_Survey.setSurvey_ID(SurveyID);
 		System.out.println("JSP SurveyID:"+SurveyID);
 		if(request.getParameter("selCoachingSession")!=null){
 		CoachingEmail.setSelectedSession(Integer.parseInt(request.getParameter("selCoachingSession")));
 		}
	}

	if(request.getParameter("preview") != null)
	{
		
		//System.out.println("Entered preview");
		int SurveyID = Integer.parseInt(request.getParameter("preview"));
		//System.out.println("SurveyID:"+SurveyID);
		
	
		int SupID = new Integer(request.getParameter("selTarget")).intValue();
		
		//System.out.println("SupID"+SupID);
	
		System.out.println("checkBoxReminder:"+request.getParameter("checkBoxReminder"));
			
		//notification
		if(request.getParameter("checkBoxReminder")==null ? true : false)	{
			//for all people-notification
			if("1".equalsIgnoreCase(request.getParameter("SendToAllorIndividual")) ? true : false){
				//send to all candidate
				int iNameSequence = user_Jenty.NameSequence(logchk.getOrg());
				int surveyOrg = CE_Survey.get_survOrg();
				int orgID = logchk.getOrg();
				AssignTarget_Rater targetRater = new AssignTarget_Rater();
				Vector<voUser> vec = targetRater.getUserList(iNameSequence , surveyOrg , orgID , ""+SurveyID);
				CoachingEmail.setSelectedUsers(vec);
				System.out.println("send notificaiton to all");
				errorFlag = CoachingEmail.notificationAll(vec,CE_Survey.get_survOrg(),SurveyID);
			}else{
				//send to single candidate
				System.out.println("send notificaiton to:"+SupID);
				errorFlag = CoachingEmail.notificationSingle(SupID, CE_Survey.get_survOrg());
			}
		
		//for single candidate-notification
		}
		else{ //reminder
			if("1".equalsIgnoreCase(request.getParameter("SendToAllorIndividual")) ? true : false){
				//for all people-reminder
				System.out.println("send reminder to all");
				int iNameSequence = user_Jenty.NameSequence(logchk.getOrg());
				int surveyOrg = CE_Survey.get_survOrg();
				int orgID = logchk.getOrg();
				AssignTarget_Rater targetRater = new AssignTarget_Rater();
				Vector<voUser> vec = targetRater.getUserList(iNameSequence , surveyOrg , orgID , ""+SurveyID);
				CoachingEmail.setSelectedUsers(vec);
				//exclude the user who have signed
				vec=CoachingEmail.filterReminderUsers(vec, CE_Survey.get_survOrg(), CE_Survey.getSurvey_ID());
				errorFlag = CoachingEmail.reminderAll(vec,surveyOrg,SurveyID);
			}
			else{//for single candidate-reminder
				System.out.println("send reminder to:"+SupID);
				errorFlag = CoachingEmail.reminderSingle(SupID, CE_Survey.get_survOrg());
			}
		}	
		if (errorFlag == -1 &&request.getParameter("checkBoxReminder")==null) {
				%>
 					<script>alert('You need to create a Coaching notification template before sending ')</script>  
				<%			
				}	
				else if (errorFlag == -1 &&request.getParameter("checkBoxReminder")!=null) {
				%>
 					<script>alert('You need to create a Coaching reminder template before sending ')</script>  
				<%			
				}	
				else if (errorFlag>=0) {
				%>
 					<script>alert('Emails Send Successfully')</script>  
				<%			
				}	
				else if (errorFlag==-100) {
					%>
	 					<script>alert('Sending Emails failed, please view sent failed Email')</script>  
					<%			
					}	
 			System.out.println("Ending... Send emails to targets");
 	}
 %>

	
<form action="EmailNotification.jsp" method="post">

<table border="0" width="500" cellspacing="0" cellpadding="0">
	<tr>
		<td><b>
		<font face="Arial" size="2" color="#000080">Send Coaching Notification Emails</font></b></td>
		<%
		if (errorFlag>0){
			%>
 					<font face="Arial" size="2" color="#000080">Send to <%=errorFlag%> Recipient</font></b></td>
			<%	
		}
 		%>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="500" cellspacing="0" cellpadding="0" bgcolor="#FFFFCC" bordercolor="#3399FF">
		<tr>
		<td width="117" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
		<td width="228" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="154" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp; </td>
	</tr>
		<tr>
		<td width="117" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<b><font face="Arial" size="2">&nbsp;Organisation:</font></b></td>
		<td width="228" style="border-style: none; border-width: medium">
		<p align="left">		

<%
	
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){ %>
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value="0" selected>ALL</option>
		<%
		Vector vOrg = logchk.getOrgList(logchk.getCompany());

		for(int i=0; i<vOrg.size(); i++)
		{
			votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
			int PKOrg = vo.getPKOrganization();
			String OrgName = vo.getOrganizationName();

			if(logchk.getOrg() == PKOrg)
			{ %>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
			<% } else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		} 
	} else { %>
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select><font size="2"> </font>
</td>
		<td width="154" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> </td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><b><font face="Arial" size="2">&nbsp;Survey:</font></b></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onChange="proceed(this.form)">
    	<option selected value=0>Please select a survey</option>
<%
	int iSurveyID=0;
	if((request.getParameter("selSurvey")) != null)
	{	
		if ((request.getParameter("selSurvey")).equals(""))
			iSurveyID = 0;
		else
		{
			iSurveyID = Integer.parseInt((request.getParameter("selSurvey")));	
		}
	}
	Vector v = CE_Survey.getSurveys(logchk.getCompany(), CE_Survey.get_survOrg());
		
	for(int i=0; i<v.size(); i++)
	{
		votblSurvey vo = (votblSurvey)v.get(i);
		
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
		
			if(iSurveyID!= 0 && iSurveyID== Surv_ID)
			{%>
				<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>	
		<%	}else
			{%>
				<option value=<%=Surv_ID%>><%=Surv_Name%></option>
			<%	}
	}%>
</select></td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
	</tr>
	
	<tr>
		<td width="137" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><b><font face="Arial" size="2">&nbsp;Session:</font></b></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selCoachingSession" onChange="proceed(this.form)">
    	<option selected value=0>Please select a Coaching Session Name</option>
<%
	if((request.getParameter("selSurvey")) != null)
	{	
		if ((request.getParameter("selSurvey")).equals(""))
			iSurveyID = 0;
		else
		{
			iSurveyID = Integer.parseInt((request.getParameter("selSurvey")));	
		}
	}
	Vector<voCoachSession> sessions=CoachingEmail.getSessionsBySurveyID(iSurveyID);
		
	for(int i=0; i<sessions.size(); i++)
	{
		voCoachSession vo = (voCoachSession)sessions.elementAt(i);
		
		int sessionPK = vo.getPK();
		String sessionName = vo.getName();
		
			if(sessionPK!= 0 && CoachingEmail.getSelectedSession()== sessionPK)
			{%>
				<option value=<%=sessionPK%> selected><%=sessionName%></option>	
		<%	}else
			{%>
				<option value=<%=sessionPK%>><%=sessionName%></option>
			<%	}
	}%>
</select></td>
	</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp; </td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<b><font face="Arial" size="2">
		<% if(request.getParameter("checkBoxReminder")!=null) 
		{	
			//System.out.println("Setting checkBoxReminder to checked");
		%>
			<input type="checkbox" name="checkBoxReminder" value="1" checked onChange="proceed(this.form)">
		<%
		} 
		else 
		{ 
			//System.out.println("Setting checkBoxReminder to Unchecked");
			%>
			<input type="checkbox" name="checkBoxReminder" value="0" onChange="proceed(this.form)">
		<%}%>
		
		&nbsp;</font></b></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
   
    	<b><font face="Arial" size="2">Is Reminder Emails</font></b></td>
			</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<input type="radio" value="1" name="SendToAllorIndividual" checked></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<b><font face="Arial" size="2">Send Notification Emails to all Targets</font></b>
		</td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font size="2">		
		<input type="radio" value="2" name="SendToAllorIndividual"></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<b><font face="Arial" size="2">Send Notification Email to Specific Target</font></b></td>
		</tr>
		<tr>
		<td width="117" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p></td>
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   		<font face="Arial" size="2">&nbsp;Target:</font>
    	<select size="1" name="selTarget">
    	<option selected value=0>Please Select a Recipient</option>
<%
	if((request.getParameter("selSurvey")) != null){
	
		int iNameSequence = user_Jenty.NameSequence(logchk.getOrg());
		int surveyOrg = CE_Survey.get_survOrg();
		int comp = logchk.getCompany();
		String selSurvey = (request.getParameter("selSurvey"));
	
		AssignTarget_Rater targetRater = new AssignTarget_Rater();
		Vector vec = targetRater.getUserList(iNameSequence , surveyOrg , comp , selSurvey);
		CoachingEmail.setSelectedUsers(vec);
		if(request.getParameter("checkBoxReminder")!=null){
		 System.out.println("get new user list");
		 vec=CoachingEmail.filterReminderUsers(vec, surveyOrg, CE_Survey.getSurvey_ID());
		 CoachingEmail.setSelectedUsers(vec);
		}
	
	
		for(int i=0; i<vec.size(); i++)
		{
			voUser vo = (voUser)vec.get(i);
			
			int iPKUser = vo.getPKUser();
			String iGivenName = vo.getGivenName();
			String iFamilyName = vo.getFamilyName();
		
		if(iNameSequence == 0)
		{
			//Family Name First
			%>
				<option value=<%=iPKUser%> ><%=iFamilyName + ", " + iGivenName%></option>
			<%
		}
		else
		{
			//Given Name First
			%>
				<option value=<%=iPKUser%> ><%=iGivenName + ", " + iFamilyName%></option>
			<%
		}
	}
} 
	%>
</select></td>
	
	
		</tr>
		<tr>
		<td height="26">
		</td>
		</tr>
		
		<tr>
		<td width="116" align="left" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
		<p align="right">
		
		<%if((request.getParameter("selCoachingSession")!=null&&request.getParameter("selCoachingSession").equalsIgnoreCase("0"))
		||request.getParameter("selSurvey")!=null&&request.getParameter("selSurvey").equalsIgnoreCase("0")){
		
			if(request.getParameter("checkBoxReminder")!=null){
		%>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<input type="button" value="Advanced Reminder Emails" name="Advanced" onclick="advancedReminder(this.form)" disabled >
		</td>
		<%
		}
		else{
		%>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<input type="button" value="Advanced Notification Emails" name="Advanced"  onclick="advancedNotification(this.form)" disabled>
		</td>
		<%
		}
		}else{
			if(request.getParameter("checkBoxReminder")!=null){
		%>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<input type="button" value="Advanced Reminder Emails" name="Advanced" onclick="advancedReminder(this.form)">
		</td>
		<%
		}
		else{
		%>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> 
		<input type="button" value="Advanced Notification Emails" name="Advanced"  onclick="advancedNotification(this.form)">
		</td>
		<%
		}
		
		
		}
		 %>
		 
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
		<input type="button" value="Send Emails" name="btnPreview" style="float: left" onClick="preview(this.form, this.form.selSurvey)">
		</td>
	</tr>
	<tr>
		<td width="116" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="228" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="155" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
	</tr>
	
	<tr>
	<font face="Arial" size="2" color="#000080">Tips: Please select Organisation, Survey and Session before proceeding to Advanced Selection</font></b></td>
</tr>
</table>
</form>
<%	}	%>
<%@ include file="../Footer.jsp"%>

</body>
</html>