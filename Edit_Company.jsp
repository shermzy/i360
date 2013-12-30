<%@ page import="java.sql.*,
                 java.io.*,
                 CP_Classes.vo.votblConsultingCompany,
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="CC" class="CP_Classes.ConsultingCompany" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<title>Edit Consulting Company</title>
</head>
<SCRIPT LANGUAGE="JavaScript">
function validate()
{
    x = document.Edit_Company
    if (x.txtCCName.value == "")
    {
	alert("<%=trans.tslt("Enter Consulting Company Name")%>");
	return false 
	}
	if (x.txtCCDesc.value == "")
    {
	alert("<%=trans.tslt("Enter Consulting Company Description")%>");
	return false 
	}
	//\\Added by Ha 02/06/08
	if (confirm ("Edit Company? "))
	return true;
	else return false;
	
	return true
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
	boolean canEdit = false;	
	boolean isExist = false;
	String xCCName = request.getParameter("txtCCName");
	String xCCDesc = request.getParameter("txtCCDesc");
    //Added by Ha 02/06/08-reedit by Ha 09/08/08
    int action  = 2;//2 means edit
    isExist = CC.checkRecordExist(xCCName, xCCDesc, logchk.getPKUser(),logchk.getCompany(),action);
    	if (isExist == false)    	
    		canEdit = CC.editRecord(logchk.getCompany(),xCCName, xCCDesc, logchk.getPKUser());
    
	if (canEdit)
	{%>
		<script>
		alert("Edited successfully");
		window.close();
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//opener.location.href('OrganizationList.jsp');
		opener.location.href='OrganizationList.jsp';
		</script>
	<%}
	else
	{%>
		<script>
		alert("Record Exists");		
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//opener.location.href('Edit_Company.jsp');
		opener.location.href='Edit_Company.jsp';
		</script>
	<%}
	%>
	<script>
		window.close();
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//opener.location.href('OrganizationList.jsp');
		opener.location.href='OrganizationList.jsp';
	</script>
	<%
}

%>
  <form name="Edit_Company" action="Edit_Company.jsp" method="post" onsubmit="return validate()">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font face="Arial" color="#000080" size="2"><%= trans.tslt("Edit Company") %></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<%
	int NameSeq=0;

	votblConsultingCompany vo = CC.getConsultingCompany(logchk.getCompany());
	int CCID = vo.getCompanyID();
	String CCName = vo.getCompanyName();
	if (CCName == null) CCName = "";
	String CCDesc = vo.getCompanyDesc();
	if (CCDesc == null) CCDesc = "";

	%>

	<tr>
		<td width="181"><font face="Arial" size="2"><%= trans.tslt("Company Name") %>:</font></td>
		<td colspan="3"><input type="text" name="txtCCName" size="50" value="<%=CCName%>"></td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td colspan="2">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="181"><font face="Arial" size="2"><%= trans.tslt("Company Description") %>:</font></td>
		<td colspan="3"><input type="text" name="txtCCDesc" size="50" value="<%=CCDesc%>"></td>
	</tr>
	<tr>
		<td width="181">&nbsp;</td>
		<td colspan="2">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="29%" align="right">&nbsp;</td>
		<td align="right" colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="29%" align="right">
		<p align="center">
		</td>
		<td align="right" width="25%">
		<font size="2">
   
		<input type="submit" value="<%= trans.tslt("Save Changes") %>" name="btnEdit" style="float: right"></td>
		<td align="right" width="24%">&nbsp;
		</td>
		<td align="right" width="22%">
		<font size="2">
   
		<input type="button" value="<%= trans.tslt("Close") %>" name="btnClose" style="float: right" onclick="window.close()"></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>