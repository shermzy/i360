<%@ page import="java.sql.*,
				 java.util.*,
                 java.io.* "%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SCluster" class="CP_Classes.SurveyCluster" scope="session"/>
<jsp:useBean id="SKB" class="CP_Classes.SurveyKB" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurveyBehaviour"%>
<%@ page import="CP_Classes.vo.votblSurveyCluster"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

<!-- added by Albert (16/07/2012): add a checkbox on top to choose all -->
function checkedAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}

function goBack() { window.location = "SurveyCompetency.jsp"; }
function goNext() { window.location = "SurveyDemos.jsp"; }

var myWindow; 
function windowOpen() 
{
	myWindow=window.open('Survey_KeyBehaviourList.jsp','windowRef','scrollbars=no, width=400, height=400');
    myWindow.location.href = 'Survey_KeyBehaviourList.jsp';
    
}

function windowOpenCluster(form) 
{
	if(form.clusterDropList.value == -1) {
		alert("Please select a cluster");
	} else{
		myWindow=window.open('Survey_ClusterKeyBehaviourList.jsp','windowRef','scrollbars=no, width=650, height=600');
    	myWindow.location.href = 'Survey_ClusterKeyBehaviourList.jsp';
	}
}
function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	if( field == null ) {
		isValid = 2;
	
	} else {

		if(isNaN(field.length) == false) {
			for (i = 0; i < field.length; i++)
				if(field[i].checked) {
					clickedValue = field[i].value;
					isValid = 1;
				}
		}else {		
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
				
		}
	}
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("<%=trans.tslt("No record selected")%>");
	else if(isValid == 2)
		alert("<%=trans.tslt("No record available")%>");
	
	isValid = 0;

}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function remove(form, field)
function remove(form, field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Delete Assigned Key Behaviour")%>?"))
		{
			form.action="SurveyKeyBehaviour.jsp?remove=1";
			form.method="post";
			form.submit();
		}
		
	}

}

function changeCluster(form, field){
	form.action="SurveyKeyBehaviour.jsp?clustChange="+field.value;
	form.method="post";
	form.submit();
}
 
</SCRIPT>

<body>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

