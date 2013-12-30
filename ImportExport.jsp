<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
				 CP_Classes.vo.votblSurvey,
				 CP_Classes.vo.votblOrganization
"%>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="Rpt9" class="CP_Classes.DevelopmentGuide" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="user" class="CP_Classes.User" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="userJ" class="CP_Classes.User_Jenty" scope="session"/>
<jsp:useBean id="ExportExcel" class="CP_Classes.ExportQuestionnaire" scope="session"/>
<jsp:useBean id="Export" class="CP_Classes.Export" scope="session"/>
<jsp:useBean id="Import" class="CP_Classes.Import" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE="JavaScript">
var target
var x = parseInt(window.screen.width) / 2 - 500;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 300;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up
		
function check(field)
{
	var check= false;
	
	for (i = 0; i < field.length; i++) 
	{
		if(field[i].checked)
		{
			target = field[i].value;
			check = true;
		}
		else if(field[i].selected != "" && check == false)
		{
			if(field[i].value != "")
			{
				check = true;
			}
		}
	}

	return check;
}

function chkSelect(form,field)
{	
	x = document.ImportExport
	var flag = false;
	if(check(field))
	{	
		if(target == 1) //Import User
		{
			flag = true;
		}
		else if(target == 2) //Import Assignment
		{
			if(check(x.ddAssign))
				flag = true;
		}
		else if(target == 3) //Import System Libraries
		{
			if(check(x.ddSysLib))
				flag = true;
		}
		else if(target == 4) //Import Setup
		{
			if(check(x.ddAdmin))
				flag = true;
		}
		else if (target == 5)//Import Questionnaire 04 Jan 2010 Qiao Li
		{
			flag = true;
		}
		else if (target == 6) //Import Nomination 08/07/2013 xukun
		{
			flag = true;
		}
		else if(target == 100) //Export User
		{
			flag = true;
		}
		else if(target == 101) //Export Assignment
		{
			//Check Survey Name chosen
			if(check(x.selSurvey0))
				flag = true;
		}
		else if(target == 102) //Export System Libraries
		{
			if(check(x.ddExpSysLib))
				flag = true;
		}
		else if(target == 103)
		{
			if(check(x.ddExpAdmin))
				flag = true;
		}
		if(flag)
		{
			form.action="ImportExport.jsp?preview=1";
			form.method="post";
			form.submit();
		}
		else
		{
			alert("<%=trans.tslt("Please complete the necessary fields")%>");
		}
	}
}

function proceed(form,field)
{
	form.action="ImportExport.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

</script>
<body>
<font face="Arial">
<%

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%>
</font> <font size="2"> <font face="Arial">
<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  { 

if(request.getParameter("proceed") != null)
{ 
	int var2 = new Integer(request.getParameter("selOrg")).intValue();
	logchk.setOrg(var2);
}
/*
 *  Updated Popup code to point to common JSP file
 *  
 */
if(request.getParameter("preview") != null)
{
	//Type = 1 : Import User
	//Type = 2 : Import Assignment
	//Type = 3 : Import System Libraries
	//Type = 4 : Import Administration
	//Type = 5 : Import Survey
		
	//Import Type Code for URL String
    //    1) Import User
    //    2) Import Assignment - Target & Rater
    //    3) Import Assignment - Target Only
    //    4) Import Competency
    //    5) Import Key Behaviour
    //    6) Import Development Activities
    //    7) Import Development Resources
    //    8) Import Division
    //    9) Import Department
    //    10) Import Group/Section
	//	  11) Import Questionnaire
	//    12) Import Cluster
	String Survey_Name = "";
	int Type = new Integer(request.getParameter("chkType")).intValue();
	
	if(Type == 1)
	{
		//Import User
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=1','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title="Import User";	
			myWindow.moveTo(x,y);
		</script>
<%
	}
    else if(Type == 2)
    {
    	//Import Assignment
    	if(Integer.parseInt(request.getParameter("ddAssign")) == 1)
    	{
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=2','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Assignment";	
			myWindow.moveTo(x,y);
		</script>
<%
		}
		else if(Integer.parseInt(request.getParameter("ddAssign")) == 2) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=3','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Assignment";
			myWindow.moveTo(x,y);
		</script>
<%
		}
    }
    else if(Type == 3)
    {
    	//Import System Libraries
    	int iImportType;
    	
    	if(Integer.parseInt(request.getParameter("ddSysLib")) == 1) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=4','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import System Library";
			myWindow.moveTo(x,y);	
		</script>
<%
		} else if(Integer.parseInt(request.getParameter("ddSysLib")) == 2) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=5','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import System Library";
			myWindow.moveTo(x,y);	
		</script>
<%
		} else if(Integer.parseInt(request.getParameter("ddSysLib")) == 3) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=6','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import System Library";
			myWindow.moveTo(x,y);	
		</script>
<%
		} else if(Integer.parseInt(request.getParameter("ddSysLib")) == 4) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=7','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import System Library";
			myWindow.moveTo(x,y);			
		</script>
