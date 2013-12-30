<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<script language="JavaScript">
<!--
function FP_swapImg() {//v1.0
 var doc=document,args=arguments,elm,n; doc.$imgSwaps=new Array(); for(n=2; n<args.length;
 n+=2) { elm=FP_getObjectByID(args[n]); if(elm) { doc.$imgSwaps[doc.$imgSwaps.length]=elm;
 elm.$src=elm.src; elm.src=args[n+1]; } }
}

function FP_preloadImgs() {//v1.0
 var d=document,a=arguments; if(!d.FP_imgs) d.FP_imgs=new Array();
 for(var i=0; i<a.length; i++) { d.FP_imgs[i]=new Image; d.FP_imgs[i].src=a[i]; }
}

function FP_getObjectByID(id,o) {//v1.0
 var c,el,els,f,m,n; if(!o)o=document; if(o.getElementById) el=o.getElementById(id);
 else if(o.layers) c=o.layers; else if(o.all) el=o.all[id]; if(el) return el;
 if(o.id==id || o.name==id) return o; if(o.childNodes) c=o.childNodes; if(c)
 for(n=0; n<c.length; n++) { el=FP_getObjectByID(id,c[n]); if(el) return el; }
 f=o.forms; if(f) for(n=0; n<f.length; n++) { els=f[n].elements;
 for(m=0; m<els.length; m++){ el=FP_getObjectByID(id,els[n]); if(el) return el; } }
 return null;
}
// -->
</script>
<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Cdepartment" class="CP_Classes.Department" scope="session"/>
<jsp:useBean id="group" class="CP_Classes.Group" scope="session"/>
<jsp:useBean id="division" class="CP_Classes.Division" scope="session"/>
<%// added to check whether the organisation is a consulting firm
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.voGroup"%>
<%@ page import="CP_Classes.vo.voDepartment"%>
<%@ page import="CP_Classes.vo.voDivision"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>
<%@ page import="CP_Classes.vo.voUserType"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
</head>
<SCRIPT LANGUAGE="JavaScript">
var x = parseInt(window.screen.width) / 2 - 385;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 235;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function AssignRelation(form)
{
	//window.document.User.action="User.jsp?AssignRelation=1";
	//window.document.User.method="post";
	//window.document.User.submit();	
	var myWindow=window.open('AssignRelationUserList.jsp?PageOpener=User.jsp','windowRef','scrollbars=no, width=770, height=470');
	myWindow.moveTo(x,y);

}

