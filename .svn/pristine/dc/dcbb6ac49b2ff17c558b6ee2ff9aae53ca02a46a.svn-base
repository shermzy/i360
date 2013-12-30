function chkdate(objName) {
//var strDatestyle = "US"; //United States date style
var strDatestyle = "EU";  //European date style
var strDate;
var strDateArray;
var strDay;
var strMonth;
var strYear;
var intday;
var intMonth;
var intYear;
var booFound = false;
var datefield = objName;
var strSeparatorArray = new Array("-"," ","/",".");
var intElementNr;
var err = 0;
var strMonthArray = new Array(12);
strMonthArray[0] = "Jan";
strMonthArray[1] = "Feb";
strMonthArray[2] = "Mar";
strMonthArray[3] = "Apr";
strMonthArray[4] = "May";
strMonthArray[5] = "Jun";
strMonthArray[6] = "Jul";
strMonthArray[7] = "Aug";
strMonthArray[8] = "Sep";
strMonthArray[9] = "Oct";
strMonthArray[10] = "Nov";
strMonthArray[11] = "Dec";
strDate = datefield.value;
if (strDate.length < 1) {
return true;
}
for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
strDateArray = strDate.split(strSeparatorArray[intElementNr]);
if (strDateArray.length != 3) {
err = 1;
return false;
}
else {
strDay = strDateArray[0];
strMonth = strDateArray[1];
strYear = strDateArray[2];
}
booFound = true;
   }
}
if (booFound == false) {
if (strDate.length>5) {
strDay = strDate.substr(0, 2);
strMonth = strDate.substr(2, 2);
strYear = strDate.substr(4);
   }
}
if (strYear.length == 2) {
strYear = '20' + strYear;
}
// US style
if (strDatestyle == "US") {
strTemp = strDay;
strDay = strMonth;
strMonth = strTemp;
}
intday = parseInt(strDay, 10);
if (isNaN(intday)) {
err = 2;
return false;
}
intMonth = parseInt(strMonth, 10);
if (isNaN(intMonth)) {
for (i = 0;i<12;i++) {
if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
intMonth = i+1;
strMonth = strMonthArray[i];
i = 12;
   }
}
if (isNaN(intMonth)) {
err = 3;
return false;
   }
}
intYear = parseInt(strYear, 10);
if (isNaN(intYear)) {
err = 4;
return false;
}
if (intMonth>12 || intMonth<1) {
err = 5;
return false;
}
if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
err = 6;
return false;
}
if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
err = 7;
return false;
}
if (intMonth == 2) {
if (intday < 1) {
err = 8;
return false;
}
if (LeapYear(intYear) == true) {
if (intday > 29) {
err = 9;
return false;
}
}
else {
if (intday > 28) {
err = 10;
return false;
}
}
}

if (strDatestyle == "US") {
tanggal = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
}
else {
tanggal = intday + " " + strMonthArray[intMonth-1] + " " + strYear;
}

return true;
}



function isNumericValue(strDate) {
	
	var strDateArray;
	
	if(strDate.indexOf("/") != -1)
		strDateArray = strDate.split("/");
	else 
		strDateArray = strDate.split(" ");
		
	if (strDateArray.length != 3) {
		return false;
	} else {
		for (var i=0; i<strDateArray.length; i++) {
	
			if(i == 0 || i == 2) {
				if (strDateArray[i].search(/^[0-9]+$/)) 
					return false;
			} else if(i ==1) {
				if(strDateArray[i].search(/^[0-9]+$/) && (strDateArray[i] != 'Jan' && strDateArray[i] != 'Feb' && strDateArray[i] != 'Mar' &&
														strDateArray[i] != 'Apr' && strDateArray[i] != 'May' && strDateArray[i] != 'Jun' &&
														strDateArray[i] != 'Jul' && strDateArray[i] != 'Aug' && strDateArray[i] != 'Sep' &&
														strDateArray[i] != 'Oct' && strDateArray[i] != 'Nov' && strDateArray[i] != 'Dec')) 
					return false;
			}
		}
	}

	return true;
}

function checkdate(objName) {
	var datefield = objName;
	
	if(objName.value != '') {
		if(!isNumericValue(objName.value)) {
			//datefield.select();
			alert("Invalid input");
			//datefield.focus();
			objName.focus();
			
			return false;
		
		} else {
			if(!chkdate(objName)) {
				//datefield.select();
				alert("Invalid date");
				//datefield.focus();
				objName.focus();
				
				return false;
			}
		}
	}
	else
	{
		alert("Please enter the date");
		return false;
	}
	return true;
}


function checkdateValid(objName) {
	var datefield = objName;
	if (chkdate(objName) == false) {
		return false;
	}
	else {
		return true;
   }
}

function checkTwoDates(field1, field2) {
	//if(parseInt(field1) > parseInt(field2)) {
	if(Date.parse(field1) > Date.parse(field2)) {
		alert("From (date) must be earlier than To (date)");
		return false;
	}
	
	return true;
}

function LeapYear(intYear) {
	if (intYear % 100 == 0) {
	if (intYear % 400 == 0) { return true; }
	}
	else {
	if ((intYear % 4) == 0) { return true; }
	}
		return false;
	}