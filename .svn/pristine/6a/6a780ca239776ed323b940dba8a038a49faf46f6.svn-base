/*
 * Author: Dai Yong
 * June 2013
 */
package Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CP_Classes.ConsultingCompany;
import CP_Classes.EventViewer;
import CP_Classes.Organization;
import CP_Classes.User;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCoachSlot;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblConsultingCompany;
import CP_Classes.vo.votblOrganization;

public class CoachSlot {
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
	
	
	public CoachSlot(){

	}
	public voCoachSlot getSelectedSlot(int CoachSlotPK){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		voCoachSlot vo = new voCoachSlot();
		String query="Select * from CoachSlot where CoachSlotPK="+CoachSlotPK;
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				vo.setPK(rs.getInt("CoachSlotPK"));
				vo.setStartingtime(rs.getInt("StartingTime"));
				vo.setEndingtime(rs.getInt("EndingTime"));
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlot.java - getSelectedSlot - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return vo;
	}

	
	public boolean addSlot(int FKCoachSlotGroup, int StartingTime, int EndingTime){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean suc=false;
		String query = "INSERT INTO CoachSlot (FKCoachSlotGroup,StartingTime,EndingTime) VALUES ('"+ FKCoachSlotGroup+"' ," + StartingTime +","+ EndingTime+")";
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(query);
			if(iSuccess!=0){
				suc=true;
				ev.addRecord("Add", "Add Coaching Slot", "Add Coaching Timing to Coaching Slot, Coaching Slot PK:"+FKCoachSlotGroup, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
			}
		
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlot.java - addSlot - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return suc;
	}
	public boolean deleteSlot(int CoachSlotPK) throws SQLException, Exception
	{
		
		String sql = "DELETE FROM CoachSlot WHERE CoachSlotPK = " + CoachSlotPK;
		Connection con = null;
		Statement st = null;
        boolean bIsDeleted = false;
			
			try
			{

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0){
					bIsDeleted=true;
					ev.addRecord("Delete", "Delete Coaching Slot", "Delete Coaching Timing of Coaching Slot, Coaching Timing PK:"+CoachSlotPK, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
				}
	  		
			} 
			catch (Exception E)
			{
				System.err.println("Slot.java - deleteSlot - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		
	
		return bIsDeleted;
	}
	

	public boolean updateSlot(int CoachSlotPK,int StartingTime,int EndingTime)throws SQLException, Exception{
	
		String sql = "Update CoachSlot Set StartingTime = " + StartingTime +
				", EndingTime = " + EndingTime + " where CoachSlotPK = " + CoachSlotPK;
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
					ev.addRecord("Update", "Update Coaching Slot", "Update Coaching Timing of Coaching Slot, Coaching Timing PK:"+CoachSlotPK, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
				}
	  		
			} 
			catch (Exception E)
			{
				System.err.println("SlotGroup.java - UpdateSlotGroup - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		
		return bIsUpdated;
		
	}
	

}
