<%@ page import="java.sql.*,
                 java.io.*,
                  CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
                   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  


<html>
<head>
<title>3-Sixty Profiler Maintenance System</title>
</head>

<body>
<script language="javascript">
	function change(form,value){
		form.action="indexAdminMaintenance.jsp?change="+value;
		form.method="post";
		form.submit();
	}
</script>
<%
if(request.getParameter("change") != null) {
	int maintenance = Integer.parseInt(request.getParameter("change"));
	logchk.changeSystemMaintenance(maintenance);
	
}
%>

<form name = "changeMaintenance">
This is used by super admin to change maintenance notification.
System is currently 
<%
if(logchk.getSystemMaintenance()==false) {
%>
	NOT under maintenance and working fine. <br><br>
	<input type = "button" value ="Change to Maintenance" name="btnChange" onClick="change(this.form, 1)">
<%
} else{
%>
	UNDER MAINTENANCE. <br><br>
	<input type="button" name="btnChange" value="Change to Normal" onclick = "change(this.form,0)">
<%
}
%>

</form>
</body>

</html>