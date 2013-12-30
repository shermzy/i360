<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  
  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt6" class="CP_Classes.Report_ToyotaIDP" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
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
	form.action="Report_ToyotaIDP.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function surv(form,field)
{
	form.action="Report_ToyotaIDP.jsp?surv="+field.value;
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
	logchk.setOrg(var2);
	CE_Survey.setSurvey_ID(0);
}

if(request.getParameter("surv") != null)
{ 
	int var1 = new Integer(request.getParameter("selSurvey")).intValue();
	CE_Survey.setSurvey_ID(var1);
}

if(request.getParameter("btnPreview") != null)
{
	int TargetID = new Integer(request.getParameter("selTarget")).intValue();
	Date timeStamp = new java.util.Date();
	SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
	String temp  =  dFormat.format(timeStamp);
		
	String file_name = "IndividualReport" + temp + ".xls";
	
	Rpt6.write(CE_Survey.getSurvey_ID(),TargetID, file_name);
		
	//read the file name.
	
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"ToyotaIDP_Report.xls\"");
		
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
<form name="Rpt_Report_ToyotaIDP" action="Report_ToyotaIDP.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font color="#000080" face="Arial" size="2">
		<%=trans.tslt("Raters' Input for Target")%></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bgcolor="#FFFFCC" bordercolor="#3399FF">
		<tr>
		<td width="141" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
		<td width="224" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
		<td width="120" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium"> 
		&nbsp;</td>
	</tr>
		<tr>
		<td width="141" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Organisations")%>:</font></td>
		<td width="224" style="border-style: none; border-width: medium">
		<p align="left">		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
<%
	ResultSet rs = logchk.getOrgList();
	while(rs.next())
	{
		int PKOrg = rs.getInt("PKOrganization");
		String OrgName = rs.getString("OrganizationName");
	
	if(logchk.getOrg() == PKOrg)
	{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%	}
	else	
	{%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%	}	
}%>
</select>
</td>
		<td width="120" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> </td>
	</tr>
	<tr>
		<td width="141" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
		<td width="336" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
	</tr>
	<tr>
		<td width="141" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Survey Name")%>:</font></td>
		<td width="336" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onchange="surv(this.form,this.form.selSurvey)">
<%    	if(CE_Survey.getSurvey_ID() == 0)
		{	%>
    	<option value="" selected>&nbsp;</option>
<%		}
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
	if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+logchk.getOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
		
		query = query+"ORDER BY SurveyName";
	ResultSet rs_SurveyDetail = db.getRecord(query);		
	while(rs_SurveyDetail.next())
	{
		int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		String Surv_Name = rs_SurveyDetail.getString("SurveyName");
		
	if(CE_Survey.getSurvey_ID() == Surv_ID)
	{
%>
		<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
<%	}
	else	
	{%>
		<option value=<%=Surv_ID%> ><%=Surv_Name%></option>

<%	}	
}%>

</select></td>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
	</tr>
	<tr>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Target Name")%>:</font></td>
		<td width="336" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selTarget">
<%
	boolean anyRecord = false;
	String [] TDetail = new String [14];
	String query1 ="SELECT DISTINCT b.TargetLoginID, PKUser, NameSequence FROM tblSurvey a, tblAssignment b, [User] c, tblOrganization d WHERE c.FKOrganization = d.PKOrganization AND a.SurveyID = b.SurveyID AND b.TargetLoginID=c.PKUser AND a.SurveyID ="+CE_Survey.getSurvey_ID();	
	ResultSet rs_Target = db.getRecord(query1);		
	while(rs_Target.next())
	{
		anyRecord = true;
		int TargetID = rs_Target.getInt("PKUser");
		int nameSequence = rs_Target.getInt("NameSequence");
		
		TDetail = user.getUserDetail(TargetID,nameSequence);
%>
	<option value=<%=TargetID%> selected> <%=TDetail[0]+ ", "+TDetail[1]%></option>
<%
	}%>
</select></td>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		&nbsp;</td>
	</tr>
	<tr>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
		<p align="right">
		&nbsp;</td>
		<td width="224" align="center" style="border-style: none; border-width: medium"> <font size="2">
   
		<p align="left"> &nbsp;</td>
		<td width="121" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   <%	if(anyRecord)
   	 	{%>
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: left">
	<%}%>
		</td>
	</tr>
	<tr>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px"> 
		&nbsp;</td>
		<td width="224" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px"> 
		&nbsp;</td>
		<td width="121" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px"> 
		&nbsp;</td>
	</tr>
</table>
</form>
<%	}	%>
<table border="0" width="610" height="26">
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
		</tr>
	<tr>
		<td align="center" height="5" valign="top">
		</td>
	</tr>
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
		</tr>
	<tr>
		<font size="2">
   
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright ? 2004 Pacific 
		Century Consulting Pte Ltd. All Rights Reserved.</font></td>
		</tr>
</table>
</body>
</html>