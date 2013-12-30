<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Coaching Session Setup</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	
}
-->
</style>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
	<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
	<jsp:useBean id="CoachOrganization" class="Coach.CoachOrganization" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
	<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" scope="session" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


		function proceed(form) {
			if(form.selOrg.value =="0"){
				alert("Please select organization");
			}else if(form.selSlotGroup.value =="0"){
				alert("Please select daily schedule");
			}else if(form.selDayGroup.value =="0"){
				alert("Please select coaching session");
			}else if(form.selSurvey.value =="0"){
				alert("Please select survey session");
			}
			else{
			window.location.href = 'SelectCoach.jsp';
			}
		}
		
		function setOrg(form){
			form.action = "CoachAssignment.jsp?setOrg=1";
			form.method = "post";
			form.submit();
		}
		function setSurvey(form){
			form.action = "CoachAssignment.jsp?setSurvey=1";
			form.method = "post";
			form.submit();
		}
		function setSlotGroup(form){
			form.action = "CoachAssignment.jsp?setSlotGroup=1";
			form.method = "post";
			form.submit();
		}
		function setDayGroup(form){
			form.action = "CoachAssignment.jsp?setDayGroup=1";
			form.method = "post";
			form.submit();
		}
		function viewTrainingSession(form){
			var value=form.selDayGroup.value;
			if(value=="0"){
				alert("Please Delect Coaching Period");
			}else{
				var myWindow=window.open('ViewDayGroup.jsp?ViewDayGroup='+ value,'windowRef','scrollbars=yes, width=480, height=250');
				var query = "ViewDayGroup.jsp?ViewDayGroup=" + value;
				myWindow.moveTo(x,y);
		    	myWindow.location.href = query;
			}
		
		}
		function viewDailySchedule(form){
			
			var value=form.selSlotGroup.value;
			if(value=="0"){
				alert("Please Select Daily Schedule");
			}else{
				var myWindow=window.open('ViewSlotGroup.jsp?ViewSlotGroup='+ value,'windowRef','scrollbars=yes, width=480, height=250');
				var query = "ViewSlotGroup.jsp?ViewSlotGroup=" + value;
				myWindow.moveTo(x,y);
		    	myWindow.location.href = query;
			}
		
		}
		
	</script>
</head>

