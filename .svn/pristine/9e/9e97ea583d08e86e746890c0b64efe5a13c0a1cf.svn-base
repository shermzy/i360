<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  
                 
<jsp:useBean id="Reminder" class="CP_Classes.ReminderCheck" scope="session"/>
<jsp:useBean id="MailGenerator" class="CP_Classes.MailGenerator" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<HTML>

<SCRIPT LANGUAGE=JavaScript>

var timerID = null
var timerRunning = false
var Time_To_Send_Reminder = 1; //Hour. Default: 1AM

function stopclock()
{
	if(timerRunning)
		clearInterval(timerID)
	timerRunning = false
}

function startclock()
{
	// Make sure the clock is stopped
	stopclock()
	//1 sec = 1000
	//1 min = 60000
	//1 hour= 3600000
	timerID = setInterval("showtime()",30000)//600000//7200000)
	
	timerRunning = true
}

function showtime()
{

	var now = new Date();
	var hours = now.getHours();
	var minutes = now.getMinutes();
	var seconds = now.getSeconds();
			
	if(hours == Time_To_Send_Reminder)
	{
		window.document.frmTimer1.action="Timer.jsp?reminder=1";
		window.document.frmTimer1.method="post";
		window.document.frmTimer1.submit();
		
	}
	
	if(hours != Time_To_Send_Reminder)
	{
		window.document.frmTimer1.action="Timer.jsp?reminder=2";
		window.document.frmTimer1.method="post";
		window.document.frmTimer1.submit();
	}

}

</SCRIPT>
<BODY onLoad="startclock()">
<%
	/*
	This Timer function is used to detect when to send Email reminders and notifications.
	Two main features in this function:
	1. REMINDER & NOTIFICATION EMAILS
		Timer will only run the ReminderCheck if HOUR = Time_To_Send_Reminder (default 1AM)
	2. GENERAL EMAILS
		Timer will check during every interval of specified time (default 30sec) and send
		emails from those inside tblEmail
	*/
	
	int nomor =0;
	Date today;
	today = new Date();
	
	if(request.getParameter("reminder") != null)
	{
    	nomor = new Integer(request.getParameter("reminder")).intValue();
    }
    System.out.println(" Reminder.getTime_Sent() "+ Reminder.getTime_Sent());
    System.out.println("Nomor = " + nomor);
    System.out.println("Get Time Sent = " + Reminder.getTime_Sent());
	if( nomor == 1 && Reminder.getTime_Sent() == false)
	{
		//Send Reminders (default 1AM)
		Reminder.check();	
		System.out.println("Check for reminder: "+today);
		Reminder.setTime_Sent(true);
	}
	
	if(nomor == 2)
	{
		//Send emails from tblEmail
		Reminder.setTime_Sent(false);
		MailGenerator.check();
	}

	System.out.println("setTime_Sent "+Reminder.getTime_Sent());
%>

<form method="POST" name="frmTimer1" action="Timer.jsp">

<table border="1" width="53%" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td width="75%" bgcolor="#000080">
		<p align="center"><b><font face="Arial" size="2" color="#FFFFFF"><%=trans.tslt("This is an email generator page")%>.</font></b></td>
	</tr>
</table>

</form>
</BODY>
</HTML>