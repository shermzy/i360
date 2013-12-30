<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<html>
<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">
<meta http-equiv="Content-Type" content="text/html">

<head>

<jsp:useBean id="JobCatDetail" class="CP_Classes.JobCategoryDetail" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.voCompetency"%>

<title>Job Category</title>

</head>

<body>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
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
	if(check(field))
	{	
		form.action="AddJobCatCompetencyDetail.jsp?added=1";
		form.method="post";
		form.submit();
		return true;
		
	}	

}

function confirmCancel() {
	window.location.href = "AddJobCatCompetency.jsp";
}

function changeCat(form,field)
{
	form.action="AddJobCatCompetencyDetail.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

</script>

<%
	int DisplayNo = 1;
	int CompID = 0; 	//competencyId
	String name = "";
	
	int compID = logchk.getCompany();
	int orgID = logchk.getOrg();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	int CatID = JobCat.getJobCatID();
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = JobCatDetail.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		

	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		JobCatDetail.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		JobCatDetail.setSortType(type);									
	} 

	if(request.getParameter("proceed") != null) {
		CatID = Integer.parseInt(request.getParameter("proceed"));
		JobCat.setJobCatID(CatID);
	}
	
	if(request.getParameter("added") != null)
	{
 	    String [] chkSelect = request.getParameterValues("Comp");
		CompID = Integer.parseInt(request.getParameter("CompID"));
		if(chkSelect != null) {
			boolean bIsAdded = false;
			
			for(int i=0; i<chkSelect.length; i++) {
				if(chkSelect[i] != null)
					try {
						bIsAdded = JobCatDetail.addCompetency(CatID, Integer.parseInt(chkSelect[i]));
					} catch(SQLException SE) {}
					
			}
			
			if(bIsAdded) {
%>
		<script>
			//window.location.href = "AddJobCatCompetency.jsp?CatID=" + <%=CatID%>;
			alert("Added successfully");
			window.close();
			opener.location.href = "AddJobCatCompetency.jsp";
			
		</script>
<%					
			}
		}
	}
%>

<form name="JobCatCompetencyList" method="post">
<table border="0" width="550" cellspacing="0" cellpadding="0">
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial">Job Category</font></b></td>
    </tr>
	<tr>
	  <td colspan="3"><ul>
	    <li><font face="Arial" size="2">To Add, tick on the checkbox(es) and click on the Save button.</font></li>
		<li><font face="Arial" size="2">To assign Competency to the Job Category, select the Job Category from the dropdown.</font></li>
	    </ul></td>
    </tr>
</table>
	
  <table border="0" width="550" class="fontstyle">
    <tr>
      <td width="158">Job Category Name 
		: </td>
      <td width="10"><input name="CatID" type="hidden" value="<%=CatID%>"></td>
      <td width="430">
<%
	if(JobCat.getIsAdd() == 1) {
		JobCat.setIsAdd(0);
%>	  
   	  <select size="1" name="selJobCat" disabled>
<% } else {
%>	  
   	  <select size="1" name="selJobCat" onChange="changeCat(this.form, this.form.selJobCat)">
<%
	}
	
	Vector v = JobCat.FilterRecord(orgID);
	
	for(int i=0; i<v.size(); i++) 
	{
		voJobCategory vo = (voJobCategory)v.elementAt(i);
		int jobCatID = vo.getPKJobCategory();
		String catName = vo.getJobCategoryName();
	
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
</select>	  </td>
    </tr>
  </table>
  <p></p>

<div style='width:570px; height:280px; z-index:1; overflow:auto'>  

<table class="tablesetting1" width="550">
<th width="20" class="header">&nbsp;</th>
<th width="29" class="header"><b>No</b></th>
<th width="485" class="header"><a href="AddJobCatCompetencyDetail.jsp?name=1"><b><font style='color:white'>
<u>Competency</u></font></b></a></th>
<% 
	
	Vector vComp = JobCatDetail.getRestCompetency(CatID, orgID);
		
	for(int i=0; i<vComp.size(); i++) {
		voCompetency vo = (voCompetency)vComp.elementAt(i);
		CompID = vo.getCompetencyID();		
		name =  vo.getCompetencyName();
%>
    <tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'" bgColor = "#FFFFCC">
       <td align="center" class="tdata">
	   		<input type="checkbox" name="Comp" value=<%=CompID%>>

		</td >
	   	<td class="tdata" align="center">
   		  <%=i+1%>
   		</td>
	   <td class="tdata"><%=name%></td>
   </tr>
<% 	
	} 

%>
</table>
</div>


<p></p>
<input name="CompID" type="hidden" id="CompID" value="<%=CompID%>" size="10">
<input type = "button" name="Add" value="Save" onclick="confirmAdd(this.form, this.form.Comp)">
<input type = "button" name="Cancel" value="Cancel"  onclick = "window.close()">




</form>

</body>
</html>