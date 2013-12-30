<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Department" class="CP_Classes.Department" scope="session"/>
<jsp:useBean id="Division" class="CP_Classes.Division" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="server" class="CP_Classes.Setting" scope="session"/>
<%@ page import="CP_Classes.vo.voDepartment"%>
<%@ page import="CP_Classes.vo.voDivision"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>
<html>
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
//void function Delete(form, field1, field2)
function Delete(form, field1, field2)
{
	if(check(field1))
	{
		if(confirm("<%=trans.tslt("Delete Department")%>?"))
		{
			form.action="Department.jsp?Delete=1&div=" + field2.value;
			form.method="post";
			form.submit();
		}
	}

}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Edit(form, field1, field2, field3)
function Edit(form, field1, field2, field3)
{
	if(field3.value != '0') {
	
		if(check(field1))
		{
			if(field2.value != "")
			{
				if(form.txtDepartmentCode.value != '') 
				{
					if(confirm("<%=trans.tslt("Edit Department")%>?"))
					{
							form.action="Department.jsp?Edit=1&div=" + field3.value;
							form.method="post";
							form.submit();
					}
				} 
				else 
				{
					alert("<%=trans.tslt("Please enter Department Code")%>");
				}
			}
			else
			{
				alert("<%=trans.tslt("Please enter Department Name")%>");
			}
		}

	} else {
		alert("<%=trans.tslt("Please select Division")%>");
	}

}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Add(form, field1, field2)
function Add(form, field1, field2)
{
	if(field2.value != '0') {
		if(field1.value != "")
		{
			if(form.txtDepartmentCode.value != '') 
			{
				if(confirm("<%=trans.tslt("Add Department")%>?"))
				{
						form.action="Department.jsp?Add=1&div=" + field2.value;
						form.method="post";
						form.submit();
				}
			} 
			else 
			{
				alert("<%=trans.tslt("Please enter Department Code")%>");
			}
		}
		else
		{
			alert("<%=trans.tslt("Please enter Department Name")%>");
		}
	} else {
		alert("<%=trans.tslt("Please select Division")%>");
	}
}
/*	choosing organization*/

function proceed(form,field)
{
	form.action="Department.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function show(field1, txtDepartment, txtDepartCode, departCode, departName)
{
	for (i = 0; i < field1.length; i++) 
	{
		if(field1[i].checked)
		{
			txtDepartment.value = departName[i].value;
			txtDepartCode.value = departCode[i].value;
		}
	}
	if(field1.checked)
	{	
		txtDepartment.value = departName.value;
		txtDepartCode.value = departCode.value;
	}
}

function populateDept(form,field)
{
	form.action="Department.jsp?div="+field.value;
	form.method="post";
	form.submit();
}

</script>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<body style="text-align: left">
<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
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
	
	int Department_ID = new Integer(request.getParameter("Department_ID")).intValue();
	boolean bIsDeleted = Department.deleteRecord(Department_ID,logchk.getOrg(), logchk.getPKUser());

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
		alert("Deletion cannot be performed. Users under this department are assigned with one or more surveys.");
		</script>
	<% }
}

if(request.getParameter("Edit") != null)
{
	int Department_ID = new Integer(request.getParameter("Department_ID")).intValue();
	
	String txtDepartment = request.getParameter("txtDepartment");		
	String txtDepartmentCode= request.getParameter("txtDepartmentCode");			
	boolean bIsEdited = Department.editRecord(Department_ID, txtDepartment, txtDepartmentCode, logchk.getOrg(), logchk.getPKUser());
	
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

if(request.getParameter("Add") != null)
{
	
	String txtDepartment = request.getParameter("txtDepartment");
	String txtDepartmentCode= request.getParameter("txtDepartmentCode");		
	int division = Integer.parseInt(request.getParameter("div"));
	
	int iFKDep = Department.checkDeptExist(txtDepartment, txtDepartmentCode, division, logchk.getOrg());
	
	if(iFKDep != 0) {
%>
		<script>
		alert("Record exists");
		</script>
<%
	} else {
		boolean bIsAdded = Department.addRecord(txtDepartment,txtDepartmentCode, logchk.getOrg(), logchk.getPKUser(), division);

		if(bIsAdded) {
%>
		<script>
		alert("Added successfully");
		</script>
<%
		} 
	}
}
%>
<form name ="Department" method="post" action="Department.jsp">
<table border="0" width="88%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4">
		<b>
		<font face="Arial" color="#000080" size="2"><%= trans.tslt("Department") %></font></b></td>
	</tr>
	<tr>
		<td colspan="4">
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
		<td width="20%"><b><font face="Arial" size="2"><%= trans.tslt("Organisation") %>:</font></b></td>
		<td width="15%"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)"><font face="Arial" size="2">
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
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp; 
   
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
%>				<option value=<%=Division_ID%> selected><%=Division_Desc%></option>
<%  		} 
 			else
 			{
%>				<option value=<%=Division_ID%>><%=Division_Desc%></option>
<%  		}	
		}
		else
		{
%>			<option value=<%=Division_ID%>><%=Division_Desc%></option>
<%		}
	}
