<%@ page import="java.sql.*,
			java.lang.String,
                 java.io.* "%>  
                 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="detail" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function cancel() 
{
	location.href = 'Login.jsp'
}

function validate()
{
    x = document.ChangePassword
    
	if (x.txtOldPass.value == "")
    {
		alert("<%=trans.tslt("Enter Password")%>");
		return false 
	}  
	if (x.txtOldPass.value.length < 4)
    {
    //by Hemilda Date 06/06/2008 change prompt
		alert("<%=trans.tslt("Password must be at least 4 characters long")%>");
		return false 
	}  
	if (x.txtNewPass.value == "")
    {
		alert("<%=trans.tslt("Enter Password")%>");
		return false 
	}  
	if (x.txtNewPass.value.length < 4)
    {
		alert("<%=trans.tslt("Password must be at least 4 charactes long")%>");
		return false 
	}  
	if(x.txtNewPass.value != x.txtConfirmPass.value)
	{
		alert("<%=trans.tslt("Password mismatch")%>");
		return false 
	}  
	
	// Added Eric Lu 14/5/08
	if (confirm("Update Password?")) {
		return true;
	} else return false;
	
}

</SCRIPT>
<body>
<%

response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	 <script>
	parent.location.href = "index.jsp";
	</script>

<%  } 
  else 
  { 
	
	if(request.getParameter("btnUpd") != null)
	{
		String oldPass = request.getParameter("txtOldPass");
		String newPass = request.getParameter("txtNewPass");

		String [] UserDetail = new String[14];
		UserDetail = detail.getUserDetail(logchk.getPKUser(),0);
		
		if(!UserDetail[12].equals(oldPass))
		{
			%>
			<script>
				alert("<%=trans.tslt("Password Invalid")%>")
			</script>
		<%
		}
		else
		{
			detail.ChangePass(logchk.getPKUser(), newPass);
			%>
			<script>
			alert("<%=trans.tslt("Password Updated")%>");
				//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
				//location.href('Login.jsp');
				location.href='Login.jsp';
			</script>
			<%
		}
	}
		
  
%>
<form name="ChangePassword" action="ChangePassword.jsp" method="post" onsubmit="return validate()">
<table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-bottom-width: 0px">
	<tr>
		<td><b><font face="Arial" size="2" color="#000080"><%= trans.tslt("Change Password") %></font></b></td>
	</tr>
	<tr>
		<td style="border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
	</tr>
</table>
<table border="1" width="379" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td width="151"><font size="2" face="Arial"><%= trans.tslt("Old Password") %>:</font></td>
		<td><input type="password" name="txtOldPass" size="20"></td>
	</tr>
	<tr>
		<td width="151"><font size="2" face="Arial"><%= trans.tslt("New Password") %>:</font></td>
		<td><input type="password" name="txtNewPass" size="20"></td>
	</tr>
	<tr>
		<td width="151"><font size="2" face="Arial"><%= trans.tslt("Confirm Password") %>:</font></td>
		<td><input type="password" name="txtConfirmPass" size="20"></td>
	</tr>
</table>
<table border="0" width="610" cellspacing="0" cellpadding="0">
	<tr>
		<td width="317">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="317"><input type="button" value="<%= trans.tslt("Cancel") %>" name="btnCancel" onclick="cancel()"></td>
		<td><input type="Submit" value="<%= trans.tslt("Update") %>" name="btnUpd"></td>
	</tr>
</table>
<%	}	%>
</form>
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
<table border="0" width="610" height="26">

	<tr>
		<td align="center" height="5" valign="top"></td></tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>

	<tr>
		<td align="center" height="5" valign="top"></td></tr>

	<tr>
		<td align="center" height="5" valign="top"></td></tr>

	<tr>
		<td align="center" height="5" valign="top"></td></tr>

	<tr>
	<%@ include file="Footer.jsp"%></tr></table>
</body>
</html>