<%
		}
		else if(Integer.parseInt(request.getParameter("ddSysLib")) == 5){
			%>
			<script>
				var myWindow=window.open('ImportUpload.jsp?type=13','windowRef','scrollbars=no,width=400,height=200');
				myWindow.document.title = "Import System Library";
				myWindow.moveTo(x,y);			
			</script>
	<%
			
		}
    }
    else if(Type == 4)
    {
    	//Import Administration
    	int iImportType;
    	
    	if(Integer.parseInt(request.getParameter("ddAdmin")) == 1) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=8','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.Title = "Import Administration";
			myWindow.moveTo(x,y);
		</script>
<%
    	} else if(Integer.parseInt(request.getParameter("ddAdmin")) == 2) {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=9','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Administration";		
			myWindow.moveTo(x,y);
		</script>
<%
    	} else {
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=10','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Administration";	
			myWindow.moveTo(x,y);
		</script>
<%
		}
    }
	//added in function of importing Questionnaire 04 Jan 2010 Qiao Li
	else if(Type == 5)
	{
		//Import Questionnaire
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=11','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Questionnaire";
			myWindow.moveTo(x,y);
		</script>
<%
	}
	else if(Type == 6)
	{
		//Import nomination
%>
		<script>
			var myWindow=window.open('ImportUpload.jsp?type=14','windowRef','scrollbars=no,width=400,height=200');
			myWindow.document.title = "Import Nomination";
			myWindow.moveTo(x,y);
		</script>
<%
	}
	else if ((Type == 100) || (Type == 101) || (Type == 102) || (Type == 103))
	{
		//Type = 100 : Export User
		//Type = 101 : Export Assignment
		//Type = 102 : Export System Libraries
		//Type = 103 : Export Administration
		//Type = 104 : Export Survey
		
		String file_name = "";
		int iExportType = 0;
		
		if (Type == 100)
		{
			file_name = "ExportUser.xls";
			iExportType = 1;
		}
		else if (Type == 101)
		{
			if(Integer.parseInt(request.getParameter("chkExportAssign")) == 1)
			{	//All
				iExportType = 3; 
				file_name = "ExportAssignment.xls";
			}
			else
			{	//Target Only
				iExportType = 2;
				file_name = "ExportTarget.xls";
			}
				
			Export.setSurveyID(Integer.parseInt(request.getParameter("selSurvey0")));
		}
		else if (Type == 102)
		{
			if(Integer.parseInt(request.getParameter("ddExpSysLib")) == 1)
			{	//COMP
				iExportType = 4;
				file_name = "ExportCompetency.xls";
			}
			else if(Integer.parseInt(request.getParameter("ddExpSysLib")) == 2)
			{	//KB
				iExportType = 5;
				file_name = "ExportBehaviour.xls";
			}
			else if(Integer.parseInt(request.getParameter("ddExpSysLib")) == 3)
			{	//DA
				iExportType = 6;
				file_name = "ExportDevelopmentActivities.xls";
			}
			else if(Integer.parseInt(request.getParameter("ddExpSysLib")) == 5)
			{	//DA
				iExportType = 11;
				file_name = "ExportCluster.xls";
			}
			else
			{	//DR
				iExportType = 7;
				file_name = "ExportDevelopmentResources.xls";
			}
		}
		else if (Type == 103)
		{
			if(Integer.parseInt(request.getParameter("ddExpAdmin")) == 1)
			{	//DIV
				iExportType = 8;
				file_name = "ExportDivision.xls";
			}
			else if(Integer.parseInt(request.getParameter("ddExpAdmin")) == 2)
			{	//DEPT
				iExportType = 9;
				file_name = "ExportDepartment.xls";
			}
			else
			{	//GRP
				iExportType = 10;
				file_name = "ExportGroup.xls";
			}
		}	
		
		Export.setOrgID(logchk.getOrg()); //Set OrgID first
		System.out.println("Setting OrgID = " + logchk.getOrg() + "\nFileName = " + file_name);

		Export.export(iExportType, logchk.getPKUser());
		
		//read the file name.		
		String output = setting.getReport_Path() + "\\"+file_name;
		
		File f = new File (output);
	
                response.reset();
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"" + file_name + "\"");
	
		//get the file name
		String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
		//OPen an input stream to the file and post the file contents thru the 
		//servlet output stream to the client m/c
		
		InputStream in = new FileInputStream(f);
		ServletOutputStream outs = response.getOutputStream();
		
		int bit = 256;
		int i = 0;
	
	   	try 
	   	{
	       	while ((bit) >= 0) 
	       	{
	       		bit = in.read();
	       		outs.write(bit);
	       	}
	       	//System.out.println("" +bit);
	   	} catch (IOException ioe) 
	  	{
	     	ioe.printStackTrace(System.out);
	    }
	    outs.flush();
	    outs.close();
	    in.close();

	}
	
} //end if(request.getParameter("preview") != null)
%>
</font>

