<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.votblSurveyCluster" %>
<%@ page import = "CP_Classes.vo.votblSurveyCompetency" %>
<%@ page import = "CP_Classes.vo.votblSurveyBehaviour" %>

<%@ page import = "CP_Classes.vo.voCluster" %>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="ClusterQ" class="CP_Classes.Cluster" scope="session"/>
<jsp:useBean id="SC" class="CP_Classes.SurveyCompetency" scope="page"/>
<jsp:useBean id="SKB" class="CP_Classes.SurveyKB" scope="page"/>

<%	
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("expires", 0);
	
	String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) {
  
  	response.sendRedirect("index.jsp");
  }
%>

<% 
//Edited by Roger 17 June 2008
//Fix bug when choosing key behaviour  
%>

<%
		//capture event when ddl changed
		String comIdStr = request.getParameter("change");
		int paramComId = 0;
		if (comIdStr != null && !"".equals(comIdStr)) {
			paramComId = Integer.parseInt(comIdStr);
		}
%>


<%
if ( request.getParameter("save") != null && !"".equals(request.getParameter("save"))) {
		
		int previousComp = 0;
		int SurveyID = CE_Survey.getSurvey_ID();
		int ClusterID = CE_Survey.getClusterID();
		System.out.println("ASDASDASDASDASDASDASDASDADSCluster id is "+ClusterID);
		String [] chkSelect = request.getParameterValues("chkKB");
		
		// Edited by Eric Lu 22/5/08
		// Added new boolean that determines whether key behaviour is added successfully
		// If boolean is true, successful message pops up
		boolean bKeyBehaviourAdded = true;

			if(chkSelect != null)
	    	{ 
	    		try
				{
		    		for(int i=0; i<chkSelect.length; i++)
					{
						System.out.println(">>>>>>>>" + chkSelect[i]);
						if (!CE_Survey.addKeyBehaviour(SurveyID,Integer.parseInt(request.getParameter("comId")),Integer.parseInt(chkSelect[i]),ClusterID))
							bKeyBehaviourAdded = false;
					}
				}
				catch(SQLException sqle)
				{	
					bKeyBehaviourAdded = false;
				}
			
				if (bKeyBehaviourAdded) {
					%>
						<script>
                                                        // Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
							alert("Added successfully, survey status has been changed to Non Commissioned, to re-open survey please go to the Survey Detail page");
						</script>
					<%
				} else {
					%>
						<script>
							alert("Added unsuccessfully");
							window.close();
							opener.location.href = 'SurveyKeyBehaviour.jsp';
						</script>
					<%
				}

			}	
			CE_Survey.setJobPos_ID(paramComId);

	}

	if(	request.getParameter("close") != null)
	{
%>					
<script>
window.close()
opener.location.href = 'SurveyKeyBehaviour.jsp';
</script>
<%
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">

  <SCRIPT LANGUAGE="JavaScript">
<!-- Begin
var data;
function checkData(field)
{
	var flag = false;
	
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
			flag = true;
		if(field[i].selected)
		{
			flag = true;
			data = field[i].value;
			
		}
	}
	if(field != null)
		flag = true;
		
	return flag;
		
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

function checkedAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}

