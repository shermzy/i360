/*
 * Author: Dai Yong
 * June 2013
 */
package Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import CP_Classes.ConsultingCompany;
import CP_Classes.EventViewer;
import CP_Classes.Organization;
import CP_Classes.User;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCoachSession;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblConsultingCompany;
import CP_Classes.vo.votblOrganization;

public class CoachSession {
//	Start of EventViewer
	private EventViewer ev=new EventViewer();
	private int UserPK;
	private voUser userDetials;
	private votblConsultingCompany companyDetail;
	private votblOrganization votblOrganizationDetail;
		
	public int getUserPK() {
		return UserPK;
	}
	public void setUserPK(int userPK) {
		UserPK = userPK;
		User user=new User();
		ConsultingCompany consultingCompany=new ConsultingCompany();
		Organization organization=new Organization();
		userDetials=user.getUserInfo(UserPK);	
		companyDetail=consultingCompany.getConsultingCompany(userDetials.getFKCompanyID());
		votblOrganizationDetail=organization.getOrganization(userDetials.getFKOrganization());
	}
//	End of EventViewer
	
	
	//this mehod is not used
	public boolean deleteUnusedSessions() throws SQLException, Exception
	{
		
		String sql = "DELETE FROM CoachSession";
		Connection con = null;
		Statement st = null;
        boolean bIsDeleted = false;
			
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0)
				bIsDeleted=true;
			} 
			catch (Exception E)
			{
				System.err.println("CoachSession.java - deleteUnusedSessions - " + E);
			}
			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		return bIsDeleted;
	}
	
	public boolean updateCoachingSession(int PKCoachSession,String SessionName,String SessionDescription,int SessionMax)throws SQLException, Exception{
		
		String sql = "Update CoachSession Set SessionName = '"+ SessionName +"',SessionDescription = '"+ SessionDescription +"',SessionMax = "+ SessionMax +" where PKCoachSession = " + PKCoachSession;
		Connection con = null;
		Statement st = null;
        boolean bIsUpdated = false;
			
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0){
					bIsUpdated=true;
					ev.addRecord("Update", "Update Coaching Session", "Update Coaching Session, Coaching Session PK:"+PKCoachSession, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
				}
	  		
			} 
			catch (Exception E)
			{
				System.err.println("CoachSession.java - updateCoachingSession - " + E);
			}

			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		return bIsUpdated;
		
	}
	public voCoachSession getSelectedSession(int PKCoachSession){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		voCoachSession vo = new voCoachSession();
		String query="Select * from CoachSession where PKCoachSession="+PKCoachSession;
		try
		{ 
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				vo.setPK(rs.getInt("PKCoachSession"));
				vo.setName(rs.getString("SessionName"));
				vo.setDescription(rs.getString("SessionDescription"));
				vo.setSessionMax(rs.getInt("SessionMax"));
				break;
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSession.java - getSelectedSession - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return vo;
	}
	public boolean updateSessionCloseDate(int PKCoachSession,Date closeDate){
		String sql = "Update CoachSession Set SessionCloseDate = '"+ closeDate +"' where PKCoachSession = " + PKCoachSession;
		Connection con = null;
		Statement st = null;
        boolean bIsUpdated = false;
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0){
					bIsUpdated=true;
					ev.addRecord("Update", "Update Coaching Session", "Update Coaching Session close date, Coaching Session PK:"+PKCoachSession, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
				}
			} 
			catch (Exception E)
			{
				System.err.println("CoachSession.java - updateSessionCloseDate - " + E);
			}

			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		return bIsUpdated;
	}
}
