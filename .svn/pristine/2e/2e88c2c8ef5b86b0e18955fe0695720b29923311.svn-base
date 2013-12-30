<%@ page
	import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>

<html>
<head>
<title>Questionnaire Data Entry</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
<jsp:useBean id="RDE" class="CP_Classes.RatersDataEntry" scope="session" />
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
<jsp:useBean id="User_Jenty" class="CP_Classes.User_Jenty" scope="session" />
<jsp:useBean id="ExportExcel" class="CP_Classes.ExportQuestionnaire" scope="session" />
<jsp:useBean id="DemographicEntry" class="CP_Classes.DemographicEntry" scope="session" />
<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session" />
<jsp:useBean id="S" class="CP_Classes.SurveyResult" scope="session" />
<jsp:useBean id="Q" class="CP_Classes.Questionnaire" scope="session" />
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurvey"%>
<%@ page import="CP_Classes.vo.voGroup"%>
<%@ page import="CP_Classes.vo.voUser"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<script language="javascript">
var x = parseInt(window.screen.width) / 2 - 250;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
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
		var query = "RatersDataEntry.jsp?" + typeSelected + "=" + ID;
		form.action = query;
		form.method = "post";
		form.submit();
	} else {
		alert("<%=trans.tslt("Please select Survey")%>");
		return false;
	}
	return true;	
}

function confirmOpen(form, raterID, type, field)
{
	//type 1 = open, 2=import, 3=export
	var isChecked = 0;
	
	if(field.checked) {
		isChecked = 1;
	}
	
	if(raterID != 0) {
		
		if(type == 1 || type == 3) {			
			form.action = "RatersDataEntry.jsp?type=" + type + "&open=" + isChecked;
			form.method = "post";
			form.submit();
		}else if(type == 2){
			form.action = "RatersDataEntry.jsp?import=1";
			form.method = "post";
			form.submit();
		}
		
		return true;	
	}else {
		alert("<%=trans.tslt("Please select the options")%>");
		return false;
	}
}

