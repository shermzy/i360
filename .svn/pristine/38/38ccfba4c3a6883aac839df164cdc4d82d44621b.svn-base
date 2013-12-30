<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%> 
<%@ page import="CP_Classes.vo.*"%>
<%@ page import="CP_Classes.SurveyResult"%> 
<%@ page pageEncoding="UTF-8"%>

<html>
<head>
<!--link REL="stylesheet" TYPE="text/css" href="..\Settings\Settings.css"-->

<title>Report for Subgroup</title>

<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="ExcelGroup" class="CP_Classes.GroupReport" scope="session"/>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script> 
	$(document).ready(function(){
	   $("#chkAllUsers").click(function(){
		  if (this.checked) {
			$(this).attr('checked', true);
			$('input:checkbox[id^=chkUser]').each(function(){
				$(this).attr('checked', true);
			})
		  } else {
			$(this).attr('checked', false);
			$('input:checkbox[id^=chkUser]').each(function(){
				$(this).attr('checked', false);
			})
		  }
	   });

	   $('table[id^=tbl]').each(function() {
			var $table = $(this);
			$('th', $table).each(function(column) {
				var $header = $(this);
				$header.click(function() {
				
					var sortDirection = 1;
					if ($header.is('.sorted-asc')) {
						sortDirection = -1;
					}
				
					var rows = $table.find('tbody > tr').nextAll().get();
	
					rows.sort(function(a, b) {
						var keyA = $(a).children('td').eq(column).text().toUpperCase();
						var keyB = $(b).children('td').eq(column).text().toUpperCase();
						if (keyA < keyB) return -sortDirection;
						if (keyA > keyB) return sortDirection;
						return 0;
					});
				
					$.each(rows, function(index, row) {
						$table.children('tbody').append(row);
					});
					
					$table.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
					if (sortDirection == 1) {
						$header.addClass('sorted-asc');
					}
					else {
						$header.addClass('sorted-desc');
					}
				});
			});
		});
	
	})
</script>

<script language="javascript">

function closeWindow()
{
	window.close();
}
/*
function check(field)
{
	var isValid = 0; //No record selected
		
	if( field == null ) {
		isValid = 2;  //No record available
	} else {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked) {
				isValid = 1; //At least 1 record is selected
				break;
			}
    }
	
	return isValid;
}
*/
function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
		
	if( field == null ) {
		isValid = 2;
	
	} else {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked) {		
				clickedValue = field[i].value;
				isValid = 1;
			}
    
		if(isValid == 0 && field != null)  {
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
		}
    }
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		return; //alert("No record selected");
	else if(isValid == 2)
		return; //alert("No record available");
	
	isValid = 0;	
	
}
function confirmPreview(form)
{
	if(form.grpName.value == ""){
		alert("Please enter a group name.");
		return;
	}
	
	var valid = check(form.chkUser);
	if(valid == 0) {
		alert("Please select at least 2 users.");
		return;
	} 
	else if (valid == 1) {
		var count = 0;
		for (i = 0; i < (form.chkUser).length; i++) {
			if((form.chkUser)[i].checked) {	
				count++;
			}
		}	
		if(count < 2){
			alert("Too few users selected.");
			return;
		}
	}
	else {
		//alert("There are no users to be selected for this survey");
	}
	
	var grpName = form.grpName.value;
	var type = '<%=request.getParameter("type")%>';
	var exGR = '<%=request.getParameter("exGR")%>';
	var orgName = '<%=request.getParameter("orgName")%>';
	var surveyName = '<%=request.getParameter("surveyName")%>';
	var surveyID = '<%=request.getParameter("surveyID")%>';
    var divID = '<%=request.getParameter("divID")%>';
    var deptID = '<%=request.getParameter("deptID")%>';
    var groupID = '<%=request.getParameter("groupID")%>';
	
	form.action="SubgroupReport.jsp?preview=" + grpName + "&orgName=" + orgName + "&surveyName=" + surveyName + "&surveyID=" + surveyID + "&divID=" + divID + "&deptID=" + deptID + "&groupID=" + groupID + "&type=" + type + "&exGR=" + exGR;
	form.method="post";
	form.submit();
} 

