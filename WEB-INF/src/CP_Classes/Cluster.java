/**
 * This Cluster class is used to manage clusters of competency
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

public class Cluster {
		
	private EventViewer ev;
	private Create_Edit_Survey user;
	private String sDetail[] = new String[13];
	private String itemName = "Cluster";

    public Cluster(){
			ev = new EventViewer();
			user = new Create_Edit_Survey();
		}
		
    /**
	 * This method retrieve the information of a cluster
	 * @param SpecificID
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
	public voCluster getCluster(int PKCluster){
		voCluster vo = new voCluster();
		
		String command = "SELECT * FROM Cluster WHERE PKCluster = "+ PKCluster;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          
           
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(command);
          
            if(rs.next())
            {
            	vo.setClusterName(rs.getString("ClusterName"));
            	vo.setPKCluster(rs.getInt("PKCluster"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	
            }
       
       
        }
        catch(Exception E) 
        {
            System.err.println("Cluster.java - getCluster - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
		
		return vo;
	}
	
	/**
	 * This method inserts one row into the database table Cluster
     * @param ClusterName - the name of the cluster
     * @param PKUer   - The ID of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
public boolean insertCluster( String ClusterName, int FKOrganization, int PKUser)throws SQLException, Exception {
		
		boolean bIsAdded=false;
		Connection con = null;
		Statement st = null;
        

		String sql = "INSERT INTO Cluster (ClusterName, FKOrganization) VALUES ('"+ ClusterName+"' ," + FKOrganization +")";

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
            System.err.println("Cluster-insertCluster " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, "InsertCluster", sDetail[2], sDetail[11], sDetail[10]);
		return bIsAdded;
		
		
	}
	
	/**
	 * This method gets all the Clusters
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
	public Vector getAllCluster(){
		
		Vector v = new Vector();
		String query = "SELECT * FROM Cluster";
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
            	voCluster vo = new voCluster();
            	vo.setPKCluster(rs.getInt("PKCluster"));
            	vo.setClusterName(rs.getString("ClusterName"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("Cluster.java - getAllCluster - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

        
        return v;

		
	}
	
	
	/**
	 * This method deletes one row from the database table Cluster and all the SurveyClusters of this cluster
	 * @param PKCluster - the ID of the cluster
     * @param PKUser - user id of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	 
	public boolean deleteCluster(int PKCluster,int PKUser) throws SQLException, Exception
	{
		String OldName = "PKCluster = "+ PKCluster;
		String sql = "DELETE FROM Cluster WHERE PKCluster = " + PKCluster;
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
				System.err.println("Cluster.java - deleteCluster - " + E);
				
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
	 * This method updates one row from the database table Cluster
	 * @param PKCluster - the ID of the cluster
	 * @param ClusterName - the new name of the cluster
     * @param PKUser - user id of the user
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	public boolean updateCluster(int PKCluster, String ClusterName, int PKUser)throws SQLException, Exception{
		Cluster cluster = new Cluster();
		String OldName = cluster.getCluster(PKCluster).getClusterName();
		String sql = "UPDATE Cluster SET ClusterName = '" + ClusterName +"' WHERE PKCluster = " + PKCluster;
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
	  		
			} 
			catch (Exception E)
			{
				System.err.println("Cluster.java - UpdateCluster - " + E);
				
			}

			finally
			{

				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection


			}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("update", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
		
		return bIsUpdated;
		
	}
	
	/**
	 * This method gets all the Clusters under an organization from the database table Cluster
	 * @param FKOrganization - The ID of the organization
	 * @throws SQLException
	 * @throws Exception
	 * @author Liu Taichen
	 * Created on: 14 June 2012
	 */
	
	public Vector getOrganizationCluster(int FKOrganizationID){
		
		Vector v = new Vector();
		String query = "SELECT * FROM Cluster WHERE FKOrganization = "+FKOrganizationID;
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
                voCluster vo = new voCluster();
            	vo.setClusterName(rs.getString("ClusterName"));
            	vo.setPKCluster(rs.getInt("PKCluster"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
        		v.add(vo);
            }
           
        }
        catch(Exception E) 
        {
            System.err.println("Cluster.java - getOrganizationCluster - " + E);
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
