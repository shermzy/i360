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
import CP_Classes.vo.voCoach;
import CP_Classes.vo.voUser;
import CP_Classes.vo.votblConsultingCompany;
import CP_Classes.vo.votblOrganization;

public class Coach {
	
	
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
	
	public Coach(){

	}
	public boolean editUploadFile(int PKCoach, String path) throws SQLException, Exception 
	{
		
		Connection con = null;
		Statement st = null;
		
		String sql = "UPDATE Coach SET UploadFile = '" + path + "' WHERE PKCoach = " + PKCoach;
		
		boolean bIsUpdated = false;
        
	    try
		{
	    	con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
				bIsUpdated=true;
			ev.addRecord("Update", "Update Coach CV", "Upload Coach's CV, CoachPK:"+PKCoach, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());
		}
		catch(Exception E)
		{
	        System.err.println("Coach.java - editUploadInfo2- " + E);
		}
		finally
	    {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
	    }
		

		return bIsUpdated;
	}
	
	public Vector<voCoach> getAllCoach(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		
		Vector<voCoach> coaches=new Vector<voCoach>();
		String query="Select * from Coach order by CoachName";
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				voCoach vo = new voCoach();
				vo.setPk(rs.getInt("PKCoach"));
				vo.setCoachName(rs.getString("CoachName"));
				vo.setLink(rs.getString("Link"));
				vo.setFileName(rs.getString("UploadFile"));
				coaches.add(vo);
			}
		}
		catch(Exception E) 
		{
			System.err.println("Coach.java - getSelectedCoach - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return coaches;
	}

	public voCoach getSelectedCoach(int PKCoach){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		voCoach vo = new voCoach();
		String query="Select * from Coach where PKCoach="+PKCoach;
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			while(rs.next())
			{	
				vo.setPk(rs.getInt("PKCoach"));
				vo.setCoachName(rs.getString("CoachName"));
				vo.setLink(rs.getString("Link"));
			}
		}
		catch(Exception E) 
		{
			System.err.println("Coach.java - getSelectedCoach - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return vo;
	}

	
	public boolean addCoach(String CoachName, String Link){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean suc=false;
		String query = "INSERT INTO Coach (CoachName,Link) VALUES ('"+ CoachName+"' ,'" + Link +"')";
		//System.out.println(query);
		try
		{ 

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(query);
			if(iSuccess!=0)
				suc=true;
			
			ev.addRecord("Insert", "Add Coach", "Coach Name:"+CoachName, userDetials.getLoginName(),companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());
			
		}
		catch(Exception E) 
		{
			System.err.println("Coach.java - addCoach - " + E);
		}
		finally
		{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		return suc;
	}
	public boolean deleteCoach(int PKCoach) throws SQLException, Exception
	{
		
		String sql = "DELETE FROM Coach WHERE PKCoach = " + PKCoach;
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
				ev.addRecord("Delete", "Delete Coach", "Coach PK:"+PKCoach, userDetials.getLoginName(),companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());
			} 
			catch (Exception E)
			{
				System.err.println("Coach.java - deleteCoach - " + E);
				
			}

			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		return bIsDeleted;
	}
	

	public boolean updateCoach(int PKCoach,String CoachName,String Link)throws SQLException, Exception{
	
		String sql = "Update Coach Set CoachName = '" + CoachName +"', Link = '" + Link +"' where PKCoach = " + PKCoach;
		//System.out.println(sql);
		Connection con = null;
		Statement st = null;
        boolean bIsUpdated = false;
			
			try
			{

				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				if(iSuccess!=0)
				bIsUpdated=true;
				ev.addRecord("Update", "Update Coach", "Update Coach, CoachPK:"+PKCoach, userDetials.getLoginName(), companyDetail.getCompanyName(), votblOrganizationDetail.getOrganizationName());
			} 
			catch (Exception E)
			{
				System.err.println("Coach.java - updateSlot - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		
		return bIsUpdated;
		
	}
	

}
