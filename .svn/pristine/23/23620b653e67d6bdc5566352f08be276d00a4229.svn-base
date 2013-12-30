<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>    
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>      

<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/dt_ex.js"></script>
<SCRIPT LANGUAGE=JAVASCRIPT>

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

	if(flag1)
	{	
		form.action="ChangeNomination.jsp?Save=1";
		form.method="post";
		form.submit();
	}
}

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
		<td>&nbsp;</td>
		<td width="142">
		<input type="button" value="Update Deadline &amp; Nomination End Date" name="btnSave" style="float: right" onclick="Save(this.form)"></td>
	</tr>
</table>
</form>
</body>

</html>