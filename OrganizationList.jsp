<%@ page import="java.sql.*,
			java.lang.String,
			javazoom.upload.*,
			java.util.*,
			CP_Classes.vo.votblConsultingCompany,
			CP_Classes.vo.votblOrganization,
            java.io.*        "%>  
                 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="org1" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CC1" class="CP_Classes.ConsultingCompany" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
<%// by Denise Date 08/12/2009 Fix the javazoom problem by changing the storage folder to the relative address %>
<%//jsp:setProperty name="upBean" property="folderstore" value="C:\Program Files\Program\Apache Group\Tomcat 4.1\webapps\360CP\Logo" / %>
<jsp:setProperty name="upBean" property="folderstore" value="..\Logo" />
</jsp:useBean>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
var x = parseInt(window.screen.width) / 2 - 300;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 150;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function add() 
{
	var myWindow=window.open('Add_Organization.jsp','windowRef','scrollbars=no, width=500, height=300');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'Add_Organization.jsp';
}

function addCompany() 
{
	var myWindow=window.open('Add_Company.jsp','windowRef','scrollbars=no, width=500, height=250');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'Add_Company.jsp';
}

var orgid;
function check(field)
{
	if(field != null)
	{
		var flag= false;
		
		for (i = 0; i < field.length; i++) 
		{
			if(field[i].checked)
			{
				orgid = field[i].value;
				flag = true;
			}
		}
		if(field.checked)
		{
			orgid = field.value;
			flag = true;
		}
	}

	return flag;
		
}

function proceed(form,field)
{
	if(check(field) && orgid != null)
	{	
	    
		form.action="OrganizationList.jsp?proceed="+orgid;
		form.method="post";
		form.submit();
		
	}
	else
	{
		alert("<%="Please select an organisation"%>");
	}
}

function Cons(form,field)
{
	if(check(field))
	{
		form.action="OrganizationList.jsp?cons="+orgid;
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select a company")%>");
	}
}

function edit(form,field)
{
	if(check(field))
	{
		form.action="OrganizationList.jsp?edit="+orgid;
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select an organisation")%>");
	}
}

function editCompany(form,field)
{
	if(check(field))
	{
		form.action="OrganizationList.jsp?editCompany="+orgid;
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select an company")%>");
	}
}

function del(form,field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Delete the selected Organisation")%>?"))
		{
			form.action="OrganizationList.jsp?del="+orgid;
			form.method="post";
			form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please select an organisation")%>");
	}
}

function delCompany(form,field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Delete the selected Company")%>?"))
		{
			form.action="OrganizationList.jsp?delCompany="+orgid;
			form.method="post";
			form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please select a company")%>");
	}
}

//Gwen Oh - 26/09/2011: Add function to select another organisation
function changeOrg(form, name)
{
	form.action = "OrganizationList.jsp?org=1&radioSelect=" + name;
	form.submit();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmUpload(form,field)
function confirmUpload(form,field)
{
	if(check(field))
	{
		form.action="OrganizationList.jsp?upload="+orgid;
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select an organisation")%>");
	}	
}

// Function added by Eric Lu 20/5/08
// Pops up a new window displaying organisation logo when logo name is clicked
//void function showLogo(logoName) {
function showLogo(logoName) {
		var myWindow=window.open('','windowRef','scrollbars=no, width=200, height=200');
		myWindow.moveTo(x,y);
	    myWindow.document.write("<html><head><title>Organisation Logo</title></head><body>");
	    myWindow.document.write("<img src='Logo/" + logoName + "' width='160' height='100'><br>");
	    myWindow.document.write("<input type='button' value='Close' onclick='window.close()'>");
	    myWindow.document.write("</body></html>");
}
</script>
<body>

<%

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	<jsp:forward page="Login.jsp"/>
<%  } 

/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = org1.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
	
	//Gwen Oh - 26/09/2011: Check if organization has changed and set the necessary fields
	if(request.getParameter("org") != null) {
		int orgSelected = Integer.parseInt(request.getParameter("radioSelect"));
		logchk.setOrg(orgSelected);
		CE_Survey.setJobPos_ID(0);
		CE_Survey.setSurveyStatus(0);
		CE_Survey.setPurpose(0);
		CE_Survey.setSurvey_ID(0);
		CE_Survey.setCompetencyLevel(0);
		assignTR.set_selectedTargetID(0);
		CE_Survey.set_survOrg(orgSelected);
	}
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		org1.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		org1.setSortType(type);									
	} 
	
	toggle = org1.getToggle_org();
	if(request.getParameter("name_org") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		org1.setToggle_org(toggle);
		
		type = Integer.parseInt(request.getParameter("name_org"));
		org1.setSortType_org(type);		
	} 


