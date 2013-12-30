<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,CP_Classes.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.zip.*"%>

<%@ page import="CP_Classes.vo.*"%>
<%@ page import="CP_Classes.Calculation"%>
<%@ page import="CP_Classes.SurveyResult"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- CSS -->

<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="lib/css/bootstrap-responsive.css">
<link type="text/css" rel="stylesheet" href="lib/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet"
	href="lib/css/bootstrap-responsive.min.css">


<!-- jQuery -->
<script type="text/javascript" src="lib/js/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/js/bootstrap.js"></script>
<script type="text/javascript" src="lib/js/jquery-1.9.1.js"></script>


<script src="lib/js/bootstrap.min.js" type="text/javascript"></script>
<jsp:useBean id="Database" class="CP_Classes.Database" scope="session" />
<jsp:useBean id="QR" class="CP_Classes.QuestionnaireReport"
	scope="session" />
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session" />
<jsp:useBean id="User_Jenty" class="CP_Classes.User_Jenty"
	scope="session" />
<jsp:useBean id="ExcelGroup" class="CP_Classes.GroupReport"
	scope="session" />

<jsp:useBean id="Setting" class="CP_Classes.Setting" scope="session" />
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session" />

<jsp:useBean id="Org" class="CP_Classes.Organization" scope="session" />
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey"
	scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.alert{
text-shadow:0 0 0 ;
}</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('.alert').alert();
		$('.info').tooltip();
		$('#genFilesSuc').hide();
		$('#delFilesSuc').hide();
		$('#delSingleFile').hide();
		var t = document.getElementsByClassName("Trows");
		if(t.length==0){
		
			$('#delFilesSuc').fadeIn();
		}else if(<%=request.getParameter("delFile")!=null%>){
			$('#delSingleFile').fadeIn();
		}else{
			
			$('#genFilesSuc').fadeIn();
		}
	});

	function formSubmit(form,id) {
		query = "GenerationComplete.jsp?";
		
		
		query+= ("theOne=" + id);
		form.action=query;
		form.submit();
		
	}
	
	function removeAll(){
		
		
		var t = document.getElementsByClassName("Trows");
		
		if(t.length>0){
			var s = confirm("Are you sure you want to delete all entries?");
			
			if(s==true){
				document.generateReport.action = "GenerationComplete.jsp?delete=1";
				document.generateReport.submit();
			}
		} else {
			alert("All files have been removed.");
		}
	}
	
	function removeClass(fileName){
		alert(fileName);
		document.generateReport.action="GenerationComplete.jsp?delFile="+fileName;
		document.generateReport.submit();
	}
	
	function downloadAll(){
		document.generateReport.action="GenerationComplete.jsp?dlall=1";
		document.generateReport.submit();
	}
</script>
<title>Report Generation Page</title>
</head>
<body>
	<%	
// 		if(request.getParameter("dlall")!=null){
// 			Vector all =new Vector();
// 			if(session.getAttribute("IndividualReport")!=null){
// 				Vector v = (Vector)session.getAttribute("IndividualReport");
// 				for(int i=0;i<v.size();i++){
// 					String s =(String) v.elementAt(i);
// 					all.add(s);
// 				}
// 				session.setAttribute("IndividualReport",v);
// 			}
// 			if(session.getAttribute("chosenFiles")!=null){
// 				Vector v = (Vector)session.getAttribute("chosenFiles");
// 				for(int i=0;i<v.size();i++){
// 					String s =(String) v.elementAt(i);
// 					all.add(s);
// 				}
// 			}
				
// 			Date timeStamp = new java.util.Date();
// 			SimpleDateFormat dFormat =  new SimpleDateFormat("dd-MM-yy[HH.mm.ss]");
// 			String name=Setting.getReport_Path() +dFormat.format(timeStamp);
// 			  ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(name));
// 			  System.out.println("zipname: "+ name);
// 			 for(int a=0;a<all.size();a++){	
// 			  	String s= (String) all.elementAt(a);
// 			  	String output = Setting.getReport_Path() + "\\" + s;
				
// 				File f = new File(output);
// 				 FileInputStream in = new FileInputStream(f);

// 		        // out put file 
		      

// 		        // name the file inside the zip  file 
// 		        zipOut.putNextEntry(new ZipEntry(s)); 
			 
// 		        // buffer size
// 		        byte[] b = new byte[1024];
// 		        int count;

