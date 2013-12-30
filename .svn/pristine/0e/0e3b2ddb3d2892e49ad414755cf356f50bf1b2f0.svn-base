<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.voUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
function goToURL() { window.location = "Login_midpage.jsp"; }
function validate()
{
	x = document.ForgotPass

	if (x.txtLoginID.value == "" && x.txtEmail.value == "")
    {
		alert("<%=trans.tslt("Enter Login ID")%>" + " OR " + "<%=trans.tslt("Enter Email")%>");
		return false 
	}	
	
	else if(x.txtLoginID.value == "" && x.txtEmail.value != "")
	{
      	if(emailCheck(x.txtEmail.value) == false)
      	return false
    }
}    
    
function emailCheck (emailStr) {

/* The following variable tells the rest of the function whether or not
to verify that the address ends in a two-letter country or well-known
TLD.  1 means check it, 0 means don't. */

var checkTLD=1;

/* The following is the list of known TLDs that an e-mail address must end with. */

var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;

/* The following pattern is used to check if the entered e-mail address
fits the user@domain format.  It also is used to separate the username
from the domain. */

var emailPat=/^(.+)@(.+)$/;

/* The following string represents the pattern for matching all special
characters.  We don't want to allow special characters in the address. 
These characters include ( ) < > @ , ; : \ " . [ ] */

var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";

/* The following string represents the range of characters allowed in a 
username or domainname.  It really states which chars aren't allowed.*/

var validChars="\[^\\s" + specialChars + "\]";

/* The following pattern applies if the "user" is a quoted string (in
which case, there are no rules about which characters are allowed
and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
is a legal e-mail address. */

var quotedUser="(\"[^\"]*\")";

/* The following pattern applies for domains that are IP addresses,
rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
e-mail address. NOTE: The square brackets are required. */

var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;

/* The following string represents an atom (basically a series of non-special characters.) */

var atom=validChars + '+';

/* The following string represents one word in the typical username.
For example, in john.doe@somewhere.com, john and doe are words.
Basically, a word is either an atom or quoted string. */

var word="(" + atom + "|" + quotedUser + ")";

// The following pattern describes the structure of the user

var userPat=new RegExp("^" + word + "(\\." + word + ")*$");

/* The following pattern describes the structure of a normal symbolic
domain, as opposed to ipDomainPat, shown above. */

var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");

/* Finally, let's start trying to figure out if the supplied address is valid. */

/* Begin with the coarse pattern to simply break up user@domain into
different pieces that are easy to analyze. */

var matchArray=emailStr.match(emailPat);

if (matchArray==null) {

/* Too many/few @'s or something; basically, this address doesn't
even fit the general mould of a valid e-mail address. */

alert("<%=trans.tslt("Email address seems incorrect")%> (<%=trans.tslt("check")%> @ <%=trans.tslt("and")%> .'s)");
return false;
}
var user=matchArray[1];
var domain=matchArray[2];

// Start by checking that only basic ASCII characters are in the strings (0-127).

for (i=0; i<user.length; i++) {
if (user.charCodeAt(i)>127) {
alert("<%=trans.tslt("This username contains invalid characters")%>.");
return false;
   }
}
for (i=0; i<domain.length; i++) {
if (domain.charCodeAt(i)>127) {
alert("<%=trans.tslt("This domain name contains invalid characters")%>.");
return false;
   }
}

// See if "user" is valid 

if (user.match(userPat)==null) {

// user is not valid

alert("<%=trans.tslt("The username doesn''t seem to be valid")%>.");
return false;
}

/* if the e-mail address is at an IP address (as opposed to a symbolic
host name) make sure the IP address is valid. */

var IPArray=domain.match(ipDomainPat);
if (IPArray!=null) {

// this is an IP address

for (var i=1;i<=4;i++) {
if (IPArray[i]>255) {
alert("<%=trans.tslt("Destination IP address is invalid")%>!");
return false;
   }
}
return true;
}

// Domain is symbolic name.  Check if it's valid.
 
var atomPat=new RegExp("^" + atom + "$");
var domArr=domain.split(".");
var len=domArr.length;
for (i=0;i<len;i++) {
if (domArr[i].search(atomPat)==-1) {
alert("<%=trans.tslt("The domain name does not seem to be valid")%>.");
return false;
   }
}

/* domain name seems valid, but now make sure that it ends in a
known top-level domain (like com, edu, gov) or a two-letter word,
representing country (uk, nl), and that there's a hostname preceding 
the domain or country. */

if (checkTLD && domArr[domArr.length-1].length!=2 && 
domArr[domArr.length-1].search(knownDomsPat)==-1) {
alert("<%=trans.tslt("The address must end in a well-known domain or two letter country")%>.");
return false;
}

// Make sure there's a host name preceding the domain.

if (len<2) {
alert("<%=trans.tslt("This address is missing a hostname")%>!");
return false;
}

// If we've gotten this far, everything's valid!
return true;

}

