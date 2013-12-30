<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Key Behaviour</title>

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
<jsp:useBean id="EditKB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="Competency" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 

<script language = "javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function editText(form, CompList, KBLevel, type)
function editText(form, CompList, KBLevel, type)
{
	if(form.Statement0.value != "") {
		if(confirm("<%=trans.tslt("Edit Key Behaviour")%>?")) {
			form.action = "EditKB.jsp?edited=" + type + "&CompList=" + CompList + "&KBLevel=" + KBLevel;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		alert("<%=trans.tslt("Please enter Statement for English version")%>");		
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
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
  	
	int pkKB = 0, fkComp = 0, KBLevel = 0, Level = 0, fkComp1 = 0;
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	fkComp1 = Comp.getPKComp();		
	KBLevel = KB.getKBLevel();
	pkKB = EditKB.getKBID();
	
	boolean clicked = false;
	// fkComp is used to fill up the select options menu
	// fkComp1 is used as reference for edition
	String statement[] = new String[6];
	String CompName = "";
	
	Vector CompResult = Competency.FilterRecord(compID, OrgID);
	
 			
	if (request.getParameter("clicked") != null) 
	{
		clicked = true;
		pkKB = Integer.parseInt(request.getParameter("clicked"));
		
		
		EditKB.setKBID(pkKB);
		/****************************
		*Edit By James 15 - Nov 2007
		****************************/
		
		voKeyBehaviour vo_KeyBehaviour=EditKB.getRecord(pkKB);
		if(vo_KeyBehaviour!=null){
						
		fkComp1 = vo_KeyBehaviour.getFKCompetency();
		Comp.setPKComp(fkComp1);
		statement = vo_KeyBehaviour.getAllKeyBehaviour();
		for(int i = 0; i < statement.length; i++){
			if(statement[i] == null)
				statement[i] = "";
		}
		Level = vo_KeyBehaviour.getKBLevel();
		KB.setKBLevel(Level);

		}
		
		int check = EditKB.CheckSysLibKB(pkKB);
			
			if(check == 1 && userType != 1) {				
%>
<script>
	alert("<%=trans.tslt("The edited System Generated Key Behaviour will be saved as a new User Generated Key Behaviour")%>")
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
			pkKB = Integer.parseInt(request.getParameter("pkKB"));
    		fkComp1 = Integer.parseInt(request.getParameter("CompList"));
			Level = Integer.parseInt(request.getParameter("KBLevel"));
		
			statement[0] = Database.SQLFixer(statement[0]);	
			statement[1] = Database.SQLFixer(statement[1]);	
			statement[2] = Database.SQLFixer(statement[2]);	
			statement[3] = Database.SQLFixer(statement[3]);	
			statement[4] = Database.SQLFixer(statement[4]);	
			statement[5] = Database.SQLFixer(statement[5]);	

			if(Integer.parseInt(request.getParameter("edited")) == 1) {	
				int exist = EditKB.CheckKBExist(statement[0], fkComp1, Level, compID, OrgID);
			
				if(exist != 0 && exist != pkKB) {
					clicked = true;

%>
<script>
	alert('<%=trans.tslt("Record exists")%>');
	
</script>	

<%								
			
				} else {
				
   					try {
						boolean edit = EditKB.editRecord(fkComp1, pkKB, statement, Level, pkUser);
							
						if(edit) {
%>
	<script>
		alert("Edited successfully");
  		window.close();
		opener.location.href = 'KeyBehaviour.jsp';
	</script>
<%
						}
					}catch(SQLException SE) {
						clicked = true;
%>
<script>
	alert('<%=trans.tslt("Record exists")%>');

</script>

<%					
					}
					
				}
			}
			else if(Integer.parseInt(request.getParameter("edited")) == 2) {	// admin or syslib
				int exist = EditKB.CheckKBExist(statement[0], fkComp1, Level, compID, OrgID);
			
				if(exist == 0) {
				
				try {
					boolean add = EditKB.addRecord(fkComp1, statement, Level, compID, OrgID, pkUser, userType);
					
					if(add) {
%>
<script>
	alert("Added successfully");
	window.close();
	opener.location.href = 'KeyBehaviour.jsp';
</script>	


<%					
					}
				}catch(SQLException SE) {
%>
<script>
	window.close();
	opener.location.href = "KeyBehaviour.jsp";
</script>	

<%						
				
				}
				
				} else {
					clicked = true;
%>
<script>
	alert('<%=trans.tslt("Record exists")%>');
</script>	

<%		
			}

			}
		}
	}
%>	

<form name="EditKB" method="post">
  <table border="0" width="400" height="165" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="77" height="20"><%= trans.tslt("Level") %></td>
      <td width="478" height="20">    
    <select name="KBLevel">
    	<%
			for(KBLevel = 1; KBLevel <= 10; KBLevel++) {
				if(clicked == true && KBLevel == Level) {
		%>
			<option value=<%=KBLevel%> selected><%=KBLevel%>
		<%	} else {
		%>
			<option value=<%=KBLevel%>><%=KBLevel%>
		<%
			}
			}
		%>
	</select>
	</td>
    </tr>
    <tr>
      <td height="12"></td>
      <td height="12"></td>
      <td height="12"></td>
    </tr>
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
				CompName =  voC.getCompetencyName();
				if(fkComp == fkComp1) {
		%>
			<option value=<%=fkComp%> selected><%=CompName%>
		<%	} else {
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
    </tr>
    <tr> 
      <td width="77" height="75"><%= trans.tslt("English") %></td>
      <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement0" cols="50" rows="4" id="textarea"><%=statement[0]%></textarea></td>
     </tr><tr>
     <td width="77" height="75"><%= trans.tslt("Indonesian") %></td>
           <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement1" cols="50" rows="4" id="textarea"><%=statement[1]%></textarea></td>
     </tr><tr>
     <td width="77" height="75"><%= trans.tslt("Thai") %></td>
          <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement2" cols="50" rows="4" id="textarea"><%=statement[2]%></textarea></td>
    </tr><tr>
    <td width="77" height="75"><%= trans.tslt("Korean") %></td>
     <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement3" cols="50" rows="4" id="textarea"><%=statement[3]%></textarea></td>
    </tr><tr>
    <td width="77" height="75"><%= trans.tslt("Traditional Chinese") %></td>
         <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement4" cols="50" rows="4" id="textarea"><%=statement[4]%></textarea></td>
    </tr><tr>
    <td width="77" height="75"><%= trans.tslt("Simplified Chinese") %></td>
         <td width="478" height="75">
    <textarea span style='font-size:10.0pt;font-family:Arial' name="Statement5" cols="50" rows="4" id="textarea"><%=statement[5]%></textarea></td>
    </tr>
  </table>
  <blockquote>
    <blockquote>
	<p>
<input name="pkKB" type="hidden" id="pkKB" value="<%=pkKB%>" size="10">
<%
	if(EditKB.CheckSysLibKB(pkKB) == 1 && userType != 1) {
%>		
<font span style='font-size:10.0pt;font-family:Arial'>
<input name="Submit" type="button" id="Submit" value="<%= trans.tslt("Save") %>" onClick="return editText(this.form, this.form.CompList.options[CompList.selectedIndex].value, this.form.KBLevel.options[KBLevel.selectedIndex].value, 1)" disabled>
</font>		
<%	
	} else {
%>
<font span style='font-size:10.0pt;font-family:Arial'>
<input name="Submit" type="button" id="Submit" value="<%= trans.tslt("Save") %>" onClick="return editText(this.form, this.form.CompList.options[CompList.selectedIndex].value, this.form.KBLevel.options[KBLevel.selectedIndex].value, 1)">
</font>		
<% } %>&nbsp;&nbsp;&nbsp;
<font span style='font-size:10.0pt;font-family:Arial'>
<input name="SaveAs" type="button" id="Submit" value="<%= trans.tslt("Save As New") %>" onClick="return editText(this.form, this.form.CompList.options[CompList.selectedIndex].value, this.form.KBLevel.options[KBLevel.selectedIndex].value, 2)">
</font>		
		&nbsp;&nbsp;
        <input name="Cancel" type="button" id="Cancel" value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit(this.form)">
      </p>
    </blockquote>
  </blockquote>
</form>
<% } %>
</body>
</html>
