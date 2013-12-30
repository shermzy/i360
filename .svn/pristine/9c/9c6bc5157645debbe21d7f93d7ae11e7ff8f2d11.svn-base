<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
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
<jsp:useBean id="EditRS" class="CP_Classes.RatingScale" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.votblScale" %>
<%@ page import="CP_Classes.vo.votblScaleValue" %>

<script language = "javascript">

function confirmEdit(form, defaultValue, type, saveType)
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
		
		if(confirm("<%=trans.tslt("Edit Rating Scale")%>?")) {
			form.action = "EditRatingScale.jsp?edit=" + saveType + "&checked=" + defaultChecked + "&scaleType=" + type;
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
//void function cancelEdit()
function cancelEdit()
{
	window.close();
}

function scaleChange(form)
{
	if(form.scale.value != 0) {
		form.action = "EditRatingScale.jsp?clicked=1&scale=" + form.scale.value;
		form.method = "post";
		form.submit();
		return true;
	} else {
		alert("<%=trans.tslt("Please select Rating Scale")%>");
		return false;
	}	
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
  /**********************************
  *Edit By James 15 Nov 2007
  *********************************/
	Vector SType = EditRS.getRecord();
	//Vector rs = SType;
	Vector scaleValue = SType;
	String statement = "", scaleType = "";
	int defaultScale = 0, RScale = 0, ScaleID = 0;
	int Edit = 0;
	int orgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();
	votblScaleValue voValue = new votblScaleValue();
	
	int defaultFlag = 0;
	
	if(request.getParameter("clicked") != null) {
		Edit = 1;	
		if(request.getParameter("scale") != null) 
			ScaleID = EditRS.getScaleID();
		else {
			ScaleID = Integer.parseInt(request.getParameter("clicked"));
			EditRS.setScaleID(ScaleID);
		}		
		
		//rs = Database.getRecord("Select * from tblScale where ScaleID = " + ScaleID);
		//rs.next();
		votblScale vo_Scale=EditRS.getRecord(ScaleID);
		if(vo_Scale!=null){
		statement = vo_Scale.getScaleDescription();
		scaleType = vo_Scale.getScaleType();
		defaultScale = vo_Scale.getScaleDefault();
		RScale = vo_Scale.getScaleRange();
		}
		if(request.getParameter("scale") != null)
			RScale = Integer.parseInt(request.getParameter("scale"));
			
		EditRS.setRS(RScale);
		
		scaleValue = EditRS.getScaleValue(ScaleID);
		
		if(scaleValue.size() != 0) {
			voValue = (votblScaleValue)scaleValue.elementAt(0);
		
		}
		
		
		int check = EditRS.CheckSysLibRatingScale(ScaleID);
			
		if(check == 1 && userType != 1) {				
%>
<script>
	alert("The edited System Generated Library will be saved as a new User Generated Library");
</script>	
<%									
		}
		
	}else if(request.getParameter("edit") != null) {
		RScale = EditRS.getRS();

		//int check = EditRS.CheckSysLibRatingScale(ScaleID);
		
		defaultScale = Integer.parseInt(request.getParameter("checked"));
		scaleType = request.getParameter("scaleType");
		statement = request.getParameter("Statement");
		
		int defaultExist = EditRS.checkDefaultExist(scaleType);
		if(defaultExist == 1 && defaultScale == 1) {
			defaultScale = 0;
			defaultFlag = 1;
		}
		
		int iMaxStrLength = 2000;						// Max char of description length
		int iMaxThaiLength = (iMaxStrLength/7);			// Max char of description length in Thai (for "alert" only)
		int iDescriptionLength = statement.length();	// Length of Scale description
		int iMaxScaleValue = 1000;						// Max char of scale value description
		int iMaxScaleValueThai = (iMaxScaleValue/7);	// Max char of scale value description in Thai (for "alert" only)
		int iScaleValueLength = 0;						// Length of Scale Value description
		String sScaleValueDesc = "";					// Store Scale Value description
		boolean bIsLengthValid = true;

		Vector value = EditRS.getValueID(ScaleID);
		int total1 = EditRS.getTotal(ScaleID);
		
		int valueID [] = new int [total1];
		for(int id=0; id<total1; id++) {
			if(id<value.size()) {
				valueID[id] = ((Integer)value.elementAt(id)).intValue();
			}
		}
		
		String desc[] = new String[RScale+1];
		boolean added = false;
		for(int i=0; i<=RScale; i++)
		{
			sScaleValueDesc = request.getParameter("scaleDesc"+i);
			iScaleValueLength = sScaleValueDesc.length();
						
			if(iScaleValueLength < iMaxScaleValue) {
				desc[i] = sScaleValueDesc;					
			} 
			else {
				bIsLengthValid = false;
				i = RScale + 1;
			}
		}
		
		boolean edittblScale;
		int addedID= 0;
		
		if (iDescriptionLength < iMaxStrLength)
		{
			if (bIsLengthValid == true)
			{
				for(int i=0; i<=RScale; i++)
				{
					
					desc[i] = request.getParameter("scaleDesc"+i);
				}

				if(Integer.parseInt(request.getParameter("edit")) == 1) {
					ScaleID = EditRS.getScaleID();
					int exist = EditRS.CheckRatingScaleExist(statement, scaleType, defaultScale, compID, orgID, RScale, ScaleID);
					if(exist == 0) {
						try {
							
							edittblScale = EditRS.edittblScale(ScaleID, scaleType, statement, defaultScale, RScale, pkUser);
							if (edittblScale == true)
							{
							//Added by Ha 27/05/08 to promt out the Edited sucessfully message
%>
							<script>
					  		alert('<%=trans.tslt("Edited succesfully")%>');
					  		opener.location.href = 'RatingScale.jsp';
					  		window.close();
							</script>
<% 								
							}
							EditRS.deletetblScaleValuebyScale(ScaleID);		
						}catch(SQLException SE) {}
					}
					else {
%>
						<script>
					  		alert('<%=trans.tslt("Record exists")%>');
					  		window.close();
						</script>
<%				
					}			
						
				}
				else {
					ScaleID = 0;
					boolean canAdd = false;
					int exist = EditRS.CheckRSExist(statement, scaleType, defaultScale, compID, orgID, RScale);
					if(exist == 0) {
						try {								
							canAdd = edittblScale = EditRS.addtblScale(statement, scaleType, defaultScale, compID, orgID, RScale, pkUser, userType);	
							if (canAdd)
							{
							//Added by Ha 27/05/08 to promt out the Added sucessfully message
%>
								<script>
						  		alert('<%=trans.tslt("Added succesfully")%>');
						  		opener.location.href = 'RatingScale.jsp';
					  			window.close();
								</script>
<% 							}
							
						}catch(SQLException SE) {}
						ScaleID = EditRS.ScaleID(compID, orgID, statement, scaleType);
					}
					else {
%>
						<script>
					  		alert('<%=trans.tslt("Record exists")%>');
					  		window.close();
						</script>
<%				
					}			
					
				}
			
				// add not applicable
				try {
					added = EditRS.addtblScaleValue(desc[0], ScaleID, 0, 0);
				}catch(SQLException SE) {}
						
				// compare the desc whether they are the same, start compare from 2nd with 1st
				for(int i=1, low=1, high=1; i<=RScale; i++) {
	
					if(desc[i].equals(desc[i-1])) {
						high = i;
						if(i == RScale) {
							try {
								added = EditRS.addtblScaleValue(desc[i], ScaleID, low, high);
							}catch(SQLException SE) {}
						}
						else {
							if(desc[i].equals(desc[i+1]) == false) {
								try {
									added = EditRS.addtblScaleValue(desc[i], ScaleID, low, high);
								}catch(SQLException SE) {}
							}
						}
					} else {
						low = i;
						high = i;
						if(i == RScale) {
							try	{
								added = EditRS.addtblScaleValue(desc[i], ScaleID, low, high);		// all adding need to add in FKOrg ID
							}catch(SQLException SE) {}
						}
						else {
							if(desc[i].equals(desc[i+1]) == false) {
								try {
									added = EditRS.addtblScaleValue(desc[i], ScaleID, low, high);
								}catch(SQLException SE) {}
							}
						}
					}
				} // for
			} else { 	// if (bIsLengthValid == true)
%>				<script>
					alert("Scale Value Description cannot be longer than <%=iMaxScaleValueThai%> characters");
					//history.back();
				</script>
<%			}
		} // end if (iDescriptionLength < iMaxStrLength)
		else
		{
%>			<script>
				alert("Scale Description cannot be longer than <%=iMaxThaiLength%> characters");
				//history.back();
			</script>
<%		}

    if(defaultFlag == 0) { 
%>
		<script>
	  		window.close();
			opener.location.href = 'RatingScale.jsp';
		</script>
<%
	}else if(defaultFlag == 1 ) {
%>
<script>
  		alert("<%=trans.tslt("Default Value already exists, this rating scale cannot be set as default")%> !");
		window.close();
		opener.location.href = 'RatingScale.jsp';
</script>			
<%			
		
	}
	}
%>


<form name="EditRS" method="post">
<table border="0" font style='font-size:10.0pt;font-family:Arial' width="500">
<tr>
<td width="80"><%= trans.tslt("Rating Scale") %></td>
<td width="10">&nbsp;</td>
<td width="410" align="left"> <select name="scale" onChange="scaleChange(this.form)">

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
<table width="500" border="0" font style='font-size:10.0pt;font-family:Arial'>
  <tr>
    <td width="80"><%= trans.tslt("Scale") %></td>
	<td width="10">&nbsp;</td>
    <td width="410">
      <textarea name="Statement" style='font-size:10.0pt;font-family:Arial' cols="50" rows="5"><%=statement%></textarea>
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><%= trans.tslt("Scale Type") %></td>
	<td>&nbsp;</td>
    <td>
      <select name="scaleType">
	  <%
			if(Edit == 1) {
				for(int i=0; i<SType.size(); i++) {
					votblScale vo = (votblScale)SType.elementAt(i);
					String type = vo.getScaleType();
					if(type.equals(scaleType)) {
		%>
			<option value=<%=type%> selected><%=type%>
		<%			} else {
		%>		
			<option value=<%=type%>><%=type%>
		<%
					}		
			}
		%>
      </select>
    </td>
  </tr>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
    <td> 
	<% if(defaultScale == 1) {
	%>
		<input type="checkbox" name="defaultValue" value="" checked>
	<% } else {
	%>
		<input type="checkbox" name="defaultValue" value="">
	<% } %>
      <%= trans.tslt("Default Scale") %></td>
  </tr>
</table>
<p></p>

<table width="419" border="1" font style='font-size:10.0pt;font-family:Arial'>
<th width="300" align="center" bgcolor="navy"><b><font style='color:white'><%= trans.tslt("Description") %></font></b></th>
<th width="119" align="center" bgcolor="navy"><b><font style='color:white'><%= trans.tslt("Value") %></font></b></th>

<%	
	int low = 0,high=0;
	String desc = "";
	String temp = "scaleDesc";

	low = voValue.getLowValue();
	high = voValue.getHighValue();
	desc = voValue.getScaleDescription();	
	
	int index = 1;
	for(int i=0; i<=RScale; i++) { 		
		//System.out.println("--->" + i + "---"+low);
%>
<tr align="center">
    <td>
		<input name="<%=temp+i%>" type="text" value="<%=desc%>" size="50">
	</td>
	<td><%=low%></td>
</tr>
<%	
	//System.out.println("---------------->" + low + "<-------------->" + high);
	if(low == high && i<RScale) {
		
		if(scaleValue.size() > 1 && scaleValue.size()> index) {
			
			voValue = (votblScaleValue)scaleValue.elementAt(index);
			low = voValue.getLowValue();
			high = voValue.getHighValue();
			desc = voValue.getScaleDescription();	
			index++;
		}else {
			low = low+1;
			high = low;
			desc = "";
		}
	}else
		low++;
	}
	}
%>
 </table> 



<blockquote>
<p>
<%
	if(EditRS.CheckSysLibRatingScale(ScaleID) == 1 && userType != 1) {
%>				
  <input type="button" name="btnEdit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.defaultValue, this.form.scaleType.options[scaleType.selectedIndex].value, 1)" disabled>
<% } else { %>
	<input type="button" name="btnEdit" value="<%= trans.tslt("Save") %>" onClick="return confirmEdit(this.form, this.form.defaultValue, this.form.scaleType.options[scaleType.selectedIndex].value, 1)">
<% } %>	  
	<input type="button" name="btnEdit" value="<%= trans.tslt("Save As New") %>" onClick="return confirmEdit(this.form, this.form.defaultValue, this.form.scaleType.options[scaleType.selectedIndex].value, 2)">
  <input type="button" name="btnCancel" value="<%= trans.tslt("Cancel") %>" onclick="cancelEdit()">
</p>
</blockquote>
</form>
<% } %>

</body>
</html>