%>
</select></td>
		<td width="16%">&nbsp;</td>
		<td width="57%">&nbsp; 
   
    	</td>
	</tr>
	<tr>
		<td width="16%">&nbsp;</td>
		<td width="17%">&nbsp;</td>
		<td width="67%" colspan="2">&nbsp;</td>
	</tr>
</table>


<table border="0" width="88%" cellspacing="0" cellpadding="0" height="79">

	<tr>
		<td>
		 
<table border="0" width="65%" cellspacing="0" cellpadding="0">

	<tr>
	
		<td width="331">
		<div style='width:331px; height:125px; z-index:1; overflow:auto'>
<table border="1" width="313" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Department") %></font></b></td>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Department Code") %></font></b></td>
	</tr>
	
	
<%
int totalRecords = 0;
if(request.getParameter("div") != null)
{
	int divID = Integer.parseInt(request.getParameter("div"));
	
	/********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector vDepartments = Department.getAllDepartments(logchk.getOrg(),divID);
	 for(int i=0; i<vDepartments.size(); i++) { 
	  totalRecords ++;
		voDepartment voD = (voDepartment)vDepartments.elementAt(i);
        
		int Department_ID=voD.getPKDepartment();
		String Department_Desc =voD.getDepartmentName();
		String Department_Code=voD.getDepartmentCode();
		
%>
		<tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFcc'">
		<td width="11%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="Department_ID" value=<%=Department_ID%> onclick="show(Department_ID, txtDepartment, txtDepartmentCode, dataCode, data)"></span><font size="2">
		</font></font>
		<td width="82%" align="left">
		<font face="Arial" size="2"><%=Department_Desc%><input type=hidden value="<%=Department_Desc%>" name="data"></font></td>
		<td width="82%" align="left">
		<font face="Arial" size="2"><%=Department_Code%><input type=hidden value="<%=Department_Code%>" name="dataCode"></font></td>
</tr>
<%	}	
}
%>		
	
</table>
</div>
		</td>
		<td width="419" align="center">
		
		<table border="0" width="55%" cellspacing="0" cellpadding="0">
			<tr>
				<td align=center colspan=2>
				<font face="Arial" size="2"><b>&nbsp;<%= trans.tslt("Department") %>:</b> </font><font size="2">
				&nbsp;<input type="text" name="txtDepartment" size="21"></font>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
				<p></p><font face="Arial" size="2"><b>&nbsp;<%= trans.tslt("Department Code") %>:</b> </font><font size="2">
				&nbsp;<input type="text" name="txtDepartmentCode" size="21"></font></td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtDepartment, this.form.selDiv)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.Department_ID,this.form.txtDepartment, this.form.selDiv)"></td>
			</tr>
			
		</table>
		
		</td>
		
	</tr>
	
</table>
		</td>
	</tr>
	<tr>
		<td width="92%">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="92%">
		<table border="0" width="33%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="153">&nbsp;</td>
				<td>
				<%if (totalRecords >0){%>
				<p align="center">
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.Department_ID, this.form.selDiv)">
				<%}%>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

</form>
<p></p>
<%@ include file="Footer.jsp"%>
<%	}%>
</body>
</html>
