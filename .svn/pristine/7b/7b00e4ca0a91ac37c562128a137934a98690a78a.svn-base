<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
				 CP_Classes.vo.votblRelationSpecific,
				 CP_Classes.vo.votblSurveyRelationSpecific,
				 CP_Classes.vo.votblSurvey,
                 java.lang.String,
                 CP_Classes.SurveyRelationSpecific"%>   
                 
<jsp:useBean id="RaterRelation" class="CP_Classes.RaterRelation" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SRS" class="CP_Classes.SurveyRelationSpecific" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session" />
<%@ page import="CP_Classes.vo.votblRelationHigh"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 125;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function root(form) 
{
	form.action="RaterRelation.jsp?root=1";
	form.method="post";
	form.submit();
}

function branch(form) 
{
	form.action="RaterRelation.jsp?branch=1";
	form.method="post";
	form.submit();
}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="RaterRelation.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

function populateRelation(form, ID){
	form.action="RaterRelation.jsp?surveyID=" + ID;
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
 	CE_Survey.set_survOrg(PKOrg);
}  

if(request.getParameter("surveyID") != null){
	
int id = Integer.parseInt(request.getParameter("surveyID"));
	
	QR.setSurveyID(id);
	System.out.println(QR.getSurveyID());
}
  
if(request.getParameter("root") != null)
{	
	int root = new Integer(request.getParameter("btnParent")).intValue();
	RaterRelation.setRelHigh(root);
%>
<script>
	var myWindow=window.open('RaterRelation_RootMenu.jsp','windowRef','scrollbars=no, width=500, height=150');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'RaterRelation_RootMenu.jsp';
    
</script>
<%
}

if(request.getParameter("branch") != null)
{	
	int branch = new Integer(request.getParameter("btnChild")).intValue();
	RaterRelation.setRelSpec(branch);
%>
<script>
	var myWindow=window.open('RaterRelation_BranchMenu.jsp','windowRef','scrollbars=no, width=500, height=300');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'RaterRelation_BranchMenu.jsp';
    
</script>
<%
}

%>

<form name ="RaterRelation" method="post" action="RaterRelation.jsp">
<font size="2">
   