</SCRIPT>
<body topmargin="0" leftmargin="0">
<%
	int FKOrganization = logchk.getOrg();

if(request.getParameter("btnSubmit") != null)
{
	int PKUser =0;
	
	boolean flag = false;
	String LoginID = request.getParameter("txtLoginID");
	String email = request.getParameter("txtEmail");
	

	
	voUser vo = user.getUserPassInfo(LoginID, email, FKOrganization);
	if(vo != null)
	{
		PKUser = vo.getPKUser();
		FKOrganization = vo.getFKOrganization();
		
		String sEmail = vo.getEmail();
		email = sEmail;
		
		String [] emailArr = sEmail.split(",");

		if(LoginID.equals("")) {
			for(int i=0; i<emailArr.length; i++) {
				if(emailArr[i].trim().equals(email)) {
					
					flag = true;
					break;
				}
			}
		} else  {
			if(email.equals("")) {
				flag = true;
			
			} else {
				for(int i=0; i<emailArr.length; i++) {
					if(emailArr[i].trim().equals(email)) {
						
						flag = true;
						break;
					}
				}
			}
		}
		
		
		flag = true;
	}

	if(flag)
	{
		user.ForgotPass(PKUser,email,FKOrganization);
		%>
		<script>
			alert("<%=trans.tslt("An email will be sent to you shortly")%>");
			parent.location.href='index.jsp?candidate=' + '<%=logchk.getOrgCode()%>';
		</script>
<%		
	}
	else
	{
		%>
		<script>
			alert("<%=trans.tslt("The Login ID or the Email address does not exist in our database")%>");
		</script>
		<%
	}
}
%>	
<form name="ForgotPass" action="ForgotPass.jsp" method="post" onsubmit="return validate()">

<table border="0" width="664" height="439" cellspacing="0" cellpadding="0">
	<tr>
		<td width="12">
			<IMG SRC="images/360_01.jpg" WIDTH=10 HEIGHT=440 ALT=""></td>
		<td valign="top">&nbsp;
		</td>
		<td valign="top" width="634">
		<table border="0" width="100%" cellspacing="0" cellpadding="0">

			<tr>
				<td><b><font face="Arial" size="2" color="#000080">
				<%= trans.tslt("Sign-in Problems") %></font></b></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font face="Arial" size="2"><STRONG><%=trans.tslt("Please key in your Login ID and/or email address, and an email will be sent to you shortly")%>.</STRONG></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font face="Arial" size="2"><em>* <%=trans.tslt("If you have forgotten your Login ID and email address, please click on Contact Us to send email to us by giving your NRIC, Family, and Given name")%>.</em></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table border="2" width="517" cellspacing="0" cellpadding="0" bordercolor="#3399FF">
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-style: none; border-width: medium" bgcolor="#FFFFCC"><b><font face="Arial" size="2">
		<%= trans.tslt("Enter Your Login ID") %>:</font></b></td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC"><input type="text" name="txtLoginID" size="20"></td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-style: none; border-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-style: none; border-width: medium" bgcolor="#FFFFCC"><b><font face="Arial" size="2">
		<%= trans.tslt("Enter Your Email") %>:</font></b></td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC"> <font size="2">
   
		<input type="text" name="txtEmail" size="27"></td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-style: none; border-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-style: none; border-width: medium" bgcolor="#FFFFCC"><input type="button" value="<%= trans.tslt("Back") %>" name="btnCancel" onclick="goToURL()"></td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC"><input type="submit" value="<%= trans.tslt("Get password") %>" name="btnSubmit"></td>
	</tr>
	<tr>
		<td width="12" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="165" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bgcolor="#FFFFCC">&nbsp;</td>
		<td style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
</table>
		
		
		<font size="2">
		<table border="0" width="610" height="26">
			<tr>
				<td align="center" height="5" valign="top"></td>
			</tr>
			<tr>
				<td align="center" height="5" valign="top">
				</td>
			</tr>
			<tr>
				<td align="center" height="5" valign="top">
				</td>
			</tr>
			<tr>
				<td align="center" height="5" valign="top">
				</td>
			</tr>
			<tr>
				<td align="center" height="5" valign="top">
				</td>
			</tr>
			<tr>
				<td align="center" height="5" valign="top">
				</td>
			</tr>
			<tr>
				<%@ include file="Main_Footer.jsp"%>				
			</tr>
			
		</table>
		</font>
		<p></td>
	</tr>
</table>

</form>
</body>
</html>