<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
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
 
function ConfirmAdd(form, field){
	if(check(field)){
		if(confirm("<%=trans.tslt("Assign Cluster")%>?")){
			form.action="Survey_ClusterList.jsp?add=1";
			form.method="post";
			form.submit();
		}
		
	}
} 

function closeWindow(){
	window.close();
}
</script>


<BODY>
<%	
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

if (!logchk.isUsable(username)){
%> 
	<font size="2">
	<script>
	parent.location.href = "index.jsp";
	</script>
<%  
} else{
	
	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	int toggle = CE_Survey.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null){	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		CE_Survey.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		CE_Survey.setSortType(type);									
	} 
/*********************************************************END ADDING TOGGLE FOR SORTING PURPOSE *************************************/

	if(request.getParameter("add") != null){
		int SurveyID = CE_Survey.getSurvey_ID();
	 	String [] chkSelect = request.getParameterValues("chkComp");
	 	boolean bClusterAdded = true;
	 	    
		if(chkSelect != null){ 
		    try{
				for(int i=0; i<chkSelect.length; i++){
					int iClusterID = 0;
					if(chkSelect[i] !=null){
						iClusterID = Integer.parseInt(chkSelect[i]);
						if(!CE_Survey.addCluster(iClusterID,SurveyID))
							bClusterAdded = false; 
					}
				}
			}
			catch(SQLException sqle){	
				bClusterAdded = false;
			}	
				
			if (bClusterAdded) {
%>
				<script>
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
	<script>
		window.close();
	 	opener.location.href = 'SurveyCluster.jsp';
	</script>
<%		
	}//end if request getParameter add


	int DisplayNo;
	int pkCluster; 
	String name, definition,origin;
	DisplayNo = 1;

	/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	
%>
<form name="ClusterList" method="post" action ="Survey_ClusterList.jsp">

<table border="1" width="610">
<tr><td>
<div style='width:610px; height:500px; z-index:1; overflow:auto'>  
<table border="1" bordercolor="#3399FF" bgcolor="#FFFFCC">
<th width="20" bgcolor="navy">
	<font size="2">
	  <input type="checkbox" name="checkAll" onClick="checkedAll(this.form, this.form.chkComp,this.form.checkAll)"></font>
</th>


<th width="81" bgcolor="navy" bordercolor="#3399FF"><a href="Survey_ClusterList.jsp?name=1"><b>
<font style='color:white' face="Arial" size="2"><u><%=trans.tslt("Name")%></u></font></b></a></th>

<% 	
	Vector vCluster = CE_Survey.FilterRecordCluster(CE_Survey.get_survOrg(), CE_Survey.getSurvey_ID());
	for(int i=0; i<vCluster.size(); i++){
		voCluster vo = (voCluster)vCluster.elementAt(i);
		pkCluster = vo.getClusterID();
		
		name =  vo.getClusterName();
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFcc'">
       <td>
	   		<font style='font-size:11.0pt;font-family:Arial'>
	   		<input type="checkbox" name="chkComp" value=<%=pkCluster%>></font><font style='font-family:Arial' size="2">
            </font>
	   </td>
	   <td>
           <font style='font-family:Arial' size="2"><% out.print(name);%></font><font size="2">
			</font>
       </td>
   </tr>
<% 		DisplayNo++;
	}//end for loop
}
%>
</table>
</div>
</td></tr>
</table>
<p></p>
<table border="0" width="55%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="210"><input type="button" name="Add" value="<%=trans.tslt("Add")%>" onclick="ConfirmAdd(this.form,this.form.chkComp)"></td>
		<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onclick="closeWindow()"></td>
	</tr>
</table>
&nbsp;&nbsp;&nbsp;

</form>
</body>
</html>