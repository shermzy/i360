<%@ page import="java.sql.*"%>
<%@ page import="CP_Classes.vo.voCompetency.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Competency</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style>
</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
<jsp:useBean id="Comp" class="CP_Classes.Competency"
	scope="session" />
<jsp:useBean id="KB" class="CP_Classes.KeyBehaviour" scope="session" />
<jsp:useBean id="DRA" class="CP_Classes.DevelopmentActivities"
	scope="session" />
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources"
	scope="session" />
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />

<script language="javascript">
function editText(form, type)
{
	if(form.editName0.value != "" && form.editDefinition0.value != "") {
		if(confirm("<%=trans.tslt("Edit Competency")%>?")) {
			form.action = "EditCompetency.jsp?edited=" + type;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.editName0.value == "") {
			alert("<%=trans.tslt("Please enter Competency Name for English version")%> !");		
			form.editName0.focus();
		}else {
			alert("<%=trans.tslt("Please enter Definition for English version")%> !");		
			form.editDefinition0.focus();
		}
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
	String username = (String) session.getAttribute("username");
	if (!logchk.isUsable(username)) {
%>
<font size="2"> <script>
	parent.location.href = "index.jsp";
</script> <%
 	} else {

 		/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

 		String chkStr = request.getParameter("checked");
 		int PKComp = 0;
 		String []name = new String[6];
 		String []definition = new String[6];

 		int orgID = logchk.getOrg();
 		int compID = logchk.getCompany();
 		int pkUser = logchk.getPKUser();
 		int userType = logchk.getUserType(); // 1= super admin

 		PKComp = Comp.getPKComp();
 		if (PKComp != 0) {

 			/***************************************
 			 *Edit By James 15 - Nov 2007
 			 ******************************************/

 			voCompetency voEdit = Comp.getCompetency(PKComp);
 			if (voEdit != null) {
 				name = voEdit.getAllCompetencyName();
 				definition = voEdit.getAllCompetencyDefinition();
 				for(int i = 0; i < name.length; i++){
 					if(name[i] == null)
 						name[i] = "";
 				}
 				for(int i = 0; i < definition.length; i++){
 					if(definition[i] == null)
 						definition[i] = "";
 				}
 			}
 		}

 		if (chkStr != null) {
 			PKComp = Integer.parseInt(chkStr);

 			Comp.setPKComp(PKComp);

 			//Comp
 			/***************************************
 			 *Edit By James 15 - Nov 2007
 			 ******************************************/
 			voCompetency voEdit = Comp.getCompetency(PKComp);
 			//ResultSet rsEdit = Database.getRecord(query);
 			//rsEdit.next();
 			if (voEdit != null) {
 				name = voEdit.getAllCompetencyName();
 				definition = voEdit.getAllCompetencyDefinition();
 				for(int i = 0; i < name.length; i++){
 					if(name[i] == null)
 						name[i] = "";
 				}
 				for(int i = 0; i < definition.length; i++){
 					if(definition[i] == null)
 						definition[i] = "";
 				}
 			}
 			//rsEdit.close();

 			int check = Comp.CheckSysLibCompetency(PKComp);

 			if (check == 1 && userType != 1) {
 %> <script>
	alert("<%=trans.tslt("The edited System Generated Competency will be saved as a new User Generated Competency")%>")
</script> <%
 	}
 		} else {
 			if (request.getParameter("edited") != null) 
 			{				 
 				PKComp = Integer.parseInt(request
 						.getParameter("PKComp"));
 				name[0] = request.getParameter("editName0");
 				definition[0] = request.getParameter("editDefinition0");
 				name[1] = request.getParameter("editName1");
 				definition[1] = request.getParameter("editDefinition1");
 				name[2] = request.getParameter("editName2");
 				definition[2] = request.getParameter("editDefinition2");
 				name[3] = request.getParameter("editName3");
 				definition[3] = request.getParameter("editDefinition3");
 				name[4] = request.getParameter("editName4");
 				definition[4] = request.getParameter("editDefinition4");
 				name[5] = request.getParameter("editName5");
 				definition[5] = request.getParameter("editDefinition5");

 				name[0] = Database.SQLFixer(name[0]);
 				definition[0] = Database.SQLFixer(definition[0]);
 				name[1] = Database.SQLFixer(name[1]);
 				definition[1] = Database.SQLFixer(definition[1]);
 				name[2] = Database.SQLFixer(name[2]);
 				definition[2] = Database.SQLFixer(definition[2]);
 				name[3] = Database.SQLFixer(name[3]);
 				definition[3] = Database.SQLFixer(definition[3]);
 				name[4] = Database.SQLFixer(name[4]);
 				definition[4] = Database.SQLFixer(definition[4]);
 				name[5] = Database.SQLFixer(name[5]);
 				definition[5] = Database.SQLFixer(definition[5]);

 				if (Integer.parseInt(request.getParameter("edited")) == 1)
 				{
 					int compExist = Comp.CheckCompetencyExist(name[0], definition[0], orgID, compID);

 					if (compExist != 0 && compExist != PKComp) 
 					{
						 %> <script>
  							alert("<%=trans.tslt("Record exists")%>");  							
						 </script> <%
 					} 
 					else 
 					{

 						try {
 							boolean edit = Comp.editRecord(name,
 									definition, 
 									PKComp, pkUser);
 						 // Changed by Ha on 14/05/08: Change message to Edited sucessfully
 							if (edit)
 							       {  								
		 						   %><script>	 
										alert("Edited successfully");
			  							window.close();
										opener.location.href = 'Competency.jsp';
									</script> <%									
                                   	}

 						} catch (SQLException SE) {
 						%> <script>
  							alert("<%=trans.tslt("Record exists")%>");  							
						  </script> <%
 							}
 					}
 				} else if (Integer.parseInt(request
 						.getParameter("edited")) == 2) { // admin or syslib
 					int compExist = Comp.CheckCompetencyExist(
 							name[0], definition[0], orgID, compID);

 					if (compExist == 0) {

 						try {
 							boolean add = Comp.addRecord(name,
 									definition, compID, orgID, pkUser,
 									userType);
 							// copy all KBs, DRAs, DRARes under this competency
 							int pk = 0;
 							if (add) {
 								pk = Comp.CheckCompetencyExist(name[0],
 												definition[0], orgID,
 												compID);

 								Comp.setPKComp(pk);
 							}

 							//kb
 							/********************
 							 * Edited by James 30 Oct 2007
 							 ************************/
 							Vector copyKB = KB.getRecord(PKComp,
 									compID, orgID);
 							for (int i = 0; i < copyKB.size(); i++) {
 								voKeyBehaviour voKB = (voKeyBehaviour) copyKB
 										.elementAt(i);
 								//while(copyKB.next()) {					
 								String kb1 = voKB.getKeyBehaviour();
 								kb1 = Database.SQLFixer(kb1);
 								int kbLvl1 = voKB.getKBLevel();

 								try {
 									boolean copy = KB.addRecord(pk,
 											kb1, kbLvl1, compID, orgID,
 											pkUser, userType);

 								} catch (SQLException SE) {
 								}
 							}
 							/********************
 							 * Edited by James 30 Oct 2007
 							 ************************/
 							//DRA
 							Vector copyDRA = DRA.getRecord(PKComp,
 									compID, orgID);
 							//while(copyDRA.next()) {

							//Gwen Oh - 27/10/2011: Added logic codes so that when sa creates the development activity, isSystemGenerated = 1 is the default
							String isSysGen = "1";
							if (userType != 1) isSysGen = "0";
						
 							for (int i = 0; i < copyDRA.size(); i++) {
 								votblDRA voDRA = (votblDRA) copyDRA
 										.elementAt(i);
 								String kb1 = voDRA.getDRAStatement();
 								kb1 = Database.SQLFixer(kb1);
 								try {
 									//Gwen Oh - 27/10/2011: Added missing isSystemGenerated parameter for addRecord method
 									boolean copy = DRA.addRecord(pk,
 											kb1, isSysGen, compID, orgID, pkUser,
 											userType);
 								} catch (SQLException SE) {
 								}
 							}

 							/********************
 							 * Edited by James 30 Oct 2007
 							 ************************/
 							//DRARes
 							Vector copyDRARes = DRARes.getRecord(
 									PKComp, compID, orgID);
 							//while(copyDRARes.next()) {	
 							for (int i = 0; i < copyDRARes.size(); i++) {
 								votblDRARES voDRAES = (votblDRARES) copyDRARes
 										.elementAt(i);
 								String kb1 = voDRAES.getResource();
 								kb1 = Database.SQLFixer(kb1);
 								int res1 = voDRAES.getResType();

 								try {
 									//Gwen Oh - 27/10/2011: Added missing isSystemGenerated parameter for addRecord method
 									boolean copy = DRARes.addRecord(pk,
 											kb1, res1, isSysGen, compID, orgID,
 											pkUser, userType);
 								} catch (SQLException SE) {
 								}
 							}
							//Changed by Ha 10/06/08 message to Added Successfully
 							if (add) {
 %> <script>  			
							alert("Added successfully");				
  							window.close();
							opener.location.href = 'Competency.jsp';
						</script> <%
 	}

 						} catch (SQLException SE) {
 %> <script>  							
  							window.close();
	opener.location.href = 'Competency.jsp';
						</script> <%
 	}
 					} else {
 %> <script>
  							alert("<%=trans.tslt("Record exists")%>");
  							
						</script> <%
 						}

 				}
 			}//end of edited Type
 		}
 %>

<form name="EditCompetency" method="post"><font span
	style='font-size: 10.0pt; font-family: Arial'> <input
	name="PKComp" type="hidden" id="PKComp" value="<%=PKComp%>" size="10">
</font>
<table width="480" border="0" font span
	style='font-size: 10.0pt; font-family: Arial'>
	<tr>
		<td width="70"><%=trans.tslt("Language") %></td>
		<td width="70"><%=trans.tslt("Competency")%></td>
		<td width="70"><%=trans.tslt("Definition")%></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("English") %></td>
		<td width="400"><textarea name="editName0"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[0]%></textarea></td>
		<td width="400"><textarea name="editDefinition0"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[0]%></textarea></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("Indonesian") %></td>
		<td width="400"><textarea name="editName1"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[1]%></textarea></td>
		<td width="400"><textarea name="editDefinition1"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[1]%></textarea></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("Thai") %></td>
		<td width="400"><textarea name="editName2"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[2]%></textarea></td>
		<td width="400"><textarea name="editDefinition2"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[2]%></textarea></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("Korean") %></td>
		<td width="400"><textarea name="editName3"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[3]%></textarea></td>
		<td width="400"><textarea name="editDefinition3"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[3]%></textarea></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("Traditional Chinese") %></td>
		<td width="400"><textarea name="editName4"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[4]%></textarea></td>
		<td width="400"><textarea name="editDefinition4"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[4]%></textarea></td>
	</tr>
	<tr>
		<td width="70"><%=trans.tslt("Simplified Chinese") %></td>
		<td width="400"><textarea name="editName5"
			style='font-size: 10.0pt; font-family: Arial' cols="30" rows="3"
			id="textarea"><%=name[5]%></textarea></td>
		<td width="400"><textarea name="editDefinition5"
			style='font-size: 10.0pt; font-family: Arial' cols="50" rows="3"
			id="textarea"><%=definition[5]%></textarea></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<blockquote>
<blockquote>
<%
	if (Comp.CheckSysLibCompetency(PKComp) == 1
				&& logchk.getUserType() != 1) {
%>
<p>&nbsp;&nbsp; <font span
	style='font-size: 10.0pt; font-family: Arial'> <input
	name="Submit" type="button" id="Submit"
	value="<%= trans.tslt("Save") %>"
	onClick="return editText(this.form, 1)" disabled> </font> <%
 	} else {
 %>

<p>&nbsp;&nbsp; <font span
	style='font-size: 10.0pt; font-family: Arial'> <input
	name="Submit" type="button" id="Submit"
	value="<%= trans.tslt("Save") %>"
	onClick="return editText(this.form, 1)"> </font> <%
 	}

 %>&nbsp;&nbsp;&nbsp;&nbsp; <font span
	style='font-size: 10.0pt; font-family: Arial'> <input
	name="SaveAs" type="button" id="Submit"
	value="<%= trans.tslt("Save As New") %>"
	onClick="return editText(this.form, 2)" > </font> &nbsp;&nbsp;&nbsp; <font
	span style='font-size: 10.0pt; font-family: Arial'> <input
	name="Cancel" type="button" id="Cancel"
	value="<%= trans.tslt("Cancel") %>" onClick="cancelEdit()"> </font></p>
</blockquote>
</blockquote>
<%
	}
%>
</form>
</body>
</html>