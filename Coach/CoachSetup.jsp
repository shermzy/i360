<%// Author: Dai Yong in June 2013%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- CSS -->

<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap-responsive.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="../lib/css/bootstrap-responsive.min.css">


<!-- jQuery -->
<script type="text/javascript" src="../lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../lib/js/bootstrap.js"></script>
<script type="text/javascript" src="../lib/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../lib/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="../lib/js/bootstrap-dropdown.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CoachSetup</title>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
<jsp:useBean id="Coach" class="Coach.Coach"scope="session" />
<jsp:useBean id="CoachDate" class="Coach.CoachDate"scope="session" />
<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup" scope="session" />
<jsp:useBean id="CoachSession" class="Coach.CoachSession" scope="session" />
<jsp:useBean id="CoachSlot" class="Coach.CoachSlot"scope="session" />
<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup" scope="session" />
<jsp:useBean id="CoachVenue" class="Coach.CoachVenue" scope="session" />
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<jsp:useBean id="SessionSetup" class="Coach.SessionSetup" scope="session" />

<script>
	function goToNewPage() {
		if (document.getElementById("target").value) {
			window.location.href = document.getElementById("target").value;
		}
	}
</script>
</head>
<body>
		<%
		Coach.setUserPK(logchk.getPKUser());
		CoachDate.setUserPK(logchk.getPKUser());
		CoachDateGroup.setUserPK(logchk.getPKUser());
		CoachSession.setUserPK(logchk.getPKUser());
		CoachSlot.setUserPK(logchk.getPKUser());
		CoachSlotGroup.setUserPK(logchk.getPKUser());
		CoachVenue.setUserPK(logchk.getPKUser());
		SessionSetup.setUserPK(logchk.getPKUser());
		%>
		<%@ include file="nav.jsp" %> 
 <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
    <script src="../lib/js/jquery.js"></script>
    <script src="../lib/js/bootstrap-transition.js"></script>
    <script src="../lib/js/bootstrap-alert.js"></script>
    <script src="../lib/js/bootstrap-modal.js"></script>
    <script src="../lib/js/bootstrap-dropdown.js"></script>
    <script src="../lib/js/bootstrap-scrollspy.js"></script>
    <script src="../lib/js/bootstrap-tab.js"></script>
    <script src="../lib/js/bootstrap-tooltip.js"></script>
    <script src="../lib/js/bootstrap-popover.js"></script>
    <script src="../lib/js/bootstrap-button.js"></script>
    <script src="../lib/js/bootstrap-collapse.js"></script>
    <script src="../lib/js/bootstrap-carousel.js"></script>
    <script src="../lib/js/bootstrap-typeahead.js"></script>
    <script src="../lib/js/bootstrap-affix.js"></script>

    <script src="../lib/js/holder/holder.js"></script>
    <script src="../lib/js/google-code-prettify/prettify.js"></script>

    <script src="../lib/js/application.js"></script>


    <!-- Analytics
    ================================================== -->
    <script>
      var _gauges = _gauges || [];
      (function() {
        var t   = document.createElement('script');
        t.type  = 'text/javascript';
        t.async = true;
        t.id    = 'gauges-tracker';
        t.setAttribute('data-site-id', '4f0dc9fef5a1f55508000013');
        t.src = '//secure.gaug.es/track.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(t, s);
      })();
    </script>
</body>
</html>