//By Hemilda date 06/06/2008 - Change Prompt message
function validate()
{
    x = document.User;
    if (x.txtLoginID.value == "") {
		alert("<%=trans.tslt("Enter Login ID")%>");
		return false;
	}
	if (x.txtFamilyName.value == "") {
		alert("<%=trans.tslt("Enter Family Name")%>");
		return false;
	}
	if (x.txtGivenName.value == "") {
		alert("<%=trans.tslt("Enter Given Name")%>");
		return false;
	}
	if (x.txtDesignation.value == "") {
		alert("<%=trans.tslt("Enter Designation")%>");
		return false;
	}
	if (x.txtIDNumber.value == "") {
		alert("<%=trans.tslt("Enter ID Number")%>");
		return false;
	}	
	if (x.selDivision.value == "") {
		alert("<%=trans.tslt("Select Division")%>");
		return false;
	}    
	if (x.selDepartment.value == "") {
		alert("<%=trans.tslt("Select Department")%>");
		return false;
	} 
	if (x.selGroup.value == "") {
		alert("<%=trans.tslt("Select Group")%>");
		return false;
	} 
	if (x.selUserType.value == "") {
		alert("<%=trans.tslt("Select User Type")%>");
		return false;
	}
	if (x.txtPassword.value == "") {
		alert("<%=trans.tslt("Enter Password")%>");
		return false;
	}  
	if (x.txtPassword.value.length < 4) {
		alert("<%=trans.tslt("Password must be at least 4 characters long")%>");
		return false;
	}  
	if(x.txtPassword.value != x.txtPassword2.value) {
		alert("<%=trans.tslt("Password mismatch")%>");
		return false;
	}  
	if (x.txtEmail.value == "") {
		alert("<%=trans.tslt("Enter Email")%>");
		return false;
	} else {
      	if(emailCheck(x.txtEmail.value) == false) return false;
    }
    //\\ Added by Ha 10/06/08 to prompt confirm message
    if (!confirm("Register new user?")) {
		return false;}
		
    if (x.txtSup.value == "") {
		alert("<%=trans.tslt("Supervisor is not assigned")%>");
		return true;
	}
		
	return true;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function search(form)
function search(form)
{
	//form.action = "UserList.jsp";
	form.action = "UserSearch.jsp";
	form.method = "post";
	form.submit();	
}

function proceed(form,field)
{
	form.action="User.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function checkName(form,field)
{
	form.action="User.jsp?checkName=1";
	form.method="post";
	form.submit();
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

alert("Email address seems incorrect (check @ and .'s)");
return false;
}
var user=matchArray[1];
var domain=matchArray[2];

// Start by checking that only basic ASCII characters are in the strings (0-127).

for (i=0; i<user.length; i++) {
if (user.charCodeAt(i)>127) {
alert("<%=trans.tslt("The username contains invalid characters")%>.");
return false;
   }
}
for (i=0; i<domain.length; i++) {
if (domain.charCodeAt(i)>127) {
alert("<%=trans.tslt("")%>.");
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

function populateDept(form, field)
{
	form.action="User.jsp?div="+field.value;
	form.method="post";
	form.submit();
}

function populateGrp(form, field1, field2)
{
	form.action="User.jsp?div=" + field1.value + "&dept="+ field2.value;
	form.method="post";
	form.submit();
}

</script>
<body onload="FP_preloadImgs(/*url*/'images/btnAssign3.jpg', /*url*/'images/btnAssign2.jpg')">
<%
String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	window.close();
	opener.parent.location.href = "Login.jsp";
	</script>
<%  } 
  else 
  { 
if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}
String LoginID =" ";
String FamilyName=" ";
String GivenName = " ";
String Designation =" ";
String IDNumber=" ";
int Division =0;
int Department=0;
int Group=0;
int UserType=0;
String dis="";
String email="";
String offTel = "";
String handphone = "";
String mobileProvider = "";
String remark = "";
String Password = "";
//Added a new field round in the User table
int round=0;
//Added by Ha 03/06/08
if (request.getParameter("txtLoginID")!=null)
	LoginID = request.getParameter("txtLoginID");
if (request.getParameter("txtFamilyName")!=null)
	FamilyName = request.getParameter("txtFamilyName");
if (request.getParameter("txtGivenName")!=null)
	GivenName = request.getParameter("txtGivenName");
if (request.getParameter("txtDesignation")!=null)
	Designation = request.getParameter("txtDesignation");
if (request.getParameter("txtIDNumber")!=null)
	IDNumber = request.getParameter("txtIDNumber");
if (request.getParameter("selDivision")!=null&&(!request.getParameter("selDivision").equals("")))
	Division = new Integer(request.getParameter("selDivision")).intValue();
if (request.getParameter("selDepartment")!=null&&(!request.getParameter("selDepartment").equals("")))
	Department = new Integer(request.getParameter("selDepartment")).intValue();
if (request.getParameter("selGroup")!=null&&(!request.getParameter("selGroup").equals("")))
	Group = new Integer(request.getParameter("selGroup")).intValue();
if (request.getParameter("selUserType")!=null&&(!request.getParameter("selUserType").equals("")))
	UserType = new Integer(request.getParameter("selUserType")).intValue();
if (request.getParameter("txtRound")!=null&&(!request.getParameter("txtRound").equals("")))
	round = new Integer(request.getParameter("txtRound")).intValue();
	
	if(request.getParameter("AssignRelation") != null)
	{
		if(Integer.parseInt(request.getParameter("AssignRelation")) == 1)
		{
			//Send Assign request
		%>
		<script>
			var myWindow=window.open('AssignRelationUserList.jsp','windowRef','scrollbars=no, width=770, height=470');
			myWindow.moveTo(x,y);
		   // myWindow.location.href = 'AssignRelationUserList.jsp';
		</script>
		<%
		}
	}
	
if(request.getParameter("btnAdd") != null)	
{	
	try
	{
	//Changed by Ha 03/06/08
		int enable = 1;
		LoginID = request.getParameter("txtLoginID");	
	   
		int iPKUserID2 = Integer.parseInt(request.getParameter("hdnSupID")); //User ID for second person in tblUserRelation			
		
		FamilyName = request.getParameter("txtFamilyName");
		GivenName = request.getParameter("txtGivenName");
		Designation = request.getParameter("txtDesignation");
		IDNumber = request.getParameter("txtIDNumber");
		Division = new Integer(request.getParameter("selDivision")).intValue();
		Department = new Integer(request.getParameter("selDepartment")).intValue();
		Group = new Integer(request.getParameter("selGroup")).intValue();		
		UserType = new Integer(request.getParameter("selUserType")).intValue();		
		Password = request.getParameter("txtPassword");
		dis = request.getParameter("cliDisabled");
		email = request.getParameter("txtEmail");
		offTel = request.getParameter("txtOffTel");
		handphone = request.getParameter("txtHandphone");
		mobileProvider = request.getParameter("txtMobileProvider");
		remark = request.getParameter("txtRemark");
		if (!(request.getParameter("txtRound")).equals(""))
			round = new Integer(request.getParameter("txtRound")).intValue();
		int CompanyID = logchk.getCompany();
		
		if(dis!=null&&dis != " ")
		{
			enable = 0;
		}
		
		/*************************************
		* Edited by junwei on 5 Mar 2008
		* checkLoginIdExist() method added to 
		* User.java to check for existing ID
		**************************************/
		if(user.checkLoginIdExist(LoginID, logchk.getOrg()) == false)
		{
			//add a check if round field is empty then it'll be null value in the table
			if(!(request.getParameter("txtRound")).equals(""))
				user.addRecord(Department,Division,UserType,FamilyName,GivenName,LoginID,Designation,IDNumber,Group,Password,enable,CompanyID,logchk.getOrg(),email, offTel, handphone, mobileProvider, remark, logchk.getPKUser(),round);
			else
				user.addRecord(Department,Division,UserType,FamilyName,GivenName,LoginID,Designation,IDNumber,Group,Password,enable,CompanyID,logchk.getOrg(),email, offTel, handphone, mobileProvider, remark, logchk.getPKUser());
			
			//Get Just created UserID and insert into tblUserRelation
			
			if (iPKUserID2 == 0 || iPKUserID2 == -1)
			{	
				//System.out.println("Supervisor = -1 (" + iPKUserID2 + ")");
				user.insertNoRelation(LoginID);
			}
			else
			{
				//System.out.println("Supervisor != -1 (" + iPKUserID2 + ")");
				user.insertRelation(LoginID, iPKUserID2);
			}
			%>
			<script>
				alert("<%=trans.tslt("User has been successfully registered")%>");
		//**** Edited by Tracy on 08 Aug 08******
		// Tranfer to User List after creating a new user.
				//window.location.href = 'User.jsp';
				window.location.href = 'UserList.jsp';
		//********************************************
			</script>
			<%
		}
		else
		{
		%>
			<script>
			alert("<%=trans.tslt("User exists")%>");
			</script>
		<%	
		}
	}
	catch (SQLException sqle) 
    {
%>		<script>
			alert("<%=trans.tslt("User exists")%>");
		</script>
<%	}
}
    
	boolean rePopulate = false;
	if(request.getParameter("div") != null||Division>0)
	{
		if(request.getParameter("div") != null&& request.getParameter("div").length() > 0)
		{
			Division = new Integer(request.getParameter("div")).intValue();
			rePopulate = true;
		}
	
	}
	
	if(request.getParameter("dept") != null||Department>0)
	{	
		if(request.getParameter("dept") != null&&request.getParameter("dept").length() > 0)
		{
			Department = new Integer(request.getParameter("dept")).intValue();
			rePopulate = true;
		}
		
	}
	
	if(request.getParameter("selGroup") != null)
	{	
		if(request.getParameter("selGroup").length() > 0)
		{
			Group = new Integer(request.getParameter("selGroup")).intValue();
			rePopulate = true;
		}
	}
	
	if(request.getParameter("selUserType") != null)
	{	
		if(request.getParameter("selUserType").length() > 0)
		{
			UserType = new Integer(request.getParameter("selUserType")).intValue();
			rePopulate = true;
		}
	}
	
	if(rePopulate)
	{
		LoginID = request.getParameter("txtLoginID");
		FamilyName = request.getParameter("txtFamilyName");
		GivenName = request.getParameter("txtGivenName");
		Designation = request.getParameter("txtDesignation");
		IDNumber = request.getParameter("txtIDNumber");
		if (request.getParameter("txtPassword")!=null)
			Password = request.getParameter("txtPassword");
		//dis = request.getParameter("cliDisabled");
		if (request.getParameter("txtEmail")!=null)
			email = request.getParameter("txtEmail");
		if (request.getParameter("txtOffTel")!=null)
			offTel = request.getParameter("txtOffTel");
		if(request.getParameter("txtHandphone")!=null)
			handphone = request.getParameter("txtHandphone");
		if(request.getParameter("txtMobileProvider")!=null)
			mobileProvider = request.getParameter("txtMobileProvider");
		if(request.getParameter("txtRemark")!=null)
			remark = request.getParameter("txtRemark");
	}
	
%>

<form name="User" action="User.jsp" method="post" onsubmit="return validate()">

<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="19%"><b><font face="Arial" size="2"><%=trans.tslt("Organisation")%>: </font></b></td>
		<td width="68%">
<p align="left"> <font size="2">
   
	<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
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

<%		} else { %>
			<option value=<%=PKOrg%>><%=OrgName%></option>
<%		}	
	}
	} else { %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
<% } // End of isConsulting %>
</select></td>

	<td width="13%"><p align="center"></td>
	</tr>
	<tr>
		<td width="19%">&nbsp;</td>
		<td width="1%"></td>
		<td width="80%">&nbsp;</td>
	</tr>
