<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="CompetencyQuery" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="ClusterQ" class="CP_Classes.Cluster" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import = "CP_Classes.vo.voCompetency" %>
<%@ page import = "CP_Classes.vo.voCluster" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Add Competency to Survey</title>
</head>


<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

<!-- added by Albert (16/07/2012): add a checkbox on top to choose all -->
function checkedAll(form, field, checkAll)
{	
	if(checkAll.checked == true) 
		for(var i=0; i<field.length; i++)
			field[i].checked = true;
	else 
		for(var i=0; i<field.length; i++)
			field[i].checked = false;	
}

function check(field){
	var isValid = 0;
	var clickedValue = 0;
	//check whether any checkbox selected
	if( field == null ) {
		isValid = 2;
	
	} else {

		if(isNaN(field.length) == false) {
			for (i = 0; i < field.length; i++)
				if(field[i].checked) {
					clickedValue = field[i].value;
					isValid = 1;
				}
		}else {		
			if(field.checked) {
				clickedValue = field.value;
				isValid = 1;
			}
				
		}
	}
	
	if(isValid == 1)
		return clickedValue;
	else if(isValid == 0)
		alert("<%=trans.tslt("No record selected")%>");
	else if(isValid == 2)
		alert("<%=trans.tslt("No record available")%>");
	
	isValid = 0;

}
 
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function ConfirmAdd(form, field)
function ConfirmAdd(form, field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Assign Competency")%>?"))
		{
			form.action="Survey_ClusterCompetencyList.jsp?add=1";
			form.method="post";
			form.submit();
		}
		
	}
	
} 

function closeWindow()
{
	window.close();
}

/*	choosing organization*/

</script>

<body>
<%	
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  } 
	
	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = CE_Survey.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		CE_Survey.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		CE_Survey.setSortType(type);									
	} 
	
	
/*********************************************************END ADDING TOGGLE FOR SORTING PURPOSE *************************************/

	if(request.getParameter("add") != null)
	{
			// Edited by Eric Lu 21/5/08
			//
			// Added new boolean to detect whether adding competencies was successful
			// If successful, "Assigned successfully" pops up
			
			int SurveyID = CE_Survey.getSurvey_ID();
			int ClusterID = CE_Survey.getClusterID();
	 	    String [] chkSelect = request.getParameterValues("chkComp");
	 	    boolean bCompetencyAdded = true;
	 	    
				if(chkSelect != null)
		    	{ 
		    		try
					{
			    		for(int i=0; i<chkSelect.length; i++)
						{
							String [] CompLevel = new String[10];
							CompLevel = request.getParameterValues("selCompLevel"+chkSelect[i]);
							for(int j=0; j<CompLevel.length; j++)
							{
								int iCompetencyID = 0;
								int iCompetencyLevel = 0;
								if(chkSelect[i] != null)
									iCompetencyID = Integer.parseInt(chkSelect[i]);
									
								if(CompLevel[j] != null)
									iCompetencyLevel = Integer.parseInt(CompLevel[j]);
								if (!CE_Survey.addCompetency(iCompetencyID,SurveyID,iCompetencyLevel,ClusterID))
									bCompetencyAdded = false;
							}
	
						}
					}
					catch(SQLException sqle)
					{	
						bCompetencyAdded = false;
					}	
					
					if (bCompetencyAdded) {
						%>
							<script>
                                                                // Changed by DeZ, 26/06/08, update survey status to Not Commissioned whenever changes are made to survey
					 			alert("Added successfully, survey status has been changed to Non Commissioned, to re-open survey please go to the Survey Detail page");
					 		</script>
						<%
					} else {
						%>
					 		<script>
					 			alert("Added unsuccessfully");
					 		</script>
						<%
					}
				}
	%>					
	<script>window.close()
	 		opener.location.href = 'SurveyCompetency.jsp';</script>
	<%
		
	}


	int DisplayNo;
	int pkCompetency; 
	String name, definition,origin;
	DisplayNo = 1;

	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	
%>
<form name="ClusterCompetencyList" method="post" action ="Survey_ClusterCompetencyList.jsp">

<table border="1" width="610">
<th bgcolor="navy"><b>
<font style='color:white' face="Arial" size="2">Cluster :
<%
	voCluster vCluster = ClusterQ.getCluster(CE_Survey.getClusterID());
	String clusterName = vCluster.getClusterName();
%>
<%=clusterName%>
</font></b>
</th>
<tr><td>
<div style='width:610px; height:500px; z-index:1; overflow:auto'>  
<table border="1" bordercolor="#3399FF" bgcolor="#FFFFCC">
<th width="20" bgcolor="navy">
	<font size="2">
	  <input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkComp,this.form.checkAll)"></font>
</th>


<th width="81" bgcolor="navy" bordercolor="#3399FF"><a href="Survey_ClusterCompetencyList.jsp?name=1"><b>
<font style='color:white' face="Arial" size="2"><u><%=trans.tslt("Name")%></u></font></b></a></th>
<th width="329" bgcolor="navy" bordercolor="#3399FF">
<a href="Survey_ClusterCompetencyList.jsp?name=2"><b>
<font style='font-family:Arial;color:white' size="2">
<u><%=trans.tslt("Definition")%></u></font></b></a></th>
<th width="77" bgcolor="navy" bordercolor="#3399FF"><a href="Survey_ClusterCompetencyList.jsp?name=3"><b>
<font style='font-family:Arial;color:white' size="2"><u><%=trans.tslt("Origin")%></u></font></b></a></th>
<th width="69" bgcolor="navy" bordercolor="#3399FF"><b>
<font style='font-family:Arial;color:white' size="2"><%=trans.tslt("Level")%></font></b></th>

<% 	

	Vector vComp = CE_Survey.FilterRecord(CE_Survey.get_survOrg(), CE_Survey.getSurvey_ID(), CE_Survey.getClusterID());
	System.out.println("vComp is "+vComp);
	System.out.println("org ID is "+CE_Survey.get_survOrg());
	System.out.println("survey ID is "+CE_Survey.getSurvey_ID());
	for(int i=0; i<vComp.size(); i++)
	{
		voCompetency vo = (voCompetency)vComp.elementAt(i);
		pkCompetency = vo.getCompetencyID();
		
		name =  vo.getCompetencyName();
		definition = vo.getCompetencyDefinition();
		origin = vo.getOrigin();
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">
       <td>
	   		<font style='font-size:11.0pt;font-family:Arial'>
	   		<input type="checkbox" name="chkComp" value=<%=pkCompetency%>></font><font style='font-family:Arial' size="2">
            </font>
	   </td>
	   <td>
           <font style='font-family:Arial' size="2"><% out.print(name);%></font><font size="2">
			</font>
       </td>
	   <td>
           <font style='font-family:Arial' size="2"><% out.print(definition);%></font><font size="2">
			</font>
       </td>
       <td>
           <div align="center"><font style='font-family:Arial' size="2">
                 <% out.print(origin);%>
             </font><font size="2">
			  </font>
           </div></td>
       <td align="center">
           <font style='font-size:11.0pt;font-family:Arial'>
           <select size="1" name="<%="selCompLevel"+pkCompetency%>">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<script>
					window.document.ClusterCompetencyList.<%="selCompLevel"+pkCompetency%>.selectedIndex=0;
					</script>

				</select></font><font style='font-family:Arial' size="2">
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
<input type="button" name="Add" value="<%=trans.tslt("Add")%>" onclick="ConfirmAdd(this.form,this.form.chkComp)"></td>
		<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onclick="closeWindow()"></td>
	</tr>
</table>
&nbsp;&nbsp;&nbsp;

</form>

</body>
</html>