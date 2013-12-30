<%@ page import="java.sql.*,
                 java.io.*,
                  CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
                   
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="ev" class="CP_Classes.EventViewer" scope="session"/>
<HTML>

<SCRIPT LANGUAGE="JavaScript">

<!-- Begin
//check if all the boxes are filled in
//Edited by Xuehai, 25 May 2011. Removed 'void' to enable running on Chrome&Firefox
    function validate(form)
    {
      var iValid =0;
      x = document.Top
      username = x.txtUsername.value
      password = x.txtPass.value
      
      if (username == "")
		alert("<%=trans.tslt("Please enter Username")%>");
      else 
      {
      	if (password == "")
       		alert("<%=trans.tslt("Please enter Password")%>");
      	else
      	{
      		iValid = 1;
      		window.document.Top.action = "Top.jsp?result=1";
      				
    		window.document.Top.method="post";	
    		window.document.Top.submit();
		}
      }
    }
//  End -->

</script>
<HEAD>
<TITLE>3-Sixty Profiler</TITLE>
<meta http-equiv="Content-Type" content="text/html">
<base target="_parent">
<% //Store data from TB_Translation into Hashtable
   //trans.getTranslation(); 
%>

</HEAD>
<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>

<%
	String orgCode = logchk.getOrgCode();
	int FKOrganization = Org.getPKOrg(orgCode);
	logchk.setOrg(FKOrganization);
	
	String result = request.getParameter("result");

if( result != null)	
{
	String username = request.getParameter("txtUsername");
	username = username.toLowerCase();
	String password=request.getParameter("txtPass");

	//check if the username of the correct format
	String user = logchk.getWithoutQuoteMarks(username);
	String usernameQuoted=logchk.getSingleQuoted(user);
   	
 	//ResultSet rs = db.getRecord("SELECT * FROM [User] a, tblConsultingCompany b, tblOrganization c WHERE a.LoginName =" + usernameQuoted + " AND a.FKUserType360 != 14 AND a.FKCompanyID = b.CompanyID AND a.FKOrganization = c.PKOrganization");
	
	String [] sInfo = new String[9]; 
    sInfo = CE_Survey.getUserInfo(usernameQuoted, orgCode);
	
	
	int iLoggedIn = 0; //0= Not logged in, 1= Logged in
    
    if(sInfo != null && sInfo[1] != null)
    { 
    	int PKUser = Integer.parseInt(sInfo[0]);
    	String LoginName = sInfo[1];
    	String db_password = sInfo[2];
    	int isEnabled = Integer.parseInt(sInfo[3]);
    	int CompanyID = Integer.parseInt(sInfo[4]);
    	String CompanyName = sInfo[5];
    	int PKOrg = Integer.parseInt(sInfo[6]);
     	String OrgName = sInfo[7];
		String OrgCode = sInfo[8];
		
		if(isEnabled == 1)
		{
	    	if(db_password.equals(password))
	    	{
	    		iLoggedIn = 1; //Logged in
		     	session.setAttribute("username", username);
		     	logchk.setPKUser(PKUser);
				logchk.setCompany(CompanyID);
				logchk.setCompanyName(CompanyName);
				logchk.setOrg(PKOrg);
				// Edited by Eric Lu 21/5/08
				// Sets selfOrg in Login.java
				logchk.setSelfOrg(PKOrg);
				logchk.setOrgCode(OrgCode);
				
				String desc = "Log into 360 System";
				String action = "Login";
				ev.addRecord(action, "Login", desc, LoginName, CompanyName, OrgName);
				
				String [] UserDetail = new String[14];
				UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
				
				if(UserDetail[8].equals("Super Administrator"))
				{
					logchk.setUserType(1);
					%>
					<jsp:forward page="indexAdmin.htm"/>
					<%
				}
				else if(UserDetail[8].equals("Administrator"))
				{
					logchk.setUserType(2);
					%>
					<jsp:forward page="indexAdmin.htm"/>
					<%
				}
				else if(UserDetail[8].equals("Data Entry Personnel"))
				{
					logchk.setUserType(3);
					%>
					<jsp:forward page="indexDE.htm"/>	
					<%
				}
				else if(UserDetail[8].equals("Participant (rater or target)"))
				{
					logchk.setUserType(4);
					%>
					<jsp:forward page="indexPARTICIPANT.htm"/>	
					<%
				}
				else if(UserDetail[8].equals("No Access"))
				{	%>
					<script>
			        alert("<%=trans.tslt("Your user account has been disabled. Please contact your administrator")%>.");
			        parent.location.href="index.jsp?candidate="+'<%=logchk.getOrgCode()%>';
					</script>
			<%	}
			}
			else 
			{ 	%>
		        <script>
		        alert("<%=trans.tslt("You have entered wrong combination of Login ID and Password")%>.\n<%=trans.tslt("Please note that Password is case-sensitive")%>.");
		        parent.location.href="index.jsp?candidate="+'<%=logchk.getOrgCode()%>';
				</script>
		<%	}
		}
		else
		{	%>
				<script>
				alert("<%=trans.tslt("Your user account has been disabled. Please contact your administrator")%>.");
				parent.location.href="index.jsp?candidate="+'<%=logchk.getOrgCode()%>';
				</script>
	<%	}
   	} 
   	
   	if(iLoggedIn == 0)
   	{ %>
        <script>
         alert("<%=trans.tslt("You have entered wrong combination of Login ID and Password")%>.\n<%=trans.tslt("Please note that Password is case-sensitive")%>.");
        parent.location.href="index.jsp?candidate="+'<%=logchk.getOrgCode()%>';
        </script>
        
	<%	}
}
%>

