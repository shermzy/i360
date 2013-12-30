<%@ page import="java.sql.*,
                 java.io.*,
                 javazoom.upload.*,
				 java.util.*,
				 CP_Classes.vo.*"
				 %>
				 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Upload Logo</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
.style3 {color: #000066; font-weight: bold; }
-->
</style>
</head>

<title>Import Questionnaire</title>
<meta http-equiv="Content-Type" content="text/html">

<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
  <jsp:setProperty name="upBean" property="folderstore"/>
</jsp:useBean>

<jsp:useBean id="org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<body>

<%

	//String command = "SELECT * FROM tblOrganization WHERE PKOrganization= "+ logchk.getOrg();
	/*********************************
	*Edit By James 15 - Nov 2007
	*********************************/
	//ResultSet rs = db.getRecord(command);
	votblOrganization vo_Org=org.getOrganization(logchk.getOrg());
	String orgName = "";
	
	if(vo_Org!=null)
		orgName = vo_Org.getOrganizationName();

	
	if (MultipartFormDataRequest.isMultipartFormData(request)) {
        // Uses MultipartFormDataRequest to parse the HTTP request.
        MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
        String todo = null;
        if (mrequest != null) todo = mrequest.getParameter("todo");
		if ((todo != null) && (todo.equalsIgnoreCase("upload"))) {
                Hashtable files = mrequest.getFiles();
				upBean.setFolderstore(setting.getLogoPath());
				upBean.setOverwrite(true);
				
                if ( (files != null) && (!files.isEmpty()) )
                {
                    UploadFile file = (UploadFile) files.get("uploadfile");
                    // Uses the bean now to store specified by jsp:setProperty at the top.

                    if (file != null)
	                {
						String sFile = file.getFileName();
						String sFileCopy = "";
						if(sFile != null)
							sFileCopy = sFile.toLowerCase();
						
						if(sFile != null) {
							if(sFileCopy.indexOf("jpg")!= -1 || sFileCopy.indexOf("bmp")!= -1 || sFileCopy.indexOf("jpeg")!= -1 || sFileCopy.indexOf("gif")!= -1 || sFileCopy.indexOf("png")!= -1 || sFileCopy.indexOf("tiff")!= -1) {
								boolean bIsUpdated = org.editLogo(logchk.getOrg(), file.getFileName(), logchk.getPKUser());
													
								if(bIsUpdated) {
%>
							<script>
								alert("Updated successfully");
								window.close();
								opener.location.href = "OrganizationList.jsp";
							</script>
<%						
								} 
							
							} else {
%>	
							<script>
								alert("Format is not supported. Formats suported are .jpg, .bmp, .jpeg, .gif, .png, .tiff");
							</script>
<%	
							}
						} else {
%>	
							<script>
								alert("Please choose a logo to upload");
							</script>
<%							
						}
					}

                    upBean.store(mrequest, "uploadfile");
                }
                else
                {
                  out.println("<li>No uploaded files");
                }
		} else out.println("<BR> todo="+todo);
    }
%>

<%--	Edited Eric Lu 14/5/08
		Added confirmation box for submitting form --%>
<form method="post" action="UploadLogo.jsp" name="upform" enctype="multipart/form-data" onsubmit="return confirm('Upload Logo?')">
<table width="392" border="0" style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="97" align="left"><span class="style3"><%=trans.tslt("Organisation")%>:</span></td>
    <td width="285" align="left"><span class="style3"><%=orgName%></span></td>
  </tr>
</table>
<p></p>
<p style='font-size:10.0pt;font-family:Arial'><%=trans.tslt("Please click the browse button and choose the logo to be imported")%>.</p>
<input name="uploadfile" type="file" size="50">
<p></p>
<input type="hidden" name="todo" value="upload">
<input type="submit" name="Submit" value="<%=trans.tslt("Upload")%>">
<input type="reset" name="Reset" value="<%=trans.tslt("Cancel")%>" onClick=window.close()>
</form>

</body>
</html>
