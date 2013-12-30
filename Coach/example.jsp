<%// Author: Dai Yong in June 2013%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>jsDatePick Javascript example</title>
<link rel="stylesheet" type="text/css" media="all" href="jsDatePick_ltr.min.css" />

<script type="text/javascript" src="jsDatePick.min.1.3.js"></script>

<script type="text/javascript">
	window.onload = function(){
		new JsDatePick({
			useMode:2,
			target:"inputField",
			dateFormat:"%d-%M-%Y"
		});
	};
</script>

<script>

function confirmAdd(form,field)
{
	alert("asdf");
	alert(field.value);
	
	
}
</script>
</head>
<body>
    <form>
    <input name="inputField" type="text" size="12" id="inputField" />
    <input type="submit" name="Submit" value="Submit" onClick="confirmAdd(this.form,this.form.inputField)">
 </form>
    
</body>
</html>
