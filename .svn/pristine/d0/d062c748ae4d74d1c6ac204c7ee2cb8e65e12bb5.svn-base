<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
				 java.util.*"%>
				   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="Department" class="CP_Classes.Department" scope="session"/>
<jsp:useBean id="Division" class="CP_Classes.Division" scope="session"/>                  
<jsp:useBean id="Group" class="CP_Classes.Group" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.voGroup"%>
<%@ page import="CP_Classes.vo.voDepartment"%>
<%@ page import="CP_Classes.vo.voDivision"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

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
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Delete(form, field1, field2, field3)
function Delete(form, field1, field2, field3)
{
	if(check(field1))
	{
		if(confirm("<%=trans.tslt("Delete Group")%>?"))
		{
			form.action="Group.jsp?Delete=1&div=" + field2.value + "&dept=" + field3.value;
			form.method="post";
			form.submit();
		}
	}
}
//void function Edit(form, field3, field, field1, field2)
function Edit(form, field3, field, field1, field2)
{

	if(field1.value != "0") {
		
		if(field2.value != "0") {

			if(check(field3))
			{
				if(field.value != "")
				{
					if(confirm("<%=trans.tslt("Edit Group")%>?"))
					{
						form.action="Group.jsp?Edit=1&div=" + field1.value + "&dept=" + field2.value;
						form.method="post";
						form.submit();
					}
				}
				else
				{
					alert("<%=trans.tslt("Please enter Group Name")%>");
				}
			}

		} else {
			alert("<%=trans.tslt("Please select Department")%>");
		}
	} else {
		alert("<%=trans.tslt("Please select Division")%>");
	}
}

//void function Add(form, field1, field2, field3)
function Add(form, field1, field2, field3)
{
	if(field2.value != "0") {
		
		if(field3.value != "0") {


			if(field1.value != "")
			{
				
				if(confirm("<%=trans.tslt("Add Group")%>?"))
				{
					form.action="Group.jsp?Add=1&div=" + field2.value + "&dept=" + field3.value;
					form.method="post";
					form.submit();
				}
				
			}
			else
			{
				alert("<%=trans.tslt("Please enter Group Name")%>");
			}
		} else {
			alert("<%=trans.tslt("Please select Department")%>");
		}
	} else {
		alert("<%=trans.tslt("Please select Division")%>");
	}
	
}

