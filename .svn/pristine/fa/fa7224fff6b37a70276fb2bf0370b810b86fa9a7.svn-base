<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%// by Hemilda 23/09/2008 fix import after add UTF-8%>
<%@ page import = "CP_Classes.vo.*" %>
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
<jsp:useBean id="Competency" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language = "javascript">
function confirmAdd(form, field, CompID)
{
	var clickedValue = 0;
	//check whether any checkbox selected
	
	for (i = 0; i < field.length; i++) 
		if(field[i].checked)
			clickedValue = field[i].value;			

	if(form.Statement.value != "" && clickedValue != 0 && CompID != 0) {
		if(confirm("<%=trans.tslt("Add Development Resource")%>?")) {			
			form.action = "AddDRARes.jsp?add=1&pkComp=" + CompID + "&ResID=" + clickedValue;
			form.method = "post";
			form.submit();						
			return true;
		}else
			return false;
	} else {
		alert("<%=trans.tslt("Please select Competency and/or Resource Type and/or enter Development Resource")%>");	
		return false;
	}
	return true;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
function cancelAdd()
{
	window.close();
	//opener.location.href = 'DevelopmentResources.jsp';
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
  	
	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	int ResID, fkComp1, fkComp, res;
	String resName [] = {"Books", "Web Resources", "Training Courses", "AV Resources"}; // Change Resource category from "In-house Resource" to "AV Resource", Desmond 10 May 2011
	String CompName, DRAResName, fkCompName;
	boolean isComp;		// if it is from Competency
	
	
	ResID = 0;
	DRAResName = "";
	CompName = "";
	fkComp1 = DRARes.getFKComp();
	fkComp = 0;
	res = DRARes.getResType();
	isComp = false;
	fkCompName = "";

	
	
	
	Vector CompResult = Competency.FilterRecord(compID, orgID);	
	
	if(request.getParameter("CompID") != null && Integer.parseInt(request.getParameter("CompID")) != 0) {
		fkComp1 = Integer.parseInt(request.getParameter("CompID"));
		res = Integer.parseInt(request.getParameter("Res"));
	} else	if(request.getParameter("add") != null) {
		if(request.getParameter("Statement") != null)	{
  			String Resource = request.getParameter("Statement");

			fkComp1 = Integer.parseInt(request.getParameter("pkComp"));
			res = Integer.parseInt(request.getParameter("ResID"));
			
			Resource = Database.SQLFixer(Resource);	
			
			int exist = DRARes.CheckDRAResExist(fkComp1, Resource, res, compID, orgID);
			if(exist == 0) {
				try {
					
					// Added logic codes so that when sa create development resource it is auto-defaulted to isSystemGenerated = 1, Desmond 19 August 2010
					String isSysGen = "1";
					if (userType != 1) isSysGen = "0";
					
					// Added missing isSystemGenerated parameter for addRecord method, Desmond 19 August 2010 
					boolean add = DRARes.addRecord(fkComp1, Resource, res, isSysGen, compID, orgID, pkUser, userType);
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
						//alert("Data added successfully!");
				  		window.close();
						opener.location.href = 'DevelopmentResources.jsp';
					</script>
<%
				}
	} else {
%>
	<script>
  		alert("<%=trans.tslt("Record exists")%>");
		//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		//window.location.href('AddDRARes.jsp');
		window.location.href='AddDRARes.jsp';
	</script>
<%	
	}
	}
	}
%>	
<form name="AddDRARes" method="post">
<table width="460" font span style='font-size:10.0pt;font-family:Arial'>
<tr>
<td height="12" width="70"><%= trans.tslt("Competency") %></td>
      <td height="12" width="10"></td>
      <td height="12" width="380">
        <select name="CompList"><font span style='font-size:11.0pt;font-family:Arial'>
        <% int t = 0;
		%>
		<option value=<%=t%>><%=trans.tslt("Please select one")%>
		<%
		
			//while(CompResult.next()) {
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
		<% 		} else {
		%>
		  <option value=<%=fkComp%>><%=CompName%>
		<%
			}
			}
		%>
		</font>
        </select></td>
</tr>
</table>
  
 <p></p>
<!--Changed 15/05/08 by Ha, add the Resource Type Headline -->
<table width="600" border="0" font span style='font-size:10.0pt;font-family:Arial'>
     <td><font face = "Arial" size = "2"><%= trans.tslt("Resource Type") %></font></td>
      <% for(int i=1; i<=resName.length; i++) {
	  %>
	  <tr height="15">
	  	<td width="175">

	  	<% if( res != 0 && res == i) {
	  	%>

	  	<input name="rbtnRes" type="radio" checked value=<%=i%>>
	  	<% res = 0;
	  	}else {
	  	%>

	  	<input name="rbtnRes" type="radio" value=<%=i%>>
	  	<% }
	  	%>
	  	<%=resName[i-1]%>
		</font>
		</td>
		</tr>
		<% } %>
	  
  </table>
  
<p></p> 
 
 <table border="0" width="460" height="102" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="70" height="84"><%= trans.tslt("Statement") %></td>
      <td width="10" height="84">&nbsp;</td>
      <td width="380" height="84">
    
    <textarea span style='font-family:Arial;font-size:10.0pt' name="Statement" cols="50" rows="5" id="textarea"></textarea>
    </font></td>
    </tr>
  </table>
  <blockquote>
    <blockquote>
      <p>
		&nbsp;&nbsp;		
        <input type="button" name="Submit" value="<%= trans.tslt("Submit") %>" onClick="return confirmAdd(this.form, this.form.rbtnRes, this.form.CompList.options[CompList.selectedIndex].value)">&nbsp;&nbsp;&nbsp;&nbsp;
        <input name="Cancel" type="button" id="Cancel" value="<%= trans.tslt("Cancel") %>" onclick="cancelAdd()">
      </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>
