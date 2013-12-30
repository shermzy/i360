<%@ page import="java.sql.*,
				java.util.*,
                 java.io.* "%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SD" class="CP_Classes.SurveyDemo" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurveyDemos"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function goBack() 
{
	if(<%=CE_Survey.getCompetencyLevel()%> != 1)
		window.location = "SurveyCompetency.jsp";
	else
		window.location = "SurveyKeyBehaviour.jsp"; 
}
function goNext() { window.location = "SurveyRating.jsp"; }
function windowOpen() 
{
	var myWindow=window.open('Survey_DemosList.jsp','windowRef','scrollbars=no, width=200, height=320');
    myWindow.location.href = 'Survey_DemosList.jsp';
}

function check(field)
{
	var flag = 0;
	if(document.SurveyCompetency.chkDel!=null){
             if(document.SurveyCompetency.chkDel.length==undefined){
                if(document.SurveyCompetency.chkDel.checked)
			flag = 1;
                 }else{
             for (i = 0; i < document.SurveyCompetency.chkDel.length; i++) 
             {
		if(document.SurveyCompetency.chkDel[i].checked)
			flag = 1;
              }
              }
             }
	else{
           
             flag=2;
             }
	
	return flag;
		
}
 
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function remove(form, field)
function remove(form, field)
{
    var result=check(field);
	if(result==1)
	{
		if(confirm("<%=trans.tslt("Delete Assigned Demographics")%>?"))
		{
			form.action="SurveyDemos.jsp?remove=1";
			form.method="post";
			form.submit();
		}
		
	}
	else if (result==0)
	{
		alert("<%=trans.tslt("No record selected")%>");
	}
         else{
               alert("No record available");
             }

} 
 </SCRIPT>
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


if(request.getParameter("remove") != null)
{
	int SurveyID = CE_Survey.getSurvey_ID();
	String [] DemoID = request.getParameterValues("chkDel");
 	
 	// Edited by Eric Lu 22/5/08
 	// Added boolean to check whether demographics have been deleted successfully
 	// If successful, alert box with successful message appears
 	// Else, alert box with unsuccessful message appears
 	boolean bDemosDeleted = true;
 	
	if(DemoID != null)
	{
		try { 
			for(int i=0; i<DemoID.length; i++) {
				if (!CE_Survey.delDemos(SurveyID,DemoID[i]))
					bDemosDeleted = false;
			}
		} catch (SQLException ex) {
			bDemosDeleted = false;
		}
		
		if (bDemosDeleted) {
			%>
				<script>
					alert("Removed successfully");
				</script>
			<%
		} else {
			%>
				<script>
					alert("Removed unsuccessfully");
				</script>
			<%
		}
	}
}
%>
<form name="SurveyCompetency" action="SurveyCompetency.jsp" method="post">
<table border="0" width="80%" cellspacing="0" cellpadding="0" bordercolor="#000000" style="border-width: 0px">
	<tr>
		<td width="114" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700">
		<a href="SurveyDetail.jsp" style="cursor:pointer"><%=trans.tslt("Survey Detail")%></a></font></td>
		<td width="28" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="SurveyCluster.jsp" style="cursor:pointer"><%=trans.tslt("Cluster")%></a></u></font></td>
		
		<td width="28" style="border-style: none; border-width: medium"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700">
		<a href="SurveyCompetency.jsp" style="cursor:pointer"><%=trans.tslt("Competency")%></a></font></td>
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		<td width="113" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyKeyBehaviour.jsp" style="cursor:pointer"><%=trans.tslt("Key Behaviour")%></a></font></b></td>
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2" color="#CC0000" style="cursor:pointer"><%=trans.tslt("Demographics")%></font></b></td>
		<td width="23" style="border-style: none; border-width: medium"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		<td width="130" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyRating.jsp" style="cursor:pointer"><%=trans.tslt("Rating Task")%></a></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="130" style="border-style: none; border-width: medium">
		<p align="center"> <font size="2">
   
		<span style="font-weight: 700">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href ="AdvanceSettings.jsp" style="cursor:pointer"><%=trans.tslt("Advanced Settings")%></a></u></font></span></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyPrelimQ.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Preliminary Questions") %></a></font></b></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="AdditionalQuestions.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Additional Questions") %></a></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="130" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
</table>
<div style='width:610px; height:256px; z-index:1; overflow:auto'>  
		<div align="center">
		<table border="1" width="49%" bordercolor="#3399FF">
		<tr>
				<td colspan="2" bgcolor="#000080">
				<p align="center"><span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Demographics")%></font></span></td>
			</tr>
		
		<tr>

				<td bgcolor="#000080" width="15%" align="center">&nbsp;
				</td>

				<td bgcolor="#000080" width="81%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Selected Demographics Option")%></font></span></td>
			</tr>
<%
	int counter =1;
	Vector v = SD.getSurveyDemo(CE_Survey.getSurvey_ID());
	
	for(int i=0; i<v.size(); i++)
	{
		votblSurveyDemos vo = (votblSurveyDemos)v.elementAt(i);
		int DemoID = vo.getDemographicID();
		String DemoName = vo.getDemographicName();
				
%>		
		<tr>
		<td bgcolor="#FFFFCC" width="15%" align="left">
				<p align="center">
				<font face="Arial" size="2">
	<%		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
			{ %>
				
				<input type="checkbox" name="chkDel" value=<%=DemoID%>>
	<%		}
			else
			{%>
				<%=counter%>.
		<%	}%>
			
			</font></td>		
				<td bgcolor="#FFFFCC" width="81%" align="left">
				<font face="Arial" size="2"><%=DemoName%></FONT></td>
			</tr>
<%		counter++;	
	}	%>			
		
		</table>
		</div>
</div>
<table border="0" width="610" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%">
		<input type="button" value="<%=trans.tslt("Back")%>" name="btnBack" style="float: left" onclick="goBack()"></td>
		<%
		if(CE_Survey.getSurveyStatus() != 2)
		{

			if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
			{ %>
			<td width="26%">
			<input type="button" value="<%=trans.tslt("Add Demographics")%>" name="btnAdd" style="float: right" onclick="windowOpen()"></td>
			<td width="24%">
			<input type="button" value="<%=trans.tslt("Remove")%>" name="btnDel" style="float: right" onclick="remove(this.form, this.form.chkDel)"></td>
			<%	}	
		}	%>
		<td width="30%">
		<input type="button" value="<%=trans.tslt("Save")%> &amp; <%=trans.tslt("Proceed")%>" name="btnNext" style="float: right" onclick="goNext()"></td>
	</tr>
</table>
</form>
<%	}	%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>

		