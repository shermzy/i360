<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt2" class="CP_Classes.Report_ListOfSurveys" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<%@ page import = "CP_Classes.vo.votblOrganization" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
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


if(request.getParameter("btnPreview") != null)
{

	int var2 = new Integer(request.getParameter("selOrg")).intValue();

	Rpt2.AllSurveys(logchk.getCompany(),var2, logchk.getPKUser());

	//read the file name.
	String file_name = "List Of Surveys.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"List Of Surveys.xls\"");
		
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
<form name="Rpt_Survey" action="Report_Survey.jsp" method="post">
<table border="0" width="49%" cellspacing="0" cellpadding="0">
	<tr>
		<td> <font size="2">
   
		<span style="font-weight: 700">
		<font face="Arial" color="#000080" size="2"><%=trans.tslt("List of Surveys")%></font></span></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bordercolor="#3399FF" bgcolor="#FFFFCC">
		<tr>
		<td width="445" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
		<tr>
		<td width="131" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<b><font face="Arial" size="2">&nbsp;<%=trans.tslt("Organisation")%>:</font></b></td>
		<td width="141" style="border-style: none; border-width: medium">
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
		<select size="1" name="selOrg">
		<option value ="0" selected><%=trans.tslt("All")%></option>
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
		<select size="1" name="selOrg">
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select><font size="2"> </font>
</td>
		<td width="157" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font size="2">
<% if(!logchk.getCompanyName().equalsIgnoreCase("demo") || logchk.getUserType() == 1) {
%>   
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: right">
<% } else { 
%>   
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: right" disabled>
<%
} %>		
		
		</td>
		
	</tr>
	<tr>
		<td width="131" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;
		</td>
		<td width="141" height="25" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;
		</td>
		<td width="173" height="25" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;
		</td>
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
<%@ include file="Footer.jsp"%>	</tr>
</table>
</body>
</html>
