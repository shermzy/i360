<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*, 
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<% 	// added to check whether organisation is a consulting company
	// Mark Oei 05 Mar 2010 %>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.votblOrganization"%>
<%@ page import="CP_Classes.vo.voUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<title>Register New User</title>
<SCRIPT LANGUAGE="JavaScript">
var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 250;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function OpenWindow1(form) 
function OpenWindow1(form) 
{
	//var myWindow=window.open('User.jsp','windowRef','scrollbars=no, width=300, height=570');
	//myWindow.moveTo(x,y);
	
	form.action="User.jsp";
	form.method="post";
	form.submit();
}

//void function OpenWindow2() 
function OpenWindow2() 
{
	//var myWindow=window.open('UserSearch.jsp','windowRef','scrollbars=no, width=300, height=530');
	//myWindow.moveTo(x,y);
	window.document.UserList.action = "UserSearch.jsp";
    window.document.UserList.method="post";	
	window.document.UserList.submit();
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

var flag1 = false;
//void function Edit(form, field)
function Edit(form, field)
{
	if(check(field)) {
		form.action="UserList.jsp?receiver=1";
		form.method="post";
		form.submit();
	}
}

var flag2 = false; 
//void function Delete(form, field)
function Delete(form, field)
{
	if(check(field)) {
		// Message edited Eric Lu 14/5/08
		//Edit message
		if(confirm("<%=trans.tslt("Delete user?")%>"))
		{
		
			form.action="UserList.jsp?delete=1";
			form.method="post";
			form.submit();
		}
	}
}
function proceed(form,field)
{
	form.action="UserList.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function root(form) 
{
	form.action="UserList.jsp?receiver=1";
	form.method="post";
	form.submit();
}

</script>
<body>
<%
if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  //else 
  //{ 

if(request.getParameter("delete") != null)
{
	/********************************
	* Edited by junwei on March 4 2008
	*********************************/
	int FKLoginID = new Integer(request.getParameter("choose")).intValue();
	boolean checkResult = user.isUserAssign(FKLoginID);
	// Added to prevent deletion of admin account by Administrator unless it is PCC
	// Mark Oei 09 Mar 2010
	boolean isAdmin = true;
	if (logchk.getOrgCode().equals("PCC"))
		isAdmin = false;
	else
		isAdmin = user.chkAdmin(FKLoginID);
		
	
	if ((checkResult == true) || (isAdmin)){
		%>
			<script>
				alert("User deletion is not allowed");
				location.href = "UserList.jsp";
			</script>
		<%
	}
	else{
		//user.deleteRelation(FKLoginID);
		user.deleteRecord(FKLoginID, logchk.getPKUser());
		%>
			<script>
				// Added alert message
				// Eric Lu 14/5/08
				alert("Deleted successfully");
				//Changed by Ha 02/06/08
				location.href = "UserList.jsp";
			</script>
		<%
	}
}

if(request.getParameter("receiver") != null)
{
	int FKLoginID = new Integer(request.getParameter("choose")).intValue();
	user.set_selectedUser(FKLoginID);
%>
	<script>
	location.href = "UserEdit.jsp";
	</script>
<%
}

/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
					
	if(request.getParameter("sorting") != null)
	{	 
		int type = new Integer(request.getParameter("sorting")).intValue();
		int toggle = user.getToggle();	//0=asc, 1=desc
		user.setSortType(type);
		
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
			
		user.setToggle(toggle);
	} 
	else
	{
		user.setSortType(1);
	}

%>			
<form action ="User.jsp" name="UserList" method="post">
<table border="0" width="98%" cellspacing="0" cellpadding="0">
	<tr>
		<font size="2">
   
		<td width="19%"><b><font face="Arial" size="2"><%=trans.tslt("Organisation")%>:</font></b></td>
		<td width="68%"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){

	Vector vOrg = logchk.getOrgList(logchk.getCompany());
	
	for(int i=0; i<vOrg.size(); i++)
	{
		votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
		int PKOrg = vo.getPKOrganization();
		String OrgName = vo.getOrganizationName();
	
		if(logchk.getOrg() == PKOrg) { %>
			<option value=<%=PKOrg%> selected><%=OrgName%></option>
		<%	} else { %>
			<option value=<%=PKOrg%>><%=OrgName%></option>
		<%	}	
	}
	} else { %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
	</select></td>
		<td width="13%">&nbsp;</td>
		</font>
	</tr>
	<tr>
    
		<td width="19%">&nbsp;</td>
		<td width="68%">&nbsp;</td>
		<td width="13%">&nbsp;
</td>
	</tr>
</table>
<font color="#000080" face="Arial" style="font-size: 10pt"><b><%=trans.tslt("User Search Result")%> :</b></font></p>

<div style='width:700px; height:261px; z-index:1; overflow:auto'>
<table border="1" cellspacing="1" width="580" id="AutoNumber4" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
  <tr>
  	<td width="31" align="center" bgcolor="#000080" height="35">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b>&nbsp;</b></font></td>
    <td width="101" align="center" bgcolor="#000080" height="35">
    <font face="Arial" style="font-size: 10pt"><a href="UserList.jsp?sorting=1"><font style='color:white'><b><u><%=trans.tslt("Login ID")%></u></b></font></a></td>
    <td width="103" align="center" bgcolor="#000080" height="35">
    <%
    	int NameSeqe =0;

    	NameSeqe= Org.getNameSeq(logchk.getOrg());
    	
    	if(NameSeqe == 0)
    	{
    %>
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=2"><font style='color:white'><u><%=trans.tslt("Family Name")%></u></a></font></b></td>
    <td width="93" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=3"><font style='color:white'><u><%=trans.tslt("Given Name")%></u></a></font></b></td>
    <td width="96" align="center" bgcolor="#000080" height="35">
    <%	}
    	else
    	{	%>
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=3"><font style='color:white'><u><%=trans.tslt("Given Name")%></u></a></font></b></td>
    <td width="93" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=2"><font style='color:white'><u><%=trans.tslt("Family Name")%></u></a></font></b></td>
    <td width="96" align="center" bgcolor="#000080" height="35">
    <%	}%>
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=4"><font style='color:white'><u><%=trans.tslt("Designation")%></u></a></font></b></td>
    <td width="93" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=5"><font style='color:white'><u><%=trans.tslt("Division")%></u></a></font></b></td>
    <td width="127" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=6"><font style='color:white'><u><%=trans.tslt("Department")%></u></a></font></b></td>
    <td width="105" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="UserList.jsp?sorting=7"><font style='color:white'><u><%=trans.tslt("Group")%></u></a></font></b></td>
    <td width="86" align="center" bgcolor="#000080" height="35">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="UserList.jsp?sorting=8"><font style='color:white'><u><%=trans.tslt("User Type")%></u></a></b></font></td>
    <td width="86" align="center" bgcolor="#000080" height="35">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="UserList.jsp?sorting=10"><font style='color:white'><u><%=trans.tslt("Supervisor")%></u></a></b></font></td>
    <td width="119" bgcolor="#000080" height="35">
    <p align="center">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="UserList.jsp?sorting=9"><font style='color:white'><u><%=trans.tslt("Status")%></u> </a></b>
    </font></td>
    <td width="119" bgcolor="#000080" height="35">
    <p align="center">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="UserList.jsp?sorting=11"><font style='color:white'><u><%=trans.tslt("Round")%></u> </a></b>
    </font></td>
  </tr>
<%
	
	String xLoginID = request.getParameter("txtLoginID");
	String xFamilyName = request.getParameter("txtFamilyName");
	String xGivenName = request.getParameter("txtGivenName");
	String xDesignation = request.getParameter("txtDesignation");
	String xIDNumber = request.getParameter("txtIDNumber");
	String xDivision = request.getParameter("selDivision");
	String xDepartment = request.getParameter("selDepartment");
	String xGroup = request.getParameter("selGroup");
	//System.out.println("xGroup = " + xGroup);
	//System.out.println("email = " + request.getParameter("txtEmail"));
	
	String xUserType = request.getParameter("selUserType");
	String xdis = request.getParameter("cliDisabled");
	String xUser2FamilyName = request.getParameter("txtUser2FamilyName");
	String chkFamilyName = "";
	
	int xCompanyID = logchk.getCompany();
	
	//System.out.println("OrganisationID = " + logchk.getOrg());
	
	Vector vUser = user.search_User_WithRelation(xDepartment,xDivision,xUserType,xFamilyName,xGivenName,xLoginID,xDesignation,xIDNumber,xGroup,xdis,xCompanyID,logchk.getOrg(),user.getSortType(), xUser2FamilyName); 
	//System.out.println(xDepartment + " " +xDivision+ " " +xUserType+ " " +xFamilyName+ " " +xGivenName+ " " +xLoginID+ " " +xDesignation+ " " +xIDNumber+ " " +xGroup+ " " +xdis+ " " +xCompanyID+ " " +logchk.getOrg()+ " " +user.getSortType()+ " " + xUser2FamilyName);
	
	for(int i=0; i<vUser.size(); i++)
	{
		voUser vo = (voUser)vUser.elementAt(i);
		String disable= "Enabled";
		int PKUser = vo.getPKUser();
		
		String FamilyName = vo.getFamilyName();
		chkFamilyName = FamilyName;
		String GivenName = vo.getGivenName();
		String LoginID = vo.getLoginName();
		String Designation = vo.getDesignation();
		String IDNumber = vo.getIDNumber();
		String Password = vo.getPassword();
		int dis = vo.getIsEnabled();
		String Group = vo.getGroupName();
		String Division = vo.getDivisionName();
		String Department = vo.getDepartmentName();
		String UserType = vo.getUserTypeName();
		String Supervisor =  vo.getSupervisorName();
		int RelationType = vo.getRelationType();
		int round = vo.getRound();
		
		if(dis == 0)
			disable = "Disable";

%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
  	<td width="31" height="19" align="left">
  		<p align="center">
  		<font face="Arial"><span style="font-size: 11pt">
  			<input type ="radio" name="choose" value=<%=PKUser%> ondblclick="root(this.form)"></span></font></td>
    <td width="101" height="19" align="left">
	<font face="Arial" size="2"><%=LoginID%>&nbsp;</font></td>
    <td width="103" height="19" align="left">
	<font face="Arial" size="2"><%=FamilyName%>&nbsp;</font></td>
    <td width="93" height="19" align="left">
	<font face="Arial" size="2"><%=GivenName%>&nbsp;</font></td>
    <td width="96" height="19" align="left">
	<font face="Arial" size="2"><%=Designation%>&nbsp;</font></td>
    <td width="93" height="19" align="left">
	<font face="Arial" size="2"><%=Division%>&nbsp;</font></td>
    <td width="127" height="19" align="left">
	<font face="Arial" size="2"><%=Department%>&nbsp;</font></td>
    <td width="105" height="19" align="left">
	<font face="Arial" size="2"><%=Group%>&nbsp;</font></td>
    <td width="86" height="19" align="left">
	<font face="Arial" size="2"><%=UserType%>&nbsp;</font></td>
    <td width="119" height="19" align="left">
	<font face="Arial" size="2">
	<%
		if(RelationType == 0)
			//No Relation
			out.println("");
		else
			//Got Relation
			out.println(Supervisor);
	%>&nbsp;</font></td>
 <td width="119" height="19" align="center">
	<font face="Arial" size="2"><%=disable%>&nbsp;</font></td>
    <td width="119" height="19" align="center">
	<font face="Arial" size="2">
	<%
		if(round == -1)
			//round is actually null
			out.println("");
		else
			//round is not null
			out.println(round);
	%>&nbsp;</font></td>

  </tr>
<%
	}
%>
</table>
</div>
<table border="0" width="579" id="AutoNumber5" style="border-collapse: collapse" bordercolor="#111111" cellpadding="0">
  <tr>
    <td width="87%" colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td width="33%">
          <font size="2">
   
		<input type="button" value="<%=trans.tslt("Register New User")%>" name="btnAdd" onclick="OpenWindow1(this.form)"></td>
    <td width="26%">
          <p align="center">
          
          <input type="button" value="<%=trans.tslt("Search")%>" name="btnSearch" onclick="OpenWindow2()" style="float: right"></td>
    <td width="17%">
          <p align="center"> <font size="2">
   
          <input type="button" value="<%=trans.tslt("Edit")%>" name="btnedit" style="float: right" onclick="Edit(this.form, this.form.choose)"></td>
    <td width="24%">

    	<input type="button" value="<%=trans.tslt("Delete")%>" name="btndelete" style="float: right" onclick="Delete(this.form, this.form.choose)"><font size="2">
	</font>
 
    </td>
  </tr>
</table>
</form>
<%	//}	%>

<table border="0" width="610" height="26">

	<tr>
   
		<td align="center" height="5" valign="top"><font size="1" color="navy" face="Arial">&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="Login.jsp">Home</a>&nbsp;| <a color="navy" face="Arial">&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="mailto:3SixtyProfiler@pcc.com.sg?subject=Regarding:">Contact 
		Us</a><a color="navy" face="Arial" href="termofuse.htm"><span style="color: #000080; text-decoration: none"> | Terms of Use </span></a>| <span style="color: #000080; text-decoration: none"><a style="TEXT-DECORATION: none; color:navy;" href="http://www.pcc.com.sg/">PCC Website</a></span></td>
	</tr><tr>
   
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright 
		&copy; 2007 Pacific Century Consulting Pte Ltd. All Rights Reserved.</font></td></tr></table>

</body>
</html>