</table>
<table border="1" width="36%" bordercolor="#3399FF" bgcolor="#FFFFCC">
	<tr>
		<td colspan="2" bgcolor="#000080" height="27">
		<p align="center"><b><font face="Arial" color="#FFFFFF" size="2"><%=trans.tslt("User Detail")%></font></b></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Login ID")%>:</font></td>
		<td align="left" width="59%">
		<font face="Arial"><span style="font-size: 11pt">
		<input name="txtLoginID" size="20" style="float: left" value="<%=LoginID.trim()%>"></span><font size="2">
		</font></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Family Name")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="text" name="txtFamilyName" size="20" value="<%=FamilyName.trim()%>"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Given Name")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="text" name="txtGivenName" size="20" value="<%=GivenName.trim()%>"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Designation")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="text" name="txtDesignation" size="20"  value="<%=Designation.trim()%>"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("ID Number")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="text" name="txtIDNumber" size="20" value="<%=IDNumber.trim()%>"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Email")%>:</font></td>
		<td align="left" width="59%">
		<input type="text" name="txtEmail" size="27" value="<%=email.trim()%>"></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Office Tel")%>:</font></td>
		<td align="left" width="59%">
		<input type="text" name="txtOffTel" size="27" value="<%=offTel.trim()%>"></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Handphone")%>:</font></td>
		<td align="left" width="59%">
		<input type="text" name="txtHandphone" size="27" value="<%=handphone.trim()%>"></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Mobile Provider")%>:</font></td>
		<td align="left" width="59%">
		<select name="txtMobileProvider">
			<option value="SingTel" <% if(mobileProvider.equals("SingTel")) { %>selected<%}%>>SingTel</option>
			<option value="StarHub" <% if(mobileProvider.equals("StarHub")) { %>selected<%}%>>StarHub</option>
			<option value="M1" <% if(mobileProvider.equals("M1")) { %>selected<%}%>>M1</option>
			<option value = "Others" <% if(mobileProvider.equals("Others")) { %>selected<%}%>>Others</option>
		</select></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Remark")%>:</font></td>
		<td align="left" width="59%">
		<textarea span style='font-family:Arial;font-size:10.0pt' name="txtRemark" cols="50" rows="5"><%=remark.trim()%></textarea></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Division")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
			<select size="1" name="selDivision" onchange="populateDept(this.form, this.form.selDivision)">