if (!logchk.isUsable(username)) 
{%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%} 
else 
{

if(CE_Survey.getCompetencyLevel() != 1)
{
	%>
	<script>
	alert("<%=trans.tslt("Your current level of survey is Competency level")%>.")
	location.href="SurveyDemos.jsp";
	</script>
<%}
else
{

if(request.getParameter("remove") != null)
{
	int SurveyID = CE_Survey.getSurvey_ID();
	String [] KBID = request.getParameterValues("chkDel");
	
	// Edited by Eric Lu 22/5/08
	// Added new boolean to determine whether key behaviour was removed successfully
	// If successful, alert box with successful message appears
	// Else if unsuccessful, alert box with unsuccessful message appears
	boolean bKeyBehaviourDeleted = true;
		
	if(KBID != null)
	{ 
		for(int i=0; i<KBID.length; i++)
		{
			if (!CE_Survey.delKeyBehaviour(SurveyID,KBID[i]))
				bKeyBehaviourDeleted = false;
		}
		
		if (bKeyBehaviourDeleted) {
			%>
				<script>
                                        // Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
					alert("Removed successfully, survey status has been changed to Non Commissioned, to re-open survey please go to the Survey Detail page");
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
<%
if(request.getParameter("clustChange") != null){
	int id = Integer.parseInt(request.getParameter("clustChange"));
	CE_Survey.setClusterID(id);	
}
//Added by Xuehai, 07 Jun 2011, Adding 'sorting by Name'
if(request.getParameter("sorting") != null)
{	 
	int type = new Integer(request.getParameter("sorting")).intValue();
	int toggle = CE_Survey.getToggle();	//0=asc, 1=desc
	CE_Survey.setSortType(type);
	
	if(toggle == 0)
		toggle = 1;
	else
		toggle = 0;
		
	CE_Survey.setToggle(toggle);
} 
else
{
	CE_Survey.setSortType(1);
}
%>
<form name="SurveKeyBehaviour" action="SurveyKeyBehaviour.jsp" method="post">
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
		<p align="center"><b><font face="Arial" size="2" color="#CC0000"><%=trans.tslt("Key Behaviour")%></font></b></td>
		
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyDemos.jsp" style="cursor:pointer"><%=trans.tslt("Demographics")%></a></font></b></td>
		
		<td width="23" style="border-style: none; border-width: medium"><b>
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></b></td>
		
		<td width="131" style="border-style: none; border-width: medium">
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
		<td width="131" style="border-style: none; border-width: medium">
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
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="131" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
</table>

<%
/***************************** THIS PART IS IF SURVEY NOT USING CLUSTER *************************************/
if(SCluster.getUseCluster(CE_Survey.getSurvey_ID())==false){ //not using cluster
%>
<div style='width:610px; height:256px; z-index:1; overflow:auto'>  
		<table border="1" width="610" bordercolor="#3399FF" bgcolor="#FFFFCC">
		<tr>
				<td colspan="3" bgcolor="#000080">
				<p align="center"><span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2"><%=trans.tslt("Key Behaviour")%></font></span></td>
			</tr>
		
		<tr>
	
					<td bgcolor="#000080" width="3%" align="center">
					<font size="2">
	  				<input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkDel,this.form.checkAll)"></font>
					</td>

				<td bgcolor="#000080" width="24%" align="center">
				<span style="font-weight: 700">
				<!-- Edited by Xuehai, 07 Jun 2011, Sorting by Name -->
				<a href="SurveyKeyBehaviour.jsp?sorting=1">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Competency Name")%></font>
				</a>
				</span></td>
				<td bgcolor="#000080" width="72%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Key Behaviour Description")%></font></span></td>
			</tr>
<%
	int counter =1;
	//Edited by Xuehai, 07 Jun 2011, Adding 'sorting'.
	//Vector v = SKB.getSurveyKB(CE_Survey.getSurvey_ID());
	Vector v = SKB.getSurveyKB(CE_Survey.getSurvey_ID(), CE_Survey.getToggle(), CE_Survey.getSortType());
	
	for(int i=0; i<v.size(); i++) {
		votblSurveyBehaviour vo = (votblSurveyBehaviour)v.elementAt(i);
			int KBID = vo.getKeyBehaviourID();
			String KBName = vo.getKBName();
			String CompName = vo.getCompetencyName();
%>		
		  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
    			<td width="3%" align="left">
					<p align="center">
					<font face="Arial" size="2">
    			<%
				if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
				{ 	%>
					
					<input type="checkbox" name="chkDel" value=<%=KBID%>>
			<%	}
				else
				{%>
					<%=counter%>.
			<%	}%>
			</font></td>
				<td width="21%" align="left">
				<font face="Arial" size="2">&nbsp;<%=CompName%></FONT></td>
				<td width="72%" align="left">
				<font face="Arial" size="2"><%=KBName%></font></td>
			</tr>
<%	counter++;	
	}	%>			
		
		</table>
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
			<input type="button" value="<%=trans.tslt("Add Key Behaviour")%>" name="btnAdd" style="float: right" onclick="windowOpen()"></td>
			<td width="24%">
			<input type="button" value="<%=trans.tslt("Remove")%>" name="btnDel" style="float: right" onclick="remove(this.form, this.form.chkDel)"></td>
			<%	}	
		}	%>
		<td width="32%">
		<input type="button" value="<%=trans.tslt("Save")%> &amp; <%=trans.tslt("Proceed")%>" name="btnNext" style="float: right" onclick="goNext()"></td>

	</tr>
</table>
<%
}
/***************************** END OF PART NOT USING CLUSTER ********************************************/


/***************************** THIS PART IS IF SURVEY USING CLUSTER *************************************/
else{ //using cluster
	Vector clusterList = null;
%>
<div style='width:610px; height:256px; z-index:1; overflow:auto'> 
	<table>
		<tr><td><%=trans.tslt("Cluster")%> : </td>
		<td>
  		<select name="clusterDropList" size="1" onChange="changeCluster(this.form, this.form.clusterDropList)">
  			<option value=-1><%=trans.tslt("Please select one")%></option>
<%
			clusterList = SCluster.getSurveyCluster(CE_Survey.getSurvey_ID());
			
			for(int i=0; i<clusterList.size(); i++){
				votblSurveyCluster vCluster = (votblSurveyCluster) clusterList.elementAt(i);
				int pkCluster = vCluster.getClusterID();
				String clusterName = vCluster.getClusterName();
				
				if(CE_Survey.getClusterID() != 0 && pkCluster == CE_Survey.getClusterID()){
%>					<option value = <%=pkCluster%> selected><%=clusterName%></option
<% 				} else{ 
%>    				<option value = <%=pkCluster%>><%=clusterName%></option>
<%  	  
		 		}
			} //end for loop
%>  			
  		</select>
  		</td>
  		</tr>
  	</table> <br>
  	
		<table border="1" width="610" bordercolor="#3399FF" bgcolor="#FFFFCC">
		<tr>
				<td colspan="3" bgcolor="#000080">
				<p align="center"><span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2"><%=trans.tslt("Key Behaviour")%></font></span></td>
			</tr>
		
		<tr>
	
					<td bgcolor="#000080" width="3%" align="center">
					<font size="2">
	  				<input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkDel,this.form.checkAll)"></font>
					</td>

				<td bgcolor="#000080" width="24%" align="center">
				<span style="font-weight: 700">
				<!-- Edited by Xuehai, 07 Jun 2011, Sorting by Name -->
				<a href="SurveyKeyBehaviour.jsp?sorting=1">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Competency Name")%></font>
				</a>
				</span></td>
				<td bgcolor="#000080" width="72%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Key Behaviour Description")%></font></span></td>
			</tr>
