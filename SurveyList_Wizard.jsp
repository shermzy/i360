<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
*"%>  
                 
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                        
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="RptX" class="CP_Classes.Report_DeleteSurvey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="JP" class="CP_Classes.JobPosition" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
<!--
// -->
var x = parseInt(window.screen.width) / 2 - 220;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 50;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function check(field)
{
	var check= false;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
			check = true;
	}
	if(field != null)
	{
		if(field.checked)
		check = true;
	}

	return check;
		
}
function AssignTarget(form,field)
{
	if(check(field))
	{
		form.action="SurveyList_Wizard.jsp?assign=1";
		form.method="post";
		form.submit();	 
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}
}

function Add(form,field, type) 	
{ 	
	if(type == 1)
	{
		if(field.value == 0) {
			var myWindow=window.open('Survey_AddSurvey_Org.jsp','windowRef','scrollbars=no, width=500, height=100');
			myWindow.moveTo(x,y);
		} 
		else 
		{
			form.action="SurveyList_Create.jsp?add=1";
			form.method="post";
			form.submit();
		}
	}
	else if(type == 2)
	{
		var myWindow=window.open('Survey_CopyJobCat.jsp?copyJobCat=' + field.value,'windowRef','scrollbars=no, width=500, height=200');
		myWindow.moveTo(x,y);	
	}

}


