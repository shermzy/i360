<%@ page import="java.sql.*,
                 java.io.*,
                 javazoom.upload.*,
				 java.util.*"
				 %>
				 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<title>Import Questionnaire</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
<jsp:setProperty name="upBean" property="folderstore"/>
</jsp:useBean>

<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<script language="javascript">
</script>
<body>

<%
      if (MultipartFormDataRequest.isMultipartFormData(request))
      {
         // Uses MultipartFormDataRequest to parse the HTTP request.
         MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
         String todo = null;
         if (mrequest != null) todo = mrequest.getParameter("todo");
	 if ( (todo != null) && (todo.equalsIgnoreCase("upload")) )
	 {
                Hashtable files = mrequest.getFiles();
				upBean.setFolderstore(setting.getUploadPath());
				upBean.setOverwrite(true);
				
                if ( (files != null) && (!files.isEmpty()) )
                {
                    UploadFile file = (UploadFile) files.get("uploadfile");
                    if (file != null)  {
						String fileName = file.getFileName();
						if(fileName != null) {
						//out.println("<li>Form field : uploadfile"+"<BR> Uploaded file : "+file.getFileName()+" ("+file.getFileSize()+" bytes)"+"<BR> Content Type : "+file.getContentType());
%>
<script>
	window.close();
	opener.location.href = "RatersDataEntry.jsp?type=2&path=" + "<%=fileName%>";
</script>
<%						
						}else {
%>
<script>
	alert("<%=trans.tslt("Please select a file to be uploaded")%>");
</script>
<%						
						}
					}
                    	// Uses the bean now to store specified by jsp:setProperty at the top.
                    upBean.store(mrequest, "uploadfile");
                }
                else
                {
                  out.println("<li>No uploaded files");
                }
	 }
         else out.println("<BR> todo="+todo);
      }
%>

<form method="post" action="ImportQuestionnaire.jsp" name="upform" enctype="multipart/form-data">
<p style='font-size:10.0pt;font-family:Arial'>
<%=trans.tslt("Please click the browse button and choose the excel file to be imported") %>.
</p>
<input name="uploadfile" type="file" size="40">
<p></p>
<input type="hidden" name="todo" value="upload">
<input type="submit" name="Submit" value="<%= trans.tslt("Upload") %>">
<input type="reset" name="Reset" value="<%= trans.tslt("Cancel") %>" onClick=window.close()>
</form>
</body>
</html>
