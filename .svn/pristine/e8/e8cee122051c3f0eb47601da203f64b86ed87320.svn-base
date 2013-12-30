<%@ page import="java.sql.*,
                 java.text.DateFormat,
                 java.util.Vector,
                 java.io.*,
                 java.lang.String"%>  
<jsp:useBean id="assign" class="CP_Classes.AssignTarget_Rater" scope="session"/>                  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

function proceed(form)
{
	form.action="FixRaterCode.jsp?proceed=1";
	form.method="post";
	form.submit();
}
</script>
<body style="text-align: left">
<%
if(request.getParameter("proceed") != null)
{
	if(Integer.parseInt(request.getParameter("proceed")) == 1)
	{
		/* NEVER CHANGE THIS VALUE WITHOUT UNDERSTANDING! IT WILL CAUSE SYSTEM INSTABILITY */
		assign.FixRaterCode(438, 1);
		
		out.println("All Raters Code fixed");
	}
}
%>

<form method="POST" action="FixRaterCode.jsp">
	<p><input type="button" value="Fix All Rater Code" name="cmdSubmit" onclick="proceed(this.form)"></p>
</form>
</body>
</html>