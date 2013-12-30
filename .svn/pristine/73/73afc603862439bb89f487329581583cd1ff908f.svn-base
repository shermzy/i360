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
<title>Target Results</title>
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
<jsp:useBean id="Excel6" class="CP_Classes.ExcelTargetResults" scope="session"/>   
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.voCompetency"%>

<script language="javascript">
function printResult(form) {
	
	form.action = "TargetResults.jsp?print=1";
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
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
	int nameSeq = User.NameSequence(OrgID);
	
	int surveyID = SurveyResult.getSurveyID();
	int targetID = SurveyResult.getTargetID();
		
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);

/**************************************************** REPORT EXCEL *********************************************************************/
	
	if(request.getParameter("print") != null) {
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "TargetResults" + temp + ".xls";

		Excel6.WriteToReport(surveyID, targetID, pkUser, file_name);	
	
		String output = Setting.getReport_Path() + "\\" + file_name;
		File f = new File (output);
	
		response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"TargetResults.xls\"");
			
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
	
	/*
	* Change(s) : Added variable selfPtr and logic codes to store and reference the index of SELF in the array raterCode, name and asgnt
	* Reason(s) : To correctly reference the rater codes (SUP/SUB/PEERS) correctly when generating the rater's input table
	* Updated By: Sebastian
	* Updated On: 28 July 2010
	*/
	int selfPtr = 0;
	
	int c=0;
	for(int i=0; i<Code.size(); i++) {
		String [] arr = (String[]) Code.elementAt(i);
		asgnt[c] = Integer.parseInt(arr[0]);
		raterCode[c] = arr[1];
		String n1 = arr[2];
		String n2 = arr[3];
		
		//set self ptr, Sebastian 28 July 2010
		if (arr[1].toUpperCase().equals("SELF")) 
			selfPtr = c;
			
		
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
	
%>

<form name="form1" method="post" action="TargetResults.jsp">
<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td colspan="3"><strong><%=trans.tslt("RATERS' INPUT FOR TARGET")%></strong></td>
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
		compOrKBList = SurveyResult.CompOrKBList(surveyID);		
	for(int rt=0; rt<totalRT; rt++) { //loop every rating rating task
	
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
//Made changes to ensure that the table output the participant code correctly using selfPtr, Sebastian 28 July 2010
%>
	 <td align="center" width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=name[selfPtr] + "(SELF)"%></font></b></td>
<%}
	for(int rc=0; rc<raterCode.length; rc++) {
		//Display rater codes that are not SELF
		if (selfPtr != rc){
%>
  <td align="center" width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=raterCode[rc]%></font></b></td>
<%	}
} %> 
</tr>


<%	
	int id=0, kbid=0;
	String kb="";	
	int j=0;
	int x = 0;
	double compScore = 0;
	
	for(int row=0; row < totalComp; row++) {				

		String [] arr = null;
		if(row < compOrKBList.size()) {
			arr = (String[]) compOrKBList.elementAt(row);
		}
		
		if(j == 0) {//fill SELF rater competency or KB rating value			
			id = Integer.parseInt(arr[0]);	//get competency/kb id
			compName = arr[1];	//get competency name
			
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
				//Add changes to use selfPtr instead to produce the scores for SELF, Sebastian 28 July 2010
				if(totalSelf != 0) {
					compScore = SurveyResult.getCompScore(asgnt[selfPtr], RTID[rt], id);
		
					if(compScore > 0) {
%>
	<td align="center" bordercolor="#3399FF"><b>
		<%=compScore%></b>
	</td>
<% 					
					} else {

%>
	<td bordercolor="#3399FF">&nbsp;</td>	
<% 					} //compscore 
				} //end IF condition for total self								
		
//sup

//Modified codes to use vector Code to loop thru completely each rater code avg mean value and diaply in the table, Sebastian 28 July 2010

for(int i=0; i<Code.size(); i++) {

		if (i != selfPtr)
		{
					compScore = SurveyResult.getCompScore(asgnt[i], RTID[rt], id);
			
					if(compScore > 0) {
%>
	<td align="center" bordercolor="#3399FF"><b>
		<%=compScore%></b>
	</td>
<% 					
					} else { 

%>
					<td bordercolor="#3399FF">&nbsp;</td>	
<% 
					} 
		}
	
	}	
				}


			}
			if(surveyLevel == 1) {
				kbid = Integer.parseInt(arr[2]);
				id = kbid;
				kb = arr[3];
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

<% 		
			} 

%>		

<%	
			//Added code to use selfPtr to determine SELF score, Sebastian 28 July 2010
			double self = -1;
			if(totalSelf != 0) {
				self = SurveyResult.IsResultExist(asgnt[selfPtr], RTID[rt], id);
				if(self >= 0 ) {		
%>				<td align="center" bordercolor="#3399FF">
				<%=self%>
</td>
<%				} else {
%>				<td bordercolor="#3399FF">&nbsp;</td>
<%				}
			}
			
//Modified codes to use vector Code to loop thru completely each rater code avg mean value and diaply in the table, Sebastian 28 July 2010
			for(int i=0; i<Code.size(); i++)
			{
				if (i != selfPtr)
				{
					double result = SurveyResult.IsResultExist(asgnt[i], RTID[rt], id);
					
					if (result >= 0)
					{
						%>
						<td align="center" bordercolor="#3399FF">
								<%=result%>
						</td>
						<%	
					}else {
						%>
						<td align="center" bordercolor="#3399FF">&nbsp;</td>
						<%
					}
				}
			}
%>
</tr>
<%
		} 
		
	}