if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);

	%>
	<jsp:forward page="SurveyList_Create.jsp"/>	
	<% 	
}

if(request.getParameter("cons") != null)
{ 
	int cons = new Integer(request.getParameter("cons")).intValue();
	
	logchk.setCompany(cons);
	System.out.println("co. name: " + logchk.getCompanyName());
	/********************
	* Edited by James 25 Oct 2007
	************************/
	
	//Gwen Oh - 27/09/2011: Reset
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
	
	Vector v1 = org1.getAllOrganizations(logchk.getCompany());
	 
	//Gwen Oh - 27/09/2011: Vector must not be empty
	if (v1.size()>0) {

		boolean isFound = false;
		for(int i=0; i<v1.size(); i++) {
		 	
			votblOrganization voorg = (votblOrganization)v1.elementAt(i);
			int PKOrg = voorg.getPKOrganization();
			System.out.println(voorg.getCompanyName() + "--" + voorg.getOrganizationName());
			//Gwen Oh - 27/09/2011: Select org that has the same name as the co.
			//						Also set the selected company's name in logchk
			if (voorg.getCompanyName().equals(voorg.getOrganizationName())) {
		 		
		 		logchk.setOrg(PKOrg);
		 		logchk.setCompanyName(voorg.getCompanyName());
		 		CE_Survey.set_survOrg(PKOrg);
		 		
		 		isFound = true;
		 		break;	
		 	}
		}
		
		//No org with the same name as the co. is found, set org to the 1st org in the list
		if (!isFound) {
			votblOrganization voorg = (votblOrganization)v1.elementAt(0);
			
			CE_Survey.set_survOrg(voorg.getPKOrganization());
			logchk.setOrg(voorg.getPKOrganization());
			logchk.setCompanyName(voorg.getCompanyName());
		}	
	}
	//Unable to find any organization that is linked to the company
	else {
		CE_Survey.set_survOrg(0);
		logchk.setOrg(0);
		logchk.setCompanyName("");
	}
}

if(request.getParameter("edit") != null)
{ 
	int PKOrg = new Integer(request.getParameter("edit")).intValue();
 	logchk.setOrg(PKOrg);
 	
 	%>
	<script>
		<% // Changed by DeZ, update height to cater to new form with Nominate Rater option %>
		var myWindow=window.open('Edit_Organization.jsp','windowRef','scrollbars=no, width=600, height=350');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'Edit_Organization.jsp';
	</script>
	<% 	
} else if(request.getParameter("upload") != null)
{ 
	int PKOrg = new Integer(request.getParameter("upload")).intValue();
 	logchk.setOrg(PKOrg);
 	
 	%>
	<script>
		var myWindow=window.open('UploadLogo.jsp','windowRef','scrollbars=no, width=450, height=180');
		myWindow.moveTo(x,y);
		myWindow.location.href = 'UploadLogo.jsp';
	</script>
	<% 	
}


if(request.getParameter("editCompany") != null)
{ 
	int CCID = new Integer(request.getParameter("editCompany")).intValue();
 	logchk.setCompany(CCID);
 	
 	%>
	<script>
		var myWindow=window.open('Edit_Company.jsp','windowRef','scrollbars=no, width=500, height=250');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'Edit_Company.jsp';
	</script>
	<% 	
}

if(request.getParameter("del") != null)
{ 
	boolean canDel  = false;
	try
	{
	// Added by Ha 02/06/08
		int PKOrg = new Integer(request.getParameter("del")).intValue();
	 	canDel = org1.deleteRecord(PKOrg, logchk.getPKUser());
	 	if (canDel)
	 	{%>
	 		<script>
			alert("<%=trans.tslt("Deleted successfully")%>");
			</script>
	 	<%}
	 	else
	 	{%>
	 		<script>
			alert("<%=trans.tslt("Deletion can not be done. This organisation may have been assigned with one or more surveys")%>");
			</script>
	 	<%}
	}
	catch(SQLException SQLE)
	{
		%>
		<script>
		alert("<%=trans.tslt("Existing Relation")%>");
		</script>
<%	}
}

