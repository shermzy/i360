<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Development Resources</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>


</head>

<body>

<jsp:useBean id="DP" class="CP_Classes.DevelopmentPlan" scope="session"/>
<jsp:useBean id="DPR" class="CP_Classes.DevelopmentPlanReport" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="SET" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "CP_Classes.vo.voDevelopmentPlan" %>
<%@ page import = "CP_Classes.vo.votblSurvey" %>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

var x = parseInt(window.screen.width) / 2 - 400;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 350; 


function check(field, option)
{
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	
	if( field == null ) {
		isValid = 2;
	
	} else {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked) {		
				clickedValue = field[i].value;
				//field[i].checked = false;
				isValid = 1;
			}
    
		if(isValid == 0 && field != null)  {
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
		}
    }
	
	if(option == 1) {
		if(isValid == 1)
			return clickedValue;
		else if(isValid == 0)
			alert("Please select a resource type");
		else if(isValid == 2)
			alert("No record available");
	} else {
	
		if(isValid == 1)
			return clickedValue;
		else if(isValid == 0)
			alert("No record selected");
		else if(isValid == 2)
			alert("No record available");
	
	}
	
	isValid = 0;	
	
}
//Edited by Xuehai 25 May 2011. Removed 'void' to enable running on Chrome&Firefox
//void function confirmAdd(form) {
function confirmAdd(form) {
//**************Edited by Tracy 12 Aug 08
//Add parameter "survey" to url link
	//Edited by Xuehai 26 May 2011, Changed 'DRAList.value' to 'document.DRAList.value'
	var myWindow=window.open('AddDevelopmentPlan.jsp?survey='+document.DRAList.Survey.value,'windowRef','scrollbars=yes, width=800, height=700');
//****************************************
	myWindow.moveTo(x,y);

}

//void function confirmEdit(form, field,survey) {	
function confirmEdit(form, field,survey) {	
	var value = check(field);
	//Edited by Xuehai 26 May 2011, Changed 'DRAList.value' to 'document.DRAList.value'
	if(value) {
		var myWindow=window.open('EditDevelopmentPlan.jsp?edit='+value+'&survey='+document.DRAList.Survey.value,'windowRef','scrollbars=yes, width=650, height=500');
		myWindow.moveTo(x,y);
	}
}

function confirmDelete(form, field) {
	var value = check(field);
	
	if(value) {
		if(confirm('<%=trans.tslt("Are you sure you want to delete this record")%> ?')) {
			// Changed by Tracy 26 Aug 08
			// Commented by Tracy 01 Sep 08
			//Edited by Xuehai 26 May 2011, Changed 'DRAList.value' to 'document.DRAList.value'
			form.action = "DevelopmentPlan.jsp?delete="+value+"&survey="+document.DRAList.Survey.value;
			// *******************************************
			form.method = "post";
			form.submit();
			return true;
	
		}
		else
			return false;
	}
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form, field)
{
	form.action="DevelopmentPlan.jsp?survey="+field.value;
	form.method="post";
	form.submit();
}

function confirmGenerate(form)
{
	if(form.Survey.value == 0) {
		alert("Please select Survey");
	} else {
		form.action="DevelopmentPlan.jsp?generate=1";
		form.method="post";
		form.submit();
	}
}
</script>
  

<%	
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);
	

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	

	int raterID = logchk.getPKUser();
	int DisplayNo, ResID, pkComp, fkComp, res;
	String resName [] = new String [6];

	resName[0] = trans.tslt("All");
	resName[1] = trans.tslt("Development Activities");
	resName[2] = trans.tslt("Books");
	resName[3] = trans.tslt("Web Resources");
	resName[4] = trans.tslt("Training Courses");
	resName[5] = trans.tslt("AV Resources"); // Change Resource category from "In-house Resources" to "AV Resources", Desmond 10 May 2011
	
	String CompName, DRARes, origin;
	DisplayNo = 1;
	ResID = 0;
	DRARes = "";
	CompName = "";
	pkComp = 0;
	fkComp = DP.getFKComp();
	res = DP.getOption();

	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	Vector vPlan = new Vector();

	if(request.getParameter("survey") != null)
	{ 
		session.setAttribute("surveyID",request.getParameter("survey"));
		int FKSurvey = new Integer(request.getParameter("survey")).intValue();
		
		DP.setFKSurvey(FKSurvey);
		//vPlan = DP.getDevelopmentPlan(raterID,0,0);
		//Edited by Roger 20 June 2008
		vPlan = DP.getDevelopmentPlanBySurveyId(raterID,0,0, FKSurvey);
		
	}
