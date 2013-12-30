<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page pageEncoding="UTF-8"%>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="server" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="ev" class="CP_Classes.EventViewer" scope="session"/>
<jsp:useBean id="DG" class="CP_Classes.DevelopmentGuide" scope="session"/>
<jsp:useBean id="RR" class="CP_Classes.RaterRelation" scope="session"/>

<jsp:useBean id="C" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="DRA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="Q" class="CP_Classes.Questionnaire" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="RS" class="CP_Classes.RatingScale" scope="session"/>
<jsp:useBean id="SR" class="CP_Classes.SurveyResult" scope="session"/>

<%
String result = request.getParameter("result");

if( result!=null){
	String orgCode=logchk.getOrgCode();
	String username = request.getParameter("txtUsername");
	username = username.toLowerCase();
	String password=request.getParameter("txtPass");
	
	int FKOrganization = Org.getPKOrg(orgCode);
	logchk.setOrg(FKOrganization);
	
	//check if the username of the correct format
	String sUser = logchk.getWithoutQuoteMarks(username);
	String usernameQuoted=logchk.getSingleQuoted(sUser);
   	
	String [] sInfo = new String[9]; 
    sInfo = CE_Survey.getUserInfo(usernameQuoted, orgCode);
	
	int iLoggedIn = 0; //0= Not logged in, 1= Logged in
    
    if(sInfo != null && sInfo[1] != null)
    { 
    	int PKUser = Integer.parseInt(sInfo[0]);
    	String LoginName = sInfo[1];
    	String db_password = sInfo[2];
    	int isEnabled = Integer.parseInt(sInfo[3]);
    	int CompanyID = Integer.parseInt(sInfo[4]);
    	String CompanyName = sInfo[5];
    	int PKOrg = Integer.parseInt(sInfo[6]);
     	String OrgName = sInfo[7];
		String OrgCode = sInfo[8];
		
		if(isEnabled == 1)
		{
	    	if(db_password.equals(password))
	    	{
	    		iLoggedIn = 1; //Logged in
		     	session.setAttribute("username", username);
		     	logchk.setPKUser(PKUser);
				logchk.setCompany(CompanyID);
				logchk.setCompanyName(CompanyName);
				logchk.setOrg(PKOrg);
				// Edited by Eric Lu 21/5/08
				// Sets selfOrg in Login.java
				logchk.setSelfOrg(PKOrg);
				logchk.setOrgCode(OrgCode);
				
				String desc = "Log into 360 System through iphone";
				String action = "Login";
				
				/*
			     *Change: uncommented the line to insert event entry
			     *Reason: to enable logging of iphone log in
			     *Updated by : Liu Taichen
			     *Updated on: 29 May 2012
			     */
				ev.addRecord(action, "Login through iphone", desc, LoginName, CompanyName, OrgName);
				
				String [] UserDetail = new String[14];
				UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
				
				if(UserDetail[8].equals("No Access")){
				%>
					<script>
			        alert("<%=trans.tslt("Your user account has been disabled. Please contact your administrator")%>.");
			        window.location.href="index.jsp";
					</script>
				<%
				}else{
				%>
					<jsp:forward page="surveyIndex.jsp"/>	
				<%
				}
			}
			else 
			{%>
		        <script>
			        alert("<%=trans.tslt("You have entered wrong combination of Login ID and Password")%>.\n<%=trans.tslt("Please note that Password is case-sensitive")%>.");
			        window.location.href="index.jsp";
				</script>
		<%}
		}else{
		%>
			<script>
				alert("<%=trans.tslt("Your user account has been disabled. Please contact your administrator")%>.");
				window.location.href="index.jsp";
			</script>
		<%}
   	}
   	if(iLoggedIn == 0)
   	{ %>
        <script>
	        alert("<%=trans.tslt("You have entered wrong combination of Login ID and Password")%>.\n<%=trans.tslt("Please note that Password is case-sensitive")%>.");
	        window.location.href="index.jsp";
        </script>
	<%	}
}

String logout = request.getParameter("logout");
if(logout!=null && "1".equals(logout)){
	
	String [] UserInfo = user.getUserDetail(logchk.getPKUser(), 1);
	// add to tblEvent
	try {
		ev.addRecord("Logout", "Logout", "Logout from i360 by iPhone.", UserInfo[2], UserInfo[11], UserInfo[10]);
	}catch(SQLException SE) {}
	
	session.removeAttribute("username");  
	String value =" "; 
	
	logchk.setPKUser(0);
	logchk.setOrg(0);
	logchk.setCompany(0);
	logchk.setUserType(0);
	
	user.set_selectedUser(0);
	user.set_LoginName(value);
	
	assignTR.setGroupID(0);
	assignTR.setTargetID(0);
	assignTR.set_selectedTargetID(0);
	assignTR.set_selectedRaterID(0);
	assignTR.set_NameSequence(0);
	assignTR.set_selectedAssID(0);
	
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.set_survOrg(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	CE_Survey.set_SurvRating(0);
	CE_Survey.set_GroupID(0);
	CE_Survey.set_CompLevel(0);
	
	DG.setSurvey_ID(0);
	DG.setType(0);
	
	RR.setRelHigh(0);
	RR.setRelSpec(0);
	
	
	C.setSurveyID(0);
	C.setGroupSection(0);
	C.setTargetID(0);
	C.setRaterID(0);
	
	Comp.setComp("");
	Comp.setOrgID(0);
	Comp.setPKComp(0);
	
	DRA.setFKCom(0);
	DRARes.setFKComp(0);
	DRARes.setResType(0);
	
	ev.setSortType(0);
	ev.setCompName("");
	ev.setOrgName("");
	
	KB.setFKComp(0);
	KB.setIsComp(0);
	KB.setAdded(0);
	KB.setKBLevel(0);
	
	Q.setJobPost("");
	Q.setName("");
	Q.setTotalComp(0);
	Q.setTotalCurrComp(0);
	Q.setAssignmentID(0);
	Q.setFutureJob("");
	Q.setTimeFrame("");
	Q.setSurveyLevel(0);
	Q.setChecked(0);
	Q.setCurrID(0);
	
	QR.setSurveyID(0);
	QR.setJobPostID(0);
	QR.setDivisionID(0);
	QR.setDepartmentID(0);
	QR.setGroupID(0);
	QR.setTargetID(0);
	QR.setRaterID(0);
	QR.setPageLoad(0);
	
	RDE.setSurveyID(0);
	RDE.setGroupID(0);
	RDE.setTargetID(0);
	RDE.setJobPost("");
	RDE.setRaterID(0);
	RDE.setPageLoad(0);
	
	RS.setRS(0);
	RS.setScaleID(0);
	RS.setRSType("");
	
	SR.setSurveyID(0);
	SR.setGroupID(0);
	SR.setTargetID(0);
	SR.setRaterID(0);
	SR.setSurveyLevel(0);
	SR.setAssignmentID(0);	
%>
	<jsp:forward page="index.jsp"/>
<%}%>