//This function saves the checkbox users into a cookie
function saveUsers(form){
	if(!check(form.chkUser)) {
		eraseCookie("users");
	} else {
		var CurrentCookie = 0; 
		for (i = 0; i < (form.chkUser).length; i++) {
			if((form.chkUser)[i].checked) {	
				//Store users in cookies
				CurrentCookie += "," + (form.chkUser)[i].value ;
			}
		}	
		createCookie("users",CurrentCookie,"1");	
	}
	alert("Users saved");
}

//This Function Creates your Cookie for you just pass in the Cookie Name, Value, and number of days before you want it to expire.
function createCookie(name,value,days)
{
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

//This Function reads the value of a given cookie for you.  Just pass in the cookie name and it will return the value.
function readCookie(name)
{
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') 
			c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) 
			return c.substring(nameEQ.length,c.length);
	}
	return null;
}

//This Function removes the cookie
function eraseCookie(name) {
	createCookie(name,"",-1);
}

//Runs on body load to check history of checkboxes on the page.
function checkCookies()
{
	var CurrentCookie = readCookie("users");
	for (i=0; i<document.SubgroupReport.elements.length; i++) {
		if (document.SubgroupReport.elements[i].type == "checkbox") {
            if (CurrentCookie && CurrentCookie.indexOf(document.SubgroupReport.elements[i].value) > -1) {
				document.SubgroupReport.elements[i].checked = true;
			}
		}
	}
}

window.onload=checkCookies();

</script>

</head>

<body>

<%
int OrgID = logchk.getOrg();
int nameSeq = User.NameSequence(OrgID);
int pkUser = logchk.getPKUser();

if (request.getParameter("preview") != null) {

	String [] chkSelect = request.getParameterValues("chkUser");
	
	String grpName = request.getParameter("preview");
	int type = Integer.parseInt(request.getParameter("type"));
	int exGR = Integer.parseInt(request.getParameter("exGR"));
	int surveyID = Integer.parseInt(request.getParameter("surveyID"));
    int divID = Integer.parseInt(request.getParameter("divID"));
    int deptID = Integer.parseInt(request.getParameter("deptID"));
    int groupID = Integer.parseInt(request.getParameter("groupID"));
    Vector DepartmentList = new Vector();
    Vector groupList = new Vector();
    		
	SurveyResult s = new SurveyResult();
	Vector Target = new Vector();
	Date timeStamp = new java.util.Date();
	SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
	String temp  =  dFormat.format(timeStamp);
	String file_name = "Group Report("+grpName+")" + temp + ".xls";
	String temp1 = "";
	
		Vector<Integer> deptIDList = new Vector<Integer>();
		Vector<Integer> groupIDList = new Vector<Integer>();
		
		if(divID==0){ //division is all, deptID might be pointing more than one
			if(deptID==0){ //department is all, group ID might be pointing more than one
				deptIDList.add(0);
				if(groupID==0){
					groupIDList.add(0);
				} else{
					String currentGroupName = "";
					groupList = QR.getGroup(QR.getSurveyID(),divID,deptID);
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
				}//end if groupID==0
			} else { //particular department, might point more than one department actually
	  			String currentDepartmentName= "";
	  			DepartmentList = QR.getDepartment(QR.getSurveyID(),divID);
	  			//get the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getPKDepartment() == deptID) currentDepartmentName=voDept.getDepartmentName();
	  			}
	  			//get all departmentIDs with the same name as the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getDepartmentName().equals(currentDepartmentName)) deptIDList.add(voDept.getPKDepartment());
	  			}
	  			if(groupID==0){
	  				//get the groupIDs from all the groups
					for(int i = 0; i < deptIDList.size(); i++){
					   for( Object o : QR.getGroup(QR.getSurveyID(), divID, deptIDList.elementAt(i))) groupList.add(o);
					}
					for(int i = 0; i < groupList.size(); i++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						groupIDList.add(voGrp.getPKGroup());
					}
	  			} else{
	  				//get the groupIDs from all the groups
					for(int i = 0; i < deptIDList.size(); i++){
					   for( Object o : QR.getGroup(QR.getSurveyID(), divID, deptIDList.elementAt(i))) groupList.add(o);
					}
					String currentGroupName = "";
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
	  			}//end if groupID==0
			}//end if deptID==0
		} else{ //divison is particular ID
			if(deptID==0){
				deptIDList.add(0);
				if(groupID==0){
					groupIDList.add(0);
				} else{
					String currentGroupName = "";
					groupList = QR.getGroup(QR.getSurveyID(),divID,deptID);
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
				}
			} else{
				deptIDList.add(deptID);
				groupIDList.add(groupID);
			}
		}
	
	//automatically calculate if not all the raters have been calculated
	// when generating the report
	//Target = s.TargetID(surveyID, divID, deptID, groupID);
	
	for (int j = 0; j < chkSelect.length; j++) {
	
		int targetId = Integer.parseInt(chkSelect[j]);
		if (!s.checkCalculationStatusComplete(targetId, surveyID ,divID, deptIDList, groupIDList))
		{						
			 if (!s.isAllRaterRated(surveyID, groupIDList, targetId))                	 
                 s.CalculateStatus(targetId,surveyID, divID, deptIDList, groupIDList, 1);
             else
                 s.CalculateStatus(targetId,surveyID, divID, deptIDList, groupIDList, 0);						
		}
	}	
	//set the Exclude Group Ranking value 
	ExcelGroup.setExGroupRanking(exGR);
	//Set the selected users
	ExcelGroup.setSelectedUsers(chkSelect);
	ExcelGroup.Report(surveyID, groupIDList, deptIDList, divID, pkUser, file_name, type);
	//Reset selected users
	ExcelGroup.setSelectedUsers(null);
	//Open the file
	String output = Setting.getReport_Path() + "\\" + file_name;
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.reset();
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"GroupReport.xls\"");
		
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
%>

