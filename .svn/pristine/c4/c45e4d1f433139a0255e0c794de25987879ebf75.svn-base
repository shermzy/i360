<%@ page import="java.sql.*,java.util.*,java.io.* "%>  
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>        
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="PrelimQController" class="CP_Classes.PrelimQuestionController" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="PrelimQuestionBean" class="CP_Classes.PrelimQuestionBean" scope="session"/>
<%@ page import="CP_Classes.PrelimQuestion"%>
<%@ page import="CP_Classes.PrelimQuestionAns"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
<title>Add Preliminary Question Rating Scale</title>
</head>


<SCRIPT LANGUAGE="JavaScript">
<!-- Begin


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

function ConfirmAddRatingScale(form){
		if(confirm("<%=trans.tslt("Add a new rating scale")%>?")){
			form.action="PrelimQ_AddRatingScale.jsp?addRatingScale=1";
			form.method="post";
			form.submit();
			opener.location.herf="SurveyPrelimQ.jsp";
			opener.location.reload(true);
		}
} 

function ConfirmAddRating(form, field,id){
	if(field!=null){
		if(confirm("<%=trans.tslt("Add a new rating scale")%>?")){
			form.action="PrelimQ_AddRatingScale.jsp?addRating="+form.selRatingScaleID.value;
			form.method="post";
			form.submit();
		}
	}else{
		alert("<%=trans.tslt("Please enter a rating")%>");
	}
} 

function populateRating(form, field)
{
	form.action="PrelimQ_AddratingScale.jsp?populateRating="+field.value;
	form.method="post";
	form.submit();
	
}

function confirmDelete(form, field) {
	form.action = "PrelimQ_AddRatingScale.jsp?delete="+ 1;
	form.method = "post";
	form.submit();
}

function confirmUpdate(form, field, field2){
	form.action = "PrelimQ_AddRatingScale.jsp?update="+ 1;
	form.method = "post";
	form.submit();
}

function closeWindow(){
	
	window.close();
}

function deleteScale(form){
	form.action = "PrelimQ_AddRatingScale.jsp?deleteScale="+ 1;
	form.method = "post";
	form.submit();
}

/*	choosing organization*/

</script>

