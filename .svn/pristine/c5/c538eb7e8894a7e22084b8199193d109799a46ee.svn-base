<%@ page import="java.sql.*,
                 java.io.*,
                 javax.servlet.http.HttpSession,
                 javax.servlet.http.HttpSessionBindingListener" %>

<html>
<jsp:useBean id="login" class="CP_Classes.Login" scope="session"/> 

<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

var myinterval = window.setInterval('displayTimeout()',1000000);

function displayTimeout()
{
	var now = new Date();
	var total = (now - <%=request.getSession().getLastAccessedTime()%>) / 1000
	alert(total + "\nMax Timeout = " + <%=request.getSession().getMaxInactiveInterval()%>)
}
</SCRIPT>

<body bgcolor="#FFFFFF" bottommargin="0">
SESSION TEST
	<%
	System.out.println("login = " + login.getOrg());
	System.out.println("session time out in "+ request.getSession().getMaxInactiveInterval());
	if(login.getOrg() != 0)
	{
		login.setOrg(1);
		System.out.println("INSIDE login = " + login.getOrg());
		//request.getSession().setMaxInactiveInterval(60);

	}
	else
	{
		System.out.println("login = 0");
		login.setOrg(1);		
		System.out.println("(SET) login = " + login.getOrg());
		request.getSession().setMaxInactiveInterval(1);
	}
	
	/*
	System.out.println("Session.getAttribute = " + session.getAttribute("test"));
	if(session.getAttribute("test") != null)
	{
		System.out.println("Session test = " + session.getAttribute("test"));
		request.getSession().setMaxInactiveInterval(1);
	}
	else
	{
		System.out.println("Session test = null");
		session.setAttribute("test", "1");
		System.out.println("(SET) Session test = " + session.getAttribute("test"));
	}
	*/
		
	//System.out.println("Creation Time = " + request.getSession().getCreationTime());
	//System.out.println("Last Accessed = " + request.getSession().getLastAccessedTime());
	
	//long total = (System.currentTimeMillis() -request.getSession().getLastAccessedTime()) / 1000; 
	//System.out.println("Total = " + total); 
	
	//request.getSession().setMaxInactiveInterval(60);
	//System.out.println("MAX = " + request.getSession().getMaxInactiveInterval());
	
	%>
</body>
</HTML>
