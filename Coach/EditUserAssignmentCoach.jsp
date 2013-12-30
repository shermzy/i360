<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Change User Assignment Coach</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	
}
-->
</style>
	<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
	<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" scope="session" />
	<jsp:useBean id="CoachDate" class="Coach.CoachDate" scope="session" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
	<jsp:useBean id="Coach" class="Coach.Coach" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
	
		function proceed(){
			opener.location.href='SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
		}
		function setCoach(form){
			form.action = "EditUserAssignmentCoach.jsp?setCoach=1";
			form.method = "post";
			form.submit();
		}
		function saveCoach(form){
			form.action = "EditUserAssignmentCoach.jsp?save=1";
			form.method = "post";
			form.submit();
		}
		function cancelAdd()
		{	
			opener.location.href="UserAssignment.jsp";
			opener.location.reload(true);
			window.close();
		}	
	</script>
</head>
<body>



<%	
	Vector coachs=Coach.getAllCoach();
	int userAssignmentPK = 0;
	int CoachPK;
	if (request.getParameter("UserAssignment") != null) {
		userAssignmentPK = Integer.parseInt(request.getParameter("UserAssignment"));
		SessionSetup.setSelectedUserAssignment(userAssignmentPK);
	} else {
		userAssignmentPK = SessionSetup.getSelectedUserAssignment();
	}
	if (request.getParameter("setCoach") != null) {
		CoachPK = Integer.parseInt(request.getParameter("selCoach"));
		SessionSetup.setSelectedCoach(CoachPK);
		System.out.println("Coach set to: "+CoachPK);
	}else {
		CoachPK = SessionSetup.getSelectedCoach();
	}
	if (request.getParameter("save") != null) {	
			SessionSetup.updateUserAssignmentCoach(userAssignmentPK, SessionSetup.getSelectedCoach());
			System.out.println("update session Coach");
		
		%>
		<script type="text/javascript">
			opener.location.href = "UserAssignment.jsp";
			window.close();
		</script>
		<%
	}
	
	%>
	
	<div align="center">
	<form>
		<table width="300">
		<p>
				<b><font color="#000080" size="2" face="Arial">Update User Assignment Coach</font></b>
				</p>
				<tr>
					<td width="500" colspan="2"><select size="1"
						name="selCoach" onChange="setCoach(this.form)">
						<option value=0>Select a Coaching Coach</option>
						<%
							for (int i = 0; i < coachs.size(); i++) {
								voCoach coach = (voCoach) coachs.elementAt(i);
								int currentCoachPK = coach.getPk();
								String coachName = coach.getCoachName();
								if (SessionSetup.getSelectedCoach() == currentCoachPK) {
						%>
						<option value=<%=currentCoachPK%> selected><%=coachName%>
							<%
								} else {
							%>
						
						<option value=<%=currentCoachPK%>><%=coachName%>
							<%
								}
							}
							%>
						
				</select></td>
				</tr>
			</table>
		
		<br>
		<br>
			<input  name="save" type="button" id="Save" value="Save" onClick="saveCoach(this.form)">
			<input name="Cancel" type="button" id="Cancel" value="Close" onClick="cancelAdd()">		
		</form>
</div>
</body>
</html>