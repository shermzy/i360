<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String"%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="Rpt11" class="CP_Classes.Report_Deleted_ListOfRatersStatus_Survey" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<%@ page pageEncoding="UTF-8" %> 
<meta http-equiv="Content-Type" content="text/html">
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

var target
function check2(field)
{
	var check= false;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
		{
			target = field[i].value;
			check = true;
		}
		
	}
	if(field.checked)
	{
		target = field.value;
		check = true;
	}

	return target;
		
}
var value;
function validate()
{
    var iValid =0;
    var flag = true;
    x = document.DeletedRater
    selSurvey = x.selSurvey
    selSurvey2 = x.selSurvey2
    chkSurveyType = x.chkSurveyType;
   	var checkin = check(selSurvey);
   	var checkin2 = check(selSurvey2);
	
	value = check2(chkSurveyType);
	
	if(value == 1)
	{
		if(checkin == false)
		{
			alert("<%=trans.tslt("Please select a survey")%>");
			flag = false;
		}
	}
	else if(value == 0)
	{
		if(checkin2 == false)
		{
			alert("<%=trans.tslt("Please select a survey")%>");
			flag = false;
		}
	}
	
	return flag;
}

function report(form)
{
	if(validate())
	{
		form.action="DeletedRater.jsp?type="+value;
		form.method="post";
		form.submit();
	}
}
/*	choosing organization*/

