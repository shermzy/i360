<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>    
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>          
<%@ page import="CP_Classes.vo.votblSurvey"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/dt_ex.js"></script>
<SCRIPT LANGUAGE=JAVASCRIPT>
	var c1 = new CodeThatCalendar(caldef2);
	var c2 = new CodeThatCalendar(caldef3);

function validate()
{
	var checkingFlag = true;
	x = document.AdvanceSettings
	if(checkdate(x.NomStartDate,x.NomEndDate) == false)
	{
		checkingFlag = false;
	}
	
	return checkingFlag;
}

		
function Save(form)
{
	var flag1 = validate();
    //\\Confirm message added by Ha 29/05/08
	if(flag1)
	{	
	    if (confirm("Update dates? "))
	    {
		form.action="SetDates.jsp?Save=1";
		form.method="post";
		form.submit();
		}
	}
}
/**
function checkdate(objName1, objName2) {


if (chkdate(objName1) == false) {
//datefield.select();
alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
//datefield.focus();
return false;
}
else {
opendate = tanggal;
   }
   
if (chkdate(objName2) == false) {
//datefield.select();
alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
//datefield.focus();
return false;
}
else {
closedate = tanggal;

   }
var correct;   
   if(doDateCheck(opendate,closedate))
   {
   	correct = true;
   }
   else
   {
   	correct = false;
   }
   return correct;
}
function chkdate(objName) {
//var strDatestyle = "US"; //United States date style
var strDatestyle = "EU";  //European date style
var strDate;
var strDateArray;
var strDay;
var strMonth;
var strYear;
var intday;
var intMonth;
var intYear;
var booFound = false;
var datefield = objName;
var strSeparatorArray = new Array("-"," ","/",".");
var intElementNr;
var err = 0;
var strMonthArray = new Array(12);
strMonthArray[0] = "Jan";
strMonthArray[1] = "Feb";
strMonthArray[2] = "Mar";
strMonthArray[3] = "Apr";
strMonthArray[4] = "May";
strMonthArray[5] = "Jun";
strMonthArray[6] = "Jul";
strMonthArray[7] = "Aug";
strMonthArray[8] = "Sep";
strMonthArray[9] = "Oct";
strMonthArray[10] = "Nov";
strMonthArray[11] = "Dec";
strDate = datefield.value;
if (strDate.length < 1) {
return true;
}
for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
strDateArray = strDate.split(strSeparatorArray[intElementNr]);
if (strDateArray.length != 3) {
err = 1;
return false;
}
else {
strDay = strDateArray[0];
strMonth = strDateArray[1];
strYear = strDateArray[2];
}
booFound = true;
   }
}
if (booFound == false) {
if (strDate.length>5) {
strDay = strDate.substr(0, 2);
strMonth = strDate.substr(2, 2);
strYear = strDate.substr(4);
   }
}
if (strYear.length == 2) {
strYear = '20' + strYear;
}
// US style
if (strDatestyle == "US") {
strTemp = strDay;
strDay = strMonth;
strMonth = strTemp;
}
intday = parseInt(strDay, 10);
if (isNaN(intday)) {
err = 2;
return false;
}
intMonth = parseInt(strMonth, 10);
if (isNaN(intMonth)) {
for (i = 0;i<12;i++) {
if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
intMonth = i+1;
strMonth = strMonthArray[i];
i = 12;
   }
}
if (isNaN(intMonth)) {
err = 3;
return false;
   }
}
intYear = parseInt(strYear, 10);
if (isNaN(intYear)) {
err = 4;
return false;
}
if (intMonth>12 || intMonth<1) {
err = 5;
return false;
}
if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
err = 6;
return false;
}
if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
err = 7;
return false;
}
if (intMonth == 2) {
if (intday < 1) {
err = 8;
return false;
}
if (LeapYear(intYear) == true) {
if (intday > 29) {
err = 9;
return false;
}
}
else {
if (intday > 28) {
err = 10;
return false;
}
}
}

if (strDatestyle == "US") {
tanggal = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
}
else {
tanggal = intday + " " + strMonthArray[intMonth-1] + " " + strYear;
}

return true;
}
function LeapYear(intYear) {
if (intYear % 100 == 0) {
if (intYear % 400 == 0) { return true; }
}
else {
if ((intYear % 4) == 0) { return true; }
}
return false;
}
**/

//Edited by Roger 16 June 2008
//Change to regex date validation
function checkdate(objName1, objName2) {
	
	if (isDate(objName1) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		opendate = objName1;
	}
   
	if (isDate(objName2) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		closedate = objName2;
	}

	var correct;   
	if(doDateCheck(opendate,closedate))
	{
		correct = true;
	} else {
	   	correct = false;
	}
	return correct;
}

