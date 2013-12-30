<%@ page import="java.sql.*,
                 java.io.*,
                 java.lang.String,
				 java.util.*,
				 CP_Classes.vo.*"%>   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="Rpt8" class="CP_Classes.Report_RaterInputForGroup" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="userJ" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="DIV" class="CP_Classes.Division" scope="session"/>
<jsp:useBean id="DEP" class="CP_Classes.Department" scope="session"/>
<jsp:useBean id="GRP" class="CP_Classes.Group" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
function proceed(form,field)
{
	form.action="Report_RaterInput_Group.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function surv(form,field)
{
	form.action="Report_RaterInput_Group.jsp?surv="+field.value;
	form.method="post";
	form.submit();
}

function group(form, field1, field2, field3)
{
	form.action="Report_RaterInput_Group.jsp?group="+field1.value + "&div=" + field2.value + "&dept=" + field3.value;
	form.method="post";
	form.submit();
}

function populateDept(form, field)
{
	form.action="Report_RaterInput_Group.jsp?div=" + field.value;
	form.method="post";
	form.submit();
}

function populateGrp(form, field1, field2)
{
	form.action="Report_RaterInput_Group.jsp?div="+ field1.value + "&dept=" + field2.value;
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
	CE_Survey.set_DivID(0);
	CE_Survey.set_DeptID(0);
	CE_Survey.set_GroupID(0);
	CE_Survey.setSurvey_ID(0);
	logchk.setOrg(var2);
}
//Changed by Ha 27/05/08 to set respective value to default then chaning value in upper layer
if(request.getParameter("surv") != null)
{ 
	int var1 = new Integer(request.getParameter("selSurvey")).intValue();
	CE_Survey.setSurvey_ID(var1);
	CE_Survey.set_DivID(0);
	CE_Survey.set_DeptID(0);
	CE_Survey.set_GroupID(0);
}

if(request.getParameter("div") != null)
{
	int divID = new Integer(request.getParameter("div")).intValue();
	CE_Survey.set_DivID(divID);
	CE_Survey.set_DeptID(0);
	CE_Survey.set_GroupID(0);
	
}

if(request.getParameter("dept") != null)
{
	
	int deptID = new Integer(request.getParameter("dept")).intValue();
	CE_Survey.set_DeptID(deptID);
	CE_Survey.set_GroupID(0);
}
if(request.getParameter("group") != null)
{ 
	int var1 = new Integer(request.getParameter("selGroup")).intValue();
	CE_Survey.set_GroupID(var1);
	
}
//Ended change made by Ha 27/05/08
if(request.getParameter("btnPreview") != null)
{
	int RaterID = new Integer(request.getParameter("selRater")).intValue();

	Rpt8.AllTargets(CE_Survey.getSurvey_ID(),RaterID, logchk.getPKUser());
		
	//read the file name.
	String file_name = "Raters Input For Group.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"Raters Input For Group.xls\"");
		
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
<form name="Rpt_RaterInput_Group" action="Report_RaterInput_Group.jsp" method="post">
<table border="0" width="483" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><font color="#000080" face="Arial" size="2">
		<%=trans.tslt("Raters' Input for Group")%></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="2" width="483" cellspacing="0" cellpadding="0" bgcolor="#FFFFCC" bordercolor="#3399FF">
		<tr>
		<td width="184" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="223" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="134" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp; </td>
	</tr>
		<tr>
		<td width="184" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Organisation")%>:</font></td>
		<td width="223" style="border-style: none; border-width: medium">
		<p align="left">		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		 <option value="0" selected><%=trans.tslt("All")%>
<%
	Vector vOrg = logchk.getOrgList(logchk.getCompany());
	
	for(int i=0; i<vOrg.size(); i++)
	{
		votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
		int PKOrg = vo.getPKOrganization();
		String OrgName = vo.getOrganizationName();
	
		if(logchk.getOrg() == PKOrg)
		{
%>
	<option value=<%=PKOrg%> selected><%=OrgName%></option>

<%		}
		else	
		{
%>
	<option value=<%=PKOrg%>><%=OrgName%></option>
<%		}	
	}
%>
</select>
</td>
		<td width="134" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> </td>
	</tr>
	<tr>
		<td width="184" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="783" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="184" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Survey")%>:</font></td>
		<td width="783" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onchange="surv(this.form,this.form.selSurvey)">
		
<%    	if(CE_Survey.getSurvey_ID() == 0)
		{	%>
    	 	<option value="0" selected><%=trans.tslt("Please select one")%>
<%		}
		else
		{
%>			 <option value="0"><%=trans.tslt("Please select one")%>
<%		}
	String query ="SELECT * FROM tblSurvey a, tblOrganization b WHERE a.FKOrganization = b.PKOrganization "; 
	
	if(CE_Survey.get_survOrg() != 0)
		query = query+" AND a.FKOrganization = "+CE_Survey.get_survOrg();
	else
		query = query+"	AND a.FKCompanyID = "+logchk.getCompany();
		
		query = query+"ORDER BY SurveyName";
		/******************************
		*Edit By James 14- Nov 2007
		*****************************/
		
	//ResultSet rs_SurveyDetail = db.getRecord(query);		
	//while(rs_SurveyDetail.next())
		
	//Vector vS=CE_Survey.getSurveys(logchk.getCompany(),CE_Survey.get_survOrg());
	// Changed by Ha 23/05/08 change parametes passing to getSurveys(int, int)
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
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Division")%>:</font></td>
		<td width="783" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selDiv" onchange = "populateDept(this.form, this.form.selDiv)">
<%    	if(CE_Survey.get_DivID() == 0)
		{	%>
    	 	<option value="0" selected><%=trans.tslt("Please select one")%>
<%		}
		else
		{
%>			<option value="0"><%=trans.tslt("Please select one")%>
<%		}

	Vector vDiv = DIV.getTargetDivisions(CE_Survey.getSurvey_ID());
			
	for(int i=0; i<vDiv.size(); i++)
	{
		voDivision vo = (voDivision)vDiv.elementAt(i);
		int DivID = vo.getPKDivision();
		String DivisionName = vo.getDivisionName();
				
	if(CE_Survey.get_DivID() == DivID)
	{
%>
		<option value=<%=DivID%> selected><%=DivisionName%></option>
<%	}
	else	
	{%>

	<option value=<%=DivID%> > <%=DivisionName%></option>
<%
	}
}%>
</select></td>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Department")%>:</font></td>
		<td width="783" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selDept" onchange = "populateGrp(this.form, this.form.selDiv, this.form.selDept)">
<%    	//if(CE_Survey.get_DeptID() == 0)
		//{	%>
    	 <option value="0" selected><%=trans.tslt("Please select one")%>
<%		//}
		
		int iDivID = 0;
		if(request.getParameter("div") != null)
		{
			if (request.getParameter("div").equals("")){
				iDivID = 0;
				}
			else
				iDivID = Integer.parseInt(request.getParameter("div"));
		}

	Vector vDept = DEP.getTargetDepartments(CE_Survey.getSurvey_ID(), CE_Survey.get_DivID());	
	for(int i=0; i<vDept.size(); i++)
	{
		voDepartment vo = (voDepartment)vDept.elementAt(i);
		int DeptID = vo.getPKDepartment();
		String DepartmentName = vo.getDepartmentName();
		
	if(CE_Survey.get_DeptID() == DeptID)
	{
		
%>
		<option value=<%=DeptID%> selected><%=DepartmentName%></option>
<%	}
	else	
	{%>

	<option value=<%=DeptID%> > <%=DepartmentName%></option>
<%
	}
}%>
</select></td>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Group")%>:</font></td>
		<td width="783" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selGroup" onchange = "group(this.form,this.form.selGroup, this.form.selDiv, this.form.selDept)">
		

<%    	if(CE_Survey.get_GroupID() == 0)
		{	%>
    	 	<option value="0" selected><%=trans.tslt("Please select one")%>
<%		}
		else
		{
%>			<option value="0"><%=trans.tslt("Please select one")%>
<%		}
	
		int iDeptID = 0;
		if(request.getParameter("dept") != null)
		{
			if (request.getParameter("dept").equals("")){
				iDeptID = 0;
				}
			else
				iDeptID = Integer.parseInt(request.getParameter("dept"));
		}
	//Changed by Ha 10/06 change parameter passing to following method
	Vector vGrp = GRP.getTargetGroups(CE_Survey.getSurvey_ID(), CE_Survey.get_DeptID());
	for(int i=0; i<vGrp.size(); i++)
	{
		voGroup vo = (voGroup)vGrp.elementAt(i);
		int GroupID = vo.getPKGroup();
		String GroupName = vo.getGroupName();
				
	if(CE_Survey.get_GroupID() == GroupID)
	{
%>
		<option value=<%=GroupID%> selected><%=GroupName%></option>
<%	}
	else	
	{%>

	<option value=<%=GroupID%> > <%=GroupName%></option>
<%
	}
}%>
</select></td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
		<td width="783" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"><font face="Arial" style="font-weight:700" size="2">&nbsp;<%=trans.tslt("Rater")%>:</font></td>
		<td width="783" align="center" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
		<p align="left"> <font size="2">
   
    	<select size="1" name="selRater">
<%
	boolean anyRecord= false;
	String [] RDetail = new String [14];

	int seq = userJ.NameSequence(logchk.getOrg());
	
	Vector vUser = CE_Survey.getAllUsers(seq, CE_Survey.getSurvey_ID(), CE_Survey.get_GroupID());
	for(int i=0; i<vUser.size(); i++)
	{
		anyRecord= true;
		voUser vo = (voUser)vUser.elementAt(i);
		int RaterID = vo.getPKUser();
		//int nameSequence = rs_Target.getInt("NameSequence");
		
		//RDetail = user.getUserDetail(RaterID,nameSequence);
%>
	<option value=<%=RaterID%> selected> <%=vo.getFullName()%></option>
<%
	}%>
</select></td>
	</tr>
	<tr>
		<td width="967" align="center" colspan="3" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
		<p align="right">
		</td>
		<td width="223" align="center" style="border-style: none; border-width: medium"> <font size="2">
   
		<p align="left"> </td>
		<td width="134" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   <%if(anyRecord)
   	{%>
<% if(!logchk.getCompanyName().equalsIgnoreCase("demo") || logchk.getUserType() == 1) {
%>	
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: left">
<% } else {
%>	
		<input type="Submit" value="<%=trans.tslt("Preview")%>" name="btnPreview" style="float: left" disabled>
<%
} %>		
	<%	}	%>
	</td>
	</tr>
	<tr>
		<td width="184" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="223" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
		<td width="134" align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp; </td>
	</tr>
</table>
</form>
<%	}	%>

<%@ include file="Footer.jsp"%>
</body>
</html>