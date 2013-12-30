package CP_Classes;

import java.util.Date;
import java.util.*;
import java.sql.*;
import java.io.Serializable;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblEmail;

/**
 * Edited By Roger 13 June 2008
 * Change to include organization Id in email calls
 * Change to include orgId as part of the query
 */

public class MailGenerator implements Serializable
{
	Date dt = new Date();
	private String itemName = "Send Mail";
	private String EventDesc = "e-Mail sends to ";
	
	/**
 	* Declaration of classes 
 	*/
	//private Database db;
	private MailHTMLStd Email;
	private EmailTemplate template;
	private GlobalFunc global;
	private EventViewer ev;
	private User user;
	
	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	private int Toggle;	// 0=asc, 1=desc
	public int SortType; //Bean Variable to store the Sorting type, such as sort by EmailID, SenderEmail, ...
	
	public MailGenerator() throws SQLException, Exception
	{
		//db =new Database();
		Email = new MailHTMLStd();
		template = new EmailTemplate();
		global = new GlobalFunc();
		user = new User();
		ev = new EventViewer();
	}
	
	public void check() throws SQLException, Exception
	{
		//db.openDB();
		//Statement data = db.con.createStatement();
		int [] EmailID = new int[15];
		int counter =0;
		Connection con = null;
		Statement st = null;
		ResultSet rs_tblEmail = null;
		try{
			String querySql="SELECT TOP 15 * FROM tblEmail ORDER BY EmailID";
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs_tblEmail=st.executeQuery(querySql);
			
			while(rs_tblEmail.next()){
				EmailID[counter] = rs_tblEmail.getInt("EmailID");
				String SenderEmail = rs_tblEmail.getString("SenderEmail");
				String ReceiverEmail = rs_tblEmail.getString("ReceiverEmail");
				String Header = rs_tblEmail.getString("Header");
				String Content = rs_tblEmail.getString("Content");
				//Edited By Roger 13 June 2008
				int orgId = rs_tblEmail.getInt("FKOrganization");
				//String filename = rs_tblEmail.getString("filename");
				Content = Content.trim();
				
				/*
				if(filename != null)
					sendMail_with_Attachment(server.getEmailHost(), SenderEmail, ReceiverEmail, Header, Content, filename);
				else
				*/
				
				/******
				 * 
				 * Edited by Roger - 12 June 2008
				 * Add one more FKOrganization to table
				 * 
				 * 
				 *****/	
				//Edited By Roger 13 June 2008
				Email.sendMail(SenderEmail, ReceiverEmail, Header, Content, orgId);
				EventDesc = EventDesc + ReceiverEmail;
		
				ev.addRecord("Send Mail", itemName, EventDesc, "System", "System", "System");
		
				counter++;
				
			}
			for(int j=0; j<counter; j++)
			{
				template.delFromtblEmail(EmailID[j]);
			}
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - check - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs_tblEmail); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
	}
	
	
	/**
	 * Get Total Records of Sent Failed Emails from tblEmail and return the int
	 *
	 */
	public int getTotSentFailedEmail() throws SQLException, Exception
	{
		String query = "Select COUNT(*) as TotRecord from tblEmail";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				return rs.getInt(1);
			
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - getTotSentFailedEmail - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);
		*/
		
