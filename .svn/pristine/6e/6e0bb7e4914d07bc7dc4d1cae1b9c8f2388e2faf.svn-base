<%@ page import="java.sql.*,
                 java.io.*,
                 java.text.DateFormat,
                 java.util.*,
                 java.util.Date,
                 java.text.*,
                 java.lang.String"%>  

<jsp:useBean id="logchk" class="CP_Classes.Login" scope="session"/>                   
<jsp:useBean id="CE_Survey" class="CP_Classes.Create_Edit_Survey" scope="session"/>    
<jsp:useBean id="global" class="CP_Classes.GlobalFunc" scope="session"/>
<jsp:useBean id="adv" class="CP_Classes.AdvanceSettings" scope="session"/>
<jsp:useBean id="trans" class="CP_Classes.Translate" scope="session"/>
<jsp:useBean id="SVR" class="CP_Classes.SurveyRating" scope="session"/>
<%@ page import="CP_Classes.vo.voCompetency"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%@ page pageEncoding="UTF-8"%>
<%// by lydia Date 05/09/2008 Fix jsp file to support Thai language %>
</head>
<SCRIPT LANGUAGE=JAVASCRIPT>

function Save(form,field)
{
    // Added by DeZ, 25/06/08, Prevent empty field input for all text input fields to prevent exception 500
    
    // Navigate DOM to get all text fields inputs
    inputs = form.getElementsByTagName("input");

	// Added by Ping Yang, 31/07/08, Disable saving function if no input
	var blnInputExist = false;
	

//    var inputL = "";
    // Check all validate all text field inputs
    for (i = 0; i < inputs.length; i++)
    {
        if(inputs[i].type == 'text' && inputs[i].id != "fixAll"){
            // Check text field for empty string
            if( inputs[i].value == "" ) inputs[i].value = 0;
//            inputL += ", " + inputs[i].value;
				// Added by Ping Yang, 31/07/08, Disable saving function if no input
				blnInputExist = true;
        }
    }
    if(blnInputExist){
	//    alert("inputs = " + inputL);
	
	//\\Confirm added by Ha 29/05/08
		
	    if (confirm ("Save Rating Task Scale?"))
	    {
	        // Submit form with data for processing
	        form.action="RatingSetup.jsp?Save=1";
	        form.method="post";
	        form.submit();
	   }
	}else{// Added by Ping Yang, 31/07/08, Disable saving function if no input
		alert("No Rating task to save");
	}    
} // End Save function

function tutup(form,field)
{
	form.action="RatingSetup.jsp?close=1";
	form.method="post";
	form.submit();
}

function change(form, field,field2)
{
	form.action="RatingSetup.jsp?change="+field;
	form.method="post";
	form.submit();
}

function fixScore(form, scale)
{
	if(IsNumeric(scale))
	{
		if(scale.value>10 || scale.value <1){
                      alert("Value range is between (1 - 10)");
                     }else{
                       form.action="RatingSetup.jsp?fix="+scale.value;
                        form.method="post";
                        form.submit();
                     }
	}
}

function fixS()
{
 alert("1");
}

//  check for valid numeric strings
function IsNumeric(oElement)
{
	var strString = oElement.value;
	var strValidChars = "0123456789.";
	var strChar;
	var blnResult = true;
	
	if (strString.length == 0) return false;
	
	//  test strString consists of valid characters listed above
	for (i = 0; i < strString.length && blnResult == true; i++)
	{
		strChar = strString.charAt(i);
		if (strValidChars.indexOf(strChar) == -1)
		{
			blnResult = false;
			alert("Please enter only numeric value for rating scale");
		}
	}
	return blnResult;
}

