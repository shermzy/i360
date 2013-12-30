<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.util.Calendar,
                 java.text.*,
                 java.lang.String,
*"%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="JobDetail" class="CP_Classes.JobCategoryDetail" scope="session"/>
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<jsp:useBean id="JP" class="CP_Classes.JobPosition" scope="page"/>
<jsp:useBean id="ORG" class="CP_Classes.Organization" scope="page"/>
<%@ page import="CP_Classes.vo.votblJobPosition"%>
<%@ page import="CP_Classes.vo.voJobCategory"%>
<%@ page import="CP_Classes.vo.voCompetency"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<script language="javascript" src="script/codethatcalendarstd.js"></script>
<script language="javascript" src="script/dt_ex.js"></script>
<SCRIPT LANGUAGE=JAVASCRIPT>
function goToURL() { window.location = "SurveyList_Wizard.jsp"; }

function check(field)
{
	var result;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
		{
			result = field[i].value;
		}

		if(field[i].selected)
		{
			result = field[i].value;
		}
	}
	
	return result;
}

function validate()
{
	var checkingFlag = true;

	var iValid =0;
            x = document.SurveyDetail
	txtSurvey = x.txtSurvey.value
	selJob2 = x.selJob2.value
	DateOpened = x.DateOpened
	DeadlineDate = x.DeadlineDate
	selKBLevel = x.selKBLevel
	SurveyLevel = x.SurveyLevel
	MinGap = x.txtMinGap.value
	MaxGap = x.txtMaxGap.value
	GapValue = (MaxGap - MinGap) + 0.1
	    
	SLevel = check(SurveyLevel);
			
	if (txtSurvey == "")
	{
		alert("<%=trans.tslt("Please enter Survey Name")%>");
		checkingFlag = false;
	}
	else if(selJob2 == "")
	{
		alert("<%=trans.tslt("Please select Job Position")%>");
		checkingFlag = false;
	}
	else if(MinGap == "")
	{
		alert("<%=trans.tslt("Please enter Min Gap")%>");
		checkingFlag = false;
	}
	else if(MaxGap == "")
	{
		alert("<%=trans.tslt("Please enter Max Gap")%>");
		checkingFlag = false;
	}
	else if(GapValue < 0)
	{
		alert("<%=trans.tslt("Min Gap must be lower than Max Gap value")%>");
		checkingFlag = false;
	}
	else if(GapValue <= 0.2)
	{
		alert("<%=trans.tslt("Gap difference must be at least 0.2")%>");
		checkingFlag = false;
	}
	else if(checkdate(DateOpened,DeadlineDate) == false)
	{
		checkingFlag = false;
	}
		
 	return checkingFlag;
}

var c1 = new CodeThatCalendar(caldef1);
var c2 = new CodeThatCalendar(caldef2);
var c3 = new CodeThatCalendar(caldef3);
var c4 = new CodeThatCalendar(caldef4);

function minGap(form,field)
{	
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();

		if(flag1)
		{	
			if(field.value != null)
			{
				form.action="SurveyDetail.jsp?minGap="+field.value;
				form.method="post";
				form.submit();
			}
			else
			{
				alert("<%=trans.tslt("Please enter the MinGap value")%>");
			}
		}
	}
}

function maxGap(form,field)
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();

		if(flag1)
		{
			if(field.value != null)
			{
				form.action="SurveyDetail.jsp?maxGap="+field.value;
				form.method="post";
				form.submit();
			}
			else
			{
				alert("<%=trans.tslt("Please enter the MinGap value")%>");
			}
		}
	}
}

function ChangeLevel(form,field)
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();

		if(flag1)
		{

			level = check(field);
			
			if(level == 0)
			{
				if(confirm("<%=trans.tslt("Please note that changing from Key Behaviour level to Competency level will remove all Key Behaviours assigned to the Survey. Do you want to proceed")%>?"))
				{
					form.action="SurveyDetail.jsp?level=0";
					form.method="post";
					form.submit();
				}
				else
				{
					form.action="SurveyDetail.jsp";
					form.method="post";
					form.submit();
				}
			}
			else if(level == 1)
			{
				form.action="SurveyDetail.jsp?level=1";
				form.method="post";
				form.submit();
				
			}
		}
	}
}

function Next(field1)
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();

		if(flag1)
		{	
			if(field1.value == 2)
			{
				if(confirm("<%=trans.tslt("Please note that no more rater can be added once the survey is closed. Do you want to proceed?")%>"))
				{
					document.SurveyDetail.action="SurveyDetail.jsp?proceed=1";
					document.SurveyDetail.method="post";
					document.SurveyDetail.submit();
				}
			}
			else
			{
				document.SurveyDetail.action="SurveyDetail.jsp?proceed=1";
				document.SurveyDetail.method="post";
				document.SurveyDetail.submit();
			}

					
		}

	}
	else
	{
		document.SurveyDetail.action="SurveyDetail.jsp";
		document.SurveyDetail.submit();
	}
}