function closeWindow(form)
{
	window.close();
 	opener.location.href = 'SurveyKeyBehaviour.jsp';
}
function CompChange(form, field) 
{
	if (field.selectedIndex == 0) {
		window.location.href = "Survey_ClusterKeyBehaviourList.jsp";
	} else {
		checkData(field);
		form.action = "Survey_ClusterKeyBehaviourList.jsp?change="+data
		form.method = "post";
		form.submit();
	}
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmAdd(form,field, fieldKB) 
function confirmAdd(form,field, fieldKB) 
{
	checkData(field);
	if(check(fieldKB)) {
		form.action = "Survey_ClusterKeyBehaviourList.jsp?close=1&save=1"
		form.method = "post";
		form.submit();
	} 
}
-->
</script>
</head>

<body bgcolor="#DEE3EF">

<!-- Populate Drop Down List -->
<form name="Survey_ClusterKeyBehaviourList" method="post" action="Survey_ClusterKeyBehaviourList.jsp">
<table width="343" border="0">
<th colspan=3 align=center><b>
<font span style='font-family:Arial; font-weight:700' size="2"><%=trans.tslt("Cluster")%>:
<%
	voCluster vCluster = ClusterQ.getCluster(CE_Survey.getClusterID());
	String clusterName = vCluster.getClusterName();
%>
<%=clusterName%>
</font>
</th>
    <tr>
      <td><font span style='font-family:Arial; font-weight:700' size="2"><%=trans.tslt("Competency")%>:</font></td>
      <td>&nbsp;</td>

      <td>
			<select name="selCompetency" onChange="CompChange(this.form,this.form.selCompetency)">
				<option value="" selected>Please select one</option>
      <%

		Vector v = SC.getSurveyClusterCompetency(CE_Survey.getSurvey_ID(), CE_Survey.getClusterID());
		
		for(int i=0; i<v.size(); i++)
		{	
			votblSurveyCompetency vo = (votblSurveyCompetency)v.elementAt(i);
			
			int CompLevel = vo.getCompetencyLevel();
			int CompID = vo.getCompetencyID();
			String Comp = vo.getCompetencyName();
			
			if(paramComId == CompID)
			{
		%>
			<option value=<%=CompID%> selected><%=Comp+" ("+CompLevel+")"%></option>
		<%	}
			else
			{
		%>
			<option value=<%=CompID%>><%=Comp+" ("+CompLevel+")"%></option>

		<%	}				
		}
		%>
    	</select>
		</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
</table>

<div style="width:388px; height:237px; z-index:1; overflow:auto"> 
<table border="1" width="383" bordercolor="#3399FF" bgcolor="#FFFFCC">
<th bgcolor="navy" width="20">
	  <font size="2">
	  <input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkKB,this.form.checkAll)"></font></th>
<th bgcolor="navy">
	<font size="2">
	
   
    	<b><font span style='font-family:Arial;color:white'><%=trans.tslt("Key Behaviour")%></font></b></th>
<% 	

//Edited by Roger 17 June 2008
//Fix problem when listing the wrong key behavior
int behaviourSize = 0;

if (paramComId != 0) {
	int CompLevel = SC.getCompetencyLevel(CE_Survey.getSurvey_ID(), paramComId);
	CE_Survey.set_CompLevel(CompLevel);
		
	Vector vKB = SKB.getKBNotAssigned(CE_Survey.getSurvey_ID(),paramComId,CE_Survey.get_CompLevel());
	behaviourSize = vKB.size();
	for(int i=0; i<vKB.size(); i++)
	{
		votblSurveyBehaviour vo = (votblSurveyBehaviour)vKB.elementAt(i);
		int pkKB = vo.getKeyBehaviourID();
		String KeyBehaviour = vo.getKBName();

%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
       <td width="20">
	   		<font span style='font-size:11.0pt;font-family:Arial'>
	   		<input type="checkbox" name="chkKB" value=<%=pkKB%>></font><font span style='font-family:Arial' size="2">
            </font>
	   </td>
	   <td width="347">
           <font span style='font-family:Arial' size="2"><%= KeyBehaviour %></font><font size="2">&nbsp;
	   	</font>
	   </td>
   </tr>
<%	}	
}
%>
</table>
</div>


<table border="0" width="91%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
 <% if (behaviourSize > 0) { %>     
        <input type="button" value="<%=trans.tslt("Add")%>" name="btnAdd" onClick="confirmAdd(this.form,this.form.selCompetency, this.form.chkKB)">
 <% } %>  &nbsp;    
        </td>
		<td width="448"> <font size="2">
   
    	<input type="button" name="btnClose" value="<%=trans.tslt("Close")%>" onClick="closeWindow()" style="float: right"></td>
	</tr>
	</table>

<input type="hidden" name="comId" value="<%=paramComId%>"/>
</form>



</body>
</html>







