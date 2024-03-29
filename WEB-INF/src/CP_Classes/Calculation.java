package CP_Classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Vector;

import CP_Classes.common.ConnectionBean;
import CP_Classes.vo.voGroup;
import CP_Classes.vo.votblSurveyRating;

/**
 * This class implements all calculation process of the 360 Feedback Survey.
 */
public class Calculation implements Serializable{
	/**
	 * Declaration of new object of classes. This object is declared private,
	 * which is to make sure that it is only accessible within this class.
	 */
	private Database db;
	private AssignTarget_Rater assign;
	private RatingScale rscale;

	/**
	 * Bean Variable to store SurveyID.
	 */
	private int SurveyID;

	/**
	 * Bean Variable to store GroupSectionID, this is basically used in Group
	 * Calculation / Report.
	 */
	private int GroupSection;

	/**
	 * Bean Variable to store TargetID, used in calculate target's results,
	 * individual report.
	 */
	private int TargetID;

	/**
	 * Bean Variable to store a particular raterID.
	 */
	private int RaterID;

	/**
	 * Local variable storing RatingTaskID, used as a temporary variable to
	 * store the ID retrieve from a ResultSet.
	 */
	private int RTID;

	/**
	 * Local variable storing CompetencyID, used as a temporary variable to
	 * store the ID retrieve from a ResultSet.
	 */
	private int competencyID;

	/**
	 * Local variable storing KeyBehaviourID, used as a temporary variable to
	 * store the ID retrieve from a ResultSet.
	 */
	private int KBID;

	/**
	 * Local variable storing TrimmedMean Result, used as a temporary variable
	 * to store the result from calculation process.
	 */
	private double trimmedMean;

	/**
	 * Local variable storing Average Mean Result, used as a temporary variable
	 * to store the result from calculation process.
	 */
	private double avgMean;

	/**
	 * Creates a new instance of Calculation Object.
	 */
	public Calculation() {
		db = new Database();
		assign = new AssignTarget_Rater();
		rscale = new RatingScale();
		RTID = 0;
		competencyID = 0;
		KBID = 0;
		trimmedMean = 0;
		avgMean = 0;
	}

