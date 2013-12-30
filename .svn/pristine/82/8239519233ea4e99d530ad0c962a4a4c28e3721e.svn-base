<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Reliability Index</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>

<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="RatingTask" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/> 
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="Excel1" class="CP_Classes.ExcelReliabilityIndex" scope="session"/>  
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>     
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
function printResult(form) {
	form.action = "ReliabilityIndex.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>


<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
  	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int nameSeq = User.NameSequence(OrgID);
	
	int surveyID = SurveyResult.getSurveyID();
	int targetID = SurveyResult.getTargetID();
	int pkUser = logchk.getPKUser();
		
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);


/**************************************************** REPORT EXCEL *********************************************************************/
	if(request.getParameter("print") != null) {
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "ReliabilityIndex" + temp + ".xls";
		
		Excel1.WriteToReport(surveyID, targetID, pkUser, file_name);	
	
		String output = Setting.getReport_Path() + "//" + file_name;
		File f = new File (output);

		response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"ReliabilityIndex.xls\"");
			
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			
			
			int bit = 256;
			int i = 0;
	
	
				try {
						while ((bit) >= 0) {
							bit = in.read();
							outs.write(bit);
						}
						//System.out.println("" +bit);
	
						} catch (IOException ioe) {
							ioe.printStackTrace(System.out);
						}
				//		System.out.println( "\n" + i + " bytes sent.");
				//		System.out.println( "\n" + f.length() + " bytes sent.");
						outs.flush();
						outs.close();
						in.close();	
	}	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/
	
	

	String compName;
	String KBName;
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	int Result, ResultSup, ResultSelf;
	
	int RTID [] = SurveyResult.RTID(surveyID);
	
	Vector kbTemp = SurveyResult.TotalKB(surveyID);		// total KB group by Competency
	int totalComp = Questionnaire.TotalList(surveyID);
	
	int totalKB [] = new int[totalComp];
	int t=0;

	for(int i=0; i<kbTemp.size(); i++) {
		int [] arr = (int[])kbTemp.elementAt(i); 
		totalKB[t++] = arr[1];
	}
		
	int totalRaterCode = SurveyResult.TotalRaterCode(surveyID, targetID);
	Vector Code = SurveyResult.RaterCode(surveyID, targetID);
	String raterCode [] = new String[totalRaterCode];
	String name [] = new String[totalRaterCode];
	int asgnt [] = new int [totalRaterCode];
	String reliability = "";
	
	int c=0;
	for(int i=0; i<Code.size(); i++) {
		String arr[] = (String []) Code.elementAt(i);
		
		asgnt[c] = Integer.parseInt(arr[0]);
		raterCode[c] = arr[1];
		String n1 = arr[2];
		String n2 = arr[3];
		
		if(nameSeq == 0)
			name[c] = n2 + " " + n1;
		else
			name[c] = n1 + " " + n2;
		c++;
	}
	
	int totalRT = SurveyResult.TotalRT(surveyID);
	int totalSup = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
	int totalOth = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");
	int totalSelf = SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SELF");
	Vector compOrKBList = null;
	
	int totalOthCompleted = SurveyResult.TotalCompleted(surveyID, targetID, "OTH%");
	int totalSelfCompleted = SurveyResult.TotalCompleted(surveyID, targetID, "SELF");
	int totalSupCompleted = SurveyResult.TotalCompleted(surveyID, targetID, "SUP%");

	double score = 0;

%>

<form name="form1" method="post" action="ReliabilityIndex.jsp">

