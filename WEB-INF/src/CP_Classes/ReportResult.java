package CP_Classes;

import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;

public class ReportResult
{
	private Database db;
	private Calculation C;
	
	
	public ReportResult() {
		db = new Database();
		C = new Calculation();
	}
	
	/**
	  * Retrieve the Target result that has been calculated to be generated in Individual Report.
	  * Group 1 = All, 2 = Others, 3 = Sup, 4 = Self
	  */
	public Vector TargetResult(int surveyID, int targetID, int group, int compID, int KBID) {
		String query = "";
		
		int surveyLevel = C.LevelOfSurvey(surveyID);
		
		
		if(surveyLevel == 0) {
			query = query + "SELECT tblTrimmedMean.RatingTaskID, Competency.CompetencyName, ";
			query = query + "tblTrimmedMean.TrimmedMean FROM tblTrimmedMean INNER JOIN tblRatingTask ON ";
			query = query + "tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query + "Competency ON tblTrimmedMean.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblTrimmedMean.Type = " + group;
			query = query + " AND tblTrimmedMean.SurveyID = " + surveyID + " and ";
			query = query + "tblTrimmedMean.TargetLoginID = " + targetID + " and ";
			query = query + "tblTrimmedMean.CompetencyID = " + compID;
			query = query + " ORDER BY tblTrimmedMean.RatingTaskID";

		} else {
			query = query + "SELECT tblTrimmedMean.RatingTaskID, Competency.CompetencyName, ";
			query = query + "tblTrimmedMean.TrimmedMean, KeyBehaviour.KeyBehaviour FROM tblTrimmedMean INNER JOIN tblRatingTask ON ";
			query = query + "tblTrimmedMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query + "Competency ON tblTrimmedMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query = query + "KeyBehaviour ON tblTrimmedMean.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblTrimmedMean.Type = " + group + " AND tblTrimmedMean.SurveyID = " + surveyID;
			query = query + " AND tblTrimmedMean.TargetLoginID = " + targetID + " and ";
			query = query + "tblTrimmedMean.CompetencyID = " + compID + " and tblTrimmedMean.KeyBehaviourID = " + KBID;
			query = query + " ORDER BY tblTrimmedMean.RatingTaskID";							
		}	
				
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
            	
            	String [] arr = new String[3];
            	arr[0] = rs.getString(1);
            	arr[1] = rs.getString(2);
            	arr[2] = rs.getString(3);
            	
            	v.add(arr);
            }
        }
        catch(Exception E) 
        {
            System.err.println("ReportResult.java - TargetResult - " + E);
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