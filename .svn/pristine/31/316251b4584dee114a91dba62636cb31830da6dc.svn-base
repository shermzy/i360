var xmlHttp
var loadstatustext="<img src='images/ajax-loading.gif' /> Loading..."

/**
* START - Methods used by Questionnaires.jsp and GetQuestionnaires.jsp
*/

function showQuestionaires()
{
	xmlHttp=GetXmlHttpObject()
	if (xmlHttp==null)
	{
	alert ("Browser does not support HTTP Request")
	return
	}
	var val;
	var url="GetQuestionnaires.jsp"
	url=url+"?sid="+Math.random()

	// The callback function
	xmlHttp.onreadystatechange=stateChanged 
	xmlHttp.open("GET",url,true)
	xmlHttp.send(null)
}

function confirmSave(type, messageToDisplay)
{
	xmlHttp=GetXmlHttpObject()
	if (xmlHttp==null)
	{
	alert ("Browser does not support HTTP Request")
	return
	}
	
	var clicked = false;
	
	if(confirm(messageToDisplay)) {
		clicked = true;
	} else {
		return false;
	}
	
	//parameter go = 1 is used to indicate next and 2 is used to indicate previous.
	var url	="GetQuestionnaires.jsp"
	url=url+"?go=3&finish=" + type
	if (document.getElementById( "commentID" ) != null && document.getElementById( "commentID" ).value != "") {
		var commentIDtemp = "" + document.Questionnaire.commentID.value
		var commentID 			= commentIDtemp.split(",")
		
		for(i=0; i < commentID.length; i++) {
			if (document.getElementById( commentID[i] ).value != "") {
				alert(document.getElementById( commentID[i] ).value);
				url=url+"&" + commentID[i] + "='" + document.getElementById( commentID[i] ).value+"'";
			}
		}
	}
	if (document.getElementById( "radiobuttonName" ) != null && document.getElementById( "radiobuttonName" ).value != "") {
		var rbtnScale2 = ""
		var radiobuttonNametemp = "" + document.Questionnaire.radiobuttonName.value
		var radiobuttonIDtemp  	= "" + document.Questionnaire.radiobuttonID.value
		var radiobuttonName 	= radiobuttonNametemp.split(",")
		var radiobuttonID 		= radiobuttonIDtemp.split(",")
		for(i=0; i < radiobuttonID.length; i++) {
			rbtnScale2 = "rbtnScale" + "_" + radiobuttonID[i]
			if (document.getElementById( rbtnScale2 ).checked) {
				
				url=url+"&rbtnScale" + "_" + radiobuttonName[i] + "=" + document.getElementById( rbtnScale2 ).value;
			}
		}
	}
	url=url+"&saveSuccessful=1"
	url=url+"&sid="+Math.random()

	// The callback function
	xmlHttp.onreadystatechange=stateChanged
	xmlHttp.open("GET",url,true)
	xmlHttp.send(null)
}

function proceedNext(totalRatingTask)
{
	xmlHttp=GetXmlHttpObject()
	if (xmlHttp==null)
	{
	alert ("Browser does not support HTTP Request")
	return
	}
	
	//parameter go = 1 is used to indicate next and 2 is used to indicate previous.
	var url		="GetQuestionnaires.jsp"
	url=url+"?go=1"
	if (document.getElementById( "commentID" ) != null && document.getElementById( "commentID" ).value != "") {
		var commentIDtemp = "" + document.Questionnaire.commentID.value
		var commentID 			= commentIDtemp.split(",")
		
		for(i=0; i < commentID.length; i++) {
			if (document.getElementById( commentID[i] ).value != "") {
				alert(document.getElementById( commentID[i] ).value);
				url=url+"&" + commentID[i] + "='" + document.getElementById( commentID[i] ).value+"'";
			}
		}
	}
	if (document.getElementById( "radiobuttonName" ) != null && document.getElementById( "radiobuttonName" ).value != "") {
		var rbtnScale2 = ""
		var radiobuttonNametemp = "" + document.Questionnaire.radiobuttonName.value
		var radiobuttonIDtemp  	= "" + document.Questionnaire.radiobuttonID.value
		var radiobuttonName 	= radiobuttonNametemp.split(",")
		var radiobuttonID 		= radiobuttonIDtemp.split(",")
		for(i=0; i < radiobuttonID.length; i++) {
			rbtnScale2 = "rbtnScale" + "_" + radiobuttonID[i]
			if (document.getElementById( rbtnScale2 ).checked) {
				url=url+"&rbtnScale" + "_" + radiobuttonName[i] + "=" + document.getElementById( rbtnScale2 ).value;
			}
		}
	}
	url=url+"&sid="+Math.random()

	// The callback function
	xmlHttp.onreadystatechange=stateChanged 
	xmlHttp.open("GET",url,true)
	xmlHttp.send(null)
}

