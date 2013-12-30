<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "CP_Classes.vo.*" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html">

<jsp:useBean id="CompetencyQuery" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="Comp" class="CP_Classes.Competency" scope="session"/>
<jsp:useBean id="KBQuery" class="CP_Classes.KeyBehaviour" scope="session"/>
<jsp:useBean id="DRAQuery" class="CP_Classes.DevelopmentActivities" scope="session"/>
<jsp:useBean id="DRAResQuery" class="CP_Classes.DevelopmentResources" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>     
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="Cluster" class="CP_Classes.Cluster" scope="session"/>
<jsp:useBean id="OrgCluster" class="CP_Classes.OrgCluster" scope="session"/>
<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<%@ page import="CP_Classes.vo.votblOrganization"%>


<title>Cluster</title>

</head>

<body>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin

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

//Edited by Xuehai 02 Jun 2011. Remove 'void'. Enable to run on different browers like Chrome&Firefox.
//void function confirmAdd(form) {
function confirmAdd(form) {
	myWindow=window.open('Cluster_AddCompetency.jsp','windowRef','scrollbars=no, width=650, height=600');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'Cluster_AddCompetency.jsp';
}


function confirmDelete(form, field) {

	

			form.action = "Cluster.jsp?delete="+ 1;
			form.method = "post";
			form.submit();
		
	
}

/*------------------------------------------------------------start: LOgin modification 1------------------------------------------*/
/*	choosing organization*/

function proceed(form,field)
{
	form.action="Cluster.jsp?proceed="+field.value;
	form.method="post";
	form.submit();
}	

function populateCompetency(form, field)
{
	form.action="Cluster.jsp?populateCompetency="+field.value;
	form.method="post";
	form.submit();
	
}
	
function addCluster(form){
	var myWindow=window.open('AddCluster.jsp','windowRef','scrollbars=no, width=480, height=250');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'AddCluster.jsp';
}
	


function editCluster(form, field){
	var myWindow=window.open('EditCluster.jsp','windowRef','scrollbars=no, width=480, height=250');
	myWindow.moveTo(x,y);
    myWindow.location.href = 'EditCluster.jsp';
}

