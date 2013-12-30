<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%//by Hemilda date 23/09/2008 to fix import after add UTF-8%>
<%@ page import = "CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rating Scale</title>
<meta http-equiv="Content-Type" content="text/html">
<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="AddRS" class="CP_Classes.RatingScale" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<script language="javascript">
//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function scaleChange(form, scale) {
function scaleChange(form, scale) {
	var query = "AddRatingScale.jsp?scalePoints=" + scale;
	form.action = query;
	form.method = "post";
	form.submit();
}

function confirmAdd(form, defaultValue, type)
{
	var scale = form.scale.value;
	var statement = 0;
	var defaultChecked = 0;

	if(scale == 0) {
		alert("<%=trans.tslt("Please select the Rating Scale")%>");
		return false;
	} else if(form.Statement.value != "") 
		statement = 1;
	
	if(statement == 1) {
		if(defaultValue.checked)
			defaultChecked = 1;
		
		for(var s=0; s<=scale; s++) {
			var temp = "";
			switch(s) {
				case 0 : temp = form.scaleDesc0.value;
						 break;
				case 1 : temp = form.scaleDesc1.value;
						 break;
				case 2 : temp = form.scaleDesc2.value;
						 break;
				case 3 : temp = form.scaleDesc3.value;
						 break;
				case 4 : temp = form.scaleDesc4.value;
						 break;						 
				case 5 : temp = form.scaleDesc5.value;
						 break;
				case 6 : temp = form.scaleDesc6.value;
						 break;
				case 7 : temp = form.scaleDesc7.value;
						 break;
				case 8 : temp = form.scaleDesc8.value;
						 break;
				case 9 : temp = form.scaleDesc9.value;
						 break;
				case 10 : temp = form.scaleDesc10.value;
						 break;		 		 		 		 
			}

			if(temp == "") {
				alert("<%=trans.tslt("Please fill in all the scale value")%>");
				return false;
			}
		}
		
		if(confirm("<%=trans.tslt("Add Rating Scale")%>?")) {
			form.action = "AddRatingScale.jsp?add=1&" + "checked=" + defaultChecked + "&scaleType=" + type;
			form.method = "post";
			form.submit();
			return true;
		} else
			return false;
			
	} else {
			alert("<%=trans.tslt("Please enter Statement")%>");		
			form.Statement.focus();
		return false;
	}

	return true;
}

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function cancelAdd()
function cancelAdd()
{
	window.close();
}
</script>


<%
	//response.setHeader("Pragma", "no-cache");
	//response.setHeader("Cache-Control", "no-cache");
	//response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	    	<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 	
  	
	int RScale = 0; // for scale points, to determine the table
	String ScaleType = "";
	int scale = 0;
	
	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	/************************************
	* Edit By James 15 - Nov 2007
	************************************/
	Vector SType=AddRS.getRecord();
	
	String statement = "";
	String desc[] = null;

	if(request.getParameter("scalePoints") != null) {	
		RScale = Integer.parseInt(request.getParameter("scalePoints"));
		AddRS.setRS(RScale);
	}else if(request.getParameter("add") != null) {
		scale = AddRS.getRS();
		RScale  = scale;
		
		ScaleType = request.getParameter("scaleType");
		statement = request.getParameter("Statement");
		int defaultValue = Integer.parseInt(request.getParameter("checked"));
		int FKOrg = 1;
		
		statement = Database.SQLFixer(statement);	
		ScaleType = Database.SQLFixer(ScaleType);	

		int defaultExist = AddRS.checkDefaultExist(ScaleType);
		if(defaultExist == 1 && defaultValue == 1) {
			defaultValue = 0;
%>
<script>
  		alert("<%=trans.tslt("Default Value is already exist, this rating scale will be set as non default")%> !");
</script>			
<%			
		}
		
		try {
			int iMaxStrLength = 2000;						// Max char of description length
			int iMaxThaiLength = (iMaxStrLength/7);			// Max char of description length in Thai (for "alert" only)
			int iDescriptionLength = statement.length();	// Length of Scale description
			int iMaxScaleValue = 1000;						// Max char of scale value description
			int iMaxScaleValueThai = (iMaxScaleValue/7);	// Max char of scale value description in Thai (for "alert" only)
			int iScaleValueLength = 0;						// Length of Scale Value description
			String sScaleValueDesc = "";					// Store Scale Value description
			boolean bIsLengthValid = true;
			
			if (iDescriptionLength < iMaxStrLength)
			{
			
			
				int exist = AddRS.CheckRSExist(statement, ScaleType, defaultValue, compID, orgID, scale);
			
			
				desc = new String[scale+1];
				for(int i=0; i<=scale; i++)
				{
					sScaleValueDesc = request.getParameter("scaleDesc"+i);
					iScaleValueLength = sScaleValueDesc.length();
					
					if(iScaleValueLength < iMaxScaleValue) {
						desc[i] = sScaleValueDesc;			
						
						System.out.println("----"+desc[i]);		
					} 
					else {
						bIsLengthValid = false;
						i = scale + 1;
					}
				}
				
				
				if(exist == 0) {

				boolean add = AddRS.addtblScale(statement, ScaleType, defaultValue, compID, orgID, scale, pkUser, userType);
				
				if(add == true) {
					int ScaleID = AddRS.ScaleID(compID, orgID, statement, ScaleType);
			
					boolean added = false;
			
					desc = new String[scale+1];
					for(int i=0; i<=scale; i++)
					{
						sScaleValueDesc = request.getParameter("scaleDesc"+i);
						iScaleValueLength = sScaleValueDesc.length();
						
						if(iScaleValueLength < iMaxScaleValue) {
							desc[i] = sScaleValueDesc;					
						} 
						else {
							bIsLengthValid = false;
							i = scale + 1;
						}
					}
					
					if (bIsLengthValid == true)
					{
						// add not applicable
						try {
							added = AddRS.addtblScaleValue(desc[0], ScaleID, 0, 0);
						}catch(SQLException SE) {}
					
						// compare the desc whether they are the same, start compare from 2nd with 1st
						for(int i=1, low=1, high=1; i<=scale; i++) {
							if(desc[i].equals(desc[i-1])) {
								high = i;
								if(i == scale) {
									try {
										added = AddRS.addtblScaleValue(desc[i], ScaleID, low, high);
									}catch(SQLException SE) {}
								}
								else {
									if(desc[i].equals(desc[i+1]) == false) {
										try {
											added = AddRS.addtblScaleValue(desc[i], ScaleID, low, high);
										}catch(SQLException SE) {}
									}
								}
							}
							else {
								low = i;
								high = i;
								if(i == scale) {
									try	{
										added = AddRS.addtblScaleValue(desc[i], ScaleID, low, high);		// all adding need to add in FKOrg ID
									}catch(SQLException SE) {}
								}
								else {
									if(desc[i].equals(desc[i+1]) == false) {
										try {
											added = AddRS.addtblScaleValue(desc[i], ScaleID, low, high);
										}catch(SQLException SE) {}
									}
								}
							}
						}// for
						
						if(added) {
%>
						<script>
					  		alert("Added successfully");
							window.close();
							opener.location.href = "RatingScale.jsp";
						</script>
						
<% 					
						}
					} // end if (bIsLengthValid == true)
					else
					{
%>						<script>
							alert("Scale Value Description cannot be longer than <%=iMaxScaleValueThai%> characters");
						</script>
<%					}
				} // end if(add == true) 
				} else {
				
%>
				<script>
			  		alert('<%=trans.tslt("Record exists")%>');
			  		
				</script>
<%								
				
				}
				
			} else {
%>
					<script>
						alert("Scale Description cannot be longer than <%=iMaxThaiLength%> characters");
					</script>
<%			}
		
		}catch(SQLException SE) { %>
			<script>
		  		//alert("Data added successfully!");
				window.close();
				opener.location.href = "RatingScale.jsp";
			</script>
<%		} 
	}
