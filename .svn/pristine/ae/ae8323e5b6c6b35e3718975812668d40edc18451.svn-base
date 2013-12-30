<%// Author: Dai Yong in June 2013%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>AddVenue</title>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body style="background-color: #DEE3EF">
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="Venue" class="Coach.CoachVenue"scope="session" />
<script language = "javascript">
function confirmAdd(form)
{
	if(form.Address1.value != "") {
		if(confirm("Add Venue?")) {
			form.action = "AddVenue.jsp?add=1";
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.Address1.value == "") {
			alert("Please enter address line 1");
			form.Address1.focus();
		}
		
		return false;
	}
	return true;
}

function cancelAdd()
{
	window.close();
}
</script>

<%
String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {
%> <font size="2">
   
<script>
parent.location.href = "../index.jsp";
</script>
<%  } 
  else 
  { 
	
	if(request.getParameter("add") != null) {
		if(request.getParameter("Address1") != null)	{
  			String Address1 = request.getParameter("Address1");
  			String Address2="";
  			String Address3="";
  			if(request.getParameter("Address2") != ""){
  				Address2=request.getParameter("Address2") ;
  			}
  			if(request.getParameter("Address3") != ""){
  				Address3=request.getParameter("Address3") ;
  			}
  			 Boolean Exist = false;
 		    
 			Vector v = Venue.getAllCoachVenue();
 			for(int i = 0; i < v.size(); i++){
 				voCoachVenue vo = (voCoachVenue)v.elementAt(i);
 				
 				String VenueName = vo.getVenue1();
 				//System.out.println("Existing Schedule Name:"+slotGroupName);
 				if(Address1.trim().equalsIgnoreCase((VenueName.trim()))){
 					Exist = true;
 					System.out.println("Same Address Line 1");
 				}

 			}

			
			if(!Exist) {						
				try{					
					boolean add =Venue.addCoachVenue(Address1, Address2, Address3);
					if(add){
						%>
						<script>
						alert("Venue successfully added");
						opener.location.href = 'Venue.jsp';
						window.close();
						</script>
						<% 
					}else{
					}
				}catch(Exception SE) {
                     System.out.println(SE);
				}
			} else {			
%>
	<script>
  		alert("Same Address Line 1");
		window.location.href='AddVenue.jsp';
	</script>
<%			
			}

	}
	}
%>	

<form name="AddSlotGroup" method="post">
  <table border="0" width="480" height="141" font span style='font-size:10.0pt;font-family:Arial'>
  <tr><b><font color="#000080" size="2" face="Arial">Add Venue</font></b> </tr>
    <tr>
      <td width="150" height="33">Address Line 1:</td>
      <td width="5" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="Address1" type="text"  style='font-size:10.0pt;font-family:Arial' size="30" maxlength="100">
	  </td>
    </tr>
      <tr>
      <td width="150" height="33">Address Line 2:</td>
      <td width="5" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="Address2" type="text"  style='font-size:10.0pt;font-family:Arial' size="30" maxlength="100">
	  </td>
    </tr>
      <tr>
      <td width="150" height="33">Address Line 3:</td>
      <td width="5" height="33">&nbsp;</td>
      <td width="400" height="33">
    	<input name="Address3" type="text"  style='font-size:10.0pt;font-family:Arial' size="30" maxlength="100">
	  </td>
    </tr>
    
    <tr>
      <td width="82" height="12"></td>
      <td width="10" height="12"></td>
      <td width="303" height="12"></td>
    </tr>
   
  </table>
  <blockquote>
    <blockquote>
      <p>
		<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</font>		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">		
	        <input class="btn btn-primary" type="button" name="Submit" value="Submit" onClick="confirmAdd(this.form)"></font><font span style='font-family:Arial'>
		</font>
			<font face="Arial">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </font>
		<font face="Arial" span style="font-size: 10.0pt; font-family: Arial">
			<input name="Cancel" class="btn btn-primary" type="button" id="Cancel" value="Cancel" onClick="cancelAdd()">
			</font> </p>
    </blockquote>
  </blockquote>
</form>

<% } %>
</body>
</html>