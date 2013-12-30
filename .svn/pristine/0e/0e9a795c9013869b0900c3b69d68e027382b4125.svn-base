<%@ page import="java.sql.*,
                 java.io.* "%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                 
<jsp:useBean id="SRT" class="CP_Classes.SurveyRating" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function edit(form, field, RTDesc, RTDisCode)
{
	if(field.value != null)
	{
		// Edited by Eric Lu 22/5/08
		// Displays confirm box to edit rating name
		if (confirm("Save the changes?")) {
			form.action="SurveyRating _Edit.jsp?edit=1";
			form.method="post";
			form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Survey Rating Name")%>");
	}
}
</SCRIPT>
<body bgcolor="#FFFFCC">
<%

String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  {
  
  if(request.getParameter("edit") != null)
  {
  		String RatName = request.getParameter("txtRatName");
  		String RTDesc = request.getParameter("txtRatDesc");
  		String RTDisplayCode = request.getParameter("txtRatDisCode");
  		boolean bIsEdited = CE_Survey.editRating(CE_Survey.getSurvey_ID(),CE_Survey.get_SurvRating(),RatName, RTDesc, RTDisplayCode);
		
		if(bIsEdited) {
%>		
		<script>
			alert("Edited successfully");
			window.close();
			opener.location.href ='SurveyRating.jsp';
		</script>
<%
		}
  }
  
%>
<form name="SurveyRating" action="SurveyRating.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="178"><b><font face="Arial" size="2"><%=trans.tslt("Rating Task Name")%>:</font></b></td>
		<td>
		<font size="2">
		<%
		
		String RatName=" ";
		
		RatName = SRT.getRatingTaskName(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		
		String RatDesc = "";
		
		// Add text box to change rating task description and rating code. 
		RatDesc = SRT.getRatingTaskDesc(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		if(RatDesc == null)
			RatDesc = "";
		String RTDisplayCode = "";
		RTDisplayCode = SRT.getRTDisplayCode(CE_Survey.getSurvey_ID(), CE_Survey.get_SurvRating());
		if(RTDisplayCode == null)
			RTDisplayCode = "";
		%>
		</font>
		<input type="text" name="txtRatName" size="58" value="<%=RatName%>"></td>
		<tr>
		<td width="178"><b><font face="Arial" size="2"><%=trans.tslt("Rating Task Description")%>:</font></b></td>
		<td><input type="text" name="txtRatDesc" size="58" value="<%=RatDesc%>"></td>
		</tr>
		<tr>
		<td width="178"><b><font face="Arial" size="2"><%=trans.tslt("Rating Task Display Code")%>:</font></b></td>
		<td><input type="text" name="txtRatDisCode" size="10" value="<%=RTDisplayCode%>"></td>
		</tr>
	</tr>
	<tr>
		<td width="178">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="178"><input type="button" value="<%=trans.tslt("Cancel")%>" name="btnCancel" onclick="window.close()"></td>
		<td>
		<input type="button" value="<%=trans.tslt("Save")%>" name="btnEdit" onclick="edit(this.form,this.form.txtRatName, this.form.txtRatDesc, this.form.txtRatDisCode)" style="float: right"></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>