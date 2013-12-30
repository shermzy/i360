<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  


<html>
<head>
<title>Raters Result</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<body>
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/> 
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/> 
<jsp:useBean id="Excel4" class="CP_Classes.ExcelRatersResults" scope="session"/>  
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>   
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="CESurvey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.voCompetency"%>
<script language="javascript">
function printResult(form) {
	form.action = "RatersResult.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>


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
%>



<%
	int surveyID = SurveyResult.getSurveyID();
		
	int assignmentID = SurveyResult.getAssignmentID();
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);
	int targetID = SurveyResult.TargetID(assignmentID);
	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
	int nameSeq = User.NameSequence(OrgID);



	String compName;
	String KBName;
	String RTCode [] = SurveyResult.RatingCode(surveyID);
	double Result;
	int totalComp = Questionnaire.TotalList(surveyID);
	
	Vector kbTemp = SurveyResult.TotalKB(surveyID);		// total KB group by Competency
	int totalKB [] = new int[totalComp];
	int t=0;
	for(int i=0; i<kbTemp.size(); i++) {
		int [] arr = (int[])kbTemp.elementAt(i); 
		totalKB[t++] = arr[1];
	}
	
	int totalRT = SurveyResult.TotalRT(surveyID);
	int RTID [] = SurveyResult.RTID(surveyID);

%>


<%

/**************************************************** REPORT EXCEL *********************************************************************/
	
	if(request.getParameter("print") != null) {		
		
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "RatersResults" + temp + ".xls";
	

		Excel4.WriteToReport(assignmentID, pkUser, file_name);	


	String output = setting.getReport_Path() + "\\"+file_name;
	
	File f = new File (output);

	response.reset();
	//set the content type(can be excel/word/powerpoint etc..)
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"RatersResults.xls\"");

		
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
            			//System.out.print("TEST" + ioe.getMessage());
%>
<script>
	form.action = "RatersResult.jsp?print=1";
	form.submit();
</script>
<%						
            		}
            		outs.flush();
            		outs.close();
            		in.close();	
}
	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/
	

%>

<form name="form1" method="post" action="">
<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td colspan="3"><strong><%=trans.tslt("RATERS' RESULTS")%> </strong></td>
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
  <tr height="5">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><strong><%=trans.tslt("Rater Code")%>:</strong></td>
    <td>&nbsp;</td>
    <td><strong><%=SurveyResult.RaterCode(assignmentID)%></strong></td>
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
<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<% if(surveyLevel == 1) {
%>
<th width="250" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% } %>
<% for(int a=0; a<RTCode.length; a++) {
%>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=RTCode[a]%></font></b></th>
<% } %>
<%
	Vector compOrKBList = SurveyResult.CompOrKBList(surveyID);	
	double compScore = 0;		
	int j=0, comp=0;
	int id = 0;
	for(int k=0; k<compOrKBList.size(); k++) {
		String [] CompArr = null;
		CompArr= (String[]) compOrKBList.elementAt(k);
			
		
		if(j == 0) {
			id = Integer.parseInt(CompArr[0]);
			compName = CompArr[1];	
%>
<tr>
	<td align="left" bordercolor="#3399FF">
		<%=compName%>
	</td>
<%		
		if(surveyLevel == 1) {
%>
<td bordercolor="#3399FF">&nbsp;</td>	
<%							
			for(int i=0; i<totalRT; i++) {			
				compScore = SurveyResult.getCompScore(assignmentID, RTID[i], id);
			
			if(compScore > 0) {
%>
	<td align="center" bordercolor="#3399FF"><b>
		<%=compScore%></b>
	</td>
<% } else { %>
<td bordercolor="#3399FF">&nbsp;</td>	
<% } }				
		
		}
		
		}
		if(surveyLevel == 1) {

			j++;
			if(j == totalKB[comp]) {
				j = 0;
				comp++;
			}
			//System.out.println (j + "--" + totalKB[comp] + "--" + comp);
			
				
			id = Integer.parseInt(CompArr[2]); 	
			KBName = CompArr[3];
%>

</tr>

<tr>
<td align="left" bordercolor="#3399FF">&nbsp;</td>
	<td align="left" bordercolor="#3399FF">
		<%=KBName%>
	</td>			
<%		} 
		//Added code to display value 0 as blank when Hide NA option is chosen or NA is excluded from calculation. Kian Hwee 17 March 2010
		int NA_Included = CESurvey.getNA_Included(surveyID);
		for(int i=0; i<totalRT; i++) {			
			Result = SurveyResult.IsResultExist(assignmentID, RTID[i], id);
			if(Result > 0 || NA_Included==1) {
%>
	<td align="center" bordercolor="#3399FF">
		<%=Result%>
	</td>
<% } else { %>
<td bordercolor="#3399FF">&nbsp;</td>	
<% } }%>
</tr>
<% } 
%>

