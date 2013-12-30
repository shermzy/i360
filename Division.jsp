<%@ page import="java.sql.*,
                 java.text.DateFormat,
                 java.util.Vector,
                 java.io.*,
                 java.lang.String"%> 
				 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                  
<jsp:useBean id="Division" class="CP_Classes.Division" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
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
//void function Delete(form, field)
function Delete(form, field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Delete Division")%>?"))
		{
			form.action="Division.jsp?Delete=1";
			form.method="post";
			form.submit();
		}
	}
}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Edit(form, field1, field2,field3)
function Edit(form, field1, field2,field3)
{
	//check whether Division Code already been filled
	//by Hemilda Date : 06/05/2008 
	if(check(field1))
	{
		if((field2.value != "")&&(field3.value != ""))
		{
			if(confirm("<%=trans.tslt("Edit Division")%>?"))
			{
					form.action="Division.jsp?Edit=1";
					form.method="post";
					form.submit();
			}
		}else{

			if (field2.value == "")
			{
				alert("<%=trans.tslt("Please enter Division Name")%>");
			}else if (field3.value == "")
				{
					alert("<%=trans.tslt("Please enter Division Code")%>");
				}
		}
	}
}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Add(form, fielddiv,fieldcode)
function Add(form, fielddiv,fieldcode)
{
	//check whether Division Code already been filled
	//by Hemilda Date : 06/05/2008 
	if((fielddiv.value != "")&&(fieldcode.value != ""))
	{
		if(confirm("<%=trans.tslt("Add Division")%>?"))
		{
				form.action="Division.jsp?Add=1";
				form.method="post";
				form.submit();
		}
	}else
	{
		if (fielddiv.value == "")
		{
			alert("<%=trans.tslt("Please enter Division name")%>");
		}else if (fieldcode.value == "")
			{
				alert("<%=trans.tslt("Please enter Division Code")%>");
			}
	}
}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="Division.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function show(field1, field2, txtDivCode, divCode, field3)
{
	
	for (i = 0; i < field1.length; i++) 
	{
		if(field1[i].checked)
		{
			field2.value = field3[i].value;
			txtDivCode.value = divCode[i].value;
		}
	}
	if(field1.checked)
	{	
		field2.value = field3.value;
	}
}

</script>
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
	int Division_ID = new Integer(request.getParameter("Division_ID")).intValue();
	boolean bIsDeleted = Division.deleteRecord(Division_ID,logchk.getOrg(), logchk.getPKUser());
	
	/*********************
	**	Eric Lu 14/5/08 **
	*********************/
	if(bIsDeleted) {
%>
		<script>
		alert("Deleted successfully");
		</script>
<%
	} else {//Messaged changed by Ha 02/06/08
%>
		<script>
		alert("Deletion cannot be performed. Users under this division are assigned with one or more surveys.");
		</script>
<%
	}
}

if(request.getParameter("Edit") != null)
{
	int Division_ID = new Integer(request.getParameter("Division_ID")).intValue();
	
	String txtDivision = request.getParameter("txtDivision");
	String txtDivisionCode= request.getParameter("txtDivisionCode");
	
	int iFKDivision = Division.checkDivExist(txtDivision, txtDivisionCode, logchk.getOrg());
	
	if(iFKDivision != 0 && iFKDivision != Division_ID) {
%>
		<script>
		alert("Record exists");
		</script>
<%
	} else {	
	
		boolean  bIsEdited = Division.editRecord(Division_ID, txtDivision, txtDivisionCode, logchk.getOrg(), logchk.getPKUser());

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

	String txtDivision = request.getParameter("txtDivision");
	String txtDivisionCode= request.getParameter("txtDivisionCode");
	
	int iFKDivision = Division.checkDivExist(txtDivision, txtDivisionCode, logchk.getOrg());
	
	if(iFKDivision != 0) {
%>
		<script>
		alert("Record exists");
		</script>
<%
	} else {
		boolean bIsAdded = Division.addRecord(txtDivision, txtDivisionCode, logchk.getOrg(), logchk.getPKUser());
	
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

   <form name ="Division" method="post" action="Division.jsp">
<table border="0" width="99%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3">
		<b>
		<font face="Arial" color="#000080" size="2"><%= trans.tslt("Division") %></font></b></td>
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
		<td width="20%"><font face="Arial" size="2"><b><%= trans.tslt("Organisation") %>:</b></td>
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
</font>
</select></td>
		<td width="67%">



		</td>
	</tr>
	<tr>
		<td width="16%">&nbsp;</td>
		<td width="17%">&nbsp;</td>
		<td width="67%">&nbsp;</td>
	</tr>
</table>



<table border="0" width="99%" cellspacing="0" cellpadding="0" height="79">
	<tr>
		<td>
		
<table border="0" width="60%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="231">
		<div style='width:281px; height:136px; z-index:1; overflow:auto'>
<table border="1" width="258" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Division") %></font></b></td>
		<td bgcolor="Navy" height="27">
		<p align="center"><b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Division Code") %></font></b></td>
	</tr>
	<%	


	
	int Division_ID = 0;
	String Division_Desc = "";
	String Division_Code = "";
	
	
	/********************
	* Edited by James 17 Oct 2007
	************************/
	Vector v = Division.getAllDivisions(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++) {
		voDivision vo = (voDivision)v.elementAt(i);

		Division_ID = vo.getPKDivision();
		Division_Desc = vo.getDivision();
		Division_Code = vo.getDivisionCode();
		
	
%>
<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="11%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="Division_ID" value=<%=Division_ID%> onclick="show(Division_ID, txtDivision, txtDivisionCode, dataCode, data)"></span><font size="2">
		</font></font>
		<td width="57%" align="left">
		<font face="Arial" size="2"><%=Division_Desc%><input type=hidden value="<%=Division_Desc%>" name="data"></font></td>
		<td width="25%" align="left">
		<font face="Arial" size="2"><%=Division_Code%><input type=hidden value="<%=Division_Code%>" name="dataCode"></font></td>
</tr>
<%	}	%>		

</table>
</div>
		</td>
		<td width="354" align="center">
		<font size="2">
   
		<b><font size="2" face="Arial"><%= trans.tslt("Division") %>:</font></b>&nbsp; </font>
		<br>
		<input type="text" name="txtDivision" size="21">
		<table border="0" width="55%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" colspan="2"><font face="Arial" size="2"><b><%= trans.tslt("Division Code") %></b></font>
				<b><font size="2">:</font></b> 
				<br><font size="2"><input type="text" name="txtDivisionCode" size="21"></font></td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtDivision,this.form.txtDivisionCode)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.Division_ID,this.form.txtDivision,this.form.txtDivisionCode)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="87%">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="87%">
		<table border="0" width="31%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="156">&nbsp;</td>
				<td>
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.Division_ID)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%	}	%>
<p></p>
<%@ include file="Footer.jsp"%>

</body>
</html>