function Comp()
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();
		if(flag1)
		{
			document.SurveyDetail.action="SurveyDetail.jsp?proceed=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		//location.href('SurveyCompetency.jsp');
		location.href='SurveyCompetency.jsp';
	}
}

function Behav()
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();
		if(flag1)
		{
			document.SurveyDetail.action="SurveyDetail.jsp?behav=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		document.SurveyDetail.action="SurveyKeyBehaviour.jsp";
		document.SurveyDetail.submit();
	}
}

function Demos()
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();
		if(flag1)
		{
			document.SurveyDetail.action="SurveyDetail.jsp?demos=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		document.SurveyDetail.action="SurveyDemos.jsp";
		document.SurveyDetail.submit();
	}
}

function Rating()
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();
		if(flag1)
		{
			document.SurveyDetail.action="SurveyDetail.jsp?rata=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		document.SurveyDetail.action="SurveyRating.jsp";
		document.SurveyDetail.submit();
	}
}

function setting()
{
	if(confirm("<%=trans.tslt("Save changes")%>?"))
	{
		var flag1 = validate();
		if(flag1)
		{
			document.SurveyDetail.action="SurveyDetail.jsp?setting=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		document.SurveyDetail.action="AdvanceSettings.jsp";
		document.SurveyDetail.submit();
	}
}

function Save(form, field1)
{
	var flag1 = validate();

	if(flag1)
	{	
		if(field1.value == 2)
		{
			if(confirm("<%=trans.tslt("Please note that no more rater can be added once the survey is closed. Do you want to proceed?")%>"))
			{
				form.action="SurveyDetail.jsp?Save=1";
				form.method="post";
				form.submit();
			}
		}
		else
		{
			form.action="SurveyDetail.jsp?Save=1";
			form.method="post";
			form.submit();
		}
	}
}

function checkdate(objName1, objName2) {

	if (chkdate(objName1) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		opendate = tanggal;
	}
   
	if (chkdate(objName2) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		closedate = tanggal;
	}
	
	var correct;   
	if(doDateCheck(opendate,closedate))
	{
		correct = true;
	} else {
	   	correct = false;
	}
	return correct;
}

function chkdate(objName) 
{
	//var strDatestyle = "US"; //United States date style
	var strDatestyle = "EU";  //European date style
	var strDate;
	var strDateArray;
	var strDay;
	var strMonth;
	var strYear;
	var intday;
	var intMonth;
	var intYear;
	var booFound = false;
	var datefield = objName;
	var strSeparatorArray = new Array("-"," ","/",".");
	var intElementNr;
	var err = 0;
	var strMonthArray = new Array(12);
	strMonthArray[0] = "Jan";
	strMonthArray[1] = "Feb";
	strMonthArray[2] = "Mar";
	strMonthArray[3] = "Apr";
	strMonthArray[4] = "May";
	strMonthArray[5] = "Jun";
	strMonthArray[6] = "Jul";
	strMonthArray[7] = "Aug";
	strMonthArray[8] = "Sep";
	strMonthArray[9] = "Oct";
	strMonthArray[10] = "Nov";
	strMonthArray[11] = "Dec";
	strDate = datefield.value;
	
	if (strDate.length < 1) {
		return true;
	}
	
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
			strDateArray = strDate.split(strSeparatorArray[intElementNr]);
			if (strDateArray.length != 3) {
				err = 1;
				return false;
			} else {
				strDay = strDateArray[0];
				strMonth = strDateArray[1];
				strYear = strDateArray[2];
			}
			booFound = true;
   		}
	}
	
	if (booFound == false) {
		if (strDate.length>5) {
			strDay = strDate.substr(0, 2);
			strMonth = strDate.substr(2, 2);
			strYear = strDate.substr(4);
   		}
	}
	
	if (strYear.length == 2) {
		strYear = '20' + strYear;
	}
	
	// US style
	if (strDatestyle == "US") {
		strTemp = strDay;
		strDay = strMonth;
		strMonth = strTemp;
	}
	
	intday = parseInt(strDay, 10);
	if (isNaN(intday)) {
		err = 2;
		return false;
	}
	
	intMonth = parseInt(strMonth, 10);
	if (isNaN(intMonth)) {
		for (i = 0;i<12;i++) {
			if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
				intMonth = i+1;
				strMonth = strMonthArray[i];
				i = 12;
	   		}
		}
		
		if (isNaN(intMonth)) {
			err = 3;
			return false;
		}
	}

	intYear = parseInt(strYear, 10);
	if (isNaN(intYear)) {
		err = 4;
		return false;
	}
	
	if (intMonth>12 || intMonth<1) {
		err = 5;
		return false;
	}

	if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
		err = 6;
		return false;
	}
	
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
		err = 7;
		return false;
	}

	if (intMonth == 2) {
		if (intday < 1) {
			err = 8;
			return false;
		}
		
		if (LeapYear(intYear) == true) {
			if (intday > 29) {
				err = 9;
				return false;
			}
		} else {
			if (intday > 28) {
				err = 10;
				return false;
			}
		}
	}

	if (strDatestyle == "US") {
		tanggal = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
	}
	else {
		tanggal = intday + " " + strMonthArray[intMonth-1] + " " + strYear;
	}
	
	return true;
}

