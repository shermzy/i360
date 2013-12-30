
<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.* "%>  
                 
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SR" class="CP_Classes.SurveyResult" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<%@ page import="CP_Classes.vo.votblAssignment"%>
<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<SCRIPT LANGUAGE=JAVASCRIPT>
var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 125;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

var target
function check(field)
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
	if(field != null && target == null)
	{
		target = field.value;
		check =true;
	}
	return check;
		
}

function root(form) 
{
	form.action="UserList.jsp?receiver=1";
	form.method="post";
	form.submit();
}

function root(form,field) 
{
	if(check(field))
	{
		form.action="AssignTarget_Rater.jsp?root="+target;
		form.method="post";
		form.submit();
	}
	
}

function branch(form,field) 
{
	if(check(field))
	{
		form.action="AssignTarget_Rater.jsp?branch="+target;
		form.method="post";
		form.submit();
	}
	
}

function checkAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
	
}

function toggleCheckBoxes(divid, elem) {

    var div = document.getElementById(divid);

    var chk = div.getElementsByTagName('input');
    var len = chk.length;

    for (var i = 0; i < len; i++) {
        if (chk[i].type === 'checkbox') {
            chk[i].checked = elem.checked;
        }
    }        
}

function update(form)
{
	
	var getCheckBox = document.getElementsByName("choose");
	var checkSelected = false;
	for(i = 0; i < getCheckBox.length; i++){
		if(getCheckBox[i].checked)
			checkSelected = true;
	}
	if(checkSelected == true){
		form.action = "updateTRassignment.jsp?update=1";
		form.method = "post";
		form.submit();
	}
	else
		alert("No record selected");
}

</SCRIPT>
<body>
   
<form name="updateTRassignment" action="updateTRassignment.jsp" method="post">
<table class="tablesetting" id="table1">
	<tr>
		<td width="400">
		<br>
		<font face="Arial" style="font-weight: 700" color="#000080" size="2">
		<%= trans.tslt("Update Assignments") %>
		</font></td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>
			<%= trans.tslt("Tick the old records that you would like to update below.") %>
			<b><a href="AssignTarget_Rater.jsp"><font face="Arial" size="2" color = "#0000ff">Back</font></a></b>
			</li>			
		</ul>
		</td>
	</tr>
</table>

		
<%	
	boolean anyRecord = false;
	votblSurvey voSurvey = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
	
	if(voSurvey != null) 
	{
		int FKOraganization = voSurvey.getFKOrganization();
		int NameSequence = voSurvey.getNameSequence();
		assignTR.set_NameSequence(NameSequence);
		CE_Survey.set_survOrg(FKOraganization);
	}

	assignTR.setGroupID(0);
	assignTR.setTargetID(0);
	String TargetDetail[];
	String RaterDetail[];
	String RaterCode =" ";
	int superior = 000;
	int other = 000;
	
	//Rianto 19-Jan-05: Implemented filter function to avoid showing the whole Targets list
	if(request.getParameter("search") != null)
	{
		if(request.getParameter("selDiv").equals(""))
			assignTR.setSelectedDiv(-1); //Select all
		else {
			//System.out.println("Div - " + request.getParameter("selDiv"));
			assignTR.setSelectedDiv(Integer.parseInt(request.getParameter("selDiv")));
		}
		
		if(request.getParameter("selDept").equals(""))
			assignTR.setSelectedDept(-1); //Select all
		else {
			//System.out.println("Dept - " + request.getParameter("selDept"));
			assignTR.setSelectedDept(Integer.parseInt(request.getParameter("selDept")));
		}
		
		if(request.getParameter("selGroup").equals(""))
			assignTR.setSelectedGroup(-1); //Select all
		else {
			//System.out.println("Grp - " + request.getParameter("selGroup"));
			assignTR.setSelectedGroup(Integer.parseInt(request.getParameter("selGroup")));
		}
	}
	
	
	if(request.getParameter("update") != null)
	{
	
		String[] assignmentids = request.getParameterValues("choose");		//Get selected emails and store into array
	
        if (Integer.parseInt(request.getParameter("update")) == 1){
        	for (int i = 0; i < assignmentids.length; i++){
        		assignTR.updateassignment(Integer.parseInt(assignmentids[i]));
        	}
        }
		
        %>
		<script language = javascript>
			alert("<%=trans.tslt("Assignment(s) Updated Successfully")%>");
		</script>
		<%	
		
	}
	
	if(request.getParameter("sorting") != null)
	{	 
		int type = new Integer(request.getParameter("sorting")).intValue();
		int toggle = user.getToggle();	//0=asc, 1=desc
		user.setSortType(type);
		
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
			
		user.setToggle(toggle);
	} 
	else
	{
		user.setSortType(1);
	}

	       
	if(request.getParameter("update") != null)
	{
		int assignmentid = new Integer(request.getParameter("choose")).intValue();
		assignTR.updateassignment(assignmentid);
		
	 
	}
	
	
	String tablename = "";
	boolean ifmodified = false;
	boolean modified = false;
	int LastTargetID = 0;
	int rec = 0;
	int assicount = 0;
	int wcount = 0; // only display warning message once
	int[] record;
	record = new int[99999];
