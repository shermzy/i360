<%@ page import="java.sql.*,CP_Classes.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.Vector"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page import="CP_Classes.Calculation"%>
<%@ page import="CP_Classes.SurveyResult"%>

<%
	//by  Yiting 19/09/2009 Fix jsp files to support Thai language"
%>
<html>
<head>
<title>Group Report</title>
<style type="text/css">
<!--
.style1 {
	font-size: 10pt
}
-->
</style>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%
	// by lydia Date 05/09/2008 Fix jsp file to support Thai language
%>

<!-- CSS -->

<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="lib/css/bootstrap-responsive.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet"
	href="lib/css/bootstrap-responsive.min.css">


<!-- jQuery -->
<script type="text/javascript" src="lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/js/bootstrap.js"></script>
<script type="text/javascript" src="lib/js/jquery-1.9.1.js"></script>


<script src="lib/js/bootstrap.min.js" type="text/javascript"></script>




	<%boolean reportCompletion =false; %>
	<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
	<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport" scope="session" />
	<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
	<jsp:useBean id="User_Jenty" class="CP_Classes.User_Jenty"
		scope="session" />
	<jsp:useBean id="ExcelGroup" class="CP_Classes.GroupReport"
		scope="session" />

	<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session" />
	<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />

	<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session" />
	<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey"
		scope="session" />


	<script type="text/javascript">
var x = parseInt(window.screen.width) / 2 - 200;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;
 

$(document).ready(function(){
	$('.info').tooltip();
	
	
	if(<%=session.getAttribute("chosenFiles")%>!=null){
		$('#dlpage').show();
	}else{
		$('#dlpage').hide();
	}
});

function loading(){
	$('.generateBtn').button('loading');
}
function proceed(form, org)
{
	
	var query = "ResetCalculation.jsp?proceed=" + org.value;
		form.action = query;
		form.method = "post";
		form.submit();
	
	return true;	
}
function closeModal()
{
	$("#myModal").modal("hide");
}
function confirmOpen(form)
{
	form.action = 'ResetCalculation.jsp?reset=1';
	form.submit();	
}
	</script>


	<%
		//response.setHeader("Pragma", "no-cache");
			//response.setHeader("Cache-Control", "no-cache");
			//response.setDateHeader("expires", 0);

			String username=(String)session.getAttribute("username");
			Vector surveyList = new Vector();
	
			if (request.getParameter("proceed") != null) {
				String PKOrg1 = request.getParameter("proceed");
				int PKOrg = Integer.parseInt(PKOrg1);
				logchk.setOrg(PKOrg);
				
				 surveyList = QR.getSurveys(PKOrg);
				
			}

			/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

					
					
					 
					//Changed made by Ha 27/05/08 to set respective value to default when changing upper layer
			
					if (request.getParameter("surveyID") != null) {
						int id = Integer.parseInt(request.getParameter("surveyID"));
						QR.setSurveyID(id);
					
						
					}
					
			

					//End of change made by Ha
					
					if (request.getParameter("reset") != null) {
						int surveyID = Integer.parseInt(request.getParameter("surveyName"));
						String[] errors = QR.resetSurvey(surveyID);
						if(errors.length >0){
							session.setAttribute("errors",errors[0]);
						}
					}
					
			%>
			</head>

	<body>
		<div class="container">
			<form name="GroupReport" method="post" action="ResetCalculation.jsp">

			<div class="row-fluid">
				<div class="offset1 span4">
					<h2><%=trans.tslt("Group Report")%></h2>
				</div>
				
			</div>
 			<div class="row-fluid">
				<% if(session.getAttribute("errors")!=null && !(((String)session.getAttribute("errors")).equalsIgnoreCase("The statement did not return a result set."))){
					String err = (String) session.getAttribute("errors");
					%><%=err %>
				<%} else if(((String)session.getAttribute("errors")).equalsIgnoreCase("The statement did not return a result set.")){
				%>Calculation reset successful!
				<%} else{
					%> Select a survey.
				<% }%>
			</div> 
			<div class="row-fluid">
				<div class="span2">
					<%=trans.tslt("Organisation")%>	:
				</div>
				
				<%
					// Added to check whether organisation is also a consulting company
						// if yes, will display a dropdown list of organisation managed by this company
						// else, it will display the current organisation only
						// Mark Oei 09 Mar 2010
						String[] UserDetail = new String[14];
						UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
						boolean isConsulting = true;
						isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
						if (isConsulting) {
				%>
				<div class="span5">
					<select size="1" name="selOrg"
						onchange="proceed(this.form,this.form.selOrg)">
						<option value="0" selected><%=trans.tslt("Please select one")%></option>
						<%
							Vector vOrg = logchk.getOrgList(logchk.getCompany());

									for (int i = 0; i < vOrg.size(); i++) {
										votblOrganization vo = (votblOrganization) vOrg
												.elementAt(i);
										int PKOrg = vo.getPKOrganization();
										String OrgName = vo.getOrganizationName();

										if (logchk.getOrg() == PKOrg) {
						%>
						<option value=<%=PKOrg%> selected><%=OrgName%></option>
						<%
							} else {
						%>
						<option value=<%=PKOrg%>><%=OrgName%></option>
						<%
							}
									}
								} else {
						%>
						<select size="1" name="selOrg"
						onchange="proceed(this.form,this.form.selOrg)">
							<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
					</select>
						<%
							} // End of isConsulting
						%>
					</select>
				</div>
			</div>

			
				
			<div class="row-fluid">
				<div class="span2">
					<%=trans.tslt("Survey")%>
					:
				</div>
				<div class="span5">
					<select name="surveyName">
						<option value=0><%=trans.tslt("Please select one")%>
							<%
								if (surveyList != null) {
										for (int j = 0; j < surveyList.size(); j++) {
											votblSurvey voSurvey = (votblSurvey) surveyList.elementAt(j);
											int ID = voSurvey.getSurveyID();
											String name = voSurvey.getSurveyName();
											int surveyID = QR.getSurveyID();
											if (surveyID != 0 && ID == surveyID) {
											%>
						
												<option value=<%=ID%> selected><%=name%>
											<%
											} else {
											%>
												<option value=<%=ID%>><%=name%>
											<%
											}
										}
								}
							%>
						
					</select>
				</div>
			</div>
	
			
					
			<div class="row-fluid"></div>

			<div class="row-fluid" style="padding-top: 50px">
				<div class="span7">
					<button type="button" class="btn btn-primary generateBtn" name="btnOpen"
						value="<%=trans.tslt("Generate")%>"
						onclick="return confirmOpen(this.form)" data-loading-text="Resetting...">Reset</button>
						<a type="button"class ="btn" href="GroupReport.jsp" >Group Report</a>
						<a type="button"class ="btn" href="IndividualReport.jsp" >Individual Report</a></div>
						
					
				</div>
			

				
		</form>
		

		<!-- Modal -->
		<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Report Generation</h3>
			</div>
			<div class="modal-body">
				<p>This process may take awhile. Please do not close the main window.</p>
			</div>

    
  </div>
		</div>


	
	<%@ include file="Footer.jsp"%>

</body>
</html>