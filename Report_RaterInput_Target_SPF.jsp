<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
				 java.util.*,
				 CP_Classes.vo.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="Rpt6sSPF" class="CP_Classes.Report_RaterInputForTarget_SPF" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="userDetail" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="userJ" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Questionnaire" class="CP_Classes.Questionnaire" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
function proceed(form,field)
{
	form.action="Report_RaterInput_Target_SPF.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function surv(form,field)
{
	form.action="Report_RaterInput_Target_SPF.jsp?surv="+field.value;
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
	
	boolean commentInclude = false;
	if(request.getParameter("comment") != null)
		commentInclude = true;
	
	// Get name of Target to append to filename, Desmond 28 Oct 09
	String [] userInfo = userDetail.getUserDetail(TargetID, 0);
		
	//read the file name.
	Date timeStamp = new java.util.Date();
	SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yy[HH.mm]");
	String temp  =  dFormat.format(timeStamp);
	String file_name = "SPF Summary Report";
	file_name += "("+temp+").xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);
	System.out.println("Rater Input Target SPF, target ID is "+TargetID);
	Rpt6sSPF.AllRaters(CE_Survey.getSurvey_ID(), TargetID, logchk.getPKUser(), file_name, commentInclude);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	response.addHeader ("Content-Disposition", "attachment;filename=\""+file_name+"\"");
		
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

	} catch (IOException ioe) {
		ioe.printStackTrace(System.out);
	}

	outs.flush();
	outs.close();
	in.close();	
}
%>
<form name="Rpt_RaterInput_Target_SPF" action="Report_RaterInput_Target_SPF.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font color="#000080" face="Arial" size="2">
		<%=trans.tslt("SPF Summary Report")%></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bgcolor="#FFFFCC" bordercolor="#3399FF">
		<tr>
		<td width="141" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="224" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="120" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp; </td>
	</tr>
		<tr>
		<td width="141" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Organisation")%>:</font></td>
		<td width="224" style="border-style: none; border-width: medium">
		<p align="left">		
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){ %>
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value="0" selected><%=trans.tslt("All")%></option>
	<%
		Vector vOrg = logchk.getOrgList(logchk.getCompany());

		for(int i=0; i<vOrg.size(); i++)
		{
			votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
			int PKOrg = vo.getPKOrganization();
			String OrgName = vo.getOrganizationName();

			if(logchk.getOrg() == PKOrg)
			{ %>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
			<% } else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		} 
	} else { %>
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select>
</td>
		<td width="120" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> </td>
	</tr>
	<tr>
		<td width="141" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="336" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="141" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Survey")%>:</font></td>
		<td width="336" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onchange="surv(this.form,this.form.selSurvey)">
<%    	if(CE_Survey.getSurvey_ID() == 0)
		{	%>
    	<option value="" selected>&nbsp;</option>
<%		}
	
		/*********************************
		*Edit By James 14 - Nov 2007
		*********************************/
	    //Vector vS=CE_Survey.getSurveys(logchk.getCompany(),CE_Survey.get_survOrg());
		
		/* Changed by Ha 23/05/08 to list the survey according to the organisation */		
		Vector vS=CE_Survey.getSurveys(logchk.getCompany(),logchk.getOrg());
		for(int i=0;i<vS.size();i++)
	
		{
		votblSurvey vo=(votblSurvey)vS.elementAt(i);
		
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
		
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
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Target")%>:</font></td>
		<td width="336" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selTarget">
    	<option value=0 selected> All Targets </option>
<%
	boolean anyRecord = false;
	String [] TDetail = new String [14];
	
	int seq = userJ.NameSequence(logchk.getOrg());
	
	Vector v = CE_Survey.getAllUsers(seq, CE_Survey.getSurvey_ID());
	
	for(int i=0; i<v.size(); i++)
	{
		anyRecord = true;
		voUser vo = (voUser)v.elementAt(i);
		int TargetID = vo.getPKUser();
		//int nameSequence = rs_Target.getInt("NameSequence");
		
		//TDetail = userDetail.getUserDetail(TargetID,nameSequence);
%>
	<option value=<%=TargetID%> selected> <%=vo.getFullName()%></option>
<%
	}%>
</select></td>
	</tr>
	<tr>
	<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
	</td>
	</tr>
	<tr>
	<%
		if(Questionnaire.commentIncluded(CE_Survey.getSurvey_ID()) == 1){
	%>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Include Narrative Comments")%>:</font></td>
		<td  width="141" align="Left" style="border-left-style: solid; border-left-width: 0px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<input type="checkbox" name="comment" value="T">
		</td>
	<% } %>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
		<p align="right">
		</td>
		<td width="224" align="center" style="border-style: none; border-width: medium"> <font size="2">
   
		<p align="left"> </td>
		<td width="121" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
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
		<td width="141" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="224" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="121" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
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
		
		<%@ include file="Footer.jsp"%>
	</tr>
</table>
</body>
</html>