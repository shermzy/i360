<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>    
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>
<jsp:useBean id="adv" class="CP_Classes.AdvanceSettings" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="stg" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="AddQController" class="CP_Classes.AdditionalQuestionController" scope="session"/>
<%@ page import="CP_Classes.vo.votblSurvey"%>

<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
		
function Save(form)
{

		form.action="AdvanceSettings.jsp?save=1";
		form.method="post";
		form.submit();
	
}

var x = parseInt(window.screen.width) / 2 - 350;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 200;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function changeAll(form)
{	
	x = document.AdvanceSettings

	for(var j=0; j<3; j++)
	{
		alert(form.selAllSca+j.value);
	}
}

function merge()
{
	var myWindow=window.open('MergeRelation.jsp','windowRef','scrollbars=no, width=450, height=250');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'MergeRelation.jsp';
	myWindow.focus(); // Added by Desmond, 23 June 08, fix pop up lose focus problem
	myWindow.resizeTo(450,290); // Added by Desmond, 23 June 08, resize to correct size after bringin pop up to front
	}

function dates()
{
	var myWindow=window.open('SetDates.jsp','windowRef','scrollbars=no, width=450, height=250');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'SetDates.jsp';
	myWindow.focus(); // Added by Desmond, 23 June 08, fix pop up lose focus problem
	myWindow.resizeTo(450,290); // Added by Desmond, 23 June 08, resize to correct size after bringin pop up to front
}

function rating()
{
	var myWindow=window.open('RatingSetup.jsp','windowRef','scrollbars=yes, width=800, height=400');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'RatingSetup.jsp';
	myWindow.focus(); // Added by Desmond, 23 June 08, fix pop up lose focus problem
	myWindow.resizeTo(800,400); // Added by Desmond, 23 June 08, resize to correct size after bringin pop up to front
}

function selfCom(form,field)
{
	if(field == 0)
	{
		if(confirm(" <%=trans.tslt("Do you want to include Self Comment")%>?"))
		{
			form.action ="AdvanceSettings.jsp?self=0";
			form.method="post";
			form.submit();
		}
	}
	else if(field == 1)
	{
		if(confirm(" <%=trans.tslt("Do you want to exclude Self Comment")%>?"))
		{
			form.action ="AdvanceSettings.jsp?self=1";
			form.method="post";
			form.submit();
		}
	}
}			

function breakDown(form,field)
{
	if(field == 0)
	{
		if(confirm(" <%=trans.tslt("Do you want to breakdown the Development Map into sub-categories")%>?"))
		{
			form.action ="AdvanceSettings.jsp?breakdown=0";
			form.method="post";
			form.submit();
		}
	}
	else if(field == 1)
	{
		if(confirm(" <%=trans.tslt("Do you want to show the Development Map in main categories")%>?"))
		{
			form.action ="AdvanceSettings.jsp?breakdown=1";
			form.method="post";
			form.submit();
		}
	}
}

// Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self as rater
function autoSelf(form,field)
{

	if(field == false)
	{
		if(confirm(" <%=trans.tslt("Do you want to DISABLE automatic assignment of Self as a rater")%>?"))
		{
			form.action ="AdvanceSettings.jsp?autoSelf=false";
			form.method="post";
			form.submit();
		}
	}
	else if(field == true)
	{
		if(confirm(" <%=trans.tslt("Do you want to ENABLE automatic assignment of Self as a rater")%>?"))
		{
			form.action ="AdvanceSettings.jsp?autoSelf=true";
			form.method="post";
			form.submit();
		}
	}
}

function autoSuperior(form,field)
{
	if(field == false)
	{
		if(confirm(" <%=trans.tslt("Do you want to DISABLE automatic assignment of Superior as a rater")%>?"))
		{
			form.action ="AdvanceSettings.jsp?autoSup=false";
			form.method="post";
			form.submit();
		}
	}
	else if(field == true)
	{
		if(confirm(" <%=trans.tslt("Do you want to ENABLE automatic assignment of Superior as a rater")%>?"))
		{
			form.action ="AdvanceSettings.jsp?autoSup=true";
			form.method="post";
			form.submit();
		}
	}
}