<body style="background-color: #DEE3EF">
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
  
  	if(request.getParameter("populateRating") != null){
		int PKScaleID = new Integer(request.getParameter("populateRating")).intValue();
		logchk.setPrelimRatingScaleID(PKScaleID);
	}
	if(request.getParameter("addRatingScale") != null){		
 		try{					
			int addedScaleID = PrelimQController.addNewRatingScale();
			
			if(addedScaleID != -1){
				%>
				<script>
				alert("A new rating scale is successfully added");
				</script>
				<% 
				logchk.setPrelimRatingScaleID(addedScaleID);
			}
			else{
				%>
				<script>
				alert("error occur when adding new rating scale");
				</script>
				<% 
			}
		}catch(Exception SE) {
                System.out.println(SE);
		}
	}
	if(request.getParameter("addRating") != null){		
		if(request.getParameter("RatingName") != null)	{
  			String name = request.getParameter("RatingName");
  			int scaleId = Integer.parseInt(request.getParameter("addRating"));
  			try{					
				boolean add = PrelimQController.addNewRating(scaleId,name);
				
				if(add){
					%>
					<script>
					alert("A new rating scale is successfully added");
					</script>
					<% 
				}
				else{
					%>
					<script>
					alert("No rating is added. Probably because some questionnaire is currently using this rating scale. ");
					</script>
					<% 
				}
			}catch(Exception SE) {
                 System.out.println(SE);
			}
		}
	}
	
	if(request.getParameter("delete") != null){
		String [] chkSelect = request.getParameterValues("checkdel");
		int scaleID = logchk.getPrelimRatingScaleID();
	 	boolean bRatingdeleted = false;
	 	if(chkSelect != null){ 
		    try{
		    	for(int i=0; i<chkSelect.length; i++){
		    		int rating = -1;
					if(chkSelect[i] != null){
						rating = Integer.parseInt(chkSelect[i]);
					}		
					bRatingdeleted = PrelimQController.deleteRating(scaleID,rating);
					if(!bRatingdeleted){
						break;
					}
				}
		    	if(bRatingdeleted){
		    		bRatingdeleted = PrelimQController.updateRatingSq(scaleID);
		    	}
			}catch(SQLException sqle){	
				System.out.println(sqle);
				bRatingdeleted = false;
			}
			
			if (bRatingdeleted) {
%>
				<script>
					alert("Rating(s) is/are successfully deleted");
				</script>
<%
			}else{
%>
		 		<script>
		 			alert("An error occured when trying to delete rating(s). Probably because some questionnaire is currently using this rating scale. ");
		 		</script>
<%
			}
		}else{
%>
	    	<script>
	    		alert("No rating selected");
			</script>	
<% 
		}	
	}
	
	if(request.getParameter("update") != null){
		String [] chkSelect = request.getParameterValues("selectSeq");
		String [] contents = request.getParameterValues("content");
		int scaleID = logchk.getPrelimRatingScaleID();
	 	boolean bRatingdeleted = false;
	 	if(chkSelect != null || contents != null){ 
		    try{
		    	bRatingdeleted = PrelimQController.updateRating(scaleID,chkSelect, contents);
			}catch(SQLException sqle){	
				System.out.println(sqle);
				bRatingdeleted = false;
			}
			
			if (bRatingdeleted) {
%>
				<script>
					alert("Rating(s) is/are successfully updated");
				</script>
<%
			}else{
%>
		 		<script>
		 			alert("An error occured when trying to update rating(s). The reason can be\n 1. Some questionnaire is currently using this rating scale.\n 2. Duplicate ranking detected.");
		 		</script>
<%
			}
		}else{
%>
	    	<script>
	    		alert("No rating selected");
			</script>	
<% 
		}	
	}
	
	if(request.getParameter("deleteScale") != null){
		//String[] select = request.getParameterValues("selRatingScaleID");
		int scaleID = logchk.getPrelimRatingScaleID();
	 	boolean bScaledeleted = false;
	    try{
	    	bScaledeleted = PrelimQController.deleteRatingScale(scaleID);
		}catch(SQLException sqle){	
			System.out.println(sqle);
			bScaledeleted = false;
		}
		
		if (bScaledeleted) {
			logchk.setPrelimRatingScaleID(PrelimQController.getFirstRatingScale());
%>
			<script>
				alert("Rating scale delete successfully ");
			</script>
<%
		}else{
%>
	 		<script>
	 			alert("An error occured when trying to delete a rating scale, it may because this rating scale is being use.");
	 		</script>
<%
		}
	}
	
	
	
	int DisplayNo = 1;
	Map<Integer, String> aResult = null;
%>
<form name="AddRating" method="post" action ="PrelimQ_AddRatingScale.jsp">

<table border="0" width="850">
<tr><td>
<div style='z-index:1; overflow:auto'>  

<table border="0" width="80%" cellspacing="0" cellpadding="0">
<tr>
  <td colspan="3"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Preliminary Question Rating Scale") %></font></b></td>
</tr>
<tr>
  <td colspan="3"><ul>
    <li><font face="Arial" size="2">
    <%= trans.tslt("To Add a new Rating Scale, click on Add New Rating Scale")%>.</font></li>
    <li><font face="Arial" size="2">
    <%= trans.tslt("To Add a new rating to an existing rating scale, choose the rating scale ID then type the rating and click on Add Rating")%>.</font></li>
    <li><font face="Arial" size="2">
    <%= trans.tslt("To Delete, click on the relevant radio button and click on the Delete button")%>.</font></li>
    <li><font face="Arial" size="2">
    <%= trans.tslt("To Update Rating Ranking , Use the relevant dropdown list to rearrange and clock on the Update button")%>.</font></li>
    <li><font face="Arial" size="2">
    <%= trans.tslt("To Update Rating Name , Edit on relevant text box and clock on the Update button")%>.</font></li>
  </ul></td>
</tr>
<tr>
	<!-- <td width="96"><font face="Arial" size="2"><%= trans.tslt("New Rating Scale") %>:</font></td>
	<td width="246">
	<input name="RatingScaleName" type="text"  style='font-size:10.0pt;font-family:Arial' id="RatingScaleName" size="25" maxlength="50">
	</td>
	<td align="center">&nbsp;</td> -->
</tr>
<tr>
	<td width="96">&nbsp;</td>
	<td width="246">&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