	/**
	 * Get the total Competency and Key Behaviour in each rating task for the
	 * specific survey. Fixed for Reliability calculation by Jenty 7 Nov 06
	 * Problem: For KB Level, it counted all KBs as the comparison, which is
	 * supposed to be just competency. Fixed: Now the sql query is changed to
	 * count just total distinct competency for all the different rating task
	 */
	public int TotalResult(int surveyID) {
		String query = "";
		int surveyLevel = LevelOfSurvey(surveyID);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iTotalComp = 0;
		try {
			if (surveyLevel == 0) {
				query = query
						+ "SELECT COUNT(*) AS Total FROM tblSurveyCompetency INNER JOIN ";
				query = query + "tblSurveyRating ON ";
				query = query
						+ "tblSurveyCompetency.SurveyID = tblSurveyRating.SurveyID ";
				query = query
						+ "inner join tblRatingTask on tblRatingTask.RatingTaskID = tblSurveyRating.RatingTaskID ";
				query = query + "WHERE tblSurveyCompetency.SurveyID = "
						+ surveyID;
				// Changed by Ha 27/06/08 need only the CP in reliability
				// calculation
				query = query + " and tblRatingTask.RatingCode = 'CP'";
			} else {

				/*
				 * query = query +
				 * "SELECT count(*) AS Total FROM tblSurveyBehaviour INNER JOIN "
				 * ; query = query + "tblSurveyRating ON "; query = query +
				 * "tblSurveyBehaviour.SurveyID = tblSurveyRating.SurveyID ";
				 * query = query +
				 * "inner join tblRatingTask on tblRatingTask.RatingTaskID = tblSurveyRating.RatingTaskID "
				 * ; query = query + "WHERE tblSurveyBehaviour.SurveyID = " +
				 * surveyID; query = query +
				 * " and tblRatingTask.RatingCode IN('CP', 'CPR', 'FPR')";
				 */
				query = "SELECT DISTINCT tblSurveyRating.RatingTaskID, tblSurveyBehaviour.CompetencyID "
						+ "FROM tblSurveyBehaviour INNER JOIN tblSurveyRating ON "
						+ "tblSurveyBehaviour.SurveyID = tblSurveyRating.SurveyID INNER JOIN tblRatingTask ON "
						+ "tblRatingTask.RatingTaskID = tblSurveyRating.RatingTaskID WHERE "
						+
						// Changed by Ha 27/06/08 need only the CP in
						// reliability calculation
						"tblSurveyBehaviour.SurveyID = "
						+ surveyID
						+ " AND tblRatingTask.RatingCode = 'CP' ";
			}

			// System.out.println(query);

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (surveyLevel == 0) {
				if (rs != null && rs.next())
					iTotalComp = rs.getInt(1);
			} else {
				while (rs != null && rs.next())
					iTotalComp++;
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - totalResult - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iTotalComp;
	}

	/**
	 * Check whether the Survey is Competency Level Analysis or Key Behaviour
	 * Level Analysis, given the SurveyID. Returns : 0 = Competency Level
	 * Analysis 1 = Key Behaviour Level Analysis
	 */
	public int LevelOfSurvey(int surveyID) {
		String query = "";
		int level = 0;

		query = query + "select LevelOfSurvey from tblSurvey where SurveyID = "
				+ surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs != null && rs.next()) {
				level = rs.getInt(1);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - LevelOfSurvey- " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return level;
	}

	/**
	 * Check whether the Survey is Competency Level Analysis or Key Behaviour
	 * Level Analysis, given the AssignmentID. Returns : 0 = Competency Level
	 * Analysis 1 = Key Behaviour Level Analysis
	 */
	public int SurveyLevelByAssignmentID(int assignmentID) {
		String query = "";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int iSurveyLevel = 0;

		try {
			query = query
					+ "SELECT tblSurvey.LevelOfSurvey FROM tblAssignment INNER JOIN ";
			query = query
					+ "tblSurvey ON tblAssignment.SurveyID = tblSurvey.SurveyID ";
			query = query + "WHERE tblAssignment.AssignmentID = "
					+ assignmentID;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iSurveyLevel = rs.getInt(1);
		} catch (Exception E) {
			System.err
					.println("Calculation.java - SurveyLevelByAssignmentID - "
							+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iSurveyLevel;
	}

	/**
	 * Retrieve the Scale Range assigned to the particular SurveyID. Returns :
	 * an array of integer types, length of array depends on total Competency
	 * assigned under the particular SurveyID.
	 */
	public int[] ScaleRange(int surveyID) {
		String query = "";
		int total = TotalRatingTask(surveyID);

		int range[] = new int[total];
		int i = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			query = query
					+ "SELECT tblSurveyRating.RatingTaskID, tblScale.ScaleRange ";
			query = query + "FROM tblSurveyRating INNER JOIN tblScale ON ";
			query = query + "tblSurveyRating.ScaleID = tblScale.ScaleID ";
			query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
			// Changed by Ha 27/06/08: need to get the CP (Rating TaskID = 1)
			// only
			query = query + " AND RatingTaskID = 1";
			query = query + " ORDER BY tblSurveyRating.RatingTaskID";

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next())
				range[i++] = rs.getInt(2);

		} catch (Exception E) {
			System.err.println("Calculation.java - ScaleRange - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return range;
	}

	/**
	 * Check whether the Reliability Check is Trimmed Mean or Average Mean,
	 * given the SurveyID. Returns : 0 = Trimmed Mean 1 = Average Mean
	 */
	public int ReliabilityCheck(int surveyID) {
		String query = "";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iReliabilityCheck = 0;

		try {
			query = query
					+ "select ReliabilityCheck from tblSurvey where SurveyID = "
					+ surveyID;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iReliabilityCheck = rs.getInt(1);
		} catch (Exception E) {
			System.err.println("Calculation.java - RealibilityCheck- " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iReliabilityCheck;
	}

	/**
	 * Check whether the N/A(0) is included in Calculation. Returns : 0 = Not
	 * Included 1 = Included
	 */
	public int NAIncluded(int surveyID) {
		String query = "";
		int na = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			query = query
					+ "select NA_Included from tblSurvey where SurveyID = "
					+ surveyID;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				na = rs.getInt("NA_Included");
		} catch (Exception E) {
			System.err.println("Calculation.java - NAIncluded - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return na;
	}

	/**
	 * Get the AssignmentID based on specific survey, target, and rater ID.
	 */
	public int AssignmentID(int surveyID, int targetID, int raterID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iAssignmentID = 0;
		try {
			String query = "select AssignmentID from tblAssignment where SurveyID = "
					+ surveyID + " and ";
			query = query + "TargetLoginID = " + targetID
					+ " and RaterLoginID = " + raterID + "";

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iAssignmentID = rs.getInt(1);

		} catch (Exception E) {
			System.err.println("Calculation.java - AssignmentID - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iAssignmentID;
	}

	/**
	 * Get the group section for each user based on their ID.
	 */
	public int GroupSection(int pkUser) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iGroupSection = 0;
		try {
			String query = "select Group_Section from [User] where PKUser = "
					+ pkUser;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iGroupSection = rs.getInt(1);
		} catch (Exception E) {
			System.err.println("Calculation.java - GroupSection - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iGroupSection;
	}

	/**
	 * Get the total Rating Task under the particular SurveyID.
	 */
	public int TotalRatingTask(int surveyID) {
		int total = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT tblSurveyRating.SurveyID, tblRatingTask.RatingTaskID, ";
			query = query
					+ "tblRatingTask.RatingCode, tblRatingTask.RatingTask FROM tblRatingTask ";
			query = query + "INNER JOIN tblSurveyRating ON ";
			query = query
					+ "tblRatingTask.RatingTaskID = tblSurveyRating.RatingTaskID WHERE ";
			query = query + "tblSurveyRating.SurveyID = " + surveyID;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				total = rs.getInt(1);
		} catch (Exception E) {
			System.err.println("Calculation.java - TotalRatingTask - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return total;
	}

	/**
	 * Get all the Rating Tasks assigned to a particular Survey.
	 */
	public Vector RatingTask(int surveyID) {

		String query = "SELECT tblSurveyRating.RatingTaskID, tblSurveyRating.RatingTaskName, ";
		query = query
				+ "tblRatingTask.RatingCode FROM tblRatingTask INNER JOIN ";
		query = query + "tblSurveyRating ON ";
		query = query
				+ "tblRatingTask.RatingTaskID =  tblSurveyRating.RatingTaskID ";
		query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
		query = query + " ORDER BY tblSurveyRating.RatingTaskID";

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				votblSurveyRating vo = new votblSurveyRating();
				vo.setRatingTaskID(rs.getInt("RatingTaskID"));
				vo.setRatingTaskName(rs.getString("RatingTaskName"));
				vo.setRatingCode(rs.getString("RatingCode"));
				v.add(vo);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - RatingTask - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return v;

	}

	/*********************************************** RATER"S INFO ******************************************************/

	/**
	 * Retrieve all the results under the specific assignmentID. The results
	 * retrieved are only those belonged to Rating Task CP, CPR, and FPR. If the
	 * Survey is under Key Behaviour Level Analysis, the results are average up
	 * to Competency Level. This method is currently used for Reliability
	 * Calculation.
	 * 
	 * Fixed by Jenty on 7 Dec 06 For KB Level, should just take from
	 * tblAvgMeanByRater since the comp result for each rater has been
	 * calculated and stored here Code edited by Ha 01/07/08
	 */
	public Vector IndividualRaterResultCompLevel(int assignmentID) {
		String query = "";
		SurveyResult SR = new SurveyResult();
		int surveyLevel = SurveyLevelByAssignmentID(assignmentID);
		int surveyID = SR.getSurveyID(assignmentID);
		// int NA_included = NAIncluded(surveyID);

		if (surveyLevel == 0) {
			query = query
					+ "select tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID, Result from tblResultCompetency ";
			query = query
					+ "inner join tblRatingTask on tblRatingTask.RatingTaskID = tblResultCompetency.RatingTaskID ";
			query = query + "where AssignmentID = " + assignmentID;
			// Change by Ha 27/06/08 need only the CP in calculating reliability
			query = query + " AND tblRatingTask.RatingCode = 'CP'";
			query = query
					+ " order by tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID";
		} else {
			/*
			 * query = query +
			 * "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, "
			 * ; query = query +
			 * "cast(avg(tblResultBehaviour.Result) as numeric(38,2)) as Result "
			 * ; query = query +
			 * "FROM tblResultBehaviour INNER JOIN KeyBehaviour ON "; query =
			 * query +
			 * "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour "
			 * ; query = query +
			 * "inner join tblRatingTask on tblRatingTask.RatingTaskID = tblResultBehaviour.RatingTaskID "
			 * ; query = query + "WHERE tblResultBehaviour.AssignmentID = " +
			 * assignmentID;
			 * 
			 * 
			 * query = query +
			 * " AND tblRatingTask.RatingCode in ('CP', 'CPR', 'FPR')"; query =
			 * query +
			 * " group by tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency"
			 * ; query = query +
			 * " ORDER BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency"
			 * ;
			 */

			// Fixed by Jenty on 7 Dec 06
			// For KB Level, should just take from tblAvgMeanByRater
			// since the comp result for each rater has been calculated and
			// stored here
			// Updated to PCC server and sync to CVS
			// Changed by Ha 04/07/08 need only the rating task CP instead of
			// CP, CPR||FPR
			query = "SELECT tblRatingTask.RatingTaskID, tblAvgMeanByRater.CompetencyID, tblAvgMeanByRater.AvgMean as Result "
					+ "FROM tblAvgMeanByRater INNER JOIN tblRatingTask ON "
					+ "tblAvgMeanByRater.RatingTaskID = tblRatingTask.RatingTaskID "
					+ "WHERE tblRatingTask.RatingCode = 'CP' "
					+ "AND tblAvgMeanByRater.AssignmentID = "
					+ assignmentID
					+ " ORDER BY tblRatingTask.RatingTaskID, tblAvgMeanByRater.CompetencyID";
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				String[] arr = new String[3];
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err
					.println("Calculation.java - IndividualRaterResultCompLevel - "
							+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Get all Raters' ID which status is completed, excludes SELF Fixed by
	 * Jenty Chou on 8th Nov 06 - Fixed to calculate rater before the status is
	 * set to complete
	 */
	public int[] RatersID(int surveyID, int targetID, int iFKAssignment,
			int total) {

		String query = "SELECT [User].PkUser, [User].GivenName, [User].FamilyName FROM tblAssignment INNER JOIN ";
		query = query
				+ "[User] ON tblAssignment.RaterLoginID = [User].PKUser WHERE ";
		query = query + "tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query
				+ "tblAssignment.TargetLoginID = "
				+ targetID
				+ " AND "
				+ "(tblAssignment.RaterStatus IN (1,2,4) or tblAssignment.AssignmentID = "
				+ iFKAssignment + ") ";
		query = query + " and RaterCode <> 'SELF' ORDER BY [User].PkUser";

		int raterLoginID[] = new int[total];
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int i = 0;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				int temp = rs.getInt(1);
				raterLoginID[i++] = temp;
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - RatersID - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return raterLoginID;
	}

	/**
	 * Get total Raters for that specific Target EXCLUDES SELF. Note that rater
	 * status must be in Completed(1), Reliable(2), or Unreliable(4).
	 * 
	 * 
	 * Fixed by Jenty Chou on 8th Nov 06 - Fixed to calculate rater before the
	 * status is set to complete
	 */
	public int totalRaters(int surveyID, int targetID, int iFKAssignment) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iTotalRaters = 0;
		String query = "";

		try {
			query = "SELECT COUNT(DISTINCT(Rater.RaterLoginID)) FROM tblAssignment AS Rater ";
			query = query + "WHERE Rater.SurveyID = " + surveyID
					+ " AND Rater.TargetLoginID = ";
			query = query + targetID;
			query = query
					+ " and (Rater.RaterStatus IN (1,2,4) or Rater.AssignmentID = "
					+ iFKAssignment + ") and RaterCode <> 'SELF'";

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iTotalRaters = rs.getInt(1);

		} catch (Exception E) {
			System.err.println("Calculation.java - totalRaters - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return iTotalRaters;
	}

	/**
	 * Get the total raters who have completed the questionnaire for the
	 * specific survey, target, and group. Note that rater status must be in
	 * Completed(1), Reliable(2), or Unreliable(4). This method is basically for
	 * Group Calculation. Group: 1 = ALL EXCLUDES SELF 2 = SUPERIOR (SUP) 3 =
	 * OTHERS (OTH) 4 = SELF
	 * 
	 * Fixed by Jenty Chou on 8th Nov 06 - Fixed to calculate rater before the
	 * status is set to complete
	 */
	public int TotalCount(int surveyID, int targetID, int group,
			int iFKAssignment) {
		String query = "";
		int total = 0;

		String groupName = "";
		String whereStatement = "";

		switch (group) {
		case 1:
			groupName = "SELF";
			break;

		case 2:
			groupName = "SUP%";
			break;

		case 3:
			groupName = "OTH%";
			break;

		case 4:
			groupName = "SELF";
			break;
		}

		query = query + "SELECT COUNT(*) AS TotalRater FROM tblAssignment ";
		query = query + "WHERE SurveyID = " + surveyID + " AND ";
		query = query
				+ "TargetLoginID = "
				+ targetID
				+ " AND (RaterStatus IN (1,2,4) or tblAssignment.AssignmentID = "
				+ iFKAssignment + ") AND";

		if (group == 1)
			whereStatement = " RaterLoginID <> " + targetID;
		else if (group == 4)
			whereStatement = " RaterCode = 'SELF'";
		else
			whereStatement = " RaterCode LIKE '" + groupName + "'";

		query = query + whereStatement;

		// System.out.println(query);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				total = rs.getInt(1);
		} catch (Exception E) {
			System.err.println("Calculation.java - totalCount - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return total;
	}

	/**
	 * Get Individual rater result, this is used when displayed in Process
	 * Result.
	 */
	public Vector IndividualRaterResult(int assignmentID) {
		String query = "";
		int surveyLevel = SurveyLevelByAssignmentID(assignmentID);

		if (surveyLevel == 0) {
			query = query
					+ "select RatingTaskID, CompetencyID, Result, AssignmentID from tblResultCompetency ";
			query = query + "where AssignmentID = " + assignmentID;
			query = query + " order by RatingTaskID, CompetencyID";
		} else {
			query = query
					+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query
					+ " tblResultBehaviour.Result, tblResultBehaviour.KeyBehaviourID ";
			query = query
					+ "FROM tblResultBehaviour INNER JOIN KeyBehaviour ON ";
			query = query
					+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblResultBehaviour.AssignmentID = "
					+ assignmentID;
			query = query
					+ " ORDER BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID";
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = new String[4];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - IndividualRaterResult - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/************************************** TARGET AVERAGE MEAN *******************************************/

	/**
	 * Retrieve target average mean from tblAvgMean based on specific survey,
	 * target, and group. Average Mean stored in this table has already been
	 * calculated in another method. Group: 1 = ALL EXCLUDES SELF 2 = SUPERIOR
	 * (SUP) 3 = OTHERS (OTH) 4 = SELF
	 */
	public Vector getTargetAvgMean(int surveyID, int targetID, int group) {
		String query = "";
		int surveyLevel = LevelOfSurvey(surveyID);

		if (surveyLevel == 0) {
			query = query
					+ "SELECT tblAvgMean.RatingTaskID, Competency.PKCompetency, ";
			query = query
					+ "Competency.CompetencyName, tblAvgMean.AvgMean AS Result ";
			query = query + "FROM tblAvgMean INNER JOIN tblRatingTask ON ";
			query = query
					+ "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query
					+ "Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency ";
			query = query + "WHERE tblAvgMean.Type = " + group
					+ " AND tblAvgMean.SurveyID = " + surveyID;
			query = query + " AND tblAvgMean.TargetLoginID = " + targetID;
			query = query
					+ " ORDER BY tblAvgMean.RatingTaskID, tblAvgMean.CompetencyID";
		} else {
			query = query
					+ "SELECT tblAvgMean.RatingTaskID, Competency.PKCompetency, ";
			query = query
					+ "Competency.CompetencyName, tblAvgMean.AvgMean AS Result, KeyBehaviour.PKKeyBehaviour, ";
			query = query
					+ " KeyBehaviour.KeyBehaviour FROM tblAvgMean INNER JOIN tblRatingTask ON ";
			query = query
					+ "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN ";
			query = query
					+ "Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency INNER JOIN ";
			query = query
					+ "KeyBehaviour ON tblAvgMean.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblAvgMean.Type = " + group
					+ " AND tblAvgMean.SurveyID = " + surveyID;
			query = query + " AND tblAvgMean.TargetLoginID = " + targetID;
			query = query
					+ " ORDER BY tblAvgMean.RatingTaskID, tblAvgMean.CompetencyID, ";
			query = query + "KeyBehaviour.PKKeyBehaviour";
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = null;

				if (surveyLevel == 0) {

					arr = new String[4];

					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					arr[3] = rs.getString(4);

				} else {
					arr = new String[6];

					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					arr[3] = rs.getString(4);
					arr[4] = rs.getString(5);
					arr[5] = rs.getString(6);
				}
				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - getTargetAvgMean - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/************************************************* TRIMMED MEAN ***************************************************/

	/**
	 * Calculate trimmed mean results given by raters, depending on which Rating
	 * Task is chosen. For KB Level, this method calculate TrimmedMean from
	 * tblAvgMeanByRater. tblAvgMeanByRater stores the average mean of each
	 * rater up to Competency Level. Group: 1 = ALL EXCLUDES SELF 2 = SUPERIOR
	 * (SUP) 3 = OTHERS (OTH) 4 = SELF Fixed by Jenty Chou on 8th Nov 06 - Fixed
	 * to calculate rater before the status is set to complete
	 */
	public Vector TrimmedMean(int surveyID, int targetID, int group,
			int iFKAssignment) {

		String query = "";
		String groupName = "";
		String whereStatement = "";
		String formula = "";

		int NA_Included = NAIncluded(surveyID);

		int surveyLevel = LevelOfSurvey(surveyID);
		int totalCount = TotalCount(surveyID, targetID, group, iFKAssignment);
		int totalNotNA = this.getTotalNonNA(surveyID, targetID, group,
				iFKAssignment);

		switch (group) {
		case 1:
			groupName = "SELF";
			break;

		case 2:
			groupName = "SUP%";
			break;

		case 3:
			groupName = "OTH%";
			break;

		case 4:
			groupName = "SELF";
			break;
		}

		if (surveyLevel == 0) {
			query = query + "SELECT tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID, ";

			if (totalCount == 1)
				formula = "sum(tblResultCompetency.Result) AS TrimMean ";
			// Changed by Ha 23/06/08: if total Rater is 3 still this formula
			else if (totalCount == 2 || totalCount == 3)
				formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2)) AS TrimMean ";
			else {
				if (group == 1) {
					// Changed by Ha 23/06/08: if we included NA as the rating
					// or total Rater is greater than 5
					if (totalNotNA > 3 || NA_Included == 1) {
						formula = "cast(cast((Sum(tblResultCompetency.Result)-(Max(tblResultCompetency.Result)+";
						formula = formula
								+ "Min(tblResultCompetency.Result))) as float)/(Count(tblResultCompetency.Result)-2) as numeric(38,2)) AS TrimMean ";
					} else if (totalNotNA <= 3 && NA_Included == 0) {
						formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2)) AS TrimMean ";
					}

				} else
					formula = "CAST(AVG(CAST(tblResultCompetency.Result AS float)) AS numeric(38, 2))  AS TrimMean ";
			}

			query = query + formula;
			query = query
					+ " from tblAssignment INNER JOIN tblResultCompetency ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID WHERE ";
			query = query + "tblAssignment.SurveyID = " + surveyID + " and ";

			if (group == 1)
				whereStatement = "tblAssignment.RaterLoginID <> " + targetID;
			else if (group == 4)
				whereStatement = "tblAssignment.RaterCode = 'SELF'";
			else
				whereStatement = "tblAssignment.RaterCode LIKE '" + groupName
						+ "'";

			query = query
					+ whereStatement
					+ " AND (tblAssignment.RaterStatus IN (1,2,4) or tblAssignment.AssignmentID = "
					+ iFKAssignment + ") ";
			query = query + "and tblAssignment.TargetLoginID = " + targetID;

			if (NA_Included == 0 && totalCount != 1)
				query = query + " and tblResultCompetency.Result <> 0";

			query = query + " group by tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID";
			query = query
					+ " order by tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID";
		} else {
			// this part is to calculate trimmedMean for KB Level
			query = query
					+ "select tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID, ";

			if (totalCount == 1)
				formula = "sum(tblAvgMeanByRater.AvgMean) AS Result ";
			else if (totalCount == 2 || totalCount == 3)
				formula = "cast(cast(sum(tblAvgMeanByRater.AvgMean) as float)/count(tblAvgMeanByRater.AvgMean) as numeric(38,2))  AS Result ";
			else {
				// Change made by Ha 21/06/08 to apply the correct formula for
				// calculating the trimmedMean
				if (totalNotNA > 3 || NA_Included == 1) {
					formula = "cast(cast((Sum(tblAvgMeanByRater.AvgMean)-(Max(tblAvgMeanByRater.AvgMean)+";
					formula = formula
							+ " Min(tblAvgMeanByRater.AvgMean))) as float)/(Count(tblAvgMeanByRater.AvgMean)-2) as numeric(38,2)) AS Result ";
				} else if (totalNotNA <= 3 && NA_Included == 0) {
					formula = "cast(cast(sum(tblAvgMeanByRater.AvgMean) as float)/count(tblAvgMeanByRater.AvgMean) as numeric(38,2))  AS Result ";
				}

			}

			query = query + formula;
			query = query
					+ "from tblAvgMeanByRater INNER JOIN tblAssignment ON ";
			query = query
					+ "tblAvgMeanByRater.AssignmentID = tblAssignment.AssignmentID ";
			query = query + "WHERE tblAvgMeanByRater.SurveyID = " + surveyID
					+ " AND ";
			query = query
					+ "tblAvgMeanByRater.AssignmentID IN (SELECT AssignmentID ";
			query = query + "FROM tblAssignment WHERE SurveyID = " + surveyID
					+ " AND (RaterStatus ";
			query = query + "IN (1, 2, 4) or tblAssignment.AssignmentID = "
					+ iFKAssignment
					+ ") AND tblAssignment.RaterCode <> 'SELF' AND ";
			query = query + "tblAssignment.TargetLoginID = " + targetID;
			query = query
					+ ") GROUP BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID ";
			query = query
					+ "ORDER BY tblAvgMeanByRater.RatingTaskID, tblAvgMeanByRater.CompetencyID";

			// System.out.println(totalCount + "\n" + query);
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				String[] arr = new String[3];
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - TrimmedMean - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * getTotalNonNA
	 * 
	 * @param surveyID
	 *            , targetID, group, iFKAssginment
	 * @return int number of input that a rater does not put NA value
	 * @author Ha by 24/06/08
	 */
	public int getTotalNonNA(int surveyID, int targetID, int group,
			int iFKAssignment) {
		int total = 0;
		String query = "";
		String whereStatement = "";
		String groupName = "";

		switch (group) {
		case 1:
			groupName = "SELF";
			break;

		case 2:
			groupName = "SUP%";
			break;

		case 3:
			groupName = "OTH%";
			break;

		case 4:
			groupName = "SELF";
			break;
		}

		query = query + "SELECT Count(tblResultCompetency.Result) as Result ";

		query = query
				+ " from tblAssignment INNER JOIN tblResultCompetency ON ";
		query = query
				+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID WHERE ";
		query = query + "tblAssignment.SurveyID = " + surveyID + " and ";
		if (group == 1)
			whereStatement = "tblAssignment.RaterLoginID <> " + targetID;
		else if (group == 4)
			whereStatement = "tblAssignment.RaterCode = 'SELF'";
		else
			whereStatement = "tblAssignment.RaterCode LIKE '" + groupName + "'";

		query = query
				+ whereStatement
				+ " AND (tblAssignment.RaterStatus IN (1,2,4) or tblAssignment.AssignmentID = "
				+ iFKAssignment + ") ";
		query = query + "and tblAssignment.TargetLoginID = " + targetID;

		query = query + " and tblResultCompetency.Result <> 0";

		query = query + " group by tblResultCompetency.RatingTaskID, ";
		query = query + "tblResultCompetency.CompetencyID";
		query = query
				+ " order by tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next()) {
				total = rs.getInt("Result");
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - getTotalNonNA - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return total;
	}

	/**
	 * Write the calculated trimmed mean results into database, tblTrimmedMean.
	 * This will be retrieved in Process Survey and Reports. Group: 1 = ALL
	 * EXCLUDES SELF 2 = SUPERIOR (SUP) 3 = OTHERS (OTH) 4 = SELF
	 */
	public boolean writeTrimmedMeanToDB(int surveyID, int targetID, int group) {
		String sql = "Insert into tblTrimmedMean values (" + surveyID + ","
				+ targetID + "," + RTID + "," + competencyID + ",";
		sql = sql + trimmedMean + ", " + group + ")";
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err
					.println("Calculation.java - writeTrimmedMeanToDB - " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return bIsAdded;
	}

	/**
	 * This is a method to call TrimmedMean and WriteToDb. You just need to call
	 * this method from JSP after Rater has finished fill up the questionnaire.
	 * Try and Catch in writeTrimmedMeantoDB used to prevent duplicate entry in
	 * database.
	 * 
	 * Calculate the trimmedmean competency score and store in tblTrimmedMean.
	 * Only calculate score for ALL.
	 * 
	 * If it is KB Level, then we just need to calculate for group of ALL.
	 * 
	 * If it is Competency Level, we have to calculate for ALL and other groups
	 * as well. (Need to be verified for this statement - 4th Dec 06)
	 * 
	 */
	public void calculateTrimmedMean(int surveyID, int targetID, int group,
			int iFKAssignment) {
		int surveyLevel = LevelOfSurvey(surveyID);
		int temp = 0;

		Vector rs = TrimmedMean(surveyID, targetID, group, iFKAssignment);

		for (int i = 0; i < rs.size(); i++) {

			String[] arr = (String[]) rs.elementAt(i);
			temp = 1;
			RTID = Integer.parseInt(arr[0]);
			competencyID = Integer.parseInt(arr[1]);
			trimmedMean = Double.parseDouble(arr[2]);

			// System.out.println(surveyID, )
			writeTrimmedMeanToDB(surveyID, targetID, group);

		}

		if (temp == 0) {

			Vector rsRT = RatingList(surveyID, surveyLevel);

			for (int i = 0; i < rsRT.size(); i++) {
				String[] arr = (String[]) rsRT.elementAt(i);

				RTID = Integer.parseInt(arr[0]);
				competencyID = Integer.parseInt(arr[1]);

				trimmedMean = 0;

				writeTrimmedMeanToDB(surveyID, targetID, group);

			}

		}

	}

	/******************************************* AVERAGE MEAN BY EACH RATER (might not been used anymore ) *******************************************/

	/**
	 * This method is used only on KB Level Analysis. This is to calculate Avg
	 * Mean of all KBs under each Competency for all raters to be used for
	 * TrimmedMean calculation.
	 */
	public Vector AvgMeanByRater(int surveyID, int targetID) {
		String query = "";
		int NA_Included = NAIncluded(surveyID);

		query = query + "SELECT tblResultBehaviour.RatingTaskID, ";
		query = query
				+ "KeyBehaviour.FKCompetency, tblResultBehaviour.AssignmentID, ";
		query = query + "cast(CAST(sum(tblResultBehaviour.Result) AS float)/";
		query = query
				+ "count(tblResultBehaviour.Result) as numeric(38,2)) AS AvgMean ";
		query = query + "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
		query = query
				+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ";
		query = query + "INNER JOIN KeyBehaviour ON ";
		query = query
				+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " AND ";
		query = query + "tblAssignment.TargetLoginID = " + targetID;
		query = query
				+ " and tblAssignment.RaterCode <> 'SELF' and tblAssignment.RaterStatus IN (1,2,4) ";

		if (NA_Included == 0)
			query = query + " and tblResultBehaviour.Result <> 0";

		query = query
				+ " GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
		query = query + "tblResultBehaviour.AssignmentID ORDER BY ";
		query = query
				+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
		query = query + "tblResultBehaviour.AssignmentID";

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = new String[4];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);

			}

		} catch (Exception E) {
			System.err.println("Calculation.java - AvgMeanByRater - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Call this method to execute AvgMeanByRater and writeAvgMeanByRaterToDB.
	 * Try and Catch in writeAvgMeanByRater used to prevent duplicate entry in
	 * database.
	 */
	public void calculateAvgMeanByRater(int surveyID, int targetID) {

		Vector rs = AvgMeanByRater(surveyID, targetID);

		for (int i = 0; i < rs.size(); i++) {
			String[] arr = (String[]) rs.elementAt(i);

			RTID = Integer.parseInt(arr[0]);
			competencyID = Integer.parseInt(arr[1]);
			KBID = Integer.parseInt(arr[2]);
			avgMean = Double.parseDouble(arr[3]);

			try {
				writeAvgMeanByRaterToDB(surveyID);
			} catch (SQLException SE) {
			}
		}

	}

	/**************************************************** AVERAGE MEAN *************************************************/

	/**
	 * Calculate Average Mean of each rater upon completion of questionnaire.
	 */
	public Vector RaterAvgMean(int assignmentID, int surveyID) {
		String query = "";

		int NA_Included = NAIncluded(surveyID);

		query = query
				+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
		query = query
				+ "CAST(cast(sum(tblResultBehaviour.Result) as float) /count(tblResultBehaviour.Result) AS numeric(38,2)) AS AvgMean ";
		query = query + "FROM tblResultBehaviour INNER JOIN KeyBehaviour ON ";
		query = query
				+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
		query = query + "WHERE tblResultBehaviour.AssignmentID = "
				+ assignmentID;

		if (NA_Included == 0)
			query = query + " and tblResultBehaviour.Result <> 0";

		query = query + " GROUP BY tblResultBehaviour.RatingTaskID, ";
		query = query + "KeyBehaviour.FKCompetency ";
		query = query
				+ "ORDER BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency";

		// System.out.println(query);

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = new String[3];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - RaterAvgMean - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * This is the method that needs to be called from JSP to calculate Rater
	 * Average Mean. Try and Catch in writeAvgMeanByRatertoDB used to prevent
	 * duplicate entry in database.
	 * 
	 * This is only used if survey is KBLevel because we need to average up the
	 * score first then we do trimmedmean or averaging
	 */
	public void calculateRaterAvgMean(int surveyID, int assignmentID) {
		deleteAvgMeanByRaterToDB(assignmentID);

		Vector rs = RaterAvgMean(assignmentID, surveyID);

		for (int i = 0; i < rs.size(); i++) {
			String[] arr = (String[]) rs.elementAt(i);

			RTID = Integer.parseInt(arr[0]);
			competencyID = Integer.parseInt(arr[1]);
			KBID = assignmentID;
			avgMean = Double.parseDouble(arr[2]);

			try {
				writeAvgMeanByRaterToDB(surveyID);
			} catch (SQLException SE) {

			}
		}

	}

	/**
	 * Write all calculated data to tblAvgMeanByRater for calculating Trimmed
	 * Mean for ALL. This is only used on KB Level Analysis.
	 */
	public boolean writeAvgMeanByRaterToDB(int surveyID) throws SQLException {
		String sql = "Insert into tblAvgMeanByRater values (" + surveyID + ","
				+ RTID + "," + competencyID + ",";
		sql = sql + KBID + "," + avgMean + ")";

		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - writeAvgMeanByRaterToDB - "
					+ E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return bIsAdded;
	}

	/**
	 * Delete all calculated data from tblAvgMeanByRater for re-calculating
	 * Trimmed Mean for ALL and groups. This is only used on KB Level Analysis.
	 */
	public boolean deleteAvgMeanByRaterToDB(int assignmentID) {
		String sql = "Delete from tblAvgMeanByRater where AssignmentID = "
				+ assignmentID;

		Connection con = null;
		Statement st = null;

		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - deleteAvgMeanByRaterToDB - "
					+ E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return bIsDeleted;
	}

	/**
	 * Calculate Average Mean Score for each Rating Task for the specific group.
	 * Group: 1 = ALL EXCLUDES SELF 2 = SUPERIOR (SUP) 3 = OTHERS (OTH) 4 = SELF
	 * 5 = SUBORDINATE 6 = PEER 7 = ADDITIONAL 8 = DIRECT REPORTS 9 = INDIRECT
	 * REPORTS
	 */
	public Vector AvgMean(int surveyID, int targetID, int group,
			int iFKAssignment) {
		String query = "";
		String groupName = "";
		String whereStatement = "";
		int NA_Included = NAIncluded(surveyID);
		int surveyLevel = LevelOfSurvey(surveyID);
		// Added by Ha 25/06/08 to add rater status to query
		int raterStatus = RaterStatus(iFKAssignment);
		switch (group) {
		case 1:
			groupName = "ALL";
			break;
		case 2:
			groupName = "SUP%";
			break;
		case 3:
			groupName = "OTH%";
			break;
		case 4:
			groupName = "SELF";
			break;
		case 5:
			groupName = "SUB%"; // Add logic for separating calculations of
								// Subordinates and Peers, added by Desmond 21
								// Oct 09
			break;
		case 6:
			groupName = "PEER%"; // Add logic for separating calculations of
									// Subordinates and Peers, added by Desmond
									// 21 Oct 09
			break;
		case 7:
			groupName = "ADD%"; // for additional raters
			break;
		case 8:
			groupName = "DIR%"; // for additional raters
			break;
		case 9:
			groupName = "IDR%"; // for additional raters
			break;
		case 11:
			groupName = "BOSS%"; // for additional raters
			break;

		}
		// Changed by Ha 06/06/08 add to query
		// "GROUP BY tblAsssingment.SurveyID"
		// Problem with old query: return empty result
		if (group == 1) // All excluding self
			whereStatement = "RaterLoginID <> " + targetID;
		else if (group == 4) // Self only
			whereStatement = "RaterCode = 'SELF'";
		else
			// Others
			whereStatement = "RaterCode LIKE '" + groupName + "'";

		// Begin preparing query for calculating average mean
		if (surveyLevel == 0) {
			// Prepare query for Competency level survey
			query = query + "SELECT tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID, ";
			query = query
					+ "cast(cast(sum(tblResultCompetency.Result) as float)/count(tblResultCompetency.Result) as numeric(38,2)) AS AvgMean ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultCompetency ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			// Added by Ha 01/07/08 include the rater with status 5 (NA) if NA
			// is included in the survey
			if (NA_Included == 0)
				query = query + " AND (tblAssignment.RaterStatus IN (1, 2, 4) ";
			else
				query = query
						+ " AND (tblAssignment.RaterStatus IN (1, 2, 4, 5) ";
			/**
			 * Added by Jenty Chou on 8th Nov Fix the problem where last rater
			 * was not calculated if we do not set the status first before
			 * calculation
			 */
			// Changed by Ha 25/06/08 this part is put in the condition if we do
			// not set the status first before calculation
			// Problem with old query: result the same if we include/exclude/NA
			// rater
			if (raterStatus == 0)
				query += "or tblAssignment.AssignmentID = " + iFKAssignment
						+ ") ";
			else
				query += ")";
			query += " AND ";
			query = query + whereStatement;

			if (NA_Included == 0)
				query = query + " and tblResultCompetency.Result <> 0";

			query = query
					+ " GROUP BY tblAssignment.SurveyID, tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID ";
			query = query + "ORDER BY tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID";
		} else {
			// Prepare query for KB level survey
			query = query
					+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";

			query = query
					+ "cast(cast(sum(tblResultBehaviour.Result) as float)/count(tblResultBehaviour.Result) as numeric(38,2)) AS AvgMean, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
			query = query + "KeyBehaviour ON ";
			query = query
					+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			// Added by Ha 01/07/08 include the rater with status 5 (NA) if NA
			// is included in the survey
			if (NA_Included == 0)
				query = query + " AND (tblAssignment.RaterStatus IN (1, 2, 4) ";
			else
				query = query
						+ " AND (tblAssignment.RaterStatus IN (1, 2, 4, 5) ";
			/**
			 * Added by Jenty Chou on 8th Nov Fix the problem where last rater
			 * was not calculated if we do not set the status first before
			 * calculation
			 */
			// Changed by Ha 25/06/08 this part is put in the condition if we do
			// not set the status first before calculation
			// Problem with old query: result the same if we include/exclude/NA
			// rater
			if (raterStatus == 0)
				query += "or tblAssignment.AssignmentID = " + iFKAssignment
						+ ") ";
			else
				query += ")";
			query += " AND ";
			query = query + whereStatement;

			if (NA_Included == 0)
				query = query + " and tblResultBehaviour.Result <> 0";

			query = query
					+ " GROUP BY tblAssignment.SurveyID, tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ORDER BY ";
			query = query
					+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID";
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = null;

				if (surveyLevel == 0) {
					arr = new String[3];

					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					// Changed by Ha 06/06/08
					v.add((String[]) arr);
				} else {
					arr = new String[4];

					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					arr[3] = rs.getString(4);
					// Changed by Ha 06/06/08
					v.add((String[]) arr);
				}
			}
		} catch (Exception E) {
			System.err.println("Calculation.java - AvgMean - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Calculate Average Mean Score for each Rating Task for the specific group.
	 * This is Allianz customised function because they want to break down OTH
	 * into specific relation
	 * 
	 * @param surveyID
	 * @param targetID
	 * @param group
	 *            1=SUP, 2=OTH, 3=SELF
	 * @param specGroup
	 *            1=DR, 2=Peer, 3=BIP
	 * @return
	 * 
	 *         Fixed by Jenty Chou on 8th Nov 06 - Fixed to calculate rater
	 *         before the status is set to complete
	 */
	public Vector AvgMeanAllianz(int surveyID, int targetID, int group,
			int specGroup, int iFKAssignment) {
		String query = "";
		String groupName = "";
		String whereStatement = "";
		int NA_Included = NAIncluded(surveyID);
		int surveyLevel = LevelOfSurvey(surveyID);

		switch (group) {
		case 1:
			groupName = "SUP%";
			break;
		case 2:
			groupName = "OTH%";
			break;
		case 3:
			groupName = "SELF";
			break;
		}

		if (group == 1)
			whereStatement = "RaterLoginID <> " + targetID;
		else if (group == 4)
			whereStatement = "RaterCode = 'SELF'";
		else
			whereStatement = "RaterCode LIKE '" + groupName + "'";

		if (surveyLevel == 0) {
			query = query + "SELECT tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID, ";
			query = query
					+ "cast(cast(sum(tblResultCompetency.Result) as float)/count(tblResultCompetency.Result) as numeric(38,2)) AS AvgMean, tblAssignment.SurveyID ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultCompetency ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			query = query
					+ " AND (tblAssignment.RaterStatus IN (1,2,4) or tblAssignment.AssignmentID = "
					+ iFKAssignment + ") AND ";
			query = query + whereStatement;

			if (NA_Included == 0)
				query = query + " and tblResultCompetency.Result <> 0";

			query = query + " GROUP BY tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID ";
			query = query + "ORDER BY tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID";

		} else {
			query = query
					+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query
					+ "cast(cast(sum(tblResultBehaviour.Result) as float)/count(tblResultBehaviour.Result) as numeric(38,2)) AS AvgMean, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
			query = query + "KeyBehaviour ON ";
			query = query
					+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			query = query + " AND tblAssignment.RaterStatus IN (1, 2, 4) AND ";
			query = query + whereStatement;

			if (NA_Included == 0)
				query = query + " and tblResultBehaviour.Result <> 0";

			query = query
					+ " GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ORDER BY ";
			query = query
					+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID";

			/*
			 * SELECT FKCompetency, CAST(CAST(SUM(AvgMean) AS float) /
			 * COUNT(FKCompetency) AS numeric(38, 2)) AS AvgMean FROM (SELECT
			 * tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency,
			 * tblResultBehaviour.KeyBehaviourID,
			 * CAST(CAST(SUM(tblResultBehaviour.Result) AS float) /
			 * COUNT(tblResultBehaviour.Result) AS numeric(38, 2)) AS AvgMean,
			 * tblRelationSpecific.RelationSpecific FROM tblAssignment INNER
			 * JOIN tblResultBehaviour ON tblAssignment.AssignmentID =
			 * tblResultBehaviour.AssignmentID INNER JOIN KeyBehaviour ON
			 * tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour
			 * LEFT OUTER JOIN tblRelationSpecific ON tblAssignment.RTSpecific =
			 * tblRelationSpecific.SpecificID WHERE (tblAssignment.SurveyID =
			 * 450) AND (tblAssignment.TargetLoginID = 6438) AND
			 * (tblAssignment.RaterStatus IN (1, 2, 4)) AND
			 * (tblAssignment.RaterCode LIKE 'OTH%') GROUP BY
			 * tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency,
			 * tblResultBehaviour.KeyBehaviourID,
			 * tblRelationSpecific.RelationSpecific HAVING
			 * (tblRelationSpecific.RelationSpecific = 'peer')) DERIVEDTBL GROUP
			 * BY FKCompetency
			 */
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = new String[4];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - AvgMeanByRater - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * Write all calculated data to tblAvgMean for future usage, such as to
	 * calculate Reliability, display all the results in Process Result, and
	 * Reports.
	 * 
	 * tblAvgMean is used to store: 1. Average KB scores for both TrimmedMean
	 * and AvgMean (KB Level) 2. Competency scores for AvgMean (Competency
	 * Level)
	 * 
	 */
	public boolean writeAvgMeanToDB(int surveyID, int group, int targetID,int assignmentID)
			throws SQLException {
		String sql = "Insert into tblAvgMean values (" + surveyID + ","
				+ targetID + "," + RTID + "," + competencyID + ",";
		sql = sql + KBID + "," + avgMean + ", " + group + ", " + assignmentID + ")";
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;
			
		} catch (Exception E) {
			System.err.println("Calculation.java - writeAvgMeanToDB - " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		// System.out.println("The query to insert into tblAvgmean: "+sql);
		return bIsAdded;
	}

	/**
	 * Write all calculated data to tblAvgMean for future usage, such as to
	 * calculate Reliability, display all the results in Process Result, and
	 * Reports.
	 * 
	 * tblAvgMean is used to store: 1. Average KB scores for both TrimmedMean
	 * and AvgMean (KB Level) 2. Competency scores for AvgMean (Competency
	 * Level)
	 * 
	 */
	public boolean writeAvgMeanToDBTen(int surveyID, int group, int targetID,
			double avgMean,int assignmentID) throws SQLException {
		String sql = "Insert into tblAvgMean values (" + surveyID + ","
				+ targetID + "," + RTID + "," + competencyID + ",";
		sql = sql + KBID + "," + avgMean + ", " + group + "," + assignmentID +  ")";
		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			System.out.println("10:"+ sql);
			int iSuccess = st.executeUpdate(sql);
			
			if (iSuccess != 0)
				bIsAdded = true;
			
		} catch (Exception E) {
			System.err.println("Calculation.java - writeAvgMeanToDBTEN - " + E);
		} finally {

			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		// System.out.println("The query to insert into tblAvgmean: "+sql);
		return bIsAdded;
	}

	/**
	 * Get all the groups associated with the particular targetLoginID
	 * 
	 * @param surveyID
	 * @return
	 * @throws SQLException
	 */
	public Vector getAllGroupType(int surveyID, int targetLoginID)
			throws SQLException {
		String query = "";

		query = " select distinct type from tblAvgMean where SurveyID ="
				+ surveyID;
		query += " and type<> 1 and type <>4 and type<>10 and TargetLoginID ="
				+ targetLoginID;

		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				int groups = rs.getInt("type");
				System.out.println("Groups : " + groups);
				v.add(groups);
			}
		} catch (Exception ex) {
			System.out.println("Calculation.java - getAllGroupTypes - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return v;
	}

	/**
	 * Get all participants ID in a particular survey. Created to fulfil
	 * parameters for getTargetAvgMean() method in Calculation.java
	 * 
	 * @param surveyID
	 * @return
	 * @throws SQLException
	 */
	public double getAverageFromGroup(int surveyID, int targetID, int type,
			int competencyID) throws SQLException {
		String query = "";

		query = " select AVG(AvgMean) as result from tblAvgMean where SurveyID ="
				+ surveyID
				+ ""
				+ " and TargetLoginID="
				+ targetID
				+ " and TYPE=" + type + " and CompetencyID=" + competencyID;

		double result = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				result = rs.getDouble("result");
				System.out.println("Average from group " + type + "=" + result);
			}
		} catch (Exception ex) {
			System.out.println("Calculation.java - getAllParticipants - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return result;
	}

	/**
	 * Get all participants ID in a particular survey. Created to fulfil
	 * parameters for getTargetAvgMean() method in Calculation.java
	 * 
	 * @param surveyID
	 * @return
	 * @throws SQLException
	 */
	public Vector getAllParticipantsID(int surveyID) throws SQLException {
		String query = "";

		query = " select distinct TargetLoginID from tblAssignment where SurveyID="
				+ surveyID;
		System.out.println("connecting to database");
		Vector v = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			System.out.println("Query participantID successful!");
			while (rs.next()) {

				int targetLoginId = rs.getInt("TargetLoginID");
				System.out.println(targetLoginId);
				v.add(targetLoginId);
			}
		} catch (Exception ex) {
			System.out.println("Calculation.java - getAllParticipants - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return v;
	}

	/**
	 * Get all competencies from a particular survey parameters for
	 * getTargetAvgMean() method in Calculation.java
	 * 
	 * @param surveyID
	 * @return
	 * @throws SQLException
	 */
	public Vector getAllCompetencies(int surveyID) throws SQLException {
		String query = "";

		query = " select Distinct competencyID from tblAvgMean where SurveyID ="
				+ surveyID;
		System.out.println("connecting to database");
		
		Vector competencies = new Vector();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			System.out.println("Query participantID successful!");
			while (rs.next()) {

				competencies.add(rs.getInt("competencyID"));
			}
		} catch (Exception ex) {
			System.out.println("Calculation.java - getAllCompetencies - "
					+ ex.getMessage());
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return competencies;
	}
	
	public double CombineGroupMeanResult(int surveyID, int targetID, Vector<Integer> group, int compID, int KBID, boolean CPR) throws SQLException{
		double result = 0;
		String query = "";
		String groupName = "";
		Vector<String> whereStatement = new Vector<String>();
		int NA_Included = NAIncluded(surveyID);
		int surveyLevel = LevelOfSurvey(surveyID);
		for(int i = 0; i < group.size(); i++){
			switch (group.get(i)) {
			case 1:
				groupName = "ALL";
				break;
			case 2:
				groupName = "SUP%";
				break;
			case 3:
				groupName = "OTH%";
				break;
			case 4:
				groupName = "SELF";
				break;
			case 5:
				groupName = "SUB%"; // Add logic for separating calculations of
									// Subordinates and Peers, added by Desmond 21
									// Oct 09
				break;
			case 6:
				groupName = "PEER%"; // Add logic for separating calculations of
										// Subordinates and Peers, added by Desmond
										// 21 Oct 09
				break;
			case 7:
				groupName = "ADD%"; // for additional raters
				break;
			case 8:
				groupName = "DIR%"; // for additional raters
				break;
			case 9:
				groupName = "IDR%"; // for additional raters
				break;
			case 11:
				groupName = "BOSS%"; // for additional raters
				break;
			}
			// Changed by Ha 06/06/08 add to query
			// "GROUP BY tblAsssingment.SurveyID"
			// Problem with old query: return empty result
			if (group.get(i) == 1) // All excluding self
				whereStatement.add("RaterLoginID <> " + targetID);
			else if (group.get(i) == 4) // Self only
				whereStatement.add("RaterCode = 'SELF'");
			else
				// Others
				whereStatement.add("RaterCode LIKE '" + groupName + "'");
		}

		if (surveyLevel == 0) {
			// Prepare query for Competency level survey
			query = query + "SELECT tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID, ";
			query = query
					+ "cast(cast(sum(tblResultCompetency.Result) as float)/count(tblResultCompetency.Result) as numeric(38,2)) AS AvgMean ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultCompetency ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID +" AND tblResultCompetency.CompetencyID = "+compID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			// Added by Ha 01/07/08 include the rater with status 5 (NA) if NA
			// is included in the survey
			if (NA_Included == 0)
				query = query + " AND tblAssignment.RaterStatus IN (1, 2, 4) ";
			else
				query = query
						+ " AND tblAssignment.RaterStatus IN (1, 2, 4, 5) ";
			/**
			 * Added by Jenty Chou on 8th Nov Fix the problem where last rater
			 * was not calculated if we do not set the status first before
			 * calculation
			 */
			query += " AND (";
			query += whereStatement.get(0);
			for(int i = 1; i < whereStatement.size(); i++){
				query += " OR ";
				query = query + whereStatement.get(i);
			}
			query += ") ";
			if (NA_Included == 0)
				query = query + " and tblResultCompetency.Result <> 0";

			query = query
					+ " GROUP BY tblAssignment.SurveyID, tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID ";
			query = query + "ORDER BY tblResultCompetency.RatingTaskID, ";
			query = query + "tblResultCompetency.CompetencyID";
		} else if(KBID == 0){
			query = query + "SELECT tblResultBehaviour.RatingTaskID, ";
			query = query + "KeyBehaviour.FKCompetency, ";
			query = query
					+ "cast(cast(sum(tblResultBehaviour.Result) as float)/count(tblResultBehaviour.Result) as numeric(38,2)) AS AvgMean ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID  inner join KeyBehaviour on " +
					"KeyBehaviour.PKKeyBehaviour = tblResultBehaviour.KeyBehaviourID ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID +" AND KeyBehaviour.FKCompetency = "+compID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			
			if (NA_Included == 0)
				query = query + " AND tblAssignment.RaterStatus IN (1, 2, 4) ";
			else
				query = query
						+ " AND tblAssignment.RaterStatus IN (1, 2, 4, 5) ";
			/**
			 * Added by Jenty Chou on 8th Nov Fix the problem where last rater
			 * was not calculated if we do not set the status first before
			 * calculation
			 */
			query += " AND (";
			query += whereStatement.get(0);
			for(int i = 1; i < whereStatement.size(); i++){
				query += " OR ";
				query = query + whereStatement.get(i);
			}
			query += ") ";
			if (NA_Included == 0)
				query = query + " and tblResultBehaviour.Result <> 0";
			query = query
					+ " GROUP BY tblAssignment.SurveyID, tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency";
			query = query + " ORDER BY ";
			query = query
					+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency";
			
		} else {
			// Prepare query for KB level survey
			query = query
					+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";

			query = query
					+ "cast(cast(sum(tblResultBehaviour.Result) as float)/count(tblResultBehaviour.Result) as numeric(38,2)) AS AvgMean, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ";
			query = query
					+ "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
			query = query + "KeyBehaviour ON ";
			query = query
					+ "tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID + " and tblResultBehaviour.KeyBehaviourID = "+KBID;
			query = query + " AND tblAssignment.TargetLoginID = " + targetID;
			// Added by Ha 01/07/08 include the rater with status 5 (NA) if NA
			// is included in the survey
			if (NA_Included == 0)
				query = query + " AND tblAssignment.RaterStatus IN (1, 2, 4) ";
			else
				query = query
						+ " AND tblAssignment.RaterStatus IN (1, 2, 4, 5) ";
			/**
			 * Added by Jenty Chou on 8th Nov Fix the problem where last rater
			 * was not calculated if we do not set the status first before
			 * calculation
			 */
			query += " AND (";
			query += whereStatement.get(0);
			for(int i = 1; i < whereStatement.size(); i++){
				query += " OR ";
				query = query + whereStatement.get(i);
			}
			query += ") ";

			if (NA_Included == 0)
				query = query + " and tblResultBehaviour.Result <> 0";

			query = query
					+ " GROUP BY tblAssignment.SurveyID, tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID ORDER BY ";
			query = query
					+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
			query = query + "tblResultBehaviour.KeyBehaviourID";
		}
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next() && !CPR){
				result = rs.getDouble(3);
			}else{
				rs.next();
				result = rs.getDouble(3);
			}
		}catch(Exception e){
			System.err.println("Calculation.java - CombineGroupMeanResult - " + e);
		}finally{
			ConnectionBean.closeRset(rs);
			ConnectionBean.closeStmt(st);
			ConnectionBean.close(con);
		}
		
		return result;
	}

	/**
	 * Call this method from JSP to execute AvgMean and writeAvgMeanToDB. Try
	 * and Catch in writeAvgMeantoDB used to prevent duplicate entry in
	 * database.
	 * 
	 * @param surveyID
	 * @param targetID
	 * @param group
	 */
	public void calculateAvgMean(int surveyID, int targetID, int group,
			int iFKAssignment) {
		int surveyLevel = LevelOfSurvey(surveyID);
		try {
			Vector rs = AvgMean(surveyID, targetID, group, iFKAssignment);
			int temp = 0;
			if(group==11){
				System.out.println("11!!!");
			}
			int[] competencies = new int[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				String[] arr = (String[]) rs.elementAt(i);

				temp = 1;
				
				RTID = Integer.parseInt(arr[0]);
				competencyID = Integer.parseInt(arr[1]);
				if (surveyLevel == 1) {
					KBID = Integer.parseInt(arr[3]);
				}
				avgMean = Double.parseDouble(arr[2]);
				competencies[i] = competencyID;
				if(group==11){
					System.out.println("11!!!");
				}
				writeAvgMeanToDB(surveyID, group, targetID,iFKAssignment);
			}

			if (temp == 0) {
				Vector rsRT = RatingList(surveyID, surveyLevel);

				for (int i = 0; i < rsRT.size(); i++) {
					String[] arr = (String[]) rsRT.elementAt(i);

					RTID = Integer.parseInt(arr[0]);
					competencyID = Integer.parseInt(arr[1]);
					competencies[i] = competencyID;
					if (surveyLevel == 1) {
						KBID = Integer.parseInt(arr[2]);
					}
					avgMean = 0;

					writeAvgMeanToDB(surveyID, group, targetID,iFKAssignment);

				}
			}
		} catch (SQLException e) {
			System.out
					.println("calculateAvgMean - Calculation.java- SQL Exception"
							+ e);
		} catch (Exception e1) {
			System.out
					.println("calculateAvgMean - Calculation.java - Exception"
							+ e1);
			e1.printStackTrace();
			System.out.println("");
		}
	}

	public void calculateWeightedAverage(int surveyID,int assignmentID){
		
		try{
			
			
			Vector participantsID = getAllParticipantsID(surveyID);// get all  participants
			Vector competencies = getAllCompetencies(surveyID);
			
		
			
			for	(int i = 0; i < participantsID.size(); i++) {
	
				
				//String[] participantRow = (String[]) participantsID.elementAt(i);
				//int participant = Integer.parseInt(participantRow[0]); 
				int participant = (Integer)participantsID.elementAt(i);
				
	
						Vector groups = getAllGroupType(surveyID, participant);
						
	
						// Vector compiledScoresForParticipant = new Vector();
						
						if(groups.size() != 0) { 
							for (int k = 0; k < competencies.size(); k++) {
								//String[] competencyRow = (String[]) competencies.elementAt(k);
								competencyID = (Integer)competencies.elementAt(k);
							
								
								double totalScore = 0;
								for (int j = 0; j <(groups.size()); j++) {
									//System.out.println("Target Group: " + (int)	groups.elementAt(j));
									//String[] groupRow = (String[]) groups.elementAt(j);
									double averageForGrp = getAverageFromGroup(surveyID, participant, (Integer)groups.elementAt(j), competencyID);
									System.out.println("average for grps :" + averageForGrp);
									totalScore+=averageForGrp;
								}
	
								double averageMean = totalScore / groups.size();
								
								writeAvgMeanToDBTen(surveyID, 10, participant,averageMean,assignmentID); 
							} 
						}
	
			}
		}catch(Exception e){
			System.out.println("Calculation.java - calculateWeightedAverage : " + e );
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param surveyID
	 * @param surveyLevel
	 * @return
	 */
	public Vector RatingList(int surveyID, int surveyLevel) {
		String query = "";

		if (surveyLevel == 0) {

			query = "SELECT tblSurveyRating.RatingTaskID, tblSurveyCompetency.CompetencyID, tblSurveyRating.SurveyID ";
			query = query
					+ "FROM tblSurveyCompetency INNER JOIN tblSurveyRating ON ";
			query = query
					+ "tblSurveyCompetency.SurveyID = tblSurveyRating.SurveyID ";
			query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
			query = query
					+ " ORDER BY tblSurveyRating.RatingTaskID, tblSurveyCompetency.CompetencyID";
		} else {
			query = "SELECT tblSurveyRating.RatingTaskID, tblSurveyBehaviour.CompetencyID, ";
			query = query
					+ "tblSurveyBehaviour.KeyBehaviourID FROM tblSurveyRating INNER JOIN ";
			query = query
					+ "tblSurveyBehaviour ON tblSurveyRating.SurveyID = tblSurveyBehaviour.SurveyID ";
			query = query + "WHERE tblSurveyRating.SurveyID = " + surveyID;
			query = query
					+ " ORDER BY tblSurveyRating.RatingTaskID, tblSurveyBehaviour.CompetencyID, ";
			query = query + "tblSurveyBehaviour.KeyBehaviourID";
		}

		Vector v = new Vector();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = new String[3];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - RatingList - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/*********************************************** GAP CALCULATION ***************************************************/

	/**
	 * Check the Gap type. Gap Type: 1 = CPCPR (Current Proficiency vs Current
	 * Proficiency Required) 2 = CPFPR (Current Proficiency vs Future
	 * Proficiency Required);
	 */
	public int GapType(int surveyID) {
		String query = "";
		int isCP = 0;
		int isCPR = 0;
		int isFPR = 0;
		int gapType = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			query = query
					+ "SELECT R.RatingCode FROM tblRatingTask AS R INNER JOIN tblSurveyRating AS S ON ";
			query = query
					+ "R.RatingTaskID = S.RatingTaskID WHERE R.RatingCode in ";
			query = query + "('CP','CPR','FPR')  and  S.SurveyID = " + surveyID;

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String code = rs.getString("RatingCode");

				if (code.equals("CP"))
					isCP = 1;
				else if (code.equals("CPR"))
					isCPR = 1;
				else if (code.equals("FPR"))
					isFPR = 1;

			}

			if (isCP == 1 && isCPR == 1)
				gapType = 1;
			else if (isCP == 1 && isFPR == 1)
				gapType = 2;
		} catch (Exception E) {
			System.err.println("Calculation.java - GapType - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return gapType;
	}

	/**
	 * Retrieve the Trimmed Mean or Average Mean Results for certain Rating
	 * Code. This function is currently being used to calculate Gap, by getting
	 * the CP, CPR, or FPR value for ALL.
	 * 
	 * For competency Level: - tblAvgMean - store competency result for ALL if
	 * reliability index is AvgMean (reliabilityIndex = 1) For this case, the
	 * KeyBehaviourID will be stored as 0.
	 * 
	 * - tblTrimmedMean - store competency result for ALL if reliability index
	 * is TrimmedMean (reliabilityIndex = 0)
	 * 
	 * 
	 * For KB Level: - Both result is stored in tblAvgMean (tblAvgMean was
	 * firstly created with the objective of storing the Average of KBs score
	 * for each group) - After all the KBs score are stored here, the final
	 * competency score is stored in tblTrimmedMean
	 * 
	 */
	public Vector ResultByRT(int surveyID, int targetID, String RTCode) {
		String query = "";

		Vector v = new Vector();

		int surveyLevel = LevelOfSurvey(surveyID);

		// If the reliability index is using Average Mean, then the ALL result
		// is stored in table tblAvgMean
		String tblName = "tblAvgMean";
		String result = "AvgMean";

		int reliabilityCheck = ReliabilityCheck(surveyID);
		if (reliabilityCheck == 0) { // reliability check = 0 => trimmed mean
			// Trimmed Mean result is stored in table trimmed mean
			tblName = "tblTrimmedMean";
			result = "TrimmedMean";
		}

		if (surveyLevel == 0) {
			query = query
					+ "SELECT tblRatingTask.RatingTaskID, tblRatingTask.RatingCode, ";
			query = query + tblName + ".CompetencyID, " + tblName + "."
					+ result + " as Result, " + tblName + ".SurveyID FROM ";
			query = query + "tblRatingTask INNER JOIN " + tblName + " ON ";
			query = query + "tblRatingTask.RatingTaskID = " + tblName
					+ ".RatingTaskID WHERE ";
			query = query + tblName + ".SurveyID = " + surveyID + " AND ";
			query = query + tblName + ".TargetLoginID = " + targetID + " AND ";
			query = query + tblName
					+ ".Type = 1 AND tblRatingTask.RatingCode = '" + RTCode
					+ "' ";
			query = query + "ORDER BY " + tblName + ".CompetencyID";
		} else {
			query = query
					+ "SELECT tblRatingTask.RatingTaskID, tblRatingTask.RatingCode, ";
			query = query
					+ "tblAvgMean.CompetencyID, tblAvgMean.AvgMean AS Result, tblAvgMean.KeyBehaviourID ";
			query = query + "FROM tblRatingTask INNER JOIN tblAvgMean ON ";
			query = query
					+ "tblRatingTask.RatingTaskID = tblAvgMean.RatingTaskID WHERE ";
			query = query + "tblRatingTask.RatingCode = '" + RTCode
					+ "' AND tblAvgMean.SurveyID = " + surveyID;
			query = query + " AND tblAvgMean.TargetLoginID = " + targetID
					+ " AND tblAvgMean.Type = 1 ";
			query = query
					+ "ORDER BY tblAvgMean.CompetencyID, tblAvgMean.KeyBehaviourID";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = null;

				arr = new String[5];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - ResultbyRT - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Hoa - 04/12/08 Get CompetencyID and KeyBehaviourID Auxiliary method for
	 * calculateGap method when CP and CPR both empty so pkComp and pkKB have to
	 * retrieve from join table of tblSurveyCompetency and tblSurveyBehaviour.
	 * 
	 * Returns: Vector of arrays where array[0] is pkComp and array[1] is pkKB
	 */
	public Vector getRT(int surveyID) {
		Vector v = new Vector();
		String query = "";
		query = "SELECT tblSurveyCompetency.competencyID,"
				+ " tblSurveyBehaviour.keyBehaviourID"
				+ " FROM tblSurveyCompetency INNER JOIN tblSurveyBehaviour"
				+ " ON tblSurveyCompetency.surveyID = tblSurveyBehaviour.surveyID"
				+ " AND tblSurveyCompetency.competencyID = tblSurveyBehaviour.competencyID"
				+ " WHERE tblSurveyCompetency.surveyID = " + surveyID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				String arr[] = null;

				arr = new String[2];

				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);

				v.add(arr);
			}
		} catch (Exception E) {
			System.err.println("Calculation.java - getRT - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * Hoa - 04/12/08 Check for whether CPR calculation is needed or ignore when
	 * calculate Gap. Return: bIncludeCalCPR flag true - include in Gap
	 * calculation false - ignore in Gap calculation
	 */
	public boolean CPRcalInclude(int surveyID) {
		Vector vRT = RatingTask(surveyID);
		boolean bIncludeCalCPR = false;

		for (int k = 0; k < vRT.size(); k++) {
			votblSurveyRating vo = new votblSurveyRating();
			vo = (votblSurveyRating) vRT.elementAt(k);
			String sRTname = vo.getRatingTaskName();

			if (sRTname.equals(("Current Proficiency Required").trim())) {
				bIncludeCalCPR = true;
			}
		}
		return bIncludeCalCPR;
	}

	/**
	 * Calculate the target's gap for that particular survey. Gap is calculated
	 * only if the gapType is CP-CPR or CP-FPR.
	 * 
	 * Last Updated 04/12/08 by Hoa. Revert back data type to int, double from
	 * Integer and Double object since null value will not be persisted in the
	 * table anymore
	 * 
	 * Fixed: prevent bean from crashed if CP and CPR empty. Now include
	 * checking for whether CPR is needed in calculation of Gap Handle all the
	 * cases of CP and CPR in Gap calculation.
	 */
	public void calculateGap(int surveyID, int targetID) {
		int pkComp, pkKB = 0;
		double result1, result2;
		double gap;
		int gapType = GapType(surveyID); // 1=CPCPR, 2=CPFPR

		// Hoa - 04/12/08 - flag for include CPR in calculation of Gap
		boolean bIncludeCalCPR = CPRcalInclude(surveyID);
		Vector vCompKbID = getRT(surveyID);

		int surveyLevel = LevelOfSurvey(surveyID);
		Vector CP = null;
		Vector CPR = null;

		if (gapType == 1) {
			CP = ResultByRT(surveyID, targetID, "CP");
			CPR = ResultByRT(surveyID, targetID, "CPR");
		} else if (gapType == 2) {
			CP = ResultByRT(surveyID, targetID, "CP");
			CPR = ResultByRT(surveyID, targetID, "FPR");
		}

		if (gapType != 0) {
			// Hoa - 04/12/08
			// if both CP and CPR empty then Gap is equal to 0
			// pkComp and pkKB will get from the join table
			if (CP.isEmpty() && CPR.isEmpty()) {
				for (int j = 0; j < vCompKbID.size(); j++) {
					String[] pkArr = (String[]) vCompKbID.elementAt(j);
					pkComp = Integer.parseInt(pkArr[0]);
					if (surveyLevel == 1)
						pkKB = Integer.parseInt(pkArr[1]);
					gap = 0;

					try {
						WritetoGapDB(surveyID, targetID, pkComp, pkKB, gap);
					} catch (SQLException SE) {
					}
				}
			}

			// Hoa - 04/12/08
			// If CP is empty then pkComp and pkKB is get from CPR
			// Set temporary value of 0 for CP avg mean in calculation
			if (CP.isEmpty() && !CPR.isEmpty()) {
				for (int i = 0; i < CPR.size(); i++) {
					String[] arr = { "0", "0", "0", "0", "0" };
					String[] arr2 = (String[]) CPR.elementAt(i);

					pkComp = Integer.parseInt(arr2[2]);

					if (surveyLevel == 1)
						pkKB = Integer.parseInt(arr2[4]);

					result1 = Double.parseDouble(arr[3]);
					result2 = Double.parseDouble(arr2[3]);

					// Hoa - 04/12/08
					// if CPR is not needed in calculation of gap
					// then set Gap equal to CP avg mean.
					if (bIncludeCalCPR == true) {
						gap = result1 - result2;
					} else {
						gap = result1;
					}

					try {
						WritetoGapDB(surveyID, targetID, pkComp, pkKB, gap);
					} catch (SQLException SE) {
					}
				}
			}

			for (int i = 0; i < CP.size(); i++) {
				String[] arr = (String[]) CP.elementAt(i);
				String[] arr2;
				if (CPR.isEmpty()) {
					String[] tmpArr = { "0", "0", "0", "0", "0" };
					arr2 = tmpArr;
				} else {
					arr2 = (String[]) CPR.elementAt(i);
				}

				pkComp = Integer.parseInt(arr[2]);

				if (surveyLevel == 1)
					pkKB = Integer.parseInt(arr[4]);

				result1 = Double.parseDouble(arr[3]);
				result2 = Double.parseDouble(arr2[3]);

				if (bIncludeCalCPR == true) {
					gap = result1 - result2;
				} else {
					gap = result1;
				}

				try {
					WritetoGapDB(surveyID, targetID, pkComp, pkKB, gap);
				} catch (SQLException SE) {
				}
			}
		}
	}

	/**
	 * Write the results of Gap Calculation into tblGap. Last Updated 04/12/08
	 * by Hoa. Revert back to int and double data type since null value is not
	 * to persist inside the table
	 */
	public boolean WritetoGapDB(int surveyID, int targetID, int pkComp,
			int pkKB, double gap) throws SQLException {
		String sql = "Insert into tblGap values (" + surveyID + "," + targetID
				+ "," + pkComp + "," + pkKB + "," + gap + ")";

		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - WritetoGapDB - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return bIsAdded;
	}

	/************************************* RELIABILITY CALCULATION ****************************************/

	/**
	 * Get target average mean from tblAvgMean based on the group. If the survey
	 * is under Key Behaviour Level Analysis, results retrieve from tblAvgMean
	 * is average up to Competency Level. This method is currently used to
	 * calculate Reliability. Code edited by Ha 27/06/08
	 */
	public Vector getTargetAvgMeanCompLevel(int surveyID, int targetID,
			int group, int iAssignmentID) {
		String query = "";
		int surveyLevel = LevelOfSurvey(surveyID);
		int NA_Included = NAIncluded(surveyID);

		if (surveyLevel == 0) {
			/*
			 * query = query +
			 * "SELECT DISTINCT tblAvgMean.RatingTaskID,tblAvgMean.CompetencyID,  "
			 * ; query = query +
			 * "Competency.CompetencyName, tblAvgMean.AvgMean AS Result "; query
			 * = query + "FROM tblAvgMean INNER JOIN tblRatingTask ON "; query =
			 * query +
			 * "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN "
			 * ; query = query +
			 * "Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency "
			 * ; query = query + "WHERE tblAvgMean.Type = " + group +
			 * " AND tblAvgMean.SurveyID = " + surveyID; query = query +
			 * " AND tblAvgMean.TargetLoginID = " + targetID; query = query +
			 * " AND tblRatingTask.RatingCode in ('CP', 'CPR', 'FPR')"; query =
			 * query +
			 * " ORDER BY tblAvgMean.RatingTaskID, tblAvgMean.CompetencyID";
			 */

			// Query rewrite by Ha 27/05/08 to get the average input of other
			// rater exclude this rater
			// Problem with old query: return the average of all raters
			query = query
					+ "SELECT SUM(tblResultCompetency.Result) / COUNT(tblResultCompetency.Result) AS MEAN";
			query = query
					+ " FROM tblResultCompetency INNER JOIN "
					+ " tblAssignment ON tblResultCompetency.AssignmentID = tblAssignment.AssignmentID";
			query = query + " WHERE     (tblAssignment.TargetLoginID = "
					+ targetID
					+ " ) AND (tblAssignment.RaterStatus IN (1, 2, 4)) AND ";
			query = query + " (tblAssignment.AssignmentID <> " + iAssignmentID
					+ ") AND (tblResultCompetency.RatingTaskID = 1) ";
			// Edit by Roger 24 July 2008
			// Don't include TargetSelf in realibility calculation
			query = query + "AND tblAssignment.RaterCode<>'SELF'";

			query = query + " AND tblAssignment.SurveyID = " + surveyID;
			if (NA_Included == 0)
				query = query + " AND (tblResultCompetency.Result <> 0)";
			query = query + " GROUP BY tblResultCompetency.CompetencyID";
			query = query + " ORDER BY tblResultCompetency.CompetencyID";

		} else {
			/*
			 * query = query +
			 * "SELECT tblAvgMean.RatingTaskID, Competency.PKCompetency, ";
			 * query = query +
			 * "Competency.CompetencyName, tblAvgMean.KeyBehaviourID, cast(avg(tblAvgMean.AvgMean) as numeric(38,2)) AS MEAN "
			 * ; query = query + "FROM tblAvgMean INNER JOIN tblRatingTask ON ";
			 * query = query +
			 * "tblAvgMean.RatingTaskID = tblRatingTask.RatingTaskID INNER JOIN "
			 * ; query = query +
			 * "Competency ON tblAvgMean.CompetencyID = Competency.PKCompetency "
			 * ; query = query + "INNER JOIN KeyBehaviour ON "; query = query +
			 * "tblAvgMean.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour "; query
			 * = query + "WHERE tblAvgMean.Type = " + group; query = query +
			 * " AND tblAvgMean.SurveyID = " + surveyID; query = query +
			 * " AND tblAvgMean.TargetLoginID = " + targetID; query = query +
			 * " AND tblRatingTask.RatingCode  = 'CP' "; query = query +
			 * " group by tblAvgMean.RatingTaskID, Competency.PKCompetency, ";
			 * query = query +
			 * "Competency.CompetencyName, tblAvgMean.KeyBehaviourID ORDER BY ";
			 * query = query +
			 * "tblAvgMean.RatingTaskID, Competency.PKCompetency, Competency.CompetencyName, tblAvgMean.KeyBehaviourID"
			 * ;
			 */
			// Query rewite by Ha 04/07/08 to calculate the average of
			// competency score
			// of other people excluding the rater that the program is
			// calculating
			// Problem with old query: calculate average mean of every rater

			query = "SELECT    SUM(tblAvgMeanByRater.AvgMean) / COUNT(tblAvgMeanByRater.AvgMean) AS MEAN ";
			query = query
					+ " FROM   tblAvgMeanByRater INNER JOIN "
					+ " tblAssignment ON tblAvgMeanByRater.AssignmentID = tblAssignment.AssignmentID ";
			query = query + "WHERE  (tblAssignment.AssignmentID <> "
					+ iAssignmentID + " ) AND (tblAssignment.TargetLoginID = "
					+ targetID + " ) AND ";
			query = query
					+ "(tblAvgMeanByRater.RatingTaskID = 1) AND RaterStatus in (1,2,4)";
			// following 2 lines added by Ping Yang 0n 010908 to calculate the
			// average of competency score
			// of other people excluding the rater that the program is
			// calculating based on the particular survey only
			query = query + "AND tblAssignment.RaterCode<>'SELF'";
			query = query + " AND tblAssignment.SurveyID = " + surveyID;

			if (NA_Included == 0)
				query = query + " AND (tblAvgMeanByRater.AvgMean <> 0)";
			// query = query + " AND (tblResultCompetency.Result <> 0)"; Edited
			// by Ping Yang on 010908 error while running query
			// problem with old query:[SQLServer 2000 Driver for
			// JDBC][SQLServer]The multi-part identifier
			// "tblResultCompetency.Result" could not be bound.
			query = query + " GROUP BY tblAvgMeanByRater.CompetencyID ";
			query = query + " ORDER BY tblAvgMeanByRater.CompetencyID";

		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			// Changed by Ha 27/06/08 to add the average result to the vector
			while (rs.next()) {
				double AvgMean = rs.getDouble("MEAN");
				v.add(new Double(AvgMean));
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - getTargetAvgCompLevel - "
					+ E);
			/*
			 * //If all the other rater put NA and survey does not include NA,
			 * //It will throw divide by Zero exception //Sol: Set Average mean
			 * 0 for all CP //By Ha 01/07/08 int totalCompCP =
			 * TotalResult(surveyID); for (int i = 0; i < totalCompCP; i++)
			 * v.add(new Double(0));
			 */
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;
	}

	/**
	 * Retrieve the total Competency or Key Behaviour for a particular Survey.
	 */
	public int TotalList(int surveyID) {

		int surveyLevel = LevelOfSurvey(surveyID);
		String query = "";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		int iTotalList = 0;

		try {
			if (surveyLevel == 0)
				query = query
						+ "Select count(*) from tblSurveyCompetency where SurveyID = "
						+ surveyID;
			else {
				query = query
						+ "Select count(*) from tblSurveyBehaviour where SurveyID = "
						+ surveyID;
			}

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				iTotalList = rs.getInt(1);
		} catch (Exception E) {
			System.err.println("Calculation.java - TotalList - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}
		return iTotalList;
	}

	/**
	 * Check the existence of SELF in the particular Survey. Returns: 0 = NOT
	 * Exist. 1 = Exist.
	 */
	public int SelfExist(int surveyID, int targetID) {

		String query = "";
		int exist = 0;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			query = query + "Select * from tblAssignment where SurveyID = "
					+ surveyID;
			query = query + " and TargetLoginID = " + targetID
					+ " and RaterCode = 'SELF'";

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			if (rs.next())
				exist = 1;

		} catch (Exception E) {
			System.err.println("Calculation.java - SelfExist - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}
		return exist;
	}

	/**
	 * Update the rater status after reliability calculation if the rater is
	 * Unreliable.
	 */
	public boolean UpdateRaterStatus(int assignmentID, int r) {
		// Changed by Ha 16/06/08 update only if the rater status is not
		// excluded
		String sql = "Update tblAssignment set RaterStatus = " + r
				+ " where AssignmentID = " + assignmentID + " AND";
		sql += " RaterStatus <> 3";

		Connection con = null;
		Statement st = null;

		boolean bIsUpdated = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsUpdated = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - UpdateRaterStatus - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsUpdated;
	}

	/**
	 * This method is to calculate the Reliability of each rater. The
	 * calculation is by comparing two arrays. Array1 = results of the rater to
	 * be compared. Array2 = average mean of all raters including the rater to
	 * be compared, exclude SELF. The rules are 40/60-70. 40/60 = 40% of rating
	 * scale used. 70 = 70% of competencies/key behaviour which has more than
	 * 40/60% gap. Competencies compared only from CP, CPR, or FPR. Edit code by
	 * Ha 27/06/08
	 */
	public int calcReliability(double rater1[], double avg[], int range[],
			int totalRaters, int surveyID) {

		/**
		 * prints for debugging System.out.println(
		 * "----------------------------------------------------------");
		 * System.out.print("Rater Input : "); for (int i=0;i<rater1.length;i++)
		 * System.out.print(rater1[i]+",");System.out.println();
		 * System.out.print("Avg   Input : "); for (int i=0;i<avg.length;i++)
		 * System.out.print(avg[i]+",");System.out.println();
		 * System.out.print("Range       : "); for (int i=0;i<range.length;i++)
		 * System.out.print(range[i]+",");System.out.println();
		 * System.out.print("Total Raters: "); System.out.println(totalRaters);
		 * System.out.println();
		 **/

		int total = rater1.length;
		int totalUnreliable = 0;
		// Changed by Ha 27/06/08 from total/2-->total because now we only
		// include CP in calculation
		int totalCases = (int) (0.7 * total);// totalCases = 70% from all inputs
		double R = 0;
		int j = 0;
		double gap = 0; // gap = the gap between rater's input and avg of all
		int gapType = GapType(surveyID);
		int NA_Included = NAIncluded(surveyID);

		// Added by Ha 27/06/08 if the survey does not include NA total case =
		// 0.7 input that rater does not put NA

		if (NA_Included == 0) {
			// If NA is not included, then, if rater put all 0, change
			// realibility to NA
			int countNonZero = 0;
			for (int t = 0; t < rater1.length; t++) {
				if (rater1[t] != 0)
					countNonZero++;
			}
			if (countNonZero == 0)
				return 2; // if he puts all NA, his reliability is 2, which is
							// NA
			totalCases = (int) (0.7 * countNonZero);
		}

		if (totalRaters == 2) {
			R = range[j] * (0.6); // R = 40% of Scale Range used
		} else {
			R = range[j] * (0.4); // R = 40% of Scale Range used
		}

		// calculate RELIABILITY INDEX(Rater A, AVG) for ALL Raters EXCLUDES
		// SELF

		/**
		 * Comment off by Ha 27/06/08 since only CP is need for calculation, do
		 * not need variable k for distinguish the two rating tasks any more
		 * Code re-write below
		 ********/

		for (int i = 0; i < total; i++) {
			if (NA_Included == 1 || rater1[i] != 0) {
				gap = avg[i] - rater1[i];
				if (gap <= -R || gap >= R)
					totalUnreliable++;
			}
		}

		// Added by Ha 09/07/08 to handle the case when survey has only 1
		// competency
		if ((gapType == 1 || gapType == 2) && totalUnreliable >= totalCases
				&& totalUnreliable > 0) {
			// System.out.println("Realiable : 0");
			return 0;// unreliable
		} else {
			// System.out.println("Realiable : 1");
			return 1;// reliable
		}
		// End of code created by Ha 27/06/08

		/*
		 * for(int i=0; i<total; i++) { //gap = rater1[i] - avg[i]; gap = avg[i]
		 * - rater1[i];
		 * 
		 * 
		 * if(k == totalList) { j++; if(totalRaters == 2) R = range [j] * (0.6);
		 * // R = 60% of Scale Range used else R = range [j] * (0.4); // R = 40%
		 * of Scale Range used
		 * 
		 * k = -1; } k++;
		 * 
		 * 
		 * if(gap <= -R || gap >= R) totalUnreliable++;
		 * 
		 * //System.out.println("unreliable: " + totalUnreliable);
		 * 
		 * //check gapType. if((gapType == 1 || gapType == 2) && ((i+1) ==
		 * total/2)) { if(totalUnreliable >= totalCases) R1 = 0;
		 * 
		 * totalUnreliable = 0; }
		 * 
		 * //System.out.println("R : " + R + " ----GAp : " + gap + "---" +
		 * "rater1 : " + rater1[i] + "----" + "Avg :" + avg[i] + "--" +
		 * totalUnreliable); }
		 */
		// System.out.println("TotalUnreliable : " +
		// totalUnreliable+"--TotalCases : "+ totalCases +"----R1 : " + R1 +
		// "----" + R2);
		/*
		 * if(R1 == 1 && R2 == 1) return 1; return 0;
		 */
	}

	/**
	 * Add by Santoso (12 Nov 08) Check if the rater status should be set to NA
	 * by looking at the rater result
	 * 
	 * @param surveyID
	 *            the survey ID
	 * @param targetID
	 *            the target ID
	 * @param iFKAssignment
	 */
	public void checkNARater(int surveyID, int targetID, int iFKAssignment) {
		// raterResult: input of the rater for CP
		Vector raterResult = IndividualRaterResultCompLevel(iFKAssignment);

		// assume true
		boolean selfNA = true;
		// populate result1 with CP of rater
		for (int k = 0; k < raterResult.size(); k++) {
			String arr[] = (String[]) raterResult.elementAt(k);
			double value = Double.parseDouble(arr[2]);
			if (value != 0) {
				selfNA = false;
				break;
			}
		}
		if (NAIncluded(surveyID) == 0 && selfNA) {
			// set SELF to NA
			UpdateRaterStatus(iFKAssignment, 5);
		}
	}

	/**
	 * This method calculates the of all Raters EXCLUDE SELF. The objective is
	 * to co-relate one rater eg. Rater A with mean score of all raters to
	 * determine how reliable the rater A's score is. Code edited by Ha 27/06/08
	 */

	public void calculateReliability(int surveyID, int targetID, int include,
			int iFKAssignment) {

		int scaleRange[] = ScaleRange(surveyID);
		// get the total number of rater
		int totalRaters = totalRaters(surveyID, targetID, iFKAssignment); // should
																			// include
																			// self
		// create a array to store the rater
		int raterLoginID[] = new int[totalRaters];

		int assignment = 0;
		int total = 0;
		int j = 0;
		int r = 0;
		int raterStatus = 0;

		// get the total number of competency for the survey
		total = TotalResult(surveyID);
		// array created to store the result
		double result1[] = new double[total];
		double result2[] = new double[total]; // avg of all

		Vector raterResult = null;
		Vector result = null;

		// Added by Ha 27/06/08 total result that a rater put a value not NA of
		// CP
		int nonNAResult = 0;
		// Get a array of login Id (Include 1,2,4 / Exclude Self)
		raterLoginID = RatersID(surveyID, targetID, iFKAssignment, totalRaters);
		for (int t = 0; t < raterLoginID.length; t++) {
			assignment = AssignmentID(surveyID, targetID, raterLoginID[t]);

			// result: get the average CP input off all other rater exclude the
			// this rater
			result = getTargetAvgMeanCompLevel(surveyID, targetID, 1,
					assignment); // 1 = all
			j = 0;

			// populate result2 with average CP of each competency
			for (int k = 0; k < result.size(); k++)
				result2[j++] = ((Double) (result.elementAt(k))).doubleValue();

			// raterResult: input of the rater for CP
			raterResult = IndividualRaterResultCompLevel(assignment);
			j = 0;

			// populate result1 with CP of rater
			for (int k = 0; k < raterResult.size(); k++) {
				String arr[] = (String[]) raterResult.elementAt(k);
				result1[j] = Double.parseDouble(arr[2]);
				if (result1[j] != 0)
					nonNAResult++;
				j++;
			}
			// End of code changed by Ha 27/06/08
			// System.out.println("*****"+raterLoginID[t]+"******");
			r = calcReliability(result1, result2, scaleRange, totalRaters,
					surveyID);

			raterStatus = RaterStatus(assignment);
			if (totalRaters == 1) {
				int type = 1;
				if (NAIncluded(surveyID) == 0 && nonNAResult == 0)// if survey
																	// is not
																	// include
																	// NA and
																	// rater put
																	// all NA
					type = 5; // NA
				if (include == 1 && raterStatus == 4)
					type = 4; // included
				UpdateRaterStatus(assignment, type);
				r = 1;
			} else {
				int type = 1;
				if (r == 0) {
					type = 2;
					if (include == 1 && raterStatus == 4)
						// Changed by HA 13/06/08 type =5 to type = 4
						type = 4; // included
					UpdateRaterStatus(assignment, type);
				} else if (r == 1) {
					type = 1;
					if (include == 1 && raterStatus == 4)
						type = 4;
					// included
					UpdateRaterStatus(assignment, type);
				} else if (r == 2) {
					type = 5; // NA
					UpdateRaterStatus(assignment, type);
				}
			}
		}
		// Comment off by Ha.code rewrite above
		/*
		 * for(i=0; i<raterLoginID.length; i++) { assignment =
		 * AssignmentID(surveyID, targetID, raterLoginID[i]); raterStatus =
		 * RaterStatus(iFKAssignment); j=0;
		 * 
		 * 
		 * if(totalRaters == 2) { // means only 2 raters exclude SELF assignment
		 * = AssignmentID(surveyID, targetID, raterLoginID[i]); raterResult =
		 * IndividualRaterResultCompLevel(assignment); int temp=0; if(i == 0)
		 * temp = 1; assignment = AssignmentID(surveyID, targetID,
		 * raterLoginID[temp]); result =
		 * IndividualRaterResultCompLevel(assignment); for(int k=0;
		 * k<result.size(); k++) { String arr[] = (String[])result.elementAt(k);
		 * result1[j++] = Double.parseDouble(arr[2]); } } else { assignment =
		 * AssignmentID(surveyID, targetID, raterLoginID[i]); //Ha: need to
		 * change to include the CP only NA include/not include //input of the
		 * rater for every competency for CP raterResult =
		 * IndividualRaterResultCompLevel(assignment); } j=0; for(int k=0;
		 * k<raterResult.size(); k++) {
		 * 
		 * String arr[] = (String[])raterResult.elementAt(k); //Changed by Ha
		 * 26/06/08: should be result1 array instead of result2 array //result2
		 * is the array for storing the average value not value for input of the
		 * rater result1[j++] = Double.parseDouble(arr[2]); } //countNonZero =
		 * 0;
		 * 
		 * }
		 */
	}

	/**************************************** LEVEL OF AGREEMENT *****************************************************/

	/**
	 * 
	 * Calculate Individual Level Of Agreement. (22-Aug-05 Rianto) Changed
	 * Formula: - old Formula: 100 - (stdev(result) * 24) - new Formula: 100 -
	 * (stdev(result * 10 / " + iMaxScale + ") * BASE)
	 * 
	 * Note: (Result * 10 / " + iMaxScale + ") = To convert any scale to 10
	 * point scale before calculating LOA E.g: (2 * 10 / 4) = 5
	 * 
	 * @see getLOABase(int iNoOfRaters)
	 * @see MaxScale(int surveyID)
	 * 
	 *      Fixed by Jenty Chou on 8th Nov 06 - Fixed to calculate rater before
	 *      the status is set to complete
	 */
	public void IndividualLevelOfAgreement(int surveyID, int targetID,
			int iFKAssignment) throws SQLException, Exception {

		// Edit by Roger 21 July 2008
		// should not use getTotRatersCompleted to get the number of rater
		// because it is base on individual target.
		// number of raters should be based on individual competency

		// int iNoOfRaters = getTotRatersCompleted(surveyID, targetID,
		// iFKAssignment);
		// int iBase = getLOABase(iNoOfRaters);

		String query = "";
		int pkComp = 0, pkKB = 0, RTID = 0;
		double LOA = 100; // Default 100%
		int iMaxScale = rscale.getMaxScale(surveyID); // Get Maximum Scale of
														// this survey
		int surveyLevel = LevelOfSurvey(surveyID);
		int raterStatus = RaterStatus(iFKAssignment);

		deleteLOA(surveyID, targetID);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();

		String[] arr = null;

		try {
			if (surveyLevel == 0) {

				query = query
						+ "SELECT tblResultCompetency.RatingTaskID, CompetencyID, ";
				// Edit by Roger 21 July 2008
				// Move part of LOA calculation out of query. because number of
				// raters will be obtain in the same query and
				// it will be not possible to access getLOABase method to
				// calculate the base
				// by Hemilda 10/10/2008 to change the sequence LOA calculation
				// by changing (
				if (iMaxScale == 6)
					query = query
							+ "(stDev(Result)) AS LOA, count(*) as numberOfRaters ";
				else
					query = query + "(stDev(Result) * 10 / " + iMaxScale
							+ ") AS LOA, count(*) as numberOfRaters ";
				query = query
						+ "FROM tblAssignment INNER JOIN tblResultCompetency ";
				query = query
						+ "ON tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
				query = query + "INNER JOIN tblRatingTask ON ";
				query = query
						+ "tblResultCompetency.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "where SurveyID = " + surveyID
						+ " AND (RaterStatus IN (1,2,4)";
				// Added by Ha 01/07/08 the OR part is needed when we do not set
				// the status first before calculation only
				// Problem wth old query: result will be the same even if raters
				// are included/excluded
				if (raterStatus == 0)
					query += " or tblAssignment.AssignmentID = "
							+ iFKAssignment;
				query = query + " ) AND TargetLoginID = " + targetID
						+ " and tblAssignment.RaterCode <> 'SELF' ";
				query = query + "AND tblRatingTask.RatingCode = 'CP'";
				// Added by Ha 08/07/08 to excluded the NA if NA is not included
				// in the survey
				if (NAIncluded(surveyID) == 0)
					query += " AND tblResultCompetency.Result <> 0";
				query = query
						+ " GROUP BY tblResultCompetency.RatingTaskID, CompetencyID ";
				query = query
						+ "order by tblResultCompetency.RatingTaskID, CompetencyID";

			} else {
				// by Hemilda 10/10/2008 to change the sequence LOA calculation
				// by changing (
				query = query
						+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
				query = query + "tblResultBehaviour.KeyBehaviourID, ";
				if (iMaxScale == 6)
					query = query
							+ "(STDEV(tblResultBehaviour.Result))  AS LOA, count(*) as numberOfRaters  ";
				else
					query = query + "(STDEV(tblResultBehaviour.Result) * 10 / "
							+ iMaxScale
							+ ")  AS LOA, count(*) as numberOfRaters  ";
				query = query
						+ "FROM tblAssignment INNER JOIN tblResultBehaviour ON ";
				query = query
						+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
				query = query
						+ "KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
				query = query + "INNER JOIN tblRatingTask ON ";
				query = query
						+ "tblResultBehaviour.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
				query = query + " AND tblAssignment.TargetLoginID = "
						+ targetID;
				query = query
						+ " AND (tblAssignment.RaterStatus IN (1, 2, 4) or tblAssignment.AssignmentID = "
						+ iFKAssignment + ") AND ";
				query = query + "tblAssignment.RaterCode <> 'SELF' ";
				query = query + "AND tblRatingTask.RatingCode = 'CP' ";
				// include by roger
				if (NAIncluded(surveyID) == 0)
					query += " AND tblResultBehaviour.Result <> 0 ";
				query = query
						+ "GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
				query = query
						+ "tblResultBehaviour.KeyBehaviourID ORDER BY tblResultBehaviour.RatingTaskID, ";
				query = query
						+ "KeyBehaviour.FKCompetency, tblResultBehaviour.KeyBehaviourID";
			}

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				LOA = 100;
				RTID = rs.getInt("RatingTaskID");
				pkComp = rs.getInt(2);
				if (surveyLevel == 1) {
					pkKB = rs.getInt("KeyBehaviourID");
					arr = new String[4];
					arr[3] = Integer.toString(pkKB);
				} else {
					arr = new String[3];
				}

				// Edit by Roger 21 July 2008
				// Shift calculation of actual LOA here
				// fix for comptency level
				if (surveyLevel == 0) {
					int numberOfRaters = rs.getInt("numberOfRaters");
					if (numberOfRaters > 1) {
						LOA = 100.0 - (rs.getDouble("LOA") * getLOABase(numberOfRaters));
					}
				} else {
					// Filter if more than 1 rater, get LOA from calculation,
					// else if 1 Rater, LOA = 100%
					int numberOfRaters = rs.getInt("numberOfRaters");

					if (numberOfRaters > 1) {
						LOA = 100.0 - (rs.getDouble("LOA") * getLOABase(numberOfRaters));
					}
				}

				// round to 2 decimal place
				BigDecimal bd = new BigDecimal(LOA);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); // round to 2
																// decimal place
				LOA = bd.doubleValue();

				arr[0] = Integer.toString(RTID);
				arr[1] = Integer.toString(pkComp);
				arr[2] = Double.toString(LOA);

				v.add(arr);
			}
		} catch (Exception E) {
			System.err
					.println("Calculation.java - IndividualLevelOfAgreement - "
							+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Propertly Close ResultSet,
											// Sebastian 29 June 2010
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		for (int i = 0; i < v.size(); i++) {
			arr = (String[]) v.elementAt(i);
			RTID = Integer.parseInt(arr[0]);
			pkComp = Integer.parseInt(arr[1]);
			LOA = Double.parseDouble(arr[2]);

			if (surveyLevel == 1)
				pkKB = Integer.parseInt(arr[3]);

			try {
				// System.out.println("Writing to DB, LOA = " + LOA);
				WriteLOAtoDB(surveyID, targetID, pkComp, pkKB, RTID, LOA);
				// System.out.println(pkComp + "---" + LOA);
			} catch (SQLException SE) {
			}
		}

	}

	public int getTotRatersCompleted(int SurveyID, int TargetLoginID,
			int iFKAssignment) throws SQLException, Exception {
		int TotRatersCompleted = 0;
		int raterStatus = RaterStatus(iFKAssignment);
		int NA_Included = NAIncluded(SurveyID);
		String command = "SELECT COUNT(*) AS TotRatersCompleted FROM tblAssignment WHERE (SurveyID = "
				+ SurveyID + ") ";
		command = command + "AND (TargetLoginID = " + TargetLoginID + ") ";
		command = command + "AND RaterLoginID <> " + TargetLoginID;
		if (NA_Included == 0)
			command = command + " AND (RaterStatus IN (1,2,4)";
		else
			command = command + " AND (RaterStatus IN (1,2,4,5) ";
		// Problem with old query: it will calculate all the raters even if the
		// raters status are excluded or not available
		// Added if Statment by Ha 01/07/08 this part is only needed when we do
		// not set the rater status first before calculation
		// so the last rater will not be calculated. Therefore, we should not
		// put OR expression in the query without the condition
		if (raterStatus == 0)
			command = command + " or tblAssignment.AssignmentID = "
					+ iFKAssignment;
		command = command + ")";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			if (rs.next())
				TotRatersCompleted = rs.getInt("TotRatersCompleted");

		} catch (Exception E) {
			System.err.println("Calculation.java - getTotRatersCompleted - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return TotRatersCompleted;
	}

	public boolean deleteLOA(int surveyID, int targetID) {

		String sql = "Delete from tblLevelOfAgreement where SurveyID = "
				+ surveyID + " and TargetLoginID = " + targetID;

		Connection con = null;
		Statement st = null;

		boolean bIsDeleted = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsDeleted = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - deleteLOA- " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsDeleted;
	}

	/**
	 * Write the Individual Level Of Agreement result to tblLevelOfAgreement.
	 * This result will be displayed in Report for each Target.
	 */
	public boolean WriteLOAtoDB(int surveyID, int targetID, int pkComp,
			int pkKB, int RTID, double stDev) throws SQLException {

		String sql = "";

		sql = "Insert into tblLevelOfAgreement values (" + surveyID + ","
				+ targetID;
		sql = sql + "," + RTID + ", " + pkComp + "," + pkKB + "," + stDev + ")";

		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - writeLOAtoDB - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsAdded;
	}

	/*
	 * (22-Aug-05 Rianto) Changed Formula: - old Formula: 100 - (stdev(result) *
	 * 24) - new Formula: 100 - (stdev(result) * BASE)
	 * 
	 * Total of Raters BASE --------------- ----- 1 No need to calculate, 1
	 * rater LOA = 100% (Filtered by codes) 2 14 3 16 4 18 5 21 6 23 7 and above
	 * 24
	 * 
	 * @param iNoOfRater int
	 * 
	 * @return iBase int
	 */
	public int getLOABase(int iNoOfRater) {
		int iBase;

		switch (iNoOfRater) {
		case 2:
			iBase = 14;
			break;
		case 3:
			iBase = 16;
			break;
		case 4:
			iBase = 18;
			break;
		case 5:
			iBase = 21;
			break;
		case 6:
			iBase = 23;
			break;
		default: // 7 and above
			iBase = 24;
		}

		return iBase;
	}

	/******************************************** GROUP LEVEL OF AGREEMENT *********************************************/

	/**
	 * THIS FUNCTION IS NOT BEING USED CURRENTLY, GROUP LOA IN GroupReport.java
	 * 
	 * Calculate Group Level Of Agreement based on SurveyID, GroupSection,
	 * DeptID, and DivID. (22-Aug-05 Rianto) Changed Formula: - old Formula: 100
	 * - (stdev(result) * 24) - new Formula: 100 - (stdev(result) * BASE)
	 * 
	 * @see getLOABase(int iNoOfRaters)
	 */
	public void GroupLevelOfAgreement(int surveyID, int groupSection,
			int deptID, int divID) throws SQLException, Exception {

		// Get No of Raters
		int iNoOfRaters = assign.getTotRatersCompleted(surveyID, groupSection,
				deptID, divID);
		int iBase = getLOABase(iNoOfRaters);

		String query = "";
		int surveyLevel = LevelOfSurvey(surveyID);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();
		try {

			if (surveyLevel == 0) {
				query = query
						+ "SELECT tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID,";
				query = query
						+ "CAST(100 - (STDEV(tblResultCompetency.Result) * "
						+ iBase + ") AS numeric(38, 2)) AS LOA ";
				query = query + "FROM [User] INNER JOIN tblAssignment ON ";
				query = query
						+ "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN ";
				query = query + "tblResultCompetency ON ";
				query = query
						+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID ";
				query = query + "INNER JOIN tblRatingTask ON ";
				query = query
						+ "tblResultCompetency.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
				query = query + " AND [User].Group_Section = " + groupSection;

				if (deptID != 0)
					query = query + " AND [User].PKDepartment = " + deptID;
				if (divID != 0)
					query = query + " AND [User].PKDivision = " + divID;

				query = query
						+ " AND tblAssignment.RaterStatus IN (1, 2, 4) AND ";
				query = query + "tblAssignment.RaterCode <> 'SELF' ";
				query = query + "AND tblRatingTask.RatingCode = 'CP' ";
				query = query
						+ "GROUP BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID ";
				query = query
						+ "ORDER BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID";
			} else {
				query = query
						+ "SELECT tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
				query = query + "tblResultBehaviour.KeyBehaviourID, ";
				query = query
						+ "CAST(100 - STDEV(tblResultBehaviour.Result) * "
						+ iBase + " AS numeric(38, 2)) AS LOA ";
				query = query + "FROM [User] INNER JOIN tblAssignment ON ";
				query = query
						+ "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN ";
				query = query + "tblResultBehaviour ON ";
				query = query
						+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID INNER JOIN ";
				query = query
						+ "KeyBehaviour ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour ";
				query = query + "INNER JOIN tblRatingTask ON ";
				query = query
						+ "tblResultBehaviour.RatingTaskID = tblRatingTask.RatingTaskID ";
				query = query + "WHERE tblAssignment.SurveyID = " + surveyID;
				query = query + " AND [User].Group_Section = " + groupSection
						+ " AND ";
				query = query
						+ "tblAssignment.RaterStatus IN (1, 2, 4) AND tblAssignment.RaterCode <> 'SELF' ";
				query = query + "AND tblRatingTask.RatingCode = 'CP' ";
				query = query
						+ "GROUP BY tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
				query = query + "tblResultBehaviour.KeyBehaviourID ORDER BY ";
				query = query
						+ "tblResultBehaviour.RatingTaskID, KeyBehaviour.FKCompetency, ";
				query = query + "tblResultBehaviour.KeyBehaviourID";
			}

			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				RTID = rs.getInt("RatingTaskID");
				competencyID = rs.getInt(2);
				if (surveyLevel == 1)
					KBID = rs.getInt("KeyBehaviourID");

				// Filter if more than 1 rater, get LOA from calculation, else
				// if 1 Rater, LOA = 100%
				if (iNoOfRaters > 1)
					avgMean = rs.getDouble("LOA");

				String[] arr = new String[4];

				arr[0] = Integer.toString(RTID);
				arr[1] = Integer.toString(competencyID);
				arr[2] = Integer.toString(KBID);
				arr[3] = Double.toString(avgMean);
				v.add(arr);

			}
		} catch (Exception E) {
			System.err.println("Calculation.java - GroupLevelOfAgreement - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		for (int i = 0; i < v.size(); i++) {
			String arr[] = (String[]) v.elementAt(i);

			RTID = Integer.parseInt(arr[0]);
			competencyID = Integer.parseInt(arr[1]);
			KBID = Integer.parseInt(arr[2]);
			avgMean = Double.parseDouble(arr[3]);
			WriteGroupLOAtoDB(surveyID, competencyID, KBID, RTID, avgMean,
					groupSection);
		}
	}

	/**
	 * Write the Group Level Of Agreement result to tblSurveyStDev. This result
	 * will be displayed in Proces Survey for each group.
	 */
	public boolean WriteGroupLOAtoDB(int surveyID, int pkComp, int pkKB,
			int RTID, double stDev, int groupSection) throws SQLException {
		String sql = "Insert into tblGroupLevelOfAgreement values (" + surveyID;
		sql = sql + "," + RTID + ", " + pkComp + "," + pkKB + "," + stDev + ","
				+ groupSection + ")";

		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);
			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - WriteGroupLOAtoDB - " + E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsAdded;
	}

	/***************** SURVEY AVERAGE MEAN (might not be in use anymore) *********************************/

	/**
	 * Calculate Average Mean Score for each Rating Task for the specific
	 * survey.
	 * 
	 * 
	 */
	public Vector SurveyAvgMean(int surveyID, int group, int groupSection) {
		String query = "";
		String whereFilter = "";

		int surveyLevel = LevelOfSurvey(surveyID);

		if (group == 1)
			whereFilter = " and tblAssignment.RaterCode <> 'SELF' ";
		else
			// 2=SELF
			whereFilter = " and tblAssignment.RaterCode = 'SELF' ";

		Vector v = new Vector();

		if (surveyLevel == 0) {
			query = query
					+ "SELECT  tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
			query = query
					+ "RatingTaskID, cast(Avg(Result) as float) AS AvgOfResult FROM [User] ";
			query = query
					+ "INNER JOIN (tblAssignment INNER JOIN tblResultCompetency ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultCompetency.AssignmentID) ";
			query = query
					+ "ON [User].PKUser = tblAssignment.TargetLoginID INNER JOIN Competency ON ";
			query = query
					+ "tblResultCompetency.CompetencyID = Competency.PKCompetency ";
			query = query + "Where SurveyID = " + surveyID
					+ " AND tblAssignment.RaterStatus IN (1,2,4)";
			query = query + whereFilter;
			query = query
					+ "GROUP BY tblResultCompetency.RatingTaskID, tblResultCompetency.CompetencyID, ";
			query = query
					+ "Competency.CompetencyName, [User].Group_Section HAVING ";
			query = query + "[User].Group_Section = " + groupSection;
			query = query
					+ " ORDER BY tblResultCompetency.CompetencyID, Competency.CompetencyName, ";
			query = query + "tblResultCompetency.RatingTaskID";
		} else {
			query = query
					+ "SELECT KeyBehaviour.FKCompetency, Competency.CompetencyName, ";
			query = query
					+ "tblResultBehaviour.RatingTaskID, cast(Avg(tblResultBehaviour.Result) as float) AS AvgOfResult , ";
			query = query
					+ "tblResultBehaviour.KeyBehaviourID, KeyBehaviour.KeyBehaviour ";
			query = query + "FROM [User] INNER JOIN tblAssignment INNER JOIN ";
			query = query + "tblResultBehaviour ON ";
			query = query
					+ "tblAssignment.AssignmentID = tblResultBehaviour.AssignmentID ON ";
			query = query
					+ "[User].PKUser = tblAssignment.TargetLoginID INNER JOIN KeyBehaviour ";
			query = query
					+ "ON tblResultBehaviour.KeyBehaviourID = KeyBehaviour.PKKeyBehaviour INNER JOIN ";
			query = query
					+ "Competency ON KeyBehaviour.FKCompetency = Competency.PKCompetency ";
			query = query + "WHERE tblAssignment.SurveyID = " + surveyID
					+ " AND tblAssignment.RaterStatus IN (1,2,4) ";
			query = query
					+ "GROUP BY tblResultBehaviour.RatingTaskID, tblResultBehaviour.KeyBehaviourID, ";
			query = query
					+ "KeyBehaviour.FKCompetency, Competency.CompetencyName, ";
			query = query + "KeyBehaviour.KeyBehaviour, ";
			query = query + "[User].Group_Section HAVING ";
			query = query + "[User].Group_Section = " + groupSection;
			query = query + " ORDER BY KeyBehaviour.FKCompetency, ";
			query = query
					+ "Competency.CompetencyName, tblResultBehaviour.KeyBehaviourID, ";
			query = query
					+ "KeyBehaviour.KeyBehaviour, tblResultBehaviour.RatingTaskID";
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				String[] arr = new String[5];
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);

				if (surveyLevel == 1)
					arr[4] = rs.getString(5);

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - SurveyAvgMean - " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection

		}

		return v;

	}

	/**
	 * Save all calculated data to tblAvgMean for future usage, such as to
	 * calculate Pearson and display all the results in Survey List.
	 */
	public boolean writeSurveyAvgMeanToDB(int surveyID, int group,
			int groupSection) {

		String sql = "Insert into tblSurveyAvgMean values (" + surveyID + ","
				+ competencyID + ",";
		sql = sql + KBID + "," + RTID + "," + avgMean + "," + group + ","
				+ groupSection + ")";

		Connection con = null;
		Statement st = null;

		boolean bIsAdded = false;
		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			int iSuccess = st.executeUpdate(sql);

			if (iSuccess != 0)
				bIsAdded = true;

		} catch (Exception E) {
			System.err.println("Calculation.java - writeSurveyAvgMeanToDB - "
					+ E);
		} finally {
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return bIsAdded;
	}

	public void calculateSurveyAvgMean(int surveyID, int group, int groupSection) {
		int surveyLevel = LevelOfSurvey(surveyID);

		Vector rs = SurveyAvgMean(surveyID, group, groupSection);

		for (int i = 0; i < rs.size(); i++) {
			String[] arr = (String[]) rs.elementAt(i);

			RTID = Integer.parseInt(arr[2]);
			competencyID = Integer.parseInt(arr[0]);
			if (surveyLevel == 1)
				KBID = Integer.parseInt(arr[4]);
			avgMean = Double.parseDouble(arr[3]);

			writeSurveyAvgMeanToDB(surveyID, group, groupSection);
		}

	}

	/********************************************** BEANS VARIABLES ****************************************************/

	/**
	 * Store bean variable SurveyID.
	 */
	public void setSurveyID(int SurveyID) {
		this.SurveyID = SurveyID;
	}

	/**
	 * Get the bean variable SurveyID.
	 */
	public int getSurveyID() {
		return SurveyID;
	}

	/**
	 * Store the bean variable GroupSection.
	 */
	public void setGroupSection(int GroupSection) {
		this.GroupSection = GroupSection;
	}

	/**
	 * Get the bean variable GroupSection.
	 */
	public int getGroupSection() {
		return GroupSection;
	}

	/**
	 * Store the bean variable TargetID.
	 */
	public void setTargetID(int TargetID) {
		this.TargetID = TargetID;
	}

	/**
	 * Get the bean variable TargetID.
	 */
	public int getTargetID() {
		return TargetID;
	}

	/**
	 * Store the bean variable RaterID.
	 */
	public void setRaterID(int RaterID) {
		this.RaterID = RaterID;
	}

	/**
	 * Get the bean variable RaterID.
	 */
	public int getRaterID() {
		return RaterID;
	}

	public void reCalculateReliability(int SurveyID) throws SQLException,
			Exception {

		String command = "SELECT DISTINCT SurveyID, TargetLoginID FROM tblAssignment WHERE SurveyID = "
				+ SurveyID + " ORDER BY SurveyID, TargetLoginID";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Vector v = new Vector();

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(command);

			while (rs != null && rs.next()) {
				int[] arr = new int[2];
				int surveyID = rs.getInt("SurveyID");
				int targetLoginID = rs.getInt("TargetLoginID");

				arr[0] = surveyID;
				arr[1] = targetLoginID;

				v.add(arr);
			}

		} catch (Exception E) {
			System.err.println("Calculation.java - recalculateReliability - "
					+ E);
		} finally {
			ConnectionBean.closeRset(rs); // Close ResultSet
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		for (int i = 0; i < v.size(); i++) {
			int[] arr = (int[]) v.elementAt(i);

			int surveyID = arr[0];
			int targetLoginID = arr[1];

			String command1 = "SELECT TOP 1 AssignmentID from tblAssignment where SurveyID = "
					+ surveyID
					+ " and TargetLoginID = "
					+ targetLoginID
					+ " and RaterCode <> 'SELF' ";

			int assignmentID = 0;

			try {
				con = ConnectionBean.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(command1);

				if (rs != null && rs.next())
					assignmentID = rs.getInt("AssignmentID");

			} catch (Exception E) {
				System.err
						.println("Calculation.java - recalculateReliability - "
								+ E);
			} finally {
				ConnectionBean.closeRset(rs); // Close ResultSet
				ConnectionBean.closeStmt(st); // Close statement
				ConnectionBean.close(con); // Close connection
			}

			calculateReliability(surveyID, targetLoginID, 0, assignmentID);
		}

	}

	/**
	 * getRaterStatus
	 * 
	 * @param RaterLoginID
	 * @return int RaterStatus 0 = incompleted 1 = completed 2 = unreliable 3 =
	 *         excluded 4 = included 5 = NA
	 * @author Ha by 10/06/08
	 * 
	 */

	public static int getRaterStatus(int RaterLoginID) {
		int raterStatus = 0;
		String sql = "Select * from tblAssignment where RaterLoginID = "
				+ RaterLoginID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next())
				raterStatus = rs.getInt("RaterStatus");

		} catch (Exception E) {
			System.err.println("Calculation.java - getRaterStatus- " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Properly Close ResultSet, Sebastian
											// 29 June 2010
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return raterStatus;
	}

	public static int RaterStatus(int AssignmentID) {
		int raterStatus = 0;
		String sql = "Select * from tblAssignment where AssignmentID = "
				+ AssignmentID;

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = ConnectionBean.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next())
				raterStatus = rs.getInt("RaterStatus");

		} catch (Exception E) {
			System.err.println("Calculation.java - getRaterStatus- " + E);
		} finally {
			ConnectionBean.closeRset(rs); // Properly Close ResultSet, Sebastian
											// 29 June 2010
			ConnectionBean.closeStmt(st); // Close statement
			ConnectionBean.close(con); // Close connection
		}

		return raterStatus;
	}

	public static void main(String args[]) throws SQLException, Exception {
		Calculation c = new Calculation();

		// calculateReliability(498, 6623, 0, 12409);
		// AssignTarget_Rater assign = new AssignTarget_Rater();

		// int iNoOfRaters = assign.getTotRatersCompleted(445, 6406);
		// double iBase = c.getLOABase(iNoOfRaters);
		// System.out.println("No of Raters = " + iNoOfRaters + "\nBASE = " +
		// iBase);

		// c.IndividualLevelOfAgreement(445, 6410, 6240);

		// calculateReliability(int surveyID, int targetID, int include)

		// c.calculateReliability(478, 6630, 0, 6847);
		// c.reCalculateReliability(475);
		// c.reCalculateReliability(476);

	}
}
