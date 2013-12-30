<%@ page import="java.sql.*,
                 java.io.*,
                 javax.servlet.http.HttpSession,
                 javax.servlet.http.HttpSessionBindingListener" %>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

var x = parseInt(window.screen.width) / 2 - 30;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 300;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function windowOpen() 
{
	var myWindow=window.open('tutorial.jsp','','scrollbars=no, width=510, height=495');
	myWindow.moveTo(x,y);
	//window.open('tutorial.htm','','scrollbars=no, width=510, height=495');
}

function windowOpen2() 
{
	var myWindow=window.open('tutorial_org.jsp','','scrollbars=no, width=520, height=450');
	myWindow.moveTo(x,y);
}

function windowOpen3() 
{
	var myWindow=window.open('tutorial_survey.jsp','','scrollbars=no, width=520, height=430');
	myWindow.moveTo(x,y);
}

function displayWarningSave_a(){
	  alert('Your session will expire in 10 minutes. Please click the "Save" button at the bottom of the page to save your responses.');
}
function displayWarningSave_b(){
	  alert('Your session will expire in 5 minutes. Please click the "Save" button at the bottom of the page to save your responses.');
}
function displayWarning(){
    alert('Warning, your session is about to expire. Any unsaved responses WILL NOT be captured in the system.');
}
//let's call this function after 29 minutes of loading this page to the browser
setTimeout("displayWarning()",29*60*1000); 
setTimeout("displayWarningSave_a()",20*60*1000);
setTimeout("displayWarningSave_b()", 25*60*1000)
</SCRIPT>
<head>
<base target="main">
</head>
<body bgcolor="#FFFFFF" bottommargin="0">