function LeapYear(intYear) 
{
	if (intYear % 100 == 0) {
		if (intYear % 400 == 0) { return true; }
	}
	else {
		if ((intYear % 4) == 0) { return true; }
	}
	return false;
}
function doCloseDateCheck(closedate){
    
    var today=new Date();
    var strMonthArray = new Array(12);
	strMonthArray[0] = "Jan";
	strMonthArray[1] = "Feb";
	strMonthArray[2] = "Mar";
	strMonthArray[3] = "Apr";
	strMonthArray[4] = "May";
	strMonthArray[5] = "Jun";
	strMonthArray[6] = "Jul";
	strMonthArray[7] = "Aug";
	strMonthArray[8] = "Sep";
	strMonthArray[9] = "Oct";
	strMonthArray[10] = "Nov";
	strMonthArray[11] = "Dec";
         
     var tmptoday=today.getDate() + " " + strMonthArray[today.getMonth()]+" " + (today.getYear());

    if(Date.parse(tmptoday)<=Date.parse(closedate)){
        
       
        return true;
     } else {
        return false;
     }
    
}
function doDateCheck(opendate, closedate) 
{
	if (Date.parse(opendate) <= Date.parse(closedate)) {
		if(doCloseDateCheck(closedate)){
                     return true;   
                     }
                else{
                    alert("<%=trans.tslt("Deadline must be later than today date")%>.");
                    return false;
                    }
                     
	} else {
		if (opendate == "" || closedate == "") 
			alert("<%=trans.tslt("Both dates must be entered")%>.");
		else 
			alert("<%=trans.tslt("Opened date must be earlier than Deadline date")%>.");
		return false;
	} 
}
//  End -->


	</script>
<body>
<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 


SimpleDateFormat formatter_db1 = new SimpleDateFormat ("dd/MM/yyyy");
SimpleDateFormat formatter_db2= new SimpleDateFormat ("yyyy/MM/dd");
SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");

