package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.votblPurpose;
import CP_Classes.vo.votblRatingTask;

/**
 * This class implements all the operations for Rating Task, which is to be used in System Libraries, Survey, and Report..
 */
public class RatingTask
{
	/**
	 * Declaration of new object of class Database. This object is declared private, which is to make sure that it is only accessible within this class Competency.
	 */
	//private Database db;
	
	/**
	 * Bean Variable for sorting purposes. Total Array depends on total SortType.
	 * 0 = ASC
	 * 1 = DESC
	 */
	public int Toggle [];	// 0=asc, 1=desc
	
	/**
	 * Bean Variable to store the Sorting type.
	 */
	public int SortType;
	
	/**
	 * Bean Variable to store the primary key of rating task for editing purpose.
	 */
	public int RTID;
	
	/**
	 * Declaration of new object of class User.
	 */
	private User_Jenty U;
	
	/**
	 * Declaration of new object of class EventViewer.
	 */
	private EventViewer EV;
	

	/**
	 * Creates a new intance of Rating Task object.
	 */
	public RatingTask() {
		//db = new Database();
		U = new User_Jenty();
		EV = new EventViewer();
		
		Toggle = new int [3];
		
		for(int i=0; i<3; i++)
			Toggle[i] = 0;
			
		SortType = 1;
	}
	
	

	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */
	public void setToggle(int toggle) {
		Toggle[SortType - 1] = toggle;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int getToggle() {
		return Toggle [SortType - 1];
	}	

	/**
	 * Store Bean Variable Sort Type.
	 */
	public void setSortType(int SortType) {
		this.SortType = SortType;
	}

	/**
	 * Get Bean Variable SortType.
	 */
	public int getSortType() {
		return SortType;
	}	
	
	/**
	 * Store Bean Variable RTID.
	 */
	public void setRTID(int RTID) {
		this.RTID = RTID;
	}

	/**
	 * Get Bean Variable RTID.
	 */
	public int getRTID() {
		return RTID;
	}	



	public boolean addRecord(String RT, String RTCode, String scaleType, int pkUser) throws SQLException, Exception {
		Connection con = null;
		Statement st = null;

		boolean bIsUpdated=false;
	
		String sql = "Insert into tblRatingTask (RatingCode, RatingTask, ScaleType) values ('" + RTCode + "','" +
							RT + "', '" + scaleType + "')";
			/*
			db.openDB();
			PreparedStatement ps = db.con.prepareStatement(sql);
			ps.executeUpdate();
			db.closeDB();
		*/
			try {
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(sql);
				
				if(iSuccess!=0)
				bIsUpdated=true;

			} catch(SQLException SE) {
				System.out.println("RatingTask.java - addRecord - " + SE.getMessage());
			}finally{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		String [] UserInfo = U.getUserDetail(pkUser);
		
		try {
			EV.addRecord("Insert", "Rating Task", RT, UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println("RatingTask.java - addRecord - " +SE.getMessage());
		}
		
			return bIsUpdated;
	}


	public boolean editRecord(int RatingTaskID, String RT, String RTCode, String scaleType, int pkUser) throws SQLException, Exception {
		String name = getRatingTask(RatingTaskID);
		Connection con = null;
		Statement st = null;
		boolean bIsUpdated=false;
		String sql = "Update tblRatingTask Set RatingTask = '" + RT + "', RatingCode = '" + RTCode + "', ScaleType = '" + scaleType + "' where RatingTaskID = " + RatingTaskID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
		try {
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			
			
			if(iSuccess!=0)
			bIsUpdated=true;

		} catch(SQLException SE) {
			System.out.println("RatingTask.java - editRecord - " + SE.getMessage());
		}finally{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		String [] UserInfo = U.getUserDetail(pkUser);
		
		try {
			EV.addRecord("Update", "Rating Task", "(" + name + ") - (" + RT + ")", UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println("RatingTask.java - editRecord - "+SE.getMessage());
		}
		
		return bIsUpdated;
	}


	public boolean deleteRecord(int RTID, int pkUser) throws SQLException, Exception {
		String name = getRatingTask(RTID);
		
		String sql = "Delete from tblRatingTask where RatingTaskID = " + RTID;
		/*
		db.openDB();
		PreparedStatement ps = db.con.prepareStatement(sql);
		ps.executeUpdate();
		db.closeDB();
		*/
		
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted=false;
		try {

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			
			if(iSuccess!=0)
			bIsDeleted=true;

		} catch(SQLException SE) {
			System.out.println("RatingTask.java - editRecord - " + SE.getMessage());
		}finally{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		String [] UserInfo = U.getUserDetail(pkUser);
		
		try {
			EV.addRecord("Delete", "Rating Task", name, UserInfo[2], UserInfo[11], UserInfo[10]);
		} catch(SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
			return bIsDeleted;
	}


	/**
	 * Retrieves all Rating Task from tblRatingTask.
	 */
	public Vector getAllRecord() throws SQLException, Exception {
		String query = "Select * from tblRatingTask order by ";
		
		if(SortType == 1)
			query = query + "RatingTask";
		else if(SortType == 2)
			query = query + "RatingCode";
		else
			query = query + "ScaleType";
		

		if(Toggle[SortType - 1] == 1)
			query = query + " DESC";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		return rs;
		*/
		Vector v=new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;


		try{
		con=ConnectionBean.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		while(rs.next()){
			votblRatingTask  vo =new votblRatingTask();
			vo.setRatingCode(rs.getString("RatingCode"));
			vo.setRatingTask(rs.getString("RatingTask"));
			vo.setRatingTaskID(rs.getInt("RatingTaskID"));
			vo.setScaleType(rs.getString("ScaleType"));
			
		
			v.add(vo);
		}
		}catch(Exception ex){
			System.out.println("RatingTask.java - getAllRecord - "+ex.getMessage());
		}
		finally{
		ConnectionBean.closeRset(rs); //Close ResultSet
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection
		}
		
		return v;
	}

	/**
	 * Get the total records in Rating Task table.
	 */
	public int getTotalRecord() throws SQLException, Exception {
		String query = "Select count(*) tblRatingTask";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next())
			return rs.getInt(1);
		*/

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
				return rs.getInt(1);
			}catch(Exception ex){
				System.out.println("RatingTask.java - getTotalRecord - "+ex.getMessage());
			}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
			}
			
		return 0;
	}
	
	/**
	 * Get the rating task name based on the code.
	 * Code is unique or each rating task.
	 */
	public String getRatingTask(String RTCode) throws SQLException, Exception {
		String query = "Select RatingTask from tblRatingTask where RatingCode = '" + RTCode + "' order by RatingTask";
		String ratingTask="";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getString(1);
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				ratingTask=rs.getString(1);
		}catch(Exception ex){
			System.out.println("RatingTask.java - getRatingTask - "+ex.getMessage());
		}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		return ratingTask;
	}
	
	/**
	 * Get the rating task name based on the ID.
	 */
	public String getRatingTask(int RTID) throws SQLException, Exception {
		String query = "Select RatingTask from tblRatingTask where RatingTaskID = " + RTID;
		String ratingTask="";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getString("RatingTask");
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				ratingTask=rs.getString(1);
			}catch(Exception ex){
				System.out.println("RatingTask.java -  getRatingTask - "+ex.getMessage());
			}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
			}
		return ratingTask;
	}
	
	
	/**
	 * Check the existance of the particular Rating Task in the database.
	 * Returns: 0 = NOT Exist
	 *		    1 = Exist
	 */
	public int CheckRTExist(String RT, String RTCode, String scaleType) throws SQLException, Exception {
		int pkComp = 0;
		
		String query = "SELECT * FROM tblRatingTask  ";
		query = query + "WHERE RatingCode = '" + RTCode + "' AND ";
		query = query + "RatingTask = '" + RT + "' and ScaleType = '" + scaleType + "'";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next())
			pkComp = rs.getInt(1);
				
		rs.close();
		db.closeDB();
			*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next())
				pkComp = rs.getInt(1);
			}catch(Exception ex){
				System.out.println("RatingTask.java -  CheckRTExist - "+ex.getMessage());
			}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
			}
		
		return pkComp;
	}
	
	public Vector getRatingTask(int iSurveyID, boolean dunAddPR, int iPurposeID) {
		String query2= "";
		
		if(dunAddPR)
		{
			if(iPurposeID != 9)
			{
				query2 ="SELECT * FROM tblRatingTaskPurpose a, tblRatingTask b WHERE (a.RatingTaskID NOT IN(SELECT RatingTaskID FROM tblSurveyRating WHERE SurveyID=" +iSurveyID+")) AND a.RatingTaskID = b.RatingTaskID AND PurposeID=" +iPurposeID;
				query2 = query2 + " AND a.RatingTaskID != 2 AND a.RatingTaskID != 3";
			}
			else
			{
				query2 = "SELECT * FROM tblRatingTask WHERE (RatingTaskID NOT IN(SELECT RatingTaskID FROM tblSurveyRating WHERE SurveyID=" +iSurveyID+"))";
				query2 = query2 + " AND RatingTaskID != 2 AND RatingTaskID != 3";
			}
		}
		else
		{
			if(iPurposeID  != 9)
				query2 ="SELECT * FROM tblRatingTaskPurpose a, tblRatingTask b WHERE (a.RatingTaskID NOT IN(SELECT RatingTaskID FROM tblSurveyRating WHERE SurveyID=" +iSurveyID+")) AND a.RatingTaskID = b.RatingTaskID AND PurposeID=" +iPurposeID;
			
			else
				query2 = "SELECT * FROM tblRatingTask WHERE (RatingTaskID NOT IN(SELECT RatingTaskID FROM tblSurveyRating WHERE SurveyID=" +iSurveyID+"))";
		}
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        Vector v = new Vector();
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query2);
      
        	while(rs.next())
            {
        		votblRatingTask vo = new votblRatingTask();
        		vo.setRatingTaskID(rs.getInt("RatingTaskID"));
        		vo.setRatingTask(rs.getString("RatingTask"));
        		vo.setScaleType(rs.getString("ScaleType"));
            	v.add(vo);
            	
            	System.out.println(rs.getInt("RatingTaskID") + "---" + rs.getString("RatingTask") +"---" + rs.getString("ScaleType")); 
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("RatingTask.java - getRatingTask - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return v;
		
	}
	
	public votblRatingTask getRatingTaskByID(int iRatingTask) {
		String query2= "";
		
		query2 = "Select * from tblRatingTask where RatingTaskID = " + iRatingTask;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        votblRatingTask vo = new votblRatingTask();
 
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query2);
      
        	if(rs.next())
            {
        		
        		vo.setRatingTaskID(rs.getInt("RatingTaskID"));
        		vo.setRatingTask(rs.getString("RatingTask"));
        		vo.setScaleType(rs.getString("ScaleType"));
        		vo.setRatingCode(rs.getString("RatingCode"));
        		
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("RatingTask.java - getRatingTaskByID - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return vo;
		
	}
	
	public Vector getScaleType() {
		String query2= "";
		
		Vector v = new Vector();
		
		query2 = "Select distinct[scaleType] from tblRatingTask";
		
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
        		v.add(rs.getString(1));
        		
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("RatingTask.java - getScaleType - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return v;
		
	}
	
	/*****************************************************
	 * Added by junwei on 5 March 2008
	 * @param iRatingTask
	 * @param iCode
	 * @return true if Rating Task or Code exist in database
	 *******************************************************/
	public boolean checkRatingTaskCodeExist(String iRatingTask, String iCode, int iRatingTaskID){
		boolean result = false;
		String query = "SELECT * FROM tblRatingTask WHERE (RatingTask='" + iRatingTask + "' " +
				"OR RatingCode='" + iCode + "') AND RatingTaskID <> " + iRatingTaskID;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()){
				result = true;
			}
		}
		catch(Exception E) 
        {
            System.err.println("RatingTask.java - checkRatingTaskCodeExist - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
			
		return result;
	}
	
	/**
	 *  Get rating code from table tblRatingTask given the corresponding RTID
	 *  
	 *  @author Jinghan
	 *  @param RTID
	 * 
	 * */
	public String getRTCode(int RTID) throws SQLException, Exception {
		String query = "Select RatingCode from tblRatingTask where RatingTaskID = " + RTID;
		String RTCode="";
		/*
		db.openDB();
		Statement stmt = db.con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next())
			return rs.getString("RatingTask");
		*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			if(rs.next())
				RTCode=rs.getString(1);
			}catch(Exception ex){
				System.out.println("RatingTask.java -  getRTCode - "+ex.getMessage());
			}finally{
			ConnectionBean.closeRset(rs); //Close ResultSet
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
			}
		return RTCode;
	}
}