function deleteCluster(form, field){
	
	if(confirm("<%=trans.tslt("Delete Cluster?")%>")){
	form.action="Cluster.jsp?deleteCluster="+field.value;
	form.method="post";
	form.submit();
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
if(request.getParameter("proceed") != null)
{ 
	int PKOrg = new Integer(request.getParameter("proceed")).intValue();
 	logchk.setOrg(PKOrg);
}

if(request.getParameter("populateCompetency") != null){
	int PKCluster = new Integer(request.getParameter("populateCompetency")).intValue();
	logchk.setClusterID(PKCluster);
}

if(request.getParameter("deleteCluster")!= null){
	int PKCluster = new Integer(request.getParameter("deleteCluster")).intValue();
	logchk.setClusterID(0);
	 Boolean delete = Cluster.deleteCluster(PKCluster, logchk.getPKUser());
	 if(delete){
		 %><script>
		 alert("Cluster deleted successfully.")
		 </script><% 
	 }
	 else{
		 %><script>
		 alert("An error occured while trying to delete the cluster.")
		 </script><% 
	 }
	
}

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

%>
<form name="CompetencyList" method="post">
<table border="0" width="58%" cellspacing="0" cellpadding="0">
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial"><%= trans.tslt("Cluster") %></font></b></td>
    </tr>
	<tr>
	  <td colspan="3"><ul>
	    <li><font face="Arial" size="2"><%= trans.tslt("To Add, click on the Add button")%>.</font></li>
	    <li><font face="Arial" size="2">
	    <%= trans.tslt("To Edit, click on the relevant radio button and click on the Edit button")%>.</font></li>
	    <li><font face="Arial" size="2">
	    <%= trans.tslt("To Delete, click on the relevant radio button and click on the Delete button")%>.</font></li>
      </ul></td>
    </tr>
	<tr>
		<td width="96"><font face="Arial" size="2"><%= trans.tslt("Organisation") %>:</font></td>
		<td width="246"><select size="1" name="selOrg" onchange="proceed(this.form,this.form.selOrg)">
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
		<td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td width="96">&nbsp;</td>
		<td width="246">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
	<td width="96"><font face="Arial" size="2"><%= trans.tslt("Cluster") %>:</font></td>
		<td width="246"><select size="1" name="selCluster" onchange="populateCompetency(this.form,this.form.selCluster)">
		
		<%
		Vector v = new Vector();
		v = Cluster.getOrganizationCluster(logchk.getOrg());
		
		for(int i=0; i<v.size(); i++)
		{
			voCluster vo = (voCluster)v.elementAt(i);
			int PKCluster = vo.getPKCluster();
			if((logchk.getClusterID() == 0) && i == 0){
				logchk.setClusterID(PKCluster);
			
				
			}
			String ClusterName = vo.getClusterName();

			if(logchk.getClusterID() == PKCluster)
			{ %>
				<option value=<%=PKCluster%> selected><%=ClusterName%></option>
			<% } else { %>
				<option value=<%=PKCluster%>><%=ClusterName%></option>
			<%	}	
		} 
%>
		</select>
		</td>
		<td align="center">&nbsp;</td>
	</tr>
		<tr>
		<td width="96">&nbsp;</td>
		<td width="246">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<p></p>
<input type="button" name="AddCluster" value="<%= trans.tslt("Add Cluster") %>" onclick="addCluster(this.form)">
<input type="button" name="EditCluster" value="<%= trans.tslt("Edit Cluster") %>"  onclick = "editCluster(this.form, this.form.selCluster)">
<input type="button" name="DeleteCluster" value="<%= trans.tslt("Delete Cluster") %>"  onclick = "deleteCluster(this.form, this.form.selCluster)">
<p></p>
<p></p>
<%
	int DisplayNo;
	String pkCompetency; 
	String name, definition, origin;
	int userType = logchk.getUserType();
	
	DisplayNo = 1;

	Vector aResult = null;	
	int OrgID = logchk.getOrg();	
	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/

	int toggle = CompetencyQuery.getToggle();	//0=asc, 1=desc
	int type = 1; //1=name, 2=origin		
			
	if(request.getParameter("name") != null)
	{	 
		if(toggle == 0)
			toggle = 1;
		else
			toggle = 0;
		
		CompetencyQuery.setToggle(toggle);
		
		type = Integer.parseInt(request.getParameter("name"));			 
		CompetencyQuery.setSortType(type);									
	} 
	
/************************************************** ADDING TOGGLE FOR SORTING PURPOSE *************************************************/
	
	//CompetencyQuery.setToggle(toggle);
	
%>



<% 
if(request.getParameter("delete") != null)
	{
			
			
	 	    String [] chkSelect = request.getParameterValues("checkdel");
	 	    boolean bCompetencydeleted = true;
	 	    
	 	 
	 	    
				if(chkSelect != null)
		    	{ 
					
		    		try
					{
			    		for(int i=0; i<chkSelect.length; i++)
						{
							
								int iOrgClusterID = 0;
								if(chkSelect[i] != null){
									iOrgClusterID = Integer.parseInt(chkSelect[i]);
								}
								
						bCompetencydeleted = OrgCluster.deleteOrgCluster(iOrgClusterID,logchk.getPKUser());
									
	
						}
					}
					catch(SQLException sqle)
					{	System.out.println(sqle);
						bCompetencydeleted = false;
					}	
					
					if (bCompetencydeleted) {
						%>
							<script>
                                                           
					 			alert("Competency is successfully deleted from the cluster");
					 		</script>
						<%
					} else {
						%>
					 		<script>
					 			alert("An error occured when trying to delete competencies");
					 		</script>
						<%
					}
				}
				else{
			 	    	%>
			 	    	<script>
			 	    	alert("No competency selected");
			 	    	</script>
			 	    	
			 	    	
			 	    	<% 
			 	    }
	%>					
	<script>window.close()
	 		opener.location.href = 'Cluster.jsp';</script>
	<%
		
	}
%>

<div style='width:610px; height:259px; z-index:1; overflow:auto'>  

<table width="593" border="1" style='font-size:10.0pt;font-family:Arial' bordercolor="#3399FF" bgcolor="#FFFFCC">
<th bgcolor="navy" bordercolor="#3399FF">&nbsp;</th>
<th width="10" bgcolor="navy" bordercolor="#3399FF"><b>
<font style='color:white'>No</font></b></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="Cluster.jsp?name=1"><b>
<font style='color:white'><u><%= trans.tslt("Name") %></u></font></b></a></th>
<th width="380" bgcolor="navy" bordercolor="#3399FF">
<a href="Cluster.jsp?name=2"><b><font style='font-family:Arial;color:white'>
<u><%= trans.tslt("Definition") %></u></font></b></a></th>
<th width="100" bgcolor="navy" bordercolor="#3399FF"><a href="Cluster.jsp?name=3"><b>
<font style='font-family:Arial;color:white'><u><%= trans.tslt("Origin") %></u></font></b></a></th>

<% 
	/********************
	* Edited by James 30 Oct 2007
	************************/
	aResult = OrgCluster.getOrgCluster(logchk.getClusterID());
	for(int i=0; i<aResult.size(); i++) {
        votblOrgCluster voOrg = (votblOrgCluster)aResult.elementAt(i);
		voCompetency voC = CompetencyQuery.getCompetency(voOrg.getFKCompetency());
		int PKOrgCluster = voOrg.getPKOrgCluster();
		pkCompetency = ""+voC.getPKCompetency();
		name =  voC.getCompetencyName();
		definition = voC.getCompetencyDefinition();
		origin = voC.getDescription();
%>
   <tr onMouseOver = "this.bgColor = '#99ccff'"
   		onMouseOut = "this.bgColor = '#FFFFCC'">
       <td style="border-style: solid; border-width: 1px" bordercolor="#3399FF">
	   		<font size="2">
   
	   		<input type="checkbox" name="checkdel" value=<%=PKOrgCluster%>></font>	   </td>
	   	<td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF">
   		  <% out.print(DisplayNo); %>
   		</td>
	   <td style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><% out.print(name);%></td>
	   <td style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><% out.print(definition);%></td>
	   <td align="center" style="border-style: solid; border-width: 1px" bordercolor="#3399FF"><% out.print(origin);%></td>
   </tr>
<% 	DisplayNo++;
	} 

%>
</table>
</div>

<p></p>
<input type="button" name="Add" value="<%= trans.tslt("Add Competency") %>" onclick="confirmAdd(this.form)">
<input type="button" name="Delete" value="<%= trans.tslt("Delete") %>"  onclick = "return confirmDelete(this.form, this.form.checkComp)">
</form>
<% } %>

<p></p>
<%@ include file="Footer.jsp"%>

</body>
</html>