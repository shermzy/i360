<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
				 CP_Classes.QuestionnaireReport,
				 CP_Classes.vo.*"%>  

<html>
<head>
<title>Target Questionnaire</title>
<style type="text/css">
<!--
.style1 {font-size: 10pt}
-->
</style>

<meta http-equiv="Content-Type" content="text/html">
<%@ page import = "java.sql.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>

<jsp:useBean id="QE" class="CP_Classes.QuestionnaireReport" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="User_Jenty" class="CP_Classes.User_Jenty" scope="session"/>     
<jsp:useBean id="ExcelQuestionnaire" class="CP_Classes.ExcelQuestionnaire" scope="session"/>     
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;

	function getID(form, ID, type)
	{
		var typeSelected = "";
		
		if(ID != 0) {
			switch(type) {
				case 1: typeSelected = "surveyID";
						  break;
				case 2: typeSelected = "groupID";
						  break;
				case 3: typeSelected = "targetID";
						  break;
				case 4: typeSelected = "raterID";
						  break;
			}
			var query = "QuestionnaireReport.jsp?" + typeSelected + "=" + ID;
			form.action = query;
			form.method = "post";
			form.submit();
		} else {
			return false;
		}
		return true;	
	}

	/*
	* Change(s) : Modified method confirmOpen to cater for the selection of output format
	* Reason(s) : To allow user to choose to download/preview a zip archive of questionnaires
	* Updated By: Sebastian
	* Updated On: 28 July 2010
	*/
	function confirmOpen(form, raterID)
	{
		//Modified code to check for the output option selected before submitting the form, Sebastian 28 July 2010
		var OutputOption = form.chkOutputOption;
		var output_val = "";
		
		for (var i=0; i < OutputOption.options.length; i++)
		{
   			if (OutputOption.options[i].selected == true)
      		{
     			output_val = OutputOption.options[i].value;
      		}
   		}
   		
   		//User select to output Single Target/Rater Questionnaire (single excel file)
		if (output_val == "1")
		{
			if(checkField(form)){
				if(raterID != 0) {
					form.action = "QuestionnaireReport.jsp?open=1";
					form.submit();
					return true;	
				}else {
					alert("<%=trans.tslt("Please select the options")%>");
					return false;
				}
			}
		}
		//User select to output All Targets Questionnaires by rater (zip file)
		else if (output_val == "2")
		{
			if(raterID != 0) {
				form.action = "QuestionnaireReport.jsp?raterTargetsOutput=1";
				form.submit();
				return true;	
			}else {
				alert("<%=trans.tslt("Please select the options")%>");
				return false;
			}
		}
	}
	
	//Added method setOutputOption to set the selection of the chkOutputOption dropdown input, Sebastian 28 July 2010
	function setOutputOption(chkObj, value) {
		for (var i=0; i < chkObj.options.length; i++)
		{
     		if (chkObj.options[i].value == value)
     		{
     			chkObj.options[i].selected = true;
     		}
   		}
	}
	
	//Added method outputSelected to display page appropriately when user selects output option, Sebastian 28 July 2010
	function outputSelected(form)
	{
		var groupName = form.groupID.options[form.groupID.selectedIndex].value; //get the selected value of the group name
		
		if (groupName != "0")
		{
			getID(form, groupName, 2);
		}
		else
		{
			var query = "QuestionnaireReport.jsp";
			form.action = query;
			form.method = "post";
			form.submit();
		}
	}
	
	//This function is created by james 16-Jun 2008 to make sure all field are selected.
	function checkField(form){
		var msg = "Please select following information : \n";
		var valid = true;
		if(form.surveyName.selectedIndex == 0 ){
			msg += " - Survey Name \n";
			valid = false;
		}
		if(form.targetName.selectedIndex == 0 ){
			msg += " - Target Name \n";
			valid = false;
		}
		if(form.raterName.selectedIndex == 0 ){
			msg += " - Rater Name \n";
			valid = false;
		}
		if(valid){
			return true;
		}else{
			alert(msg);
		}
	
	}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

	function proceed(form,field)
	{
		form.action="QuestionnaireReport.jsp?proceed="+field.value;
		form.method="post";
		form.submit();
	}
