package CP_Classes;

import java.util.Date;
import java.util.Vector;
import java.sql.*;
import java.io.Serializable;
import java.text.*;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.*;

/**
 * 
 * Change Log
 * ==========
 *
 * Date        By				Method(s)            					      Change(s) 
 * ====================================================================================================================================
 * 25/05/12    Liu Taichen      Email_Survey_Open_Participant_Option(int,     Created method Email_Survey_Open Participant_Option
 *                              String, Vector, int, boolean, int)                                                 
 *
 * 10/07/12    Liu Taichen      Email_With_Individual_Report(Vector,           Created this method to send individual report to targets
 * 								 int, boolean, int, int) 
 *
 * 18/07/12    Liu Taichen      Email_With_Importance_Report(Vector,           Created this method to send importance individual report to targets
 * 								 int, boolean, int, int)
 *
 * 18/07/12    Liu Taichen        Email_With_Importance_Report(Vector,         allow email sending record
 * 								 int, boolean, int, int)
 *
 * 17/08/12    Su Lingtong      checksurveystatus(int SurveyID)                created method to check survey status
   
 * 17/08/12    Su Lingtong      Email_Survey_Open_Participant_Option(int,      Modified method so that it can return more specific error messages
 *                              String, Vector, int, boolean, int)            
 * 
 * 21/08/12    Su Lingtong      Email_Survey_Open_Participant_Option(int,      Modified method  types of message: 1. [count] emails are successfully sent.
 *                              String, Vector, int, boolean, int)  		   2. No targets left to rate    3. No raters under given conditions
 *
 * 24/08/12    Su Lingtong      Email_Survey_Open_Participant_Option(int,      Record failed emails according to the error message
 *                              String, Vector, int, boolean, int) 
 
 * 24/08/12    Su Lingtong      check_FKorgnization();                         Return FKorgnization value of a given survey
 * 
 * 24/08/12    Su Lingtong      Failed_email_report()                          Create Failed_email_report() method to record failed emails with different error messages
 */
public class ReminderCheck implements Serializable
{
	int counterNominateReminder = 0;
	int counterNominateNotify = 0;
	
	Date dt = new Date();
	Date dts = new Date();
	Date dw = new Date();	
	
	Database db = new Database();
	MailHTMLStd Email = new MailHTMLStd();
	EmailTemplate template = new EmailTemplate();
	AssignTarget_Rater assign = new AssignTarget_Rater();
	User UserX = new User();
	Create_Edit_Survey CE_Survey = new Create_Edit_Survey();
	Setting server = new Setting();
	Organization org = new Organization();
	OpenOffice OO =new OpenOffice();
	String UserDetail[] = new String[14];
	int counter=0;
	
	private XMultiComponentFactory xRemoteServiceManager = null;
	
	private XComponent xDoc = null;
	private String storeURL;
	private XSpreadsheet xSpreadsheet = null;
	private boolean Time_Sent=false;	
	
	SimpleDateFormat formatter= new SimpleDateFormat ("dd-MM-yyyy");
	SimpleDateFormat database= new SimpleDateFormat ("MM/dd/yyyy");
	
	GlobalFunc global = new GlobalFunc();
	
	String date = database.format(global.addDay(dw, 0));
	
	String date_after = database.format(global.addDay(dw, -1));
	
	String date_before = database.format(global.addDay(dw, 1));
	
	String five_days_before = database.format(global.addDay(dw, 5));
	
	String three_days_before = database.format(global.addDay(dw, 3));
	
	String content = "";
	
	//public ReminderCheck()
	//{
	//	System.out.println(date_after);	
	//}
	
	/**
	 *****************************************************Start of checking*******************************
	 */
	
/******
 * 
 * Edited by Roger - 12 June 2008
 * Add one more FKOrganization to table
 * 
 *****/
	
	public void check() throws SQLException, Exception
	{
		/**
		 * This will check the tblEmail and send all the records before deleting it
		 */

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		/* TOYOTA SETTING */
		if(server.getCompanySetting() == 3)
		{
			int EmailID =0;
		
			String command = "SELECT * FROM tblEmail";
			Vector v = new Vector();
			
			try
	        {          

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(command);

				while(rs.next())
				{
					votblEmail vo = new votblEmail();
					EmailID = rs.getInt("EmailID");
					String SenderEmail = rs.getString("SenderEmail");
					String ReceiverEmail = rs.getString("ReceiverEmail");
					String Header = rs.getString("Header");
					String Content = rs.getString("Content");
					//Added by Xuehai 23 Jun 2011.
					String Log = rs.getString("Log");
					int orgId = rs.getInt("FKOrganization");
					
					
					/* RIANTO TO BE DELETED */
					//System.out.println("RIANTO EMAILS SENT FOR tblEmail");
			 		//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
			 		/* END RIANTO TO BE DELETED */
			 		vo.setEmailID(EmailID);
			 		vo.setSenderEmail(SenderEmail);
			 		vo.setReceiverEmail(ReceiverEmail);
			 		vo.setHeader(Header);
			 		vo.setContent(Content);
			 		vo.setLog(Log);
			 		vo.setOrgId(orgId);
			 		//Edited By Roger 13 June 2008
			 		
			 		v.add(vo);
					
				}
  
	        }
	        catch(Exception E) 
	        {
	            System.err.println("ReminderCheck.java - check - " + E);
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection


	        }

			for(int i=0; i<v.size(); i++)
			{
				votblEmail vo = (votblEmail)v.elementAt(i);
				
				EmailID = vo.getEmailID();
				String SenderEmail = vo.getSenderEmail();
				String ReceiverEmail = vo.getReceiverEmail();
				String Header = vo.getHeader();
				String Content = vo.getContent();
				//Edited By Roger 13 June 2008
				int orgId = vo.getOrgId();
				
				/* RIANTO TO BE DELETED */
				//System.out.println("RIANTO EMAILS SENT FOR tblEmail");
		 		//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
		 		/* END RIANTO TO BE DELETED */
		 		
				//Edited By Roger 13 June 2008
				Email.sendMail(SenderEmail, ReceiverEmail, Header, Content, orgId);
				template.delFromtblEmail(EmailID);
					
			}
		}
		
		/**
		 * This will do the checking of all the necessary dates.
		 */
		
		String command = "SELECT * FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID ";
		
		String command_con1 = command + "AND a.SurveyID = d.SurveyID AND a.SurveyStatus != 1";
		
		/**
		 * This will Remind admin of new survey opening
		 */
		 
		String command_con2 = command_con1 + " AND  a.DateOpened = '"+ date_before + "'";
					
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con2);

