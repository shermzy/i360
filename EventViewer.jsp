<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
				 CP_Classes.vo.votblEvent,
                 java.lang.String"%>  
				 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Event Viewer</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<jsp:useBean id="EV" class="CP_Classes.EventViewer" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="ExcelEvent" class="CP_Classes.ExcelEventViewer" scope="session"/>  
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>   
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<body>
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/dt_ex.js"></script>
<script language="javascript">

//Gwen Oh - 22/09/2011: Added trim function
function trim(str)
{
    return str.replace(/^\s*/, "").replace(/\s*$/, "");
}

// Gwen Oh - 20/09/2011: Added javascript functions to validate date
var c1 = new CodeThatCalendar(caldef2);
var c2 = new CodeThatCalendar(caldef3);

function validateDates()
{
	var checkingFlag = true;
	x = document.AdvanceSettings
	if(checkdate(x.fromDateTxt,x.toDateTxt) == false)
	{
		checkingFlag = false;
	}
	
	return checkingFlag;
}

function checkdate(fromDate, toDate) {
	
	if (fromDate != "") {
		if (isDate(fromDate) == false) {
			alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
			return false;
		}
   	}
   	
   	if (toDate != "") {
		if (isDate(toDate) == false) {
			alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
			return false;
		}
	}
	
	if (fromDate != "" && toDate !="") {
		var correct;   
		if(doDateCheck(fromDate,toDate))
		{
			correct = true;
		} else {
		   	correct = false;
		}
		return correct;
	}
	else {
		return true;
	}
}

function isDate(fld) {
    var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;
    var errorMessage = 'Please enter valid date as month, day, and four digit year.\nYou may use a slash, hyphen or period to separate the values.\nThe date must be a real date. 30/2/2000 would not be accepted.\nFormay dd/mm/yyyy.';
    if ((fld.match(RegExPattern)) && (fld!='')) {
       return true;
    } else {
		return false;
    } 
}

function doDateCheck(opendate, closedate) {
	var tmpopendate = opendate.substring(3,5) +"/" + opendate.substring(0,2) + "/" + opendate.substring(6,10);
	var tmpclosedate = closedate.substring(3,5) +"/" + closedate.substring(0,2) + "/" + closedate.substring(6,10);
	
	if (Date.parse(tmpopendate) <= Date.parse(tmpclosedate)) {
		return true;
	}
	else {
		//if (opendate == "" || closedate == "") 
		//alert("<%=trans.tslt("Both dates must be entered")%>.");
		//else 
		alert("<%=trans.tslt("First date must be earlier than the second date")%>.");
		return false;
   }
}


function check(field)
{
	var isValid = 0;

	//check whether any checkbox selected
	
	for (i = 0; i < field.length; i++) 
		if(field[i].checked)
			isValid = 1;
		
	if(isValid == 0 && field != null)  
		if(field.checked) {
			clickedValue = field.value;
			isValid = 1;
		}
	
	if (isValid == 1)
		return true;
	else
		return false;
}
 
function checkedAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}
 
function confirmDelete(form, field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Are you sure you want to delete the records")%> ?"))
		{
			//modified link for deleting event to include query string org of the selected org, to ensure that after the event(s) are deleted, the page refreshes and shows the events of the organisation selected, Sebastian, 29 July 2010
			//Gwen Oh - 26/09/2011: Modified link to include dates
			form.action="EventViewer.jsp?delete=1&org=<%out.print(request.getParameter("org"));%>&fromDate=<%out.print(request.getParameter("fromDate"));%>&toDate=<%out.print(request.getParameter("toDate"));%>";
			form.method="post";
			form.submit();
			return true;
		}else
			return false;		
	}
	else
	{
		alert("<%=trans.tslt("No record selected")%>");
		return false;
	}

} 

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function compChange(form, field)
function compChange(form, field)
{
	form.action="EventViewer.jsp?comp=" + field.value;
	form.method="post";
	form.submit();
} 

//void function orgChange(form, field)
function orgChange(form, field)
{	
	//Gwen Oh - 22/09/2011: This function will now have to validate dates as well. So it will be the same as the show function.
	show (form, field);
	//form.action="EventViewer.jsp?org=" + field.value;
	//form.method="post";
	//form.submit();
} 