//Added by Albert 08/06/2012 to add filter by department and division
function populateDept(form,field1, field2)
{
	form.action="QuestionnaireReport.jsp?groupID=0&div="+field1.value + "&dept=0&surveyID=" + field2.value;
	form.method="post";
	form.submit();
}	

function populateGrp(form,field1, field2, field3)
{
	form.action="QuestionnaireReport.jsp?groupID=0&div="+field1.value + "&dept=" + field2.value + "&surveyID=" + field3.value;
	form.method="post";
	form.submit();
}
function populateTarget(form,field1, field2, field3, field4)
{
	form.action="QuestionnaireReport.jsp?targetID=0&groupID="+field4.value+"&div="+field1.value + "&dept=" + field2.value + "&surveyID=" + field3.value;
	form.method="post";
	form.submit();
}
function populateRater(form,field1, field2, field3, field4, field5)
{
	form.action="QuestionnaireReport.jsp?raterID=0&targetID="+field5.value+"&groupID="+field4.value+"&div="+field1.value + "&dept=" + field2.value + "&surveyID=" + field3.value;
	form.method="post";
	form.submit();
}
	
</script>

<p>
  <%
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);
 
	String username=(String)session.getAttribute("username");

	if (!logchk.isUsable(username)) 
  	{	%> 
   		<font size="2">
	   	<script>
			parent.location.href = "index.jsp";
		</script>
<%  } 
	else 
	{ 		
		// Changed 23/05/08 by Ha, set Survey ID, group ID, TargetID, raterID = 0 when clicking the show button (for changing organisation)	
		if(request.getParameter("proceed") != null)
		{ 
			int PKOrg = new Integer(request.getParameter("proceed")).intValue();
		 	logchk.setOrg(PKOrg);
		 	QE.setSurveyID(0);
		 	QE.setDepartmentID(0);
		 	QE.setDivisionID(0);
		 	QE.setGroupID(0);
		 	QE.setTargetID(0);
		 	QE.setRaterID(0);
		}

		/*-------------------------------------------end login modification 1--------------------------------------*/
	
		int OrgID = logchk.getOrg();
		int compID = logchk.getCompany();
		int pkUser = logchk.getPKUser();
		int userType = logchk.getUserType();	// 1= super admin
%>

<form name="QuestionnaireReport" method="post" action="QuestionnaireReport.jsp">
<table boQEr="0" width="454" cellspacing="0" cellpadding="0" border="0">
	<tr>
	  <td colspan="4" align="left"><b><font color="#000080" size="2"  face="Arial"><%=trans.tslt("Questionnaire Report")%> </font></b></td>
    </tr>
	<tr>
	  <td colspan="4" align="right">&nbsp;</td>
    </tr>
</table>

<table boQEr="0" width="462" cellspacing="0" cellpadding="0" border="2" bordercolor="#3399FF" style='font-size:10.0pt;font-family:Arial'>
	<tr>
		<td width="130" align="right" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="28" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="142" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="155" align="center" bordercolor="#FFFFFF">&nbsp;</td>
	</tr>
	<tr>
		<td width="130" align="right" bordercolor="#FFFFFF"><font face="Arial" style="font-size: 11pt"><span class="style1"><%=trans.tslt("Organisation")%></span> 
		:</font></td>
		<td width="28" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="142" bordercolor="#FFFFFF">
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){ %>
		<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
		<option value="0" selected><%=trans.tslt("All")%></option>
<%
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
			<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
			<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
<% } // End of isConsulting %>
</select></td>
		<td width="155" align="center" bordercolor="#FFFFFF">&nbsp;</td>
	</tr>
	<tr>
		<td width="130" bordercolor="#FFFFFF">&nbsp;</td>
		<td width="28" bordercolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF">&nbsp;</td>

	</tr>
</table>