<body>
	
	<!-- select Organization -->


	<%
		String username = (String) session.getAttribute("username");
		Vector surveys=new Vector();

		if (!logchk.isUsable(username)) {
	%>
	<font size="2"> <script>
		parent.location.href = "../index.jsp";
	</script> <%
 	} else {
		Vector organizationlist=CoachOrganization.getAllOrganizations();
		Vector slotGroupList=CoachSlotGroup.getAllSlotGroup();
		Vector dayGroupList=CoachDateGroup.getAllDateGroup();
		if(SessionSetup.getSelectedOrganisation()!=0){
			surveys=SessionSetup.getSurveybyOrganizationID(SessionSetup.getSelectedOrganisation());
		}
		
 		if (request.getParameter("setOrg") != null) {
 			//set up the org
 			if(request.getParameter("selOrg")!=null){
 				int orgPK=Integer.parseInt(request.getParameter("selOrg"));
 				SessionSetup.setSelectedOrganisation(orgPK);
 				System.out.println("Selected ORG:"+orgPK);
 				surveys=SessionSetup.getSurveybyOrganizationID(orgPK);
 			}
 			
 		}
 		if (request.getParameter("setSurvey") != null) {
 			//set up the org
 			if(request.getParameter("selSurvey")!=null){
 				int surveyPK=Integer.parseInt(request.getParameter("selSurvey"));
 				SessionSetup.setSelectedSurvey(surveyPK);
 				System.out.println("Selected Survey:"+surveyPK);
 				surveys=SessionSetup.getSurveybyOrganizationID(SessionSetup.getSelectedOrganisation());
 			}
 			
 		}
 		if (request.getParameter("setSlotGroup") != null) {
 			if(request.getParameter("selSlotGroup")!=null){
 				int slotGroupPK=Integer.parseInt(request.getParameter("selSlotGroup"));
 				SessionSetup.setSelectedSlotGroup(slotGroupPK);
 				System.out.println("Selected Slot Group:"+slotGroupPK);
 			}
 			
 		}
 		if (request.getParameter("setDayGroup") != null) {
 			if(request.getParameter("selDayGroup")!=null){
 				int dayGroupPK=Integer.parseInt(request.getParameter("selDayGroup"));
 				SessionSetup.setSelectedDayGroup(dayGroupPK);
 				System.out.println("Selected Day Group:"+dayGroupPK);
 			}
 			
 		}
 		
 %>

		<form name="AddSlot" method="post">
			<p>
				<b><font color="#000080" size="2" face="Arial">Organization Name:</font></b>
			</p>
			<table>
				<tr>
					<td width="500" colspan="1"><select size="1"
						name="selOrg" onChange="setOrg(this.form)">
						<option value=0>Select a organization</option>
							<%
								for (int i = 0; i < organizationlist.size(); i++) {

										votblOrganization Organization = (votblOrganization) organizationlist.elementAt(i);
										int OrganizationPK = Organization.getPKOrganization();
										String OrganizationName = Organization.getOrganizationName();
										String OrganizationCode = Organization.getOrganizationCode();

										if (SessionSetup.getSelectedOrganisation() == OrganizationPK) {
							%>
							<option value=<%=OrganizationPK%> selected><%=OrganizationName%>
								<%
									} else {
								%>
							
							<option value=<%=OrganizationPK%>><%=OrganizationName%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
			</table>
			<br>
				<p>
				<b><font color="#000080" size="2" face="Arial">Survey Name:</font></b>
			</p>
			<table>
				<tr>
					<td width="500" colspan="1"><select size="1"
						name="selSurvey" onChange="setSurvey(this.form)">
						<option value=0>Select a Survey</option>
							<%
								for (int i = 0; i < surveys.size(); i++) {
									
										
									votblSurvey survey=(votblSurvey)surveys.elementAt(i);
										int surveyPK = survey.getSurveyID();
										String surveyName=survey.getSurveyName();
										if (SessionSetup.getSelectedSurvey() == surveyPK) {
							%>
							<option value=<%=surveyPK%> selected><%=surveyName%>
								<%
									} else {
								%>
							
							<option value=<%=surveyPK%>><%=surveyName%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
			</table>
			<br>
			<!-- select daily schedule -->
			<p>
				<b><font color="#000080" size="2" face="Arial">Daily Schedule:</font></b>
			</p>
			<table width="300">
				<tr>
					<td width="500" colspan="2"><select size="1"
						name="selSlotGroup" onChange="setSlotGroup(this.form)">
						<option value=0>Select a Daily Schedule</option>
							<%
								for (int i = 0; i < slotGroupList.size(); i++) {

										voCoachSlotGroup slotGroup = (voCoachSlotGroup) slotGroupList.elementAt(i);
										int slotGroupPK = slotGroup.getPk();
										String slotGroupName = slotGroup.getSlotGroupName();

										if (SessionSetup.getSelectedSlotGroup()== slotGroupPK) {
							%>
								<option value=<%=slotGroupPK%> selected><%=slotGroupName%>
								<%
									} else {
								%>
							
								<option value=<%=slotGroupPK%>><%=slotGroupName%>
								<%
									}

										}
								%>
							
					</select></td>
					<td><input align="left" type="button" name="viewDayGroup" value="View Daily Schedule" onclick="viewDailySchedule(this.form)"></td>
				</tr>
				
			</table>
				
			<br>

			<!-- select coaching period -->
			<p>
				<b><font color="#000080" size="2" face="Arial">Coaching Period:</font></b>
			</p>
			<table width="300">
				<tr>
					<td width="500" colspan="2"><select size="1"
						name="selDayGroup" onChange="setDayGroup(this.form)">
						<option value=0>Select a Coaching Session</option>
							<%
								for (int i = 0; i < dayGroupList.size(); i++) {

										voCoachDateGroup dayGroup = (voCoachDateGroup) dayGroupList.elementAt(i);
										int dayGroupPK = dayGroup.getPK();
										String dayGroupName = dayGroup.getName();
										

										if (SessionSetup.getSelectedDayGroup() == dayGroupPK) {
							%>
							<option value=<%=dayGroupPK%> selected><%=dayGroupName%>
								<%
									} else {
								%>
							
							<option value=<%=dayGroupPK%>><%=dayGroupName%>
								<%
									}

										}
								%>
							
					</select></td>
					<td><input  align="left" type="button" name="viewSlotGroup" value="View Coaching Period" onclick="viewTrainingSession(this.form)"></td>
					
				</tr>
			</table>
			
			<br>
			<br>
			
			<!-- select coach -->
			<input align="right" type="button" name="selectCoach" value="Proceed to select coach and venue" onclick="proceed(this.form)">
			<!-- generate coaching sessions -->
		</form> <%
 	}
 %>
</body>
</html>
</body>
</html>