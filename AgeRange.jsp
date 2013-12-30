<%
/**********************************
** Last Edited 29 October 2007 by Y
**
***********************************/
%>
<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>  
                 
<jsp:useBean id="age" class="CP_Classes.AgeRange" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
	// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<%@ page import="CP_Classes.vo.voAge"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
var hasil
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
		if(confirm("<%=trans.tslt("Delete Age Range")%>?"))
		{
			form.action="AgeRange.jsp?Delete=1";
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
			if(confirm("<%=trans.tslt("Edit Age Range")%>?"))
			{
					if(isNumericValue(field2.value)) {
						form.action="AgeRange.jsp?Edit=1";
						form.method="post";
						form.submit();
					} else {
						alert("Please input numeric values only");
						return false;
					}
			}
		}
		else
		{
			alert("<%=trans.tslt("Please enter Age Range")%>");
		}
	}

}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Add(form, field)
function Add(form, field)
{
	if(field.value != "")
	{
		if(confirm("<%=trans.tslt("Add Age Range")%>?"))
		{	
			if(isNumericValue(field.value)) {
				form.action="AgeRange.jsp?Add=1";
				form.method="post";
				form.submit();
			} else {
				alert("Please input numeric values only");
				return false;
			}
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Age Range")%>");
	}
}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="AgeRange.jsp?proceed="+field.value;
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

function isNumericValue(str) {
	var isValid = true;
	
	//By Hemilda date:05/06/2008 seach per char, so dec can be detected.
	//using regular expression to search for string existence
	for (  i = 0; i <str.length; i++)
	{
		if ((str.charAt(i)).search(/^\d+(\.\d+)?$/) == -1)
		{
			isValid = false;
		}
	}
	
	//if(str.search(/^\d+(\.\d+)?$/) == -1) 
		//isValid = false;
	
	return isValid;
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

	int age_ID = new Integer(request.getParameter("age_ID")).intValue();
	boolean bIsDeleted = age.deleteRecord(age_ID, logchk.getPKUser());

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
	int age_ID = new Integer(request.getParameter("age_ID")).intValue();
	
	int txtAge = new Integer(request.getParameter("txtAge")).intValue();
	
	//check whether age range already exists for edit
	//by Hemilda Date : 06/05/2008 
	boolean bIsExist = age.existRecord_Edit(txtAge, logchk.getOrg(),age_ID);
	if(!bIsExist) {
		boolean bIsEdited = age.editRecord(age_ID, txtAge, logchk.getOrg(), logchk.getPKUser());

		if(bIsEdited) {
	%>
			<script>
			alert("Edited successfully");
			</script>
	<%
		} 
	}else{
%>		
		<script>
		alert("Record exists");
		</script>
<%		
	}
}

if(request.getParameter("Add") != null)
{
	
		int txtAge = new Integer(request.getParameter("txtAge")).intValue();
		
		boolean bIsExist = age.existRecord(txtAge, logchk.getOrg());
		
		if(!bIsExist) {
		
			boolean bIsAdded = age.addRecord(txtAge, logchk.getOrg(), logchk.getPKUser());
			
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
%>
			
<%	
}
%>

<form name ="AgeRange" method="post" action="AgeRange.jsp">
<table border="0" width="88%" cellspacing="0" cellpadding="0" height="79">
	<tr>
		<td colspan="4">
<b>
<font face="Arial" color="#000080" size="2"><%= trans.tslt("Age Range") %></font></b></td>
	</tr>
	<tr>
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
		<td width="20%"><b><font face="Arial" size="2"><%= trans.tslt("Organisation") %>:</font></b></td>
		<td width="15%"> <font size="2">
   
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
			{	%>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
		<%	} else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
		<%	}	
		}	// End of for loop
	} else { %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select></td>
		<td width="14%">&nbsp;</td>
		<td width="51%">&nbsp; 
   
    	</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;
</td>
	</tr>
	<tr>
		<td colspan="4">
<table border="0" width="55%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="208">
		<div style='width:232px; height:136px; z-index:1; overflow:auto'>
<table border="1" width="210" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<font face="Arial" style="font-weight:700" color="#FFFFFF" size="2"><%= trans.tslt("Age Range") %></font></td>
	</tr>

<%
	/**************************
	* Edited by Yuni 17 Oct 2007
	***************************/
	Vector v = age.getAllAges(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++) {
		voAge vo = (voAge)v.elementAt(i);

		int age_ID = vo.getPKAge();
		int age_range = vo.getAgeRangeTop();
%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="12%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="age_ID" value=<%=age_ID%> onclick="show(age_ID,txtAge,data)"></span><font size="2">
		</font></font>
		<td width="81%" align="center">
		<font face="Arial" size="2"><%=age_range%><input type=hidden value="<%=age_range%>" name="data"></font></td>
</tr>

<%	}	%>		

</table>
</div>
		</td>
		<td width="269" align="center">
		<font face="Arial" size="2"><b><%= trans.tslt("Age Range") %>:&nbsp;</b> </font><input type="text" name="txtAge" size="6"><table border="0" width="68%" cellspacing="0" cellpadding="0">

			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtAge)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.age_ID,this.form.txtAge)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="87%" colspan="4">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="87%" colspan="4">
		<table border="0" width="31%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="131">&nbsp;</td>
				<td>
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.age_ID)"></td>
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