</SCRIPT>
<body>
<%
String username=(String)session.getAttribute("username");
int SurveyID = CE_Survey.getSurvey_ID();

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<% 	} 

	if(request.getParameter("self") != null)
	{
		int setSelf = 0;
		int selfcom = new Integer(request.getParameter("self")).intValue();
		
		if(selfcom == 0)
		{
			setSelf = 1;
		}
		else if(selfcom == 1)
		{
			setSelf = 0;			
		}

		boolean isUpdated = adv.update_SelfComment(CE_Survey.getSurvey_ID(), setSelf,logchk.getPKUser());
		
		if(isUpdated) {
%>		
		<script>
			alert("Updated successfully");
		</script>
<%
		}
	}

	if(request.getParameter("breakdown") != null)
	{
		int iSetBreak = 0;
		int iBreakdown = new Integer(request.getParameter("breakdown")).intValue();
		
		if(iBreakdown == 0)
			iSetBreak = 1;
		else
			iSetBreak = 0;
			
		boolean isUpdated = adv.update_Breakdown(CE_Survey.getSurvey_ID(), iSetBreak, logchk.getPKUser());
		
		if(isUpdated) {
%>		
		<script>
			alert("Updated successfully");
		</script>
<%
		}
	}

        // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self as rater
        if(request.getParameter("autoSelf") != null)
	{       
                boolean iSetAutoSelf = Boolean.parseBoolean( request.getParameter("autoSelf") );
                //System.out.println(">>Adding, autoSelf: " + iSetAutoSelf);
		//boolean iSetAutoSelf = true;
		//int iAutoSelf = new Integer(request.getParameter("autoSelf")).intValue();
		
		//if(iAutoSelf == 0) iSetAutoSelf = false;
			
		boolean isUpdated = adv.update_AutoAssignSelf(CE_Survey.getSurvey_ID(), iSetAutoSelf, logchk.getPKUser());
		
		if(isUpdated) {
%>		
		<script>
			alert("Updated successfully");
		</script>
<%
		}
	}

        // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self as rater
        if(request.getParameter("autoSup") != null)
	{       
                boolean iSetAutoSup = Boolean.parseBoolean( request.getParameter("autoSup") );
                //System.out.println(">>Adding, autoSup: " + iSetAutoSup);
			
		boolean isUpdated = adv.update_AutoAssignSup(CE_Survey.getSurvey_ID(), iSetAutoSup, logchk.getPKUser());
		
		if(isUpdated) {
%>		
		<script>
			alert("Updated successfully");
		</script>
<%
		}
	}
        
    	if(request.getParameter("save") != null)
    	{
    		if(request.getParameter("header")!=null)
    		{
			String header = AddQController.escapeInvalidChars(request.getParameter("header"));
			//save the header for the additional questions 
			if(!AddQController.getQuestionnaireHeader(SurveyID).equals(""))
			{
				//header already exists update the header
				AddQController.updateQuestionnaireHeader(SurveyID, header);
				
			}
			else
			{
				//header does not exist insert the new header
				AddQController.saveQuestionnaireHeader(SurveyID, header);
			
			}
			
			%>
			<Script>
			alert("Saved successfully")
			location.href='AdvanceSettings.jsp';
			</Script>
			<%
			}
    	}
	
	%>

<form name="AdvanceSettings" action="AdvanceSettings.jsp" method="post">
<font size="2">
   