if(request.getParameter("setting") != null || request.getParameter("maxGap") != null || request.getParameter("minGap") != null || request.getParameter("Save") != null || request.getParameter("proceed") != null || request.getParameter("level") != null || request.getParameter("behav") != null || request.getParameter("demos") != null || request.getParameter("rata") != null)
{	
	Date hari_ini;
	hari_ini = new Date();
	
	int NA_Included = 0;
	int	XComment_Included = 0;
	int JobFutureID = 0;
	int TimeFrameID = 0;
	int SurveyID = CE_Survey.getSurvey_ID();
	String month = "NA";
	String DeadlineDate = request.getParameter("DeadlineDate");
	int selStatus = 0;
	String selStatus_str = request.getParameter("selStatus");
	String DateOpened = request.getParameter("DateOpened");
	String AnalysisDate =" ";
	String MinGap = "";
	String MaxGap = "";
	int FKCompanyID = logchk.getCompany();
	
	MinGap = request.getParameter("txtMinGap");
	MaxGap = request.getParameter("txtMaxGap");
   
	String SurveyName = "";
	int JobPosID1 = 0;
	int Reliability = 0;
	String str_NA_Included = "";
	int LevelofSurvey = 0;
	String db_Comment_Included = "";
	
	if(selStatus_str != null)
		selStatus = new Integer(selStatus_str).intValue();
	else
		selStatus = 2;
	
        /****************************************
        *UPDATED  by Thant Thura Myo
        *date 17 Jan 2008

        ******************************************/
       Date dToday=new Date();
       dToday=new Date(day_view.format(dToday));
       Date dDeadlineDate=new Date(DeadlineDate);
       String selOriginalVal=request.getParameter("selOriginalVal");
       System.out.println("sel orig val="+ selOriginalVal);
       if((dToday.before(dDeadlineDate)||dToday.equals(dDeadlineDate) )&& selOriginalVal.equals("2")){
 
         CE_Survey.setSurveyStatus(1);
         selStatus=1;
         
       }
	if(CE_Survey.getSurveyStatus() != 2 && selStatus == 2)
	{
		AnalysisDate = day_view.format(hari_ini);
		CE_Survey.setSurveyStatus(2);
	}
	else if(CE_Survey.getSurveyStatus() == 2 && selStatus == 2)
	{
		AnalysisDate = day_view.format(hari_ini);
		CE_Survey.setSurveyStatus(2);
	}
							
	if(CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(SurveyID))
		{ 
			SurveyName = request.getParameter("txtSurvey");
			JobPosID1 = new Integer(request.getParameter("selJob2")).intValue();
			Reliability = new Integer(request.getParameter("Reliability")).intValue();
			str_NA_Included = request.getParameter("NA_Included");
			LevelofSurvey = new Integer(request.getParameter("SurveyLevel")).intValue();
			db_Comment_Included = request.getParameter("Comment_Included");
		
			CE_Survey.setCompetencyLevel(LevelofSurvey);
			
			Date d1a = formatter_db1.parse(DateOpened);
			DateOpened = formatter_db2.format(d1a);
			
			Date d1b = formatter_db1.parse(DeadlineDate);
			DeadlineDate = formatter_db2.format(d1b);
		}
	}
		
	if(str_NA_Included != null)
	{
		NA_Included = 1;
	}
	
	if(db_Comment_Included != null)
	{
		XComment_Included = 1;
	}
	
	
	
	if(selStatus == 1)	// If combo Status is "Open"
	{
		boolean bExist = CE_Survey.checkSurveyCompetency(SurveyID);
		
		if(bExist)
		{
			if(SurveyID == 0)
			{	
				try
		    	{
					CE_Survey.addRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,JobFutureID,TimeFrameID,NA_Included,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser());    
		    	    
		    	    if(CE_Survey.getJobCat() != 0) {
		    	   		Vector vComp = JobDetail.getCompetencyAssigned(CE_Survey.getJobCat(), CE_Survey.get_survOrg());
	 	
						for(int i=0; i<vComp.size(); i++) {
							voCompetency vo = (voCompetency)vComp.elementAt(i);
		
							int FKComp = vo.getCompetencyID();
							SurveyID = CE_Survey.getSurvey_ID();
							CE_Survey.addCompetency(FKComp,SurveyID,1);
							

						}
						 
		    		    CE_Survey.setJobCat(0);
		    	
					}
		    	   
		    	}
		    	catch(SQLException sqle)
		    	{	%>
		    		<script>
		    			alert("<%=trans.tslt("Survey name has been used")%>");
		    			//location.href('SurveyDetail.jsp');
		    			location.href='SurveyDetail.jsp';
		    		</script>
		    <%	}
		    } else 
		    {
		    	if(CE_Survey.Allow_SurvChange(SurveyID))
				{
			    	try
			    	{
			    		CE_Survey.updateRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser());    
			    	}
			    	catch(SQLException sqle)
			    	{	%>
			    		<script>
			    			alert("<%=trans.tslt("Survey name has been used")%>");
			    			//location.href('SurveyDetail.jsp');
			    			location.href='SurveyDetail.jsp';
			    		</script>
			    <%	}
				}
				else
				{
					try 
					{
						CE_Survey.updateRecord_AfterRaterComplete(month, DeadlineDate, selStatus, AnalysisDate, SurveyID, logchk.getPKUser());
					}
					catch(SQLException sqle) 
					{
						System.out.println(sqle);
					}
				}
		    }
			
		}   
	}
	
	if(CE_Survey.getSurveyStatus() != 2)	// If survey is not "Closed" yet
	{	
		if(SurveyID == 0)
		{	
			try
	    	{
				CE_Survey.addRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,JobFutureID,TimeFrameID,NA_Included,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser());    
	    		   
	    	    if(CE_Survey.getJobCat() != 0) {
	    	   		Vector vComp = JobDetail.getCompetencyAssigned(CE_Survey.getJobCat(), CE_Survey.get_survOrg());
 	
					for(int i=0; i<vComp.size(); i++) {
						voCompetency vo = (voCompetency)vComp.elementAt(i);
	
						int FKComp = vo.getCompetencyID();
						SurveyID = CE_Survey.getSurvey_ID();
						CE_Survey.addCompetency(FKComp,SurveyID,1);
						

					}
					 
	    		    CE_Survey.setJobCat(0);
	    	
				}
	    	}
	    	catch(SQLException sqle)
	    	{	%>
	    		<script>
	    			alert("<%=trans.tslt("Survey name has been used")%>");
	    			//location.href('SurveyDetail.jsp');
	    			location.href='SurveyDetail.jsp';
	    		</script>
	    <%	}
	    } else
	    {
	    	if(CE_Survey.Allow_SurvChange(SurveyID))
			{ 
		    	try
		    	{
		    		CE_Survey.updateRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser());    
		    	}
		    	catch(SQLException sqle)
		    	{	%>
		    		<script>
		    			alert("<%=trans.tslt("Survey name has been used")%>");
		    			//location.href('SurveyDetail.jsp');
		    			location.href='SurveyDetail.jsp';
		    		</script>
		    <%	} 
			}
			else
			{
				CE_Survey.updateRecord_AfterRaterComplete(month, DeadlineDate, selStatus, AnalysisDate, SurveyID, logchk.getPKUser());
			}
	    }
	}
	else	// Survey has been closed
	{	
		CE_Survey.updateRecord_AfterRaterComplete(month,DeadlineDate, selStatus , AnalysisDate, SurveyID, logchk.getPKUser());
	}

	
	if(request.getParameter("minGap") != null || request.getParameter("maxGap") != null)
	{
%>	    <script>
		    //location.href('SurveyDetail.jsp');
		    location.href='SurveyDetail.jsp';
	    </script>
<%	}
	
    if(request.getParameter("proceed") != null)
	{
%>	    <script>
	    	//location.href('SurveyCompetency.jsp');
	    	location.href='SurveyCompetency.jsp';
	    </script>	
<%	}
	else if(request.getParameter("level") != null)
	{
		if(CE_Survey.getSurvey_ID() != 0)
			CE_Survey.del_Survey_KeyBehaviour(CE_Survey.getSurvey_ID());
   	}
   	else if(request.getParameter("behav") != null)
	{
%>		<script>
    		//location.href('SurveyKeyBehaviour.jsp');
    		location.href='SurveyKeyBehaviour.jsp';
    	</script>
<%	}
	else if(request.getParameter("demos") != null)
	{
%>		<script>
    		//location.href('SurveyDemos.jsp');
    		location.href='SurveyDemos.jsp';
    	</script>		
<%	}
   	else if(request.getParameter("rata") != null)
	{
%>		<script>
		    //location.href('SurveyRating.jsp');
		    location.href='SurveyRating.jsp';
	    </script>			
<%	}
	else if(request.getParameter("setting") != null)
	{
%>		<script>
		    //location.href('AdvanceSettings.jsp');
		    location.href='AdvanceSettings.jsp';
	    </script>			
<%	}
	else if(request.getParameter("Save") != null)
	{
%>		<script>
  	  		alert("<%=trans.tslt("Saved successfully")%>");
    	</script>		
<%	}
}