%>

<form name="AddRS" method="post">
<table border="0" font style='font-size:10.0pt;font-family:Arial'>
<tr>
<td width="80"><%= trans.tslt("Rating Scale") %></td>
<td width="10">&nbsp;</td>
<td width="420" align="left"> <select name="scale" onchange="scaleChange(this.form, this.form.scale.options[scale.selectedIndex].value)">

	   <% int t=0;
	   %>
	   <option value=<%=t%> selected><%=""%>
	   <%
			for(int scale1 = 3; scale1 <= 10; scale1++) {
				if(RScale == scale1) {
		%>
			<option value=<%=scale1%> selected><%=scale1%>
		<%  } else {
		%>
			<option value=<%=scale1%>><%=scale1%>
		<%
			}
			}
		%>
      </select></td>
</tr>
</table>
<p></p>
<table border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="80"><%= trans.tslt("Scale") %></td>
    <td width="10">&nbsp;</td>
    <td width="410">
      &nbsp;<!--webbot bot="Validation" b-value-required="TRUE" i-maximum-length="50" --><textarea name="Statement" style='font-size:10.0pt;font-family:Arial' cols="50" rows="5" ><%=statement%></textarea>
	</td>
  </tr>
</table>
<p></p>
<table border="0" font style='font-size:10.0pt;font-family:Arial'>
<tr>
<td width="80"><font size="2"><%= trans.tslt("Scale Type") %></font> </td>
    <td width="10">&nbsp;</td>
    <td width="410">
	  <select name="scaleType">
	  <%
	  /****************************
	  *Edit By James 15 - Nov 
	  ****************************/
			//while(SType.next()) 
			for(int i=0;i<SType.size();i++)
			{
				votblScale vo_Scale=(votblScale)SType.elementAt(i);
				String type = vo_Scale.getScaleType();
		%>
			<option value=<%=type%>><%=type%>
		<%
			}
		%>
      </select></td>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><input type="checkbox" name="defaultValue" value=""><%= trans.tslt("Default Scale") %></td>
</tr>
</table>
<p></p>


<table width="418" border="1" font style='font-size:10.0pt;font-family:Arial'>
<th width="300" align="center" bgcolor="navy"><b>
<font style="color: white"><%= trans.tslt("Description") %></font></b></th>
<th width="80" align="center" bgcolor="navy"><b>
<font style="color: white"><%= trans.tslt("Value") %></font></b></th>
<%
	for(int i=0; i<=RScale; i++) { 
		String temp = "scaleDesc";
		
		String sDescription ="";
		
		if(desc != null)
			sDescription = desc[i];

%>
<tr>
    <td>
	<input name="<%=temp+i%>" type="text" size="50"  style='font-size:10.0pt;font-family:Arial' value="<%=sDescription%>"></td>
    <td align="center"><% out.println(i); %></td>
</tr>
<%
	}
%>
</table>
<p>
  <input type="button" name="btnAdd" value="<%= trans.tslt("Add") %>" onClick="return confirmAdd(this.form, this.form.defaultValue, this.form.scaleType.options[scaleType.selectedIndex].value)"><font size="2">
	</font>
  <input type="button" name="btnCancel" value="<%= trans.tslt("Cancel") %>" onclick="cancelAdd()"><font size="2">
	</font>
</p>
</form>
<% } %>
</body>
</html>