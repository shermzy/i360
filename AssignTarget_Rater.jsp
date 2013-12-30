
<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*"%>  
                 
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
<!-- CSS -->

<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap-responsive.css">



<!-- jQuery -->
<script type="text/javascript" src="lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/js/bootstrap.js"></script>
<script type="text/javascript" src="lib/js/jquery-1.9.1.js"></script>


<script src="lib/js/bootstrap.min.js" type="text/javascript"></script>
<script src="lib/js/bootstrap-dropdown.js"></script>

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
function delBranch(assID) 
{
	
	if(confirm("<%=trans.tslt("Delete Rater?")%>"))
	{
		//var myWindow=window.open('AssignTR_RaterMenu.jsp?del=1','windowRef','scrollbars=no, width=350, height=550');
		window.location.href = 'AssignTarget_Rater.jsp?branch='+ assID + '&delRater=1';
    	
	}
	else
	{
		window.close();
		opener.location.href ='AssignTarget_Rater.jsp';
	}
}


function del(target) 
{
	if(confirm("<%=trans.tslt("Delete Target?")%>"))
	{
		
    	window.location.href = 'AssignTarget_Rater.jsp?root=' + target + '&delTarget=1';
	}

}

function popme()
{
	alert("<%=trans.tslt("The assignment has been saved")%>.");
	//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
	//location.href('SurveyList_AssignTR.jsp');
	location.href='SurveyList_AssignTR.jsp';
}

function root(form) 
{
	form.action="UserList.jsp?receiver=1";
	form.method="post";
	form.submit();
}

function popme_Sup()
{
	alert("<%=trans.tslt("The assignment has been saved")%>.");
	location.href='Nomination_AssignTR.jsp';
}

function root(x,target) 
{
form = document.AssignTarget_Rater;
form.action="AssignTarget_Rater.jsp?root="+target;
form.method="post";
form.submit();
	
}

function branch(form,assID,target) 
{
// 	form = document.AssignTarget_Rater;
// 		form.action="AssignTarget_Rater.jsp?branch="+target;
// 		form.method="post";
// 		form.submit();

	window.location.href ="AssignTR_RaterMenu_EditRater.jsp?assID=" + assID+ "&targetID=" +target;
	
	
}

function search(form)
{
	form.action="AssignTarget_Rater.jsp?search=1";
	form.method="post";
	form.submit();
}

function addRater(target)
{

	var myWindow=window.open('AssignTR_TargetMenu_AddRater.jsp','windowRef','scrollbars=no, width=800, height=600');
	//myWindow.moveTo(x,y);
    myWindow.location.href = 'AssignTR_TargetMenu_AddRater.jsp?target=' + target;
}
function add()
{
	var myWindow=window.open('AssignTR_AddTarget.jsp','windowRef','scrollbars=no, width=800, height=600');
	//myWindow.moveTo(x,y);
    myWindow.location.href = 'AssignTR_AddTarget.jsp';
}

function populateDept(form, field)
{
    //Changed by Ha on 20/05/08 to refresh the page
	form.action = "AssignTarget_Rater.jsp?selDiv=" + field.value +"&selDept=0&selGroup=0" ;
	form.submit();
}

function populateGrp(form, field1, field2)
{
    //Changed by Ha on 20/05/08 to refresh the page
	form.action = "AssignTarget_Rater.jsp?selDiv=" + field1.value + "&selDept=" + field2.value +"&selGroup=0";
	form.submit();
}

function update(form, field)
{
	if (check(field)){
		// Message edited Eric Lu 14/5/08
		//Edit message
		if(confirm("<%=trans.tslt("update assignment?")%>"))
		{
		
			form.action="AssignTarget_Rater.jsp?update=1";
			form.method="post";
			form.submit();
		}
	}
	
}
</SCRIPT>
<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  }
  

assignTR.setGroupID(0);
assignTR.setDivID(0);
assignTR.setDeptID(0);

if(request.getParameter("root") != null)
{
	int Target = new Integer(request.getParameter("root")).intValue();
	assignTR.setGroupID(0);
	assignTR.set_selectedTargetID(Target);

}