if(request.getParameter("delCompany") != null)
{ 
	boolean canDel = false;
	try
	{
	//Added by Ha 02/06/08
		int PKOrg = new Integer(request.getParameter("delCompany")).intValue();
	 	canDel = CC1.deleteRecord(PKOrg, logchk.getPKUser());
	 	if (canDel)
	 	{%>
	 		<script>
			alert("<%=trans.tslt("Deleted successfully")%>");
			</script>
	 	<%}
	 	else
	 	{%>
	 		<script>
			alert("<%=trans.tslt("Deletion can not be done. This company may have been assigned with one or more surveys")%>");
			</script>
	 	<%}
	 	
	}
	catch(SQLException SQLE)
	{
		%>
		<script>
		alert("<%=trans.tslt("Existing Relation")%>");
		</script>
<%	}
}

%>
<form name="OrganizationList" action="OrganizationList.jsp" method="post">
<%
//Gwen Oh - 26/09/2011: Comment setting of survey details. Only set survey details when org is changed.
/*CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
	CE_Survey.set_survOrg(0);
*/	
	%>
<table border="0" width="550" cellspacing="0" cellpadding="0">

	<tr>
		<td><b><font face="Arial" size="2" color="#000080"><%=trans.tslt("Personal Detail")%></font></b></td>
	</tr>
</table>
<table border="0" width="35%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="38%" height="20">&nbsp;</td>
		<td width="62%" height="20">&nbsp;</td>
	</tr>
<%		
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
%>	
	<tr>
		<td width="38%" align="left"><font face="Arial" size="2"><%=trans.tslt("Name")%>:</font></td>
		<td width="62%"><font face="Arial" size="2"><%=UserDetail[0]+", "+UserDetail[1]%></font></td>
	</tr>
	<tr>
		<td width="38%" align="left"><font face="Arial" size="2"><%=trans.tslt("User Type")%>:</font></td>
		<td width="62%"><font face="Arial" size="2"><%=UserDetail[8]%></font></td>
	</tr>
	<tr>
		<td width="38%" align="left"><font face="Arial" size="2"><%=trans.tslt("ID number")%>:</font></td>
		<%	if(UserDetail[4] == null)
				UserDetail[4] = "NA";	%>
		<td width="62%"><font face="Arial" size="2"><%=UserDetail[4]%></font></td>
	</tr>
	<tr>
		<% String temp = "Organization"; // Added allow easy change of description %>
		<td width="38%" align="left"><font face="Arial" size="2"><%=trans.tslt(temp+" Name")%>:</font></td>
		<td width="62%"><font face="Arial" size="2"><%=UserDetail[10]%></font></td>
	</tr>
  </table>

  <table border="0" width="550" cellspacing="0" cellpadding="0">
  <%	
  	if(logchk.getUserType() == 1)
  	{	
  		%>
	<tr>
		<td width="555" colspan="3" height="46"><b>
		<font face="Arial" size="2" color="#000080">
		<%=trans.tslt("List Of Consulting Company")%>:</font></b></td>
	<tr>
		<td width="554" colspan="3">
		<div style='width:550px; height:153px; z-index:1; overflow:auto'>
<table border="1" width="550" bordercolor="#3399FF" bgcolor="#FFFFCC">
	<tr>
		<td width="26" bgcolor="#000080">&nbsp;</td>
		<td width="294" bgcolor="#000080">
		<p align="center">
		<span style="font-weight: 700">
		<a href="OrganizationList.jsp?name=1">
		<font face="Arial" color="#FFFFFF" size="2"><u>
		<%=trans.tslt("Consulting Company")%></u></font></a></span></td>
		<td width="299" bgcolor="#000080" align="left">
		<p align="center"><span style="font-weight: 700">
		<a href="OrganizationList.jsp?name=2">
		<font face="Arial" color="#FFFFFF" size="2"><u><%=trans.tslt("Description")%></u></font></a></acronym></span></td>
	</tr>
	
<%
	String command1 = "SELECT * FROM tblConsultingCompany";
	/********************
	* Edited by James 25 Oct 2007
	************************/
	 Vector v = org1.getRecord_Sorted(command1,1);
	 
	 for(int i=0; i<v.size(); i++) {
	  votblConsultingCompany vo = (votblConsultingCompany)v.elementAt(i);
		int CCID = vo.getCompanyID();
		String CCName = vo.getCompanyName();
		String CCDesc = vo.getCompanyDesc();
		
		if(CCDesc == null)
			CCDesc = " ";
	%>
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="26">
		<p align="center"><font face="Arial"><font size="2">
		<%
		if(logchk.getCompany() == CCID)
		{	%>
		</font><span style="font-size: 11pt">
		<input type="radio" value=<%=CCID%> checked name="chkCCID" onClick="Cons(this.form,this.form.chkCCID)"></span><font size="2">
		<%	}
		else
		{	%>
		</font><span style="font-size: 11pt">
		<input type="radio" value=<%=CCID%> name="chkCCID" onClick="Cons(this.form,this.form.chkCCID)"><font size="2">
		<%	}	%></font></span></font></td>
		<td width="294"><font face="Arial" size="2"><%=CCName%></font></td>
		<td align="left"><font face="Arial" size="2"><%=CCDesc%>&nbsp</font><font size="2">
		</font>
		</td>
		
	</tr>
<%	}	
%>	
</table>
</div>
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
	<tr>
		<td width="156" height="29"> <font size="2">
   
		<input type="button" value="<%=trans.tslt("Add Company")%>" name="btnAddCompany" style="float: left" onClick="addCompany()"></td>
		<td width="185" height="29">
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Edit Company")%>" name="btnEditCompany" style="float: left" onClick="editCompany(this.form, this.form.chkCCID)"></td>
		<td width="185" height="29"> <font size="2">
   
    	<input type="button" value="<%=trans.tslt("Delete Company")%>" name="btnDelCompany" onClick="delCompany(this.form,this.form.chkCCID)" style="float: right"></td>
	</tr>
	</tr>
<%	}
%>	

