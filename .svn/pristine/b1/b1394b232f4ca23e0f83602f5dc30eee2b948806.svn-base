<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.util.Calendar,
                 java.text.*,
                 java.lang.String,
				 CP_Classes.vo.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/> 
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose Relation to Merge</title>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function save(form){
	if(confirm("Save the Merge Relation option?")){
	form.action = "MergeRelation.jsp?save=1";
	form.method = "post";
	form.submit();
}}
</SCRIPT>
<body>
<%
int checkpeer = 0;
int checksub = 0;
int checkadd = 0;
if (request.getParameter("save")!=null){
	if(request.getParameter("checkpeer")!=null){
		checkpeer = 1;
	}
	if(request.getParameter("checksub")!=null){
		checksub = 1;
	}
	if(request.getParameter("checkadd")!=null){
		checkadd = 1;
	}
	
	SimpleDateFormat formatter_db1 = new SimpleDateFormat ("dd/MM/yyyy");
	SimpleDateFormat formatter_db2= new SimpleDateFormat ("yyyy/MM/dd");
	SimpleDateFormat day_view= new SimpleDateFormat ("dd/MM/yyyy");
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
	
	int Comment_Included = 0;
	
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
	FKOrg = vo.getFKOrganization();
	
	String MinGap = Float.toString(vo.getMIN_Gap()); //get values from database
	String MaxGap = Float.toString(vo.getMAX_Gap());
	Comment_Included = vo.getComment_Included();
	int selStatus = vo.getSurveyStatus();
	int Reliability = vo.getReliabilityCheck();
	int SurveyID = vo.getSurveyID();
	int FKCompanyID = vo.getFKCompanyID();
	int XComment_Included = vo.getComment_Included();
	CE_Survey.setCompetencyLevel(LevelofSurvey);
	CE_Survey.setSurveyStatus(db_SurveyStatus);
	CE_Survey.set_survOrg(FKOrg);
	
	int MergeRelation = checkadd + checksub * 2 + checkpeer * 4;

	CE_Survey.setMergeRelation(MergeRelation);
	boolean success = CE_Survey.updateRecord(Survey_Name,JobPosID1,month,DateOpened,LevelofSurvey,DeadlineDate,selStatus,AnalysisDate,Reliability,NA_Included,SurveyID,CE_Survey.get_survOrg(),FKCompanyID,MinGap,MaxGap,XComment_Included, logchk.getPKUser(), useCluster, CE_Survey.getHideCompDesc(), breakCPR, CE_Survey.getMergeRelation());

	
    if(success){
    	%>
    	<script>alert("Options successfully saved.");</script>
    	<%
    }
    else{
    	%>
    	<script>alert("An error occurred. Options are not saved.");</script>
    	<%
    }

}

%>
<form name="MergeRelation" action="MergeRelation.jsp" method="post">
<font size="2">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font face="Arial" size="2" color="#000080"><%="Choose the Relation to be merged with Additional Raters into Others" %></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="1" width="99%" bordercolor="#3399FF" bgcolor="#FFFFCC">
<tr>
<%if (CE_Survey.getMergeRelation() / 4 == 0 ) {%>
<td><input type="checkbox" name="checkpeer" value=1 >

<%}
else{
	%>
	<td><input type="checkbox" name="checkpeer" checked value=1 >
	<%
}
	%>

</td>
<td><b>Peer</b>
</td>
</tr>
<tr>
<%if (((CE_Survey.getMergeRelation() % 4) /2)== 0 ) {%>
<td><input type="checkbox" name="checksub" value=2 >
<%}
else{
	%>
	<td><input type="checkbox" name="checksub" checked value=2 >
	<%
}
	%>

</td>
</td>
<td><b>Subordinates</b>
</td>
</tr>
<tr>
<%if (CE_Survey.getMergeRelation() % 2 == 0 ) {%>
<td><input type="checkbox" name="checkadd" value=3 >
<%}
else{
	%>
	<td><input type="checkbox" name="checkadd" checked value=3 >
	<%
}
	%>
</td>
</td>
<td><b>Additional Raters</b>
</td>
</tr>

</table>
<table border="0" width="99%" cellspacing="0" cellpadding="0">
	<tr>
		<td>&nbsp;</td>
		<td width="142">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td width="142">
		<input type="button" value=" Save  " name="btnSave" style="float: right" onClick="save(this.form)"></td>
	</tr>
	<tr>
	<td>&nbsp;
	</td>
	<td width="142">
		<input type="button" value="Cancel" name="btnCancel" style="float: right" onClick="window.close()"></td>
	</tr>
</table>
</font>
</form>

</body>
</html>