%>
<form name="SurveyDetail" action="SurveyDetail.jsp" method="post">

<table border="0" width="81%" cellspacing="0" cellpadding="0" bordercolor="#000000" style="border-width: 0px">
	<tr>
		<td width="114" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color="#CC0000">
		<%=trans.tslt("Survey Detail")%></font></td>
		<td width="28" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onclick="Comp()" style="cursor:pointer"><%=trans.tslt("Competency")%></a></u></font></td>
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="113" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onclick="Behav()" style="cursor:pointer"><%=trans.tslt("Key Behaviour")%></a></u></font></b></td>
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onclick="Demos()" style="cursor:pointer"><%=trans.tslt("Demographics")%></a></u></font></b></td>
		<td width="23" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="134" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onclick="Rating()" style="cursor:pointer"><%=trans.tslt("Rating Task")%></a></u></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="134" style="border-style: none; border-width: medium">
		<p align="center">&nbsp; <font size="2">
   
		<span style="font-weight: 700">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onclick="setting()" style="cursor:pointer"><%=trans.tslt("Advanced Settings")%></a></u></font></span></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="134" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
</table>

		<table border="1" width="610" bordercolor="#3399FF">

		<tr>
				<td colspan="5" bgcolor="#000080" height="33">
				<p align="center">
				<font color="#FFFFFF" face="Verdana" style="font-weight: 700" size="2">
				<%=trans.tslt("Survey Detail")%></font></td>
			</tr>
		
		<tr>
				<td bgcolor="#FFFFCC"><font face="Arial" size="2">
				<%=trans.tslt("Survey Name")%>:</font></td>
				<td colspan="4" bgcolor="#FFFFCC"><font face="Arial">
				<p align="left">
<font size="2">
<%
		SimpleDateFormat month_view= new SimpleDateFormat ("MM/yyyy");
		//SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
		
		Date today;
		Date next2Week;
		today = new Date();
		next2Week = global.addDay(today, 14);
		
		int Survey_ID=0;
		int JobPosID1=0;
		int NA_Included=0;
		String Survey_Name="";
		String month = "";
		String DateOpened = day_view.format(today);
		String DeadlineDate = day_view.format(next2Week);
		String AnalysisDate = " ";
		int db_SurveyStatus=0;
		int ReliabilityCheck =0;
		String SurveyStatus = "";
		int LevelofSurvey =0;
		int KBLevel=0;
		String JobPosition_Desc1;
		String JobPosition_Desc2;
		String TimeFrame;
		int FKOrg=0;
		float MinGap=-1f;
		float MaxGap=1f;
		int Comment_Included = 0;
				
	/********************
	* Edited by James 02 Nov 2007
	************************/
	if(CE_Survey.getSurvey_ID() != 0 && request.getParameter("new") == null) {
		votblSurvey vo = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
		
		System.out.println(CE_Survey.getSurvey_ID());
		if(vo != null) {
			Survey_ID = vo.getSurveyID();
			Survey_Name = vo.getSurveyName();
			JobPosID1 = vo.getJobPositionID();
			DateOpened = vo.getDateOpened();
			LevelofSurvey = vo.getLevelOfSurvey();
			DeadlineDate = vo.getDeadlineSubmission();
			db_SurveyStatus = vo.getSurveyStatus();
			AnalysisDate = vo.getAnalysisDate();	
			ReliabilityCheck = vo.getReliabilityCheck();
			NA_Included = vo.getNA_Included();
			FKOrg = vo.getFKOrganization();
			MinGap = vo.getMIN_Gap();
			MaxGap = vo.getMAX_Gap();	
			Comment_Included = vo.getComment_Included();
		}
		CE_Survey.setCompetencyLevel(LevelofSurvey);
		CE_Survey.setSurveyStatus(db_SurveyStatus);
		CE_Survey.set_survOrg(FKOrg);
	
	} else {
		CE_Survey.setSurvey_ID(0);
	}
		
	if(CE_Survey.getSurveyStatus() != 2)
	{	
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>				
			</font>
				<span style="font-size: 11pt">
			<input type="text" name="txtSurvey" size="60" value="<%=Survey_Name%>"></span><font size="2">
	<%	}	
		else
		{	%>
			</font>
				<span style="font-size: 11pt">
			<input type="text" name="txtSurvey" size="60" value="<%=Survey_Name%>" disabled><font size="2">
	<%	}	
	}	
	else
	{	%>
			</font>
				<span style="font-size: 11pt">
			<input type="text" name="txtSurvey" size="60" value="<%=Survey_Name%>" disabled><font size="2">
<%	}	%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		</font>
		
		</span><font size="1">(<%=trans.tslt("100 chars max")%>.)</font></td>
			</tr>
		
			<tr>
				<td style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Job Position")%>:</font></td>
				<td width="22%" style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC"><font face="Arial">
				<font size="2">
