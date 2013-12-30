<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*,CP_Classes.vo.*,java.util.*," %>
<%//by Hemilda 23/09/2008 fix import after adding UTF-8%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<jsp:useBean id="mail" class="CP_Classes.MailGenerator" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>   
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<title>Sent Failed Emails</title>

</head>

<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<body>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	
	for (i = 0; i < field.length; i++) 
		if(field[i].checked) {		
			clickedValue = field[i].value;
			field[i].checked = false;
			isValid = 1;
		}
		
	if(isValid == 0 && field != null)  {
		if(field.checked) {
			clickedValue = field.value;
			isValid = 1;
		}
	}
	
	if (isValid == 1)
		return clickedValue;
	else
		alert("<%=trans.tslt("No record selected")%>!");
		
	isValid = 0;	
	
}

function checkAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmSendEmail(form) {
function confirmSendEmail(form) {
	//Added by junwei on 3 March 2008
	var getCheckBox = document.getElementsByName("chkEmail");
	var checkSelected = false;
	
	for(i = 0; i < getCheckBox.length; i++){
		if(getCheckBox[i].checked)
			checkSelected = true;
	}//end of for loop
	
	if(checkSelected == true){
		form.action = "SentFailedEmail.jsp?action=1";
	    form.method = "post";	
		if (validate())
			form.submit();
	}//end of if loop
	else
		alert("Please select email to be sent");
}

//Edit By James 02-June 2008
function showAll(form) {
	form.txtRecord1.value=form.txtRecord2.value;
	form.submit();
}
//End of showAll
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmDelete(form) {
function confirmDelete(form) {
	//Added by junwei on 3 March 2008
	var getCheckBox = document.getElementsByName("chkEmail");
	var checkSelected = false;
	for(i = 0; i < getCheckBox.length; i++){
		if(getCheckBox[i].checked)
			checkSelected = true;
	}
	if(checkSelected == true){
		form.action = "SentFailedEmail.jsp?action=2";
		form.method = "post";
		if (validate())
			form.submit();
	}
	else
		alert("No record selected");
}

function validate()
{
	//Check whether txtRecord1 is numeric
	if(isNumericValue(window.document.SentFailedEmail.txtRecord1.value)){
		if(window.document.SentFailedEmail.txtRecord1.value==0){
		alert("Minimum number is 1.");
		}else
		return true;
	  }else{
	  alert("Please enter integer number only");
	  }
	return false; //else
}



function isNumericValue(str) {
	var isValid = true;
	//using regular expression to search for string existence
	//Edit By James 2-May 2008 Only integer number is allowed
	if(str.search(/^\d+$/) == -1) 
		isValid = false;
	
	return isValid;
}

/*------------------------------------------------------------start: Login modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="SentFailedEmail.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	
</script>


<%	
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);
	
//Edited by Roger 15 July 2008 
//Remove redundant variable added earlier on
/**
int orgId = 0;
String orgStr = request.getParameter("selOrg");
if (orgStr != null) {
	orgId = new Integer(orgStr).intValue();
}
**/

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

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

%>


<%
//Shifted by James 02-June 2008 
	int DisplayNo;

	String EmailID, SenderEmail, ReceiverEmail, Header, Content, Log;
	int userType = logchk.getUserType();
	
	DisplayNo = 1;

	Vector rst = null;	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
	
	if(request.getParameter("action") != null)
	{
	
		String[] chkSelect = request.getParameterValues("chkEmail");		//Get selected emails and store into array
		
		if(chkSelect != null) 
		{
			String EmailIDs = global.putArrayListToString(chkSelect);

			switch(Integer.parseInt(request.getParameter("action")))
			{
				case 1 : //Send Email(s)
					System.out.println(EmailIDs);
					boolean bIsSent=mail.sendFailedEmail(EmailIDs, pkUser);
					if(bIsSent){
					%>
					<script language = javascript>
						alert("<%=trans.tslt("Email(s) Sent Successfully")%>");
					</script>
					<%
					}else{
					%>
					<script language = javascript>
						alert("<%=trans.tslt("Email(s) Sent Fail")%>");
					</script>	
					<%
					}
					break;
					
				case 2 : //Delete
					mail.delSentFailedEmail(EmailIDs, pkUser, 2); //delOption = 2, to delete multiple emails
					%>
					<script language = javascript>
						alert("<%=trans.tslt("Email(s) Deleted Successfully")%>");
					</script>
					<%
					break;
			}
		}
	}
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = mail.getToggle();	//0=asc, 1=desc
	int sortBy = 0; //EmailID
	String sortType = "ASC"; //toggle=0=ASC, toggle=1=DESC. default "ASC"
	
	if(request.getParameter("sortBy") != null)
	{	 
		if(toggle == 0)
		{	
			toggle = 1;
			sortType = "DESC";
		}
		else
		{
			toggle = 0;
			sortType = "ASC";
		}
		mail.setToggle(toggle);		
		
		sortBy = Integer.parseInt(request.getParameter("sortBy"));	//Set sortBy, get from global function
	} 
	
//End of Shifting (James 02-June 2008)
%>



<form name="SentFailedEmail" method="post" action="SentFailedEmail.jsp" onSubmit="return validate()">
<table class="tablesetting">
	<tr>
	  <td colspan="3" height="1"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Sent Failed Email") %></font></b></td>
    </tr>
	<tr>
	  <td colspan="3" height="73"><ul>
	    <li><font face="Arial" size="2">
	    <%= trans.tslt("To Send Email, click on the relevant checkbox(es) and click on the Send Email button")%>.</font></li>
	    <br><%= trans.tslt("Note")%>: <%= trans.tslt("Email will be deleted once sent")%>
	    <li><font face="Arial" size="2">
	    <%= trans.tslt("To Delete, click on checkbox(es) and click on the Delete button")%>.</font></li>
      </ul></td>
    </tr>
	<tr>
		<td><font face="Arial" size="2"><%= trans.tslt("Organisation") %>:</font></td>
		<td><select size="1" name="selOrg" onChange="proceed(this.form,this.form.selOrg)">
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	int selectedOrg = 0;
	if (isConsulting){
		Vector vOrg = logchk.getOrgList(logchk.getCompany());
		//Edited by Roger 1 July 2008 (Revision 1.6)
		//Add a new attribute selectedOrg to keep track of the org being choose for the drop down list
		//to fix problem where email is not displayed properly when clicking the show button

		for(int i=0; i<vOrg.size(); i++)
		{
			votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
			int PKOrg = vo.getPKOrganization();
			String OrgName = vo.getOrganizationName();
	
			if(logchk.getOrg() == PKOrg) { 
				selectedOrg = PKOrg; %>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
			<%	} else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		}
	} else { 
		selectedOrg = logchk.getSelfOrg(); %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting

	int TotRecords = mail.getTotSentFailedEmailByOrgId(selectedOrg); //Get Total Records in DB
	int TotDisplay = 10; //Set Total Records to be displayed
	
	System.out.println(TotRecords);
	
	if(TotRecords < 10){//Added by ping yang on 30/7/08 to enable alert when input out of range
		TotDisplay = TotRecords;
	}
	
	if(request.getParameter("txtRecord1") != null)
	{
		TotDisplay = Integer.parseInt(request.getParameter("txtRecord1"));
		if(Integer.parseInt(request.getParameter("txtRecord1")) <= 0)
		TotDisplay = 0; //Display All 
	}
	
	if (TotDisplay > TotRecords) {//Added by ping yang on 30/7/08 to enable alert when input out of range
		//Changed by Liu Taichen on 9 May 2012.  The number displayed in the prompt is corrected to the total number of mails
		%><script>alert("Please enter digit within range" + <%=TotRecords%>);</script><%	
		TotDisplay = TotRecords; //Set TotDisplay to maximum records	
		
	}
	
	if(request.getParameter("proceed") != null) {
		TotDisplay = TotRecords;
	}
	%>
	
	
</select></td>
		<td align="center" height="21">&nbsp;</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td height="19"></td>
	</tr>
	<tr>
		<td valign="top"><b>Displaying</b></td>
		<td valign="top" height="38" width="261">
		<input type="text" name="txtRecord1" size="10" value="<%=TotDisplay%>"> <b>
		of</b>
   		<input type="text" name="txtRecord2" size="10" readonly value="<%=TotRecords%>"> <b>
		Records</b></td>
		<td valign="top" height="38" width="228">
		<font size="2">
		<p align="center">
		<input type="submit" name="cmdShow" value="<%= trans.tslt("Show") %>" style="float: left">
		<%
		//Edit By James 02-June 2008 add a button to show all records
		%>
		<input type="button" name="cmdShowAll" value="<%= trans.tslt("Show All") %>" style="float: left" onClick="return showAll(this.form)">
		<%
		//End of show all button
		%>
		</td>
		</tr>
	<tr>
		<td width="90">&nbsp;</td>
		<td width="261">&nbsp;</td>
		<td height="19" width="228">&nbsp;</td>
	</tr>
</table>
<%
//Edit by James shift coding up for updating after sending or deleting 2-June 2008
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	//Edited by Roger 30 June 2008 (Revision 1.7)
	//Fix bug cause by previous bug fix, use selectedOrg instead orgId
	rst = mail.getSentFailedEmail(sortBy, sortType, TotDisplay, logchk.getPKUser(), selectedOrg);

%>


<% 
/*******************************

Edited by Roger - 12 June 2008
Fix sorting . added txtRecord1 parameter to sorting

**********************************/
%>
<div style='width:610px; height:259px; z-index:1; overflow:auto'>  

<table class="tablesetting" bordercolor="#3399FF" bgcolor="#FFFFCC">
<th bgcolor="navy" bordercolor="#3399FF">&nbsp;<font face="Arial" size="2"><span style="font-size: 11pt"><input type ="checkbox" name="chkAll" value="checkbox" onClick="checkAll(this.form, this.form.chkEmail, this.form.chkAll)"></span></font></th>
<th width="10" bgcolor="navy" bordercolor="#3399FF"><b>
<font style='color:white'>No</font></b></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="SentFailedEmail.jsp?sortBy=0&txtRecord1=<%=TotDisplay%>"><b>
<font style='color:white'><u><%= trans.tslt("Email ID") %></u></font></b></a></th>
<th width="380" bgcolor="navy" bordercolor="#3399FF">
<a href="SentFailedEmail.jsp?sortBy=1&txtRecord1=<%=TotDisplay%>"><b><font style='font-family:Arial;color:white'>
<u><%= trans.tslt("Sender Email") %></u></font></b></a></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="SentFailedEmail.jsp?sortBy=2&txtRecord1=<%=TotDisplay%>"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Receiver Email") %></u></font></b></a></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="SentFailedEmail.jsp?sortBy=3&txtRecord1=<%=TotDisplay%>"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Header") %></u></font></b></a></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="SentFailedEmail.jsp?sortBy=4&txtRecord1=<%=TotDisplay%>"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Content") %></u></font></b></a></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='font-family:Arial;color:white'><%= trans.tslt("Log") %></font></b></th>

<% 
/**************************
*Edit By James 15 - Nov 2007
***************************/	
//while(rst.next()) 
for(int i=0;i<rst.size();i++)
{
        votblEmail vo=(votblEmail)rst.elementAt(i);
		EmailID= ""+vo.getEmailID();
		SenderEmail= vo.getSenderEmail();
		ReceiverEmail= vo.getReceiverEmail();
		Header = vo.getHeader();
		Content = vo.getContent();
		Log = vo.getLog();
		
		String shortContent = vo.toShort(Content, 50);
		if(Content != null){
			Content = Content.replaceAll("\"", "'");
		}
%>
   	<tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFCC'">
   	<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top">
  		<input type ="checkbox" name="chkEmail" value=<%=EmailID%>></td>
	<td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top">
   	<%=DisplayNo%>
   	</td>
	<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=EmailID%></td>
	<td style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=SenderEmail%></td>
	<td align="left" style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=ReceiverEmail%></td>
	<td align="left" style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=Header%></td>
   	<td align="left" style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=Content%></td>  
   	<td align="left" style="border-style: solid; border-width: 1px" bordercolor="#3399FF" valign="top"><%=Log%>&nbsp;</td>  
 	</tr>
<% 	DisplayNo++;
	} 

%>
</table>
</div>

<p></p>
<input type="button" name="cmdSend" value="<%= trans.tslt("Send Email") %>" onClick="confirmSendEmail(this.form)">&nbsp;
<input type="button" name="cmdDelete" value="<%= trans.tslt("Delete") %>"  onclick = "confirmDelete(this.form)">
</form>
<% } %>

<p></p>
<%@ include file="Footer.jsp"%>

</body>
</html>