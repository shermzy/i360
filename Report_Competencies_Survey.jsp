<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
				 java.util.*,
				 CP_Classes.vo.*"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt3" class="CP_Classes.Report_ListOfCompetencies_Survey" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
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
	form.action="Report_Competencies_Survey.jsp?proceed="+field.value;
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
	logchk.setOrg(var2);
}

if(request.getParameter("btnPreview") != null)
{
	int SurveyID = new Integer(request.getParameter("selSurvey")).intValue();
	int Comp = new Integer(request.getParameter("CompType")).intValue();

	if(Comp != 2)
	{
		Rpt3.AllCompetencies(SurveyID, Comp, logchk.getPKUser());
	}
	else
	{
		Rpt3.AllCompetencies_KeyBehav(SurveyID, logchk.getPKUser());
	}
		
	//read the file name.
	String file_name = "List Of Competencies for Specific Survey.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"List Of Competencies for Specific Survey.xls\"");
		
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
<form name="Rpt_Competencies_Survey" action="Report_Competencies_Survey.jsp" method="post">
<table border="0" width="49%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b>
		<font face="Arial" color="#000080" size="2">
   
    	<%=trans.tslt("Competency Listing for Specific Survey")%></b></td>
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
		<font face="Arial" size="2">&nbsp;<%=trans.tslt("Organisation")%>:</font></b></td>
		<td width="223" bordercolor="#3399FF" style="border-style: none; border-width: medium">
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
</select><font size="2"> </font>
</td>
		<td width="121" bordercolor="#3399FF" style="border-style: none; border-width: medium"> 

   
    	</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="344" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium">
		<p align="left"><b><font face="Arial" size="2">&nbsp;<%=trans.tslt("Survey")%>:</font></b></td>
		<td width="344" height="25" colspan="2" bordercolor="#3399FF" style="border-style: none; border-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey">
<%	boolean anyRecord = false;
	
	/********************************
	*Edit By James 14 -Nov 2007
	********************************/
	
	//ResultSet rs_SurveyDetail = db.getRecord(query);		
	//while(rs_SurveyDetail.next())
	Vector vS=CE_Survey.getSurveys(logchk.getCompany(),CE_Survey.get_survOrg());
	for(int i=0;i<vS.size();i++)
	
	{
		anyRecord = true;
		votblSurvey vo=(votblSurvey)vS.elementAt(i);
		
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
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
		<input type="radio" value="0" checked name="CompType" style="font-weight: 700"></td>
		<td width="535" align="center" colspan="2" style="border-style: none; border-width: medium">
		<font face="Arial" size="2">
   
    	<p align="left"><b><%=trans.tslt("Competency Listing for Specific Survey")%></b></td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="535" align="center" style="border-style: none; border-width: medium" colspan="2">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">
		<input type="radio" value="1" name="CompType" style="font-weight: 700"></td>
		<td width="535" align="center" colspan="2" style="border-style: none; border-width: medium">
		<font size="2">
   
    	<p align="left">
		<b>
		<font face="Arial">
   
    	<%=trans.tslt("Competency Listing for Specific Survey with Definitions")%></b></td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="535" align="center" style="border-style: none; border-width: medium" colspan="2">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">
		<input type="radio" value="2" name="CompType" style="font-weight: 700"></td>
		<td width="535" align="center" colspan="2" style="border-style: none; border-width: medium">
		<font size="2">
   
    	<p align="left">
		<b>
		<font face="Arial">
   
    	<%=trans.tslt("Competency Listing for Specific Survey with Definitions and Key Behaviours")%></b></td>
	</tr>

	<tr>
		<td width="35" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
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
		<td width="35" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="313" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="131" align="center" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
	</table>
</form>
<%	}	%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>