			 if(rs.next())	
	        {
	        	Email_Survey_Open(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
		String command_con3 = command_con1 + " AND  a.DateOpened = '"+ date + "'";
					
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con3);

			if(rs.next())	
	        {
	        	Email_Survey_Open(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
       
		/**
		 * Upon kick off survey email will be sent to the targets and raters.
		 */
		 //Modified calling of method to include passing of 2 additional variable values, int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
		Email_Survey_Open_Participant_Option(0, date, 0, 0, false, 0);
		
		
		/**
		 *	To remind Incomplete Raters regarding their questionnaire FIVE DAYS BEFORE
		 */
		 
		String command_con5 = command + "AND a.SurveyStatus = 1 AND  a.DeadlineSubmission = '"+ five_days_before + "' AND RaterStatus = 0 ORDER BY d.RaterLoginID";
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con5);

			while(rs.next())	
	        {
	        	
	        	Email_Participant_Reminder(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
       
		
		/**
		 *	To remind Incomplete Raters regarding their questionnaire THREE DAYS BEFORE
		 */
		 
		command_con5 = command + "AND a.SurveyStatus = 1 AND  a.DeadlineSubmission = '"+ three_days_before + "' AND RaterStatus = 0 ORDER BY d.RaterLoginID";
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con5);

			while(rs.next())	
	        {
	        	
	        	Email_Participant_Reminder(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

		/**
		 *	To remind Incomplete Raters regarding their questionnaire ONE DAY BEFORE
		 */
		 
		command_con5 = command + "AND a.SurveyStatus = 1 AND  a.DeadlineSubmission = '"+ date_before + "' AND RaterStatus = 0 ORDER BY d.RaterLoginID";
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con5);

			while(rs.next())	
	        {
	        	
	        	Email_Participant_Reminder(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
		/**
		 *	To remind Incomplete Raters regarding their questionnaire ONE THE DAY
		 */
		 
		command_con5 = command + "AND a.SurveyStatus = 1 AND  a.DeadlineSubmission = '"+ date + "' AND RaterStatus = 0 ORDER BY d.RaterLoginID";
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con5);

			while(rs.next())	
	        {
	        	
	        	Email_Participant_Reminder(rs);
	        	setTime_Sent(true);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
				
		/**
		 *	To inform admin of Raters' completion
		 */
		
		int SurveyID=0;
		int var = 0;
		boolean AllCompleted = true;
		String command_con6 = command + "AND a.SurveyStatus = 1 AND  a.DeadlineSubmission = '"+ date + "' ORDER BY a.SurveyID";
				
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con6);
			while(rs.next())	
	        {
	        	SurveyID = rs.getInt("SurveyID");
	        	int RaterStatus = rs.getInt("RaterStatus");
	        	
	        	if(SurveyID == var)
				{
		        	if(RaterStatus != 1)
		        	{
		        		AllCompleted = false;
		        	}
		        }
				else
				{
					if(var != 0 && AllCompleted)
					{
						Email_Participant_Completion(SurveyID);
						setTime_Sent(true);
					}
					AllCompleted = true;
					
					var = SurveyID;
					
					if(RaterStatus != 1)
		        	{
		        		AllCompleted = false;
		        	}
					
					
				}
			
				
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

		/**
		 *	Inform admin of deadline
		 */
		
		String command_con7 = command + " AND  a.DeadlineSubmission = '"+ date_before + "'";
							
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con7);
			
			if(rs.next())	
	        {
	        	Email_Survey_Closed(rs);
			}
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
		setTime_Sent(true);
		
		/**
		 *	Inform admin about incomplete raters after deadline
		 */
		int SurveyIDX=0;
		int varX = 0;
		int counter = 0;
		int RaterLogin = 0;

		int [] RaterID = new int [20];
		String command_con8 = command + " AND  a.DeadlineSubmission = '"+ date + "' AND d.RaterStatus = 0 ";
		command_con8 = command_con8 + "AND RaterLoginID <> 0 AND a.SurveyStatus <> 2 ORDER BY a.SurveyID";
							
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con8);
			
			while(rs.next())	
	        {
	        	SurveyIDX = rs.getInt("SurveyID");
	        	RaterLogin = rs.getInt("RaterLoginID");
				 
	        	if(SurveyIDX == varX)
				{
		        	counter++;
		        	RaterID[counter] = RaterLogin;
		        }
				else
				{
					if(varX != 0)
					{
						Email_Incomplete_Raters(varX, RaterID);
						setTime_Sent(true);
					}

					counter =0;
					RaterID = new int [20];
					varX = SurveyIDX;

					RaterID[counter] = RaterLogin;
				}

			}
			
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
        
		Email_Incomplete_Raters(varX, RaterID);
		setTime_Sent(true);
		
		/**
		 *Send email for Sup to Nominate
		 */
		 String command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_nom = command_nom +"WHERE (a.NominationStartDate = '"+ date + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_nom = command_nom +"WHERE (a.NominationStartDate = '"+ date + "')";
		 }
		 /* END TOYOTA SETTING */
		 
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_nom);
			
			while(rs.next())	
	        {
	         	
	         	SurveyIDX = rs.getInt("SurveyID");
				Sup_Nominate(SurveyIDX,0);
				
				//TOYOTA
				setTime_Sent(true);
				System.out.println("SurveyIDX "+SurveyIDX);
				//END TOYOTA
			
	        }
				
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - check - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
		/**
		 *Send email for Sup to Nominate REMINDER
		 */
		 
		 /* Commented off by Rianto 21-01-05: Doesn't make sense */	
		 //command_nom = command; //+"AND (a.NominationEndDate = '"+ five_days_before + "')";
		 
		 command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ five_days_before + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ five_days_before + "')";
		 }
		 /* END TOYOTA SETTING */
			
		//System.out.println("Check sSQL date 5daysbefore = " + command_nom);
		
		 try
		 {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_nom);
			
			while(rs.next())	
		     {
		     	SurveyIDX = rs.getInt("SurveyID");
				Sup_Nominate(SurveyIDX,1);
				setTime_Sent(true);
			 }
				
		 }
		 catch(Exception E) 
		 {
			 System.err.println("ReminderCheck.java - check - " + E);
		 }
		 finally
		 {
			 ConnectionBean.closeRset(rs); //Close ResultSet
			 ConnectionBean.closeStmt(st); //Close statement
			 ConnectionBean.close(con); //Close connection
		 }
        
         
		 
		
		/**
		 *Send email for Sup to Nominate REMINDER
		 */	 
		 command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ three_days_before + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ three_days_before + "')";
		 }
		 /* END TOYOTA SETTING */
		 //System.out.println("Check sSQL date 3daysbefore= " + command_nom);
		 
		 try
		 {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_nom);
			
			 while(rs.next())	
	         {
	         	SurveyIDX = rs.getInt("SurveyID");
				Sup_Nominate(SurveyIDX,1);
				setTime_Sent(true);
			 }
				
		 }
		 catch(Exception E) 
		 {
			 System.err.println("ReminderCheck.java - check - " + E);
		 }
		 finally
		 {
			 ConnectionBean.closeRset(rs); //Close ResultSet
			 ConnectionBean.closeStmt(st); //Close statement
			 ConnectionBean.close(con); //Close connection
		 }
        
       
		/**
		 *Send email for Sup to Nominate REMINDER
		 */
		 
		 command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ date_before + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ date_before + "')";
		 }
		 /* END TOYOTA SETTING */
		 
		 //System.out.println("Check sSQL date_before = " + command_nom);
		 
		 try
		 {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_nom);
			
			while(rs.next())	
	        {
	         	SurveyIDX = rs.getInt("SurveyID");
				Sup_Nominate(SurveyIDX,1);
				setTime_Sent(true);
	        }
				
		 }
		 catch(Exception E) 
		 {
			 System.err.println("ReminderCheck.java - check - " + E);
		 }
		 finally
		 {
			 ConnectionBean.closeRset(rs); //Close ResultSet
			 ConnectionBean.closeStmt(st); //Close statement
			 ConnectionBean.close(con); //Close connection
		 }
        
		 
		 /**
		 *Send email for Sup to Nominate REMINDER
		 */
		 command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ date + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_nom = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_nom = command_nom +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_nom = command_nom +"WHERE (a.NominationEndDate = '"+ date + "')";
		 }
		 /* END TOYOTA SETTING */
		 
		 //System.out.println("Check sSQL date = " + command_nom);
		 
		 try
		 {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_nom);
			
			while(rs.next())	
	        {
	         	SurveyIDX = rs.getInt("SurveyID");
				Sup_Nominate(SurveyIDX,1);
				setTime_Sent(true);
	        }
				
		 }
		 catch(Exception E) 
		 {
			 System.err.println("ReminderCheck.java - check - " + E);
		 }
		 finally
		 {
			 ConnectionBean.closeRset(rs); //Close ResultSet
			 ConnectionBean.closeStmt(st); //Close statement
			 ConnectionBean.close(con); //Close connection
		 }
		 
		/**
		 *	Inform admin of deadline
		 */
		 String command_con9 = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 command_con9 = command_con9 +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 command_con9 = command_con9 +"WHERE (a.NominationEndDate = '"+ date + "')";
		 
		 /* TOYOTA SETTING */
		 if(server.getCompanySetting() == 3)
		 {
		 	command_con9 = "SELECT * FROM tblSurvey a INNER JOIN tblOrganization b ON a.FKOrganization = ";
		 	command_con9 = command_con9 +"b.PKOrganization INNER JOIN tblJobPosition c ON a.JobPositionID = c.JobPositionID ";
		 	command_con9 = command_con9 +"WHERE (a.NominationEndDate = '"+ date + "')";
		 }
		 /* END TOYOTA SETTING */
		 
					
		 try
		 {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_con9);
			
			if(rs.next())	
	        {
	        	NominationEndDate(rs);
			}
				
		 }
		 catch(Exception E) 
		 {
			 System.err.println("ReminderCheck.java - check - " + E);
		 }
		 finally
		 {
			 ConnectionBean.closeRset(rs); //Close ResultSet
			 ConnectionBean.closeStmt(st); //Close statement
			 ConnectionBean.close(con); //Close connection
		 }
        
		/* STATUS OF EMAILS SENT */
		//Developed by Rianto: 25-Jan-05, Can be improved by putting in one function
		if(counterNominateReminder != 0)
		{	
			System.out.println("Total Nomination Reminder Emails sent\t: " + counterNominateReminder);
			//Edited By Roger 13 June 2008 NTD
			Email.sendMail(server.getAdminEmail(), server.getAdminEmail(), "STATUS OF NOMINATION REMINDER EMAILS SENT", "Total: " + counterNominateReminder + " Email(s) being sent", 1);
		}
		
		if(counterNominateNotify != 0)
		{	
			
			System.out.println("Total Nomination Notification Emails sent\t: " + counterNominateNotify);
			//Edited By Roger 13 June 2008 NTD
			Email.sendMail(server.getAdminEmail(), server.getAdminEmail(), "STATUS OF NOMINATION NOTIFICATION EMAILS SENT", "Total: " + counterNominateNotify + " Email(s) being sent", 1);
		}
		/* END STATUS OF EMAILS SENT */
		
	}
	/**
	 *****************************************************END OF CALLING OF FUCNTION*******************************
	 */
	
	/**
	 *****************************************************START OF FUNCTIONS DECLARATION***************************
	 */
	
	/**
	 * This will Remind admin of new survey opening
	 */
	
	public void Email_Survey_Open(ResultSet rs)throws SQLException, Exception
	{
		String SurveyStatus_str = "";
		int SurveyID = rs.getInt("SurveyID");
		String SurveyName = rs.getString("SurveyName");
		String DateOpened = formatter.format(rs.getDate("DateOpened"));
		int SurveyStatus = rs.getInt("SurveyStatus");
		int OrgID = rs.getInt("FKOrganization");
		int AdminID = rs.getInt("AdminAssigned");
		String OrgName = rs.getString("OrganizationName");
		String JobPosName = rs.getString("JobPosition");
		
		UserDetail = CE_Survey.getUserDetail_ADV(AdminID);
		
		String fullname = UserDetail[0]+" "+UserDetail[1];
		
		if(SurveyStatus == 1)
			SurveyStatus_str = "Open";
		else if(SurveyStatus == 2)
			SurveyStatus_str = "Closed";
		else if(SurveyStatus == 3)
			SurveyStatus_str = "Not Commissioned";
		
		content = template.Survey_Open(fullname,DateOpened,OrgName,JobPosName,SurveyName,SurveyStatus_str);
		
		/* RIANTO TO BE DELETED */
		//System.out.println("RIANTO EMAILS SENT FOR Email_Survey_Open");
		//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
		/* END RIANTO TO BE DELETED */
		
		//Edited By Roger 13 June 2008
		Email.sendMail(server.getAdminEmail(), UserDetail[13], template.Survey_Open_Subject(), content,OrgID);
	
	}
	
	/*
	 * Change(s) :Created method Email_Survey_Open Participant_Option
	 * Reason(s) : To enable sending of email to specific group of raters
	 * Updated By: Liu Taichen
	 * Updated On: 25 May 2012
	 */
	/*
	 * Change(s): Break down original error message "Survey is NOT open, or selected rater does NOT exist 
	 *            or selected rater has NO targets left to rate for specified survey" into 3 specific
	 *            error messages
	 * Reason(s): To specify the error message
	 * Updated By: Su Lingtong
	 * Updated On: 17 Aug 2012 
	 */
	
	/*Change(s): Method Email_Survey_Open Participant_Option() now returns an array of integers.
	 *           fisrt entry is errorFlag and second entry is the count of successfully sent emails
	 *Reason(s): To show the exact count of successfully sent emails
	 *Updated By: Su Lingtong
	 *Updated On: 21 Aug 2012
	 * */
	
	/*
	 * Change(s): Record failed emails according to different types of error
	 * Reason(s): To record failed emails
	 * Updated by: Su Lingtong
	 * Updated On 24 Aug 2012
	 */
	/**
	 * Method called in SendPartEmail.jsp to send remainder or participant email to the specific groups of raters
	 * @param int SurveyID - Specify which survey to reference for targets
	 * @param int SendDate - Specify which the date which the email will be send
	 * @param Vector RaterIDs - Specify the IDs of the raters to send the emails
	 * @param int isReminder - Specify if email to be sent is a reminder email
	 * @param boolean isAttachment - Specify if the email to be sent will include excel questionnaire as attachment
	 * @param int pkUser - Specify the user of the system
	 * @throws SQLException
	 * @throws Exception
	 * @return int[0] - Return 0 if email sent successfully. Return 1 if email template have not been created. Return 2 if user rater does not exist.
	 * @return int[0] - Return 0 if email sent successfully. Return 1 if email template have not been created. Return 2 if user rater does not exist.
	 *                  Return 3 if selected rater has NO targets left to rate for specified survey 
	 *                  Return 4 if survey is NOT open
	 *                  Return 5 if selected rater does NOT exist under certain condition	 *                 
	** @return int[1] - Return count of successfully sent emails
	*/
	public int[] Email_Survey_Open_Participant_Option(int SurveyID,  String SendDate, Vector RaterID, int isReminder, boolean isAttachment, int pkUser) throws SQLException, Exception
	 {
		 int[] answer = new int[2];
		 int errorFlag = -1;		
		 int emailcount = 0;
		 boolean atleastonesuccess = false;
		 
		 if (RaterID.size() == 0) {
			 
			 errorFlag = 5;
		 }
		 else if (check_survey_status(SurveyID) == 2) 
		 {
		 for(int i = 0; i < RaterID.size(); i ++){
				int ID = ((votblAssignment)RaterID.elementAt(i)).getRaterLoginID();
				Failed_email_report(SurveyID, ID, 4);}
		 errorFlag = 4; //Edited by Lingtong; If survey is not open, return errorflag = 4;
		 } else
		    {
		    for(int i = 0; i < RaterID.size(); i ++){
			   int ID = ((votblAssignment)RaterID.elementAt(i)).getRaterLoginID();
			   errorFlag = Email_Survey_Open_Participant_Option(SurveyID, SendDate, ID, isReminder, isAttachment, pkUser);
	           
	           if(errorFlag == 0){
				//Eidted by Su Lingtong: If at least one email is successfully sent out, set atleastonesuccess is true.
				atleastonesuccess = true;
				emailcount = emailcount+1;} //Edited by Lingtong; count how many email are successfully sent out 
		      }
		    }		 
		 if (atleastonesuccess) errorFlag = 0;
		 answer[0] = errorFlag;
		 answer[1] = emailcount;
		 return answer;
	 }
	
	 
	/*
	  * Change(s): Add method checksurveystatus(); return 1 is open, 2 is closed
	  * Reason(s): To check a given survey is open or not so that it can give a more specific error message;
	  * Updated by: Su Lingtong
	  * Updated on: 17/08/2012  
	  */	 
	 public int check_survey_status(int SurveyID){
		 int result = 0;
		 String query = "SELECT SurveyStatus FROM tblSurvey WHERE (SurveyID = "+SurveyID+")";
		 Connection con = null;
		 Statement st = null;
		 ResultSet rs = null;
		 
		 try
	        {     
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(query);
				if (rs.next()){
				result = rs.getInt(1);	
				    if (rs.next()) {
					throw new Exception("More than one record found");
				    } else {
					throw new Exception("No record found");
				    }
				}
	        }
		 catch(Exception E) 
	        {	          
	        }
	        finally
	        {
	        	ConnectionBean.closeRset(rs); //Close ResultSet
	        	ConnectionBean.closeStmt(st); //Close statement
	        	ConnectionBean.close(con); //Close connection
	        }
		return result;
	        
	 }
	
	 /**
	  * Method called in the jsp file to send reminder or participant email to the rater
	  * @param int SurveyID - Specify which survey to reference for targets
	  * @param int SendDate - Specify which the date which the email will be send
	  * @param int RaterID - Specify which rater to reference for sending email
	  * @param int isReminder - Specifiy if email to be sent is a reminder email
	  * @param boolean isAttachment - Specify if the email to be sent will include excel questionnaire as attachment
	  * @param int pkUser - Specify the user of the system
	  * @throws SQLException
	  * @throws Exception
	  * @return int - Return 0 if email sent successfully. Return 1 if email template have not been created. Return 2 if user rater does not exist.
	  * 	Return 3 if survey is NOT open, selected rater does NOT exist or selected rater has NO targets left to rate for specified survey 
	**/
	
	//Added parameters int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
	public int Email_Survey_Open_Participant_Option(int SurveyID, String SendDate, int RaterID, int isReminder, boolean isAttachment, int pkUser) throws SQLException, Exception
	{
		/* If SurveyID = 0  {SendDate='mm/dd/yyyy'; RaterID=0; isReminder(not built yet)} Email being triggered by Timer.jsp
		 * If SurveyID != 0 {SendDate=''; RaterID=RaterID; isReminder=0 or 1} Email being triggered manually
		*/
		
		String command = "";
		int iPKRater = 0;
		
		if(SurveyID == 0)
		{
			// Email being triggered by Timer.jsp
			command = "SELECT DISTINCT d.RaterLoginID FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
			command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
			command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
			command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 AND a.DateOpened = '"+ SendDate + "'";
			command = command +" ORDER BY d.RaterLoginID";
		}
		else
		{
			/*// Email being triggered manually
			command = "SELECT DISTINCT d.RaterLoginID FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
			command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
			command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
			command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 AND (a.SurveyID = " + SurveyID + ") ";
			*///Change By Ping Yang on 18/08/08. Query always give an empty rs.
			
			command = "SELECT DISTINCT d.RaterLoginID FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
			command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
			command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
			command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND RaterLoginID <> 0 AND (a.SurveyID = " + SurveyID + ") ";
					
			if(RaterID != 0)
				command = command + "AND (d.RaterLoginID = " + RaterID + ") ";
			
			command = command + "ORDER BY d.RaterLoginID";
		}
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		/*
		 * Change(s) : Moved try-catch out of if-else statement, added a check and a new error message.
		 * Reason(s) : Wrong error prompt (i.e. "You need to create a participant notification template before sending") was displayed 
		 * 				when user tries to send notification email to a specific rater for a survey that's closed
		 * Updated By: Desmond
		 * Updated On: 11 Aug 2010
		 */
		boolean emailErrors = false;
		try
        {          
			int errorFlag = 0;
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
        
			if(RaterID != 0)
			{	// Send to a specific participant
				
				// Check if survey is open and whether if rater exists and has target(s) left to rate
				if(rs.next()){ 
				
					//Modified calling of method to include passing of 2 additional variable values, int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
					if(isReminder == 1)
		        		errorFlag = Email_Participant_Reminder(RaterID, SurveyID, isAttachment, pkUser);
		        	else
		        		errorFlag = Email_Survey_Open_Participant(RaterID, SurveyID, isAttachment, pkUser);
		        	
					if (errorFlag == 1) {
						return 1;
					}else if(errorFlag==100){
						//Edited by Xuehai 24 Jun 2011. Error code 100 means that some errors happened when sending the email
						emailErrors = true;
					}
					
					setTime_Sent(true);
					
				} else {
					//Failed_email_report(SurveyID, RaterID, 3);
					// Selected rater has NO targets left to rate for this survey
					return 3;
				}
			}
			else
			{	// Send to ALL participants
				
				if(rs.next()){ // Added by Ping Yang on 18/08/08. Check if rater exists.
					do	
			        {
						iPKRater = rs.getInt("RaterLoginID");	
						//Modified calling of method to include passing of 2 additional variable values, int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
			        	if(isReminder == 1)
			        		errorFlag = Email_Participant_Reminder(iPKRater, SurveyID, isAttachment, pkUser);
			        	else
			        		errorFlag = Email_Survey_Open_Participant(iPKRater, SurveyID, isAttachment, pkUser);
			        		        	
			        	if (errorFlag == 1) {
			        		return 1;
			        	}else if(errorFlag==100){
							//Edited by Xuehai 24 Jun 2011. Error code 100 means that some errors happened when sending the email
							emailErrors = true;
						}
			        	
			        	setTime_Sent(true);
					}while(rs.next());
				}else{
					return 2;
				}
			}
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Survey_Open_Participant_Option - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        //Edited by Xuehai 24 Jun 2011. Error code 100 means some errors happened when sending the emails.
		if(emailErrors){
			System.out.println("ReminderCheck.java - Email_Survey_Open_Participant_Option - Emails sent with error happened.");
			return 100;
		}
		return 0;
	}
	
	
	
	/*
	  * Change(s): Add method check_FKorgnization(); return FKorganization value of a survey
	  * Updated by: Su Lingtong
	  * Updated on: 17/08/2012  
	  */	 
	public int check_FKorgnization (int SurveyID){
		
		int FKorgnization = 0;
		
        String query   = "SELECT * FROM tblSurvey WHERE (SurveyID = "+SurveyID+")";
		
		ResultSet rs = null;
		Connection con1 = null;
		Statement st1 = null;
		
		try
		{
			con1=ConnectionBean.getConnection();
			st1=con1.createStatement();
		    rs = st1.executeQuery(query);
			while (rs.next())
				FKorgnization = rs.getInt("FKOrganization");
				
		}
		catch(Exception E)
		{           
		}
		finally
        {
			ConnectionBean.closeStmt(st1); //Close statement
			ConnectionBean.close(con1); //Close connection
        }

		return FKorgnization;
	}
	
	
	/*Change(s): Create Failed_email_report() method to record failed emails with different error messages
	 *Reason(s): To keep record of failed emails
	 *Updated by: Su Lingtong
	 *Updated On: 23 Aug 2012
	 */
	
	public void Failed_email_report(int SurveyId, int RaterId, int errorFlag) throws SQLException, Exception 
	{	
		
		String SenderEmail = null;
		String ReceiverEmail = " ";
		String Header = " ";
		String Content = " ";
		String Log = null;
		int orgId = 0;

		
		if (errorFlag == 3) Log = "Rater has no targets left to rate!";
		if (errorFlag == 4) Log = "Selected Survey is NOT opened.";
		if (errorFlag == 5) {
			                  ReceiverEmail = null;
			                  Log = "There is no rater under this condition.";
			                  orgId = check_FKorgnization(SurveyId);
		                    }
		
		String[] RaterDetails = new String[14];
		if (errorFlag !=5) {RaterDetails = CE_Survey.getUserDetail_ADV(RaterId);
		                    ReceiverEmail = RaterDetails[13];}		
		
		
		SenderEmail = server.getAdminEmail();		
		orgId = check_FKorgnization(SurveyId);

		String command ="INSERT INTO tblEmail(SenderEmail, ReceiverEmail, Header, Content, Log, FKOrganization) VALUES('"+SenderEmail+"','"+ReceiverEmail+"','"+Header+"','"+Content+"', '"+ Log +"', "+orgId+")";
		Connection con = null;
		Statement st = null;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			st.executeUpdate(command);

		}
		catch(Exception E)
		{
            System.err.println("EmailTemplate.java - addTotblEmail - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
	}	
	
	/**
	 * Send 2 different sets of email. One to SELF and one more to Raters.
	 * @param SurveyID
	 * @param SendDate
	 * @param RaterID
	 * @param WithRater	0 = No, 1 = Yes
	 * @param isReminder
	 * @throws SQLException
	 * @throws Exception
	 */
	public void SendParticipantEmailSelfAndRater(int SurveyID, String SendDate, int RaterID, int WithRater, int isReminder) throws SQLException, Exception
	{
		String command = "";
		
		//SELF
		command = "SELECT * FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 AND d.RTRelation = 2 AND (a.SurveyID = " + SurveyID + ") ";
		
		if(RaterID != 0)
			command = command + "AND (d.RaterLoginID = " + RaterID + ") ";
		
		command = command + "ORDER BY d.RaterLoginID";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
			
	        while(rs.next())	
	        {
	        	if(isReminder == 1)
	        		Email_Participant_Reminder(rs);
	        	else
	        		Email_Survey_Open_Self(rs);
	        	setTime_Sent(true);
			}
       
        }
        catch(Exception E) 
        {
            System.err.println("RemiderCheck.java - SendParticipantEmailSelfAndRater - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

        if(WithRater == 1)
        {
        	//OTHER RATERS
    		command = "SELECT * FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
    		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
    		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
    		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 AND d.RTRelation <> 2 AND (a.SurveyID = " + SurveyID + ") ";
    		
    		try
            {          

    			con=ConnectionBean.getConnection();
    			st=con.createStatement();
    			rs=st.executeQuery(command);
    			

                while(rs.next())	
                {
                	if(isReminder == 1)
                		Email_Participant_Reminder(rs);
                	else
                		Email_Survey_Open_Participant(rs);
                	setTime_Sent(true);
        		}
           
            }
            catch(Exception E) 
            {
                System.err.println("RemiderCheck.java - SendParticipantEmailSelfAndRater - " + E);
            }
            finally
            {
            	ConnectionBean.closeRset(rs); //Close ResultSet
            	ConnectionBean.closeStmt(st); //Close statement
            	ConnectionBean.close(con); //Close connection


            }
            
        }
        
	}
	
	/**
	 * Send 2 sets of different emails. One to SELF and one to Raters.
	 * @param rs
	 * @throws SQLException
	 * @throws Exception
	 */
	public void Email_Survey_Open_Self(ResultSet rs)throws SQLException, Exception
	{
		int var = 0;
		String fullname="";
		int [] Targets = new int[20];
		String [] RaterDetail = new String[14];
		
		String Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
		int AssID = rs.getInt("AssignmentID");
		int RaterID = rs.getInt("RaterLoginID");
		int TargetID = rs.getInt("TargetLoginID");
		int SurvID = rs.getInt("SurveyID");
		//Edited By Roger 13 June 2008
		int orgId = rs.getInt("PKOrganization");
		
		if(RaterID == var)
		{
			Targets[counter] = TargetID;
			counter++;
		}
		else
		{
			var = RaterID;
			RaterDetail = CE_Survey.getUserDetail_ADV(RaterID);
			fullname = RaterDetail[0]+" "+RaterDetail[1];
			
			Targets[counter] = TargetID;
			counter = 0;
		}

		// Need to fix (Maruli)
		String title = template.Survey_Open_Self_Subject(Deadline);
		//content = template.fillPartTemplate(4, SurvID, fullname, Deadline,AssID);
		
		Email.sendMail(server.getAdminEmail(), RaterDetail[13], title , content, orgId);
	}
	
	/**
	 * Send email to participants
	 * @param rs
	 * @throws SQLException
	 * @throws Exception
	 */
	public void Email_Survey_Open_Participant(ResultSet rs)throws SQLException, Exception
	{
		int var = 0;
		String fullname="";
		int [] TargetID = new int[20];
		int [] AssID = new int[20];
		
		String [] RaterDetail = new String[14];
		
		//String Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
		//int AssID = rs.getInt("AssignmentID");
		//int RaterID = rs.getInt("RaterLoginID");
		//int PKTarget = rs.getInt("TargetLoginID");
		//int SurvID = rs.getInt("SurveyID");
		String Deadline = "";
		int RaterID = 0;
		int PKTarget = 0;
		int SurvID = 0;
		int orgId = 0;
		
		while(rs.next())
		{
			Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
			RaterID = rs.getInt("RaterLoginID");
			PKTarget = rs.getInt("TargetLoginID");	// Maybe no use at all, can remove? (Maruli)
			SurvID = rs.getInt("SurveyID");
			//Edited By Roger 13 June 2008
			orgId = rs.getInt("PKOrganization");
			
			TargetID[counter] = rs.getInt("TargetLoginID");
			AssID[counter] = rs.getInt("AssignmentID");
			counter++;
		}
		
		if(RaterID == var)
		{
			TargetID[counter] = PKTarget;
			counter++;
		}
		else
		{
			var = RaterID;
			RaterDetail = CE_Survey.getUserDetail_ADV(RaterID);
			fullname = RaterDetail[0]+" "+RaterDetail[1];
			
			TargetID[counter] = PKTarget;
			counter = 0;
		}
/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		//String title = template.Survey_Open_Participant_Subject(Deadline);
		String title = org.getEmailSubject_SurvID(SurvID, 2);
		content = template.fillPartTemplate(2, SurvID, fullname, Deadline, AssID);
		//Edited By Roger 13 June 2008
		Email.sendMail(server.getAdminEmail(), RaterDetail[13], title , content, orgId);
	}
	
 	/**
	  * To prepare participant email contents and send to rater email address
	  * @param int iRaterID - Specify which rater to reference for sending email
	  * @param int iSurveyID - Specify which survey to reference for targets
	  * @param boolean isAttachment - Specify if the email to be sent will include excel questionnaire as attachment
	  * @param int pkUser - Specify the user of the system
	  * @throws SQLException
	  * @throws Exception
	  * @author Maruli
	  * @return int - Return 0 if email sent successfully. Return 1 if email template have not been created.
	**/
	//Added parameters int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
	public int Email_Survey_Open_Participant(int iRaterID, int iSurveyID, boolean isAttachment, int pkUser)throws SQLException, Exception
	{
		String [] RaterDetail = new String[14];
		String Deadline = "";
		String sPosition = "";
		String fullname="";
		int SurvID = 0;
		int iCounter = 0;
		int iTemp = 0;
		int orgId = 0;
		
		// Count total number of Targets under this Rater
		String command = "SELECT COUNT(d.TargetLoginID) AS TotalTarget ";
		command = command + "FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 ";
		command = command + "AND (a.SurveyID = " + iSurveyID + ") AND (d.RaterLoginID = " + iRaterID + ") ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);


			if(rs.next())
				iTemp = rs.getInt("TotalTarget");
       
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Survey_Open_Participant - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		int [] AssID = new int [iTemp];
		
		// Get all Targets under this Rater
		command = "SELECT d.SurveyID, d.AssignmentID, d.RaterLoginID, d.TargetLoginID, a.DeadLineSubmission, a.SurveyName, c.JobPosition, b.PKOrganization ";
		command = command + "FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 ";
		command = command + "AND (a.SurveyID = " + iSurveyID + ") AND (d.RaterLoginID = " + iRaterID + ") ";
		
		RaterDetail = CE_Survey.getUserDetail_ADV(iRaterID);
		fullname = RaterDetail[0]+" "+RaterDetail[1];
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

			while(rs.next())
			{
				AssID[iCounter] = rs.getInt("AssignmentID");
				
				SurvID = rs.getInt("SurveyID");
				sPosition = rs.getString("JobPosition");
				Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
				//Edited By Roger 13 June 2008
				orgId = rs.getInt("PKOrganization");
				iCounter++;
			}
       
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Survey_Open_Participant - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
        
		/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		
		//String title = template.Survey_Open_Participant_Subject(Deadline);
        String title = org.getEmailSubject_SurvID(SurvID, 2);
		if(server.getCompanySetting() == 3)
			title = template.Survey_Open_Participant_Subject(Deadline, sPosition);
		
		content = template.fillPartTemplate(2, SurvID, iRaterID, fullname, Deadline, AssID);
		
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			// Added by Ping Yang to check for String "null"
			return 1; //return 1 - email template empty
		}
		
		boolean emailSent=true;
		//Added If condition to determine if user chose to include excel questionnaires as attachment, Sebastian 27 July 2010
		if (isAttachment) //user choose to include Excel Questionnaire as Attachment
		{
			// Commented away codes for sending out invitation email with zip archive attachment, Desmond 10 August 2010
			/*
			String zipOutputPath = "";
			String zipFilename = "";
			
			QuestionnaireReport QR = new QuestionnaireReport();
			zipOutputPath = QR.generateQuestionnairesByRaterZipped(SurvID, iRaterID, pkUser);
			
			zipFilename = zipOutputPath.substring(zipOutputPath.indexOf("Questionnaires"));
			
			Email.sendMail_with_Attachment(server.getAdminEmail(), RaterDetail[13], title, content, zipFilename, orgId);
			*/
			
			// Added codes for sending out invitation email with multiple questionnaires attached, in the form of multiple Excel files, Desmond 10 August 2010
			QuestionnaireReport QR = new QuestionnaireReport();
			Vector files = QR.generateQuestionnairesByRater(SurvID, iRaterID, pkUser);
			
			emailSent=Email.sendMail_with_MultiAttachment(server.getAdminEmail(), RaterDetail[13], title, content, files, orgId);
		}
		else
		{
			//Edited By Roger 13 June 2008
			emailSent=Email.sendMail(server.getAdminEmail(), RaterDetail[13], title , content, orgId);
		}
		
		if(!emailSent){
			System.out.println("ReminderCheck - Email_Survey_Open_Participant - Email sent failed.");
			return 100;
		}
		return 0;
	}
	
 	/**
	  * To prepare participant email contents and send to target email address
	  * @param Vector Targets - target IDs
	  * @param int iSurveyID - Specify which survey to reference for targets
	  * @param boolean isAttachment - Specify if the email to be sent will include excel questionnaire as attachment
	  * @param int pkUser - Specify the user of the system
	  * @throws SQLException
	  * @throws Exception
	  * @author Liu Taichen
	  * @return int - Return 0 if email sent successfully. Return 1 if email template have not been created.
	**/
	//Added parameters int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
	public int Email_With_Individual_Report(Vector Targets, int iSurveyID, int fileFormat, Vector options, int pkUser)throws SQLException, Exception
	{
		String [] TargetDetail = new String[14];
		String Deadline = "";
		String sPosition = "";

		int orgId = 0;
		String[] option = new String[5];
		for(int i =0;  i<5 ; i++){
			option[i] = (String)options.elementAt(i);
		}
		
		for(Object itarget:Targets){
		votblAssignment target = (votblAssignment)itarget;
		int targetID = target.getTargetLoginID();
		TargetDetail = UserX.getUserDetail(targetID, 0);
		/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		
		//String title = template.Survey_Open_Participant_Subject(Deadline);
        String title = org.getEmailSubject_SurvID(iSurveyID, 4);
        System.out.println("title = " + title);
        //title = "hello";
		if(server.getCompanySetting() == 3)
			title = template.Survey_Open_Participant_Subject(Deadline, sPosition);
		
		content = template.fillEmailIndividualReport(4, iSurveyID, targetID, Deadline);
		
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			// Added by Ping Yang to check for String "null"
			return 1; //return 1 - email template empty
		}
		
		boolean emailSent=true;
		Date timeStamp = new java.util.Date();			
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		timeStamp = new java.util.Date();
		dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		temp  =  dFormat.format(timeStamp);
		
		
			Vector files = new Vector();
			String fileName = "Individual_Report_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";
			String[] filenames = new String[2];
			
			int type = 2;
			// Added codes for sending out invitation email with multiple questionnaires attached, in the form of multiple Excel files, Desmond 10 August 2010
			IndividualReport ir = new IndividualReport();
			ir.setFormat(fileFormat);
			ir.Report(iSurveyID, targetID, pkUser, fileName, type, option[0], option[1], option[2],option[3], 0 ,"Individual Report Template_Cluster.xls" );
			if(fileFormat != 0){
				int name = fileName.indexOf(".");
			
			    fileName = fileName.substring(0,name + 1) + "pdf";
			}
			filenames[0] = fileName;
			filenames[1] = fileName;
			files.add(filenames);
			
			if(option[4].equals("on")){
				String[] guideName = new String[2];
				guideName[0] = "Development Guide.xls";
				guideName[1] = "Development Guide.xls";
				DevelopmentGuide DG = new DevelopmentGuide();
				DG.SelTarget(iSurveyID, targetID, pkUser, guideName[0]);
				files.add(guideName);
				
			}
			emailSent=Email.sendMail_with_MultiAttachment(server.getAdminEmail(), TargetDetail[13], title, content, files, orgId);
		
		
		
		if(!emailSent){
			System.out.println("ReminderCheck - Email_Survey_Open_Participant - Email sent failed.");
			return 100;
		}
		}
		return 0;
	}
	
	/**
	  * To prepare participant email contents and send to target email address
	  * @param Vector Targets - target IDs
	  * @param int iSurveyID - Specify which survey to reference for targets
	  * @param int iImpSurveyID - Specify the importance survey to be used
	  * @param int pkUser - Specify the user of the system
	  * @throws SQLException
	  * @throws Exception
	  * @author Liu Taichen
	  * @return int - Return 0 if email sent successfully. Return 1 if email template have not been created.
	**/
	//Added parameters int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
	public int Email_With_Importance_Report(Vector Targets, int iSurveyID, int iImpSurveyID, Vector options, int pkUser)throws SQLException, Exception
	{
		
		initializeEmailRecord();
		int row = 1;
		String [] TargetDetail = new String[14];
		String Deadline = "";
		String sPosition = "";
		int orgId = 0;
		String[] option = new String[options.size()];
		for(int i =0;  i<options.size() ; i++){
			option[i] = (String)options.elementAt(i);
		}
		
		for(Object itarget:Targets){
			
			
			Date timeStamp = new java.util.Date();			
			SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
			SimpleDateFormat eFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
			String temp  =  dFormat.format(timeStamp);
			String etemp  =  eFormat.format(timeStamp);

			votblAssignment target = (votblAssignment)itarget;
			int targetID = target.getTargetLoginID();
			TargetDetail = UserX.getUserDetail(targetID, 0);
		/*
* Change : Get the subject from database
* Reason : Previously hardcode in java file
* Add by : Johanes
* Add on : 9/11/2009
*/	
		
		//String title = template.Survey_Open_Participant_Subject(Deadline);
       String title = org.getEmailSubject_SurvID(iSurveyID, 4);
       System.out.println("title = " + title);
       //title = "hello";
		if(server.getCompanySetting() == 3)
			title = template.Survey_Open_Participant_Subject(Deadline, sPosition);
		
		content = template.fillEmailIndividualReport(4, iSurveyID, targetID, Deadline);
		
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			// Added by Ping Yang to check for String "null"
			return 1; //return 1 - email template empty
		}
		
		boolean emailSent=false;
		
		
			Vector files = new Vector();
			
			
           
			//if not only sending DG
			if(option[6].equals("")){
			String fileName = "Importance_Report_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";

			String templateFile = option[5];

			String[] filenames = new String[2];
			
			int type = 2;
			// Added codes for sending out invitation email with multiple questionnaires attached, in the form of multiple Excel files, Desmond 10 August 2010
			ImptIndividualReport ir = new ImptIndividualReport();

			if(!option[3].equals("")){
				ir.setFormat(1);
				ir.Report(iSurveyID, iImpSurveyID, targetID, pkUser, fileName, type, option[0], option[1], option[2], 0 ,templateFile );
				int name = fileName.indexOf(".");
			
			    fileName = fileName.substring(0,name + 1) + "pdf";
			}else{
				ir.Report(iSurveyID, iImpSurveyID, targetID, pkUser, fileName, type, option[0], option[1], option[2], 0 ,templateFile );
			}
			filenames[0] = fileName;
			filenames[1] = fileName;
			files.add(filenames);
			
			if(option[4].equals("on")){
				String[] guideName = new String[2];
				guideName[0] = "Development_Guide_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";
				guideName[1] = "Development_Guide_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";
				DevelopmentGuide DG = new DevelopmentGuide();
				if(DG.SelTarget(iSurveyID, targetID, pkUser, guideName[0]))
				{
					System.out.println("development guide is included");
				files.add(guideName);
				}
				else
				{
					System.out.println("Development Guide not included");
				}
			}				
		}
			//If only sending dg
			else{
				String[] guideName = new String[2];
				guideName[0] = "Development_Guide_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";
				guideName[1] = "Development_Guide_of_" + TargetDetail[0]+"_"+ TargetDetail[1]+"(" + temp + ").xls";
				DevelopmentGuide DG = new DevelopmentGuide();
				if(DG.SelTarget(iSurveyID, targetID, pkUser, guideName[0])){
					System.out.println("development guide is included");
				files.add(guideName);}
				else{
					System.out.println("Development Guide not included");
				}
				
			}
			//if no file is included, do not send
			if(files.size() != 0){
			emailSent=Email.sendMail_with_MultiAttachment(server.getAdminEmail(), TargetDetail[13], title, content, files, orgId);
			}
			String attachments = "";
			if(files.size() ==0){
				attachments ="No file attached";
			}
			for(Object o : files){
				String[] names = (String[])o;
				attachments = attachments +" " + names[0];
			}
			
			OO.insertString(xSpreadsheet,TargetDetail[0]+"_"+ TargetDetail[1], row, 0);
			OO.insertString(xSpreadsheet, etemp, row, 2);
			OO.insertString(xSpreadsheet,TargetDetail[13], row, 3);			
		    OO.insertString(xSpreadsheet, title, row, 4);
		    OO.insertString(xSpreadsheet, attachments, row, 5);
		if(!emailSent){
			
			OO.insertString(xSpreadsheet,"Unsuccessful", row, 1);
			System.out.println("ReminderCheck - Email_With_Importance_Report - Email sent failed.");
			row ++;
		}
		else{
			OO.insertString(xSpreadsheet,"Successful", row, 1);
			row ++;
		}
		
		}
		OO.storeDocComponent(xRemoteServiceManager, xDoc, getStoreURL());
		OO.closeDoc(xDoc);
		return 0;
	}
	/**
	 * Reminder email will be sent to the raters.
	 * @param rs
	 * @throws SQLException
	 * @throws Exception
	 */
	public void Email_Participant_Reminder(ResultSet rs)throws SQLException, Exception
	{
		int var = 0;
		String fullname="";
		String [] RaterDetail = new String[14];
		int [] TargetID = new int[20];
		int [] AssID = new int[20];
		
		//String Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
		//int AssID = rs.getInt("AssignmentID");
		//int RaterID = rs.getInt("RaterLoginID");
		//int PKTarget = rs.getInt("TargetLoginID");
		//int SurvID = rs.getInt("SurveyID");
		
		String Deadline = "";
		int RaterID = 0;
		int PKTarget = 0;
		int SurvID = 0;
		int orgId = 0;
		
		while(rs.next())
		{
			Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
			RaterID = rs.getInt("RaterLoginID");
			PKTarget = rs.getInt("TargetLoginID");	// Maybe no use at all, can remove? (Maruli)
			SurvID = rs.getInt("SurveyID");
			orgId = rs.getInt("PKOrganization");
			
			TargetID[counter] = rs.getInt("TargetLoginID");
			AssID[counter] = rs.getInt("AssignmentID");
			counter++;
		}
		
		if(RaterID == var)
		{
			TargetID[counter] = PKTarget;
			counter++;
		}
		else
		{
			var = RaterID;
			RaterDetail = CE_Survey.getUserDetail_ADV(RaterID);
			fullname = RaterDetail[0]+" "+RaterDetail[1];
			
			TargetID[counter] = PKTarget;
			counter = 0;
		}
/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		//String title = template.Participant_Reminder_Subject();
		String title = org.getEmailSubject_SurvID(SurvID, 3);
		content = template.fillPartTemplate(3, SurvID, fullname, Deadline, AssID);
		
		if (RaterID != 0)
			Email.sendMail(server.getAdminEmail(), RaterDetail[13], title, content, orgId);
	}
	
	 /**
	  * To prepare participant reminder email contents and send to rater email
	  * 
	  * @param int iRaterID - Specify which rater to reference for sending email
	  * @param int iSurveyID - Specify which survey to reference for targets
	  * @param boolean isAttachment - Specify if the email to be sent will include excel questionnaire as attachment
	  * @param int pkUser - Specify the user of the system
	  * @throws SQLException
	  * @throws Exception
	  * @return int - Return 0 if email sent successfully. Return 1 if email template have not been created.
	**/
	//Added parameters int pkUser and boolean isAttachment to facilitate in the functionality of including Excel Questionnaire as Attachment, Sebastian 27 July 2010
	public int Email_Participant_Reminder(int iRaterID, int iSurveyID, boolean isAttachment, int pkUser)throws SQLException, Exception
	{
		String [] RaterDetail = new String[14];
		String Deadline = "";
		String sPosition = "";
		String fullname="";
		int SurvID = 0;
		int iCounter = 0;
		int iTemp = 0;
		int orgId = 0;
		
		// Count total number of Targets under this Rater
		String command = "SELECT COUNT(d.TargetLoginID) AS TotalTarget ";
		command = command + "FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 ";
		command = command + "AND (a.SurveyID = " + iSurveyID + ") AND (d.RaterLoginID = " + iRaterID + ") ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);

			if(rs.next())
				iTemp = rs.getInt("TotalTarget");

        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Participant_Reminder - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		int [] AssID = new int [iTemp];
		
		// Get all Targets under this Rater
		command = "SELECT d.SurveyID, d.AssignmentID, d.RaterLoginID, d.TargetLoginID, a.DeadLineSubmission, c.JobPosition, b.PKOrganization  ";
		command = command + "FROM tblSurvey a, tblOrganization b, tblJobPosition c, tblAssignment d, [User] e ";
		command = command + "WHERE a.FKOrganization = b.PKOrganization AND a.JobPositionID = c.JobPositionID ";
		command = command + "AND a.SurveyID = d.SurveyID AND d.RaterLoginID = e.PKUser ";
		command = command + "AND a.SurveyStatus = 1 AND d.RaterStatus = 0 AND TargetLoginID <> 0 ";
		command = command + "AND (a.SurveyID = " + iSurveyID + ") AND (d.RaterLoginID = " + iRaterID + ") ";
		
	
		RaterDetail = CE_Survey.getUserDetail_ADV(iRaterID);
		fullname = RaterDetail[0]+" "+RaterDetail[1];
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
			 
			while(rs.next())
			{
				AssID[iCounter] = rs.getInt("AssignmentID");
				
				SurvID = rs.getInt("SurveyID");
				sPosition = rs.getString("JobPosition");
				//Edited By Roger 13 June 2008
				orgId = rs.getInt("PKOrganization");
				Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
				iCounter++;
			}

        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Participant_Reminder - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
       
/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		//String title = template.Participant_Reminder_Subject();
		String title = org.getEmailSubject_SurvID(SurvID, 3);
		if(server.getCompanySetting() == 3)
			title = template.Participant_Reminder_Subject(sPosition);
			
		content = template.fillPartTemplate(3, SurvID, iRaterID, fullname, Deadline, AssID);
		
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			// Added by Ping Yang to check for String "null"
			return 1; //return 1 - email template empty
		}
		
		boolean emailSent = true;
		//Added If condition to determine if user chose to include excel questionnaires as attachment, Sebastian 27 July 2010
		if (isAttachment) //user choose to include Excel Questionnaire as Attachment
		{
			// Commented away codes for sending out reminder email with zip archive attachment, Desmond 10 August 2010
			/*
			String zipOutputPath = "";
			String zipFilename = "";
			
			QuestionnaireReport QR = new QuestionnaireReport();
			zipOutputPath = QR.generateQuestionnairesByRaterZipped(SurvID, iRaterID, pkUser);
			
			zipFilename = zipOutputPath.substring(zipOutputPath.indexOf("Questionnaires"));
			
			Email.sendMail_with_Attachment(server.getAdminEmail(), RaterDetail[13], title, content, zipFilename, orgId);
			*/
			
			// Added codes for sending out reminder email with multiple questionnaires attached, in the form of multiple Excel files, Desmond 10 August 2010
			QuestionnaireReport QR = new QuestionnaireReport();
			Vector files = QR.generateQuestionnairesByRater(SurvID, iRaterID, pkUser);
			
			emailSent = Email.sendMail_with_MultiAttachment(server.getAdminEmail(), RaterDetail[13], title, content, files, orgId);
		}
		else
		{
			// Allianz requested not to send reminder out to these 2 persons
			if(RaterDetail[13] != "jens.reisch@allianz.co.id" || RaterDetail[13] != "chris.james@allianz.com.my")
				//Edited By Roger 13 June 2008
				emailSent = Email.sendMail(server.getAdminEmail(), RaterDetail[13], title, content, orgId);
		}
		if(!emailSent){
			System.out.println("ReminderCheck - Email_Participant_Reminder - Email sent failed.");
			return 100;
		}
		return 0;
	}
	
	/**
	 * Upon completion of survey email will be sent to admin.
	 */
	
	public void Email_Participant_Completion(int SurveyID)throws SQLException, Exception
	{
		String fullname="";
		String command_str = " SELECT * FROM tblSurvey WHERE SurveyID = " + SurveyID+" AND  DeadlineSubmission < '"+ date + "'";
		
		String [] AdminDetail = new String[14];
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_str);

			if(rs.next())
			{
				String SurveyName = rs.getString("SurveyName");
				String DateOpened = formatter.format(rs.getDate("DateOpened"));
				String Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
				int AdminAssigned = rs.getInt("AdminAssigned");
				//Edited By Roger 13 June 2008
				int orgId = rs.getInt("FKOrganization");
				
				AdminDetail = CE_Survey.getUserDetail_ADV(AdminAssigned);
				fullname = AdminDetail[0]+" "+AdminDetail[1];
				
				String title = template.Participant_Completion_Subject();
				content = template.Participant_Completion(fullname,SurveyName,DateOpened,Deadline);
				
				/* RIANTO TO BE DELETED */
				//System.out.println("RIANTO EMAILS SENT FOR Email_Participant_Completion");
			 	//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
			 	/* END RIANTO TO BE DELETED */
				
				//Edited By Roger 13 June 2008
				Email.sendMail(server.getAdminEmail(), AdminDetail[13], title, content, orgId);
			
			}
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Participant_Completion - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
       
	}
	
	/**
	 *	Send email to remind admin to close the survey
	 */
	
	public void Email_Survey_Closed(ResultSet rs)throws SQLException, Exception
	{
		String SurveyStatus_str = "";
		int SurveyID = rs.getInt("SurveyID");
		String SurveyName = rs.getString("SurveyName");
		String DateOpened = formatter.format(rs.getDate("DateOpened"));
		int SurveyStatus = rs.getInt("SurveyStatus");
		int OrgID = rs.getInt("FKOrganization");
		int AdminID = rs.getInt("AdminAssigned");
		String OrgName = rs.getString("OrganizationName");
		String JobPosName = rs.getString("JobPosition");
		
		UserDetail = CE_Survey.getUserDetail_ADV(AdminID);
		
		String fullname = UserDetail[0]+" "+UserDetail[1];
		
		if(SurveyStatus == 1)
			SurveyStatus_str = "Open";
		else if(SurveyStatus == 2)
			SurveyStatus_str = "Closed";
		else if(SurveyStatus == 3)
			SurveyStatus_str = "Not Commissioned";
		
		content = template.Survey_Closed(fullname,DateOpened,OrgName,JobPosName,SurveyName,SurveyStatus_str);
		
						/* RIANTO TO BE DELETED */
				//System.out.println("RIANTO EMAILS SENT FOR Email_Survey_Closed");
		 		//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
		 		/* END RIANTO TO BE DELETED */
		
		//Edited By Roger 13 June 2008	
		Email.sendMail(server.getAdminEmail(), UserDetail[13], template.Survey_Closed_Subject(), content, OrgID);
	
	}
	
	/**
	 *Send email to remind admin about incomplete raters
	 */
	
	public void Email_Incomplete_Raters(int SurveyID, int [] Raters)throws SQLException, Exception
	{
		String admin_name="";
		
		String command_str = " SELECT * FROM tblSurvey WHERE SurveyID = " + SurveyID;
	
		String [] AdminDetail = new String[14];
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_str);

			if(rs.next())
			{
				String SurveyName = rs.getString("SurveyName");
				String DateOpened = formatter.format(rs.getDate("DateOpened"));
				String Deadline = formatter.format(rs.getDate("DeadlineSubmission"));
				int AdminAssigned = rs.getInt("AdminAssigned");
				//Edited By Roger 13 June 2008
				int orgId = rs.getInt("FKOrganization");
			
				AdminDetail = CE_Survey.getUserDetail_ADV(AdminAssigned);
				admin_name = AdminDetail[0]+" "+AdminDetail[1];
				
				String title = template.Incomplete_Raters_Subject();
				content = template.Incomplete_Raters(admin_name,SurveyName,DateOpened,Deadline, Raters);
				
				/* RIANTO TO BE DELETED */
				//System.out.println("RIANTO EMAILS SENT FOR Email_Incomplete_Raters");
			 	//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
			 	/* END RIANTO TO BE DELETED */
				//Edited By Roger 13 June 2008
				Email.sendMail(server.getAdminEmail(), AdminDetail[13], title, content, orgId);
			}

        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Email_Incomplete_Raters - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

	}
	
	//Add by Xuehai 19 May 2011
	//Sending Nomination Mails needs an attachment.
	/**
	 *	Send email for Sup to Nominate
	 *	
	 *	isReminder = 1 => send reminder email
	 *	errorFlag = 1 => template is empty
	 *
	 *@param surveyID
	 *@param isReminder
	 *@param fileName Which is the attachment of the email. null if no attachment.
	 *@return errorFlag
	 */
	public int Sup_Nominate(int SurveyID, int isReminder, String fileName) throws SQLException, Exception
	{
		//Edited By Roger 13 June 2008
		//Get Org ID From SurveyID
		int orgId = 0;
		String sql = "SELECT FKOrganization FROM tblSurvey WHERE SurveyID=" + SurveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(sql);
		
		if (rs.next()) {
			orgId = rs.getInt("FKOrganization");
		}
		
    	ConnectionBean.closeRset(rs); //Close ResultSet
    	ConnectionBean.closeStmt(st); //Close statement
    	ConnectionBean.close(con); //Close connection
		
		String NominationClosedDate ="";
		
		String [] supDetail = new String[14];
						
		//String command_str = "SELECT DISTINCT User2, NominationEndDate FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = ";
		//command_str = command_str +"tblSurvey.SurveyID INNER JOIN tblUserRelation ON tblAssignment.TargetLoginID = ";
		//command_str = command_str +"tblUserRelation.User1 WHERE (tblAssignment.SurveyID = "+SurveyID+")";
		
		//TOYOTA
		/* Commented off by Rianto: ResultSet should only get Distinct SupervisorID & NominateEndDate, No need for TargetLoginID
		*
		String command_str = "SELECT DISTINCT tblAssignment.TargetLoginID, User2, NominationEndDate FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = ";
		command_str = command_str +"tblSurvey.SurveyID INNER JOIN tblUserRelation ON tblAssignment.TargetLoginID = ";
		command_str = command_str +"tblUserRelation.User1 WHERE (tblAssignment.SurveyID = "+SurveyID+")";
		*
		*/
		
		String command_str = "SELECT DISTINCT User2, RelationType, NominationEndDate FROM tblAssignment INNER JOIN tblSurvey ON tblAssignment.SurveyID = ";
		command_str = command_str +"tblSurvey.SurveyID INNER JOIN tblUserRelation ON tblAssignment.TargetLoginID = ";
		command_str = command_str +"tblUserRelation.User1 WHERE (tblAssignment.SurveyID = "+SurveyID+")";
		
		//END TOYOTA
	
		int supID = 0;
		int iRelationType = 0;
		
		//Added by xuehai 24 Jun 2011. Record the total number of persons and successful emails.
    	int totalMemNum = 0;
		int sendEmailNum = 0;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_str);
			
			while(rs.next())
			{
				supID = rs.getInt("User2");
				iRelationType = rs.getInt("RelationType");
				NominationClosedDate = formatter.format(rs.getDate("NominationEndDate"));
				supDetail = CE_Survey.getUserDetail_ADV(supID);	
				String title ="";
				
				if(assign.getNomSupStatus(SurveyID, supID) == 0) //0=Incomplete, 1=Completed
				{
					
					if(isReminder == 1 && iRelationType != 0)
					{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
					 	//title = template.Sup_Nominate_Reminder_Subject();
						title = org.getEmailSubject_SurvID(SurveyID, 1);
						
						//content = template.Sup_Nominate_Reminder(supID, SurveyID, NominationClosedDate);
						content = template.fillNomTemplate(1, SurveyID, supID, NominationClosedDate);
						//--Added by Roger 30 June 2008
						//use to flag out content empty error to jsp
						if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
							// Added by Ping Yang to check for String "null"
							return 1; //return 1 - email template empty
						}
						//--End of Adding
						
						//Edited by Xuehai 19 May 2011
						//If fileName is not null, send email with attachment
						boolean sendEmail = true;
						if(fileName != null && fileName.length()>0){
							sendEmail = Email.sendMail_with_Attachment(server.getAdminEmail(), supDetail[13], title, content, fileName, orgId);
						}else{
							//Edited By Roger 13 June 2008					
							sendEmail = Email.sendMail(server.getAdminEmail(), supDetail[13], title, content, orgId);
						}//--End of Checking attachment
						
						totalMemNum++;
						if(sendEmail){
							sendEmailNum++;
						}
						counterNominateReminder++;
					}
					else if(isReminder == 1 && iRelationType == 0)	// No Supervisor? Send email to Admin
					{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
						//title = template.Sup_Nominate_Reminder_Subject();
						title = org.getEmailSubject_SurvID(SurveyID, 1);
						//content = template.Sup_Nominate_Reminder(supID, SurveyID, NominationClosedDate);
						content = template.fillNomTemplate(1, SurveyID, supID, NominationClosedDate);
						//--Added by Roger 30 June 2008
						//use to flag out content empty error to jsp
						if (content == null || "".equals(content.trim())|| "null".equals(content.trim().toLowerCase())) {
							// Added by Ping Yang to check for String "null"
							return 1; //return 1 - email template empty
						}
						//--End of Adding
						
						//Edited by Xuehai 19 May 2011
						//If fileName is not null, send email with attachment
						boolean sendEmail = true;
						if(fileName != null && fileName.length()>0){
							sendEmail = Email.sendMail_with_Attachment(server.getAdminEmail(), server.getAdminEmail(), title, content, fileName, orgId);
						}else{
							//Edited By Roger 13 June 2008
							sendEmail = Email.sendMail(server.getAdminEmail(), server.getAdminEmail(), title, content, orgId);
						}//--End of Checking attachment
						totalMemNum++;
						if(sendEmail){
							sendEmailNum++;
						}
						counterNominateReminder++;
					}
					else if(isReminder == 0 && iRelationType != 0)
					{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
						//title = template.Sup_Nominate_Subject();
						title = org.getEmailSubject_SurvID(SurveyID, 0);
						//content = template.Sup_Nominate(supID, SurveyID, NominationClosedDate);
						content = template.fillNomTemplate(0, SurveyID, supID, NominationClosedDate);
						//--Added by Roger 30 June 2008
						//use to flag out content empty error to jsp
						if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
							// Added by Ping Yang to check for String "null"
							return 1; //return 1 - email template empty
						}
						//--End of Adding
						
						//Edited by Xuehai 19 May 2011
						//If fileName is not null, send email with attachment
						boolean sendEmail = true;
						if(fileName != null && fileName.length()>0){
							sendEmail = Email.sendMail_with_Attachment(server.getAdminEmail(), supDetail[13], title, content, fileName, orgId);
						}else{
							//Edited By Roger 13 June 2008				
							sendEmail = Email.sendMail(server.getAdminEmail(), supDetail[13], title, content, orgId);
						}//--End of Checking attachment
						totalMemNum++;
						if(sendEmail){
							sendEmailNum++;
						}
						counterNominateNotify++;
					}
					else if(isReminder == 0 && iRelationType == 0)	// No Supervisor? Send email to Admin
					{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
						//title = template.Sup_Nominate_Subject();
						title = org.getEmailSubject_SurvID(SurveyID, 0);
						//content = template.Sup_Nominate(supID, SurveyID, NominationClosedDate);
						content = template.fillNomTemplate(0, SurveyID, supID, NominationClosedDate);
						//--Added by Roger 30 June 2008
						//use to flag out content empty error to jsp
						if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
							// Added by Ping Yang to check for String "null"
							return 1; //return 1 - email template empty
						}
						//--End of Adding						
						
						//Edited by Xuehai 19 May 2011
						//If fileName is not null, send email with attachment
						boolean sendEmail=true;
						if(fileName != null && fileName.length()>0){
							sendEmail=Email.sendMail_with_Attachment(server.getAdminEmail(), server.getAdminEmail(), title, content, fileName, orgId);
						}else{
							//Edited By Roger 13 June 2008
							sendEmail=Email.sendMail(server.getAdminEmail(), server.getAdminEmail(), title, content, orgId);
						}//--End of Checking attachment
						totalMemNum++;
						if(sendEmail){
							sendEmailNum++;
						}
						counterNominateNotify++;
					}
				}			
			}
       		
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - Sup_Nominate - " + E);
        }
        finally
        {
        	System.out.print("CLOSED!!");
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
        if(totalMemNum!=sendEmailNum){
        	System.out.println("ReminderCheck - Sup_Nominate - Sending emails finished. But some Emails are failed.");
        	return 100;
        }
        return 0; //no error	
	}
	
	//Edited by Roger 30 June 2008
	//Include a return error flag
	/**
	 *	Send email for Sup to Nominate
	 *	
	 *	isReminder = 1 => send reminder email
	 *	errorFlag = 1 => template is empty
	 *
	 *@param surveyID
	 *@param isReminder
	 *@return errorFlag
	 */
	
	public int Sup_Nominate(int SurveyID, int isReminder)throws SQLException, Exception
	{
		//Edited by Xuehai 19 May 2011
		//Invoke a new method which support attachment.
		return Sup_Nominate(SurveyID, isReminder, null);
	}
	
	//Add by Xuehai 19 May 2011
	//Sending Nomination Mails needs an attachment.
	/**
	 *	Send email for Specific Sup to Nominate
	 *	
	 *	if isReminder == 1
	 *		send reminder email
	 *@param fileName, the file name of the attachment, null if no attachment
	 *
	 */
	public int Sup_Nominate(int SurveyID, int SupID, int isReminder, String fileName)throws SQLException, Exception
	{
		//Edited By Roger 13 June 2008
		//Get Org ID From SurveyID
		int orgId = 0;
		String sql = "SELECT FKOrganization FROM tblSurvey WHERE SurveyID=" + SurveyID;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;	
		
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(sql);
		
		if (rs.next()) {
			orgId = rs.getInt("FKOrganization");
		}
		
    	ConnectionBean.closeRset(rs); //Close ResultSet
    	ConnectionBean.closeStmt(st); //Close statement
    	ConnectionBean.close(con); //Close connection
		
		
		String title ="";
		String [] supDetail = new String[14];
		supDetail = CE_Survey.getUserDetail_ADV(SupID);
		
		boolean emailSend = true;
		
		if(isReminder == 1)
		{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
		 	//title = template.Sup_Nominate_Reminder_Subject();
			title = org.getEmailSubject_SurvID(SurveyID, 1);
			//content = template.Sup_Nominate_Reminder(SupID, SurveyID, getNomEndDate(SurveyID));
			content = template.fillNomTemplate(1, SurveyID, SupID, getNomEndDate(SurveyID));
			//--Added by Roger 30 June 2008
			//use to flag out content empty error to jsp
			if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
				// Added by Ping Yang to check for String "null"
				return 1; //return 1 - email template empty
			}
			//--End of Adding
			
			//Edited by Xuehai 19 May 2011
			//If fileName is not null, send email with attachment
			if(fileName != null && fileName.length()>0){
				emailSend = Email.sendMail_with_Attachment(server.getAdminEmail(), supDetail[13], title, content, fileName, orgId);
			}else{
				//Edited By Roger 13 June 2008		
				emailSend = Email.sendMail(server.getAdminEmail(), supDetail[13], title, content, orgId);
			}//--End of Checking attachment
		}
		else if(isReminder == 0)
		{/*
 * Change : Get the subject from database
 * Reason : Previously hardcode in java file
 * Add by : Johanes
 * Add on : 9/11/2009
 */	
			//title = template.Sup_Nominate_Subject();
			title = org.getEmailSubject_SurvID(SurveyID, 0);
			//content = template.Sup_Nominate(SupID, SurveyID, getNomEndDate(SurveyID));
			content = template.fillNomTemplate(0, SurveyID, SupID, getNomEndDate(SurveyID));
			//--Added by Roger 30 June 2008
			//use to flag out content empty error to jsp
			if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
				// Added by Ping Yang to check for String "null"
				return 1; //return 1 - email template empty
			}
			//--End of Adding
			
			//Edited by Xuehai 19 May 2011
			//If fileName is not null, send email with attachment
			if(fileName != null && fileName.length()>0){
				emailSend = Email.sendMail_with_Attachment(server.getAdminEmail(), supDetail[13], title, content, fileName, orgId);
			}else{
				//Edited By Roger 13 June 2008	
				emailSend = Email.sendMail(server.getAdminEmail(), supDetail[13], title, content, orgId);
			}//--End of Checking attachment
		}				
		if(emailSend == false){
			System.out.println("ReminderCheck - Sup_Nominate - Sending email failed.");
			return 100;
		}
		return 0;
	}
	
	//Edited by Xuehai 19 May 2011
	//Invoke the method which support attachment.
	/**
	 *	Send email for Specific Sup to Nominate
	 *	
	 *	if isReminder == 1
	 *		send reminder email
	 */
	public int Sup_Nominate(int SurveyID, int SupID, int isReminder)throws SQLException, Exception
	{
		return Sup_Nominate(SurveyID, SupID, isReminder, null);
	}
	
	public void NominationEndDate (ResultSet rs)throws SQLException, Exception
	{
		String SurveyStatus_str = "";
		int SurveyID = rs.getInt("SurveyID");
		String SurveyName = rs.getString("SurveyName");
		int SurveyStatus = rs.getInt("SurveyStatus");
		int OrgID = rs.getInt("FKOrganization");
		int AdminID = rs.getInt("AdminAssigned");
		String NomEndDate = formatter.format(rs.getDate("NominationEndDate"));
		String OrgName = rs.getString("OrganizationName");
		String JobPosName = rs.getString("JobPosition");
		
		
		UserDetail = CE_Survey.getUserDetail_ADV(AdminID);
		
		String fullname = UserDetail[0]+" "+UserDetail[1];
		
		if(SurveyStatus == 1)
			SurveyStatus_str = "Open";
		else if(SurveyStatus == 2)
			SurveyStatus_str = "Closed";
		else if(SurveyStatus == 3)
			SurveyStatus_str = "Not Commissioned";
		
		content = template.NominationEnd(fullname,NomEndDate,OrgName,JobPosName,SurveyName,SurveyStatus_str);
		
						/* RIANTO TO BE DELETED */
				//System.out.println("RIANTO EMAILS SENT FOR NOMINATION END DATE");
		 		//System.out.println("DateBefore =  " + date_before + "\nDate = " + date + "\n3DaysBefore = " +three_days_before );
		 		/* END RIANTO TO BE DELETED */
		Email.sendMail(server.getAdminEmail(), UserDetail[13], template.NominationEnd_Subject(), content, OrgID);
	}
	
	
	public String getNomEndDate(int SurveyID) throws SQLException, Exception
	{
		String NomEndDate = "";
		String command_str = "SELECT * from tblSurvey WHERE SurveyID = "+SurveyID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command_str);
			
			if(rs.next())
				NomEndDate = formatter.format(rs.getDate("NominationEndDate"));
			
       
        }
        catch(Exception E) 
        {
            System.err.println("ReminderCheck.java - getNomEndDate- " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		
		return NomEndDate;
	}
	
	public void initializeEmailRecord(){
		String templateURL 	= "file:///" + server.getOOReportTemplatePath() + "Email_Record_Template.xls";
		//String templateURL 	= "file:///" + server.getOOReportTemplatePath() + "Individual Report Template.xls";
		Date timeStamp = new java.util.Date();			
		SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyHHmmss");
		String temp  =  dFormat.format(timeStamp);
		try{
	
			this.setStoreURL("file:///" + server.getOOReportPath()+"Email_Record("+temp+").xls");
			xRemoteServiceManager = OO.getRemoteServiceManager("uno:socket,host=localhost,port=2002;urp;StarOffice.ServiceManager");
			xDoc = OO.openDoc(xRemoteServiceManager, templateURL);

			//save as the template into a new file first. This is to avoid the template being used.		
			OO.storeDocComponent(xRemoteServiceManager, xDoc, storeURL);
			OO.closeDoc(xDoc);
        
			//open up the saved file and modify from there
			xDoc = OO.openDoc(xRemoteServiceManager, storeURL);
			xSpreadsheet = OO.getSheet(xDoc, "Record");
			}
		catch(Exception e){
			System.out.println("ReminderCheck.java - initializeEmailRecord  Exception: " + e);
		}
		
	}
	
	public void setTime_Sent(boolean value) 
	{
	    Time_Sent = value;
	}
	  
	public boolean getTime_Sent() 
	{
	  	return Time_Sent;
	}
	
	public String getMessage()
	{
		String stemp = "ReminderCheck.java";
		return stemp;
	}
	
	public static void main (String [] args)throws SQLException, Exception
	{
		ReminderCheck RC = new ReminderCheck();
		//RC.check();
		
		//RC.Email_Survey_Open_Participant_Option(459, "", 0, 0);
		//RC.Email_Survey_Open_Participant_Option(459, "", 5263, 0);
		//RC.Email_Survey_Open_Participant(415,"");
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}
}