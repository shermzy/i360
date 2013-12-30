
<HTML>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="lib/css/bootstrap-responsive.css" rel="stylesheet">
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

<SCRIPT type="text/JavaScript">
	//Edited by Xuehai 24 May 2011. Remove 'void' to enable it running on Chrome&Firefox
	//void function printAlert()
	$(document).ready({
		$('.dropdown-toggle').dropdown();
		$(".collapsed").collapse()
	}}
</script>

<TITLE>360 interface revise</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html">

</HEAD>
<body>
	<div class="container">
		<div class="navbar">

			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar collapsed"
							data-toggle="collapse" data-target=".nav-collapse">
							<span class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</a>
						<div class="nav-collapse">
						<ul class="nav">
							<li><a href="#">3-SIXTY PROFILER</a></li>
							<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
								role="button" data-toggle="dropdown" data-target="#"
								href="/page.html"> Administration <b class="caret"></b>
							</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
									<li class="dropdown-submenu"><a tabindex="-1">Setup</a>
										<ul class="dropdown-menu">
										<li><a href="AgeRange.jsp">
											Age Range
										</a></li>
										<li><a href="EthnicGroup.jsp">
										Ethnic Group
										</a></li>
										<li><a href="Location.jsp">
											Location
										</a></li>
										<li><a href="Division.jsp">
											Division
										</a></li>
										<li><a href="Department.jsp">
											Department
										</a></li>
										<li><a href="Group.jsp">
											Group
										</a></li>
										<li><a href="JobFunction.jsp">
											Job Function
										</a></li>
										<li><a href="JobLevel.jsp">
											Job Level
										</a></li>
										<li><a href="JobPosition.jsp">
											Job position
										</a></li>
										<li><a href="UserSearch.jsp">
											User
										</a></li>
										<li><a href="RaterRelation.jsp">
											Rater Relation
										</a></li>
										<li><a href="TimeFrame.jsp">
											Time Frame
										</a></li>
										</ul></li>
									<li class="dropdown-submenu"><a tabindex="-1">System</a>
										<ul class="dropdown-menu">
										<li><a href="Cluster.jsp">
											Cluster
										</a>
										<li><a href="Comepetency.jsp">
											Competency
										</a>
										<li><a href="KeyBehaviour.jsp">
											Key Behaviour
										</a>
										<li><a href="JobCategory.jsp">
											Job Category
										</a>
										<li><a href="DevelopmentActivities.jsp">
											Development Activity
										</a>
										<li><a href="DevelopmentResources.jsp">
											Development Resource
										</a>
										<li><a href="MatchCompetencies.jsp">
											Match Competencies
										</a>
										<li><a href="RatingTask.jsp">
											Rating Task
										</a>
										<li><a href="RatingScale.jsp">
											Rating Scale
										</a>
										
										
										
										</ul></li>
									<li><a href="OrganizationList.jsp">Organisation List</a></li>
									<li><a href="DemographicEntryForAll.jsp">Demographic Entry</a></li>
									<li><a href="#">Event Viewing</a></li>
								</ul></li>
								
							<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
								role="button" data-toggle="dropdown" data-target="#"
								href="/page.html"> System Libraries <b class="caret"></b>
							</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">

								</ul></li>
								
							<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
								role="button" data-toggle="dropdown" data-target="#"
								href="/page.html"> Processing <b class="caret"></b>
							</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">

								</ul></li>
							<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
								role="button" data-toggle="dropdown" data-target="#"
								href="/page.html"> Reports <b class="caret"></b>
							</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
									<li class="dropdown-submenu"><a tabindex="-1">System Reports</a>
										<ul class="dropdown-menu">
											<li> <a href="Report_Competencies.jsp">List of Competencies</a></li>
										</ul>
									</li>
									<li class ="dropdown-submenu"><a tabindex="-1">Input Reports</a>
										<ul class ="dropdown-menu">
											<li>
												<a href="Report_Competencies_Survey.jsp">Competency by Survey</a>
											</li>
											<li>
												<a href="Report_Survey.jsp">List of Surveys</a>
											</li>
											<li>
												<a href="Report_RaterStatus_Survey.jsp">List of Rater's Status</a>
											</li>
											<li>
												<a href="Report_Competencies_Survey.jsp">Competency by Survey</a>
											</li>
										</ul>
									</li>
									<li class ="dropdown-submenu"><a tabindex="-1">Output Reports</a>
										<ul class ="dropdown-menu">
										<li>
												<a href="QuestionnaireReport.jsp">Questionnaire</a>
											</li>
											<li>
												<a href="IndividualReport.jsp">Individual Report</a>
											</li>
											<li>
												<a href="GroupReportTest.jsp">Group Report</a>
											</li>
											<li>
												<a href="GroupSummaryReport.jsp">Group Summary Report</a>
											</li>
											<li>
												<a href="Report_DevelopmentGuide.jsp">Development Guide</a>
											</li>
										</ul>
										</li>
									
								</ul></li>
							<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
								role="button" data-toggle="dropdown" data-target="#"
								href="/page.html"> Tools <b class="caret"></b>
							</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
									
								</ul></li>
								
							<li><a href="Help.htm"> Help</a></li>
					

						</ul>
					</div>
				</div>
			</div>

		</div>
	</div>
	


</BODY>

</HTML>