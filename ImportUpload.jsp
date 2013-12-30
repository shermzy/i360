<%@ 
page import="java.sql.*,
			 java.io.*,
			 javazoom.upload.*,
			 java.util.*, java.util.regex.*"
%>		 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <jsp:useBean id="Import" class="CP_Classes.Import" scope="session"/>        
        <jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
        <jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
        <jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>    
        <jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
            <jsp:setProperty name="upBean" property="folderstore"/>
            <jsp:setProperty name="upBean" property="whitelist" value="*.xls" />
        </jsp:useBean>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ page pageEncoding="UTF-8"%>
		<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
        <script language="javascript">
			function formSubmit() {
				try {
					this.frmImportUpload.submit();
				} catch (error) {
					alert("<%=trans.tslt("Please select a file to be uploaded")%>");
				}
			}
		</script>
    </head>
    <body>
<%
	int iImportType = (request.getParameter("type") == null)?0:Integer.parseInt(request.getParameter("type"));
	//Added a new variable to save the language type selected by the user when in ImportLanguage.jsp, Chun Yeong 22 Jul 2011
	int iLanguage = (request.getParameter("lang") == null)?0:Integer.parseInt(request.getParameter("lang"));
%>
        <form method="post" action="ImportUpload.jsp?type=<%=iImportType%>&lang=<%=iLanguage%>" name="frmImportUpload" enctype="multipart/form-data">
<%
			
			//Mutlipart Form Data Request
			MultipartFormDataRequest mrequest = null;
			
			boolean isUpload = false;
			boolean isFileNotFoundError = true;
            
			//Multipart Form Data Request Check    
            if (MultipartFormDataRequest.isMultipartFormData(request)) {
                //Get Multipart Form Data Request
                mrequest = new MultipartFormDataRequest(request);
                //Set Upload Operation
                isUpload = (mrequest.getParameter("todo")!= null)?true:false;				
            } //End of Multipart Form Data Request Check			

            //Multipart Form Data Request Null Check
            if(mrequest != null && isUpload) {
				//Start of Try-Catch
				try{
                //Get Files Hashtable
                Hashtable files = mrequest.getFiles();
                upBean.setFolderstore(setting.getUploadPath());
                upBean.setOverwrite(true);

				//Files Null and Is Empty Check
				if((files != null) && (!files.isEmpty())) {
					//Get File 
					UploadFile file = (UploadFile) files.get("uploadfile");

					//File Null Check
					if(file != null) {
						String sFileName = file.getFileName();
						//File Name Null Check
						if(sFileName != null) {
							upBean.store(mrequest, "uploadfile");
							isFileNotFoundError = false;
							
							//Added a if statement to differentiate the ImportLanguage page from the ImportExport page, Chun Yeong 22 Jul 2011
							if(iImportType == 12 ) {
%>
							<script>
                                window.close();
                                opener.location.href = "ImportLanguage.jsp?type=<%=iImportType%>&lang=<%=iLanguage%>&path=" + "<%=sFileName%>";
                            </script>
<%							
							//Else reopen the ImportExport.jsp
							} else {
%>
							<script>
                                window.close();
                                opener.location.href = "ImportExport.jsp?type=<%=iImportType%>&path=" + "<%=sFileName%>";
                            </script>
<%							} //End of If statement
						} //End of File Name Check
					} //End of File Null Check
				} //End of Files Null and Is Empty Check
				}catch(Exception ex) {
					isFileNotFoundError = false;
%>
					<script>
                        alert('<%=trans.tslt("Format is not correct. Formats suported is .xls")%>');
                    </script>
<%
				}
            } //End of Multopart Form Request Null Check
			
			//File Not Found Check
			if(isFileNotFoundError && isUpload) {
%>
				<script>
                    alert('<%=trans.tslt("Please select a file to be uploaded")%>');
                </script>
<%	
			} //End of File Not Found Check
%>
<%	
	/*
	* Change(s) : Add Switch statement to determine import type title in the import pop up window
	* Reason(s) : To display the type of import to the user access the function
	* Updated By: Sebastian
	* Updated On: 05 July 2010
	*/	
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
    //    11) Import Questionnaire
	//	  12) Import Language
    //    13) Import Cluster
    //    14) Import Nomination
	String importDesc = "";
	
	switch (iImportType)
	{
		case 1: //Import User
			importDesc = "Import User";	
			break;
			
		case 2: //Import Assignment - Target & Rater
			importDesc = "Import Assignment - Target & Rater";	
			break;
		
		case 3: //Import Assignment - Target Only
			importDesc = "Import Assignment - Target Only";	
			break;
			
		case 4: //Import Competency
			importDesc = "Import Competency";
			break;
 			
 		case 5: //Import Key Behaviour
 			importDesc = "Import Key Behaviour";
  			break;
	 		
	 	case 6: //Import Development Activities
	 		importDesc = "Import Development Activities";
	 		break;
	  		
		case 7: //Import 
			importDesc = "Import Development Resources";
			break;
		
		case 8: //Import Division
			importDesc = "Import Division";
			break;
			
		case 9: //Import Department
			importDesc = "Import Department";
			break;
			
		case 10: //Import Group/Section
			importDesc = "Import Group\\Section";
			break;
		
		case 11: //Import Questionnaire
			importDesc = "Import Questionnaire";
			break;
		
		//Added Import translation for the new functionality, Chun Yeong 22 Jul 2011
		case 12: //Import Language Translation
			importDesc = "Import Language Translation";
			break;
			
		case 13:  //Import Cluster
		    importDesc = "Import Cluster";
		
		case 14:
			importDesc = "Import Nomination";
		//End Import translation***
	}
		
	if (!importDesc.equals(""))
	{
		%>
            <p style='font-size:10.0pt;font-family:Arial;font-weight:bold'>
                <%=trans.tslt(importDesc)%>
                <br><br>
            </p>
		<%
	}
%>
            <p style='font-size:10.0pt;font-family:Arial'>
                <%=trans.tslt("Please click the browse button and choose the excel file to be imported")%>
                <br><br>
            </p>
            <p></p>
            <input name="uploadfile" type="file" size="40">
            <p></p>
            <input type="hidden" name="todo" value="upload">
            <input type="button" name="Submit" value="<%= trans.tslt("Import") %>" onClick="formSubmit();"> 
            <input type="reset" name="Reset" value="<%= trans.tslt("Cancel") %>" onClick=window.close()>
        </form>
    </body>
</html>