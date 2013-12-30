<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
                 CP_Classes.vo.votblOrganization"%>  
				 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Edit Organisation</title>
</head>
<SCRIPT LANGUAGE="JavaScript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 100;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


function validate()
{
    x = document.Edit_Organization
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
	
	// Edited Eric Lu 15-May-08
	// Added confirmation box for editing organization
	if (confirm("Edit Organisation?")) {
		return true;
	} else {
		return false;
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
  
if(request.getParameter("btnEdit") != null)
{
	String OrgName = request.getParameter("txtOrgName");
	String OrgCode = request.getParameter("txtOrgCode");
	int NameSeq = new Integer(request.getParameter("selNameSeq")).intValue();
        
        // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
        String nomRater = request.getParameter("selNomRater");
        
        // Changed by DeZ, 18/06/08, to add function to enable/disable Nominate Rater
	boolean bIsEdited = org.editRecord(logchk.getOrg(), OrgCode, OrgName, logchk.getCompany(), NameSeq, logchk.getPKUser(), nomRater);
	
	if(bIsEdited) {
	%>
	<script>
		alert("Edited successfully");
		window.close();
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//opener.location.href('OrganizationList.jsp');
		opener.location.href='OrganizationList.jsp';
	</script>
	<%
	}
}

%>
  <form name="Edit_Organization" action="Edit_Organization.jsp" method="post" onSubmit="return validate()">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font face="Arial" color="#000080" size="2"><%= trans.tslt("Edit Organisation") %></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="0" width="98%" cellspacing="0" cellpadding="0">
<%
	int PKOrg=0;
	int NameSeq=0;
	String NameSequence = "";
	String OrgCode="";
	String OrgName="";
        String nomRater="";
        
	/*****************************
	* Edit By James 15 - Nov 2007
	***********************************/
	
	votblOrganization vo_Org=org.getOrganization(logchk.getOrg());
	String logoName = "";
	
        
	if(vo_Org!=null)
	{
		PKOrg = vo_Org.getPKOrganization();
		OrgCode = vo_Org.getOrganizationCode();		
		OrgName = vo_Org.getOrganizationName();
		logoName = vo_Org.getOrganizationLogo();
		NameSeq = vo_Org.getNameSequence();
                
		if(NameSeq == 0)
			NameSequence = "FamilyName, OtherName";
		else
			NameSequence = "OtherName, FamilyName";
	}
	%>

	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Organisation Name") %>:</font></td>
		<td width="498">
<%
	System.out.println(setting.getIsStandalone());
	if(setting.getIsStandalone() == false || (setting.getIsStandalone() && logchk.getUserType() == 1))
	{	%>
			<input type="text" name="txtOrgName" size="50" value="<%=OrgName%>">
<%	}	
	else
	{	%>
			<input type="text" name="txtOrgName" size="50" value="<%=OrgName%>" readonly>
<%	}	%>			
		
		</td>
<%-- 
// Edited Eric Lu 14/5/08 
// Wrapped image name with "" so that image file names with spaces inside will not prevent image from rendering
--%>
		<td width="304" rowspan="5" align="center" valign="top"><img src="<%="Logo/" + logoName%>" width="160" height="100"></td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Organisation Code") %>:</font></td>
		<td>
<%
	if(setting.getIsStandalone() == false || (setting.getIsStandalone() && logchk.getUserType() == 1))
	{	%>		
		<input type="text" name="txtOrgCode" size="10" value="<%=OrgCode%>">
<%	}	
	else
	{	%>
		<input type="text" name="txtOrgCode" size="10" value="<%=OrgCode%>" readonly>
<%	}	%>		
		</td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="181"><font face="Arial" size="2">
		<%= trans.tslt("Name Sequence") %>:</font></td>
		<td><select size="1" name="selNameSeq">
		<option value="0"><%= trans.tslt("Family Name") %>, <%= trans.tslt("Other Name") %></option>
		<option value="1"><%= trans.tslt("Other Name") %>, <%= trans.tslt("Family Name") %></option>
		<script>
			window.document.Edit_Organization.selNameSeq.selectedIndex=<%=NameSeq%>;
		</script>
		</select><font size="2"> </font>
		
		</td>
	</tr>
    <tr>
		<td width="181">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
    <% // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater %>
    <tr>
        <td width="181"><font face="Arial" size="2">
        <%= trans.tslt("Nominate Raters") %>:</font></td>
        <td>
            <select size="1" name="selNomRater">
                <option value="True"><%= trans.tslt("Yes")%></option>
                <option value="False"><%= trans.tslt("No") %></option>
            </select>
            <script>
                <%
                int nomRaterCurr = 1;
                if(vo_Org.getNomRater()) { nomRaterCurr = 0; }
                %>
                window.document.Edit_Organization.selNomRater.selectedIndex=<%=nomRaterCurr%>;
            </script>
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
		<td align="left" width="56%"><input type="submit" value="<%= trans.tslt("Save Changes") %>" name="btnEdit" style="float: left"></td>
		<td align="right" width="15%">
		<font size="2">
   
		<input type="button" value="<%= trans.tslt("Close") %>" name="btnClose" style="float: right" onClick="window.close()"></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>