package Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import CP_Classes.MailHTMLStd;
import CP_Classes.User;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCoachSession;
import CP_Classes.vo.voUser;

public class CoachingEmail {
	MailHTMLStd Email = new MailHTMLStd();
	int selectedSession;
	Vector<voUser> selectedUsers=new Vector<voUser>();
	Vector<voUser> unselectedUsers=new Vector<voUser>();
	int action;

	public int notificationAll(Vector<voUser> users,int orgId,int SurveyID) throws SQLException, Exception{
		
		boolean suc=true;
		for(int i=0;i<users.size();i++){
			//to do
			//check the user sign or not
			int sent=this.notificationSingle(users.elementAt(i).getPKUser(), orgId);
			if(sent==-1){
				return -1;
			}else{
				suc=false;
			}
		}
		if(suc){
			return users.size();
		}else{
			return-100;	}
	}

	public int notificationSingle(int userID, int orgId) throws SQLException, Exception{

		String subject=this.getEmailSubject(orgId, 1);
		String content=this.getEmailContent(orgId, 1);
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			return -1; //return 1 - email template empty
		}
		User user=new User();
		voUser userDetails=user.getUserInfo(userID);
		String toEmail=userDetails.getEmail();
		if(Email.sendMail("3SixtyAdmin@pcc.com.sg", toEmail, subject, this.fillContent(content, userID), orgId))
			return 1;
		else
			return -100;
	}
	public int reminderAll(Vector<voUser> users,int orgId,int SurveyID) throws SQLException, Exception{
		boolean suc=true;
		for(int i=0;i<users.size();i++){
			//to do
			//check the user sign or not
			int sent=this.reminderSingle(users.elementAt(i).getPKUser(), orgId);
			if(sent==-1){
				return -1;
			}else{
				suc=false;
			}
		}
		if(suc){
			return users.size();
		}else{
			return-100;	}

	}
	public int reminderSingle(int userID, int orgId) throws SQLException, Exception{

		String subject=this.getEmailSubject(orgId, 2);
		String content=this.getEmailContent(orgId, 2);
		if (content == null || "".equals(content.trim()) || "null".equals(content.trim().toLowerCase())) {
			return -1; //return 1 - email template empty
		}
		User user=new User();
		voUser userDetails=user.getUserInfo(userID);
		String toEmail=userDetails.getEmail();
		if(Email.sendMail("3SixtyAdmin@pcc.com.sg", toEmail, subject, this.fillContent(content, userID), orgId))
			return 1;
		else
			return -100;
	}
	public String fillContent(String content, int UserPK){

		User user=new User();
		voUser userDetails=user.getUserInfo(UserPK);
		String raterName=userDetails.getFamilyName()+" "+userDetails.getGivenName();
		String userName=userDetails.getLoginName();
		String password=userDetails.getPassword();
		content=content.replaceAll("&lt;Rater Name&gt;"," "+raterName);
		content=content.replaceAll("&lt;Username&gt;", userName);
		content=content.replaceAll("&lt;Password&gt;", password);

		return content;
	}
	public String getEmailSubject(int FKOrg, int iOpt) throws SQLException, Exception {
		//iOpt 1 :EmailCoachNotification
		//iOpt 2 :EmailCoachReminder

		String query = "";
		String field;
		if(iOpt == 1)
			field = "EmailCoachNotificationSubject";
		else 
			field = "EmailCoachReminderSubject";

		query = query + "SELECT " + field + " FROM tblOrganization WHERE PKOrganization = " + FKOrg;	

		//		System.out.println(query);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sEmailTemplateSubject = "";
		try
		{          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
			{
				sEmailTemplateSubject = rs.getString(1);
			}			
		}
		catch(Exception E) 
		{
			System.err.println("CoachingEmail.java - getEmailTemplate - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		return sEmailTemplateSubject;
	}
	public String getEmailContent(int FKOrg, int iOpt) throws SQLException, Exception {

		//iOpt 1 :EmailCoachNotification
		//iOpt 2 :EmailCoachReminder
		String query = "";
		String field;
		if(iOpt == 1)
			field = "EmailCoachNotification";
		else
			field = "EmailCoachReminder";

		query = query + "SELECT " + field + " FROM tblOrganization WHERE PKOrganization = " + FKOrg;	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sEmailTemplate = "";

		try
		{          
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
			{
				sEmailTemplate = rs.getString(1);
			}			

		}
		catch(Exception E) 
		{
			System.err.println("CoachingEmail.java - getEmailTemplate - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		return sEmailTemplate;
	}
	public Vector<voUser> removeRegisteredCandidate(Vector<voUser> users,int SurveyID){


		return users;
	}
	public Vector<voUser> filterReminderUsers(Vector<voUser> users,int orgId,int SurveyID) throws SQLException, Exception{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector<voUser> SignedUsers=new Vector<voUser>();
		String query="Select distinct [User].PKUser,[User].GivenName,[User].FamilyName from CoachAssignment inner join [User] on [User].PKUser=CoachAssignment.UserFK where OrgFK="+orgId+" and SurveyFK="+SurveyID+" and SessionFK="+this.getSelectedSession();
		//System.out.println(query);
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				int userPK=rs.getInt("PKUser");
				User user=new User();
				voUser userDetails=user.getUserInfo(userPK);
				SignedUsers.add(userDetails);
			}
			//			System.out.println("size of all users:"+users.size());
			//			System.out.println("size of signed users:"+SignedUsers.size());
		}
		catch(Exception E) 
		{
			System.err.println("CoachingEmail.java - filterReminderUsers - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}

		//re-format users
		Vector<voUser> formatedUser=new Vector<voUser>();
		for(int i=0;i<users.size();i++){
			int userPK=users.elementAt(i).getPKUser();
			User user=new User();
			voUser userDetails=user.getUserInfo(userPK);
			formatedUser.add(userDetails);
		}
		//		System.out.println("size of formatedUser all users:"+formatedUser.size());

		ArrayList<Integer> toBeRemoved=new ArrayList<Integer>();
		for(int i=0;i<formatedUser.size();i++){
			for(int j=0;j<SignedUsers.size();j++){
				if(formatedUser.elementAt(i).getPKUser()==SignedUsers.elementAt(j).getPKUser()){
					toBeRemoved.add(i);
					break;
				}
			}
		}


		for(int i=toBeRemoved.size()-1;i>=0;i--){
			formatedUser.remove(toBeRemoved.get(i).intValue());	
		}
		//System.out.println("size of unsigned User:"+formatedUser.size());
		//exclude the users
		return formatedUser;
	}
	public void deleteUser(int userPK){
		int toBeRemoved;
		for(int i=0;i<this.getSelectedUsers().size();i++){
			if(this.getSelectedUsers().elementAt(i).getPKUser()==userPK){
				toBeRemoved=i;
				this.getSelectedUsers().remove(toBeRemoved);
				break;
			}
		}
	}
	public Vector<voCoachSession> getSessionsBySurveyID(int SurveyID) throws SQLException, Exception{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector<voCoachSession> sessions=new Vector<voCoachSession>();
		String query="SELECT DISTINCT CoachSession.PKCoachSession, CoachSession.SessionName";
		query=query+" FROM CoachAssignment INNER JOIN";
		query=query+" CoachSession ON CoachSession.PKCoachSession = CoachAssignment.SessionFK";
		query=query+" WHERE CoachAssignment.SurveyFK = "+SurveyID;
		//		System.out.println(query);
		try
		{ 
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				voCoachSession vo = new voCoachSession();
				vo.setPK(rs.getInt("PKCoachSession"));
				vo.setName(rs.getString("SessionName"));
				sessions.add(vo);
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachingEmail.java - getSessionsBySurveyID - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return sessions;
	}

	public int getSelectedSession() {
		return selectedSession;
	}

	public void setSelectedSession(int selectedSession) {
		this.selectedSession = selectedSession;
	}

	public Vector<voUser> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(Vector<voUser> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public Vector<voUser> getUnselectedUsers() {
		return unselectedUsers;
	}

	public void setUnselectedUsers(Vector<voUser> unselectedUsers) {
		this.unselectedUsers = unselectedUsers;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}


}