function doDateCheck(opendate, closedate) {
//Edited By Roger
//Fix date validation bug
var tmpopendate = opendate.value.substring(3,5) +"/" + opendate.value.substring(0,2) + "/" + opendate.value.substring(6,10);
var tmpclosedate = closedate.value.substring(3,5) +"/" + closedate.value.substring(0,2) + "/" + closedate.value.substring(6,10);

	if (Date.parse(tmpopendate) <= Date.parse(tmpclosedate)) {

return true;
}
else {
if (opendate == "" || closedate == "") 
alert("<%=trans.tslt("Both dates must be entered")%>.");
else 
alert("<%=trans.tslt("Nomination Start date must be earlier than Nomination End date")%>.");
return false;
   }
   
}

//Edited by Roger 16 June 2008
//Change to a simplier date validation with regex
function isDate(fld) {
    var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;
    var errorMessage = 'Please enter valid date as month, day, and four digit year.\nYou may use a slash, hyphen or period to separate the values.\nThe date must be a real date. 30/2/2000 would not be accepted.\nFormay dd/mm/yyyy.';
    if ((fld.value.match(RegExPattern)) && (fld.value!='')) {
       return true;
    } else {
		return false;
    } 
}



//  End -->



</SCRIPT>
<body>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font face="Arial" size="2" color="#000080"><%=trans.tslt("Dates Setup")%></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<%
	SimpleDateFormat formatter_db1 = new SimpleDateFormat ("dd/MM/yyyy");
	SimpleDateFormat formatter_db2= new SimpleDateFormat ("yyyy/MM/dd");
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");

	if(request.getParameter("Save") != null)
	{
		String NomStart = request.getParameter("NomStartDate");
		String NomEnd = request.getParameter("NomEndDate");
				
		Date d1a = formatter_db1.parse(NomStart);
		NomStart = formatter_db2.format(d1a);
		
		Date d1b = formatter_db1.parse(NomEnd);
		NomEnd = formatter_db2.format(d1b);

		CE_Survey.updateNominationDates(NomStart,NomEnd,CE_Survey.getSurvey_ID());

		%>
		<script>
			alert("<%=trans.tslt("Dates have been updated")%>");
		</script>
<%		
	}
%>
		
<form name="AdvanceSettings" action="AdvanceSettings.jsp" method="post">
<table border="1" width="99%" bordercolor="#3399FF" bgcolor="#FFFFCC">
<%


	Date today;
	Date next2Week;
	today = new Date();
	next2Week = global.addDay(today, 14);
	
	String NomStartDate = day_view.format(today);
	String NomEndDate = day_view.format(next2Week);
	
	votblSurvey rs_SurveyDetail = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
	if(rs_SurveyDetail != null)
	{
		NomStartDate = day_view.format(rs_SurveyDetail.getStartDateNomination());
		NomEndDate = day_view.format(rs_SurveyDetail.getEndDateNomination());
	}
%>		
	<tr>
		<td bgcolor="#000080" colspan="2"><b>
		<font face="Arial" size="2" color="#FFFFFF"><%=trans.tslt("Dates")%></font></b></td>
	</tr>
	<tr>
		<td width="148"><font face="Arial" size="2"><%=trans.tslt("Nomination Start Date")%>:</font></td>
		<td><font face="Arial" size="2">
				<span style="font-size: 11pt">
				&nbsp;<input type="text" name="NomStartDate" size="10" value=<%=NomStartDate%>></span></font><font size="2" face="Arial"><img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onClick="c1.popup('NomStartDate')"></font></td>
	</tr>
	<tr>
		<td width="148"><font face="Arial" size="2"><%=trans.tslt("Nomination End Date")%>:</font></td>
		<td><font face="Arial" size="2">
				<span style="font-size: 11pt">
								
				&nbsp;<input type="text" name="NomEndDate" size="10" value=<%=NomEndDate%>></span></font><font size="2" face="Arial"><img border="0" style="cursor:pointer" src="images/CAL-icon.gif" width="16" height="16" onClick="c2.popup('NomEndDate')"></font></td>
	</tr>
</table>

<table border="0" width="44%">
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>

<table border="0" width="99%" cellspacing="0" cellpadding="0">
	<tr>
		<td>&nbsp;</td>
		<td width="142">&nbsp;</td>
	</tr>
	<tr>
		<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onClick="window.close()"></td>
		<td width="142">
		<input type="button" value="<%=trans.tslt("Save")%>" name="btnSave" style="float: right" onClick="Save(this.form)"></td>
	</tr>
</table>
</form>
</body>

</html>