//Gwen Oh - 20/09/2011: Added show function
function show (form,field)
{
	var fromDate = trim(document.getElementById('fromDateTxt').value);
	var toDate = trim(document.getElementById('toDateTxt').value);
	var toSubmit = true;
	
	//No dates defined
	if (fromDate == "" && toDate == "")
		form.action="EventViewer.jsp?fromDate=" + fromDate + "&toDate=" + toDate +"&org=" + field.value;
	else {
		//Check that dates are valid
		if(checkdate(fromDate, toDate))
			form.action="EventViewer.jsp?fromDate=" + fromDate + "&toDate=" + toDate +"&org=" + field.value;
		else
			toSubmit = false;
	}
	
	if (toSubmit) {
		form.method="post";
		form.submit();
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
  //Check if any records need to be deleted
if(request.getParameter("delete") != null) {
 	    String [] chkSelect = request.getParameterValues("eventID");
		
		if(chkSelect != null) {
			for(int i=0; i<chkSelect.length; i++) {
				if(chkSelect[i] != null) {
					System.out.println("CHK BOX: " + Integer.parseInt(chkSelect[i]));
					EV.deleteRecord(Integer.parseInt(chkSelect[i]));
				}
			}
%>
		<script>
			alert("<%=trans.tslt("Record(s) Deleted")%>");
			
			//modified href to include query string org of the selected org, to ensure that after the event(s) are deleted, the page refreshes and shows the events of the organisation selected, Sebastian, 29 July 2010
			//Gwen Oh - 26/09/2011: Modified link to include dates
			window.location.href = "EventViewer.jsp?org=<%out.print(request.getParameter("org"));%>&fromDate=<%out.print(request.getParameter("fromDate"));%>&toDate=<%out.print(request.getParameter("toDate"));%>";
			
		</script>
<%					
				
		}
	}


/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
%>
<script>
	alert("<%=trans.tslt("As there are many records, this operation may take a while")%>");
</script>
<%
	String fromDate = "";
	String toDate = "";
	
	if (request.getParameter("fromDate")!=null && !request.getParameter("toDate").equals("null")) 
		fromDate = request.getParameter("fromDate");
	
	if (request.getParameter("toDate")!=null && !request.getParameter("toDate").equals("null"))
		toDate = request.getParameter("toDate");
	int toggle = EV.getToggle();	//0=asc, 1=desc
	int type = 7; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		EV.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		EV.setSortType(type);									
	} 
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	String deletedDate = "";
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
	int userType = logchk.getUserType();	// 1= super admin
	int pkUser = logchk.getPKUser();
	
	//Modified the assignment of compName value by adding an IF condition, ensure that the company selected will not revert to default when the page reloads, Sebastian, 29 July 2010 
	String compName = "";
	
	//if user never select any organisation, reset the comp name and org name to the defaults, Sebastian, 05 July 2010
	if (request.getParameter("org") == null && request.getParameter("comp") == null && request.getParameter("name") == null) 
	{
		//Modified to check if user is sa and which consulting company is selected, To ensure that the events returned are from the consulting company selected, Sebastian, 05 July 2010
		if (userType == 1) // 1 = sa
		{
			compName = EV.getCompNameByCompID(logchk.getCompany());
		}
		else
		{
			compName = EV.companyName(pkUser);
		}
		//
		EV.setCompName(compName);
		EV.setOrgName("All");
	}		
	//
	
	compName = EV.getCompName();	
	String orgName = EV.getOrgName();	
	
	//ResultSet rsEvent = null;
	//ResultSet rsCompany = EV.getCompanies(orgName);
	//ResultSet rsOrg = null;
	Vector rsEvent = null;
	Vector rsCompany = EV.getCompanies(orgName);
	Vector rsOrg = null;
	
	if(request.getParameter("comp") != null) {
		compName = request.getParameter("comp").trim();

		rsOrg = EV.getOrganization(compName);
		EV.setCompName(compName);
		
		//Added code to set org name to all, To ensure that when comp name changes, all events from the organisations in the changed comp will be shown, Sebastian, 29 July 2010
		EV.setOrgName("All");
	}
	else
		rsOrg = EV.getOrganization(compName);
	/*
	* Change(s) : Added new variable UserDetailtmp and IF conditions using UserDetailtmp
	* Reason(s) : To get user's organisation and fix the issue of user who is not sa being able to view events in other organisation
	* Updated By: Sebastian
	* Updated On: 29 July 2010
	*/
	String [] UserDetailtmp = new String[16];
	UserDetailtmp = CE_Survey.getUserDetail(logchk.getPKUser());
		
	if(request.getParameter("org") != null) {
		orgName = request.getParameter("org").trim();
		EV.setOrgName(orgName);
	}
	else{
		//added an IF condition, Ensure that retrieving companies is only done when user is sa, Sebastian, 29 July 2010
		if (userType == 1)
		{
			rsCompany = EV.getCompanies(orgName);
		}
		else
		{
			//added an IF condition, fix the issue where events of other organisations can be seen if user is not sa, Sebastian, 29 July 2010
			if (!UserDetailtmp[10].equals(orgName))
			{
				EV.setOrgName(UserDetailtmp[10]);
			}
			//
		}//End IF condition
	}
	
	compName = EV.getCompName();
	orgName = EV.getOrgName();

	//Branched an IF condition to get event records based on the user type, If user is not SA or not a consulting company, the ALL selection of the org should only return events of the user organisation, Sebastian, 29 July 2010
	//Gwen Oh - 22/09/2011: Changed method call to getAllRecords() according to the changes made to the parameters in EventView.java
	if (logchk.getUserType() == 1 || (logchk.getUserType() == 2 && Org.isConsulting(UserDetailtmp[10]) == true))
	{
			rsEvent = EV.getAllRecords(compName, orgName, fromDate, toDate, logchk.getUserType());
	}else if (logchk.getUserType() == 2 && Org.isConsulting(UserDetailtmp[10]) == false)
	{	
			rsEvent = EV.getAllRecords(compName, UserDetailtmp[10], fromDate, toDate, logchk.getUserType());
	}
	//End IF condition
	
	String t = "All";

	if(request.getParameter("print") != null) {		
		
		Date timeStamp = new java.util.Date();
		
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "EventViewer" + temp + ".xls";
	

		ExcelEvent.WriteToReport(compName, orgName, pkUser, logchk.getUserType(), file_name);	


	String output = setting.getReport_Path() + "\\"+file_name;
	
	File f = new File (output);

	response.reset();
	//set the content type(can be excel/word/powerpoint etc..)
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"EventViewer.xls\"");

		
	//get the file name
	String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
	//OPen an input stream to the file and post the file contents thru the 
	//servlet output stream to the client m/c
	
		InputStream in = new FileInputStream(f);
		ServletOutputStream outs = response.getOutputStream();
		
		
		int bit = 256;
		int i = 0;


    		try {
        			while ((bit) >= 0) {
        				bit = in.read();
        				outs.write(bit);
        			}
        			//System.out.println("" +bit);

            		} catch (IOException ioe) {
            			//System.out.print("TEST" + ioe.getMessage());
%>
<script>
	form.action = "EventViewer.jsp?print=1";
	form.submit();
</script>
<%						
            		}
            		outs.flush();
            		outs.close();
            		in.close();	
}
	



	
%>	
	