<%	if(CE_Survey.getSurveyStatus() != 2)
	{

		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
					</font>
					<span style="font-size: 11pt">
					<select size="1" name="selJob2">
	<%	}	
		else
		{	%>
					<select size="1" name="selJob2" disabled>
<%		}	
	}
	else
	{	%>
				<select size="1" name="selJob2" disabled>
<%	}

	Vector vJobPos = JP.getAllJobPositions(CE_Survey.get_survOrg());
	
	for(int i=0; i<vJobPos.size(); i++)
	{
		votblJobPosition voJob = (votblJobPosition)vJobPos.elementAt(i);
		int JobPosition_ID1 = voJob.getJobPositionID();
		JobPosition_Desc1 = voJob.getJobPosition();

		if(JobPosID1 == JobPosition_ID1)
		{
%>			<option value=<%=JobPosition_ID1%> selected><%=JobPosition_Desc1%></option>
<%		}
		else
		{
%>			<option value=<%=JobPosition_ID1%>><%=JobPosition_Desc1%></option>
<%		}	
		
	}
%>
				</select></span></font></td>

				<td width="3%" style="border-bottom-style:solid; border-bottom-width:1px; border-top-style:solid; border-top-width:1px" rowspan="5" bgcolor="#FFFFCC">&nbsp;
				</td>

				<td width="17%" style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2">
				<%=trans.tslt("Status")%>:</font></td>
				<td style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC"> <font size="2">

<%	if(logchk.getUserType() == 1)
	{	%>
		<select size="1" name="selStatus">
<%	}
	else
	{	
	
		if(CE_Survey.getSurveyStatus() != 2)
		{	%>   		
	    		<select size="1" name="selStatus">
	<%	}	
		else
		{	%>
				<select size="1" name="selStatus" disabled>
	<%	}	
	}%>
				<option value="1"><%=trans.tslt("Open")%></option>
				<option value="2"><%=trans.tslt("Closed")%></option>
				<option value="3" selected><%=trans.tslt("Not Commissioned")%></option>

					<script>
					if(<%=db_SurveyStatus%> != 0)
					{
						window.document.SurveyDetail.selStatus.selectedIndex=<%=db_SurveyStatus-1%>;
					}
					</script>
															
				</select>
                                 <input type="hidden" name="selOriginalVal" value=<%=db_SurveyStatus%> />
                                 </td>
			</tr>
			<tr>
				<td bgcolor="#FFFFCC"><font face="Arial" size="2">
				<%=trans.tslt("Reliability")%>:</font></td>
				<td width="22%" style="border-right-style: solid; border-right-width: 1px" bgcolor="#FFFFCC"><font face="Arial">
				<font size="2">

	<%	
	if(CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>	
					</font>
					<span style="font-size: 11pt">
					<select size="1" name="Reliability">
	<%	}
		else
		{%>				
				<select size="1" name="Reliability" disabled>
	<%	}	
	}
	else
	{
		%>
				<select size="1" name="Reliability" disabled>
<%	}	%>
			<option value="0">Trimmed Mean</option>
				<option value="1">Reliability Index</option>

					<script>
					window.document.SurveyDetail.Reliability.selectedIndex=<%=ReliabilityCheck%>;
					</script>
				
				</select></span></font></td>
				
				<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
			<font size="2">
   
			<font face="Arial">
				<%=trans.tslt("Opened Date")%> :</font></td>
				<td width="29%" bgcolor="#FFFFCC"><font face="Arial">
				<span style="font-size: 11pt">
				<%	
	if(CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>	
					<input type="text" name="DateOpened" size="10" value='<%=DateOpened%>'>
	<%	}
		else
		{%>				
				<input type="text" name="DateOpened" size="10" value='<%=DateOpened%>'disabled>
	<%	}	
	}
	else
	{
		%>
				<input type="text" name="DateOpened" size="10" value='<%=DateOpened%>' disabled>
<%	}	%>
				</span><font size="2">
				<img border="0" src="images/CAL-icon.gif" style="cursor:pointer" width="16" height="16" onClick="c2.popup('DateOpened')">
				</font></font></td>
				
			</tr>
			<tr>
				<td style="border-bottom-style: solid; border-bottom-width: 1px" bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Include NA into calculation")%>?</font></td>
				<td width="22%" height="38" style="border-bottom-style: solid; border-bottom-width: 1px; border-right-style:solid; border-right-width:1px" bgcolor="#FFFFCC">
				<p align="left">