if(request.getParameter("branch") != null)
{
	int assignmentid = new Integer(request.getParameter("branch")).intValue();
	assignTR.set_selectedAssID(assignmentid);

}
if(request.getParameter("delTarget") != null)
{
	System.out.println("Delete");
	boolean bIsDeletedTarget = assignTR.delTarget(CE_Survey.getSurvey_ID(),assignTR.get_selectedTargetID(), logchk.getPKUser());
	boolean bIsDeletedTargetResult = assignTR.delTargetsResult(CE_Survey.getSurvey_ID(),assignTR.get_selectedTargetID());	// Delete Target's results from tblAvgMean
	System.out.println("target" + bIsDeletedTarget);
	System.out.println("target" + bIsDeletedTargetResult);

}
if(request.getParameter("delRater") != null)
{
		//Changed by Ha 19/06/08 to automatically calculated again when a rater is removed
		System.out.println(assignTR.get_selectedAssID());
		int targetID = SR.TargetID(assignTR.get_selectedAssID());
		boolean bIsDeleted = assignTR.delRater(assignTR.get_selectedAssID(), logchk.getPKUser());
		System.out.println("rater deleted? " + bIsDeleted);
		
		SR.updateCalculationStatus(targetID, CE_Survey.getSurvey_ID(),0);
		if (!SR.isAllRaterRated(CE_Survey.getSurvey_ID(),assignTR.getGroupID(), targetID)) 
			SR.CalculateStatus(targetID,CE_Survey.getSurvey_ID(), assignTR.getDivID(), assignTR.getDeptID(), assignTR.getGroupID(), 1);	
		else    	
		    SR.CalculateStatus(targetID,CE_Survey.getSurvey_ID(), assignTR.getDivID(), assignTR.getDeptID(), assignTR.getGroupID(), 0);	
		//End of change made by Ha 19/06/08
		assignTR.set_selectedTargetID(0);
		assignTR.set_selectedAssID(0);
}


%>
<body>
	<div class="container">   
	<div class="row-fluid">
	<div class="span6">
		<form name="AssignTarget_Rater" action="AssignTarget_Rater.jsp" method="post">
			<div class="row-fluid">
			
					<h5><%= trans.tslt("Assign Target / Rater") %></h5>
				
			</div>
			
		

