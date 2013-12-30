<%@ page import="java.sql.*,
                 java.io.*,
				 java.util.*,
                 java.lang.String"%>  
                 
<jsp:useBean id="TimeFrame" class="CP_Classes.TimeFrame" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<% 	// added to check whether organisation is a consulting company
// Mark Oei 09 Mar 2010 %>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<%@ page import="CP_Classes.vo.votblTimeFrame"%>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

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

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function Delete(form, field)
function Delete(form, field)
{
	if(check(field))
	{
		if(confirm("<%=trans.tslt("Delete Time Frame")%>?"))
		{
			form.action="TimeFrame.jsp?Delete=1";
			form.method="post";
			form.submit();
		}
	}

}
//void function Edit(form, field1, field2)
function Edit(form, field1, field2)
{
//by Hemilda date 06/06/2008 min value = 1 and max 1 dec digits behind
	var flagDec=false;
	var posDec = 0;
	var fieldStrTemp="";
	var fieldStr = field2.value;
	var lenDec = 0;
	
	if(check(field1))
	{
		if(field2.value != "")
		{
			if(confirm("<%=trans.tslt("Edit Time Frame")%>?"))
			{
				if(isNumericValue(field2.value)) {
					if (fieldStr  < 1)
					{
						alert("The minimum number is 1");
						return false;
					}else{
						posDec = fieldStr.indexOf('.');
						if (posDec  >= 0)
						{
							lenDec = ((fieldStr.substr(posDec+1, fieldStr.length)).length);
							if(lenDec > 1)
							{
								flagDec = true;
							}
						}
							
						if (flagDec==true)
						{
							fieldStrTemp = fieldStr.substr(0, fieldStr.length - lenDec);
							fieldStrTemp = fieldStrTemp +  fieldStr.substr(posDec+1, 1);
							document.TimeFrame.txtTimeFrame.value = eval(fieldStrTemp);
						}
					
						form.action="TimeFrame.jsp?Edit=1";
						form.method="post";
						form.submit();
					}
				} else {
					alert("Please input numeric values only");
					return false;
				}
			}
		}
		else
		{
			alert("<%=trans.tslt("Please enter Time Frame")%>");
		}
	}

}
//void function Add(form, field)
function Add(form, field)
{
	var flagDec=false;
	var posDec = 0;
	var fieldStrTemp="";
	var fieldStr = field.value;
	var lenDec = 0;
	
	if(field.value != "")
	{
		if(confirm("<%=trans.tslt("Add Time Frame")%>?"))
		{
			//by Hemilda date 06/06/2008 min value = 1 and max 1 dec digits behind
				if(isNumericValue(fieldStr)) {
					if (fieldStr < 1)
					{
						alert("The minimum number is 1");
						return false;
					}else{
							posDec = fieldStr.indexOf('.');
							if (posDec  >= 0)
							{
								lenDec = ((fieldStr.substr(posDec+1, fieldStr.length)).length);
								if(lenDec > 1)
								{
									flagDec = true;
								}
							}
							
							if (flagDec==true)
							{
								fieldStrTemp = fieldStr.substr(0, fieldStr.length - lenDec);
								fieldStrTemp = fieldStrTemp +  fieldStr.substr(posDec+1, 1);
								document.TimeFrame.txtTimeFrame.value = eval(fieldStrTemp);
							}
						form.action="TimeFrame.jsp?Add=1";
						form.method="post";
						form.submit();
					}
				} else {
					alert("Please input numeric values only");
					return false;
				}
		}
	}
	else
	{
		alert("<%=trans.tslt("Please enter Time Frame")%>");
	}
}

/*	choosing organization*/

