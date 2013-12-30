<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String,
                 CP_Classes.vo.votblDRA,
				 CP_Classes.vo.votblDRARES"%>   

<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="DRAQuery" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="DRA" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="DRAResQuery" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script > 
	$(document).ready(function(){
	   //$('#MatchCompetencies_DResDAct').ajaxForm();
	   $("#chkAllDRA").click(function(){
		  if (this.checked) {
			$(this).attr('checked', true);
			$('input:checkbox[id^=chkComp]').each(function(){
				$(this).attr('checked', true);
			})
		  } else {
			$(this).attr('checked', false);
			$('input:checkbox[id^=chkComp]').each(function(){
				$(this).attr('checked', false);
			})
		  }
	   });
	   
	   $("#chkAllDRARes").click(function(){
		  if (this.checked) {
			$(this).attr('checked', true);
			$('input:checkbox[id^=chkRes]').each(function(){
				$(this).attr('checked', true);
			})
		  } else {
			$(this).attr('checked', false);
			$('input:checkbox[id^=chkRes]').each(function(){
				$(this).attr('checked', false);
			})
		  }
	   });
	   
	   $('table[id^=tbl]').each(function() {
			var $table = $(this);
			$('th', $table).each(function(column) {
				var $header = $(this);
				$header.click(function() {
				
					var sortDirection = 1;
					if ($header.is('.sorted-asc')) {
						sortDirection = -1;
					}
				
					var rows = $table.find('tbody > tr').nextAll().get();
	
					rows.sort(function(a, b) {
						var keyA = $(a).children('td').eq(column).text().toUpperCase();
						var keyB = $(b).children('td').eq(column).text().toUpperCase();
						if (keyA < keyB) return -sortDirection;
						if (keyA > keyB) return sortDirection;
						return 0;
					});
				
					$.each(rows, function(index, row) {
						$table.children('tbody').append(row);
					});
					
					$table.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
					if (sortDirection == 1) {
						$header.addClass('sorted-asc');
					}
					else {
						$header.addClass('sorted-desc');
					}
				});
			});
		});
	})
</script>
<SCRIPT LANGUAGE="JavaScript">

