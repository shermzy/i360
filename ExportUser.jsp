<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="ExportUser" class="CP_Classes.Export" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
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
	form.action="ExportUser.jsp?proceed="+field.value;
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
	
	if(request.getParameter("proceed") != null)
	{ 
		int PKOrg = new Integer(request.getParameter("proceed")).intValue();
	 	logchk.setOrg(PKOrg);
	}
	
	if(request.getParameter("btnPreview") != null)
	{		
		System.out.println("CompanyID = " + logchk.getCompany() + ", OrgID = " + logchk.getOrg() + ", PKUser = " + logchk.getPKUser());
		
		ExportUser.export(1, logchk.getCompany(), logchk.getOrg(), logchk.getPKUser()); //Export User

		//read the file name.
		String file_name = "ExportUser.xls";		
		String output = setting.getReport_Path() + "\\"+file_name;
		
		File f = new File (output);
	
	     response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"ExportUser.xls\"");
	
			
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		
		InputStream in = new FileInputStream(f);
		ServletOutputStream outs = response.getOutputStream();
		
		int bit = 256;
		int i = 0;
	
	   	try 
	   	{
	       	while ((bit) >= 0) 
	       	{
	       		bit = in.read();
	       		outs.write(bit);
	       	}
	       	//System.out.println("" +bit);
	   	} catch (IOException ioe) 
	  	{
	     	ioe.printStackTrace(System.out);
	    }
	    //		System.out.println( "\n" + i + " bytes sent.");
	    //		System.out.println( "\n" + f.length() + " bytes sent.");
	    outs.flush();
	    outs.close();
	    in.close();
	}
	%>

<form name="ExportUser" action="ExportUser.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<font face="Arial" style="font-weight: 700" color="#000080" size="2"><%=trans.tslt("Export User")%></font></td>
	</tr>
		<tr>
		<td width="489">&nbsp;</td>
	</tr>
		</table>
<table border="2" width="483" bordercolor="#3399FF" cellspacing="0" cellpadding="0">
	<tr>
		<td width="498" colspan="5" style="border-style:none; border-width:medium; " bordercolor="#3399FF">&nbsp;</td>
	</tr>
	<tr>
		<td width="137" colspan="2" style="border-style:none; border-width:medium; " bordercolor="#3399FF">
		<font face="Arial" size="2">&nbsp;<b><%=trans.tslt("Organisation")%>:</b></font></td>
		<td width="134" style="border-style:none; border-width:medium; " bordercolor="#3399FF">
		<p align="left"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value ="0"  selected><%=trans.tslt("All")%></option>
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
</select></td>
		<td width="88" style="border-style:none; border-width:medium; " bordercolor="#3399FF">&nbsp; 
   
    	</td>
		<td width="97" style="border-style:none; border-width:medium; " bordercolor="#3399FF"> <font size="2">
   
    	<p align="center">
   
    	</td>
	</tr>
	<tr>
		<td width="487" align="center" height="25" colspan="5" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF">&nbsp;</td>
	</tr>
	<tr>
		<td width="487" align="center" height="25" colspan="5" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="451" align="center" bordercolor="#3399FF" style="border-style: none; border-width: medium" colspan="4" bgcolor="#FFFFCC">&nbsp;</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="421" align="center" style="border-style:none; border-width:medium; " bordercolor="#3399FF" colspan="4" bgcolor="#FFFFCC"> <font size="2">
   
    	<p align="center">
<% if(logchk.getCompany() != 2 || logchk.getUserType() == 1) {
%>
		<input type="Submit" value="<%=trans.tslt("Export User")%>" name="btnPreview" style="float: right">
<% } else { 
%>
		<input type="Submit" value="<%=trans.tslt("Export User")%>" name="btnPreview" style="float: right" disabled>
<%
} %>		
		
		</td>
	</tr>
	<tr>
		<td width="35" align="center" style="border-left-style: none; border-left-width: medium; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="102" align="center" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;</td>
		<td width="134" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="88" style="border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
		<td width="97" style="border-right-style: none; border-right-width: medium; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC">&nbsp;
		</td>
	</tr>
	</table>
</form>
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
		<td align="center" height="5" valign="top">
		<font size="1" color="navy" face="Arial">&nbsp;Copyright ? 2004 Pacific 
		Century Consulting Pte Ltd. All Rights Reserved.</font></td>
	</tr>
</table>
</font>
</body>
</html>
