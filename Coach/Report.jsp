<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="CP_Classes.vo.*"%>

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


<title>User Assignment</title>
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
	<jsp:useBean id="User" class="CP_Classes.User" scope="session" />
	<jsp:useBean id="CoachOrganization" class="Coach.CoachOrganization" scope="session" />
	<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
	<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" scope="session" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
	<jsp:useBean id="Venue" class="Coach.CoachVenue" scope="session" />
	<jsp:useBean id="Export" class="CP_Classes.Export" scope="session"/>
	<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
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
		function bookingStatusReport(form){
			form.action="Report.jsp?report=1";
			form.method="post";
			form.submit();
		}
	</script>
</head>

<body>
	
	<!-- select Session -->

	<%
		String username = (String) session.getAttribute("username");
		Vector userAssignments=new Vector();
		if (!logchk.isUsable(username)) {
	%>
	<font size="2"> <script>
		parent.location.href = "../index.jsp";
	</script> <%
 	} else {
		Vector sessionlist=SessionSetup.getSessionAllNames();
		userAssignments=SessionSetup.getUserAssignment();
 	if(request.getParameter("report") != null){
 			
 		if(SessionSetup.getSelectedSession()==0){
 			%>
				<script type="text/javascript">
					alert("Please select coaching session");
				</script>
				<% 
 			}
 		else{
				
		String file_name = "";
		int iExportType = 12;
		
		Export.setOrgID(logchk.getOrg()); //Set OrgID first
		Export.setSelectedSession(SessionSetup.getSelectedSession());
		Export.export(iExportType, logchk.getPKUser());
		file_name = Export.getCoachingStatusFileName();
		
		//read the file name.		
		String output = setting.getReport_Path() + "\\"+file_name;
		File f = new File (output);
	
        response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"" + file_name + "\"");
	
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		InputStream in = new FileInputStream(f);
		ServletOutputStream outs = response.getOutputStream();
		
		int bit = 256;
		int i = 0;
	
	   	try 
	   	{
	       	while ((bit) >= 0) 
	       	{
	       		bit = in.read();
	       		outs.write(bit);
	       	}
	       	//System.out.println("" +bit);
	   	} catch (IOException ioe) 
	  	{
	     	ioe.printStackTrace(System.out);
	    }
	    outs.flush();
	    outs.close();
	    in.close();
 		}
	
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
					<h2>Coach Booking Report</h2>
					<br>
		<form method="post">
			<table>
				<tr>
				<td>
				<b><font color="#000080" size="2" face="Arial">Session Name:  </font></b>
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
				
			</table>
			<br>
		<br>
           
             	<input class="btn btn-primary" type="button" name="report"
							value="Generate Booking Status Report"
							onclick="bookingStatusReport(this.form)">   
              
              
         
</form> <%
 	}
 %>
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