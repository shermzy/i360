<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="Rpt10" class="CP_Classes.Report_DeleteSurvey" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8" %> 
<% // by Lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function check(field)
{
	var flag = false;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].selected)
			flag = true;
	}
if(field.selected)
		flag = true;
	return flag;
}

function validate()
{
    var iValid =0;
    x = document.DeletedSurvey
    txtSurvey = x.selSurvey
	var checkin = check(txtSurvey);

	if(checkin == false)
	{
		alert("<%=trans.tslt("Please select a survey")%>");
		return false;
	}
	
	return true;
		
}
/*	choosing organization*/

function proceed(form,field)
{
	form.action="DeletedSurvey.jsp?proceed="+field.value;
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

if(request.getParameter("btnGet") != null)
{
	String filename="";
	int DeletedSurvey = new Integer(request.getParameter("selSurvey")).intValue();
	
	String sql1 = "SELECT * FROM tblDeletedSurvey WHERE DeletedSurveyID= "+DeletedSurvey;
	ResultSet rs3 = db.getRecord(sql1);
	if(rs3.next())
		filename = rs3.getString("Filename");
	
	
	
	//read the file name.
	String output = setting.getDeleted_Path()  + "\\"+filename;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename="+filename);
		
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

%>
<form name ="DeletedSurvey" method="post" action="DeletedSurvey.jsp" onsubmit="validate()">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4"><b><font face="Arial" size="2" color="#000080">
		<%= trans.tslt("Deleted Survey") %></font></b></td>
	</tr>
		<tr>
		<td width="15%" colspan="2">&nbsp;</td>
		<td width="21%">&nbsp;</td>
		<td width="64%">&nbsp;</td>
	</tr>

		<tr>
		<td width="15%" colspan="2"><font face="Arial" size="2"><%= trans.tslt("Organisations") %>:</font></td>
		<td width="21%"><font face="Arial"><span style="font-size: 11pt"><select size="1" name="selOrg">
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
</select></span></font></td>
		<td width="64%"><font face="Arial"><span style="font-size: 11pt"><input type="button" value="<%= trans.tslt("Show") %>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)"></span></font></td>
	</tr>

		<tr>
		<td width="15%" colspan="2">&nbsp;</td>
		<td width="21%">&nbsp;</td>
		<td width="64%">&nbsp;</td>
	</tr>

		<tr>
		<td width="15%" colspan="2">&nbsp;</td>
		<td width="83%" colspan="2">&nbsp;</td>
	</tr>

		<tr>
		<td width="15%" colspan="2"><font face="Arial" size="2">
		<%= trans.tslt("Survey Name") %>:</font></td>
		<td width="83%" colspan="2">
		<select size="1" name="selSurvey">
<%
	String sql = "SELECT * FROM tblDeletedSurvey WHERE FKOrganization="+logchk.getOrg()+" ORDER BY SurveyName";
	ResultSet rs2 = db.getRecord(sql);
	while(rs2.next())
	{

		int DeletedSurveyID = rs2.getInt("DeletedSurveyID");
		String SurveyName = rs2.getString("SurveyName");
%>
	<option value=<%=DeletedSurveyID%>><%=SurveyName%></option>
<%	}	%>
		</select></td>
	</tr>

		<tr>
		<td width="15%" colspan="2">&nbsp;</td>
		<td width="83%" colspan="2">&nbsp;</td>
	</tr>

		<tr>
		<td width="8%">&nbsp;</td>
		<td width="90%" colspan="3">&nbsp;</td>
	</tr>

		<tr>
		<td width="8%">&nbsp;</td>
		<td width="90%" colspan="3">
		<input type="submit" value="<%= trans.tslt("Get Selected Survey Report") %>" name="btnGet"></td>
	</tr>

		</table>
<p><font face="Arial" style="font-size: 11pt">:</font></p>
</form>
<%	}%>
</body>
</html>
