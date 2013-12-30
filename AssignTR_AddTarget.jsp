<%@ page
	import="java.sql.*,java.io.*,java.util.*,java.text.*,java.lang.String"%>

<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session" />
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey"
	scope="session" />
<jsp:useBean id="GFunc" class="CP_Classes.GlobalFunc" scope="session" />
<jsp:useBean id="user" class="CP_Classes.User" scope="session" />
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />
<jsp:useBean id="Department" class="CP_Classes.Department"
	scope="session" />
<jsp:useBean id="Division" class="CP_Classes.Division" scope="session" />
<jsp:useBean id="Group" class="CP_Classes.Group" scope="session" />
<jsp:useBean id="Orgs" class="CP_Classes.Organization" scope="session" />
<%@ page import="CP_Classes.vo.voGroup"%>
<%@ page import="CP_Classes.vo.voDepartment"%>
<%@ page import="CP_Classes.vo.voDivision"%>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<%@ page import="CP_Classes.vo.voUser"%>
<html>
<head>
<!-- CSS -->

<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap-responsive.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap-responsive.min.css">


<!-- jQuery -->
<script type="text/javascript" src="lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/js/bootstrap.js"></script>
<script type="text/javascript" src="lib/js/jquery-1.9.1.js"></script>


<script src="lib/js/bootstrap.min.js" type="text/javascript"></script>
<script src="lib/js/bootstrap-dropdown.js"></script>

<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function closeME(form)
{ 
	form.action = "AssignTR_AddTarget.jsp?close=1";
	form.method="post";
	form.submit();
}
function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	if( field == null ) {
		isValid = 2;
	
	} else {

		if(isNaN(field.length) == false) {
			for (i = 0; i < field.length; i++)
				if(field[i].checked) {
					clickedValue = field[i].value;
					isValid = 1;
				}
		}else {		
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
				
		}
	}
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("No record selected");
	else if(isValid == 2)
		alert("No record available");
	
	isValid = 0;
		
}
function refresh(form, field1, field2, field3)
{
	form.action="AssignTR_AddTarget.jsp?refresh="+field3.value + "&div=" + field1.value + "&dept=" + field2.value;
	form.method="post";
	form.submit();	
}

function add(form, field)
{
//\\ Changed by Ha on 20/05/08 to pop out the Add Target?
	if(check(field))
	{
		if (confirm("Add Target?"))
		{
		form.action="AssignTR_AddTarget.jsp?add=1";
		form.method="post";
		form.submit();	
		return true;
		}
		else
		return false;
	}
}


function checkedAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}

function populateDept(form, field)
{
	form.action="AssignTR_AddTarget.jsp?div="+ field.value;
	form.method="post";
	form.submit();	
}

function populateGrp(form, field1, field2)
{
	form.action="AssignTR_AddTarget.jsp?div="+ field1.value + "&dept=" + field2.value;
	form.method="post";
	form.submit();	
}

function populate(form, field1, field2, field3)
{
	form.action="AssignTR_AddTarget.jsp?div=" + field1.value + "&dept=" + field2.value + "&GroupName=" + field3.value;
	form.method="post";
	form.submit();	
}

function refresh(form)
{
	var radios = document.getElementsByName("roundRadio");
	var v = null;
	if(radios[0].checked){
		v = radios[0].value;
	}else{
		v = radios[1].value;
	}
	form.action="AssignTR_AddTarget.jsp?div=" + form.selDivision.value + "&dept=" + 
			form.selDepartment.value + "&GroupName=" + form.GroupName.value + "&roundRadio="+v;
	form.method="post";
	form.submit();	
}


