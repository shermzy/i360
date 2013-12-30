<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%
//Added by Xuehai, provide option for user to choose what data will be deleted.
%>
<title>Delete Survey Confirm</title>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>
	function delConfirm(isAll, isAss, isRat, isTar){
		//returnValue: isAll:123, isAss:3, isRat:2, isTar:1;
		var returnValue='';
		if(isAll==true){
			returnValue="123";
		}else{
			returnValue="";
			if(isAss==true)returnValue=returnValue + "3";
			if(isRat==true)returnValue=returnValue + "2";
			if(isTar==true)returnValue=returnValue + "1";
			
			returnValue=(returnValue==""?"0":returnValue);
		}
		if(confirm("<%=trans.tslt("Delete Survey")%>?")){
			if((window.opener!=null)&&(!window.opener.closed)){ 
				window.opener.postDelete(returnValue);
			}
			window.close();
		}
	}
	
	function cancel(){
		window.close();
	}
	
	function ckAllChange(ckAll){
		if(ckAll.checked){
			document.getElementById("ckAss").disabled=true;
			document.getElementById("ckRat").disabled=true;
			document.getElementById("ckTar").disabled=true;
		}else{
			document.getElementById("ckAss").disabled=false;
			document.getElementById("ckRat").disabled=false;
			document.getElementById("ckTar").disabled=false;
		}
	}
</script>
<body style="text-align: left" bgcolor="#E2E6F1">
<form name="survey" action="SurveyList_DelConfirm" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
	<td><font size="2" face="Arial"><b>Choose Data to be deleted</b></font></td>
	</tr>
</table>
<br />
<table border="0" width="95%" cellspacing="0" cellpadding="0" height="19">
	<tr>
		<td width="30px" align="right"><INPUT TYPE="checkbox" CHECKED ID="ckAll" onclick="ckAllChange(this);"></td>
		<td colspan="2"><font face="Arial" size="2">All</font></td>
	</tr>
	<tr>
		<td width="30px" align="right">&nbsp;</td>
		<td width="30px" align="right"><INPUT TYPE="checkbox" disabled ID="ckAss"></td>
		<td><font face="Arial" size="2">Target-rater assignments</font></td>
	</tr>
	<tr>
		<td width="30px" align="right">&nbsp;</td>
		<td width="30px" align="right"><INPUT TYPE="checkbox" disabled ID="ckRat"></td>
		<td><font face="Arial" size="2">Raters</font></td>
	</tr>
	<tr>
		<td width="30px" align="right">&nbsp;</td>
		<td width="30px" align="right"><INPUT TYPE="checkbox" disabled ID="ckTar"></td>
		<td><font face="Arial" size="2">Targets</font></td>
	</tr>
	<tr>
		<td colspan="3" align="center">
	    	<input type="button" value="<%=trans.tslt("OK")%>" name="btnOK" onclick="delConfirm(this.form.ckAll.checked, this.form.ckAss.checked,this.form.ckRat.checked, this.form.ckTar.checked)" style="float:center">
	    	&nbsp;
	    	<input type="button" value="<%=trans.tslt("Cancel")%>" name="btnCancel" onclick="cancel()" style="float:center">
		</td>
	</tr>
	</table>
</form>
</body>
</html>