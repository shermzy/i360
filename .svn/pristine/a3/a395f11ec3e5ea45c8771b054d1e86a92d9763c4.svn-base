<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
<title>Coach</title>

<jsp:useBean id="Coach" class="Coach.Coach"scope="session" />
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<style>
input
{
class="btn btn-primary";
} 
</style>

<script>
var x = parseInt(window.screen.width) / 2 - 240;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 115;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up


function check(field)
{
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
		alert("No record selected");
	else if(isValid == 2)
		alert("No record available");
	
	isValid = 0;

}

	function proceed(form) {
		form.action = "Coach.jsp?proceed=1";
		form.method = "post";
		form.submit();
	}
	function addCoach(form){
		var myWindow=window.open('AddCoach.jsp','windowRef','scrollbars=no, width=480, height=250');
		myWindow.moveTo(x,y);
	    myWindow.location.href = 'AddCoach.jsp';
	}
	function updatePDF(form, field){
		var value = check(field);
		if(value)
		{						
			var myWindow=window.open('UploadCoachFile.jsp?UploadCoachInfo='+ value,'windowRef','scrollbars=no, width=480, height=250');
			var query = "UploadCoachFile.jsp?UploadCoachInfo=" + value;
			myWindow.moveTo(x,y);
	    	myWindow.location.href = query;
		}
		
	}
		
	function editCoach(form, field){
		var value = check(field);
		if(value)
		{						
			var myWindow=window.open('EditCoach.jsp?editedCoach='+ value,'windowRef','scrollbars=no, width=480, height=250');
			var query = "EditCoach.jsp?editedCoach=" + value;
			myWindow.moveTo(x,y);
	    	myWindow.location.href = query;
		}
		
	}
	function deleteCoach(form, field) {
		var value = check(field);
		if (value) {
			if (confirm("Are you sure to delete the coaching slot")) {
				form.action = "Coach.jsp?deleteCoach=" + value;
				form.method = "post";
				form.submit();
			}
		}
	}
</script>
</head>
<body>
<%@ include file="nav.jsp" %> 
	<%
		Vector Coachs = new Vector();
		Coach.setUserPK(logchk.getPKUser());
		System.out.println("JSP login, userPK:"+logchk.getPKUser());
		if (request.getParameter("proceed") == null) {
				LoginStatus.setSelectedCoach(1);
		}
		if(request.getParameter("deleteCoach")!= null){
			int PKCoach = Integer.valueOf(request.getParameter("deleteCoach"))  ;
			 Boolean delete =Coach.deleteCoach(PKCoach);
			 if(delete){
				 %><script>
				 alert("Coach deleted successfully.");
				 </script><% 
				 Coachs=Coach.getAllCoach();
			 }
			 else{
				 %><script>
				 alert("Coach used in Coaching Assgiment and cannot be delete");
				 </script><% 
			 }
			
		}
	%>

	<p>
	<br>
		<b><font color="#000080" size="3" face="Arial">Coach Management</font></b>
	</p>

	<!-- list all the Schedule  -->
	

	<%
	Coachs=Coach.getAllCoach();
	%>
	<form>
		<br> 
		<table>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Name</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Online Profile</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>CV</font>
			</b></th>

			<%
				int DisplayNo = 1;
				
				for (int i = 0; i < Coachs.size(); i++) {
					voCoach voCoach = new voCoach();
					voCoach = (voCoach) Coachs.elementAt(i);

					int pkCoach = voCoach.getPk();
					String name=voCoach.getCoachName();
					String link=voCoach.getLink();
					String fileName=voCoach.getFileName();
			%>
			<tr onMouseOver="this.bgColor = '#99ccff'"
				onMouseOut="this.bgColor = '#FFFFCC'">
				<td style="border-width: 1px"><font size="2"> <input type="radio" name="selCoach" value=<%=pkCoach%>></font></td>
				<td align="center"><%=DisplayNo%></td>
				<td align="left"><%=name%></td>
				
				<%
			if(link==null||"".equalsIgnoreCase(link)){
			%><td align="center">&nbsp;</td><%
			}else{
							%>
				<td align="center"><a href=<%=link%>  target="_blank">Link</a></td>
				<%
			}
			 %>
			<%
			if(fileName==null||"".equalsIgnoreCase(fileName)){
			%><td align="center">&nbsp;</td><%
			}else{
			String filePath=setting.getCoachFilePath()+"\\"+fileName;
			
			System.out.println(filePath);
				%>
				<td><a href="file://<%=filePath.replace("\\", "/")%>"><%=fileName%> </a></td>
				<%
			}
			 %>
			</tr>
			<%
				DisplayNo++;
				}
			%>
		</table>
		<!--  button for slot-->
		<p></p>
			<input class="btn btn-primary" type="button" name="AddCoach" value="Add Coach" onclick="addCoach(this.form)"> 
			<input class="btn btn-primary" type="button" name="EditCoach" value="Edit Coach" onclick="editCoach(this.form, this.form.selCoach)"> 
			<input class="btn btn-primary" type="button" name="DeleteCoach" value="Delete Coach" onclick="deleteCoach(this.form, this.form.selCoach)">
			<input class="btn btn-primary" type="button" name="updateInfo" value="Upload CV" onclick="updatePDF(this.form, this.form.selCoach)"> 
		<p></p>
	</form>
</body>
</html>