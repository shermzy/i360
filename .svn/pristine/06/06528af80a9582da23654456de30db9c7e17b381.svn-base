<%@ page import="java.sql.*,
				java.util.*,
                 java.io.* "%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                    
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="SCluster" class="CP_Classes.SurveyCluster" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurveyCluster"%>

<!-- This file is created on 18 June 2012 by Albert -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
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

function goBack() { 
	window.location = "SurveyDetail.jsp"; 
}

function goNext(form){
 	window.location = "SurveyCompetency.jsp";
}

var myWindow;
var x = parseInt(window.screen.width) / 2 - 500;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 300;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
function windowOpen(){
	myWindow=window.open('Survey_ClusterList.jsp','windowRef','scrollbars=no, width=650, height=600');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'Survey_ClusterList.jsp';
}

function check(field){
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
	} // end if field==null
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("<%=trans.tslt("No record selected")%>");
	else if(isValid == 2)
		alert("<%=trans.tslt("No record available")%>");
	
	isValid = 0;
}
 
function remove(form, field){
	if(check(field)){
		if(confirm("<%=trans.tslt("Remove Assigned Cluster")%>?")){
			form.action="SurveyCluster.jsp?remove=1";
			form.method="post";
			form.submit();
		}
	}
}

function closeAllPopout() {
	if (myWindow != null) {
		myWindow.close();
	}
}
 </SCRIPT>
 
<body onUnload="closeAllPopout()">

<%
String username=(String)session.getAttribute("username");
if (!logchk.isUsable(username)){
%> 
	<font size="2">   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  
} else{
	if(SCluster.getUseCluster(CE_Survey.getSurvey_ID())==false){
%>
		<script>
			alert("<%=trans.tslt("Your survey is not using cluster. Proceed to Competency page")%>.")
			location.href="SurveyCompetency.jsp";
		</script>
<%
	} else{ 
		if(request.getParameter("remove") != null){
			int SurveyID = CE_Survey.getSurvey_ID();
			String [] ClusterID = request.getParameterValues("chkDel");
	
			// Added new boolean to check whether cluster has been removed successfully
			boolean bClusterRemoved = true;
			if(ClusterID != null){ 
				for(int i=0; i<ClusterID.length; i++){
					if (!CE_Survey.delCluster(SurveyID,ClusterID[i]))
						bClusterRemoved = false;
				}
			}
			if (bClusterRemoved) {
%>
				<script>
					alert("Removed successfully, survey status has been changed to Non Commissioned, to re-open survey please go to the Survey Detail page");
				</script>
<%
			} else {
%>
				<script>
					alert("Removed unsuccessfully");
				</script>
<%
			}// end else bClusterRemoved
		}//end if request.getParameter("remove")

		if(request.getParameter("sorting") != null){	 
			int type = new Integer(request.getParameter("sorting")).intValue();
			int toggle = CE_Survey.getToggle();	//0=asc, 1=desc
			CE_Survey.setSortType(type);
	
			if(toggle == 0)
				toggle = 1;
			else
				toggle = 0;
		
			CE_Survey.setToggle(toggle);
		} else{
			CE_Survey.setSortType(1);
		}
	}//end else getUseCluster
%>

<form name="SurveyCluster" action="SurveyCluster.jsp" method="post">
<table border="0" width="81%" cellspacing="0" cellpadding="0" bordercolor="#000000" style="border-width: 0px">
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
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color="#CC0000">
		<%=trans.tslt("Cluster")%></font></td>
		
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="SurveyCompetency.jsp" style="cursor:pointer"><%=trans.tslt("Competency")%></a></u></font></td>
		
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="113" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="SurveyKeyBehaviour.jsp" style="cursor:pointer"><%=trans.tslt("Key Behaviour")%></a></u></font></b></td>
		
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Demos()" style="cursor:pointer"><%=trans.tslt("Demographics")%></a></u></font></b></td>
		
		<td width="23" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="134" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="Rating()" style="cursor:pointer"><%=trans.tslt("Rating Task")%></a></u></font></b></td>
		
	</tr>
	<tr>
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
		<td width="134" style="border-style: none; border-width: medium">
		<p align="center">&nbsp; <font size="2">
   
		<span style="font-weight: 700">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="#" onClick="setting()" style="cursor:pointer"><%=trans.tslt("Advanced Settings")%></a></u></font></span></td>
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
		<td width="134" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
</table>


<div style='width:627px; height:256px; z-index:1; overflow:auto'>  
	<table border="1" width="610" bordercolor="#3399FF" bgcolor="#FFFFCC">
		<tr>
			<td colspan="2" bgcolor="#000080">
			<p align="center"><span style="font-weight: 700">
			<font face="Arial" color="#FFFFFF" size="2">
			<%=trans.tslt("Cluster")%></font></span></td>
		</tr>
		
		<tr>		
			<td bgcolor="#000080" width="3%" align="center">
			<font size="2">
	  		<input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkDel,this.form.checkAll)"></font>
			</td>
		
			<td bgcolor="#000080" width="24%" align="center">
			<span style="font-weight: 700">
			<!-- Edited by Xuehai, 07 Jun 2011, Sorting by Name -->
			<a href="SurveyCluster.jsp?sorting=1">
			<font face="Arial" color="#FFFFFF" size="2">
			<%=trans.tslt("Name")%></font>
			</a>
			<!-- End of Edited -->
			</span></td>
		</tr>
<%
	int counter = 1;
	Vector vCluster = SCluster.getSurveyCluster(CE_Survey.getSurvey_ID(), CE_Survey.getToggle(), CE_Survey.getSortType());
		
	for(int i=0; i<vCluster.size(); i++){
		votblSurveyCluster vo = (votblSurveyCluster) vCluster.elementAt(i);
		int ClusterID = vo.getClusterID();
		String ClusterName = vo.getClusterName();				
%>		
		<tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFcc'">
    		<td width="3%" align="left"><font face="Arial" size="2">
			<p align="center">
<%
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID())){ 	
%>	
			<input type="checkbox" name="chkDel" value=<%=ClusterID%>>
<%		} else{
%>
		<%=counter%>.
<%		}
%>
			</font></td>
			<td width="24%" align="left">
			<font face="Arial" size="2">&nbsp;<%=ClusterName%></FONT></td>
		</tr>
<%		counter++;	
	}	
%>			
		
	</table>
</div>

<table border="0" width="610" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<td width="20%">
		<input type="button" value="<%=trans.tslt("Back")%>" name="btnBack" style="float: left" onClick="goBack()"></td>
		
<%
	if(CE_Survey.getSurveyStatus() != 2){
		if(CE_Survey.Allow_SurvChange(CE_Survey.getSurvey_ID())){ 		
%>
		<td width="26%">
		<input type="button" value="<%=trans.tslt("Add Cluster")%>" name="btnAdd" style="float: right" onClick="windowOpen()">
		</td>
		<td width="24%">
		<input type="button" value="<%=trans.tslt("Remove")%>" name="btnDel" style="float: right" onClick="remove(this.form, this.form.chkDel)">
		</td>
<%		}
	}
%>
		<td width="30%">
		<input type="button" value="<%=trans.tslt("Save")%> &amp; <%=trans.tslt("Proceed")%>" name="btnNext" style="float: right" onClick="goNext(this.form)"></td>
	</tr>
</table>
</form>
<%	
}//end else (logchk.isUsable)
%>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>