<table border="0" width="99%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="3">
		<b>
		<font face="Arial" color="#000080" size="2"><%=trans.tslt("Rater Relation")%></font></b></td>
		</tr>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></b></td>
		<td width="16%"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){
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
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select></td>
		<td width="64%">&nbsp;
</td>
	</tr>
	<tr>
		<td width="20%">&nbsp;</td>
		<td width="16%">&nbsp;</td>
		<td width="64%">&nbsp;</td>
	</tr>
	<%
	/*
	*Change(s):Added survey dropbox
	*Reason(s):To enable displaying of rater relation for different surveys
	*Updated By: Liu Taichen
	*Updated On: 4 June 2012
	*/
	
	%>
	
	<tr>
		<td width="20%"><b><font face="Arial" size="2"><%=trans.tslt("Survey")%>:</font></b></td>
		  
		<td width="851" height="25" colspan="2" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"> <font size="2">
   
    	<select size="1" name="selSurvey" onChange="populateRelation(this.form,this.form.selSurvey.options[selSurvey.selectedIndex].value)">
    	<option selected value=''>Please select a survey</option>
<%
	int iSurveyID= QR.getSurveyID();
	
	boolean anyRecord=false;
	
	
	Vector v = CE_Survey.getSurveys(logchk.getCompany(), CE_Survey.get_survOrg());
	
	for(int i=0; i<v.size(); i++)
	{
		votblSurvey vo = (votblSurvey)v.get(i);

		anyRecord=true;
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
		
			if(iSurveyID!= 0 && iSurveyID== Surv_ID)
			{%>
				<option value=<%=Surv_ID%> selected><%=Surv_Name%></option>	
		<%	}else
			{%>
				<option value=<%=Surv_ID%>><%=Surv_Name%></option>
			<%	}
	}%>
</select></td>
	</tr>
	<tr>
					<td align="right" bordercolor="#FFFFCC">&nbsp;</td>
					<td bordercolor="#FFFFCC">&nbsp;</td>
					<td bordercolor="#FFFFCC">&nbsp;</td>
				</tr>
</table>

</font>


	<table border="2" width="461" bgcolor="#FFFFCC" bordercolor="#3399FF" cellspacing="0" cellpadding="0">
		<tr>
			<td>
<table border="0" width="452" cellspacing="0" cellpadding="0" height="39" style="border-left-width:0px; border-right-width:0px; border-top-width:2px; border-bottom-width:2px" bordercolor="#000080">
		<tr>
		
		<td width="17%" align="right" valign="top" style="border-style:none; border-width:medium; " bordercolorlight="#000000" bordercolordark="#000000" colspan="2">&nbsp;
		</td>
		
		<td align="right" style="border-style:none; border-width:medium; " bordercolorlight="#000000" bordercolordark="#000000" width="57%">
		</tr>
		
<%

        
		Vector rs_Rel = RaterRelation.getAllRelationHigh();		
		for(int j=0; j<rs_Rel.size(); j++) {
			votblRelationHigh voRel = (votblRelationHigh)rs_Rel.elementAt(j);
			int Rel_ID = voRel.getRelationID();
			String Rel_Desc = voRel.getRelationHigh();
		%>
		
		<tr>
		
		<td width="7%" align="right" valign="top" style="border-style: none; border-width: medium; " bordercolorlight="#000000" bordercolordark="#000000" bordercolor="#000080">
		<p align="center">
		<font size="2">
		<%
			if(RaterRelation.getRelHigh() == Rel_ID)
			{	%>
			</font>
			<input type="radio" name="btnParent" value=<%=Rel_ID%> onclick="root(this.form)" style="float: right" checked><font size="2">
			<%
			}
			else
			{	%>
			</font>
			<input type="radio" name="btnParent" value=<%=Rel_ID%> onclick="root(this.form)" style="float: right"><font size="2">
			<%	}	%>
		</font>
		
		</td>
		
		<td width="10%" align="right" valign="top" style="border-style: none; border-width: medium; " bordercolorlight="#000000" bordercolordark="#000000" bordercolor="#000080">
		<font face="Arial" color="#000080" size="2"><b>
		<p align="left"><%=Rel_Desc%></b></font></td>
		
		<td align="right" style="border-style: none; border-width: medium; " bordercolorlight="#000000" bordercolordark="#000000" width="57%" bordercolor="#000080">
		<p align="right"></tr>
		<%
	/* Displaying of superior child */
    /********************
	* Edited by James 25 Oct 2007
	************************/
	 Vector r = SRS.getRelationSpecific(Rel_ID,QR.getSurveyID());
	
	 for(int i=0; i<r.size(); i++) {
	  
		votblSurveyRelationSpecific vo = (votblSurveyRelationSpecific)r.elementAt(i);
     
            	
	  	int Spec_ID1 =  vo.getSpecificID();
		String Spec_Desc1 = vo.getRelationSpecific();
		
	
		%>
	<tr>
		<td width="7%" align="right" valign="top" style="border-style:none; border-width:medium; " bordercolor="#000080">&nbsp;
		</td>
		<td width="10%" align="right" valign="top" style="border-style:none; border-width:medium; " bordercolor="#000080">
		<p align="center">
			<font size="2" color="#0066FF">
			<%
			if(RaterRelation.getRelSpec() == Spec_ID1)
			{
			%>
				</font>
				<font color="#0066FF">
				<input type="radio" value=<%=Spec_ID1%> name="btnChild" onclick="branch(this.form)" style="float: right" checked></font><font size="2" color="#0066FF">
			<%
			}
			else
			{	%>
				</font>
				<font color="#0066FF">
				<input type="radio" value=<%=Spec_ID1%> name="btnChild" onclick="branch(this.form)" style="float: right"></font><font size="2" color="#0066FF">
			<%	}	%>
		</font>
		</td>
		<td align="left" valign="top" style="border-style:none; border-width:medium; " bordercolor="#000080">
		<font face="Arial" style="font-weight: 700" color="#0066FF" size="2"><%=Spec_Desc1%></font></td>
	</tr>
<%		}
%>	
		
<%}	%>		
	
	<tr>
		<td width="7%" align="right" valign="top" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
		<td width="10%" align="right" valign="top" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
		<td align="left" valign="top" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
	</tr>
	
</table></td>
		</tr>
</table>


	</form>
	<%	}	%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>