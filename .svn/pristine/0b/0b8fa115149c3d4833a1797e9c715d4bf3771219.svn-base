<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 java.text.*,
                 java.lang.String"%>  
                 
<% // by Lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<%@ page pageEncoding="UTF-8" %>                 
                 
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
 <%@ page import="CP_Classes.vo.votblSurvey"%>
  <%@ page import="CP_Classes.vo.votblOrganization"%>
<html>
<SCRIPT LANGUAGE=JAVASCRIPT>
function add(form, field, field2)
{
	var valid = true;
	if(field.value == "") {
		valid = false;
	} 
	
	if(valid)
	{
		if(field2.value != "0") {
			form.action="CopySurvey.jsp?add=1"
			form.method="post";
			form.submit();
		} else {
			alert("<%=trans.tslt("Please select Organisation")%>");
		}
	}	
	else
	{
		alert("<%=trans.tslt("Please enter new Survey name")%>");
	}
}

</script>
<head>
<title>
Copy Survey
</title>
</head>
<body bgcolor="#FFFFCC">
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
  
	if(request.getParameter("add") != null)
	{
		String NewSurveyName = request.getParameter("NewSurveyName");
		int OrgID = new Integer(request.getParameter("selOrg")).intValue();
		int status = new Integer(request.getParameter("selStatus")).intValue();
		//Edit by Roger 21 July 2008
		//Survey that belongs to different organization can have the same name. include org id check 
		//int iSurveyID = CE_Survey.getSurveyID(NewSurveyName);
		
		boolean valid = CE_Survey.checkCopySurvey(NewSurveyName, OrgID);
		
		if(valid)
		{
			//try
			//{
				CE_Survey.CopySurvey(CE_Survey.getSurvey_ID(),NewSurveyName, OrgID, status, logchk.getPKUser());
				
			//*** Edited by Tracy 14 Aug 08, get new survey ID based on new survey name and Organization ID
				int newlyCopiedSurveyId = CE_Survey.getSurveyOrgID(NewSurveyName, OrgID);
			//********************************************
				CE_Survey.setSurvey_ID(newlyCopiedSurveyId); 
			%>
				<script>
				window.close();
				opener.opener.location.href = "SurveyDetail.jsp";
				opener.close();
				</script>

	<%		/*}
			catch(Exception E)
			{	%>
				<script>
					window.close();
				</script>
		<%	}	*/
		}
		else
		{
			%>		
			<script>
			alert("<%=trans.tslt("Survey name exists")%>");
			</script>
	<%	}	

}
%>	
<form name="CopySurvey" method="post">
	<table border="0" width="98%" cellspacing="0" cellpadding="8" bordercolorlight="#C0C0C0" bordercolordark="#808080" bordercolor="#808080" style="border-width: 0px">
	<tr>
	
	<td width="100" style="border-style: none; border-width: medium; " height="22" align="left">
	<b>
	<font face="Arial" size="3" color="black">
	<%= trans.tslt("Copy Survey") %>
	</font>
	</b>
	</td>
	
	<td>
	</td>
	
	</tr>
	
		<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
		<td style="border-style: none; border-width: medium" align="left">&nbsp;</td>
	</tr>
	
	<tr>
	<td width="100" style="border-style: none; border-width: medium; " height="22" align="left">
	<b>
	<font face="Arial" size="2" color="#000080">
	<%= trans.tslt("Selected Survey") %>:</font></b></td>
		<td style="border-style: none; border-width: medium; " height="22" align="left">
<font face="Arial" size="2" color="#000080">
<b>
<%	
	String SurveyName=" ";
	int db_SurveyStatus=0;
	int FKOrg = 0;
	/************************
	*Edited By James 02 Nov 02
	************************/
	
	votblSurvey voSurvey = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
	
	if(voSurvey != null) {
	
	SurveyName = voSurvey.getSurveyName();
	db_SurveyStatus = voSurvey.getSurveyStatus();
	FKOrg = voSurvey.getFKOrganization();
	}
	
%>
	<%=SurveyName%>

</b>

</font>

</td>	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
		<td style="border-style: none; border-width: medium" align="left">&nbsp;</td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">
		<b>
		<font face="Arial" size="2" color="#000080"><%= trans.tslt("New Survey Name") %>:</font></b></td>
		<td style="border-style: none; border-width: medium" align="left">
		<p align="center"><font face="Arial"><span style="font-size: 11pt">
		<input name="NewSurveyName" size="20" style="float: left"></span></font></td>
	</tr>
	<tr>
		<td style="border-style: none; border-width: medium">&nbsp;</td>
		<td style="border-style: none; border-width: medium" align="left">&nbsp;</td>
	</tr>
    
<%
	//Remove by Roger 3 July 2008
	//Organization is always default to previously specified, no need to choose again 
%>	 

<!--  
	<tr>
		<td width="100" style="border-style: none; border-width: medium">
		<b>
		<font face="Arial" size="2" color="#000080"><%= trans.tslt("Organisation") %>:</font></b></td>
		<td width="665" style="border-style: none; border-width: medium" align="left">
		<p><font face="Arial"><span style="font-size: 11pt">
<select size="1" name="selOrg">
<option value="0">&nbsp</option>
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
</select></span></font></td>
	</tr>
	<tr>
		<td width="100" height="21" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="665" height="21" style="border-style: none; border-width: medium" align="left">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="100" style="border-style: none; border-width: medium">
		<b>
		<font face="Arial" size="2" color="#000080"><%= trans.tslt("Status") %>:</font></b></td>
		<td width="665" style="border-style: none; border-width: medium" align="left">
		<p>
		<select size="1" name="selStatus">
			
				<option value="1"><%= trans.tslt("Open") %></option>
				<option value="2"><%= trans.tslt("Closed") %></option>
				<option value="3" selected><%= trans.tslt("Not Commissioned") %></option>


															
				</select><font size="2"> </font>
		</td>
	</tr>

-->

</table>
<%	
	//Added by Roger 3 July 2008 (Revision 1.2)
	//Use Hidden field to default the value
%>
<input type="hidden" name="selStatus" value="3"/>
<input type="hidden" name="selOrg" value="<%=logchk.getOrg()%>"/>
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="400">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
	<td width="400"></td>

		<td width="200">
		<input type="button" value="<%= trans.tslt("Add") %>" name="btnAdd" style="float: left" onClick="add(this.form,this.form.NewSurveyName,this.form.selOrg)"/>
	
		<input type="button" value="<%= trans.tslt("Cancel") %>" name="btnCancel" style="float: left" onClick="window.close()"/></td>
			</tr>
</table>
</form>
<%	}	%>
</body>
</html>