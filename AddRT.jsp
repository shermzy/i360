<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%//by Hemilda 23/09/2008 fix import after add UTF-8%>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rating Task</title>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="EditRT" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">
function confirmAdd(form, scaleType)
{	
	if(form.Name.value != "" && form.Definition.value != "") {
		if(confirm("<%=trans.tslt("Add Rating Task")%>?")) {
			form.action = "AddRT.jsp?add=1&type=" + scaleType.value;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.Name.value == "") {
			alert("<%=trans.tslt("Please enter Rating Task Name")%>");		
			form.Name.focus();
		}else {
			alert("<%=trans.tslt("Please enter Rating Code")%>");		
			form.Definition.focus();
		}
		return false;
	}
	return true;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelEdit()
function cancelEdit()
{
	window.close();
	//opener.location.href = 'RatingTask.jsp';
}
</script>


<%	
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
  	
	int pkUser = logchk.getPKUser();
	int compExist = 0;
	String sType[] = new String[2];
	sType[0] = "Importance";
	sType[1] = "Proficiency";
	
	if(request.getParameter("add") != null) {
		if(request.getParameter("Name") != null)	{
  			String RT = request.getParameter("Name");
  			String RTCode = request.getParameter("Definition");
  			String scaleType = request.getParameter("type");
			
			RT = Database.SQLFixer(RT);	
			RTCode = Database.SQLFixer(RTCode);		

			// check whether competency already exists in database
			compExist = EditRT.CheckRTExist(RT, RTCode, scaleType);
						
			if(compExist == 0) {
				try{					
					boolean add = EditRT.addRecord(RT, RTCode, scaleType, pkUser);
					if(add) {
%>
	<script>
		alert("Added successfully");
		window.close()
		opener.location.href = "RatingTask.jsp";
	</script>
<%							
					}
				}catch(SQLException SE) {
				
%>
	<script>
		window.close()
		opener.location.href = "RatingTask.jsp";
	</script>
<%					
				}
			} else {			
%>
	<script>
  		alert("<%=trans.tslt("Record exists")%>");
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//window.location.href('AddRT.jsp');
		window.location.href='AddRT.jsp';
	</script>
<%			
			}

	}
	}
%>	

<form>
<table font span style='font-size:11.0pt;font-family:Arial' width="441" border="0">
  <tr>
    <td width="100"><%= trans.tslt("Rating Task") %></td>
    <td width="331">
      <input name="Name" style='font-size:10.0pt;font-family:Arial' type="text" value="" size="40" maxlength="50">    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><%= trans.tslt("Rating Code") %></td>
    <td>
      <input type="text" name="Definition" style='font-size:10.0pt;font-family:Arial' value="">    
	  </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><%= trans.tslt("Scale Type") %></td>
    <td>
      <select name="scaleType">
	  <%
	  		int a = 0;
			for(a = 0; a < sType.length; a++) {
		%>
			<option value=<%=sType[a]%>><%=sType[a]%>
		<%
			}
		%>
      </select>    </td>
  </tr>
</table>
<p></p>
<table width="411">
	<tr>
		<td width="103">&nbsp;</td>
		<td width="63"><input type="button" name="Save" value="<%= trans.tslt("Save") %>" onClick="return confirmAdd(this.form, this.form.scaleType)" align="right"></td>
		<td width="229"><input type="button" name="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit(this.form)"></td>
	</tr>
</table>


</form>
<% } %>
</body>
</html>
