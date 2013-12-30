package CP_Classes;

import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Date;
import java.sql.*;
import java.io.Serializable;
import java.text.*;

/**
 * Edited By Roger 13 June 2008
 * Change to include organization Id in email calls
 */
/**
 * Change Log
 * ==========
 *
 * Date        By                Method(s)                                                    Change(s) 
 * =====================================================================================================
 * 15/09/11    Gwen Oh          	sendMail()			                                     1) Put the portion for creating the email message into a new private method.
 * 			   										                                         2) Added code to send out an undeliverable email again using different properties.
 *
 * 7/07/2012   Liu Taichen       sendMail_with_MultiAttachment(String, String , String ,      changed the host used to pcc.com.sg
 *                                String t, Vector , int )
 **/

public class MailHTMLStd implements Serializable
{
	EmailTemplate template = new EmailTemplate();
	Setting server = new Setting();
	GlobalFunc global = new GlobalFunc();
	
	public String addressString = server.getBCC_Email();
	
	public static void main (String args[]) throws Exception 
	{
		new MailHTMLStd();
	}
	
	public MailHTMLStd()
	{
		//sendMail("smtp.pacific.net.sg", "rianto@pcc.com.sg", "jenty_c@hotmail.com", "Test HMTL Mail", "hi this is inside class");
		//sendMail("smtp.mailhub.pfizer.com", "suwandy@pcc.com.sg", "suwandy82@hotmail.com", "Test HMTL Mail", "hi this is inside class");
		//sendMail("rianto@pcc.com.sg", "rianto@pcc.com.sg", "Test HMTL Mail", "hi this is inside class");
		
		try {
			//sendMail("3sixtyadmin@pcc.com.sg", "maruli@pcc.com.sg", "Testing sending email from server", "This is the content");  
		}
		catch (Exception e){
			
		}
	}
	
	/**
	 *	Send mail function
	 *	
	 *	@param 	host  - email host
	 *	@param 	from  - email sender
	 *	@param 	to  - email receiver
	 *	@param 	subject  - email subject
	 *	@param 	content  - email content
	 */
	/*
	 * Change(s) : 1) Put the portion for creating the email message into a new private method.
	 * 			   2) Added code to send out an undeliverable email again using different properties.
	 * Reason(s) : 1) Creation of email message might be called more than once in the method
	 * 			   2) If mail is sent unsuccessfully using the authentication method, use the non-authenticcation
	 * 			   method to send the email. This works vice-versa.
	 * Updated By: Gwen Oh
	 * Updated On: 15 Sep 2011
	 */
	public boolean sendMail(String from, String to, String subject, String content, int orgId)throws SQLException, Exception
	{
		boolean bIsSent=false;
		try{
			String host = server.getEmailHost();
			Date dw = new Date();	
			SimpleDateFormat database= new SimpleDateFormat ("MM/dd/yyyy");
			String date = database.format(global.addDay(dw, 0));
			
			// Get system properties
			Properties props = System.getProperties();
			
			// Setup mail server
			props.put("mail.smtp.host", host);
			
			// Session variable declaration
			Session session = null;
			
			// Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode (used with Starhub)
			if(host.equalsIgnoreCase("pcc.com.sg"))
			{
				// Added by Desmond 7 Oct, for sending email using Singnet SMTP
				props.put("mail.smtp.auth", "true" );
				Authenticator auth = new SMTPAuthenticator();
				
				// Get session
				session = Session.getDefaultInstance(props, auth);
				
			} else {
				
				//By default we use No-Authentication Mode, Desmond 27 Oct 09
				// Get session
				session = Session.getDefaultInstance(props, null);
			}
			
			Message message = createEmailMessage(session, from, to, subject, content);
			
			try {
				//Send message
				Transport.send(message);
			}
			catch (Exception e) {
				
				//If authentication mode is used before, try the non-authentication mode and vice-versa
				if(host.equalsIgnoreCase("pcc.com.sg"))
				{
					props.put("mail.smtp.host", "smtp.starhub.net.sg");
					props.put("mail.smtp.auth", "false" );
					// Get session
					session = Session.getInstance(props, null);
				}
				else {
					props.put("mail.smtp.host", "pcc.com.sg");
					props.put("mail.smtp.auth", "true" );
					Authenticator auth = new SMTPAuthenticator();
					// Get session
					session = Session.getInstance(props, auth);
				}
				
				message = createEmailMessage(session, from, to, subject, content);
				//Try sending the message again
				Transport.send(message);
			}
			System.out.println("\nMail sent Successfully to "+to.trim() + " email: "+subject.trim()+".");
			bIsSent=true;
		} catch (Exception e) {
			//e.printStackTrace();
			Database db = new Database();
			//Edited by xuehai 23 June 2011. Added 'e.getMessage()' to record the error msg.
			template.addTotblEmail(server.getAdminEmail(), to, db.SQLFixer(subject), db.SQLFixer(content), db.SQLFixer(e.getMessage()), orgId);
			System.out.println("\nMail sent FAILED to "+to.trim() + " email: "+subject.trim()+".");
		}
		return bIsSent;
	}
	
	



	
	private Message createEmailMessage(Session session, String from, String to, String subject, String content) throws MessagingException {
		
		// Create the message
		Message message = new MimeMessage(session);
		
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		String[] result = addressString.split(",");
		for (int x=0; x<result.length; x++)
		{
			if(server.getAllowBCCEmail() == 1) //If BCCEmail allowed
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(result[x].trim()));
		}
		
