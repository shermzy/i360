<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>                
<jsp:useBean id="TimerCalc" class="CP_Classes.TimerSchedule.ScheduleTask" scope="session"/>


<HTML>

<BODY >
<%
TimerCalc.TimerRunning();
	

%>

<form method="POST" name="frmTimerCalc" >

<table border="1" width="53%" bgcolor="#FFFFCC" bordercolor="#3399FF">
	<tr>
		<td width="75%" bgcolor="#000080">
		<p align="center"><b><font face="Arial" size="2" color="#FFFFFF">This is Timer For Calculation</font></b></td>
	</tr>
</table>

</form>
</BODY>
</HTML>