<%
	if(Division == 0)%>
				<option value='' selected>&nbsp;</option>
<%

	/***********************
	* Edited by Yuni 14 Nov 2007
	************************/
	Vector v = division.getAllDivisions(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++) {
		voDivision vo = (voDivision)v.elementAt(i);

		int div_ID = vo.getPKDivision();
		String div_Name = vo.getDivision();


	if(Division != 0 && Division == div_ID)
	{
	%>
		<option value=<%=div_ID%> selected><%=div_Name%></option>
<%	}else{%>
		<option value=<%=div_ID%>><%=div_Name%></option>
<%	}
}	%>		
			</select></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Department")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt"><select size="1" name="selDepartment" onchange="populateGrp(this.form, this.form.selDivision, this.form.selDepartment)">
				<%
	if(Department == 0)%>
				<option value='' selected>&nbsp;</option>
<%
	int div = 0;
	//Changed by Ha 03/06/08
	if(request.getParameter("div") != null||Division>0)
	{
		if (request.getParameter("div")!=null)
		{
			String divID = request.getParameter("div");
			if(divID.length() > 0)
			{
				div = Integer.parseInt(divID);
			}
		}
		else
		{
			div = Division;
		}
	}
	
	/********************
	* Edited by James 17 Oct 2007
	************************/
	 Vector vDepartments = Cdepartment.getAllDepartments(logchk.getOrg(),div);
	 for(int i=0; i<vDepartments.size(); i++) { 
	
	
		voDepartment voD = (voDepartment)vDepartments.elementAt(i);
        
		int dep_ID=voD.getPKDepartment();
		String dep_Name =voD.getDepartmentName();
	

	if(Department != 0 && Department == dep_ID)
	{
	%>
		<option value=<%=dep_ID%> selected><%=dep_Name%></option>
<%	}else{%>		
		<option value=<%=dep_ID%>><%=dep_Name%></option>

<%	}
}	%>			
		</select></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Group/Section")%>:</font></td>
		<td align="left" width="59%"><select size="1" name="selGroup">