function proceed(form,field)
{
	form.action="Group.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function show(field1,grpDesc,txtGroup)
{
	for (i = 0; i < field1.length; i++) 
	{
		if(field1[i].checked)
		{
			txtGroup.value = grpDesc[i].value
		}
	}
	if(field1.checked)
	{	
		txtGroup.value = grpDesc.value;
	}
}

function populateDept(form,field)
{
	form.action="Group.jsp?div="+field.value;
	form.method="post";
	form.submit();
}

function populateGrp(form, field1, field2)
{
	form.action="Group.jsp?div=" + field1.value + "&dept=" +field2.value;
	form.method="post";
	form.submit();
}


</script>
<body style="text-align: left">
<b>
<%

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> </b> <font size="2">
   
    	<b>
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  { 

if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}

if(request.getParameter("Delete") != null)
{
	int Group_ID = new Integer(request.getParameter("Group_ID")).intValue();
	boolean bIsDeleted = Group.deleteRecord(Group_ID,logchk.getOrg(), logchk.getPKUser());
	if(bIsDeleted) {
%>
		<script>
		alert("Deleted successfully");
		</script>
<%
	} 
	//Added by Ha 02/06/08
	else
	{%>
		<script>
		alert("Deletion cannot be performed. Users under this division are assigned with one or more surveys.");
		</script>
<% 	}
}

if(request.getParameter("Edit") != null)
{
	int Group_ID = new Integer(request.getParameter("Group_ID")).intValue();
	
	String txtGroup = request.getParameter("txtGroup");
	int dept = Integer.parseInt(request.getParameter("dept"));
	
	int iFKGrp = Group.checkGroupExist(txtGroup, logchk.getOrg(), dept);
	
	if(iFKGrp != 0 && iFKGrp != Group_ID) {
%>
		<script>
		alert("Record exists");
		</script>
<%
	} else {
		boolean bIsEdited = Group.editRecord(Group_ID,txtGroup,logchk.getOrg(), logchk.getPKUser());
		if(bIsEdited) {
%>
		<script>
		alert("Edited successfully");
		</script>
<%
		} else {
%>
		<script>
		alert("Record exists");
		</script>
<%
		}
	}
}

if(request.getParameter("Add") != null)
{
	
	String txtGroup = request.getParameter("txtGroup");
	int dept = Integer.parseInt(request.getParameter("dept"));
	
	int iFKGrp = Group.checkGroupExist(txtGroup, logchk.getOrg(), dept);
	
	if(iFKGrp != 0) {
%>
		<script>
		alert("Record exists");
		</script>
<%
	} else {
		boolean bIsAdded = Group.addRecord(txtGroup,logchk.getOrg(), logchk.getPKUser(), dept);
	
		if(bIsAdded) {
%>
		<script>
		alert("Added successfully");
		</script>
<%
		} else {
%>
		<script>
		alert("Record exists");
		</script>
<%
		
		}
	}
}
%>

</b>

<form name ="Group" method="post" action="Group.jsp">
<table border="0" width="88%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3">
		<b>
		<font face="Arial" color="#000080" size="2"><%= trans.tslt("Group/Section") %></font></b></td>
		</tr>
	<tr>
		<td colspan="3">
<font size="2">
   
<ul>
	<li><font face="Arial" size="2"><%= trans.tslt("To Add, click on the Add button") %>.
	</font></li>
	<li><font face="Arial" size="2"><%= trans.tslt("To Edit, click on the relevant radio button and click on the Edit button") %>.
	</font></li>
	<li><font face="Arial" size="2"><%= trans.tslt("To Delete, click on the relevant radio button and click on the Delete button") %>.
	</font></li>
</ul>
		</td>
		</tr>
	<tr>
		<td width="20%"><b><font face="Arial" size="2"><%= trans.tslt("Organisations") %>:</font></b></td>
		<td width="15%"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
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

			if(logchk.getOrg() == PKOrg)
			{ %>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
			<% } else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		} 
	} else { %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>

</select></td>
		<td width="41%">&nbsp;
</td>
	</tr>
	<tr>
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><font face="Arial" size="2"><%= trans.tslt("Division") %>:</font></b></td>
		<td width="15%"><select size="1" name="selDiv" onChange="populateDept(this.form, this.form.selDiv)"><font face="Arial" size="2">
		<option selected value='0'></option>
<%

 /********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector v = Division.getAllDivisions(logchk.getOrg());
	
	int Division_ID = 0;
	String Division_Desc = "";
	
	 for(int i=0; i<v.size(); i++) {
		voDivision vo = (voDivision)v.elementAt(i);

		Division_ID = vo.getPKDivision();
		Division_Desc = vo.getDivision();

	
	if(request.getParameter("div") != null) 
	{	
		if(Division_ID == Integer.parseInt(request.getParameter("div")))
		{
%>		<option value=<%=Division_ID%> selected><%=Division_Desc%></option>
<%  	} 
 		else
 		{
%>		<option value=<%=Division_ID%>><%=Division_Desc%></option>
<%  	}	
	}
	else
	{
%>	<option value=<%=Division_ID%>><%=Division_Desc%></option>
<%	}
}

%>
</select></td>
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp; 
   
    	</td>
	</tr>
	<tr>
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><font face="Arial" size="2"><%= trans.tslt("Department") %>:</font></b></td>
		<td width="15%"><select size="1" name="selDept" onChange="populateGrp(this.form, this.form.selDiv, this.form.selDept)"><option value="0" selected></option><font face="Arial" size="2">
<%
if(request.getParameter("div") != null){
	int divID = Integer.parseInt(request.getParameter("div"));
	int Dept_ID = 0;
	String Dept_Desc = "";
	/********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector vDepartments = Department.getAllDepartments(logchk.getOrg(),divID);
	 
	 for(int i=0; i<vDepartments.size(); i++) {
	  
		voDepartment voD = (voDepartment)vDepartments.elementAt(i);
        
		Dept_ID=voD.getPKDepartment();
		Dept_Desc =voD.getDepartmentName();
		
	
	if(request.getParameter("dept") != null) 
	{
		if(Dept_ID == Integer.parseInt(request.getParameter("dept")))
		{
%>		<option value=<%=Dept_ID%> selected><%=Dept_Desc%></option>
<%  	} 
 		else
 		{
%>		<option value=<%=Dept_ID%>><%=Dept_Desc%></option>
<%  	}	
	}
	else
	{
%>	<option value=<%=Dept_ID%>><%=Dept_Desc%></option>
<%	}
}
}
%>
</select></td>
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp; 
   
    	</td>
	</tr>
	<tr>
		<td width="25%">&nbsp;</td>
		<td width="33%">&nbsp;</td>
		<td width="41%">&nbsp;</td>
	</tr>
</table>
<table border="0" width="99%" cellspacing="0" cellpadding="0" height="79">
	<tr>
		<td>
<table border="0" width="71%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="320">
		<div style='width:210px; height:175px; z-index:1; overflow:auto'>
<table border="1" width="100%" height="12" bordercolor="#3399FF" bgcolor="#FFFFCC">
	<tr>

		<td bgcolor="Navy" colspan="3">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Group / Section") %></font></b></td>
	</tr>
		
	
<%	
	int totalRecords = 0;
	if(request.getParameter("dept") != null){
	int deptID = Integer.parseInt(request.getParameter("dept"));
	
	/********************
	* Edited by James 17 Oct 2007
	************************/
	
	 Vector vGroup = Group.getAllGroups(logchk.getOrg(),deptID);
	 for(int i=0; i<vGroup.size(); i++) { 
	   
		voGroup voG = (voGroup)vGroup.elementAt(i);      
		totalRecords++;
		int Group_ID = voG.getPKGroup();
		String Group_Desc = voG.getGroupName();
%>
<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="8%" align="center"><font face="Arial">
		<span style="font-size: 11pt"> <font size="2">
   
    	<input type="radio" name="Group_ID" value=<%=Group_ID%> onclick="show(Group_ID,hidGroup,txtGroup)"></font></span><font size="2">
		</font></font>

		<td width="76%" align="left">
		<font face="Arial" size="2"><%=Group_Desc%><input type=hidden value="<%=Group_Desc%>" name="hidGroup"></font></td>
		
</tr>
<%	}	
}
%>		
	
</table>
</div>
		</td>
		<td width="371" align="center">
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td width="37%">
				<p align="right">
		<font face="Arial" size="2"><b><%= trans.tslt("Group") %>:&nbsp;&nbsp;&nbsp; </b> </font></td>
				<td width="63%"> <font size="2">
   
    			<input type="text" name="txtGroup" size="21"></td>
			</tr>
			<tr>
				<td width="37%">&nbsp;</td>
				<td width="63%">&nbsp;</td>
			</tr>
		</table>
		<table border="0" width="55%" cellspacing="0" cellpadding="0"></td>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtGroup, this.form.selDiv, this.form.selDept)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.Group_ID,this.form.txtGroup, this.form.selDiv, this.form.selDept)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="87%">
		<table border="0" width="28%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="99">&nbsp;</td>
				<td>
				<%if (totalRecords > 0){ %>
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: right" onclick="Delete(this.form, this.form.Group_ID, this.form.selDiv, this.form.selDept)"></td>
				<%}%>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%	}%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>