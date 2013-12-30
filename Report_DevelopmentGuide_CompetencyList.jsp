<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
                 CP_Classes.vo.voCompetency"%>   

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="Rpt9" class="CP_Classes.DevelopmentGuide" scope="session"/>
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="CompetencyQuery" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>


<SCRIPT LANGUAGE="JavaScript">
<!-- Begin


function check(field)
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
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("No record selected");
	else if(isValid == 2)
		alert("No record available");
	
	isValid = 0;	
	
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function ConfirmAdd(form, field)
function ConfirmAdd(form, field)
{
	if(check(field))
	{
	//\\ Changed message from print to save by Ha 26/05/08
		if(confirm("<%=trans.tslt("Save Development Guide")%>?"))
		{
			form.action="Report_DevelopmentGuide_CompetencyList.jsp?add=1";
			form.method="post";
			form.submit();
		}
		
	}
	
} 

function closeWindow()
{
	window.close();
}
/* Edited by Xuehai. Check or Uncheck all */
function checkAll(chkAll, chkComp){
	if(chkComp && chkComp.length>0){
		var checked=chkAll.checked;
		var i;
		for(i=0;i<chkComp.length;i++){
			chkComp[i].checked=checked;
		}
	}
}
</script>

<body>
<%	
Date timeStamp = new java.util.Date();
	SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
	String temp  =  dFormat.format(timeStamp);
	String file_name = "Development Guide " + temp + ".xls";

	if(request.getParameter("add") != null)
	{		
			int SurveyID = CE_Survey.getSurvey_ID();
			// Changed by Ha 26/05/08: continue the method only if chkComp !=null
			if (request.getParameterValues("chkComp")!=null)
			{
	 	   		String [] chkSelect = request.getParameterValues("chkComp");
	 	    
				Rpt9.SelComp(chkSelect,logchk.getPKUser(),file_name);
					
				String output = setting.getReport_Path() + "\\"+file_name;
				File f = new File (output);
					
				//set the content type(can be excel/word/powerpoint etc..)
				response.reset();
				response.setContentType ("application/xls");
				//set the header and also the Name by which user will be prompted to save
				response.addHeader ("Content-Disposition", "attachment;filename="+file_name+"");
							
				//get the file name
				String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
				//OPen an input stream to the file and post the file contents thru the 
				//servlet output stream to the client m/c
			
				InputStream in = new FileInputStream(f);
				ServletOutputStream outs = response.getOutputStream();
							
				int bit = 256;
				int i = 0;									
				try {
					   while ((bit) >= 0) 
					   {
					       bit = in.read();
					       outs.write(bit);
					   }
					   //System.out.println("" +bit);
					
			 	} 
				catch (IOException ioe) 
				{
					   ioe.printStackTrace(System.out);
				}

				outs.flush();
           		outs.close();
          		in.close();	
			}
	}	
	int DisplayNo;
	String pkCompetency; 
	String name, definition;
	DisplayNo = 1;

	
%>
<form name="Report_DevelopmentGuide_CompetencyList" method="post" action="Report_DevelopmentGuide_CompetencyList.jsp">

<table border="1">
<tr><td>
<div style='width:900px; height:500px; z-index:1; overflow:auto;'>  
<table border="1" bgcolor="#FFFFCC" bordercolor="#3399FF">
<th bgcolor="navy"><input type="checkbox" name="chkAll" onclick='checkAll(this, this.form.chkComp)'></th>
<th bgcolor="navy"><b><font style='font-family:Arial;color:white' size="2"><%=trans.tslt("Name")%></font></b></th>
<th bgcolor="navy"><b><font style='font-family:Arial;color:white' size="2"><%=trans.tslt("Definition")%></font></b></th>
<% 	
	/*
	*changed by clement
	*23-jan-2008
	*/
	
	// System.out.println("OrgID = "+logchk.getOrg());
	// String query1 ="SELECT * FROM Competency WHERE FKOrganizationID = "+logchk.getOrg();
	Vector v = CompetencyQuery.getCompetencyByOrg(logchk.getOrg());
	
	for(int i=0; i<v.size(); i++){
		voCompetency vo = (voCompetency) v.get(i);

		pkCompetency = Integer.toString(vo.getPKCompetency());
		
		name = vo.getCompetencyName();
		definition = vo.getCompetencyDefinition();
		
	%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
       <td>
	   		<font face="Arial" style="font-size: 11.0pt; font-family: Arial">
	   		<input type="checkbox" name="chkComp" value=<%=pkCompetency%>></font><font style='font-family:Arial' size="2">
            </font>
	   </td>
	   <td>
           <font style='font-family:Arial' size="2"><% out.print(name);%></font><font size="2" face="Arial">
			</font>
       </td>
	   <td>
           <font style='font-family:Arial' size="2"><% out.print(definition);%></font><font size="2" face="Arial">
			</font>
       </td>
   </tr>
<% 	DisplayNo++;
	} 
%>
</table>
</div>
</td></tr>
</table>
<p></p>
<table border="0" width="55%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="210">

<input type="button" name="Add" value="<%=trans.tslt("Save")%>" onclick="ConfirmAdd(this.form,this.form.chkComp)"></td>
		<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onclick="closeWindow()"></td>
	</tr>
</table>
&nbsp;&nbsp;&nbsp;

</form>

</body>
</html>