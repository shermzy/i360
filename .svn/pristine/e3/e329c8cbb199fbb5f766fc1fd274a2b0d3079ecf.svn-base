/**
 * This OrgCluster class is used to manage clusters of competency
 * @author Liu Taichen
 * @version 1.0      14 June 2012
 */

package CP_Classes;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblOrgCluster;
import CP_Classes.vo.voCluster;

public class OrgCluster {
	
	private EventViewer ev;
	private Create_Edit_Survey user;
	private String sDetail[] = new String[13];
    private String itemName = "OrgCluster";

	public OrgCluster(){
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	

	/**
	 * This method inserts one row into the database table tblOrgCluster
	 * @param FKOrganizationID - The ID of the organization
     * @param FKCluster    - The ID of the relation specific
     * @param FKCompetency  - The ID of the rater relation
     * @param PKUer   - The ID of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
	public boolean insertOrgCluster(int FKCluster, int FKCompetency, int PKUser)throws SQLException, Exception {
		
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;
        

		String sql = "INSERT INTO tblOrgCluster (FKCluster, FKCompetency) VALUES (" + FKCluster+", "+FKCompetency +")";

		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess=st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsAdded=true;


		}
		catch(Exception E)
		{
            System.err.println("OrgCluster-insertOrgCluster " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, "Insert Organization Cluster", sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
		
		
	}
	

	
	/**
	 * This method deletes one row from the database table tblOrgCluster
	 * @param PKOrgCluster - the id of the organization cluster
     * @param PKUser - user id of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	 
	public boolean deleteOrgCluster(int PKOrgCluster, int PKUser) throws SQLException, Exception
	{
		String OldName = "PKOrgCluster = " + PKOrgCluster;
		String sql = "DELETE FROM tblOrgCluster WHERE PKOrgCluster = " + PKOrgCluster;
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
				System.err.println("OrgCluster.java - deleteOrgCluster - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
	
		return bIsDeleted;
	}
	
	/**
	 * This method gets all the OrgCluster under a cluster from the database table tblOrgCluster
	 * @param FKCluster - The ID of the cluster
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
	public Vector getOrgCluster(int FKCluster){
		
		Vector v = new Vector();
		String query = "SELECT * FROM tblOrgCluster WHERE FKCluster= "+FKCluster;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
        try
        { 
        	
        	
         
        	con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
                        
            while(rs.next())
            {
                votblOrgCluster vo = new votblOrgCluster();
            	vo.setFKCompetency(rs.getInt("FKCompetency"));
            	vo.setFKCluster(rs.getInt("FKCluster"));
            	vo.setPKOrgCluster(rs.getInt("PKOrgCluster"));
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("OrgCluster.java - getOrgCluster - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

        
        return v;

		
	}
	

	

}

