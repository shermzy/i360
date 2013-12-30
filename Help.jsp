<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>    
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<body>
<%
	String sFileName = "3-Sixty Net Manual.doc";
	if(setting.getNomModule() == 1)
		sFileName = "3-Sixty Net Manual Nomination.doc"; //Nomination Module
	
	//read the file name.		
	String output = setting.getReport_Path() + "\\Template\\" + sFileName;
	
	File f = new File (output);

	//set the content type(can be excel/word/powerpoint etc..)
	response.setContentType ("application/doc");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"" + sFileName + "\"");

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
%>
<script language = javascript>
window.close();
</script>
</body>