<font size="2">
<%	
	if(CE_Survey.getSurveyStatus() != 2)
	{

		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
				</font>
				<input type="checkbox" name="NA_Included" value=<%=NA_Included%>><font size="2">
	<%	}
		else				
		{%>
				</font>
				<input type="checkbox" name="NA_Included" value=<%=NA_Included%> disabled><font size="2">
	<%	}
	}
	else				
	{%>
				</font>
				<input type="checkbox" name="NA_Included" value=<%=NA_Included%> disabled><font size="2">
<%	}
			if(NA_Included == 1)
				{	%>
					<script>
					window.document.SurveyDetail.NA_Included.checked=true;
					</script>
		<%		}	%>
				</font>
				<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to include")%>)</font></td>
				
				
				<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
				<font face="Arial" size="2">
				<%=trans.tslt("Deadline")%>:</font></td>
				<td width="29%" bgcolor="#FFFFCC"><font face="Arial">
				<span style="font-size: 11pt">
								
				<input type="text" name="DeadlineDate" size="10" value='<%=DeadlineDate%>'></span><font size="2">
				<img border="0" src="images/CAL-icon.gif" style="cursor:pointer" width="16" height="16" onClick="c3.popup('DeadlineDate')">
				</font></font></td>
			</tr>

		<tr>
				<td style="border-right-style: solid; border-right-width: 1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2">
				<%=trans.tslt("Survey Level")%>:</font></td>
				<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; " bgcolor="#FFFFCC">
<font size="2">
<%	if(CE_Survey.getSurveyStatus() != 2)
	{

		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>				
				</font>				
				<select size="1" name="SurveyLevel" onchange="ChangeLevel(this.form,this.form.SurveyLevel)">
	<%	}
		else				
		{%>		
				<select size="1" name="SurveyLevel" onchange="ChangeLevel(this.form,this.form.SurveyLevel)" disabled>
	<%	}	
	}
	else				
	{%>		
			<select size="1" name="SurveyLevel" onchange="ChangeLevel(this.form,this.form.SurveyLevel)" disabled>
<%	}	%>	
	
					<option value="0" >Competency Level</option>
						<option value="1">Key Behaviour Level</option>
				</select><font size="2">
				<script>
				window.document.SurveyDetail.SurveyLevel.selectedIndex=<%=LevelofSurvey%>;
				</script>

				</font>

				</td>
				
				<td width="16%" height="38" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Organisation")%>:</font></td>
				<td width="29%" height="38" bgcolor="#FFFFCC">
<font size="2">		
<%
	String OrgName=ORG.getOrganisationName(CE_Survey.get_survOrg());

%>


<font face="Arial" size="2">&nbsp;<%=OrgName%> </font>
				</td>		
				
			</tr>
			<tr>
				<td width="24%" style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Include Comments")%>?</font></td>
				<td style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font size="2">
<%	
	if(CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>  
				<input type="checkbox" name="Comment_Included" value=<%=Comment_Included%>>
	<%	}
		else	
		{%>				
				<input type="checkbox" name="Comment_Included" value=<%=Comment_Included%> disabled>
	<%	}
	}
	else	
	{%>				
			<input type="checkbox" name="Comment_Included" value=<%=Comment_Included%> disabled>
<%	}

				if(Comment_Included == 1)
				{	%>
					<script>
					window.document.SurveyDetail.Comment_Included.checked=true;
					</script>
		<%		}	%>
				<font face="Arial">&nbsp;</font></font><font size="1" face="Arial">
				(<%=trans.tslt("Tick to include")%>)</font></td>
				
		<%	
				if(AnalysisDate.length() <= 3)
				{	%>
					<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
					<font face="Arial" size="2">&nbsp;</font></td>
					<td width="29%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">&nbsp;
					</td>
		<%		}
				else
				{	%>			
				
				<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Closed Date")%>:</font></td>
				<td width="29%" bgcolor="#FFFFCC"><font face="Arial">
				<font size="2">
				<%
				
				if(AnalysisDate == null || AnalysisDate.length() == 0)
				{				
					AnalysisDate =" ";
				}
				%>
				</font>
				<span style="font-size: 11pt">
				<input type="text" name="AnalysisDate" size="10" value="<%=AnalysisDate%>" disabled></span><font size="2">
				</font></font></td>
		<%		}	%>		
				
			</tr>
						
		</table>