<%
	/* Nomination module */			
	if(logchk.getUserType() != 4)
	{	//User = Admin or SA
%>		
		<div class="row-fluid">
			<div class="span2">
  				Division:
        	</div>
        	<div class="span2">
        		<select size="1" name="selDiv" onChange="populateDept(this.form, this.form.selDiv)">
			<%
				int iDiv = 0;
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
				if(request.getParameter("selDiv") != null)
				{
					if (request.getParameter("selDiv").equals(""))
						iDiv = 0;
					else
						iDiv = Integer.parseInt(request.getParameter("selDiv")); 
				}
					
				if(iDiv == 0){%>
						<option value=0 selected>All</option>
				<% 
					}else{
				%>
						<option value=0>All</option>
				<%  }
			
				Vector vDiv = SR.getSurveyDiv(CE_Survey.getSurvey_ID(), logchk.getOrg());
				
				for(int i=0; i<vDiv.size(); i++) 
				{
					String [] row = (String[])(vDiv.elementAt(i));		
					int DivID = Integer.parseInt(row[0]);
					String DivName = row[1];
			
					if(iDiv != 0 && iDiv == DivID)
					{	%>
						<option value=<%=DivID%> selected><%=DivName%></option>
				<%	}else{%>
						<option value=<%=DivID%>><%=DivName%></option>
				<%	}
				}	%>			
					</select>
			</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			Department:		
		</div>
	
   		<div class="span2">
   			<select size="1" name="selDept" onChange="populateGrp(this.form, this.form.selDiv, this.form.selDept)">
		<%
			int iDept = 0;
			
			if(request.getParameter("selDept") != null)
			{
				//Group 
				if (request.getParameter("selDept").equals(""))
					iDept = 0;
				else
					iDept = Integer.parseInt(request.getParameter("selDept"));
				//End Group 
			}
				
			if(iDept == 0){%>
					<option value=0 selected>All</option>
			<%
				}else{
			%>
					<option value=0 >All</option>
			<%  }
			Vector vDept = SR.getvDept();
			
			if(vDept.isEmpty()){
				vDept = SR.getSurveyDept(CE_Survey.getSurvey_ID(), iDiv);
			}
			
			for(int i=0; i<vDept.size(); i++) 
			{
				String [] row = (String[])(vDept.elementAt(i));		
				int Dept_ID = Integer.parseInt(row[0]);
				String DeptName = row[1];
				
				if(iDept != 0 && iDept== Dept_ID )
				{	%>
					<option value=<%=Dept_ID%> selected><%=DeptName%></option>
			<%	}else{%>
					<option value=<%=Dept_ID%>><%=DeptName%></option>
			<%	}
			}	%>			
				</select>
				</div>
			</div>
		     <div class="row-fluid">
		     	<div class="span2">
		     	Group:
		     	</div>
		     
		    <div class="span2">
		    <select  name="selGroup">
		<%
			int iGroup = 0;
			
			if(request.getParameter("selDept") != null)
			{
				//Group 
				if (request.getParameter("selDept").equals(""))
					iGroup = 0;
				else
					iGroup = Integer.parseInt(request.getParameter("selGroup"));
				//End Group 
			}
		
			if(iGroup == 0){%>
					<option value=0 selected>All</option>
			<%
				}else{
			%>
				<option value=0>All</option>	
			<%  }
			
			Vector vGroup = SR.getvGroup();
			
			if(vGroup.isEmpty()){
				vGroup = SR.getSurveyGroup(CE_Survey.getSurvey_ID(), iDept);
			}
			
			for(int i=0; i<vGroup.size(); i++) 
			{
				String [] row = (String[])(vGroup.elementAt(i));
				int Division_ID = Integer.parseInt(row[0]);
				String Division_Desc = row[1];
		
				int Group_ID = Integer.parseInt(row[0]);
				String GroupName = row[1];
				
				if(iGroup != 0 && iGroup== Group_ID )
				{	%>
					<option value=<%=Group_ID%> selected><%=GroupName%></option>
			<%	}else{%>
					<option value=<%=Group_ID%>><%=GroupName%></option>
			<%	}
			}	%>			
		</select>
		</div>
		</div>
      <div class="row-fluid">
     	<div class="span2">
     	 <input type="button" class="btn" value="<%= trans.tslt("Show")%>" name="btnSearch" onclick="search(this.form)">
     	 </div>
   		<%		
		if(CE_Survey.getSurveyStatus() == 2)
		{ %>
		<div class="span2">
			<input type="button" class="btn" value="<%= trans.tslt("Back") %>" name="btnBack" style="float: left" onclick="location.href='SurveyList_AssignTR.jsp'">
		</div>
	<%	}
		else
		{
			/* Nomination module */		
			
			if(logchk.getUserType() == 4)
			{	//User is Participant or Rater
		%><div class="span3">
			<input type="button" class="btn" value="<%= trans.tslt("Save & Finish") %>" name="btnBack" style="float: left" onclick="popme_Sup()">
			</div>
	<%		}
			else
			{	%>
			<div class="span3">
			<input type="button" class="btn" value="<%= trans.tslt("Save & Finish") %>" name="btnBack" style="float: left" onclick="popme()">
			</div>
	<%		}	
		}
   		
   		if(CE_Survey.getSurveyStatus() != 2)
	{ 
		/* Nomination module */			
		if(logchk.getUserType() == 4)
		{	//User is Participant or Rater
			//Don't Show Add Target Button
		}
		else
		{	
		%><div class="span1">
		<input type="button" class="btn" value="<%= trans.tslt("Add Target") %>" name="btnAdd" onclick="add()">
		</div>
		
		<%
		}
	}	%>	
<% } //END if(logchk.getUserType() != 4)
	/* END Nomination module */			
	
 %>		</div></div>
 
<div class="span2">
	 <table width="178" border="1" style='border-width:2px; font-size:10.0pt;font-family:Arial' bordercolor="#3399FF">
	  <tr height="20">
	    <td colspan="2" align="center" bordercolor="#3399FF" bgcolor="#000099" style="border-left-style:solid; border-left-width:1px; border-right-style:solid; border-right-width:1px; border-top-style:solid; border-top-width:1px">
		<b>
		<font color="#FFFFFF"><span class="style12"><%= trans.tslt("LEGEND") %></span></font></b></td>
	    </tr>
	  <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/Group.bmp"></td>
	    <td style="border-left-style:solid; border-left-width:1px; border-right-style:solid; border-right-width:1px; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Group") %></span></font></td>
	  </tr>
	  <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/Person.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Target") %></span></font></td>
	  </tr>
	  <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/RaterCompleted.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Rater Completed") %></span></font></td>
	  </tr>
	  <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/RaterIncomplete.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Rater Incomplete") %></span></font></td>
	  </tr>
	  <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/RaterUnreliable.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Rater Unreliable") %></span></font></td>
	  </tr>
	    <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/RaterExcluded.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Rater Excluded") %></span></font></td>
	  </tr>
	   <tr height="20">
	    <td align="center" style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<img src="images/RaterNA.bmp"></td>
	    <td style="border-style: solid; border-width: 1px; " bordercolor="#3399FF" bgcolor="#FFFFCC">
		<font color="#000080"><span class="style10">&nbsp;<%= trans.tslt("Rater NA") %></span></font></td>
	  </tr>
	</table>
 </div>
		
