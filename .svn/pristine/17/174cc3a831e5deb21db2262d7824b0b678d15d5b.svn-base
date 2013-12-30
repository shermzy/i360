<%@ page import="java.sql.*"%>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import = "java.util.*" %>
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
<jsp:useBean id="EditRT" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import = "CP_Classes.vo.votblRatingTask" %>

<script language = "javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmEdit(form, field)
function confirmEdit(form, field)
{
	if(form.ratingTask.value != "" && form.ratingCode.value != "") {
		if(confirm("<%=trans.tslt("Edit Rating Task")%>?")) {
			form.action = "EditRT.jsp?edit=" + field.value;
			form.method = "post";
			form.submit();
		}else{
			return false;
		}
	}  else {
		if(form.ratingTask.value == "") {
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
	int RTID = 0;	// rating id
	String ratingTask = "";
	String ratingCode = "";
	String scaleType = "";
	
	RTID = EditRT.getRTID();
	if(RTID != 0) {
		votblRatingTask vo = EditRT.getRatingTaskByID(RTID);
		ratingCode = vo.getRatingCode();
		ratingTask = vo.getRatingTask();
		scaleType = vo.getScaleType();
		
	}

	if(request.getParameter("clicked") != null) {
		RTID = Integer.parseInt(request.getParameter("clicked"));
		EditRT.setRTID(RTID);
		
		votblRatingTask vo = EditRT.getRatingTaskByID(RTID);
		ratingCode = vo.getRatingCode();
		ratingTask = vo.getRatingTask();
		scaleType = vo.getScaleType();

	} else if (request.getParameter("edit") != null) {
		RTID = Integer.parseInt(request.getParameter("RTID"));
		ratingTask = request.getParameter("ratingTask");
		scaleType = request.getParameter("edit");
		ratingCode = request.getParameter("ratingCode");
		
		try{
			/*********************************
			* Edited by junwei on 5 March 2008
			*********************************/
			
			boolean result = EditRT.checkRatingTaskCodeExist(ratingTask, ratingCode, RTID);
			if(result == false){
				boolean edit = EditRT.editRecord(RTID, ratingTask, ratingCode, scaleType, pkUser);
				if(edit) {				
							%>
								<script>
									alert("Edited successfully");
							  		window.close();
									opener.location.href = 'RatingTask.jsp';
								</script>
							<%
					}	
			}
			else{
				%>
				<script>
					alert('<%=trans.tslt("Record exists")%>');
				</script>
				<%
			}
		} catch(SQLException SE) {
			%>
				<script>
			  		alert('<%=trans.tslt("Record exists")%>');
			  		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
			  		//window.location.href('EditRT.jsp');
			  		window.location.href='EditRT.jsp';
				</script>
			<%		
		}
}
%>

<form>
<table font span style='font-size:11.0pt;font-family:Arial' width="441" border="0">
  <tr>
    <td width="100"><%= trans.tslt("Rating Task") %></td>
    <td width="331">
      <input name="ratingTask" style='font-size:10.0pt;font-family:Arial' type="text" value="<%=ratingTask%>" size="40" maxlength="50">    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><%= trans.tslt("Rating Code") %></td>
    <td>
      <input type="text" style='font-size:10.0pt;font-family:Arial' name="ratingCode" value="<%=ratingCode%>">    </td>
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
	    	
		Vector v = EditRT.getScaleType();
	
		for(int i=0; i<v.size(); i++) {
				String type = (String)v.elementAt(i);
				if(scaleType.equals(type)) {
		%>
			<option value=<%=scaleType%> selected><%=scaleType%>
		<% } else {
		%>
			<option value=<%=type%>><%=type%>
		<%
			}
			}
		%>
      </select>    </td>
  </tr>
</table>
<p></p>
<input type="hidden" name="RTID" value="<%=RTID%>">
<table>
<tr>
<td width="95">&nbsp;</td>
<td width="54">
<input type="button" name="Save" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.scaleType)">
</td>
<td width="236">
<input type="button" name="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit(this.form)">
</td>
</tr></table>


</form>
<% } %>
</body>
</html>
