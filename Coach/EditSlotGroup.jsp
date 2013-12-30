<%// Author: Dai Yong in June 2013%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "CP_Classes.vo.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Edit Time Slot</title>
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
<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup"scope="session" />
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<script language = "javascript">
function confirmEdit(form)
{
	if(form.Name.value != "") {
		if(confirm("Edit Time Slot?")) {
			form.action = "EditSLotGroup.jsp?edit=1";
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.Name.value == "") {
			alert("Please enter Time Slot Name");
			form.Name.focus();
		}
		return false;
	}
	return true;
}

function cancelEdit()
{
	window.close();
}
</script>

<%
String username=(String)session.getAttribute("username");
int   PK_CoachSlotGroup = LoginStatus.getSelectedSlotGroup();
  if (!logchk.isUsable(username)) 
  {
%> <font size="2">
   
<script>
parent.location.href = "../index.jsp";
</script>
<%
	} 
  else 
  { 
	if(request.getParameter("edit") != null) {
		if(request.getParameter("Name") != null)	{
  			String name = request.getParameter("Name");
  			

	// check whether SlotGroup name already exists in database
		    Boolean Exist = false;
		    
				Vector v = CoachSlotGroup.getAllSlotGroup();
				for (int i = 0; i < v.size(); i++) {
					voCoachSlotGroup vo = (voCoachSlotGroup) v
							.elementAt(i);

					String slotGroupName = vo.getSlotGroupName();

					if (name.trim().equalsIgnoreCase(
							(slotGroupName.trim()))) {
						Exist = true;
					}

				}
				
				if (!Exist) {
					try {
						boolean editsuc = CoachSlotGroup.updateSlotGroup(PK_CoachSlotGroup, name);
						
						if (editsuc) {
%>
						<script>
						alert("Time Slot Edited Successfully");
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
  		alert("Time Slot Name Exists");
		window.location.href='EditSlotGroup.jsp';
	</script>
<%			
			}

	}
	}
%>	
<p>
		<b><font color="#000080" size="3" face="Arial">Edit Time Slot</font></b>
	</p>
<form name="EditSlotGroup" method="post">
  <table border="0" width="480" height="141" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="120" height="33">Time Slot Name:</td>
      <td width="10" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="Name" type="text"  style='font-size:10.0pt;font-family:Arial' id="Name" value="<%=CoachSlotGroup.getSelectedSlotGroup(PK_CoachSlotGroup).getSlotGroupName()%>"size="30" maxlength="50">
	  </td>
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