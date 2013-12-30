<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<html>
<head>

<title>Coach Booking System</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
<style type="text/css">
</style>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
	<jsp:useBean id="User" class="CP_Classes.User" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

		function proceed(form) {
			
		}
		function check(field)
		{
			var isValid = 0;
			var clickedValue = 0;
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
		function signUp(form,field) {
			var value = check(field);
			if (value) {
					form.action = "CoachBookingSignUp.jsp?signUp=" + value;
					form.method = "post";
					form.submit();
			}
			
		}
		function unSignUp(form,field) {
			var value = check(field);
			if (value) {
					form.action = "CoachBookingSignUp.jsp?unSignUp=" + value;
					form.method = "post";
					form.submit();
			}
			
		}
		
		function setSessionName(form) {
			
			if (form.selSession.value == "0") {
				alert("Please Select Session");
			}else{
				form.action = "CoachBookingSignUp.jsp?setSession=1";
				form.method = "post";
				form.submit();
			}
			
		}
		function setSessionDate(form) {
		
				form.action = "CoachBookingSignUp.jsp?setdate=1";
				form.method = "post";
				form.submit();
			
		}
		
	</script>
</head>

<body>
	
	<!-- select Session -->


	<%
		SessionSetup.setUserPK(logchk.getPKUser());
		String username = (String) session.getAttribute("username");
		Vector userAssignments=new Vector();
		int userPK=logchk.getPKUser();

		if (!logchk.isUsable(username)) {
	%>
	<font size="2"> <script>
		parent.location.href = "../iphone/index.jsp";
	</script> <%
 	} else {
		Vector sessionlist=SessionSetup.getSessionAllNamesUsedbyCandidate(logchk.getOrg());
		userAssignments=SessionSetup.getUserAssignmentBYCandidate();
 		if (request.getParameter("setSession") != null) {
 			//set up the org
 				int sessionPK=Integer.parseInt(request.getParameter("selSession"));
 				SessionSetup.setSelectedSession(sessionPK);
 				System.out.println("Selected Session:"+sessionPK);
 				userAssignments=SessionSetup.getUserAssignmentBYCandidate();
 		}
 		if (request.getParameter("setdate") != null) {
 			//set up the org
 				int datePK=Integer.parseInt(request.getParameter("selDate"));
 				System.out.println("set date PK:"+datePK);
 				SessionSetup.setSelectedDate(datePK);
 				//0 : all dates
 				if(SessionSetup.getSelectedDate()==0){
 					userAssignments=SessionSetup.getUserAssignmentBYCandidate();
 				}else{
 					userAssignments=SessionSetup.getUserAssignmentBYCandidate(datePK);
 				}
 		}
 		if (request.getParameter("signUp") != null) {
 			int selAssignmentPK=Integer.parseInt(request.getParameter("selAssignment"));
 			voCoachUserAssignment selectedUserAssignment=SessionSetup.getSelectedvoUserAssignment(selAssignmentPK);
 			if(SessionSetup.reachMax(userPK)){
 				%>
 				<script type="text/javascript">
 					alert("Sorry, you have already booked all your coaching slots!");
 				</script>
 				<% 
 				
 			}else if(selectedUserAssignment.getUserFK()!=0){
 				%>
 				<script type="text/javascript">
 					alert("Sorry, this session has been booked already!");
 				</script>
 				<% 
 			}else{
 				SessionSetup.candidateSignUp(selAssignmentPK, userPK);
 				if(SessionSetup.getSelectedDate()==0){
 				userAssignments=SessionSetup.getUserAssignmentBYCandidate();
 				}else{
 				userAssignments=SessionSetup.getUserAssignmentBYCandidate(SessionSetup.getSelectedDate());
 				}
 				%>
 				<script type="text/javascript">
 					alert("Book successfully");
 				</script>
 				<% 
 			}
 			
 		}
 		if (request.getParameter("unSignUp") != null) {
 			int selAssignmentPK=Integer.parseInt(request.getParameter("selAssignment"));
 			//validate whether the user has sign up already and only sign one
 			if(!SessionSetup.checkUserHasSigned(selAssignmentPK, userPK)){
 				%>
 				<script type="text/javascript">
 					alert("Sorry, you did not book the slot");
 				</script>
 				<% 
 				
 			}else{
 				SessionSetup.candidateUnSign(selAssignmentPK, userPK);
 				if(SessionSetup.getSelectedDate()==0){
 				userAssignments=SessionSetup.getUserAssignmentBYCandidate();
 				}else{
 				userAssignments=SessionSetup.getUserAssignmentBYCandidate(SessionSetup.getSelectedDate());
 				}
 				%>
 				<script type="text/javascript">
 					alert("Delete booking successfully");
 				</script>
 				<% 
 			}
 		}
 		System.out.println("User login: "+userPK);
 		if(!SessionSetup.validateUser(logchk.getOrg(), userPK)){
 			userAssignments=new Vector();
 			System.out.println("User not authorized");
 			%>
				<script type="text/javascript">
					alert("Sorry, you have no coaching session to book!");
				</script>
				<% 
 		}
 		/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = SessionSetup.getToggle();	//0=asc, 1=desc
	int type = 1; //1=date, 2=starting time 3= coach 4= status
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		SessionSetup.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		SessionSetup.setSortType(type);									
	} 
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
 		
 %>

		<form method="post">
			<p  align="center" >
			<b><font color="#000080" size="4" face="Arial">Welcome to Coaching Booking System</font></b>
			</p>
			<table>
				<tr>
					<td>
					<b><font color="#000080" size="2" face="Arial">Session Name:</font></b>
					</td>
					<td><select size="1" name="selSession" onChange="setSessionName(this.form)">
						<option value=0>Select a Session Name</option>
							<%
								for (int i = 0; i < sessionlist.size(); i++) {

									voCoachSession sessionDistic = (voCoachSession) sessionlist.elementAt(i);
										int sessionPK = sessionDistic.getPK();
										String sessionName = sessionDistic.getName();
										String sessionCode = sessionDistic.getDescription();

										if (SessionSetup.getSelectedSession() == sessionPK) {
							%>
							<option value=<%=sessionPK%> selected><%=sessionName%>
								<%
									} else {
								%>
							
							<option value=<%=sessionPK%>><%=sessionName%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
				<tr>
				<td height="20">
				</td>
				</tr>
				<tr>
					<td>
					<b><font color="#000080" size="2" face="Arial">Session Date:</font></b>
					</td>
					<td ><select size="1"
						name="selDate" onChange="setSessionDate(this.form)">
						<option value=0>ALL</option>
							<%
								for (int i = 0; i < SessionSetup.getCoachDates().size(); i++) {

									voCoachDate date = (voCoachDate)SessionSetup.getCoachDates().elementAt(i);
										int datePK = date.getPK();
										String Date=date.getDate().substring(0, 10);
										Date dateString = new SimpleDateFormat("yyyy-MM-dd").parse(Date);
										SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
										String finalDate=sdf.format(dateString);
										if (SessionSetup.getSelectedDate() == datePK) {
							%>
							<option value=<%=datePK%> selected><%=finalDate%>
								<%
									} else {
								%>
							
							<option value=<%=datePK%>><%=finalDate%>
								<%
									}
										}
								%>
							
					</select></td>
				</tr>
			</table>
			<table>
			<br>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="200" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CoachBookingSignUp.jsp?name=1"><b>
					<font style='color: white'>Date</font>
			</b></th>
			<th width="70" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CoachBookingSignUp.jsp?name=2"><b>
					<font style='color: white'>Starting</font>
			</b></th>
			<th width="70" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CoachBookingSignUp.jsp?name=5"><b>
					<font style='color: white'>Ending</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CoachBookingSignUp.jsp?name=3"><b>
					<font style='color: white'>Coach</font>
			</b></th>
			<th width="200" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Booked By</font>
			</b></th>
			
			

			<%
				int DisplayNo = 1;
				
				for (int i = 0; i < userAssignments.size(); i++) {
					voCoachUserAssignment userAssignment = new voCoachUserAssignment();
					userAssignment = (voCoachUserAssignment) userAssignments.elementAt(i);

					int AssignmentPK = userAssignment.getAssignmentPK();
					int StartingTime =userAssignment.getStartingTime();
					int EndingTime = userAssignment.getEndingTime();
					String CoachName=userAssignment.getCoachName();
					String sessionVenue=userAssignment.getSessionVenue();
					String Date=userAssignment.getDate().substring(0, 10);
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(Date);
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					String finalDate=sdf.format(date);
					String OrganizationName=userAssignment.getOrganizationName();
					int Status=userAssignment.getStatus();
					int UserFK=userAssignment.getUserFK();
					int venueFK=userAssignment.getVenueFK();
					String userNameInAssignment=SessionSetup.getUserNamebyID(UserFK);
					String fullName=User.getUserInfo(UserFK).getFamilyName()+" "+User.getUserInfo(UserFK).getGivenName();
					String link=userAssignment.getCoachLink();
					String fileName=userAssignment.getCoachFileName();
					String startingTime4Digits;
					String endingTime4Digits;
				if (StartingTime < 1000) {
					startingTime4Digits="0"+StartingTime;
				} else {
					startingTime4Digits=""+StartingTime;
				}
				if (EndingTime < 1000) {
					endingTime4Digits="0"+EndingTime;
				} else {
					endingTime4Digits=""+EndingTime;
				}
			%>
				<tr onMouseOver="this.bgColor = '#99ccff'"
					onMouseOut="this.bgColor = '#FFFFCC'">
					<td style="border-width: 1px"><font size="2"> <input
							type="radio" name="selAssignment" value=<%=AssignmentPK%>>
					</font>
					</td>
					<td align="center"><%=DisplayNo%></td>
					<td align="center"><%=finalDate%></td>
					<td align="center"><%=startingTime4Digits%></td>
					<td align="center"><%=endingTime4Digits%></td>
					<td align="center"><%=CoachName%></td>
					<td align="center"><%=fullName%></td>
				</tr>
				<%
				DisplayNo++;
				}
			%>
		</table>
		<br>
			
			<!-- select coach -->
			<div align="center">
			<input type="button" name="sign" value="Book" onclick="signUp(this.form,this.form.selAssignment)" style="height: 45px; width: 130px">
			&nbsp;&nbsp;&nbsp;
			<input type="button" name="unsign" value="Delete Booking" onclick="unSignUp(this.form,this.form.selAssignment)" style="height: 45px; width: 130px">
			</div>
		</form> <%
 	}
 %>
	<p>&nbsp;</p>
	<%
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
%>

<table border="0" align="center" height="26" id="table1">

	<tr>
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
		<% // Denise 05/01/2010 update new email address %>
		<td align="center" height="5" valign="top"><font size="1" color="navy" face="Arial">&nbsp;&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="Login.jsp">Home</a>&nbsp;| <a color="navy" face="Arial">&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="mailto:3SixtyAdmin@pcc.com.sg?subject=Regarding:">Contact 
		Us</a><a color="navy" face="Arial" href="termofuse.jsp" target="_blank"><span style="color: #000080; text-decoration: none"> | Terms of Use </span></a>| <span style="color: #000080; text-decoration: none"><a style="TEXT-DECORATION: none; color:navy;" href="http://www.pcc.com.sg/" target="_blank">PCC Website</a></span></font></td></tr><tr>
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright &copy; <%=year%> Pacific Century Consulting Pte Ltd. All Rights Reserved.
		</font>
		</td>
		
	</tr>
		
</table>
</body>
</html>