</table>
<%
	int included = Questionnaire.commentIncluded(surveyID);
	int selfIncluded = Questionnaire.SelfCommentIncluded(surveyID);
	//Changed by Ha 16/06/08
	if(!SurveyResult.RaterCode(assignmentID).equals("SELF")&&included == 1 || (selfIncluded == 1 && SurveyResult.RaterCode(assignmentID).equals("SELF"))) {
%>

<p></p>

<p><font style='font-size:10.0pt;font-family:Arial'><strong><%=trans.tslt("Narrative Comments")%></strong></font></p>

<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>

<% 
//Added by Roger 19 June 2008
//Additional key behaviour column
if(surveyLevel == 1) {
%>
<th width="250" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% } %>

<th width="400" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Narrative Comments")%></font></b></th>

<%
	Vector compComment = SurveyResult.CompListSurvey(surveyID);	
	//Added by Roger 19 June 2008 
	//Add an additional keyhaviour column when survey level is key behaviour
	Vector keyBehaviourNames = null;		
	Vector comment = null;
	id = 0;
	for(int i=0; i<compComment.size(); i++) {
		voCompetency vo = (voCompetency)compComment.elementAt(i);
		
		int start = 0;
		//Changed by Ha 16/06/08 from getCompetencyID to getPKCompetency
		id = vo.getPKCompetency();
		compName = vo.getCompetencyName();
			
%>

<%
			comment = SurveyResult.getComment(assignmentID, id);
			//Added by Roger 19 June 2008
			keyBehaviourNames = SurveyResult.getKeyBehaviourNames(assignmentID, id);
			for(int k=0; k<comment.size(); k++) {
				String sComment = (String)comment.elementAt(k);
				String sKb = "";
				if (keyBehaviourNames.size() > 0) {
					//Edited by Roger 23 June 2008 to fix a bug
					sKb = (String) keyBehaviourNames.elementAt(k);
				}
		
				if(start == 0) {
					start++;
%>
<%
//Edited by Roger 24 June 2008 (Revision 1.5)
//Change the way narrative comment (for survey with key behaviour level) is displayed

 if (surveyLevel==1) {%>
<tr>
		<td width="200" align="left" bordercolor="#3399FF"><%=compName%></td>
		<td width="250" bordercolor="#3399FF">&nbsp; </td>
		<td align="left" bordercolor="#3399FF">&nbsp; </td>
</tr>

<tr>
		<td width="200" align="left" bordercolor="#3399FF">&nbsp;</td>
		<td width="250" bordercolor="#3399FF"> <%=sKb%></td>
		<td align="left" bordercolor="#3399FF"><%=sComment%></td>
</tr>


<% } else { %>
<tr>
		<td width="200" align="left" bordercolor="#3399FF"><%=compName%></td>
		<td align="left" bordercolor="#3399FF"><%=sComment%></td>
</tr>

<% } %>

<%			
				}else {
%>

<% if (surveyLevel==1) {%>
<tr>
		<td width="200" align="left" bordercolor="#3399FF">&nbsp;</td>
		<td width="250" bordercolor="#3399FF"> <%=sKb%></td>
		<td align="left" bordercolor="#3399FF"><%=sComment%></td>
</tr>

<% } else { %>
<tr>
		<td width="200" align="left" bordercolor="#3399FF"><%=compName%></td>
		<td align="left" bordercolor="#3399FF"><%=sComment%></td>
</tr>

<% } %>		
<%				
				
			}
%>
	
<%				
			}
			if (start == 0) {
%>
		<tr><td><%=compName%>&nbsp;</td><% if (surveyLevel==1) { %> <td>&nbsp;</td> <% } %><td align="left" bordercolor="#3399FF">Nil</td></tr>					
<%			
			}
%>	
	

<%		
		}
%>	
</table>

<% } %>
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
<p>&nbsp;</p>
<% } %>
</body>
</html>
