<%// Author: Dai Yong in June 2013%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="CP_Classes.vo.*"%>
<%@ page pageEncoding="UTF-8" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Coaching Period</title>

<jsp:useBean id="CoachSlotGroup" class="Coach.CoachSlotGroup"scope="session" />
<jsp:useBean id="CoachSlot" class="Coach.CoachSlot"scope="session" />
<jsp:useBean id="LoginStatus" class="Coach.LoginStatus" scope="session" />
<jsp:useBean id="CoachDateGroup" class="Coach.CoachDateGroup"scope="session" />
<jsp:useBean id="CoachDate" class="Coach.CoachDate"scope="session" />
</head>
<body>
<br>
<div align="center">
	<table align="center">
			
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>&nbsp;</font>
			</b></th>
			<th width="30" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>No</font>
			</b></th>
			<th width="150" bgcolor="navy" bordercolor="#3399FF" align="center"><b>
					<font style='color: white'>Coaching Date</font>
			</b></th>
			

				<%
				int DisplayNo = 1;
				int pkDate=0;
				Vector CoachDates=new Vector();
				int dayGroupPK=Integer.parseInt(request.getParameter("ViewDayGroup"));
				voCoachDateGroup dateGroup=CoachDateGroup.getSelectedDateGroup(dayGroupPK);
				
				%>
				<p align="center">
				<b><font color="#000080" size="2" face="Arial">Schedule Details For <%=dateGroup.getName()%></font></b>
				</p>
				<%
				CoachDates = CoachDateGroup.getSelectedDateGroupDetails(dayGroupPK);
				for (int i = 0; i < CoachDates.size(); i++) {
					voCoachDate voCoachDate = new voCoachDate();
					voCoachDate = (voCoachDate) CoachDates.elementAt(i);

					pkDate = voCoachDate.getPK();
					String date=voCoachDate.getDate();
					//System.out.println("ending time" + endingingTime);
			%>
			<tr onMouseOver="this.bgColor = '#99ccff'"
				onMouseOut="this.bgColor = '#FFFFCC'">
				<td style="border-width: 1px"><font size="2"> <input type="radio" name="selDate" value=<%=pkDate%>></font></td>
				<td align="center"><%=DisplayNo%></td>
				<td align="center"><%=date%></td>
				
			</tr>
			<%
				DisplayNo++;
				}
			%>
		</table>
</div>
</body>
</html>