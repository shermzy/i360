<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %> 
<%@ page import = "java.lang.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="server" class="CP_Classes.Setting" scope="session"/>
<html>

<head>

 
<title>3-Sixty Profiler Ver 1.3.15.50 </title>

<%
//Added by Xuehai, 08 Jun 2011. Include cookie to store the candidate value in the URL.
//The aim is, once the session expires, instead of showing the error page, it can redirect to the page user used to login.
%>
<script language="JavaScript" type="text/javascript">
   function addCookie(objName,objValue,objHours){
    var str = objName + "=" + escape(objValue);
    if(objHours > 0){
     var date = new Date();
     var ms = objHours*3600*1000;
     date.setTime(date.getTime() + ms);
     str += "; expires=" + date.toGMTString();
    }
    document.cookie = str;
   }
  
   function getCookie(objName){
   try{
    var arrStr = document.cookie.split("; ");
    for(var i = 0;i < arrStr.length;i ++){
     var temp = arrStr[i].split("=");
     if(temp[0] == objName) 
     	return unescape(temp[1]);
    }
    }catch(ex){}
    return null;
   }
</script>

</head>
<%  //Store data from TB_Translation into Hashtable
trans.getTranslation();
if (request.getParameter("candidate") != null&&!(request.getParameter("candidate").equals("null")))
{
	
	String sOrg = request.getParameter("candidate");
	logchk.setOrgCode(sOrg);
	
	if(request.getParameter("org") != null)
	{
		sOrg = request.getParameter("org");
		logchk.setOrgCode(sOrg);
	}

	if(request.getParameter("admin") != null)
	{
		sOrg = request.getParameter("admin");
		logchk.setOrgCode(sOrg);
	}
	
	//Gwen Oh - 15/09/11: Set the organisation id
	int organisation = Org.getPKOrg(sOrg);
	logchk.setOrg(organisation);
	CE_Survey.set_survOrg(organisation);
%>
<%
//Added by Xuehai, 08 Jun 2011. Store the candidate data in the cookie.
// So that the data can be used when session expires.
%>
<script language="JavaScript" type="text/javascript">
try{
	var name='candidate';
	var value='<%=request.getParameter("candidate") %>';
	addCookie(name, value, 24);
}catch(ex){}
</script>
<%
if(logchk.getSystemMaintenance()==false) {
%>
<frameset framespacing="0" border="0" frameborder="0" rows="126,357">
	<frame name="top" scrolling="no" noresize target="middle" src="Top.jsp">
	<frame name="middle" src="Login_midpage.jsp" target="middle" scrolling="auto">
		<noframes>
	<body>

	<p>This page uses frames, but your browser doesn't support them.</p>

	</body>
	</noframes>
</frameset>
<%
}else{
%>
<frameset framespacing="0" border="0" frameborder="0" rows="126,357">
	<frame name="top" scrolling="no" noresize target="middle" src="Top_maintenance.jsp">
	<frame name="middle" src="Login_midpage_maintenance.jsp" target="middle" scrolling="auto">
		<noframes>
	<body>

	<p>This page uses frames, but your browser doesn't support them.</p>

	</body>
	</noframes>
</frameset>
<%
}
}
else
{%>


<body>
<font face="Arial" color="#FF0000" size="2"><b> </b>
<font color="#FF0000"><b>You are not authorised to view this page or Session Expired, please re-login!</b></font>
<b><font color="#FF0000"><a href="#" onClick="toLogin();">&nbsp;Login&nbsp;</a></font></b></font>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
<%
//Added by Xuehai, 08 Jun 2011. Redirect to login page.
%>
<script language="JavaScript" type="text/javascript">
function toLogin(){
	var value=getCookie('candidate');
	if(value==null || value==''){
		value='PCC';
	}
	window.location.href='index.jsp?candidate=' + value;
}

window.setTimeout("toLogin();", 2000);
</script>
</body>

<% }%>
</html>