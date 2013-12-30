<%@ page import="java.sql.*,
				java.util.*,
                 java.io.* "%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                 
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="RT" class="CP_Classes.RatingTask" scope="session"/>
<jsp:useBean id="RS" class="CP_Classes.RatingScale" scope="session"/>
<jsp:useBean id="PRP" class="CP_Classes.Purpose" scope="session"/>
<jsp:useBean id="SVR" class="CP_Classes.SurveyRating" scope="session"/>
<%@ page import="CP_Classes.vo.votblPurpose"%>
<%@ page import="CP_Classes.vo.votblRatingTask"%>
<%@ page import="CP_Classes.vo.votblScale"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Survey- Rating Task</title>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
function goBack() { window.location = "SurveyKeyBehaviour.jsp"; }
function goNext() { window.location = ""; }

function check(field)
{
	var flag = false;
	
	if(field != null)
	{
		for (i = 0; i < field.length; i++) 
		{
			if(field[i].checked)
			{
				flag = true;
			}
		}
		if(field != null && flag == false && field.value != null)
		{
			if(field.checked)
			{
				flag = true;
			}
		}
	}
		
	return flag;
		
}

function purpose(form,field)
{
	form.action="Survey_RatingList.jsp?purpose="+field.value;
	form.method="post";
	form.submit();
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmAdd(form,field1,field2) 
function confirmAdd(form,field1,field2) 
{
    //\\ Confirm message added by Ha 29/05/08
	if(check(field1) || check(field2))
	{
	    if (confirm("Add Rating Tasks?"))
	    {
		form.action = "Survey_RatingList.jsp?add=1"
		form.method = "post";
		form.submit();
		}
	}
	else
	{
		alert("<%=trans.tslt("Please choose a rating task")%>");
	}
}


 </SCRIPT>

<body bgcolor="#DEE3EF">
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	window.close();
	opener.location.href = "index.jsp";
	</script>
<%  } 
  else 
  {

if(request.getParameter("purpose") != null)
{
	int var1 = new Integer(request.getParameter("purpose")).intValue();
	CE_Survey.setPurpose(var1);
	
	System.out.println("Purpose ID"+var1);
} else {
	// Edited by Eric Lu 22/5/08
	// Resets purpose if first time accessing this page
	CE_Survey.setPurpose(0);
}
if(request.getParameter("add") != null)
{
		String rating="";
		String scale="";
		boolean RequiredChosen = false;
		String [] chkSelect = new String[10];
		String [] ScaleDesc = new String[10];
		String [] radioSelect = new String[10];
		
		int SurveyID = CE_Survey.getSurvey_ID();
		ScaleDesc = request.getParameterValues("selScaleDesc");
		chkSelect = request.getParameterValues("chkRat");
		radioSelect = request.getParameterValues("radioRat");
		int count = 0;		// count shifted, Mark Oei 16 April 2010 
		

		/*------------------------------------------------- for checkbox ------------------------------------------------*/
		
		// Changed by Ha 28/05/09 to pop out message when action is done successfully
		if(chkSelect != null)
		{
		    boolean added = false;
		    count = 0;	//change to declare variable outside if statement, Mark Oei 16 April 2010
	    for(int i=0; i<chkSelect.length; i++)
		{
			rating =chkSelect[i];
			
			if(rating != null)
			{

				String RatName=" ";
				RatName = RT.getRatingTask(Integer.parseInt(rating));
				
				ScaleDesc = new String[10];
				ScaleDesc = request.getParameterValues("selScaleDesc"+rating);
				for(int j=0; j<ScaleDesc.length; j++)
				{
					scale = ScaleDesc[j];
					CE_Survey.editPurpose(SurveyID,CE_Survey.getPurpose());
					added = CE_Survey.addRating(SurveyID,scale,rating,RatName);
					if (added) count ++;
				}
			}
		}
		//Added to insert default values into database when rating tasks are added
		//Mark Oei 16 April 2010
	    int lLimit = 0;
	    int uLimit = 0;
	    int scaleRange = CE_Survey.getRatScale();

	    int taskCount = (Integer)session.getAttribute("taskCount") + count;
	    
		if ((taskCount == 1 && Integer.parseInt(rating) == 1)){
			lLimit = scaleRange/2 - 1;
			uLimit = scaleRange/2 + 1; 			
		} else {
			lLimit = -1;
			uLimit = 1;
		}
		boolean isSuccessful = CE_Survey.setMinPassScore(SurveyID, lLimit, uLimit);
		if(isSuccessful)
		{
			System.out.println("Default Limits "+lLimit + ", " + uLimit +" has been inserted successfully");
		}
		    if (count==chkSelect.length)
		    {%>
		    	
		    	<script>
                                // Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
				alert("Added successfully, survey status has been changed to Non Commissioned, to re-open survey please go to the Survey Detail page");
			    </script>
		    	
		    <% }
	    	
		}
		
		/*-------------------------------------------------------- for radiobox ----------------------------------------------*/
		
		rating="";

		if(radioSelect != null)
		{
		for(int i=0; i<radioSelect.length; i++)
		{
			rating =radioSelect[i];
			
			if(rating != null)
			{

				String RatName=RT.getRatingTask(Integer.parseInt(rating));
			
				ScaleDesc = new String[10];
				ScaleDesc = request.getParameterValues("selScaleDesc"+rating);
				for(int j=0; j<ScaleDesc.length; j++)
				{
					scale = ScaleDesc[j];
					CE_Survey.addRating(SurveyID,scale,rating,RatName);
				}
			}
		}
		}		
		
%>					
<script>window.close();
 		opener.location.href = 'SurveyRating.jsp';</script>
<%
	}
%>
<form name="Survey_RatingList" action="Survey_RatingList.jsp" method="post">

	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="3">
			<ul>
				<li><font face="Arial" size="2">
				<%=trans.tslt("To Select Rating Task, checked on the provided boxes under Select")%>.
				</font><font size="2"> </font>
				</li>
			</ul>
			</td>
		</tr>
		<tr>
			<td width="28%">
			<p align="center"><b><font face="Arial" size="2"><%=trans.tslt("Purpose of Survey")%>:</font></b></td>
			<td width="42%"><select size="1" name="selPurpose" onchange="purpose(this.form, this.form.selPurpose)">
			<option value ="0" selected><%=trans.tslt("Choose one")%></option>
<%
	Vector v = PRP.getAllPurpose();
			
	for(int i=0; i<v.size(); i++)
	{
		votblPurpose vo = (votblPurpose)v.elementAt(i);
		int PurposeID = vo.getPurposeID();
		String PurposeName = vo.getPurposeName();
		
		if(CE_Survey.getPurpose() == PurposeID)
		{
%>			
			<option value=<%=PurposeID%> selected><%=PurposeName%></option>
<%		}
		else
		{
%>			<option value=<%=PurposeID%>><%=PurposeName%></option>
<%		}	
		
	}%>				
	
			</select></td>
			
			<td width="30%"><b><font face="Arial" size="2"><%=trans.tslt("Scale Range Chosen")%>: <%=CE_Survey.getRatScale()%>
			</font></b></td>
			
		</tr>
		<tr>
			<td width="28%">&nbsp;
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td width="28%">&nbsp;
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
	<div style='width:946px; height:198px; z-index:1; overflow:auto'>  
		<table border="1" width="95%" bordercolor="#3399FF">
		<tr>
				<td colspan="3" bgcolor="#000080">
				<p align="center"><span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Rating Task")%></font></span></td>
			</tr>
		
		<tr>
				<td bgcolor="#000080" width="3%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" size="2" color="#FFFFFF"><%=trans.tslt("Select")%></font></span></td>
				<td bgcolor="#000080" width="23%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Rating Task")%></font></span></td>
				<td bgcolor="#000080" width="68%" align="center">
				<span style="font-weight: 700">
				<font face="Arial" color="#FFFFFF" size="2">
				<%=trans.tslt("Scale Description")%></font></span></td>
			</tr>