//var flag = false;	
function survey(form,field)
{
	if(check(field))
	{
		form.action="SurveyList_Wizard.jsp?survey=1";
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function process(form,field)
{
	if(check(field))
	{
		form.action="SurveyList_Wizard.jsp?process=1";
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function showJob(form)
{
	form.action="SurveyList_Wizard.jsp?job=1";
	form.method="post";
	form.submit();
}


function copy(form,field)
{
	if(check(field))
	{
		form.action="SurveyList_Wizard.jsp?copy=1";
		form.method="post";
		form.submit();	
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

function DeleteAway(form,field)
{
	if(check(field))
	{
		if(confirm("Delete Survey?"))
		{
			form.action="SurveyList_Wizard.jsp?del=1";
			form.method="post";
			form.submit();	
		}
	}
	else
	{
		alert("<%=trans.tslt("Please select a survey")%>");
	}

}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="SurveyList_Wizard.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	


</SCRIPT>


<body>
<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  { 

assignTR.setGroupID(0);

if(request.getParameter("job") != null)
{
	int var1 = new Integer(request.getParameter("selJob")).intValue();
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	System.out.println("James : Date 02-Nov .\nDebug information : "+var1+" : "+var2);
	CE_Survey.set_survOrg(var2);
	CE_Survey.setJobPos_ID(var1);
}

if(request.getParameter("proceed") != null)
{ 
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	CE_Survey.set_survOrg(var2);
}

if(request.getParameter("survey") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>
	<jsp:forward page="SurveyDetail.jsp"/>	
	<%
}

if(request.getParameter("process") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>
	<jsp:forward page="SurveyResult.jsp"/>	
	<%
}

if(request.getParameter("add") != null)
{
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setSurvey_ID(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
	CE_Survey.set_survOrg(var2);
%>
	<script>
	window.location = "SurveyDetail.jsp";
</script>
<%
}	

if(request.getParameter("assign") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1);
	assignTR.setGroupID(0);
	assignTR.setTargetID(0);
	CE_Survey.setJobPos_ID(0);
	CE_Survey.setSurveyStatus(0);
	CE_Survey.setPurpose(0);
	CE_Survey.setCompetencyLevel(0);
	assignTR.set_selectedTargetID(0);
	assignTR.set_selectedAssID(0);
%>
	<jsp:forward page="AssignTarget_Rater.jsp"/>	
	<%
}

if(request.getParameter("copy") != null)
{
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	CE_Survey.setSurvey_ID(var1); 
%>	
<script>	
	window.open('CopySurvey.jsp','windowRef','scrollbars=no, width=300, height=300');
</script>
<%    
}

if(request.getParameter("del") != null)
{
	int FKOrg = 0;
	int var1 = new Integer(request.getParameter("Survey")).intValue();
	/********************
	* Edited by James 02 Nov 2007
	************************/
	votblSurvey rs_Survey = CE_Survey.getSurveyDetail(var1);
		
	FKOrg = rs_Survey.getFKOrganization();
			
	
	String filename = RptX.DeleteReport(var1);
	CE_Survey.addDeletedSurvey(var1, filename, FKOrg, logchk.getCompany(), logchk.getPKUser());

	
}

/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
					
	if(request.getParameter("sorting") != null)
	{	 
		int type = new Integer(request.getParameter("sorting")).intValue();
		int toggle = CE_Survey.getToggle();	//0=asc, 1=desc
		CE_Survey.setSortType(type);
		
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
			
		CE_Survey.setToggle(toggle);
	} 
	else
	{
		CE_Survey.setSortType(1);
	}

%>

<form name="SurveyList" action="SurveyList_Wizard.jsp" method="post">
<table border="0" width="610" cellspacing="0" cellpadding="0">
	<tr>
				<td width="154">
				<p align="left">
				<b>
				<font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></b></td>
		<td width="172"><font face="Arial"><span style="font-size: 11pt">
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value ="0"  selected><%=trans.tslt("List all Organization")%></option>

<%
	Vector vOrg = logchk.getOrgList(logchk.getCompany());
	
	for(int i=0; i<vOrg.size(); i++)
	{
		votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
		int PKOrg = vo.getPKOrganization();
		String OrgName = vo.getOrganizationName();
	
		if(CE_Survey.get_survOrg() == PKOrg)
		{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%		}
		else	
		{
%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%		}	
	}
%>
</select></span></font></td>

				<td width="154">&nbsp;
				</td>
		<td width="172">&nbsp;</td>

		<td width="99">&nbsp;
</td>

			</tr>
	<tr>
				<td width="20%">&nbsp;</td>
				<td width="27%">&nbsp;</td>
				<td width="154">&nbsp;
				</td>
		<td width="172">&nbsp;</td>

		<td width="99">&nbsp;
</td>

			</tr>
	<tr>
				<td width="11%"><b><font face="Arial" size="2"><%=trans.tslt("Job Position")%>: </font></b></td>
				<td width="27%"><font face="Arial">
				<span style="font-size: 11pt">
				<select size="1" name="selJob" >

	<option value ="0" selected><%=trans.tslt("List all survey")%></option>
<%

	Vector vJobPos = JP.getAllJobPositions(logchk.getCompany(), CE_Survey.get_survOrg());
	for(int i=0; i<vJobPos.size(); i++)
	{
		votblJobPosition vo = (votblJobPosition)vJobPos.elementAt(i);
		
		int JobPosition_ID = vo.getJobPositionID();
		String JobPosition_Desc = vo.getJobPosition();

		if(CE_Survey.getJobPos_ID() == JobPosition_ID)
		{
%>			
<option value=<%=JobPosition_ID%> selected><%=JobPosition_Desc%></option>
<%		}
		else
		{
%>			<option value=<%=JobPosition_ID%>><%=JobPosition_Desc%></option>
<%		}	
		
	}%>				

				</select></span></font></td>
				<td width="154">
				<font size="2">
   &nbsp;<input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onclick="showJob(this.form)" style="float: right"></td>
		<td width="172">&nbsp;</td>

		<td width="99">
<table border="0" width="96%" cellspacing="0" cellpadding="0">
	<tr>
		<font size="2">
   
		<td width="71%"> </td>
	</tr>
</table>


				</td>

			</tr>
	<tr>
				<td width="14%">&nbsp;</td>
				<td width="22%">&nbsp;</td>
				<td width="150">&nbsp;</td>
		<td width="164">&nbsp;</td>

		<td width="79">&nbsp;</td>

			</tr>
	<tr>
				<td width="14%"> <font face="Arial" size="2">
   
    			<p align="left">
    			<a onclick="clickAll(this.form)">
				&nbsp;</a></td>
				<td width="22%">&nbsp;</td>
				<td width="150">&nbsp;</td>
		<td width="164">&nbsp;</td>

		<td width="79">&nbsp;</td>

			</tr>
	</table>
<table width="610" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#3399FF">
<th bgcolor="#000080" width="854"><b><font style='color:white' size="2"><%=trans.tslt("Survey")%></font></b></th>
</table>

  <div style='width:627px; height:201px; z-index:1; overflow:auto'>
<table width="610" border="1" style='font-size:11.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
<tr>
<th width="54%" bgcolor="#000080" style="border-right-style: none; border-right-width: medium" colspan="2"> 
   
    	<b><font size="2" style="color: white"><a href="SurveyList_Wizard.jsp?sorting=1"><font style='color:white'><u><%=trans.tslt("Name")%></u></a></font></b></th>
<font face="Tahoma">
<th width="70" bgcolor="#000080"><b><font style="color: white" size="2"><a href="SurveyList_Wizard.jsp?sorting=2"><font style='color:white'><u><%=trans.tslt("Opening Date")%></u></a></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><a href="SurveyList_Wizard.jsp?sorting=3"><font style='color:white'><u><%=trans.tslt("Deadline")%></u></a></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><a href="SurveyList_Wizard.jsp?sorting=4"><font style='color:white'><u><%=trans.tslt("Closed Date")%></u></a></font></b></th>
<th width="100" bgcolor="#000080"><b><font style='color:white'><a href="SurveyList_Wizard.jsp?sorting=5"><font style='color:white'><u><%=trans.tslt("Status")%></u></a></font></b></th>
<th width="82" bgcolor="#000080"></font>
<span style="font-weight: 400"><b><font size="2" style="color: white"><a href="SurveyList_Wizard.jsp?sorting=6"><font style='color:white'><u><%=trans.tslt("Organization")%></u></a></font></b></span></font></th>

  <%	
	int Survey_ID=0;
	int JobPosID1=0;
	String Survey_Name=" ";
	String month = " ";
	String DateOpened = " ";
	String DeadlineDate = " ";
	int db_SurveyStatus=0;
	String SurveyStatus=" ";
	
	SimpleDateFormat month_view= new SimpleDateFormat ("MM/yyyy");
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
	
	/********************
	* Edited by James 02 Nov 2007
	************************/
		Vector rs_SurveyDetail = CE_Survey.getRecord_Survey(logchk.getCompany(), CE_Survey.get_survOrg(), CE_Survey.getJobPos_ID());		
		//while(rs_SurveyDetail.next())
		for(int i=0;i<rs_SurveyDetail.size();i++)
		{
	       votblSurvey vo=(votblSurvey)rs_SurveyDetail.elementAt(i);
			int Surv_ID = vo.getSurveyID();
			String Surv_Name = vo.getSurveyName();
			DateOpened = vo.getDateOpened();
			DeadlineDate = vo.getDeadlineSubmission();
			db_SurveyStatus = vo.getSurveyStatus();
			String anal_Date = vo.getAnalysisDate();
			String OrganizationName = vo.getOrganizationName();
				
		if(anal_Date == null)
			anal_Date = " ";
					
		if(db_SurveyStatus == 1)
			SurveyStatus ="Open";
		else if(db_SurveyStatus == 2)
			SurveyStatus ="Closed";										
		else if(db_SurveyStatus == 3)
			SurveyStatus ="Not Commissioned";
		else if(db_SurveyStatus == 0)
			SurveyStatus ="Not Available";
%>

<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
    	
				<td width="4%" >
				<p align="center">
				<font face="Arial"><span style="font-size: 10pt">
				<font color="#FFC36B" size="3">
				<input type="radio" value=<%=Surv_ID%> name="Survey" ondblclick="copy(this.form,this.form.Survey)"></font></span></font></td>
				<td width="50%" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2">&nbsp<%=Surv_Name%></td>
				

    <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2"><%=DateOpened%></td>
    <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2"><%=DeadlineDate%></td>
    <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2"><%=anal_Date%>&nbsp</td>
    <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2"><%=SurveyStatus%></td>
     <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><font size="2"><%=OrganizationName%></font></td>

  </tr>
  <%	}	%>
</table>
</div>


<table border="0" width="610" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="44%">
		<font size="2">
   
		<p align="center">
		</td>
		<td width="30%">
		<p align="center">
		<font size="2" face="Tahoma">
   
		<input type="button" value="<%=trans.tslt("Add New Survey")%>" name="btnAddSurvey" onclick="Add(this.form,this.form.selOrg,1)" style="float: right"></td>
		<td align="right" width="26%">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Copy From Job Category")%>" name="btnCopyJobCat" onclick="Add(this.form,this.form.selOrg,2)"></td>

		<td align="right" width="26%">
		<font size="2">
   
    	<input type="button" value="<%=trans.tslt("Copy Survey")%>" name="btnCopy" onclick="copy(this.form,this.form.Survey)"></td>
	</tr>
</table>

<%}	%>
</form>
<p></p>
<%@ include file="Footer.jsp"%>
</font>
</body>


</html>