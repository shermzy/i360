<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  


<html>
<head>
<title>Raters Result</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

</head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>

<jsp:useBean id="User" class="CP_Classes.User_Jenty" scope="session"/>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/> 
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>   
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Rpt1" class="CP_Classes.Report_ListOfCompetencies" scope="session"/>    

<script language="javascript">
function printResult(form) {
	form.action = "RatersResult.jsp?print=1";
	form.submit();
	
   	return true;
}

</script>


<%


String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
%>


<%

/**************************************************** REPORT EXCEL *********************************************************************/
	
	if(request.getParameter("print") != null) {		
		/*
		Date timeStamp = new java.util.Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		*/
		//String file_name = "RatersResults" + temp + ".xls";
	

		//Excel4.WriteToReport(assignmentID, pkUser, file_name);	
Rpt1.AllCompetencies_KeyBehav(logchk.getCompany(), logchk.getOrg(), logchk.getPKUser());

		
	//read the file name.
	String file_name = "ListOfCompetencies.xls";		
	String output = setting.getReport_Path() + "\\"+file_name;
	
	File f = new File (output);


	//set the content type(can be excel/word/powerpoint etc..)
	response.setContentType ("application/xls");
	//set the header and also the Name by which user will be prompted to save
	response.addHeader ("Content-Disposition", "attachment;filename=\"ListOfCompetencies.xls\"");

		
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
        			//System.out.println("" +bit);

            		} catch (IOException ioe) {
            			System.out.print("TEST" + ioe.getMessage());
%>
<script>
	form.action = "RatersResult.jsp?print=1";
	form.submit();
</script>
<%						
            		}
            //		System.out.println( "\n" + i + " bytes sent.");
            //		System.out.println( "\n" + f.length() + " bytes sent.");
            		outs.flush();
            		outs.close();
            		in.close();	
}
	
	
	
/**************************************************** REPORT EXCEL *********************************************************************/
	

%>
<form name="form1" method="post" action="">

<p>
    <input type="submit" name="print" value="<%=trans.tslt("DOWNLOAD")%>" onClick="return printResult(this.form)">
</p>
</form>
<p>&nbsp;</p>
<% } %>
</body>
</html>