<%
	String query2="";
	boolean dunAddPR = false;
	
	int iRatingTaskID = SVR.getRatingTaskID(CE_Survey.getSurvey_ID());	
	if(iRatingTaskID != 0)
		dunAddPR = true;	
		
	boolean anyRecord = false;
	boolean anyscale = false;

	Vector vRT = RT.getRatingTask(CE_Survey.getSurvey_ID(), dunAddPR, CE_Survey.getPurpose());
	
		for(int i=0; i<vRT.size(); i++)
		{	
			anyRecord = true;
			votblRatingTask vo = (votblRatingTask)vRT.elementAt(i);
			int RatID = vo.getRatingTaskID();
			String RatName = vo.getRatingTask();
			String ScaleType = vo.getScaleType();
			
%>		
		  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFFF'">
    	
				<td bgcolor="#FFFFCC" width="3%" align="left">
				<p align="center">
				<font size="2" face="Arial">
				<%if(CE_Survey.getPurpose() != 9)
				  {%>

						</font><font face="Arial">
						<input type="checkbox" name="chkRat" checked value=<%=RatID%>></font><font size="2" face="Arial">

				<%  }
				  else
				  {
				  	if(RatID != 2 && RatID != 3)
					{%>
						</font><font face="Arial">
						<input type="checkbox" name="chkRat" value=<%=RatID%>></font><font size="2" face="Arial">
				<%	}
					else
					{	%>
						</font><font face="Arial">
						<input type="radio" name="radioRat" value=<%=RatID%>></font><font size="2" face="Arial">	
				<%	}	
				  }%>
				</font>
				</td>
				<td bgcolor="#FFFFCC" width="23%" align="left">
				<font face="Arial" size="2"><%=RatName%></FONT></td>
				
				<td bgcolor="#FFFFCC" width="68%" align="left">
				<p>
				<font face="Arial" style="font-size: 11pt"><select size="1" name=<%="selScaleDesc"+RatID%>>
<%
			Vector vScale = RS.getRatingScale(CE_Survey.getRatScale(), ScaleType, CE_Survey.get_survOrg());
			
			for(int j=0; j<vScale.size(); j++)
			{				
				votblScale voS = (votblScale)vScale.elementAt(j);
				anyscale = true;
				int ScaleID = voS.getScaleID();
				String ScaleDesc = voS.getScaleDescription();
%>
			<option value=<%=ScaleID%>><%=ScaleDesc%></option>
			
		
<%			}	%>
</select></font></td>
</tr>
<%		}		
		
		if(anyRecord == false && CE_Survey.getPurpose() != 0)
		{
		%>
		<tr>
    	
				<td bgcolor="#FFFFCC" width="96%" align="left" colspan="3">
				<p align="center">
				<font size="2" face="Arial"><%=trans.tslt("All the rating tasks under this purpose of survey have been added")%></font></td>
				
				</tr>

		<%}	%>
		</table>
		</div>
		<p>
<%		if(anyRecord && anyscale)
		{
		%>
		<input type="button" name="btnAddddtion" value="<%=trans.tslt("Add")%>" onclick="confirmAdd(this.form,this.form.chkRat,this.form.radioRat)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%		}	%>		
<input type="button" name="btnClose" value="<%=trans.tslt("Close")%>" onclick="window.close()">

	</p>

	</form>
<%	}	%>	
	</body>
	</html>
  