function proceed(form,field)
{
	form.action="DeletedRater.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function del(form)
{
	if(validate())
	{
		form.action="DeletedRater.jsp?del="+value;
		form.method="post";
		form.submit();
	}
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

if(request.getParameter("type") != null)
{
	String filename="";
	String SurveyName="";
	int DeletedSurvey=0;
	String sql1="";
	boolean IsFileFound = false;
	int SurveyType = new Integer(request.getParameter("type")).intValue();
	
	/* SurveyType  = 0 -->Not deleted
	 * 			   = 1 -->Deleted
	 **/
	if(SurveyType == 0)
	{
		DeletedSurvey = new Integer(request.getParameter("selSurvey2")).intValue();
		
		boolean IsNull = Rpt11.AllRaters(DeletedSurvey,0);

		if(IsNull == false)
		{
			String extract_company = "SELECT * FROM tblConsultingCompany a, tblOrganization b, tblSurvey c WHERE a.CompanyID = b.FKCompanyID AND c.FKOrganization = b.PKOrganization AND SurveyID = "+ DeletedSurvey;
							
			db.openDB();
			Statement stmt = db.con.createStatement();
			ResultSet rs_Company = stmt.executeQuery(extract_company);
			if(rs_Company.next())	
			{
				SurveyName = rs_Company.getString("SurveyName");
			}
			IsFileFound = true;
		}
		else
		{	%>
		<script>
			alert("<%=trans.tslt("No report to found for this survey")%>");
		</script>
		<%
		}
	}
	else if(SurveyType == 1)
	{
		DeletedSurvey = new Integer(request.getParameter("selSurvey")).intValue();
		
		Rpt11.AllRaters(DeletedSurvey,1);
		
		String sqlcommand1 = "SELECT * FROM tblConsultingCompany a,tblOrganization b,tblDeletedSurvey c WHERE a.CompanyID = b.FKCompanyID AND c.FKOrganization = b.PKOrganization AND DeletedSurveyID = "+ DeletedSurvey;
				
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs_SQL = stmt.executeQuery(sqlcommand1);
		if(rs_SQL.next())	
		{
			SurveyName = rs_SQL.getString("SurveyName");
		}
		IsFileFound = true;
	}

	if(IsFileFound)
	{	
		//read the file name.
		filename = "ListOfDeletedRatersForSurvey_"+SurveyName+".xls";
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
	
	            		} catch (IOException ioe) {
	            			ioe.printStackTrace(System.out);
	            		}
	            		outs.flush();
	            		outs.close();
	            		in.close();	
	  }
      

}

if(request.getParameter("del") != null)
{
	int DeletedSurvey=0;
	int SurveyType = new Integer(request.getParameter("del")).intValue();
	
	/* SurveyType  = 0 -->Not deleted
	 * 			   = 1 -->Deleted
	 **/
	if(SurveyType == 0)
	{
		DeletedSurvey = new Integer(request.getParameter("selSurvey2")).intValue();
		Rpt11.deleteRecord(DeletedSurvey);
	}
	else if(SurveyType == 1)
	{
		DeletedSurvey = new Integer(request.getParameter("selSurvey")).intValue();
		Rpt11.deleteRecord(DeletedSurvey);
	}
}

%>
<form name ="DeletedRater" method="post" action="DeletedRater.jsp">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4"><b><font face="Arial" size="2" color="#000080">
		<%= trans.tslt("Deleted Rater") %></font></b></td>
	</tr>
		<tr>
		<td width="20%" colspan="2">&nbsp;</td>
		<td width="29%">&nbsp;</td>
		<td width="51%">&nbsp;</td>
	</tr>

		<tr>
		<td width="20%" colspan="2" height="27"><b><font face="Arial" size="2"><%= trans.tslt("Organisations") %>:</font></b></td>
		<td width="29%" height="27"><font face="Arial">
		<span style="font-size: 11pt"><select size="1" name="selOrg">
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
		<td width="51%" height="27"><font face="Arial">
		<span style="font-size: 11pt"><input type="button" value="<%= trans.tslt("Show") %>" name="btnShow" onclick="proceed(this.form,this.form.selOrg)"></span></font></td>
	</tr>

		<tr>
		<td width="20%" colspan="2">&nbsp;</td>
		<td width="29%">&nbsp;</td>
		<td width="51%">&nbsp;</td>
	</tr>

		<tr>
		<td width="2%">
		<p align="center"> <font size="2">
   
    	<input type="radio" value="1" checked name="chkSurveyType"></td>
		<td width="17%"><b><font face="Arial" color="#000080" size="2"><%= trans.tslt("For Deleted Survey") %>:</font></b></td>
		<td width="80%" colspan="2">&nbsp;</td>
	</tr>

		<tr>
		<td width="20%" colspan="2" height="24">
		<p align="center"><font face="Arial" size="2">
		<%= trans.tslt("Survey Name") %>:</font></td>
		<td width="80%" colspan="2" height="24">
		<font face="Arial"><span style="font-size: 11pt">
		<select size="1" name="selSurvey">
<%
	String sql = "SELECT * FROM tblDeletedSurvey WHERE FKOrganization="+logchk.getOrg()+" ORDER BY SurveyName";
	ResultSet rs2 = db.getRecord(sql);
	while(rs2.next())
	{

		int DeletedSurveyID = rs2.getInt("DeletedSurveyID");
		String DeletedSurveyName = rs2.getString("SurveyName");
%>
	<option value=<%=DeletedSurveyID%>><%=DeletedSurveyName%></option>
<%	}	%>
		</select></span></font></td>
	</tr>

		<tr>
		<td width="20%" colspan="2">&nbsp;</td>
		<td width="80%" colspan="2">&nbsp;</td>
	</tr>

		<tr>
		<td width="2%">
		<p align="center"><input type="radio" value="0" name="chkSurveyType"></td>
		<td width="17%"><b><font face="Arial" color="#000080" size="2"><%= trans.tslt("For On-going Survey") %>:</font></b></td>
		<td width="80%" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%" colspan="2" height="24">
		<p align="center"><font face="Arial" size="2">
		<%= trans.tslt("Survey Name") %>:</font></td>
		<td width="80%" colspan="2" height="24">
		<font face="Arial"><span style="font-size: 11pt">
		<select size="1" name="selSurvey2">
<%
	String sql1 = "SELECT DISTINCT a.SurveyID, a.SurveyName FROM tblSurvey a, tblDeletedAssignment b WHERE a.SurveyID = b.SurveyID AND FKOrganization="+logchk.getOrg()+" ORDER BY SurveyName";
	ResultSet rs4 = db.getRecord(sql1);
	while(rs4.next())
	{
		int SurveyID = rs4.getInt("SurveyID");
		String SurveyName2 = rs4.getString("SurveyName");
%>
	<option value=<%=SurveyID%>><%=SurveyName2%></option>
<%	}	%>
		</select></span></font></td>
	</tr>

		<tr>
		<td width="20%" colspan="2">&nbsp;</td>
		<td width="80%" colspan="2">&nbsp;</td>
	</tr>

		<tr>
		<td width="50%" colspan="2">&nbsp;</td>
		<td width="49%" colspan="2">
		&nbsp;</td>
	</tr>

		<tr>
		<td width="50%" colspan="2"><font face="Arial" size="2"><span style="font-size: 11pt"><input type="button" value="<%= trans.tslt("Get Report") %>" name="btnGet" onclick = "report(this.form)" style="float: left"></span></font></td>
		<td width="49%" colspan="2">
		<input type="button" value="<%= trans.tslt("Clear Deleted Records For Selected Survey") %>" name="btnDelete" onclick="del(this.form)"></td>
	</tr>

		</table>
<p><font face="Arial" style="font-size: 11pt"></p>
</form>
<%	}%>
</body>
</html>