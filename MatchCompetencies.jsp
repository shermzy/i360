<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*,java.util.*,CP_Classes.vo.*," %>
<%@ page import="CP_Classes.vo.votblOrganization"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Match Competencies</title>

<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>


</head>

<body>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="DRAResQuery" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="DRARes" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>   
<jsp:useBean id="setting" class="CP_Classes.Setting" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<% 	// added to check whether organisation is a consulting company%>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>

<SCRIPT LANGUAGE="JavaScript">
var x = parseInt(window.screen.width) / 2 - 500;  // the number 250 is the exact half of the width of the pop-up and so should be changed according to the size of the pop-up
var y = parseInt(window.screen.height) / 2 - 300;  // the number 125 is the exact half of the height of the pop-up and so should be changed according to the size of the pop-up

function openWindow(form, org, comp, selOrg, selComp){
	//alert(org.value + ", " + comp.value + ", " + selOrg.value + ", " + selComp.value);
	var width = 950;
	var height = 680;
	var left = parseInt((screen.availWidth/2) - (width/2));
   	var top = parseInt((screen.availHeight/2) - (height/2)-100);
   
	var str = "MatchCompetencies_DResDAct.jsp?org=" + org.value + "&comp=" + comp.value + "&org2="+selOrg.value + "&comp2=" + selComp.value + "&res=0";
	var myWindow=window.open(str,'windowRef','scrollbars=yes, width=' + width + ',height=' + height + ',left=' + left + ',top=' + top + 'screenX=' + left + ',screenY=' + top);
	//myWindow.moveTo(x,y);
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/
// Added function to refresh the page by Ha 20/5/08
function proceed(form, org, comp, selOrg, selComp){
	//alert(org.value + ", " + comp.value + ", " + selOrg.value + ", " + selComp.value);
	form.action="MatchCompetencies.jsp?org="+org.value + "&comp=" + comp.value + "&org2="+selOrg.value + "&comp2=" + selComp.value
	form.method="post";
	form.submit();
}


	
			
</script>
  

<%	
	
	String username=(String)session.getAttribute("username");

  	if (!logchk.isUsable(username)) {
%> 		
		<font size="2">   
		<script>
            parent.location.href = "index.jsp";
        </script>
<%  } else { 	
		
		int PKOrg = 0;
		int comp = 0;
		int selOrg = 0;
		int selComp = 0;
		
		if(request.getParameter("org") != null){ 
			PKOrg = new Integer(request.getParameter("org")).intValue();
			logchk.setOrg(PKOrg);
			comp = new Integer(request.getParameter("comp")).intValue();
		}
%>

<form name="MComp" method="post" action="MatchCompetencies.jsp">
<table border="0" width="579" cellspacing="0" cellpadding="0" font span style='font-size:10.0pt;font-family:Arial;'>
	<tr>
		<td colspan="4"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Match Competencies") %> </font></b></td>
	</tr>
	<tr>
	  <td colspan="4">
	  <ul>
		<li><font face="Arial" size="2"><%= trans.tslt("Select the new competency that you would like to match") %>.</font></li>
		<li><font face="Arial" size="2"><%= trans.tslt("Select the existing competency that you would like to match to") %>.</font></li>
		<li><font face="Arial" size="2"><%= trans.tslt("Click Match") %>.</font></li>
	  </ul></td>
	</tr>
	<tr>
		<td width="94"><%= trans.tslt("Organisation") %></td>
		<td width="14">&nbsp;</td>
		<td width="330"><select size="1" name="selOrg1" onChange="proceed(this.form, this.form.selOrg1, this.form.Competency1, this.form.selOrg2, this.form.Competency2)">
		<%
		int fkComp, res;
        String DRAResName;
        DRAResName = "";
        fkComp = DRARes.getFKComp();
        res = DRARes.getResType();
        		
        int orgID = logchk.getOrg();	
        int compID = logchk.getCompany();
		
		Vector CompResult = Comp.FilterRecordWithoutSystemGenerated(compID, orgID);
		//System.out.println("compID = " + compID + ", orgID = " + orgID + ", ");
		
		//Re-generate the compResult using another org value		
		if(request.getParameter("org") != null){ 
			PKOrg = new Integer(request.getParameter("org")).intValue();
			logchk.setOrg(PKOrg);
			comp = new Integer(request.getParameter("comp")).intValue();
			
			orgID = logchk.getOrg();	
	        compID = logchk.getCompany();
	        CompResult = Comp.FilterRecordWithoutSystemGenerated(compID, orgID);
		}
		
        // Added to check whether organisation is also a consulting company
        // if yes, will display a dropdown list of organisation managed by this company
        // else, it will display the current organisation only
        String [] UserDetail = new String[14];
        UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
        boolean isConsulting = true;
        isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
        if (isConsulting){
            Vector vOrg = logchk.getOrgList(logchk.getCompany());
        
            for(int i=0; i<vOrg.size(); i++) {
                votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
                PKOrg = vo.getPKOrganization();
                String OrgName = vo.getOrganizationName();
        
                if(logchk.getOrg() == PKOrg) { 
		%>
                    <option value=<%=PKOrg%> selected><%=OrgName%></option>
        <% 		} else { %>
                    <option value=<%=PKOrg%>><%=OrgName%></option>
        <%		}	
            } 
        } else { 
		%>
        	<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
        <% 
		} // End of isConsulting 
		%>
        </select></td>
		<td width="141">&nbsp;</td>
	</tr>
    <tr>
		<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
      	<td>&nbsp;</td>
	</tr>
	<%
        
            
    %>
	<tr>
	  <td><%= trans.tslt("New Competency") %></td>
	  <td>&nbsp;</td>
	  <td><select name="Competency1">
		<%
			String CompName = "";
	        int pkComp = 0;
			for(int i=0; i<CompResult.size(); i++) {
				voCompetency voC = (voCompetency)CompResult.elementAt(i);
				pkComp = 	voC.getPKCompetency();
				CompName = 	voC.getCompetencyName();
				
				if(pkComp == comp) {
		%>
					<option value = <%=pkComp%> selected><%=CompName%>
		<%		
					comp = 0;
				}else {
		%>
					<option value = <%=pkComp%>><%=CompName%>
		<%
				}
			}
		%>
		  </select></td>
	  <td align="left"></td>
	</tr>	
	
    <tr>
		<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
      	<td>&nbsp;</td>
	</tr>
    
    
     <tr>
		<td width="94" align="center" style="border-left-style: solid; border-left-width: 1px; border-top-style: solid; border-top-width: 1px; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="49"><%= trans.tslt("Organisation") %></td>
		<td width="14" align="center" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:none; border-right-width:medium; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="49">&nbsp;</td>
	  <td width="330" align="center" style="border-top-style: solid; border-top-width: 1px; border-left-style:none; border-left-width:medium; border-right-style:solid; border-right-width:1px; border-bottom-style:none; border-bottom-width:medium" bordercolor="#3399FF" bgcolor="#FFFFCC" height="49"><p align="left"> <font face="Arial" size="2">
      <select size="1" name="selOrg2" onChange="proceed(this.form, this.form.selOrg1, this.form.Competency1, this.form.selOrg2, this.form.Competency2)">
		<%
		//Re-generate the compResult using another org value		
		if(request.getParameter("org2") != null){ 
			PKOrg = new Integer(request.getParameter("org2")).intValue();
			logchk.setOrg(PKOrg);
			comp = new Integer(request.getParameter("comp2")).intValue();
			
			orgID = logchk.getOrg();	
	        compID = logchk.getCompany();
	        CompResult = Comp.FilterRecordWithoutSystemGenerated(compID, orgID);
		}
		
        // Added to check whether organisation is also a consulting company
        // if yes, will display a dropdown list of organisation managed by this company
        // else, it will display the current organisation only
        UserDetail = new String[14];
        UserDetail = CE_Survey.getUserDetail(logchk.getPKUser());
        isConsulting = true;
        isConsulting = Org.isConsulting(UserDetail[10]); // check whether organisation is a consulting company 
        if (isConsulting){
            Vector vOrg = logchk.getOrgList(logchk.getCompany());
        
            for(int i=0; i<vOrg.size(); i++) {
                votblOrganization vo = (votblOrganization)vOrg.elementAt(i);
                PKOrg = vo.getPKOrganization();
                String OrgName = vo.getOrganizationName();
        
                if(logchk.getOrg() == PKOrg) { 
		%>
                    <option value=<%=PKOrg%> selected><%=OrgName%></option>
        <% 		} else { %>
                    <option value=<%=PKOrg%>><%=OrgName%></option>
        <%		}	
            } 
        } else { 
		%>
        	<option value=<%=logchk.getSelfOrg()%>><%=UserDetail[10]%></option>
        <% 
		} // End of isConsulting 
		%>
        </select></td>
		<td width="141" >&nbsp;</td>
	</tr>
    
    <tr>
		<td align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
	  	<td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
	  	<td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      	<td>&nbsp;</td>
	</tr>
    
    <tr>
	  <td align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><%= trans.tslt("Competency") %></td>
	  <td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
	  <td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF"><p align="left"> <font face="Arial" size="2">
      <select name="Competency2">
		<%
			for(int i=0; i<CompResult.size(); i++) {
				voCompetency voC = (voCompetency)CompResult.elementAt(i);
				pkComp = 	voC.getPKCompetency();
				CompName = 	voC.getCompetencyName();
				
				if(pkComp == comp) {
		%>
					<option value = <%=pkComp%> selected><%=CompName%>
		<%		
					comp = 0;
				}else {
		%>
					<option value = <%=pkComp%>><%=CompName%>
		<%
				}
			}
		%>
		  </select></td>
	  <td align="left"></td>
	</tr>	
    
    <tr>
		<td align="center" style="border-left-style: solid; border-left-width: 1px; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
	  	<td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: none; border-right-width: medium; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
	  	<td align="center" style="border-left-style: none; border-left-width: medium; border-right-style: solid; border-right-width: 1px; border-top-style: none; border-top-width: medium; border-bottom-style: solid; border-bottom-width: 1px" bordercolor="#3399FF" bgcolor="#FFFFCC" bordercolorlight="#3399FF" bordercolordark="#3399FF">&nbsp;</td>
      	<td >&nbsp;</td>
	</tr>	
    
    
   
 </table> 
<%
	//Reset back to the original org
	if(request.getParameter("org") != null){ 
		PKOrg = new Integer(request.getParameter("org")).intValue();
		logchk.setOrg(PKOrg);
	}
}
%>

<p></p>
<input type="button" name="Save" value="<%= trans.tslt("Match") %>" onClick="openWindow(this.form, this.form.selOrg1, this.form.Competency1, this.form.selOrg2, this.form.Competency2)">
</form>

<p></p>
<%@ include file="Footer.jsp"%>
</body>
</html>