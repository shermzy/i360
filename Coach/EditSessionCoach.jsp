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
	<jsp:useBean id="CoachDate" class="Coach.CoachDate" scope="session" />
	<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
	<script type="text/javascript">
	var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
	
		function proceed(){
			opener.location.href='SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
		}
		function cancelAdd()
		{	
			opener.location.href='SelectCoach.jsp';
			opener.location.reload(true);
			window.close();
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
		
		
		function deleteOneSessionCoach(form, field) {
			var value = check(field);
			if (value) {
				if (confirm("Are you sure to delete the coach")) {
					//alert("coach to be delete: "+value)
					form.action = "EditSessionCoach.jsp?deleteCoach=" + value;
					form.method = "post";
					form.submit();
				}
			}
		}
		
		function addOneSessionCoach(form) {
			var myWindow=window.open('AddSessionCoach.jsp','windowRef','scrollbars=no, width=480, height=250');
			myWindow.moveTo(x,y);
		    myWindow.location.href = 'AddSessionCoach.jsp';
		}
		
	</script>
</head>
<body>
<div align="center">
<form>
<br>
		<p><b><font color="#000080" size="3" face="Arial">Coach List</font></b></p>

<%
	int datePK = 0;
	if (request.getParameter("EditSessionCoach") != null) {
		datePK = Integer.parseInt(request.getParameter("EditSessionCoach"));
		SessionSetup.setSelectDateToEdit(datePK);
	} else {
		datePK = SessionSetup.getSelectDateToEdit();
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

	<table>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Coaches</font>
			</b></th>

				<%
				int DisplayNo = 1;
				Vector CoachDates=new Vector();
				
				
				voCoachDate date=new voCoachDate();
				date=CoachDate.getSelectedDate(datePK);
				
				%>
				<p align="center">
				<b><font color="#000080" size="2" face="Arial">Coaches On <%=date.getDate()%></font></b>
				</p>
				<%
					
					Vector coaches=SessionSetup.getCoachBySessionIDandDateID(datePK);
					for (int j = 0; j < coaches.size(); j++) {
						voCoach coach = new voCoach();
						coach = (voCoach) coaches.elementAt(j);
						String coachname=coach.getCoachName();
						int coachPK=coach.getPk();
				%>
				<tr onMouseOver="this.bgColor = '#99ccff'"
				onMouseOut="this.bgColor = '#FFFFCC'">
				<td style="border-width: 1px"><font size="2"> <input type="radio" name="selCoach" value=<%=coachPK%>></font></td>
				<td align="center"><%=DisplayNo%></td>
				<td align="center"><%=coachname%></td>
			</tr>
			<%
				DisplayNo++;
				}
			%>
		</table>
		
		<br>
		<br>
			<input  name="addCoach" type="button" id="Cancel" value="Add Coach" onClick="addOneSessionCoach(this.form)">	
			<input  name="deleteCoach" type="button" id="Cancel" value="Delete Coach" onClick="deleteOneSessionCoach(this.form,this.form.selCoach)">
			<input name="Cancel" type="button" id="Cancel" value="Close" onClick="cancelAdd()">		
		</form>
</div>
</body>
</html>