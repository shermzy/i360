<script language="JavaScript">
<!--
function FP_preloadImgs() {//v1.0
 var d=document,a=arguments; if(!d.FP_imgs) d.FP_imgs=new Array();
 for(var i=0; i<a.length; i++) { d.FP_imgs[i]=new Image; d.FP_imgs[i].src=a[i]; }
}

function FP_swapImg() {//v1.0
 var doc=document,args=arguments,elm,n; doc.$imgSwaps=new Array(); for(n=2; n<args.length;
 n+=2) { elm=FP_getObjectByID(args[n]); if(elm) { doc.$imgSwaps[doc.$imgSwaps.length]=elm;
 elm.$src=elm.src; elm.src=args[n+1]; } }
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
</script>
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
</script>
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
</script>
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
</script>
<%@ page import="java.sql.*,
                 java.io.* "%>  
                 
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
	if(confirm("<%=trans.tslt("Delete Target?")%>"))
	{
		var myWindow=window.open('','windowRef','scrollbars=no, width=150, height=50');
    	myWindow.location.href = 'AssignTR_TargetMenu.jsp?del=1';
	}
	else
	{
		window.close();
		opener.location.href ='AssignTarget_Rater.jsp';
	}
}

function add() 
{
	window.close();
	opener.location.href ='AssignTR_TargetMenu_AddRater.jsp';
}
</SCRIPT>

<body onload="FP_preloadImgs(/*url*/'images/buttonB.jpg',/*url*/'images/buttonC.jpg',/*url*/'images/button5.jpg',/*url*/'images/button6.jpg',/*url*/'images/button5E.jpg',/*url*/'images/button5F.jpg')" bgcolor="#FFFFCC">
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

assignTR.setGroupID(0);
if(request.getParameter("del") != null)
{
	boolean bIsDeletedTarget = assignTR.delTarget(CE_Survey.getSurvey_ID(),assignTR.get_selectedTargetID(), logchk.getPKUser());
	boolean bIsDeletedTargetResult = assignTR.delTargetsResult(CE_Survey.getSurvey_ID(),assignTR.get_selectedTargetID());	// Delete Target's results from tblAvgMean

	if(bIsDeletedTarget || bIsDeletedTargetResult) {
%>	
<script>
alert("Deleted successfully");
window.close();
opener.location.href = 'AssignTarget_Rater.jsp';
</script>
<%
	}
}
%>
<form name="AssignTR_TargetMenu" action="AssignTR_TargetMenu.jsp" method="post">
<table border="0" width="97%" cellspacing="0" cellpadding="0" height="20">

<tr>
		<td>
		<p align="left">
		<a onclick="add()">
		<img border="0" id="img2" src="images/button5D.jpg" height="20" width="100" alt="Add Rater" onmouseover="FP_swapImg(1,0,/*id*/'img2',/*url*/'images/button5E.jpg')" onmouseout="FP_swapImg(0,0,/*id*/'img2',/*url*/'images/button5D.jpg')" onmousedown="FP_swapImg(1,0,/*id*/'img2',/*url*/'images/button5F.jpg')" onmouseup="FP_swapImg(0,0,/*id*/'img2',/*url*/'images/button5E.jpg')" fp-style="fp-btn: Border Left 1" fp-title="Add Rater"></td>
		</a>
	</tr>
	<tr>
		<td>&nbsp;
		</td>
	</tr>
<%
	/* Nomination module */			
	if(logchk.getUserType() != 4)
	{	%>	
	<tr>
		<td>
		<p align="left">
		
		<a onclick="del()">
		<img border="0" id="img1" src="images\buttonA.jpg" height="20" width="100" alt="Delete Target" fp-style="fp-btn: Border Left 1" fp-title="Delete Target" onmouseover="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/buttonB.jpg')" onmouseout="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/buttonA.jpg')" onmousedown="FP_swapImg(1,0,/*id*/'img1',/*url*/'images/buttonC.jpg')" onmouseup="FP_swapImg(0,0,/*id*/'img1',/*url*/'images/buttonB.jpg')"></a></td>
	</tr>
<%	}%>	
	
	
</table>
</form>
<%	}	%>
</body>
</html>