<%

	int nameSeq = User_Jenty.NameSequence(OrgID);

	Vector surveyList = QE.getSurvey(compID, OrgID);
	Vector DivisionList = null;
	Vector DepartmentList = null;
	Vector groupList  = null;
	Vector targetList = null;
	Vector raterList  = null;
	
	int surveyID = QE.getSurveyID();
	int divID = QE.getDivisionID();
	int deptID = QE.getDepartmentID();
	int groupID  = QE.getGroupID();	
	int target   = QE.getTargetID();
	int rater 	 = QE.getRaterID();
	
	if(surveyID != 0) {				
		DivisionList = QE.getDivision(surveyID);
		DepartmentList = QE.getDepartment(surveyID, QE.getDivisionID());
		groupList = QE.getGroup(surveyID, QE.getDivisionID(), QE.getDepartmentID());
	}
	/*
	if(groupID != 0)
		targetList = QE.getTarget(surveyID, groupID);
	
	if(target != 0)
		raterList = QE.getRater(surveyID, groupID, target);
	*/
	
	if(request.getParameter("open") != null) {
		surveyID = QE.getSurveyID();
		target = QE.getTargetID();
		//Changed by Ha 23/05/08: process only if the raterName !=null
		String raterName = request.getParameter("raterName");
		if (raterName !=null)
		{
			rater = Integer.parseInt(request.getParameter("raterName"));	
			QE.setRaterID(rater);
			
			Date timeStamp = new java.util.Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			String temp = dFormat.format(timeStamp);
			
			String file_name = "QuestionnaireReport" + temp + ".xls";

			ExcelQuestionnaire.QuestionnaireReport(surveyID, target, rater, pkUser, file_name);

			String output = Setting.getReport_Path() + "\\" + file_name;
			File f = new File (output);
		
			//set the content type(can be excel/word/powerpoint etc..)
			response.reset();
			response.setContentType ("application/xls");
			//set the header and also the Name by which user will be prompted to save
			response.addHeader ("Content-Disposition", "attachment;filename=\"Questionnaire.xls\"");
				
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
	}
	
	/*
	* Change(s) : Added codes to enable generation of zip archive contatining list of rater questionnaires and downloading of the archive
	* Reason(s) : To enable functionality to download zip archive of questionnaires by raters
	* Updated By: Sebastian
	* Updated On: 28 July 2010
	*/
	//Added codes to enable generation of zip archive of questionnaires for download, Sebastian 28 July 2010
	if(request.getParameter("raterTargetsOutput") != null) {
		surveyID = QE.getSurveyID();
		target = QE.getTargetID();
		if (request.getParameter("raterName") != null)
		{
			int raterID = Integer.parseInt(request.getParameter("raterName"));
			
			// Updated method call to newly renamed method generateQuestionnairesByRaterZipped(), Desmond 10 August 2010
			String zipFile = QE.generateQuestionnairesByRaterZipped(surveyID, raterID, pkUser);
			
			//Zip archive is created successfully
			if (!zipFile.equals(""))
			{
				//Prompt download of the zip file created
				File f = new File (zipFile);
		
				//set the content type(can be excel/word/powerpoint etc..)
				response.reset();
				response.setContentType ("application/zip");
				//set the header and also the Name by which user will be prompted to save
				response.addHeader ("Content-Disposition", "attachment;filename=\"Questionnaire.zip\"");
			
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
			else
			{
				%>
					<script>
						alert("Unable to create zip archive.");
					</script>
				<%
			}
	   	}
	}
/*
 * Modified and Changed by Albert 12/06/2012 to add filter based on division and department. Also to fix bug where two different groups with same name appears twice
 */		
	if(request.getParameter("surveyID") != null) {
		int id = Integer.parseInt(request.getParameter("surveyID"));
		QE.setSurveyID(id);
		QE.setDivisionID(0);
		QE.setDepartmentID(0);
		QE.setGroupID(0);
		QE.setRaterID(0);
		QE.setTargetID(0);	
		QE.setPageLoad(1);		
		DivisionList = QE.getDivision(id);
		DepartmentList = QE.getDepartment(id, QE.getDivisionID());
		groupList = QE.getGroup(id, QE.getDivisionID(), QE.getDepartmentID());
	}
	if (request.getParameter("div")!=null){				
	    int id = Integer.parseInt(request.getParameter("div"));
		QE.setDivisionID(id);
		QE.setDepartmentID(0);
		QE.setGroupID(0);
		QE.setRaterID(0);
		QE.setTargetID(0);	
		QE.setPageLoad(1);	
		
		DivisionList = QE.getDivision(QE.getSurveyID());
		DepartmentList = QE.getDepartment(QE.getSurveyID(), QE.getDivisionID());
		groupList = QE.getGroup(QE.getSurveyID(), QE.getDivisionID(), QE.getDepartmentID());
	}
	if (request.getParameter("dept")!=null){		
		int id = Integer.parseInt(request.getParameter("dept"));
		QE.setDepartmentID(id);
		QE.setGroupID(0);
		QE.setRaterID(0);
		QE.setTargetID(0);	
		QE.setPageLoad(1);	
		DivisionList = QE.getDivision(QE.getSurveyID());
		DepartmentList = QE.getDepartment(QE.getSurveyID(),  QE.getDivisionID());
		groupList = QE.getGroup(QE.getSurveyID(), QE.getDivisionID(), QE.getDepartmentID());
	}
	if (request.getParameter("groupID")!=null){	
		int id = Integer.parseInt(request.getParameter("groupID"));	
		QE.setGroupID(id);
		QE.setRaterID(0);
		QE.setTargetID(0);	
		QE.setPageLoad(1);	
		DivisionList = QE.getDivision(QE.getSurveyID());
		DepartmentList = QE.getDepartment(QE.getSurveyID(),  QE.getDivisionID());
		groupList = QE.getGroup(QE.getSurveyID(),  QE.getDivisionID(), QE.getDepartmentID());
		//Add IF condition to determine if user select to output all targets questionnaires by rater, Sebastian 28 July 2010
		if (request.getParameter("chkOutputOption").equals("2"))
		{
			//Retrieve Raterlist instead of targetList if user select to output all targets questionnaires by rater
			raterList = QE.getRater(QE.getSurveyID(), QE.getDivisionID(), QE.getDepartmentID(), QE.getGroupID());
		}
		else
		{
			targetList = QE.getTarget(QE.getSurveyID(), QE.getDivisionID(), QE.getDepartmentID(), QE.getGroupID());
		}
	}
	
	if(request.getParameter("targetID") != null) {
		int ID = QE.getSurveyID();
		int targetID = Integer.parseInt(request.getParameter("targetID"));
		QE.setTargetID(targetID);
		QE.setRaterID(0);
		raterList = QE.getRaterTar(surveyID, QE.getDivisionID(), QE.getDepartmentID(), QE.getGroupID(), 0, targetID, 0);
	}
	
	if(request.getParameter("raterID") != null) {
		String raterID = request.getParameter("raterID");
		QE.setRaterID(Integer.parseInt(raterID));
	}

/*
 *  End of changes by Albert
 */
%>

  <table width="461" boQEr="0" style='font-size:10.0pt;font-family:Arial' bgcolor="#FFFFCC" border="2" bordercolor="#3399FF">
    <tr>
      <td width="122" align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td width="16" bordercolor="#FFFFCC">&nbsp;</td>
	  <td width="197" bordercolor="#FFFFCC">&nbsp;
	  </td>
    </tr>
    <tr>
      <td width="122" align="right" bordercolor="#FFFFCC"><%=trans.tslt("Survey")%> 
		: </td>
      <td width="16" bordercolor="#FFFFCC">&nbsp;</td>
      <% int t = 0; %>
	  <td width="197" bordercolor="#FFFFCC">
	  <select name="surveyName" onchange = "getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 1)">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <% 
	  /*********************
	   * Edit By James 14-Nov 2007
	   ***********************/
	
	 // while(surveyList.next()) {
	  for(int i=0;i<surveyList.size();i++){
	  votblSurvey vo=(votblSurvey)surveyList.elementAt(i);  
	  		int ID = vo.getSurveyID();
			String name = vo.getSurveyName();
			int selectedSurvey = QE.getSurveyID();
			
			if(selectedSurvey != 0 && ID == selectedSurvey) {
	  %>
	  	<option value = <%=selectedSurvey%> selected><%=name%>
	  <% } else {  %>
	  	<option value = <%=ID%>><%=name%>	  
	  <% }
		   } 
	  %>
      </select></td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
<!--
 * Modified and Changed by Albert 12/06/2012 to add filter based on division and department. Also to fix bug where two different groups with same name appears twice
-->    
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Division")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC"><select name="division" onChange="populateDept(this.form, this.form.division, this.form.surveyName, this.form.JobPost)">
          <option value=<%=t%>><%=trans.tslt("All")%>
          <% 	
		   if(DivisionList != null) {
				for(int k=0;k<DivisionList.size();k++){
					voDivision voD=(voDivision)DivisionList.elementAt(k);
					int ID = voD.getPKDivision();
					String name = voD.getDivisionName();
					divID = QE.getDivisionID();
					
		  			if(divID != 0 && ID == divID){
	  %>			<option value = <%=ID%> selected><%=name%>
      <% 			} else{ 
	  %>    		<option value = <%=ID%>><%=name%>
        <%  	  
		 			}
				} 
			}%>
          </select></td>
    </tr>
    
     <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Department")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC"><select name="department" onChange="populateGrp(this.form, this.form.division, this.form.department, this.form.surveyName, this.form.JobPost)"> 
          <option value=<%=t%>><%=trans.tslt("All")%>
        <% 	DepartmentList = QE.getDepartment(QE.getSurveyID(), QE.getDivisionID());
        	//delete duplicate entries with same department name but different IDs
        	Vector<voDepartment> uniqueDeptList = new Vector<voDepartment>();
			for(int i=0; i<DepartmentList.size(); i++){
				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
				Boolean duplicated = false;
				for(voDepartment vo : uniqueDeptList){
					if(voDept.getDepartmentName().equals(vo.getDepartmentName())){
						duplicated = true;
					}
				}
				if(!duplicated){
					uniqueDeptList.add(voDept);
				}
			}	
        	if(uniqueDeptList != null) {
				for(int l=0;l<uniqueDeptList.size();l++){
					voDepartment voDep=(voDepartment)uniqueDeptList.elementAt(l);
					int ID = voDep.getPKDepartment();
					String name =voDep.getDepartmentName();						
					deptID = QE.getDepartmentID();		
					if(deptID != 0 && ID == deptID) {
	  %>				<option value = <%=ID%> selected><%=name%>
       <% 			} else{ 
       %>  				<option value = <%=ID%>><%=name%>
        <% 	
		  			}
				} 
			}%>
          </select></td>
    </tr>
    
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Group")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="groupID" onChange="populateTarget(this.form, this.form.division, this.form.department, this.form.surveyName, this.form.groupID)">
	  <option value=<%=t%>><%=trans.tslt("All")%>
	  <%
	  		surveyID = QE.getSurveyID();
	  		divID = QE.getDivisionID();
	  		deptID = QE.getDepartmentID();
	  		groupID = QE.getGroupID();
	  			  		
	  		if(deptID==0){// deptID="All" is selected
	  			groupList = QE.getGroup(surveyID,divID,deptID);
	  		} else{// particular deptID
	  			Vector sameNameDepartment = new Vector();
	  			String currentDepartmentName= "";
	  			//get the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getPKDepartment() == QE.getDepartmentID()) currentDepartmentName=voDept.getDepartmentName();
	  			}
	  			//get all departmentIDs with the same name as the current department name
	  			for(int i=0; i<DepartmentList.size(); i++){
	  				voDepartment voDept = (voDepartment) DepartmentList.elementAt(i);
	  				if(voDept.getDepartmentName().equals(currentDepartmentName)) sameNameDepartment.add(voDept);
	  			}
	  			//get the groups from different departments
	  			groupList = new Vector();
	  			for(int i=0; i<sameNameDepartment.size(); i++){
	  				voDepartment voDept = (voDepartment) sameNameDepartment.elementAt(i);
	  				for(Object o: QE.getGroup(surveyID,divID,voDept.getPKDepartment())) groupList.add(o);
	  			}
	  		}
	  		//delete duplicate entries with same group name but different IDs
	  		Vector<voGroup> uniqueGroupList = new Vector<voGroup>();
			for(int i=0; i<groupList.size(); i++){
				voGroup voGrp = (voGroup) groupList.elementAt(i);
				Boolean duplicated = false;
				for(voGroup vo : uniqueGroupList){
					if(voGrp.getGroupName().equals(vo.getGroupName())){
						duplicated = true;
					}
				}
				if(!duplicated){
					uniqueGroupList.add(voGrp);
				}
			}
	   		if(uniqueGroupList != null) {
				for(int l=0;l<uniqueGroupList.size();l++){
					voGroup voGrp=(voGroup) uniqueGroupList.elementAt(l);
					int ID = voGrp.getPKGroup();
					String name =voGrp.getGroupName();						
					groupID = QE.getGroupID();		
					if(groupID != 0 && ID == groupID) {
	  %>				<option value = <%=ID%> selected><%=name%>
       <% 			} else{ 
       %>  				<option value = <%=ID%>><%=name%>
        <% 	
		  			}
				} 
			}%>
      </select></td>
    </tr>
    
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <%//Added a dropdown box chkOutputOption to allow user to make a choice of outputing questionnaires by single target/rater or list of targets by rater, Sebastian 28 July 2010%>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Output")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">
      <select name="chkOutputOption" onchange = "outputSelected(this.form)">
	  	<option value="1">Single Excel File</option>
	  	<option value="2">Zip Archive</option>
      </select></td>
    </tr>
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <%
    //Add the condition if user select to output targets questionnaires by rater, don't display the Target Dropdown Box, Sebastian 28 July 2010
    if (request.getParameter("chkOutputOption") != null && request.getParameter("chkOutputOption").equals("2")){
    	//Don't Display the Target Dropdown Box
    }
    else{
    	//Display the Target Dropdown Box
    %>
    <tr>
      <td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Target")%> : </td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC"><select name="targetName" onChange="populateRater(this.form, this.form.division, this.form.department, this.form.surveyName, this.form.groupID, this.form.targetName)">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <%
	  		surveyID = QE.getSurveyID();
	  		divID = QE.getDivisionID();
	  		deptID = QE.getDepartmentID();
	  		groupID = QE.getGroupID();
	  		target = QE.getTargetID();
				  			  		
	  		if(groupID==0){// groupID="All" is selected
	  			targetList = QE.getTarget(surveyID,divID,deptID,groupID);
	  		} else{// particular groupID
	  			Vector sameNameGroup = new Vector();
	  			String currentGroupName= "";
	  			//get the current group name
	  			for(int i=0; i<groupList.size(); i++){
	  				voGroup voGrp = (voGroup) groupList.elementAt(i);
	  				if(voGrp.getPKGroup() == QE.getGroupID()) currentGroupName=voGrp.getGroupName();
	  			}
	  			//get all groupIDs with the same name as the current group name
	  			for(int i=0; i<groupList.size(); i++){
	  				voGroup voGrp = (voGroup) groupList.elementAt(i);
	  				if(voGrp.getGroupName().equals(currentGroupName)) sameNameGroup.add(voGrp);
	  			}
	  			//get the targets from different groups
	  			targetList = new Vector();
	  			for(int i=0; i<sameNameGroup.size(); i++){
	  				voGroup voGrp = (voGroup) sameNameGroup.elementAt(i);
	  				for(Object o: QE.getTarget(surveyID,divID,deptID,voGrp.getPKGroup())) targetList.add(o);
	  			}
	  		}
	   		if(targetList != null) {
				for(int l=0;l<targetList.size();l++){
					votblAssignment voT = (votblAssignment) targetList.elementAt(l);
					int loginID = voT.getTargetLoginID();
					String name = voT.getFullName();						
					int selectedTarget = QE.getTargetID();		
					if(loginID != 0 && loginID == selectedTarget) {
	  %>				<option value = <%=loginID%> selected><%=name%>
       <% 			} else{ 
       %>  				<option value = <%=loginID%>><%=name%>
        <% 	
		  			}
				} 
			}%>
      </select></td>
    </tr>
    
    <tr>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    
    <%} //end else(display target drop down box)
    %>
    
    <tr>
      <td align="right" bordercolor="#FFFFCC">
   
    	<%=trans.tslt("Rater")%> :</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC">
   
    	<select name="raterName">
	  <option value=<%=t%>><%=trans.tslt("Please select one")%>
	  <%
	surveyID = QE.getSurveyID();
	divID = QE.getDivisionID();
	deptID = QE.getDepartmentID();
	groupID = QE.getGroupID();
	target = QE.getTargetID(); 
	Vector sameNameGroup = new Vector();
	if(groupID!=0){// particular groupID
	  	String currentGroupName= "";
	  	//get the current group name
	  	for(int i=0; i<groupList.size(); i++){
	  		voGroup voGrp = (voGroup) groupList.elementAt(i);
	  		if(voGrp.getPKGroup() == QE.getGroupID()) currentGroupName=voGrp.getGroupName();
	  	}
	  	//get all groupIDs with the same name as the current group name
	  	for(int i=0; i<groupList.size(); i++){
	  		voGroup voGrp = (voGroup) groupList.elementAt(i);
	  		if(voGrp.getGroupName().equals(currentGroupName)){
	  		 	sameNameGroup.add(voGrp);
	  		}
	  	}
	}
	if (request.getParameter("chkOutputOption") != null && request.getParameter("chkOutputOption").equals("2")){
    	//rater list is independent from target since target is not considered
    	if(groupID==0) raterList = QE.getRater(surveyID,divID,deptID,groupID);
    	else{
    		//get the targets from different groups
	  		raterList = new Vector();
	  		for(int i=0; i<sameNameGroup.size(); i++){
	  			voGroup voGrp = (voGroup) sameNameGroup.elementAt(i);
	  			for(Object o: QE.getRater(surveyID,divID,deptID,voGrp.getPKGroup())) raterList.add(o);
	  		}
    	}
    }
    else{
    	//rater list is dependent from target since target is considered
    	if(groupID==0) raterList = QE.getRaterTar(surveyID,divID,deptID,groupID, 0, QE.getTargetID(), 0);
    	else{
    		//get the targets from different groups
	  		raterList = new Vector();
	  		for(int i=0; i<sameNameGroup.size(); i++){
	  			voGroup voGrp = (voGroup) sameNameGroup.elementAt(i);
	  			for(Object o: QE.getRaterTar(surveyID,divID,deptID,voGrp.getPKGroup(), 0, QE.getTargetID(), 0)){
	  			 raterList.add(o);
	  			 }
	  		}
    	}
    }
/*
 *  End of changes by Albert
 */    	
	if(raterList!=null) {
	  for(int l=0;l<raterList.size();l++){
	  	votblAssignment voR=(votblAssignment)raterList.elementAt(l);
		int loginID = voR.getRaterLoginID();
		String name = voR.getFullName();
		int selectedTarget = QE.getRaterID();
		if(loginID == selectedTarget) {
	  %>
	  <option value = <%=loginID%> selected><%=name%>	
	 <% } else { %>
	  	<option value = <%=loginID%>><%=name%>	  
		<% } 
	  }//end for loop
	}%>
      </select></td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <%
    	//Added codes to persist the state of the input chkOutputOption, to allow the output option selected to retain when the page loads, Sebastian 28 July 2010
    	String OutputOptionValue = request.getParameter("chkOutputOption");
    	
    	if (OutputOptionValue != null)
    	{
    	%>
    		<script>setOutputOption(document.QuestionnaireReport.chkOutputOption,<%=OutputOptionValue%>);</script>
    	<%
    	}
    %>
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" bordercolor="#FFFFCC">&nbsp;</td>
      <td bordercolor="#FFFFCC">&nbsp;</td>
	  <td bordercolor="#FFFFCC">&nbsp; 
   	  </td>
    	<td bordercolor="#FFFFCC">
<%  if(compID != 2 || userType == 1) {
%>
	   	<input type="button" style="float: center" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value)">
<%
	} else {
%>
	   	<input type="button" style="float: center" name="btnOpen" value="<%=trans.tslt("Preview")%>" onclick = "return confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value)" disabled>
<%
	} %>
</td>
    </tr>
  </table>
  
  <p></p>

<%  } %>
</form>
<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>