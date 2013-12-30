<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<html>
<head>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<title>Job Category</title>
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

<body>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

var x = parseInt(window.screen.width) / 2 - 210;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 110;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

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
//void function confirmAdd() {
function confirmAdd() {
	var myWindow=window.open('JobCatUpdate.jsp','windowRef','scrollbars=no, width=420, height=220');
	myWindow.moveTo(x,y);
}

//void function confirmEdit(form, field) {	
function confirmEdit(form, field) {	
	var value = check(field);
	
	if(value) {
		var myWindow=window.open('JobCatUpdate.jsp?clicked='+value,'windowRef','scrollbars=no, width=420, height=220');
		myWindow.moveTo(x,y);    	
	}
}

function confirmDelete(form, field) {
	var value = check(field);
	
	if(value)
	{	
		if(confirm("Delete Job Category?")) {
			form.action = "JobCategory.jsp?delete="+value;
			form.method = "post";
			form.submit();
			return true;
		} else
			return false;
	}

}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form)
{
	form.action="JobCategory.jsp?proceed=1";
	form.method="post";
	form.submit();
}

</script>

<body>
<%	
	
	String username=(String)session.getAttribute("username");

  	if (!logchk.isUsable(username)) {
%>
		<script>
			parent.location.href = "index.jsp";
		</script>
<%  
	} else { 
		
		int iFKOrg 		= logchk.getOrg();	
		int iFKUser 	= logchk.getPKUser();
		int iFKUserType = logchk.getUserType();	// 1= super admin
		int iFKCC		= logchk.getCompany();
		
		String sOrigin = "";
        int iFKJobCat = 0;
        String sJobCatName = "";
        
     
		if(request.getParameter("proceed") != null) { 
			iFKOrg = Integer.parseInt(request.getParameter("selOrg"));
			logchk.setOrg(iFKOrg);
		}

		if(request.getParameter("delete") != null) {
			iFKJobCat = Integer.parseInt(request.getParameter("delete"));
			
			int check = JobCat.CheckSysLibJobCategory(iFKJobCat);
			
			if((iFKUserType == 1) || (check == 0)) {
				try {
					boolean bIsDelete = JobCat.deleteRecord(iFKJobCat, iFKUser);	
					
					if(bIsDelete) {
%>
				<script>
					alert("Deleted successfully");
				</script>


<%				
					}	
				} catch (SQLException SE) {
%>
				<script>
					alert("Data is being used");
				</script>

<%		
				}			
			} else if((iFKUserType != 16) && check == 1){
%>
			<script>
				alert("You are not allowed to delete System Generated Libraries!");
				//window.location.href = "Competency.jsp";
			</script>
<%	
			}
		}

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

	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

%>
<form name="Job Category" method="post">

<table class="tablesetting">
	<tr bgcolor="#CCCCCC" class="fontstyle" align="center">
		<td align="center" bgcolor="#FFFFCC" height="30"
		onMouseOver="this.bgcolor='#FFCC33'; this.style.cursor = 'hand'" onClick="window.location.href = 'JobCategory.jsp'">
		<span class="style1">Job Category</span></td>
	
		<td align="center" bgcolor="#99ccff"
		onMouseOver="this.bgcolor='#FFCC33'; this.style.cursor = 'hand'" onClick="window.location.href = 'AddJobCatCompetency.jsp'">
		<span class="style1">Competency</span></td>
	</tr>
</table>
&nbsp;
<table class="tablesetting">
  <tr class="headerfont">
    <tr>
	  <td colspan="2"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Job Category") %></font></b></td>
    </tr>
    </tr>
  <tr>
    <td colspan="2">
    	<ul>
		<li>To Add, click on the Add button.</li>
	    <li>To Edit, click on the relevant radio button and click on the Edit button.</li>
		<li>To Delete, click on the relevant radio button and click on the Delete button.</li>
		</ul>
	</td>
  </tr>
</table>
<table class="tablesetting">
  <tr>
    <td width="118">Organisation</td>
    <td width="23">:</td>
    <td width="436" colspan="2">
		<select size="1" name="selOrg" onChange="proceed(this.form,this.form.selOrg)">
<%
	Vector vJobCat = new Vector();
	vJobCat.removeAllElements();

	vJobCat = JobCat.getJobCategory(iFKOrg);	
	voJobCategory voJobCat = new voJobCategory();

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
        </select>	
	</td>
  </tr>
</table>
<p></p>
<div style='width:610px; height:140px; z-index:1; overflow:auto'>
<table class="tablesetting1">
  <tr class="header">
	<td width="31">&nbsp;</td>
    <td width="37"><b>No.</b></td>
    <td width="430"><a href="JobCategory.jsp?name=1"><b><font style='color:white'><u>Job Category</u></font></b></a></td>
    <td width="100"><a href="JobCategory.jsp?name=2"><b><font style='color:white'><u>Origin</u></font></b></a></td>
  </tr>
 
<% 	

	for(int i=0; i<vJobCat.size(); i++) {
    	int j = i+1;
		voJobCat = (voJobCategory)vJobCat.elementAt(i);

		iFKJobCat = voJobCat.getPKJobCategory();
		sJobCatName 	= voJobCat.getJobCategoryName();
		sOrigin 	= voJobCat.getOrigin();
	
%>
    <tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'" bgColor = "#FFFFCC">
       <td class="tdata" align="center">
	   		<input type="radio" name="radioJobCat" value=<%=iFKJobCat%>>
	   </td>
	   	<td class="tdata" align="center"><%=j%></td>
	   <td class="tdata"><%=sJobCatName%></td>
	   <td class="tdata" align="center"><%=sOrigin%></td>
   </tr>
<% 	
	} 
%>
  </tr>
</table>
</div>
<p></p>
<input type="button" name="Add" value="Add" onclick="confirmAdd()">
<input type="button" name="btnEdit" value="Edit"  onclick = "confirmEdit(this.form, this.form.radioJobCat)">
<input type="button" name="Submit" value="Delete"  onclick = "return confirmDelete(this.form, this.form.radioJobCat)">
<% } %>
</form>
</body>
</html>