</SCRIPT>
<body>
<%
	String username=(String)session.getAttribute("username");

  	if (!logchk.isUsable(username)) 
  	{%> <font size="2">
		<script>
			parent.location.href = "index.jsp";
		</script>
<% 	} 

	if(request.getParameter("Save") != null)
	{
		boolean masuk = false;
		int CompID=0;
		float ratingValue = 0;
		float fValue = 0;
		int RatingTaskID_sql [] = adv.getRatingTaskID();
		int hide = 0;
                
                // Delete all fix rating values first
		adv.delete_tblRatingSetup(CE_Survey.getSurvey_ID(),logchk.getPKUser());

		for(int j=0; j<RatingTaskID_sql.length;j++)
		{
//System.out.println("========================== Record ["+ j +"] =============================");                    
                        // Get all competency with this id
			Vector vCompID = adv.getAllRecord_SurveyCompetency(CE_Survey.getSurvey_ID(),logchk.getPKUser());

			for(int i=0; i<vCompID.size(); i++)
			{
				voCompetency vo = (voCompetency)vCompID.elementAt(i);
				CompID = vo.getPKCompetency();
				String str_value = request.getParameter("txtRatingScale"+RatingTaskID_sql[j]+","+CompID);
//System.out.println(">>str_value = ^" + str_value + "^");

                                // Changed by DeZ, 30.06.08, fix problem with values in CPR being inserted under NI as well
				if(str_value != null) {
                                    
                                    ratingValue = Float.parseFloat(str_value);
//                                  //fValue = Float.parseFloat(str_value);
				} else {
                                    //System.out.println(">>str_value is null, set ratingValue to 0...");
                                    ratingValue = 0;
                                }
//System.out.println(">>ratingValue = " +  ratingValue + "; RatingTaskID_sql["+j+"] = " + RatingTaskID_sql[j]);
				if(ratingValue != 0 && RatingTaskID_sql[j] != 0)
				{
//System.out.println(">>value NOT 0, insert new value");
					adv.insert_tblRatingSetup(CE_Survey.getSurvey_ID(), CompID, RatingTaskID_sql[j], ratingValue, logchk.getPKUser());
				}
				else
				{
//System.out.println(">>value 0, removing current value");
					adv.delete_tblRatingSetup_Spec(CE_Survey.getSurvey_ID(),RatingTaskID_sql[j],logchk.getPKUser());
				}
			
			} // End for loop
			
			hide =1;
			if(RatingTaskID_sql[j] != 0)
			{
				String hide_str = request.getParameter("chkHide"+RatingTaskID_sql[j]);
				
				if(hide_str != null)
				{
					hide = 2;
				}
		
				adv.update_adminSetup(CE_Survey.getSurvey_ID(),RatingTaskID_sql[j],hide,logchk.getPKUser());		
			}
		}
		
%>		<script>
		   	alert("<%=trans.tslt("Saved successfully")%>");
		</script>	
<%						
	}	
	
	if(request.getParameter("close") != null)
	{
%>		<script>
    		window.close();
    	</script>	
<%	}    	

	if(request.getParameter("fix") != null)
	{
		int CompID=0;
		int hide=0;
		int ratingValue =0;
	
		int RatingTaskID_sql [] = adv.getRatingTaskID();
		
		for(int j=0; j<RatingTaskID_sql.length;j++)
		{
			
			String fix_str = request.getParameter("txtMainScale"+RatingTaskID_sql[j]);
			if(RatingTaskID_sql[j] != 0)
			{
				
				if(fix_str != null && !fix_str.equals("")) {
					
					
					float ratingTaskID_value = Float.parseFloat(fix_str);

				    if(ratingTaskID_value != 0)
						adv.deleteRatingSetup_Spec(CE_Survey.getSurvey_ID(), RatingTaskID_sql[j], logchk.getPKUser());
				
					Vector vCompetency = adv.getAllRecord_SurveyCompetency(CE_Survey.getSurvey_ID(),logchk.getPKUser());
					for(int i=0; i<vCompetency.size(); i++)
					{
						voCompetency vo = (voCompetency)vCompetency.elementAt(i);
						CompID = vo.getPKCompetency();
						//System.out.println("---"+RatingTaskID_sql[j] + "----------------------------------"+fix_str+"------"+ratingTaskID_value);
			
						if(ratingTaskID_value != 0)
							adv.insertRatingSetup(CE_Survey.getSurvey_ID(), CompID, RatingTaskID_sql[j], ratingTaskID_value, logchk.getPKUser());
					}
				}
			}
		}
		
		/**
		 *	To check whether to hide the rating task 
		 */
		for(int j=0; j<RatingTaskID_sql.length;j++)
		{
			String hide_str = request.getParameter("chkHide"+RatingTaskID_sql[j]);
			
			if(RatingTaskID_sql[j] != 0)
			{
				hide =1;
				if(hide_str != null)
					hide = 2;

				adv.update_adminSetup(CE_Survey.getSurvey_ID(), RatingTaskID_sql[j], hide, logchk.getPKUser());
			}
		}

	%>
	<script>
   	//Edited by Xuehai, 06 Jun 2011. Changing location.href() to location.href='';
   	//location.href('RatingSetup.jsp');
   	location.href='RatingSetup.jsp';
    </script>	
<%    
	}
	
	%>
		
<form name="RatingSetup" action="RatingSetup.jsp" method="post">

<table border="0" width="97%" cellspacing="0" cellpadding="0">

	<tr>
		<td><b>
<font face="Arial" size="2" color="#000080"><%=trans.tslt("Rating Scale Settings For Individual Competency")%></font></b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="100%">
		<p align="center"><font face="Arial">

		</font></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<table border="1" width="97%" bordercolor="#3399FF">
	
	<tr>
		<td width="32" bgcolor="#000080" align="center"><b>
		<font color="#FFFFFF" face="Arial" size="2">No</font></b></td>
		<td width="370" bgcolor="#000080" align="center"><b>
		<font color="#FFFFFF" face="Arial" size="2"><%=trans.tslt("Competency Name")%></font></b></td>