<form name="Top" action="Top.jsp" method="post">

<%
				
/*
*Changes: Added a logo to be displayed on top of the pcc logo
*Reasons: To allow organization logo to be displayed
*Updated By: Liu Taichen	
*Updated On: 31 May 2012
*/
					
					String orgLogo = "";
					votblOrganization vo_Org = Org.getOrganization(logchk.getOrg());
					orgLogo = "Logo/" + vo_Org.getOrganizationLogo();
					
					if(vo_Org.getOrganizationLogo() != null){
					if (!vo_Org.getOrganizationLogo().equals("")) {
						//for organizations other than moe
						if(vo_Org.getPKOrganization() != 77){
			%>
			<IMG
				STYLE="position: absolute; TOP: 3px; LEFT: 438px; WIDTH: 155px; HEIGHT: 136px"
				SRC=<%=orgLogo%>>
				
				<%} 
						//if the organization is moe
						else{ %>
				<IMG
				STYLE="position: absolute; TOP: 21px; LEFT: 360px; WIDTH: 233px; HEIGHT: 99px"
				SRC=<%=orgLogo%>>
			<%
				}}
					}
			%>



<TABLE WIDTH=800 BORDER=0 CELLPADDING=0 CELLSPACING=0>
	<TR>
		<TD width="6" ROWSPAN=19>
			<IMG SRC="images/360_01.jpg" WIDTH=10 HEIGHT=581 ALT=""></TD>
			</TR>
	<TR>
		<TD COLSPAN=2>
			<IMG SRC="images/360_03.jpg" WIDTH=156 HEIGHT=69 ALT=""></TD>
		<TD width="37">
			<IMG SRC="images/360_04.jpg" WIDTH=28 HEIGHT=69 ALT=""></TD>
		<TD COLSPAN=4 ROWSPAN=8>
		<%
			/* TOYOTA ENV */
			//if(server.getCompanySetting() == 3)
			//if(logchk.getOrgCode() != null && (logchk.getOrgCode().equals("KPRBC") || logchk.getOrgCode().equals("DEMO")) )
			if( logchk.getOrgCode() != null && logchk.getOrgCode().equals("Allianz") )
				/*
				*Changes: Display the PCC logo instead of the MOE logo on the banner
				*Reasons: To prevent MOE logo from showing
				*Updated By: Liu Taichen	
				*Updated On: 31 May 2012
				*/
			{%>
				<IMG SRC="images/360_05_blanklogo.jpg" WIDTH=399 HEIGHT=132 ALT=""></TD>
			<%}
			else
			{%>
				<IMG SRC="images/360_05_blanklogo.jpg" WIDTH=399 HEIGHT=132 ALT=""></TD>
			<%}
			/* END TOYOTA ENV */
		%>
			
		<TD COLSPAN=3 ROWSPAN=6>
		<%
			/* TOYOTA ENV */
			//if(server.getCompanySetting() == 3)
			if( logchk.getOrgCode() != null && logchk.getOrgCode().equals("Allianz") )
			{%>
				<IMG SRC="images/360_06_TMT.jpg" WIDTH=207 HEIGHT=120 ALT=""></TD>
			<%}
			else
			{%>
				<IMG SRC="images/360_06.jpg" WIDTH=207 HEIGHT=120 ALT=""></TD>
			<%}
			/* END TOYOTA ENV */
		%>
			
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=69 ALT=""></TD>
	</TR>
	<TR>
		<TD COLSPAN=2>
			<IMG SRC="images/360_07.jpg" WIDTH=156 HEIGHT=4 ALT=""></TD>
		<TD ROWSPAN=3>
			<IMG SRC="images/360_08.jpg" WIDTH=28 HEIGHT=31 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=4 ALT=""></TD>
	</TR>
	<TR>
		
    <TD COLSPAN=2 background="images/360_09.jpg"><table width="156" border="0" cellpadding="0" cellspacing="0" background="images/360_09.jpg">
        <tr><!-- Edited by Xuehai, 25 May 2011. Enable to run on Chrome&Firefox -->
          <td width="57"><div align="right"><font size="1" style='-webkit-text-size-adjust:none;' face="Verdana, Arial, Helvetica, sans-serif"><strong>Log 
              in </strong></font></div></td>
          <td width="10"><img src="images/spacer.gif" width="5" height="5"> </td>
          <td width="89"><input type="text" style='width:85px;height:20px;' name="txtUsername" tabindex = 1 onKeyPress="if(event.keyCode==13) { return validate(); }" disabled></td>
        </tr>
      </table> </TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=25 ALT=""></TD>
	</TR>
	<TR>
		<TD COLSPAN=2 ROWSPAN=2>
			<IMG SRC="images/360_10.jpg" WIDTH=156 HEIGHT=5 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=2 ALT=""></TD>
	</TR>
	<TR>
		<TD ROWSPAN=4>
	
			<IMG SRC="images/360_11.jpg" ALT="" WIDTH=28 HEIGHT=32 border="0"></a></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=3 ALT=""></TD>
	</TR>
	<TR>
		
    <TD COLSPAN=2 ROWSPAN=2 valign="top" background="images/360_12.jpg"><table width="156" border="0" cellpadding="0" cellspacing="0" background="images/360_12.jpg">
        <tr><!-- Edited by Xuehai, 25 May 2011. Enable to run on Chrome&Firefox -->
          <td width="57"><div align="right"><font size="1" style='-webkit-text-size-adjust:none;' face="Verdana, Arial, Helvetica, sans-serif"><strong>Password</strong></font></div></td>
          <td width="10"><img src="images/spacer.gif" width="5" height="5"> </td>
          <td width="89"><input type="password" name="txtPass" style='width:85px;height:20px;' tabindex = 2 onKeyPress="if(event.keyCode==13) { return validate(); }" disabled></td>
        </tr>
      </table> </TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=17 ALT=""></TD>
	</TR>
	<TR>
		<TD COLSPAN=3 ROWSPAN=2>
		<%
			/* TOYOTA ENV */
			//if(server.getCompanySetting() == 3)
			if(logchk.getOrgCode() != null && (logchk.getOrgCode().equals("KPRBC") || logchk.getOrgCode().equals("DEMO")) )
			{%>
			<IMG SRC="images/360_13.jpg" WIDTH=207 HEIGHT=12 ALT=""></TD>
			<%}
			else
			{%>
			<IMG SRC="images/360_13.jpg" WIDTH=207 HEIGHT=12 ALT=""></TD>
			<%}
			/* END TOYOTA ENV */
		%>	
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=8 ALT=""></TD>
	</TR>
	<TR>
		<TD COLSPAN=2>
			<IMG SRC="images/360_14.jpg" WIDTH=156 HEIGHT=4 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=4 ALT=""></TD>
	</TR>
	<TR>
		<TD COLSPAN=9>
			<IMG SRC="images/360_15.jpg" WIDTH=789 HEIGHT=7 ALT=""></TD>
		<TD width="1">
			<IMG SRC="images/360_16.jpg" WIDTH=1 HEIGHT=7 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=7 ALT=""></TD>
	</TR>
	<TR>
		
    <TD COLSPAN=2 height="34">&nbsp; 
  
    </TD>
		<TD COLSPAN=2 height="34">&nbsp;
			</TD>
		<TD width="108" height="34">&nbsp;
			</TD>
		<TD width="89" height="34">&nbsp;
			</TD>
		<TD width="87" height="34">&nbsp;
			</TD>
		<TD width="88" height="34">&nbsp;
			</TD>
		<TD COLSPAN=2 height="34">
			<IMG SRC="images/360_23.jpg" WIDTH=119 HEIGHT=33 ALT=""></TD>
		<TD height="34">
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=33 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=31 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=56 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=50 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=50 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=49 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=53 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=48 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=78 ALT=""></TD>
	</TR>
	<TR>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=10 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=129 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=27 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=28 HEIGHT=1 ALT=""></TD>
		<TD width="115">
			<IMG SRC="images/spacer.gif" WIDTH=115 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=108 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=89 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=87 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=88 HEIGHT=1 ALT=""></TD>
		<TD width="118">
			<IMG SRC="images/spacer.gif" WIDTH=118 HEIGHT=1 ALT=""></TD>
		<TD>
			<IMG SRC="images/spacer.gif" WIDTH=1 HEIGHT=1 ALT=""></TD>
		<TD></TD>
	</TR>
</TABLE>

<!-- End ImageReady Slices -->
<!-- ImageReady Slices (360 interface revise.psd) -->
<div id="Layer1" style="position:absolute; width:188px; height:79px; z-index:1; left: 48px; top: 179px; visibility: hidden; overflow: auto;"> 
  <table width="211" border="0">
    <tr> 
      <td width="205" bgcolor="#00CCFF" class="style1"><font size="2" face="Verdana, Arial, Helvetica, sans-serif"><span class="style6">Set 
        up</span></font></td>
    </tr>
    <tr> 
      <td bgcolor="#006699" class="style1"><font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif"><span class="style8">System</span></font></td>
    </tr>
    <tr> 
      <td bgcolor="#00CCFF" class="style1"><font size="2" face="Verdana, Arial, Helvetica, sans-serif"><span class="style6">Rater's 
        Demographic Entry </span></font></td>
    </tr>
    <tr> 
      <td bgcolor="#006699" class="style1"><font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif"><span class="style8">Event 
        viewer </span></font></td>
    </tr>
  </table>
</div>
<map name="Map11">
  <area shape="rect" coords="2,5,31,26" href="#">
</map>

<%
	System.gc();
%>
</BODY>
</form>
</HTML>