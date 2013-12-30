<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rating Scale</title>

<meta http-equiv="Content-Type" content="text/html">

</head>

<body>
<jsp:useBean id="CompQuery" class="CP_Classes.Competency" scope="session" />
<jsp:useBean id="RS" class="CP_Classes.RatingScale"	scope="session" />
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<SCRIPT LANGUAGE="JavaScript">
	<!-- Begin
	
	var x = parseInt(window.screen.width) / 2 - 300;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
	var y = parseInt(window.screen.height) / 2 - 200; 
	
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
		var myWindow=window.open('AddRatingScale.jsp','windowRef','scrollbars=yes, width=500, height=400');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'AddRatingScale.jsp';
	}
	
	//void function confirmEdit(form, field) {	
	function confirmEdit(form, field) {	
		var value = check(field);
		
		if(value) {
			var myWindow=window.open('EditRatingScale.jsp','windowRef','scrollbars=yes, width=550, height=400');
			myWindow.moveTo(x,y);
			var query = "EditRatingScale.jsp?clicked=" + value;
	    	myWindow.location.href = query;
		}
	}
	
	function confirmDelete(form, field) {
		var value = check(field);
		
		if(value) {
			if(confirm('<%=trans.tslt("Delete Rating Scale")%>?')) {
				form.action = "RatingScale.jsp?delete="+value;
				form.method = "post";
				form.submit();
				return true;
			} else
				return false;
		}
	}
	
	/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
	/*	choosing organization*/
	
	function proceed(form,org,range)
	{
		form.action="RatingScale.jsp?org="+org.value+"&range=" + range.value;
		form.method="post";
		form.submit();
	}	
	
	function proceed1(form, org, range)
	{
	    range.value = 0;
	    form.action="RatingScale.jsp?org="+org.value+"&range=" + range.value;
		form.method="post";
		form.submit();
	}
	
	</script>

