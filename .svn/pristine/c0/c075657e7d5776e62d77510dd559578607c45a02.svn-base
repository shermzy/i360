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
		<td><font face="Arial"><b><font color="#000080"><%=trans.tslt("Welcome")%></font>!</b></font></td>
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
	%>		<td><font face="Arial" size="2">3-Sixty Profiler</font><font face="Times New Roman" size="2"> </font>
			<font face="Arial"><sup>®</sup></font><font face="Arial" size="2"> 
			<%=trans.tslt("is a 360")%><sup>o</sup><%=trans.tslt(" feedback management software designed to collect and collate feedback. ")%> 
			<%=trans.tslt("It helps employees to identify strengths and development areas. Please login to proceed")%>.
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
				<td><b><font face="Arial" size="2" color="blue"><%=trans.tslt("The system will be shutdown for maintenance from midnight to 1am daily.")%> 
				<%=trans.tslt(" Access to the system will be disabled during this time. Any inconvenience caused is regretted.")%></font></b></td>
			</tr>
	<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<% // Quick fix to the hyperlink to 3-Sixty Profiler product page, to discuss with Ms Ros whether to change create a url forwarder on webhosting side, Desmond 23 Oct 09%>
				<td><font face="Arial" size="2"><%=trans.tslt("Find out more about")%> <a href="http://119.73.212.179:8008/product_3sixty.html" target="_blank">3-Sixty 
				Profiler</a> <%=trans.tslt("or")%> <a href="http://www.pcc.com.sg" target="_blank"><%=trans.tslt("our company")%></a>.</font></td>
			</tr>
		<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><font face="Arial" size="2"><a href="ForgotPass.jsp"><%=trans.tslt("Forgot your password")%>?</a></font></td>
	</tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <!--by Hemilda Date 18/06/2008-->	
 <tr>
    <td>&nbsp;</td>
    <td><span><font face="Arial" size="2">*<%=trans.tslt("This application system supports IE 9.0 and below, Chrome v11, Safari v5, FireFox v4 & v5.")%><font></span></td>
  </tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
  <tr>
    <td>&nbsp;</td>
    <td><span class="style3"><%=trans.tslt("Support")%>:</span></td>
  </tr>	
 <tr>
    <td>&nbsp;</td>
	<% // Update email address to new one, Desmond 23 Oct 09 %>
    <td><span class="style2"><%=trans.tslt("Email")%>: <a href="mailto:3SixtyAdmin@pcc.com.sg?subject=Regarding:&amp;body=Please provide us with the following information: %0a%0a1. Login Name: %0a2. Survey Name: %0a3. The screenshot of the page where the problem occured: %0a4. Problem descriptions:">3SixtyAdmin@pcc.com.sg</span></a>
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><span class="style2"><%=trans.tslt( "Contact")%>: +65 6896 0080 </span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<% // Update fax number to new one, Desmond 23 Oct 09 %>
    <td><span class="style2"><%=trans.tslt("Facsimile")%>: +65 6515 3204</span></td>
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