/*------------------------------------------------------------start: Login modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="RatersDataEntry.jsp?proceed="+field.value;
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
  {%> <font size="2"> <script>
	parent.location.href = "index.jsp";
</script> <%  } 
  else 
  { 	

if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
 	RDE.setSurveyID(0);
	RDE.setGroupID(0);
	RDE.setRaterID(0);
	RDE.setTargetID(0);	
 	
}

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

%>

<form name="RatersDataEntry" method="post" action="">
<table border="0" width="593" cellspacing="0" cellpadding="0"
	style='font-size: 10.0pt; font-family: Arial'>
	<tr>
		<td colspan="4" align="left" bordercolor="#FFFFFF"><b><font
			color="#000080" size="2" face="Arial"><%=trans.tslt("Rater's Data Entry")%>
		</font></b></td>
	</tr>
	<tr>
		<td colspan="4" align="left" bordercolor="#FFFFFF">
		<ul>
			<li><font face="Arial" size="2"><%=trans.tslt("Select the survey, group, target, and rater's name")%>.</font></li>
			<li><font face="Arial" size="2"><%=trans.tslt("Click on Open Questionnaire to open the questionnaire on screen")%>.</font></li>
			<li><font face="Arial" size="2"><%=trans.tslt("To Export Questionnaire in excel format, click on Export")%>.</font></li>
			<li><font face="Arial" size="2"><%=trans.tslt("To Import Questionnaire, click on Import, then browse for the excel file located in the computer")%>.</font></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="left" bordercolor="#FFFFFF">&nbsp;</td>
	</tr>
</table>
<table border="2" width="511" cellspacing="0" cellpadding="0"
	style='font-size: 10.0pt; font-family: Arial' bgcolor="#FFFFCC"
	bordercolor="#3399FF">
	<tr>
		<td width="99" align="right" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="16" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="222" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="162" align="left" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
	</tr>
	<tr>
		<td width="99" align="right" bordercolor="#FFFFFF" bgcolor="#FFFFFF"><%=trans.tslt("Organisation")%>
		:</td>
		<td width="16" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="222" bordercolor="#FFFFFF" bgcolor="#FFFFFF"><select

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
		<td width="162" align="left" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
	</tr>
	<tr>
		<td width="99" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="16" bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
		<td bordercolor="#FFFFFF" bgcolor="#FFFFFF">&nbsp;</td>
	</tr>

	</tr>
</table>

<%	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int nameSeq = User_Jenty.NameSequence(OrgID);
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();
	
	Vector surveyList = RDE.getSurvey(compID, OrgID);
	Vector groupList  = null;
	Vector targetList = null;
	Vector raterList  = null;
		
	int surveyID = RDE.getSurveyID();
	int groupID  = RDE.getGroupID();	
	int target   = RDE.getTargetID();	
	int assignmentID = Q.AssignmentID(surveyID, target, RDE.getRaterID());
	
	if(surveyID != 0)
		groupList = RDE.getGroup(surveyID);
	
	if(groupID != 0)
	{
		if(nameSeq == 0) {
			//System.out.println("Target Family");
			targetList = RDE.getTarget(surveyID, groupID, nameSeq);
		} else {
			//System.out.println("Target Given");
			targetList = RDE.getTarget(surveyID, groupID, nameSeq);
		}
	}
	
	if(target != 0)
	{
		if(nameSeq == 0) {
			//System.out.println("Rater Family");
			raterList = RDE.getRater(surveyID, groupID, target, nameSeq);
		} else {
			//System.out.println("Rater Given");
			raterList = RDE.getRater(surveyID, groupID, target, nameSeq);
		}
	}
	
	
	if(request.getParameter("import") != null) {
		int raterID = Integer.parseInt(request.getParameter("raterName"));
		RDE.setRaterID(raterID);
		assignmentID = Q.AssignmentID(surveyID, target, raterID);
		
		int ratingExist = ExportExcel.checkRatingExist(assignmentID);
			if(ratingExist == 1) {		%> <script>
				if(confirm("<%=trans.tslt("Rating already exist, do you want to overwrite the existing rating input")%>?")) 
					window.location.href = "RatersDataEntry.jsp?type=2&overwrite=1";
				else
					window.location.href = "RatersDataEntry.jsp?type=2&overwrite=0";
				</script> <%				
			} 
			else {				%> <script>
					window.location.href = "RatersDataEntry.jsp?type=2&overwrite=1";
				</script> <%	
		}
	}
	else if(request.getParameter("finish") != null) {		//calculate
		if(Integer.parseInt(request.getParameter("finish")) == 1) {
			//By Hemilda date 10/08/2008 not automatically calculate when importing questionnaire
			//S.Calculate(assignmentID, 0);		// calculation part, 0=not include/exclude rater
			Q.SetRaterStatus(assignmentID, 1);
		
			if(userType == 1 || userType == 2) {
%> <script>
					alert("<%=trans.tslt("Rating completed. Please proceed to process result if you wish view the rater''s input imported")%>.");
					window.location.href = "RatersDataEntry.jsp";
				</script> <%		
			}
			else {
%> <script>
					alert("<%=trans.tslt("Rating completed")%>.");
					window.location.href = "RatersDataEntry.jsp";
				</script> <%		
			}
		}
	}
		
	if(request.getParameter("surveyID") != null) {
		int ID = Integer.parseInt(request.getParameter("surveyID"));
		RDE.setSurveyID(ID);
		RDE.setGroupID(0);
		RDE.setRaterID(0);
		RDE.setTargetID(0);	
		RDE.setPageLoad(1);	
		
		groupList = RDE.getGroup(ID);
	}
	else if(request.getParameter("groupID") != null) {
		int group = Integer.parseInt(request.getParameter("groupID"));
		RDE.setGroupID(group);
		RDE.setRaterID(0);
		RDE.setTargetID(0);
		targetList = RDE.getTarget(surveyID, group, nameSeq);
	} 
	else if(request.getParameter("targetID") != null) {
		int ID = RDE.getSurveyID();
		int targetID = Integer.parseInt(request.getParameter("targetID"));
		RDE.setTargetID(targetID);
		RDE.setRaterID(0);
		raterList = RDE.getRater(surveyID, groupID, targetID, nameSeq);
	}
	else if(request.getParameter("raterID") != null) {
		String raterID = request.getParameter("raterID");
		RDE.setRaterID(Integer.parseInt(raterID));
	}
	else if(request.getParameter("type") != null) {
	
		int type = Integer.parseInt(request.getParameter("type"));
		int raterID = RDE.getRaterID();	
		if(type == 1)  {
			
			int isOpen = Integer.parseInt(request.getParameter("open"));		
			raterID = Integer.parseInt(request.getParameter("raterName"));
			RDE.setRaterID(raterID);
			
			// If any demographic is chosen, forward to demo page. Otherwise, proceed into Questionnaire
			
			if( isOpen == 1) {
				if(DemographicEntry.bDemographicSelected(surveyID))
				{
%> <script>
					window.open('DemographicEntry.jsp','windowRef');
					
				</script> <%				}
				else
				{
%> <script>
					window.open('Questionnaire.jsp','windowRef');
				</script> <%				}			
			
			} else {
				if(DemographicEntry.bDemographicSelected(surveyID))
				{
%> <script>
					window.location.href = "DemographicEntry.jsp";
				</script> <%				}
				else
				{
%> <script>
					window.location.href = "Questionnaire.jsp";
				</script> <%				}
			}
		} else if(type == 2)  { //import
			// Changed by Ha 30/05/08 to pop out message import successfully/unsuccessfully accordingly
			if(request.getParameter("path") != null) {				
				String reportPath = Setting.getUploadPath() + "\\" + request.getParameter("path");				
				int overwrite = ExportExcel.getOverwrite();
// Added by DeZ, 16.07.08, To identify problem where Import Questionaires gives blank narrative comments even though data is available
//System.out.println("\n------------------------------------------------------------------------------------------------");
				boolean canImport = ExportExcel.Import(surveyID, target, RDE.getRaterID(), pkUser, reportPath, overwrite);
				if (canImport)
				{
					int completed = Q.checkRatingCompleted(assignmentID);
					
					if(completed == 1) {
						int commentIncluded = Q.commentIncluded(surveyID);
						
						int commentInput = 1;
						if(commentIncluded == 1)
							commentInput = Q.checkCommentInput(assignmentID);
						if(target == RDE.getRaterID())
							commentInput = Q.SelfCommentIncluded(surveyID);
				
						if(commentInput == 0) {	%> 
								<script>
								if(confirm("<%=trans.tslt("No comments included. Do you want to set the Questionnaire complete status to finish")%>?"))
									window.location.href = "RatersDataEntry.jsp?finish=1";
								else
								{
									alert("<%=trans.tslt("Import successful")%>")
									window.location.href = "RatersDataEntry.jsp";
								}
								</script> <%				
						} else {		%>
								 <script>
								if(confirm("<%=trans.tslt("Do you want to set the Questionnaire complete status to finish ")%>?")) {
									window.location.href = "RatersDataEntry.jsp?finish=1";
								}
								else 
								{
									alert("<%=trans.tslt("Import successful")%>")
									window.location.href = "RatersDataEntry.jsp";
								}
								</script> <%					
						}
						
					}
					
					if(userType == 1 || userType == 2) 
					{
	%>					 <script>
							alert("<%=trans.tslt("Rating updated. Please proceed to process result if you wish view the rater's input imported")%>.");
							window.location.href = "RatersDataEntry.jsp";
						</script> <%		
					}
					else 
					{
	%>					<script>
							alert("<%=trans.tslt("Rating updated")%>.");
							window.location.href = "RatersDataEntry.jsp";
						</script> <%		
					}
				}
				else
				{
					String errMsg = "Import unsuccessfully. Please check the format again";
					
					//Output error msg if the error is from the validation of input questionniare ratings, Sebastian 29 July 2010
					if (!ExportExcel.getValErrMsg().equals(""))
					{
						errMsg = ExportExcel.getValErrMsg();
						ExportExcel.setValErrMsg("");
					}
					%>
					 <script>
						alert("<%=trans.tslt(errMsg)%>.");
						window.location.href = "RatersDataEntry.jsp";
					</script> <%
				 }
				
				
			} else {
				
				int overwrite = Integer.parseInt(request.getParameter("overwrite"));
				ExportExcel.setOverwrite(overwrite);
%> <script>
					var myWindow=window.open('ImportQuestionnaire.jsp','windowRef','scrollbars=no, width=400, height=200');
					myWindow.moveTo(x,y);
					myWindow.location.href = 'ImportQuestionnaire.jsp';
				</script>
<%			
			}			
		} else if(type == 3){		// export		
					String raterName = request.getParameter("raterName");
				    // Changed by Ha 22/05/08 : downloading part
				    if (raterName != null) 
				    {		    
						try {raterID = Integer.parseInt(raterName);}
					    catch (Exception e) {
						System.out.println(e.getMessage());
					}		
					RDE.setRaterID(raterID);
					
					Date timeStamp = new java.util.Date();
					SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
					String temp  =  dFormat.format(timeStamp);
					
					String file_name = "Questionnaire" + temp + ".xls";
					ExportExcel.Export(surveyID, target, RDE.getRaterID(), pkUser, file_name);	
					
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
				} catch (IOException ioe) {ioe.printStackTrace(System.out);}
					outs.flush();
					outs.close();
					in.close();	
	       		    }
			}
		}
%>

<table width="511" border="2"
	style='font-size: 10.0pt; font-family: Arial' bgcolor="#FFFFCC"
	bordercolor="#3399FF" height="245">
	<tr>
		<td width="123" align="right" bordercolor="#FFFFCC">&nbsp;</td>
		<td width="6" bordercolor="#FFFFCC">&nbsp;</td>
		<td width="214" bordercolor="#FFFFCC">
		<div align="left"></div>
		</td>
	</tr>
	<tr>
		<td width="123" align="right" bordercolor="#FFFFCC"><%=trans.tslt("Survey")%>
		:</td>
		<td width="6" bordercolor="#FFFFCC">&nbsp;</td>
		<% int t = 0; %>
		<td colspan="2" width="214" bordercolor="#FFFFCC">
		<div align="left"><select name="surveyName"
			onChange="getID(this.form, this.form.surveyName.options[surveyName.selectedIndex].value, 1)">
			<option value=<%=t%>><%=trans.tslt("Please select one")%> <% for(int i=0; i<surveyList.size(); i++) {
				votblSurvey vo = (votblSurvey)surveyList.elementAt(i);
			
				int ID = vo.getSurveyID();
				String name = vo.getSurveyName();
				
				int selectedSurvey = RDE.getSurveyID();
			
			if(selectedSurvey != 0 && ID == selectedSurvey) {
	  %>
			
			<option value=<%=selectedSurvey%> selected><%=name%> <% } else {  %>
			
			<option value=<%=ID%>><%=name%> <% }
		   } 
	  %>
			
		</select></div>
		</td>
	</tr>
	<tr>
		<td height="19" bordercolor="#FFFFCC">&nbsp;</td>
		<td height="19" bordercolor="#FFFFCC">&nbsp;</td>
		<td height="19" bordercolor="#FFFFCC">
		<div align="left"></div>
		</td>
	</tr>
	<tr>
		<td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Group Name")%>
		:</td>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td colspan="2" bordercolor="#FFFFCC">
		<div align="left"><select name="groupName"
			onchange="getID(this.form, this.form.groupName.options[groupName.selectedIndex].value, 2)">
			<option value=<%=t%>><%=trans.tslt("Please select one")%> <% if(groupList != null) { 
		  	for(int j=0; j<groupList.size(); j++) {
				voGroup voG = (voGroup)groupList.elementAt(j);
	  			int ID = voG.getPKGroup();
				String name = voG.getGroupName();
				int selectedGroup = RDE.getGroupID();
				
				if(selectedGroup != 0 && ID == selectedGroup) {
	  %>
			
			<option value=<%=ID%> selected><%=name%> <% } else { %>
			
			<option value=<%=ID%>><%=name%> <% }
			} }%>
			
		</select></div>
		</td>
	</tr>
	<tr>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td bordercolor="#FFFFCC">
		<div align="left"></div>
		</td>
	</tr>
	<tr>
		<td align="right" bordercolor="#FFFFCC"><%=trans.tslt("Target's Name")%>
		:</td>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td colspan="2" bordercolor="#FFFFCC">
		<div align="left"><select name="targetName"
			onchange="getID(this.form, this.form.targetName.options[targetName.selectedIndex].value, 3)">
			<option value=<%=t%>><%=trans.tslt("Please select one")%> <% 	if(targetList != null) {
	  			for(int j=0; j<targetList.size(); j++) {
					voUser voUsr = (voUser)targetList.elementAt(j);
					
					int loginID = voUsr.getTargetLoginID();
					String givenName = voUsr.getGivenName();
					String familyName = voUsr.getFamilyName();
					String name="";
				
					//0=familyname first
					if(nameSeq == 0)
						name = familyName + " " + givenName;
					else
						name = givenName + " " + familyName;	
					
					int selectedTarget = RDE.getTargetID();
		
				if(loginID == selectedTarget) {
	  %>
			
			<option value=<%=loginID%> selected><%=name%> <% } else { %>
			
			<option value=<%=loginID%>><%=name%> <% }
			} 
			}%>
			
		</select></div>
		</td>
	</tr>
	<tr>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td bordercolor="#FFFFCC">
		<div align="left"></div>
		</td>
	</tr>
	<tr>
		<td align="right" bordercolor="#FFFFCC"><font size="2"> <%=trans.tslt("Rater's Name")%>
		: </td>
		<td bordercolor="#FFFFCC">&nbsp;</td>
		<td colspan="2" bordercolor="#FFFFCC">

		<div align="left"><select name="raterName">
			<option value=<%=t%>><%=trans.tslt("Please select one")%> <% 	if(raterList != null) {
	  		for(int j=0; j<raterList.size(); j++) {
				voUser voUsr = (voUser)raterList.elementAt(j);
					
				int loginID = voUsr.getRaterLoginID();
				String givenName = voUsr.getGivenName();
				String familyName = voUsr.getFamilyName();
				String name = "";
			
			//0=familyname first
				if(nameSeq == 0)
					name = familyName + " " + givenName;
				else
					name = givenName + " " + familyName;
										
			int selectedTarget = RDE.getRaterID();
			
			if(loginID == selectedTarget) {
	  %>
			
			<option value=<%=loginID%> selected><%=name%> <% } else { %>
			
			<option value=<%=loginID%>><%=name%> <% } 
		} 
		}%>
			
		</select></div>
		</td>
	</tr>
	<tr>
		<td align="right" bordercolor="#FFFFCC" height="22">&nbsp;</td>
		<td bordercolor="#FFFFCC" height="22">&nbsp;</td>
		<td bordercolor="#FFFFCC" height="22">
		<div align="left"></div>
		</td>
	</tr>
<!-- by Hemilda 10/10/2008 remove the checkbox to open new window the questionnaired -->
	<tr>
		<td align="right" bordercolor="#FFFFCC" height="22">&nbsp;</td>
		<td bordercolor="#FFFFCC" height="22">&nbsp;</td>
		<td bordercolor="#FFFFCC" height="22">
		<div align="left"></div>
		</td>
	</tr>
	<tr>
		<td align="right" bordercolor="#FFFFCC" height="22">
		<%
	if(compID != 3) {
%>
<!-- by Hemilda 10/10/2008 remove the checkbox to open new window the questionnaired  change the parameter to 0-->
 <input type="submit" style='font-size: 10.0pt; font-family: Arial'
			name="btnImport" value="<%=trans.tslt("Import")%>"
			<%//Added code to disable the Import button. Already have a Import function in Tools > Import and Export that replaces this, Sebastian 29 July 2010%>
			<%//Removed the code disabling the Import button. Liu Taichen 8 May 2012 %>
			onclick="return confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value, 2, 0)" >
		<% } else {
%> <input type="submit" style='font-size: 10.0pt; font-family: Arial'
			name="btnImport" value="<%=trans.tslt("Import")%>"
			onclick="return confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value, 2, 0)"
			> <%
} %>
		</td>
		<td bordercolor="#FFFFCC" height="22">&nbsp;</td>
		<td bordercolor="#FFFFCC" height="22">
		<%
	if(compID != 3) {
%> 
<!-- by Hemilda 10/10/2008 remove the checkbox to open new window the questionnaired  change the parameter to 0-->
<input type="button" style='font-size: 10.0pt; font-family: Arial'
			name="btnExport" value="<%=trans.tslt("Export")%>"
			<%//Added code to disable the Export button. Already have a Export function in Input Reports > Raters' Inputs for Target that replaces this, Sebastian 29 July 2010%>
			<%//Removed the code disabling the Export button. Liu Taichen 8 May 2012 %>
			onclick="return confirmOpen(this.form, this.form.raterName.value, 3, 0)" >
		<%	
	} else {
%> <input type="button" style='font-size: 10.0pt; font-family: Arial'
			name="btnExport" value="<%=trans.tslt("Export")%>"
			onclick="return confirmOpen(this.form, this.form.raterName.value, 3, 0)"
			> <%	
	}
%>
		</td>
<!-- by Hemilda 10/10/2008 remove the checkbox to open new window the questionnaired  change the parameter to 0-->
		<td width="131" height="22" align=right bordercolor="#FFFFCC"><input
			type="submit" style='font-size: 10.0pt; font-family: Arial'
			name="btnOpen" value="<%=trans.tslt("Open Questionnaire")%>"
			onclick=" return confirmOpen(this.form, this.form.raterName.options[raterName.selectedIndex].value, 1, 0)">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="3" bordercolor="#FFFFCC">&nbsp;</td>
	</tr>
</table>

<% } %>
</form>

<p></p>
<%@ include file="Footer.jsp"%> </font>
</body>
</html>