Vector vTR = assignTR.getTargetAssignments (CE_Survey.getSurvey_ID(), logchk.getUserType(), assignTR.getSelectedDiv(), assignTR.getSelectedDept(), assignTR.getSelectedGroup(), logchk.getPKUser());

for(int j=0; j<vTR.size(); j++)
{	
	votblAssignment vo = (votblAssignment)vTR.elementAt(j);
	anyRecord = true;
	int AssID = vo.getAssignmentID();
    String MTargetName = "";
	int RTRelation = vo.getRTRelation();
	RaterCode = vo.getRaterCode();
	int RaterStatus = vo.getRaterStatus();
	int RaterID = vo.getRaterLoginID();
	int TargetID = vo.getTargetLoginID();	
	String TargetName = vo.getLoginName();
	int GroupID = vo.getFKGroup();
	String GroupName = vo.getGroupName();
	TargetDetail = user.getUserDetail(TargetID,assignTR.get_NameSequence());
	RaterDetail = user.getUserDetail(RaterID,assignTR.get_NameSequence()); 
	


	if (assignTR.getSelectedDiv()==0 || assignTR.getSelectedDept()==0 || assignTR.getSelectedGroup() ==0){
	    ifmodified = assignTR.ifanytargetmodified(CE_Survey.getSurvey_ID(), TargetID);	
	    
	} else
	ifmodified = assignTR.iftargetmodified(CE_Survey.getSurvey_ID(), TargetID, assignTR.getSelectedDiv(), assignTR.getSelectedDept(), assignTR.getSelectedGroup());

	for (int i=0; i<rec; i++){
		if (record[i] == TargetID) ifmodified = false;
	}	
	
	if (ifmodified && (TargetID != LastTargetID)){
		  record[rec] = TargetID;
		  tablename = "table" + Integer.toString(rec*10);
		  rec++;
		  System.out.println(tablename);
	      MTargetName = assignTR.TargetName(TargetID);
	      LastTargetID = TargetID;%>	      
	     
	    <div style="position:relative;">   
	    <table border="1">
	   <tr>
	   <td>
	    <b><font face="Arial" size="2">Target: </font></b>
	    <font face="Arial" color=#ff0000 size="2"><b><%=MTargetName%></b></font>
	    
	    <br><b><font face="Arial" color=#0000ff size="2">New: </font></b>
	    
	    <br><b><font face="Arial" size="2">Division: </font></b>
	    <font face="Arial" size="2"><%=assignTR.TargetDiv(TargetID)%>; </font>	    
	    
	    <b><font face="Arial" size="2">Department: </font></b>
	    <font face="Arial" size="2"><%=assignTR.TargetDep(TargetID)%>; </font>	    
	    
	    <b><font face="Arial" size="2">Group: </font></b>
	    <font face="Arial" size="2"><%=assignTR.TargetGrp(TargetID)%></font></br>   
	    </td>
	    </tr>
	    </table>
	    </div>
	    
	    
	    <div id=<%=tablename%>  style='position:relative;'>
	    <table border="1" cellspacing="1" width="580" id="AutoNumber4" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">
	    <tr>
	  	<td width="31" align="center" bgcolor="#000080" height="35">
	    <font face="Arial" size="2"><span style="font-size: 11pt"><input type ="checkbox" name="chkAll" value="checkbox" onClick="toggleCheckBoxes('<%=tablename%>',this)"></span></font>
	    <td width="101" align="center" bgcolor="#000080" height="35">	  
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=2"><font style='color:white'><u><%=trans.tslt("AssignmentID")%></u></a></font></b></td>
	    <td width="93" align="center" bgcolor="#000080" height="35">
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=3"><font style='color:white'><u><%=trans.tslt("Target")%></u></a></font></b></td>
	    <td width="96" align="center" bgcolor="#000080" height="35">
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=4"><font style='color:white'><u><%=trans.tslt("Rater")%></u></a></font></b></td>
	    <td width="93" align="center" bgcolor="#000080" height="35">
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=5"><font style='color:white'><u><%=trans.tslt("Division")%></u></a></font></b></td>
	    <td width="127" align="center" bgcolor="#000080" height="35">
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=6"><font style='color:white'><u><%=trans.tslt("Department")%></u></a></font></b></td>
	    <td width="105" align="center" bgcolor="#000080" height="35">
	    <b><font face="Arial" color="#FFFFFF" style="font-size: 10pt"><a href="updateTRassignment.jsp?sorting=7"><font style='color:white'><u><%=trans.tslt("Group")%></u></a></font></b></td>
	    <td width="86" align="center" bgcolor="#000080" height="35">
	    <font face="Arial" color="#FFFFFF" style="font-size: 10pt"><b><a href="updateTRassignment.jsp?sorting=8"><font style='color:white'><u><%=trans.tslt("Entry Date")%></u></a></b></font></td>
  
	    </tr>
	   
	   <% 
	   for(int i=0; i<assignTR.howmanychanged(CE_Survey.getSurvey_ID(), TargetID); i++)
	     {    
		 Object assignmentID = assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(0);
	     Integer aid = new Integer(assignmentID.toString());
	     int AssignmentID = aid.intValue();
	     Object rater = assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(1);
	     String Rater = rater.toString();
	     Object div = 	assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(2);
	     String Division = div.toString();
	     Object dep = assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(3);
	     String Department = dep.toString();
	     Object grp = assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(4);
	     String Group = grp.toString();
	     Object date = assignTR.GetAssignment(TargetID,CE_Survey.getSurvey_ID())[i].get(5);
	     String Date = date.toString();
		
			
		
		%>
		  <tr onMouseOver = "this.bgColor = '#99ccff'"
		    	onMouseOut = "this.bgColor = '#FFFFcc'">
		  	<td width="31" height="19" align="left">
		  		<p align="center">
		  		<font face="Arial"><span style="font-size: 11pt">
		  			<input type ="checkbox" name="choose" value=<%=AssignmentID%>></span></font></td>
		    <td width="101" height="19" align="left">
			<font face="Arial" size="2"><%=AssignmentID%>&nbsp;</font></td>
		    <td width="120" height="19" align="left">
			<font face="Arial" size="2"><%=MTargetName%>&nbsp;</font></td>
		    <td width="120" height="19" align="left">
			<font face="Arial" size="2"><%=Rater%>&nbsp;</font></td>
		    <td width="96" height="19" align="left">
			<font face="Arial" size="2"><%=Division%>&nbsp;</font></td>
		    <td width="110" height="19" align="left">
			<font face="Arial" size="2"><%=Department%>&nbsp;</font></td>
		    <td width="105" height="19" align="left">
			<font face="Arial" size="2"><%=Group%>&nbsp;</font></td>
		    <td width="86" height="19" align="left">
			<font face="Arial" size="2"><%=Date%>&nbsp;</font></td>
		
		  </tr>
		<%
			}
		%>
	    
	    </table>
	    </div>      
	    
	<%} 
} %>





        <tr>
		<td width="7%" valign="top" bordercolor="#3399FF" style="border-style:none; border-width:medium; ">&nbsp;</td>
		<td width="13%" valign="top" bordercolor="#3399FF" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
		
		<td width="13%" valign="top" align="left" bordercolor="#3399FF" style="border-style:none; border-width:medium; ">&nbsp;
		</td>
			<td width="51%" valign="top" align="left" bordercolor="#3399FF" style="border-style:none; border-width:medium; ">
			<td width="16%" bordercolor="#3399FF" style="border-style:none; border-width:medium; ">&nbsp;</td>
		</tr>
	
	

<input type="button" value="<%=trans.tslt("Update")%>" name="btnupdate" style="float: left" onclick="update(this.form)"><font size="2">
<b><a href="AssignTarget_Rater.jsp"><font face="Arial" size="2" color = "#0000ff" style="float: left">Back</font></a></b>


</font>

			</td>
		</tr>
</table>
</form>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>