%>
</tr>


</table>

<p></p>
<%		
	//Edited by Roger 19 June 2008
	//Add one more key behavior column
	Vector compComment = null;
	Vector comment = null;
	Vector keyBehaviourNames = null;
	//added by tracy 08 sep 08-----
	Vector kbID = null;
	//end tracy add 08 sep 08----
	int srl = SurveyResult.getSurveyLevel();
	
	int id = 0;
	int selfIncluded = Questionnaire.SelfCommentIncluded(surveyID);
	
	if(selfIncluded == 1 && totalSelf == 1) {
%>	
<p><font style='font-size:10.0pt;font-family:Arial'><strong><%=trans.tslt("Narrative Comments by")%> <%="SELF"%></strong></font></p>	

<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<% if (srl==1) { %> 
<th width="250" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'>Key Behaviour</font></b></th>
<% } %>
<th width="400" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Narrative Comments")%></font></b></th>

<%	
	compComment = SurveyResult.CompListSurvey(surveyID);				
	for(int i=0; i<compComment.size(); i++) {
		int start = 0;
		
		voCompetency voComp = (voCompetency)compComment.elementAt(i);
		id = voComp.getPKCompetency();
		compName = voComp.getCompetencyName();
			
%>

<%
			
			//Self
			//--commented by tracy 08 sep 08-- keyBehaviourNames = SurveyResult.getKeyBehaviourNames(asgnt[totalOth], id);
			
			//added by tracy 08 sep 08----
			keyBehaviourNames = SurveyResult.getKeyBehaviour(surveyID, id);
			kbID = SurveyResult.getKeyBehaviourID(surveyID, id);
			//end add by tracy 08 sep 08----
			
			comment = SurveyResult.getComment(asgnt[totalOth], id);
			
			//commented by tracy 08 sep 08------
			//for(int j=0; j<comment.size(); j++) {
			 	//String sComment = (String) comment.elementAt(j);
				//String sKb = "";
				//if (keyBehaviourNames.size() > 0) {
					//sKb = (String) keyBehaviourNames.elementAt(j);
				//}
			// end commented by tracy 08 sep 08-------
			
			//added by tracy 08 sep 08-------
			for(int j=0; j<keyBehaviourNames.size(); j++) {
				String sKb= (String) keyBehaviourNames.elementAt(j);
				int iKbID= ((Integer)kbID.elementAt(j)).intValue();
				String sComment="";
				sComment= SurveyResult.getKBComment(asgnt[totalOth], id, iKbID);
				if(sComment.equals("")) {
					sComment="Nil";
				}
			//end add by tracy 08 sep 08------
			
				if(start == 0) {
					start++;
%>

<%
//Edited by Roger 24 June 2008 (Revision 1.4)
//Change the way narrative comment (for survey with key behaviour level) is displayed

if (srl==1) {%>
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

<% if (srl==1) {%>
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

		<tr><td align="left" bordercolor="#3399FF"><%=compName%>&nbsp;</td><% if (srl==1) { %> <td align="left" bordercolor="#3399FF">&nbsp;</td> <% } %><td align="left" bordercolor="#3399FF">Nil</td></tr>			
<%			
			}
%>	
	
</tr>	
<%		
		}
%>	
</table>

<p></p>

<% } %>


<%	
int included = Questionnaire.commentIncluded(surveyID);
if(included == 1) {

for(int rc=totalOth+totalSelf, sup=0; rc<raterCode.length; rc++, sup++) {
%>			
<p><font style='font-size:10.0pt;font-family:Arial'><strong><%=trans.tslt("Narrative Comments by")%> <%=raterCode[rc]%></strong></font></p>


<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<% if (srl==1) { %> 
<th width="250" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'>Key Behaviour</font></b></th>
<% } %>
<th width="400" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Narrative Comments")%></font></b></th>

<%	

	compComment = SurveyResult.CompListSurvey(surveyID);				
	comment = null;
	id = 0;
	
	for(int i=0; i<compComment.size(); i++) {
		int start = 0;
		
		voCompetency voComp = (voCompetency)compComment.elementAt(i);
		id = voComp.getPKCompetency();
		compName = voComp.getCompetencyName();
			
%>

<%
			//Edited  by Tracy 08 sep 08-------------			
			comment = SurveyResult.getComment(asgnt[totalOth + totalSelf + sup], id);
			//keyBehaviourNames = SurveyResult.getKeyBehaviourNames(asgnt[totalOth + totalSelf + sup], id);
			kbID= SurveyResult.getKeyBehaviourID(surveyID, id);
			keyBehaviourNames = SurveyResult.getKeyBehaviour(surveyID, id);
			System.out.println(">> Line 542 [TargetResult.jsp]:"+asgnt[totalOth + totalSelf + sup]+" | " + comment.size());
			for(int j=0; j<keyBehaviourNames.size(); j++) {
				String sKb= (String) keyBehaviourNames.elementAt(j);
				int iKbID= ((Integer)kbID.elementAt(j)).intValue();
				String sComment="";
				sComment= SurveyResult.getKBComment(asgnt[totalOth + totalSelf + sup], id, iKbID);
			if(sComment.equals("")) {
					sComment="Nil";
				}
			//end edit by tracy 08 sep 08---------
			
				if(start == 0) {
					start++;
		
%>
<% if (srl==1) {%>
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

<% if (srl==1) {%>
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
		<tr><td align="left" bordercolor="#3399FF"><%=compName%>&nbsp;</td><% if (srl==1) { %> <td align="left" bordercolor="#3399FF">&nbsp;</td> <% } %><td align="left" bordercolor="#3399FF">Nil</td></tr>		
<%			
			}
%>	

<%		
		}
%>	
</table>

<p></p>
<%  } %>

<%
compComment = null;
for(int oth=0; oth<totalOth; oth++) {

%>			
<p><font style='font-size:10.0pt;font-family:Arial'><strong><%=trans.tslt("Narrative Comments by")%> <%=raterCode[oth]%></strong></font></p>


<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<th width="200" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<% if (srl==1) { %> 
<th width="250" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'>Key Behaviour</font></b></th>
<% } %>
<th width="400" align="left" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Narrative Comments")%></font></b></th>

<%	
	compComment = SurveyResult.CompListSurvey(surveyID);				
	comment = null;
	id = 0;
	
	for(int i=0; i<compComment.size(); i++) {
		int start = 0;
		
		voCompetency voComp = (voCompetency)compComment.elementAt(i);
		id = voComp.getPKCompetency();
		compName = voComp.getCompetencyName();
			
%>

<%
			comment = SurveyResult.getComment(asgnt[oth], id);
			//Edited by Tracy 08 sep 08---------
			kbID= SurveyResult.getKeyBehaviourID(surveyID, id);
			keyBehaviourNames = SurveyResult.getKeyBehaviour(surveyID, id);
			System.out.println(">> Line 631 [TargetResult.jsp]:"+asgnt[oth]+" | " + comment.size());
			for(int j=0; j<keyBehaviourNames.size(); j++) {
				String sKb= (String) keyBehaviourNames.elementAt(j);
				int iKbID= ((Integer)kbID.elementAt(j)).intValue();
				String sComment="";
				sComment= SurveyResult.getKBComment(asgnt[oth], id, iKbID);
				if(sComment.equals("")) {
					sComment="Nil";
				}
			//End edit by tracy 08 sep 08-------------
				
				if(start == 0) {
					start++;
%>
<% if (srl==1) {%>
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

<% if (srl==1) {%>
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
		<tr><td align="left" bordercolor="#3399FF"><%=compName%>&nbsp;</td><% if (srl==1) { %> <td align="left" bordercolor="#3399FF">&nbsp;</td> <% } %><td align="left" bordercolor="#3399FF">Nil</td></tr>						
<%			
			}
%>	
	
<%		
		}
%>	
</table>

<p></p>
<%  }
} 
//by Hemilda 09012008 to add Note%>

<p>Note : Only Key Behaviours with comments are reflected in this downloaded excel file</p>
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
