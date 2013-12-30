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
<jsp:useBean id="ImportLanguage" class="CP_Classes.ImportLanguage" scope="session"/>

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
	x = document.ImportLanguage
	var flag = true;
	if(flag)
	{
		form.action="ImportLanguage.jsp?preview=1";
		form.method="post";
		form.submit();
	}
	else
	{
		alert("<%=trans.tslt("Please complete the necessary fields")%>");
	}
}

function view(form)
{
	var query = "ImportLanguage.jsp?view=1";
	form.action = query;
	form.method = "post";
	form.submit();
}  
</script>
<body>
<font face="Arial">
<%

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%>
	<script>
		parent.location.href = "index.jsp";
	</script>
<%  } 
  else 
  { 

	if(request.getParameter("preview") != null)
	{
		//Import Type Code for URL String
		//    For 1 to 11 refer to ImportExport.jsp
		//	  12) Import Language
		
		int iLanguage = (request.getParameter("lang") == null)?0:Integer.parseInt(request.getParameter("lang"));
%>
			<script>
				var myWindow=window.open('ImportUpload.jsp?type=12&lang=<%=iLanguage%>','windowRef','scrollbars=no,width=400,height=200');
				myWindow.document.title="Import Language Translation";	
				myWindow.moveTo(x,y);
			</script>
<%
	}
	
	if(request.getParameter("view") != null)
	{
		String file_name="UploadLanguageTemplate.xls";
        String output = setting.getReport_Path_Template() + "\\" + file_name;
		File f = new File (output);
		response.reset();
		
		//set the content type(can be excel/word/powerpoint etc..)
		response.setContentType ("application/xls");
		//set the header and also the Name by which user will be prompted to save
		response.addHeader ("Content-Disposition", "attachment;filename=\"UploadLanguageTemplate.xls\"");
		
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
%>
</font>

<form name="ImportLanguage" action="ImportLanguage.jsp" method="post">
  <table border="0" width="505" cellspacing="0" cellpadding="0" style="border-width:0px; " bordercolor="#3399FF">
    <tr>
      <td width="505" colspan="6"><b> <font face="Arial" size="2" color="#000080"> <%=trans.tslt("Import Language")%></font></b></td>
    </tr>
    <tr>
      <td width="505" colspan="6">
      <ul>
      		<li><font face="Arial" size="2"><%=trans.tslt("Click 'View Template' to download the template")%>.</font></li>
      	   	<li><font face="Arial" size="2"><%=trans.tslt("Complete the Excel document")%>.</font></li>
           	<li><font face="Arial" size="2"><%=trans.tslt("Select the type of Language")%>.</font></li>
           	<li><font face="Arial" size="2"><%=trans.tslt("Click 'Process' to upload the Excel document")%>.</font></li>
      </ul>
      </td>
    </tr>
    <tr>
      <td colspan="6"><b> <font face="Arial" size="2"><%=trans.tslt("Language")%> : </font></b><font face="Arial" size="2"> <font face="Arial">
      
        <select size="1" name="lang">
		<% 	//Used array of string for language dropbox, Chun Yeong 13 Jul 2011
		
		int lang = 0;
		if(request.getParameter("lang") != null){
			lang = Integer.parseInt(request.getParameter("lang"));
		}
		//Modified by Yiping
		//10/01/2012
		//The sequence of the languages is not the same as in table TB_Translation
		String[] languages = {"English", "Indonesian", "Thai", "Chinese(Simplified)", "Chinese(Traditional)", "Korean"};
		for(int i=1; i<languages.length; i++) {
			if(lang == i) {
		%>
	        	<option value = <%=i%> selected><%=languages[i]%></option>
		<%  } else { %>
				<option value = <%=i%>><%=languages[i]%></option>
		<%	}
		}
		%>
			
		</select>
        </font></font></td>
      
    </tr>
    <tr>
      <td width="503" colspan="4"><font face="Arial"> 
        <%
		if(request.getParameter("path") != null) 
		{
			
			String reportPath = setting.getUploadPath() + "\\" + request.getParameter("path");
			
			if(request.getParameter("type") != null)
			{
				int iType = Integer.parseInt(request.getParameter("type"));
				
				int language = Integer.parseInt(request.getParameter("lang"));
				
				//Initial String[]
				String[] sImportStatus = {"",""};
				
				//Import Type Check
				if(iType == 12)
					sImportStatus = ImportLanguage.importFromFile(iType, language, reportPath);
%>
        <b><font color="#0000FF"><%=(sImportStatus[0].equals(""))?"":("<br>" + sImportStatus[0])%></font></b> </font>
<%
   			} //end if(request.getParameter("type") != null)
   		} //end if(request.getParameter("path") != null)
   		%>
      </td>
    </tr>
    <tr>
      <td  align="center"  style="border-top-style: none; border-top-width: medium">&nbsp;</td>
    </tr>
    <tr>
      <td width="27" align="center"><font face="Arial">&nbsp; </font> </td>
      <td width="172" align="center"><font face="Arial">
      		<input type="button" name="VTemplate" value="<%=trans.tslt("View Template")%>" onClick="view(this.form,1)"></font> </td>
      <td width="304" align="center" rowspan="2" colspan="2"><font size="2"> <font face="Arial">
        <% if(logchk.getCompany() != 2 || logchk.getUserType() == 1) {
%>
        		<input type="button" value="<%=trans.tslt("Process")%>" name="btnPreview" style="float: right"  onclick="chkSelect(this.form, this.form.lang)">
<%   
   			} else { 
%>
        		<input type="button" value="<%=trans.tslt("Process")%>" name="btnPreview" style="float: right" disabled>
<%
   			}
%>
        </font></td>
    </tr>
    <tr>
      <td width="458" align="center" colspan="5">&nbsp;</td>
    </tr>
    <tr>
    	<%@ include file="Footer.jsp"%>
    </tr>
  </table>
</form>
<font face="Arial">
<%	}	%>
</font>
</body>
</html>
