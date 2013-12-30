/**
 * @author xuehai
 */

/******************************************** 
 ** Javascript functions for Questionnaires.jsp 
 ********************************************/
function checkTextAreaSize(txtAreaField, iMaxLimit) {
	if(txtAreaField.value.length > iMaxLimit) {
		alert("Maximum characters exceeded. Maximum characters allowed: " + iMaxLimit);
		txtAreaField.value = txtAreaField.value.substring(0,iMaxLimit);
	}
}

function disableButton(form){
	//Disable User to click again while system is processing
	form.btnNext.disabled = true;
	form.btnPrev.disabled = true;
	form.btnFinish.disabled = true;
	form.btnExit.disabled = true;
	form.btnSave.disabled = true;
}

function enableButton(form){
	//Disable User to click again while system is processing
	form.btnNext.disabled = false;
	form.btnPrev.disabled = false;
	form.btnFinish.disabled = false;
	form.btnExit.disabled = false;
	form.btnSave.disabled = false;
}

function goNext(form)
{
	//disableButton(form);
	
	form.action = "Questionnaires.jsp?go=1";
	form.method = "post";
	form.submit();
}

function goPrev(form){
	
	form.action = "Questionnaires.jsp?go=2";
	form.method = "post";
	form.submit();
}
//End. Removing 'void'

function confirmFinish(form, type)
{
	//type 1=save, 2=finish
	var clicked = false;
	
	if(type == 1) {
		if(confirm("If you have finished and do not wish to change the ratings any further, click CANCEL here and click on the FINISH button instead. If you simply want to save your ratings for now, click OK here"))
			clicked = true;
		else{
			return false;				
		}
	}
	else if(type == 2)
		clicked = true;
	else {
		if(confirm("Your rating will be saved and your assignment status for this target will be set to incomplete"))
			clicked = true;
		else 
		{
			return false;				
		}				
	}
					
	if(clicked == true) {
		form.action = "Questionnaires.jsp?go=3&finish=" + type;
		form.method = "post";
		form.submit();
	}
	
	return true;
}

function showtip(current,e,text){
	if (document.all||document.getElementById){
		thetitle=text.split('<br>');
	
		if (thetitle.length>1){
			thetitles='';

			for (i=0;i<thetitle.length;i++)
				thetitles+=thetitle[i];
				current.title=thetitles;
		} else
			current.title=text;
	
	} else if (document.layers){
		document.tooltip.document.write('<layer bgColor="white" style="border:1px solid black;font-size:12px;">'+text+'</layer>');
		document.tooltip.document.close();
		document.tooltip.left=e.pageX+5;
		document.tooltip.top=e.pageY+5;
		document.tooltip.visibility="show";
	}
}

function hidetip(){
	if (document.layers)
		document.tooltip.visibility="hidden";
}

//Add by James 4-June 2008
function displayExpiredWarning(){
	alert('Warning, your session is about to expire');
	if (window.opener && !window.opener.closed){
	    window.opener.location.href="Top_Login.jsp?logoff=1";
		self.close();
	    }else{
		window.location.href="Top_Login.jsp?logoff=1";
	}
	
}//End of displayExpiredWarning

function displayWarning(){
	var answer = confirm('This session will expire in two minutes. Would you like to extend this session?')
	if (answer){
		document.Questionnaire.action = "Questionnaires.jsp?go=3&finish=1";
		document.Questionnaire.method = "post";
		document.Questionnaire.submit();	
	}
	else{
		setTimeout("displayExpiredWarning()",2*60*1000);
	}
}//End of displayWarning