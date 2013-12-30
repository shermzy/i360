package CP_Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voAge;
import CP_Classes.vo.votblRatingTask;
import CP_Classes.vo.votblSurvRatingTask;
import CP_Classes.vo.votblSurveyRating;

public class SurveyRating {

	/**
	 * delete the rating from the survey
	 * 
	 * @param SurveyID
	 * @return true if deleted successfully
	 * @throws SQLException
	 * @throws Exception
	 * @author Yuni
	 */
	public boolean deleteSurveyRating(int iSurveyID) {
		
		String sql = "DELETE FROM tblSurveyRating WHERE SurveyID = "+iSurveyID;
		
		Connection con = null;
		Statement st = null;
		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception ex) {
			System.out.println("SurveyRating.java - deleteSurveyRating - "
							+ ex.getMessage());
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		
		return bIsDeleted;

	}
	
	/*public votblSurveyRating getSurveyRating(int iSurveyID) {
		String query5 ="SELECT * FROM tblSurveyRating WHERE RatingTaskID= 3 AND SurveyID=" +iSurveyID+" OR RatingTaskID= 5 AND SurveyID=" +iSurveyID;
	
		votblSurveyRating vo = new votblSurveyRating();
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		vo.setPKAge(rs.getInt("PKAge"));
        		vo.setAgeRangeTop(rs.getInt("AgeRangeTop"));
            	
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getSurveyRating - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return vo;
		
	}*/
	
	public boolean existSurveyRating(int iSurveyID) {
		String query5 ="SELECT * FROM tblSurveyRating WHERE RatingTaskID= 3 AND SurveyID=" +iSurveyID+" OR RatingTaskID= 5 AND SurveyID=" +iSurveyID;
	
		boolean bExist = false;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		bExist = true;
            }
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - existSurveyRating - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return bExist;
		
	}
	
	public int getSurveyScaleRange(int iSurveyID) {
		String sql ="SELECT tblSurveyRating.SurveyID, tblScale.ScaleRange FROM tblSurveyRating INNER JOIN tblScale ON tblSurveyRating.ScaleID = tblScale.ScaleID WHERE tblSurveyRating.SurveyID = " + iSurveyID;
		
		int iScaleRange = 10;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(sql);
      
        	if(rs.next())
            {
        		iScaleRange = rs.getInt("ScaleRange");
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getSurveyScaleRange - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return iScaleRange;
		
	}
	
	public Vector getSurveyRatingTask(int iSurveyID) {
		String query2 ="SELECT a.RatingTaskID, a.RatingTaskName, c.ScaleDescription FROM tblSurveyRating a, tblRatingTask b, tblScale c WHERE a.RatingTaskID = b.RatingTaskID AND a.ScaleID = c.ScaleID AND SurveyID=" +iSurveyID;

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
        		votblSurvRatingTask vo = new votblSurvRatingTask();
        		vo.setFKRatingTaskID(rs.getInt("RatingTaskID"));
        		vo.setSurvRatingTask(rs.getString("RatingTaskName"));
        		vo.setScaleDescription(rs.getString("ScaleDescription"));
            	
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getSurveyRating - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return v;
		
	}
	
	public int getRatingTaskID(int iSurveyID) {
		String query5 = "SELECT RatingTaskID FROM tblSurveyRating WHERE SurveyID=" +iSurveyID+" AND RatingTaskID = 2 OR SurveyID=" +iSurveyID+" AND RatingTaskID = 3";
		
		int iRatingTaskID = 0;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		iRatingTaskID = rs.getInt(1);
            }
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getRatingTaskID - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return iRatingTaskID;
		
	}
	
	public String getRatingTaskName(int iSurveyID, int iRTID) {
		String query5 = "SELECT * FROM tblSurveyRating WHERE SurveyID = "+iSurveyID+" AND RatingTaskID=" +iRTID;
		
		String sRatingTaskName = "";
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		sRatingTaskName = rs.getString("RatingTaskName");
            }
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getRatingTaskName - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return sRatingTaskName;
		
	}
	
	public Vector getAllPurpose(int iSurveyID, boolean dunAddPR, int iPurposeID) {
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
        		vo.setScaleType(rs.getString("SclaeType"));
            	v.add(vo);
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("Purpose.java - getAllPurpose - " + E);
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
	 * get Survey Fixed Score
	 * 
	 * @param iSurveyID
	 * @param iCompID
	 * @param iRatingTaskID
	 * @return
	 */
	public Vector getSurveyFixedScore(int iSurveyID, int iCompID, int iRatingTaskID) {
		String command = "SELECT * FROM tblRatingSetup WHERE SurveyID = "+iSurveyID+" AND CompetencyID ="+iCompID+" AND RatingTaskID ="+iRatingTaskID;
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        Vector v = new Vector();
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(command);
      
        	while(rs.next())
            {
        		float Score = rs.getFloat("Score");
            	v.add(new Float(Score));
            }
            
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getSurveyRating - " + E);
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
	 * 	Get rating task description from database
	 * 
	 *	@author Jinghan
	 * 	@param iSurveyID
	 *  @param iRTID
	 * 
	 */
	public String getRatingTaskDesc(int iSurveyID, int iRTID) {
		String query5 = "SELECT * FROM tblSurveyRating WHERE SurveyID = "+iSurveyID+" AND RatingTaskID=" +iRTID;
		
		String sRatingTaskDesc = "";
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		sRatingTaskDesc = rs.getString("RTDescription");
            }
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getRatingTaskDesc - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return sRatingTaskDesc;
	}
	
	/**
	 * 	get the display code specified by user from database
	 * 
	 *  @author Jinghan
	 *  @param iSurveyID
	 *  @param iRTID
	 * 
	 * 
	 * */
	public String getRTDisplayCode(int iSurveyID, int iRTID) {
		String query5 = "SELECT * FROM tblSurveyRating WHERE SurveyID = "+iSurveyID+" AND RatingTaskID=" +iRTID;
		
		String RTDisplayCode = "";
		
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try
        {          
        	con=ConnectionBean.getConnection();
        	st=con.createStatement();
        	rs=st.executeQuery(query5);
      
        	if(rs.next())
            {
        		RTDisplayCode = rs.getString("RTDisplayCode");
            }
        }
        catch(Exception E) 
        {
            System.err.println("SurveyRating.java - getRTDisplayCode - " + E);
        }
        finally
        {

        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection

        }
		
		return RTDisplayCode;
	}
	
}
