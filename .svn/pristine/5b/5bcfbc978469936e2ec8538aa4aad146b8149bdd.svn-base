<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<html>
<head>


<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
.style1 {
	color: #000099;
	font-weight: bold;
}
-->

<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>

<body style="background-color: #FFFFFF">
<jsp:useBean id="JobDetail" class="CP_Classes.JobCategoryDetail" scope="session"/>
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>    
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.voCompetency"%>

<title>Job Category</title>

<script language = "javascript">
var x = parseInt(window.screen.width) / 2 - 300;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 250;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


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
//void function confirmAdd(form, field) {
function confirmAdd(form, field) {
	if(field.value != 0) {
		var myWindow=window.open('AddJobCatCompetencyDetail.jsp?add=1','windowRef','scrollbars=no, width=600, height=500');
		myWindow.moveTo(x,y);
		return false;
	}else {
		alert("Please select Job Category");
		return false;
	}
}

function confirmDelete(form, field) {
	if(check(field))
	{	
		if(confirm("Delete Competency assignment(s)?")) {
			form.action="AddJobCatCompetency.jsp?delete=1";
			form.method="post";
			form.submit();
			return true;
		} else
			return false;
	
	}
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelAdd()
function cancelAdd()
{
	window.close();
}

function changeCat(form,field)
{
	form.action="AddJobCatCompetency.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

function checkedAll(form, checkAll, field)
{	
	var alreadyCheck = 0;
	
	if(field != null) {
		if(checkAll.checked == true) {
			for(var i=0; i<field.length; i++) {
				field[i].checked = true;
				alreadyCheck == 1;
			}
			
			if(alreadyCheck == 0)
				field.checked = true;
		}
		else {
			for(var i=0; i<field.length; i++) {
				field[i].checked = false;	
				alreadyCheck == 1;
			}
			
			if(alreadyCheck == 0)
				field.checked = false;
				
		}
	}
}
</script>

<%
request.setCharacterEncoding("UTF-8");

String username=(String)session.getAttribute("username");

 if (!logchk.isUsable(username)) {
%>
		<script>
			parent.location.href = "index.jsp";
		</script>
<% 
	} 
  else 
  { 

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	int CatID = JobCat.getJobCatID(); 
	String name = "";
	int SysLib = JobCat.CheckSysLibJobCategory(CatID);
	
	if(request.getParameter("delete") != null) {
 	    String [] chkSelect = request.getParameterValues("checkComp");
		if(chkSelect != null) {
			boolean bIsDeleted = false;
			for(int i=0; i<chkSelect.length; i++) {
				if(chkSelect[i] != null) {
					try {
						bIsDeleted = JobDetail.DeleteCompetency(Integer.parseInt(chkSelect[i]));
					} catch(SQLException SE) {}
				}
			}
			if(bIsDeleted) {
%>
		<script>
			alert("Removed successfully");
		</script>

<%			
			}
		}

	}
	
	if(request.getParameter("proceed") != null) {
		CatID = Integer.parseInt(request.getParameter("proceed"));
		JobCat.setJobCatID(CatID);
		SysLib = JobCat.CheckSysLibJobCategory(CatID);
	}		
%>			


<form name="AddCompetency" method="post">
<table border="0" class="tablesetting" width="593">
<tr bgcolor="#CCCCCC" align="center">
		<td align="center" bgcolor="#99ccff" height="30" 
		onMouseOver="this.bgcolor='#FFCC33'; this.style.cursor = 'hand'" onClick="window.location.href = 'JobCategory.jsp'">
		<span class="style1">Job Category</span></td>
		<td align="center" bgcolor="#FFFFCC"
		onMouseOver="this.bgcolor='#FFCC33'; this.style.cursor = 'hand'" onClick="window.location.href = 'AddJobCatCompetency.jsp'">
		<span class="style1">Competency</span></td>
</tr>
</table>
&nbsp;
<table border="0" class="tablesetting">
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial">Add 
		Competency to the Job Category</font></b></td>
    </tr>
	<tr>
	  <td colspan="3"><ul>
	    <li><font face="Arial" size="2">To Add, click on the Add Competency 
		button.</font></li>
	    <li><font face="Arial" size="2">To Remove Competency assigned, tick on 
		the relevant checkbox(es) and click on the Remove Competency button.</font></li>
	    </ul></td>
    </tr>
</table>
  <table class="tablesetting">
    <tr>
      <td width="98">Job Category :</td>
      <td width="24"><input name="CatID" type="hidden" value="<%=CatID%>"></td>
      <td width="455">
<%
	if(request.getParameter("addCat") != null) {
%>	  
   	  <select size="1" name="selJobCat" disabled>
<% } else {
%>	  
   	  <select size="1" name="selJobCat" onChange="changeCat(this.form, this.form.selJobCat)">
<%
	}
%>
<option value=0 selected></option>
<%	
	Vector v = JobCat.FilterRecord(orgID);
	
	
	for(int i=0; i<v.size(); i++) 
	{
		voJobCategory vo = (voJobCategory)v.elementAt(i);
		int jobCatID = vo.getPKJobCategory();
		String catName =  vo.getJobCategoryName();
	
	if(jobCatID == CatID)
	{
%>
	<option value=<%=jobCatID%> selected><%=catName%></option>

<%	}
	else	
	{%>
	<option value=<%=jobCatID%>><%=catName%></option>
<%	}	
}%>
</select></td>
    </tr>
  </table>
&nbsp;
<%
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = JobCat.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		JobCat.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		JobCat.setSortType(type);									
	} 
%>
<div style='width:610px; height:200px; z-index:1; overflow:auto'> 
<table class="tablesetting1">
<th width="20" class="header"><input name="chkAll" type="checkbox" onClick="checkedAll(this.form, this.form.chkAll, this.form.checkComp)"></th>
<th width="35" class="header"><b>No</b></th>
<th width="178" class="header"><a href="AddJobCatCompetency.jsp?name=1"><b>
<font style='color:white'><u>Competency</u></font></b></a></th>
<th width="340" class="header"><a href="AddJobCatCompetency.jsp?name=2"><b>
<font style='color:white'><u>Definition</u></font></b></a></th>

<%
	
	Vector vComp = JobDetail.getCompetencyAssigned(CatID, orgID);
	 	
	for(int i=0; i<vComp.size(); i++) {
		voCompetency vo = (voCompetency)vComp.elementAt(i);
		
		int FKComp = vo.getPKJobCategoryItem();
		String CompName =  vo.getCompetencyName();		
		String definition = vo.getCompetencyDefinition();
%>
      <tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'" bgColor = "#FFFFCC">
       <td class="tdata">
	   		<input type="checkbox" name="checkComp" value=<%=FKComp%>></td>
	   	<td align="center" class="tdata">
   		  <%= i+1 %>
   		</td>
	   <td class="tdata"><%=CompName%></td>
	   <td class="tdata"><%=definition%></td>
   </tr>
<% 	
	} 

%>
</table>
</div>  
<p></p>
<table border="0" class="tablesetting">
  <tr>
    <td width="127">
<%
	if(SysLib == 1 && userType != 1) {
%>
<input type = "button" name="Submit" value="Add Competency" onClick="return confirmAdd(this.form, this.form.selJobCat)" disabled>
<%	
	} else {
%>
<input type = "button" name="Submit" value="Add Competency" onClick="return confirmAdd(this.form, this.form.selJobCat)">
<%		
	}
%>	
	</td>
    <td width="456">
<%
	if(SysLib == 1 && userType != 1) {
%>
<input name="Delete" type = "button" id="Delete" value="Remove Competency" onClick="return confirmDelete(this.form, this.form.checkComp)" disabled>
<%	
	} else {
%>
<input name="Delete" type = "button" id="Delete" value="Remove Competency" onClick="return confirmDelete(this.form, this.form.checkComp)">
<%		
	}
%>	
	</td>
  </tr>
</table>
<p></p>
</form>
<% } %>
</body>
</html>