<td colspan="3">
<input type="button" name="Add New Rating Scale" value="<%=trans.tslt("Add New Rating Scale")%>" onclick="ConfirmAddRatingScale(this.form)">
</td>
</tr>
<tr>
<td width="96">&nbsp;</td>
<td width="246">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="96">&nbsp;</td>
<td width="246">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="96"><font face="Arial" size="2"><%= trans.tslt("Rating Scale ID") %>:</font></td>
	<td width="246"><select size="1" name="selRatingScaleID" onchange="populateRating(this.form,this.form.selRatingScaleID)">
	<%
	Vector v = new Vector();
	v = PrelimQController.getAllRatingScale();
	
	for(int i=0; i<v.size(); i++)
	{
		int id = ((Integer)v.elementAt(i)).intValue();
		if((logchk.getPrelimRatingScaleID() == 0) && i == 0){
			logchk.setPrelimRatingScaleID(id);
		}
		if(logchk.getPrelimRatingScaleID() == id)
		{ %>
			<option value=<%=id%> selected><%=id%></option>
		<% } else { %>
			<option value=<%=id%>><%=id%></option>
		<%	}	
	} 
%>
	</select>
	</td>
	<td><input type="button" value="<%= trans.tslt("Remove rating scale") %>" name="btnDeleteScale" onclick="return deleteScale(this.form)"></td>
	<td align="center">&nbsp;</td>
</tr>
	<tr>
	<td width="96">&nbsp;</td>
	<td width="246">&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
<td width="96"><font face="Arial" size="2"><%= trans.tslt("Rating") %>:</font></td>
<td width="246">
<input name="RatingName" type="text"  style='font-size:10.0pt;font-family:Arial' id="RatingName" size="25" maxlength="50">
</td>
<td align="center">&nbsp;</td>
</tr>
<tr>
<td width="96">&nbsp;</td>
<td width="246">&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td colspan="3">
<input type="button" name="Add Rating" value="<%=trans.tslt("Add Rating")%>" onclick="ConfirmAddRating(this.form,this.form.RatingName)">
</td>
</tr>
</table>
<br><br>
<table width="80%" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
<th width ="10" bgcolor="navy" bordercolor="#3399FF"></th>
<th width="15" bgcolor="navy" bordercolor="#3399FF"><b>
<font style='color:white'>No</font></b></th>
<th bgcolor="navy" bordercolor="#3399FF"><b>
<font style='color:white'><u><%= trans.tslt("Rating Name") %></u></font></b></a></th>

<% 
	aResult = PrelimQController.getAllRating(logchk.getPrelimRatingScaleID());
	for(Map.Entry<Integer, String> entry : aResult.entrySet()){
		String rating = Integer.toString(entry.getKey());
		String value = entry.getValue();
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'">
       <td style="border-style: solid; border-width: 1px" bordercolor="#3399FF">
	   		<font size="2">
   
	   		<input type="checkbox" name="checkdel" value=<%=rating%>></font>	   </td>
	   
   		  <% 
   		  //out.print(DisplayNo); 
   		  
   		  %>
   		  <td> 
   		  	<select name="selectSeq"><%
   		  	int selected = Integer.parseInt(rating);
   		  	for(int n=1; n<=aResult.size(); n++){
   	   		  	String shownSelected = "";
   		  		if(selected == n)
   		  			shownSelected = "selected";
   		  		out.print("<option value=\""+n+"\" "+shownSelected+">"+n+"</option>");
   		  	}
   		  	%></select> 
   		  </td>
   		
	   <td style="border-style: solid; border-width: 1px" bordercolor="#3399FF">
	   	<input type="text" name = "content" value="<% out.print(value);%>">
	   </td>
   </tr>
<% 	DisplayNo++;
	} 

%>
</table>
<br>

<table border="0" width="80%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<input type="button" name="Delete" value="<%= trans.tslt("Delete") %>"  onclick = "return confirmDelete(this.form, this.form.checkdel)">
		</td>
		<td><input type="button" value="<%= trans.tslt("Update") %>" name="btnUpdate" onclick="return confirmUpdate(this.form, this.form.selectSeq, this.form.content)"></td>
		<td><input type="button" value="<%=trans.tslt("Close Window")%>" name="btnClose" onclick="closeWindow()"></td>
	</tr>
</table>

</div>
</td></tr>
</table>
<p></p>



&nbsp;&nbsp;&nbsp;

</form>

</body>
</html>