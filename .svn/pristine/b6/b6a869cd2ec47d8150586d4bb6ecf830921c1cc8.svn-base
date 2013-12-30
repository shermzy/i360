package CP_Classes;

import java.sql.*;

import CP_Classes.common.ConnectionBean;


class Main
{
	public double AvgLevelOfAgreement(int surveyID, int compID) throws SQLException, Exception
	{
		String query = "";
		//double LOA = -1;
		//Get No of Raters
		GroupReport G = new GroupReport();
		
		int iNoOfRaters = G.getTotRatersCompleted(surveyID, 0, 0, 0);
		int iBase = 24;//C.getLOABase(iNoOfRaters);
		double avg = 100;
		double LOA = 0; //Default 100%
		
		int iMaxScale = 10;//rscale.getMaxScale(surveyID); //Get Maximum Scale of this survey
		
		query += "SELECT DISTINCT ";
        query += "tblAssignment.TargetLoginID, tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, ";
        query += "CAST(100 - STDEV(tblAvgMeanByRater.AvgMean * 10 / "+iMaxScale+") * "+iBase+" AS numeric(38, 2)) AS LOA, [User].LoginName, [User].GivenName ";
        query += "FROM tblAssignment INNER JOIN ";
        query += "tblAvgMeanByRater ON tblAssignment.AssignmentID = tblAvgMeanByRater.AssignmentID INNER JOIN ";
        query += "tblRatingTask ON tblAvgMeanByRater.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
        query += "[User] ON tblAssignment.TargetLoginID = [User].PKUser ";
        query += "WHERE (tblAssignment.SurveyID = "+surveyID+") AND (tblAssignment.RaterStatus IN (1, 2, 4)) AND (tblAvgMeanByRater.CompetencyID = "+compID+") AND ";
        query += "(tblAssignment.RaterCode <> 'SELF') AND (tblRatingTask.RatingCode = 'CP') ";

		query += "GROUP BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, tblAssignment.TargetLoginID, [User].LoginName, [User].GivenName ";

		//System.out.println("---------------------");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

//			Filter if more than 1 rater, get LOA from calculation, else if 1 Rater, LOA = 100%
			if(iNoOfRaters > 1) {
				double sum = 0;
				int count = 0;
				
				while(rs.next()) {
					LOA = rs.getDouble("LOA");
					sum = sum + LOA;
					count ++;
					//System.out.println(LOA +"--------" + rs.getInt("TargetLoginID"));
				}
				
				avg = sum/count;
				avg = Math.round(avg*100)/100.0;
			
			}
            
        }
        catch(Exception E) 
        {
            System.err.println("Main.java - AvgLevelOfAgreement - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }

		return avg;
	}
	
	public double LevelOfAgreement(int surveyID, int compID, int KBID) throws SQLException, Exception
	{
		String query = "";
		int surveyLevel = 1;
		
		int divID = 0;
		int groupSection = 0;
		int deptID = 0;
		
		if(surveyLevel == 1) 
		{

		
			query = "SELECT ROUND(AVG(LevelOfAgreement), 2) AS LOA FROM tblLevelOfAgreement WHERE SurveyID = " + surveyID + 
					"AND CompetencyID = " + compID + " AND KeyBehaviourID = " + KBID + " and TargetLoginID IN " + 
					"(SELECT DISTINCT TargetLoginID FROM tblAssignment WHERE SurveyID = " + surveyID;
			if(divID != 0) 
				query = query + " AND tblAssignment.FKTargetDivision = " + divID;
			if(deptID != 0) 
				query = query + " AND tblAssignment.FKTargetDepartment = " + deptID;
			if(groupSection != 0)
				query = query + " AND tblAssignment.FKTargetGroup = " + groupSection;
			
			query += ") GROUP BY CompetencyID, KeyBehaviourID";
		}
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int iNoOfRaters = 100;
		double LOA = 0;
		
		try
        {          

			con=ConnectionBean.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);

			//Filter if more than 1 rater, get LOA from calculation, else if 1 Rater, LOA = 100%
			if(iNoOfRaters > 1)	{
				if(rs.next())
					LOA = rs.getDouble("LOA");
			}
            
        }
        catch(Exception E) 
        {
            System.err.println("Main.java - LevelOfAgreement - " + E);
        }
        finally
        {
        	ConnectionBean.closeRset(rs); //Close ResultSet
        	ConnectionBean.closeStmt(st); //Close statement
        	ConnectionBean.close(con); //Close connection
        }
		
		return LOA;
	}
	
	public static void main (String [] args)throws SQLException, Exception {
		
		//C.IndividualLevelOfAgreement(432, 136);
	
		//SurveyResult S = new SurveyResult();
		//S.Calculate(465,1);
		Main M = new Main();
		//M.AvgLevelOfAgreement(470, 702);
		System.out.println(M.LevelOfAgreement(470, 702, 1924));
	}
}