<form id="SubgroupReport" name="SubgroupReport" method="post" action="SubgroupReport.jsp">

<table cellpadding="0" cellspacing="0" border="0">
<tr>
    <td colspan="2" width="567">
    	<li><font style='font-family:Arial' size="2">Select the team members to be included in the sub-group.</font></li>
        <li><font style='font-family:Arial' size="2">Provide a name for the sub-group.</font></li>
        <li><font style='font-family:Arial' size="2">Click on Preview to view the sub-group report.</font></li>
    </td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td colspan="2"><font style='font-family:Arial' size="2">Organisation: <b><u><%=request.getParameter("orgName")%></u></b>&nbsp;&nbsp; Survey: <b><u><%=request.getParameter("surveyName")%></u></b></font></td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<tr>
	<td width="20%"><font style='font-family:Arial' size="2"> Sub-group name: </font></td>
    <td width="80%" align="left"><input type="text" id="grpName" name="grpName" ></td>
</tr>
<tr>
	<td colspan="2">&nbsp;</td>
</tr>
<tr>
	<td colspan="2">
    	<div style='width:600px; height:350px; overflow:auto;'>  
		<table id="tblUsers" name="tblUsers" border="1" bgcolor="#FFFFCC" bordercolor="#3399FF">
			<th bgcolor="navy" class="sorted-asc"><font style='font-family:Arial' size="2"><input type="checkbox" id="chkAllUsers" name="chkAllUsers"></font></th>
			<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u>Team Members</u></font></b></a></th>

        <%
        	int surveyID = Integer.parseInt(request.getParameter("surveyID"));
        	int divID = Integer.parseInt(request.getParameter("divID"));
        	int deptID = Integer.parseInt(request.getParameter("deptID"));
        	int groupID = Integer.parseInt(request.getParameter("groupID"));
        	Vector DepartmentList = new Vector();
    		Vector groupList = new Vector();
        	Vector<Integer> deptIDList = new Vector<Integer>();
			Vector<Integer> groupIDList = new Vector<Integer>();
		
		if(divID==0){ //division is all, deptID might be pointing more than one
			if(deptID==0){ //department is all, group ID might be pointing more than one
				deptIDList.add(0);
				if(groupID==0){
					groupIDList.add(0);
				} else{
					String currentGroupName = "";
					groupList = QR.getGroup(QR.getSurveyID(),divID,deptID);
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
				}//end if groupID==0
			} else { //particular department, might point more than one department actually
	  			String currentDepartmentName= "";
	  			DepartmentList = QR.getDepartment(QR.getSurveyID(),divID);
	  			//get the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getPKDepartment() == deptID) currentDepartmentName=voDept.getDepartmentName();
	  			}
	  			//get all departmentIDs with the same name as the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getDepartmentName().equals(currentDepartmentName)) deptIDList.add(voDept.getPKDepartment());
	  			}
	  			if(groupID==0){
	  				//get the groupIDs from all the groups
					for(int i = 0; i < deptIDList.size(); i++){
					   for( Object o : QR.getGroup(QR.getSurveyID(), divID, deptIDList.elementAt(i))) groupList.add(o);
					}
					for(int i = 0; i < groupList.size(); i++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						groupIDList.add(voGrp.getPKGroup());
					}
	  			} else{
	  				//get the groupIDs from all the groups
					for(int i = 0; i < deptIDList.size(); i++){
					   for( Object o : QR.getGroup(QR.getSurveyID(), divID, deptIDList.elementAt(i))) groupList.add(o);
					}
					String currentGroupName = "";
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
	  			}//end if groupID==0
			}//end if deptID==0
		} else{ //divison is particular ID
			if(deptID==0){
				deptIDList.add(0);
				if(groupID==0){
					groupIDList.add(0);
				} else{
					String currentGroupName = "";
					groupList = QR.getGroup(QR.getSurveyID(),divID,deptID);
					//get the name of the current selected group
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
					   	if(voGrp.getPKGroup() == groupID){
						   currentGroupName = voGrp.getGroupName();
						}
					} 
					//get the groupIDs of all the groups with the same name as the current seleceted one
					for(int i = 0; i < groupList.size(); i ++){
						voGroup voGrp = (voGroup) groupList.elementAt(i);
						if(voGrp.getGroupName().equals(currentGroupName)){
							groupIDList.add(voGrp.getPKGroup());
						}
					}
				}
			} else{
				deptIDList.add(deptID);
				groupIDList.add(groupID);
			}
		}

        	//Get all the targets for the survey
        	SurveyResult sr = new SurveyResult();
        	Vector Target = new Vector();
			Target = sr.TargetID(surveyID, divID, deptIDList, groupIDList);
			
			for (int j = 0; j < Target.size(); j++)
			{
				voUser vo = (voUser)Target.get(j);
				int targetID 		= vo.getTargetLoginID();
				String sGivenName 	= vo.getGivenName();
				String sFamilyName 	= vo.getFamilyName();
				//int iStatus			= vo.getStatus();
				String sFullName 	= sFamilyName + " " + sGivenName;
				
				if(nameSeq == 2)
					sFullName = sGivenName + " " + sFamilyName;
		%>	  
		          <tr class="row">
		            <td width="34" align="center"><input type="checkbox" id="chkUser" name="chkUser" value=<%=targetID%>></td>
		            <td width="514"><font style='font-family:Arial' size="2"><%=sFullName%></font></td>
		          </tr>
		<%
			}
        
        %>
		</table>
    </div>
	</td>
</tr>
</table>
<p></p>
<table border="0" width="55%" cellspacing="0" cellpadding="0">
<tr>
   	<td width="210"><input type="button" name="Save" value="Preview" onClick="confirmPreview(this.form)" ></td>
	<td><input type="button" value="Save Users" name="btnSaveUsers" onClick="saveUsers(this.form)"></td>
    <td><input type="button" value="Close" name="btnClose" onClick="closeWindow()"></td>
</tr>
</table>

</form>

</body>
</html>