package CP_Classes;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
/**
 * This class implements all the operations for Group table in the database.
 */
 
public class Group
{

/**
 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Age.
 */

	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private String sDetail[] = new String[13];
 	private String itemName = "Group";
 	private int Department=0;
 
	public Group()
	{
	
		ev = new EventViewer();
		user = new Create_Edit_Survey();
	}
	
	/**
	 * Check Group Exist in the database.
	 *
	 * Parameters:
	 *		String GroupName
	 *
	 */
	public int checkGroupExist(String GroupName) throws SQLException, Exception
	{
		int iPKGroup = 0;
		
		String command = "SELECT * FROM [Group] WHERE GroupName = '"+ GroupName + "'";
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKGroup = rs1.getInt("PKGroup");
		
		rs1.close();
		db.closeDB();*/

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
            	iPKGroup = rs.getInt("PKGroup");
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - checkGroupExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }

		return iPKGroup;
		
	}
	
	/**
	 * Check Group Exist in the database.
	 *
	 * Parameters:
	 *		String GroupName, int FKOrganization
	 *
	 */
	public int checkGroupExist(String GroupName, int FKOrganization) throws SQLException, Exception
	{
		int iPKGroup = 0;
		
		String command = "SELECT * FROM [Group] WHERE GroupName = '"+ GroupName + "' and FKOrganization = " + FKOrganization;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKGroup = rs1.getInt("PKGroup");
		
		rs1.close();
		db.closeDB();*/
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
            	iPKGroup = rs.getInt("PKGroup");
            }
            
         
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - checkGroupExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		return iPKGroup;
		
	}
	
	/**
	 * Check Group Exist in the database.
	 *
	 * Parameters:
	 *		String GroupName, int FKOrganization
	 *
	 */
	public int checkGroupExist(String GroupName, int FKOrganization, int FKDep) throws SQLException, Exception
	{
		int iPKGroup = 0;
		
		String command = "SELECT * FROM [Group] WHERE GroupName = '"+ GroupName + "' and FKDepartment = " + FKDep + " and FKOrganization = " + FKOrganization;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			iPKGroup = rs1.getInt("PKGroup");
		
		rs1.close();
		db.closeDB();*/
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
            	iPKGroup = rs.getInt("PKGroup");
            }
            
         
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - checkGroupExist - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		return iPKGroup;
		
	}
	
	/**
	 * Add a new record to the database.
	 *
	 * Parameters:
	 *		FKDepartment
	 *		GroupName
	 *
	 */
	public boolean addRecord(String GroupName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		//db.openDB();
		//GroupName = db.SQLFixer(GroupName);
		String sql = "INSERT INTO [Group] (GroupName, FKOrganization) VALUES ('"+GroupName+"',"+ FKOrganization+")";
		//PreparedStatement ps = db.con.prepareStatement(sql);
		//ps.executeUpdate();
		
		//System.out.println("Group");
		//db.closeDB();
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
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
            System.err.println("Group.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Insert", itemName, GroupName, sDetail[2], sDetail[11], sDetail[10]);
		
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database without event viewer capturing
	 *
	 * Parameters:
	 *		FKDepartment
	 *		GroupName
	 *
	 */
	public boolean addRecord(String GroupName, int FKOrganization) throws SQLException, Exception 
	{
		//db.openDB();
		
		String sql = "INSERT INTO [Group] (GroupName, FKOrganization) VALUES ('"+GroupName+"',"+ FKOrganization+")";
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();*/
		Connection con = null;
		Statement st = null;


		boolean bIsAdded = false;
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
            System.err.println("Group.java - addRecord - " + E);
		}
		finally
        {
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


        }
		//db.closeDB();
		return bIsAdded;
	}
	
	/**
	 * Add a new record to the database
	 * @param GroupName
	 * @param FKOrganization
	 * @param PKUser
	 * @param FKDepartment
	 * @throws SQLException
	 * @throws Exception
	 * @author Su See
	 */
	public boolean addRecord(String GroupName, int FKOrganization, int PKUser, int FKDepartment) throws SQLException, Exception 
	{
		//db.openDB();
		//GroupName = db.SQLFixer(GroupName);
		String sql = "INSERT INTO [Group] (GroupName, FKOrganization, FKDepartment) VALUES ('"+GroupName+"',"+ FKOrganization+ ", " + FKDepartment +")";
		System.out.println("SQL: " + sql);
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();*/
		Connection con = null;
		Statement st = null;


		boolean bIsAdded = false;
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
            System.err.println("Group.java - addRecord - " + E);
		}
		finally
        {

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

        }
		
		if(PKUser != 0) {
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Insert", itemName, GroupName, sDetail[2], sDetail[11], sDetail[10]);
		}
		
		return bIsAdded;
	}
	
	/**
	 * Edit a record in the database.
	 *
	 * Parameters:
	 *		PKGroup		- primary key
	 *		GroupName 
	 *
	 */
	public boolean editRecord(int PKGroup, String GroupName, int FKOrganization, int PKUser) throws SQLException, Exception 
	{
		String OldName = "";
		String command = "SELECT * FROM [Group] WHERE PKGroup = "+PKGroup;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("GroupName");
		
		rs1.close();
		db.openDB();*/

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
            	OldName = rs.getString("GroupName");
            }
            
           
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - editRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection


        }
		
        String sql = "UPDATE [Group] SET GroupName = '" + GroupName  + "'WHERE PKGroup = " + PKGroup+" AND FKOrganization="+FKOrganization;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB */
		boolean bIsUpdated = false;
        
		try	
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if(iSuccess!=0)
			bIsUpdated=true;

	
		}
			
		catch(Exception E)
		{
	        System.err.println("Group.java - editRecord- " + E);
		}
		
		finally
	   	{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection

	    }
		
		if(PKUser != 0) {
			sDetail = user.getUserDetail(PKUser);
			ev.addRecord("Update", itemName, "("+OldName+") - ("+GroupName+")", sDetail[2], sDetail[11], sDetail[10]);
		}
		return bIsUpdated;
	}
	
	/**
	 * Delete an existing record from the database.
	 *
	 * Parameters:
	 *		PKGroup 	- primary key
	 */
	 
	public boolean deleteRecord(int PKGroup, int FKOrganization, int PKUser) throws SQLException, Exception
	{
		String OldName = "";
		String command = "SELECT * FROM [Group] WHERE PKGroup = "+PKGroup;
		/*ResultSet rs1 = db.getRecord(command);
		if(rs1.next())
			OldName = rs1.getString("GroupName");
		
		rs1.close();
		db.openDB();*/
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
            	OldName = rs.getString("GroupName");
            }
            
            
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - deleteRecord - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
        
        String sql = "Delete from [Group] where PKGroup = " + PKGroup+" AND FKOrganization="+FKOrganization;
		/*PreparedStatement ps = db.con.prepareStatement(sql);
		ps.execute();
		db.closeDB();*/


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
			System.err.println("Group.java - deleteRecord - " + E);
			
		}

		finally
		{

			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		sDetail = user.getUserDetail(PKUser);
		ev.addRecord("Delete", itemName, OldName, sDetail[2], sDetail[11], sDetail[10]);
		return bIsDeleted;
	}
	
	/**
	 * This function will link Group with Department table
	 * @param iPKDepartment
	 * @param iPKGroup
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean linkGroup(int iPKDepartment, int iPKGroup) throws SQLException, Exception
	{
		String sSQL = "UPDATE [Group] SET FKDepartment = "+iPKDepartment+" WHERE PKGroup = "+iPKGroup;
		
		/*db.openDB();
		
		PreparedStatement ps = db.con.prepareStatement(sSQL);
		ps.executeUpdate();
		
		db.closeDB();*/

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;
        
		try	
		{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sSQL);
			if(iSuccess!=0)
			bIsUpdated=true;

	
		}
			
		catch(Exception E)
		{
	        System.err.println("Group.java - linkGroup- " + E);
		}
		
		finally
    	{
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection


    	}
		return bIsUpdated;
	}
	/**
	 * Get Group
	 * 
	 * @param iFKOrg
	 * @param iDepID
	 * @return
	 * @author James
	 */
	public Vector getAllGroups(int iFKOrg,int iDepID){
		String querySql = "SELECT * FROM [Group] WHERE FKOrganization="+iFKOrg+ " AND FKDepartment =" + iDepID + " ORDER BY GroupName";
		Vector v = new Vector();
    	
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(querySql);
        	
            while(rs.next())
            {
            	voGroup vo = new voGroup();
            	vo.setGroupName(rs.getString("GroupName"));
            	vo.setPKGroup(rs.getInt("PKGroup"));
            	vo.setFKOrganization(rs.getInt("FKOrganization"));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - getGroup - " + E);
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
	 * Store Bean Variable Sort Type.
	 */
	public void setDepartment(int Department) {
		this.Department = Department;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getDepartment() {
		return Department;
	}
	
	/**
	 * Get all Group Targets
	 * 
	 * @param iFKOrg
	 * @param iDepID
	 * @return
	 * @author Yuni
	 */
	public Vector getTargetGroups(int iSurveyID, int iDeptID){
		
		String query2 ="SELECT DISTINCT c.PKGroup, c.GroupName FROM tblAssignment a, [User] b, [Group] c WHERE a.RaterLoginID = b.PKUser AND b.Group_Section = c.PKGroup ";
		query2 = query2+ " AND SurveyID = "+iSurveyID;
		query2 = query2+ " AND c.FKDepartment =" + iDeptID;
		query2 = query2+ " ORDER BY c.GroupName";
		
		Vector v = new Vector();
    	

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query2);
           
            while(rs.next())
            {
            	
            	voGroup vo = new voGroup();
            	vo.setGroupName(rs.getString("GroupName"));
            	vo.setPKGroup(rs.getInt("PKGroup"));
            	
            	v.add(vo);
            }
            
        
       
        }
        catch(Exception E) 
        {
            System.err.println("Group.java - getGroup - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
	
        }

        return v;
	}
	
	public static void main(String [] args) {
	
	}

}