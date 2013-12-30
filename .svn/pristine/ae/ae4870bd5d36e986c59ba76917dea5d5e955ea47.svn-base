<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
                 
<jsp:useBean id="RaterRelation" class="CP_Classes.RaterRelation" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SurveyRelationSpecific" class ="CP_Classes.SurveyRelationSpecific" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session"/>

<html>
<head>
<title>Superior - Branch Menu</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function closeME(form)
{ 
	form.action = "RaterRelation_BranchMenu.jsp?close=1";
	form.method="post";
	form.submit();
}

function Delete(form)
{
	// Edited Eric Lu 14/5/08
	if (confirm("Delete Specific Relation?")) {
		form.action = "RaterRelation_BranchMenu.jsp?delete=1";
		form.method="post";
		form.submit();
	}
}

function Editing(form, field1)
{
	if(field1.value != "")
	{
		// Edited Eric Lu 14/5/08
		if (confirm("Edit Specific Relation?")) {
			form.action = "RaterRelation_BranchMenu.jsp?edit=1";
			form.method="post";
			form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Specific Relation Name")%>");
	}
}
</script>
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
		try
		{
			// Edited Eric Lu 14/5/08
			
   /*
	*Change(s): Use class SurveyRelationSpecific to manage relation specifics
	*Reason(s): To associate relation specific to survey instead of organization
	*Updated by: Liu Taichen
	*Updated on: 5 June 2012
	*/
			String branch_Name = request.getParameter("txtParent");
			boolean bRecordEdited = SurveyRelationSpecific.update(RaterRelation.getRelSpec(), branch_Name, logchk.getPKUser());
			
			if (bRecordEdited) {
				RaterRelation.setRelHigh(0);
				RaterRelation.setRelSpec(0);
	%>		
		<script>
			alert("Specific Relation edited successfully");
			window.close();
			opener.location.href ='RaterRelation.jsp';
		</script>
	<%	
			} else {
//by Hemilda date 06/06/2008 change prompt message
	%>
		<script>
			alert("Specific Relation Exist");
		</script>
	<%
			}
		}
		catch (SQLException sqle) 
	    {
	
			%>
				<script>
					alert("<%=trans.tslt("Existing Relation")%>");
				</script>
	<%	}
	}

if(request.getParameter("delete") != null)
{
	// Edited Eric Lu 14/5/08
	
	/*
	*Change(s): Use class SurveyRelationSpecific to manage relation specifics
	*Reason(s): To associate relation specific to survey instead of organization
	*Updated by: Liu Taichen
	*Updated on: 5 June 2012
	*/
	boolean bRecordDeleted = SurveyRelationSpecific.delete(RaterRelation.getRelSpec(), logchk.getPKUser());
	
	if (bRecordDeleted) {
		RaterRelation.setRelHigh(0);
		RaterRelation.setRelSpec(0);
	%>
		<script>
			alert("Specific Relation deleted successfully");
			window.close();
			opener.location.href ='RaterRelation.jsp';
		</script>
<%		
	} else {
%>
		<script>
			alert("Specific Relation could not be deleted");
		</script>
<%
	}		
}

if(request.getParameter("close") != null)
{	
	RaterRelation.setRelHigh(0);
	RaterRelation.setRelSpec(0);
	%>
	<script>
		window.close();
		opener.location.href ='RaterRelation.jsp';
	</script>
<%
}

%>
<form name="RaterRelation_BranchMenu" action="RaterRelation_BranchMenu.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-width: 0px; " bordercolor="#3399FF">
	<tr>
		<td width="981" style="border-style:none; border-width:medium; " colspan="4">
		<b><font face="Arial" size="2" color="#000080"><%=trans.tslt("Branch Menu")%></font></b></td>
	</tr>
	<tr>
		<td width="981" style="border-style:none; border-width:medium; " colspan="4" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="981" style="border-left-style:solid; border-left-width:1px; border-right-style:solid; border-right-width:1px; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" colspan="4" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="138" style="border-left-style:solid; border-left-width:1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">
		<p align="center"></td>
		<td width="137" style="border-style:none; border-width:medium; " bordercolor="#3399FF">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Delete Specific Relation")%>" name="btnDelete" onclick= "Delete(this.form)">
    	
    	</td>
		<td width="490" style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" colspan="2" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="985" style="border-left-style:solid; border-left-width:1px; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" colspan="4" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-left-style:solid; border-left-width:1px; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="503" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" colspan="2" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="51%" style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-left-style:solid; border-left-width:1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="503" style="border-style:none; border-width:medium; " colspan="2" bordercolor="#3399FF">
		<p align="left"><b><font face="Arial" size="2"><%=trans.tslt("Specific Relation Name")%>:</font></b></td>
		<td width="51%" style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">
		<p align="center">
		<font face="Arial"><font size="2">
		<%
		String name= "";
		/*
		*Change(s): Use class SurveyRelationSpecific to manage relation specifics
		*Reason(s): To associate relation specific to survey instead of organization
		*Updated by: Liu Taichen
		*Updated on: 5 June 2012
		*/
		String sRelSpec = SurveyRelationSpecific.getRelationSpecific(RaterRelation.getRelSpec());
		
		if(!sRelSpec.equals(""))
		{
		%>
		</font><span style="font-size: 11pt">
		<input name="txtParent" size="30" style="float: left" value="<%=sRelSpec%>"></span></font></td>
	<%	}	%>
	</tr>
	<tr>
		<td width="981" style="border-left-style:solid; border-left-width:1px; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" colspan="4" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-left-style:solid; border-left-width:1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="503" style="border-style:none; border-width:medium; " colspan="2" bordercolor="#3399FF">
		<font size="2">
   
		<input type ="button" value="<%=trans.tslt("Edit Specific Relation")%>" name="btnEdit" onclick="Editing(this.form, this.form.txtParent)"></td>
		<td width="51%" style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF">&nbsp;
		
   
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-left-style:solid; border-left-width:1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="503" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" colspan="2" bordercolor="#3399FF">&nbsp;
		</td>
		<td width="51%" style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium">&nbsp;
		</td>
		<td width="503" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" colspan="2">&nbsp;
		</td>
		<td width="51%" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="4%" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
		<td width="503" style="border-style:none; border-width:medium; " colspan="2">
		<p align="center">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Cancel")%>" name="btnCancel" onclick="closeME(this.form)" style="float: left"></td>
		<td width="51%" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
	</tr>
	</table>
</form>
<%	}	%>
</body>
</html>	