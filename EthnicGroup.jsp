<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>  
                 
<jsp:useBean id="ethnic" class="CP_Classes.EthnicGroup" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.voEthnic"%>
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
		if(confirm("<%=trans.tslt("Delete Ethnic Group")%>?"))
		{
			form.action="EthnicGroup.jsp?Delete=1";
			form.method="post";
			form.submit();
		}
	}

}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Edit(form, field1, field2)
function Edit(form, field1, field2)
{
	if(check(field1))
	{
		if(field2.value != "")
		{
			if(confirm("<%=trans.tslt("Edit Ethnic Group")%>?"))
			{
					form.action="EthnicGroup.jsp?Edit=1";
					form.method="post";
					form.submit();
			}
		}
		else
		{
			alert("<%=trans.tslt("Please enter Ethnic Group")%>");
		}
	}

}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Add(form, field)
function Add(form, field)
{
	if(field.value != "")
	{
		if(confirm("<%=trans.tslt("Add Ethnic Group")%>?"))
		{
				form.action="EthnicGroup.jsp?Add=1";
				form.method="post";
				form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Ethnic Group")%>");
	}
}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="EthnicGroup.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function show(field1, field2,field3)
{
	
	for (i = 0; i < field1.length; i++) 
	{
		if(field1[i].checked)
		{
			field2.value = field3[i].value;
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
	
	int Ethnic_ID = new Integer(request.getParameter("ethnic_ID")).intValue();
	boolean bIsDeleted = ethnic.deleteRecord(Ethnic_ID, logchk.getPKUser());

	if(bIsDeleted) {
%>
		<script>
		alert("Deleted successfully");
		</script>
<%
	} 
	
}

if(request.getParameter("Edit") != null)
{
	int Ethnic_ID = new Integer(request.getParameter("ethnic_ID")).intValue();

	String txtEthnic = request.getParameter("txtEthnic");
	boolean bIsEdited = ethnic.editRecord(Ethnic_ID, txtEthnic, logchk.getOrg(), logchk.getPKUser());

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

	String txtEthnic = request.getParameter("txtEthnic");
	
	boolean bExist = ethnic.existRecord(txtEthnic, logchk.getOrg());
	
	if(!bExist) {
		boolean bIsAdded = ethnic.addRecord(txtEthnic, logchk.getOrg(), logchk.getPKUser());
			
		if(bIsAdded) {
%>
		<script>
		alert("Added successfully");
		</script>
<%
		} 
	} else {
%>		
		<script>
		alert("Record exists");
		</script>
<%		
	}
}
%>

<form name ="EthnicGroup" method="post" action="EthnicGroup.jsp">
<table border="0" width="88%" cellspacing="0" cellpadding="0" height="323">
	<tr>
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
		<td colspan="4">
<b>
<font face="Arial" color="#000080" size="2"><%= trans.tslt("Ethnic Group") %></font></b></td>
	</tr>
	<tr>
		<font size="2">
   
		<td colspan="4">
<ul>
	<li><font face="Arial" size="2">
	<%= trans.tslt("To Add, click on the Add button") %>.
	</font></li>
	<li><font face="Arial" size="2">
	<%= trans.tslt("To Edit, click on the relevant radio button and click on the Edit button") %>.
	</font></li>
	<li><font face="Arial" size="2">
	<%= trans.tslt("To Delete, click on the relevant radio button and click on the Delete button") %>.
	</font></li>
</ul>
		</td>
	</tr>
	<tr>
		<td width="20%" height="21"><font face="Arial" size="2"><b><%= trans.tslt("Organisation") %></b>:</font></td>
		<td width="15%" height="21"> 
		<font size="2" face="Arial" style="font-size: 10pt" color="#000080">
   
    	<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
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
			<%	} else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		}
	} else { %>
			<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
		<% } // End of isConsulting %>
</select></td>
		<td width="14%" height="21">&nbsp;</td>
		<td width="57%" height="21">&nbsp; 
		
   
    	</td>
		</tr>

	<tr>
		<td colspan="4">&nbsp;
</td>
	</tr>
	<tr>
		<td colspan="4" height="143">
<table border="0" width="63%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="235">
		<div style='width:232px; height:136px; z-index:1; overflow:auto'>
<table border="1" width="210" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Ethnic Group") %></font></b></td>
	</tr>
	
	
	<%
	/********************
	* Edited by James 17 Oct 2007
	************************/
	Vector v = ethnic.getAllEthnics(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++) {
		voEthnic vo = (voEthnic)v.elementAt(i);

		int ethnic_ID = vo.getPKEthnic();
		String ethnic_Desc =vo.getEthnicDesc();
	
%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="11%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="ethnic_ID" value=<%=ethnic_ID%> onclick="show(ethnic_ID,txtEthnic,data)"></span><font size="2">
		</font></font>
		<td width="76%" align="left">
		<font face="Arial" size="2"><%=ethnic_Desc%><input type=hidden value="<%=ethnic_Desc%>" name="data"></font></td>
</tr>
<%	}
%>	
	
</table>
</div>
		</td>
		<td width="367" align="center">
		<font face="Arial" size="2"><b><%= trans.tslt("Ethnic Group") %>:</b> </font><font size="2">&nbsp;
		</font><input type="text" name="txtEthnic" size="21"><table border="0" width="55%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtEthnic)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.ethnic_ID,this.form.txtEthnic)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="87%" colspan="4" height="19">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="87%" colspan="4">
		<table border="0" width="31%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="144">&nbsp;</td>
				<td>
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.ethnic_ID)"></td>
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
