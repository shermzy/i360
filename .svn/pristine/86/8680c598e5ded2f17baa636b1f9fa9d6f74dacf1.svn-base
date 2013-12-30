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
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Rater's Input By Group</title>
<style type="text/css">
<!--
body,td,th {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
}
-->
</style></head>

<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="SurveyResult" class="CP_Classes.SurveyResult" scope="session"/>
<jsp:useBean id="Calculation" class="CP_Classes.Calculation" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="Excel2" class="CP_Classes.ExcelTargetTrimmedMean" scope="session"/>   
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>    
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
function printResult(form) {
	form.action = "TargetTrimmedMean.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>

<body>
<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {	%>   
	<script>
		parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  {
  	int surveyID = SurveyResult.getSurveyID();
	
	int surveyLevel = Calculation.LevelOfSurvey(surveyID);
	int targetID = SurveyResult.getTargetID();
	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
	int nameSeq = User.NameSequence(OrgID);
	int reliabilityCheck = Calculation.ReliabilityCheck(surveyID);
	
	//System.out.println(surveyID + "--" + targetID);
	
	/**************************************************** REPORT EXCEL *********************************************************************/
	if(request.getParameter("print") != null) {
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "RaterInputByGroup" + temp + ".xls";
		Excel2.WriteToReport(surveyID, targetID, pkUser, file_name);	
	
		String output = Setting.getReport_Path() + "//" +  file_name;
		File f = new File (output);
	
		response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"RaterInputByGroup.xls\"");
			
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

						outs.flush();
						outs.close();
						in.close();	
	}
	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/

%>
<form action="" method="post" name="result">
<table width="800" border="0"  font style='font-size:10.0pt;font-family:Arial'>
 <% if (reliabilityCheck == 0) {
 // Changed by HA 07/07/08 so  reliability index/trimmed mean can be displayed correctly%> 
  <tr>
    <td colspan="3"><strong><%=trans.tslt("RATERS' INPUT BY GROUP - TRIMMED MEAN")%></strong></td>
  </tr>
  
  <%} else {%>
   <tr>
    <td colspan="3"><strong><%=trans.tslt("RATERS' INPUT BY GROUP - RELIABILITY INDEX")%></strong></td>
  </tr> <%} %>
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
<%
	Vector vRT 		= SurveyResult.getRatingTask(surveyID);
	Vector vComp 	= SurveyResult.CompList(surveyID);
	
	int totalSup 	= SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SUP%");
	int totalOth 	= SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "OTH%");
	int totalSelf 	= SurveyResult.TotalRaterCodeSpecific(surveyID, targetID, "SELF");
	
	String raterName [] = SurveyResult.RatersName(targetID);
	String name = "";
	
	if(nameSeq == 0)
		name = raterName[1] + " " + raterName[0];
	else
		name = raterName[0] + " " + raterName[1];
		
	if(vRT != null) {
		for(int rt=0; rt<vRT.size(); rt++) {
		
			int rtid = Integer.parseInt(((String [])vRT.elementAt(rt))[0]);
			String rtName = ((String [])vRT.elementAt(rt))[1] + " (" + ((String [])vRT.elementAt(rt))[2] + ")";
%>
&nbsp;
<p><strong><%=rtName%></strong></p>
<table border="1" style='border-color:#3399FF; font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC">
<th width="300" align="center" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Competency")%></font></b></th>
<%			
			if(surveyLevel == 1) {
%>
<th width="500" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<%			
			}
			
			if(totalSelf > 0) { 
%>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%="SELF(" + name + ")"%></font></b></th>
<% 
			} 
			if(totalSup > 0) { 
%>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("SUP")%></font></b></th>
<% 
			} 
			if(totalOth > 0) { 
%>	
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("OTHER")%></font></b></th>
<% 
			} 
%>	
<th width="100" bgcolor="navy" bordercolor="#3399FF"><b><font style='color:white'><%=trans.tslt("ALL")%></font></b></th>

<%
	//write all the competencies and KBs here
	for(int comp=0; comp<vComp.size(); comp++) {
		int fkComp 		= Integer.parseInt(((String [])vComp.elementAt(comp))[0]);
		String compName = ((String [])vComp.elementAt(comp))[1];
		
		double self = 0, sup=0, oth=0, all=0;
%>
<tr>
	<td><strong><%=compName%></strong></td>
<%
	//System.out.println(surveyLevel + ", " + reliabilityCheck + ", " + totalSelf + ", " + totalSup + ", " + totalOth);
	if(surveyLevel == 1) {
		if(reliabilityCheck == 0)
		{
			
			all = SurveyResult.TrimmedMeanAll(surveyID, targetID, fkComp, rtid);
		}
		else
		{
			
			all = SurveyResult.getAvgMean(surveyID, targetID, 1, fkComp, rtid);
			
		}
%>
	<td>&nbsp;</td>
<%	
	}else {
		if(reliabilityCheck == 0)
		{
		
			all = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 1, fkComp, 0, rtid);
			
		}
		else
			all = SurveyResult.getTargetAvgMean(surveyID, targetID, 1, fkComp, 0, rtid);
			
	}
	
	if(totalSelf > 0) {
		if(surveyLevel == 1)
			self = SurveyResult.getAvgMean(surveyID, targetID, 4, fkComp, rtid);
		else 		
			self = SurveyResult.TargetResult(surveyID, targetID, "SELF", fkComp, 0, rtid);
%>
	<td align="center"><strong><%=self%></strong></td>
<%	
	}
	if(totalSup > 0) {
		/*System.out.println("[TargetTrimmedMean] surveyID = " + surveyID);
		System.out.println("[TargetTrimmedMean] targetID = " + targetID);
		System.out.println("[TargetTrimmedMean] fkComp = " + fkComp);
		System.out.println("[TargetTrimmedMean] rtid = " + rtid);*/
		if(surveyLevel == 1)
			sup = SurveyResult.getAvgMean(surveyID, targetID, 2, fkComp, rtid);
		else {
			if(reliabilityCheck == 0)
				sup = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 2, fkComp, 0, rtid);
			else
				sup = SurveyResult.getTargetAvgMean(surveyID, targetID, 2, fkComp, 0, rtid);
		}
%>
	<td align="center"><strong><%=sup%></strong></td>
<%	
	}
	if(totalOth > 0) {
		if(surveyLevel == 1)
			oth = SurveyResult.getAvgMean(surveyID, targetID, 3, fkComp, rtid);
		else {
			if(reliabilityCheck == 0)
				oth = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 3, fkComp, 0, rtid);
			else
				oth = SurveyResult.getTargetAvgMean(surveyID, targetID, 3, fkComp, 0, rtid);
		}
%>
	<td align="center"><strong><%=oth%></strong></td>
<%	
	}
%>
	<td align="center"><strong><%=all%></strong></td>
</tr>
<%
		if(surveyLevel == 1) {
			
			Vector vKB = SurveyResult.KBList(surveyID, fkComp);
			if(vKB != null) {
%>
<tr>	
<%			
				for(int kb=0; kb<vKB.size(); kb++) {
					int fkKB 		= Integer.parseInt(((String [])vKB.elementAt(kb))[0]);
					String kbName = ((String [])vKB.elementAt(kb))[1];
%>
	<td>&nbsp;</td>
	<td><%=kbName%></td>
<%
	if(totalSelf > 0) {
%>
	<td align="center"><%=SurveyResult.TargetResult(surveyID, targetID, "SELF", fkComp, fkKB, rtid)%></td>
<%	
	}
	if(totalSup > 0) {
		if(reliabilityCheck == 0)
			sup = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 2, fkComp, 0, rtid);
		else 		
			sup = SurveyResult.getTargetAvgMean(surveyID, targetID, 2, fkComp, fkKB, rtid);
%>
	<td align="center"><%=SurveyResult.getTargetTrimmedMean(surveyID, targetID, 2, fkComp, fkKB, rtid)%></td>
<%	
	}
	if(totalOth > 0) {
		if(reliabilityCheck == 0)
			oth = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 3, fkComp, 0, rtid);
		else 		
			oth = SurveyResult.getTargetAvgMean(surveyID, targetID, 3, fkComp, fkKB, rtid);
%>
	<td align="center"><%=SurveyResult.getTargetTrimmedMean(surveyID, targetID, 3, fkComp, fkKB, rtid)%></td>
<%	
	}
	
	if(reliabilityCheck == 0)
		{
		all = SurveyResult.getTargetTrimmedMean(surveyID, targetID, 1, fkComp, fkKB, rtid);
		System.out.println("All "+all);
		}
	else 
		
		all = SurveyResult.getTargetAvgMean(surveyID, targetID, 1, fkComp, fkKB, rtid);
%>	
	<td align="center"><%=all%></td>
</tr>	
<%				
				}
			}
		}
%>	
</tr>	
<%		
	}	//for each competency
	
%>


</table>
<%
		}//for(int rt=0; rt<vRT.size(); rt++)	
	}//if(vRT != null)
%>
<p></p>
<%
	if(compID != 2) {
%>	
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>">
<% } else { %>
<input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>" disabled>
<% } %>
</form>
<%
	}
%>
</body>
</html>