<form name="ImportExport" action="ImportExport.jsp" method="post">
  <table border="0" width="505" cellspacing="0" cellpadding="0" style="border-width:0px; " bordercolor="#3399FF">
    <tr>
      <td width="505" colspan="6"><b> <font face="Arial" size="2" color="#000080"> <%=trans.tslt("Import and Export")%></font></b></td>
    </tr>
    <tr>
      <td >&nbsp;</td>
    </tr>
    <tr>
      <td width="199" colspan="2"><b> <font face="Arial" size="2"><%=trans.tslt("Organisation")%> : </font></b><font face="Arial" size="2"> <font face="Arial">
        <select size="1" name="selOrg" onChange="proceed(this.form,this.form.selOrg)">
          <%
      	// Added to check whether organisation is also a consulting company
      	// if yes, will display a dropdown list of organisation managed by this company
      	// else, it will display the current organisation only
      	// Mark Oei 09 Mar 2010
      	String [] UserDetail = new String[14];
      	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
      	boolean isConsulting = true;
      	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
      	if (isConsulting){
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
      			<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
      		<% } // End of isConsulting %>
        </select>
        </font></font></td>
      <td width="191"><p align="left"> <font size="2" face="Arial"> &nbsp;</font></td>
      <td width="113"></td>
      <td width="1" rowspan="2" ></td>
    </tr>
    <tr>
      <td width="503" colspan="4"><font face="Arial"> 
        <%
		if(request.getParameter("path") != null) 
		{
			/*
			 * Updated Import Status handling code
			 * By: Chun Pong
			 * Date: 03 Jul 2008
			 */
			String reportPath = setting.getUploadPath() + "\\" + request.getParameter("path");
			int overwrite = ExportExcel.getOverwrite();
			
			if(request.getParameter("type") != null)
			{
				int iType = Integer.parseInt(request.getParameter("type"));
				System.out.println("iType = "+iType);
				//Added Import Type Check Code to prevent invalid import type intermittent error from happening
				//By: Chun Pong
				//Date: 10 Jul 2008
				
				//Initial String[]
				String[] sImportStatus = {"",""};
				
				//Import Type Check
				//added in one more type: quesionnaire (Qiao Li 04 Jan 2009)
				//Added getUserType to method parameters list to prevent importing of another SA account or 
				//allow an Admin to import an Admin account  Mark Oei 11 Mar 2010
				if(iType > 0 && iType < 15){
					sImportStatus=Import.importFromFile(iType, reportPath, logchk.getCompany(), logchk.getOrg(), logchk.getCompanyName(), logchk.getOrgCode(),true, username, logchk.getUserType());
				}
%>
        <b><font color="#0000FF"><%=(sImportStatus[0].equals(""))?"":("<br>" + sImportStatus[0])%></font></b> </font>
<%
   			} //end if(request.getParameter("type") != null)
   		} //end if(request.getParameter("path") != null)
   		%>
      </td>
    </tr>
    <tr>
      <td width="199" align="center" height="25" bordercolor="#000080" colspan="2"><font face="Arial"> <br>
        </font>
        <p align="left"><font face="Arial"><b><%=trans.tslt("Import")%></b></font></td>
      <td width="305" height="25" colspan="3" bordercolor="#000080">&nbsp;</td>
      <td width="1" height="25"></td>
    </tr>
    <tr>
      <td width="26" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font size="2"> <font face="Arial">
        <input type="radio" value="1" checked name="chkType">
        </font> </td>
      <td width="172" align="center" height="25" bordercolor="#3399FF" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("User")%></font></b></td>
      <td width="304" height="25" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <% if(logchk.getCompany() == 2) //Demo
	{%>
    <tr>
      <td width="26" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font size="2"> <font face="Arial">
        <input type="radio" value="5" name="chkType">
        </font> </td>
      <td width="172" align="center" height="25" bordercolor="#3399FF" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Survey")%></font></b></td>
      <td width="304" height="25" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <%}%>
    <tr>
      <td width="26" align="center" height="25" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font size="2"> <font face="Arial">
        <input type="radio" value="2" name="chkType">
        </font> </td>
      <td width="172" align="center" height="25" bordercolor="#3399FF" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Assignment")%></font></b></td>
      <td width="304" height="25" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:solid; border-top-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center" height="22" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><font face="Arial" size="2"><%=trans.tslt("Import Type")%>:</font></td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font face="Arial" size="2">
        <!-- Updated option 1 from "Complete" to "Target & Rater" and removed option 3 -->
        <select size="1" name="ddAssign">
          <option selected value="1">Target & Rater</option>
          <option value="2">Target Only</option>
        </select>
        </font> </td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><input type="radio" value="3" name="chkType"></td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("System Library")%></font></b></td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><font face="Arial" size="2"><%=trans.tslt("Import Type")%>:</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font face="Arial" size="2">
        <select size="1" name="ddSysLib">
         <%
          /*
          *Change(s):added another option Cluster
          *Reason(s):To allow the importing of cluster
          *Updated By: Liu Taichen
          *Updated On: 20/06/2012
          */
        %>
          <option value="5">Cluster </option>
          <option selected value="1">Competency</option>
          <option value="2">Key Behaviour</option>
          <option value="3">Development Activities</option>
          <option value="4">Development Resources </option>
         
        </select>
        </font></td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><input type="radio" value="4" name="chkType"></td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Administration")%></font></b></td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-style:none; border-width:medium; " bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><font face="Arial" size="2"><%=trans.tslt("Import Type")%>:</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><font face="Arial" size="2">
        <select size="1" name="ddAdmin">
          <option selected value="1">Division</option>
          <option value="2">Department</option>
          <option value="3">Group / Section </option>
        </select>
        </font></td>
    </tr>
	<% //Added in one more selection for questionnaires by Qiao Li 30 Dec 2009%>
	 <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
	<tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><input type="radio" value="5" name="chkType"></td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Questionnaire")%></font></b></td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <% //Added in one more selection for nomination by Xukun  8/7/2013%>
    <tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: solid; border-bottom-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
	<tr>
      <td width="26" align="center" height="22" style="border-left-style: solid; border-left-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-right-style:none; border-right-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><input type="radio" value="6" name="chkType"></td>
      <td width="172" align="center" height="22" style="border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Nomination")%></font></b></td>
      <td width="304" height="22" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-bottom-style: none; border-bottom-width: medium; border-left-style:none; border-left-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="457" align="center" colspan="5" style="border-left-style: solid; border-left-width: 1px; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
    </tr>
    <tr>
      <td width="458" align="center" colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td width="458" align="center" colspan="5"><p align="left"><font face="Arial"><b><%=trans.tslt("Export")%></b></font></td>
    </tr>
    <tr>
      <td width="26" align="center" style="border-left-style: solid; border-left-width: 1px; border-top-style: solid; border-top-width: 1px; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="41"><font size="2"> <font face="Arial">
        <input type="radio" value="100" name="chkType">
        </font> </td>
      <td width="172" align="center" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="41"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("User")%></font></b></td>
      <td width="304" align="center" colspan="3" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="41"><font face="Arial">&nbsp; </font> </td>
    </tr>
    <% if(logchk.getCompany()== 2) //Demo
	{%>
    <tr>
      <td width="26" align="center" style="border-left-style: solid; border-left-width: 1px; border-top-style: solid; border-top-width: 1px; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="27"><font size="2"> <font face="Arial">
        <input type="radio" value="105" name="chkType">
        </font> </td>
      <td width="172" align="center" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="27"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Survey")%></font></b></td>
      <td width="304" align="center" colspan="3" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="27"><font face="Arial">&nbsp; </font> </td>
    </tr>
    <tr>
      <td width="26" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="39"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center" bordercolor="#3399FF" style="border-style: none; border-width: medium" bgcolor="#FFFFCC" height="39"><p align="left"><font face="Arial" size="2">Survey Name:</font></td>
      <td width="304" align="center" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="39"><p align="left"> <font face="Arial" size="2">
          <select size="1" name="selSurvey0">
            <%    	if(CE_Survey.getSurvey_ID() == 0)
		{	%>
            <option value="" selected>&nbsp;</option>
            <%		}
	/***********************************
	*Edit By James 15-Nov 2007
	**********************************/
	
	Vector rs_SurveyDet = CE_Survey.getSurveys(logchk.getCompany(),logchk.getOrg());	
	
	//while(rs_SurveyDet.next())
	for(int i=0;i<rs_SurveyDet.size();i++)
	{
		votblSurvey vo=(votblSurvey)rs_SurveyDet.elementAt(i);
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
		
	if(CE_Survey.getSurvey_ID() == Surv_ID)
	{
%>
            <option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
            <%	}
	else	
	{%>
            <option value=<%=Surv_ID%> ><%=Surv_Name%></option>
            <%	}	
}%>
          </select>
          </font>&nbsp<br>
      </td>
    </tr>
    <% }%>
    <tr>
      <td width="26" align="center" style="border-left-style: solid; border-left-width: 1px; border-top-style: solid; border-top-width: 1px; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="57"><font size="2"> <font face="Arial">
        <input type="radio" value="101" name="chkType">
        </font> </td>
      <td width="172" align="center" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="57"><p align="left"><b><font face="Arial" size="2" color="#000080"> <%=trans.tslt("Assignment")%></font></b></td>
      <td width="304" align="center" colspan="3" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="57"><font face="Arial">&nbsp; </font> </td>
    </tr>
    <tr>
      <td width="26" align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style:none; border-right-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center" bordercolor="#3399FF" style="border-style: none; border-width: medium" bgcolor="#FFFFCC"><p align="left"><font face="Arial" size="2"><%=trans.tslt("Survey Name")%>:</font></td>
      <td width="304" align="center" colspan="3" style="border-right-style: solid; border-right-width: 1px; border-left-style:none; border-left-width:medium; border-top-style:none; border-top-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC"><p align="left"> <font face="Arial" size="2">
          <select size="1" name="selSurvey0">
            <%    	if(CE_Survey.getSurvey_ID() == 0)
		{	%>
            <option value="" selected>&nbsp;</option>
            <%		}
	/***********************************
	*Edit By James 15-Nov 2007
	**********************************/
	
	Vector rs_SurveyDet = CE_Survey.getSurveys(logchk.getCompany(),logchk.getOrg());	
	
	//while(rs_SurveyDet.next())
	for(int i=0;i<rs_SurveyDet.size();i++)
	{
		votblSurvey vo=(votblSurvey)rs_SurveyDet.elementAt(i);
		int Surv_ID = vo.getSurveyID();
		String Surv_Name = vo.getSurveyName();
		
	if(CE_Survey.getSurvey_ID() == Surv_ID)
	{
%>
            <option value=<%=Surv_ID%> selected><%=Surv_Name%></option>
            <%	}
	else	
	{%>
            <option value=<%=Surv_ID%> ><%=Surv_Name%></option>
            <%	}	
}%>
          </select>
          </font></td>
    </tr>
    <tr>
      <td width="457" align="left" colspan="5" style="border-left-style: solid; border-left-width: 1px; border-right-style:solid; border-right-width:1px; border-top-style:none; border-top-width:medium; border-bottom-style:solid; border-bottom-width:1px" bordercolor="#3399FF" bgcolor="#FFFFCC"><font size="2">
        <table border="0" width="440" id="table1" cellspacing="1">
          <tr>
            <td width="22" align="left">&nbsp;</td>
            <td width="165" align="left"><font face="Arial" size="2"><%=trans.tslt("Export Type")%> :</font></td>
            <td width="26" align="left"><p align="left"> <font face="Arial" size="2">
                <input type="radio" name="chkExportAssign" checked value="1">
                </font></td>
            <td width="215" align="left"><font face="Arial" size="2"><%=trans.tslt("Complete")%></font></td>
          </tr>
          <tr>
            <td width="22" valign="top" align="left">&nbsp;</td>
            <td width="165" valign="top" align="left">&nbsp;</td>
            <td width="26" valign="top" align="left"><font size="2"> <font face="Arial">
              <input type="radio" name="chkExportAssign" value="2">
              </font></td>
            <td width="215" align="left"><font face="Arial" size="2"><%=trans.tslt("Target Only")%></font></td>
          </tr>
          <tr>
            <td width="22" valign="top" align="left">&nbsp;</td>
            <td width="165" valign="top" align="left">&nbsp;</td>
            <td width="26" valign="top" align="left">&nbsp;</td>
            <td width="215" align="left">&nbsp;</td>
          </tr>
        </table>
        <table border="0" id="table2" width="500" cellspacing="0" bordercolor="#3399FF">
          <tr>
            <td width="22" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium"><font face="Arial" size="2">
              <input type="radio" value="102" name="chkType">
              </font></td>
            <td width="172" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium"><b> <font color="#000080" face="Arial" size="2"><%=trans.tslt("System Library")%></font></b></td>
            <td style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: solid; border-top-width: 1px; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
          </tr>
          <tr>
            <td width="22" style="border-style: none; border-width: medium">&nbsp;</td>
            <td width="172" style="border-style: none; border-width: medium"><font face="Arial" size="2"><%=trans.tslt("Export Type")%> :</font></td>
            <td style="border-style: none; border-width: medium"><font face="Arial" size="2">
              <select size="1" name="ddExpSysLib">
                <option value="5">Cluster </option>
                <option selected value="1">Competency</option>
                <option value="2">Key Behaviour</option>
                <option value="3">Development Activities</option>
                <option value="4">Development Resources </option>
                
              </select>
              </font></td>
          </tr>
          <tr>
            <td width="22" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;</td>
            <td width="172" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;</td>
            <td style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px">&nbsp;</td>
          </tr>
          <tr>
            <td width="22" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-bottom-style: none; border-bottom-width: medium"><font face="Arial" size="2">
              <input type="radio" value="103" name="chkType">
              </font></td>
            <td width="172" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-bottom-style: none; border-bottom-width: medium"><b> <font color="#000080" face="Arial" size="2"><%=trans.tslt("Administration")%></font></b></td>
            <td style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-bottom-style: none; border-bottom-width: medium">&nbsp;</td>
          </tr>
          <tr>
            <td width="22" style="border-style: none; border-width: medium">&nbsp;</td>
            <td width="172" style="border-style: none; border-width: medium"><font face="Arial" size="2"><%=trans.tslt("Export Type")%> :</font></td>
            <td style="border-style: none; border-width: medium"><font face="Arial" size="2">
              <select size="1" name="ddExpAdmin">
                <option selected value="1">Division</option>
                <option value="2">Department</option>
                <option value="3">Group / Section </option>
              </select>
              </font></td>
          </tr>
          <tr>
            <td width="22" style="border-style: none; border-width: medium">&nbsp;</td>
            <td width="172" style="border-style: none; border-width: medium">&nbsp;</td>
            <td style="border-style: none; border-width: medium">&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td  align="center"  style="border-top-style: none; border-top-width: medium">&nbsp;</td>
    </tr>
    <tr>
      <td width="27" align="center"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center"><font face="Arial">&nbsp; </font> </td>
      <td width="304" align="center" rowspan="2" colspan="2"><font size="2"> <font face="Arial">
        <% if(logchk.getCompany() != 2 || logchk.getUserType() == 1) {
%>
        <input type="button" value="<%=trans.tslt("Process")%>" name="btnPreview" style="float: right"  onclick="chkSelect(this.form,this.form.chkType)">
        <%   
   } else { 
   %>
        <input type="button" value="<%=trans.tslt("Process")%>" name="btnPreview" style="float: right" disabled>
        <%
   } %>
        </font></td>
    </tr>
    <tr>
      <td width="27" align="center"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center"><font size="2">
        <p align="right"> </td>
    </tr>
  </table>
</form>
<font face="Arial">
<%	}	%>
</font>
<%@ include file="Footer.jsp"%>
</body>
</html>
