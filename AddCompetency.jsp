<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "CP_Classes.vo.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Competency</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body style="background-color: #DEE3EF">
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">
function confirmAdd(form)
{
	if(form.Name.value != "" && form.Definition.value != "") {
		if(confirm("<%=trans.tslt("Add Competency")%>?")) {
			form.action = "AddCompetency.jsp?add=1";
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.Name.value == "") {
			alert("<%=trans.tslt("Please enter Competency Name")%>");
			form.Name.focus();
		}else {
			alert("<%=trans.tslt("Please enter Definition")%>");
			form.Definition.focus();
		}
		return false;
	}
	return true;
}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelAdd()
function cancelAdd()
{
	window.close();
	//opener.location.href = 'Competency.jsp';
}
</script>

<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {
%> <font size="2">
   
<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

%>

<%
	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	int compExist = 0;
	
	if(request.getParameter("add") != null) {
		if(request.getParameter("Name") != null)	{
  			String name = request.getParameter("Name");
  			String definition = request.getParameter("Definition");
  			
			name = Database.SQLFixer(name);	
			definition = Database.SQLFixer(definition);		
			
			
			// check whether competency already exists in database
			compExist = Comp.CheckCompetencyExist(name, definition, orgID, compID);
						
			if(compExist == 0) {						
				try{					
					boolean add = Comp.addRecord(name, definition, compID, orgID, pkUser, userType);
					if(add) {
						int pkComp = Comp.CheckCompetencyExist(name, definition, orgID, compID);
						Comp.setPKComp(pkComp);
%>
	<script>
		alert("<%=trans.tslt("Please proceed to create Key Behaviours. Each Competency should have at least 3 Key Behaviours")%>.");
		window.location.href = "AddKB.jsp?comp=1";
	</script>
<%							
					}
				}catch(SQLException SE) {
				
%>
	<script>
		alert("<%=trans.tslt("Please proceed to create Key Behaviours. Each Competency should have at least 3 Key Behaviours")%>.");
		window.location.href = "AddKB.jsp?comp=1";
	</script>
<%					
				}
			} else {			
%>
	<script>
  		alert("<%=trans.tslt("Record exists")%>");
		//window.location.href('AddCompetency.jsp');
		window.location.href='AddCompetency.jsp';
	</script>
<%			
			}

	}
	}
%>	


<form name="AddCompetency" method="post">
  <table border="0" width="480" height="141" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="70" height="33"><%= trans.tslt("Competency") %></td>
      <td width="10" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="Name" type="text"  style='font-size:10.0pt;font-family:Arial' id="Name" size="50" maxlength="50">
	  </td>
    </tr>
    <tr>
      <td width="82" height="12"></td>
      <td width="10" height="12"></td>
      <td width="303" height="12"></td>
    </tr>
    <tr>
      <td width="82" height="84"><%= trans.tslt("Definition") %></td>
      <td width="10" height="84">&nbsp;</td>
      <td width="303" height="84">
    	<textarea name="Definition" style='font-size:10.0pt;font-family:Arial' cols="50" rows="5" id="textarea"></textarea>
	  </td>
    </tr>
  </table>
  <blockquote>
    <blockquote>
      <p>
		<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</font>		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">		
	        <input type="button" name="Submit" value="<%= trans.tslt("Submit") %>" onClick="return confirmAdd(this.form)"></font><font span style='font-family:Arial'>
		</font>
			<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </font>
		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">
			<input name="Cancel" type="button" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelAdd()">
			</font> </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>