function proceed(form,field)
{
	form.action="TimeFrame.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}

function show(field1,field3,field4,field5)
{
	
	for (i = 0; i < field1.length; i++) 
	{
		if(field1[i].checked)
		{
			for (j = 0; j < window.document.TimeFrame.TimeType.length; j++) 
			{
			
				if(window.document.TimeFrame.TimeType[j].value == field4[i].value)
				{
					window.document.TimeFrame.TimeType.selectedIndex=j
				}
			}
			
		 	var index = field3[i].value.indexOf(" ");
			field5.value = field3[i].value.substring(0, index);
		}
	}
	if(field1.checked)
	{	
		
		var index = field3.value.indexOf(" ");
		field5.value = field3.value.substring(0, index);
	}
	
}

function isNumericValue(str) {
	var isValid = true;
	
	//using regular expression to search for string existence
	if(str.search(/^\d+(\.\d+)?$/) == -1) 
		isValid = false;
	
	return isValid;
}


</script>
<body style="text-align: left">
<%
//response.setHeader("Pragma", "no-cache");
//response.setHeader("Cache-Control", "no-cache");
//response.setDateHeader("expires", 0);

String username=(String)session.getAttribute("username");

  if (!logchk.isUsable(username)) 
  {%> <font size="2">
   
    	<jsp:forward page="Login.jsp"/>
<%  } 
  else 
  { 

if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}


if(request.getParameter("Delete") != null)
{

	int TimeFrame_ID = new Integer(request.getParameter("TimeFrame_ID")).intValue();
	
	boolean bIsDeleted = TimeFrame.deleteRecord(TimeFrame_ID, logchk.getPKUser());

	if(bIsDeleted) {
%>
		<script>
		alert("Deleted successfully");
		</script>
<%
	} 

}

if(request.getParameter("Edit") != null)
{
	/******************************
	* Edited by junwei 5 March 2008
	*******************************/
	int TimeFrame_ID = new Integer(request.getParameter("TimeFrame_ID")).intValue();//selected radio time frame
	int TimeType = new Integer(request.getParameter("TimeType")).intValue();//selected time type
	
	String txtTimeFrame = request.getParameter("txtTimeFrame");//entered time frame
	
	boolean result = TimeFrame.checkTimeFrameExist(txtTimeFrame, TimeType, logchk.getOrg());

	if(result == true) {
	
%>		
		<script>
		alert("Record exists");
		</script>
<%	
	
	} else {
		boolean bIsEdited = TimeFrame.editRecord(TimeFrame_ID, txtTimeFrame, TimeType, logchk.getOrg(), logchk.getPKUser());
	
		if(bIsEdited) {
%>
		<script>
		alert("Edited successfully");
		</script>
<%
		} 
	}
}

if(request.getParameter("Add") != null)
{
	//edited by junwei 28 feb 2008
	int TimeType = new Integer(request.getParameter("TimeType")).intValue();
	String txtTimeFrame = request.getParameter("txtTimeFrame");
	
	boolean bExist = TimeFrame.existRecord(txtTimeFrame, TimeType, logchk.getOrg());
	
	if(bExist == true) {
	
	%>		
		<script>
		alert("Record exists");
		</script>
	<%		
	
	} 
	else {
	
		boolean bIsAdded = TimeFrame.addRecord(txtTimeFrame, TimeType, logchk.getOrg(), logchk.getPKUser());
	
		if(bIsAdded) {
		
		%>
			<script>
			alert("Added successfully");
			</script>
		<%
		
		} 
	}
}
%>

<form name ="TimeFrame" method="post" action="TimeFrame.jsp">

<table border="0" width="99%" cellspacing="0" cellpadding="0" height="79">

	<tr>
		<td colspan="4"><b><font face="Arial" color="#000080" size="2"><%=trans.tslt("Time Frame")%></font></b></td>
	</tr>
	<tr>
   
		<td colspan="4">
<font size="2">
   
<ul>
	<li><font face="Arial" size="2"><%=trans.tslt("To Add, click on the Add button")%>.</font></li>
	<li><font face="Arial" size="2"><%=trans.tslt("To Edit, click on the relevant radio button and click on the Edit button")%>.</font></li>
	<li><font face="Arial" size="2"><%=trans.tslt("To Delete, click on the relevant radio button and click on the Delete button")%>.</font></li>
</ul>
		</td>
		</tr>

	<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
	<tr>
   
		<td width="21%"><b><font face="Arial" size="2"><%=trans.tslt("Organisations")%>:</font></b></td>
		<font size="2">
   
		<td width="17%"> 
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
    	<select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
<%
// Added to check whether organisation is also a consulting company
// if yes, will display a dropdown list of organisation managed by this company
// else, it will display the current organisation only
// Mark Oei 09 Mar 2010
	String [] UserDetail = new String[14];
	UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
	boolean isConsulting = true;
	isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
	if (isConsulting){
		Vector vOrg = logchk.getOrgList(logchk.getCompany());

		for(int i=0; i<vOrg.size(); i++)
		{
			votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
			int PKOrg = vo.getPKOrganization();
			String OrgName = vo.getOrganizationName();

			if(logchk.getOrg() == PKOrg)
			{ %>
				<option value=<%=PKOrg%> selected><%=OrgName%></option>
			<% } else { %>
				<option value=<%=PKOrg%>><%=OrgName%></option>
			<%	}	
		} 
	} else { %>
		<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
	<% } // End of isConsulting %>
</select></td>
   
		<td width="11%">&nbsp;</td>
   
		<td width="50%">&nbsp; 
		
   
    	</td>
		</tr>
	<tr>
		<font size="2" face="Arial" style="font-size: 14pt" color="#000080">
   
		<td colspan="4">&nbsp;
</td>
	</tr>

	<tr>
		<td colspan="4">&nbsp;
</td>
	</tr>

	<tr>
		<td colspan="4">
<table border="0" width="83%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="204">
<div style='width:227px; height:136px; z-index:1; overflow:auto'>			
<table border="1" width="210" height="12" bgcolor="#FFFFCC" bordercolor="#3399FF">

	<tr>
		<td colspan="2" bgcolor="Navy" height="27">
		<p align="center">
		<b><font face="Arial" color="#FFFFFF" size="2"><%=trans.tslt("Time Frame")%></font></b></td>
	</tr>

<%
   /********************
	* Edited by James 25 Oct 2007
	************************/
	 Vector v = TimeFrame.getAllTimeFrames(logchk.getOrg());
	 
	 for(int i=0; i<v.size(); i++) {
	  
		votblTimeFrame vo = (votblTimeFrame)v.elementAt(i);
      

		int TimeFrame_ID = vo.getTimeFrameID();
		String TimeFrame_Desc = vo.getTimeFrame();
		int TimeType = vo.getTimeType();
%>
  <tr onMouseOver = "this.bgColor = '#99ccff'"
    	onMouseOut = "this.bgColor = '#FFFFcc'">

		<td width="12%" align="center"><font face="Arial">
		<span style="font-size: 11pt"><input type="radio" name="TimeFrame_ID" value=<%=TimeFrame_ID%> onclick="show(TimeFrame_ID,data1,data2,txtTimeFrame)"></span><font size="2">
		</font></font>
		<td width="54%" align="center">
		<font face="Arial" size="2"><%=TimeFrame_Desc%></font>
		<input type=hidden value="<%=TimeFrame_Desc%>" name="data1">
		<input type=hidden value="<%=TimeType%>" name="data2">
		
		</td>
</tr>
<%	}	%>		

</table>
</div>
		</td>
		<td width="528" align="center">
		&nbsp;<table border="0" width="84%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" width="160">
		<font size="2" face="Arial"><b><%=trans.tslt("Time Frame")%>:</b>&nbsp;
		</font>
		<input type="text" name="txtTimeFrame" size="7"></td>
				<td align="center">
				<p align="right">
				<b><font size="2" face="Arial"><%=trans.tslt("Time Type")%>:
				</font></b><font size="2">&nbsp;&nbsp; </font> </td>
				<td align="center" width="121">
				<p align="left">
				<select size="1" name="TimeType">
				<option value="1"><%=trans.tslt("Month(s)")%></option>
				<option value="2"><%=trans.tslt("Year(s)")%></option>
				</select></td>
			</tr>
			<tr>
				<td align="center" width="160">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center" width="121">&nbsp;</td>
			</tr>
			<tr>
				<td align="center" width="160">
				<input type="button" value="<%=trans.tslt("Add")%>" name="btnAdd" onclick="Add(this.form, this.form.txtTimeFrame)" style="float: right"></td>
				<td align="center">&nbsp;
				</td>
				<td align="center" width="121">
				<input type="button" value="<%=trans.tslt("Edit")%>" name="btnEdit" onclick="Edit(this.form, this.form.TimeFrame_ID,this.form.txtTimeFrame)" style="float: left"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
		</td>
	</tr>
	<tr>
		<td width="93%" colspan="4">&nbsp;
		</td>
	</tr>
	<tr>
		<td width="86%" colspan="4">
		<table border="0" width="31%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="173">&nbsp;</td>
				<td>
				<input type="button" value="<%=trans.tslt("Delete")%>" name="btnDel" style="float: left" onclick="Delete(this.form, this.form.TimeFrame_ID)"></td>
			</tr>
		</table>
		</td>
	</tr>
	
</table>

</form>
<p></p>
<%@ include file="Footer.jsp"%>
<%	}	%>
</body>
</html>