<table border="0" width="610" style='font-size:10.0pt;font-family:Arial'>
	<tr>
		<td><font face="Arial" color="#000080"><b>
		<%= trans.tslt("Welcome") %>!
		</b></font></td>
	</tr>
	
	<font size="2" face="Arial">
	<%		
	if(logchk.getUserType() == 1)
	{
%>		<jsp:forward page="OrganizationList.jsp"/>	
<%	}
	else if(logchk.getUserType() == 2)
	{
%>		</font>
		<tr>
		<td>&nbsp;</td>
		</tr>
		<tr>
		<td><%= trans.tslt("3-Sixty Profiler is a thoroughly researched, well proven expert application for") %>
			<%= trans.tslt("managing and analysing multi-rater feedback implementation. Its resulting outputs can") %>
			<%= trans.tslt("be used as input for development, succession management, performance appraisal") %>,
			<%= trans.tslt("performance feedback, training analysis, potential assessment etc") %>.</td>
		</tr>
		<tr>
		<td>&nbsp;</td></tr>
		<tr>
		<td>
		<%= trans.tslt("As an administrator, you will be expected to") %>:	
		</td></tr>
		<tr>
		<td>
		
		<ul>
			<li><%= trans.tslt("Set up the user database") %></li>
			<li><%= trans.tslt("Set up 3-Sixty Profiling surveys") %></li>
			<li><%= trans.tslt("Assign surveys to respondents") %></li>
			<li><%= trans.tslt("Process data collected") %></li>
			<li><%= trans.tslt("Generate reports") %></li>
		</ul>
		</table>
	
		<table border="0" width="100%">
			<tr>
				<td><font size="2" face="Arial"><b><%= trans.tslt("Step by step Guide") %></b> 
				(<%= trans.tslt("Click if accessing first time or need help") %>).</font></td></tr><tr>
				<td>
				<ul>
					<%
					if(logchk.getCompany() == 1) //PCC, Might need to do some modification if it's consulting company
					{%>
					<li><font face="Arial" size="2"><a href="Login.jsp" onclick="windowOpen2()" topmargin="0" leftmargin="0"><%= trans.tslt("Accessing first time as an organisation") %></a></font></li>
					<%}%>
					<li><font face="Arial" size="2"><a href="Login.jsp" onclick="windowOpen3()" topmargin="0" leftmargin="0"><%= trans.tslt("Setting up a new survey") %></a></font></li></ul></td></tr></table></td></tr><%
	}
	else if(logchk.getUserType() == 4)
	{	%>
		<tr>
		<td>

		<table border="0" width="580" style='font-size:10.0pt;font-family:Arial' id="table1">
			<tr>
			<td>
		
		<font face="Arial" size="2">
	<% 	if(logchk.getOrgCode() != null && (logchk.getOrgCode().equals("KPRBC") || logchk.getOrgCode().equals("DEMO") || !logchk.getOrgCode().equals("Allianz")) ) 
	   	{
	%>		<font face="Arial" style="font-size: 10pt">	
<p align=justify>As a rater, you are expected to provide vital feedback to your colleague(s) who are target(s) for the survey. 
Your input will be used for development feedback. Please be honest with your feedback. Your inputs will be anonymous as 
only aggregated scores will be presented to the target. However, please be accountable for your inputs as qualitative comments will not be screened for "accidental disclosure".
</p>
<!-- By Hemilda  18/06/2008 remove some statement Demographic Menu -->

	<p>Click on the Rater's To-Do List option on the Side menu bar to provide feedback.
	</p>	
			</font></td></tr>
			
	<%  }
		else
		{
			%><b>Purpose:</b>
			<br>
			This Multi-Source Feedback <b><span style="font-size: 10.0pt; font-family: Arial">(MSF)</span></b><font face="Arial" size="2"> exercise </font><span style="font-size: 10.0pt; font-family: Arial">is part of 
			the &quot;</span><span style="font-size: 10.0pt; font-family: Arial; color: black">Managing 
			People/Self&quot; module of the Asia Future Leader (AFL) Program - 2005/06.&nbsp; </span><span style="font-size: 10.0pt; font-family: Arial">The purpose of this exercise 
			is to help the AFL participants identify and understand their emerging and 
			established competency strengths, which will be shared with them by an assigned 
			development coach during the AFL program. <b>For the results of the feedback 
			process to be useful, it is important that an honest and accurate response is 
			provided</b></span><font face="Arial" size="2">.</font><p>
			<font face="Arial" size="2">
			<b>Directions:</b> <br>
			</font><font color="#FF0000">
			<span style="font-size: 10.0pt; font-family: Arial; ">To 
			start the MSF questionnaire, please click on <b>&#39;Rater&#39;s To-Do-List&#39;</b> on the 
			left navigation bar and then select <b>&#39;Multi-Source Feedback for AFL&#39;</b></span></font><span style="font-size: 10.0pt; font-family: Arial; color: black">. A 
			pop-up window will appear to request for your demographic information. Once 
			completed, you are directed to the questionnaire.<br>
			<br>
			Next, please rate how well each behavior describes the person you are rating.&nbsp;
			<u>Consider how often the person shows this behaviour when it is needed or 
			desired in relevant situations</u>.&nbsp;Please select the description that best 
			represents your own observations of the person</span><font face="Arial" size="2">:</font></p><ul>
			<li><span style="font-size: 10.0pt; font-family: Arial; color: black">
			Describes the person <b><u>not at all</u></b> - rarely or never acts 
			this way</span></li><li><span style="font-size: 10.0pt; font-family: Arial; color: black">
			Describes the person <b><u>to some extent</u></b> - sometimes acts this 
			way</span></li><li><span style="font-size: 10.0pt; font-family: Arial; color: black">
			Describes the person <b><u>to a large extent</u></b> - often acts this 
			way</span></li><li><span style="font-size: 10.0pt; font-family: Arial; color: black">
			Describes the person <b><u>extremely well</u></b> - consistently acts 
			this way</span></li><li><span style="font-size: 10.0pt; font-family: Arial; color: black">
			Unable to rate</span></li></ul><p>After you have completed the rating of the items in each category, please 
			add in specific examples of how the person behaved in situations where such 
			behaviours were needed or desired (optional).<br>
			<br>
			You will need approximately 30 minutes to complete the questionnaire.<%	
		}	//if logchk.getOrgCode() != null
	}	//else if(logchk.getUserType() == 4) 
	/*
	 * Change : Put checking for organization in login page
	 * Reason : for organization checking
	 * Add by : Johanes
	 * Add on : 26/10/2009
	 */	
	if(logchk.getOrg()!= 1)
		CE_Survey.set_survOrg(logchk.getOrg());
%>
	</table>
	&nbsp;
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<%@ include file="Footer.jsp"%>

	</body>
	</html>