function proceedPrev(form)
{
	xmlHttp=GetXmlHttpObject()
	if (xmlHttp==null)
	{
	alert ("Browser does not support HTTP Request")
	return
	}
	
	//parameter go = 1 is used to indicate next and 2 is used to indicate previous.
	var url		="GetQuestionnaires.jsp"
	url=url+"?go=2"
	if (document.getElementById( "commentID" ) != null && document.getElementById( "commentID" ).value != "") {
		var commentIDtemp = "" + document.Questionnaire.commentID.value
		var commentID 			= commentIDtemp.split(",")
		
		for(i=0; i < commentID.length; i++) {
			if (document.getElementById( commentID[i] ).value != "") {
				alert(document.getElementById( commentID[i] ).value);
				url=url+"&" + commentID[i] + "='" + document.getElementById( commentID[i] ).value +"'";
			}
		}
	}
	if (document.getElementById( "radiobuttonName" ) != null && document.getElementById( "radiobuttonName" ).value != "") {
		var rbtnScale2 = ""
		var radiobuttonNametemp = "" + document.Questionnaire.radiobuttonName.value
		var radiobuttonIDtemp  	= "" + document.Questionnaire.radiobuttonID.value
		var radiobuttonName 	= radiobuttonNametemp.split(",")
		var radiobuttonID 		= radiobuttonIDtemp.split(",")
		for(i=0; i < radiobuttonID.length; i++) {
			rbtnScale2 = "rbtnScale" + "_" + radiobuttonID[i]
			if (document.getElementById( rbtnScale2 ).checked) {
				url=url+"&rbtnScale" + "_" + radiobuttonName[i] + "=" + document.getElementById( rbtnScale2 ).value;
			}
		}
	}
	url=url+"&sid="+Math.random()

	// The callback function
	xmlHttp.onreadystatechange=stateChanged 
	xmlHttp.open("GET",url,true)
	xmlHttp.send(null)
}
/**
* END - Methods used by RatingDetail.jsp and GetRatingDetail.jsp
*/

/**
* BEGINNING - AJAX Methods
*/

function stateChanged() 
{
  if (xmlHttp.readyState==0 || xmlHttp.readyState==1 || xmlHttp.readyState==2 || xmlHttp.readyState==3)
  { 
	document.getElementById("txtHint").innerHTML = loadstatustext;
  }
  if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
  {
    if (xmlHttp.status == 200) 
	{
		var response = xmlHttp.responseText.split("|");
		if (response[0].indexOf("Save!") != -1) {
			document.getElementById("txtHint").innerHTML = response[1];
			window.scrollTo(0, 0);
		} else {
			document.getElementById("txtHint").innerHTML = response[0];
		}
    } else if (xmlHttp.status == 404) {
		// Retrive error message or redirect to error page
		document.getElementById("txtHint").innerHTML = "File not found";
	} else {
		// Give a catch all error message
		document.getElementById("txtHint").innerHTML = "We are currently experiencing technical difficulties and are addressing the issue.";
	}
  }
} 

function GetXmlHttpObject()
{ 
var objXMLHttp=null
if (window.XMLHttpRequest)
{
objXMLHttp=new XMLHttpRequest()
}
else if (window.ActiveXObject)
{
objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP")
}
return objXMLHttp
}

/**
* END - AJAX Methods
*/