<table border="0" width="78%" cellspacing="0" cellpadding="0" bordercolor="#000000" style="border-width: 0px">
	<tr>
		<td width="114" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700">
		<a href="SurveyDetail.jsp" style="cursor:pointer"><%= trans.tslt("Survey Detail") %></a></font></td>
		<td width="28" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color=blue><u>
		<a href="SurveyCluster.jsp" style="cursor:pointer"><%=trans.tslt("Cluster")%></a></u></font></td>
		<td width="28" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="101" style="border-style: none; border-width: medium">
		<p align="center">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" size="2">
		<a href="SurveyCompetency.jsp" style="cursor:pointer"><%= trans.tslt("Competency") %></a></font></td>
		<td width="20" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="113" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyKeyBehaviour.jsp" style="cursor:pointer"><%= trans.tslt("Key Behaviour") %></a></font></b></td>
		<td width="24" style="border-style: none; border-width: medium">
		<p align="center"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="109" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyDemos.jsp" style="cursor:pointer"><%= trans.tslt("Demographics") %></a></font></b></td>
		<td width="23" style="border-style: none; border-width: medium"><b>
		<font size="2">
		<img border="0" src="images/gray_arrow.gif" width="19" height="26"></font></b></td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyRating.jsp" style="cursor:pointer"><%= trans.tslt("Rating Task") %></a></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center">&nbsp; <font size="2">
   
		<span style="font-weight: 700">
		<font face="Arial" style="font-size: 10pt; font-weight: 700" color="#CC0000"><%= trans.tslt("Advanced Settings") %></font></span></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="SurveyPrelimQ.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Preliminary Questions") %></a></font></b></td>
	</tr>
	<tr height="30">
		<td width="114" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">
		<p align="center"><b><font face="Arial" size="2">
		<a href="AdditionalQuestions.jsp?entry=1" style="cursor:pointer"><%= trans.tslt("Additional Questions") %></a></font></b></td>
	</tr>
	<tr>
		<td width="114" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="28" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="101" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="20" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="113" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="24" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="109" style="border-style: none; border-width: medium">&nbsp;
		</td>
		<td width="23" style="border-style: none; border-width: medium">&nbsp;</td>
		<td width="135" style="border-style: none; border-width: medium">&nbsp;
		</td>
	</tr>
</table>

		</font>
		
	


<table border="1" width="43%" bordercolor="#3399FF" bgcolor="#FFFFCC">
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		<td align="center" bgcolor="#000080" colspan="2">
		<p align="center"><b><font color="#FFFFFF" face="Arial" size="2">
		<%= trans.tslt("Advance Settings") %></font></b></td>
	</tr>
	<% if (stg.getNomModule() == 1)
	   {	%>
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		<td width="33" align="center"><font face="Arial">
		<input type="radio" value="0" name="chkOpt" onclick = "dates()"></font></td>
		<td><font face="Arial" size="2">&nbsp;<%= trans.tslt("Set Dates") %></font></td>
	</tr>
	<% }

//System.out.println(">>Competency Exists: " + CE_Survey.checkCompetencyExist(CE_Survey.getSurvey_ID()) );
//System.out.println(">>Rating Exists: " + CE_Survey.checkRating(CE_Survey.getSurvey_ID()) );
	// Changed by DeZ, 15.07.08, hide Fix Rating Task Scale for Key Behaviour
	// Changed by DeZ, 27.06.08, hide Fix Rating Task Scale when survey has no assigned Competency and/or Rating Task
	if( CE_Survey.checkCompetencyExist(CE_Survey.getSurvey_ID()) && CE_Survey.checkRating(CE_Survey.getSurvey_ID()) && 
			CE_Survey.getSurveyLevel(CE_Survey.getSurvey_ID()) != 1 )
	{
	%>
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		<td width="33" align="center"><font face="Arial">
		<input type="radio" value="1" name="chkOpt" onclick = "rating()"></font></td>
		<td><font face="Arial" size="2">&nbsp;<%= trans.tslt("Fix Rating Task Scale") %></font></td>
	</tr>
        <%
        } // End hiding of Fix Rating Task Scale

		int selfcomment = 0;
		int breakdown = 0;
		//Added to not display Include Self comments selection when Include comments is not checked
		//Mark Oei 19 Mar 2010
		int othercomment = 0;

                
                // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self and/or Superior as rater
                boolean autoSelf = true;
                boolean autoSup = true;
                
		/********************/
		votblSurvey rs_SurveyDetail = CE_Survey.getSurveyDetail(CE_Survey.getSurvey_ID());
		
		if(rs_SurveyDetail != null)
		{
			othercomment = rs_SurveyDetail.getComment_Included(); //Added to check Comment Included is checked
			selfcomment = rs_SurveyDetail.getSelf_Comment_Included();
			breakdown = rs_SurveyDetail.getDMapBreakdown();
 
                        // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self and/or Superior as rater
                        autoSelf = rs_SurveyDetail.getAutoSelf();
                        autoSup = rs_SurveyDetail.getAutoSup();
                        //System.out.println(">>AutoSelf setted to " + rs_SurveyDetail.getAutoSelf());
                        //System.out.println(">>AutoSup setted to " + rs_SurveyDetail.getAutoSup());

		}
		//Added to prevent user to include self-rater comments when others cannot provide comments on target
		//Mark Oei 19 Mar 2010
		if (othercomment != 0) { %>
			<tr onMouseOver = "this.bgColor = '#99ccff'"
				onMouseOut = "this.bgColor = '#FFFFcc'">
		<%	if(selfcomment == 0) {%>
				<td width="33" align="center"><font face="Arial">
				<input type="radio" value=<%=selfcomment%> name="chkOpt" onclick = "selfCom(this.form,<%=selfcomment%>)"></font></td>
				<td><font face="Arial" size="2">&nbsp;<%= trans.tslt("Include Self Comment") %>?</font></td>
			<% } else {%>
				<td width="33" align="center"><font face="Arial">
				<input type="radio" value=<%=selfcomment%> name="chkOpt" onclick = "selfCom(this.form,<%=selfcomment%>)"></font></td>
				<td><font face="Arial" size="2">&nbsp;<%= trans.tslt("Exclude Self Comment") %>?</font></td>
				<% } %>
			</tr>
	<%	}	%>
		
	<tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		
