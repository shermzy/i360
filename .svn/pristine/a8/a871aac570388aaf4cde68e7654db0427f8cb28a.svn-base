<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Cdepartment" class="CP_Classes.Department" scope="session"/>
<jsp:useBean id="group" class="CP_Classes.Group" scope="session"/>
<jsp:useBean id="division" class="CP_Classes.Division" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<%@ page import="CP_Classes.vo.voGroup"%>
<%@ page import="CP_Classes.vo.voDepartment"%>
<%@ page import="CP_Classes.vo.voDivision"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>
<%@ page import="CP_Classes.vo.voUserType"%>
<%@ page import="CP_Classes.vo.voUser"%>

<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<title>Assign Relation</title>
<SCRIPT LANGUAGE="JavaScript">
var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 125;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function check()
{
	//edited by junwei feb 28 2008
	field = document.getElementsByName("choose");
	var check= "";
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
			check = field[i].value;
	}
	
	return check;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function CancelExecute()
function CancelExecute()
{
	window.close();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function AssignRelationExecute(form)
function AssignRelationExecute(form)
{
	//opener.location.href('UserEdit.jsp?UserID2=' + check() );
	//opener.location.href('User.jsp?UserID2=' + check() );
	var value = check();
	if(value == "")
	{
		alert("Please choose a supervisor");
	}
	else
	{
		form.action="AssignRelationUserList.jsp?UserID2=" + value;
		form.method="post";
		form.submit();
	}
	//opener.User.hdnSupID.value = "hidden Sup ID";
	//opener.User.txtSup.value = "Supervisor";
	//window.close();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function AssignNoRelationExecute(form)
function AssignNoRelationExecute(form)
{
	//opener.location.href('UserEdit.jsp?UserID2=' + check() );
	//opener.location.href('User.jsp?UserID2=' + check() );
	
	form.action="AssignRelationUserList.jsp?UserID2=-1";
	form.method="post";
	form.submit();
	
	//opener.User.hdnSupID.value = "hidden Sup ID";
	//opener.User.txtSup.value = "Supervisor";
	//window.close();
}

function proceed(form,field)
{
	form.action="AssignRelationUserList.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function search(form, field1, field2)
{
	form.action="AssignRelationUserList.jsp?search=1&div=" + field1.value + "&dept=" + field2.value;
	form.method="post";
	form.submit();
}

function populateDept(form, field)
{
	form.action="AssignRelationUserList.jsp?div="+field.value;
	form.method="post";
	form.submit();
}

function populateGrp(form, field1, field2)
{
	form.action="AssignRelationUserList.jsp?div=" + field1.value + "&dept="+ field2.value;
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
	
	if(request.getParameter("PageOpener") != null) {
		session.setAttribute("PageOpener", (String)request.getParameter("PageOpener"));
	}	
	
	String sPageOpener = "";
	if(session.getAttribute("PageOpener") != null) {
		sPageOpener = (String)session.getAttribute("PageOpener");	
	}

	//System.out.println("------->"+ sPageOpener + "------" + request.getParameter("PageOpener"));
%>			
<form action ="User.jsp" name="UserList" method="post">

<table border="0" width="98%" cellspacing="0" cellpadding="0">
	<tr>
		<font size="2">
   
		<td width="19%"><b><font face="Arial" size="2"><%= trans.tslt("Organisations") %>:</font></b></td>
		<td width="13%"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)" disabled>
        
        
<%
	//Get value from return PKUser2 AssignRelationUserList.jsp
		if(request.getParameter("UserID2") != null)
		{
			if(Integer.parseInt(request.getParameter("UserID2")) == -1)
			{	//Assigned No Relation
				if(sPageOpener.equals("User.jsp"))
				{	
					//PageOpener = User.jsp
				%>
					<script language = javascript>
					opener.User.txtSup.value="";
					opener.User.hdnSupID.value=-1;
					window.close();
					</script>
				<%
				}
				else
				{
					//PageOpener = UserEdit.jsp
				%>
					<script language = javascript>
					opener.UserEdit.txtSup.value="";
					opener.UserEdit.hdnSupID.value=-1;
					window.close();
					</script>
				<%
				}
			}

			
			voUser voSup = null;
			
			if(request.getParameter("UserID2") != null)
				voSup = user.getUserInfo(Integer.parseInt(request.getParameter("UserID2")));
				
			if(voSup != null)
			{
				int iPKUserID2 = voSup.getPKUser();
				String sSup =  voSup.getSupervisorName();
				
				if(sPageOpener.equals("User.jsp"))

				{	

					//PageOpener = User.jsp
				%>
					<script language = javascript>
					opener.User.txtSup.value="<%=sSup%>";
					opener.User.hdnSupID.value="<%=iPKUserID2%>";
					window.close();
					</script>
				<%
				}
				else
				{	//PageOpener = UserEdit.jsp
				%>
					<script language = javascript>
					opener.UserEdit.txtSup.value="<%=sSup%>";
					opener.UserEdit.hdnSupID.value="<%=iPKUserID2%>";
					window.close();
					</script>
				<%
				}
			}
		}

	Vector vOrg = logchk.getOrgList(logchk.getCompany());
	
	for(int i=0; i<vOrg.size(); i++)
	{
		votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
		int PKOrg = vo.getPKOrganization();
		String OrgName = vo.getOrganizationName();
	
		if(logchk.getOrg() == PKOrg)
		{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%		}
		else	
		{
%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%		}	
	}
%>
</select></td>
		<td width="68%">&nbsp;
</td>
		</font>
	</tr>
	<tr>
   
		<td width="19%"><b><font face="Arial" size="2"><%= trans.tslt("Division") %>:</font></b></td>
		<td width="13%"><font face="Arial" size="2">
		<span style="font-size: 11pt">
			<select size="1" name="selDivision" onChange="populateDept(this.form, this.form.selDivision)">
<%
	int iDivision =0;
	int iDepartment=0;
	int iGroup=0;

	if(request.getParameter("selDivision") != null)
	{	
		//Just need to check Division whether is null
		
		//Division
		if (request.getParameter("selDivision").equals(""))
			iDivision = 0;
		else
			iDivision = Integer.parseInt(request.getParameter("selDivision"));
		//End Division
		
		//Department 
		if(request.getParameter("selDepartment") != null) {
			if (request.getParameter("selDepartment").equals(""))
				iDepartment = 0;
			else
				iDepartment = Integer.parseInt(request.getParameter("selDepartment"));
			//End Department  
		}
		//Group 
		
		if(request.getParameter("selGroup") != null) {
			if (request.getParameter("selGroup").equals(""))
				iGroup = 0;
			else
				iGroup = Integer.parseInt(request.getParameter("selGroup"));
			//End Group 
		}
	}
		
	if(iDivision == 0)%>
				<option value='' selected>&nbsp;</option>
<%

	/***********************
	* Edited by Yuni 14 Nov 2007
	************************/
	Vector v = division.getAllDivisions(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++) {
		voDivision vo = (voDivision)v.elementAt(i);

		int div_ID = vo.getPKDivision();
		String div_Name = vo.getDivision();


	if(iDivision != 0 && iDivision == div_ID)
	{
	%>
		<option value=<%=div_ID%> selected><%=div_Name%></option>
<%	}else{%>
		<option value=<%=div_ID%>><%=div_Name%></option>
<%	}
}	%>		
			</select></span></font></td>
		<td width="68%">&nbsp;
</td>
	</tr>
	<tr>
   
		<td width="19%"><b><font face="Arial" size="2"><%= trans.tslt("Department") %>:</font></b></td>
		<td width="13%"><font face="Arial" size="2">
		<span style="font-size: 11pt"><select size="1" name="selDepartment" onChange="populateGrp(this.form, this.form.selDivision, this.form.selDepartment)">

				<option value='' selected>&nbsp;</option>
<%
	int div = 0;
	if(request.getParameter("div") != null)
	{
		String divID = request.getParameter("div");
		if(divID.length() > 0)
		{
			div = Integer.parseInt(divID);
		}
	}
	
	/********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector vDepartments = Cdepartment.getAllDepartments(logchk.getOrg(),div);
	 for(int i=0; i<vDepartments.size(); i++) { 
	
	
		voDepartment voD = (voDepartment)vDepartments.elementAt(i);
        
		int dep_ID=voD.getPKDepartment();
		String dep_Name =voD.getDepartmentName();
	

	if(iDepartment != 0 && iDepartment == dep_ID)
	{
	%>
		<option value=<%=dep_ID%> selected><%=dep_Name%></option>
<%	}else{%>		
		<option value=<%=dep_ID%>><%=dep_Name%></option>

<%	}
}	%>			
		</select></span></font></td>
		<td width="68%">&nbsp;
</td>
	</tr>
	<tr>
		<td width="19%"><b><font face="Arial" size="2"><%= trans.tslt("Group") %>:</font></b></td>
		<td width="13%"> <font size="2">

		<select size="1" name="selGroup">

			<option value='' selected>&nbsp;</option>
<%
	int dept = 0;
	if(request.getParameter("dept") != null)
	{
		String department = request.getParameter("dept");
		if(department.length()> 0)
		{
			dept = Integer.parseInt(department);
		}
	}
	
	/********************
	* Edited by Yuni 14 Nov 2007
	************************/
	
	 Vector vGroup = group.getAllGroups(logchk.getOrg(),dept);
	 for(int i=0; i<vGroup.size(); i++) { 
	   
		voGroup voG = (voGroup)vGroup.elementAt(i);      
		
		int Group_ID = voG.getPKGroup();
		String GroupName = voG.getGroupName();
	
	

	if(iGroup != 0 && iGroup== Group_ID )
	{
	%>
		<option value=<%=Group_ID%> selected><%=GroupName%></option>
<%	}else{%>
		<option value=<%=Group_ID%>><%=GroupName%></option>
<%	}
}	
%>			
		</select></td>
		<td width="68%">&nbsp;
</td>
	</tr>
	<tr>
   
		<td width="19%">&nbsp;</td>
		<td width="13%"><input type="button" value="<%= trans.tslt("Search")%>" name="btnSearch" onclick="search(this.form, this.form.selDivision, this.form.selDepartment)"></td>
		<td width="68%">&nbsp;
</td>
	</tr>
</table>
<font color="#000080" face="Arial" style="font-size: 10pt"><b><%= trans.tslt("User List") %> :</b></font></p>

<div style='width:729px; height:261px; z-index:1; overflow:auto'>
<table border="1" cellspacing="1" width="728" id="AutoNumber4" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
  <tr>
  	<td width="31" align="center" bgcolor="#000080" height="35">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b>&nbsp;</b></font></td>
    <td width="48" align="center" bgcolor="#000080" height="35">
    <font face="Arial" style="font-size: 10pt"><a href="UserList.jsp?sorting=1"><font style='color:white'><b><u><%= trans.tslt("Login ID") %></u></b></font></a></td>
    <td width="71" align="center" bgcolor="#000080" height="35">
  
    <%
    	int NameSeqe =Org.getNameSeq(logchk.getOrg());
   
    	if(NameSeqe == 0)
    	{
    %>
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=2"><font style='color:white'><u><%= trans.tslt("Family Name") %></u></a></font></b></td>
    <td width="81" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=3"><font style='color:white'><u><%= trans.tslt("Given Name") %></u></a></font></b></td>
    <td width="47" align="center" bgcolor="#000080" height="35">
        <%	}
    	else
    	{	%>
    	<b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=3"><font style='color:white'><u><%= trans.tslt("Given Name") %></u></a></font></b></td>
    <td width="57" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=2"><font style='color:white'><u><%= trans.tslt("Family Name") %></u></a></font></b></td>
    <td width="71" align="center" bgcolor="#000080" height="35">
        <%	}%>
        
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=4"><font style='color:white'><u><%= trans.tslt("Designation") %></u></a></font></b></td>
    <td width="56" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=5"><font style='color:white'><u><%= trans.tslt("Division") %></u></a></font></b></td>
    <td width="54" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=6"><font style='color:white'><u><%= trans.tslt("Department") %></u></a></font></b></td>
    <td width="50" align="center" bgcolor="#000080" height="35">
    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="AssignRelationUserList.jsp?sorting=7"><font style='color:white'><u><%= trans.tslt("Group") %></u></a></font></b></td>
    <td width="61" align="center" bgcolor="#000080" height="35">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="AssignRelationUserList.jsp?sorting=8"><font style='color:white'><u><%= trans.tslt("User Type") %></u></a></b></font></td>
    <td width="51" bgcolor="#000080" height="35">
    <p align="center">
    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="AssignRelationUserList.jsp?sorting=9"><font style='color:white'><u><%= trans.tslt("Status") %></u> </a></b>
    </font></td>
  </tr>
<%
	//Rianto 19-Jan-05: Implemented filter function to avoid showing the whole user list
	if(request.getParameter("search") != null)
	{
	
	String xLoginID = request.getParameter("txtLoginID");
	String xFamilyName = request.getParameter("txtFamilyName");
	String xGivenName = request.getParameter("txtGivenName");
	String xDesignation = request.getParameter("txtDesignation");
	String xIDNumber = request.getParameter("txtIDNumber");
	String xDivision = request.getParameter("selDivision");
	String xDepartment = request.getParameter("selDepartment");
	//String xGroup = request.getParameter("txtGroup");
	String xGroup = request.getParameter("selGroup");
	
	String xUserType = request.getParameter("selUserType");
	String xdis = request.getParameter("cliDisabled");


	int xCompanyID = logchk.getCompany();

	Vector rs_search_User = user.search_User(xDepartment,xDivision,xUserType,xFamilyName,xGivenName,xLoginID,xDesignation,xIDNumber,xGroup,xdis,xCompanyID,logchk.getOrg(),user.getSortType()); 
	for(int i=0; i<rs_search_User.size(); i++)
	{
		String disable= "Enabled";
		
		voUser vo = (voUser)rs_search_User.elementAt(i);
		
		int PKUser = vo.getPKUser();
		String FamilyName = vo.getFamilyName();
		String GivenName = vo.getGivenName();
		String LoginID = vo.getLoginName();
		String Designation = vo.getDesignation();
		String IDNumber = vo.getIDNumber();
		String Password = vo.getPassword();
		int dis = vo.getIsEnabled();
		String Division = vo.getDivisionName();
		String Department = vo.getDepartmentName();
		String UserType = vo.getUserTypeName();
		String Group = vo.getGroupName();
		
		if(dis == 0)
			disable = "Disable";
%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
  	<td width="31" height="19" align="center">
  		<p align="center">
  		<font face="Arial"><span style="font-size: 11pt">
  		<input type ="radio" name="choose" value=<%=PKUser%>></span></font></td>
    <td width="48" height="19" align="center">
	<font face="Arial" size="2"><%=LoginID%>&nbsp;</font></td>
    <td width="71" height="19" align="center">
	<font face="Arial" size="2"><%=FamilyName%>&nbsp;</font></td>
    <td width="81" height="19" align="center">
	<font face="Arial" size="2"><%=GivenName%>&nbsp;</font></td>
    <td width="47" height="19" align="left">
	<font face="Arial" size="2"><%=Designation%>&nbsp;</font></td>
    <td width="57" height="19" align="left">
	<font face="Arial" size="2"><%=Division%>&nbsp;</font></td>
    <td width="71" height="19" align="left">
	<font face="Arial" size="2"><%=Department%>&nbsp;</font></td>
    <td width="56" height="19" align="left">
	<font face="Arial" size="2"><%=Group%>&nbsp;</font></td>
    <td width="54" height="19" align="center">
	<font face="Arial" size="2"><%=UserType%>&nbsp;</font></td>
    <td width="50" height="19" align="center">
	<font face="Arial" size="2"><%=disable%>&nbsp;</font></td>

    

  </tr>
<%
	}
} //end if(request.getParameter("btnSearch") != null)
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
   
          <input type="button" value="<%= trans.tslt("Assign No Supervisor") %>" name="btnAssignNoRelation" onclick="AssignNoRelationExecute(this.form)" style="float: right"></td>
    <td width="26%">
          <p align="center">
          <input type="button" value="<%= trans.tslt("Assign") %>" name="btnAssign" onclick="AssignRelationExecute(this.form)" style="float: right"></td>
    <td width="17%">
          <p align="center"> <font size="2">
   
          <input type="button" value="<%= trans.tslt("Cancel") %>" name="btnCancel" onclick="CancelExecute()" style="float: right"></td>
    <td width="24%">
    <font size="2">&nbsp;
	</font></td>
  </tr>
</table>
</form>


<table border="0" width="610" height="26">

	<tr>
   
		<td align="center" height="5" valign="top"></td></tr>

	</table>

</body>
</html>