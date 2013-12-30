/*
 * (04-10-2005) Maruli
 * Fix getAllRecord_SurveyRating so that rating scale can be shown in Fix Rating Task
 */

package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voCompetency;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblRatingSetup;
import CP_Classes.vo.votblSurveyBehaviour;

public class AdvanceSettings
{
	/**
	 * Declaration of classes 
	 */
	private EventViewer ev;
	private Create_Edit_Survey user;
	
	private int Rating =0;
	private int RatingTaskID [];
	private String sDetail[] = new String[13];
	private String itemName = "Advance Settings";
	
	/**
	 * Creates a new intance of AdvanceSettings object.
	 */
	public AdvanceSettings()
	{
		ev = new EventViewer();
		user = new Create_Edit_Survey();
		
		RatingTaskID = new int [4];
	}	
	
	/**
	 * Insert into tblRatingSetup
	 * @param SurveyID
	 * @param CompetencyID
	 * @param RatingTaskID
	 * @param Score
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean insert_tblRatingSetup(int SurveyID, int CompetencyID, int RatingTaskID, float Score, int PKUser) throws SQLException, Exception 
	{
		
		String command3  = "INSERT INTO tblRatingSetup (SurveyID, CompetencyID, RatingTaskID, Score) VALUES ("+SurveyID+", "+CompetencyID+", "+RatingTaskID+", "+Score+")";
		Connection con = null;
		Statement st = null;		
		boolean bIsAdded = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsAdded=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - insert_tblRatingSetup - " + E);
			update_tblRatingSetup(SurveyID, CompetencyID, RatingTaskID, Score, PKUser);
			
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}

		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Insert Rating Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}		
		
		return bIsAdded;
	}
	
	/**
	 * Insert rating scale value into tblRatingSetup (Fix score)
	 * @param SurveyID
	 * @param CompetencyID
	 * @param RatingTaskID
	 * @param Score
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean insertRatingSetup(int SurveyID, int CompetencyID, int iRatingTaskID, float Score, int PKUser) throws SQLException, Exception 
	{
		
		String command3 = "INSERT INTO tblRatingSetup (SurveyID, CompetencyID, RatingTaskID, Score) VALUES ("+SurveyID+", "+CompetencyID+", "+iRatingTaskID+", "+Score+")";
			
		
		Connection con = null;
		Statement st = null;		
		boolean bIsAdded = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsAdded=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - insertRatingSetup - " + E);
			updateRatingSetup(SurveyID, CompetencyID, 2, Score, PKUser);
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Insert Rating Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}		
		
		return bIsAdded;
	}
	
	/**
	 * 	Update tblSurveyRating set adminSetup
	 *	0 = default; 1 = show; 2 = hide
	 */
	 public void update_adminSetup(int SurveyID, int RatingTaskID, int AdminSetup, int PKUser) throws SQLException, Exception 
	 {

		int value = 0; //not for any purpose
		int anyrecord = 0;			
		String command3  = "";

		if(AdminSetup != 2)
		{
			String query = "SELECT * FROM tblRatingSetup WHERE (SurveyID = "+SurveyID+") AND (RatingTaskID = "+RatingTaskID+")";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			try{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(query);
				while(rs.next()){
					value = 1;
				
				}

			}catch(SQLException SE)
			{
				System.out.println("AdvanceSettings.java - update_adminSetup - "+SE.getMessage());
			}finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

			}


			if(value != 1)
				value = 0;
			
			command3 = "UPDATE tblSurveyRating SET AdminSetup ="+value+" WHERE RatingTaskID ="+RatingTaskID+" AND SurveyID = "+SurveyID;
				
			boolean bIsUpdated = false;
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(command3);
				if(iSuccess!=0)
				bIsUpdated=true;

	  		
			} 
			catch (Exception E)
			{
				System.err.println("AdvanceSettings.java - update_adminSetup - " + E);
				
			}
			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		}
		else
		{
			String query1 = "SELECT * FROM tblRatingSetup WHERE (SurveyID = "+SurveyID+") AND (RatingTaskID = "+RatingTaskID+")";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			try{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(query1);

				while(rs.next())
				{
					value = 2;
					anyrecord =1;
				}

			}catch(SQLException SE)
			{
				System.out.println("AdvanceSettings.java - update_adminSetup - "+SE.getMessage());
			}finally{
				ConnectionBean.closeRset(rs); //Close ResultSet
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection

			}

			
			if(anyrecord == 0 && value == 1)
				value = 0;

			command3 = "UPDATE tblSurveyRating SET AdminSetup ="+value+" WHERE RatingTaskID ="+RatingTaskID+" AND SurveyID = "+SurveyID;
				
			boolean bIsUpdated = false;
			try
			{
				con=ConnectionBean.getConnection();
				st=con.createStatement();
				int iSuccess = st.executeUpdate(command3);
				if(iSuccess!=0)
				bIsUpdated=true;

	  		
			} 
			catch (Exception E)
			{
				System.err.println("AdvanceSettings.java - update_adminSetup - " + E);
				
			}
			finally
			{
				ConnectionBean.closeStmt(st); //Close statement
				ConnectionBean.close(con); //Close connection
			}
		}	

		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Update Admin Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
	}
	
	/**
	 * Update into tblRatingSetup
	 */
	public boolean update_tblRatingSetup(int SurveyID, int CompetencyID, int RatingTaskID, float Score, int PKUser) throws SQLException, Exception 
	{

		String command3  = "UPDATE tblRatingSetup SET CompetencyID ="+CompetencyID+", RatingTaskID ="+RatingTaskID+", Score = "+Score+" WHERE SurveyID = "+SurveyID;

		Connection con = null;
		Statement st = null;		
		boolean bIsUpdated = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsUpdated=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - update_tblRatingSetup - " + E);
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Update Rating Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	}
	
	/**
	 * Update tblRatingSetup (Fix Score)
	 * @param SurveyID
	 * @param CompetencyID
	 * @param RatingTaskID
	 * @param Score
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean updateRatingSetup(int SurveyID, int CompetencyID, int RatingTaskID, float Score, int PKUser) throws SQLException, Exception 
	{
		
		String command3  = "UPDATE tblRatingSetup SET CompetencyID ="+CompetencyID+", RatingTaskID ="+RatingTaskID+", Score = "+Score+" WHERE SurveyID = "+SurveyID;
	
		Connection con = null;
		Statement st = null;		
		boolean bIsUpdated = false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsUpdated=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - updateRatingSetup - " + E);
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Update Rating Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	}
	
	
	/**
	 * Delete from tblRatingSetup
	 */
	public boolean delete_tblRatingSetup(int SurveyID, int PKUser) throws SQLException, Exception 
	{
		Connection con = null;
		Statement st = null;

		String command3  = "DELETE FROM tblRatingSetup WHERE SurveyID = "+SurveyID;
		
		boolean bIsDeleted = false;
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsDeleted=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - delete_tblRatingSetup - " + E);
			
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Update Rating Setup", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsDeleted;
	}
	
	/**
	 * Delete from tblRatingSetup specifically
	 */
	public boolean delete_tblRatingSetup_Spec(int SurveyID, int RatingTaskID, int PKUser) throws SQLException, Exception 
	{
		String command3  = "DELETE FROM tblRatingSetup WHERE SurveyID = "+SurveyID+" AND RatingTaskID="+RatingTaskID;
		boolean bIsDeleted = false;
		
		Connection con = null;
		Statement st = null;		
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsDeleted=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - delete_tblRatingSetup_Spec - " + E);
			
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Delete Rating Setup specifically", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsDeleted;
	}
	
	/**
	 * Delete from tblRatingSetup (Fix Score)
	 * @param SurveyID
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean deleteRatingSetup_Spec(int SurveyID, int iRatingTaskID, int PKUser) throws SQLException, Exception 
	{
		String command3  = "DELETE FROM tblRatingSetup WHERE SurveyID = "+SurveyID+" AND RatingTaskID = " + iRatingTaskID;
		
		boolean bIsDeleted = false;
		
		Connection con = null;
		Statement st = null;		
		
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command3);
			if(iSuccess!=0)
			bIsDeleted=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - delete_tblRatingSetup_Spec - " + E);
			
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection


		}
		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Insert", itemName, "Delete Rating Setup specifically", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsDeleted;
	}
	
	/**
	 * WHICH JSP?
	 */
	/**
	 * 
	 * @param SurveyID
	 * @return ResultSet
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector getAllRecord_RatingSetup(int SurveyID) throws SQLException, Exception 
	{
		String query = "SELECT * FROM tblRatingSetup WHERE SurveyID = "+SurveyID+" ORDER BY CompetencyID, RatingTaskID";

		Vector v = new Vector();
    	

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
            	
            	votblRatingSetup vo = new votblRatingSetup();
            	
            	vo.setCompetencyID(rs.getInt("CompetencyID"));
            	vo.setRatingTaskID(rs.getInt("RatingTaskID"));
            	vo.setScore(rs.getInt("Score"));
            	vo.setSurveyID(rs.getInt("SurveyID"));
            	
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
	 * Retrieves all the Competencies from tblSurveyCompetency table.
	 * 
	 * @param SurveyID
	 * @param PKUser
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vector getAllRecord_SurveyCompetency(int SurveyID, int PKUser) throws SQLException, Exception 
	{
		String query = "SELECT * FROM tblSurveyCompetency a, Competency b WHERE a.CompetencyID = b.PKCompetency ";
		query = query + "AND SurveyID = "+SurveyID+" ORDER BY PKCompetency";	
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        Vector v = new Vector();
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
      
        	while(rs.next())
            {
        		voCompetency vo = new voCompetency();
        		int iFKCompetency = rs.getInt("PKCompetency");
        		vo.setPKCompetency(iFKCompetency);
        		String sCompetencyName = rs.getString("CompetencyName");
        		vo.setCompetencyName(sCompetencyName);
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("AdvanceSettings.java - getAllRecord_SurveyCompetency - " + E);
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
	 * Retrieves all the Rating from tblSurveyRating table.
	 */
	public Vector getAllRecord_SurveyRating(int SurveyID, int PKUser)
	{
		String query = "SELECT a.*, b.*, tblScale.ScaleRange FROM tblSurveyRating a INNER JOIN ";
		query = query + "tblRatingTask b ON a.RatingTaskID = b.RatingTaskID INNER JOIN tblScale ON a.ScaleID = tblScale.ScaleID ";
		query = query + "WHERE (a.SurveyID = "+SurveyID+") AND (b.RatingTaskID <> 1) ORDER BY b.RatingTaskID ";
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
		Vector v = new Vector();
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query);
      
        	while(rs.next())
            {
        		voCompetency vo = new voCompetency();
        		vo.setRatingTaskID(rs.getInt("RatingTaskID"));
        		vo.setRatingName(rs.getString("RatingTaskName"));
        		vo.setAdminSetup(rs.getInt("AdminSetup"));
        		vo.setScaleRange(rs.getInt("ScaleRange"));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("AdvanceSettings.java - getAllRecord_SurveyCompetency - " + E);
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
	 *	Update Self comment in tblSurvey
	 */
	public boolean update_SelfComment(int SurveyID, int Self_Comment_Included, int PKUser) throws SQLException, Exception 
	{
		
		String command = "UPDATE tblSurvey SET Self_Comment_Included = "+Self_Comment_Included+" WHERE SurveyID ="+SurveyID;
	
		Connection con = null;
		Statement st = null;		
		boolean bIsUpdated= false;
		try
		{
			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command);
			if(iSuccess!=0)
			bIsUpdated=true;

  		
		} 
		catch (Exception E)
		{
			System.err.println("AdvanceSettings.java - update_SelfComment - " + E);
		}
		finally
		{
			ConnectionBean.closeStmt(st); //Close statement
			ConnectionBean.close(con); //Close connection
		}
		
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Update", itemName, "Update Self Comment", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	}
	
	/**
	 * Update Development Map breakdown in tblSurvey
	 * @param SurveyID
	 * @param iBreakdown - 1=true, 0=false
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 * @author Maruli
	 */
	public boolean update_Breakdown(int SurveyID, int iBreakdown, int PKUser) throws SQLException, Exception 
	{
		
		String command = "UPDATE tblSurvey SET DMapBreakdown = "+iBreakdown+" WHERE SurveyID ="+SurveyID;
		
		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;
        
		try	
		{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
			
		catch(Exception E)
		{
	        System.err.println("AdvanceSettings.java - update_Breakdown - " + E);
		}
		
		finally
    	{
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection


    	}
			
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Update", itemName, "Update Dev Map Breakdown", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	}
	
	/**
	 * To check whether a survey has the DMap broken down into sub-categories
	 * @param iSurvey
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @author Maruli
	 */
	public boolean bIsBreakdown(int iSurvey) throws SQLException, Exception
	{
		int i = 0;
		boolean isBreakdown = false;
		
		String sSQL = "SELECT DMapBreakdown FROM tblSurvey WHERE SurveyID = " + iSurvey;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sSQL);
        	
    		if(rs.next())
    			i = rs.getInt("DMapBreakdown"); 
            
        }
        catch(Exception E) 
        {
            System.err.println("AdvanceSettings.java - bIsBreakdown - " + E);
        }
        finally
        {
	        ConnectionBean.closeRset(rs); //Close ResultSet
	        ConnectionBean.closeStmt(st); //Close statement
	        ConnectionBean.close(con); //Close connection
        }


		if(i == 0)
			isBreakdown = false;
		else
			isBreakdown = true;
		
		return isBreakdown;
	}
	
        // Added by DeZ, 19/06/08, to add option to enable/disable automatic assign Self and/or Superior as rater
	/**
	 * Update mode of Auto Assigning Self as Rater in tblSurvey
	 * @param SurveyID
	 * @param iAutoSelf - 1=true, 0=false
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 * @author DeZ
	 */
	public boolean update_AutoAssignSelf(int SurveyID, boolean iAutoSelf, int PKUser) throws SQLException, Exception 
	{
            //System.out.println(">>Inside update: " + iAutoSelf);
                //boolean autoSelf = true;
                
                //if( iAutoSelf == true ) autoSelf = false;
            
		String command = "UPDATE tblSurvey SET AutoAssignSelf = '"+ iAutoSelf +"' WHERE SurveyID ="+SurveyID;
            //System.out.println(">>SQL: " + command);	
		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;
        
		try	
		{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
			
		catch(Exception E)
		{
	        System.err.println("AdvanceSettings.java - update_AutoAssignSelf - " + E);
		}
		
		finally
    	{
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection


    	}
			
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Update", itemName, "Update Auto Assign Self Mode", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	} // end update_AutoAssignSelf()
	
	/**
	 * Update mode of Auto Assigning Superior as Rater in tblSurvey
	 * @param SurveyID
	 * @param iAutoSup - 1=true, 0=false
	 * @param PKUser
	 * @throws SQLException
	 * @throws Exception
	 * @author DeZ
	 */
	public boolean update_AutoAssignSup(int SurveyID, boolean iAutoSup, int PKUser) throws SQLException, Exception 
	{
            //System.out.println(">>Inside update_AutoAssignSup: " + iAutoSup);
                //boolean autoSelf = true;
                
                //if( iAutoSelf == true ) autoSelf = false;
            
		String command = "UPDATE tblSurvey SET AutoAssignSuperior = '"+ iAutoSup +"' WHERE SurveyID ="+SurveyID;
            //System.out.println(">>SQL: " + command);	
		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;
        
		try	
		{

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			int iSuccess = st.executeUpdate(command);
			if(iSuccess!=0)
			bIsUpdated=true;

		}
			
		catch(Exception E)
		{
	        System.err.println("AdvanceSettings.java - update_AutoAssignSup - " + E);
		}
		
		finally
    	{
		ConnectionBean.closeStmt(st); //Close statement
		ConnectionBean.close(con); //Close connection


    	}
			
		sDetail = user.getUserDetail(PKUser);
		try {
			ev.addRecord("Update", itemName, "Update Auto Assign Superior Mode", sDetail[2], sDetail[11], sDetail[10]);
		} catch (SQLException SE) {
			System.out.println(SE.getMessage());
		}
		
		return bIsUpdated;
	} // end update_AutoAssignSup()
        
        
	/**
	 * Store Bean Variable Rating
	 */	
	public void setRating(int Rating) 
	{
		this.Rating = Rating;
	}
	
	/**
	 * Get Bean Variable Rating.
	 */
	public int getRating() 
	{
		return Rating;
	}
	
	/**
	 * Store Bean Variable toggle either 1 or 0.
	 */	
	public void setRatingTaskID(int [] value) 
	{
		RatingTaskID = value;
	}

	/**
	 * Get Bean Variable toggle.
	 */
	public int [] getRatingTaskID() 
	{
		return RatingTaskID;
	}
}