<% 	// Change to disable display list of organisations if the user is not
	// the super administrator or administrator or consulting company,
	// Mark Oei 09 Mar 2010
	String chkUserType = UserDetail[8]; 			// get the UserType to check
	boolean isConsulting = true;
	isConsulting = org1.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){
	if (chkUserType.equals("Super Administrator") || chkUserType.equals("Administrator")){	// start of if chkUserType
%>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="185"><b>
		<font face="Arial" size="2" color="#000080">
		<%=trans.tslt("List Of Organisations")%>:</font></b><font size="2"><font size="2"></td>
		<td width="185">&nbsp;</td>
		<td width="184"> </td>
	</tr>
	<tr>
		<td width="554" colspan="3">&nbsp;</td>
		
	</tr>
</table>
  <div style='width:550px; height:143px; z-index:1; overflow:auto'>
<table border="1" width="550" bordercolor="#3399FF" bgcolor="#FFFFCC" height="12">
	<tr>
		<td width="26" bgcolor="#000080">&nbsp;</td>
		<td width="313" bgcolor="#000080">
		<p align="center">
		<a href="OrganizationList.jsp?name_org=1">
		<font face="Arial" style="font-weight:700" color="#FFFFFF" size="2">
		<u>
		<%=trans.tslt("Organisations Name")%></u></font></td>
		<td width="110" bgcolor="#000080" align="left">
		<p align="center"><a href="OrganizationList.jsp?name_org=2">
		<font face="Arial" style="font-weight:700" color="#FFFFFF" size="2">
		<u>
		<%=trans.tslt("Organisations Code")%></u></font></td>
		<td width="204" bgcolor="#000080">
		<p align="center">
		<a href="OrganizationList.jsp?name_org=3">
		<font face="Arial" style="font-weight:700" color="#FFFFFF" size="2">
		<u>
		<%=trans.tslt("Name Sequence")%></u></font></td>
		<td width="204" bgcolor="#000080">
		<p align="center">
		<font face="Arial" style="font-weight:700" color="#FFFFFF" size="2">
		<u>
		<%=trans.tslt("Logo Name")%></u></font></td>
	</tr>
<%
	String NameSequence = "";
	int orgSelected = logchk.getOrg();
	
	//String command = "SELECT * FROM tblOrganization WHERE FKCompanyID= "+ logchk.getCompany();
	/********************
	* Edited by James 25 Oct 2007
	*
	* Re-edited by Eric Lu 16 May 2008
	* Added new column "Logo Updated" to List of Organizations table
	************************/
	 Vector v2 = org1.getAllOrganizations(logchk.getCompany());
	 
	 for(int i=0; i<v2.size(); i++) {
	 	votblOrganization vo2 = (votblOrganization)v2.elementAt(i);
		
		int PKOrg = vo2.getPKOrganization();
		String OrgCode = vo2.getOrganizationCode();
		String OrgName = vo2.getOrganizationName();
		int NameSeq = vo2.getNameSequence();
		// Edited Eric Lu 20/5/08
		// Added new column "Logo Name" that shows filename of logo uploaded.
		// When filename is clicked, new window showing the logo appears.
		String OrgLogo = vo2.getOrganizationLogo();
		
		if (OrgLogo == null) OrgLogo = "";
		
		if(NameSeq == 0)
			NameSequence = "FamilyName, OtherName";
		else
			NameSequence = "OtherName, FamilyName";
	%>
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="26">
<%--
// Edited Eric Lu 15/5/08
// 
// Corrected function to run when radio button is double clicked.
--%>
		<font face="Arial"><span style="font-size: 11pt">
		<%
		//Gwen - 26/09/2011: Removed 'onDblClick="edit(this.form,this.form.chkOrgID)"' which leads to the edit organisation function, 
		//to listen for the onClick function to set the organization
		if (orgSelected==PKOrg) { 
		%>
		<input type="radio" value=<%=PKOrg%> name="chkOrgID" onClick="changeOrg(this.form, <%=PKOrg%>)" checked=true></span></font></td>
		<%} else { %>
		<input type="radio" value=<%=PKOrg%> name="chkOrgID" onClick="changeOrg(this.form, <%=PKOrg%>)"></span></font></td>
		<%}%>
		<td width="313"><font face="Arial" size="2"><%=OrgName%></font></td>
		<td align="left"><font face="Arial" size="2"><%=OrgCode%></font></td>
		<td><font face="Arial" size="2"><%=NameSequence%></font></td>	
		<td><font face="Arial" size="2"><a href="#" onClick="showLogo('<%=OrgLogo%>')"><%=OrgLogo%></a></font></td>	
	</tr>
<%	}	
%>	

</table>
</div>
&nbsp;
<table border="0" width="550" cellspacing="0" cellpadding="0">
<tr>
		<td width="221"><input type="button" value="<%=trans.tslt("Upload Logo")%>" name="btnUpload" style="float: left" onClick="confirmUpload(this.form, this.form.chkOrgID)"></td>
		<td width="131">&nbsp; </td>
		<td width="284">&nbsp; </td>
	</tr>

<tr>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
<%
	if(setting.getIsStandalone() == false || (setting.getIsStandalone() && logchk.getUserType() == 1))
	{	%>
<tr>
		<td width="221"> <font size="2">
   
		<input type="button" value="<%=trans.tslt("Add Organisation")%>" name="btnAddOrg" style="float: left" onClick="add()"></td>
		<td width="131"> 
		<!-- p align="center" -->
		<input type="button" value="<%=trans.tslt("Edit Organisation")%>" name="btnEdit" style="float: left" onClick="edit(this.form, this.form.chkOrgID)"></td>
		<td width="284"> <font size="2">
   
    	<input type="button" value="<%=trans.tslt("Delete Organisation")%>" name="btnDelOrg" onClick="del(this.form,this.form.chkOrgID)" style="float: right"></td>
	</tr>
<%	}
	else
	{	%>
<tr>
	<!-- 	Allows Consulting Company Administrator to add organisation information 
			Mark Oei 09 Mar 2010 -->
	<td width="221"> <font size="2">
		<!-- p align="center" -->
		<input type="button" value="<%=trans.tslt("Add Organisation")%>" name="btnAddOrg" style="float: left" onClick="add()"></td>

	<td width="131"> 
		<!-- p align="center" -->
		<input type="button" value="<%=trans.tslt("Edit Organisation")%>" name="btnEdit" style="float: left" onClick="edit(this.form, this.form.chkOrgID)"></td>
</tr>
<%	}
	}	// End of chkUserType if statement
	}	// End of isConsulting check
%>

</tr>	
	
</table>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>