        message.setContent(content, "text/html; charset=utf-8"); //utf-8
        
        return message;
	}
	/*
	public boolean sendMail(String from, String to, String subject, String content, int orgId)throws SQLException, Exception
	{
		boolean bIsSent=false;
		try{
			String host = server.getEmailHost();
			Date dw = new Date();	
			SimpleDateFormat database= new SimpleDateFormat ("MM/dd/yyyy");
			String date = database.format(global.addDay(dw, 0));
			
			// Get system properties
			Properties props = System.getProperties();
			
			// Setup mail server
			props.put("mail.smtp.host", host);
			
//			
//			 * Change(s) : Added Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode 
//			 * Reason(s) : Mail sending functions are not working because SMTP settings are wrong,
//			 * 				we also need to cater to both Singnet and Starhub SMTP server depending on which connection
//			 * 				is be used by the server hosting this application
//			 * Updated By: Desmond
//			 * Updated On: 27 Oct 2009
//			 
			
			// Session variable declaration
			Session session = null;
			
			// Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode (used with Starhub)
			if(host.equalsIgnoreCase("pcc.com.sg"))
			{
				// Added by Desmond 7 Oct, for sending email using Singnet SMTP
				props.put("mail.smtp.auth", "true" );
				Authenticator auth = new SMTPAuthenticator();
				
				// Get session
				session = Session.getDefaultInstance(props, auth);
			} else {
				
				//By default we use No-Authentication Mode, Desmond 27 Oct 09
				
				// Get session
				session = Session.getDefaultInstance(props, null);
			}
			
			// Create the message
			Message message = new MimeMessage(session);
			
			// Fill its headers
			message.setSubject(subject);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//** Commented off by Rianto 21-Feb-04
			//** These lines of codes are for single address sending
			//** Implemented multiple addresses sending
			//InternetAddress bcc = new InternetAddress(addressString);
			//message.addRecipient(Message.RecipientType.BCC, bcc);
			
			String[] result = addressString.split(",");
			for (int x=0; x<result.length; x++)
			{
				//System.out.println("Adding Recipient: " + result[x].trim());
				//if(server.getCompanySetting() != 3) //If not = Toyota (Toyota can only send to internal mail)
				if(server.getAllowBCCEmail() == 1) //If BCCEmail allowed
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(result[x].trim()));
			}
			
			//Edit by Xuehai 1 Jul 2011. All the contents have been changed to <br /> instead of '\n'
			//content = content.replaceAll("\\n", "<br>");
	        message.setContent(content, "text/html; charset=utf-8"); //utf-8
			// Send message
	        
			Transport.send(message);
			System.out.println("\nMail sent Successfully to "+to.trim() + " email: "+subject.trim()+".");
			bIsSent=true;
		} catch (Exception e) {
			e.printStackTrace();
			Database db = new Database();
			//Edited by xuehai 23 June 2011. Added 'e.getMessage()' to record the error msg.
			template.addTotblEmail(server.getAdminEmail(), to, db.SQLFixer(subject), db.SQLFixer(content), db.SQLFixer(e.getMessage()), orgId);
			System.out.println("\nMail sent FAILED to "+to.trim() + " email: "+subject.trim()+".");
		}
		return bIsSent;
	}
	*/
	
	/**
	 *	Send mail with attachment function
	 *	
	 *	@param 	host  - email host
	 *	@param 	from  - email sender
	 *	@param 	to  - email receiver
	 *	@param 	subject  - email subject
	 *	@param 	content  - email content
	 *	@param 	filename  - email attachment
	 *
	 *	19-11-2004 -- 360 system has not yet catered  for attachment. 
	 *	In future filename fied must be added in tblEmail database before activating this function
	 */
	 public boolean sendMail_with_Attachment(String from, String to, String subject, String content, String filename, int orgId) throws SQLException, Exception
	 {
		 boolean mailSend = true;
		 try
		 {
			 // Get system properties
			 String host = server.getEmailHost();
			 String filename_path = server.getReport_Path()+"\\"+filename;
			 
			 Properties props = System.getProperties();
			 
			 // Setup mail server
			 props.put("mail.smtp.host", host);
			 
			/*
			 * Change(s) : Added Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode 
			 * Reason(s) : Mail sending functions are not working because SMTP settings are wrong,
			 * 				we also need to cater to both Singnet and Starhub SMTP server depending on which connection
			 * 				is be used by the server hosting this application
			 * Updated By: Desmond
			 * Updated On: 27 Oct 2009
			 */
			
			// Session variable declaration
			Session session = null;
			
			// Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode (used with Starhub)
			if(host.equalsIgnoreCase("pcc.com.sg"))
			{
				// Added by Desmond 7 Oct, for sending email using Singnet SMTP
				props.put("mail.smtp.auth", "true" );
				Authenticator auth = new SMTPAuthenticator();
				
				// Get session
				session = Session.getDefaultInstance(props, auth);
			} else {
				
				//By default we use No-Authentication Mode, Desmond 27 Oct 09
				
				// Get session
				session = Session.getDefaultInstance(props, null);
			}
			 
			 // Define message
			 Message message = new MimeMessage(session);
			 message.setFrom(new InternetAddress(from));
			 message.addRecipient(Message.RecipientType.TO, 
			 new InternetAddress(to));
			 
			 InternetAddress bcc = new InternetAddress(addressString);
			 message.addRecipient(Message.RecipientType.BCC, bcc);
			 
			 message.setSubject(subject);
			 
			 // Create the message part 
			 BodyPart messageBodyPart = new MimeBodyPart();
			 
			 // Fill the message
			 //Modified pageEncoding to html in order to enable input of symbols in the email content. i.e registered sign, Sebastian 29 July 2010
			 //Edit by Xuehai 1 Jul 2011. All the contents have been changed to <br /> instead of '\n'
			 //content = content.replaceAll("\\n", "<br>"); //Change the email content from using plain text syntax to html syntax. To cater to html page encoding, Sebastian 29 July 2010
			 messageBodyPart.setContent(content, "text/html");
			 
			 // Create a Multipart
			 Multipart multipart = new MimeMultipart();
			 
			 // Add part one
			 multipart.addBodyPart(messageBodyPart);
			 
			 // Part two is attachment
			 
	         // Create second body part
	         messageBodyPart = new MimeBodyPart();
	         
             // Get the attachment
	         DataSource source = new FileDataSource(filename_path);
	         
	         // Set the data handler to the attachment
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         
	         // Set the filename
	         messageBodyPart.setFileName(filename);
	         
	         //Added charset UTF-8 to enable input of symbols in the email content. i.e registered sign, Sebastian 29 July 2010
             messageBodyPart.setHeader("Content-Type","charset=\"utf-8\"");
	         
	         // Add part two
             multipart.addBodyPart(messageBodyPart);
             
             // Put parts in message
             message.setContent(multipart);
           
             // Send the message
             Transport.send(message);
             System.out.println("\nMail sent Successfully to "+to.trim() + " email: "+subject.trim()+".");
		 } 
		 catch (Exception e) 
		 {
			 mailSend = false;
			 
			 e.printStackTrace();
			 Database db = new Database();
			
			//Edited by xuehai 23 June 2011. Added 'e.getMessage()' to record the error msg.
			template.addTotblEmail(server.getAdminEmail(), to, db.SQLFixer(subject), db.SQLFixer(content), db.SQLFixer(e.getMessage()), orgId);
			System.out.println("\nATTACHMENT Mail sent FAILED to "+to.trim() + " email: "+subject.trim()+".");
		 
		 }
		 return mailSend;
	 } // End sendMail_with_Attachment()
	 
		/**
		 *	Send mail with multiple attachments
		 *	
		 *	@param 	host  - email host
		 *	@param 	from  - email sender
		 *	@param 	to  - email receiver
		 *	@param 	subject  - email subject
		 *	@param 	content  - email content
		 *	@param 	files  - Vector contain pairs of filenames (i.e. filenames = {"filename in report folder", "filename used for renaming"})
		 *
		 *	@return void
		 *
		 *	@author Desmond
		 *	@since v.1.3.12.89 (10 August 2010)
		 */
		 public boolean sendMail_with_MultiAttachment(String from, String to, String subject, String content, Vector files, int orgId) throws SQLException, Exception
		 {
			 boolean mailSend = true;
			 try
			 {
				 // Get system properties
				 //String host = server.getEmailHost();
				 
				 String host = "pcc.com.sg";
				 String filename_path = "";
				 
				 Properties props = System.getProperties();
				 
				 // Setup mail server
				 props.put("mail.smtp.host", host);
				
				// Session variable declaration
				Session session = null;
				
				// Logic to handle switching between authentication mode (used with Singnet) or No-Authentication mode (used with Starhub)
				if(host.equalsIgnoreCase("pcc.com.sg"))
				{
					// Added by Desmond 7 Oct, for sending email using Singnet SMTP
					props.put("mail.smtp.auth", "true" );
					Authenticator auth = new SMTPAuthenticator();
					
					// Get session
					session = Session.getDefaultInstance(props, auth);
				} else {
					
					//By default we use No-Authentication Mode, Desmond 27 Oct 09
					
					// Get session
					session = Session.getDefaultInstance(props, null);
				}
				 
				 // Define message
				 Message message = new MimeMessage(session);
				 message.setFrom(new InternetAddress(from));
				 message.addRecipient(Message.RecipientType.TO, 
				 new InternetAddress(to));
				 
				 InternetAddress bcc = new InternetAddress(addressString);
				 message.addRecipient(Message.RecipientType.BCC, bcc);
				 
				 message.setSubject(subject);
				 
				 // Create the message part 
				 BodyPart messageBodyPart = new MimeBodyPart();
				 
				 // Fill the message
				 //Modified pageEncoding to html in order to enable input of symbols in the email content. i.e registered sign, Sebastian 29 July 2010
				 //Edit by Xuehai 1 Jul 2011. All the contents have been changed to <br /> instead of '\n'
				 //content = content.replaceAll("\\n", "<br>"); //Change the email content from using plain text syntax to html syntax. To cater to html page encoding, Sebastian 29 July 2010
				 messageBodyPart.setContent(content, "text/html");
				 
		         //Added charset UTF-8 to enable input of symbols in the email content. i.e registered sign, Desmond 10 August 2010
	             //messageBodyPart.setHeader("Content-Type","charset=\"utf-8\"");
				 
				 // Create a Multipart
				 Multipart multipart = new MimeMultipart();
				 
				 // Add part one
				 multipart.addBodyPart(messageBodyPart);
				 
				 // Part two is attachment
				 
				 // String array for temporarily storing pairs of filenames
				 String [] filenames = new String[2];
				 
				 // Attach multiple attachments to the email
				 for(int i=0; i<files.size(); i++) {
				 
			         // Create second body part
			         messageBodyPart = new MimeBodyPart();
			         
			         filenames = (String[]) files.get(i);
			         
		             // Get the attachment
			         filename_path = server.getReport_Path()+"\\"+filenames[0];
			         DataSource source = new FileDataSource(filename_path);
			         
			         // Set the data handler to the attachment
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         
			         // Set the filename
			         messageBodyPart.setFileName(filenames[1]);
			         
			         //Added charset UTF-8 to enable input of symbols in the email content. i.e registered sign, Sebastian 29 July 2010
		             messageBodyPart.setHeader("Content-Type","charset=\"utf-8\"");
			         
			         // Add part two
		             multipart.addBodyPart(messageBodyPart);
	             
				 }
	             
	             // Put parts in message
	             message.setContent(multipart);
	           
	             // Send the message
	             Transport.send(message);
	             System.out.println("\nMail sent Successfully to "+to.trim() + " email: "+subject.trim()+".");
			 } 
			 catch (Exception e) 
			 {
				 mailSend = false;
				 
				 e.printStackTrace();
				 Database db = new Database();
				
				//Edited by xuehai 23 June 2011. Added 'e.getMessage()' to record the error msg.
				 template.addTotblEmail(server.getAdminEmail(), to, db.SQLFixer(subject), db.SQLFixer(content), db.SQLFixer(e.getMessage()), orgId);
				 System.out.println("\nATTACHMENT Mail sent FAILED to "+to.trim() + " email: "+subject.trim()+".");
			 }   
			 return mailSend;
		 } // End sendMail_with_MultiAttachment()

	// Added SMTP Authenticator for sending email when SMTP Authentication mode is enabled, i.e. when using Singnet SMTP, by Desmond 7 Oct 09
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		
		/**
		  * Prepares the PasswordAuthentication object that is needed when SMTP Authentication mode is enabled
		  * 
		  * @author Desmond
		  * @since 07 Oct 2009
		  */
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(server.getEmailUser(), server.getEmailPwd());
		}
	} // End SMTPAuthenticator
} // End MailHTMLStd class