<form name="form1" method="post" action="">
<table width="833" border="0" font style='font-size:10.0pt;font-family:Arial'>
<%	
	if(userType == 1) {
%>
  <tr>
    <td width="93" align="left"><%= trans.tslt("Company") %>:</td>
    <td width="13">&nbsp;</td>
    <td width="429">
		<select name="Company" onChange="compChange(this.form, this.form.Company)">
		<option value=<%=t%>>All
        <%
		
	/********************
	* Edited by James 29 Oct 2007
	************************/
	
			//while(rsCompany.next()) {
			for(int i=0; i<rsCompany.size(); i++) {
			votblEvent voCompany= (votblEvent)rsCompany.elementAt(i);
				String name = voCompany.getCompanyName();
			
				if(name.equals(compName)) { %>
					<option value = "<%=name%>" selected><%=name%>
				<% }else { %>
					<option value = "<%=name%>"><%=name%>
				<% } 	// End of if statements
			}			// End of for loop
		%>
		</select>
	</td>
    <td width="280">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <%	}	// End of if statement for userType = 1 %> 
  <tr>   
    <td align="left" width="93"><%= trans.tslt("Organisation") %>:</td>
	<td width="13">&nbsp;</td>
    <td width="429">
		<select name="Organization" onChange="orgChange(this.form, this.form.Organization)">
		<option value=<%=t%>>All
        <%
				
	/********************
	* Edited by James 29 Oct 2007
	************************/
    	// Added to check whether organisation is also a consulting company
    	// if yes, will display a dropdown list of organisation managed by this company
    	// else, it will display the current organisation only
    	// Mark Oei 
    	String [] UserDetail = new String[14];
    	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
    	boolean isConsulting = true;
    	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
    	if (isConsulting){
			//while(rsOrg.next()) {
			for(int i=0; i<rsOrg.size(); i++) {
			votblEvent voOrg = (votblEvent)rsOrg.elementAt(i);
				//int ID = rsOrg.getInt("PKOrganization");
				String name = voOrg.getOrganizationName();
				//System.out.println(name);
				
				if(name.equals(orgName)) {
		%>
        <option value = "<%=name%>" selected><%=name%>
        <%
				}else {
		%>
        <option value = "<%=name%>"><%=name%>
        <%
				}
			}			
    	} else { 
    	//Added an IF condition when displaying the dropdown list of organisation, Fix the issue where organisation selected does not remain selected after the page is submitted and returned, Sebastian, 29 July 2010
			if (orgName.equals(UserDetail[10]))
			{
				%>
				<option value="<%=UserDetail[10]%>" selected><%=UserDetail[10]%></option><%
			}else {
				%>
				<option value="<%=UserDetail[10]%>"><%=UserDetail[10]%></option><% 
			}
		// End IF condition
		} // End of isConsulting %>
		</select>
	</td>    
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
  <!--
  Gwen Oh - 20/09/2011: Added option to filter by date range and added a show button
  -->
  <tr>
  	<td align="left" width="93">Date Range:</td>
  	<td width="13">&nbsp;</td>
  	<td width="429">
  	<input type="text" size="12" id="fromDateTxt" value='<%=fromDate%>' />
  	<img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onClick="c1.popup('fromDateTxt')" />
  	&nbsp;to&nbsp;
  	<input type="text" size="12" id="toDateTxt" value='<%=toDate%>' />
  	<img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onClick="c2.popup('toDateTxt')" />
  	</td>
  	<td align="left"><input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onClick="show(this.form, this.form.Organization)"></td>
  </tr>
