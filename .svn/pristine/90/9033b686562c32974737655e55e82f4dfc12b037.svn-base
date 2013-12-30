<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
                 java.util.Vector,
                 CP_Classes.vo.votblOrganization,
                 CP_Classes.vo.votblSurvey"%>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="RptNom" class="CP_Classes.Report_Nomination" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
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
	form.action="Report_Nomination.jsp?proceed="+field.value;
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
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}

if(request.getParameter("btnPreview") != null)
{
	RptNom.NominationStatus(logchk.getCompany(), logchk.getOrg(), Integer.parseInt(request.getParameter("selSurvey")), logchk.getPKUser());
		
	//read the file name.
	String file_name = "NominationReport.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	
	File f = new File (output);


	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"NominationReport.xls\"");

		
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
<form name="Rpt_Nomination" action="Report_Nomination.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<font face="Arial" style="font-weight: 700" color="#000080" size="2"><%=trans.tslt("Nomination Report")%></font></td>
	</tr>
		<tr>
		<td width="489">&nbsp;</td>
	</tr>
		</table>
<table border="2" width="483" bordercolor="#3399FF" cellspacing="0" cellpadding="0">
	<tr>
		<td width="498" colspan="5" style="border-style:none; border-width:medium; " bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="137" colspan="2" style="border-style:none; border-width:medium; " bordercolor="#3399FF">
		<font face="Arial" size="2">&nbsp;<b><%=trans.tslt("Organisation")%>:</b></font></td>
		<td width="134" style="border-style:none; border-width:medium; " bordercolor="#3399FF">
		<p align="left">
<%
	/*************************************************
	*The following was edited by Clement on 9-Jan-2008
	*************************************************/

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
</select></td>
		<td width="88" style="border-style:none; border-width:medium; " bordercolor="#3399FF">&nbsp; 
   
    	</td>
		<td width="97" style="border-style:none; border-width:medium; " bordercolor="#3399FF"> <font size="2">
   
    	<p align="center">
   
    	</td>
	</tr>
	<tr>
		<td width="487" align="center" height="25" colspan="5" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="487" align="center" height="25" colspan="5" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC"> <font size="2">
   		<p align="left">&nbsp;
   	<font face="Arial" size="2"><b>Survey :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </b></font>
		   
    	<select size="1" name="selSurvey">
<%
	/*************************************************
	*The following was edited by Clement on 9-Jan-2008
	*************************************************/
	boolean anyRecord=false;
	
	Vector v = CE_Survey.getSurveys(logchk.getCompany(), logchk.getOrg());
	
	/*
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
		if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
	
	query += " order by SurveyName";
	
	ResultSet rs_SurveyDetail = db.getRecord(query);*/	
		
	for(int i=0; i<v.size(); i++)
	{
		votblSurvey vo = (votblSurvey)v.get(i);
		
		anyRecord=true;
		
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
%>
	<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
<%
	}%>
</select></td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="451" align="center" bordercolor="#3399FF" style="border-style: none; border-width: medium" colspan="4" bgcolor="#FFFFCC">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="421" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" colspan="4" bgcolor="#FFFFCC"> <font size="2">
   
    	<p align="center">
<% if(!logchk.getCompanyName().equalsIgnoreCase("demo") || logchk.getUserType() == 1) {
%>
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: center">
<% } else { 
%>
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: center" disabled>
<%
} %>		
		
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-left-style: none; border-left-width: medium; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="102" align="center" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="134" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="88" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="97" style="border-right-style: none; border-right-width: medium; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
	</tr>
	</table>
</form>
<%	}	%>
<table border="0" width="610" height="26">
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>

	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
		<td align="center" height="5" valign="top"></td>
	</tr>
	<tr>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
	<%@ include file="Footer.jsp"%>
	</tr>
</table>
</font>
</body>
</html>