// 		        while ((count = in.read(b)) > 0) {
// 		            System.out.println();
// 		            zipOut.write(b, 0, count);
// 		        }
// 		        zipOut.close();
// 		        in.close();
// 			 }
// 		}
		
		if(request.getParameter("delFile")!=null){
			String fileName = (String) request.getParameter("delFile");
			if(session.getAttribute("IndividualReport")!=null){
				Vector v = (Vector)session.getAttribute("IndividualReport");
				for(int i=0;i<v.size();i++){
					String s =(String) v.elementAt(i);
					if(fileName.equalsIgnoreCase(s)){
						v.remove(i);
					}
				}
				session.setAttribute("IndividualReport",v);
			}
		if(session.getAttribute("chosenFiles")!=null){
			Vector v = (Vector)session.getAttribute("chosenFiles");
			for(int i=0;i<v.size();i++){
				String s =(String) v.elementAt(i);
				if(fileName.equalsIgnoreCase(s)){
					v.remove(i);
				}
			}
			session.setAttribute("chosenFiles",v);
			}
		}
		if(request.getParameter("delete")!=null){
			session.removeAttribute("IndividualReport");
			session.removeAttribute("chosenFiles");
		}
		if (request.getParameter("theOne") != null) {
			
			String fileName = (String) request.getParameter("theOne");
			System.out.println("Extraction from generation page: "
					+ fileName);
			// String fileName = (String) request.getParameter("Success");
			String output = Setting.getReport_Path() + "\\" + fileName;
		
			File f = new File(output);
			//set the content type(can be excel/word/powerpoint etc..)
			response.reset();
			System.out.println("setcontentype");
			response.setContentType("application/xls");
			//set the header and also the Name by which user will be prompted to save
			System.out.println("addheader");
			response.addHeader("Content-Disposition",
					"attachment;filename=\"" + fileName + "\"");

			//get the file name
			String name = f.getName().substring(
					f.getName().lastIndexOf("/") + 1,f.getName().length());
			//OPen an input stream to the file and post the file contents thru the 
			//servlet output stream to the client m/c
			System.out.println("inputstream");
			InputStream in = new FileInputStream(f);
			System.out.println("outputstream");
			ServletOutputStream outs = response.getOutputStream();

			int bit = 256;
			int i = 0;

			try {
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
			} catch (IOException ioe) {
				System.out.println("Buffering problem:");
				ioe.printStackTrace(System.out);
			}

			outs.flush();
			outs.close();
			in.close();
			System.out.println("end");
			session.removeAttribute("Success");

		}
	%>
	<div class="container">
	
		<div class="row-fluid">
			<div class="span7">
				<div id="delFilesSuc" class="alert alert-success" style="background-color: #6CAC52"
					id="successMsg">
					<a class="close" data-dismiss="alert" href="#">&times;</a> <b><i
						class="icon-ok icon-white"></i><span style="color: white">
						Success! All Files have been deleted</span></b>
				
				</div>
					<div id="genFilesSuc" class="alert alert-success" style="background-color: #6CAC52"
					id="successMsg">
					<a class="close" data-dismiss="alert" href="#">&times;</a> <b><i
						class="icon-ok icon-white"></i><span style="color: white">
						File Generation Successful!</span></b>
				
				</div>
				
					<div id="delSingleFile" class="alert alert-success" style="background-color: #6CAC52"
					id="successMsg">
					<a class="close" data-dismiss="alert" href="#">&times;</a> <b><i
						class="icon-ok icon-white"></i><span style="color: white">
						<%if(request.getParameter("delFile")!=null){
							String msg = (String)request.getParameter("delFile");
							%><%=msg%> has been deleted.</span></b>
						<%} %>
				
				</div></div>
			</div>
		</div>
		
		<div class="row-fluid" style ="padding-top:20px">
			<button type="button" class="btn btn-danger" onclick="javascript: removeAll();" value="ClearEntries">Clear Entries</button>
		
			<!-- <button type="button" class="btn btn-success" onclick="javascript: downloadAll();" value="downloadAll">Download All</button>-->
		</div>
		
		</br>
		
		<div class="row-fluid">
			<div class="span7">
			<form name="generateReport" action="GenerationComplete.jsp"
						method="post">
				<table class="table table-hover">
				
					<tr>
						<td><b>File Name:</b></td>
					</tr>



					<%//"chosenfiles are group report files"
					if(session.getAttribute("chosenFiles")!=null){
					Vector files = (Vector) session.getAttribute("chosenFiles");
						if (files.size() > 0) {
							for (int i = 0; i < files.size(); i++) {
									String fileName = (String) files.elementAt(i);
									%><tr class="Trows"><td><label onclick="formSubmit(this.form,'<%=fileName%>')"><input type="hidden" id="<%=fileName%>" name="chooseFile" value="<%=fileName%>"><%=fileName%></label></td>
									<td>
										<button type="button" class="close" onclick="javascript: removeClass('<%=fileName %>');" >×</button>
									</td></tr>
									<%
							}
						} 
					}
					%>
							<%if(session.getAttribute("IndividualReport")!=null){
								Vector grpfiles = (Vector) session.getAttribute("IndividualReport");
									if (grpfiles.size() > 0) {
										for (int i = 0; i < grpfiles.size(); i++) {
										String fileName1 = (String) grpfiles.elementAt(i);
										
										%><tr class="Trows"><td><label onclick="formSubmit(this.form,'<%=fileName1%>')"><input type="hidden" id="<%=fileName1%>" name="chooseFile" value="<%=fileName1%>"><%=fileName1%></label></td>
										<td>
											<button type="button" class="close" onclick="javascript: removeClass('<%=fileName1%>');" >×</button>
										</td>
										</tr>
										<%
										}
									}	
							}
						%>
					
					<%
					Vector a=new Vector();
					Vector b=new Vector();
					if(session.getAttribute("chosenFiles")!=null){
					 a = (Vector) session.getAttribute("chosenFiles");
					}
					if(session.getAttribute("IndividualReport")!=null){
						b = (Vector) session.getAttribute("IndividualReport");
					}
					if((session.getAttribute("chosenFiles")==null && session.getAttribute("IndividualReport")==null)||(a.size()==0 && b.size()==0)){
						%><tr><td>There are no generated files.</td><tr>
					<%}%>



				</table>
				</form>
			</div>
		</div>


		<div class="row-fluid">

			<div class="offset1 span2">
				<a class="btn info" value="Back to Group Report page"
					href="GroupReport.jsp" data-toggle="tooltip" title=""
					data-placement="right"
					data-original-title="Return back to Group report generation page.">
					Group Report</a>
			</div>
	
		

			<div class="span2">
				<a class="btn info" value="Back to Individual Report page"
					href="IndividualReport.jsp" data-toggle="tooltip" title=""
					data-placement="right"
					data-original-title="Return back to Individual report generation page.">
					Individual Report</a>
			</div>
		
		</div>
	</div>
</body>
</html>