</table>

<p></p>
<%
	if(userType == 1) {
%>
<div style='width:610px; height:230px; z-index:1; overflow:auto;'>  
<%
	}else {
%>
<div style='width:610px; height:278px; z-index:1; overflow:auto'>  
<%	}
%>
<table border="1" bgcolor="#FFFFCC" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' width="593">
<%
	if(userType == 1) {
%>
<th bordercolor="#3399FF" bgcolor="navy"><input type="checkbox" name="checkAll" value="checkbox" onClick="checkedAll(this.form, this.form.eventID, this.form.checkAll)"></th>
<% } %>
<th bgcolor="navy" bordercolor="#3399FF"><b>
<font style='font-family:Arial;color:white'>No</font></b></th>
<th bgcolor="navy" bordercolor="#3399FF"><a href="EventViewer.jsp?name=1"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Action") %></u></font></b></a></th>
<th bgcolor="navy" bordercolor="#3399FF"><a href="EventViewer.jsp?name=2"><b><font style='font-family:Arial;color:white'><u><%= trans.tslt("Item") %></u></font></b></a></th>
<th bgcolor="navy" bordercolor="#3399FF"><a href="EventViewer.jsp?name=3"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Description") %></u></font></b></a></th>
<th bgcolor="navy" bordercolor="#3399FF"><a href="EventViewer.jsp?name=4"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Login Name") %></u></font></b></a></th>
<th bgcolor="navy" bordercolor="#3399FF"><a href="EventViewer.jsp?name=7"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Action Date") %></u></font></b></a></th>

<%
	int displayNo = 0;
	/********************
	* Edited by James 29 Oct 2007
	************************/
	//while(rsEvent.next()) {	
	for(int i=0; i<rsEvent.size(); i++) {
	 votblEvent voEvent = (votblEvent)rsEvent.elementAt(i);
		displayNo++;
		int eventID = voEvent.getEventID();	
%>
<tr>
<%
	if(userType == 1) {
%>
    <td bordercolor="#3399FF" align="center"><input name="eventID" type="checkbox" value="<%=eventID%>"></td>
<% } %>	
	<td align="center" bordercolor="#3399FF"><font size="2"><%=displayNo%></td>
    <td bordercolor="#3399FF"><%=voEvent.getEventAction()%></td>
	<td bordercolor="#3399FF"><%=voEvent.getItem()%></td>
    <td bordercolor="#3399FF"><%=voEvent.getDescription()%></td>
    <td align="center" bordercolor="#3399FF"><%=voEvent.getLoginName()%></td>
	<td align="center" bordercolor="#3399FF"><%=day_view.format(voEvent.getDeletedDate())%></td> 	
</tr>

<% } %>
</table>
</div>


<p></p>
<%
	if(userType == 1) {
%>

<input type="button" name="Delete" value="<%= trans.tslt("Delete") %>" onClick="confirmDelete(this.form, this.form.eventID)">
<% } %>
<input type="submit" name="print" value="<%= trans.tslt("DOWNLOAD") %>">
</form>

<% } %>

<p></p>
<%@ include file="Footer.jsp"%>

</body>
</html>