<%		
		if(breakdown == 0)
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="" name="chkOpt" onclick = "breakDown(this.form,<%=breakdown%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Breakdown Development Map into sub-categories?</font></td>
<%		}	
		else
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="2" name="chkOpt" onclick = "breakDown(this.form,<%=breakdown%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Show Development Map in main categories?</font></td>
<%		}	%>

	</tr>
<% // Added by DeZ, 18/06/08, to add function to enable/disable Nominate Rater %>
        <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		
<%              //System.out.println(">>Display, autoSelf: " + autoSelf); System.out.println(">>Display, autoSup: " + autoSup);
		if(autoSelf == false)
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="" name="chkOpt" onclick = "autoSelf(this.form,<%=!autoSelf%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Automatically assign Self as Rater?</font></td>
<%		}	
		else
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="2" name="chkOpt" onclick = "autoSelf(this.form,<%=!autoSelf%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Don't automatically assign Self as Rater?</font></td>
<%		}	%>

	</tr>
        <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
		
<%
		if(autoSup == false)
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="" name="chkOpt" onclick = "autoSuperior(this.form,<%=!autoSup%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Automatically assign Superior as Rater?</font></td>
<%		}	
		else
		{	%>
			<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="2" name="chkOpt" onclick = "autoSuperior(this.form,<%=!autoSup%>)"></font></td>
			<td><font size="2" face="Arial">&nbsp;Don't automatically assign Superior as Rater?</font></td>
<%		}	%>

			

	</tr>
	 <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
    	<td width="33" align="center"><font face="Arial" size="2">
			<input type="radio" value="2" name="chkMerge" onclick = "merge()"></font></td>
			<td><font size="2" face="Arial">&nbsp;Change Relation Merging Options?</font></td>
			</tr>
</table>
<br/>
	<table border=1 bordercolor="#3399FF">
<tr>
<td bgcolor="#000080">
<font color="white"  face="Verdana" style="font-weight: 700" size="2">
<b><%=trans.tslt("Instructions")%></b></font>
</td>
</tr>

<tr><td>
<textarea rows=5 cols=75 name="header" id="header">
<% String h = AddQController.getQuestionnaireHeader(SurveyID); 
		if(!h.equals(""))
		{
			out.print(h);
		}
%>
</textarea>
</td></tr>

</table>

<INPUT type="button" value="Save" onclick="Save(this.form)"/> 
<font size="2">
   
<p>&nbsp;</p>
<p>&nbsp;</p>
<p></p> 
<%@ include file="Footer.jsp"%>
</form>
</body>

</html>