<%
		//response.setHeader("Pragma", "no-cache");
		//response.setHeader("Cache-Control", "no-cache");
		//response.setDateHeader("expires", 0);
	
	String username=(String)session.getAttribute("username");
	
	  if (!logchk.isUsable(username)) 
	  {%>
<font size="2"> <script>
		parent.location.href = "index.jsp";
	</script> <%  } 
	  else 
	  { 	
	
	if(request.getParameter("org") != null)
	{ 
		int PKOrg = new Integer(request.getParameter("org")).intValue();
	 	logchk.setOrg(PKOrg);
		
		int range = new Integer(request.getParameter("range")).intValue();
		RS.setRS(range);
	}
	
	/*-------------------------------------------------------------------end login modification 1--------------------------------------*/
	
	
	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	
		int toggle = RS.getToggle();	//0=asc, 1=desc
		int type = 1; //1=name, 2=origin		
				
		if(request.getParameter("name") != null)
		{	 
			if(toggle == 0)
				toggle = 1;
			else
				toggle = 0;
			
			RS.setToggle(toggle);
			
			type = Integer.parseInt(request.getParameter("name"));			 
			RS.setSortType(type);									
		} 
		
	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
		
		
	%>

<form name="RatingScale" method="post">
<table border="0" width="62%" cellspacing="0" cellpadding="0" font
	style='font-size: 10.0pt; font-family: Arial'>
	<tr>
		<td colspan="3"><b><font color="#000080" size="2"
			face="Arial"><%=trans.tslt("Rating Scale")%> </font></b></td>
	</tr>
	<tr>
		<td colspan="3">
		<ul>
			<li><font face="Arial" size="2"><%=trans.tslt("To Add, click on the Add button")%>.</font></li>
			<li><font face="Arial" size="2"><%=trans.tslt("To Edit, click on the relevant radio button and click on the Edit button")%>.</font></li>
			<li><font face="Arial" size="2"><%=trans.tslt("To Delete, click on the relevant radio button and click on the Delete button")%>.</font></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td width="90"><%=trans.tslt("Organisation")%></td>
		<td width="210"><select size="1" name="selOrg" onChange = "proceed1(this.form, this.form.selOrg, this.form.scaleRange)">
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
		<td>&nbsp;</td>
	</tr>

	<%
		int orgID = logchk.getOrg();	
		int compID = logchk.getCompany();
		
		
		int scaleRange = RS.getRS();
	
		Vector RScale = null;
		String origin = "";
		
		RScale = RS.FilterRecord(scaleRange, compID, orgID);	
	
	%>

	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td align="center"><input type="button"
			value="<%=trans.tslt("Show")%>" name="btnShow"
			onclick="proceed(this.form,this.form.selOrg, this.form.scaleRange)"></td>
	</tr>
	<tr>
		<% int t=0; %>
		<td><%=trans.tslt("Scale Range")%>:</td>
		<td><select name="scaleRange">
			<option value=<%=t%>><%=trans.tslt("All")%> <%
				for(int range = 3; range <= 10; range++) {
					if(scaleRange != 0 && scaleRange == range) {
			%>
			
			<option value=<%=range%> selected><%=range%> <% } else {
			%>
			
			<option value=<%=range%>><%=range%> <%
				}
				}
			%>
			
		</select></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="90">&nbsp;</td>
		<td width="210">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<p></p>

<%
		int DisplayNo = 1;	
		
		
	%>



<div style='width: 610px; height: 200px; z-index: 1; overflow: auto;'>
<table border="1" bgcolor="#FFFFCC"
	style='border-color: #3399FF; font-size: 10.0pt; font-family: Arial'>
	<th width="24" bgcolor="navy" bordercolor="#3399FF">&nbsp;</th>
	<th width="21" bgcolor="navy" bordercolor="#3399FF"><b><font
		style='font-size: 10.0pt; font-family: Arial; color: white'> No</font></b></th>
	<th width="600" bgcolor="navy" bordercolor="#3399FF"><a
		href="RatingScale.jsp?name=1"><b><font
		style='font-size: 10.0pt; font-family: Arial; color: white'><u><%=trans.tslt("Scale Description")%></u></font></b></a></th>
	<th width="100" bgcolor="navy" bordercolor="#3399FF"><a
		href="RatingScale.jsp?name=2"><b><font
		style='font-size: 10.0pt; font-family: Arial; color: white'><u><%=trans.tslt("Scale Type")%></u></font></b></a></th>
	<th width="70" bgcolor="navy" bordercolor="#3399FF"><a
		href="RatingScale.jsp?name=3"><b><font
		style='font-size: 10.0pt; font-family: Arial; color: white'><u><%=trans.tslt("Default")%></u></font></b></a></th>
	<th bgcolor="navy" width="100" bordercolor="#3399FF"><a
		href="RatingScale.jsp?name=4"><b><font
		style='font-size: 10.0pt; font-family: Arial; color: white'><u><%=trans.tslt("Origin")%></u></font></b></a></th>

	<% 	
		/********************
		* Edited by James 02 Nov 2007
		************************/	
		//while(RScale.next()) {
		for(int i=0; i<RScale.size(); i++) {
		votblScale vo=(votblScale)RScale.elementAt(i);
			//int ScaleID = RScale.getInt("ScaleID");
			//String scaleDesc =  RScale.getString("ScaleDescription");
			//String scaleType = RScale.getString("ScaleType");
			//int defaultValue = RScale.getInt("ScaleDefault");
			//origin = RScale.getString("Description");
			int ScaleID = vo.getScaleID();
			String scaleDesc = vo.getScaleDescription();
			String scaleType = vo.getScaleType();
			int defaultValue = vo.getScaleDefault();			
			origin = vo.getDescription();
	%>
	<tr onMouseOver="this.bgColor = '#99ccff'"
		onMouseOut="this.bgColor = '#FFFFCC'">
		<td bordercolor="#3399FF"><input type="radio" name="radioEdit"
			value="<%=ScaleID%>"></td>
		<td align="center" bordercolor="#3399FF">
		<% out.println(DisplayNo); %>
		</td>
		<td bordercolor="#3399FF">
		<% out.println(scaleDesc); %>
		</td>
		<td align="center" bordercolor="#3399FF">
		<% out.println(scaleType); %>
		</td>
		<td align="center" bordercolor="#3399FF">
		<% if(defaultValue == 1) {
		   %> <input type="checkbox" name="defaultValue"
			value="<%=defaultValue%>" checked disabled> <% } else {
			%> <input type="checkbox" name="defaultValue"
			value="<%=defaultValue%>" disabled> <% }
			%>
		</td>
		<td align="center" bordercolor="#3399FF">
		<% out.println(origin); %>
		</td>
	</tr>
	<% 	DisplayNo++;
		} 
	
		}
	%>
</table>
</div>

<p></p>
<input type="button" name="btnAdd" value="<%=trans.tslt("Add")%>"
	onclick="confirmAdd()"> <input type="button" name="btnEdit"
	value="<%=trans.tslt("Edit")%>"
	onclick="confirmEdit(this.form, this.form.radioEdit)"> <input
	type="button" name="btnDelete" value="<%=trans.tslt("Delete")%>"
	onclick="return confirmDelete(this.form, this.form.radioEdit)">
</form>

<% 	
	
	if(request.getParameter("delete") != null)
	{
	int pkUser = logchk.getPKUser();
			int ScaleID = Integer.parseInt(request.getParameter("delete"));
			int check = RS.CheckSysLibRatingScale(ScaleID);
			
			int userType = logchk.getUserType();	// 1= super admin
			if((userType == 1) || (check == 0)) 
			{
			try 
			{
				boolean del = RS.deletetblScale(ScaleID, pkUser);			
				if(del) 
				{
				%> <script>
						alert("Deleted successfully");
						window.location.href = 'RatingScale.jsp';
				 </script> 
	
	<%-- Change by Ha on 15/04/08: if there is sql exception , it is not the RatingScale.deleteblScale
	 that throws it. Therefore, it will never goes to the catch block. --%> 
	    		<% } 
				else if (del == false) 
				{%>
			 	<script>
						alert("Deletion cannot be performed. Data is being used!");			
				</script>  <%
				}
			} catch(SQLException SE) 
			
			{			
			%> <script>
				alert("Deletion cannot be performed. Data is being used!");			
			</script> <%	
			}
			
			}else if((userType != 1) && check == 1)
			{
			%> <script>
				alert("You are not allowed to delete System Generated Libraries!");
				//window.location.href = "Competency.jsp";
			</script> <%	
			}		
	  }
	%>

<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>
