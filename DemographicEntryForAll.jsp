<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<html>
<head>
<title>Demographic Entry</title>

<meta http-equiv="Content-Type" content="text/html">

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style></head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="DemographicEntry" class="CP_Classes.DemographicEntry" scope="session"/>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function openQuestionnaire(form)
function openQuestionnaire(form)
{	
	// Edited Eric Lu 16-May-08
	// Added new confirm box to update demographic entry
	if (confirm("Update Demographic Entry?")) {
		form.action= "DemographicEntryForAll.jsp?edit=1";
		form.method = "post";
		form.submit();
	}
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancel(form)
function cancel(form)
{	
	window.close();	
}

// Added by Eric Lu 21/5/08
// Function activated when organisation is changed in the drop-down list
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function proceed(form,field)
function proceed(form,field)
{
	form.action="DemographicEntryForAll.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}
</script>

<%
	response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
	if(request.getParameter("proceed") != null) { 
		int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 		logchk.setOrg(PKOrg);
	}

	String jobFunction="", ageRange="", gender="", ethnicGroup="", location="", department="";
	String jobLevel=""; 
	int age=0,ethnic=0,gdr=0,jobftn=0,joblvl=0,loc=0, pkDemographic = 0;
	

		int orgID = logchk.getOrg();	
		int selfOrgID = logchk.getSelfOrg();
		int compID = logchk.getCompany();
		int raterID = logchk.getPKUser();
		/*
		SVR ID = Database.getRecord("Select * from [User] where PKUser = '" + raterID + "'");
		ID.next();
		int fkRater = ID.getInt(1);
		*/
	
    /********************
	* Edited by James 29 Oct 2007
	************************/
		
		voUserDemographic vo = RDE.getRaterInfo(raterID);
		
	
		if(vo!=null){
		
		
		
		
		
		pkDemographic = vo.getPKUserDemographic();
		age           = vo.getFKAge();
		ethnic        = vo.getFKEthnic();
		gdr           = vo.getFKGender();
		jobftn        = vo.getFKJobFunction();
		joblvl        = vo.getFKJobLevel();
		loc           = vo.getFKLocation();
		
		ageRange    = DemographicEntry.AgeRange(age);
		ethnicGroup = DemographicEntry.Ethnic(ethnic);
		gender      = DemographicEntry.Gender(gdr);
		jobFunction = DemographicEntry.JobFunction(jobftn);
		jobLevel    = DemographicEntry.JobLevel(joblvl);
		location    = DemographicEntry.Location(loc);
		}
		
		
		Vector JobFunction = DemographicEntry.getAllJobFunction(orgID);
		Vector JobLevel    = DemographicEntry.getAllJobLevel(orgID);
		Vector AgeRange    = DemographicEntry.getAllAgeRange(orgID);
		Vector Gender      = DemographicEntry.getAllGender(orgID);
		Vector EthnicGroup = DemographicEntry.getAllEthnic(orgID);
		Vector Location    = DemographicEntry.getAllLocation(orgID);
		
		
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

			try {
				DemographicEntry.deleteRecord(pkDemographic);				
				boolean add = DemographicEntry.InsertRecord(raterID, age, ethnic, gdr, jobftn, joblvl, loc);
				
				if(add) {
%>
<script>
alert("<%=trans.tslt("Updated successfully")%>");
window.location.href = "DemographicEntryForAll.jsp";
</script>
<%					
				}
			}catch(SQLException SE) {
%>
<script>
alert("<%=trans.tslt("Updated successfully")%>");
window.location.href = "DemographicEntryForAll.jsp";
</script>
<%				

			}
			
			voUserDemographic vo1 = RDE.getRaterInfo(raterID);
			
			/********************
			* Edited by James 29 Oct 2007
			************************/
			
			if(vo1!=null) {
			
			//pkDemographic = rater.getInt(1);
			//age           = rater.getInt(3);
			//ethnic        = rater.getInt(4);
			//gdr           = rater.getInt(5);
			//jobftn        = rater.getInt(6);
			//joblvl        = rater.getInt(7);
			//loc           = rater.getInt(8);
		
		
		pkDemographic = vo1.getPKUserDemographic();
		age           = vo1.getFKAge();
		ethnic        = vo1.getFKEthnic();
		gdr           = vo1.getFKGender();
		jobftn        = vo1.getFKJobFunction();
		joblvl        = vo1.getFKJobLevel();
		loc           = vo1.getFKLocation();
			
			
			
			ageRange    = DemographicEntry.AgeRange(age);
			ethnicGroup = DemographicEntry.Ethnic(ethnic);
			gender      = DemographicEntry.Gender(gdr);
			jobFunction = DemographicEntry.JobFunction(jobftn);
			jobLevel    = DemographicEntry.JobLevel(joblvl);
			location    = DemographicEntry.Location(loc);
			//department  = DemographicEntry.Department(deptID);	
					
			}
				
		}
		
		

%>

<form name="DemographicEntry" method="post">
<table width="200" border="0">
      <tr>
        <td><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Demographic Entry") %> </font></b></td>
      </tr>
</table>

&nbsp;
<table width="486" border="2" bordercolor="#3399FF" style='font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
  <tr>
    <td width="115" align="right" bordercolor="#FFFFCC"><%= trans.tslt("Organisation") %> : </td>
    <td width="10" bordercolor="#FFFFCC">&nbsp;</td>
    <td width="292" bordercolor="#FFFFCC"> <font size="2">
   	  <select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
	  <%
		// Added to check whether organisation is also a consulting company
		// if yes, will display a dropdown list of organisation managed by this company
		// else, it will display the current organisation only
		// Mark Oei 09 Mar 2010
		String [] UserDetail = new String[14];
		UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
		boolean isConsulting = true;
		isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
		if (isConsulting){
			Vector vOrg = logchk.getOrgList(logchk.getCompany());
		
			for(int i=0; i<vOrg.size(); i++)
			{
				votblOrganization vo1 = (votblOrganization)vOrg.elementAt(i);
				int PKOrg = vo1.getPKOrganization();
				String OrgName = vo1.getOrganizationName();
		
				if(logchk.getOrg() == PKOrg)
				{ %>
					<option value=<%=PKOrg%> selected><%=OrgName%></option>
				<% } else { %>
					<option value=<%=PKOrg%>><%=OrgName%></option>
				<%	}	
			} 
		} else { %>
				<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
			<% } // End of isConsulting %>
	  </select></font>
	</td>
  </tr>
  <tr>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td width="115" align="right" bordercolor="#FFFFCC"><%= trans.tslt("Job Function") %> : </td>
    <td width="10" bordercolor="#FFFFCC">&nbsp;</td>
    <% int t = 0; 		
	%>
	<td width="292" bordercolor="#FFFFCC">
      <select name="jobFunction">
	  <% 	
	  
			/********************
			* Edited by James 29 Oct 2007
			************************/
	  
	  //while(JobFunction.next()) {	
		for(int i=0; i<JobFunction.size(); i++) {
			voJobFunction vo2=(voJobFunction)JobFunction.elementAt(i);
	  		//int funcID = JobFunction.getInt(1);
			//String name = JobFunction.getString(2);
			
			int funcID = vo2.getPKJobFunction();
			String name = vo2.getJobFunctionName();
			
			if(jobFunction.equals(name)) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 	  	
	  	} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   } 
	  %>
      </select></td>
  </tr>
  <tr>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" bordercolor="#FFFFCC"><%= trans.tslt("Job Level") %> : </td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">
      <select name="jobLevel">
	  <% 
	  /********************
			* Edited by James 29 Oct 2007
			************************/
			
	  	//while(JobLevel.next()) {
		for(int i=0; i<JobLevel.size(); i++) {
		voJobLevel vo3=(voJobLevel)JobLevel.elementAt(i);
	  		//int funcID = JobLevel.getInt(1);
			//String name = JobLevel.getString(2);
			int funcID =vo3.getPKJobLevel();
			String name=vo3.getJobLevelName();
			if(joblvl == funcID) {
	  %>
	  	<option value = <%=funcID%> selected><%=name%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=name%>	  
	  <% }
		   } 
	  %>
    </select></td>
  </tr>
  <tr height="5">
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
    <td height="5" bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" bordercolor="#FFFFCC"><%= trans.tslt("Age Range") %> : </td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">
      <select name="ageRange">
	  <% 
	  	int temp = 0;
		int funcID1 = 0, funcID2 = 0;
		String name1 = "", name2 = "", name3 = "", ageR = "";
		
		//if(orgID == 1)
			//AgeRange.next();
			  /********************
			* Edited by James 29 Oct 2007
			************************/
			
	  	//while(AgeRange.next()) {
		for(int i=1; i<AgeRange.size(); i++) {
	  		//funcID1 = AgeRange.getInt(1);
			//name1 = AgeRange.getString(2);
			voAge voA=(voAge)AgeRange.elementAt(i);
			funcID1 = voA.getPKAge();
			name1=	""+voA.getAgeRangeTop();
			if(temp == 0) {
				ageR = "Below " + name1;				
				temp++;
			}
			else {
				name3 = Integer.toString(Integer.parseInt(name1)-1);
				ageR =  name2 + " - " + name3;
			}	
			name2 = name1;

			if(age == funcID1) {
	  %>
	  	<option value = <%=funcID1%> selected><%=ageR%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID1%>><%=ageR%>	  
	  <% }
		   }//while