//***********Edited by Tracy 12 Aug 08*******************
// Display development list on page load
	else if(session.getAttribute("surveyID")!=null) {
		String strSurveyID= (String)session.getAttribute("surveyID");
		int FKSurvey = Integer.parseInt(strSurveyID);
		
		DP.setFKSurvey(FKSurvey);
		vPlan = DP.getDevelopmentPlanBySurveyId(raterID,0,0, FKSurvey);
//*********************************************************
	}
	// out.print(session.getAttribute("surveyID")); commented by Tracy 01 Sept 08	
	if(request.getParameter("generate") != null) {
		int FKSurvey = new Integer(request.getParameter("Survey")).intValue();
		
		java.util.Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		
		String file_name = "IndividualDevelopmentPlan"+temp+".xls";
		String displayed = "IndividualDevelopmentPlan";						
		
		DPR.Report(FKSurvey, raterID, file_name);	
		
		String output = SET.getReport_Path() + "/" + file_name;
		File f = new File (output);
		
		displayed += ".xls";
		
		//set the content type(can be excel/word/powerpoint etc..)
		response.reset();
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"" + displayed + "\"");
	
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
	
/*-------------------------------------------------------------------end login modification 1--------------------------------------*/


/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	/*int toggle = DRAResQuery.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		DRAResQuery.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		DRAResQuery.setSortType(type);									
	} */
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	
%>

<%
	// Moved by Tracy 26 Aug 08**************************
	// Commented by Tracy 01 Sept 08********************
	if(request.getParameter("delete") != null) {
		
		ResID = Integer.parseInt(request.getParameter("delete"));
		
		DP.deleteRecord(ResID);
		session.setAttribute("surveyID",request.getParameter("survey"));
		int FKSurvey = new Integer(request.getParameter("survey")).intValue();
		
		DP.setFKSurvey(FKSurvey);
		vPlan = DP.getDevelopmentPlanBySurveyId(raterID,0,0, FKSurvey);
%>		
	<script>
	alert("Deleted successfully");
	window.reload();
	</script>
<%
	}
	// End move by Tracy 26 Aug 08************************
	// End comment by Tracy 01 Sept 08********************

%>

<form name="DRAList" method="post" action="DevelopmentPlan.jsp">
<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	<tr>
	  <td colspan="4"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Individual Development Plan") %> </font></b></td>
    </tr>
	<tr>
	  <td colspan="4"><ul>
	    <li><font face="Arial" size="2"><%= trans.tslt("To display an existing Development Plan, select the appropriate survey and click on the Show My Plan button") %>.</font></li>
	    <li><font face="Arial" size="2"><%= trans.tslt("To create a new Development Plan, click on the Add button") %>.</font></li>
	    <li><font face="Arial" size="2"><%= trans.tslt("To edit/delete a Development Plan item, select the appropriate survey, click on the Show My Plan button, select the development item then click in the Edit/Delete button") %>.</font></li>
      
	  </ul></td>
	 <tr>
      <td width="131">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="355">&nbsp;</td>
      <td width="75" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td><%= trans.tslt("Survey") %></td>
      <td>:</td>
      <td><select name="Survey" onchange="proceed(this.form, this.form.Survey)">
        <option value=0 selected><%=trans.tslt("Please select one")%>
        <%
			//Edit by Roger 1 July 2008
			//display survey base on target instead
			//Vector vSurvey = DP.getSurveys(pkUser);
			Vector vSurvey = DP.getSurveysByTargetDevCompetency(pkUser);
			for(int i=0; i<vSurvey.size(); i++) {
				votblSurvey vo = (votblSurvey)vSurvey.elementAt(i);
			
				int pkSurvey = vo.getSurveyID();
				String sSurveyName = vo.getSurveyName();
				if(pkSurvey != 0 && pkSurvey == DP.getFKSurvey()) {
		%>
        <option value = <%=pkSurvey%> selected><%=sSurveyName%>
        <%		

				}else {
		%>
        <option value = <%=pkSurvey%>><%=sSurveyName%>
        <%
				}
			}
		%>
        </select></td>
      <td align="left"><input type="button" value="<%= trans.tslt("Show My Plan") %>" name="btnShow" onclick="proceed(this.form, this.form.Survey)"></td>
    </tr>	
    <tr>
      <td width="131">&nbsp;</td>
      <td width="18">&nbsp;</td>
      <td width="355">&nbsp;</td>
      <td width="75" align="center">&nbsp;</td>
    </tr>
 </table>
  