<%
	int counter =1;
	//Edited by Xuehai, 07 Jun 2011, Adding 'sorting'.
	Vector v = SKB.getSurveyClusterKB(CE_Survey.getSurvey_ID(), CE_Survey.getToggle(), CE_Survey.getSortType(), CE_Survey.getClusterID());
	
	for(int i=0; i<v.size(); i++) {
		votblSurveyBehaviour vo = (votblSurveyBehaviour)v.elementAt(i);
			int KBID = vo.getKeyBehaviourID();
			String KBName = vo.getKBName();
			String CompName = vo.getCompetencyName();
%>		
		  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
    			<td width="3%" align="left">
					<p align="center">
					<font face="Arial" size="2">
    			<%
				if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID()))
				{ 	%>
					
					<input type="checkbox" name="chkDel" value=<%=KBID%>>
			<%	}
				else
				{%>
					<%=counter%>.
			<%	}%>
			</font></td>
				<td width="21%" align="left">
				<font face="Arial" size="2">&nbsp;<%=CompName%></FONT></td>
				<td width="72%" align="left">
				<font face="Arial" size="2"><%=KBName%></font></td>
			</tr>
<%	counter++;	
	}	%>			
		
		</table>
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
			<input type="button" value="<%=trans.tslt("Add Key Behaviour")%>" name="btnAdd" style="float: right" onclick="windowOpenCluster(this.form)"></td>
			<td width="24%">
			<input type="button" value="<%=trans.tslt("Remove")%>" name="btnDel" style="float: right" onclick="remove(this.form, this.form.chkDel)"></td>
			<%	}	
		}	%>
		<td width="32%">
		<input type="button" value="<%=trans.tslt("Save")%> &amp; <%=trans.tslt("Proceed")%>" name="btnNext" style="float: right" onclick="goNext()"></td>

	</tr>
</table>
<%
}
/***************************** END OF PART USING CLUSTER ************************************************/
%>

</form>
<%	}	
}%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>