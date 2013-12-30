<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "CP_Classes.vo.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Edit Session Cut-Off Date</title>
</head>

<body style="background-color: #DEE3EF">
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>    
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />
<link rel="stylesheet" type="text/css" media="all" href="jsDatePick_ltr.min.css" />

<script type="text/javascript" src="jsDatePick.min.1.3.js"></script>

<script type="text/javascript">
	window.onload = function(){
		new JsDatePick({
			useMode:2,
			target:"inputField",
			dateFormat:"%d-%M-%Y"
		});
	};
</script>
<script language = "javascript">
function confirmEdit(form,field)
{
	if(field.value != "") {
				if (confirm("Edit Cut-Off Date?")) {
					form.action = "EditSessionCutOffDate.jsp?edit=1";
					form.method = "post";
					form.submit();
					return true;
			} else
				return false;
		} else {
			if(field.value == "")
			alert("Please enter Cut-Off date");
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
	Date cutOffdate=new Date();
	int selectedSessionPK;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	if (request.getParameter("editedSession") != null) {
		selectedSessionPK=Integer.valueOf(request.getParameter("editedSession"));
		SessionSetup.setSelectedSession(selectedSessionPK);
		cutOffdate=SessionSetup.getSessionCutOffDateBySessionID(selectedSessionPK);
		
	}
	if (!logchk.isUsable(username)) {
%>
   
<script>
parent.location.href = "../index.jsp";
</script>
<%
	} 
  else 
  { 
	if(request.getParameter("edit") != null) {
		if(!request.getParameter("inputField").equalsIgnoreCase(""))	{
  			String DateString = request.getParameter("inputField");
  			Date date=sdf.parse(DateString);
 						
			boolean editsuc = SessionSetup.updateSessionCutOffDate(date, SessionSetup.getSelectedSession());
			if (editsuc) {
			%>
			<script>
			alert("Cut-Off Date edited successfully");
			opener.location.href = 'SessionManagement.jsp';
			window.close();
			</script>
			<% 
			}
			else{
			%>
			<script>
			alert("Cut-Off Date edited Failed");
			opener.location.href = 'SessionManagement.jsp';
			window.close();
			</script>
			<% 
			}
	}
	}
%>	

<form name="EditDate" method="post">
	<p>	
		<br>
			<b><font color="#000080" size="3" face="Arial">Edit Cut-Off Date</font></b>
		<br>
	</p>
  <table border="0" width="480" style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="100" height="33">Cut-Off Date</td>
      <td width="10" height="33">&nbsp;</td>
      <td width="400" height="33">
      <%if(cutOffdate==null){
      %>
		<input name="inputField" type="text" size="12" id="inputField" type="text"  style='font-size:10.0pt;font-family:Arial' id="Date" size="50" maxlength="20">
	  <% 
      }
      else{
      %>
		<input name="inputField" type="text" size="12" id="inputField" type="text"  style='font-size:10.0pt;font-family:Arial' id="Date" value="<%=sdf.format(cutOffdate)%>"size="50" maxlength="20">
		<% 
       }%>
    	
	  </td>
    </tr>
       
  </table>
  <blockquote>
    <blockquote>
      <p>
		<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</font>		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">		
	        <input type="button" name="Submit" value="Submit" onClick="confirmEdit(this.form,this.form.inputField)"></font><font span style='font-family:Arial'>
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