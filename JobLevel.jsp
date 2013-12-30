<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                 
<jsp:useBean id="JobLevel" class="CP_Classes.JobLevel" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<%@ page import="CP_Classes.vo.voJobLevel"%>
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
		if(confirm("<%=trans.tslt("Delete Job Level")%>?"))
		{
			form.action="JobLevel.jsp?Delete=1";
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
			if(confirm("<%=trans.tslt("Edit Job Level")%>?"))
			{
				if(isNumericValue(field2.value)) {
					form.action="JobLevel.jsp?Edit=1";
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
			alert("<%=trans.tslt("Please enter Job Level")%>");
		}
	}
}
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Add(form, field)
function Add(form, field)
{
	if(field.value != "")
	{
		if(confirm("<%=trans.tslt("Add Job Level")%>?"))
		{
				if(isNumericValue(field.value)) {
					form.action="JobLevel.jsp?Add=1";
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
		alert("<%=trans.tslt("Please enter Job Level")%>");
	}
}

function proceed(form,field)
{
	form.action="JobLevel.jsp?proceed="+field.value;
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
	
	//using regular expression to search for string existence
	if(str.search(/^\d+$/) == -1) 
		isValid = false;
	
	return isValid;
}

</script>
<body style="text-align: center">
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
	int JobLevel_ID = new Integer(request.getParameter("JobLevel_ID")).intValue();
	JobLevel.deleteRecord(JobLevel_ID,logchk.getOrg(), logchk.getPKUser());

%>
	<script>
	//added by junwei 28 feb 2008
	alert("Deleted successfully");
	</script>
<%
}

if(request.getParameter("Edit") != null)
{
	int JobLevel_ID = new Integer(request.getParameter("JobLevel_ID")).intValue();

	String txtJobLevel = request.getParameter("txtJobLevel").trim();
	
	
	int iPKJobLevel = JobLevel.getPKJobLevel(txtJobLevel, logchk.getOrg());
	
	if(iPKJobLevel != 0 && iPKJobLevel != JobLevel_ID) {
%>
		<script>
		alert("Record exists ");
		</script>
<%	
 
	} else {
		boolean bIsEdited = JobLevel.editRecord(JobLevel_ID, txtJobLevel,logchk.getOrg(), logchk.getPKUser());
	
		if(bIsEdited) {
%>
		<script>
		alert("Edited successfully");
		</script>
<%
		}
	}
}

if(request.getParameter("Add") != null)
{

	String txtJobLevel = request.getParameter("txtJobLevel").trim();
	
	boolean bExist = JobLevel.existRecord(txtJobLevel, logchk.getOrg());
	
	if(!bExist) {
		boolean bIsAdded = JobLevel.addRecord(txtJobLevel,logchk.getOrg(), logchk.getPKUser());
	
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

<form name ="JobLevel" method="post" action="JobLevel.jsp">
<table border="0" width="99%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3">
		<b>
		<font face="Arial" color="#000080" size="2"><%= trans.tslt("Job Level") %></font></b></td>
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
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="17%"><b><font face="Arial" size="2"><%= trans.tslt("Organisation") %>:</font></b></td>
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
		<td width="68%">&nbsp;
</td>
	</tr>
	<tr>
		<td width="17%">&nbsp;</td>
		<td width="15%">&nbsp;</td>
		<td width="68%">&nbsp;</td>
	</tr>
</table>
<table border="0" width="99%" cellspacing="0" cellpadding="0" height="79">
	<tr>
		<td>
<table border="0" width="674" cellspacing="0" cellpadding="0">
	<tr>
		<td width="255">
				<div style='width:242px; height:137px; z-index:1; overflow:auto'>
<table border="1" width="100%" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%= trans.tslt("Job Level") %></font></b></td>
	</tr>

<%

   /********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector v = JobLevel.getAllJobLevels(logchk.getOrg());
	 
	 for(int i=0; i<v.size(); i++) {
	  
		voJobLevel vo = (voJobLevel)v.elementAt(i);
      
		int JobLevel_ID = vo.getPKJobLevel();
        String JobLevel_Desc = vo.getJobLevelName();
				
%>
<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="18%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="JobLevel_ID" value=<%=JobLevel_ID%> onclick="show(JobLevel_ID,txtJobLevel,data)"></span><font size="2">
		</font></font>
		<td width="68%" align="left">
		<font face="Arial" size="2"><%=JobLevel_Desc%><input type=hidden value="<%=JobLevel_Desc%>" name="data"></font></td>
</tr>
<%	}	%>		
	
</table>
</div>
		</td>
		<td width="419" align="center">
		<font face="Arial" size="2"><b><%= trans.tslt("Job Level") %>:</b>&nbsp; </font>
		<input type="text" name="txtJobLevel" size="20"><table border="0" width="55%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" onclick="Add(this.form, this.form.txtJobLevel)"></td>
				<td align="center">
				<input type="button" value="<%= trans.tslt("Edit") %>" name="btnEdit" onclick="Edit(this.form, this.form.JobLevel_ID,this.form.txtJobLevel)"></td>
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
				<td width="112">&nbsp;</td>
				<td>
				<input type="button" value="<%= trans.tslt("Delete") %>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.JobLevel_ID)"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%	}	%>
<div align="left">
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>