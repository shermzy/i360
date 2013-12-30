<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Development Activities</title>
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
<jsp:useBean id="DRA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">
function confirmAdd(form, CompID)
{
   
	if(form.Statement.value != "" && CompID != 0) {
		if(confirm("<%=trans.tslt("Add Development Activity")%>?")) {
			form.action = "AddDRA.jsp?add=1&CompID=" + CompID;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		alert("<%=trans.tslt("Please select Competency and/or enter Development Activity")%>");
		return false;
	}
	return true;
}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelAdd()
function cancelAdd()
{
	window.close();
	//opener.location.href = 'DevelopmentActivities.jsp';
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
  	
	String CompName, fkCompName;
	int fkComp, fkComp1;
	
	fkCompName = "";
	CompName = "";
	fkComp = 0;
	fkComp1 = DRA.getFKCom();

	int orgID = logchk.getOrg();	
	System.out.println("Organization id is "+orgID);
	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
%>

<% 
	Vector CompResult = Comp.FilterRecord(compID, orgID);

	if(request.getParameter("CompID") != null) {
		fkComp1 = Integer.parseInt(request.getParameter("CompID"));
		DRA.setFKCom(fkComp1);
		
		fkCompName = Comp.CompetencyName(fkComp1);
	}
	
	if(request.getParameter("add") != null) {
		if(request.getParameter("Statement") != null)	{
  			String DRAStatement = request.getParameter("Statement");

			DRAStatement = Database.SQLFixer(DRAStatement);	

			fkComp1 = Integer.parseInt(request.getParameter("CompID"));
			
			int exist = DRA.CheckDRAExist(fkComp1, DRAStatement, compID, orgID);
			if(exist == 0) {
				try {
					
					// Added logic codes so that when sa create development activity it is auto-defaulted to isSystemGenerated = 1, Desmond 19 August 2010
					String isSysGen = "1";
					if (userType != 1) isSysGen = "0";
					
					// Added missing isSystemGenerated parameter for addRecord method, Desmond 19 August 2010
					boolean add = DRA.addRecord(fkComp1, DRAStatement, isSysGen, compID, orgID, pkUser, userType);
					DRA.setFKCom(fkComp1);
					if(add) {
%>
			<script>
					alert("Added successfully");
					window.close();
					opener.location.href = "DevelopmentActivities.jsp";
			</script>
<%
					}
				}catch(SQLException SE) {
%>
					<script>
				  		window.close();
						opener.location.href = 'DevelopmentActivities.jsp';
					</script>				
<%
				}
			} else {
%>
	<script>
  		alert("<%=trans.tslt("Record exists")%>");
  		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//window.location.href('AddDRA.jsp');
		window.location.href='AddDRA.jsp';
		//opener.location.href = "AddDRARes.jsp?";
	</script>

<%	
	}
		}
	}
%>	


<form name="AddDRA" method="post">
  <table border="0" width="585" height="130" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td height="12"><%= trans.tslt("Competency") %></td>
      <td height="12"></td>
      <td height="12">	  
        <select name="CompList"><font span style='font-size:10.0pt;font-family:Arial'>
        <% int t = 0;
		%>
		<option value=<%=t%>>Please select one
		<%
	/********************
	* Edited by James 30 Oct 2007
	************************/			
			//while(CompResult.next()) {
			for(int i=0; i<CompResult.size(); i++) {
			voCompetency voC = (voCompetency)CompResult.elementAt(i);
				fkComp = 	voC.getPKCompetency();
				CompName = 	voC.getCompetencyName();
				
				if(fkComp1 != 0 && fkComp1 == fkComp) {
		%>
			<option value=<%=fkComp1%> selected><%=CompName%>
		<% 		fkComp1 = 0;
				} else {
		%>
			<option value=<%=fkComp%>><%=CompName%>
		<%
			}
			}
		%>
        </font></select>
</td>
    </tr>
    <tr>
      <td width="77" height="12"></td>
      <td width="16" height="12"></td>
      <td width="478" height="12"></td>
    </tr>
    <tr>
      <td width="77" height="84"><%= trans.tslt("Statement") %></td>
      <td width="16" height="84">&nbsp;</td>
      <td width="478" height="84">
    
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement" cols="50" rows="5" id="textarea"></textarea>    </td>
    </tr>
  </table>
  <blockquote>
    <blockquote>
      <p>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
        <input type="button" name="Submit" value="<%= trans.tslt("Submit") %>" onClick="return confirmAdd(this.form, this.form.CompList.options[CompList.selectedIndex].value)">&nbsp;&nbsp;&nbsp;&nbsp;
        <input name="button" type="submit" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelAdd(this.form)">
      </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>