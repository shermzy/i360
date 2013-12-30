<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.util.Calendar,
                 java.text.*,
                 java.lang.String,
				 CP_Classes.vo.*"%>  

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

var sMsg = "The survey has not been assigned with Rating task, Competency and/or Key Behaviour. You are not allowed to open the Survey";

function changeClusterOption(form, field){
	var flag1 = validate();
	
	if(flag1){
		if(field.checked==true){
			alert("Note that the survey status will be switched to Not Commissioned");
			if(confirm("<%=trans.tslt("Please note changing the survey from not using cluster to using cluster will remove all the Competencies and Key Behaviors previously assigned. Do you want to proceed?")%>")){
				form.action="SurveyDetail.jsp?delAll=1";
				form.method="post";
				form.submit();		
			} else{
				field.checked=false;
			}
		} else{
			alert("Note that the survey status will be switched to Not Commissioned");
		}
	}
}

function hideCD(value){
	document.SurveyDetail.action="SurveyDetail.jsp?hideCD="+ value;
	document.SurveyDetail.method="post";
	document.SurveyDetail.submit();
}
function hideKBDesc(value){
	document.SurveyDetail.action="SurveyDetail.jsp?hideKBDesc="+ value;
	document.SurveyDetail.method="post";
	document.SurveyDetail.submit();
	
}
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
	// Commented off as this is not needed
	// Mark Oei 16 April 2010
	// MinGap = x.txtMinGap.value
	// MaxGap = x.txtMaxGap.value
	// GapValue = (MaxGap - MinGap) + 0.1
	    
	SLevel = check(SurveyLevel);
			
	if (txtSurvey == "")
	{
		alert("<%=trans.tslt("Please enter Survey Name")%>");
		checkingFlag = false;
	//Edited by Roger 
	//Fix the survey name length validation
	} else if (txtSurvey.length > 100) {
		alert("Survey Name cannot be longer than 100 characters, please try again");
		checkingFlag = false;
	} else if(selJob2 == "")
		
	{
		
		/*
		*Change(s): Changed the alert information
		*Reason(s): To provide a solution when there is no job position available for the user when it is impossible to create a survey
		*Updated By: Liu Taichen
		*Updated On: 6 June 2012
		*/
		alert("<%=trans.tslt("There is currently no job position in the organization. Please create a job position before creating a survey.")%>");
		checkingFlag = false;
	} 
	else if(checkdate(DateOpened, DeadlineDate) == false)
	{
		checkingFlag = false;
	}

 	return checkingFlag;
}

var c1 = new CodeThatCalendar(caldef1);
var c2 = new CodeThatCalendar(caldef2);
var c3 = new CodeThatCalendar(caldef3);
var c4 = new CodeThatCalendar(caldef4);