		return 0;
			
	}
	
	//Added by Roger 30 June 2008
	/**
	 * Get Total Records of Sent Failed Emails from tblEmail and return the int
	 * by organization id
	 */
	public int getTotSentFailedEmailByOrgId(int orgId) throws SQLException, Exception
	{
		String query = "Select COUNT(*) as TotRecord from tblEmail WHERE FKOrganization="+orgId;
		System.out.println(query);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				return rs.getInt(1);
			
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - getTotSentFailedEmail - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);
		*/
		
		return 0;
			
	}
	
	/**
	 * Get Sent Failed Emails from tblEmail and return the ResultSet
	 *
	 * Pass in TotDisplay to let system know how many records to display
	 *		- TotDisplay = 0 = Display All
	 *		- TotDisplay > 0 = Display amount
	 *
	 */
	public Vector getSentFailedEmail(int sortBy, String sortType, int TotDisplay, int PKUser, int orgId) throws SQLException, Exception
	{
		Vector v=new Vector();
		
		Vector tblFieldname = global.getTblFieldname("tblEmail");
	    
		String query = "Select";
		if (TotDisplay > 0) 
			query = query + " TOP " + TotDisplay;
		
		//Edited By Roger 13 June 2008
		String orgQuery = "";
		if (orgId != 0) {
			orgQuery = "WHERE FKOrganization=" + orgId;
		}
		
		query = query + " * from tblEmail "+orgQuery+" ORDER BY " + tblFieldname.elementAt(sortBy) + " " + sortType;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				votblEmail vo=new votblEmail();
				
				vo.setContent(rs.getString("Content"));
				vo.setEmailID(rs.getInt("EmailID"));
				
				vo.setHeader(rs.getString("Header"));
				vo.setReceiverEmail(rs.getString("ReceiverEmail"));
				vo.setSenderEmail(rs.getString("SenderEmail"));
				//Added by Xuehai 23 Jun 2011. 
				vo.setLog(rs.getString("Log"));
				//Edited By Roger 13 June 2008
				vo.setOrgId(rs.getInt("FKOrganization"));
				v.add(vo);
			}
			
			
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - getSentFailedEmail - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		//System.out.println(query);
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			*/
		
		String sDetail[] = new String[13];
		sDetail = user.getUserDetail(PKUser, 0);
		ev.addRecord("SELECT", "tblEmail", "tblEmail", sDetail[2], sDetail[11], sDetail[10]);
		
		return v;	
	}
	
	/**
	 * DELETE (MULTIPLE) Sent Failed Emails from tblEmail depending on delOption
	 *
	 * delOption:
	 * 1 = Delete Single Email
	 * 2 = Delete Multiple Emails
	 *
	 */
	public boolean delSentFailedEmail(String EmailIDs, int PKUser, int delOption) throws SQLException, Exception
	{
		boolean bIsDeleted=false;
		String query = "DELETE from tblEmail WHERE EmailID";
		String sDelOption = " IN "; //Default = IN
		
		if(delOption == 1)
			sDelOption = " = ";
		
		query = query + sDelOption + "(" + EmailIDs + ")";
		
		System.out.println(query);
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		stmt.executeUpdate(query);
		*/
		Connection con = null;
		Statement st = null;
		
		try{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(query);
			if(iSuccess!=0)
			bIsDeleted=true;

			
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - delSentFailedEmail - "+SE.getMessage());
		}finally{
		
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
		
		System.out.println("Emails " + EmailIDs + " Have been deleted successfully");
		
		String sDetail[] = new String[13];
		sDetail = user.getUserDetail(PKUser, 0);
		ev.addRecord("DELETE", "tblEmail", EmailIDs, sDetail[2], sDetail[11], sDetail[10]);		
		return bIsDeleted;
	}
	
	/**
	 *  Send Sent Failed Emails from tblEmail WHERE EmailID IN EmailIDs
	 */
	/**
	 * Edit By James change return type to boolean 2-June 2008
	 */

	public boolean sendFailedEmail(String EmailIDs, int PKUser) throws SQLException, Exception
	{
		boolean bIsResent=false;
		String query = "SELECT * from tblEmail WHERE EmailID IN (" + EmailIDs + ")";
	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
			{
				String EmailID = rs.getString("EmailID");
				String from = rs.getString("SenderEmail");
				String to = rs.getString("ReceiverEmail");
				String subject = rs.getString("Header");
				String content = rs.getString("Content");
				//Edited By Roger 13 June 2008
				int org = rs.getInt("FKOrganization");
				
				System.out.println("Sending email for EmailID: " + EmailID);
				//Edited By Roger 13 June 2008
				bIsResent=Email.sendMail(from, to, subject, content, org);
				delSentFailedEmail(EmailID, PKUser, 1);
			}
			
			
		}catch(SQLException SE){
		System.out.println("MailGenerator.java - sendFailedEmail - "+SE.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

		}
	
		String sDetail[] = new String[13];
		sDetail = user.getUserDetail(PKUser, 0);
		ev.addRecord("SEND EMAIL", "tblEmail", EmailIDs, sDetail[2], sDetail[11], sDetail[10]);		
		return bIsResent;
	}
	
	/**
	* Store Bean Variable toggle either 1 or 0.
	*/	
	public void setToggle(int toggle) {
		Toggle = toggle;
	}
	
	/**
	* Get Bean Variable toggle.
		 */
	public int getToggle() {
		return Toggle;
	}	
		
	
	public static void main(String args[]) throws SQLException, Exception{
		MailGenerator RC = new MailGenerator();
		
		//RC.check();
		
		//ResultSet rst = RC.getSentFailedEmail(0,"DESC", 0, 1070); //1070 = admintyt
		//while(rst.next())
		//{
		//	System.out.println(rst.getInt("EmailID"));
		//}
		
		//RC.delSentFailedEmail("678176", 1070, 1);
	
		int tot = RC.getTotSentFailedEmail();
		System.out.println(tot);
	}
}