
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Development Activities</title>


<%@ page import = "java.util.Vector" %>
<%@ page import = "CP_Classes.vo.votblDRA"%>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "java.sql.*" %>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

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
<jsp:useBean id="Competency" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">

// Change by Ha on 14/05/08: import Vector, votblDRA, voCompetency, sql

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmEdit(form, CompList, type)
function confirmEdit(form, CompList, type)
{
	if(form.Statement0.value != "") {
		if(confirm("<%=trans.tslt("Edit Development Activity")%>?")) {
			form.action = "EditDRA.jsp?edited=" + type + "&CompList=" + CompList;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		alert("<%=trans.tslt("Please select Competency and/or enter Development Activity for English version")%>");
		form.Statement0.focus();
		return false;
	}
	return true;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelEdit()
function cancelEdit()
{
	window.close();
}
</script>

<%
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);

	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	int pkDRA = 0, fkComp = 0, fkComp1 = 0;
	// fkComp is used to fill up the select options menu
	// fkComp1 is used as reference for edition
	String[] statement = new String[6];
	String CompName = "", fkCompName = "";
	
	fkComp1 = DRA.getFKCom();
	pkDRA = DRA.getDRAID();
	
	if (pkDRA != 0) {
		
		/*****************************************
		*Edit By James 15 - Nov 2007
		**********************************************/
		
		
		votblDRA vo_Edit=DRA.getDRA(pkDRA);
		
		if(vo_Edit!=null)
		statement = vo_Edit.getAllDRAStatement();
		for(int i = 0; i < statement.length; i++){
			if(statement[i] == null)
				statement[i] = "";
		}
		
	}
	
	Vector CompResult = Competency.FilterRecord(compID, OrgID);
	
 			
	if (request.getParameter("clicked") != null) 
	{
		pkDRA = Integer.parseInt(request.getParameter("clicked"));
		
		DRA.setDRAID(pkDRA);
		
		/*****************************************
		*Edit By James 15 - Nov 2007
		**********************************************/
		votblDRA vo_EditDRA=DRA.getDRA(pkDRA);
		
		if(vo_EditDRA!=null)			
			fkComp1 = vo_EditDRA.getCompetencyID();
		
		DRA.setFKCom(fkComp1);
		
		statement = vo_EditDRA.getAllDRAStatement();
		for(int i = 0; i < statement.length; i++){
			if(statement[i] == null)
				statement[i] = "";
		}	
		/*****************************************
		*Edit By James 15 - Nov 2007
		**********************************************/
	
		voCompetency voComp=Competency.getCompetency(fkComp1);
		
		if(voComp!=null)
		fkCompName = voComp.getCompetencyName();
		
		
		int check = DRA.CheckSysLibDRA(pkDRA);
			
			if(check == 1 && userType != 1) {				
%>
<script>
	alert("The edited System Generated Library will be saved as a new User Generated Library");
</script>	

<%									
			}						
	}
  	else {
		if(request.getParameter("edited") != null)
		{
			statement[0] = request.getParameter("Statement0");
			statement[1] = request.getParameter("Statement1");
			statement[2] = request.getParameter("Statement2");
			statement[3] = request.getParameter("Statement3");
			statement[4] = request.getParameter("Statement4");
			statement[5] = request.getParameter("Statement5");
			pkDRA = Integer.parseInt(request.getParameter("pkDRA"));
    		fkComp1 = Integer.parseInt(request.getParameter("CompList"));		// in case the competency has been edited
			
			statement[0] = Database.SQLFixer(statement[0]);	
			statement[1] = Database.SQLFixer(statement[1]);	
			statement[2] = Database.SQLFixer(statement[2]);	
			statement[3] = Database.SQLFixer(statement[3]);	
			statement[4] = Database.SQLFixer(statement[4]);	
			statement[5] = Database.SQLFixer(statement[5]);	
						
			if(Integer.parseInt(request.getParameter("edited")) == 1) {	
   				try{
   					/******************************
   					* Edited by junwei March 5 2008
   					******************************/
   					int exist = DRA.CheckDRAExist(fkComp1, statement[0], compID, OrgID);
   					// Changed by Ha 11/06/08 so the user can edit the record
   					// if he does not change the old record
   					if(exist == 0||exist==pkDRA){
						boolean edit = DRA.editRecord(fkComp1, pkDRA, statement, pkUser);
						if(edit) {
						%>	
							<script>
							alert("Edited successfully");
							window.close();
							opener.location.href = 'DevelopmentActivities.jsp';
							</script>
						<%					
						}
					}
					else{
					%>
						<script>
						alert("Record exists");
						//commented off by Yuni 6/3/08
						//This will refresh the page and user will lose the data they entered
						//window.location.href('EditDRA.jsp');
						</script>
					<%
					}
					
				} catch(SQLException SE) {
					%>
					<script>
						alert('<%=trans.tslt("Record exists")%>');
						
						//commented off by Yuni 6/3/08
						//This will refresh the page and user will lose the data they entered
						//window.location.href('EditDRA.jsp');
					</script>	
					<%										
				}
				DRA.setFKCom(fkComp1);
				
			}
			else if(Integer.parseInt(request.getParameter("edited")) == 2) {	// admin or syslib
				int exist = DRA.CheckDRAExist(fkComp1, statement[0], compID, OrgID);
				if(exist == 0) {
					try {
						
						// Added logic codes so that when sa create development activity it is auto-defaulted to isSystemGenerated = 1, Desmond 19 August 2010
						String isSysGen = "1";
						if (userType != 1) isSysGen = "0";
						
						// Added missing isSystemGenerated parameter for addRecord method, Desmond 19 August 2010
						boolean add = DRA.addRecord(fkComp1, statement, isSysGen, compID, OrgID, pkUser, userType);
						DRA.setFKCom(fkComp1);
						
						if(add) {
						%>
						<script>
							alert("Added successfully");
							window.close();
							opener.location.href = 'DevelopmentActivities.jsp';
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
						alert('<%=trans.tslt("Record exists")%>');
						//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
						//window.location.href('EditDRA.jsp');
						window.location.href='EditDRA.jsp';
					</script>	
					<%						
				}

		}
		}
	}
%>	

<form name="EditDRA" method="post">
  <table border="0" width="450" height="116" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td height="12"><%= trans.tslt("Competency") %></td>
      <td height="12">
        <select name="CompList">
        <%
	/********************
	* Edited by James 30 Oct 2007
	************************/			
			//while(CompResult.next()) {
		
			for(int i=0; i<CompResult.size(); i++) {
			voCompetency voC = (voCompetency)CompResult.elementAt(i);
				fkComp = 	voC.getPKCompetency();
				CompName = 	voC.getCompetencyName();
				
				if(fkComp1 != 0 && fkComp == fkComp1) {
		%>
			<option value=<%=fkComp1%> selected><%=CompName%>
		<%
			fkComp1 = 0;
			}else {
		%>
			<option value=<%=fkComp%>><%=CompName%>
		<%
			}
			}
		%>
        </select></td>
    </tr>
    <tr>
      <td width="77" height="12"></td>
      <td width="16" height="12"></td>
      <td width="478" height="12"></td>
    </tr>
    <tr>
      <td width="77" height="20"><%= trans.tslt("Statement") %></td>
      <td width="16" height="20">&nbsp;</td>
      </tr>
    <tr>
    <td width="77" height="84">English</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement0" cols="50" rows="4" id="textarea"><%=statement[0]%></textarea></font></td>
    </tr>
    <tr>
    <td width="77" height="84">Indonesian</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement1" cols="50" rows="4" id="textarea"><%=statement[1]%></textarea></font></td>
    </tr>
    <tr>
    <td width="77" height="84">Thai</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement2" cols="50" rows="4" id="textarea"><%=statement[2]%></textarea></font></td>
    </tr>
    <tr>
    <td width="77" height="84">Korean</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement3" cols="50" rows="4" id="textarea"><%=statement[3]%></textarea></font></td>
    </tr>
    <tr>
    <td width="77" height="84">Traditional Chinese</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement4" cols="50" rows="4" id="textarea"><%=statement[4]%></textarea></font></td>
    </tr>
    <tr>
    <td width="77" height="84">Simplified Chinese</td>
    <td width="478" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement5" cols="50" rows="4" id="textarea"><%=statement[5]%></textarea></font></td>
	 </tr>
  </table>
  <blockquote>
    <blockquote>
      <p>
	  	 <input name="pkDRA" type="hidden" id="pkDRA" value="<%=pkDRA%>" size="10">
		&nbsp;		
<%
	if(DRA.CheckSysLibDRA(pkDRA) == 1 && userType != 1) {
%>				
		<input type="submit" name="Edit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 1)" disabled>
<%	} else { %>		
        <input type="submit" name="Edit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 1)">
<% } %>&nbsp;&nbsp;
		<input type="submit" name="SaveAs" value="<%= trans.tslt("Save As New") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 2)">&nbsp;&nbsp;
        <input name="Cancel" type="submit" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit(this.form)">
      </p>
    </blockquote>
  </blockquote>
</form>
</body>
</html>
