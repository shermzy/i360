<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Select Venue</title>
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
	<jsp:useBean id="CoachVenue" class="Coach.CoachVenue" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
	
		function proceed(){
			opener.location.href='SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
		}
		function setVenue(form){
			form.action = "EditSessionVenue.jsp?setVenue=1";
			form.method = "post";
			form.submit();
		}
		function saveVenue(form){
			form.action = "EditSessionVenue.jsp?save=1";
			form.method = "post";
			form.submit();
		}
		function cancelAdd()
		{	
			opener.location.href='SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
		}	
		function viewVenueDetail(form){
			var value=form.selVenue.value;
				if(value=="0"){
				alert("Please select a venue");
				}else{
				var myWindow=window.open('ViewVenueDetails.jsp?ViewDayGroup='+ value,'windowRef','scrollbars=yes, width=480, height=250');
				var query = "ViewVenueDetails.jsp?ViewDayGroup=" + value;
				myWindow.moveTo(x,y);
	    		myWindow.location.href = query;
				}
		}
	</script>
</head>
<body>

<div align="center">
<form>
<br>
		<p><b><font color="#000080" size="3" face="Arial">Coaching Venue</font></b></p>

<%	
	Vector venues=CoachVenue.getAllCoachVenue();
	int datePK = 0;
	int venuePK;
	if (request.getParameter("EditSessionVenue") != null) {
		datePK = Integer.parseInt(request.getParameter("EditSessionVenue"));
		SessionSetup.setSelectDateToEdit(datePK);
	} else {
		datePK = SessionSetup.getSelectDateToEdit();
	}
	if (request.getParameter("setVenue") != null) {
		venuePK = Integer.parseInt(request.getParameter("selVenue"));
		SessionSetup.setSelectedVenue(venuePK);
		System.out.println("venue set to: "+venuePK);
	}else {
		venuePK = SessionSetup.getSelectedVenue();
	}
	if (request.getParameter("save") != null) {	
	
		if(!SessionSetup.CheckSessionVenueExist(SessionSetup.getSelectDateToEdit())){
			SessionSetup.addSessionVenue(SessionSetup.getSelectDateToEdit(), SessionSetup.getSelectedVenue());
			System.out.println("new session venue");
		}else{
			SessionSetup.updateSessionVenue(SessionSetup.getSelectDateToEdit(), SessionSetup.getSelectedVenue());
			System.out.println("update session venue");
		}
		
		%>
		<script type="text/javascript">
			opener.location.href = 'SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
		</script>
		<%
	}
	if (request.getParameter("deleteCoach") != null) {
		int PKCoach = Integer.valueOf(request.getParameter("deleteCoach"));
		Boolean delete = SessionSetup.deleteSessionCoach(datePK, PKCoach);
		if (delete) {
			%><script>
				 alert("Coach deleted successfully.");
				 opener.location.href='SelectCoach.jsp';
			     opener.location.reload(true);
			</script>
			<% 
			 }
			 else{
				 %><script>
				 alert("An error occured while trying to delete the coach.");
				 </script><% 
			 }
		}
	%>

	<table width="300">
				<tr>
					<td width="500" colspan="2"><select size="1"
						name="selVenue" onChange="setVenue(this.form)">
						<option value=0>Select a Coaching Venue</option>
						<%
							for (int i = 0; i < venues.size(); i++) {
								voCoachVenue venue = (voCoachVenue) venues.elementAt(i);
								int currentVenuePK = venue.getVenuePK();
								String venueAddress1 = venue.getVenue1();
								if (SessionSetup.getSelectedVenue() == currentVenuePK) {
						%>
						<option value=<%=currentVenuePK%> selected><%=venueAddress1%>
							<%
								} else {
							%>
						
						<option value=<%=currentVenuePK%>><%=venueAddress1%>
							<%
								}

								}
							%>
						
				</select></td>
					<td><input  type="button" name="viewVenueDetails" value="View Venue Details" onclick="viewVenueDetail(this.form)"></td>
				</tr>
			</table>
		
		<br>
		<br>
			<input  name="save" type="button" id="Save" value="Save Venue" onClick="saveVenue(this.form)">
			<input name="Cancel" type="button" id="Cancel" value="Close" onClick="cancelAdd()">		
		</form>
</div>
</body>
</html>