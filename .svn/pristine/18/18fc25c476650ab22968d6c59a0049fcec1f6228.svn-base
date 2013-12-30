<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html">
<title>New Page 1</title>
<base target="middle">

<style type="text/css">
<!--
body
{
	font-family:  arial
} 

.style1 {
	color: #000066;
	font-style: italic;
	font-weight: bold;
	font-size: 10pt;
}
.style2 {
	font-size: 10pt;
}
.style3 {
	font-weight: bold;
	font-size: 10pt;
}

-->
</style>
</head>

<body leftmargin="0" topmargin="0" style="text-align: left">


<TABLE WIDTH=947 BORDER=0 CELLPADDING=0 CELLSPACING=0 height="440">
	<TR>
		<TD width="10" ROWSPAN=9 valign="top">
			<IMG SRC="images/360_01.jpg" WIDTH=10 HEIGHT=440 ALT=""></TD>
	</TR>
	<tr>
	<TD width="937" ROWSPAN=9 valign="top">
<table border="0" width="610" height="26">
	<tr>
		<td>&nbsp;</td>
		<td><font face="Arial"><b><font color="red"><%=trans.tslt("System Under Maintenance!")%></font></b></font></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	<%	if(logchk.getOrgCode() != null && logchk.getOrgCode().equals("Allianz"))
		{
	%>		<td><font face="Arial" size="2">Welcome to the ALLIANZ multi-source 
			feedback exercise.</font></td>			
	<%	}
		else
		{
	%>		<td><font face="Arial" size="2">
			Please be informed that the system is currently undergoing maintenance. If you would like us to inform you when the system is available again,
			 please send an email to <a href="3SixtyAdmin@pcc.com.sg">3SixtyAdmin@pcc.com.sg</a>
			</font>
			</font></td>
	<%	}
	%>
		
	</tr>
	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><b><font face="Arial" size="2" color="blue"> 
				<%=trans.tslt("Any inconvenience caused is regretted.")%></font></b></td>
			</tr>

	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>

	<tr>
		<td height="5"></td>
		<td align="center" height="5" valign="top">&nbsp;</td>
	</tr>
	<tr>
	<%@ include file="Main_Footer.jsp"%>	</tr>
	</table>
	</TD>

	</tr>
	</TABLE>
</body>

</html>