<p></p>


<div style="width:720px; height:300px; z-index:1; overflow:auto;"> 
<table width="700" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
<th width="20" bgcolor="navy">&nbsp;

</th>
<th width="25" bgcolor="navy"><b><font span style='font-family:Arial;color:white'>No</font></b></th>
<th bgcolor="navy" width="100"><a href="DevelopmentPlan.jsp?name=1"><b>
<font span style='font-family:Arial;color:white'><u><%= trans.tslt("Competency") %></u></font></b></a></th>
<th bgcolor="navy" align="center" width="240"><a href="DevelopmentPlan.jsp?name=2"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Development Activities/Resources") %></u></font></b></a></th>
<th bgcolor="navy" align="center" width="70"><a href="DevelopmentPlan.jsp?name=2"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Proposed Completion Date") %></u></font></b></a></th>
<th bgcolor="navy" align="center" width="129"><div align="center"><a href="DevelopmentPlan.jsp?name=2"><b>
  <font style='font-family:Arial;color:white'><u><%= trans.tslt("Development Preview Process") %></u></font></b></a></div></th>
<th bgcolor="navy" align="center" width="70"><a href="DevelopmentPlan.jsp?name=2"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Actual Completion Date") %></u></font></b></a></th>
<% 	
	/*String arr[] = {"Completion of activity or feedback from supervisor and peers", "Book summary or book review discussion with supervisor", "Course completion certificate"};
	String resources[] = {"Identify effective decision makers to 'bounce ideas off' prior to finalizing a decision.",
	 					"Innovation at Work, Katherine E. Holt, Ph.D, Publisher: ASTD, March 2003, ISBN: 1-56286-351-7",
						"Creating and Marketing Innovative Products: From Concept to Commercialisation, www.nus.edu.sg/nex"};
	String competency[] = {"Decision Making (Problem Solving)",
	 					"Innovation",
						"Innovation"};
	String completion []= {"18 Oct 2007",
	 					"&nbsp;",
						"&nbsp;"};
	String timeframe[]= {"18 Oct 2007",
	 					"31 Oct 2007",
						"02 Dec 2007"};*/
	
	
	for(int i=0; i<vPlan.size(); i++) {
		voDevelopmentPlan voPlan = (voDevelopmentPlan)vPlan.elementAt(i);
		
		String arr = voPlan.getProcess();
		String resources = voPlan.getResource();
		String competency = voPlan.getCompetencyName();
		String completion = voPlan.getCompletionDate();
		String timeframe = voPlan.getProposedDate();
		ResID = voPlan.getPKDevPlan();
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFCC'">
       <td align="center">
	   		<input type="radio" name="radioDRARes" value=<%=ResID%>><font size="2">
	   </td>
	   	<td align="center">
   		  <% out.print(DisplayNo);%></td>
	   <td>
		<%=competency%>
	   </td>
	   <td align="left">
		<%=resources%>
	   </td>
	   <td align="center">
		<%=timeframe%>
        </td>
	   <td align="left">
		<%=arr%>
	   </td>
	   <td align="center">
		<%
		if(completion.equals(""))  {
		 	out.print("&nbsp;");
		} else {
			out.print(completion);
		}
		
		%>
        </td>
   </tr>
<% 	DisplayNo++;
	// Edited by Tracy 26 aug 08
	// Commented by Tracy 01 Sep 08
	} }
	// End comment by tracy 01 Sep 08
	
%>
</table>
</div>

<p></p>
<input type="button" name="btnAdd" value="<%= trans.tslt("Add") %>" onClick="confirmAdd(this.form)">
<input type="button" name="btnEdit" value="<%= trans.tslt("Edit") %>"  onclick = "confirmEdit(this.form, this.form.radioDRARes,this.form.Survey)">
<input type="button" name="btnDelete" value="<%= trans.tslt("Delete") %>"  onclick = "return confirmDelete(this.form, this.form.radioDRARes)">
<input type="button" name="btnGenerate" value="<%= trans.tslt("Print Development Plan") %>"  onclick = "return confirmGenerate(this.form)">&nbsp; </form>


<p></p>
<div align="center">
  <%@ include file="Footer.jsp"%>
</div>
</body>
</html>