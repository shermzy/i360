<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Job Category</title>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>

<link REL="stylesheet" TYPE="text/css" href="Settings\Settings.css">

<style type="text/css">
<!--
body {
	background-color: #eaebf4;
}
-->
</style></head>

<body class="pagecolor">
<jsp:useBean id="JobCat" class="CP_Classes.JobCategory" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>    
<jsp:useBean id="JobCatDetail" class="CP_Classes.JobCategoryDetail" scope="session"/>
<script language = "javascript">

function confirmSave(form, saveType)
{
	if(form.job.value != "") {
		if(confirm("Add/Edit Job Category?")) {
			form.action = "JobCatUpdate.jsp?update=" + saveType;
			form.method = "post";
			form.submit();
			return true;
		}else
			return false;
	} else {
		if(form.job.value == "") {
			alert("Please enter Job Category Name");		
			form.job.focus();
		}
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
String username=(String)session.getAttribute("username");
  if (!logchk.isUsable(username)) 
  {
%> <font size="2">
   
<script>
	parent.location.href = "index.jsp";
</script>
<%  } 
  else 
  { 

/*-------------------------------------------------------------------end login modification 1--------------------------------------*/

%>


<%
	int orgID = logchk.getOrg();
  	int compID = logchk.getCompany();
	int pkUser = logchk.getPKUser();
	int userType = logchk.getUserType();	// 1= super admin
	int CatID = 0; 
	String name = "";	
	int compExist = 0;
	
		
	if(request.getParameter("clicked") != null) {
		CatID = Integer.parseInt(request.getParameter("clicked"));
			
		int check = JobCat.CheckSysLibJobCategory(CatID);
		if(userType != 1 && check == 1) {
%>
			<script>
				alert("The edited System Generated Library will be saved as a new User Generated Library");
			</script>
<%
		}
		
	} else if(request.getParameter("hidJobCat") != null)
		CatID = Integer.parseInt(request.getParameter("hidJobCat"));

	if(request.getParameter("update") != null) {
		if(request.getParameter("job") != null)	{
  			name = request.getParameter("job");
  			int exist = JobCat.CheckJobCategoryExist(name, orgID);
  			
  			int saveType = Integer.parseInt(request.getParameter("update"));

			if(exist == 0 && (CatID == 0 || saveType == 2)) {//add
                            int prevCatID=0;
                                if(CatID!=0)
                                    prevCatID=CatID;
                                
				JobCat.addRecord(name, orgID, pkUser, userType);		
								
				CatID = JobCat.CheckJobCategoryExist(name, orgID);
				JobCat.setIsAdd(1);
				//System.out.println("aa " + CatID + "--" + compID + "--" + orgID);
	
				if(CatID != 0) {
					//System.out.println("aa " + CatID);
					JobCat.setJobCatID(CatID);
                                        
                                        //Competancy Copy
					/********************
					* Add by Thant Thura 08 Jan 2008
					************************/
					Vector veComp = JobCat.getCompetency(prevCatID);
					for(int i=0; i<veComp.size(); i++) {
                                            Integer iFKC = (Integer)veComp.elementAt(i);
                                            int iFKComp=iFKC.intValue();
						try {
							boolean copy = JobCatDetail.addCompetency(CatID, iFKComp);
                                       		}catch(SQLException SE) {}
					}
	%>
		<script>	
			alert("Now, you are required to select competencies for the Job Category that you have just created.");
			window.close();
			opener.location.href = "AddJobCatCompetency.jsp?addCat=" + <%=CatID%>;
		</script>
	<%									
				}		
				
			} 
			  //Added by Ha 11/06/08 to prompt message edit successfully if
			  //user does not change the old record and click save
			 else if((exist == 0 || exist==CatID)&& saveType == 1) 
				{		//edit
					boolean save = JobCat.editRecord(name, CatID, pkUser);
				
					if(save) 
					{
						%>
						 <script>
							alert("Edited successfully");
							window.close();
							opener.location.href = "JobCategory.jsp";
						</script>
						<%				
					}
				}
			  else
			  { 			
			  	%>
					<script>	
						alert("Record exists");
					</script>
				<%		
			  }

		}
	}


	if(CatID != 0) {
		name = JobCat.JobCategoryName(CatID);
	}
%>			


<form name="AddJobCat" method="post">
<table border="0" width="400" cellspacing="0" cellpadding="0">
	

<%
	if(CatID == 0) {
%>
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial">Add Job 
		Category</font></b></td>
    </tr>
	<tr>
	  <td colspan="3"><ul>
	    <li><font face="Arial" size="2">To Add, fill in the Job Category Name 
		and click on the Save button.</font></li>
	    </ul></td>
    </tr>
    <table border="0" width="450" height="85" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="400" height="30">Job Category Name : </td>
    </tr>
	    <tr>
      <td width="400" height="30"><input name="job" type="text"  style='font-size:10.0pt;font-family:Arial' id="job" size="50" maxlength="50" value="<%=name%>"><input name="hidJobCat" type="hidden" value="<%=CatID%>">	   </td>  
    </tr>
  </table>
  <p></p> 

  <table width="450">
  <tr>
<%  
	int check = JobCat.CheckSysLibJobCategory(CatID);

    if(userType != 1 && check == 1) {
%>
    <td width="100" align="right"><input name="btnSave" type="button" value="Save" onClick="return confirmSave(this.form, 1)" disabled></td>
<%
	} else {
%>	

	 <td width="100" align="right"><input name="btnSave" type="button" value="Save" onClick="return confirmSave(this.form, 1)"></td>

<%
	}	
%>  
 
    <td width="160"><input name="btnCancel" type="button" value="Cancel" onClick="window.close()"></td>
  </tr>
</table>
<%
	} else {
%>
	<tr>
	  <td colspan="3"><b><font color="#000080" size="2" face="Arial">Edit Job 
		Category</font></b></td>
    </tr>
	<tr>
	  <td colspan="3"><ul>
	    <li><font face="Arial" size="2">To edit, modify the Job Category Name 
		and click on the Save/ Save as New button.</font></li>
	    </ul></td>
    </tr>
    <table border="0" width="450" height="85" font span style='font-size:10.0pt;font-family:Arial'>
    <tr>
      <td width="400" height="30">Job Category Name : </td>
    </tr>
	    <tr>
      <td width="400" height="30"><input name="job" type="text"  style='font-size:10.0pt;font-family:Arial' id="job" size="50" maxlength="50" value="<%=name%>"><input name="hidJobCat" type="hidden" value="<%=CatID%>">	   </td>  
    </tr>
  </table>
  <p></p> 

  <table width="450">
  <tr>
<%  
	int check = JobCat.CheckSysLibJobCategory(CatID);

    if(userType != 1 && check == 1) {
%>
    <td width="100" align="right"><input name="btnSave" type="button" value="Save" onClick="return confirmSave(this.form, 1)" disabled></td>
<%
	} else {
%>	

	 <td width="100" align="right"><input name="btnSave" type="button" value="Save" onClick="return confirmSave(this.form, 1)"></td>

<%
	}	
%>  
  <td width="90" align="center"><input name="btnSaveAs" type="button" value="Save As New" onClick="return confirmSave(this.form, 2)"></td>
    <td width="160"><input name="btnCancel" type="button" value="Cancel" onClick="window.close()"></td>
  </tr>
</table>
<%	
	}
%>
</table>



</form>
<% } %>
</body>
</html>