<%
	if(Group == 0)%>
			<option value='' selected>&nbsp;</option>
<%
	int dept = 0;
	if(request.getParameter("dept") != null||Department>0)
	{
		if (request.getParameter("dept")!=null)
		{
			String department = request.getParameter("dept");
			if(department.length()> 0)
			{
				dept = Integer.parseInt(department);
			}
		}
		else
			dept = Department;
	}
	
	/********************
	* Edited by Yuni 14 Nov 2007
	************************/
	
	 Vector vGroup = group.getAllGroups(logchk.getOrg(),dept);
	 for(int i=0; i<vGroup.size(); i++) { 
	   
		voGroup voG = (voGroup)vGroup.elementAt(i);      
		
		int Group_ID = voG.getPKGroup();
		String GroupName = voG.getGroupName();
	
	

	if(Group != 0 && Group== Group_ID )
	{
	%>
		<option value=<%=Group_ID%> selected><%=GroupName%></option>
<%	}else{%>
		<option value=<%=Group_ID%>><%=GroupName%></option>
<%	}
}	
%>			
		</select></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("User Type")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt"><select size="1" name="selUserType">
				<%
	if(UserType == 0)%>
				<option value='' selected>&nbsp;</option>
<%
	/********************
	* Edited by James 17 Oct 2007
	************************/
	/* Codes added to limit other organization from creating Administrator
	 * accounts. Only PCC can create Administrator accounts
	 * Mark Oei 09 Mar 2010
	 */
	
	 Vector vUser = user.getAllUsers();
	 String chkOrg = logchk.getOrgCode();
	 
	 for(int i=0; i<vUser.size(); i++) { 
	   
		voUserType voU = (voUserType)vUser.elementAt(i);      
		
		int type_ID  =voU.getPKUserType();
        String type_Name = voU.getUserTypeName();
     
        if (!(chkOrg.equals("PCC"))&& (type_Name.equals("Administrator"))){
        	continue;
        }
        else {
        	if(UserType != 0 && UserType== type_ID )
        	{%>
				<option value=<%=type_ID%> selected><%=type_Name%></option>
<%			} else {%>		
				<option value=<%=type_ID%>><%=type_Name%></option>
<%			}
        }
	 }	
