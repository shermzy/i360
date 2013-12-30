<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Development Resources</title>

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
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="EditDRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="Competency" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmEdit(form, CompID, type)
function confirmEdit(form, CompID, type)
{
	if(form.Resource0.value != "") {
		if(confirm("<%=trans.tslt("Edit Development Resource")%>?")) {
			form.action = "EditDRARes.jsp?edited=" + type + "&CompID=" + CompID;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		alert("<%=trans.tslt("Please select Competency  and/or Resource Type and/or enter Development Resource for English version")%>");
		form.Resource0.focus();
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

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 		
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	String resName [] = {"Books", "Web Resources", "Training Courses", "AV Resources"}; // Change Resource category from "In-house Resources" to "AV Resources", Desmond 10 May 2011
	int ResID = 0, fkComp = 0, fkComp1 = 0, res = 0;
	// fkComp is used to fill up the select options menu
	// fkComp1 is used as reference for edition
	String[] resource = new String[6];
	String CompName = "", fkCompName = "";
	
	res = EditDRARes.getResType();
	fkComp1 = EditDRARes.getFKComp();
	ResID = EditDRARes.getDRAResID();
	if(ResID != 0) {
	/******************************************
	*Edit By James 15 - Nov 2007
	*****************************************/
		//String query = "Select * from tblDRARes where ResID = " + ResID;
		//ResultSet rsEdit = Database.getRecord(query);
		
		votblDRARES vo_DRARES=EditDRARes.getRecord(ResID);
		
		if(vo_DRARES!=null)								
		resource = vo_DRARES.getAllResource();
		
		for(int i = 0; i < resource.length; i++){
			if(resource[i] == null)
				resource[i] = "";
		}
		
	}
	
	Vector CompResult = Competency.getAllRecord();
	
 			
	if (request.getParameter("clicked") != null) 
	{
		ResID = Integer.parseInt(request.getParameter("clicked"));
		EditDRARes.setDRAResID(ResID);
	/******************************************
	*Edit By James 15 - Nov 2007
	*****************************************/
	
		votblDRARES vo_DRARES=EditDRARes.getRecord(ResID);
		
		if(vo_DRARES!=null)	{
						
		fkComp1 = vo_DRARES.getCompetencyID();
		resource = vo_DRARES.getAllResource();
		for(int i = 0; i < resource.length; i++){
			if(resource[i] == null)
				resource[i] = "";
		}
		res = vo_DRARES.getResType();
		}
		
		EditDRARes.setResType(res);
		EditDRARes.setFKComp(fkComp1);
		
		int check = EditDRARes.CheckSysLibDRARes(ResID);

			
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
			resource[0] = request.getParameter("Resource0");
			resource[1] = request.getParameter("Resource1");
			resource[2] = request.getParameter("Resource2");
			resource[3] = request.getParameter("Resource3");
			resource[4] = request.getParameter("Resource4");
			resource[5] = request.getParameter("Resource5");
			ResID = Integer.parseInt(request.getParameter("ResID"));
    		fkComp1 = Integer.parseInt(request.getParameter("CompID"));		// in case the competency has been edited
			res = EditDRARes.getResType();	
			resource[0] = Database.SQLFixer(resource[0]);	
			resource[1] = Database.SQLFixer(resource[1]);	
			resource[2] = Database.SQLFixer(resource[2]);	
			resource[3] = Database.SQLFixer(resource[3]);	
			resource[4] = Database.SQLFixer(resource[4]);	
			resource[5] = Database.SQLFixer(resource[5]);	
						
			if(Integer.parseInt(request.getParameter("edited")) == 1) {					
				try {
					/********************************
					* Edited by juwnei 3 March 2008
					********************************/
					int exist = EditDRARes.CheckDRAResExist(fkComp1, resource[0], res, compID, OrgID);
					//Changed by Ha 11/06/08 to allow user edit the record when he does not change anything
					if(exist == 0||exist==ResID){
	   					boolean edit = EditDRARes.editRecord(fkComp1, ResID, resource, pkUser);
						if(edit) {
						%>
						<script>
							alert("Edited successfully");
							window.close();
							opener.location.href = 'DevelopmentResources.jsp';
						</script>
						<%	
						}
					}
					else{
						%>
						<script>
							alert('<%=trans.tslt("Record exists")%>');
							//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
							//window.location.href('EditDRARes.jsp');
							window.location.href='EditDRARes.jsp';
						</script>	
						<%	
					}
				} catch (SQLException SE) {
					%>
					<script>
						alert('<%=trans.tslt("Record exists")%>');
						//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
						//window.location.href('EditDRARes.jsp');
						window.location.href='EditDRARes.jsp';
					</script>	
					<%						
				}
				DRARes.setFKComp(fkComp1);
				DRARes.setResType(res);
%>
	<script>
  		window.close();
		opener.location.href = 'DevelopmentResources.jsp';
	</script>
<%
			}
			else if(Integer.parseInt(request.getParameter("edited")) == 2) {	// admin or syslib
				int exist = EditDRARes.CheckDRAResExist(fkComp1, resource[0], res, compID, OrgID);
				if(exist == 0) {
					try {
						
						// Added logic codes so that when sa create development resource it is auto-defaulted to isSystemGenerated = 1, Desmond 19 August 2010
						String isSysGen = "1";
						if (userType != 1) isSysGen = "0";
						
						// Added missing isSystemGenerated parameter for addRecord method, Desmond 19 August 2010
						boolean add = EditDRARes.addRecord(fkComp1, resource, res, isSysGen, compID, OrgID, pkUser, userType);
						DRARes.setFKComp(fkComp1);
						DRARes.setResType(res);
						
						if(add) {
%>
							<script>
								alert("Added successfully");
								window.close();
								opener.location.href = 'DevelopmentResources.jsp';
							</script>	

<%				
						}
					}catch(SQLException SE) {
%>
							<script>
								window.close();
								opener.location.href = 'DevelopmentResources.jsp';
							</script>	
<%					}
				} else {
%>
							<script>
								alert('<%=trans.tslt("Record exists")%>');
								//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
								//window.location.href('EditDRARes.jsp');
								window.location.href='EditDRARes.jsp';
							</script>	
							<%				
				}
		}
	}
}
%>	

<form name="EditDRARes" method="post">
<table font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="70" height="12"><%= trans.tslt("Competency") %></td>
      <td width="10" height="12"></td>
      <td width="380" height="12">
      <select name="CompList">
        <%
		
		//	while(CompResult.next()) {
	/********************
	* Edited by James 30 Oct 2007
	************************/		
		for(int i=0; i<CompResult.size(); i++) {
			voCompetency voC = (voCompetency)CompResult.elementAt(i);
				fkComp = 	voC.getPKCompetency();
				CompName = 	voC.getCompetencyName();
				//fkComp = CompResult.getInt(1);
				//CompName = CompResult.getString(2);
				
				if(fkComp1 != 0 && fkComp == fkComp1) {
		%>
        <option value=<%=fkComp%> selected><%=CompName%>
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
</table>

<p></p>

  <table width="614" border="0" font span style='font-size:10.0pt;font-family:Arial'>
    
      <% for(int i=1; i<=resName.length; i++) {
	  %>
	  <tr height="15">
	  	<td>

	  	<% if(res != 0 && res == i) {
	  	%>

	  	<input name="rbtnRes" type="radio" checked value=<%=i%> disabled>
	  	<% res = 0;
	  	}else {
	  	%>

	  	<input name="rbtnRes" type="radio" value=<%=i%> disabled>
	  	<% }
	  	%>
	  	<%=resName[i-1]%></td>
		 </tr>
		<% } %>
	 
  </table>
  
<p></p>
  <table border="0" width="460" height="116" font span style='font-size:10.0pt;font-family:Arial'>

    <tr>
      <td width="70" height="30"><%= trans.tslt("Resource") %></td>
      <td width="10" height="30">&nbsp;</td>
    </tr>
    <tr> 
    <td width="70" height="84"><%= trans.tslt("English") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource0" cols="50" rows="5" id="textarea"><%=resource[0]%></textarea></font></td>
    </tr>
    <tr>
    <td width="70" height="84"><%= trans.tslt("Indonesian") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource1" cols="50" rows="5" id="textarea"><%=resource[1]%></textarea></font></td>
    </tr>
    <tr>
    <td width="70" height="84"><%= trans.tslt("Thai") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource2" cols="50" rows="5" id="textarea"><%=resource[2]%></textarea></font></td>
	</tr>
    <tr>
    <td width="70" height="84"><%= trans.tslt("Korean") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource3" cols="50" rows="5" id="textarea"><%=resource[3]%></textarea></font></td>
    </tr>
    <tr>
    <td width="70" height="84"><%= trans.tslt("Traditional Chinese") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource4" cols="50" rows="5" id="textarea"><%=resource[4]%></textarea></font></td>
    </tr>
    <tr>
    <td width="70" height="84"><%= trans.tslt("Simplified Chinese") %></td>
    <td width="380" height="84">
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Resource5" cols="50" rows="5" id="textarea"><%=resource[5]%></textarea></font></td>
    </tr>
  </table>
  <blockquote>
    <blockquote>
      <p>
	  	 <input name="ResID" type="hidden" id="ResID" value="<%=ResID%>" size="10">
<%
	if(EditDRARes.CheckSysLibDRARes(ResID) == 1 && userType != 1) {
%>			
        <input type="submit" name="Edit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 1)" disabled>
<% } else { %>		
		<input type="submit" name="Edit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 1)">
<% } %>		
		<input type="submit" name="SaveAs" value="<%= trans.tslt("Save As New") %>" onClick="return confirmEdit(this.form, this.form.CompList.options[CompList.selectedIndex].value, 2)">
        <input name="Cancel" type="submit" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit(this.form)">
      </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>