</SCRIPT>
<body bgcolor="#E2E6F1">
<%
	String username = (String) session.getAttribute("username");

	if (!logchk.isUsable(username)) {
%>
<font size="2"> <script>
	parent.location.href = "index.jsp";
</script> <%
 	}

 	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

 	int toggle = assignTR.getToggle(); //0=asc, 1=desc

 	int type = assignTR.getSortType(); //1=name, 2=origin		

 	if (request.getParameter("name") != null) {

 		if (toggle == 0)
 			toggle = 1;
 		else
 			toggle = 0;

 		assignTR.setToggle(toggle);

 		type = Integer.parseInt(request.getParameter("name"));
 		assignTR.setSortType(type);
 	}

 	/*********************************************************END ADDING TOGGLE FOR SORTING PURPOSE *************************************/

 	if (request.getParameter("add") != null) 
 	{
 		String[] User = request.getParameterValues("chkUser");
 		boolean canAdd;
 		int count = 0;
 		// Changed by Ha on 22/05/08 to pop out the message after adding
 		for (int i = 0; i < User.length; i++)
 		{
 		 	String round_str = request.getParameter("selRound");
 		 	if(round_str != null && round_str.length() != 0) {
 		 		int selRound = Integer.parseInt(round_str);
 		 		assignTR.setRound(selRound);
 		 	} else if(request.getParameter("roundRadio") != null && !request.getParameter("roundRadio").equals("existing")){
 		 		int selRound = Integer.parseInt(request.getParameter("roundRadio"));
 		 		assignTR.setRound(selRound);
 		 	}else{
 		 		assignTR.setRound(1);
 		 	}
 			canAdd = assignTR.addTarget(CE_Survey.getSurvey_ID(),Integer.parseInt(User[i]), logchk.getPKUser(), assignTR.getRound());
 			if (canAdd) 
 			{
 				 count++;
 %> <%
 			} 
 			else if (canAdd == false) 
 			{
 %> 		     <script>
				 alert("<%=trans.tslt("Added unsucessfully")%>");
			     window.close();				  		
				 opener.location.href = 'AssignTR_AddTarget_Rater.jsp';
				</script> <%
 			}
 		}
 		if (count == User.length)
 		{
 %>			 <script>
			 alert("<%=trans.tslt("Added successfully")%>");
		     window.close();				  		
			 opener.location.href = 'AssignTarget_Rater.jsp';
			</script> <%
 		}

 		assignTR.set_selectedTargetID(0);
 		assignTR.set_selectedAssID(0);
 %> <%
 	}

 	//if(request.getParameter("refresh") != null)
 	//{
 	String div_str = request.getParameter("selDivision");
 	String dept_str = request.getParameter("selDepartment");
 	String group_str = request.getParameter("GroupName");
 	boolean existingRound = true;
 	if(request.getParameter("roundRadio")!= null && !request.getParameter("roundRadio").equals("existing")){
 		existingRound = false;
 	}

 	if (group_str != null && group_str.length() != 0) {
 		int group = Integer.parseInt(group_str);
 		assignTR.setGroupID(group);
 	} else {
 		assignTR.setGroupID(0);
 	}
 	if (div_str != null && div_str.length() != 0) {
 		int div = Integer.parseInt(div_str);
 		assignTR.setDivID(div);
 	} else {
 		assignTR.setDivID(0);
 	}
 	if (dept_str != null && dept_str.length() != 0) {
 		int dept = Integer.parseInt(dept_str);
 		assignTR.setDeptID(dept);
 	} else {
 		assignTR.setDeptID(0);
 	}

 	//}

 	if (request.getParameter("close") != null) {
 		assignTR.set_selectedTargetID(0);
 		assignTR.set_selectedAssID(0);
 %> <script>
		window.close();
		opener.location.href ='AssignTarget_Rater.jsp';
	</script> <%
 	}
 %>
<form name="AssignTR_AddTarget" action="AssignTR_AddTarget.jsp"
	method="post">