%>				
		</select></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Password")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="password" name="txtPassword" size="20"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Confirm Password")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="password" name="txtPassword2" size="20"></span></font></td>
	</tr>
	<!-- Adding a new field for round by Albert(1 June 2012) -->
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Round")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<span style="font-size: 11pt">
		<input type="text" name="txtRound" size="20"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2"><%=trans.tslt("Supervisor")%></font><font face="Arial" style="font-size: 11pt">:</font></td>
		<td align="left" width="59%"><font face="Arial" size="2">
		<span style="font-size: 11pt">

		<input type="text" name="txtSup" size="15" value="" readonly>
		<input type="hidden" name="hdnSupID" value="-1">
		<img border="0" id="img1" src="images/btnAssign1.jpg" height="20" width="60" alt="Assign" onclick="AssignRelation(this.form)" fp-style="fp-btn: Glow Rectangle 1; fp-proportional: 0" fp-title="Assign" onmouseover="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/btnAssign3.jpg')" onmouseout="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/btnAssign1.jpg')" onmousedown="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/btnAssign2.jpg')" onmouseup="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/btnAssign3.jpg')"></span></font></td>
	</tr>
	<tr>
		<td width="36%" align="left"><font face="Arial" size="2">
		<%=trans.tslt("Disabled")%>:</font></td>
		<td align="left" width="59%"><font face="Arial">
		<font size="2">
		<%
		//Change condition by Ha 03/06/08
		if(dis!=null && dis != " ")
		{
		%>
			</font>
		<span style="font-size: 11pt">
			<input type="checkbox" name="cliDisabled" value= "0"></span><font size="2">
		<%}else	{%>
			</font>
		<span style="font-size: 11pt">
			<input type="checkbox" name="cliDisabled" value= "1"><font size="2">
		<%	}	%>
			</font>
			</span></font></td>
	</tr>
</table>
<table border="0" width="100%" height="56">
	<tr>
		<td width="137">&nbsp;</td>
		<td width="838" align="right">&nbsp;</td>
	</tr>
	<tr>
		<td width="490" align="right">
		
   
		<input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" style="float: right" onclick="search(this.form)"><font size="2"><input type="submit" 
="<%=trans.tslt("Save")%>" name="btnAdd" style="float: right"></td>
		<td width="489" align="right">&nbsp;
		
   
		</td>
	</tr>
</table>
</form>
</cfoutput>
<%	}	%>
</body>
</html>