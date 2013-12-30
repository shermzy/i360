<%@ page import="java.sql.*,
                 java.io.* , CP_Classes.SurveyResult"%>  
                 
<jsp:useBean id="db" class="CP_Classes.Database" scope="session"/>
<jsp:useBean id="assignTR" class="CP_Classes.AssignTarget_Rater" scope="session"/>
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>
<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>  
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html">
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<script language="JavaScript">
<!--
function FP_swapImg() {//v1.0
 var doc=document,args=arguments,elm,n; doc.$imgSwaps=new Array(); for(n=2; n<args.length;
 n+=2) { elm=FP_getObjectByID(args[n]); if(elm) { doc.$imgSwaps[doc.$imgSwaps.length]=elm;
 elm.$src=elm.src; elm.src=args[n+1]; } }
}

function FP_preloadImgs() {//v1.0
 var d=document,a=arguments; if(!d.FP_imgs) d.FP_imgs=new Array();
 for(var i=0; i<a.length; i++) { d.FP_imgs[i]=new Image; d.FP_imgs[i].src=a[i]; }
}

function FP_getObjectByID(id,o) {//v1.0
 var c,el,els,f,m,n; if(!o)o=document; if(o.getElementById) el=o.getElementById(id);
 else if(o.layers) c=o.layers; else if(o.all) el=o.all[id]; if(el) return el;
 if(o.id==id || o.name==id) return o; if(o.childNodes) c=o.childNodes; if(c)
 for(n=0; n<c.length; n++) { el=FP_getObjectByID(id,c[n]); if(el) return el; }
 f=o.forms; if(f) for(n=0; n<f.length; n++) { els=f[n].elements;
 for(m=0; m<els.length; m++){ el=FP_getObjectByID(id,els[n]); if(el) return el; } }
 return null;
}
// -->


function del() 
{
	if(confirm("<%=trans.tslt("Delete Rater?")%>"))
	{
		//var myWindow=window.open('AssignTR_RaterMenu.jsp?del=1','windowRef','scrollbars=no, width=350, height=550');
		var myWindow=window.open('AssignTR_RaterMenu.jsp?del=1','windowRef','scrollbars=no, width=150, height=50');
    	//myWindow.location.href = 'AssignTR_RaterMenu.jsp?del=1';
	}
	else
	{
		window.close();
		opener.location.href ='AssignTarget_Rater.jsp';
	}
}

function edit()
{
	window.close();
	opener.location.href ='AssignTR_RaterMenu_EditRater.jsp';
	}
</script>
<body onload="FP_preloadImgs(/*url*/'images/button2B.jpg',/*url*/'images/button2C.jpg')" bgcolor="#FFFFCC">
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
	  
if(request.getParameter("del") != null)
{
		//Changed by Ha 19/06/08 to automatically calculated again when a rater is removed
		SurveyResult SR = new SurveyResult();
		int targetID = SR.TargetID(assignTR.get_selectedAssID());
		boolean bIsDeleted = assignTR.delRater(assignTR.get_selectedAssID(), logchk.getPKUser());
	
%>	
		<script>
		alert("Deleted successfully");
		window.close();
		opener.location.href ='AssignTarget_Rater.jsp';
		</script>
<%
	
		SR.updateCalculationStatus(targetID, CE_Survey.getSurvey_ID(),0);
		if (!SR.isAllRaterRated(CE_Survey.getSurvey_ID(),assignTR.getGroupID(), targetID)) 
			SR.CalculateStatus(targetID,CE_Survey.getSurvey_ID(), assignTR.getDivID(), assignTR.getDeptID(), assignTR.getGroupID(), 1);	
		else    	
		    SR.CalculateStatus(targetID,CE_Survey.getSurvey_ID(), assignTR.getDivID(), assignTR.getDeptID(), assignTR.getGroupID(), 0);	
		//End of change made by Ha 19/06/08
		assignTR.set_selectedTargetID(0);
		assignTR.set_selectedAssID(0);
}
%>
<form name="AssignTR_RaterMenu" action="AssignTR_RaterMenu.jsp" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<a onclick="del()">
		<img border="0" id="img1" src="images/button2A.jpg" height="20" width="100" alt="Delete Rater" onmouseover="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/button2B.jpg')" onmouseout="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/button2A.jpg')" onmousedown="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/button2C.jpg')" onmouseup="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/button2B.jpg')" fp-style="fp-btn: Border Left 1" fp-title="Delete Rater"></td>
		</a>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>
		<p align="left">
		
		<a onclick="edit()">
		<img border="0" id="img2" src="images\button4A.jpg" height="20" width="100" alt="Edit Rater" fp-style="fp-btn: Border Left 1" fp-title="Edit Rater" onmouseover="FP_swapImg(1,0,/*id*/'img2',/*url*/'images/button4B.jpg')" onmouseout="FP_swapImg(0,0,/*id*/'img2',/*url*/'images/button4A.jpg')" onmousedown="FP_swapImg(1,0,/*id*/'img2',/*url*/'images/button4C.jpg')" onmouseup="FP_swapImg(0,0,/*id*/'img2',/*url*/'images/button4B.jpg')"></a></td>
	</tr>
</table>
</form>
<%	}	%>
</body>
</html>
