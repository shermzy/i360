<%@ page pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
	<link rel=apple-touch-icon href="icon.png" />
	<link rel="shortcut icon" href="icon.png" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/> 
	<title>3-Sixty Profiler - Welcome - iPhone Version</title>
	<link rel="stylesheet" href="iui/iui.css" type="text/css" /> 
	<link rel="stylesheet" href="iui/t/default/default-theme.css"  type="text/css"/> 
	<script type="application/x-javascript" src="iui/iui.js"></script>
</head> 
<%
	Calendar c = Calendar.getInstance();
	int iYear = c.get(c.YEAR);	
%>
<body>
	<div class="toolbar"> 
		<h1 id="pageTitle"></h1>
		<a class="backButton" href="#" onclick="window.location.href='surveyIndex.jsp'">&nbsp;Back</a>
		<a title="Log out" class="logoutButton" href="#" onclick="window.location.href='login.jsp?logout=1'">Exit</a>
	</div>
	<ul id="about" title="About 3-Sixty" selected="true">
		<li>
			<br/>
			<p style="font-size:14px;">&nbsp;&nbsp;&nbsp;3-Sixty Profiler ® is a performance management and 360° feedback management software designed to help deliver real time information on employee performance and skills gaps. Login to proceed.</p>
			<br/>
			<p style='font-size:12px;text-align:center;color:navy;' align='center'>
				Copyright &copy; <%=iYear%>&nbsp; Pacific Century Consulting Pte Ltd. All Rights Reserved.
			</p>
		</li>
	</ul>
</body> 
</html>