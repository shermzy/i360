<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="server" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<html>
<head>
	<link rel=apple-touch-icon href="icon.png" />
	<link rel="shortcut icon" href="icon.png" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Login - iPhone Version</title>
	
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	
	<script type="application/x-javascript" src="iui/iui.js"></script> 
	<SCRIPT LANGUAGE="JavaScript">
	    function validate(form){
	      var iValid =0;
	      var username = form.txtUsername.value
	      var password = form.txtPass.value
	      
	      if (username == "")
			alert("<%=trans.tslt("Please enter Username")%>");
	      else{
	      	if (password == "")
	       		alert("<%=trans.tslt("Please enter Password")%>");
	      	else{
	      		iValid = 1;
	      		form.action = "login.jsp?result=1";	
	    		form.method="post";	
	    		form.submit();
			}
	      }
	    }
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

<%
if(request.getParameter("candidate") != null&&!(request.getParameter("candidate").equals("null"))){
	
	String sOrg = request.getParameter("candidate");
	logchk.setOrgCode(sOrg);
	
	if(request.getParameter("org") != null){
		sOrg = request.getParameter("org");
		logchk.setOrgCode(sOrg);
	}

	if(request.getParameter("admin") != null){
		sOrg = request.getParameter("admin");
		logchk.setOrgCode(sOrg);
	}
	int FKOrganization = Org.getPKOrg(sOrg);
	logchk.setOrg(FKOrganization);
	%>
	<script language="JavaScript" type="text/javascript">
		try{
			var name='candidate';
			var value='<%=request.getParameter("candidate") %>';
			addCookie(name, value, 24);
		}catch(ex){}
	</script>
	<%
}else{
%>
<script language="JavaScript" type="text/javascript">
function toLogin(){
	var value=getCookie('candidate');
	if(value==null || value==''){
		value='PCC';
	}
	window.location.href='index.jsp?candidate=' + value;
}
//alert("You are not authorised to view this page or Session Expired, please re-login!");
toLogin();
</script>
<%
}

if(logchk.getSystemMaintenance()==false) {
%>
<body> 
	<div class="toolbar"> 
		<h1 id="pageTitle"></h1> 
	</div>
	
	<form id="login" title="3-Sixty Profiler" class="panel" name="loginForm" action="index.jsp" method="post" selected="true"> 
		<fieldset>
			<div class="row"> 
				<label style="float:right">Log in</label> 
				<input type="text" name="txtUsername" placeholder="Your login" />
			</div>
			<div class="row"> 
				<label>Password</label> 
				<input type="password" name="txtPass" placeholder="Your password" /> 
			</div> 
		</fieldset>
		<a class="whiteButton" href="javascript:validate(this.loginForm);">Login</a>
		<%
			Calendar c = Calendar.getInstance();
			int iYear = c.get(c.YEAR);	
		%>
		<br>
		<p style='font-size:12px;text-align:center;color:navy;' align='center'>
			Copyright &copy; <%=iYear%>&nbsp; Pacific Century Consulting Pte Ltd. All Rights Reserved.
		</p>
	</form>
</body>
<%
}else{
%>
<body> 
	<div class="toolbar"> 
		<h1 id="pageTitle"></h1> 
	</div>
	
	<form id="login" title="3-Sixty Profiler" class="panel" name="loginForm" action="index.jsp" method="post" selected="true"> 
		<fieldset>
			<div class="row"> 
				<label><font color=red>System is currently under daily maintenance (midnight to 1AM). We are sorry for the inconvenience.</font></label> 
			</div>
			<div class="row"> 
				<label>Log in</label> 
				<input type="text" name="txtUsername" placeholder="Your login" disabled>
			</div>
			<div class="row"> 
				<div style="float:left"><label>Password</label></div> 
				<input type="password" name="txtPass" placeholder="Your password" disabled> 
			</div> 
		</fieldset>
		<a class="whiteButton" href="javascript:validate(this.loginForm);">Login</a>
		<%
			Calendar c = Calendar.getInstance();
			int iYear = c.get(c.YEAR);	
		%>
		<br>
		<p style='font-size:12px;text-align:center;color:navy;' align='center'>
			Copyright &copy; <%=iYear%>&nbsp; Pacific Century Consulting Pte Ltd. All Rights Reserved.
		</p>
	</form>
</body> 
<%
}
%>
</html>