<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td colspan="3"><strong><%=trans.tslt("RELIABILITY INDEX")%> </strong></td>
  </tr>
  <tr>
    <td width="193">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="573">&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Company Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.CompanyName(compID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Organisation Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.OrganizationName(OrgID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Survey Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.SurveyName(surveyID)%></strong></td>
  </tr>
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Target Name")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.UserName(OrgID, targetID)%></strong></td>
  </tr>
  <tr>
    <td height="5">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="left"><strong><%=SurveyResult.SurveyLevel(surveyID)%></strong></td>
  </tr>
</table>

<p></p>

<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<%
	for(int rt=0; rt<totalRT; rt++) {
		compOrKBList = SurveyResult.CompOrKBList(surveyID);			
		String RTName = RatingTask.getRatingTask(RTCode[rt]);
	if(surveyLevel == 0) {
%>	
	<td width="300" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=RTCode[rt] + "(" + RTName + ")"%></font></b></td>
<%		
	}else {
%>
	<td width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=RTCode[rt] + "(" + RTName + ")"%></font></b></td>
	<td width="500" align="center" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% } %>

<% if(totalSelf != 0) {
%>
	 <td align="center" width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=name[totalOth] + "(SELF)"%></font></b></td>
<%}
	for(int rc=totalOth+totalSelf; rc<raterCode.length; rc++) {
%>
  <td align="center" width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=raterCode[rc]%></font></b></td>
<%	} %>  
<%
	for(int rc=0; rc<totalOth; rc++) {
%>
  <td align="center" width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=raterCode[rc]%></font></b></td>
<%	} %>  
</tr>



<%	
	int id=0, kbid=0;
	String kb="";	
	int j=0;
	int x = 0;
	double self = -1;
	for(int row=0; row < totalComp; row++) {				
		String [] CompArr = null;
	
		if(row < compOrKBList.size())
			CompArr= (String[]) compOrKBList.elementAt(row);
			
		if(j == 0) {			
			id = Integer.parseInt(CompArr[0]);
			compName = CompArr[1];
%>
<tr>
	<td align="left" bordercolor="#3399FF">
<% if(surveyLevel == 1) {
%>
	<b><%=compName%></b>	
<%
} else{
%>	
		<%=compName%>
	<% } %>
	
	</td>
<%	
if(surveyLevel == 1) {
%>	
	<td bordercolor="#3399FF">&nbsp;</td>
<%				
		if(totalSelf != 0) {
			score = SurveyResult.getCompScore(asgnt[totalOth], RTID[rt], id);
%>			
<td align="center" bordercolor="#3399FF"><b>
		<%=score%>
		</b>
</td>
<%
	score = 0;
	}
	
	for(int sup=0; sup<totalSup; sup++) {
		score = SurveyResult.getCompScore(asgnt[totalOth + totalSelf + sup], RTID[rt], id);

%>
<td align="center" bordercolor="#3399FF"><b>
		<%=score%>
		</b>
</td>
<%		
	score = 0;
	}
%>



<%
	for(int oth=0; oth<totalOth; oth++) {
		score = SurveyResult.getCompScore(asgnt[oth], RTID[rt], id);
%>
<td align="center" bordercolor="#3399FF"><b>
		<%=score%>
		</b>
</td>
<%
		score = 0;
	}
		}
		}
		
if(surveyLevel == 1) {

		kbid = Integer.parseInt(CompArr[2]);
		id = kbid;
		kb = CompArr[3];
		j++;
		if(j == totalKB[x]) {
			j = 0;
			x++;
		}
%>
</tr>
<td bordercolor="#3399FF">&nbsp;</td>
<td align="left" bordercolor="#3399FF">
		<%=kb%>
</td>

<% } %>		

<%	
self = -1;
	if(totalSelf != 0) {
		self = SurveyResult.IsResultExist(asgnt[totalOth], RTID[rt], id);
		if(self >= 0 ) {		
%>			
<td align="center" bordercolor="#3399FF">
		<%=self%>
</td>
<%	 } else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<%	
	}
	}
	int flag = 0;
	for(int sup=0; sup<totalSup; sup++) {
		double sup1 = SurveyResult.IsResultExist(asgnt[totalOth + totalSelf + sup], RTID[rt], id);
		if(sup1 >= 0 ) {	
			
%>
<td align="center" bordercolor="#3399FF">
		<%=sup1%>
</td>
<%		}else {
	
%>
<td align="center" bordercolor="#3399FF">&nbsp;</td>
<%
		}
	} 
%>



<%
	flag = 0;
	for(int oth=0; oth<totalOth; oth++) {
		double oth1 = SurveyResult.IsResultExist(asgnt[oth], RTID[rt], id);
		if(oth1 >= 0 ) {	
%>
<td align="center" bordercolor="#3399FF">
		<%=oth1%>
</td>
<%		}else {

%>
<td align="center" bordercolor="#3399FF">&nbsp;</td>
<%
		}
	}
%>
</tr>
<%
	} 
	}
%>
</tr>

<tr>
<td bordercolor="#3399FF">&nbsp;</td>
<% if (surveyLevel == 1) {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<% } %>

<%
  int selfIndex = -1;
  if(totalSelf != 0 ) {
  	 if(totalSelfCompleted != 0)
	 	selfIndex = SurveyResult.RatersStatus(asgnt[totalOth]);

	  if(selfIndex > 0) {
		//Changed by Ha 25/06/08 if the rater status is 4 it is unreliable
  			if(selfIndex == 2 || selfIndex == 4)
				reliability = "Unreliable";
  			else if (selfIndex==5)
  		//Added by Ha 01/07/08 one more status: rater NA
  				reliability = "NA";
			else
				reliability = "Reliable";

%>
<td align="center" bordercolor="#3399FF">&nbsp;</td>
<%
	}  else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<% } 
}%>


<%		
	for(int sup=0; sup<totalSup; sup++) {		
		int sup1 =  SurveyResult.RatersStatus(asgnt[totalOth+totalSelf+sup]);

		if(sup1 > 0 ) {	
			if(sup1 == 2 || sup1 == 4)
			//Changed by Ha 25/06/08 if the rater status is 4 it is unreliable
				reliability = "Unreliable";
			else if (sup1 == 5)
			//Added by Ha 01/07/08 one more status: rater NA
  				reliability = "NA";
			else
				reliability = "Reliable";

%>
<td align="center" bordercolor="#3399FF"><%=reliability%></td>
<%
	}  else {
%>
<td bordercolor="#3399FF">&nbsp;</td>
<% } 	
	}	%>


<%	
	for(int oth=0; oth<totalOth; oth++) {
		int other =  SurveyResult.RatersStatus(asgnt[oth]);

		if(other > 0 ) {	
			//Changed by Ha 25/06/08 if the rater status is 4 it is unreliable
			if(other == 2 || other == 4)
				reliability = "Unreliable";
			//Added by Ha 01/07/08 one more status: rater NA
			else if (other == 5)
  				reliability = "NA";
			else
				reliability = "Reliable";

%>
<td align="center" bordercolor="#3399FF"><%=reliability%></td>
<%
	}  else {
%>
<td align="center" bordercolor="#3399FF">&nbsp;</td>
<% } 	
	}	%>
</tr>

</table>

<p></p>
<p>
<%
	if(compID != 2 || logchk.getUserType() == 1) {
%>	
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>">
<% } else { %>
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>" disabled>
<% } %>
</p>
</form>
<% } %>
</body>
</html>
