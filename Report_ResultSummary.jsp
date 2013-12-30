<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt" class="CP_Classes.Report_ResultSummary" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
function proceed(form,field)
{
	form.action="Report_ResultSummary.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
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
  else 
  { 

if(request.getParameter("proceed") != null)
{ 
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	CE_Survey.set_survOrg(var2);
}

if(request.getParameter("btnPreview") != null)
{
	int SurveyID = new Integer(request.getParameter("selSurvey")).intValue();
	Rpt.generateReport(SurveyID);

	//read the file name.
	String file_name = "SummaryReport.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"SummaryReport.xls\"");
		
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
%>
<form name="Rpt_Competencies_Survey" action="Report_ResultSummary.jsp" method="post">
<table border="0" width="49%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b>
		<font face="Arial" color="#000080" size="2">
   
    	<%=trans.tslt("Result Summary Report")%></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bordercolor="#3399FF">
		<tr>
		<td width="461" colspan="4" bordercolor="#3399FF" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
		<tr>
		<td width="117" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		<b>
		<font face="Arial" size="2">&nbsp;<%=trans.tslt("Organisations")%>:</font></b></td>
		<td width="223" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		<p align="left">		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value ="0"  selected><%=trans.tslt("List all Organization")%></option>
<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next())
	{
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
	if(CE_Survey.get_survOrg() == PKOrg)
	{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%	}
	else	
	{%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%	}	
}%>
</select><font size="2"> </font>
</td>
		<td width="121" bordercolor="#3399FF" style="border-style: none; border-width: medium"> 

   
    	</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		&nbsp;</td>
		<td width="344" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		&nbsp;</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		<p align="left"><b><font face="Arial" size="2">&nbsp;<%=trans.tslt("Survey Name")%>:</font></b></td>
		<td width="344" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey">
<%	boolean anyRecord = false;
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
		if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
		
	ResultSet rs_SurveyDetail = db.getRecord(query);		
	while(rs_SurveyDetail.next())
	{
		anyRecord = true;
		int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		String Surv_Name = rs_SurveyDetail.getString("SurveyName");
%>
	<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
<%
	}%>
</select></td>
	</tr>
	<tr>
		<td width="107" align="center" bordercolor="#3399FF" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="624" align="center" colspan="3" bordercolor="#3399FF" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
	</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bordercolor="#3399FF" bgcolor="#FFFFCC">
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="513" align="center" colspan="2" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>

	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">
		&nbsp;</td>
		<td width="313" align="center" style="border-style: none; border-width: medium"> <font size="2">
   
    	
   
		&nbsp;</td>
		<td width="131" align="center" style="border-style: none; border-width: medium"> <font size="2">
   <%	if(anyRecord)
   	 	{%>
		<% if(!logchk.getCompanyName().equalsIgnoreCase("demo") || logchk.getUserType() == 1) {
%>
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: left">
		
<% } else {
%>
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: left" disabled>
		
<%
} %>		
		<%}%>
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">
		&nbsp;</td>
		<td width="313" align="center" style="border-style: none; border-width: medium">
		&nbsp;</td>
		<td width="131" align="center" style="border-style: none; border-width: medium">
		&nbsp;</td>
	</tr>
	</table>
</form>
<%	}	%>
<table border="0" width="610" height="26">
	<tr>
		<font size="2">
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;<a style="TEXT-DECORATION: none; color:navy;" href="Login.jsp">Home</a>&nbsp;|
		<a color="navy" face="Arial">&nbsp;</a><a style="TEXT-DECORATION: none; color:navy;" href="mailto:3SixtyProfiler@pcc.com.sg?subject=Regarding:">Contact 
		Us</a><a color="navy" face="Arial" href="termofuse.htm"><span style="color: #000080; text-decoration: none"> 
		| Terms of Use </span></a>|
		<span style="color: #000080; text-decoration: none">
		<a style="TEXT-DECORATION: none; color:navy;" href="http://www.pcc.com.sg/" target="_blank">
		PCC Website</a></span></font></td>
		</font>
	</tr>
	<tr>
		<font size="2">
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright ? 2004 Pacific 
		Century Consulting Pte Ltd. All Rights Reserved.</font></td>
		</font>
	</tr>
</table>
</body>
</html>