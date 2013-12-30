<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import="CP_Classes.User,
				 CP_Classes.vo.voUser,
				 CP_Classes.vo.voUserDemographic,
				 CP_Classes.vo.voJobFunction,
				 CP_Classes.vo.voJobLevel,
				 CP_Classes.vo.voGender,
				 CP_Classes.vo.voAge,
				 CP_Classes.vo.voEthnic,
				 CP_Classes.vo.voLocation,
				 java.util.Vector"%> 
<html>
<head>
<title>Demographic Entry</title>

<meta http-equiv="Content-Type" content="text/html">

<style type="text/css">
<!--
body {
	background-color: white; //#eaebf4;
}
-->
</style></head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="DemographicEntry" class="CP_Classes.DemographicEntry" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>

<%@ page import="CP_Classes.PrelimQuestion"%>

<script language="javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function openQuestionnaire(form)
function openQuestionnaire(form)
{	
	form.action= "DemographicEntry.jsp?edit=1";
	form.method = "post";
	form.submit();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancel(form)
function cancel(form)
{	
	var usertype = <%=logchk.getUserType()%>
	if(usertype == 4) {
		window.location.href = "RatersToDoList.jsp";
		//window.close();
//		opener.location = "RatersToDoList.jsp"
	}else {
		window.location.href = "Questionnaire.jsp";
		//window.close();
		//opener.location = "Questionnaire.jsp"
	}
}
</script>

<%
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
	int raterID = RDE.getRaterID();
	int surveyID = RDE.getSurveyID();

	String jobFunction="", ageRange="", gender="", ethnicGroup="", location="", department="";
	String jobLevel=""; 
	int age=0,ethnic=0,gdr=0,jobftn=0,joblvl=0,loc=0, pkDemographic = 0;
	
		int orgID = logchk.getOrg();	
		int compID = logchk.getCompany();
	
		if(raterID == 0)
			raterID = logchk.getPKUser();
			
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 

		User user = new User();
		voUser voU = user.getUserInfo(raterID);
		int fkRater = voU.getPKUser();
		
		//ResultSet ID = Database.getRecord("Select * from [User] where PKUser = '" + raterID + "'");
		//ID.next();
		//int fkRater = ID.getInt(1);
			//int deptID = ID.getInt(2);
		
		voUserDemographic rater = RDE.getRaterInfo(raterID);
		
		pkDemographic = rater.getPKUserDemographic();
		age           = rater.getFKAge();
		ethnic        = rater.getFKEthnic();
		gdr           = rater.getFKGender();
		jobftn        = rater.getFKJobFunction();
		joblvl        = rater.getFKJobLevel();		//pk
		loc           = rater.getFKLocation();

		ageRange    = DemographicEntry.AgeRange(age);
		ethnicGroup = DemographicEntry.Ethnic(ethnic);
		gender      = DemographicEntry.Gender(gdr);
		jobFunction = DemographicEntry.JobFunction(jobftn);
		jobLevel    = DemographicEntry.JobLevel(joblvl);
		location    = DemographicEntry.Location(loc);
		//department  = DemographicEntry.Department(deptID);			
			
		Vector JobFunction = DemographicEntry.getAllJobFunction(orgID);
		Vector JobLevel    = DemographicEntry.getAllJobLevel(orgID);
		Vector AgeRange    = DemographicEntry.getAllAgeRange(orgID);
		Vector Gender      = DemographicEntry.getAllGender(orgID);
		Vector EthnicGroup = DemographicEntry.getAllEthnic(orgID);
		Vector Location    = DemographicEntry.getAllLocation(orgID);
		//ResultSet Department  = DemographicEntry.getAllDepartment(orgID);

		if(request.getParameter("edit") != null) {			
			String jobFunct [] = request.getParameterValues("jobFunction");
			if(jobFunct != null)
				for(int i=0; i<jobFunct.length;i++)
					if(Integer.parseInt(jobFunct[i]) != jobftn)
						jobftn = Integer.parseInt(jobFunct[i]);
			
			String jobLvl [] = request.getParameterValues("jobLevel");
			if(jobLvl != null)
				for(int i=0; i<jobLvl.length;i++)
					if(Integer.parseInt(jobLvl[i]) != joblvl)
						joblvl = Integer.parseInt(jobLvl[i]);
			
			String ageR [] = request.getParameterValues("ageRange");
			if(ageR != null)
			for(int i=0; i<ageR.length;i++)
				if(Integer.parseInt(ageR[i]) != age)
					age = Integer.parseInt(ageR[i]);
					
			String gndr [] = request.getParameterValues("gender");
			if(gndr != null)
			for(int i=0; i<gndr.length;i++)
				if(Integer.parseInt(gndr[i]) != gdr)
					gdr = Integer.parseInt(gndr[i]);
					
			String ethnc [] = request.getParameterValues("ethnicGroup");
			if(ethnc != null)
			for(int i=0; i<ethnc.length;i++)
				if(Integer.parseInt(ethnc[i]) != ethnic)
					ethnic = Integer.parseInt(ethnc[i]);
					
			String lctn [] = request.getParameterValues("location");
			if(lctn != null)
			for(int i=0; i<lctn.length;i++)
				if(Integer.parseInt(lctn[i]) != loc)
					loc = Integer.parseInt(lctn[i]);
			
			Vector<PrelimQuestion> v = PrelimQController.getQuestions(surveyID);
			try {
				DemographicEntry.deleteRecord(pkDemographic);				
				boolean add = DemographicEntry.InsertRecord(raterID, age, ethnic, gdr, jobftn, joblvl, loc);
				if(v.size() > 0){
%>					<script>
						window.location.href = "PrelimQAnswers.jsp";
					</script>
<%				}else{
%>					<script>
						window.location.href = "Questionnaire.jsp";
					</script>
<%				}
			}catch(SQLException SE) {
				if(v.size() > 0){
%>					<script>
						window.location.href = "PrelimQAnswers.jsp";
					</script>
<%				}else{
%>					<script>
						window.location.href = "Questionnaire.jsp";
					</script>
<%				}
			}
			
		}
%>

<form name="DemographicEntry" method="post">
<table width="391" border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="115" align="right"><%= trans.tslt("Job Function") %> : </td>
    <td width="10">&nbsp;</td>
    <% int t = 0; 
		int exist = 0;
	%>
	<td width="252">
	<%	exist = DemographicEntry.JobFunctionSelected(surveyID);
		if(exist == 0) {
	%>
		<select name="jobFunction" disabled>
	<% } else { %>	
      <select name="jobFunction">
	  <% 
	    /*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
	  	for(int i=0; i<JobFunction.size(); i++){
			voJobFunction vo = (voJobFunction) JobFunction.get(i);
			
	  		int funcID = vo.getPKJobFunction();
			String name = vo.getJobFunctionName();

			if(jobFunction.equals(name)) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 	  	
	  	} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   } 
		   }
	  %>
      </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><%= trans.tslt("Job Level") %> :</td>
    <td>&nbsp;</td>
    <td>
	
	<%	
		exist = DemographicEntry.JobLevelSelected(surveyID);
		if(exist == 0) {
	%>
		
		<select name="jobLevel" disabled>
	<% } else { %>	
      <select name="jobLevel">
	  <% 
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
	  	for(int i=0; i<JobLevel.size(); i++){
			voJobLevel vo = (voJobLevel) JobLevel.get(i);
			
	  		int funcID = vo.getPKJobLevel();
			String name = vo.getJobLevelName();
			
			if(joblvl == funcID) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   } 
		   }
	  %>
    </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><%= trans.tslt("Age Range") %> : </td>
    <td>&nbsp;</td>
    <td>
	<%	exist = DemographicEntry.AgeSelected(surveyID);
		if(exist == 0) {
	%>
		<select name="ageRange" disabled>
	<% } else { %>	
      <select name="ageRange">
	  <% 
	  	int temp = 0;
		int funcID1 = 0, funcID2 = 0;
		String name1 = "", name2 = "", name3 = "", name = "";
		
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
		
		// Temp var j used to loop AgeRange, since AgeRange.next() was called previously, to fix that
		// if j  == 1, then the first object vector will not be retrieved, so similiar to .next()
		int j = 0;
		if(orgID == 1)
			j = 1;
		// AgeRange.next();
		for(;j<AgeRange.size();j++){
			voAge vo = (voAge) AgeRange.get(j);
			
	  		funcID1 = vo.getPKAge();
			name1 = Integer.toString(vo.getAgeRangeTop());
			
			if(temp == 0) {
				name = "Below " + name1;				
				temp++;
			}
			else {
				name3 = Integer.toString(Integer.parseInt(name1)-1);
				name =  name2 + " - " + name3;
			}	
			name2 = name1;

			if(age == funcID1) {
	  %>
	  	<option value = <%=funcID1%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID1%>><%=name%>	  
	  <% }
		   }//while
%>		   
		<option value = <%=funcID1%>><%="Above " + name3%>   
<%			    
		   }
	  %>
    </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><%= trans.tslt("Gender") %> : </td>
    <td>&nbsp;</td>
    <td>
	
	<%	exist = DemographicEntry.GenderSelected(surveyID);
		if(exist == 0) {
	%>
		<select name="gender" disabled>
	<% } else { %>	
      <select name="gender">
	  <% 
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
	  	for(int i=0; i<Gender.size(); i++){
			voGender vo = (voGender)Gender.get(i);
			
	  		int funcID = vo.getPKGender();
			String name = vo.getGenderDesc();
		
			if(gender.equals(name)) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   }
		   } 
	  %>
    </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><%= trans.tslt("Ethnic Group") %> : </td>
    <td>&nbsp;</td>
    <td>
	<%	exist = DemographicEntry.EthnicSelected(surveyID);
		if(exist == 0) {
	%>
		<select name="ethnicGroup" disabled>
	<% } else { %>	
      <select name="ethnicGroup">
	  <% 
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
		for(int i=0; i<EthnicGroup.size(); i++){
			voEthnic vo = (voEthnic)EthnicGroup.get(i);
			
	  		int funcID = vo.getPKEthnic();
			String name = vo.getEthnicDesc();
		
			if(ethnicGroup.equals(name)) {
	  %>
	  	  	<option value = <%=funcID%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   } 
		   }
	  %>
    </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><%= trans.tslt("Location") %> : </td>
    <td>&nbsp;</td>
    <td>
	<%	exist = DemographicEntry.LocationSelected(surveyID);
		if(exist == 0) {
	%>
		<select name="location" disabled>
	<% } else { %>	
      <select name="location">
	  <% 
		/*************************************************
		*The following was edited by clement at 9-jan-2007
		*************************************************/ 
		
		for(int i=0; i<Location.size(); i++){
			voLocation vo = (voLocation) Location.get(i);
			
	  		int funcID = vo.getPKLocation();
			String name = vo.getLocationName();
		
			if(location.equals(name)) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   }
		   } 
	  %>
    </select></td>
  </tr>
  </table>
<p></p>
<table>
<tr>
<td width="135" align="right"><input type="button" name="btnOk" value="  <%= trans.tslt("Ok") %>  " onclick="openQuestionnaire(this.form)"></td>
<td width="21">&nbsp;</td>
<td width="225"><input type="button" name="btnCancel" value="<%= trans.tslt("Cancel") %>" onclick="cancel(this.form)"></td>
</tr>
</table>
<% } %>
</form>

</body>
</html>