<table border="0" width="610" cellspacing="0" cellpadding="0">
	
	<tr>
		<td colspan="3">
		<table border="1" width="100%" bordercolor="#3399FF">
			<tr>
				<font size="2">
   
				<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; " bordercolor="#3399FF" colspan="4" bgcolor="#FFFFCC">&nbsp;
				</td>
			</tr>
			<tr>
   
				<td width="20%" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-bottom-style: none; border-bottom-width: medium; border-top-style:none; border-top-width:medium" rowspan="3" bordercolor="#3399FF" bgcolor="#FFFFCC">
				<font size="2">
   
				<p align="center">
   
				<b>
				<font face="Arial" size="2"><%=trans.tslt("Gap Definition")%>:</font></b></td>
				<td style="border-style:none; border-width:medium; " width="216" bgcolor="#CCCCFF" bordercolor="#3399FF">
				<font face="Arial" size="2"><%=trans.tslt("Meet Expectation is when")%>&nbsp; </font></td>
				<td style="border-style:none; border-width:medium; " bgcolor="#CCCCFF" bordercolor="#3399FF">
				<font size="2">
                           <%
                           if(CE_Survey.getSurveyStatus() != 2)
                        {
                                if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
                                 { 
                                    
                           %>
                                
				<font face="Arial" style="font-size: 11pt" size="2"> <input type="text" name="txtMinGap" size="2" value ="<%=MinGap%>" onchange ="minGap(this.form, this.form.txtMinGap)"></font>
                                 <font face="Arial" size="2">&nbsp; 
				&lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp;  
				</font>
                                <font face="Arial" style="font-size: 11pt" size="2">   
				<input type="text" name="txtMaxGap" size="2" value ="<%=MaxGap%>" onchange ="maxGap(this.form, this.form.txtMaxGap)"></font>
                           <%	
                                } else				
                                    
                                {
                            %>
				<font face="Arial" style="font-size: 11pt" size="2"> <input type="text" name="txtMinGap" size="2" value ="<%=MinGap%>" onchange ="minGap(this.form, this.form.txtMinGap)" disabled></font>
                                    <font face="Arial" size="2">&nbsp; 
				&lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp;  
				</font>
                                <font face="Arial" style="font-size: 11pt" size="2">   
				<input type="text" name="txtMaxGap" size="2" value ="<%=MaxGap%>" onchange ="maxGap(this.form, this.form.txtMaxGap)" disabled></font>
                            <%
                                } 
                                
                    }else{
                           %>
                                
				<font face="Arial" style="font-size: 11pt" size="2"> <input type="text" name="txtMinGap" size="2" value ="<%=MinGap%>" onchange ="minGap(this.form, this.form.txtMinGap)" disabled></font>
                                 <font face="Arial" size="2">&nbsp; 
				&lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp;  
				</font>
                                <font face="Arial" style="font-size: 11pt" size="2">   
				<input type="text" name="txtMaxGap" size="2" value ="<%=MaxGap%>" onchange ="maxGap(this.form, this.form.txtMaxGap)" disabled></font>
                           <%
                    }
                            %>
				
				</td>
				<td style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" rowspan="3" bgcolor="#FFFFCC">&nbsp;
				</td>
			</tr>
			<tr>
				<td style="border-style: none; border-width: medium" width="216" bgcolor="#FFCCCC" bordercolor="#3399FF">
				<font face="Arial" size="2"><%=trans.tslt("Strength is when")%> </font></td>
				<td style="border-style:none; border-width:medium; " bgcolor="#FFCCCC" bordercolor="#3399FF">
				<font size="2">
   
				<font face="Arial" size="2"><%=trans.tslt("Gap")%>&nbsp; &gt;&nbsp;
				<%=MaxGap%></font></td>
			</tr>
			<tr>
				<td style="border-style: none; border-width: medium" width="216" bgcolor="#CCFFCC" bordercolor="#3399FF">
				<font face="Arial" size="2">
				<%=trans.tslt("Developmental Area is when")%> </font></td>
				<td style="border-style:none; border-width:medium; " bgcolor="#CCFFCC" bordercolor="#3399FF">
				<font size="2">
   
				<font face="Arial" size="2"><%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp; 
				<%=MinGap%>&nbsp;</font></td>
			</tr>
			<tr>
   
				<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" colspan="4" bgcolor="#FFFFCC">&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="246">&nbsp;
		</td>
		<td width="183">&nbsp;</td>
		<td>&nbsp;
		</td>
	</tr>
	<tr>
		<td width="246" valign="top">
		<%
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()) == false)
		{ %> 
		<a href="mailto:3SixtyProfiler@pcc.com.sg?subject=Regarding: &body=Please provide us with the following information:%0A%0A
		1. Admin Name:%0A
		2. Organisation:%0A
		3. Survey Name:%0A
		4. Reason for Re-opening of Survey:%0A
		5. Re-opening Date of Survey:%0A
		6. Deadline of Survey:"><%=trans.tslt("Re-Opening Request of Survey")%></a>
		<%	}	%>
		</td>
		
		<td width="183">
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Save")%>" name="btnSave" style="float: right" onclick="Save(this.form, this.form.selStatus)"></td>
		<td>
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Proceed")%>" name="btnNextttt" style="float: right" onclick="Next(this.form.selStatus)"></td>
	</tr>
</table>
</form>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>		