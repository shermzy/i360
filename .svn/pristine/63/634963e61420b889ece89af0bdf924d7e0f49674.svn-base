<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Select Coach</title>
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
	<jsp:useBean id="Organization" class="CP_Classes.Organization" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
	<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" scope="session" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
		function check(field)
		{
			var isValid = 0;
			var clickedValue = 0;
			//check whether any checkbox selected
			if( field == null ) {
				isValid = 2;
			
			} else {

				if(isNaN(field.length) == false) {
					for (i = 0; i < field.length; i++)
						if(field[i].checked) {
							clickedValue = field[i].value;
							isValid = 1;
						}
				}else {		
					if(field.checked) {
						clickedValue = field.value;
						isValid = 1;
					}
						
				}
			}
			
			if(isValid == 1)
				return clickedValue;
			else if(isValid == 0)
				alert("No record selected");
			else if(isValid == 2)
				alert("No record available");
			
			isValid = 0;

		}
		function editSessionCoach(form, field){
			var value = check(field);
			
			if(value)
			{	
				var myWindow=window.open('EditSessionCoach.jsp?EditSessionCoach='+ value,'windowRef','scrollbars=no, width=480, height=400');
				var query = "EditSessionCoach.jsp?EditSessionCoach=" + value;
				myWindow.moveTo(x,y);
		    	myWindow.location.href = query;
			}
			
		}
		function editSessionVenue(form, field){
			var value = check(field);
			
			if(value)
			{	
				var myWindow=window.open('EditSessionVenue.jsp?EditSessionVenue='+ value,'windowRef','scrollbars=no, width=480, height=400');
				var query = "EditSessionVenue.jsp?EditSessionVenue=" + value;
				myWindow.moveTo(x,y);
		    	myWindow.location.href = query;
			}
		}
		function generateForm(form){
			form.action = "SelectCoach.jsp?generate=1";
			form.method = "post";
			form.submit();
		}

		
	</script>
</head>
<body>
	<form>
	<table>		
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Starting Time</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Ending Time</font>
			</b></th>

			<%	
				if(request.getParameter("generate")!=null){
					System.out.println("Generate the form");
					SessionSetup.generateScheduleForm();
					%>
						<script>
						alert("Train Schedule generate successfully");
						window.location.href='UserAssignment.jsp';
						</script>
					<%
					
					//generate the schedule from
				}
			
				Vector CoachSlots = new Vector();
				int slotGroupPK=SessionSetup.getSelectedSlotGroup();
				CoachSlots = CoachSlotGroup.getSelectedSlotGroupDetails(slotGroupPK);
				voCoachSlotGroup slotGroup=CoachSlotGroup.getSelectedSlotGroup(slotGroupPK);
				%>
				<p>
				<b><font color="#000080" size="2" face="Arial">Schedule Details For <%=slotGroup.getSlotGroupName()%></font></b>
				</p>
				<%
				int Display = 1;
				int pkslot=0;
				for (int i = 0; i < CoachSlots.size(); i++) {
					voCoachSlot voCoachSlot = new voCoachSlot();
					voCoachSlot = (voCoachSlot) CoachSlots.elementAt(i);

					pkslot = voCoachSlot.getPK();
					int startingTime = voCoachSlot.getStartingtime();
					int endingingTime = voCoachSlot.getEndingtime();
					String startingTime4Digits;
					String endingTime4Digits;
				if (startingTime < 1000) {
					startingTime4Digits="0"+startingTime;
				} else {
					startingTime4Digits=""+startingTime;
				}
				if (endingingTime < 1000) {
					endingTime4Digits="0"+endingingTime;
				} else {
					endingTime4Digits=""+endingingTime;
				}
			
					//System.out.println("ending time" + endingingTime);
			%>
			<tr>
				<td align="center"><%=Display%></td>
				<td align="center"><%=startingTime4Digits%></td>
				<td align="center"><%=endingTime4Digits%></td>
			</tr>
			<%
				Display++;
				}
			%>
		</table>
		<hr>
		<table>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Coaching Date</font>
			</b></th>
			<th width="300" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Venue</font>
			</b></th>
			<th width="300" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Coaches</font>
			</b></th>
			

				<%
				int DisplayNo = 1;
				int pkDate=0;
				Vector CoachDates=new Vector();
				int dayGroupPK=SessionSetup.getSelectedDayGroup();
				voCoachDateGroup dateGroup=CoachDateGroup.getSelectedDateGroup(dayGroupPK);
				
				
				%>
				<p>
				<b><font color="#000080" size="2" face="Arial">Coaches For <%=SessionSetup.getSessionName()%></font></b>
				</p>
				<%
				CoachDates = CoachDateGroup.getSelectedDateGroupDetails(dayGroupPK);
				for (int i = 0; i < CoachDates.size(); i++) {
					voCoachDate voCoachDate = new voCoachDate();
					voCoachDate = (voCoachDate) CoachDates.elementAt(i);

					pkDate = voCoachDate.getPK();
					String date=voCoachDate.getDate();
					voCoachVenue venue=SessionSetup.getSessionVenue(pkDate);
					String address1=venue.getVenue1();
				%>
				<tr onMouseOver="this.bgColor = '#99ccff'"
				onMouseOut="this.bgColor = '#FFFFCC'">
				<td style="border-width: 1px"><font size="2"> <input type="radio" name="selDate" value=<%=pkDate%>></font></td>
				<td align="center"><%=DisplayNo%></td>
				<td align="center"><%=date%></td>
			
				<!-- address1 column -->
					<%
						if (address1 == null || "".equalsIgnoreCase(address1)) {
					%><td align="center">&nbsp;</td>
					<%
						} else {
					%>
					<td align="center"><%=address1%></td>
					<%
						}
					%>
				<!-- address1 column -->
				<!--display all coaches  -->
				<td>
				<%
					Vector coaches=SessionSetup.getCoachBySessionIDandDateID(pkDate);
				for (int j = 0; j < coaches.size(); j++) {
					voCoach coach = new voCoach();
					coach = (voCoach) coaches.elementAt(j);
					String coachname=coach.getCoachName();
					%>
					<%=coachname%>, 
					<%
				}
				
				%>
				</td>
				
			</tr>
			<%
				DisplayNo++;
				}
			%>
		</table>
		<br>
		<br>
		<input name="editCoach" type="button" id="Cancel" value="Edit Coach" onClick="editSessionCoach(this.form,this.form.selDate)">
		<input name="editVenue" type="button" id="venue" value="Edit Venue" onClick="editSessionVenue(this.form,this.form.selDate)">
		<input name="refresh" type="button" id="Refresh" value="Refresh List" onClick="javascript: location.reload()">
		<input name="generate" type="button" id="generate" value="Generate Coaching Schedule" onClick="generateForm(this.form)">
	</form>
		<p>Tips: For Firefox and Chrome Users, the table may not be updated automatically. You may use the refresh button</p>
</body>
</html>