/*
 * Author: Dai Yong
 * June 2013
 */
package Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.ConsultingCompany;
import CP_Classes.EventViewer;
import CP_Classes.Organization;
import CP_Classes.User;
import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCoachSlot;
import CP_Classes.vo.voCoachSlotGroup;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblConsultingCompany;
import CP_Classes.vo.votblOrganization;

public class CoachSlotGroup  {
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
	
	public CoachSlotGroup(){

	}
	public int getFirstSlotGroupPK(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int firstSlotGroupPK=0;
		String query="Select * from CoachSlotGroup";
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				
				firstSlotGroupPK=rs.getInt("PK_CoachSlotGroup");
				break;
			
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlotGroup.java - getSelectedSlotGroup - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return firstSlotGroupPK;
	}
	public voCoachSlotGroup getSelectedSlotGroup(int PK_CoachSlotGroup){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		voCoachSlotGroup vo = new voCoachSlotGroup();
		String query="Select * from CoachSlotGroup where PK_CoachSlotGroup="+PK_CoachSlotGroup;
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				
				
				vo.setPk(rs.getInt("PK_CoachSlotGroup"));
				vo.setSlotGroupName(rs.getString("SlotName"));
			
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlotGroup.java - getSelectedSlotGroup - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return vo;
	}

	public Vector<voCoachSlotGroup> getAllSlotGroup(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector<voCoachSlotGroup> v = new Vector<voCoachSlotGroup>();
		String query="Select * from CoachSlotGroup order by SlotName";

		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
//			System.out.println("CoachSlotGroup list:");

			while(rs.next())
			{	
				voCoachSlotGroup vo = new voCoachSlotGroup();
				vo.setPk(rs.getInt("PK_CoachSlotGroup"));
				vo.setSlotGroupName(rs.getString("SlotName"));
//				System.out.println("SlotGroup Name:"+rs.getString("SlotName"));
				v.add(vo);
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlotGroup.java - getAllSlotGroup - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return v;
	}
	
	public Vector<voCoachSlot> getSelectedSlotGroupDetails(int FKCoachSlotGroup){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Vector<voCoachSlot> v = new Vector<voCoachSlot>();
		String query="Select * from CoachSlot where FKCoachSlotGroup="+FKCoachSlotGroup+" order by StartingTime";

		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
//			System.out.println("Slot Details list for"+FKCoachSlotGroup+": ");

			while(rs.next())
			{	
				voCoachSlot vo = new voCoachSlot();
				vo.setPK(rs.getInt("CoachSlotPK"));
				vo.setStartingtime(rs.getInt("StartingTime"));
				vo.setEndingtime(rs.getInt("EndingTime"));
//				System.out.println("Slot Detail:"+rs.getString("CoachSlotPK")+", Starting time:"+rs.getInt("StartingTime")+",ending time"+rs.getInt("EndingTime"));
				v.add(vo);
			}
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlotGroup.java - getSelectedSlotGroupDetails - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return v;
	}
	public boolean addSlotGroup(String name){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean suc=false;
		String query = "INSERT INTO CoachSlotGroup (SlotName) VALUES ('"+ name+"' )";
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(query);
			if(iSuccess!=0){
				suc=true;
				ev.addRecord("Add", "Add Coaching Timing", "Add Coaching Slot, Coaching Slot Name:"+name, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());				
			}
		
		}
		catch(Exception E) 
		{
			System.err.println("CoachSlotGroup.java - addSlotGroup - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return suc;
	}
	public boolean deleteSlotGroup(int PKSlotGroup) throws SQLException, Exception
	{
		
		String sql = "DELETE FROM CoachSlotGroup WHERE PK_CoachSlotGroup = " + PKSlotGroup;
		String sql2 = "DELETE FROM CoachSlot WHERE FKCoachSlotGroup = " + PKSlotGroup;
		
		Connection con = null;
		Statement st = null;
        boolean bIsDeleted = false;
			
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				st.executeUpdate(sql2);
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0){
					bIsDeleted=true;
					ev.addRecord("Delete", "Delete Coaching Slot", "Delete Coaching Slot, Coaching Slot PK:"+PKSlotGroup, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
				}
	  		
			} 
			catch (Exception E)
			{
				System.err.println("SlotGroup.java - deleteSlotGroup - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		
	
		return bIsDeleted;
	}
	

	public boolean updateSlotGroup(int PK_CoachSlotGroup,String name)throws SQLException, Exception{
	
		String sql = "UPDATE CoachSlotGroup SET SlotName = '" + name +"' WHERE PK_CoachSlotGroup = " + PK_CoachSlotGroup;
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
					ev.addRecord("Update", "Upate Coaching Slot", "Update Coaching Slot, Coaching Slot PK:"+PK_CoachSlotGroup, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());					
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
