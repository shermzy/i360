<%@ page import="java.sql.*,
                 java.io.*,				 
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<title>Add Organisation</title>
</head>
<SCRIPT LANGUAGE="JavaScript">
function validate()
{
    x = document.Add_Organization
    if (x.txtOrgName.value == "")
    {
	alert("<%=trans.tslt("Enter Organisation Name")%>");
	return false 
	}
	if (x.txtOrgCode.value == "")
    {
	alert("<%=trans.tslt("Enter Organisation Code")%>");
	return false 
	}
	//\\ Added by Ha 02/06/08
	if (confirm("Add a new Organisation? "))
	return true
	else
	return false;
}
</SCRIPT>
<body bgcolor="#FFFFCC">
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
  
if(request.getParameter("btnAdd") != null)
{
	
	String OrgName = request.getParameter("txtOrgName");
	String OrgCode = request.getParameter("txtOrgCode");
	int NameSeq = new Integer(request.getParameter("selNameSeq")).intValue();
        
        String nomRaterMode = request.getParameter("selNomRater"); // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
	boolean canAdd = false;
	try
	{
	// Added by Ha 02/06/08
		canAdd = org.addRecord(OrgCode, OrgName, logchk.getCompany(), NameSeq, logchk.getPKUser(), nomRaterMode);
		if (canAdd){
%>
		<script>
		    alert("Added successfully");
			window.close();
			//opener.location.href('OrganizationList.jsp');
			opener.location.href='OrganizationList.jsp';
		</script>
<%		}
		else{
			//Changed by HA 09/06/08 prompt message when adding cannot be done
			if(OrgName.length()<=150){
%>
				<script>
		    	alert("Record Exists");
				</script>
<%
			} else{
%>				
				<script>
				alert("Organisation name should be less than 150 letters");
				</script>
			<%
			}
			%>
			
	<%  }//end else
	}
	catch(SQLException SQLE)
	{
		%>
		<script>
		alert("<%=trans.tslt("Existing Relation")%>");
		</script>
<%	}

} 
%>
  <form name="Add_Organization" action="Add_Organization.jsp" method="post" onsubmit="return validate()">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font face="Arial" color="#000080" size="2">
		<%= trans.tslt("Add New Organisation") %></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="0" width="98%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Organisation Name") %>:
		</font></td>
		<td><input type="text" name="txtOrgName" size="50"></td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Organisation Code") %>:
		</font></td>
		<td><input type="text" name="txtOrgCode" size="10"></td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Name Sequence") %>:
		</font></td>
		<td><select size="1" name="selNameSeq">
		<option value="0"><%= trans.tslt("Family Name") %>, <%= trans.tslt("Other Name") %></option>
		<option value="1"><%= trans.tslt("Other Name") %>, <%= trans.tslt("Family Name") %></option>
		</select></td>
	</tr>
        <% // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater %>
        <tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
        <tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Nominate Raters") %>:</font></td>
		<td>
                <select size="1" name="selNomRater">
                    <option value="True"><%= trans.tslt("Yes")%></option>
                    <option value="False"><%= trans.tslt("No") %></option>
		</select>
		</td>
	</tr>
</table>
<table border="0" width="96%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="29%">&nbsp;</td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td width="29%" align="right">&nbsp;</td>
		<td align="right" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		
		<td align="right" width="56%">
		<font size="2">
   
		<input type="submit" value="<%= trans.tslt("Register New Organisation") %>" name="btnAdd" style="float: left"></td>
		<td align="right" width="15%">
		<font size="2">
   
		<input type="button" value="<%= trans.tslt("Close") %>" name="btnClose" style="float: right" onclick="window.close()"></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>