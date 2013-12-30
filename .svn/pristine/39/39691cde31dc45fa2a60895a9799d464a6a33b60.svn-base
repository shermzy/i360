<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- CSS -->

<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap-responsive.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap-responsive.min.css">


<!-- jQuery -->
<script type="text/javascript" src="../lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../lib/js/bootstrap.js"></script>
<script type="text/javascript" src="../lib/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../lib/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="../lib/js/bootstrap-dropdown.js"></script>

<title>Coach Booking System</title>
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
	<jsp:useBean id="User" class="CP_Classes.User" scope="session" />
	<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
	<jsp:useBean id="Venue" class="Coach.CoachVenue" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

		function proceed(form) {
			
		}
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
		function signUp(form,field) {
			var value = check(field);
			if (value) {
					form.action = "CandidateSignUp.jsp?signUp=" + value;
					form.method = "post";
					form.submit();
			}
			
		}
		function unSignUp(form,field) {
			var value = check(field);
			if (value) {
					form.action = "CandidateSignUp.jsp?unSignUp=" + value;
					form.method = "post";
					form.submit();
			}
			
		}
		
		
		function setSessionName(form) {
			
			if (form.selSession.value == "0") {
				alert("Please Select Session");
			}else{
				form.action = "CandidateSignUp.jsp?setSession=1";
				form.method = "post";
				form.submit();
			}
			
		}
		function setSessionDate(form) {
		
				form.action = "CandidateSignUp.jsp?setdate=1";
				form.method = "post";
				form.submit();
			
		}
		function newPopup(url) {
		popupWindow = window.open(
		url,'popUpWindow','height=x,width=y,left=400,top=400,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no,directories=no,status=yes')
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
		parent.location.href = "../index.jsp";
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
 			//System.out.println(selectedUserAssignment.getUserFK());
 			//validate whether the user has sign up already and only sign one
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
 		// validate the users, if the users are not on the list, they should not see the assigments
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

		<form name="AddSlot" method="post">
			<p>
			<h2><font color="#000080" size="4" face="Arial">Welcome to Coaching Booking System</font></h2>
			</p>
			<table>
				<tr>
					<td>
					<b><font color="#000080" size="2" face="Arial">Session Name:</font></b>
					</td>
					<td width="500" colspan="1"><select size="1"
						name="selSession" onChange="setSessionName(this.form)">
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
					<td width="500" colspan="1"><select size="1"
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
			<div class="alert alert-info">
				<strong>Sort the table by clicking on the column name</strong>
			</div>
			<table>
			
			
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>S/N</font>
			</b></th>
			<th width="130" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CandidateSignUp.jsp?name=1"><b>
					<font face="Arial" color="#FFFFFF" size="2"><u>Date</u></font>
			</b></th>
			<th width="70" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CandidateSignUp.jsp?name=2"><b>
					<font face="Arial" color="#FFFFFF" size="2"><u>Start Time</u></font>
			</b></th>
			<th width="70" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CandidateSignUp.jsp?name=5"><b>
					<font face="Arial" color="#FFFFFF" size="2"><u>Ending</u></font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><a href="CandidateSignUp.jsp?name=3"><b>
				<font face="Arial" color="#FFFFFF" size="2"><u>Coach</u></font>
			</b></th>
			<th width="200" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Booked By</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Venue</font>
			</b></th>
			<th width="60" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Link</font>
			</b></th>
			<th width="60" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Profile</font>
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
					voCoachVenue venue=Venue.getSelectedCoachVenue(venueFK);
					String address1=venue.getVenue1();
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
					<!-- address1 column -->
					<%
						if (address1 == null || "".equalsIgnoreCase(address1)) {
					%><td align="center">&nbsp;</td>
					<%
						} else {
					%>
					<td align="center">
					<a href="JavaScript:newPopup('ViewCandidateAssignmentVenue.jsp?ViewDayGroup=<%=venueFK%>');"><%=address1%></a></td>
					<%
						}
					%>
				<!-- address1 column -->
						
					<!-- Link column -->
					<%
						if (link == null || "".equalsIgnoreCase(link)) {
					%><td align="center">&nbsp;</td>
					<%
						} else {

									//System.out.println(filePath);
					%>
					<td align="center"><a href=<%=link%> target="_blank">View</a>
					</td>
					<%
						}
					%>
					<!-- Link column -->
					<!-- fileName column -->
					<%
						if (fileName == null || "".equalsIgnoreCase(fileName)) {
					%><td align="center">&nbsp;</td>
					<%
						} else {
									String filePath = "" + "../CoachFilePath/" + fileName;

									//System.out.println(filePath);
					%>
					<td align="center"><a href="<%=filePath.replace("\\", "/")%>"
						target="_blank">View</a></td>
					<%
						}
					%>
					<!-- fileName column -->
					

				</tr>
				<%
				DisplayNo++;
				}
			%>
		</table>
		<br>
		<br>
		<br>
			
			<!-- select coach -->
			<input class="btn btn-primary" type="button" name="sign" value="Book" onclick="signUp(this.form,this.form.selAssignment)">
			<input class="btn btn-primary" type="button" name="unsign" value="Delete Booking" onclick="unSignUp(this.form,this.form.selAssignment)">
		</form> <%
 	}
 %>
 	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
 <%@ include file="../Footer.jsp"%>
  <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
    <script src="../lib/js/jquery.js"></script>
    <script src="../lib/js/bootstrap-transition.js"></script>
    <script src="../lib/js/bootstrap-alert.js"></script>
    <script src="../lib/js/bootstrap-modal.js"></script>
    <script src="../lib/js/bootstrap-dropdown.js"></script>
    <script src="../lib/js/bootstrap-scrollspy.js"></script>
    <script src="../lib/js/bootstrap-tab.js"></script>
    <script src="../lib/js/bootstrap-tooltip.js"></script>
    <script src="../lib/js/bootstrap-popover.js"></script>
    <script src="../lib/js/bootstrap-button.js"></script>
    <script src="../lib/js/bootstrap-collapse.js"></script>
    <script src="../lib/js/bootstrap-carousel.js"></script>
    <script src="../lib/js/bootstrap-typeahead.js"></script>
    <script src="../lib/js/bootstrap-affix.js"></script>

    <script src="../lib/js/holder/holder.js"></script>
    <script src="../lib/js/google-code-prettify/prettify.js"></script>

    <script src="../lib/js/application.js"></script>


    <!-- Analytics
    ================================================== -->
    <script>
      var _gauges = _gauges || [];
      (function() {
        var t   = document.createElement('script');
        t.type  = 'text/javascript';
        t.async = true;
        t.id    = 'gauges-tracker';
        t.setAttribute('data-site-id', '4f0dc9fef5a1f55508000013');
        t.src = '//secure.gaug.es/track.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(t, s);
      })();
    </script>
</body>
</html>