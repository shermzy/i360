<%// Author: Dai Yong in June 2013%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "CP_Classes.vo.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Edit Timing</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body style="background-color: #DEE3EF">
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>    
<jsp:useBean id="CoachSlot" class="Coach.CoachSlot"scope="session" />
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<script language = "javascript">
function confirmEdit(form)
{
	if((form.StartingTime.value != "")&&(form.EndingTime.value != "") ) {
				if (confirm("Edit Timing?")) {
					form.action = "EditSLot.jsp?edit=1";
					form.method = "post";
					form.submit();
					return true;
			} else
				return false;
		} else {
			if(form.StartingTime.value == "")
			alert("Please enter both starting time");
			if(form.EndingTime.value == "")
				alert("Please enter both ending time");
			return false;
		}
		return true;
	}

	function cancelEdit() {
		window.close();
	}
</script>

<%
	String username = (String) session.getAttribute("username");
	//System.out.println("check:" + request.getParameter("editedSlot"));
	int CoachSlotPK = LoginStatus.getSelectedSlot();
	if (request.getParameter("editedSlot") != null) {
		LoginStatus.setSelectedSlot(Integer.valueOf(request.getParameter("editedSlot")));
		CoachSlotPK = Integer.valueOf(request.getParameter("editedSlot"));
		
	}

	if (!logchk.isUsable(username)) {
%> <font size="2">
   
<script>
parent.location.href = "../index.jsp";
</script>
<%
	} 
  else 
  { 
	if(request.getParameter("edit") != null) {
		if(!request.getParameter("StartingTime").equalsIgnoreCase("") && !request.getParameter("EndingTime").equalsIgnoreCase(""))	{
  			String StartingTime = request.getParameter("StartingTime");
  			String EndingTime = request.getParameter("EndingTime");
				//check time clash
		  		Boolean Exist = false;
				if (!Exist) {
					try {
					//	System.out.println("CoachSlotPK:"+CoachSlotPK);
					//	System.out.println("StartingTime:"+StartingTime);
					//	System.out.println("EndingTime:"+EndingTime);
						boolean editsuc = CoachSlot.updateSlot(CoachSlotPK, Integer.parseInt(StartingTime), Integer.parseInt(EndingTime));
						
					//	System.out.println("editsuc:"+editsuc);
						
						if (editsuc) {
%>
						<script>
						alert("Schedule Slot Updated");
						opener.location.href = 'SlotGroup.jsp';
						window.close();
						</script>
						<% 
					}
					else{
					}
				}catch(SQLException SE) {
                     System.out.println(SE);
				}
			} else {			
%>
	<script>
  		alert("Updated failed");
		window.location.href='EditSlot.jsp';
	</script>
<%			
			}

	}
	}
%>	

<form name="EditSlot" method="post">
<p>
		<b><font color="#000080" size="3" face="Arial">Edit Coaching Timing</font></b>
	</p>
  <table border="0" width="480"  font span style='font-size:10.0pt;font-family:Arial'>
   <tr><font color="#000080" size="2" face="Arial">Notes: Please Use 4 digits time. E.g. 1300</font></tr>
   <tr><font color="#000080" size="2" face="Arial">&nbsp;</font></tr>
    <tr>
      <td width="60" height="33">Starting Time</td>
      <td width="10" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="StartingTime" type="text"  style='font-size:10.0pt;font-family:Arial' id="StartingTime" value="<%=CoachSlot.getSelectedSlot(CoachSlotPK).getStartingtime()%>"size="30" maxlength="4">
	  </td>
    </tr>
    <tr>
      <td width="60" height="33">Starting Time</td>
      <td width="10" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="EndingTime" type="text"  style='font-size:10.0pt;font-family:Arial' id="EndingTime" value="<%=CoachSlot.getSelectedSlot(CoachSlotPK).getEndingtime()%>"size="30" maxlength="4">
	  </td>
    </tr>
    <tr>
      <td width="82" height="12"></td>
      <td width="10" height="12"></td>
      <td width="303" height="12"></td>
    </tr>
   
  </table>
  <blockquote>
    <blockquote>
      <p>
		<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</font>		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">		
	        <input type="button" name="Submit" value="Submit" onClick="return confirmEdit(this.form)"></font><font span style='font-family:Arial'>
		</font>
			<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </font>
		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">
			<input name="Cancel" type="button" id="Cancel" value="Cancel" onClick="cancelEdit()">
			</font> </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>