function ChangeLevel(form,field)
{
	if(confirm("<%=trans.tslt("Do you wish to change the survey level? Note that the survey status will be switched to Not Commissioned")%>"))
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
                                // Added by DeZ, 30.06.08, fix problem with survey status not switched 
                                //   to Not Commissioned when survey level switched from Competency to Key Behaviour
                                form.selStatus.selectedIndex = 2;
                                
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
		//always here
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

function Cluster()
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
		//location.href='SurveyCluster.jsp';
		document.SurveyDetail.action="SurveyCluster.jsp";
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
			document.SurveyDetail.action="SurveyDetail.jsp?comp=1";
			document.SurveyDetail.method="post";
			document.SurveyDetail.submit();
		}
	}
	else
	{
		//location.href='SurveyCompetency.jsp';
		document.SurveyDetail.action="SurveyCompetency.jsp";
		document.SurveyDetail.submit();
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
//Edited by Roger 16 June 2008
//Change to regex date validation
function checkdate(objName1, objName2) {
	
	if (isDate(objName1) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		opendate = objName1;
	}
   
	if (isDate(objName2) == false) {
		//datefield.select();
		alert("<%=trans.tslt("That date is invalid. Please try again")%>.");
		//datefield.focus();
		return false;
	} else {
		closedate = objName2;
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
         
    // var tmptoday=today.getDate() + " " + strMonthArray[today.getMonth()]+" " + (today.getYear());
	//Edited by Roger 16 June 2008
	var daystr;
	if (today.getDate() < 10) {
		daystr = "0"+today.getDate();
	} else {
		daystr = today.getDate();
	}
	
	var monthstr;
	if ((today.getMonth()+1) < 10) {
		monthstr = "0"+(today.getMonth()+1);
	} else {
		monthstr = today.getMonth()+1;
	}
	
//Edited By Roger
//Fix date validation bug	
	var tmptoday= monthstr +"/" +daystr + "/" +today.getYear();
	var tmpclosedate = closedate.value.substring(3,5) +"/" + closedate.value.substring(0,2) + "/" + closedate.value.substring(6,10);
	
    if(Date.parse(tmptoday)<=Date.parse(tmpclosedate)){
	
        
       
        return true;
     } else {
        return false;
     }
    
}
function doDateCheck(opendate, closedate) 
{

//Edited By Roger
//Fix date validation bug
var tmpopendate = opendate.value.substring(3,5) +"/" + opendate.value.substring(0,2) + "/" + opendate.value.substring(6,10);
var tmpclosedate = closedate.value.substring(3,5) +"/" + closedate.value.substring(0,2) + "/" + closedate.value.substring(6,10);

	if (Date.parse(tmpopendate) <= Date.parse(tmpclosedate)) {
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

//Edited by Roger 16 June 2008
//Change to a simplier date validation with regex
function isDate(fld) {
    var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;
    var errorMessage = 'Please enter valid date as month, day, and four digit year.\nYou may use a slash, hyphen or period to separate the values.\nThe date must be a real date. 30/2/2000 would not be accepted.\nFormay dd/mm/yyyy.';
    if ((fld.value.match(RegExPattern)) && (fld.value!='')) {
       return true;
    } else {
		return false;
    } 
}

//Denise 14/12/2009 disable NA_included option when hideOption is clicked
function isHideNAOption()
{
	if (SurveyDetail.HideNAOption.checked){
			alert("Note that NA will not be included in calculation since you" 
				+ 	" have chosen to hide the NA option in the questionnaire.");
			SurveyDetail.NA_Included.disabled = true;
			SurveyDetail.NA_Included.checked = false;
			
		}else{	
			SurveyDetail.NA_Included.disabled = false;
			SurveyDetail.NA_Included.checked = false;
   		}
}


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
// Remove minGap and maxGap check 
// Mark Oei 16 April 2010
if(request.getParameter("delAll") != null || request.getParameter("setting") != null || request.getParameter("Save") != null || request.getParameter("proceed") != null || request.getParameter("level") != null || request.getParameter("comp") != null || request.getParameter("behav") != null || request.getParameter("demos") != null || request.getParameter("rata") != null)
{	
	Date hari_ini;
	hari_ini = new Date();
	
	int NA_Included = 0;
	int HideNAOption = 0;  //Denise 14/12/2009 to store the hideNAOption value
	int SplitOthersOption = 0;  //Qiao Li 17/12/2009 to store the splitOthersOption value
	int useCluster = 0; //Albert 15/06/2012 to store the useCluster value
	int breakCPR = 0; //Albert 09/07/2012 to store the breakCPR value
	int hideKBDesc = 0; //Albert 05/01/2013 to store the hideKB description value
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
	//To insert some default value of MinGap and MaxGap
	//Mark Oei 16 April 2010
	String MinGap = "";
	String MaxGap = "";
	int FKCompanyID = logchk.getCompany(); 
	//commented off as this is not needed
	//Mark Oei 29 April 2010
	//MinGap = request.getParameter("txtMinGap");
	//MaxGap = request.getParameter("txtMaxGap");

	String SurveyName = "";
	int JobPosID1 = 0;
	int Reliability = 0;
	String str_NA_Included = "";
	int LevelofSurvey = 0;
	String db_Comment_Included = "";
	String str_HideNAOption = "" ; //Denise 14/12/2009 to check if hideOption is clicked
	String str_SplitOthersOption = "" ; //Qiao Li 17/12/2009 to check if splitOthersOption is clicked
	String str_useCluster = "" ; //Albert 15/06/2012 to check if useCluster is clicked
	String str_breakCPR = "" ; //Albert 09/07/2012 to check if breakCPR is clicked
	String str_hideKBDesc = "" ; //Albert 05/01/2013 to check if hideKBDesc is clicked
	
	
	if(selStatus_str != null)
	{
		selStatus = new Integer(selStatus_str).intValue();		
	}
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

		
	str_HideNAOption = request.getParameter("HideNAOption"); //Denise 14/12/2009. Get hideNAOption value
	str_SplitOthersOption = request.getParameter("SplitOthersOption"); //Qiao Li 17/12/2009. Get SplitOthersOption value
	str_useCluster = request.getParameter("useCluster"); //Albert 15/06/2012 . Get useCluster value
	str_breakCPR = request.getParameter("breakCPR"); //Albert 09/07/2012 . Get breakCPR value
	str_hideKBDesc = request.getParameter("hideKBDesc");
					
	if(CE_Survey.getSurveyStatus() != 2) // Survey is not Closed
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
	
	if(str_HideNAOption != null && !str_HideNAOption.equals("")) //Densie 14/12/2009 check if hideNAOption is chosen
	{
		HideNAOption = 1; 
	}
	if(str_SplitOthersOption != null && !str_SplitOthersOption.equals("")) //Qiao Li 17/12/2009 check if splitOthersOption is chosen
	{
		SplitOthersOption = 1; 
	}
	if(str_useCluster != null && !str_useCluster.equals("")) //Albert 15/06/2012 check if useCluster is chosen
	{
		useCluster = 1; 
	}
	if(str_breakCPR != null && !str_breakCPR.equals("")) //Albert 09/07/2012 check if breakCPR is chosen
	{
		breakCPR = 1; 
	}
	if(str_hideKBDesc != null && !str_hideKBDesc.equals(""))
	{
		hideKBDesc = 1; 
	}
//del if selStatus==1...

//Changed by Ha 29/05/08 to set open date correctly accroding to the type of the survey.
//Fix the bug when the date appears in the interface not saved but still saved in database
//Fix the bug when the information appears to change in interface but does not change in database
//Change the date passed to method CE_Survey.updateRecord_AfterRaterComplete, change type of Date passed to the query from dd/mm/yyyy to mm/dd/yyyy 
	
	
				
				
	if(CE_Survey.getSurveyStatus() !=2)	// If survey is not "Closed" yet
	{		
            // if the survey status is open, the open date must be today or later.
            if(selStatus != 3) // If the survey status is Open
            {
                //add another check for Date if it is first time adding
                //updated by thant thura myo
                //date 20 Feb 2008
                 
                 boolean bExist =  false;
                 int surveyLevel ;
                 //Changed by HA 09/06/08 
                 /*If the survey is in Competency Level, check whether it has Competency*/
                 /* If the Survey is in the KB level, check whether  for each competency, it*/
                 /*  has at least 1 KB or not */
                 if (request.getParameter("SurveyLevel")!=null)
                    surveyLevel = Integer.parseInt(request.getParameter("SurveyLevel"));
                 else
                	surveyLevel = CE_Survey.getSurveyLevel(SurveyID);
                 if (surveyLevel ==0)
                	bExist = CE_Survey.checkCompetencyExist(SurveyID);
                 else
                	 bExist = CE_Survey.checkSurveyCompetency(SurveyID);
                
                 if (bExist)
                 {                	 
	                 Date tempToday=new Date();                
	                 SimpleDateFormat day_view1= new SimpleDateFormat ("yyyy/MM/dd");
	                 String DateToday = day_view1.format(tempToday);
	                 Date dDateOpen = null;
	                 if (DateOpened != null)
	                 {	                	
	                	 dDateOpen=new Date(DateOpened);	
	                 }                
	                	 tempToday=new Date(DateToday);
	               		 Date dToday1=tempToday;
	                 
	           			 if(dDateOpen==null||dToday1.before(dDateOpen)|| dToday1.equals(dDateOpen))
	           			 {		            
	           				
		                	 if(SurveyID == 0)
						   {	
		                	 /*
	                         * Add new function for Survey name checking
	                         *    @author thant thura myo
	                         *  @date 20 feb 2008	             
	                        */
	                     	//check for survey name for new record.
		                     boolean bNameExist=CE_Survey.checkSurveyName(SurveyName);
		                     System.out.println(">>>>>>>>>>>");
		                     System.out.println("a. Check exist or not "+bNameExist); //added 'a' for aiding debugging, Mark Oei 29 April 2010
		                     if(!bNameExist)
		                     {//if not exist, save as new, if exist, error                        
	                     	 	try
	                         	{
	                         	
		                         	/**********************************************************
		                         	* Added by junwei on March 04 2008
		                         	* to checking for competency, key behaviour and rating added
		                         	***********************************************************/
		                         	int selectedSurveyLevel = Integer.parseInt(request.getParameter("SurveyLevel"));
		                         	if(selectedSurveyLevel == 0)
		                         	{
		                         		
	                         			boolean checkedResult = CE_Survey.checkRating(SurveyID);	
	                         			if(checkedResult == true)
	                         			{                       			
	                         		 		CE_Survey.addRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,JobFutureID,TimeFrameID,NA_Included,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, breakCPR, hideKBDesc);
	                         			}
	                         			else
	                         			{
	                         			%>
	                         			<script>
	                         				 alert(sMsg);
	                                         //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
	                                         //location.href('SurveyDetail.jsp');
	                                         location.href='SurveyDetail.jsp';
	                         			</script>
	                         			<%
	                         			}
	                         		}
	                         		else
	                         		{
	                         			
	                         			boolean checkedResult = CE_Survey.checkBehaviourRating(SurveyID);
	                         			if(checkedResult == true)
	                         			{	                          			
	                         			 CE_Survey.addRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,JobFutureID,TimeFrameID,NA_Included,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, breakCPR, hideKBDesc); 
	                         			}
	                         			else
	                         			{
	                         			%>
	                         			<script>
	                         				alert(sMsg);
	                         				//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
	                         				//location.href('SurveyDetail.jsp');
	                         				location.href='SurveyDetail.jsp';
	                         			</script>
	                         			<%
	                         			}
	                         		}
	                         	
	                             	if(CE_Survey.getJobCat() != 0) 
	                             	{
	                                     Vector vComp = JobDetail.getCompetencyAssigned(CE_Survey.getJobCat(), CE_Survey.get_survOrg());
	
	                                     for(int i=0; i<vComp.size(); i++)
	                                     {
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
	                                         alert("<%=trans.tslt("Survey name exists")%>");
	                                         //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
	                                         //location.href('SurveyDetail.jsp');
	                                         location.href='SurveyDetail.jsp';
	                                 </script>
	                     		<%	
	                     		}	                         
	                         }//end if(bNameExist)
	                         else
	                         {
	                         %>
	                                 <script>
	                                         alert("<%=trans.tslt("Survey name exists")%>");
	                                         //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
	                                         //location.href('SurveyDetail.jsp');
	                                         location.href='SurveyDetail.jsp';
	                                 </script>
	                     	<%
	                     	  }
	     				 } //end SurveyId ==0
                         else
	  					 {
	    				   	if(CE_Survey.Allow_SurvChange(SurveyID))
						 	{ 
	    				   		  CE_Survey.changeHideNAOption( HideNAOption, CE_Survey.getSurvey_ID()); //change the Hide NA Option 
								  CE_Survey.changeSplitOthersOption( SplitOthersOption, CE_Survey.getSurvey_ID()); //change the SplitOthersOption (Qiao Li 17 Dec 2009)
								  CE_Survey.changeUseClusterOption( useCluster, CE_Survey.getSurvey_ID()); //change the useCluster option (Albert 15 June 2012)
								  CE_Survey.changeBreakCPR( breakCPR, CE_Survey.getSurvey_ID()); //change the breakCPR option (Albert 09 July 2012)
								  CE_Survey.changeHideKBDesc( hideKBDesc, CE_Survey.getSurvey_ID());
	               			 	  boolean bNameExist=CE_Survey.checkSurveyName(SurveyName,SurveyID);
			                      System.out.println(">>>>>>>>>>>");
			                      System.out.println("b. Check exist or not "+bNameExist);//added 'b' for aiding debugging, Mark Oei 29 April 2010
			                      if(!bNameExist)
			                      {//if not exist, save as new, if exist, error              
                      		 		 try
                      		  		 {
				                      /**********************************************************
				                      * Added by junwei on March 04 2008
				                      * to checking for competency, key behaviour and rating added
				                      ***********************************************************/
					                      int selectedSurveyLevel = Integer.parseInt(request.getParameter("SurveyLevel"));
			                      		  if(selectedSurveyLevel == 0)
	                      				  {
			                      			   
			                      				boolean checkedResult = CE_Survey.checkRating(SurveyID);	
			                      				if(checkedResult == true)
	                      						{
	                                            	//get values from database to display
	                                            	//Mark Oei 29 April 2010
	                                            	votblSurvey vo = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
	                                        		MinGap = Float.toString(vo.getMIN_Gap()); //get values from database
	                                        		MaxGap = Float.toString(vo.getMAX_Gap());

	                      							CE_Survey.updateRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, CE_Survey.getHideCompDesc(), breakCPR,hideKBDesc, CE_Survey.getMergeRelation());

			                      				}
	                      						else
	                      						{
			                      					%>
			                      					<script>
			                      					alert(sMsg);
			                      					//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
			                      					//location.href('SurveyDetail.jsp');
			                      					location.href='SurveyDetail.jsp';
			                      					</script>
			                      					<%
	                      						}
	                      			 	   }
	                      				   else
	                      		     	   {
	                      						boolean checkedResult = CE_Survey.checkBehaviourRating(SurveyID);
	                      						if(checkedResult == true)
	                      						{
	                                            	//get values from database to display
	                                            	//Mark Oei 29 April 2010
	                                            	votblSurvey vo = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
	                                        		MinGap = Float.toString(vo.getMIN_Gap()); //get values from database
	                                        		MaxGap = Float.toString(vo.getMAX_Gap());

	                      					 		CE_Survey.updateRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, CE_Survey.getHideCompDesc(), breakCPR,hideKBDesc, CE_Survey.getMergeRelation());

	                      						}
	                      						else
	                      						{
			                      					%>
			                      					<script>
			                      					alert(sMsg);
			                      					//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
			                      					//location.href('SurveyDetail.jsp');
			                      					location.href='SurveyDetail.jsp';
			                      					</script>
			                      					<%
	                      						}
	                      			 		}                                  
                     		  			}//End of try
                        			 	catch(SQLException sqle)
                      					{	%>
		                              		<script>
		                                      alert("<%=trans.tslt("Survey name exists")%>");
		                                      //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		                                      //location.href('SurveyDetail.jsp');
		                                      location.href='SurveyDetail.jsp';
		                              		</script>
                  					<%	}                      
                      				}
	                   			 	else
                      				{
				                          %>
				                              <script>
			                                   alert("<%=trans.tslt("Survey name exists")%>");
		                                      //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
		                                      //location.href('SurveyDetail.jsp');
		                                      location.href='SurveyDetail.jsp';
				                              </script>
				                          <%
                        			}
								}
								else
								{
									try 
									{
										
										String tempDay = DeadlineDate.substring(0,2);
										String tempMonth = DeadlineDate.substring(3,5);
										String newDeadlineDate = DeadlineDate.substring(6,10);
									    newDeadlineDate = tempMonth +"/" + tempDay + "/" +newDeadlineDate;									    
										CE_Survey.updateRecord_AfterRaterComplete(month, newDeadlineDate, selStatus, AnalysisDate, SurveyID, logchk.getPKUser());
									}
									catch(SQLException sqle) 
									{
										System.out.println(sqle);
									}
								}
	    					}
                 		}
	           		 	else
	           		 	{         
                              %>
                              <script>
                              alert("<%=trans.tslt("Opened Date must not be earlier than today")%>");
                              //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                              //location.href('SurveyDetail.jsp');
                              location.href='SurveyDetail.jsp';
                              </script>
                            	<%     
                         }//end of  if((dToday.before(dDateOpen)){
                         // end of openedDate !=null
           		}//end of if (bExist)
           		else
           		{
           			
           			%>	
        			<script>
        				alert(sMsg);
        				//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
        				//location.href('SurveyDetail.jsp');
        				location.href='SurveyDetail.jsp';
        			</script>
        		<%	
           		}
           	}
           	else if (selStatus == 3)
           	{//if(CE_Survey.getSurveyStatus() == 3))
				if(SurveyID == 0)
				{	
                      //check for survey name for new record.
                            boolean bNameExist=CE_Survey.checkSurveyName(SurveyName);
                            System.out.println(">>>>>>>>>>>");
                            System.out.println("c. Check exist or not "+bNameExist); //added 'c' for aiding debugging, Mark Oei 29 April 2010
                            if(!bNameExist){
                                
                                //add another check for Date if it is first time adding
                                //updated by thant thura myo
                                //date 20 Feb 2008
                                 /*Date tempToday=new Date();
                                 SimpleDateFormat day_view1= new SimpleDateFormat ("yyyy/MM/dd");
                                 String DateToday = day_view1.format(tempToday);
                                 Date dDateOpen=new Date(DateOpened);
                                 tempToday=new Date(DateToday);
                                 Date dToday1=tempToday;*/
                                 //dToday is today date  
          
                                    try
                                    {
                                                    CE_Survey.addRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,JobFutureID,TimeFrameID,NA_Included,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, breakCPR, hideKBDesc);    

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
                                                    alert("<%=trans.tslt("Survey name exists")%>");
                                                    //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                                                    //location.href('SurveyDetail.jsp');
                                                    location.href='SurveyDetail.jsp';
                                            </script>
                                <%	}
                            // }else{
                                %>
                                    <script>
                                   //\\         alert("<%=trans.tslt("Opened Date must be earlier than today")%>");
								   
								   			<% //Edited by Roger 17 June 2008
											if (request.getParameter("Save") != null) {%>
                                            //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                                            //location.href('SurveyDetail.jsp');
                                            location.href='SurveyDetail.jsp';
											<% } %>
                                   //\\ </script>
                            <%     
                             //}
                        }else{
                              %>
                                    <script>
                                            alert("<%=trans.tslt("Survey name exists")%>");
                                            //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                                            //location.href('SurveyDetail.jsp');
                                            location.href='SurveyDetail.jsp';
                                    </script>
                        <%  
                        }
	    } else
	    {
	    	if(CE_Survey.Allow_SurvChange(SurveyID))
			{ 
						 CE_Survey.changeHideNAOption( HideNAOption, CE_Survey.getSurvey_ID()); //Densie 14/12/2009 Change the hide NA option
						 CE_Survey.changeSplitOthersOption( SplitOthersOption, CE_Survey.getSurvey_ID()); //change the SplitOthersOption (Qiao Li 17 Dec 2009)
						 CE_Survey.changeUseClusterOption( useCluster, CE_Survey.getSurvey_ID()); //change the useCluster option (Albert 15 June 2012)
						 CE_Survey.changeBreakCPR( breakCPR, CE_Survey.getSurvey_ID()); //change the breakCPR option (Albert 09 July 2012)
						 CE_Survey.changeBreakCPR( hideKBDesc, CE_Survey.getSurvey_ID());
                          boolean bNameExist=CE_Survey.checkSurveyName(SurveyName,SurveyID);
                          System.out.println(">>>>>>>>>>>");
                          System.out.println("d. Check exist or not "+bNameExist); //added 'd' for aiding debugging, Mark Oei 29 April 2010
                          if(!bNameExist){//if not exist, save as new, if exist, error
                            
                                try
                                {
                                	//get values from database to display
                                	//Mark Oei 29 April 2010
                                	votblSurvey vo = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
                            		MinGap = Float.toString(vo.getMIN_Gap()); //get values from database
                            		MaxGap = Float.toString(vo.getMAX_Gap());

                                        CE_Survey.updateRecord(SurveyName,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, CE_Survey.getHideCompDesc(), breakCPR,hideKBDesc, CE_Survey.getMergeRelation());    

                                }
                                catch(SQLException sqle)
                                {	%>
                                        <script>
                                                alert("<%=trans.tslt("Survey name exists")%>");
                                                //Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
                                                //location.href('SurveyDetail.jsp');
                                                location.href='SurveyDetail.jsp';
                                        </script>
                            <%	} 
                           }
                          else{
                              %>
                                        <script>
                                                alert("<%=trans.tslt("Survey name exists")%>");
                                                //location.href('SurveyDetail.jsp');
                                                location.href='SurveyDetail.jsp';
                                        </script>
                            <%
                          }
			}
			else
			{
				//Changed by Ha 29/05/08 change the date passed to method CE_Survey.updateRecord_AfterRaterComplete, change type of Date passed to the query from dd/mm/yyyy to mm/dd/yyyy 
			   
			    String tempDay = DeadlineDate.substring(0,2);
				String tempMonth = DeadlineDate.substring(3,5);
				String newDeadlineDate = DeadlineDate.substring(6,10);
			    newDeadlineDate = tempMonth +"/" + tempDay + "/" +newDeadlineDate;									    
				CE_Survey.updateRecord_AfterRaterComplete(month, newDeadlineDate, selStatus, AnalysisDate, SurveyID, logchk.getPKUser());
			}
	    }
          }// end of else of if(CE_Survey.getSurveyStatus() == 3)
	}//if(CE_Survey.getSurveyStatus() != 2)
	else	// Survey has been closed
	{	
		//Changed by Ha 29/05/08 change the date passed to method CE_Survey.updateRecord_AfterRaterComplete, change type of Date passed to the query from dd/mm/yyyy to mm/dd/yyyy 
		String tempDay = DeadlineDate.substring(0,2);
		String tempMonth = DeadlineDate.substring(3,5);
		String newDeadlineDate = DeadlineDate.substring(6,10);
	    newDeadlineDate = tempMonth +"/" + tempDay + "/" +newDeadlineDate;									    
		CE_Survey.updateRecord_AfterRaterComplete(month,newDeadlineDate, selStatus , AnalysisDate, SurveyID, logchk.getPKUser());
		
	}
	
	//Changed by Denise 14/12 to fix the null pointer exception
   	if ((selOriginalVal.equals("2"))&&((dToday.before(dDeadlineDate)||dToday.equals(dDeadlineDate)))&&selStatus!=2)
       {
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

%>	<!--    <script>
		    //location.href('SurveyDetail.jsp');
		    location.href='SurveyDetail.jsp';
	    </script> -->
<%	// }
	
	if(request.getParameter("proceed") != null)
{
	%>
	<script>
    	//location.href('SurveyCompetency.jsp');
    	location.href='SurveyCluster.jsp';
    </script>
<% 	}
	else if(request.getParameter("comp") != null)
	{
%>
		<script>
	    	//location.href('SurveyCompetency.jsp');
	    	location.href='SurveyCompetency.jsp';
	    </script>
<% 	}
	
	else if(request.getParameter("level") != null)
	{
		if(CE_Survey.getSurvey_ID() != 0)
			CE_Survey.del_Survey_KeyBehaviour(CE_Survey.getSurvey_ID());
   	}
   	else if(request.getParameter("delAll") != null)
	{
		if(CE_Survey.getSurvey_ID() != 0){
			CE_Survey.del_Survey_Competency(CE_Survey.getSurvey_ID());
			CE_Survey.del_Survey_KeyBehaviour(CE_Survey.getSurvey_ID());
		}
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
		<a href="#" onClick="Cluster()" style="cursor:pointer"><%=trans.tslt("Cluster")%></a></u></font></td>
		
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Comp()" style="cursor:pointer"><%=trans.tslt("Competency")%></a></u></font></td>
		
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="113" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Behav()" style="cursor:pointer"><%=trans.tslt("Key Behaviour")%></a></u></font></b></td>
		
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Demos()" style="cursor:pointer"><%=trans.tslt("Demographics")%></a></u></font></b></td>
		
		<td width="23" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="134" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Rating()" style="cursor:pointer"><%=trans.tslt("Rating Task")%></a></u></font></b></td>
		
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
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
		<a href="#" onClick="setting()" style="cursor:pointer"><%=trans.tslt("Advanced Settings")%></a></u></font></span></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
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
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyPrelimQ.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Preliminary Questions") %></a></font></b></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
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
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="AdditionalQuestions.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Additional Questions") %></a></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
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
		int useCluster =0;
		int breakCPR = 0;
		int hideKBDesc = 0;
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
		float MinGap=-1f; 	// used to insert an initial default value, Mark Oei 16 April 2010
		float MaxGap=1f;	// used to insert an initial default value, Mark Oei 16 April 2010
		int Comment_Included = 0;
				
	/********************
	* Edited by James 02 Nov 2007
	************************/
	if(CE_Survey.getSurvey_ID() != 0 && request.getParameter("new") == null) {
		
		votblSurvey vo = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
		
		
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
		useCluster = vo.getUseCluster();
		breakCPR = vo.getBreakCPR();
		hideKBDesc = vo.getHideKBDesc();
		FKOrg = vo.getFKOrganization();
		MinGap = vo.getMIN_Gap(); //get values from database
		MaxGap = vo.getMAX_Gap();	
		Comment_Included = vo.getComment_Included();
		
		CE_Survey.setCompetencyLevel(LevelofSurvey);
		CE_Survey.setSurveyStatus(db_SurveyStatus);
		CE_Survey.set_survOrg(FKOrg);
	//Changed by Ha 04/06/08
	} else {
//By Hemilda 23 Sept 2008 to add parameter org from previous page , so there will be job position based on org for new survey.
		String Org =request.getParameter("Org");
		
		//by Hemilda  09012009 to add condition only copy Survey by job need to send parameter then var Org will have value. copy survey by category already being set in front then var Org will be null.
		if (Org != null)
		{
			CE_Survey.set_survOrg(Integer.parseInt(Org));
		}
		//System.out.println("Org "+Org);
		CE_Survey.setSurvey_ID(0);
		CE_Survey.setSurveyStatus(0);
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
<%	if(CE_Survey.getSurveyStatus() !=2)
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

				<td width="3%" style="border-bottom-style:solid; border-bottom-width:1px; border-top-style:solid; border-top-width:1px" rowspan="8" bgcolor="#FFFFCC">&nbsp;
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
			<% //Updated by Hoa 04/12/2008 - Remove Trimmed Mean Option %>
			<!-- 				 
				<option value="0">Trimmed Mean</option> 
			 -->
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
				<select size="1" name="SurveyLevel" onChange="ChangeLevel(this.form,this.form.SurveyLevel)">
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
		<% //Denise 14/12/2009 add Hide NA Option row %>  		
			</tr>
			
				<tr>
<td style="border-right-style: solid; border-right-width: 1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2">
				<%=trans.tslt("Hide N/A Option")%>?</font></td>
				<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; " bgcolor="#FFFFCC">
<font size="2">			
	<%		
	if (CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
				</font>
				<input type="checkbox" name="HideNAOption" value= "HideNAOption" onclick = "isHideNAOption()"><font size="2">
	<%	}else
		{%>
				</font>
				<input type="checkbox" name="HideNAOption" value= "HideNAOption" disabled ><font size="2">
	<%	}}
	else {%>
				</font>
				<input type="checkbox" name="HideNAOption" value= "HideNAOption" disabled ><font size="2">
	<%	
	}
	if(CE_Survey.getHideNAOption(CE_Survey.getSurvey_ID()) == 1)
				{	%>
					<script>
					window.document.SurveyDetail.HideNAOption.checked=true;
					window.document.SurveyDetail.NA_Included.disabled = true;
					</script>
		<%		}	%>
				</font>
				<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to Hide")%>)</font></td>
				
		<% //added in the option to split the rater "others" to "subordinates" and "peers"
		   //(Qiao Li 17 Dec 2009)
		%>  		
				<font face="Arial" size="2">&nbsp;</font></td>
				<td width="16%" height="38" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Split Others Option")%>?</font></td>
				<td width="29%" height="38" bgcolor="#FFFFCC">
				<font size="2">	
	<%		
	if (CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
				</font>
				<input type="checkbox" name="SplitOthersOption" value= "SplitOthersOption"><font size="2">
	<%	}else
		{%>
				</font>
				<input type="checkbox" name="SplitOthersOption" value= "SplitOthersOption" disabled ><font size="2">
	<%	}}
	else {%>
				</font>
				<input type="checkbox" name="SplitOthersOption" value= "SplitOthersOption" disabled ><font size="2">
	<%	
	}
	if(CE_Survey.getSplitOthersOption(CE_Survey.getSurvey_ID()) == 1)
				{	%>
					<script>
					window.document.SurveyDetail.SplitOthersOption.checked=true;
					</script>
		<%		}	%>
				</font>
				<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to Split")%>)</font></td>
				
			</tr>
	<!-- Added by Albert (15 June 2012) to add new option whether to use comptency clustering -->	
		<tr>
				<td width="24%" style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Use Competency Cluster")%>?</font></td>
				<td style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font size="2">
<%	
	if (CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
				</font>
				<input type="checkbox" name="useCluster" value= "useCluster" onClick="changeClusterOption(this.form, this.form.useCluster)"><font size="2">
	<%	}else
		{%>
				</font>
				<input type="checkbox" name="useCluster" value= "useCluster" disabled onClick ="changeClusterOption(this.form, this.form.useCluster)"><font size="2">
	<%	}}
	else {%>
				</font>
				<input type="checkbox" name="useCluster" value= "useCluster" disabled onClick ="changeClusterOption(this.form, this.form.useCluster)"><font size="2">
	<%	
	}
	if(CE_Survey.getUseClusterOption(CE_Survey.getSurvey_ID()) == 1)
				{	%>
					<script>
					window.document.SurveyDetail.useCluster.checked=true;
					</script>
		<%		}	%>
				</font>
				<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to use")%>)</font></td>

					<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
					<font face="Arial" size="2">Hide Competency Description?</font></td>
					<td width="29%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
					<% 
					//competency level survey
					if(request.getParameter("hideCD") != null){
						int value = Integer.parseInt(request.getParameter("hideCD"));
						CE_Survey.setHideCompDesc(value);
					}
					if(CE_Survey.getCompetencyLevel() == 0){
					%>
					<input type="checkbox" name="hidCD" value= "hidCD" disabled onClick ="hideCD()">
					<%
					}
					//keybehavior level survey
					else{
						
					//do not hide competency description
                          if(CE_Survey.getHideCompDesc() ==0){
                        	  %>
                          
                        	  <input type="checkbox" name="hidCD" value= "hidCD" onClick ="hideCD(1)">
                        	  <% 	  
                          }
					//hide competency description
                          else{
                        	  %>
                        	  <input type="checkbox" name="hidCD" value= "hidCD" checked onClick ="hideCD(0)">
                        	  <% 
                          }
					}
					
					%>
					<font size="2"></font>
					<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to use")%>)</font></td>
				</tr>

	<!-- Added by Albert (9 Jul 2012) add breakdown CPR all -->
	<tr>
				<td width="24%" style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font face="Arial" size="2"><%=trans.tslt("Breakdown CPR")%>?</font></td>
				<td style="border-style:solid; border-width:1px; " bgcolor="#FFFFCC">
				<font size="2">
<%	
	if (CE_Survey.getSurveyStatus() != 2)
	{
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
		{ %>
				</font>
				<input type="checkbox" name="breakCPR" value= "breakCPR"><font size="2">
	<%	}else
		{%>
				</font>
				<input type="checkbox" name="breakCPR" value= "breakCPR" disabled><font size="2">
	<%	}}
	else {%>
				</font>
				<input type="checkbox" name="breakCPR" value= "breakCPR" disabled><font size="2">
	<%	
	}
	if(CE_Survey.getBreakCPR(CE_Survey.getSurvey_ID()) == 1)
				{	%>
					<script>
					window.document.SurveyDetail.breakCPR.checked=true;
					</script>
		<%		}	%>
				</font>
				<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to break")%>)</font></td>	

				
				<td width="16%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
					<font face="Arial" size="2">Hide KB Description?</font></td>
					<td width="29%" style="border-left-style: solid; border-left-width: 1px" bgcolor="#FFFFCC">
					<% 
					//KB level survey
					if(CE_Survey.getCompetencyLevel() != 0){
					%>
					<input type="checkbox" name="hideKBDesc" value= "hideKBDesc" disabled onClick ="hideKBDesc()">
					<%
					}
					//Competency level survey
					else{
						
					//do not hide KB description
                          if(CE_Survey.getHideKBDesc(CE_Survey.getSurvey_ID()) ==0){
                        	  %>
                          
                        	  <input type="checkbox" name="hideKBDesc" value= "hideKBDesc" onClick ="hideKBDesc(1)">
                        	  <% 	  
                          }
					//hide KB description
                          else{
                        	  %>
                        	  <input type="checkbox" name="hideKBDesc" value= "hideKBDesc" checked onClick ="hideKBDesc(0)">
                        	  <% 
                          }
					}
					
					%>
					<font size="2"></font>
					<font face="Arial" size="2">&nbsp; </font>
				<font face="Arial" size="1">(<%=trans.tslt("Tick to hide")%>)</font></td>
			</tr>
			
		</table>

<table border="0" width="610" cellspacing="0" cellpadding="0">
	
	<tr>
		<td colspan="3">
		<!-- <% //Commented off as Gap Defintion is shifted
			 	//except for 2 input box to insert default MinGap and MaxGap values
				// Mark Oei 16 April 2010 %>
		<% /* <table border="1" width="100%" bordercolor="#3399FF">
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
		-->*/ %>
        <%/*
               if(CE_Survey.getSurveyStatus() != 2)
               {
         			if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
                    {                
         */%>
        <% /*
                <!-- Edit by Roger 17 June 2008 -->
                <!-- back up (onchange ="minGap(this.form, this.form.txtMinGap)") -->                
				<!-- <font face="Arial" style="font-size: 11pt" size="2"> --> */ %>
				 	<input type="hidden" name="txtMinGap" size="2" value ="<%=MinGap%>"/><!--</font>-->
       <% /*   <!--<font face="Arial" size="2">&nbsp;	&lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp;	</font>
                     <font face="Arial" style="font-size: 11pt" size="2"> -->   
                <!-- Edit by Roger 17 June 2008 -->
                <!-- back up (onchange ="maxGap(this.form, this.form.txtMaxGap)")--> */ %>
                <input type="hidden" name="txtMaxGap" size="2" value ="<%=MaxGap%>" ><!-- </font>--> 
        <%/*	
               } else {*/
           %>
		<% /*		<!-- <font face="Arial" style="font-size: 11pt" size="2"> <input type="text" name="txtMinGap" size="2" value ="<%//=MinGap%>" onchange ="minGap(this.form, this.form.txtMinGap)" disabled></font>
                     <font face="Arial" size="2">&nbsp; %lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp; </font>
                     <font face="Arial" style="font-size: 11pt" size="2">
                     <input type="text" name="txtMaxGap" size="2" value ="<%//=MaxGap%>" onchange ="maxGap(this.form, this.form.txtMaxGap)" disabled></font>
                --> */ %>            
        <%/* 
             	} 
          }	else	{
		*/ %>
                <!--
				<font face="Arial" style="font-size: 11pt" size="2"> <input type="text" name="txtMinGap" size="2" value ="<%//=MinGap%>" onchange ="minGap(this.form, this.form.txtMinGap)" disabled></font>
                <font face="Arial" size="2">&nbsp; &lt;&nbsp; <%=trans.tslt("Gap")%>&nbsp; &lt;=&nbsp; </font>
                <font face="Arial" style="font-size: 11pt" size="2">   
				<input type="text" name="txtMaxGap" size="2" value ="<%//=MaxGap%>" onchange ="maxGap(this.form, this.form.txtMaxGap)" disabled></font>
                 -->          
        <% // } %>
		<% /*		<!--
				</td>
				<td style="border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" rowspan="3" bgcolor="#FFFFCC">&nbsp;
				</td>
			</tr>
			<tr>
				<td style="border-style: none; border-width: medium" width="216" bgcolor="#FFCCCC" bordercolor="#3399FF">
				<font face="Arial" size="2"><=trans.tslt("Strength is when")> </font></td>
				<td style="border-style:none; border-width:medium; " bgcolor="#FFCCCC" bordercolor="#3399FF">
				<font size="2">
   
				<font face="Arial" size="2"><=trans.tslt("Gap")>&nbsp; &gt;&nbsp;
				<//=MaxGap></font></td>
			</tr>
			<tr>
				<td style="border-style: none; border-width: medium" width="216" bgcolor="#CCFFCC" bordercolor="#3399FF">
				<font face="Arial" size="2">
				<=trans.tslt("Developmental Area is when")> </font></td>
				<td style="border-style:none; border-width:medium; " bgcolor="#CCFFCC" bordercolor="#3399FF">
				<font size="2">
   
				<font face="Arial" size="2"><=trans.tslt("Gap")>&nbsp; &lt;=&nbsp; 
				<//=MinGap>&nbsp;</font></td>
			</tr>
			<tr>
				<td style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" colspan="4" bgcolor="#FFFFCC">&nbsp;
				</td>
			</tr>
		</table> --> */%>
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
   
		<input type="button" value="<%=trans.tslt("Save")%>" name="btnSave" style="float: right" onClick="Save(this.form, this.form.selStatus)"></td>
		<td>
		<font size="2">
   
		<input type="button" value="<%=trans.tslt("Proceed")%>" name="btnNextttt" style="float: right" onClick="Next(this.form.selStatus)"></td>
	</tr>
</table>
</form>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>		