<table border="0" width="430" cellspacing="0"
	style="border-left-width: 2px; border-right-width: 2px; border-top-width: 2px; border-bottom-width: 0px"
	bordercolor="#3399FF">
	<tr>
		<td width="128" colspan="2"><font size="2"> <b><font
			face="Arial" size="2"> <%=trans.tslt("Selected Survey")%>:</font></b></td>
		<td colspan="2"><font face="Arial" style="font-size: 12">
		<%
			String SurveyName = " ";
			int Org = 0;
			votblSurvey voSurvey = CE_Survey.getSurveyDetail(CE_Survey
					.getSurvey_ID());
			if (voSurvey != null) {
				SurveyName = voSurvey.getSurveyName();
				Org = voSurvey.getFKOrganization();
				CE_Survey.set_survOrg(Org);
			}
		%> <%=SurveyName%> </font></td>
	</tr>
	<tr>
		<td width="10">&nbsp;</td>
		<td width="118">&nbsp;</td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4"><b><font face="Arial" size="2"><%=trans.tslt("Search Name Through")%>:</font></b></td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium; border-top-style: solid; border-top-width: 1px">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium"><font
			face="Arial" size="2"><%=trans.tslt("Division")%>:</font></td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px"><select
			size="1" name="selDivision"
			onChange="populateDept(this.form, this.form.selDivision)">

			<option value="0" selected>All</option>


			<%
				Vector vDiv = Division.getAllDivisions(logchk.getOrg());

				for (int i = 0; i < vDiv.size(); i++) {
					voDivision voDiv = (voDivision) vDiv.elementAt(i);

					int div_ID = voDiv.getPKDivision();
					String div_Name = voDiv.getDivisionName();

					if (assignTR.getDivID() != 0 && assignTR.getDivID() == div_ID) {
			%>
			<option value=<%=div_ID%> selected><%=div_Name%></option>
			<%
				} else {
			%>
			<option value=<%=div_ID%>><%=div_Name%></option>
			<%
				}
				}
			%>

		</select></td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px">&nbsp;</td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium"><font
			face="Arial" size="2"><%=trans.tslt("Department")%>:</font></td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px"><select
			size="1" name="selDepartment"
			onChange="populateGrp(this.form, this.form.selDivision, this.form.selDepartment)">

			<option value="0" selected>All</option>
			<%
				int div = 0;
				if (request.getParameter("div") != null) {
					String divID = request.getParameter("div");
					if (divID.length() > 0) {
						div = Integer.parseInt(divID);
					}
				}
				Vector vDepartments = Department.getAllDepartments(logchk.getOrg(),
						div);

				for (int i = 0; i < vDepartments.size(); i++) {

					voDepartment voD = (voDepartment) vDepartments.elementAt(i);
					int dep_ID = voD.getPKDepartment();
					String dep_Name = voD.getDepartmentName();
					if (assignTR.getDeptID() != 0 && assignTR.getDeptID() == dep_ID) {
			%>
			<option value=<%=dep_ID%> selected><%=dep_Name%></option>
			<%
				} else {
			%>
			<option value=<%=dep_ID%>><%=dep_Name%></option>

			<%
				}
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px">&nbsp;</td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium"><font
			face="Arial" size="2"><%=trans.tslt("Group")%>:</font></td>
		<td width="209"><font face="Arial"><span
			style="font-size: 11pt"><select size="1" name="GroupName"
			onChange="populate(this.form, this.form.selDivision, this.form.selDepartment, this.form.GroupName)">

			<option value="0" selected>All</option>
			<%
				int dept = 0;
				if (request.getParameter("dept") != null) {
					String deptID = request.getParameter("dept");
					if (deptID.length() > 0) {
						dept = Integer.parseInt(deptID);
					}
				}

				Vector vGroup = Group.getAllGroups(logchk.getOrg(), dept);
				for (int i = 0; i < vGroup.size(); i++) {

					voGroup voG = (voGroup) vGroup.elementAt(i);
					int Group_ID = voG.getPKGroup();
					String Group_Desc = voG.getGroupName();

					if (assignTR.getGroupID() == Group_ID) {
			%>
			<option value=<%=Group_ID%> selected><%=Group_Desc%></option>
			<%
				} else {
			%>
			<option value=<%=Group_ID%>><%=Group_Desc%></option>
			<%
				}

				}
			%>
		</select></span></font></td>
		<td width="85"
			style="border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		</td>
	</tr>
<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px">&nbsp;</td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px">
		<font size="2"> <input type="button"
			value="<%= trans.tslt("Search") %>" name="btnSearch"
			style="float: left"
			onclick="refresh(this.form, this.form.selDivision, this.form.selDepartment, this.form.GroupName)">
		<p></td>
	</tr>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium">&nbsp;</td>
		<td colspan="2"
			style="border-right-style: solid; border-right-width: 1px">&nbsp;</td>
	</tr>
	<tr>
	<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium"><font
			face="Arial" size="2"><%=trans.tslt("Add to Round1")%>:</font></td>
		<td width="239">	
		<input type = "radio" name = "roundRadio" value = "existing" <% if(existingRound) { %> checked<%} %>
		onChange="refresh(this.form)">Existing Round
		<input type = "radio" name = "roundRadio" value = "<%=assignTR.getNewRound(voSurvey.getSurveyID())%>" <% if(!existingRound) { %> checked<%} %>
		onChange="refresh(this.form)"> New Round(Round <%=assignTR.getNewRound(voSurvey.getSurveyID())%>)</td>
		<td width="60"
			style="border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		</td>
	</tr>
	<% if(existingRound){ %>
	<tr>
		<td width="9"
			style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium">&nbsp;</td>
		<td width="118"
			style="border-left-style: none; border-left-width: medium"></td>
		<td width="209"><font face="Arial"><span
			style="font-size: 11pt"><select size="1" name="selRound">
			<%

				Vector<Integer> rounds  = assignTR.getAllRound(voSurvey.getSurveyID());
				for (int i = 0; i < rounds.size(); i++) {
					int round = rounds.get(i);
					if (assignTR.getRound() == round) {
			%>
			<option value=<%=round%> selected><%=round%></option>
			<%
				} else {
			%>
			<option value=<%=round%>><%=round%></option>
			<%
				}

				}
			%>
		</select></span></font></td>
		<td width="85"
			style="border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		</td>
	</tr><% } %>
</table>
<div style='width: 433px; height: 277px; z-index: 1; overflow: auto'>
<table border="1" width="100%" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td width="421" colspan="4" bgcolor="#000080">
		<p align="center"><b> <font face="Arial" color="#FFFFFF"
			size="2"><%=trans.tslt("Targets")%></font></b>
		</td>
	</tr>
	<tr>
		<td width="28" align="center" bgcolor="#000080"><input
			type="checkbox" name="checkAll"
			onclick="checkedAll(this.form, this.form.chkUser,this.form.checkAll)">
		</td>

		<%
			int NameSeqe = Orgs.getNameSeq(logchk.getOrg());
			String First = "Family Name"; //default
			String Second = "Other Name"; //default
			int FirstOrder = 1; //Name Order in the first column
			int SecondOrder = 2; //Name Order in the second column

			if (NameSeqe != 0) {
				First = "Other Name";
				Second = "Family Name";
				FirstOrder = 2;
				SecondOrder = 1;
			} //end if(NameSeqe != 0)
		%>
		<td width="198" align="center" bgcolor="#000080"><b> <a
			href="AssignTR_AddTarget.jsp?name=<%=FirstOrder%>"> <font
			style='font-family: Arial; color: white' size="2"> <u><%=trans.tslt(First)%></u></font></a></b></td>
		<td width="200" align="center" bgcolor="#000080"><b> <a
			href="AssignTR_AddTarget.jsp?name=<%=SecondOrder%>"><font
			style='font-family: Arial; color: white' size="2"> <u><%=trans.tslt(Second)%></u></font></a></b></td>

		<td width="144" align="center" bgcolor="#000080"><b> <a
			href="AssignTR_AddTarget.jsp?name=3"><font
			style='font-family: Arial; color: white' size="2"> <u><%=trans.tslt("Login Name")%></u></font></a></b></td>
	</tr>
	<%
		// Changed by Ha 21/05/08 to sort the list
		//if(request.getParameter("refresh") != null)
		//{
		Vector vUser = assignTR.getUserList(CE_Survey.getSurvey_ID(),
				logchk.getPKUser(), CE_Survey.get_survOrg(), assignTR
						.getDivID(), assignTR.getDeptID(), assignTR
						.getGroupID(), logchk.getUserType());
		for (int k = 0; k < vUser.size(); k++) {
			voUser vo = (voUser) vUser.elementAt(k);

			int PKUser = vo.getPKUser();
			String FirstName = vo.getFamilyName(); //default order based on NameSeq == 0 (Family name first)
			String SecondName = vo.getGivenName(); //default order based on NameSeq == 0 (Family name first)
			String LoginName = vo.getLoginName();

			if (NameSeqe != 0) {
				//Swap name order
				String sTempName = FirstName;
				FirstName = SecondName;
				SecondName = sTempName;
			} //end if(NameSeqe != 0)
	%>
	<tr onMouseOver="this.bgColor = '#99ccff'"
		onMouseOut="this.bgColor = '#FFFFcc'">
		<td width="28" align="center"><font face="Arial"><span
			style="font-size: 11pt"> <input type="checkbox" name="chkUser"
			value=<%=PKUser%>></span></font></td>
		<td width="198" align="left"><font face="Arial" size="2"><%=FirstName%></font></td>
		<td width="200" align="left"><font face="Arial" size="2"><%=SecondName%></font></td>
		<td width="144" align="left"><font face="Arial" size="2"><%=LoginName%></font></td>
	</tr>
	<%
		}
		//}
	%>
</table>
</div>
<table border="0" width="46%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="266" align="right">&nbsp;</td>
		<td align="right">&nbsp;</td>
	</tr>
	<tr>
		<td width="266" align="left"><input type="button"
			value="<%= trans.tslt("Cancel") %>" name="btnCancel"
			onClick="closeME(this.form)"></td>
		<td align="left"><input type="button"
			value="<%= trans.tslt("Add") %>" name="btnAdd"
			onClick="return add(this.form,this.form.chkUser)"></td>
	</tr>
</table>
</form>
</body>
</html>