<table class="table">
		
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
	boolean ifmodified = false;
	boolean modified = false;
	int LastTargetID = 0;
	int rec = 0;
	int assicount = 0;
	int wcount = 0; // only display warning message once
	int[] record;
	record = new int[99999];
Vector vTR = assignTR.getTargetAssignments (CE_Survey.getSurvey_ID(), logchk.getUserType(), assignTR.getSelectedDiv(), assignTR.getSelectedDept(), assignTR.getSelectedGroup(), logchk.getPKUser());
System.out.println("SurveyID = " + CE_Survey.getSurvey_ID() +  "UserType= " + logchk.getUserType() + "Div= " + assignTR.getSelectedDiv() + "Dept =" +  assignTR.getSelectedDept() + "Group =" + assignTR.getSelectedGroup() + "User" +  logchk.getPKUser());
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
	
	if (ifmodified && wcount == 0){%>   	  
	   
	    <b><font face="Arial" size="2" color = "#0000FF">Details of some target(s) has been modified.</font></b>
		
			<b><font face="Arial" size="2" color = "#0000FF">Please click </font></b>
			<b><a href="updateTRassignment.jsp"><font face="Arial" size="2" color = "#FF0000">here</font></a></b>
			<b><font face="Arial" size="2" color = "#0000FF">to update assignment.</font></b>
				
		<br><font face="Arial" size="2">                                           </font>		
	</div>
	     
	
	
	<% wcount++;		
	}	
}




System.out.println("Size"+ vTR.size());
for(int j=0; j<vTR.size(); j++)
{	
	System.out.println("at"+ j);	
	votblAssignment vo = (votblAssignment)vTR.elementAt(j);
	anyRecord = true;
	int AssID = vo.getAssignmentID();
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

	if((GroupID != assignTR.getGroupID()) || (assignTR.getGroupID() == 0))
	{
		assignTR.setGroupID(GroupID);
%>		


	<tr>
		<td>
		<img border="0" src="images/Group.bmp" width="16" height="16"></td>
		<td><%=GroupName%></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
<%	}	
		if(TargetID != assignTR.getTargetID() || assignTR.getTargetID() == 0){
			assignTR.setTargetID(TargetID);
		%>	
		
		<tr>
		<td></td>
			<td>	
					
					<%	if(CE_Survey.getSurveyStatus() != 2)
					{ %>	
					<label onclick="root(1,<%=TargetID%>)">
				<%	}	%>
					<img border="0" src="images/Person.bmp" width="16" height="16" >
			
				
			
					<%=TargetDetail[0] +", "+TargetDetail[1]%></label>		</td>
			<td><button type="button" class="btn-success" onclick="addRater(<%=TargetID%>)">Add </button></td>
			<td> <button type="button" class="btn-danger" onclick ="del(<%=TargetID%>)">Delete</button></td>
		</tr>
	<%	}	%>
	<tr>
	<td></td>
		<td style="padding-left:40px" onclick="branch(this.form,<%=AssID%>,<%=TargetID%>)">
		

<%	if(RaterID != 0)
	{	
		String path=" ";	
		
		if(RaterStatus == 0)
			path = "images/RaterIncomplete.bmp";
		else if(RaterStatus == 1)
			path = "images/RaterCompleted.bmp";
		else if(RaterStatus == 2)
			path = "images/RaterUnreliable.bmp";
		else if(RaterStatus == 3)
			path = "images/RaterExcluded.bmp";	
		else if(RaterStatus == 4)
		//Changed by Ha 16/06/08 path should be RaterUnreliable.bmp
			path = "images/RaterUnreliable.bmp";	
		else if(RaterStatus == 5)
		//Changed by Ha 01/07/08 path is RaterNA.bmp
			path = "images/RaterNA.bmp";				

		String raterDetails = RaterDetail[0] +", "+RaterDetail[1] + " (" + RaterCode + ")";
				
		if(CE_Survey.getSurveyStatus() != 2)
			{ %>	
			<label><img border="0" src=<%=path%> >
		<%	}
		else{%>
		<label></label>
		<%} %>	
			<%=raterDetails%></label></td>
			<td>&nbsp;</td>
		<td><button type="button" class="close" onclick="delBranch(<%=AssID%>)" data-dismiss="modal" aria-hidden="true">×</button></td>
		</tr>
	<%	
	}
}		//end while
%>

	
</table>

	


<input type="button" value="<%=trans.tslt("Update")%>" name="btnupdate" style="float: right" onclick="update(this.form, this.form.choose)"><font size="2">


</form>

<%@ include file="Footer.jsp"%>
</body>
</html>