function check(field)
{
	var isValid = 0;
	var clickedValue = 0;
		
	if( field == null ) {
		isValid = 2;
	
	} else {
		for (i = 0; i < field.length; i++) 
			if(field[i].checked) {		
				clickedValue = field[i].value;
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
		return; //alert("No record selected");
	else if(isValid == 2)
		return; //alert("No record available");
	
	isValid = 0;	
	
}

function confirmSave(form, chkComp, chkRes, org, comp, selOrg, selComp, res)
{
	if(!check(chkComp) && !check(chkRes)) {
		alert("No records selected.");
		return;
	}
	
	//get the radio button resource type
	var clickedValue = 0;

	for (i = 0; i < res.length; i++) 
	if(res[i].checked) {
		clickedValue = res[i].value;
		res[i].checked = false;
	}
	
	form.action="MatchCompetencies_DResDAct.jsp?save=1" + "&org=" + org.value + "&comp=" + comp.value + "&org2="+selOrg.value + "&comp2=" + selComp.value + "&res=" + clickedValue;
	form.method="post";
	form.submit();
} 

function changeResourceType(form, org, comp, selOrg, selComp, res)
{
	var clickedValue = 0;
	
	for (i = 0; i < res.length; i++) 
	if(res[i].checked) {
		clickedValue = res[i].value;
		res[i].checked = false;
	}
	
	form.action="MatchCompetencies_DResDAct.jsp?org=" + org.value + "&comp=" + comp.value + "&org2="+selOrg.value + "&comp2=" + selComp.value + "&res=" + clickedValue;
	form.method="post";
	form.submit();
}

function closeWindow()
{
	window.close();
}

</script>

<body>
<%	

	int newPKOrg = 0;
	int newComp = 0;
	int PKOrg = 0;
	int comp = 0;
	int res = -1;
	
	if(request.getParameter("org") != null)
	{ 
		newPKOrg = new Integer(request.getParameter("org")).intValue();
		newComp = new Integer(request.getParameter("comp")).intValue();
	
		PKOrg = new Integer(request.getParameter("org2")).intValue();
		
		comp = new Integer(request.getParameter("comp2")).intValue();
		DRA.setFKCom(comp);
		DRARes.setFKComp(comp);
		
		res = new Integer(request.getParameter("res")).intValue();
	}

	int fkComp = DRA.getFKCom();
	int orgID = PKOrg;	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	
	String resName [] = new String [5];

	resName[0] = trans.tslt("All");		
	resName[1] = trans.tslt("Books");
	resName[2] = trans.tslt("Web Resources");
	resName[3] = trans.tslt("Training Courses");
	resName[4] = trans.tslt("AV Resources");
	
	//If user click on save button
	if(request.getParameter("save") != null)
	{
		String [] chkSelectDRA = null;
		String [] chkSelectDRARes = null;
		
		//Checkbox Development Activities
		if (request.getParameterValues("chkComp")!=null) chkSelectDRA = request.getParameterValues("chkComp");
		
		//Checkbox Development Resources
		if (request.getParameterValues("chkRes")!=null)	chkSelectDRARes = request.getParameterValues("chkRes");
		
		if(chkSelectDRA != null){
			int count = 0;
			for(String s: chkSelectDRA){
				votblDRA voDRA = (votblDRA) DRA.getDRA(Integer.parseInt(s));
				
				String DRAStatement = Database.SQLFixer(voDRA.getDRAStatement());	
				
				int exist = DRA.CheckDRAExist(newComp, DRAStatement, compID, newPKOrg);
				if(exist == 0) {
					try {
						String isSysGen = "1";
						if (userType != 1) isSysGen = "0";
						
						boolean save = DRA.addRecord(newComp, DRAStatement, isSysGen, compID, newPKOrg, pkUser, userType);
						//DRA.setFKCom(fkComp);
						if(save) {
							count++;
%>			

<%
						}
					} catch(SQLException SE) {
%>
<script>
						alert("SQLException <%=SE.toString()%>");
						window.close();
						opener.location.href = 'MatchCompetencies.jsp?org=' + <%=newPKOrg%> + '&comp=' + <%=newComp%> + '&org2=' + <%=PKOrg%> + '&comp2=' + <%=comp%>
</script>				
<%
					}
				} //End if else statement
			} //End loop, for each string in checkbox		

%>
<script>
			alert("<%=count%> Development Activities successfully added.");
			
</script>
<%
		} //End if array is not null
		
		if(chkSelectDRARes != null){
			int count = 0;
			for(String s: chkSelectDRARes){
				votblDRARES voDRARES = (votblDRARES)DRARes.getRecord(Integer.parseInt(s));
				String Resource = Database.SQLFixer(voDRARES.getResource());	
				int resType = voDRARES.getResType();
				
				int exist = DRARes.CheckDRAResExist(newComp, Resource, resType, compID, newPKOrg);
				if(exist == 0) {
					try {
					
						String isSysGen = "1";
						if (userType != 1) isSysGen = "0";

						boolean save = DRARes.addRecord(newComp, Resource, resType, isSysGen, compID, newPKOrg, pkUser, userType);
						
						if(save) {
							count++;			
						}
					} catch(SQLException SE) {
%>
<script>
						alert("SQLException <%=SE.toString()%>");
						window.close();
						opener.location.href = 'MatchCompetencies.jsp?org=' + <%=newPKOrg%> + '&comp=' + <%=newComp%> + '&org2=' + <%=PKOrg%> + '&comp2=' + <%=comp%>
</script>
<%
					}
				} //End if doesn't exists 
			} //End for loop
%>
<script>
			alert("<%=count%> Development Resources successfully added.");
			window.close();
			opener.location.href = 'MatchCompetencies.jsp?org=' + <%=newPKOrg%> + '&comp=' + <%=newComp%> + '&org2=' + <%=PKOrg%> + '&comp2=' + <%=comp%>
</script>
<%
		} //End if array not null
	
	} //End if user click save
	

	Vector DRAResult = DRAQuery.FilterRecord(fkComp, compID, orgID);
	Vector DRAResResult = DRAResQuery.FilterRecordByType(fkComp, res, compID, orgID);
	
%>
<form id="MatchCompetencies_DResDAct" name="MatchCompetencies_DResDAct" method="post" action="MatchCompetencies_DResDAct.jsp">
<input type="hidden" value="<%=newPKOrg%>" name="newPKOrg">
<input type="hidden" value="<%=newComp%>" name="newComp">
<input type="hidden" value="<%=PKOrg%>" name="PKOrg">
<input type="hidden" value="<%=comp%>" name="comp">
<table border="0">
<tr>
    <td><font style='font-family:Arial' size="2">Organisation: <b><u><%=Org.getOrganisationName(PKOrg)%></u></b>; Competency: <b><u><%=Comp.CompetencyName(comp)%></u></b></font></td>
</tr>
<tr>
	<td>
		<div style='width:900px; height:250px; z-index:1; overflow:auto;'>  
		<table id="tblDRA" name="tblDRA" border="1" bgcolor="#FFFFCC" bordercolor="#3399FF">
			<th bgcolor="navy" class="sorted-asc"><font style='font-family:Arial' size="2"><input type="checkbox" id="chkAllDRA" name="chkAllDRA"></font></th>
			<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u><%= trans.tslt("Development Activity") %></u></font></b></a></th>
			<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u><%= trans.tslt("Origin") %></u></font></b></a></th>
<% 	
	int DRAID = 0;
	String DRAName = "";
	String origin = "";
	for(int i=0; i<DRAResult.size(); i++){
		votblDRA voDRA = (votblDRA)DRAResult.elementAt(i);
		DRAID = voDRA.getDRAID();		
		DRAName =  voDRA.getDRAStatement();
		origin = voDRA.getDescription();
%>
   			<tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFcc'">
       			<td width="3%"><font face="Arial" style="font-size: 11.0pt; font-family: Arial">
	   				<input type="checkbox" id="chkComp" name="chkComp" value=<%=DRAID%>></font></td>
	   			<td width="87%"><font style='font-family:Arial' size="2"><% out.print(DRAName);%></font></td>
	   			<td width="10%"><font style='font-family:Arial' size="2"><% out.print(origin);%></font></td>
   			</tr>
<% 	
	} 
%>
		</table></div>
	</td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
	<td>
        <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td><font face = "Arial" size = "2"><%= trans.tslt("Resource Type") %></font></td>
            <td width="20">&nbsp;&nbsp;</td>
<%			
            for(int i=0; i<resName.length; i++) {
%>
            <td><font face = "Arial"; size = "2">
<% 				if(res != -1 && res == i) {		  	%>
                	<input name="rbtnRes" type="radio" value=<%=i%> checked onClick="changeResourceType(this.form, this.form.newPKOrg, this.form.newComp, this.form.PKOrg, this.form.comp, rbtnRes)">
<% 					res = -1;
            	} else {
%>	
                	<input name="rbtnRes" type="radio" value=<%=i%> onClick="changeResourceType(this.form, this.form.newPKOrg, this.form.newComp, this.form.PKOrg, this.form.comp, rbtnRes)">
<% 				}
%>			<%=resName[i]%></font></td>
            <td>&nbsp;&nbsp;</td>
<%			} 

			//get the Resource value again
			if(request.getParameter("res") != null) {	 
				res = new Integer(request.getParameter("res")).intValue();
			}	
%>
		</tr>
    	</table>
    </td>
</tr>
<tr>
	<td>
    	<div style='width:900px; height:250px; z-index:1; overflow:auto;'>  
		<table id="tblDRARes" name="tblDRARes" border="1" bgcolor="#FFFFCC" bordercolor="#3399FF">
			<th bgcolor="navy" class="sorted-asc"><font style='font-family:Arial' size="2"><input type="checkbox" id="chkAllDRARes" name="chkAllDRARes" value="1"></font></th>
			<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u><%=trans.tslt("Development Resource")%></u></font></b></a></th>
<%			if(res == 0) {		%>
				<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u><%= trans.tslt("Resource Type") %></u></font></b></a></th>	
<%			}					%>
			<th bgcolor="navy" class="sorted-asc"><a href="#"><b><font style='font-family:Arial;color:white' size="2"><u><%=trans.tslt("Origin")%></u></font></b></a></th>
<% 	
	int ResID = 0;
	int resType = 0;
	String DRAResName = "";
	for(int i=0; i<DRAResResult.size(); i++){
		votblDRARES voDRARES = (votblDRARES)DRAResResult.elementAt(i);
		ResID = voDRARES.getResID();		
		DRAResName =  voDRARES.getResource();
		origin =  voDRARES.getDescription();
		resType = voDRARES.getResType();
%>
   			<tr onMouseOver = "this.bgColor = '#99ccff'" onMouseOut = "this.bgColor = '#FFFFcc'">
       			<td width="3%"><font face="Arial" style="font-size: 11.0pt; font-family: Arial">
	   				<input type="checkbox" id="chkRes" name="chkRes" value=<%=ResID%>></font></td>
	   			<td width="80%"><font style='font-family:Arial' size="2"><% out.print(DRAResName);%></font></td>
<%				if(res == 0) {
					if(resType == 1) {				%>
						<td width="7%"><font style='font-family:Arial' size="2"><%=resName[1]%></font></td>
<%					} else if (resType == 2) {		%>
						<td width="7%"><font style='font-family:Arial' size="2"><%=resName[2]%></font></td>
<%					} else if (resType == 3) {		%>
						<td width="7%"><font style='font-family:Arial' size="2"><%=resName[3]%></font></td>
<%					} else if (resType == 4) {		%>
						<td width="7%"><font style='font-family:Arial' size="2"><%=resName[4]%></font></td>
<%					}
				}								%>
	   			<td width="10%"><font style='font-family:Arial' size="2"><% out.print(origin);%></font></td>
   			</tr>
<% 
	} 
%>
		</table></div>
	</td>
</tr>
</table>
<p></p>
<table border="0" width="55%" cellspacing="0" cellpadding="0">
<tr>
   	<td width="210"><input type="button" name="Save" value="<%=trans.tslt("Save")%>" onClick="confirmSave(this.form, this.form.chkComp, this.form.chkRes, this.form.newPKOrg, this.form.newComp, this.form.PKOrg, this.form.comp, this.form.rbtnRes)" ></td>
	<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onClick="closeWindow()"></td>
</tr>
</table>

&nbsp;&nbsp;&nbsp;
<%
	
	if(request.getParameter("org") != null){ 
	//	logchk.setOrg(newPKOrg);
		DRA.setFKCom(0);
		DRARes.setFKComp(0);
		DRARes.setResType(0);
	}
	
%>
</form>

</body>
</html>