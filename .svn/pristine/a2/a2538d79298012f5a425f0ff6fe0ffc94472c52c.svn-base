<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*,CP_Classes.vo.*," %>

<html>
<head>

<jsp:useBean id="RatingTask" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<title>Rating Task</title>

<meta http-equiv="Content-Type" content="text/html1">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 125;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

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
//void function confirmAdd(form) {
function confirmAdd(form) {
	var myWindow=window.open('AddRT.jsp','windowRef','scrollbars=no, width=480, height=230');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'AddRT.jsp';
}

//void function confirmEdit(form, field) {	
function confirmEdit(form, field) {	
	var value = check(field);
	
	if(value)
	{		
		var myWindow=window.open('EditRT.jsp','windowRef','scrollbars=yes, width=480, height=230');
		var query = "EditRT.jsp?clicked=" + value;
		myWindow.moveTo(x,y);
    	myWindow.location.href = query;
	}

}

function confirmDelete(form, field) {
	var value = check(field);
	
	if(value)
	{	
		if(confirm('<%=trans.tslt("Delete Rating Task")%>?')) {
			form.action = "RatingTask.jsp?delete="+value;
			form.method = "post";
			form.submit();
			return true;
		} else
			return false;
	}
}

</script>


<%	
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = RatingTask.getToggle();	//0=asc, 1=desc
	int type = RatingTask.getSortType(); //1=name, 2=origin		
	int pkUser = logchk.getPKUser();
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		RatingTask.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		RatingTask.setSortType(type);									
	} 
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	



	int DisplayNo;
	int RTID = 0;		// Rating task ID 
	String RTCode = "";		// Rating Code
	String ratingTask = "";
	String scaleType = "";
	DisplayNo = 1;
	int userType = logchk.getUserType();	// 1= super admin
	
	Vector aResult = RatingTask.getAllRecord();
%>

<form name="RatingTaskList" method="post">

<table width="58%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3"><b><font color="#000080" size="2" face="Arial"><%=trans.tslt("Rating Task")%> </font></b></td>
  </tr>
  <% if(userType == 1) {
  %>
  <tr>
    <td colspan="3"><ul>
        <li><font face="Arial" size="2"><%=trans.tslt("To Add, click on the Add button")%>.</font></li>
        <li><font face="Arial" size="2"><%=trans.tslt("To Edit, click on the relevant radio button and click on the Edit button")%>.</font></li>
        <li><font face="Arial" size="2"><%=trans.tslt("To Delete, click on the relevant radio button and click on the Delete button")%>.</font></li>
    </ul></td>
  </tr>
  <%
  }
  %>
</table>

&nbsp;

<table border="1" bgcolor="#FFFFCC" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' width="610">
<% if(userType == 1) {
		%>
<th width="20" bgcolor="navy" bordercolor="#3399FF">&nbsp;</th>
		<% }
		%>	
<th width="20" bgcolor="navy" bordercolor="#3399FF"><b><font style='font-family:Arial;color:white'>
No</font></b></th>
<th width="309" bgcolor="navy" bordercolor="#3399FF"><a href="RatingTask.jsp?name=1"><b><font style='font-family:Arial;color:white'><u><%=trans.tslt("Rating Task")%></u></font></b></th>
<th width="124" bgcolor="navy" bordercolor="#3399FF"><a href="RatingTask.jsp?name=2"><b><font style='font-family:Arial;color:white'><u><%=trans.tslt("Code")%></u></font></b></th>
<th width="129" bgcolor="navy" bordercolor="#3399FF"><a href="RatingTask.jsp?name=3"><b><font style='font-family:Arial;color:white'><u><%=trans.tslt("Scale Type")%></u></font></b></th>

<% 	
	/********************
	* Edited by James 02 Nov 2007
	************************/
	//while(aResult.next()) {
	for(int i=0; i<aResult.size(); i++) {
	votblRatingTask vo=(votblRatingTask)aResult.elementAt(i);
		//RTID = aResult.getInt(1);		
		//RTCode =  aResult.getString(2);
		//ratingTask = aResult.getString(3);
		//scaleType = aResult.getString(4);
		RTID = vo.getRatingTaskID();		
		RTCode = vo.getRatingCode();
		ratingTask = vo.getRatingTask();
		scaleType = vo.getScaleType();
%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFCC'">
		<% if(userType == 1) {
		%>
		 <td bordercolor="#3399FF">
	   		<input type="radio" name="checkRT" value=<%=RTID%>>
		 </td>
		<% }
		%>	
	   	<td align="center" bordercolor="#3399FF">
   		  <% out.print(DisplayNo); %>
   		</td>
	   <td bordercolor="#3399FF"><% out.print(ratingTask);%>
       </td>
	   <td align="center" bordercolor="#3399FF"><% out.print(RTCode);%>
       </td>
	   <td align="center" bordercolor="#3399FF"><% out.print(scaleType);%>
       </td>
   </tr>
<% 	DisplayNo++;
	} 

%>
</table>
<p></p>

<%
if(userType == 1) {
%>
<table width="261" border="0">
  <tr>
    <td width="41"><input type="submit" name="btnAdd" value="<%=trans.tslt("Add")%>" onclick="confirmAdd(this.form)"></td>
    <td width="40"><input type="submit" name="btnEdit" value="<%=trans.tslt("Edit")%>" onclick = "confirmEdit(this.form, this.form.checkRT)"></td>
    <td width="249"><input type="submit" name="btnDelete" value="<%=trans.tslt("Delete")%>" onclick = "return confirmDelete(this.form, this.form.checkRT)"></td>
  </tr>
</table>
<%	
}
%>
<%
	if(request.getParameter("delete") != null) {
		int pkDRA = Integer.parseInt(request.getParameter("delete"));

		try {
			boolean delete = RatingTask.deleteRecord(pkDRA, pkUser);			
			
			if(delete) {
%>
		<script>
			alert("Deleted successfully");
			window.location.href = "RatingTask.jsp";
		</script>
<%
			}
	} catch(SQLException SE) {
%>
		<script>
			alert("<%=trans.tslt("Deletion cannot be performed. Data is being used")%> !");
			window.location.href = "RatingTask.jsp";
		</script>
<%	
	}

	}
	}
%>
</form>

<table border="0" width="610" height="26">

	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>

	<tr>
		<td align="center" height="5" valign="top"></td></tr>

	<tr>
	<p></p>
	<%@ include file="Footer.jsp"%>
</table>

</body>
</html>