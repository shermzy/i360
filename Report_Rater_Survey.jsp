<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt4" class="CP_Classes.Report_ListOfRaters_Survey" scope="session"/>
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
	form.action="Report_Rater_Survey.jsp?proceed="+field.value;
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

	Rpt4.AllRaters(SurveyID, logchk.getPKUser());
		
	//read the file name.
	String file_name = "List Of Raters for Specific Survey.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"List Of Raters for Specific Survey.xls\"");
		
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
<form name="Rpt_Rater_Survey" action="Report_Rater_Survey.jsp" method="post">
<table border="0" width="518" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4">
		<b>
		<font face="Arial" size="2" color="#000080"><%=trans.tslt("List of Raters for Specific Survey")%></font></b></td>
	</tr>
		<tr>
		<td width="518" colspan="4">&nbsp;</td>
	</tr>
		<tr>
		<td width="117"><font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></td>
		<td width="214" colspan="2">
		<p align="left">		<font face="Arial">		<select size="1" name="selOrg">
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
</select></font><font size="2" face="Arial"> </font>
</td>
		<td width="188"> <font size="2">
   
    	<font face="Arial">
   
    	<input type="button" value="<%=trans.tslt("Show")%>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)" style="float: left"></font></td>
	</tr>
	<tr>
		<td width="117" align="center" height="25">&nbsp;</td>
		<td width="402" height="25" colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="117" align="center" height="25">
		<p align="left"><font face="Arial" size="2"><%=trans.tslt("Survey Name")%>:</font></td>
		<td width="402" height="25" colspan="3"> <font size="2">
   
    	<font face="Arial">
   
    	<select size="1" name="selSurvey">
<%
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
	if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
		
	ResultSet rs_SurveyDetail = db.getRecord(query);		
	while(rs_SurveyDetail.next())
	{
		int Surv_ID = rs_SurveyDetail.getInt("SurveyID");
		String Surv_Name = rs_SurveyDetail.getString("SurveyName");
%>
	<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
<%
	}%>
</select></font></td>
	</tr>
	<tr>
		<td width="518" align="center" colspan="4">
		&nbsp;</td>
	</tr>
	<tr>
		<td width="116" align="center"> <font size="2">
   
		<p align="right">
		&nbsp;</td>
		<td width="144" align="center"> <font size="2">
   
		<font face="Arial">
   
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: right"></font></td>
		<td width="70" align="center"> &nbsp;</td>
		<td width="188">
		<p align="left"> &nbsp;</td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>