%>		   
		<option value = <%=funcID1%>><%="Above " + name3%>   
    </select></td>
  </tr>
  <tr height="5">
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" bordercolor="#FFFFCC"><%= trans.tslt("Gender") %> : </td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">
      <select name="gender">
	  <% 
	  
			/********************
			* Edited by James 29 Oct 2007
			************************/
	 for(int i=0; i<Gender.size(); i++) {
	  	//while(Gender.next()) {
		voGender voG=(voGender)Gender.elementAt(i);
	  		//int funcID = Gender.getInt(1);
			//String gen = Gender.getString(2);
			int funcID=voG.getPKGender();
			String gen=voG.getGenderDesc();
			if(gender.equals(gen)) {
	  %>
	  	<option value = <%=funcID%> selected><%=gen%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=gen%>	  
	  <% }
		   }
	  %>
    </select></td>
  </tr>
  <tr height="5">
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" bordercolor="#FFFFCC"><%= trans.tslt("Ethnic Group") %> : </td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">
      <select name="ethnicGroup">
	  <% 
	  
			/********************
			* Edited by James 29 Oct 2007
			************************/
			for(int i=0; i<EthnicGroup.size(); i++) {
			//while(EthnicGroup.next()) {
			//int funcID = EthnicGroup.getInt(1);
			//String eth = EthnicGroup.getString(2);
			voEthnic voE=(voEthnic)EthnicGroup.elementAt(i);
			int funcID=voE.getPKEthnic();
			String eth=voE.getEthnicDesc();
			
			if(ethnicGroup.equals(eth)) {
	  %>
	  	  	<option value = <%=funcID%> selected><%=eth%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=eth%>	  
	  <% }
		   } 
	  %>
    </select></td>
  </tr>
  <tr height="5">
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" bordercolor="#FFFFCC"><%= trans.tslt("Location") %> : </td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">
      <select name="location">
	  <% 
			/********************
			* Edited by James 29 Oct 2007
			************************/  
	  	//while(Location.next()) {
		for(int i=0; i<Location.size(); i++) {
			voLocation voL=(voLocation)Location.elementAt(i);
	  		//int funcID = Location.getInt(1);
			//String lc = Location.getString(2);
			int funcID=voL.getPKLocation();
			String lc=voL.getLocationName();
		
		
			if(location.equals(lc)) {
	  %>
	  	<option value = <%=funcID%> selected><%=lc%>
	  <% 
			} else {  %>
	  	<option value = <%=funcID%>><%=lc%>	  
	  <% }
		   }
	  %>
    </select></td>
  </tr>
  <tr>
    <td bordercolor="#FFFFCC">&nbsp;</td>
    <td bordercolor="#FFFFCC">&nbsp;</td>
            <td bordercolor="#FFFFCC" align=right> <font size="2">
            <%	
            	// Edited by Eric Lu 21/5/08
            	// Enables edit button only when self organisation = selected organisation
            	if (orgID == selfOrgID) {
            %>
    			<input style='font-size:10.0pt;font-family:Arial' type="submit" name="btnOk" value="  <%= trans.tslt("Edit") %>  " onclick="openQuestionnaire(this.form)">
    		<%
    			}
    		%>
    		</td>
        <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
  </tr>
</table>

<% } %>
</form>

<p></p>
<%@ include file="Footer.jsp"%>

</body>
</html>