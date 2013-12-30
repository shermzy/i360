
<%
	// Author: Dai Yong in AUG 2013
%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Add Slot To User Assignment</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style>
</head>

<body style="background-color: #DEE3EF">
	<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
	<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
	<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup"
		scope="session" />
	<jsp:useBean id="CoachDate" class="Coach.CoachDate" scope="session" />
	<jsp:useBean id="CoachSlot" class="Coach.CoachSlot" scope="session" />
	<jsp:useBean id="Coach" class="Coach.Coach" scope="session" />
	<jsp:useBean id="Venue" class="Coach.CoachVenue" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup"
		scope="session" />
	<script language="javascript">
		function confirmAdd(form) {
			form.action = "AddSlotToUserAssignment.jsp?add=1";
			form.method = "post";
			form.submit();
		}
		function selectDateGroup(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectDateGroup=1";
			form.method = "post";
			form.submit();
		}
		function selectDate(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectDate=1";
			form.method = "post";
			form.submit();
		}
		function selectSlotGroup(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectSlotGroup=1";
			form.method = "post";
			form.submit();
		}
		function selectSlot(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectSlot=1";
			form.method = "post";
			form.submit();
		}
		function selectCoach(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectCoach=1";
			form.method = "post";
			form.submit();
		}
		function selectVenue(form) {
			form.action = "AddSlotToUserAssignment.jsp?selectVenue=1";
			form.method = "post";
			form.submit();
		}

		function cancelAdd() {
			opener.location.href = "UserAssignment.jsp";
			window.close();
		}
	</script>

	<%
		String username = (String) session.getAttribute("username");

		if (!logchk.isUsable(username)) {
	%>
	<font size="2"> <script>
		parent.location.href = "../index.jsp";
	</script> <%
 	} else {
 		//for maintaining the selected data in the dropdown list

 		Vector DateGroupList = new Vector();
 		DateGroupList = CoachDateGroup.getAllDateGroup();
 		Vector SlotGroupList = new Vector();
 		SlotGroupList = CoachSlotGroup.getAllSlotGroup();
 		Vector coachList = new Vector();
 		coachList = Coach.getAllCoach();
 		Vector venueList = new Vector();
 		venueList = Venue.getAllCoachVenue();

 		int selSessionID;
 		int selDateGroup;
 		int selDate;
 		int selSlotGroup;
 		int selSlot;
 		int selCoach;
 		int selVenue;
 		Vector coachDates = new Vector();
 		Vector coachSlots = new Vector();
 		if (request.getParameter("SessionID") != null) {
 			selSessionID = Integer.parseInt(request
 					.getParameter("SessionID"));
 			SessionSetup.setSelectedSession(selSessionID);
 		//	System.out.println("SessionID: " + selSessionID);
 		}
 		//Date
 		if (request.getParameter("selectDateGroup") != null) {
 			selDateGroup = Integer.parseInt(request
 					.getParameter("selDateGroup"));
 			SessionSetup.setSelectedDayGroup(selDateGroup);
 			coachDates = CoachDateGroup
 					.getSelectedDateGroupDetails(selDateGroup);
 		}
 		if (request.getParameter("selectDate") != null) {
 			selDate = Integer.parseInt(request.getParameter("selDate"));
 			SessionSetup.setSelectedDate(selDate);

 		}
 		if (SessionSetup.getSelectedDayGroup() != 0) {
 			coachDates = CoachDateGroup
 					.getSelectedDateGroupDetails(SessionSetup
 							.getSelectedDayGroup());
 		}
 		//Slot
 		if (request.getParameter("selectSlotGroup") != null) {
 			selSlotGroup = Integer.parseInt(request
 					.getParameter("selSlotGroup"));
 			SessionSetup.setSelectedSlotGroup(selSlotGroup);
 			coachSlots = CoachSlotGroup
 					.getSelectedSlotGroupDetails(selSlotGroup);
 		}
 		if (request.getParameter("selectSlot") != null) {
 			selSlot = Integer.parseInt(request.getParameter("selSlot"));
 			SessionSetup.setSelectedSlot(selSlot);
 		}
 		if (SessionSetup.getSelectedSlotGroup() != 0) {
 			coachSlots = CoachSlotGroup
 					.getSelectedSlotGroupDetails(SessionSetup
 							.getSelectedSlotGroup());
 		}
 		//Coach and Venue
 		if (request.getParameter("selectCoach") != null) {
 			selCoach = Integer.parseInt(request
 					.getParameter("selCoach"));
 			SessionSetup.setSelectedCoach(selCoach);
 		}
 		if (request.getParameter("selectVenue") != null) {
 			selVenue = Integer.parseInt(request
 					.getParameter("selVenue"));
 			SessionSetup.setSelectedVenue(selVenue);
 		}

 		if (request.getParameter("add") != null) {
 			if(SessionSetup.getSelectedDayGroup()==0){
 				 %><script>
				 alert("Please Select Coaching Period");
				 </script><% 
 			}else if(SessionSetup.getSelectedDate()==0){
 				 %><script>
				 alert("Please Select Coaching Date");
				 </script><% 
 			}else if(SessionSetup.getSelectedSlotGroup()==0){
 				 %><script>
				 alert("Please Select Time Slot Name");
				 </script><% 
 			}else if(SessionSetup.getSelectedSlot()==0){
 				 %><script>
				 alert("Please Select Coaching Time");
				 </script><% 
 			}else if(SessionSetup.getSelectedCoach()==0){
				 %><script>
				 alert("Please Select Coach");
				 </script><% 
			}
 			else{
 				boolean suc=SessionSetup.AddSlotToUserAssignment();
 				//add the time slot to user assignment
 				if(suc){
					%>
					<script>
					alert("Coaching Slot Added Successfully");
					
					</script>
					<% 
				}
				else{
					
				}
 			}
 			

 					
 		}
 %>
		<p>
			<b><font color="#000080" size="3" face="Arial">Add
					Coaching Slot to Candidate Assignment</font></b>
		</p>
		<form name="AddSlotToUserAssignment" method="post">
			<table>
				<tr>
					<td width="140"><font face="Arial" size="2">Coaching
							Period Name:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1"
						name="selDateGroup" onChange="selectDateGroup(this.form)">
							<option value=0>Please select a Coaching</option>
							<%
								voCoachDateGroup voCoachDateGroup = new voCoachDateGroup();

									for (int i = 0; i < DateGroupList.size(); i++) {
										voCoachDateGroup = (voCoachDateGroup) DateGroupList
												.elementAt(i);
										int DateGroupPK = voCoachDateGroup.getPK();
										String DateGroupName = voCoachDateGroup.getName();
										String DateGroupDis = voCoachDateGroup.getdescription();

										if (SessionSetup.getSelectedDayGroup() == DateGroupPK) {
							%>
							<option value=<%=DateGroupPK%> selected><%=DateGroupName%>
								<%
									} else {
								%>
							
							<option value=<%=DateGroupPK%>><%=DateGroupName%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<tr>
					<td width="140"><font face="Arial" size="2">Coaching
							Date:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1" name="selDate"
						onChange="selectDate(this.form)">
							<option value=0>Please select a Coaching Date</option>
							<%
								int DisplayNo = 1;
									int DatePK = 0;
									voCoachDate voCoachDate = new voCoachDate();

									for (int i = 0; i < coachDates.size(); i++) {
										voCoachDate = (voCoachDate) coachDates.elementAt(i);
										DatePK = voCoachDate.getPK();
										String date = voCoachDate.getDate();
										String[] DateInParts = date.split(" ");
										String dateWithoutTime = DateInParts[0];
										String[] DateWithoutTimeInParts = dateWithoutTime
												.split("-");
										String finalDate = DateWithoutTimeInParts[2] + "-"
												+ DateWithoutTimeInParts[1] + "-"
												+ DateWithoutTimeInParts[0];
										if (SessionSetup.getSelectedDate() == DatePK) {
							%>
							<option value=<%=DatePK%> selected><%=finalDate%>
								<%
									} else {
								%>
							
							<option value=<%=DatePK%>><%=finalDate%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<tr>
					<td height="20"></td>
				</tr>
				<tr>
					<td width="120"><font face="Arial" size="2">Time Slot
							Name:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1"
						name="selSlotGroup" onChange="selectSlotGroup(this.form)">
							<option value=0>Please Select Time Slot Name</option>
							<%
								voCoachSlotGroup voCoachSlotGroup = new voCoachSlotGroup();

									for (int i = 0; i < SlotGroupList.size(); i++) {
										voCoachSlotGroup = (voCoachSlotGroup) SlotGroupList
												.elementAt(i);
										int slotGroupPK = voCoachSlotGroup.getPk();
										String slotGroupName = voCoachSlotGroup.getSlotGroupName();

										if (SessionSetup.getSelectedSlotGroup() == slotGroupPK) {
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
				</tr>
				<tr>
					<td width="120"><font face="Arial" size="2">Time Slot:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1" name="selSlot"
						onChange="selectSlot(this.form)">
							<option value=0>Please Select Time Slot</option>
							<%
								// asdf
									DisplayNo = 1;
									int slotPK = 0;

									for (int i = 0; i < coachSlots.size(); i++) {
										voCoachSlot voCoachSlot = new voCoachSlot();
										voCoachSlot = (voCoachSlot) coachSlots.elementAt(i);

										slotPK = voCoachSlot.getPK();
										int startingTime = voCoachSlot.getStartingtime();
										int endingingTime = voCoachSlot.getEndingtime();
										String startingTime4Digits;
										String endingTime4Digits;
										if (startingTime < 1000) {
											startingTime4Digits = "0" + startingTime;
										} else {
											startingTime4Digits = "" + startingTime;
										}
										if (endingingTime < 1000) {
											endingTime4Digits = "0" + endingingTime;
										} else {
											endingTime4Digits = "" + endingingTime;
										}

										if (SessionSetup.getSelectedSlot() == slotPK) {
							%>
							<option value=<%=slotPK%> selected><%=startingTime4Digits%> - <%=endingTime4Digits%>
								<%
									} else {
								%>
							
							<option value=<%=slotPK%>><%=startingTime4Digits%> - <%=endingTime4Digits%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<tr>
					<td height="20"></td>
				</tr>
				<!--Coach  -->
				<tr>
					<td width="140"><font face="Arial" size="2">Coach Name:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1" name="selCoach"
						onChange="selectCoach(this.form)">
							<option value=0>Please select a Coach</option>
							<%
								voCoach voCoach = new voCoach();
									int coachPK;
									for (int i = 0; i < coachList.size(); i++) {
										voCoach = (voCoach) coachList.elementAt(i);

										coachPK = voCoach.getPk();
										String name = voCoach.getCoachName();

										if (SessionSetup.getSelectedCoach() == coachPK) {
							%>
							<option value=<%=coachPK%> selected><%=name%>
								<%
									} else {
								%>
							
							<option value=<%=coachPK%>><%=name%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<!-- end of Coach -->

				<!-- Venue -->
				<tr>
					<td width="140"><font face="Arial" size="2">Venue:</font></td>
					<td width="23">:</td>
					<td width="500" colspan="1"><select size="1" name="selVenue"
						onChange="selectVenue(this.form)">
							<option value=0>Please select Venue</option>
							<%
								voCoachVenue voVenue = new voCoachVenue();
									int venuePK;
									for (int i = 0; i < venueList.size(); i++) {
										voVenue = (voCoachVenue) venueList.elementAt(i);

										venuePK = voVenue.getVenuePK();
										String venue1 = voVenue.getVenue1();

										if (SessionSetup.getSelectedVenue() == venuePK) {
							%>
							<option value=<%=venuePK%> selected><%=venue1%>
								<%
									} else {
								%>
							
							<option value=<%=venuePK%>><%=venue1%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<!-- end of Venue -->

				<tr>
					<td height="20" colspan="3"><font face="Arial" size="2"></font></td>
				</tr>
				<tr>
					<td width="140" colspan="3"><font color="#000080" face="Arial"
						size="2">Notes:</font></td>
				</tr>
				<tr>
					<td width="140" colspan="3"><font color="#000080" face="Arial"
						size="2">New Coaching Date should be added in Coaching
							Period Management.</font></td>
				</tr>
				<tr>
					<td width="140" colspan="3"><font color="#000080" face="Arial"
						size="2">New Time Slot should be added in Time Slot
							Management.</font></td>
				</tr>
				<tr>
					<td height="20" colspan="3"><font face="Arial" size="2"></font></td>
				</tr>
			</table>



			<blockquote>
				<blockquote>
					<p>
						<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</font> <font face="Arial" span
							style="font-size: 10.0pt; font-family: Arial"> <input
							class="btn btn-primary" type="button" name="Submit"
							value="Submit" onClick="confirmAdd(this.form)">
						</font><font span style='font-family: Arial'> </font> <font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</font> <font face="Arial" span
							style="font-size: 10.0pt; font-family: Arial"> <input
							name="Cancel" class="btn btn-primary" type="button" id="Cancel"
							value="Save and Exit" onClick="cancelAdd()">
						</font>
					</p>
				</blockquote>
			</blockquote>

		</form> <%
 	}
 %>
</body>
</html>