<%
	
	int counter = 0;
	String RatingName[] = new String [10];
	int RatingTaskID[] = new int [10];
	int AdminSetup [] = new int [10];
	int ScaleRange [] = new int [10];
	Vector rs = adv.getAllRecord_SurveyRating(CE_Survey.getSurvey_ID(),logchk.getPKUser());
	System.out.println(rs.size());
	for(int i=0; i<rs.size(); i++)
	{
		voCompetency vo = (voCompetency)rs.elementAt(i);
		RatingTaskID[counter] = vo.getRatingTaskID();
		RatingName[counter] = vo.getRatingName();
		AdminSetup[counter] = vo.getAdminSetup();
		ScaleRange[counter] = vo.getScaleRange();
		
		%>
			<td bgcolor="#000080" align="center"><b>
				<font color="#FFFFFF" face="Arial" size="2"><%=RatingName[counter]%></font></b></td>
<%		counter++;	
	}
	adv.setRatingTaskID(RatingTaskID);
%>
	</tr>
	<tr>
	<td width="402" bgcolor="#66CCFF" align="center" colspan="2"><b> <font face="Arial" size="2"><%=trans.tslt("Tick to Hide")%></font></b></td>
	<%
	for(int d=0; d<RatingName.length;d++)
	{
		//System.out.println(RatingName[d] + "----->" + AdminSetup[d]);
		if(RatingName[d] != null)
		{	
			if(AdminSetup[d] == 2)
			{	%>
				<td align ="center" bgcolor="#66CCFF" >
				<font size="2">
				<input type="checkbox" name="<%="chkHide"+RatingTaskID[d]%>" style="float: center" value="2" checked></td>
<%			}
			else
			{
%>				<td bgcolor="#66CCFF" align ="center"><input type="checkbox" name="<%="chkHide"+RatingTaskID[d]%>"  style="float: center" value="1"></td>	
<%			}	
		}
	}
%>
		</tr>


	<tr>
	<td width="402" bgcolor="#66FF99" align="center" colspan="2"><b> <font face="Arial"size="2"><%=trans.tslt("Fixed For All")%></font></b></td>
<%
	for(int d=0; d<RatingName.length;d++)
	{
		//System.out.println(RatingName[d] + "----->" + AdminSetup[d]);
		if(RatingName[d] != null)
		{	
%>
				<td align ="center" bgcolor="#66FF99" >
				<font face="Arial" size="2">
                                <% // Changed by DeZ, 25/06/08, Prevent empty field input for all text input fields to prevent exception 500 %>
				<input id="fixAll" type=text name="<%="txtMainScale"+RatingTaskID[d]%>" size="4" style="float: center">
				<input type="button" value="Fix All" name="<%="btnFix"+RatingTaskID[d]%>" onclick="fixScore(this.form, <%="txtMainScale"+RatingTaskID[d]%>)" style="float: center"></font></td>
			</td>
<%			

		}
	}
%>
		</tr>


<%	
	counter = 0;
	int numbering = 1;
	Vector rs2 = adv.getAllRecord_SurveyCompetency(CE_Survey.getSurvey_ID(),logchk.getPKUser());
	
	for(int i=0; i<rs2.size(); i++)
	{
		voCompetency vo = (voCompetency)rs2.elementAt(i);
		int CompID = vo.getPKCompetency();
		String CompetencyName = vo.getCompetencyName();
%>
		<tr>
		<td width="32" bgcolor="#FFFFCC" align="center"><font face="Arial" size="2"><%=numbering%></font></td>
		<td width="370" bgcolor="#FFFFCC"><font face="Arial" size="2">&nbsp;<%=CompetencyName.trim()%></font></td>
<%
		for(int j=0; j<RatingName.length;j++)
		{
			if(RatingName[j] != null)
			{
%>				<td width="529" bgcolor="#FFFFCC" align="center">
				<font face="Arial">
<%
				int compid_ratSet=0;
				int ratingid_ratSet=0;
				float score = 0;
				
				Vector vScores = SVR.getSurveyFixedScore(CE_Survey.getSurvey_ID(),CompID, RatingTaskID[j]);
				System.out.println(vScores.size() + "!!!");
				for(int k=0; k<vScores.size(); k++)
				{
					score = ((Float)vScores.elementAt(k)).floatValue();

					if(score > 0)
					{	
					
			%>			<input type="text" name="<%="txtRatingScale"+RatingTaskID[j]+","+CompID%>" size="4" value="<%=score%>">
			<%		}	
					else
					{ 
			%>			<input type="text" name="<%="txtRatingScale"+RatingTaskID[j]+","+CompID%>" size="4">
		<%			}
				};
%>
<%			}		%>
				</select>
			</font> <font size="2"><b><font face="Arial">&nbsp;</font></td>
<%		}
%>
		</tr>
<%		counter++;
		numbering++;
	}			
%>
	
</table>

<table border="0" width="97%" cellspacing="0" cellpadding="0">
	<tr>
		<td>&nbsp;</td>
		<td width="220">&nbsp;</td>
	</tr>
	<tr>
		<td><input type="button" value="<%=trans.tslt("Close")%>" name="btnClose" onclick ="tutup(this.form,this.form.chkHide)"></td>
		<td width="220">
		<input type="button" value="<%=trans.tslt("Save")%>" name="btnSave" style="float: right" onclick="Save(this.form,this.form.chkHide)"></td>
	</tr>
</table>
</form>
</body>

</html>